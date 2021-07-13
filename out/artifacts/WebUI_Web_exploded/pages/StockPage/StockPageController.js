var MainPage = "../mainPage/MainPage.html";

window.onload = function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const stock = urlParams.get('stock');
    updateStockDetails();
    updateTransactionsList();
    updateBuyCommandList();
    updateSellCommandList();
    $('#newCommandStockSymbol').val(stock);
    $('#addCommandForm').submit(onAddCommand);
    setInterval(ReportUserTransaction, 2000);
    setInterval(updateTransactionsList, 2000);
    setInterval(updateStockDetails, 2000);
    setInterval(updateBuyCommandList, 2000);
    setInterval(updateSellCommandList, 2000);
};
$.ajax({
    url: 'user',
    data: {
        action: "isAdmin"
    },
    type: 'GET',
    success: showAdminFields,
    error: showUserField
});

function showAdminFields(){

    $(".adminFields").show();
    $(".userFields").hide();
}
function showUserField(){

    $(".userFields").show();
    $(".adminFields").hide();
}
function updateStockDetails()
{
    var stock = $('#newCommandStockSymbol').val();

    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getStock",
                stock:stock
            },
            type: 'GET',
            success: updateStockDetailsCallBack
        }
    );
    $.ajax(
        {
            url: 'user',
            data: {
                action: "getUserHoldForStock",
                stock:stock
            },
            type: 'GET',
            success: updateUserHoldCallBack
        }
    );
}
function updateUserHoldCallBack(Quantity)
{
    $('#userHold').val(Quantity);
}
function updateStockDetailsCallBack(stock)
{
    $('#stockSymbol').val(stock.stockSymbol.value);
    $('#stockCompanyName').val(stock.companyName.value);
    $('#stockPrice').val(stock.stockPrice.value);

}

function updateTransactionsList()
{
    var stock = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getTransactionList",
                stock:stock
            },
            type: 'GET',
            success: refreshStockTransactionList
        }
    );
}


function refreshStockTransactionList(transactions) {
    var transactionTable = $('#stockTranTable tbody');
    transactionTable.empty();

    transactions.forEach(function (transaction) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td'));
        td = $(document.createElement('td')).text(transaction.transactionDate.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(transaction.transactionQuantity.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(transaction.transactionPrice.value);
        td.appendTo(tr);

        tr.appendTo(transactionTable);
    });
}
function updateBuyCommandList()
{
    var stock = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getBuyCommandList",
                stock:stock
            },
            type: 'GET',
            success: refreshStockBuyCommandList
        }
    );
}

function refreshStockBuyCommandList(commands)
{
    var buyTable = $('#stockBuyTable tbody');
    buyTable.empty();

    commands.forEach(function (command) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td'));
        td = $(document.createElement('td')).text(command.Date.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Quantity.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Price.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.User.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Type.value);
        td.appendTo(tr);

        tr.appendTo(buyTable);
    });
}
function updateSellCommandList()
{
    var stock = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getSellCommandList",
                stock:stock
            },
            type: 'GET',
            success: refreshStockSellCommandList
        }
    );
}

function refreshStockSellCommandList(commands)
{
    var sellTable = $('#stockSellTable tbody');
    sellTable.empty();

    commands.forEach(function (command) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td'));
        td = $(document.createElement('td')).text(command.Date.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Quantity.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Price.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.User.value);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(command.Type.value);
        td.appendTo(tr);

        tr.appendTo(sellTable);
    });
}
function onAddCommand(){
    var CmdDirection = $('input[name=CommandDir]:checked', '#addCommandForm').val();
    var newCommandSymbol = $('#newCommandStockSymbol').val();
    var CmdType = $('input[name=CommandType]:checked', '#addCommandForm').val();
    var newCommandQuantity = $('#newCommandQuantity').val();
    var newCommandPrice = $('#newCommandPrice').val();

    if(CmdDirection == undefined || CmdType == undefined ||
        newCommandQuantity == "" || newCommandPrice == "")
    {
        alert("Please fill all the fields!")
    }
    else {
        $.ajax({
            url: 'command',
            data: {
                action: "addCommand",
                newCommandSymbol: newCommandSymbol,
                CmdType: CmdType,
                CmdDirection: CmdDirection,
                newCommandQuantity: newCommandQuantity,
                newCommandPrice: newCommandPrice
            },
            type: 'GET',
            success: function () {
                reportAddCommand();
                ReportUserTransaction(); },
            error: function (error) {
                alert(error.responseText);
            }
        });
    }

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
}

function back()
{
    window.location = MainPage;
}

function togglePrice() {
    var CmdType = $('input[name=CommandType]:checked', '#addCommandForm').val();
    var stockPrice = $('#stockPrice').val();

    if (CmdType == 'MKT'){
        $('#newCommandPrice').val(stockPrice)
        $('#newCommandPrice').prop('disabled', true);
    }
    else {
        $('#newCommandPrice').prop('disabled', false);
    }
}

function reportAddCommand() {
        alert("Command Added!");
}

function ReportUserTransaction() {
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