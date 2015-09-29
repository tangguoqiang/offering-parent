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
<title>群分享</title>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;">
	<div class="container-fluid">
		<div class="row">
			<div id="memberDiv" style="margin-top:10px;">
				<input id="groupId" value="${groupId}" hidden="true">
			</div>

			<div id="showAll" style="margin-left:auto;margin-right:auto;margin-top:60px;font-size:42px;color:#6c6c6c;width:400px;cursor:pointer">
				<img src="../images/icon_6p.png" style="margin-right:15px"></img>点击查看全部成员
			</div>

			<div class="list-group" style="margin-top:60px;margin-bottom: 205px;">
				<div class="list-group-item group">
					<span style="float:left;font-size:48px;color:#121212">群名称</span> 
					<span id="groupName" style="float:right;font-size:42px;color:#6c6c6c"></span>
				</div>
				<div class="list-group-item group">
					<span style="float:left;font-size:48px;color:#121212">创建日期</span> 
					<span id="createTime" style="float:right;font-size:42px;color:#6c6c6c"></span>
				</div>
				<div class="list-group-item group">
					<span style="float:left;font-size:48px;color:#121212">群介绍</span> 
					<div id="groupInfo"  style="float:right;font-size:42px;color:#6c6c6c;word-wrap: break-word;width:500px;overflow:hidden;margin-top:-30px;text-align:right;margin-bottom: 10px;"></div>
				</div>
			</div>
			<div style="height:198px"></div>
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
	var tmpMembers=[];
	Date.prototype.format = function(fmt)   
    { //author: meizz   
      var o = {   
        "M+" : this.getMonth()+1,                 //月份   
        "d+" : this.getDate(),                    //日   
        "h+" : this.getHours(),                   //小时   
        "m+" : this.getMinutes(),                 //分   
        "s+" : this.getSeconds(),                 //秒   
        "q+" : Math.floor((this.getMonth()+3)/3), //季度   
        "S"  : this.getMilliseconds()             //毫秒   
      };   
      if(/(y+)/.test(fmt))   
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
      for(var k in o)   
        if(new RegExp("("+ k +")").test(fmt))   
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
      return fmt;   
    }; 
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/activity/listMembers",
        data:{
        	groupId:$("#groupId").val()
        },
        dataType:"json",
        success : function(data){
        	$("#groupName").text(data.data.groupName);
        	var d=new Date();
        	d.setTime(parseInt(data.data.createTime));
        	$("#createTime").text(d.format("yyyy-MM-dd"));
        	$("#groupInfo").text(data.data.groupInfo);
        	
        	if(typeof data.data.members == "undefined")
        	{
        		$("#showAll").hide();
        		return;
        	}
        		
        	var members = data.data.members;
        	var len =members.length;
        	if(len <=8)
        	{
        		$("#showAll").hide();
        		for(var i=0;i<len;i++){
            		member = members[i];
            		$("#memberDiv").append("<div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"+
            				"<div class='member img-circle' style='background-image: url(.."+member.url+")'></div>"
        					+"<div class='member-info'>"+member.nickname+"</div></div>");
            	}
        	}else{
        		for(var i=0;i<8;i++){
            		member = members[i];
            		$("#memberDiv").append("<div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"+
            				"<div class='member img-circle' style='background-image: url(.."+member.url+")'></div>"
        					+"<div class='member-info'>"+member.nickname+"</div></div>");
            	}
        		
        		for(var i=8;i<len;i++)
        		{
        			tmpMembers.push(members[i]);
        		}
        	}
        		
        	$("#memberDiv").append("<div style='clear:both' id='splitLine'></div>");
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
	
	$("#showAll").bind("click",function(){
		for(var i=0,len=tmpMembers.length;i<len;i++){
    		member = tmpMembers[i];
    		$("#memberDiv").append("<div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"+
    				"<div class='member img-circle' style='background-image: url(.."+member.url+")'></div>"
					+"<div class='member-info'>"+member.nickname+"</div></div>");
    	}
		if(tmpMembers.length!=0)
		{
			var n = 4 - tmpMembers.length%4;
			for(var i=0;i<n;i++)
			{
				$("#memberDiv").append("<div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'><div class='member img-circle'></div><div class='member-info'></div></div>");
	    	}
		}
		
		
		tmpMembers=[];
		//$("#showAll").hide();
	});
	
	$("#viewBtn").bind("click",function(){
		if(browser.versions.android)
		{
			window.location ="http://fir.im/v6km";
			return;
		}else if(browser.versions.weixin || browser.versions.qq )
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