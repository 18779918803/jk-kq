<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <!-- 搜索条件区 -->
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="name" class="control-label ">用户ID:</label>

                                <input type="text" id="id" name="id" placeholder="用户ID" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">名字:</label>
                                <input type="text" id="name" name="name" placeholder="名字"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label ">邮箱:</label>
                                <input type="text" id="email" name="email" placeholder="邮箱"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                        <i class="fa fa-search fa-btn"></i>搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <!-- 工具按钮区 -->
                    <div class="table-toolbar" id="table-toolbar">
                    </div>
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
                    url: "${basePath}/user/list",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.id = $("#id").val();
                        d.email = $("#email").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {title: "用户id", data: "id"},
                    {title: "名字", data: "first"},
                    {title: "邮箱", data: "email"}
                ]
            });


            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });


        });



    </script>
</@footer>
