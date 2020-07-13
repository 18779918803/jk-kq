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
                            <div class="col-md-12">
                                <#list allotList as allotList>    
                                    <div class="col-sm-3" style="margin-bottom:10px">
                                        <input name="uids" value="${allotList.id}" type="checkbox" class="icheck" 
                                            <#if usableList??>
                                                <#list usableList as item>
                                                    <#if item.uid == allotList.id>
                                                        checked
                                                    </#if>
                                                </#list>
                                            </#if> 
                                            required> ${allotList.name}              
                                    </div> 
                                </#list>
                            </div>
                        </div>
    <!-- 隐藏域 -->
                        <input type="hidden" name="id" id="id" value="${id!}">
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
            url: "${basePath}/sw/receivingRecords/allot",
            data: $('#krtForm').serialize(),
            validateForm: validateForm,
            success: function (rb) {
                krt.layer.msg(rb.msg);
                if (rb.code === 200) {
                    var index = krt.layer.getFrameIndex(); //获取窗口索引
                    //krt.table.reloadTable();
                    krt.layer.close(index);
                }
            }
        });
    }
</script>
</@footer>

