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
                         任务实例ID：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="taskId" name="taskId" value="${swTask.taskId!}" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         任务名称：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="taskName" name="taskName" value="${swTask.taskName!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         收文ID：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="swid" name="swid" value="${swTask.swid!}" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         任务状态：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="status" name="status" value="${swTask.status!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
    <!-- 隐藏域 -->
            <input type="hidden" name="id" id="id" value="${swTask.id!}">
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
            url: "${basePath}/sw/swTask/update",
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

