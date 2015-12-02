<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<script src="<%=baseUrl%>/pages/common/media/js/ajaxfileupload.js" type="text/javascript"></script>  
<title>大拿维护</title>
<style type="text/css">
#detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
}
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">大拿维护</div>
	  <div class="panel-body">
	    <form class="form-horizontal">
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<button id="addBtn" type="button" class="btn btn-default">新增</button>
	            <button id="delBtn" type="button" class="btn btn-default">删除</button>
	            <button id="uploadBtn" type="button" class="btn btn-default">附件上传</button>
	            <button id="topicBtn" type="button" class="btn btn-default">咨询话题</button>
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
	  <!-- table-striped -->
	  <table id="adminTable" class="table table-bordered">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
					<th width="100px" dataIndex="id" style="display: none;">用户Id</th>
                    <th width="100px" dataIndex="nickname" renderFunc="viewUser(this);">用户名</th>
                    <th width="100px" dataIndex="phone">手机号</th>
					<th width="100px" dataIndex="company">公司</th>
					<th width="100px" dataIndex="post">职位</th>
					<th width="100px" dataIndex="onlineTime">空闲时间</th>
					<!-- <th width="100px" dataIndex="industryName">行业</th>
					<th width="100px" dataIndex="schoolName">学校</th> -->
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
	        <h4 class="modal-title">大拿维护</h4>
	      </div>
	      <div class="modal-body">
	        <form id="detailForm" class="form-horizontal" style="width: 500px;">
	        	<div class="row">
			     <!-- <div class="col-sm-3">
			     	<div class="form-group">
					    <label for="url" class="col-sm-3 control-label">头像:</label>
					    <div class="col-sm-9">
					      	<img id="url" class="img-circle img-responsive">
					    </div>
					  </div>
			     </div> -->
			     <div class="col-sm-12">
			     	<div class="form-group">
					    <label for="nickname" class="col-sm-3 control-label">用户名:</label>
					    <div class="col-sm-9">
					    	<input class="form-control" id="id" style="display: none;">
					      	<input class="form-control" id="nickname">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="phone" class="col-sm-3 control-label">手机号:</label>
					    <div class="col-sm-9">
					      	<input type="tel" class="form-control" id="phone">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="isshow" class="col-sm-3 control-label">是否可见:</label>
					    <div class="col-sm-9">
					      	<select class="form-control" id="isshow">
		                      <option value="0">是</option>
		                      <option value="1">否</option>
		                    </select>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="orderNo" class="col-sm-3 control-label">顺序号:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="orderNo">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="company" class="col-sm-3 control-label">公司:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="company">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="post" class="col-sm-3 control-label">职位:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="post">
					    </div>
					  </div>
					  <div class="form-group">
					     <label for="industry" class="col-sm-3 control-label">行业</label>
					     <div class="col-sm-9">
					       <select class="form-control" id="industry">
							  <option value="0">互联网</option>
							  <option value="1">金融</option>
							  <option value="2">快消品</option>
							  <option value="3">广告</option>
							  <option value="4">咨询</option>
							  <option value="5">会计事务所</option>
							  <option value="6">其他</option>
							</select>
					     </div>
					  </div>
					  <div class="form-group">
					     <label for="workYears" class="col-sm-3 control-label">工作经验</label>
					     <div class="col-sm-9">
					       <select class="form-control" id="workYears">
							  <option value="1">一年</option>
							  <option value="2">两年</option>
							  <option value="3">三年</option>
							  <option value="4">四年</option>
							  <option value="5">五年</option>
							  <option value="6">五年以上</option>
							</select>
					     </div>
					  </div>
					  <div class="form-group">
					     <label for="online_startTime" class="col-sm-3 control-label">空闲时间</label>
					     <div class="col-sm-4" style="width: 33%;">
					       <select class="form-control" id="online_startTime">
							  <option>08:00</option>
							  <option>09:00</option>
							  <option>10:00</option>
							  <option>11:00</option>
							  <option>12:00</option>
							  <option>13:00</option>
							  <option>14:00</option>
							  <option>15:00</option>
							  <option>16:00</option>
							  <option>17:00</option>
							  <option>18:00</option>
							  <option>19:00</option>
							  <option>20:00</option>
							  <option>21:00</option>
							  <option>22:00</option>
							  <option>23:00</option>
							  <option>24:00</option>
							</select>
					     </div>
					     <label for="online_endTime" class="col-sm-1 control-label">至</label>
					     <div class="col-sm-4" style="width: 33%;">
					       <select class="form-control" id="online_endTime">
							  <option>08:00</option>
							  <option>09:00</option>
							  <option>10:00</option>
							  <option>11:00</option>
							  <option>12:00</option>
							  <option>13:00</option>
							  <option>14:00</option>
							  <option>15:00</option>
							  <option>16:00</option>
							  <option>17:00</option>
							  <option>18:00</option>
							  <option>19:00</option>
							  <option>20:00</option>
							  <option>21:00</option>
							  <option>22:00</option>
							  <option>23:00</option>
							  <option>24:00</option>
							</select>
					     </div>
					  </div>
					  <div class="form-group">
					    <label for="tags" class="col-sm-3 control-label">标签:</label>
					    <div class="col-sm-9">
					      	<textarea class="form-control" id="tags" placeholder="" rows="4"></textarea>
					    </div>
					  </div>
					  <div class="form-group">
					     <label for="introduce" class="col-sm-3 control-label">个人简介</label>
					     <div class="col-sm-9">
					       <textarea class="form-control" id="introduce" placeholder="" rows="4"></textarea>
					     </div>
					  </div>
			     </div>
				</div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="saveUser()">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="uploadModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">附件上传</h4>
	      </div>
	      <div class="modal-body">
	        <form class="form-horizontal">
	         <div class="form-group">
			    <div class="col-sm-9">
			      	<label for="phone" class="col-sm-2 control-label">类型:</label>
				    <div class="col-sm-9">
				      	<select class="form-control" id="uploadType">
	                      <option value="0">大拿头像</option>
	                      <option value="1">背景图片</option>
	                    </select>
				    </div>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-9">
			    	<input class="form-control" id="id" style="display: none;">
			      	<label for="phone" class="col-sm-2 control-label">文件:</label>
				    <div class="col-sm-9">
				      	<input type="file" class="form-control" id="fileToUpload" name="fileToUpload">
				    </div>
			    </div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="upload()">上传</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 咨询话题维护 -->
	<div class="modal fade" role="dialog" id="topicModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">咨询话题</h4>
	      </div>
	      <div class="modal-body">
	      	<div class="row" style="padding-bottom: 10px;">
		      	<div class="col-sm-12">
		        	<button id="addTopicBtn" type="button" class="btn btn-default">新增</button>
		            <button id="delTopicBtn" type="button" class="btn btn-default">删除</button>
		        </div>
	          </div>
	         <table id="topicTable" class="table table-bordered">
			  	<thead>
					<tr class="success">
						<th width="40px" dataIndex="_index">序号</th>
						<th width="100px" dataIndex="id" style="display: none;">Id</th>
	                    <th width="100px" dataIndex="title" renderFunc="viewTopic(this);">标题</th>
	                    <th width="100px" dataIndex="content">内容</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			  </table>
			  <nav>
				  <ul id="paginator_comment"> </ul>
			  </nav>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="topicDetailModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">话题维护</h4>
	      </div>
	      <div class="modal-body">
	        <form id="topicForm" class="form-horizontal">
	        	<div class="row">
			     <div class="col-sm-12">
			     	<div class="form-group">
					    <label for="title" class="col-sm-2 control-label">标题:</label>
					    <div class="col-sm-10">
					    	<input class="form-control" id="id" style="display: none;">
					      	<input class="form-control" id="title">
					    </div>
					  </div>
					  <div class="form-group">
					     <label for="content" class="col-sm-2 control-label">内容</label>
					     <div class="col-sm-10">
					       <textarea class="form-control" id="content" placeholder="" rows="4"></textarea>
					     </div>
					  </div>
			     </div>
				</div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button id="saveTopicBtn" type="button" class="btn btn-primary">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
