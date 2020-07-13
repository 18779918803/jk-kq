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
                                            <input type="text" id="name" name="name" value="${workType.name!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            备注：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="remark" name="remark" value="${workType.remark!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <#if workType.pid == 0>
                                    <input type="hidden" name="pid" id="pid" value="0">
                                <#else>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="pname" class="control-label col-sm-4">
                                                <span class="form-required">*</span>父类ID：
                                            </label>
                                            <div class="col-sm-8">
                                                <select class="form-control select2" style="width: 100%" id="pid" name="pid"
                                                        required>
                                                    <option value="">==请选择==</option>
                                                    <#list workTypeParent as item>
                                                        <option value="${item.id}" ${((workType.pid==item.id)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </#if>

                                <!-- 隐藏域 -->
                                <input type="hidden" name="id" id="id" value="${workType.id!}">
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
                url: "${basePath}/kq/workType/update",
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

