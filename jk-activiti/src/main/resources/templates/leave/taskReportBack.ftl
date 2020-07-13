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
                                            <span class="form-required">*</span>申请人：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="applyUserName" name="applyUserName"
                                                   value="${leave.applyUser!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>申请时间：
                                        </label>
                                        <div class="col-sm-10">

                                            <input type="text" class="form-control pull-right" name="applyTime"
                                                   id="startTime"
                                                   value="${(leave.applyTime?string("yyyy-MM-dd"))!}"
                                                   readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                   required>
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>

                                            <#--<input type="text" id="applyUserName" name="applyUserName"-->
                                            <#--value="${leave.applyUserName!}" class="form-control" required>-->
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>标题：
                                        </label>
                                        <div class="col-sm-10">
                                            <#--<input name="id"  value="${leave.id!}" type="hidden">-->

                                            <#--<input type="hidden" name="p_COM_comment" />-->
                                            <input type="text" id="title" name="title" value="${leave.title!}"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>原因：
                                        </label>
                                        <div class="col-sm-10">
                                            <textarea rows="2" name="reason" class="form-control"
                                                      required>${leave.reason!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>开始时间：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="startTime"
                                                       id="startTime"
                                                       value="${(leave.startTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>结束时间：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="endTime"
                                                       id="endTime"
                                                       value="${(leave.endTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>





                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>请假时长，单位秒：
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" id="totalTime" name="totalTime"
                                                   value="${leave.totalTime!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>实际开始时间：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="realityStartTime"
                                                       id="realityStartTime"
                                                       value="${(leave.startTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-2">
                                            <span class="form-required">*</span>实际结束时间：
                                        </label>
                                        <div class="col-sm-10">
                                            <div class="input-group input-group-addon-right-radius">
                                                <input type="text" class="form-control pull-right" name="realityEndTime"
                                                       id="realityEndTime"
                                                       value="${(leave.endTime?string("yyyy-MM-dd"))!}"
                                                       readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                                       required>
                                                <div class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>






                            <!-- 隐藏域 -->
                            <input type="hidden" name="p_DT_realityStartTime" />
                            <input type="hidden" name="p_DT_realityEndTime" />
                            <#--<input type="hidden" name="saveEntity" value="true" />-->

                            <input type="hidden" name="id" id="id" value="${leave.id!}">
                            <input name="taskId" id="taskId" value="${taskId!}" type="hidden">
                            <input type="hidden" name="instanceId" id="instanceId" value="${leave.instanceId!}">
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
            <#--var leave = ${leave};-->
            <#--var dateSub = formatTotalDateSub(leave.totalTime);-->
            <#--$('#totalTime').val(dateSub);-->
        });

        /**
         * 计算出相差天数
         * @param secondSub
         */
        function formatTotalDateSub (secondSub) {
            var days = Math.floor(secondSub / (24 * 3600));     // 计算出小时数
            var leave1 = secondSub % (24*3600) ;                // 计算天数后剩余的毫秒数
            var hours = Math.floor(leave1 / 3600);              // 计算相差分钟数
            var leave2 = leave1 % (3600);                       // 计算小时数后剩余的毫秒数
            var minutes = Math.floor(leave2 / 60);              // 计算相差秒数
            var leave3 = leave2 % 60;                           // 计算分钟数后剩余的毫秒数
            var seconds = Math.round(leave3);
            return days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + ' 秒';
        }

        //提交
        function doSubmit() {
            $('input[name="p_DT_realityStartTime"]').val($('input[name="realityStartTime"]').val());
            $('input[name="p_DT_realityEndTime"]').val($('input[name="realityEndTime"]').val());
            // if ($('#approvalDes').val()) {
            //     $('#p_COM_comment').val($('#approvalDes').val());
            // }
            var taskId = $("#taskId").val();
            krt.ajax({
                type: "POST",
                url: "${basePath}/leave/complete/" + taskId,
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

