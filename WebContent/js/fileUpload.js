//控件生成所需要的参数
//url的绝对路径
var fileUpload_ctx = "";
//附件控件div的id
var fileUpload_fileDivId = "";
//文件列表显示div的id
var fileUpload_listId = "";
//文件上传弹框div的id
var fileUpload_winId = "";

//保存到数据库所需要的参数
//文件关联id
var fileUpload_tableId = "";
//文件类型（用户附件,离职用户附件等）
var fileUpload_attachmentType = "";
$(function(){
	
	
})

/**
 * 初始化上传附件控件div
 * @param ctx			url的绝对路径
 * @param fileDivId		附件控件div的id
 * @param winDivId		上传弹框div的id
 * @param tableId		附件关联id
 * @param attachmentType附件关联类型
 */
function initFileUploadDiv(ctx,fileDivId,tableId,attachmentType){
	fileUpload_ctx = ctx;
	fileUpload_fileDivId = fileDivId;
	fileUpload_listId = fileUpload_fileDivId+'_fileList';
	fileUpload_winId = fileUpload_fileDivId+'_fileWin'
	fileUpload_tableId = tableId;
	fileUpload_attachmentType = attachmentType;
	
	//添加打开上传附件弹框的按钮和附件列表div
	var divHtmlStr = "<a id='"+fileUpload_fileDivId+"_openWin' href='#' class='easyui-linkbutton' plain='true' icon='icon-up' onclick='openFileUploadWin(\"文件上传\",false)'>上传文件</a>"
					+"<div id='"+fileUpload_listId+"' style='text-align: right;'></div>";
	
	$('#'+fileUpload_fileDivId).html(divHtmlStr);
	$('#'+fileUpload_fileDivId+'_openWin').linkbutton();
	$('#'+fileUpload_fileDivId).css("text-align","right");
	
	//添加上传附件弹框div
	$(document.body).append("<div id='"+fileUpload_winId+"'></div>");
	
	//初始化上传附件弹框
	initFileUploadWin();
	//初始化附件列表
	initFileUploadList();
}

function initFileListDiv(ctx,fileDivId,tableId,attachmentType) {
    fileUpload_ctx = ctx;
    fileUpload_fileDivId = fileDivId;
    fileUpload_listId = fileUpload_fileDivId+'_fileList';
    fileUpload_winId = fileUpload_fileDivId+'_fileWin'
    fileUpload_tableId = tableId;
    fileUpload_attachmentType = attachmentType;

    //添加打开上传附件弹框的按钮和附件列表div
    var divHtmlStr = "<div id='"+fileUpload_listId+"' style='text-align: right;'></div>";

    $('#'+fileUpload_fileDivId).html(divHtmlStr);
    $('#'+fileUpload_fileDivId+'_openWin').linkbutton();
    $('#'+fileUpload_fileDivId).css("text-align","right");



    //初始化附件列表
    $.post(fileUpload_ctx+"/attachmentController/getFilesById",{tableid:fileUpload_tableId,attachmentType:fileUpload_attachmentType},function(data){
        if(data != "" && typeof(data) != "undefined"){
            var attachments = eval('('+data+')');
            var htmlStr = "";
            htmlStr = "<ul>";
            for(var i = 0;i < attachments.length;i++){
                htmlStr += "<li>"
                    +"<a title='下载:"+attachments[i].OLDNAME+"' href='javascript:void(0)'  onclick='downloadAttach(\""+attachments[i].NEWNAME+"\");' style='margin-right: 10px;' >"+attachments[i].OLDNAME+"</a>"
                    +"<a title='下载:"+attachments[i].OLDNAME+"' onclick='downloadAttach(\""+attachments[i].NEWNAME+"\");'><img src='"+fileUpload_ctx+"/images/download.gif'/></a>"
                    +"</li>";

            }
            htmlStr += "</ul>";
            $("#"+fileUpload_listId).html(htmlStr);
        }
    })
}

/**
 * 初始化上传附件弹框div
 */
