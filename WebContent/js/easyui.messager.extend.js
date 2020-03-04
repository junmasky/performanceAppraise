/**
 * 弹出的加载层遮罩
 * @param msg 显示的信息
 * @param obj 遮罩对象的id、class或者document.body
 */
function loadMsg(msg,obj){
	if(msg == "" || typeof(msg) == undefined){
		msg = "正在处理中，请稍待。。。"
	}
	$("<div class=\"datagrid-mask\"></div>").css({
		display:"block",
		width:"100%",
		height:$(obj).height()		//$(window).height() 当前可见区域的大小
	}).appendTo(obj);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(obj).css({
		display:"block",
		left:($(obj).outerWidth(true) - 190) / 2,
		top:($(obj).height() - 45) / 2,		////$(window).height() 当前可见区域的大小
	});
}

/**
 * 取消加载层遮罩
 */
function disLoadMsg(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}