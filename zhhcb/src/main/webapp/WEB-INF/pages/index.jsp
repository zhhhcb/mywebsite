<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
      <div class="navbar navbar-inverse  navbar-static-top" style="width:1400px;display:block;float:none;" 
      >
        <div class="center">
          <div class="navbar-inner">
          <ul class="nav nav-pills">
              <li>
                <a href="welcome.php">首页</i></a>
              </li>
              <li>
                <a href="video.php">视频</i></a>
              </li>
              <li>
                <a href="picture.php">图片</a>
              </li>
              <li><a href="music.php">音乐</i></a></li>
              <li><a href="article.php">美文</i></a></li>
          </ul>
          <!-- search -->
 
          <!-- login and regiest -->
          <ul class="nav nav-pills">
          
              <li id="user">
                  <a id="login" type="submit"style="display:block" href="javascript:login();" >登录</a>
              </li>
              <li>
                  <a>|</a>
              </li>
              <li>
                  <a id="regist" href="regist.php" style="display:block">注册</a>
              </li>
              <li>
                <a id="loginout" style="display:none" href="loginout.php">退出</a>
              </li>
        </ul>
          </div>
            </div>
        </div>
        <br><br><br>
                
     <script src="/resources/js/jquery-2.0.2.js" type="text/javascript"></script>
     <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
  </body>
</html>
