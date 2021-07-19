var MainPage = "pages/mainPage/MainPage.html";

function sendRedirect(res)
{
    var userName = $('.userName').val();
    window.location = MainPage + "?userName=" + userName;
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
            showSnackbar(error.responseText);
        }
    });

}

