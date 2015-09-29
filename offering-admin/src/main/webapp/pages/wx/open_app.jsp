<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String baseUrl = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=baseUrl%>/pages/common/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<title>活动分享</title>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;">
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align: center;font-size: 48px;font-weight: bold;">
				链接打不开？请点击上方选择<font color="green">"在Safari中打开"</font>
			</div>
		</div>
	</div>
</body>
<script src="<%=baseUrl%>/pages/common/media/js/jquery.min.js" type="text/javascript"></script>
<script src="<%=baseUrl%>/pages/common/media/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	//判断访问终端
	var browser={
	    versions:function(){
	        var u = navigator.userAgent, app = navigator.appVersion;
	        return {
	            trident: u.indexOf('Trident') > -1, //IE内核
	            presto: u.indexOf('Presto') > -1, //opera内核
	            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
	            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
	            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
	            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
	            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
	            iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
	            iPad: u.indexOf('iPad') > -1, //是否iPad
	            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
	            weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
	            qq: u.match(/\sQQ/i) == " qq" //是否QQ
	        };
	    }(),
	    language:(navigator.browserLanguage || navigator.language).toLowerCase()
	}
	
	window.location="https://itunes.apple.com/us/app/offering/id1020064463?l=zh&ls=1&mt=8";
	
});

</script> 
</html>