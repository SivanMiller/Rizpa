window.onload = function ()
{
    refreshUserList();
    setInterval(refreshUserList, 2000);
    refreshStockList();
    setInterval(refreshStockList, 2000);
};


function refreshUserList() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "getUsers"
            },
            type: 'GET',
            success: refreshUserListCallback
        }
    );
}

function refreshUserListCallback(users) {
    var usersTable = $('.usersTable tbody');
    usersTable.empty();


    var tr = $(document.createElement('tr'));

    var td = $(document.createElement('td')).text("User Name");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("User Type");
    td.appendTo(tr);
    tr.appendTo(usersTable);

    users.forEach(function (user) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(user.key);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(user.value);
        td.appendTo(tr);
        tr.appendTo(usersTable);
    });
}

function refreshStockList() {
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "getStocks"
            },
            type: 'GET',
            success: refreshStockListCallback
        }
    );
}
function refreshStockListCallback(stocks) {
    var usersTable = $('.stocksTable tbody');
    usersTable.empty();


    var tr = $(document.createElement('tr'));

    var td = $(document.createElement('td')).text("company name");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("stock symbol");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("price");
    td.appendTo(tr);
    td = $(document.createElement('td')).text("turnover");
    td.appendTo(tr);
    tr.appendTo(usersTable);

    stocks.forEach(function (stock) {
        tr = $(document.createElement('tr'));
        td = $(document.createElement('td')).text(stock.companyName);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockSymbol);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockPrice);
        td.appendTo(tr);
        td = $(document.createElement('td')).text(stock.stockQuantity);
        td.appendTo(tr);

        tr.appendTo(usersTable);
    });
}

function loadGameClicked(event) {
    //var file = event.target.files[0];
    //var reader = new FileReader();
    //var creatorName = getUserName();

    //reader.onload = function () {
       // var content = reader.result;

    var fileName = event.target.files[0];
    var fileName = $('#myfile').val();
    $.ajax(
        {
            url: 'mainPage',
            data: {
                action: "loadXML",
                fileName: fileName
            },
            type: 'GET',
            //success: refreshUserListCallback
        }
    );
    //};

    // $.ajax // Getting creator's name.
    //     ({
    //         url: 'login',
    //         data: {
    //             action: "status"
    //         },
    //         type: 'GET',
    //         success: function (json) {
    //             creatorName = json.userName;
    //             reader.readAsText(file);
    //         }
    //     });
}
