<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>普通用户管理</title>
<style type="text/css">
#detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
}
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">用户维护</div>
	  <div class="panel-body">
	    <form class="form-horizontal">
          <div class="form-group">
	      	<div class="col-sm-10">
	            <button id="delBtn" type="button" class="btn btn-default">预留</button>
	        </div>
          </div>
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<label for="userName" class="col-sm-1 control-label">用户名:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="userName">
			    </div>
			    <label for="qryPhone" class="col-sm-1 control-label">手机号:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="qryPhone">
			    </div>
	            <button id="queryBtn" type="button" class="btn btn-default">查询</button>
	        </div>
          </div>
		</form>
	  </div>
	  <table id="adminTable" class="table table-bordered table-striped">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
					<th width="10px" dataIndex="id" style="display: none;">用户Id</th>
                    <th width="100px" dataIndex="nickname" renderFunc="viewUser(this);">用户名</th>
                    <th width="100px" dataIndex="phone">手机号</th>
					<th width="100px" dataIndex="schoolName">学校</th>
					<th width="100px" dataIndex="major">专业</th>
					<th width="100px" dataIndex="gradeName">年级</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		  </table>
		  <nav>
			  <ul id="paginator" > </ul>
		  </nav>
	</div>
	<div class="modal fade" id="detailModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">用户维护</h4>
	      </div>
	      <div class="modal-body">
	        <form id="detailForm" class="form-horizontal">
			  <div class="form-group">
			    <label for="nickname" class="col-sm-2 control-label">用户名:</label>
			    <div class="col-sm-8">
			    	<input class="form-control" id="id" style="display: none;">
			      	<input class="form-control" id="nickname">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="phone" class="col-sm-2 control-label">手机号:</label>
			    <div class="col-sm-8">
			      	<input type="tel" class="form-control" id="phone">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="schoolName" class="col-sm-2 control-label">学校:</label>
			    <div class="col-sm-8">
			      	<input class="form-control" id="schoolName">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="major" class="col-sm-2 control-label">专业:</label>
			    <div class="col-sm-8">
			      	<input class="form-control" id="major">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="gradeName" class="col-sm-2 control-label">年级:</label>
			    <div class="col-sm-8">
			      	<input class="form-control" id="gradeName">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="url" class="col-sm-2 control-label">头像:</label>
			    <div class="col-sm-8">
			      	<img id="url">
			    </div>
			  </div>
			</form>
	      </div>
	      <!-- <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="saveUser()">保存</button>
	      </div> -->
	    </div>
	  </div>
	</div>
<script type="text/javascript">
var currentPage;
var selId;
$(document).ready(function(){
	currentPage = 1;
	selId = "";
	 // 查询按钮点击事件
	 $("#queryBtn").click (function(){
		parent.$("#message").text("加载中");
		parent.$("#messageModal").modal({backdrop:false});
        $.ajax({
            type:"post",
            url:'<%=baseUrl%>'+"/user/listUsers",
            data:{
            	nickname:$("#userName").val(),
            	phone:$("#qryPhone").val(),
            	type : '<%=GloabConstant.USER_TYPE_NORMAL %>',
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	if(data.totalCount == 0)
            		data.totalCount=1;
            	sel_id="";
            	loadTableData("adminTable", data.records);
            	
            	initPage("paginator",currentPage,data.totalCount,pageClicked);
            	
            	$("#adminTable tbody tr").css("cursor","pointer");
        		$("#adminTable tbody tr").click(function() {
        			sel_id=$(this).children("td:eq(0)").text();
        			$(this).css("background","gray");
        			$(this).siblings().css("background","white");
        		});
            	parent.$("#messageModal").modal("hide");
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
	 });
	 
	 $("#queryBtn").trigger("click");
});

function pageClicked(page){
	currentPage=page;
	$("#queryBtn").trigger("click");
}

function viewUser(el){
	$("#detailModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/user/getUserInfo",
        data:{
        	id:$(el).parent().prev().html()
        },
        dataType:"json",
        success : function(data){
        	putJsonToForm("detailForm",data);
        	$("#url").attr("src",'<%=baseUrl%>'+data.url);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function showDialog() {
	$("#myModal").modal("show");
	$("#corpusInput").val("");
	$("#checkInput").html("");
}
</script> 
</body>
</html>