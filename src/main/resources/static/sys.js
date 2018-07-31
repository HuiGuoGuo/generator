var tbody = $('tbody');
$(function () {
    tbody.empty();
    $.ajax({
        type: 'GET',
        url: '/sys/generator/listTable',
        data: {
            'page': 1,
            'pageSize': 12
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
                        '<td>' + datas[i].createTime + '</td>' +
                        '<td><a href="sys/generator/' + datas[i].tableName + '" class="btn btn-info">生成代码</a></td>' +
                        '</tr>')
                }
            }
        },
    })
})