<#import "/layouts/mainLayout.ftl" as mainLayout>
<#import "/utils.ftl" as utils>

<@mainLayout.layout>
    <p>
        <a href="/">
            Return to home
        </a>
    </p>

    <h2>Dye dopant: ${dyeDopant.name}</h2>

    <p>
        <#assign quantumEfficiency = dyeDopant.tauNR/(dyeDopant.tauNR+dyeDopant.tauRad) />
        Dopant: ${dyeDopant.name}, tauRad: ${dyeDopant.tauRad?string("0.##E0")}, tauNR: ${dyeDopant.tauNR?string("0.##E0")}, quantum efficiency: ${quantumEfficiency?string("0.####")}
    </p>

    <div style="max-width: 80%; margin-left: 10%; margin-right: 10%;">
        <canvas id="sigmasChart" width="400" height="400"></canvas>
    </div>

    <@utils.plotLineChartScript chartId="sigmasChart" plotData=dyeDopantPlot/>
</@>