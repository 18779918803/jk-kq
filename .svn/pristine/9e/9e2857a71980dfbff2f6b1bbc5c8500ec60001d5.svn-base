<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <#if todoItemList??>
                    <div class="row animated fadeInRight">
                        <div class="ibox float-e-margins">
                            <div id="ibox-content">
                                <div id="vertical-timeline" class="vertical-container light-timeline">
                         <#list todoItemList as todoItem>
                                    <div  class="vertical-timeline-block">
                                        <div class="vertical-timeline-icon navy-bg">
                                            <i class="fa fa-file-text"></i>
                                        </div>
                                        <div class="vertical-timeline-content">
                                            <h2>【[[${ todoItem.nodeName }]]】[[${ todoItem.itemName }]]</h2>
                                            <p style="word-break: break-word;">[[${ todoItem.itemContent }]]
                                            </p>
                                            <!--<a href="javascript:void(0)" class="btn btn-sm btn-warning" th:onclick="selectUser([[${todoItem.taskId}]])" style="margin-left: 5px;"> 委托</a>-->
                                            <a href="javascript:void(0)" class="btn btn-sm btn-primary"
                                               th:onclick="showVerifyDialog([[${todoItem.taskId}]], [[${todoItem.module}]], [[${todoItem.taskName}]], [[${todoItem.nodeName}]])">
                                                办理</a>
                                            <span class="vertical-date">
                         	<!--今天--><span class="todoTimeSpan"
                                           th:text="${dates.format(todoItem.todoTime, 'yyyy-MM-dd HH:mm:ss')}"></span> <br>
                                      <small th:text="${dates.format(todoItem.todoTime, 'yyyy-MM-dd HH:mm:ss')}"></small>
                                     </span>
                                        </div>
                                    </div>
                            </#list>
                                </div>
                            </div>
                        </div>
                    </div>

            <#else >
                <div style="text-align: center; height: 600px; line-height: 600px;">暂无任务</div>
            </#if>



        </section>
    </div>
</@body>
<@footer>


<script type="text/javascript">


    var prefix = "${basePath}/todoitem";
    $(document).ready(function () {
        transDate();
    });

    function showVerifyDialog(taskId, module, pageName, nodeName) {
        var url = ${basePath} +module + "/showVerifyDialog/" + taskId;
        top.krt.layer.openDialog(nodeName, url, "1000px", "500px");
        // $.modal.open(nodeName, url);
        // $.modal.open(nodeName, url);
    }

    function transDate() {
        var $times = document.getElementsByClassName("todoTimeSpan");
        if ($times && $times.length > 0) {
            for (var i = 0; i < $times.length; i++) {
                $time = $times[i];
                var date = $time.innerHTML.trim();
                date = new Date(date);
                var tt = date;
                var days = parseInt((new Date().getTime() - date) / 86400000);
                var today = new Date().getDate();
                var year = tt.getFullYear();
                var mouth = tt.getMonth() + 1;
                var day = tt.getDate();
                var time = tt.getHours() < 10 ? "0" + tt.getHours() : tt.getHours();
                var min = tt.getMinutes() < 10 ? "0" + tt.getMinutes() : tt.getMinutes();
                var result, offset;
                offset = Math.abs(today - day);
                if (days < 4 && offset < 4) {
                    if (offset === 0) {
                        result = "今天" + time + ":" + min;
                    } else if (offset === 1) {
                        result = "昨天" + time + ":" + min;
                    } else if (offset === 2) {
                        result = "前天" + time + ":" + min;
                    } else if (offset === 3) {
                        result = "3天前" + time + ":" + min;
                    }
                } else {
                    // result = year + "-" + mouth + "-" + day + " " + time + ":" + min;
                    result = "3天以前";
                }
                $time.innerHTML = result;
            }
        }
    }

</script>

