var tbody = $('tbody');
var pages;
var pageSize = 12;
var currpage= 1;
$(function () {
    getData()
    $('#page').jqPaginator({
        totalPages: pages,
        visiblePages: 5,
        currentPage: currpage,
        first: '<li class="first"><a href="javascript:void(0);">First</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">Previous</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">Next</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">Last</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            currpage = num;
            getData();
        }
    });

    function getData() {
        tbody.empty();
        $.ajax({
            type: 'GET',
            url: '/sys/generator/listTable',
            data: {
                'page': currpage,
                'pageSize': pageSize
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    var datas = data.data.data;
                    pages = data.data.pages;
                    for (i in datas) {
                        tbody.append('<tr>' +
                            '<td>' + datas[i].tableName + '</td>' +
                            '<td>' + datas[i].engine + '</td>' +
                            '<td>' + datas[i].tableComment + '</td>' +
                            '<td>' + datas[i].createTime + '</td>' +
                            '<td><a href="sys/generator/' + datas[i].tableName + '" class="btn btn-info">生成代码</a></td>' +
                            '</tr>')
                    }
                }
            },
        })
    }

})