<script type="text/javascript">
var currentPage;
var sel_id,sel_topic_id;
$(document).ready(function(){
	currentPage = 1;
	selId = "";
	 // 查询按钮点击事件
	 $("#queryBtn").click (function(){
		parent.$("#message").text("加载中");
		parent.$("#messageModal").modal({backdrop:false});
        $.ajax({
            type:"post",
            url:'<%=baseUrl%>'+"/greater/listGreaters",
            data:{
            	nickname:$("#userName").val(),
            	phone:$("#qryPhone").val(),
            	type : '<%=GloabConstant.USER_TYPE_GREATER%>',
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	sel_id="";
            	loadTableData("adminTable", data.records);
            	initPage("paginator",currentPage,data.totalCount,pageClicked);
            	
        		$("#adminTable tbody tr").click(function() {
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
	 
	 $("#addBtn").click (function(){
		 $("#url").removeAttr("src");
		 resetForm("detailForm");
		 $("#isshow").val("0");
		 $("#orderNo").val("0");
		 $("#detailModal").modal("show");
	 });
	 
	 $("#delBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/greater/delGreater",
		        data:{id:sel_id},
		        dataType:"json",
		        success : function(data){
		        	if(data.success){
		        		$("#detailModal").modal("hide");
		            	showMoment("操作成功");
		            	$("#queryBtn").trigger("click");
		        	}else{
		        		showMoment(data.msg);
		        	}
		        	
		        },
		        error:function(XMLHttpRequest, textStatus, errorThrown){
		        }
		    });
	 });
	 
	 $("#uploadBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $("#uploadType").val("0");
		 $("#fileToUpload").val("");
		 $("#uploadModal").modal("show");
	 });
	 
	 $("#topicBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $("#topicModal").modal("show");
		 loadTopics();
	 });
	 
	 $("#addTopicBtn").click (function(){
		 $("#topicDetailModal").modal("show");
		 resetForm("topicForm");
	 });
	 
	 $("#delTopicBtn").click (function(){
		 if(sel_topic_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/greater/delTopic",
		        data:{
		        	id:sel_topic_id
		        },
		        dataType:"json",
		        success : function(data){
		        	showMoment("操作成功");
		        	loadTopics();
		        },
		        error:function(XMLHttpRequest, textStatus, errorThrown){
		        }
		    });
	 });
	 
	 $("#saveTopicBtn").click (function(){
		 var data = parseFormToJson("topicForm");
		 data.greaterId=sel_id;
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/greater/saveTopic",
		        data:data,
		        dataType:"json",
		        success : function(data){
		        	showMoment("操作成功");
		        	$("#topicDetailModal").modal("hide");
		        	loadTopics();
		        },
		        error:function(XMLHttpRequest, textStatus, errorThrown){
		        }
		    });
	 });
	 
	 $("#queryBtn").trigger("click");
});

