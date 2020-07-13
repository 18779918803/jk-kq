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
                                        <input type="text" id="name" name="name" placeholder="姓名"
                                               class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label for="number" class="control-label ">起始日期:</label>
                                        <div class="input-group input-group-addon-right-radius">
                                            <input type="text" class="form-control pull-right" name="startDate"
                                                   id="startDate" readonly
                                                   onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="control-label ">结束日期:</label>
                                        <div class="input-group input-group-addon-right-radius">
                                            <input type="text" class="form-control pull-right" name="endDate"
                                                   id="endDate" readonly
                                                   onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="state" class="control-label ">状态:</label>
                                        <div class="form-group">
                                            <select class="form-control select2" style="width: 100%" id="state" name="state">
                                                <option value="">全部</option>
                                                <option value="0">待值班</option>
                                                <option value="1">已值班</option>
                                                <option value="-1">未值班</option>
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
                                <@shiro.hasPermission name="OndutyRecord:ondutyRecord:insert">
                                    <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip"
                                            class="btn btn-success btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="OndutyRecord:ondutyRecord:delete">
                                    <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                        <i class="fa fa-trash fa-btn"></i>批量删除
                                    </button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="OndutyRecord:ondutyRecord:excelOut">
                                    <@krt.excelOut id="excelOutBtn" url="${basePath}/kq/ondutyRecord/excelOut"></@krt.excelOut>
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
                    url: "${basePath}/kq/ondutyRecord/list",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.organId = organId;
                        d.startDate = $("#startDate").val();
                        d.endDate = $("#endDate").val();
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
                    {title: "值班日期", data: "date"},
                    {title: "状态", data: "state",
                        "render": function(data, type, row){
                            if( data == 1 ){
                                return '<span class="badge bg-blue">已值班</span>';
                            }else if( data == 0 ){
                                return '<span class="badge bg-yellow">待值班</span>';
                            }else if( data == -1 ){
                                return '<span class="badge bg-red">未值班</span>';
                            }
                        }
                    },
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return ' <@shiro.hasPermission name="OndutyRecord:ondutyRecord:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '" rname="'+row.name+'">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="OndutyRecord:ondutyRecord:delete">'
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
                top.krt.layer.openDialog("新增值班记录", "${basePath}/kq/ondutyRecord/insert", "1000px", "500px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                var name = $(this).attr("rname");
                top.krt.layer.openDialog("修改值班记录", "${basePath}/kq/ondutyRecord/update?id=" + id +"&name="+name, "1000px", "500px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/kq/ondutyRecord/delete",
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
                            url: "${basePath}/kq/ondutyRecord/deleteByIds",
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
