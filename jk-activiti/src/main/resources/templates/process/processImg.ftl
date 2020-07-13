<#include "/common/layoutList.ftl">
<@header></@header>

<style>
	.iblock {
		display: inline-block;
		height: 100%;
		width: 0;
		vertical-align: middle;
	}
</style>
<@body class="body-bg-default">
	<div class="container-div" style="text-align: center;">
		<img alt="暂无图片" style="vertical-align: middle;">
		<i class="iblock"></i>
	</div>
</@body>
<@footer>
<script th:inline="javascript">
	var prefix = ${basePath}/+ "process";
	var instanceId = ${instanceId};
	$(function() {
		var url =  prefix + '/read-resource?pProcessInstanceId=' + instanceId;
		$('img').attr('src', url);
	});
</script>
</@footer>