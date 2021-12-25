<#macro generateSelectVue elementsList modelVar>
    <select v-model="${modelVar}">
        <#list elementsList as element>
            <option>${element}</option>
        </#list>
    </select>
</#macro>

<#macro plotLineChartScript chartId plotData>
    <script>
        const ctx = document.getElementById('${chartId}').getContext('2d');

        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [
                    <#list plotData.XData as x>
                        ${x?string("0.##E0")}
                        <#sep>,</#sep>
                    </#list>
                ],
                datasets: [
                    <#list plotData.YDataList as yData>
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
                            borderColor: 'rgb(255,0,0)'
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
                        max: ${plotData.maxYValue?string("0.##E0")},
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