var stam = "/Web/pages/login/stam.html";

function sendRedirect(json)
{
    if (json.key){
        window.location = stam;
    }

    alert(json.value);
}

function checkLogin() {

    event.preventDefault();
    var userName = $('.userName').val();

    $.ajax
    ({
        url: 'loginResponse',
        data: {
            action: "loginResponse",
            userName: userName
        },
        type: 'GET',
        success: sendRedirect,
        error: function() {
           alert("lo tov");
        },
    });

}


