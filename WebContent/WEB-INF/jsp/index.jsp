<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="/WEB-INF/jsp/system/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广州市审计局绩效考核系统</title>

<script language="javascript"
	src="${pageContext.request.contextPath}/js/index.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/menu.css">

<script type="text/javascript">
	$(function() {
		$(window).resize(function() {

		});

	});
</script>
<style type="text/css">
input {
	height: 22px;
	border: 1px solid #CCCCCC;
	padding-top: 3px !important;
	padding-left: 3px !important;
}

#thisul li div:hover {
	padding-top: 5px;
	color: red;
}

.nav ul li div {
	cursor: pointer;
}

.inMsgPic {
	position: absolute;
	right: 10px;
	bottom: -5px;
	width: 42px;
	z-index: 300;
	height: 14px;
	border-top: 0;
	cursor: pointer;
}

p.menutitle_logoo {
	margin: 0px;
	padding-top: 40px;
	padding-left: 0px;
	font-family: "微软雅黑", "黑体", "宋体";
	font-size: 15px;
	height: 0px;
	font-weight: 900;
	position: absolute;
	float: left;
	left: 500px;
	color: black;
}

.treeNodeImg {
	background-image: url(images/im_head.png);
}
</style>


<script type="text/javascript">
	var messages = new Array();		//消息数组
	var msgNum = 0;					//消息数量
	var messagerShow = null;		//消息弹框
	$(function(){
		//生成菜单
		if('${userSession.firstMenu}' != ''){
			var firstMenu = eval('(${userSession.firstMenu})');
			for(var i = 0;i<firstMenu.length;i++){
				var icon = firstMenu[i].ICON
				$("#menuAccordion").accordion('add', {
					title: firstMenu[i].MENUNAME,
					content: "<ul id='tree_"+firstMenu[i].ID+"'></ul>",
					selected: false,
					iconCls:icon
				});
				$("#tree_"+firstMenu[i].ID).tree({
					url:'${ctx}/commonController/getTreeJsonData2',
					lines:false,
					onClick:function(node){
// 						$.get("${ctx }/menuController/getMenuById?r="+Math.random(),{id:node.id},function(data){
// 							if(data != "" && data.indexOf("errmsg") == -1){
// 								var result = eval("("+data+")");
// 							}
// 						});
						openTab(node.id,node.text,node.clickUrl,'01','否')
					},
					onBeforeLoad:function(node,param){
						param.autoId="201807200934";
						param.refer=firstMenu[i].ID;
					}
				});

	
	
			}
			$("#menuAccordion").accordion("select",0);
		}
		
// 		var ws;
// 		if ('WebSocket' in window) {
//             ws = new WebSocket("ws://"+window.location.host+"${ctx}/webSocketServer?userWebPage=${userSession.userId}_index");
//         } else if ('MozWebSocket' in window) {
//             ws = new MozWebSocket("ws://"+window.location.host+"${ctx}/webSocketServer?userWebPage=${userSession.userId}_index");
//         } else {
//             //如果是低版本的浏览器，则用SockJS这个对象，对应了后台“sockjs/webSocketServer”这个注册器，
//             //它就是用来兼容低版本浏览器的
//             ws = new SockJS("http://"+window.location.host+"${ctx}/sockjs/webSocketServer?userWebPage=${userSession.userId}_index");
//         }
//         ws.onopen = function (evnt) {
//         };
        
        
//         //接收到消息
//         ws.onmessage = function (evnt) {
//         	var showType = ["slide","fade","show"];
//         	var msgObj = eval("("+evnt.data+")");
// 			var msg = msgObj.msgContent;
// 			if(msgObj.msgType == "0" && msg != "${userSession.userName}上线了"){
// 				$.messager.show({
// 	    			title:"提示",
// 	    			msg:msg,
// 	    			height:150,
// 	    			timeout:3000,
// 	    			showType:showType[Math.floor(Math.random()*3)]
// 	    		});
// 			}else if(msgObj.msgType == "1"){
// 				if($(".messager-body").length == 0){
// 					messages = new Array();
// 					msgNum = 0;
// 		            messagerShow = $.messager.show({
// 		    			title:"提示",
// 		    			msg:msg,
// 		    			height:150,
// 		    			timeout:0,
// 		    			showType:showType[Math.floor(Math.random()*3)]
// 		    		});
// 		            messages[msgNum++] = msg;
// 				}else{
// 					messages[msgNum++] = msg;
// 					if(messagerShow != null){
// 						messagerShow.window("close");
// 					}
// 					messagerShow = $.messager.show({
// 		    			title:"提示",
// 		    			msg:"<a onclick='showMessages();'>您有"+messages.length+"条消息，请点击<span style='color:red;'>查看</span>显示所有消息</a>",
// 		    			height:150,
// 		    			timeout:0,
// 		    			showType:'show'
// 		    		});
// 				}
// 			}
            
//         };
//         ws.onerror = function (evnt) {
//             console.log(evnt)
//         };
//         ws.onclose = function (evnt) {
//         }
	});
	function showMessages(){
		//获取当前messager的数量,减一是要去掉本messager
		var messagerNum = $(".messager-body").length-1;
		var messagerRight;
		var messagerBottom;
		for(var i = 0;i < messages.length;i++){
// 			$.messager.alert("提示", messages[i]);
			messagerRight = Math.floor(messagerNum/4)*250;
			messagerBottom = -document.body.scrollTop-document.documentElement.scrollTop+(messagerNum%4)*150;
			$.messager.show({
    			title:"提示",
    			msg:messages[i],
    			height:150,
    			timeout:0,
    			showType:'show',
    			style:{
    				left:'',
    				right:messagerRight,
    				top:'',
    				bottom:messagerBottom
    			}
    			
    		});
			messagerNum++;
			if(messagerNum >= 16){
				messagerNum = 0;
			}
		}
		messages = new Array();
		msgNum = 0;
		messagerShow.window("close");
		messagerShow = null;
	}
	function zhuxiao() {
		if (confirm("确定要注销当前登录吗？")) {
			var url = "${ctx }/systemController/exit";
			$.post(url);
			window.location = "${ctx }/systemController/login";

		}
	}
	function openBBS() {
		window.open("http://100.83.24.91:8080/BBS");
	}
	function editPasswd(){
	 	var src = "${ctx}/userController/goEditPasswd?id=${userSession.userId}";
		$("#editIFrame").attr("src",src);
		openWin("修改密码",false);
	}
