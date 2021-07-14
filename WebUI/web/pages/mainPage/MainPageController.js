var StockPage = '../StockPage/StockPage.html';

window.onload = function ()
{
    $("#uploadForm").submit(ClickLoad);
    $("#addStockForm").submit(onAddStock);
    getUserList();
    setInterval(getUserList, 2000);
    getStockList();
    setInterval(getStockList, 2000);
    refreshUserFunds();
    setInterval(refreshUserFunds, 2000);
    getUserAccountMovementList();
    setInterval(getUserAccountMovementList, 2000);
    setInterval(ReportUserTransaction, 2000);
};

function onAddStock(){
    var newStockCompanyName = $('#newStockCompanyName').val();
    var newStockSymbol      = $('#newStockSymbol').val();
    var newStockQuantity    = $('#newStockQuantity').val();
    var newStockPrice       = $('#newStockPrice').val();

    $.ajax(
        {
            url: 'stock',
            data: {
                newStockCompanyName: newStockCompanyName,
                newStockSymbol: newStockSymbol,
                newStockQuantity: newStockQuantity,
                newStockPrice: newStockPrice,
                action: "addStock"
            },
            type: 'GET',
            error: function (error) {
                $("#snackbar").text(error.responseText);
                // Get the snackbar DIV
                var x = document.getElementById("snackbar");

                // Add the "show" class to DIV
                x.className = "show";

                // After 3 seconds, remove the show class from DIV
                setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
            },
            success: function (res) {
                $("#snackbar").text(res);
                // Get the snackbar DIV
                var x = document.getElementById("snackbar");

                // Add the "show" class to DIV
                x.className = "show";

                // After 3 seconds, remove the show class from DIV
                setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
                getStockList();
            }
        }
    );

    return false;
}

function onAddFunds(){
    var userFunds = $('#addFunds').val();
    $.ajax(
        {
            url: 'command',
            data: {
                action: "addFunds",
                userFunds: userFunds
            },
            type: 'GET',
            success: refreshUserFunds()
        }
    );
}

function getStockList() {
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getStocks"
            },
            type: 'GET',
            success: refreshStockList
        }
    );
}

function refreshStockList(stocks) {
    var stocksTable = $('#stocksTable tbody');
    stocksTable.empty();

    stocks.forEach(function (stock) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td'));
        td.append('<a href="' + StockPage + '?stock=' + stock.stockSymbol.value +'">' + stock.companyName.value + '</a>')
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
                getStockList();
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

function ReportUserTransaction(){
    $.ajax(
        {
            url: 'command',
            data: {
                action: "reportTransactions"
            },
            type: 'GET',
            success: alertNewTransaction
        }
    );
}

function alertNewTransaction(transaction) {
    transaction.forEach(function (transaction) {
        var message = "New transaction made! ";
        message += transaction.value.transactionSellUser.value + " Sold " + transaction.value.transactionBuyUser.value + " ";
        message += transaction.value.transactionQuantity.value + " of Stock " + transaction.key + " for the price of " + transaction.value.transactionPrice.value;
        alert(message);
    })
}

$.ajax({
    url: 'user',
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


function refreshUserFunds() {
    $.ajax(
        {
            url: 'user',
            data: {
                action: "userFunds"
            },
            type: 'GET',
            success: function(res){
                $("#userFunds").val(res);
            }
        }
    );
}

function getUserAccountMovementList() {
    $.ajax(
        {
            url: 'user',
            data: {
                action: "userAccountMovements"
            },
            type: 'GET',
            success: refreshUserAccountMovementList
        }
    );
}

function refreshUserAccountMovementList(userActions) {

    var userAccountMovementTable = $('#accountMovementTable tbody');
    userAccountMovementTable.empty();

    var tr;
    var td;

    userActions.forEach(function(action) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(action.Type);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.Date);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.Price);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.remainderBefore);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(action.remainderAfter);
        td.appendTo(tr);
        tr.appendTo(userAccountMovementTable);
    });
}

function getUserList() {
    $.ajax(
        {
            url: 'user',
            data: {
                action: "getUsers"
            },
            type: 'GET',
            success: refreshUserList
        }
    );
}

function refreshUserList(users) {
    var usersTable = $('#usersTable tbody');
    usersTable.empty();
    var tr;
    var td;

    users.forEach(function (user) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(user.key);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(user.value);
        td.appendTo(tr);
        tr.appendTo(usersTable);
    });
}

