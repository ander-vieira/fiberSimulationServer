function printResults(result) {
    document.getElementById("simResult").innerText = result.lightP*1e6+" μW";
    document.getElementById("simTime").innerText = result.elapsedTime+" s";
}

document.addEventListener("DOMContentLoaded", function(){
    var simForm = document.getElementById("simForm");
    var simValidation = document.getElementById("simValidation");
    var simResult = document.getElementById("simResult");

    simForm.addEventListener("submit", function(e) {
        e.preventDefault();

        var concentration = parseFloat(simForm.querySelector("[name=concentration]").value);
        var diameter = parseFloat(simForm.querySelector("[name=diameter]").value);
        var length = parseFloat(simForm.querySelector("[name=length]").value);

        if(isNaN(concentration)) {
            simValidation.innerText = "The concentration is not a number";
            simResult.innerText = "";
            return;
        }

        if(isNaN(diameter)) {
            simValidation.innerText = "The diameter is not a number";
            simResult.innerText = "";
            return;
        }

        if(isNaN(length)) {
            simValidation.innerText = "The length is not a number";
            simResult.innerText = "";
            return;
        }

        simValidation.innerText = "";
        simResult.innerText = "Please wait...";

        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if(this.readyState == 4 && this.status == 200) {
                printResults(JSON.parse(this.responseText));
            }
        }

        xhttp.open("POST", "/iterative", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send(JSON.stringify({"dyeDopants": [{"dopant": "Rh6G", "concentration": concentration}], "diameter": diameter, "length": length}));

        console.log("Simulating with diameter "+diameter+" and length "+length);
    });
});
