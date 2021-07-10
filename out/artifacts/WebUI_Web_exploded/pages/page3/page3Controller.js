
window.onload = function () {

    $("#addCommandForm").submit(onAddCommand);
}
function onAddCommand(){

    if( $('#newCommandSellRadio').is(':checked'))
        var newCommandDIR = "sell";
    else
        var newCommandDIR = "buy";
    newCommandDIR=document.forms.CommandDir.name-shared-by-radio-buttons.value

}

function back()
{
    window.location = MainPage;
}