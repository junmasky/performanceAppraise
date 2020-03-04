var importPath;
var importData;

//打开窗口
function openImportWin(title,maxFlag,path,data){
	
	importData = data;
	importPath = path;
	
	initImportWin();
	
	$('#fileUpload_winId').dialog({
	    title:title
	});
	$('#fileUpload_winId').dialog("center");
	if(maxFlag){
		$('#fileUpload_winId').dialog("maximize");
	}
	$('#fileUpload_winId').dialog("open");
}


function initImportWin(){
	//添加上传附件弹框div
	$(document.body).append("<div id='fileUpload_winId'></div>");
	
	
	$("#fileUpload_winId").dialog({
	    width:400,
	    height:150,
	    closed: false,
	    closable: true,
	    modal: true,
	    resizable:true,
	    maximizable:true,
	    draggable:true
	});
	$('#fileUpload_winId').dialog("close");
	$("#fileUpload_winId").panel({
		cls:"theme-panel-blue theme-border-radius"
	});
	var htmlStr = "<form id='fileUpload_winId_form'>"
	+"<table style='width: 380px;height: 100%;text-align: center;'>"
	+"<tr><td><input id='fileUpload_winId_file' style='width: 300px;' class='easyui-filebox' type='text'  name='file' multiple></td></tr>"
	+"<tr><td><a id='fileUpload_winId_save' href='#' class='easyui-linkbutton' plain='true' icon='icon-save' onclick='uploadFileImport()'>上传文件</a></td></tr>"
	+"</table>"
	+"</form>";
	$('#fileUpload_winId').html(htmlStr);
	$('#fileUpload_winId_file').filebox({buttonText:"选择文件"});
	$('#fileUpload_winId_save').linkbutton();
	
}



/**
 * 上传附件
 */
function uploadFileImport(){
 	

 		var option = {
				url:importPath,
				data:importData,
				type:'POST',
				dataType:'html',
// 				headers:{"ClientCallMode":"ajax"},
	            success:function(data){
	            	$.messager.progress('close');
	               	closeFileUploadImportWin();
	                	
	                var rslt=JSON.parse(data);
	                $.messager.alert("提示",rslt.desc,'info');

	            },
	            error:function(request){
	                $.messager.alert("提示","调用失败",'info');
	            }
		}
 		$.messager.progress({
 			msg:'请稍等',
 			text:'正在上传...',
 			interval:3000
 		}); 
        $("#fileUpload_winId_form").ajaxSubmit(option);
 	
}


//关闭窗口
function closeFileUploadImportWin(){
	$('#fileUpload_winId').dialog("close");
}
