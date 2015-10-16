<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String baseUrl = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<link href="<%=baseUrl%>/pages/common/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<title>活动分享</title>
<style type="text/css">
.title{
	font-size:2em;
	font-weight:bold;
	line-height:1.5em;
}
</style>
</head>
<body style="font-size: 1em;overflow: hidden;background-color: #F2F3EE;">
	<div class="container-fluid" style="padding: 0px 0px 0px 0px;overflow: hidden;">
		<div class="row">
			<div class="col-xs-12" style="padding-left: 0px;padding-right: 0px;">
				<input id="activityId" value="${activityId}" hidden="true">
				<img id="activityImg" class="img-responsive" style="width: 100%;" alt="" src="">
			</div>
		</div>
		<div class="row" style="border: thin;background-color: #FFFFFF;">
			<div style="margin-left:15px;margin-top:2%;" class="col-xs-12" >
				<p class="title"></p>
			</div>
		</div>
		
		<!-- 活动状态非已结束时显示 start -->
		<div id="speakerDiv_wjs" class="container" style="display:none;margin-left: 3%;margin-right: 3%;margin-top: 5%;border-radius:15px;background-color: #FFFFFF;">
			<div  class="row" >
				<div style='width: 12.6%;margin-left:4%;float: left;margin-top: -3.5%;' >
					<img id="speakerImg_wjs" style='width: 100%;' class='img-circle' alt="">
				</div>
				<div style='margin-left:3%;float: left;margin-top: 2.6%;' >
					<p id="speakerName_wjs" style='font-size:1.5em;font-weight:bold;'></p>
				</div>
				<div style='margin-left:1.6%;float: left;margin-top: 3%;' >
					<p id="post_wjs" style='font-size:1.2em;color:#c5c5c5'></p>
				</div>
				<div style='margin-right:32px;float: right;margin-top: 3%;' >
					<p style='font-size:1.5em;font-weight:bold;color: #FFCD19;'>分享嘉宾</p>
				</div>
			</div>
			<div class="row" style="margin-left: 10%;margin-right: 7%;">
				<div class="col-xs-4" style="margin-right: 0px;width: 36%;">
					<hr color="#000000" style="height:1px;width: 100%;">
				</div>
				<div class="col-xs-4" style="padding-right: 0px;padding-left: 0px;width: auto">
					<p style='font-size:1.5em;font-weight:bold;text-align: center;margin-top: 10px;'>嘉宾介绍</p>
				</div>
				<div class="col-xs-3" style="margin-left: 0px;width: 36%;">
					<hr color="#000000" style="height:1px;width: 100%;">
				</div>
			</div>
			<div class="row" style="margin-left: 7%;margin-right: 7%;" >
				<div class="col-xs-12">
					<p id="speaker_remark" style='font-size:1.5em;margin-top: 10px;text-align: center;'></p>
				</div>
			</div>
		</div>
		<div id="activityDiv_wjs" class="container" style="display:none;margin-left: 3%;margin-right: 3%;margin-top: 1.4%;border-radius:15px;background-color: #FFFFFF;">
			<div class="row" style="margin-top: 1.6%;">
				<div style="margin-left: 5.3%;float: left;">
					<p style='font-size:1.6em;font-weight:bold;'>活动简介</p>
				</div>
				<div style="margin-right: 3.9%;float: right;">
					<p id="time" style='font-size:1.5em;color: #ffcb19;'></p>
				</div>
				<div style="margin-right: 3.2%;float: right;">
					<p id="date" style='font-size:1.5em;color: #ffcb19;'></p>
				</div>
				<div style="margin-right: 2.6%;float: right;">
					<img class="img-responsive" alt="" src="../images/share/1.png">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 5.8%;float: left;height: auto;width: 8%;" >
					<img class="img-responsive" alt="" src="../images/share/2.png">
				</div>
				<div style="margin-left: 3.5%;float: left;margin-right:4.7%; width: 75%; ">
					<p id="activity_remark" style='font-size:1.5em;'></p>
				</div>
			</div>
			<div class="row" style="margin-bottom: 2%;">
				<div style="margin-left: 5.8%;float: left;height: auto;width: 8%;" >
					<img class="img-responsive" alt="" src="../images/share/3.png">
				</div>
				<div style="margin-left: 3.5%;float: left;">
					<p id="type" style='font-size:1.5em;font-weight:bold;'></p>
				</div>
			</div>
		</div>
		<div id="memberDiv_wjs" class="container" style="display:none;margin-left: 3%;margin-right: 3%;margin-top: 1.4%;border-radius:15px;background-color: #FFFFFF;margin-bottom: 20%;">
			<div class="row" style="margin-top: 1.6%;">
				<div style="margin-left: 5.3%;float: left;">
					<p style='font-size:1.6em;font-weight:bold;'>小伙伴们</p>
				</div>
				<div style="margin-right: 3.9%;float: right;">
					<p id="joinMembers" style='font-size:1.5em;color: #ffcb19;'></p>
				</div>
			</div>
			<div id="members" class="row" style="margin-bottom: 2%;">
			</div>
		</div>
		<!-- end -->
		<!-- 活动状态为已结束时显示 start -->
		<div id="speakerDiv" class="row" style="display:none; background-color: #FFFFFF;">
			<div style='width: 10.3%;margin-left:30px;float: left;' >
				<img id="speakerImg" style='width: 100%;' class='img-circle' alt="">
			</div>
			<div style='margin-left:3%;float: left;margin-top: 2.6%;' >
				<p id="speakerName" style='font-size:1.5em;font-weight:bold;'></p>
			</div>
			<div style='margin-left:1.6%;float: left;margin-top: 3%;' >
				<p id="post" style='font-size:1.2em;color:#c5c5c5;'></p>
			</div>
			<div style='margin-right:32px;float: right;margin-top: 3%;' >
				<p style='font-size:1.5em;font-weight:bold;color: #FFCD19;'>分享嘉宾</p>
			</div>
		</div>
		<iframe id="content" style="display:none;width: 100%;overflow: auto;padding-right: 0px;border:none;margin-bottom: 20%;">
		</iframe>
		<!-- end -->
		<div class="navbar-fixed-bottom col-xs-12" style="background:#545158;opacity:1;height:60px;padding: 0px 0px 0px 0px;">
				<img class="img-responsive" src="../images/icon.png" style="float:left;margin:2% 3% 4% 3%;height: 41px;"/>
				<div style="float:left;margin-top:2%;">
					<div style="font-size:1.8em;color:#FFFFFF">Offering</div>
					<div style="font-size:1.2em;color:#FFFFFF;">没有难拿的offer</div>
				</div>
				<div id="viewBtn" style="text-align:center;font-size:2em;font-weight:bold;line-height:2em;height:41px;width:35%;float:right;margin-right:3%;margin-top:2%;background-color:#ffcb19;border-radius:15px;color:#FFFFFF;" >
					立即下载
				</div>
		</div>
	</div>
	
	<div class="modal fade" id="iosModal">
	  	<div class="col-xs-12">
	  		<img class="img-responsive" src="../images/arrow.png">
	  	</div>
	</div>
