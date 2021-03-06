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
                                            <input type="hidden" name="organId" id="organId" value="${(openUser.organ_id)!}" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="organ" value="${(openUser.organ)!}" id="organ" readonly class="form-control">
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
                                            <span class="form-required">*</span>角色：
                                        </label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="roleId" id="roleId" value="${(openUser.roleId)!}" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="role" value="${(openUser.role)!}" id="role" readonly class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="positionTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id" value="${openUser.id!}">
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

        //选择职位菜单
        $("#position,#positionTreeBtn").click(function () {
            var url = "${basePath}/kq/sys/role/list";
            krt.layer.open({
                type: 2,
                area: ['310px', '300px'],
                title: "选择菜单",
                maxmin: false,
                content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sValue=" + $("#positionId").val(),
                btn: ['确定', '取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择资源");
                    } else {
                        $("#position").val(nodes[0].name);
                        $("#positionId").val(nodes[0].id);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#role").val("");
                    $("#roleId").val("");
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
                url: "${basePath}/kq/openUser/update",
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

