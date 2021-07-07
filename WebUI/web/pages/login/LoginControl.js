var MainPage = "/Web/pages/login/MainPage.html";

function sendRedirect(res)
{
    window.location = MainPage;

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
        error: function (error)
        {
            alert(error.responseText);
        }
    });

}