function initFileUploadWin(){
	$('#'+fileUpload_winId).dialog({
	    width:400,
	    height:150,
	    closed: false,
	    closable: true,
	    modal: true,
	    resizable:true,
	    maximizable:true,
	    draggable:true
	});
	$('#'+fileUpload_winId).dialog("close");
	$("#"+fileUpload_winId).panel({
		cls:"theme-panel-blue theme-border-radius"
	});
	var htmlStr = "<form id='"+fileUpload_winId+"_form'>"
	+"<table style='width: 380px;height: 100%;text-align: center;'>"
	+"<tr><td><input id='"+fileUpload_winId+"_file' style='width: 300px;' class='easyui-filebox' type='text'  name='file' multiple></td></tr>"
	+"<tr><td><a id='"+fileUpload_winId+"_save' href='#' class='easyui-linkbutton' plain='true' icon='icon-save' onclick='saveAttach()'>保存文件</a></td></tr>"
	+"</table>"
	+"</form>";
	$('#'+fileUpload_winId).html(htmlStr);
	$('#'+fileUpload_winId+'_file').filebox({buttonText:"选择文件"});
	$('#'+fileUpload_winId+'_save').linkbutton();
}

//打开窗口
function openFileUploadWin(title,maxFlag){
	$('#'+fileUpload_winId).dialog({
	    title:title
	});
	$('#'+fileUpload_winId).dialog("center");
	if(maxFlag){
		$('#'+fileUpload_winId).dialog("maximize");
	}
	$('#'+fileUpload_winId).dialog("open");
}
//关闭窗口
function closeFileUploadWin(){
	$('#'+fileUpload_winId).dialog("close");
}

/**
 * 初始化附件列表
 */
function initFileUploadList(){
	$.post(fileUpload_ctx+"/attachmentController/getFilesById",{tableid:fileUpload_tableId,attachmentType:fileUpload_attachmentType},function(data){
		if(data != "" && typeof(data) != "undefined"){
			var attachments = eval('('+data+')');
			var htmlStr = "";
			htmlStr = "<ul>";
			for(var i = 0;i < attachments.length;i++){
				htmlStr += "<li>"
					+"<a title='下载:"+attachments[i].OLDNAME+"' href='javascript:void(0)'  onclick='downloadAttach(\""+attachments[i].NEWNAME+"\");' style='margin-right: 10px;' >"+attachments[i].OLDNAME+"</a>"
					+"<a title='下载:"+attachments[i].OLDNAME+"' onclick='downloadAttach(\""+attachments[i].NEWNAME+"\");'><img src='"+fileUpload_ctx+"/images/download.gif'/></a>"
					+"<a title='删除' onclick='removeAttach(\""+attachments[i].NEWNAME+"\");'><img src='"+fileUpload_ctx+"/images/delete.gif'/></a>"
					+"</li>";
				
			}
			htmlStr += "</ul>";
			$("#"+fileUpload_listId).html(htmlStr);
		}
	})
}

/**
 * 上传附件
 */
function saveAttach(){
 	if(fileUpload_tableId != "" && typeof(fileUpload_tableId) != "undefined"){
 		var option = {
				url:fileUpload_ctx+'/attachmentController/save',
				data:{id:fileUpload_tableId,attachmentType:fileUpload_attachmentType},
				type:'POST',
				dataType:'html',
// 				headers:{"ClientCallMode":"ajax"},
	            success:function(data){
	                var rsltarr=eval("("+data+")");
	                if (rsltarr.id == "1") {
	                	$.messager.alert("提示", rsltarr.text);
                    	initFileUploadList();
                    	$.messager.progress('close');
                    	closeFileUploadWin();
	                } else {
	                    $.messager.alert("提示", rsltarr.text);
	                    initFileUploadList();
	                    $.messager.progress('close');
	                }
	            },
	            error:function(request){
	                alert("调用失败");
	            }
		}
 		$.messager.progress({
 			msg:'请稍等',
 			text:'正在上传...',
 			interval:3000
 		}); 
        $("#"+fileUpload_winId+"_form").ajaxSubmit(option);
 	}else{
 		$.messager.alert("提示", "请先保存信息");
 	}
}

/**
 * 下载附件
 * @param id 附件id
 */
function downloadAttach(key){
 	if(key != ""){
	 	var src = fileUpload_ctx+"/attachmentController/download?key="+key;
	 	window.location.href = src;
 	}
}

/**
 * 删除附件
 * @param id 附件id
 */
function removeAttach(key){
	if (key != "") {
		$.messager.alert("提示","是否确定删除该文件","info",function(){
			$.ajax({
				cache:true,
				type:"POST",
		        url: fileUpload_ctx+'/attachmentController/delete',
		        data:{key:key},
		        async: false,
		        error: function(request) {
		            alert("调用失败");
		        },
		        success: function(data) {
		        	var rsltarr=eval("("+data+")");
		        	if(rsltarr.id == 1){
		        		initFileUploadList();
					}else{
						$.messager.alert("提示", rsltarr.text);
						initFileUploadList();
					}
		        }
		    });
        });
	}else{
		$.messager.alert("提示", "请选择要删除的记录！");
	}
}