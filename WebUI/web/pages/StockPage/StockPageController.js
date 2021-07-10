window.onload = function () {
    $('#addCommandForm').submit(onAddCommand);
    selectStockOptionCreate();
};

function onAddCommand(){

    //todo: get all parmeters from the form

    // if( $('#newCommandSellRadio').is(':checked'))
    //     var newCommandDIR = "sell";
    // else if( $('#newCommandBuyRadio').is(':checked'))
    //     var newCommandDIR = "buy";
    var newCommandDIR = $('input[name=CommandDir]:checked', '#addCommandForm').val();

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
}

function selectStockOptionCreate() {
    $.ajax({
        url: 'mainPage',
        data: {
            action: "getUserStocks"
        },
        type: 'GET',
        success: selectStockOptionCreateCallBack,
    });
}

function selectStockOptionCreateCallBack(stockList){
    var select = $('#newCommandStockSymbol');

    stockList.forEach(function(stock) {
        var opt = new Option(stock, stock);
        select.append(opt);
    });

    // for (var i = min; i<=max; i++){
    //     var opt = document.createElement('option');
    //     opt.value = i;
    //     opt.innerHTML = i;
    //     select.appendChild(opt);
    // }
}

function back()
{
    window.location = MainPage;
}