window.onload = function ()
{
    $("#uploadForm").submit(ClickLoad);
    refreshUserAccountMovementList();
    refreshUserList();
   // setInterval(refreshUserList, 2000);
    refreshStockList();
   // setInterval(refreshStockList, 2000);
    refreshUserFunds();


};

$.ajax({
    url: 'mainPage',
    data: {
        action: "isAdmin"
    },
    type: 'GET',
    success: hideUserFields,
    error: showUserFields
});

function showUserFields(){

    $(".userFields").show();
}

function hideUserFields(){

    $(".userFields").hide();
}

function onAddCommand(){

}

function onAddFunds(){

    var userFunds = $('#userFunds').val();
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "addFunds",
                userFunds: userFunds
            },
            type: 'GET',
            success: refreshUserFunds()
        }
    );
}

function refreshUserFunds() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "userFunds"
            },
            type: 'GET',
            success: function(res){
                $("#funds-placeholder").text("Funds: " + res);
            }
        }
    );
}

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

function refreshUserAccountMovementList() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "userAccountMovements"
            },
            type: 'GET',
            success: refreshUserAccountMovementListCallback
        }
    );
}
function refreshUserAccountMovementListCallback(userActions) {
    var userAccountMovementTable = $('.accountMovementTable tbody');
    userAccountMovementTable.empty();

    var tr = $(document.createElement('tr'));
    var td = $(document.createElement('td')).text("action type");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("action date");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("action price");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("user funds before action");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("user funds after action");
    td.appendTo(tr);
    tr.appendTo(userAccountMovementTable);

    userActions.forEach(function (action) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(action.Type.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.Date.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.Price.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.remainderBefore.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.remainderAfter.value);
        td.appendTo(tr);
        tr.appendTo(userAccountMovementTable);
    });
}

function refreshUserListCallback(users) {
    var usersTable = $('.usersTable tbody');
    usersTable.empty();

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

function refreshStockList() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "getStocks"
            },
            type: 'GET',
            success: refreshStockListCallback
        }
    );
}
function refreshStockListCallback(stocks) {
    var stocksTable = $('.stocksTable tbody');
    stocksTable.empty();

    var tr = $(document.createElement('tr'));
    var td = $(document.createElement('td')).text("company name");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("stock symbol");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("price");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("turnover");
    td.appendTo(tr);
    tr.appendTo(stocksTable);

    stocks.forEach(function (stock) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(stock.companyName.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockSymbol.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockPrice.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockQuantity.value);
        td.appendTo(tr);

        tr.appendTo(stocksTable);
    });
}

function ClickLoad() {

    var file1 = this[0].files[0];

    if (file1 != undefined) {
        var formData = new FormData();
        formData.append("fake-key-1", file1);
        $.ajax({
            method: 'POST',
            data: formData,
            url: 'uploadFile',
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            error: function (error) {
                alert(error.responseText);
            },
            success: function (res) {
                alert(res);
                refreshStockList();
                refreshUserFunds();
            }
        });
    }
    else{
        alert("Please choose a file!!");
    }

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
}
