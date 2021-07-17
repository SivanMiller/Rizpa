
function ReportUserTransaction(){
    $.ajax(
        {
            url: 'command',
            data: {
                action: "reportTransactions"
            },
            type: 'GET',
            success: notifyNewTransaction
        }
    );
}

function notifyNewTransaction(transaction) {
    transaction.forEach(function (transaction) {
        var message = "New transaction made! ";
        message += transaction.value.transactionSellUser.value + " Sold " + transaction.value.transactionBuyUser.value + " ";
        message += transaction.value.transactionQuantity.value + " of Stock " + transaction.key + " for the price of " + transaction.value.transactionPrice.value;
        showSnackbar(message);
    })
}