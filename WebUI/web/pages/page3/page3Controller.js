
window.onload = function () {

    $("#addCommandForm").submit(onAddCommand);
    selectStockOptionCreate();
}
function onAddCommand(){
//todo: get all parmeters from the form
    if( $('#newCommandSellRadio').is(':checked'))
        var newCommandDIR = "sell";
    else
        var newCommandDIR = "buy";


}

function selectStockOptionCreate() {
    //todo: move to the needed servlet
    $.ajax({
        url: 'mainPage',
        data: {
            action: "getUserStocks"
        },
        type: 'GET',
        success: selectStockOptionCreateCallBack(stockList),

    });
}
function selectStockOptionCreateCallBack(stockList){

  //todo: need to go over the list and creat options
    var select = $(#newCommandStockSymbol )

    for (var i = min; i<=max; i++){
        var opt = document.createElement('option');
        opt.value = i;
        opt.innerHTML = i;
        select.appendChild(opt);
    }
}

function back()
{
    window.location = MainPage;
}