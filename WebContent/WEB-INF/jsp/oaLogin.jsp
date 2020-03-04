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
        	login();
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
    </script>
</head>
<body>
	<form id="thisForm" method="post">
		<input id="userId" name="userId"  type="text" value="${userId }" >
		<input id="userPassword" name="userPassword" value="${userPassword }" >
    </form>
</body>
</html>