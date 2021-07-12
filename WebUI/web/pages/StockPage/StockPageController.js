
window.onload = function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const stock = urlParams.get('stock');
    $('#newCommandStockSymbol').val(stock);
    $('#addCommandForm').submit(onAddCommand);
    // selectStockOptionCreate();
};

function onAddCommand(){

    //todo: get all parmeters from the form
    var CmdDirection = $('input[name=CommandDir]:checked', '#addCommandForm').val();
    var newCommandSymbol = $('#newCommandStockSymbol').val();
    var CmdType = $('input[name=CommandType]:checked', '#addCommandForm').val();
    var newCommandQuantity = $('#newCommandQuantity').val();
    var newCommandPrice = $('#newCommandPrice').val();

    $.ajax({
        url: 'mainPage',
        data: {
            action: "addCommand",
            newCommandSymbol: newCommandSymbol,
            CmdType: CmdType,
            CmdDirection: CmdDirection,
            newCommandQuantity: newCommandQuantity,
            newCommandPrice: newCommandPrice
        },
        type: 'GET',
        error: function (error){
            alert(error.responseText);
        },
    });

    // return value of the submit operation
    // by default - we'll always return false so it doesn't redirect the user.
    return false;
}

// function selectStockOptionCreate() {
//     $.ajax({
//         url: 'mainPage',
//         data: {
//             action: "getUserStocks"
//         },
//         type: 'GET',
//         success: selectStockOptionCreateCallBack,
//     });
// }
//
// function selectStockOptionCreateCallBack(stockList){
//     var select = $('#newCommandStockSymbol');
//
//     stockList.forEach(function(stock) {
//         var opt = new Option(stock, stock);
//         select.append(opt);
//     });
// }

function back()
{
    window.location = MainPage;
}