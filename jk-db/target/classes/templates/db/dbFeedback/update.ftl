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
                                            督办类别：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="dbType"
                                                    name="dbType">
                                                <option value="">==请选择==</option>
                                                <@dic type="db_type" ; dicList>
                                                    <#list dicList as item>
                                                        <option value="${item.code}" ${((dbFeedback.dbType==item.code)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            编号：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="dbId" name="dbId" value="${dbFeedback.dbId!}"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            督办事项来源：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="dbSource" name="dbSource"
                                                   value="${dbFeedback.dbSource!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            事项内容及要求（简要）：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="dbContentBrief" name="dbContentBrief"
                                                   value="${dbFeedback.dbContentBrief!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            所涉事项要求办结期限：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="dbAskFinaltime"
                                                       id="dbAskFinaltime"
                                                       value="${(dbFeedback.dbAskFinaltime?string("yyyy-MM-dd HH:mm:ss"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
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
                                            重要程度（A,B,C）：
                                        </label>
                                        <div class="col-sm-8">
                                            <select class="form-control select2" style="width: 100%" id="dbLevel"
                                                    name="dbLevel">
                                                <option value="">==请选择==</option>
                                                <@dic type="db_level" ; dicList>
                                                    <#list dicList as item>
                                                        <option value="${item.code}" ${((dbFeedback.dbLevel==item.code)?string("selected",""))!}>${item.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>牵头部门（公司）：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group">
                                                <input type="text" value="${dbFeedback.leadDepartment!}"
                                                       id="leadDepartment" name="leadDepartment" class="form-control"
                                                       readonly required>
                                                <#--        <input type="hidden" name="leadDepartment" value="${dbApply.leadDepartment!}" id="leadDepartment" >-->
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="leadDepartmentBtn"
                                                            type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <script type="text/javascript">
                                                $(function () {
                                                    $("#leadDepartment,#leadDepartmentBtn").click(function () {
                                                        var url = "db/dbApply/openUserList";
                                                        krt.layer.open({
                                                            type: 2,
                                                            area: ['1000px', '600px'],
                                                            title: "选择公司（部门）",
                                                            maxmin: true, //开启最大化最小化按钮
                                                            content: "${basePath}/common/listSelect?url=" + encodeURI(url),
                                                            btn: ['确定', '取消', '关闭'],
                                                            yes: function (index, layero) {
                                                                var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                                                                var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                                                                if (sId == null || sId == '') {
                                                                    krt.layer.msg("请选择公司（部门）");
                                                                } else {
                                                                    $("#leadDepartment").val(sName);
                                                                    // $("#leadDepartment").val(sId);
                                                                    krt.layer.close(index);
                                                                }
                                                            },
                                                            btn2: function () {
                                                                $("#leadDepartment").val("");
                                                                $("#leadDepartment").val("");
                                                            },
                                                            cancel: function (index) {
                                                                krt.layer.close(index);
                                                            }
                                                        });
                                                    });
                                                })
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            <span class="form-required">*</span>责任部门（公司）：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group">
                                                <input type="text" value="${dbFeedback.responsibilityDepartment!}"
                                                       id="responsibilityDepartment" name="responsibilityDepartment"
                                                       class="form-control" readonly required>
                                                <#--        <input type="hidden" name="responsibilityDepartment" value="${dbApply.responsibilityDepartment!}" id="responsibilityDepartment" >-->
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="responsibilityDepartmentBtn"
                                                            type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <script type="text/javascript">
                                                $(function () {
                                                    $("#responsibilityDepartment,#responsibilityDepartmentBtn").click(function () {
                                                        var url = "db/dbApply/openUserList";
                                                        krt.layer.open({
                                                            type: 2,
                                                            area: ['1000px', '600px'],
                                                            title: "选择公司（部门）",
                                                            maxmin: true, //开启最大化最小化按钮
                                                            content: "${basePath}/common/listSelect?url=" + encodeURI(url),
                                                            btn: ['确定', '取消', '关闭'],
                                                            yes: function (index, layero) {
                                                                var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                                                                var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                                                                if (sId == null || sId == '') {
                                                                    krt.layer.msg("请选择公司（部门）");
                                                                } else {
                                                                    $("#responsibilityDepartment").val(sName);
                                                                    // $("#responsibilityDepartment").val(sId);
                                                                    krt.layer.close(index);
                                                                }
                                                            },
                                                            btn2: function () {
                                                                $("#responsibilityDepartment").val("");
                                                                $("#responsibilityDepartment").val("");
                                                            },
                                                            cancel: function (index) {
                                                                krt.layer.close(index);
                                                            }
                                                        });
                                                    });
                                                })
                                            </script>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            实际完成时间：
                                        </label>
                                        <div class="col-sm-8">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="totalTime"
                                                       id="totalTime"
                                                       value="${(dbFeedback.totalTime?string("yyyy-MM-dd HH:mm:ss"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
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
                                            完成佐证（最终完成情况及佐证材料目录）：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" id="finalEvidence" name="finalEvidence"
                                                   value="${dbFeedback.finalEvidence!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            牵头部门负责人：
                                        </label>
                                        <div class="col-sm-8">
                                            <textarea rows="2" name="departmentPrincipal"
                                                      class="form-control">${dbFeedback.departmentPrincipal!}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            牵头部门分管领导：
                                        </label>
                                        <div class="col-sm-8">
                                            <textarea rows="2" name="chargeLead"
                                                      class="form-control">${dbFeedback.chargeLead!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            总经理意见：
                                        </label>
                                        <div class="col-sm-8">
                                            <textarea rows="2" name="managerAdvice"
                                                      class="form-control">${dbFeedback.managerAdvice!}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            董事长意见：
                                        </label>
                                        <div class="col-sm-8">
                                            <textarea rows="2" name="presidentAdvice"
                                                      class="form-control">${dbFeedback.presidentAdvice!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 隐藏域 -->
                            <input type="hidden" name="id" id="id" value="${dbFeedback.id!}">
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
                url: "${basePath}/db/dbFeedback/update",
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

