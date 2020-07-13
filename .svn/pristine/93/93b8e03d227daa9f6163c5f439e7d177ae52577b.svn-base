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
                                            <span class="form-required">*</span>姓名：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
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
                                                    <button class="btn btn-default" id="resTreeBtn" type="button">
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
                                            <span class="form-required">*</span>出差日期：
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
                                            <span class="form-required">*</span>出差原因：
                                        </label>
                                        <div class="col-sm-8">
                                            <textarea rows="2" name="reason" class="form-control" required></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>出差天数：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="day" name="day" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>申请状态：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="state"
                                                    name="state" required>
                                                <option value="">==请选择==</option>
                                                <option value="0">审核中</option>
                                                <option value="1">审批通过</option>
                                                <option value="-1">审批拒绝</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>出差类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="typeId"
                                                    name="typeId" required>
                                                <option value="">==请选择==</option>
                                                    <#list travelTypes as item>
                                                        <option value="${item.id}">${item.name}</option>
                                                    </#list>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!-- 隐藏域 -->
                                <input type="hidden" name="openUserId" id="openUserId">
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
        //选择部门菜单
        $("#organ,#resTreeBtn").click(function () {
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


        var validateForm;
        $(function () {
            //验证表单
            validateForm = $("#krtForm").validate({});

        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/kq/travelRecord/insert",
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

