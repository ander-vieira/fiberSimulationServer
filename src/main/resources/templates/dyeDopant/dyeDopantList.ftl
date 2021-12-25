<#import "/layouts/mainLayout.ftl" as mainLayout>

<@mainLayout.layout>
    <p>
        <a href="/">
            Return to home
        </a>
    </p>

    <h2>Dye dopants</h2>

    <#if dyeDopants?has_content>
        <ul>
            <#list dyeDopants as dyeDopant>
                <li>
                    <#assign quantumEfficiency = dyeDopant.tauNR/(dyeDopant.tauNR+dyeDopant.tauRad) />
                    Dopant: ${dyeDopant.name}, tauRad: ${dyeDopant.tauRad?string("0.##E0")}, tauNR: ${dyeDopant.tauNR?string("0.##E0")}, quantum efficiency: ${quantumEfficiency?string("0.####")}
                </li>
            </#list>
        </ul>
    <#else>
        <p>No dye dopants were found.</p>
    </#if>
</@>