function viewUser(el){
	//$("#phone").attr("disabled","disabled");
	$("#detailModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/greater/getGreaterInfo",
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

function saveUser(){
	var data = parseFormToJson("detailForm");
	if(data.phone == null || data.phone.trim() == "")
	{
		showMoment("手机号不能为空");
		return;
	}
	var re =/^1\d{10}$/;
	if(!re.test(data.phone))
	{
		showMoment("手机号格式不正确");
		return;
	}
	
	re =/^\d{1,4}$/;
	if(!re.test(data.orderNo))
	{
		showMoment("顺序号只能为数字！");
		return;
	}
	
	if(data.orderNo == null || data.orderNo.trim() === "")
		data.orderNo=0;
	
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/greater/saveGreater",
        data:data,
        dataType:"json",
        success : function(data){
        	if(data.success){
        		$("#detailModal").modal("hide");
            	showMoment("保存成功");
            	$("#queryBtn").trigger("click");
        	}else{
        		showMoment(data.msg);
        	}
        	
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function pageClicked(newpage){
	currentPage = newpage;
	$("#queryBtn").trigger("click");
}


function upload(){
	if($("#fileToUpload").val() == ''){
		 showMoment("请选择上传文件！");
		 return;
	 }
	 $.ajaxFileUpload({
	 		url:"<%=baseUrl%>/greater/uploadImage",
	 		secureuri:false,
	 		type:'GET',
	 		fileElementId:'fileToUpload',
	 		dataType: 'json',
	 		data:{
	 			id:sel_id,
	 			uploadType:$("#uploadType").val()
	 		},
	 		success: function (result, status){
	 			showMoment("上传成功");
	 			$("#fileToUpload").val("");
	 			//$("#uploadModal").modal("hide");
	 		},
	 		error: function (data, status, e){
	 		}
	 });
}

function loadTopics(){
	selTopicId = "";
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/greater/listTopics",
        data:{
        	greaterId:sel_id
        },
        dataType:"json",
        success : function(data){
        	loadTableData("topicTable", data.records);
    		$("#topicTable tbody tr").click(function() {
    			sel_topic_id=$(this).children("td:eq(1)").text();
    			$(this).css("background","gray");
    			$(this).siblings().css("background","white");
    		});
        	
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function viewTopic(el){
	$("#topicDetailModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/greater/getTopicInfo",
        data:{
        	id:$(el).parent().prev().html()
        },
        dataType:"json",
        success : function(data){
        	putJsonToForm("topicForm",data);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}
</script> 
</body>
</html>