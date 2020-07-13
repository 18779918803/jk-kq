<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row row-space15">
                <div class="col-sm-2">
                    <#--机构数-->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">机构树</h3>
                            <div class="box-tools">
                                <button class="btn btn-box-tool" data-widget="collapse" id="expandBtn" title="展开">
                                    <i class="fa  fa-chevron-down"></i>
                                </button>
                                <button class="btn btn-box-tool" data-widget="collapse" id="collapseBtn" title="折叠"
                                        style="display:none;">
                                    <i class="fa  fa-chevron-up"></i>
                                </button>
                                <button class="btn btn-box-tool" data-widget="collapse" id="refBtn" title="刷新">
                                    <i class="fa fa-refresh"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div id="ztree" class="ztree"></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-10">
                    <!-- 搜索条件区 -->
                    <div class="box-search">
                        <div class="row row-search">
                            <div class="col-xs-12">
                                <form class="form-inline" action="">
                                    <div class="form-group">
                                        <label for="name" class="control-label ">姓名:</label>
                                        <input type="text" id="name" name="name" placeholder="姓名" class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label for="gender" class="control-label ">性别:</label>
                                        <div class="form-group">
                                            <select class="form-control select2" style="width: 100%" id="gender" name="gender">
                                                <option value="">全部</option>
                                                <option value="1">男</option>
                                                <option value="2">女</option>
                                                <option value="0">未知</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="state" class="control-label ">状态:</label>
                                        <div class="form-group">
                                            <select class="form-control select2" style="width: 100%" id="state" name="state">
                                                <option value="">全部</option>
                                                <option value="0">审核</option>
                                                <option value="1">通过</option>
                                                <option value="-1">拒绝</option>
                                            </select>
                                        </div>
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
                                <@shiro.hasPermission name="OpenUser:openUser:insert">
                                    <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                            class="btn btn-success btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="OpenUser:openUser:delete">
                                    <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                        <i class="fa fa-trash fa-btn"></i>批量删除
                                    </button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="OpenUser:openUser:excelOut">
                                    <@krt.excelOut id="excelOutBtn" url="${basePath}/kq/openUser/excelOut"></@krt.excelOut>
                                </@shiro.hasPermission>
                            </div>
                            <table id="datatable" class="table table-bordered table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        /******************************** tree事件 *************************************/
            //ztree设置
        var setting = {
                view: {
                    selectedMulti: false,
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: "pid",
                    }
                },
                callback: {
                    onClick: zTreeOnClick
                }
            };

        //初始化定义部门名称
        var organId = '';
        //ztree点击事件
        function zTreeOnClick(event, treeId, treeNode) {
            var id = treeNode.id;
            if(id == organId){
                organId = '';
            }else {
                organId = id;
            }
            datatable.ajax.reload();
        }

        //加载树数据
        function initTree() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/organ/organTree",
                async: false,
                success: function (rb) {
                    if (rb.code == 200) {
                        $.fn.zTree.init($("#ztree"), setting, rb.data);
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //刷新树
        $("#refBtn").click(function () {
            $("#organCode").val("");
            initTree();
        });

        //展开
        $("#expandBtn").click(function () {
            tree.expandAll(true);
            $(this).hide();
            $('#collapseBtn').show();
        });

        //折叠
        $("#collapseBtn").click(function () {
            tree.expandAll(false);
            $(this).hide();
            $('#expandBtn').show();
        });

        //初始化tree
        initTree();
        var tree = $.fn.zTree.getZTreeObj("ztree");
        //初始化时为展开状态
        $("#expandBtn").click();

        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/kq/openUser/list",
                    type: "post",
                    data: function (d) {
                        d.gender = $("#gender").val();
                        d.organId = organId;
                        d.name = $("#name").val();
                        d.state = $("#state").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {
                        title: "序号",
                        data: "id",
                        render: function(data, type, full, meta) {
                            return meta.row + 1 + meta.settings._iDisplayStart;
                        }
                    },
                    {title: "姓名", data: "name"},
                    {title: "公司/部门", data: "organ"},
                    {
                        title: "性别", data: "gender",
                        "render": function (data, type, row) {
                            if (data == 1) {
                                return '男';
                            } else if (data == 2) {
                                return '女';
                            } else {
                                return '未知';
                            }
                        }
                    },
                    {
                        title: "昵称", data: "nickName",
                        "render": function (data, type, row) {
                            return decodeURI(data);
                        }
                    },
                    {title: "国家", data: "country"},
                    {title: "省份", data: "province"},
                    {title: "城市", data: "city"},
                    {title: "角色", data: "role"},
                    {title: "语言", data: "language"},
                    {
                        title: "状态", data: "state",
                        "render": function (data, type, row) {
                            if (data == 1) {
                                return '<span class="badge bg-blue">通过</span>';
                            } else if (data == 0) {
                                return '<span class="badge bg-yellow">审核</span>';
                            } else if (data == -1) {
                                return '<span class="badge bg-red">拒绝</span>';
                            }
                        }
                    },
                    {title: "注册时间", data: "insertTime"},
                    {title: "登录时间", data: "updateTime"},
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return '<@shiro.hasPermission name="OpenUser:openUser:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        }
                    }
                ]
            });

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //新增
            $("#insertBtn").click(function () {
                top.krt.layer.openDialog("新增用户", "${basePath}/kq/openUser/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改用户", "${basePath}/kq/openUser/update?id=" + id, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/kq/openUser/delete",
                        data: {"id": id},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
            });

            //批量删除
            $("#deleteBatchBtn").click(function () {
                var ids = getIds();
                if (ids == "") {
                    krt.layer.error("请选择要删除的数据!");
                    return false;
                } else {
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/kq/openUser/deleteByIds",
                            data: {"ids": ids},
                            success: function (rb) {
                                krt.layer.msg(rb.msg);
                                if (rb.code == 200) {
                                    krt.table.reloadTable(ids);
                                }
                            }
                        });
                    });
                }
            });
        });
    </script>
</@footer>
