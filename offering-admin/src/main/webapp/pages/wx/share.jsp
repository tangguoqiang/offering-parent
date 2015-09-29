<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String baseUrl = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=0.3, maximum-scale=0.3, minimum-scale=0.3, user-scalable=no" />
<link href="<%=baseUrl%>/pages/common/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=baseUrl%>/pages/common/media/css/share.css" rel="stylesheet" type="text/css"/>
<title>活动分享</title>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;">
	<div class="container-fluid">
		<div class="row">
			<div id="mainDiv" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="padding-bottom: 205px;">
				<input id="activityId" value="${activityId}" hidden="true">
				<div class="top">
				</div> 
				<div class="navbar-fixed-bottom" style="background:#545158;opacity:1;height:198px">
					<img src="../images/icon.png" style="float:left;margin-left:30px;margin-top:31px;margin-right:48px"/>
					<div style="float:left;margin-top:25px">
						<div style="font-size:60px;color:#FFFFFF">Offering</div>
						<div style="font-size:48px;color:#FFFFFF">没有难拿的offer</div>
					</div>
					<!-- <img id="viewBtn" src="../images/icon_check_6p_nor.png" style="float:right;margin-right:10px;margin-top:31px"/> -->
					<div id="viewBtn" style="float:right;margin-right:30px;margin-top:31px;background-color:#ffcb19;border-radius:15px;height:136px;width:360px;color:#FFFFFF;font-size:52px;line-height:136px;text-align:center;font-weight:bold" >立即下载</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="iosModal">
	  	<div class="row" style="margin-top: 85px;">
	  		<div class="col-md-offset-8 col-sm-4 col-xs-6 col-md-4">
	  			<img src="../images/arrow.png">
	  		</div>
	  	</div>
	  	<div class="row" style="margin-top: 85px;">
	  		<div class="col-sm-12 col-xs-18 col-md-12">
	  			<p style="font-size: 48px;text-align:center;color:#FFFFFF;font-weight: bolder;">点击右上角，选择在浏览器中打开，等你哦！</p>
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
	
	if(browser.versions.ios && !browser.versions.weixin && !browser.versions.qq)
	{
		window.location="https://itunes.apple.com/us/app/offering/id1020064463?l=zh&ls=1&mt=8";
	}
	
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/app/getActivityInfo",
        data:{
        	id:$("#activityId").val()
        },
        dataType:"json",
        success : function(data){
        	//$("#activity_image").css("background-image","url(../"+data.data.url+")");
        	$(".top").css("background-image","url(../"+data.data.url+")");
        	var speakers = data.data.speakers;
        	for(var i=0,len=speakers.length;i<len;i++)
        	{
        		$("#mainDiv").append("<div class='content img-circle img-responsive' style='background-image:url(.."+speakers[i].url+")'></div>");
        		$("#mainDiv").append("<div style='text-align:center;font-size:66px;color:#121212;margin-top:54px;font-weight:bold;'>"+speakers[i].name+"</div>");
        		$("#mainDiv").append("<div style='text-align:center;font-size:42px;color:#121212;margin:54px 50px;'>"+speakers[i].remark+"</div>");
        	}
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
	
	$("#viewBtn").bind("click",function(){
		if(browser.versions.android)
		{
			window.location ="http://fir.im/v6km";
			return;
		}else if(browser.versions.weixin || browser.versions.qq)
		{
			$("#iosModal").modal("show");
		}else if(browser.versions.ios)
		{
			window.location ="https://itunes.apple.com/us/app/offering/id1020064463?l=zh&ls=1&mt=8";
			return;
		}
	});
	
});

</script> 
</html>