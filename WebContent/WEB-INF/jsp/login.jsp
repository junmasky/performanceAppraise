<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css"/>
	<title>广州市审计局绩效考核系统</title>
    <script type="text/javascript">
	    if (top.location != self.location) {
			top.location.href = self.location.href;
		}
        $(function(){
            $("#userPassword").textbox({
            	events:{
            		//得到焦点
            		focus:function(){
                        $("#left_hand").animate({
                            left: "150",
                            top: " -38"
                        },{step: function(){
                            if(parseInt($("#left_hand").css("left"))>140){
                                $("#left_hand").attr("class","left_hand");
                            }
                        }}, 2000);
                        $("#right_hand").animate({
                            right: "-64",
                            top: "-38px"
                        },{step: function(){
                            if(parseInt($("#right_hand").css("right"))> -70){
                                $("#right_hand").attr("class","right_hand");
                            }
                        }}, 2000);
                    },
                    //失去焦点
                    blur:function(){
                        $("#left_hand").attr("class","initial_left_hand");
                        $("#left_hand").attr("style","left:100px;top:-12px;");
                        $("#right_hand").attr("class","initial_right_hand");
                        $("#right_hand").attr("style","right:-112px;top:-12px");
                    }
            	}
            });
            if("${errMsg}" != ""){
            	$("#errMsgDiv").css("display","");
            }
        });
		//登录
		function login() {
// 			if($("#userPassword").val()==""){
// 				$("#errmsg").text("密码不能为空");
// 				return false;
// 			}
// 			if($("#certCode").val()==""){
// 				$("#errmsg").text("验证码不能为空");
// 				return false;
// 			}
		
// 			$("#thisForm").submit(); 
			
			$.ajax({
				cache:true,
				type:"POST",
		        url: '${ctx}/systemController/loginValidate',
		        data:$('#thisForm').serialize(),
		        async: true,
		        error: function(request) {
		            alert("调用失败");
		        },
		        success: function(data) {
		        	if(data.code == 200){
		        		window.location.href = "${ctx}/systemController/index";
					}else{
						$("#errmsg").html(data.data.errMsg);
						if(data.data.errMsg != ""){
			            	$("#errMsgDiv").css("display","");
			            }
					}
		        }
		    });
		
		}
		function enter(){
			if(event.keyCode==13){
				login();
			}
		}
    </script>
</head>
<body>
	<form id="thisForm" action="${ctx }/systemController/login" method="post" onkeydown="enter();">
		<div class="top_div"></div>
		<div style="width: 400px;height: 200px;margin: auto auto;background: #ffffff;text-align: center;margin-top: -100px;border: 1px solid #e7e7e7">
		    <div style="width: 165px;height: 96px;position: absolute">
		        <div class="tou"></div>
		        <div id="left_hand" class="initial_left_hand"></div>
		        <div id="right_hand" class="initial_right_hand"></div>
		    </div>
		
		    <p style="padding: 30px 0px 10px 0px;position: relative;">
		        <input name="userId" class="easyui-textbox" type="text" data-options="iconCls:'icon-man',iconAlign:'left',prompt:'请输入用户名',width:'290'" >
		    </p>
		    <p style="position: relative;">
		        <input id="userPassword" name="userPassword" class="easyui-textbox" data-options="type:'password',iconCls:'icon-lock',iconAlign:'left',prompt:'',width:'290'" >
		    </p>
			<div id="errMsgDiv" style="display: none;" ><font size=2 color=red id="errmsg">&nbsp;${errMsg}</font></div>
		    <div style="height: 50px;line-height: 50px;margin-top: 30px;border-top: 1px solid #e7e7e7;">
		        <p style="margin: 0px 35px 20px 45px;">
		           <span style="float: right">
		               <a href="#" style="background: #008de1;padding: 7px 10px;border-radius: 4px;border: 1px solid #1a7598;color: #FFF;font-weight: bold;" onclick="login();">登录</a>
		           </span>
		        </p>
		    </div>
		
		</div>
		
		<div style="position: fixed;bottom: 0px;text-align: center;width: 100%;">
		<!--     Copyright ©2015 <a style="margin-left: 10px;color: #000000;text-decoration: underline" href="http://www.sevennight.cn">http://www.sevennight.cn</a> -->
		</div>
    </form>
</body>
</html>