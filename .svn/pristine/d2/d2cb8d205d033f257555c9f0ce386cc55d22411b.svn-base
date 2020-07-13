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
                         <span class="form-required">*</span>申请类别：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="applyType" name="applyType" required>
        <option value="">==请选择==</option>
            <@dic type="apply_type" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}" ${((dbDelay.applyType==item.code)?string("selected",""))!}>${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>申请原因：
                    </label>
                    <div class="col-sm-8">
    <textarea rows="2" name="applyReason" class="form-control" required>${dbDelay.applyReason!}</textarea>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>调整后的时间：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="upTime" id="upTime" value="${(dbDelay.upTime?string("yyyy-MM-dd HH:mm:ss"))!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required>
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
                    </div>
                </div>
            </div>
            <!-- 隐藏域 -->
            <input type="hidden" name="id" id="id" value="${dbDelay.id!}">
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
            url: "${basePath}/db/dbDelay/update",
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

