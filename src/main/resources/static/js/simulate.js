function ajaxPostRequest(url, body, callback) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4) {
            if(this.status == 200) {
                callback(JSON.parse(this.responseText));
            } else {
                console.log("AJAX to "+url+" returned code "+this.status)
            }
        }
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(body));
}

document.addEventListener("DOMContentLoaded", function(){
    if(document.getElementById("app")) {
        app = new Vue({
          el: '#app',
          data: {
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
                        {"dopant": "Rh6G",
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
    }
});
