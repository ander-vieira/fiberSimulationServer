<#import "/layouts/mainLayout.ftl" as mainLayout>

<@mainLayout.layout>
    <p>
        <a href="/">
            Return to home
        </a>
    </p>

    <form action="" method="POST" id="simForm">
        <h2>Iterative method simulation</h2>

        <p>
            Concentration:
            <input type="text" name="concentration" value="1.5e22"> m<sup>-3</sup>
        </p>

        <p>
            Diameter:
            <input type="text" name="diameter" value="1e-3"> m
        </p>

        <p>
            Length:
            <input type="text" name="length" value="0.01"> m
        </p>

        <p id="simValidation"></p>

        <p>
            <input type="submit" value="Simulate">
            <input type="reset" value="Reset">
        </p>
    </form>

    <p>
        Result: <span id="simResult"></span>
    </p>

    <p>
        Simulated in: <span id="simTime"></span>
    </p>
</@>