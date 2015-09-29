<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String baseUrl = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Offering</title>
	<script src="js/jquery.min.js" type="text/javascript"></script>
	<link href="css/login.css" rel="stylesheet" type="text/css"/>
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<form id="loginForm" style="visibility: false;" method="post"></form>
	<div class="login-main">
	 	<div class="login-tit">Offering</div>
	 	<form action="" class="login form-horizontal">
		  <div class="form-group">
		    <label for="userName" class="col-sm-3 control-label" style="font-size:1.5em;padding-top: 15px;">用户名:</label>
		    <div class="col-sm-7">
		      <input class="form-control" id="userName">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="password" class="col-sm-3 control-label" style="font-size:1.5em;padding-top: 15px;">密&nbsp&nbsp&nbsp&nbsp码:</label>
		    <div class="col-sm-7">
		      <input type="password" class="form-control" id="password">
		    </div>
		  </div>
		  <div class="form-group">
			  <div class="col-sm-offset-3 col-sm-10">
			  	<div id="error-message" class="error-message"></div>
			  </div>
		  </div>
		  <div class="form-group">
			  <div class="col-sm-offset-5 col-sm-10">
			  	<button type="button" class="btn btn-primary"  style="font-size:1.6em;width: 120px;" id="butt">登录</button>
			  </div>
		  </div>
		</form>
	</div>
<script type="text/javascript">
$(document).ready(function (){
	$("#userName").val("");
	$("#password").val("");
	/**
	 * 登陆表单提交函数;
	 * */
	 $("#butt").click(function(){//点击登录按钮
		 login();
	 });
	
	 $(window).keydown(function (e) { 
		 if(e.which == 13) {
			 login();
		 }
	 });
});

function login() {
	$("#error-message").css("display","none");
	var userName = $("#userName").val().trim();
	if(userName == "")
	{
		$("#error-message").css({"display":"inline"});
		$("#error-message").html("用户名不能为空！");
		return;
	}
	$.ajax({
		async:false,
		type:'post',
		url:"<%=baseUrl%>"+'/login',
		data:{
			username:userName,
			password:$("#password").val()
		},
		dataType:'json',
		success:function(data){
			if(data.success){
				$("#loginForm").action="<%=baseUrl%>/";
				$("#loginForm").submit();
			}else{
				$("#error-message").css({"display":"inline"});
				$("#error-message").html(data.msg);
			}
		},
		error:function(textStatus,errorThrown){
		}
	});
	
}
</script>
</body>
</html> 