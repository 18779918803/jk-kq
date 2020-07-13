<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box-search">
            <div class="row row-search">
                <div class="col-xs-12">
                    <form role="form" class="form-horizontal krt-form" action="">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="receivedSn" class="control-label col-sm-2">来文字号:</label>
                                    <div class="col-sm-10">
                                        <input type="text" disabled="disabled" id="receivedSn" name="receivedSn" value="${receivingRecords.receivedSn!}" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="title" class="control-label col-sm-2">标题:</label>
                                    <div class="col-sm-10">
                                        <input type="text" disabled="disabled" id="title" name="title" value="${receivingRecords.title!}" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="swid" name="swid" value="${receivingRecords.id}">   
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
                    <@shiro.hasPermission name="sw:swTask:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="sw:swTask:delete">
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
    var swid = $('#swid').val();
    console.log(swid);
    var datatable;
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/sw/swTask/list",
                type: "post",
                data: function (d) {
                    d.swid = swid;
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
                {title: "任务实例ID",data: "taskId"},
                {title: "历史附件",data: "attachment", orderable: false,
                    "render": function (data, type, row) {
                        return ' <@shiro.hasPermission name="sw:swTask:list">'
                            + '<button class="btn btn-xs btn-info attachmentBtn" rid="' + row.id + '" attachment="' + row.attachment + '">'
                            + '<i class="fa fa-file fa-btn"></i> 附件'
                            + '</button>'
                            + '</@shiro.hasPermission>';}
                },
                {title: "收文ID",data: "swid"},
                {title: "创建时间",data: "insertTime"},
                {title: "任务状态",data: "status",
                    render: function (data) {
                        return krt.util.getDic('sw_task_status',data);
                    }
                },
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        return ' <@shiro.hasPermission name="sw:swTask:start">'
                            + '<button class="btn btn-xs btn-success startBtn" rid="' + row.id + '" status="' + row.status + '">'
                            + '<i class="fa fa-hourglass-1 fa-btn"></i>启动'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            + '<@shiro.hasPermission name="sw:swTask:list">'
                            + '<button class="btn btn-xs btn-warning setBtn" taskId="' + row.id + '" swid="' + row.swid + '">'
                            + '<i class="fa fa-edit fa-btn"></i>设置'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            + '<@shiro.hasPermission name="sw:swTask:stop">'
                            + '<button class="btn btn-xs btn-danger stopBtn" rid="' + row.id + '" status="' + row.status + '">'
                            + '<i class="fa fa-minus-circle fa-btn"></i>停止'
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

        //查看文件
        $(document).on("click", ".attachmentBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialogView("查看历史文件","${basePath}/sw/swTask/versionAttachment?id=" + id,"1400px","850px");
        })

        //设置
        $(document).on("click", ".setBtn", function () {
            var taskId = $(this).attr("taskId");
            var swid = $(this).attr("swid");
            top.krt.layer.openDialogView("设置任务流程图","${basePath}/sw/swTask/info?swid=" + swid + "&taskId=" + taskId,"1400px","800px");
        });

        //新增
        $("#insertBtn").click(function () {
           krt.ajax({
            type: "POST",
            url: "${basePath}/sw/swTask/insert",
            data: {
                "swid": swid
            },
            success: function (rb) {
                krt.layer.msg(rb.msg);
                if (rb.code === 200) {
                    datatable.ajax.reload();
                }
            }
        });
        });

        //启动
        $(document).on("click", ".startBtn", function () {
            var id = $(this).attr("rid");
            var status = $(this).attr("status");
            var msg = "你确定启动吗？";
            if(status == 2){
                msg = "任务正在进行中，确定再次启动吗？";
            }else if (status == 1 || status == 3){
                krt.layer.msg("任务结束，不能启动");
                return;
            }
            krt.layer.confirm(msg, function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sw/swTask/start",
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

        //停止
        $(document).on("click", ".stopBtn", function () {
            var id = $(this).attr("rid");
            var status = $(this).attr("status");
            var msg = "你确定停止吗？";
            if(status == 4){
                krt.layer.msg("任务未开始，不能停止");
                return;
            }else if (status == 1 || status == 3){
                krt.layer.msg("任务结束，不能停止");
                return;
            }else if (status == 5){
                krt.layer.msg("任务已停止");
                return;
            }
            krt.layer.confirm(msg, function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sw/swTask/stop",
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

        
    });
</script>
</@footer>
