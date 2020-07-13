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
                <label for="receivedSn" class="control-label ">来文字号:</label>
    <input type="text" id="receivedSn" name="receivedSn" placeholder="来文字号" class="form-control">
            </div>
            <div class="form-group">
                <label for="startDate" class="control-label ">收文起始日期:</label>
                <div class="input-group input-group-addon-right-radius">
                    <input type="text" class="form-control pull-right" name="startDate" id="startDate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="endDate" class="control-label ">收文截止日期:</label>
                <div class="input-group input-group-addon-right-radius">
                    <input type="text" class="form-control pull-right" name="endDate" id="endDate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="receivedOrg" class="control-label ">来文单位:</label>
                <div class="form-group">
                    <select class="form-control select2" style="width: 100%" id="receivedOrg" name="receivedOrg">
                        <option value="">==请选择==</option>
                            <@dic type="sw_received_org" ; dicList>
                                <#list dicList as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                            </@dic>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="securityClassification" class="control-label ">密级:</label>
    <div class="form-group">
        <select class="form-control select2" style="width: 100%" id="securityClassification" name="securityClassification">
            <option value="">==请选择==</option>
                <@dic type="sw_security_classification" ; dicList>
                    <#list dicList as item>
                        <option value="${item.code}">${item.name}</option>
                    </#list>
                </@dic>
        </select>
    </div>
            </div>
            <div class="form-group">
                <label for="importance" class="control-label ">重要程度:</label>
    <div class="form-group">
        <select class="form-control select2" style="width: 100%" id="importance" name="importance">
            <option value="">==请选择==</option>
                <@dic type="sw_importance" ; dicList>
                    <#list dicList as item>
                        <option value="${item.code}">${item.name}</option>
                    </#list>
                </@dic>
        </select>
    </div>
            </div>
            <div class="form-group">
                <label for="category" class="control-label ">类别:</label>
    <div class="form-group">
        <select class="form-control select2" style="width: 100%" id="category" name="category">
            <option value="">==请选择==</option>
                <@dic type="sw_category" ; dicList>
                    <#list dicList as item>
                        <option value="${item.code}">${item.name}</option>
                    </#list>
                </@dic>
        </select>
    </div>
            </div>
        <div class="form-group">
            <label for="editable" class="control-label ">可编辑:</label>
            <div class="form-group">
                <select class="form-control select2" style="width: 100%" id="editable" name="editable">
                    <option value="">==请选择==</option>
                        <@dic type="sys_is_not" ; dicList>
                            <#list dicList as item>
                                <option value="${item.code}">${item.name}</option>
                            </#list>
                        </@dic>
                </select>
            </div>
        </div>

            <div class="form-group">
                <label for="complete" class="control-label ">归档:</label>
    <div class="form-group">
        <select class="form-control select2" style="width: 100%" id="complete" name="complete">
            <option value="">==请选择==</option>
                <@dic type="sw_complete" ; dicList>
                    <#list dicList as item>
                        <option value="${item.code}">${item.name}</option>
                    </#list>
                </@dic>
        </select>
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
                    <@shiro.hasPermission name="sw:receivingRecords:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                        <@shiro.hasPermission name="sw:receivingRecords:excelOut">
                            <@krt.excelOut id="excelOutBtn" url="${basePath}/sw/receivingRecords/excelOut"></@krt.excelOut>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="sw:receivingRecords:excelIn">
                            <@krt.excelIn id="excelInBtn" url="${basePath}/sw/receivingRecords/excelIn"></@krt.excelIn>
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
                url: "${basePath}/sw/receivingRecords/list",
                type: "post",
                data: function (d) {
                d.receivedSn = $("#receivedSn").val();
                d.startDate = $("#startDate").val();
                d.endDate = $("#endDate").val();
                d.receivedOrg = $("#receivedOrg").val();
                d.securityClassification = $("#securityClassification").val();
                d.importance = $("#importance").val();
                d.category = $("#category").val();
                d.complete = $("#complete").val();
                d.editable = $("#editable").val();
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
        },{title: "id",data: "id"},
                {title: "来文字号",data: "receivedSn"},
                {title: "收文日期",data: "receivedDate"},
                {title: "来文单位",data: "receivedOrg",
                    render: function (data) {
                        return krt.util.getDic('sw_received_org',data);
                    }
                },
                {title: "标题",data: "title"},
                {title: "密级",data: "securityClassification",
                    render: function (data) {
                        return krt.util.getDic('sw_security_classification',data);
                    }
                },
                {title: "重要程度",data: "importance",
                    render: function (data) {
                        return krt.util.getDic('sw_importance',data);
                    }
                },
                {title: "类别",data: "category",
                    render: function (data) {
                        return krt.util.getDic('sw_category',data);
                    }
                },
                {title: "可编辑",data: "editable",
                    render: function (data) {
                        return krt.util.getDic('sys_is_not',data);
                    }
                },
                {title: "归档",data: "complete",
                    render: function (data) {
                        return krt.util.getDic('sw_complete',data);
                    }
                },
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        return ' <@shiro.hasPermission name="sw:receivingRecords:circulate">'
                            + '<button class="circulateBtn btn btn-success btn-xs" rid="' + row.id + '">'
                            + '<i class="fa fa-plus fa-btn"></i> 流转'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            +' <@shiro.hasPermission name="sw:receivingRecords:readSet">'
                            + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-eye fa-btn"></i>传阅'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            +' <@shiro.hasPermission name="sw:receivingRecords:allot">'
                            + '<button class="btn btn-xs btn-info allotBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-eye fa-btn"></i>分发'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            +' <@shiro.hasPermission name="sw:receivingRecords:see">'
                            + '<button class="btn btn-xs btn-danger attachmentBtn" rid="' + row.id
                            + '"receivedSn="' + row.receivedSn +'"title="'+ row.title + '"receivedOrg="'+ row.receivedOrg + '">'
                            + '<i class="fa fa-file fa-btn"></i>文件'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            +' <@shiro.hasPermission name="sw:receivingRecords:update">'
                            + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-edit fa-btn"></i>修改'
                            + '</button>'
                            + '</@shiro.hasPermission>'
                            +' <@shiro.hasPermission name="sw:receivingRecords:update">'
                            + '<button class="btn btn-xs bg-purple completeBtn" rid="' + row.id + '" complete="'+ row.complete +'">'
                            + '<i class="fa fa-check-circle fa-btn"></i>归档'
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
            top.krt.layer.openDialog("新增收文记录","${basePath}/sw/receivingRecords/insert","1000px","700px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改收文记录","${basePath}/sw/receivingRecords/update?id=" + id,"1000px","700px");
        });

        //归档
        $(document).on("click", ".completeBtn", function () {
            var id = $(this).attr("rid");
            var complete = $(this).attr("complete");
            if (complete == 1) {
                krt.layer.msg("文件已归档，不能再次归档!");
                return;
            }else{
                krt.layer.confirm("你确定归档吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/sw/receivingRecords/complete",
                        data: {"id": id},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                //krt.table.reloadTable();
                            }
                        }
                    });
                });
            }
        });

        //流转
        $(document).on("click", ".circulateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.tab.openTab("收文任务管理","${basePath}/sw/swTask/list?id=" + id);
        });

        //分发
        $(document).on("click", ".allotBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("收文分发","${basePath}/sw/receivingRecords/allot?id=" + id,"1000px","600px");
        });

        //传阅
        $(document).on("click", ".seeBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("收文传阅","${basePath}/sw/receivingRecords/readSet?id=" + id,"500px","800px");
        });

        //查看文件
        $(document).on("click", ".attachmentBtn", function () {
            var id = $(this).attr("rid");
            var receivedSn = $(this).attr("receivedSn");
            var receivedOrg = $(this).attr("receivedOrg");
            var title = $(this).attr("title");
            top.krt.layer.openDialogView("标题:  " + title + "  来文单位： " + receivedOrg, "${basePath}/sw/receivingRecords/showAttachment?id=" + id,"1400px","800px");
        })
    });
</script>
</@footer>
