<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>新增用户(天)</title>
<style type="text/css">
/* #detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
} */
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">新增用户(天)</div>
	  <!-- <div class="panel-body">
	    <form class="form-horizontal">
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
	  </div> -->
	  <table id="mainTable" class="table table-bordered">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
                    <th width="100px" dataIndex="day">日期</th>
                    <th width="100px" dataIndex="count">新增用户</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		  </table>
	</div>
<script type="text/javascript">
var sel_id;
$(document).ready(function(){
	 loadData();
});

function loadData(){
	parent.$("#message").text("加载中");
	parent.$("#messageModal").modal({backdrop:false});
    $.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/sys/countNewUsersByDay",
        data:{
        	
        },
        dataType:"json",
        success : function(data){
        	sel_id="";
        	loadTableData("mainTable", data.records);
        	
        	parent.$("#messageModal").modal("hide");
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

</script> 
</body>
</html>