var stam = "/Web/pages/login/stam.html";

function checkLogin() {
    $.ajax
    ({
        url: 'loginResponse',
        data: {
            action: "login"
        },
        type: 'GET',
        success:sendRedirect(),
        error:function (){alert("lo tov")}
    });

}

function sendRedirect()
{
    window.location = stam;

}


