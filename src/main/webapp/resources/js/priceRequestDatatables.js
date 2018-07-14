var ajaxUrl = "/ajax/profile/priceRequests/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#priceRequestDatatable").DataTable(extendsOpts({
        "columns": [
            {
                "data": "name"
            }, {
                "data": "numberOfVotes"
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderTodaysBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderAllBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderVoteBtn
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

function getAllVotes() {
    document.location.href="/votes";
}

function redirectToMenus(restId) {
    document.location.href="/menus?restId=" + restId;
}

function getTodaysMenuWithMeals(restId) {
    document.location.href="getTodaysMenuWithMeals?restId=" + restId;
}

function createVote(restId) {
    $.ajax({
        type: "POST",
        url: voteAjaxUrl,
        data: {"restId":restId}
    }).done(function () {
            updateTable();
            successNoty("common.saved");
        }
    );
}

function renderTodaysBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='getTodaysMenuWithMeals(" + row.id + ");'>" +
            "<span class='fa fa-cutlery' aria-hidden='true'></span></a>";
    }
}

function renderAllBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='redirectToMenus(" + row.id + ");'>" +
            "<span class='fa fa-reorder' aria-hidden='true'></span></a>";
    }
}

function renderVoteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='createVote(" + row.id + ");'>" +
            "<span class='fa fa-check' aria-hidden='true'></span></a>";
    }
}