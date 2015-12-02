<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>账户管理</title>
<style type="text/css">
/* #detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
} */
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">账户管理</div>
	  <div class="panel-body">
	    <form class="form-horizontal">
          <!-- <div class="form-group">
	      	<div class="col-sm-10">
	            <button id="delBtn" type="button" class="btn btn-default">删除</button>
	            <button id="topBtn" type="button" class="btn btn-default">置顶</button>
	            <button id="viewCommentsBtn" type="button" class="btn btn-default">评论信息</button>
	        </div>
          </div> -->
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<label for="qryName" class="col-sm-1 control-label">用户名:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="qryName">
			    </div>
	            <button id="queryBtn" type="button" class="btn btn-default">查询</button>
	        </div>
          </div>
		</form>
	  </div>
	  <table id="tradeTable" class="table table-bordered">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
					<th width="100px" dataIndex="id" style="display: none;">id</th>
                    <th width="100px" dataIndex="name">用户名</th>
                    <th width="100px" dataIndex="code">账户编码</th>
					<th width="100px" dataIndex="balance">账户余额</th>
					<th width="100px" dataIndex="opTime" type="time">最近操作时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		  </table>
		  <nav>
			  <ul id="paginator"> </ul>
		  </nav>
	</div>
<script type="text/javascript">
var currentPage;
var sel_id;
$(document).ready(function(){
	currentPage = 1;
	 // 查询按钮点击事件
	 $("#queryBtn").click (function(){
		parent.$("#message").text("加载中");
		parent.$("#messageModal").modal({backdrop:false});
        $.ajax({
            type:"post",
            url:'<%=baseUrl%>'+"/trade/listAccounts",
            data:{
            	name:$("#qryName").val(),
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	sel_id="";
            	loadTableData("tradeTable", data.records);
            	initPage("paginator",currentPage,data.totalCount,pageClicked);
            	
        		$("#topicTable tbody tr").click(function() {
        			sel_id=$(this).children("td:eq(1)").text();
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

function pageClicked(newpage){
	currentPage = newpage;
	$("#queryBtn").trigger("click");
}
</script> 
</body>
</html>