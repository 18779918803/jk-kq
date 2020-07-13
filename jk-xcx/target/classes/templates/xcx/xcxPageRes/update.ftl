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
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         路径：
                    </label>
                    <div class="col-sm-10">
    <input type="text" id="url" name="url" value="${xcxPageRes.url!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         名称：
                    </label>
                    <div class="col-sm-10">
    <input type="text" id="name" name="name" value="${xcxPageRes.name!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-2">
                         图片地址：
                    </label>
                    <div class="col-sm-10">
    <input type="text" id="src" name="src" value="${xcxPageRes.src!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
    <!-- 隐藏域 -->
            <input type="hidden" name="id" id="id" value="${xcxPageRes.id!}">
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
            url: "${basePath}/xcx/xcxPageRes/update",
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

