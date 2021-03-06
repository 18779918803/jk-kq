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
                                            <span class="form-required">*</span>公司/部门：
                                        </label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="organId" id="organId" value="" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="organ" value=""
                                                       id="organ" class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="organTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>姓名：
                                        </label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="openUserId" id="openUserId" value="" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="name" value="" id="name" readonly class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="nameTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
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
                                            <span class="form-required">*</span>值班日期：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="date" id="date"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
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
                                            <span class="form-required">*</span>状态：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="state"
                                                    name="state" required>
                                                <option value="0" selected="selected">待值班</option>
                                                <option value="1">已值班</option>
                                                <option value="-1">未值班</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id">
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        //选择部门菜单
        $("#organ,#organTreeBtn").click(function () {
            var url = "${basePath}/sys/organ/organTree";
            krt.layer.open({
                type: 2,
                area: ['310px', '900px'],
                title: "选择菜单",
                maxmin: false,
                content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sValue=" + $("#organId").val(),
                btn: ['确定', '取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择资源");
                    } else {
                        $("#organ").val(nodes[0].name);
                        $("#organId").val(nodes[0].id);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#organ").val("");
                    $("#organId").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                },
            });
        });

        //选择姓名菜单
        $("#name,#nameTreeBtn").click(function () {
            var url = "${basePath}/kq/openUser/tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '600px'],
                title: "选择菜单",
                maxmin: false,
                content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "?organId=" + $("#organId").val(),
                btn: ['确定', '取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择值班人");
                    } else {
                        $("#name").val(nodes[0].name);
                        $("#openUserId").val(nodes[0].id);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#name").val("");
                    $("#openUserId").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });





        var validateForm;
        $(function () {
            //验证表单
            validateForm = $("#krtForm").validate({});

        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/kq/ondutyRecord/insert",
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