</script>
</head>

<body id="body" class="easyui-layout">
	<div id="iframe-div"
		style="position: absolute; z-index: 2000; width: 0%; height: 0%; top: 0; left: 0; scrolling: no;"></div>
	<div id="northpanel" region="north" border="false"
		style="height: 70px; background: #B3DFDA; overflow: hidden;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td style="height: 70px;" id="homepageHead"
						onClick="try{hideAllMenu();}catch(e){}">
						<div class="box" id="DIV_TOP" onMouseOver="hideAllMenu1();">
							<!-- 头部bannar  -->
							<table style="width: 100%; border: 0px; cellpadding: 0px;"
								cellspacing="0">
								<tr>

									<td>
										<div class="menu_right" style="background-image: url(${ctx}/images/logo-new.png);">
											<div class="nav">
												<!-- 我的工作台下面几个快捷方式 -->
												<ul>
													<li><a class="" href="#" onclick="zhuxiao();"
														style="margin-right: 10px;"> <img
															src="${ctx}/images/menu/exnt1.png"
															onmousemove="mouseDown7(this);" border=1 width="25"
															height="25" onmouseout="onmouseout7(this);" alt="退出系统"
															title="退出系统" />
													</a></li>
<!-- 													<li><a class="" href="#" onclick="downloadBZHGJ();" -->
<!-- 														style="margin-right: 10px;"> <img -->
<%-- 															src="${ctx}/images/menu/backIndex1.png" --%>
<!-- 															onmousemove="mouseDown1(this);" border=1 width="25" -->
<!-- 															height="25" onmouseout="onmouseout1(this);" alt="标准化工具" -->
<!-- 															title="标准化工具" /> -->
<!-- 													</a></li> -->
<!-- 													<li><a class="" href="#" onclick="openBBS();" -->
<!-- 														style="margin-right: 10px;"> <img -->
<%-- 															src="${ctx}/images/menu/shenpizhongxin1.png" --%>
<!-- 															onmousemove="mouseDown2(this);" border=1 width="25" -->
<!-- 															height="25" onmouseout="onmouseout2(this);" alt="论坛" -->
<!-- 															title="论坛" /> -->
<!-- 													</a></li> -->
<!-- 													<li><a class="" href="#" onclick="editPasswd();" -->
<!-- 														style="margin-right: 10px;"> <img -->
<%-- 															src="${ctx}/images/menu/editUser1.png" --%>
<!-- 															onmousemove="mouseDown0(this);" border=1 width="25" -->
<!-- 															height="25" onmouseout="onmouseout0(this);" alt="修改密码" -->
<!-- 															title="修改密码" /> -->
<!-- 													</a></li> -->
												</ul>
											</div>
										</div>
									</td>
								</tr>
							</table>


						</div>
					</td>

				</tr>

			</tbody>
		</table>

	</div>



	<div id="westPanel" style="width:10%;height:100%;" data-options="region:'west',title:'菜单',split:true,collapsed:true">
		<div id="menuAccordion" class="easyui-accordion" data-options="" style="width:100%;height:100%;border: 0px;">   
		</div>
	</div>
	<div id="centerpanel" region="center" style="border: 0px;height:100%;">
		<div id="mainTab" style="border: 0px;" class="easyui-tabs theme-tab-blue-block" data-options="tabPosition:'top',plain:true,pill:true" fit="true"
			tools="#tab-tools" onmouseover="killallmenu();">
			<div title="待办" id="shouye" style="background-color: #ADD8E6;overflow: hidden;" style="width:90%;">
				<center>
					<iframe style="border: solid 0px #9FB6CD;" id="top_page"
						scrolling="yes" frameborder=0 width="100%;" height="100%" src=""></iframe>
				</center>
			</div>
		</div>
	</div>
	<div id="editWin"  style="">
		<iframe id="editIFrame" src=""  scrollbar = "no" frameborder=0  width="100%" height="100%"></iframe>
	</div>
