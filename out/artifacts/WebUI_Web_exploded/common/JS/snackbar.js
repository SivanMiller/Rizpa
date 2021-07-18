
function showSnackbar(res) {
    var message = document.createElement("div");
    message.className = "snackbar";
    var container = document.getElementById("snackbar-container");
    message.innerHTML = res;
    container.append(message);
    message.className = "snackbar show";
    setTimeout(function(){ message.className = message.className.replace("snackbar show", "snackbar"); }, 5000);


    // $("#snackbar").text(res);
    // // Get the snackbar DIV
    // var x = document.getElementById("snackbar");
    //
    // // Add the "show" class to DIV
    // x.className = "show";
    //
    // // After 3 seconds, remove the show class from DIV
    // setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}