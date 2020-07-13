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
                                            <span class="form-required">*</span>设备信息：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="data" name="data" value="${deviceInfo.data!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>设备型号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="model" name="model" value="${deviceInfo.model!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>系统版本号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="rom" name="rom" value="${deviceInfo.rom!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>SD卡剩余空间：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="space" name="space" value="${deviceInfo.space!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>应用版本号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="app" name="app" value="${deviceInfo.app!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>剩余内存：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="memory" name="memory" value="${deviceInfo.memory!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>用户数：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="user" name="user" value="${deviceInfo.user!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>指纹数：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="fingerprint" name="fingerprint"
                                                   value="${deviceInfo.fingerprint!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>人脸数：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="face" name="face" value="${deviceInfo.face!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>头像数量：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="headpic" name="headpic"
                                                   value="${deviceInfo.headpic!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>打卡记录数量：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="clockin" name="clockin"
                                                   value="${deviceInfo.clockin!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>现场照片数量：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="pic" name="pic" value="${deviceInfo.pic!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id" value="${deviceInfo.id!}">
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
                url: "${basePath}/kq/deviceInfo/update",
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

