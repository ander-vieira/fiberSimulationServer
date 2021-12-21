<#import "/layouts/mainLayout.ftl" as mainLayout>

<@mainLayout.layout>
    <p>
        <a href="/">
            Return to home
        </a>
    </p>

    <div id="app">
        <form>
            <h2>Iterative method simulation</h2>

            <p>
                Concentration:
                <input v-model="concentration" type="text"> m<sup>-3</sup>
            </p>

            <p>
                Diameter:
                <input v-model="diameter" type="text"> m
            </p>

            <p>
                Length:
                <input v-model="length" type="text"> m
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
</@>