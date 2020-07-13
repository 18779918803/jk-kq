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
                    <@shiro.hasPermission name="sw:receivingRecords:excelOut">
                        <@krt.excelOut id="excelOutBtn" url="${basePath}/sw/receivingRecords/excelOut"></@krt.excelOut>
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
                url: "${basePath}/sw/receivingRecords/instructionList",
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
                        return krt.util.getDic('sw_received_org', data);
                    }
                },
                {title: "标题",data: "title"},
                {title: "密级",data: "securityClassification",
                    render: function (data) {
                        return krt.util.getDic('sw_security_classification', data);
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
                {title: "归档",data: "complete",
                    render: function (data) {
                        return krt.util.getDic('sw_complete',data);
                    }
                },
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        return ' <@shiro.hasPermission name="sw:swProcess:instruction">'
                            + '<button class="btn btn-xs btn-warning instructionBtn" rid="' + row.id + '"receivedSn="'
                            + row.receivedSn +'"title="'+ row.title + '"receivedOrg="'+ row.receivedOrg +'">'
                            + '<i class="fa fa-edit fa-btn"></i>审批'
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

        //审批
        $(document).on("click", ".instructionBtn", function () {
            var id = $(this).attr("rid");
            var receivedSn = $(this).attr("receivedSn");
            var receivedOrg = $(this).attr("receivedOrg");
            var title = $(this).attr("title");
            var content = "";
            var status = "";

            krt.layer.open({
                type: 2,
                area: ['1400px', '900px'],
                title: "标题:  " + title + "  来文单位： " + receivedOrg,
                maxmin: true,
                content: "${basePath}/sw/receivingRecords/instructionPage?id=" + id,
                btn: ['同意', '驳回', '关闭'],
                yes: function (index, layero) {
                    content = layero.find("iframe")[0].contentWindow.$("#content").val();
                    status = 2;
                    //var status = layero.find("iframe")[0].contentWindow.$("#status").val();
                    if (content == null || content == '') {
                        krt.layer.msg("请批示");
                    } else {
                        console.log(content+"  ----status:"+ status);
                        instruction(id, content, status);
                        krt.layer.close(index);
                    }
                },
                btn2: function (index, layero) {
                    content = layero.find("iframe")[0].contentWindow.$("#content").val();
                    status = 3;
                    if (content == null || content == '') {
                        krt.layer.msg("请批示");
                    } else {
                        console.log(content+"  ----status:"+ status);
                        instruction(id, content, status);
                        krt.layer.close(index);
                    }                    
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
            
        });

        //查看文件
        $(document).on("click", ".attachmentBtn", function () {
            var attachment = $(this).attr("rid");
            top.krt.layer.openDialogView("查看文件","${basePath}/sw/receivingRecords/showAttachment?attachment=" + attachment,"1000px","850px");
        })
       
    });

    function instruction(swid, content, status){
       krt.ajax({
            type: "POST",
            url: "${basePath}/sw/swProcess/instruction",
            data: {
                "swid": swid,
                "content": content,
                "status": status
            },
            success: function (rb) {
                krt.layer.msg(rb.msg);
                krt.table.reloadTable();
            }
        }); 
    }
</script>
</@footer>
