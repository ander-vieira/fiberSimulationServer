package com.fibersim.fiberSimulationServer.service.resources;

public class DyeDopant {
    public double tauRad;
    public double tauNR;
    public FunctionLL sigmaabs;
    public FunctionLL sigmaemi;
    public double minLambda;
    public double maxLambda;

    public static final String SIGMAS_PREFIX = "/csv/";

    public DyeDopant(String dopant) {
        switch (dopant) {
            case "RhB":
                tauRad = 4.8e-9;
                tauNR = 1;
                sigmaabs = new SplineCSV(SIGMAS_PREFIX+"sigmaabs_RhB.csv", 559.29e-9, 3.37e-20);
                sigmaemi = new SplineCSV(SIGMAS_PREFIX+"sigmaemi_RhB.csv", 571.08e-9, 2.4973e-20);
                minLambda = 360e-9;
                maxLambda = 760e-9;
                break;
            case "LumR":
                tauRad = 6e-9;
                tauNR = 1.14e-7;
                sigmaabs = new SplineCSV(SIGMAS_PREFIX+"sigmaabs_LumR.csv", 573.37e-9, 1.9862e-20);
                sigmaemi = new SplineCSV(SIGMAS_PREFIX+"sigmaemi_LumR.csv", 600.85e-9, 2.1984e-20);
                minLambda = 360e-9;
                maxLambda = 750e-9;
                break;
            case "LumO":
                tauRad = 6e-9;
                tauNR = 7.4e-8;
                sigmaabs = new SplineCSV(SIGMAS_PREFIX+"sigmaabs_LumO.csv", 527.00e-9, 2.5290e-20);
                sigmaemi = new SplineCSV(SIGMAS_PREFIX+"sigmaemi_LumO.csv", 569.00e-9, 2.1047e-20);
                minLambda = 320e-9;
                maxLambda = 740e-9;
                break;
            case "LumY":
                tauRad = 6e-9;
                tauNR = 1.14e-7;
                sigmaabs = new SplineCSV(SIGMAS_PREFIX+"sigmaabs_LumY.csv", 477.00e-9, 2.3849e-20);
                sigmaemi = new SplineCSV(SIGMAS_PREFIX+"sigmaemi_LumY.csv", 495.00e-9, 1.0178e-20);
                minLambda = 360e-9;
                maxLambda = 650e-9;
                break;
            default: //Rh6G
                tauRad = 4.8e-9;
                tauNR = 1;
                sigmaabs = new SplineCSV(SIGMAS_PREFIX+"sigmaabs_Rh6G.csv", 530.58e-9, 4.3298e-20);
                sigmaemi = new SplineCSV(SIGMAS_PREFIX+"sigmaemi_Rh6G.csv", 544.02e-9, 2.0375e-20);
                minLambda = 410e-9;
                maxLambda = 710e-9;
                break;
        }
    }

}
