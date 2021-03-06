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
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <div class="col-sm-11"></div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-sm btn-info saveBtn" id="save" onclick="saveText()">保存</button>
                                    </div>
                                </div>
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
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label for="pname" class="control-label col-sm-3">
                                <span class="form-required">*</span>批示：
                            </label>
                            <div class="col-sm-9">
                                <textarea rows="4" required name="content" id="content" class="form-control">${content!}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            
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
        //验证表单
        //validateForm = $("#krtForm").validate({});
        //instructionsBySwid();
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
                    $("#instructions").append(rb.data.instructions);
                    $("#content").val(rb.data.content);
                    $("#propose").val(rb.data.propose);
                }
            }
        }); 
    }

    //保存文本
    function saveText() {
        var id = '${swid}';
        var text = $("#text").val();
        krt.ajax({
            type: "POST",
            url: "${basePath}/sw/receivingRecords/saveText",
            data: {
                "id": id,
                "text" : text
            },
            success: function (rb) {
                krt.layer.msg(rb.msg);
            }
        }); 
    }

</script>
</@footer>
