var MainPage = "../mainPage/MainPage.html";

window.onload = function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const stock = urlParams.get('stock');
    $('#newCommandStockSymbol').val(stock);
    $('#addCommandForm').submit(onAddCommand);
    setInterval(ReportUserTransaction, 2000);
};

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
            success: ReportUserTransaction,
            error: function (error) {
                alert(error.responseText);
            },
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