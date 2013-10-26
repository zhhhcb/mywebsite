<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-ch" class="no-js">
  <head>
    <base href="<%=basePath%>"> 
    
    <title>首页</title>
    <meta charset="UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="/resources/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/demo.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/component2.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/crumbs.css" />
	<script src="/resources/js/modernizr-2.6.2.min.js"></script>
	
	
  </head>
  
  <body>
  	<!-- <div id="crumbs">
  		<ul>
  			<li><a href="#">主页</a></li>
  			<li><a href="#">图片</a></li>
  			<li><a href="#">音乐</a></li>
  			<li><a href="#">博客</a></li>
  			<li><a href="#">联系我们</a></li>
  		</ul>
  	</div> -->
    <div class="container">
			<!-- Top Navigation -->
			<div class="codrops-top clearfix">
				<a class="codrops-icon codrops-icon-prev" href="http://tympanus.net/Development/CreativeLinkEffects/"><span>Previous Demo</span></a>
				<span class="right"><a class="codrops-icon codrops-icon-drop" href="http://tympanus.net/codrops/?p=16114"><span>Back to the Codrops Article</span></a></span>
			</div>
			<header>
				<h1>Circular Navigation <span>Building a Circular Navigation with CSS Transforms</span></h1>	
				<nav class="codrops-demos">
					<a href="index.html">Demo 1</a>
					<a class="current-demo" href="index2.html">Demo 2</a>
					<a href="interactivedemo/index.html">Intractive demo</a>
				</nav>
			</header>
			<div class="component">
				<h2>Celery swiss chard melon</h2>
				<!-- Start Nav Structure -->
				<button class="cn-button" id="cn-button">Menu</button>
				<div class="cn-wrapper" id="cn-wrapper">
					<ul>
						<li><a href="#"><span>About</span></a></li>
						<li><a href="#"><span>Tutorials</span></a></li>
						<li><a href="#"><span>Articles</span></a></li>
						<li><a href="#"><span>Snippets</span></a></li>
						<li><a href="#"><span>Plugins</span></a></li>
						<li><a href="#"><span>Contact</span></a></li>
						<li><a href="#"><span>Follow</span></a></li>
					 </ul>
				</div>
				<!-- End of Nav Structure -->
			</div>
			<section>
				<p>Soko leek tomatillo quandong winter purslane caulie jícama daikon dandelion bush tomato. Daikon cress amaranth leek cabbage black-eyed pea kakadu plum scallion watercress garbanzo gram caulie welsh onion water spinach tomatillo groundnut desert raisin. Wakame salsify bunya nuts spring onion lotus root prairie turnip fennel onion dandelion black-eyed pea bok choy zucchini taro. Jícama collard greens amaranth bell pepper catsear brussels sprout sweet pepper daikon spring onion aubergine broccoli rabe quandong mustard celery corn groundnut peanut. Mung bean fennel eggplant water spinach bunya nuts sierra leone bologi epazote okra caulie groundnut black-eyed pea parsnip fava bean squash.</p>
				<p>Parsnip tomatillo swiss chard garbanzo gourd potato silver beet. Celery swiss chard melon zucchini arugula pea quandong beet greens radish artichoke black-eyed pea endive winter purslane horseradish garlic amaranth collard greens chickpea. Rock melon pumpkin collard greens celery broccoli rabe endive nori brussels sprout gourd courgette sea lettuce artichoke desert raisin coriander chard.</p>
				<p>Collard greens ricebean horseradish wattle seed chard epazote potato peanut gram earthnut pea spinach yarrow desert raisin salad mung bean summer purslane fennel. Water spinach celery cucumber grape cauliflower nori daikon sweet pepper endive lentil turnip greens catsear leek beet greens. Melon seakale parsnip soybean bamboo shoot fennel scallion. Coriander groundnut squash corn aubergine bitterleaf azuki bean dandelion courgette broccoli rabe. Chickweed salsify chickweed groundnut nori okra lentil water spinach rock melon broccoli. Soko leek tomatillo quandong winter purslane caulie jícama daikon dandelion bush tomato. Daikon cress amaranth leek cabbage black-eyed pea kakadu plum scallion watercress garbanzo gram caulie welsh onion water spinach tomatillo groundnut desert raisin. Wakame salsify bunya nuts spring onion lotus root prairie turnip fennel onion dandelion black-eyed pea bok choy zucchini taro. Jícama collard greens amaranth bell pepper catsear brussels sprout sweet pepper daikon spring onion aubergine broccoli rabe quandong mustard celery corn groundnut peanut. Mung bean fennel eggplant water spinach bunya nuts sierra leone bologi epazote okra caulie groundnut black-eyed pea parsnip fava bean squash.</p>
				<p>Parsnip tomatillo swiss chard garbanzo gourd potato silver beet. Celery swiss chard melon zucchini arugula pea quandong beet greens radish artichoke black-eyed pea endive winter purslane horseradish garlic amaranth collard greens chickpea. Rock melon pumpkin collard greens celery broccoli rabe endive nori brussels sprout gourd courgette sea lettuce artichoke desert raisin coriander chard.</p>
				<p>Collard greens ricebean horseradish wattle seed chard epazote potato peanut gram earthnut pea spinach yarrow desert raisin salad mung bean summer purslane fennel. Water spinach celery cucumber grape cauliflower nori daikon sweet pepper endive lentil turnip greens catsear leek beet greens. Melon seakale parsnip soybean bamboo shoot fennel scallion. Coriander groundnut squash corn aubergine bitterleaf azuki bean dandelion courgette broccoli rabe. Chickweed salsify chickweed groundnut nori okra lentil water spinach rock melon broccoli.</p>
			</section>
		</div><!-- /container -->
		<script src="/resources/js/polyfills.js"></script>
		<script src="/resources/js/demo2.js"></script>
  </body>
</html>
