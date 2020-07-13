<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <!-- 搜索条件区 -->
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="name" class="control-label ">key:</label>

                                <input type="text" id="key" name="key" placeholder="组织名称" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">名字:</label>
                                <input type="text" id="name" name="name" placeholder="编号"
                                       class="form-control">
                            </div>

                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                        <i class="fa fa-search fa-btn"></i>搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <!-- 工具按钮区 -->
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="model:model:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="model:model:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="model:model:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/gck/entity/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="model:model:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/gck/entity/excelIn"></@krt.excelIn>
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
                    url: "${basePath}/modeler/modelList",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.key = $("#key").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                   {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: "key", data: "key"},
                    {title: "名称", data: "name"},
                    {title: "版本", data: "version"},
                    {title: "创建时间", data: "createTime"},
                    {title: "修改时间", data: "lastUpdateTime"},
                    {title: "元数据", data: "metaInfo"},
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                             var res=' <@shiro.hasPermission name="model:model:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="model:model:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            res+=' <@shiro.hasPermission name="model:model:excelOut">'
                                + '<button class="btn btn-xs btn-warning excelOutBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>导出'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="model:model:upload">'
                                + '<button class="btn btn-xs btn-danger uploadBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>部署'
                                + '</button>'
                                + '</@shiro.hasPermission>';

                                return res;



                        }
                    }
                ]
            });


            function showVerifyDialog(taskId, module, pageName, nodeName, todoUserId) {
                if (todoUserId !== currentUser.loginName) {
                    $.modal.alertWarning("不允许非待办人办理待办事项！");
                    return;
                }
                var url = prefix + "/showVerifyDialog/" + taskId + "?module=" + module + "&formPageName=" + pageName;
                $.modal.open(nodeName, url);
            }


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //添加模型
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增模型", "${basePath}/modeler/addModal", "1000px", "500px");
            });


            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("模型在线设计", "modeler/modeler.html?modelId=" +id)
                //top.krt.layer.openDialog("修改组织", "${basePath}/gck/entity/update?id=" + id, "1000px", "500px");
            });


            //导出export2Bpmn
            $(document).on("click", ".excelOutBtn", function () {
                var id = $(this).attr("rid");
                window.location.href ="${basePath}/modeler/export/" + id;

            });

            //部署
            $(document).on("click", ".uploadBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("确认要部署至流程定义吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/modeler/deploy/"+id,
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

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/modeler/delete",
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
                            url: "${basePath}/modeler/deleteByIds",
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
