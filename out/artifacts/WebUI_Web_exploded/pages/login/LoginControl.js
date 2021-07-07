var stam = "/Web/pages/login/MainPage.html";

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
    var isAdmin = $('.isAdmin').is(':checked');

    $.ajax
    ({
        url: 'loginResponse',
        data: {
            userName: userName,
            isAdmin: isAdmin
        },
        type: 'GET',
        success: sendRedirect,
        error: function() {
           alert("lo tov");
        },
    });

}


