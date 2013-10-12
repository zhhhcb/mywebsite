<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/index.css" rel="stylesheet" type="text/css">
    

  </head>
  
  <body>
      <div class="navbar navbar-inverse  navbar-static-top" style="width:1400px;display:block;float:none;">
          <div class="navbar-inner">
          <div class="center">
          
          <ul class="nav nav-pills">
              <li>
                <a href="#">首页</i></a>
              </li>
              <li>
                <a href="javascript:alert('逗比，还没做好啊')">视频</i></a>
              </li>
              <li>
                <a href="#">图片</a>
              </li>
              <li><a href="#">音乐</i></a></li>
              <li><a href="#">美文</i></a></li>
          </ul>
          <!-- search -->
 
          <!-- login and regiest -->
          <ul class="nav nav-pills">
              <li id="user">
                  <a id="login" type="submit"style="display:block" href="#" >登录</a>
              </li>
              <li>
                  <a id="regist" href="#" style="display:block">注册</a>
              </li>
          </ul>
          </div>
            </div>
        </div>
      
     <script src="/resources/js/jquery-2.0.2.js" type="text/javascript"></script>
     <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
  </body>
</html>
