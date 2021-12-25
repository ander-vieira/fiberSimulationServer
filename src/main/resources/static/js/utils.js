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

plotColor = {
    plotColors: [
        "rgb(255, 0, 0)",
        "rgb(0, 255, 0)",
        "rgb(0, 0, 255)",
    ],
    current: 0,
    get: function() {
        var result = this.plotColors[this.current];

        this.current = (this.current+1)%this.plotColors.length;

        return result;
    }
}