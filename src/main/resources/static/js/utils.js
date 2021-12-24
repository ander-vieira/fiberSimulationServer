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
