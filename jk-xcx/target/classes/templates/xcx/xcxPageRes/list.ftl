<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <!-- 列表数据区 -->
        <div class="box">
            <div class="box-body">
                <!-- 工具按钮区 -->
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasPermission name="xcx:xcxPageRes:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="xcx:xcxPageRes:delete">
                        <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                            <i class="fa fa-trash fa-btn"></i>批量删除
                        </button>
                    </@shiro.hasPermission>
                </div>
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    var datatable;
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/xcx/xcxPageRes/list",
                type: "post",
                data: function (d) {
    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
    d.orderType = d.order[0].dir;
                }
            },
            columns: [
    {title: 'id', data: "id",visible:false},
        {title: '<input type="checkbox" id="checkAll" class="icheck">',
            data: "id", class:"td-center", width:"40",orderable: false,
            render: function (data) {
                return '<input type="checkbox" class="icheck check" value="' + data + '">';
            }
        },
                {title: "id",data: "id"},
                {title: "名称",data: "name"},
                {title: "路径",data: "url"},
                {title: "图片地址",data: "src"},
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        return ' <@shiro.hasPermission name="xcx:xcxPageRes:update">'
                            + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-edit fa-btn"></i>修改'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            + '<@shiro.hasPermission name="xcx:xcxPageRes:delete">'
                            + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-trash fa-btn"></i>删除'
                            + '</button>'
                            + '</@shiro.hasPermission>';
                    }
                }
            ]
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //新增
        $("#insertBtn").click(function () {
            top.krt.layer.openDialog("新增小程序页面资源列表","${basePath}/xcx/xcxPageRes/insert","1000px","500px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改小程序页面资源列表","${basePath}/xcx/xcxPageRes/update?id=" + id,"1000px","500px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/xcx/xcxPageRes/delete",
                    data: {"id": id},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            krt.table.reloadTable();
                        }
                    }
                });
            });
        });

        //批量删除
        $("#deleteBatchBtn").click(function () {
            var ids = getIds();
            if (ids == "") {
                krt.layer.error("请选择要删除的数据!");
                return false;
            } else {
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/xcx/xcxPageRes/deleteByIds",
                        data: {"ids": ids},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable(ids);
                            }
                        }
                    });
                });
            }
        });
    });
</script>
</@footer>
