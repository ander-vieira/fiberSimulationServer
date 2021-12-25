<#import "/layouts/mainLayout.ftl" as mainLayout>
<#import "/utils.ftl" as utils>

<@mainLayout.layout>
    <p>
        <a href="/">
            Return to home
        </a>
    </p>

    <div id="vueApp">
        <form>
            <h2>Iterative method simulation</h2>

            <p>
                Dopant:
                <@utils.generateSelectVue dyeDopants "dopant" />
            </p>

            <p>
                Concentration (m<sup>-3</sup>)
                <input v-model="concentration" type="text">
            </p>

            <p>
                Diameter (m)
                <input v-model="diameter" type="text">
            </p>

            <p>
                Length (m)
                <input v-model="length" type="text">
            </p>

            <p>{{simValidation}}</p>

            <p>
                <button type="button" v-on:click="submitForm">Simulate</button>
                <button type="button" v-on:click="resetForm">Reset</button>
            </p>
        </form>

        <p>
            Result: {{simResult}}
        </p>

        <p>
            Simulated in: {{simTime}}
        </p>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function(){
            app = new Vue({
              el: '#vueApp',
              data: {
                dopant: "${dyeDopants?first}",
                concentration: 1.5e22,
                diameter: 1e-3,
                length: 0.1,
                simValidation: "",
                simResult: "",
                simTime: ""
              },
              methods: {
                "submitForm": function() {
                    if(isNaN(this.concentration)) {
                        this.simValidation = "The concentration is not a number";
                        this.simResult = "";
                        return;
                    }

                    if(isNaN(this.diameter)) {
                        this.simValidation = "The diameter is not a number";
                        this.simResult = "";
                        return;
                    }

                    if(isNaN(this.length)) {
                        this.simValidation = "The length is not a number";
                        this.simResult = "";
                        return;
                    }

                    this.simValidation = "";
                    this.simResult = "Please wait...";

                    ajaxPostRequest("/iterative", {
                        "dyeDopants": [
                            {"dopant": this.dopant,
                             "concentration": this.concentration}
                        ],
                        "diameter": this.diameter,
                        "length": this.length
                    }, function(result) {
                        this.simResult = result.lightP*1e6+" μW";
                        this.simTime = result.elapsedTime+" s";
                    }.bind(this));
                },
                "resetForm": function() {
                    this.concentration = 1.5e22;
                    this.diameter = 1e-3;
                    this.length = 0.1;
                }
              }
            });
        });
    </script>
</@>