<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css">
    
    <script src="../js/jquery-2.0.2.js" type="text/javascript"></script>
    <script src="../js/bootstrap.js" type="text/javascript"></script>
      <div class="navbar navbar-inverse  navbar-static-top" style="width:1400px;display:block;float:none;" 
      >
        <div class="center">
          <div class="navbar-inner">
          <ul class="nav nav-pills">
              <li>
                <a href="welcome.php"><i class="icon-home">&nbsp;首页</i></a>
              </li>
              <li>
                <a href="video.php"><i class="icon-film">&nbsp;视频</i></a>
              </li>
              <li>
                <a href="picture.php"><i class="icon-picture">&nbsp;图片</i></a>
              </li>
              <li><a href="music.php"><i class="icon-music">&nbsp;音乐</i></a></li>
              <li><a href="article.php"><i class=" icon-book">&nbsp;美文</i></a></li>
          </ul>
          <!-- search -->
          <ul class="nav nav-pills">
                       <li>
                          <form class="form-search" action="search.php" method="POST">
                              <div class="input-append" style="height:15px">
                                <input type="text" style="width:200px;"name="search" class="span2 search-query" placeholder="搜索...">
                              <button type="submit" class="btn"><i class="icon-search"></i></button>
                             </div>
                          </form>
                      </li>
          </ul>
          <!-- login and regiest -->
          <ul class="nav nav-pills">
              <li id="loginform">
                <form class="navbar-search pull-right">
                  <div class="input-prepend">
                      <span class="add-on"><i class="icon-user"></i></span>
                      <input type="text" class="span2"id="name" placeholder="用户名" style="height:20px;width:130px;">]
                  </div>
                  <div class="input-prepend">
                      <span class="add-on"><i class="icon-lock"></i></span>
                      <input type="password" class="span2" id="pass" placeholder="密码" style="height:20px;width:130px;" onkeyup="if (event.keyCode == 13) login();">
                  </div>

                </form>
                 <span id="error"></span>
              </li>
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
                <?php
                    if(!isset($_SESSION)){
                      session_start();
                    }
                    if(isset($_SESSION['nickname'])){
                      if ($_SESSION['nickname']!="")
                      {
                        $username = $_SESSION['username'];
                        $nickname = $_SESSION['nickname'];
                        $url = "userinfo.php?user=".$username;
                        $html_ = "<a href=".$url."><i class=icon-user>&nbsp;".$nickname."</i></a>";
                        echo "<script type='text/javascript'>$('#login').css('display','none');$('#user').html('".$html_."');$('#loginform').html(''); $('#regist').css('display','none');$('#loginout').show();</script>";
                      }
                    }
                  ?>

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
