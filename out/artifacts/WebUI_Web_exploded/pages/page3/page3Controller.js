window.onload = function () {
    $('#addCommandForm').submit(onAddCommand);
    selectStockOptionCreate();
};

function onAddCommand(){

   var sivan = $('input[name=CommandDir]:checked', '#addCommandForm').val();
//todo: get all parmeters from the form
    if( $('#newCommandSellRadio').is(':checked'))
        var newCommandDIR = "sell";
    else if( $('#newCommandBuyRadio').is(':checked'))
        var newCommandDIR = "buy";

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
}

function selectStockOptionCreate() {
    //todo: move to the needed servlet
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

  //todo: need to go over the list and creat options
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