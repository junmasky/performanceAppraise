<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/system/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/easyuiExtends.js"></script>
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
		url:'${ctx}/appraiseController/getStatisticsDetailsGridJson',
// 		sortName: 'COMPLETE_DEADLINE',
// 		sortOrder: 'asc', 
		singleSelect:true,//是否单选
		pagination:true,
		remoteSort: false,
// 		idField:'EXAMPLE_ID',
		pageSize:50,
		frozenColumns:[[
	        {field:'CK',checkbox:true},
	        {field:'EXAMPLE_ID',title:'考核实例id',width:200,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'EXAMPLE_TITLE',title:'考核标题',width:200,sortable:true},
            {field:'APPRAISE_TYPE',title:'考核类型',width:200,sortable:true},
            {field:'WEIGHTS',title:'权重',width:200,sortable:true},
            {field:'USER_NUM',title:'总人数',width:200,sortable:true},
            {field:'USER_COMPLETE_NUM',title:'完成人数',width:200,sortable:true},
            {field:'SCORE_AVG',title:'平均分',width:200,sortable:true},
            {field:'WEIGHTS_SCORE',title:'权重分数',width:200,sortable:true},
            {field:'WEIGHTS_SCORE_SUM',title:'总权重分数',width:200,sortable:true}
//             {field:'WEIGHTS_SCORE',title:'分数',width:200,sortable:true,
//     			formatter: function(value,row,index){
//     				if(row.APPRAISE_STATUS_NUM == 0){
//     					return value;
//     				} else {
// //     					return "进行中";
//     					return value;
//     				}
//     			}
//             }
		]],
		rownumbers:true,
		onBeforeLoad:function(param){
			param.exampleId = "${exampleId}";
	
		},
		rowStyler:function(index,row){
		},
		onDblClickRow:function(rowIndex, rowData){
			goList();
		},
		onSelect:function(index, row){
		},
		onLoadSuccess:function(data){
			$(this).datagrid("mergeCells",{index:0,field:'EXAMPLE_TITLE',rowspan:data.rows.length});
			$(this).datagrid("mergeCells",{index:0,field:'WEIGHTS_SCORE_SUM',rowspan:data.rows.length});
		},
		toolbar:'#bar'
	});

	$("div").css("border","0px");
})

</script>
</head>

<body class="easyui-layout"  style="border:none;">
	<div region="center" style="background-color: none; border:none;" fit="true">
			<div id="list" style="border:none;"></div>
	</div>
	<div id="bar">

<!-- 		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a> -->
<!-- 		<a id="edit" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">绩效考核</a> -->
<!-- 		<a id="remove" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="remove();">删除</a> -->
	</div>
	<div id="editWin" data-options="" style="">
		<iframe id="editIFrame" src=""  scrollbar = "no" frameborder=0  width="100%" height="100%"></iframe>
	</div>
</body>

<script type="text/javascript">
	function goList(){
		var exampleId;
		var appraiseType;
		var title;
	 	var rows = $('#list').datagrid("getSelections");
	 	if(rows.length==1){
	 		exampleId = rows[0].EXAMPLE_ID;
	 		appraiseType = rows[0].APPRAISE_TYPE;
	 		title = rows[0].EXAMPLE_TITLE+"详细信息";
	 	}else{
			$.messager.alert("提示","请选择一条记录！");
			return false;
	 	}
	 	
	 	var src = "${ctx}/appraiseController/list?readonly=readonly&exampleId="+exampleId+"&appraiseType="+encodeURI(appraiseType);
		$("#editIFrame").attr("src",src);
		openWin(title,false);
// 		window.parent.openTab("LIST-"+exampleId,title,src,'01','否');
	}

</script>
</html>