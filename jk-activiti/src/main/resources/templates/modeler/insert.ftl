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
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>key：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="key" name="key" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            描述：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="description" name="description" class="form-control">
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
                url: "${basePath}/modeler/create",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();

                        top.krt.tab.openTab("模型在线设计", "modeler/modeler.html?modelId=" + rb.data)
                        krt.layer.close(index);
                    }
                },
                fail:function (rb) {
                    var index = krt.layer.getFrameIndex(); //获取窗口索引
                    krt.table.reloadTable();
                    krt.layer.close(index);
                }
            });
        }


        // function createMyMenuItem(dataUrl, menuName) {
        //     var panelUrl = window.parent.frameElement.getAttribute('data-id');
        //     dataIndex = $.common.random(1,100), flag = true;
        //     if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
        //     var topWindow = $(window.parent.parent.document);
        //     // 选项卡菜单不存在
        //     if (flag) {
        //         var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '" data-panel="' + panelUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        //         $('.menuTab', topWindow).removeClass('active');
        //
        //         // 添加选项卡对应的iframe
        //         var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-panel="' + panelUrl + '" seamless></iframe>';
        //         $('.mainContent', topWindow).find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(str1);
        //
        //         // 添加选项卡
        //         $('.menuTabs .page-tabs-content', topWindow).append(str);
        //     }
        //     return false;
        // }
    </script>
</@footer>

