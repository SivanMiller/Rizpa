
function showSnackbar(res) {
    var message = document.createElement("div");
    message.className = "snackbar";
    var container = document.getElementById("snackbar-container");
    message.innerHTML = res;
    container.append(message);
    message.className = "snackbar show";
    setTimeout(function(){ message.className = message.className.replace("snackbar show", "snackbar"); }, 5000);
}