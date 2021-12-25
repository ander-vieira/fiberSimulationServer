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
                    <a href="/dyeDopant/${dyeDopant}">${dyeDopant}</a>
                </li>
            </#list>
        </ul>
    <#else>
        <p>No dye dopants were found.</p>
    </#if>
</@>