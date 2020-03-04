<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	$(function(){
	// 	$.post("${ctx}/appraiseController/getAppraising",{exampleId:'1',userId:''},function(data){
	// 		console.info(data);
	// 		var htmlStr = "";
	// 		htmlStr += data[0].EXAMPLE_TITLE
	// 		$('#div1').html(htmlStr);
	// 	});
	// 	$("#thisForm").form({
	// 		onLoadSuccess:function(data){
	// 			$('#div1').html(data);
	// 		}
	// 	});
		
		var selectIndex = ",";
		
		$('#list').datagrid({
			iconCls:'icon-save',
			fit:true,
			nowrap: false,
			striped: true,
			collapsible:true,
			fitColumns:true,
			title:'绩效考核',
			url:'${ctx}/appraiseController/getAppraising',
	// 		sortName: 'COMPLETE_DEADLINE',
	// 		sortOrder: 'asc', 
			singleSelect:false,//是否单选
			pagination:false,
			remoteSort: false,
			idField:'APPRAISE_CONTENT_ID',
	// 		pageSize:50,
			frozenColumns:[[
		        {field:'CK',checkbox:true,hidden:true},
		        {title:'考核实例id',field:'EXAMPLE_ID',width:200,sortable:true,hidden:true},   //,hidden:true
		        {title:'考核内容id',field:'APPRAISE_CONTENT_ID',width:200,sortable:true,hidden:true} 
			]],
			columns:[[
	            {field:'ITEMS',title:'考核项目',width:120,fixed:true},
	            {field:'CONTENT',title:'考核内容',width:150},
	            {field:'REQUIREMENT',title:'要求',width:250},
	            //{field:'STANDARD_SCORE',title:'标准分',width:40,fixed:true},
	           // {field:'DEDUCT_POINTS_ITEMS',title:'扣分事项',width:250},
	            {field:'APPRAISE_SCORE1',title:'自评',width:40,fixed:true},
	            {field:'APPRAISE_SCORE',title:'评分',width:160,fixed:true,
	            	formatter: function(value,row,index){
						var oldScore = "";
						if (value > -1){
							oldScore = value;
						}
						var htmlStr = "<div class='editScoreDiv'><input id='input-"+row.APPRAISE_CONTENT_ID+"' type='text' value='"+oldScore+"' ></div>";
						return htmlStr;
					}
	            }
			]],
			rownumbers:true,
			onBeforeLoad:function(param){
				param.exampleId = "${exampleId}";
				param.userId = "${userId}";
		
			},
			rowStyler:function(index,row){
	// 			if(row.IS_COMPLETE == "是"){
	// 				return "color:green;";
	// 			}
			},
			onLoadSuccess:function(data){
				if(data.rows[0].APPRAISE_TYPE == "自评"){
					$(this).datagrid("hideColumn","APPRAISE_SCORE1");
				}
				var obj = {}
				for(var i = 0;i < data.rows.length;i++){
					if(typeof(obj[data.rows[i].ITEMS]) == "undefined"){
						obj[data.rows[i].ITEMS] = data.rows[i].STANDARD_SCORE;
					}else{
						obj[data.rows[i].ITEMS] = obj[data.rows[i].ITEMS] + data.rows[i].STANDARD_SCORE;
					}
				}
				for(var i = 0;i < data.rows.length;i++){
					var maxValue = 0;
					if(data.rows[i].APPRAISE_ITEMS_TYPE == "考核项目"){
						maxValue = obj[data.rows[i].ITEMS];
					}else{
						maxValue = data.rows[i].STANDARD_SCORE;
					}
					$('#input-'+data.rows[i].APPRAISE_CONTENT_ID).numberbox({    
					    min:0,
					    max:maxValue,
					    onChange:function(newValue,oldValue){
					    	var appraiseContentId =  this.id.split("input-")[1];
					    	saveScore1(appraiseContentId,newValue,oldValue);
					    }
					});
				}
				setTableTitle();
				formatTable();
			},
// 			onDblClickCell: function(index,field,value){
// 				if(field.indexOf("APPRAISE_SCORE") == -1){
// 					return false;
// 				}
// 				var oldScore = value;
// 				var row = $(this).datagrid("getRows")[index];
// 				if(typeof($("#input-"+row.APPRAISE_CONTENT_ID).val()) != "undefined"){
// 					return false;
// 				}
// 				var htmlStr = "<div class='editScoreDiv'><input id='input-"+row.APPRAISE_CONTENT_ID+"' type='text' style='background-color: yellow;' value='"+oldScore+"' ></div>";
// 				var newRow = {};
// 				newRow[field] = htmlStr;
// 				$(this).datagrid('updateRow',{
// 					index: index,
// 					row: newRow
// 				});
// 				formatTable();
// 				$("#input-"+row.APPRAISE_CONTENT_ID).blur(function(){
// 					var newScore = this.value;
// 					var appraiseContentId = row.APPRAISE_CONTENT_ID;
// 					var successSign = saveScore(appraiseContentId,field,newScore,oldScore);
					
// 				});
// 				$("#input-"+row.APPRAISE_CONTENT_ID).focus();
// 			},
			onSelect:function(index, row){
// 				var rows = $(this).datagrid("getSelections");
// 				for(var j = 0;j < rows.length;j++){
// 					selectIndex += $(this).datagrid("getRowIndex",rows[j])+",";
// 				}
				//处室自评、非本处室评分、非分管局领导评分、分管局领导评分、局长评分
				if(row.APPRAISE_ITEMS_TYPE == "考核项目"){
					if("${exampleId}"=="1"){
						if(index >= 0 && index <= 3){
							selectBatch(0,3);
						}else if(index >= 4 && index <= 8){
							selectBatch(4,8);
						}else if(index >= 9 && index <= 15){
							selectBatch(9,15);
						}else if(index >= 17 && index <= 19){
							selectBatch(17,19);
						}
					}else if("${exampleId}"=="2"){
						if(index >= 0 && index <= 3){
							selectBatch(0,3);
						}else if(index >= 4 && index <= 8){
							selectBatch(4,8);
						}else if(index >= 9 && index <= 14){
							selectBatch(9,14);
						}else if(index >= 16 && index <= 18){
							selectBatch(16,18);
						}
					}
				}
			},
			onUnselect:function(index, row){
				if(row.APPRAISE_ITEMS_TYPE == "考核项目"){
					if("${exampleId}"=="1"){
						if(index >= 0 && index <= 3){
							unselectBatch(0,3);
						}else if(index >= 4 && index <= 8){
							unselectBatch(4,8);
						}else if(index >= 9 && index <= 15){
							unselectBatch(9,15);
						}else if(index >= 17 && index <= 19){
							unselectBatch(17,19);
						}
					}else if("${exampleId}"=="2"){
						if(index >= 0 && index <= 3){
							unselectBatch(0,3);
						}else if(index >= 4 && index <= 8){
							unselectBatch(4,8);
						}else if(index >= 9 && index <= 14){
							unselectBatch(9,14);
						}else if(index >= 16 && index <= 18){
							unselectBatch(16,18);
						}
					}
				}
			},
			toolbar:'#bar'
		});
	
		$("div").css("border","0px");
		
		
	});
	
	function selectBatch(indexStart,indexEnd){
		var selectIndex = ",";
		var rows = $('#list').datagrid("getSelections");
		for(var j = 0;j < rows.length;j++){
			selectIndex += $('#list').datagrid("getRowIndex",rows[j])+",";
		}
		for(var i = indexStart;i <= indexEnd;i++){
			if(selectIndex.indexOf(","+i+",") == -1){
				$('#list').datagrid("checkRow",i);
			}
		}
	}
	function unselectBatch(indexStart,indexEnd){
		var selectIndex = ",";
		var rows = $('#list').datagrid("getSelections");
		for(var j = 0;j < rows.length;j++){
			selectIndex += $('#list').datagrid("getRowIndex",rows[j])+",";
		}
		for(var i = indexStart;i <= indexEnd;i++){
			if(selectIndex.indexOf(","+i+",") > -1){
				$('#list').datagrid("uncheckRow",i);
			}
		}
	}

