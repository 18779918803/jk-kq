<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <input type="hidden" id="sName" value="${RequestParameters['sName']!}"/>
        <input type="hidden" id="sId" value="${RequestParameters['sId']!}"/>
        <input type="hidden" id="roleId" value="${RequestParameters['roleId']!}"/>
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
        getResByRoleId();
        

        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/xcx/xcxPageRes/list",
                type: "post",
                data: function (d) {
                d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                d.orderType = d.order[0].dir;
                }
            },
            columns: [
                {title: 'id', data: "id",visible:false},
                {title: "id",data: "id"},
                {title: "名称",data: "name"},
                {title: "路径",data: "url"},
                {title: "图片地址",data: "src"},
                {
                    title: '<input type="checkbox" id="checkAll" class="icheck">',
                    data: "id", orderable: false, class:"td-center", width:"40",
                    "render": function (data, type, row) {
                        var checked = "";
                        var sId = "," + $("#sId").val() + ",";
                        var id = "," + row.id + ",";
                        if (sId.indexOf(id) > -1) {
                            checked = "checked";
                        }
                        return '<input type="checkbox" ' + checked + ' class="icheck cCheck" name="check" value="' + data + '" rname="' + row.name + '" rid="' + row.id + '">';
                    }
                }
            ],
            "fnDrawCallback": function () {
                cCheck();
            }
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });


    });

    function getResByRoleId(){
        var roleId = $("#roleId").val();
        krt.ajax({
            type: "POST",
            url: "${basePath}/xcx/xcxRoleRes/getResByRoleId",
            data: {
                "roleId": roleId
            },
            success: function (rb) {
                if (rb.code == 200) {
                    $("#sId").val(rb.data);
                }
            }
        });
    }
</script>
</@footer>
