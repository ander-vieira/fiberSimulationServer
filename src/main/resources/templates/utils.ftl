<#macro generateSelectVue elementsList modelVar>
    <select v-model="${modelVar}">
        <#list elementsList as element>
            <option>${element}</option>
        </#list>
    </select>
</#macro>

<#macro plotLineChartScript chartId xData yDataList>
    <#assign maxValue = 0 />
    <#list yDataList as yData>
        <#if maxValue < yData.data?max>
            <#assign maxValue = yData.data?max />
        </#if>
    </#list>

    <script>
        const ctx = document.getElementById('${chartId}').getContext('2d');

        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [
                    <#list xData as x>
                        ${x?string("0.##E0")}
                        <#sep>,</#sep>
                    </#list>
                ],
                datasets: [
                    <#list yDataList as yData>
                        {
                            label: '${yData.name}',
                            data: [
                                <#list yData.data as y>
                                    ${y?string("0.##E0")}
                                    <#sep>,</#sep>
                                </#list>
                            ],
                            fill: false,
                            pointRadius: 0,
                            backgroundColor: '${yData.color}',
                            borderColor: '${yData.color}'
                        }
                        <#sep>,</#sep>
                    </#list>
                ]
            },
            options: {
                scales: {
                    xAxes: {
                    },
                    yAxes: {
                        beginAtZero: true,
                        max: ${maxValue?string("0.##E0")},
                        ticks: {
                            format: { maximumSignificantDigits: 4 },
                            count: 11
                        },
                    },
                }
            }
        });
    </script>
</#macro>