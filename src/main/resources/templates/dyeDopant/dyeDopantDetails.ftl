<#import "/layouts/mainLayout.ftl" as mainLayout>
<#import "/utils.ftl" as utils>

<@mainLayout.layout>
    <p>
        <a href="/dyeDopant">
            Return to list
        </a>
    </p>

    <h2>Dye dopant: ${dyeDopant.name}</h2>

    <p>
        tauRad: ${dyeDopant.tauRad?string("0.##E0")}
    </p>
    <p>
        tauNR: ${dyeDopant.tauNR?string("0.##E0")}
    </p>
    <p>
        Quantum efficiency: ${dyeDopant.quantumEfficiency?string("0.####")}
    </p>

    <div style="max-width: 80%; margin-left: 10%; margin-right: 10%;">
        <canvas id="sigmasChart" width="400" height="400"></canvas>
    </div>

    <@utils.plotLineChartScript chartId="sigmasChart" plotData=dyeDopantPlot/>
</@>