</script>
</head>
<body>
	<body class="easyui-layout"  style="border:none;">
	<div region="center" style="background-color: none; border:none;" fit="true" >
			<div id="list" style="border:none;"></div>
	</div>
	<div id="bar" style="text-align: center;" >
		<a id="a_save" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true,disabled:true" onclick="submit();">提交</a>
		<a id="a_back" href="#" class="easyui-linkbutton" plain="true" icon="icon-cancel" onclick="back()">关闭</a>
	</div>
</body>
</body>

<script type="text/javascript">
	//评分统计数据
	var scoreStatistics;
	//考核实例标题
	var exampleTitle;
	//考核用户类型
	var appraiseType;
	
	//判断是否显示提交考核按钮
	function isShowSubmit(){
// 		$("#a_save").hide();
		if(appraiseComplete() && "${appraiseStatus}" != "1"){
			$('#a_save').linkbutton('enable');
// 			$("#a_save").show();
		}
	}
	//判断考核是否完成，完成返回true，未完成返回false
	function appraiseComplete(){
		var completeSign = false;
		for(var i = 0;i < scoreStatistics.length;i++){
			if(scoreStatistics[i].SCORED_NUM != scoreStatistics[i].SCORE_NUM){
				completeSign = false;
				break;
			}else{
				completeSign = true;
			}
		}
		return completeSign;
	}
	//提交考核
	function submit(){
		//考核完成后不能提交考核
		if("${appraiseStatus}" == "1"){
			$.messager.confirm('确认对话框', "考核已完成，请刷新待办页面", function(r){
    			if (r){
    			    //关闭页面;
// 	        		window.parent.tabCloseBySubTitle(exampleTitle+"-"+appraiseType+"-"+scoreStatistics[0].USER_NAME);
	        		back();
    			}
    		});
			return false;
		}else{
			//判断考核是否完成
			if(!appraiseComplete()){
				$.messager.alert("提示", "评分未完成");
				return false;
			}
		}
		var param = {
				exampleId:"${exampleId}",
				userId:"${userId}",
				appraiseStatus:1
		};
		$.ajax({
			cache:true,
			type:"POST",
	        url: '${ctx}/appraiseController/saveAppraiseUser',
	        data:param,
	        async: true,
	        beforeSend:function(request){
	        	$.messager.progress({
	        		msg:'请稍等',
	        		text:'正在保存...',
	        		interval:3000
	        	});
	        },
	        complete:function(request){
	        	$.messager.progress('close');
	        },
	        error: function(request) {
	            alert("调用失败");
	        },
	        success: function(data) {
	        	var rsltarr = eval(data);
	        	if(rsltarr.code >= 1){
	        		$.messager.confirm('确认对话框', rsltarr.message+"，请刷新待办页面", function(r){
	        			if (r){
	        			    //关闭页面;
// 	        				window.parent.tabCloseBySubTitle(exampleTitle+"-"+appraiseType+"-"+scoreStatistics[0].USER_NAME);
	        				back();
	        			}
	        		});
				}else{
					$.messager.alert("提示", rsltarr.message);	
				}
	        }
	    });
	}
	
	function setTableTitle(){
		var rows = $('#list').datagrid("getRows");
		exampleTitle = rows[0].EXAMPLE_TITLE;
		appraiseType = rows[0].APPRAISE_TYPE;
		$.get("${ctx}/appraiseController/getScoreStatistics",{exampleId:"${exampleId}",userId:"${userId}"},function(data){
			var rsltarr = eval(data);
        	if(rsltarr.code >= 1){
        		scoreStatistics = rsltarr.data.scoreStatistics;
        		var title = "评分情况：";
        		var sum = 0;
        		for(var i = 0;i < scoreStatistics.length;i++){
        			title += scoreStatistics[i].ITEMS+"（"+scoreStatistics[i].SCORED_NUM+"/"+scoreStatistics[i].SCORE_NUM+"，合计："+scoreStatistics[i].SCORE_SUM+"分）";
	        		sum += scoreStatistics[i].SCORE_SUM;
        		}
        		title = title+"  总分："+sum+"分";
			}else{
				title = rows[0].EXAMPLE_TITLE+"-"+rows[0].APPRAISE_TYPE;
			}
        	$('#list').datagrid("getPanel").panel("setTitle",title);
        	//判断是否显示提交考核按钮
        	isShowSubmit()
			
		});
// 		$('#list').datagrid("getPanel").panel("setTitle",rows[0].EXAMPLE_TITLE+"-"+rows[0].APPRAISE_TYPE);
	}
	//临时写死
	function formatTable(){
		$('#list').datagrid("mergeCells",{index:0,field:'ITEMS',rowspan:4});
		$('#list').datagrid("mergeCells",{index:4,field:'ITEMS',rowspan:5});
		if("${exampleId}"=="1"){
			$('#list').datagrid("mergeCells",{index:9,field:'ITEMS',rowspan:7});
// 			$('#list').datagrid("mergeCells",{index:16,field:'ITEMS',rowspan:2});
			$('#list').datagrid("mergeCells",{index:17,field:'ITEMS',rowspan:3});
		}else if("${exampleId}"=="2"){
			$('#list').datagrid("mergeCells",{index:9,field:'ITEMS',rowspan:6});
// 			$('#list').datagrid("mergeCells",{index:15,field:'ITEMS',rowspan:2});
			$('#list').datagrid("mergeCells",{index:16,field:'ITEMS',rowspan:3});
		}
		
		//评分列合并
		var rows = $('#list').datagrid("getRows");
		if(rows[0].APPRAISE_ITEMS_TYPE == "考核项目"){
			$('#list').datagrid("mergeCells",{index:0,field:'APPRAISE_SCORE',rowspan:4});
			$('#list').datagrid("mergeCells",{index:4,field:'APPRAISE_SCORE',rowspan:5});
			
			$('#list').datagrid("mergeCells",{index:0,field:'APPRAISE_SCORE1',rowspan:4});
			$('#list').datagrid("mergeCells",{index:4,field:'APPRAISE_SCORE1',rowspan:5});
			if("${exampleId}"=="1"){
				$('#list').datagrid("mergeCells",{index:9,field:'APPRAISE_SCORE',rowspan:7});
				$('#list').datagrid("mergeCells",{index:17,field:'APPRAISE_SCORE',rowspan:3});
				
				$('#list').datagrid("mergeCells",{index:9,field:'APPRAISE_SCORE1',rowspan:7});
				$('#list').datagrid("mergeCells",{index:17,field:'APPRAISE_SCORE1',rowspan:3});
			}else if("${exampleId}"=="2"){
				$('#list').datagrid("mergeCells",{index:9,field:'APPRAISE_SCORE',rowspan:6});
				$('#list').datagrid("mergeCells",{index:16,field:'APPRAISE_SCORE',rowspan:3});
				
				$('#list').datagrid("mergeCells",{index:9,field:'APPRAISE_SCORE1',rowspan:6});
				$('#list').datagrid("mergeCells",{index:16,field:'APPRAISE_SCORE1',rowspan:3});
			}
		}
	}

	function saveScore(appraiseContentId,field,newScore,oldScore){
		var index = $("#list").datagrid('getRowIndex',appraiseContentId);
	//		var data = $("#list").datagrid('getData');
		//考核完成后不能修改保存评分
		if("${appraiseStatus}" == "1"){
			$.messager.alert("提示", "考核已完成，不能修改评分");	
			return false;
		}
		if(newScore == oldScore){
			var newRow = {};
			newRow[field] = oldScore;
	 		$("#list").datagrid('updateRow',{
	 			index: index,
	 			row: newRow
	 		});
	 		formatTable();
	 		return false;
		}
		var param = {
				exampleId:"${exampleId}",
				userId:"${userId}",
				appraiseContentId:appraiseContentId,
				appraiseScore:newScore
		};
		$.ajax({
			cache:true,
			type:"POST",
	        url: '${ctx}/appraiseController/saveAppraiseScore',
	        data:param,
	        async: true,
	        beforeSend:function(request){
	        	$.messager.progress({
	        		msg:'请稍等',
	        		text:'正在保存...',
	        		interval:3000
	        	});
	        },
	        complete:function(request){
	        	$.messager.progress('close');
	        },
	        error: function(request) {
	            alert("调用失败");
	        },
	        success: function(data) {
	        	var rsltarr = eval(data);
	        	if(rsltarr.code >= 1){
	         		var newRow = {};
					newRow[field] = newScore;
	         		$("#list").datagrid('updateRow',{
	         			index: index,
	         			row: newRow
	         		});
	         		formatTable();
	         		setTableTitle();
				}else{
					$.messager.alert("提示", rsltarr.message);	
				}
	        }
	    });
	
	}
	
	function saveScore1(appraiseContentId,newScore,oldScore){
		var index = $("#list").datagrid('getRowIndex',appraiseContentId);
		//考核完成后不能修改保存评分
		if("${appraiseStatus}" == "1"){
			$.messager.alert("提示", "考核已完成，不能修改评分");	
			return false;
		}
		if(newScore == oldScore){
	 		formatTable();
	 		return false;
		}
		var param = {
				exampleId:"${exampleId}",
				userId:"${userId}",
				appraiseContentId:appraiseContentId,
				appraiseScore:newScore
		};
		$.ajax({
			cache:true,
			type:"POST",
	        url: '${ctx}/appraiseController/saveAppraiseScore',
	        data:param,
	        async: true,
	        beforeSend:function(request){
	        	$.messager.progress({
	        		msg:'请稍等',
	        		text:'正在保存...',
	        		interval:3000
	        	});
	        },
	        complete:function(request){
	        	$.messager.progress('close');
	        },
	        error: function(request) {
	            alert("调用失败");
	        },
	        success: function(data) {
	        	var rsltarr = eval(data);
	        	if(rsltarr.code >= 1){
	         		formatTable();
	         		setTableTitle();
				}else{
					$.messager.alert("提示", rsltarr.message);	
				}
	        }
	    });
	
	}
	
	function back(){
// 		parent.reloadList();
		window.history.back();
	}

</script>
</html>