</body>
<script src="<%=baseUrl%>/pages/common/media/js/jquery.min.js" type="text/javascript"></script>
<%-- <script src="<%=baseUrl%>/pages/common/media/js/bootstrap.min.js" type="text/javascript"></script> --%>
<script src="<%=baseUrl%>/pages/common/media/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
var version = '<%=GloabConstant.APP_SERVICE_VERSION%>';
var ACTIVITY_STATUS_JS = '<%=GloabConstant.ACTIVITY_STATUS_JS%>';
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
        url:'<%=baseUrl%>'+"/app/v" + version + "/getActivityInfo",
        data:{
        	id:$("#activityId").val()
        },
        dataType:"json",
        success : function(data){
        	$("#activityImg").attr("src",'<%=baseUrl%>' + data.data.url);
        	$(".title").text(data.data.title);
        	var speaker = data.data.speaker;
        	
        	if(data.data.status == ACTIVITY_STATUS_JS)
        	{
        		$("#speakerDiv").show();
        		$("#content").show();
        		$("#speakerImg").attr("src",'<%=baseUrl%>'+speaker.url);
            	$("#speakerName").text(speaker.name);
            	$("#post").text(speaker.company+speaker.post);
            	$("#content").attr("src",'<%=baseUrl%>/app/v1/activitySummary?id=' + $("#activityId").val());
        	}else{
        		$("#speakerDiv_wjs").show();
        		$("#activityDiv_wjs").show();
        		$("#memberDiv_wjs").show();
        		$("#speakerImg_wjs").attr("src",'<%=baseUrl%>'+speaker.url);
            	$("#speakerName_wjs").text(speaker.name);
            	$("#post_wjs").text(speaker.company+speaker.post);
            	$("#speaker_remark").html(speaker.remark.replace(/\n/g,"<br>"));
            	$("#activity_remark").html(data.data.remark.replace(/\n/g,"<br>"));
            	
            	var startTime = new Date();
            	startTime.setTime(parseInt(data.data.startTime));
            	var endTime = new Date();
            	endTime.setTime(parseInt(data.data.endTime));
            	$("#date").text(startTime.format("MM-dd"));
            	$("#time").text(startTime.format("hh:mm")+"-"+endTime.format("hh:mm"));
            	
            	var type = data.data.type;
            	if(type=='0' || type=='1')
            		$("#type").text("分享方式：线上分享会");
            	else
            		$("#type").text("地点："+data.data.address);
            	
            	$("#joinMembers").html("<font color='#565656'>已参与</font>"+data.data.joinMembers+"<font color='#565656'>人</font>");
            	
            	var members = data.data.members;
            	if(members != null && members.length > 0)
            	{
            		for(var i=0,len=members.length;i<len;i++)
            		{
            			if(i == 10)
            				break;
            			$("#members").append("<div style='float:left;margin-left:2.5%;width:7%'><img class='img-responsive img-circle' alt='' src='<%=baseUrl%>"+ members[i].url + "'></div>");
            		}
            	}
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