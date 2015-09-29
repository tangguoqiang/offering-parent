<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<script src="<%=baseUrl%>/pages/common/media/js/ajaxfileupload.js" type="text/javascript"></script>  
<title>活动维护</title>
<style type="text/css">
#detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
}
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">活动维护</div>
	  <div class="panel-body">
	    <form class="form-horizontal">
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<button id="addBtn" type="button" class="btn btn-default">新增</button>
	            <button id="delBtn" type="button" class="btn btn-default">删除</button>
	            <button id="speakerBtn" type="button" class="btn btn-default">主讲人维护</button>
	            <button id="uploadBtn" type="button" class="btn btn-default">附件上传</button>
	            <button id="publishBtn" type="button" class="btn btn-default">发布</button>
	            <button id="startBtn" type="button" class="btn btn-default">开始活动</button>
	            <button id="endBtn" type="button" class="btn btn-default">结束活动</button>
	        </div>
          </div>
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<label for="qryTitle" class="col-sm-1 control-label">标题:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="qryTitle">
			    </div>
			    <label for="qryStatus" class="col-sm-2 control-label">状态:</label>
			    <div class="col-sm-3">
			       <select class="form-control" id="qryStatus">
                      <option value="">全部</option>
                      <option value="3">草稿</option>
                      <option value="0">未开始</option>
                      <option value="1">正在进行</option>
                      <option value="2">已结束</option>
                    </select>
			    </div>
	            <button id="queryBtn" type="button" class="btn btn-default">查询</button>
	        </div>
          </div>
		</form>
	  </div>
	  <table id="activityTable" class="table table-bordered">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
					<th width="100px" dataIndex="id" style="display: none;">活动Id</th>
                    <th width="100px" dataIndex="title" renderFunc="viewActivity(this);">标题</th>
                    <th width="100px" dataIndex="startTime" type="time">开始时间</th>
					<th width="100px" dataIndex="endTime" type="time">结束时间</th>
					<th width="100px" dataIndex="type" type="combo" group="ACTIVITY_TYPE">活动分类</th>
					<th width="100px" dataIndex="status" type="combo" group="ACTIVITY_STATUS">状态</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		  </table>
		  <nav>
			  <ul id="paginator"> </ul>
		  </nav>
	</div>
	
	<!-- 维护活动信息 -->
	<div class="modal fade" role="dialog" id="detailModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">活动维护</h4>
	      </div>
	      <div class="modal-body">
	        <form id="detailForm" class="form-horizontal">
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">标题:</label>
			    <div class="col-sm-9">
			    	<input class="form-control" id="id" style="display: none;">
			      	<input class="form-control" id="title">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for=startTime class="col-sm-2 control-label">开始时间:</label>
			    <div class="col-sm-9 input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii">
				    <input class="form-control" size="16" type="text" id="startTime" readonly>
	                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="endTime" class="col-sm-2 control-label">结束时间:</label>
			    <div class="col-sm-9 input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii">
			    	<input class="form-control" size="16" type="text" id="endTime" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="type" class="col-sm-2 control-label">活动分类:</label>
			    <div class="col-sm-9">
			      	<select class="form-control" id="type">
			      		<option value="0">求职咨询</option>
                      	<option value="1">线上宣讲会</option>
                    </select>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="groupName" class="col-sm-2 control-label">群聊名称:</label>
			    <div class="col-sm-9">
			      	<input class="form-control" id="groupName">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="groupInfo" class="col-sm-2 control-label">群聊信息:</label>
			    <div class="col-sm-9">
			      	<textarea class="form-control" rows="3" id="groupInfo"></textarea>
			    </div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="saveActivity()">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 主讲人维护 -->
	<div class="modal fade" role="dialog" id="speakerModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">主讲人维护</h4>
	      </div>
	      <div class="modal-body">
	      	<table id="speakerTable" class="table table-bordered">
			  	<thead>
					<tr class="success">
						<th width="40px" dataIndex="_index">序号</th>
						<th width="100px" dataIndex="id" style="display: none;">主讲人Id</th>
	                    <th width="100px" dataIndex="name" renderFunc="viewSpeaker(this);">主讲人姓名</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			  </table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="addSpeaker()">添加</button>
	        <button type="button" class="btn btn-primary" onclick="delSpeaker()">删除</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 新增主讲人 -->
	<div class="modal fade" role="dialog" id="addSpeakerModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">主讲人维护</h4>
	      </div>
	      <div class="modal-body">
	        <form id="speakerForm" class="form-horizontal">
			  <div class="form-group">
			    <label for="speaker" class="col-sm-2 control-label">主讲人:</label>
			    <div class="col-sm-9">
			        <input class="form-control" id="id" style="display: none;">
			      	<select class="form-control" id="speakerId">
                    </select>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="remark" class="col-sm-2 control-label">描述:</label>
			    <div class="col-sm-9">
			      	<textarea class="form-control" rows="3" id="remark"></textarea>
			    </div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="saveSpeaker()">保存</button>
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
	                      <option value="0">活动图片</option>
	                      <option value="1">活动分享图片</option>
	                      <option value="2">群分享图片</option>
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
<script type="text/javascript">
var currentPage;
var sel_id;
var sel_speaker_id;
$(document).ready(function(){
	//加载人员下拉框数据
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/greater/listGreaters",
        data:{
        	currentPage:1,
        	pageSize:50
        },
        dataType:"json",
        success : function(data){
        	initCombox("speakerId",data.records);
        	
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
	
	$('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	
	currentPage = 1;
	 // 查询按钮点击事件
	 $("#queryBtn").click (function(){
		parent.$("#message").text("加载中");
		parent.$("#messageModal").modal({backdrop:false});
        $.ajax({
            type:"post",
            url:'<%=baseUrl%>'+"/activity/listActivities",
            data:{
            	title:$("#qryTitle").val(),
            	status:$("#qryStatus").val(),
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	sel_id="";
            	loadTableData("activityTable", data.records);
            	
            	$('#paginator').bootstrapPaginator({ 
            		bootstrapMajorVersion:3,
            		currentPage:currentPage,
            		numberOfPages: 5, 
            		totalPages:Math.ceil(data.totalCount/pageSize),
            		size:"normal",  
                    alignment:"center",  
                    itemTexts: function (type, page, current) {  
                        switch (type) {  
                            case "first":  
                                return "第一页";  
                            case "prev":  
                                return "<";  
                            case "next":  
                                return ">";  
                            case "last":  
                                return "最后页";  
                            case "page":  
                                return  page;  
                        }                 
                    },  
                    //onPageChanged: page,
                    onPageClicked: function (e, originalEvent, type, page) {  
                    	currentPage=page;
                    	$("#queryBtn").trigger("click");
                    }  
                });
            	$("#paginator a").css("cursor","pointer");
            	
            	$("#activityTable tbody tr").css("cursor","pointer");
        		$("#activityTable tbody tr").click(function() {
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
		 resetForm("detailForm");
		 $("#type").val(0);
		 $("#detailModal").modal("show");
	 });
	 
	 $("#delBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/activity/delActivity",
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
	 
	 $("#speakerBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $("#speakerModal").modal("show");
		 parent.$("#message").text("加载中");
		 parent.$("#messageModal").modal({backdrop:false});
	     $.ajax({
	         type:"post",
	         url:'<%=baseUrl%>'+"/activity/listSpeakers",
	            data:{
	            	id:sel_id
	            },
	            dataType:"json",
	            success : function(data){
	            	sel_speaker_id = "";
	            	loadTableData("speakerTable", data.records);
	            	
	            	$("#speakerTable tbody tr").css("cursor","pointer");
	        		$("#speakerTable tbody tr").click(function() {
	        			sel_speaker_id=$(this).children("td:eq(1)").text();
	        			$(this).css("background","gray");
	        			$(this).siblings().css("background","white");
	        		});
	            	parent.$("#messageModal").modal("hide");
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
	 
	 $("#publishBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/activity/publishActivity",
		        data:{
		        	id:sel_id
		        },
		        dataType:"json",
		        success : function(data){
		        	if(data.success)
		        	{
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
	 
	 $("#startBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/activity/startActivity",
		        data:{
		        	id:sel_id
		        },
		        dataType:"json",
		        success : function(data){
		        	if(data.success)
		        	{
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
	 
	 $("#endBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/activity/endActivity",
		        data:{
		        	id:sel_id
		        },
		        dataType:"json",
		        success : function(data){
		        	if(data.success)
		        	{
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
	 
	 $("#queryBtn").trigger("click");
});

function viewActivity(el){
	$("#detailModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/activity/getActivityInfo",
        data:{
        	id:$(el).parent().prev().html()
        },
        dataType:"json",
        success : function(data){
        	putJsonToForm("detailForm",data);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function saveActivity(){
	var data = parseFormToJson("detailForm");
	if(data.title == null || data.title.trim() == "")
	{
		showMoment("标题不能为空");
		return;
	}
	if(data.startTime == null || data.startTime.trim() == "")
	{
		showMoment("开始时间不能为空");
		return;
	}
	if(data.endTime == null || data.endTime.trim() == "")
	{
		showMoment("结束时间不能为空");
		return;
	}
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/activity/saveActivity",
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

function page(newpage){
	currentPage = newpage;
	$("#queryBtn").trigger("click");
}

function upload(){
	if($("#fileToUpload").val() == ''){
		 showMoment("请选择上传文件！");
		 return;
	 }
	 $.ajaxFileUpload({
	 		url:"<%=baseUrl%>/activity/uploadImage",
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

function addSpeaker(){
	 $("#speakerId").removeAttr("disabled");
	 resetForm("speakerForm");
	 $("#addSpeakerModal").modal("show");
}

function saveSpeaker(){
	var data = parseFormToJson("speakerForm");
	data.activityId=sel_id;
	 $.ajax({
	        type:"post",
	        url:'<%=baseUrl%>'+"/activity/saveSpeaker",
	        data:data,
	        dataType:"json",
	        success : function(data){
	        	$("#addSpeakerModal").modal("hide");
	        	 $.ajax({
	    	         type:"post",
	    	         url:'<%=baseUrl%>'+"/activity/listSpeakers",
	    	            data:{
	    	            	id:sel_id
	    	            },
	    	            dataType:"json",
	    	            success : function(data){
	    	            	sel_speaker_id = "";
	    	            	loadTableData("speakerTable", data.records);
	    	            	
	    	            	$("#speakerTable tbody tr").css("cursor","pointer");
	    	        		$("#speakerTable tbody tr").click(function() {
	    	        			sel_speaker_id=$(this).children("td:eq(1)").text();
	    	        			$(this).css("background","gray");
	    	        			$(this).siblings().css("background","white");
	    	        		});
	    	            },
	    	            error:function(XMLHttpRequest, textStatus, errorThrown){
	    	            }
	    	        });
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
	        }
	    });
}

function delSpeaker(){
	if(sel_speaker_id==""){
		 showMoment("请选择一条记录");
		 return;
	 }
	
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/activity/delSpeaker",
        data:{
        	id:sel_speaker_id
        },
        dataType:"json",
        success : function(data){
        	$.ajax({
   	         type:"post",
   	         url:'<%=baseUrl%>'+"/activity/listSpeakers",
   	            data:{
   	            	id:sel_id
   	            },
   	            dataType:"json",
   	            success : function(data){
   	            	sel_speaker_id = "";
   	            	loadTableData("speakerTable", data.records);
   	            	
   	            	$("#speakerTable tbody tr").css("cursor","pointer");
   	        		$("#speakerTable tbody tr").click(function() {
   	        			sel_speaker_id=$(this).children("td:eq(1)").text();
   	        			$(this).css("background","gray");
   	        			$(this).siblings().css("background","white");
   	        		});
   	            },
   	            error:function(XMLHttpRequest, textStatus, errorThrown){
   	            }
   	        });
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function viewSpeaker(el){
	$("#addSpeakerModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/activity/getSpeakerInfo",
        data:{
        	id:$(el).parent().prev().html()
        },
        dataType:"json",
        success : function(data){
        	putJsonToForm("speakerForm",data);
        	$("#speakerId").attr("disabled","disabled");
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}
</script> 
</body>
</html>