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
                                <label for="name" class="control-label ">任务名称:</label>
                                <input type="hidden" id="instanceId" name="instanceId" value="${instanceId}">
                                <input type="text" id="activityName" name="activityName" placeholder="任务名称" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">办理人ID:</label>
                                <input type="text" id="assignee" name="assignee" placeholder="名字"
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
                    url: "${basePath}/process/listHistory",
                    type: "post",
                    data: function (d) {
                        d.instanceId = $("#instanceId").val();
                        d.activityName = $("#activityName").val();
                        d.assignee = $("#assignee").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {title: "活动ID", data: "activityId"},
                    {title: "任务名称", data: "activityName"},
                    {title: "处理人ID", data: "assigneeName"},
                    {title: "备注", data: "comment"},
                    {title: "开始时间", data: "startTime"},
                    {title: "结束时间", data: "endTime"},
                    {title: "耗时", data: "durationInMillis"},
                ]
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });


        });



    </script>
</@footer>
