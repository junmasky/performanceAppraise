$(function(){
	
	$('#editWin').dialog({
	    width:800,
	    height:450,
	    closed: false,
	    closable: true,
	    modal: true,
	    resizable:true,
	    maximizable:true,
	    draggable:true
	});
	$('#editWin').dialog("close");
	$("#editWin").panel({
		cls:"theme-panel-blue theme-border-radius"
	});
	
})
//打开窗口
function openWin(title,maxFlag){
	$('#editWin').dialog({
	    title:title,
	    width:800,
	    height:450
	});
	$('#editWin').dialog("center");
	if(maxFlag){
		$('#editWin').dialog("maximize");
	}
	$('#editWin').dialog("open");
}
//关闭窗口
function closeWin(listId,rowId){
	$("#"+listId).datagrid("reload");
	$("#"+listId).datagrid("selectRecord",rowId);
	$('#editWin').dialog("close");
}