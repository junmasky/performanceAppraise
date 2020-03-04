<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!-- ie指定文档模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />


<!-- css -->
<link href="${ctx}/css/main.css?random=<%=Math.random()%>" rel="stylesheet" type="text/css"/>
<link href="${ctx}/css/style.css?random=<%=Math.random()%>" rel="stylesheet" type="text/css"/>
<link href="${ctx}/css/combox.css?random=<%=Math.random()%>" rel="stylesheet" type="text/css"/>



<!-- 工具 -->
<%-- <script type="text/javascript" src="${ctx}/js/UUID.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/Base64.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/matech/mt_utils.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/matech/mt_common.js"></script> --%>

<!-- jquery -->
<%-- <script type="text/javascript" src="${ctx}/jquery/jquery-1.8.3.min.js"></script> 
<script type="text/javascript" src="${ctx}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/easyui-lang-zh_CN.js"></script>--%>

 <!-- 原生EasyUI -->
 <%-- 
 <!-- 引入JQuery -->
 <script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<!-- 引入EasyUI -->
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<!-- 引入EasyUI的中文国际化js，让EasyUI支持中文 -->
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入validatebox扩展的规则 -->
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.4.4/jquery.easyui.validatebox.expand.js"></script>
<!-- 引入EasyUI的样式文件-->
<link rel="stylesheet" href="${ctx}/js/jquery-easyui-1.4.4/themes/default/easyui.css" type="text/css"/>
<!-- 引入EasyUI的图标样式文件-->
<link rel="stylesheet" href="${ctx}/js/jquery-easyui-1.4.4/themes/icon.css" type="text/css"/>
--%>

 <!-- InsdepUI for EasyUI -->
<link rel="stylesheet" href="${ctx}/js/insdep/insdep.easyui.min.css?random=<%=Math.random()%>" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/insdep/icon.css?random=<%=Math.random()%>" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/insdep/iconfont/iconfont.css?random=<%=Math.random()%>" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/insdep/jquery.min.js?random=<%=Math.random()%>"></script>
<script type="text/javascript" src="${ctx}/js/insdep/jquery.easyui.min.js?random=<%=Math.random()%>"></script>
<script type="text/javascript" src="${ctx}/js/insdep/insdep.extend.min.js?random=<%=Math.random()%>"></script>


<!-- jquery -->
<%-- <script type="text/javascript" src="${ctx }/js/jquery/jquery-1.7.2.min.js"></script> --%>
<!-- jquery-MD5 -->
<script type="text/javascript" src="${ctx }/js/jquery/JQuery.md5.js?random=<%=Math.random()%>"></script>

<script type="text/javascript" src="${ctx }/js/md5.js?random=<%=Math.random()%>"></script>



<%-- <link href="${ctx}/upload/uploadify.css" rel="stylesheet" type="text/css" /> --%>
<script type="text/javascript" src="${ctx}/js/my.js?random=<%=Math.random()%>"></script>

<script type="text/javascript" src="${ctx}/js/dateboxExtends.js?random=<%=Math.random()%>"></script>
<script type="text/javascript" src="${ctx}/js/validateboxExtend.js?random=<%=Math.random()%>"></script>
<script type="text/javascript" src="${ctx}/js/initInsdep.js?random=<%=Math.random()%>"></script>

<%--<script type="text/javascript" src="${ctx }/js/jquery/jquery-2.2.4.min.js"></script>--%>