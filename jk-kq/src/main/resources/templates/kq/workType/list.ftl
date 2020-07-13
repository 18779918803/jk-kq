<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">工作类型</h3>
                </div>
                <div class="box-body">
                    <div class="table-toolbar" id="table-toolbar">

                        <button id="insertBtn" data-placement="left" data-toggle="tooltip"
                                class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                        <button id="expandTableBtn" data-placement="left" data-toggle="tooltip"
                                class="btn bg-orange btn-sm">
                            <i class="fa fa-chevron-down"></i> 全部展开
                        </button>
                        <button id="collapseTableBtn" data-placement="left" data-toggle="tooltip"
                                class="btn bg-purple btn-sm" style="display:none;">
                            <i class="fa fa-chevron-up"></i> 全部收缩
                        </button>
                    </div>
                    <div class="table-responsive" id="table-body">
                        <table id="tree-table" class="table table-bordered table-hover"></table>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        /******************************** treeTable事件 ********************************/

        //初始化treeTable
        function initTreeTable() {
            $("#tree-table").treetable({
                expandable: true,
                onNodeExpand: nodeExpand,
                onNodeCollapse: nodeCollapse
            });
        }

        //刷新table
        function reloadTable() {
            var pid = $("#pid").val();
            initTable(pid);
        }

        //展开事件
        function nodeExpand() {
            getNodeViaAjax(this.id);
        }

        //收缩事件
        function nodeCollapse() {

        }

        //初始化父表
        function initTable(pid) {
            krt.ajax({
                type: "POST",
                url: "${basePath}/kq/workType/list",
                data: {"pid": pid},
                success: function (rb) {
                    if (rb.code == 200) {
                        var workTypesList = rb.data;
                        var tbody = '';
                        for (var i = 0; i < workTypesList.length; i++) {
                            var node = workTypesList[i];
                            var hasChild = "";
                            if (node.hasChild == 'true') {
                                hasChild = 'data-tt-branch="true"';
                            }
                            var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + '>'
                                + "<td> <i class='fa'></i>" + node.name + "</td>"

                                + "<td>" + node.remark + "</td>"
                                + "<td>"
                                + '<button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加子类别</button>'
                                + "</td>"
                                + "</tr>";
                            tbody = tbody + tr;
                        }
                        $("#tree-table").remove();
                        var html = '<table id="tree-table" class="table table-bordered table-hover table-jk"><thead><tr><th>考勤工作类型</th><th>描述</th><th>操作</th></tr></thead><tbody id="tbody">' + tbody + '</tbody></table>';
                        $("#table-body").append(html);
                        initTreeTable();
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //加载子节点
        function getNodeViaAjax(pid) {
            krt.ajax({
                type: 'POST',
                url: "${basePath}/kq/workType/list",
                data: {"pid": pid},
                success: function (rb) {
                    if (rb.code == 200) {
                        var childNodes = rb.data;
                        if (childNodes) {
                            var parentNode = $("#tree-table").treetable("node", pid);
                            for (var i = 0; i < childNodes.length; i++) {
                                var node = childNodes[i];
                                var nodeToAdd = $("#tree-table").treetable("node", node.id);
                                if (!nodeToAdd) {
                                    var hasChild = "";
                                    if (node.hasChild == 'true') {
                                        hasChild = 'data-tt-branch="true"';
                                    }
                                    var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + ' data-tt-parent-id="' + pid + '">'
                                        + "<td> <i class='fa'></i>" + node.name + "</td>"
                                        + "<td>" + node.remark + "</td>"
                                        + "<td>"
                                        + '<button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button>'
                                        + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button>'
                                        + "</td>"
                                        + "</tr>";
                                    $("#tree-table").treetable("loadBranch", parentNode, tr);
                                }
                            }
                        }
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //加载默认
        initTable(0);

        //收缩
        $("#collapseTableBtn").click(function () {
            $('#tree-table').treetable('collapseAll');
            $(this).hide();
            $('#expandTableBtn').show();
        });

        //展开
        $("#expandTableBtn").click(function () {
            $('#tree-table').treetable('expandAll');
            $(this).hide();
            $('#collapseTableBtn').show();
        })

        //新增
        $("#insertBtn").click(function () {
            top.krt.layer.openDialog("添加类型", "${basePath}/kq/workType/insert", "1000px", "480px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改类型", "${basePath}/kq/workType/update?id=" + id, "1000px", "480px");
        });

        //添加子资源
        $(document).on("click", ".addBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("添加子类型", "${basePath}/kq/workType/insert?pid=" + id, "1000px", "480px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除此类型及子类型吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/kq/workType/delete",
                    data: {"id": id},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            var pid = $("#pid").val();
                            initTable(pid);
                        }
                    }
                });
            });
        });
    </script>
</@footer>
