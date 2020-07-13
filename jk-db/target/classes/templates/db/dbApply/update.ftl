<#include "/common/layoutForm.ftl">
<@header></@header>
<@body >
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>督办类别：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="dbType" name="dbType" value="${dbApply.dbType!}" class="form-control" required>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>编号：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="dbId" name="dbId" value="${dbApply.dbId!}" class="form-control" required>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>督办事项来源：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="dbSource" name="dbSource" value="${dbApply.dbSource!}" class="form-control" required>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>来文日期：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="dbLwDate" id="dbLwDate" value="${(dbApply.dbLwDate?string("yyyy-MM-dd HH:mm:ss"))!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required>
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>来文明示签批内容：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="dbLwContent" name="dbLwContent" value="${dbApply.dbLwContent!}" class="form-control" required>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>事项内容及要求（简要）：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="dbContentBrief" name="dbContentBrief" value="${dbApply.dbContentBrief!}" class="form-control" required>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>所涉事项要求办结期限：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="dbAskFinaltime" id="dbAskFinaltime" value="${(dbApply.dbAskFinaltime?string("yyyy-MM-dd HH:mm:ss"))!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required>
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>重要程度（A,B,C）：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="dbLevel" name="dbLevel" required>
        <option value="">==请选择==</option>
            <@dic type="db_level" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}" ${((dbApply.dbLevel==item.code)?string("selected",""))!}>${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>牵头部门（公司）：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group">
        <input type="text" value="${dbApply.leadDepartment!}" id="leadDepartmentName"  name="leadDepartmentName" class="form-control" readonly required>
        <input type="hidden" name="leadDepartmentId" value="${dbApply.leadDepartment!}" id="leadDepartmentId" >
        <div class="input-group-btn">
            <button class="btn btn-default" id="leadDepartmentBtn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#leadDepartmentId,#leadDepartmentBtn").click(function () {
                var url = "db/dbApply/openUserList";
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择公司（部门）",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${basePath}/common/listSelect?url="+encodeURI(url),
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择公司（部门）");
                        } else {
                            $("#leadDepartmentId").val(sId);
                            $("#leadDepartmentName").val(sName);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#leadDepartmentId").val("");
                        $("#leadDepartmentName").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>责任部门（公司）：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group">
        <input type="text" value="${dbApply.responsibilityDepartment!}" id="responsibilityDepartmentName"  name="responsibilityDepartmentName" class="form-control" readonly required>
        <input type="hidden" name="responsibilityDepartmentId" value="${dbApply.responsibilityDepartment!}" id="responsibilityDepartmentId" >
        <div class="input-group-btn">
            <button class="btn btn-default" id="responsibilityDepartmentBtn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#responsibilityDepartmentId,#responsibilityDepartmentBtn").click(function () {
                var url = "db/dbApply/openUserList";
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择公司（部门）",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${basePath}/common/listSelect?url="+encodeURI(url),
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择公司（部门）");
                        } else {
                            $("#responsibilityDepartmentId").val(sId);
                             $("#responsibilityDepartmentName").val(sName);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#responsibilityDepartmentId").val("");
                        $("#responsibilityDepartmentName").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         牵头部门负责人：
                    </label>
                    <div class="col-sm-8">
    <textarea rows="2" name="departmentPrincipal" class="form-control">${dbApply.departmentPrincipal!}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         牵头部门分管领导：
                    </label>
                    <div class="col-sm-8">
    <textarea rows="2" name="chargeLead" class="form-control">${dbApply.chargeLead!}</textarea>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         总经理意见：
                    </label>
                    <div class="col-sm-8">
    <textarea rows="2" name="managerAdvice" class="form-control">${dbApply.managerAdvice!}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         董事长意见：
                    </label>
                    <div class="col-sm-8">
    <textarea rows="2" name="presidentAdvice" class="form-control">${dbApply.presidentAdvice!}</textarea>
                    </div>
                </div>
            </div>
                </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id" value="${dbApply.id!}">
                        </div>
                    </form>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    var validateForm;
    $(function () {
        //验证表单
        validateForm = $("#krtForm").validate({});

    });
    //提交
    function doSubmit() {
        krt.ajax({
            type: "POST",
            url: "${basePath}/db/dbApply/update",
            data: $('#krtForm').serialize(),
            validateForm: validateForm,
            success: function (rb) {
                krt.layer.msg(rb.msg);
                if (rb.code === 200) {
                    var index = krt.layer.getFrameIndex(); //获取窗口索引
                    krt.table.reloadTable();
                    krt.layer.close(index);
                }
            }
        });
    }
</script>
</@footer>

