<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/system/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/easyuiExtends.js"></script>
<!-- 附件上传相关js -->
<script type="text/javascript" src="${ctx }/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/fileImport.js"></script>
<style type="text/css">
.easyui-linkbutton{
/* 	border: 0px solid; */
	
}
</style>
<script type="text/javascript">
$(function(){

	$('#list').datagrid({
		iconCls:'icon-save',
		fit:true,
		nowrap: true,
		striped: true,
		collapsible:true,
		fitColumns:true,
		url:'${ctx}/appraiseController/getAppraiseUserGridJson',
// 		sortName: 'COMPLETE_DEADLINE',
// 		sortOrder: 'asc', 
		singleSelect:true,//是否单选
		pagination:true,
		remoteSort: false,
// 		idField:'id',
		pageSize:50,
		frozenColumns:[[
	        {field:'CK',checkbox:true},
// 	        {title:'用户名',field:'userName',width:200,sortable:true}   //,hidden:true
	        {field:'EXAMPLE_ID',title:'考核实例id',width:200,sortable:true,hidden:true},
            {field:'USER_ID',title:'用户id',width:200,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'EXAMPLE_TITLE',title:'考核标题',width:200,sortable:true},
            {field:'USER_NAME',title:'用户',width:200,sortable:true},
            {field:'APPRAISE_TYPE',title:'类型',width:200,sortable:true},
            {field:'APPRAISE_STATUS',title:'状态',width:200,sortable:true,
    			formatter: function(value,row,index){
    				if(value == 1){
    					return "已完成";
    				} else {
    					return "进行中";
    				}
    			}
            }
		]],
		rownumbers:true,
		onBeforeLoad:function(param){
			param.userId = "${userId}";
			param.appraiseStatus = "${appraiseStatus}";
			param.exampleId = "${exampleId}";
			param.appraiseType = "${appraiseType}";
	
		},
		rowStyler:function(index,row){
			if(row.APPRAISE_STATUS == 1){
				return "color:green;";
			}else{
				return "color:blue;";
			}
		},
		onDblClickRow:function(rowIndex, rowData){
			edit();
		},
		onSelect:function(index, row){
		},
		toolbar:'#bar'
	});

	$("div").css("border","0px");
	if("${readonly}" == "readonly"){
		$("#edit").hide();
		$("#gostatistics").hide();
	}
	$("#gostatistics").hide();
	if("${userSession.userId}" == "maj" || "${userSession.userId}" == "jiangj"){
		$("#gostatistics").show();
	}
})

</script>
</head>

<body class="easyui-layout"  style="border:none;">
	<div region="center" style="background-color: none; border:none;" fit="true">
			<div id="list" style="border:none;"></div>
	</div>
	<div id="bar">

<!-- 		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a> -->
		<a id="edit" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">绩效考核</a>
		<a id="gostatistics" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart',plain:true" onclick="gostatistics();">统计结果</a>
<!-- 		<a id="remove" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="remove();">删除</a> -->

	</div>
	<div id="editWin" data-options="" style="">
		<iframe id="editIFrame" src=""  scrollbar = "no" frameborder=0  width="100%" height="100%"></iframe>
	</div>
</body>

<script type="text/javascript">


//修改
function edit(){
	var exampleId;
	var userId;
	var title;
 	var rows = $('#list').datagrid("getSelections");
 	if(rows.length==1){
 		exampleId = rows[0].EXAMPLE_ID;
 		userId = rows[0].USER_ID;
 		title = rows[0].EXAMPLE_TITLE+"-"+rows[0].APPRAISE_TYPE+"-"+rows[0].USER_NAME;
 	}else{
		$.messager.alert("提示","请选择一条要修改的记录！");
		return false;
 	}
 	
 	var src = "${ctx}/appraiseController/appraising?exampleId="+exampleId+"&userId="+userId;
// 	$("#editIFrame").attr("src",src);
// 	openWin("修改用户信息",false);
// 	window.parent.openTab(exampleId+"_"+userId,title,src,'01','否');
	window.location.href = src;
}

function gostatistics(){
 	
 	var src = "appraiseController/statistics";
// 	$("#editIFrame").attr("src",src);
// 	openWin("修改用户信息",false);
	window.parent.openTab("statistics","考核统计",src,'01','否');
}

// openTab(node.id,node.text,node.clickUrl,'01','否')

function reloadList(){
	$("#list").datagrid("reload");
// 	$("#list").datagrid("selectRecord",rowId);
}
</script>
</html>