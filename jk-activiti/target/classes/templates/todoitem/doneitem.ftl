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
                                <label for="name" class="control-label ">事项标题:</label>
                                <input type="text" id="itemName" name="itemName" placeholder="事项标题"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">模块名称:</label>
                                <input type="text" id="module" name="module" placeholder="模块名称"
                                       class="form-control">
                            </div>


                            <div class="form-group">
                                <label for="name" class="control-label ">任务名称:</label>
                                <input type="text" id="taskName" name="taskName" placeholder="模块名称"
                                       class="form-control">
                            </div>

                            <div class="form-group">
                                <label for="name" class="control-label ">待办人名称:</label>
                                <input type="text" id="todoUserName" name="todoUserName" placeholder="模块名称"
                                       class="form-control">
                            </div>


                            <div class="form-group">
                                <label for="name" class="control-label ">处理人名称:</label>
                                <input type="text" id="handleUserName" name="handleUserName" placeholder="模块名称"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">通知时间:</label>
                                <input type="text" class="time-input" id="todoItemStartTime" placeholder="开始时间"
                                       name="params[todoItemStartTime]"/>
                                <span>-</span>
                                <input type="text" class="time-input" id="todoItemEndTime" placeholder="结束时间"
                                       name="params[todoItemEndTime]"/>
                            </div>

                            <div class="form-group">
                                <label for="name" class="control-label ">处理时间:</label>
                                <input type="text" class="time-input" id="handleStartTime" placeholder="开始时间"
                                       name="params[handleStartTime]"/>
                                <span>-</span>
                                <input type="text" class="time-input" id="handleEndTime" placeholder="结束时间"
                                       name="params[handleEndTime]"/>
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
                        <@shiro.hasPermission name="todoitem:todoitem:list">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/todoitem/doneExport"></@krt.excelOut>
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
                    url: "${basePath}/todoitem/doneList",
                    type: "post",
                    data: function (d) {
                        d.module = $("#module").val();
                        d.itemName = $("#itemName").val();
                        d.taskName = $("#taskName").val();
                        d.todoUserName = $("#todoUserName").val();
                        d.handleUserName = $("#handleUserName").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {title: "事项标题", data: "itemName"},
                    {title: "事项内容", data: "itemContent"},
                    {title: "任务名称", data: "taskName"},
                    {title: "模块名称", data: "module"},
                    {title: "待办人名称", data: "todoUserName"},
                    {title: "待办人ID", data: "todoUserId", visible: false},
                    {title: "处理人名称", data: "handleUserName"},
                    {title: "处理人ID", data: "handleUserId", visible: false},
                    {title: "通知时间", data: "todoTime"},
                    {title: "处理时间", data: "handleTime"},
                    {title: "是否查看", data: "isView"},
                    {title: "是否处理", data: "isHandle"},
                    {
                        title: "已办任务名称", data: "nodeName", render: function (data) {
                            return '<span class="badge badge-primary">' + data + '</span>';
                        }
                    },

                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            var res = "";

                            res += ' <@shiro.hasPermission name="todoitem:todoitem:list">'
                                + '<button class="btn btn-xs btn-warning detailBtn" rid="' + row.instanceId + '" module="' + row.module + '">'
                                + '<i class="fa fa-edit fa-btn"></i>申请详情'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="todoitem:todoitem:list">'
                                + '<button class="btn btn-xs btn-danger historyBtn" rid="' + row.instanceId + '">'
                                + '<i class="fa fa-trash fa-btn"></i>审批历史'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            res += ' <@shiro.hasPermission name="todoitem:todoitem:list">'
                                + '<button class="btn btn-xs btn-warning processBtn" rid="' + row.instanceId + '">'
                                + '<i class="fa fa-edit fa-btn"></i>进度查看'
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

            /* 查看审批历史 */
            $(document).on("click", ".historyBtn", function () {
                var instanceId = $(this).attr("rid");
                top.krt.layer.openDialog("审批历史", "${basePath}/process/historyList/" + instanceId, "1000px", "500px");
            });


            /* 申请详情 */
            $(document).on("click", ".detailBtn", function () {
                var instanceId = $(this).attr("rid");
                var module = $(this).attr("module");
                top.krt.layer.openDialog("申请详情", "${basePath}/" + module + "/showFormDialog/" + instanceId, "1000px", "500px");
            });

            /* 进度查看 */
            $(document).on("click", ".processBtn", function () {
                var instanceId = $(this).attr("rid");
                top.krt.layer.openDialog("查看流程图", "${basePath}/process/processImg/" + instanceId, "1000px", "500px");
            });


            // /* 查看审批历史 */
            // function showHistoryDialog(instanceId) {
            //     var url = ctx + 'process/historyList/' + instanceId;
            //     $.modal.open("查看审批历史", url, null, null, null, true);
            // }
            //
            // function showProcessImgDialog(instanceId) {
            //     var url = ctx + 'process/processImg/' + instanceId;
            //     $.modal.open("查看流程图", url, null, null, null, true);
            // }
            //
            // /* 选择用户 */
            // function selectUser(taskId) {
            //     var url = ctx + 'user/authUser/selectUser?taskId=' + taskId;
            //     $.modal.open("关联系统用户", url);
            // }
            //
            // function showFormDialog(instanceId, module) {
            //     var url = ctx + module + "/showFormDialog/" + instanceId;
            //     $.modal.open('申请详情', url, null, null, null, true);
            // }


        });


    </script>
</@footer>
