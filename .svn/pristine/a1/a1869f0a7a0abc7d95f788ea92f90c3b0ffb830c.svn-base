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
                                <label for="instanceId" class="control-label ">流程实例ID:</label>
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
                    url: "${basePath}/leave/taskDoneList",
                    type: "post",
                    data: function (d) {

                        d.instanceId = $("#instanceId").val();
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
                    {title: "办理时间", data: "doneTime"},
                    {title: "流程实例ID", data: "instanceId"},
                    {
                        title: "已办任务名称",
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




        });
    </script>
</@footer>
