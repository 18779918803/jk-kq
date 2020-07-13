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
                         <span class="form-required">*</span>来文字号：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="receivedSn" name="receivedSn" class="form-control" required>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>收文日期：
                    </label>
                    <div class="col-sm-8">
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="receivedDate" id="receivedDate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required>
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
                         <span class="form-required">*</span>来文单位：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="receivedOrg" name="receivedOrg" required>
        <option value="">==请选择==</option>
            <@dic type="sw_received_org" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}">${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>标题：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="title" name="title" class="form-control" required>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>密级：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="securityClassification" name="securityClassification" required>
        <option value="">==请选择==</option>
            <@dic type="sw_security_classification" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}">${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>重要程度：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="importance" name="importance" required>
        <option value="">==请选择==</option>
            <@dic type="sw_importance" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}">${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         拟办意见：
                    </label>
                    <div class="col-sm-10">
    <textarea rows="2" name="propose" class="form-control"></textarea>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         备注：
                    </label>
                    <div class="col-sm-10">
    <textarea rows="2" name="remark" class="form-control"></textarea>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>类别：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="category" name="category" required>
        <option value="">==请选择==</option>
            <@dic type="sw_category" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}">${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         <span class="form-required">*</span>可编辑：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="editable" name="editable" required>
        <option value="">==请选择==</option>
            <@dic type="sys_is_not" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}">${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         附件：
                    </label>
                    <div class="col-sm-10">
    <div id='uploader-attachment' class="uploader"></div>
    <input type="hidden" id="attachment"  name="attachment">
    <script type="text/javascript">
        $(function () {
            $('#uploader-attachment').krtUploader({
                type: 'lg-list',
                url: '${basePath}/upload/fileUpload?dir=file',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '文件', extensions: 'doc,docx,xls,xlsx,ppt,zip,rar,pdf'}
                    ]
                },
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除文件',
                resultAllCallBack: function (data) {
                    $("#attachment").val(data);
                },
            });
        });
    </script>
                    </div>
                </div>
            </div>
                </div>
                
    <div class="row">
        <div class="col-sm-12">
            <div class="form-group">
                <label for="pname" class="control-label col-sm-2">
                        文件文本：
                </label>
                <div class="col-sm-10">
                    <@krt.editor name="text" validate=''></@krt.editor>
                </div>
            </div>
        </div>
    </div>
    <!-- 隐藏域 -->
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
            url: "${basePath}/sw/receivingRecords/insert",
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

