package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.iterative.*;
import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.core.util.SimulationTimer;
import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.resources.reader.DyeDopantReader;
import com.fibersim.fiberSimulationServer.resources.reader.MediumReader;
import com.fibersim.fiberSimulationServer.resources.reader.PowerSourceReader;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IterativeSimService {
    @Autowired
    DyeDopantReader dyeDopantReader;
    @Autowired
    MediumReader mediumReader;
    @Autowired
    PowerSourceReader powerSourceReader;

    public IterativeSimResponseDTO dyeIterative(IterativeSimParamsDTO params) {
        SimulationTimer simulationTimer = new SimulationTimer();
        simulationTimer.startTimer();

        double dz = 5e-5;
        int numZZ = (int)Math.ceil(params.getLength()/dz);

        List<DyeDopantDTO> dyeDopants = dyeDopantReader.readDopants(params.getDyeDopants());

        MediumResource pmma = mediumReader.readMedium("PMMA");
        MediumResource clad = mediumReader.readMedium("clad");

        PowerSourceResource sun = powerSourceReader.readSource("AM1.5");

        int numLL = 151;
        LambdaRange lambdaRange = new LambdaRange();
        for(DyeDopantDTO dyeDopant : dyeDopants) {
            lambdaRange.addDopant(dyeDopant.getDyeDopant());
        }
        double[] ll = lambdaRange.getLL(numLL);
        double dlambda = lambdaRange.getDLambda(numLL);

        double Nsolconst = SideAbsorption.twoInterphases(sun, dyeDopants.get(0), pmma, clad, params.getDiameter(), 0.98, dlambda, ll);

        IterativeAttenuator iterativeAttenuator = new IterativeAttenuator(pmma, ll, dz);
        IterativeDyeDopant iterativeDyeDopant = new IterativeDyeDopant(IterativeDyeDopantParams.builder()
                .dyeDopantDTO(dyeDopants.get(0))
                .medium(pmma)
                .diameter(params.getDiameter())
                .dz(dz)
                .Nsolconst(Nsolconst)
                .numZZ(numZZ)
                .ll(ll)
                .build());

        double[] finalP = new double[numLL];
        double lambdaP, lambdaPleft, error, e, oldLambdaP, oldLambdaPleft;

        do {

            //Update N2
            iterativeDyeDopant.updateN2();

            double[] previousP = finalP.clone();

            for(int k = 0 ; k < numLL ; k++) {
                //Propagate P to the right
                lambdaP = 0;
                for(int j = 0 ; j < numZZ-1 ; j++) {
                    oldLambdaP = lambdaP;

                    //Update P
                    lambdaP += iterativeAttenuator.updateP(oldLambdaP, k);
                    lambdaP += iterativeDyeDopant.updateP(oldLambdaP, j, k);

                    //Update wabs and west
                    iterativeDyeDopant.updateW(oldLambdaP, lambdaP, j, k);
                }

                finalP[k] = lambdaP;

                //Propagate P to the left
                lambdaPleft = 0;
                for(int j = numZZ-2 ; j >= 0 ; j--) {
                    oldLambdaPleft = lambdaPleft;

                    //Update Pleft
                    lambdaPleft += iterativeAttenuator.updateP(oldLambdaPleft, k);
                    lambdaPleft += iterativeDyeDopant.updateP(oldLambdaPleft, j, k);

                    //Update wabs and west
                    iterativeDyeDopant.updateW(oldLambdaPleft, lambdaPleft, j, k);
                }
            }

            error = 0;
            for(int k = 0 ; k < numLL ; k++) {
                e = Math.abs(finalP[k]-previousP[k])/(finalP[k]+Double.MIN_VALUE);

                if(e > error) error = e;
            }
        } while(error > 1e-8);

        return IterativeSimResponseDTO.builder()
                .elapsedTime(simulationTimer.getTime())
                .lightP(Arrays.stream(finalP).sum())
                .build();
    }
}