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
                                
                                <#list organs as organs>    
                                    <input name="organIds" value="${organs.id}" type="checkbox" class="icheck" 
                                        <#if usableList??>
                                            <#list usableList as item>
                                                <#if item.organId == organs.id>
                                                    checked
                                                </#if>
                                            </#list>
                                        </#if> 
                                        required> ${organs.name} <br>               
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
            url: "${basePath}/sw/receivingRecords/readSet",
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

