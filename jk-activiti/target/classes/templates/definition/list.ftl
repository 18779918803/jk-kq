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
                                <label for="name" class="control-label ">分类:</label>
                                <input type="text" id="category" name="category" placeholder="编号"
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


                        <@shiro.hasPermission name="definition:definition:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/definition/upload"></@krt.excelIn>
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
                    url: "${basePath}/definition/list",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.key = $("#key").val();
                        d.category = $("#category").val();
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
                    {title: "组织名称", data: "name"},
                    {title: "版本", data: "version"},
                    {title: "描述", data: "description"},
                    {title: "所属类别", data: "category"},
                    {title: "部署时间", data: "deploymentTime"},
                    {
                        title: "流程定义", data: "resourceName", render: function (data, type, row) {
                            var url = '${basePath}/definition/readResource?pdid=' + row.id + '&resourceName=' + data;
                            return '<a href="' + url + '" target="_blank"> ' + data + '</a>';
                        }
                    },
                    {
                        title: "流程图", data: "diagramResourceName", render: function (data, type, row) {
                            var url = '${basePath}/definition/readResource?pdid=' + row.id + '&resourceName=' + data;
                            return '<a href="' + url + '" target="_blank"> ' + data + '</a>';
                        }
                    },
                    {
                        title: "流程定义状态", data: "suspendStateName", render: function (data, type, row) {
                            return '<span class="badge badge-primary">' + data + '</span>';
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {

                            var suspendOrActive = row.suspendState === '2' ? '激活' : '挂起';
                            var icon = row.suspendState === '2' ? 'fa fa-check' : 'fa fa-stop';
                            var res = ' <@shiro.hasPermission name="definition:definition:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>转模型'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="definition:definition:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.deploymentId + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            res += ' <@shiro.hasPermission name="definition:definition:state">'
                                + '<button class="btn btn-xs btn-primary suspendOrActiveBtn" rid="' + row.id + '" state="'+row.suspendState+'">'
                                + '<i class="fa fa-edit fa-btn"></i>'+suspendOrActive
                                + '</button>'
                                + '</@shiro.hasPermission>';


                            return res;


                        }
                    }
                ]
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });


            //转模型
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("确认要转换成流程模型吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/definition/convert2Model",
                        data: {"processDefinitionId": id},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });

            });


            //激活或者是挂起
            $(document).on("click", ".suspendOrActiveBtn", function () {
                var id = $(this).attr("rid");
                var state = $(this).attr("state");
                var suspendOrActive = state === '2' ? '激活' : '挂起' ;
                krt.layer.confirm("确认要" + suspendOrActive + "流程定义吗?？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/definition/suspendOrActiveApply",
                        data: {"id": id,"suspendState":state},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
                //top.krt.layer.openDialog("修改组织", "${basePath}/gck/entity/update?id=" + id, "1000px", "500px");
            });



            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/definition/delete",
                        data: {"ids": id},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
                //top.krt.layer.openDialog("修改组织", "${basePath}/gck/entity/update?id=" + id, "1000px", "500px");
            });


        });


        function suspendOrActiveApply(id, suspendState) {
            var suspendOrActive = suspendState === '2' ? '激活' : '挂起' ;
            $.modal.confirm("确认要" + suspendOrActive + "流程定义吗?", function() {
                var url = ctx + "definition/suspendOrActiveApply";
                var data = { "id": id, "suspendState": suspendState };
                $.operate.submit(url, "post", "json", data);
            });
        }


    </script>
</@footer>
