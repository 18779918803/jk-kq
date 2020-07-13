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
                                <label for="type" class="control-label ">请假类型:</label>
                            </div>
                            <div class="form-group">
                                <label for="title" class="control-label ">标题:</label>
                                <input type="hidden" id="currentName" value="${currentUser.username}">
                                <input type="text" id="title" name="title" placeholder="标题" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="startTime" class="control-label ">开始时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="startTime" id="startTime"
                                           readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="endTime" class="control-label ">结束时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="endTime" id="endTime"
                                           readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="instanceId" class="control-label ">流程实例ID:</label>
                            </div>
                            <div class="form-group">
                                <label for="applyUser" class="control-label ">申请人:</label>
                                <input type="text" id="applyUser" name="applyUser" placeholder="申请人"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="applyTime" class="control-label ">申请时间:</label>
                                <div class="input-group input-group-addon-right-radius">
                                    <input type="text" class="form-control pull-right" name="applyTime" id="applyTime"
                                           readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
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
                        <@shiro.hasPermission name="Leave:leave:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                    class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Leave:leave:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="Leave:leave:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/activiti/leave/excelOut"></@krt.excelOut>
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
                    url: "${basePath}/activiti/leave/list",
                    type: "post",
                    data: function (d) {
                        d.type = $("#type").val();
                        d.title = $("#title").val();
                        d.startTime = $("#startTime").val();
                        d.endTime = $("#endTime").val();
                        d.instanceId = $("#instanceId").val();
                        d.applyUser = $("#applyUser").val();
                        d.applyTime = $("#applyTime").val();
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
                    {title: "请假类型", data: "type"},
                    {title: "标题", data: "title"},
                    {title: "原因", data: "reason"},
                    {title: "开始时间", data: "startTime"},
                    {title: "结束时间", data: "endTime"},
                    {title: "请假时长，单位秒", data: "totalTime"},
                    {title: "创建人", data: "createUserName"},
                    {title: "申请人", data: "applyUserName"},
                    {title: "流程实例ID", data: "instanceId"},
                    {
                        title: "当前任务名称",
                        data: "taskName",
                        render: function (data) {
                            return '<span class="badge badge-primary">' + data + '</span>';
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {

                            var res="";




                            if (row.instanceId) {

                                res += ' <@shiro.hasPermission name="Leave:leave:list">'
                                    + '<button class="btn btn-xs btn-primary detailBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>表单数据'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="Leave:leave:list">'
                                    + '<button class="btn btn-xs btn-primary historyBtn" rid="' + row.instanceId + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>审批历史'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';

                                res += '<@shiro.hasPermission name="Leave:leave:list">'
                                    + '<button class="btn btn-xs btn-primary showBtn" rid="' + row.instanceId +'">'
                                    + '<i class="fa fa-trash fa-btn"></i>进度查看'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';

                                if (row.taskName.indexOf('已结束') === -1) {

                                    res += ' <@shiro.hasPermission name="Leave:leave:list">'
                                        + '<button class="btn btn-xs btn-primary cancelBtn" rid="' + row.instanceId + '" createBy="' + row.createBy+ '">'
                                        + '<i class="fa fa-edit fa-btn"></i>撤销'
                                        + '</button>'
                                        + '</@shiro.hasPermission>';
                                    var suspendOrActive = row.suspendState === '2' ? '激活' : '挂起';
                                    var icon = row.suspendState === '2' ? 'fa fa-check' : 'fa fa-stop';
                                    res +=+ '<@shiro.hasPermission name="Leave:leave:list">'
                                        + '<button class="btn btn-xs btn-primary suspendOrActiveBtn" rid="' + row.instanceId + '" createBy="' + row.createBy+ '" suspendState="' +  row.suspendState +   '">'
                                        + '<i class="fa fa-trash fa-btn"></i>'+suspendOrActive
                                        + '</button>'
                                        + '</@shiro.hasPermission>';
                                }

                            }else{
                                res += ' <@shiro.hasPermission name="Leave:leave:update">'
                                    + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>修改'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="Leave:leave:delete">'
                                    + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>删除'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                                res += '<@shiro.hasPermission name="Leave:leave:commit">'
                                    + '<button class="btn btn-xs btn-primary commitBtn" rid="' + row.id + '" createBy="' + row.createBy + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>提交审核'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }

                            return res;
                        }
                    }
                ]
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            $(document).on("click", ".historyBtn", function () {
                var instanceId = $(this).attr("rid");
                var url = '${basePath}/process/historyList/' + instanceId;
                top.krt.layer.openDialog("查看审批历史", url, "1000px", "500px");
            });


            $(document).on("click", ".showBtn", function () {
                var instanceId = $(this).attr("rid");
                var url = '${basePath}/process/processImg/' + instanceId;
                top.krt.layer.openDialog("查看流程图", url, "1000px", "500px");
            });


            $(document).on("click", ".detailBtn", function () {
                var id = $(this).attr("rid");
                var url = '${basePath}/leave/view/' + id;
                top.krt.layer.openDialog("查看详情", url, "1000px", "500px");
            });


            <#--/* 查看审批历史 */-->
            <#--function showHistoryDialog(instanceId) {-->
                <#--var url = ${basePath} + 'process/historyList/' + instanceId;-->
                <#--// $.modal.open("查看审批历史", url, null, null, null, true);-->
                <#--top.krt.layer.openDialog("查看审批历史", url, "1000px", "500px");-->
            <#--}-->

            <#--function showProcessImgDialog(instanceId) {-->
                <#--var url = ctx + 'process/processImg/' + instanceId;-->
                <#--$.modal.open("查看流程图", url, null, null, null, true);-->
            <#--}-->

            // function cancelApply(instanceId, createBy) {
            //     if (createBy !== currentUser.loginName) {
            //         $.modal.alertWarning("不允许非创建人撤销申请！");
            //         return;
            //     }
            //     $.modal.confirm("确认要撤销申请吗?", function() {
            //         var url = ctx + "process/cancelApply";
            //         var data = { "instanceId": instanceId };
            //         $.operate.submit(url, "post", "json", data);
            //     });
            // }

            $(document).on("click", ".cancelBtn", function () {
                var instanceId = $(this).attr("rid");
                var createBy = $(this).attr("createBy");
                var username = $("#currentName").val();
                if (createBy !== username) {
                    alert("不允许非创建人提交申请！");
                    return;
                }
                krt.layer.confirm("确认要撤销申请吗?", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/process/cancelApply",
                        data: {"instanceId": instanceId},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
            });

            // function suspendOrActiveApply(instanceId, createBy, suspendState) {
            //     var suspendOrActive = suspendState === '2' ? '激活' : '挂起' ;
            //     if (createBy !== currentUser.loginName) {
            //         $.modal.alertWarning("不允许非创建人" + suspendOrActive + "申请！");
            //         return;
            //     }
            //     $.modal.confirm("确认要" + suspendOrActive + "申请吗?", function() {
            //         var url = ctx + "process/suspendOrActiveApply";
            //         var data = { "instanceId": instanceId, "suspendState": suspendState };
            //         $.operate.submit(url, "post", "json", data);
            //     });
            // }

            $(document).on("click", ".suspendOrActiveBtn", function () {
                var instanceId = $(this).attr("rid");
                var createBy = $(this).attr("createBy");
                var suspendState = $(this).attr("suspendState");
                var username = $("#currentName").val();
                if (createBy !== username) {
                    alert("不允许非创建人提交申请！");
                    return;
                }
                var suspendOrActive = suspendState === '2' ? '激活' : '挂起' ;
                krt.layer.confirm("确认要提交"+suspendOrActive+"申请吗?", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/process/suspendOrActiveApply",
                        data: {"instanceId": instanceId,"suspendState":suspendState},

                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
            });


            //修改
            $(document).on("click", ".commitBtn", function () {
                var id = $(this).attr("rid");
                var createBy = $(this).attr("createBy");
                var username = $("#currentName").val();
                if (createBy !== username) {
                    alert("不允许非创建人提交申请！");
                    return;
                }

                krt.layer.confirm("确认要提交申请吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/activiti/leave/submitApply",
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

            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增请假", "${basePath}/activiti/leave/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改请假", "${basePath}/activiti/leave/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/activiti/leave/delete",
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
                            url: "${basePath}/activiti/leave/deleteByIds",
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
