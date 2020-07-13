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
                                            <span class="form-required">*</span>工作类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            备注：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="remark" name="remark" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <#if pid == 0>
                                    <input type="hidden" name="pid" id="pid" value="0">
                                <#else>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="pname" class="control-label col-sm-4">
                                                <span class="form-required">*</span>父类型：
                                            </label>
                                            <div class="col-sm-8">
                                                <input type="hidden" id="pid" name="pid" value="${workType.id}" class="form-control">
                                                <input type="text" id="pname" name="pname" value="${workType.name}" disabled="disabled" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                                <!-- 隐藏域 -->
                            </div>
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
                url: "${basePath}/kq/workType/insert",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        var contentWindow = top.krt.tab.getContentWindow();
                        contentWindow.reloadTable();
                        krt.layer.close(index);
                    }
                }
            });
        }
    </script>
</@footer>

