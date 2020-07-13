<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <#--  <input type="hidden" id="content" value="${RequestParameters['content']!}"/>  -->
        <#--  <input type="hidden" id="status" value="${RequestParameters['status']!}"/>  -->
        <!-- 列表数据区 -->
        <div class="box">
            <div class="box-body">
            </div>
                <embed id="embed1" src="${url!}" width="100%" height="550px"></embed>
        </div>
        
        <form role="form" class="form-horizontal krt-form" id="krtForm">
            <div class="box-body">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label for="pname" class="control-label col-sm-2">
                                拟办意见：
                            </label>
                            <div class="col-sm-10">
                                <textarea id="propose" readonly rows="2" name="propose" class="form-control">${propose!}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label for="pname" class="control-label col-sm-2">
                                领导批示：
                            </label>
                            <div id="instructions" class="col-sm-10">
                            </div>
                        </div>
                    </div>
                
                </div>
            </div>
        </form>
           
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    var validateForm;
    $(function () {
        //验证表单
        //validateForm = $("#krtForm").validate({});
        instructionsBySwid();
        instructionPageData();
    });

    function instructionsBySwid(){
        var id = '${swid}';
        krt.ajax({
            type: "POST",
            url: "${basePath}/sw/receivingRecords/instructionsBySwid",
            data: {"id": id},
            success: function (rb) {
                if (rb.code === 200) {
                    $("#instructions").append(rb.data);
                }
            }
        }); 
    }

    function instructionPageData(){
        var id = '${swid}';
        krt.ajax({
            type: "POST",
            url: "${basePath}/sw/receivingRecords/instructionPageData",
            data: {"id": id},
            success: function (rb) {
                if (rb.code === 200) {
                    $("#propose").val(rb.data.propose);
                }
            }
        }); 
    }

</script>
</@footer>