</body>

<script type="text/javascript">

	window.onload = function() {
// 		$("#top_page").attr("src","${ctx}/systemController/homePage");
		$("#top_page").attr("src","${ctx}/appraiseController/list?userId=${userSession.userId}&appraiseStatus=0");
		
		
	}

	$(document).ready(function() {
		//鼠标变手指
		$("div img").mouseover(function() {
			$(this).css("cursor", "pointer");
		});
	})
	var mainTab = $("#mainTab");

	//打开新页签
	function openTab(id, name, url, menuType, scroll) {
		var n = $("#mainTab").tabs("getTab", name);

		var scrolling = "";

		if (scroll == "是") {
			scrolling = 'scrolling="auto"';
		} else if (scroll == "否") {
			scrolling = 'scrolling="no"';
		} else {
			scrolling = 'scrolling="auto"';
		}

		if (url.indexOf("http://") == -1) {
			url = "${ctx}" + "/" + url;
		}


		if ((!n)) {
			$('#mainTab')
					.tabs(
							'add',
							{
								id : id,
								title : name,
								content : '<iframe id="frame'
										+ id
										+ '" '
										+ scrolling
										+ ' frameborder=0 width="100%" height="100%" src="'
										+ url + '"></iframe>',
								//iconCls:'icon-save',
								closable : true
							});
		} else {
			if (n)
				$("#mainTab").tabs("select", name);
		}

	}
	function tabCloseBySubTitle(name) {
		$("#mainTab").tabs("close", name);
	}

	function mainPageRefer() {
		$("#top_page").attr("src",
				"${pageContext.request.contextPath}/mainPage/main.jsp");

	}

	//返回首页
	function gotoMain() {

		$("#mainTab").tabs("select", "首页");
	}

	function changeUser() {

	}

	function mouseDown0(obj) {
		obj.src = "${ctx}/images/menu/editUser2.png";
	}
	function onmouseout0(obj) {
		obj.src = "${ctx}/images/menu/editUser1.png";
	}
	function mouseDown1(obj) {
		obj.src = "${ctx}/images/menu/backIndex2.png";
	}
	function onmouseout1(obj) {
		obj.src = "${ctx}/images/menu/backIndex1.png";
	}
	function mouseDown2(obj) {
		obj.src = "${ctx}/images/menu/shenpizhongxin2.png";
	}
	function onmouseout2(obj) {
		obj.src = "${ctx}/images/menu/shenpizhongxin1.png";
	}
	function mouseDown3(obj) {
		obj.src = "./images/menu/email2.png";
	}
	function onmouseout3(obj) {
		obj.src = "./images/menu/email1.png";
	}
	function mouseDown4(obj) {
		obj.src = "./images/menu/changing2.png";
	}
	function onmouseout4(obj) {
		obj.src = "./images/menu/changing1.png";
	}
	function mouseDown5(obj) {
		obj.src = "./images/menu/control2.png";
	}
	function onmouseout5(obj) {
		obj.src = "./images/menu/control1.png";
	}
	function mouseDown6(obj) {
		obj.src = "${ctx}/images/menu/help2.png";
	}
	function onmouseout6(obj) {
		obj.src = "${ctx}/images/menu/help1.png";
	}
	function mouseDown7(obj) {
		obj.src = "${ctx}/images/menu/exnt2.png";
	}
	function onmouseout7(obj) {
		obj.src = "${ctx}/images/menu/exnt1.png";
	}
	
	function mouseDownMenu(obj) {
		$(obj).css("background-image","url(${ctx}/images/menu/an.png)");
	}
	function onmouseoutMenu(obj) {
		$(obj).css("background-image","url(${ctx}/images/menu/liang.png)");
	}
	
	function downloadBZHGJ(){
		window.location = "${ctx}/download/bzhgj/DataStand.zip";
	}
	
	var myDate = new Date();
	var mytime = myDate.toLocaleDateString();
	$("#wellcome").text("您好！${userSession.userName } 今天是" + mytime);

</script>


<!-- 底部状态栏 -->
<div id="south">
	<div id="indexBbar"></div>
</div>



</body>

</html>
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="${pageContext.request.contextPath}/css/message.css" /> --%>

</html>
