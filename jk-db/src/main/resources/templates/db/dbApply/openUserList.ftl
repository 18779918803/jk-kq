<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <!-- 参数 -->
        <input type="hidden" id="sName" value="${RequestParameters['sName']!}"/>
        <input type="hidden" id="sId" value="${RequestParameters['sId']!}"/>
        <!-- 列表数据区 -->
        <div class="box">
            <div class="box-body">
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    var datatable;
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/sys/organ/list?pid=40",
                type: "post",
                data: function (d) {
    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
    d.orderType = d.order[0].dir;
                }
            },
            columns: [
    {title: 'id', data: "id",visible:false},
                {title: "部门",data: "name"},
                {
                    title: "操作",
                    data: "id", orderable: false, class:"td-center", width:"40",
                    "render": function (data, type, row) {
                        var checked = "";
                        var sId = $("#sId").val();
                        if (sId == data) {
                            checked = "checked";
                        }
                        return '<input type="checkbox" ' + checked + ' class="iCheck cCheck" name="check" value="' + data +  '" rid="' + row.id + '" rname="' + row.name+'">';
                    }
                }
            ],
            "fnDrawCallback": function () {
                cCheck();
            }
        });
        $(document).on("click", ".icheck", function () {
            //alert(event.type + ' callback');
            var sId = $(this).val();
            var sName = $(this).val();
            var sName = $(this).attr("rname");
            $("#sId").val(sId);
            $("#sName").val(sName);
        });
    })

</script>
</@footer>
