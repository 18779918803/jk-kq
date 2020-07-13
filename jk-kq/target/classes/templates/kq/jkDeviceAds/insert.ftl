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
                         设备轮播图的排序号：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="imgIndex" name="imgIndex">
        <option value="">==请选择==</option>
            <@dic type="adIndex" ; dicList>
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
                         图标地址：
                    </label>
                    <div class="col-sm-8">
    <div id='uploader-imgUrl' class="uploader"></div>
    <input type="hidden" id="imgUrl"  name="imgUrl">
    <script type="text/javascript">
        $(function () {
            $('#uploader-imgUrl').krtUploader({
                type: 'grid-list',
                url: '${basePath}/upload/fileUpload?dir=image',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '图片', extensions: 'gif,jpg,jpeg,png,bmp'}
                    ]
                },
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除图片',
                resultAllCallBack: function (data) {
                    $("#imgUrl").val(data);
                },
            });
        });
    </script>
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
            url: "${basePath}/kq/jkDeviceAds/insert",
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

