var tbody = $('tbody');
$(function () {
    tbody.empty();
    $.ajax({
        type: 'GET',
        url: '/sys/generator/listTable',
        data: {
            'page': 1,
            'pageSize': 20
        },
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.code == 0) {
                var datas = data.data.data;
                for (i in datas) {
                    tbody.append('<tr>' +
                        '<td>' + datas[i].tableName + '</td>' +
                        '<td>' + datas[i].engine + '</td>' +
                        '<td>' + datas[i].tableComment + '</td>' +
                        '<td>' + datas[i].createTime + '</td>' + '</tr>')
                }
            }
        },
    })
})