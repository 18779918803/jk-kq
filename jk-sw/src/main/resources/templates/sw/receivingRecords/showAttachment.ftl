<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="row row-space15">
        <div class="col-sm-7">
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body row-box">
                    <#if editable == 2>
                        <embed src="${url!}" width="100%" height="600px"></embed>
                    <#else>
                        <div class="row">
                            <div class="col-sm-12">
                            <@krt.editor height="600px" name="text" value="${text!}" validate=''></@krt.editor>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <form role="form" class="form-horizontal krt-form" id="krtForm">
                <div class="box-body">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="pname" class="control-label col-sm-3">
                                    拟办意见：
                                </label>
                                <div class="col-sm-9">
                                    <textarea id="propose" readonly rows="4" name="propose" class="form-control">${propose!}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="pname" class="control-label col-sm-3">
                                    领导批示：
                                </label>
                                <div id="instructions" class="col-sm-9">
                                </div>
                            </div>
                        </div>
                    
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
    var validateForm;
    $(function () {
        instructionsBySwid();
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
</script>
</@footer>
