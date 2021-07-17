var MainPage = "../mainPage/MainPage.html";

window.onload = function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const stock = urlParams.get('stock');
    $('#newCommandStockSymbol').val(stock);
    $('#addCommandForm').submit(onAddCommand);
    updateStockDetails();
    setInterval(updateStockDetails, 2000);
    ReportUserTransaction();
    setInterval(ReportUserTransaction, 2000);
    updateTransactionsList();
    setInterval(updateTransactionsList, 2000);
    updateBuyCommandList();
    setInterval(updateBuyCommandList, 2000);
    updateSellCommandList();
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
    var symbol = $('#newCommandStockSymbol').val();

    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getStock",
                symbol: symbol
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
                symbol: symbol
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
    var symbol = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getTransactionList",
                symbol: symbol
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
    var symbol = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getBuyCommandList",
                symbol: symbol
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
    var symbol = $('#newCommandStockSymbol').val();
    $.ajax(
        {
            url: 'stock',
            data: {
                action: "getSellCommandList",
                symbol: symbol
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
        showSnackbar("Please fill all the fields!")
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
            error: function(error) { showSnackbar(error.responseText); },
            success: function () {
                reportAddCommand();
                ReportUserTransaction(); }

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
        showSnackbar("Command Added!");
}
