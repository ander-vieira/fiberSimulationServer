<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fiber Simulations</title>

    <link rel="stylesheet" href="css/main.css" />

    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>
    <script src="/js/simulate.js"></script>
</head>
<body>
    <h1>Fiber Simulations</h1>
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
</body>
</html>