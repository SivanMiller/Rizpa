var stam = "/Web/pages/login/MainPage.html";

function sendRedirect(res)
{
    //if (json.key){
        window.location = stam;
    //}

    alert(res.responseText);
}

function error(res)
{
    alert(res.responseText);
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
        error: error,
    });

}


