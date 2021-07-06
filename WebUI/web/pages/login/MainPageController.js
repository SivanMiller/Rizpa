window.onload = function ()
{
    refreshUserList();
    setInterval(refreshUserList, 2000);
};


function refreshUserList() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "getUsers"
            },
            type: 'GET',
            success: refreshUserListCallback
        }
    );
}

function refreshUserListCallback(users) {
    var usersTable = $('.usersTable tbody');
    usersTable.empty();
    //var userList = json.users;

    var tr = $(document.createElement('tr'));

    var td = $(document.createElement('td')).text("User Name");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("User Type");
    td.appendTo(tr);
    tr.appendTo(usersTable);

    users.forEach(function (user) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(user.key);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(user.value);
        td.appendTo(tr);
        tr.appendTo(usersTable);
    });
}

