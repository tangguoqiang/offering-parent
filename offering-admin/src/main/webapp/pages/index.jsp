<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="common/include.jsp"%>
<title>Offering</title>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;" >
 	<form id="mainForm" style="visibility: false;" method ="post"></form>
	<nav id="topBar" class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="javascript:void(0);">Offering</a>
	    </div>
	    <div class="collapse navbar-collapse">
	      <ul class="nav navbar-nav" id="navBar">
	        <li class="dropdown active">
	          <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">用户管理 <span class="caret"></span></a>
	          <ul class="dropdown-menu" role="menu">
	          	<li><a href="javascript:void(0);" url="pages/user/user.jsp" onclick="clickTopbar(this);">用户维护 <span class="sr-only">(current)</span></a></li>
	          	<li class="divider"></li>
	           	<li><a href="javascript:void(0);" url="pages/user/greater.jsp" onclick="clickTopbar(this);">大拿维护</a></li>
	          </ul>
	        </li>
	        <li>
	          <a href="javascript:void(0);" url="pages/activity/activity.jsp" onclick="clickTopbar(this);" role="button" aria-expanded="false">活动管理 <span class="sr-only"></span></a>
	        </li>
	      </ul>
	      
	      <ul class="nav navbar-nav navbar-right">
	      	<p class="navbar-text" style="font-size: medium;font-weight: bold;color: blue;">欢迎回来:<%= userName%></p>
	      	<!-- <li><a href="javascript:void(0);" onclick="resetPass();">修改密码</a></li> -->
	        <li><a href="javascript:void(0);" onclick="logout();">注销</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>
	<iframe id="mainIframe" src="pages/user/user.jsp" style="width: 99.6%;border: none;overflow: false;">
	</iframe>
	
	<div class="modal fade" id="messageModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="关闭"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示框</h4>
	      </div>
	      <div class="modal-body">
	        <p id="message"></p>
	      </div>
	    </div>
	  </div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#mainIframe").attr("height",$(window).height() - 75);
//	if(auth === '0'){
//		$.each($(".lan_bottitle").children(),function(i,el){
//			if(i != 0){
//				$(el).hide();
//			}
//		});
//	}else if(auth === '-1'){
//		$.each($(".lan_bottitle").children(),function(i,el){
//			$(el).show();
//		});
//	}
	
	// 加载短信数据
//	loadTableData();
});

function clickTopbar(el){
	$("#navBar").children(".active").removeClass("active");
	$(".dropdown-menu").children(".active").removeClass("active");
	if($(el).parent().parent().hasClass("dropdown-menu")){
		/* $(el).parent().parent().children(".active").removeClass("active"); */
		$(el).parent().addClass("active");
		$(el).parent().parent().parent().addClass("active");
	}else{
		$(el).parent().addClass("active");
	}
	$("#mainIframe").attr("src",$(el).attr("url"));
}

function logout(){
	$.ajax({
        async:false,
        type:"post",
        url:baseUrl+"/logout",
        data:{
        },
        dataType:"json",
        success : function(data){
        	$("#mainForm").action=baseUrl+"/";
			$("#mainForm").submit();
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

</script> 
</body>
</html>