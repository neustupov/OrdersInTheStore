var ajaxUrl = "/ajax/profile/priceRequests/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#priceRequestDatatable").DataTable(extendsOpts({
        "columns": [
            {
                "data": "id"
            }, {
                "data": "user.name"
            },
            {
                "data": "addDateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10);
                    }
                    return date;
                }
            }, {
                "data": "client",
                "render": function (data) {
                    return data.name + " " + data.lastName
                }
            }, {
                "data": "products",
                "render": function (data) {
                    if (data) {
                        var products = "";
                        data.forEach(function (item, i) {
                            products += (i+1) + ". " + item.type.name + " " + item.brand.name + " "
                                + item.model.name + "<br ?> ";
                        });
                        return products;
                    } else {
                        return " ";
                    }
                }
            }, {
                "data": "ready",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='ready($(this)," + row.id + ");'/>";
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    }));
});

/*function redirectToMenus(restId) {
 document.location.href = "/menus?restId=" + restId;
 }

 function createVote(restId) {
 $.ajax({
 type: "POST",
 url: voteAjaxUrl,
 data: {"restId": restId}
 }).done(function () {
 updateTable();
 successNoty("common.saved");
 }
 );
 }

 function renderAllBtn(data, type, row) {
 if (type === "display") {
 return "<a onclick='redirectToMenus(" + row.id + ");'>" +
 "<span class='fa fa-reorder' aria-hidden='true'></span></a>";
 }
 }*/

function ready(chkbox, id) {
    var ready = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "ready=" + ready
    }).done(function () {
        chkbox.closest("tr").toggleClass("disabled");
        successNoty(ready ? "common.ready" : "common.notReady");
    }).fail(function () {
        $(chkbox).prop("checked", !ready);
    });
}