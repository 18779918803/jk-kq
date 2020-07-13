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
                <label for="dbType" class="control-label ">督办类别:</label>
                <input type="hidden" id="currentName" value="${currentUser.username}">
    <input type="text" id="dbType" name="dbType" placeholder="督办类别" class="form-control">
            </div>
            <div class="form-group">
                <label for="dbId" class="control-label ">编号:</label>
    <input type="text" id="dbId" name="dbId" placeholder="编号" class="form-control">
            </div>
            <div class="form-group">
                <label for="dbAskFinaltime" class="control-label ">所涉事项要求办结期限:</label>
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="dbAskFinaltime" id="dbAskFinaltime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
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
                    <@shiro.hasPermission name="dbApply:dbApply:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="dbApply:dbApply:delete">
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
                url: "${basePath}/db/dbApply/list",
                type: "post",
                data: function (d) {
                d.dbType = $("#dbType").val();
                d.dbId = $("#dbId").val();
                d.dbAskFinaltime = $("#dbAskFinaltime").val();
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
                {title: "督办类别",data: "dbType"},
                {title: "编号",data: "dbId"},
                {title: "督办事项来源",data: "dbSource"},
                {title: "来文日期",data: "dbLwDate"},
                {title: "来文明示签批内容",data: "dbLwContent"},
                {title: "事项内容及要求（简要）",data: "dbContentBrief"},
                {title: "所涉事项要求办结期限",data: "dbAskFinaltime"},
                {title: "重要程度（A,B,C）",data: "dbLevel",
                    render: function (data) {
                        return krt.util.getDic('db_level',data);
                    }
                },
                {title: "牵头部门（公司）",data: "leadDepartment"},
                {title: "责任部门（公司）",data: "responsibilityDepartment"},
                {title: "牵头部门负责人",data: "departmentPrincipal"},
                {title: "牵头部门分管领导",data: "chargeLead"},
                {title: "总经理意见",data: "managerAdvice"},
                {title: "董事长意见",data: "presidentAdvice"},
                {title: "状态（1:已申请，2:已延期，3:已撤销）",data: "status",
                    render: function (data) {
                        return krt.util.getDic('status',data);
                    }
                },
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        var res = ' <@shiro.hasPermission name="dbApply:dbApply:update">'
                            + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-edit fa-btn"></i>修改'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            + '<@shiro.hasPermission name="dbApply:dbApply:delete">'
                            + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-trash fa-btn"></i>删除'
                            + '</button>'
                            + '</@shiro.hasPermission>';
                        if (row.status == '1') {
                            res +=  '<@shiro.hasPermission name="dbApply:dbApply:excelOut">'
                                + '<button type="button" id="outExcelBtn" class="btn btn-xs bg-orange"  rid="' + row.id + '">'
                                + '<i class="fa fa-share-square-o fa-btn"></i> 导出立项申请表'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="dbApply:dbApply:delayBtn">'
                                + '<button class="btn btn-xs btn-danger delayBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-chevron-right fa-btn"></i>延期'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                +'<@shiro.hasPermission name="dbApply:dbApply:cancel">'
                                + '<button class="btn btn-xs btn-danger cancelBtn"  rid="' + row.id + '">'
                                + '<i class="fa fa-reply fa-btn"></i> 撤销'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            if (row.instanceId) {
                                res +='<@shiro.hasPermission name="dbApply:dbApply:commited">'
                                + '<button class="btn btn-xs btn-success commitedBtn" rid="' + row.id + '" createBy="' + row.createBy + '">'
                                + '<i class="fa fa-check-square-o fa-btn"></i>已提交审核'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            }else{
                                res +='<@shiro.hasPermission name="dbApply:dbApply:commit">'
                                + '<button class="btn btn-xs btn-info commitBtn" rid="' + row.id + '" createBy="' + row.createBy + '">'
                                + '<i class="fa fa-long-arrow-right fa-btn"></i>提交审核'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                            }
                        }
                        return res;
                    }
                }
            ]
        });
        //撤销
        $(document).on("click", ".cancelBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("督查督办撤销申请","${basePath}/db/dbApply/cancel?id="+id,"1000px","500px");
        });
        //延期
        $(document).on("click", ".delayBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("督查督办延期申请","${basePath}/db/dbApply/delay?id="+id,"1000px","500px");
        });

        //已提交审批
        $(document).on("click", ".commitedBtn", function () {
                alert("已提交申核，不要多次提交！");
                return;
        });
        //审批
        $(document).on("click", ".commitBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("确认要提交申请吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/db/dbApply/submitApply",
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

        //导出
        $(document).on("click", "#outExcelBtn", function () {
            var id = $(this).attr("rid");
            window.location.href = "${basePath}/db/dbApply/excelOut?id="+id;
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //新增
        $("#insertBtn").click(function () {
            top.krt.layer.openDialog("新增督查督办申请表","${basePath}/db/dbApply/insert","1000px","500px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改督查督办申请表","${basePath}/db/dbApply/update?id=" + id,"1000px","500px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/db/dbApply/delete",
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
                        url: "${basePath}/db/dbApply/deleteByIds",
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
