<%@page import="com.offering.constant.GloabConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<script src="<%=baseUrl%>/pages/common/media/js/ajaxfileupload.js" type="text/javascript"></script>  
<title>社区话题维护</title>
<style type="text/css">
/* #detailModal .modal-body{
	max-height: 450px;
	overflow: auto;
} */
</style>
</head>
<body style="padding:0px 0px 0px 0px;  margin:0 auto;overflow: hidden;">
	<div class="panel panel-primary">
	  <div class="panel-heading">社区话题维护</div>
	  <div class="panel-body">
	    <form class="form-horizontal">
          <div class="form-group">
	      	<div class="col-sm-10">
	            <button id="delBtn" type="button" class="btn btn-default">删除</button>
	            <button id="topBtn" type="button" class="btn btn-default">置顶</button>
	            <button id="viewCommentsBtn" type="button" class="btn btn-default">评论信息</button>
	        </div>
          </div>
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<label for="qryContent" class="col-sm-1 control-label">内容:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="qryContent">
			    </div>
			    <label for=""qryIstop"" class="col-sm-2 control-label">置顶:</label>
			    <div class="col-sm-3">
			       <select class="form-control" id="qryIstop">
                      <option value="">全部</option>
                      <option value="0">是</option>
                      <option value="1">否</option>
                    </select>
			    </div>
	            <button id="queryBtn" type="button" class="btn btn-default">查询</button>
	        </div>
          </div>
		</form>
	  </div>
	  <table id="topicTable" class="table table-bordered">
		  	<thead>
				<tr class="success">
					<th width="40px" dataIndex="_index">序号</th>
					<th width="100px" dataIndex="id" style="display: none;">话题Id</th>
                    <th width="100px" dataIndex="content" renderFunc="viewTopic(this);">内容</th>
                    <th width="100px" dataIndex="createTime" type="time">创建时间</th>
					<th width="100px" dataIndex="name">创建人</th>
					<th width="100px" dataIndex="isTop" type="combo" group="YESNO">置顶</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		  </table>
		  <nav>
			  <ul id="paginator"> </ul>
		  </nav>
	</div>
	
	<!-- 查看话题信息 -->
	<div class="modal fade" role="dialog" id="detailModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">社区话题信息</h4>
	      </div>
	      <div class="modal-body">
	        <form id="detailForm" class="form-horizontal">
			  <div class="form-group">
			    <label for="content" class="col-sm-2 control-label">内容:</label>
			    <div class="col-sm-9">
			    	<input class="form-control" id="id" style="display: none;">
			      	<textarea class="form-control" id="content" rows="3"></textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="createTime" class="col-sm-2 control-label">创建时间:</label>
			    <div class="col-sm-9">
			    	<input class="form-control" id="createTime">
			    </div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="delTopic();">删除</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 查看评论列表 -->
	<div class="modal fade" role="dialog" id="commentModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">&times;</span></button> -->
	        <h4 class="modal-title">评论信息</h4>
	      </div>
	      <div class="modal-body">
	         <table id="commentTable" class="table table-bordered">
			  	<thead>
					<tr class="success">
						<th width="40px" dataIndex="_index">序号</th>
						<th width="100px" dataIndex="id" style="display: none;">Id</th>
	                    <th width="100px" dataIndex="comment">内容</th>
	                    <th width="100px" dataIndex="createTime" type="time">评论时间</th>
						<th width="100px" dataIndex="name">创建人</th>
						<th width="100px" dataIndex="toUserName">被评论人</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			  </table>
			  <nav>
				  <ul id="paginator_comment"> </ul>
			  </nav>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="delComment();">删除</button>
	      </div>
	    </div>
	  </div>
	</div>
<script type="text/javascript">
var currentPage;
var sel_id;
var sel_comment_id;
$(document).ready(function(){
	currentPage = 1;
	 // 查询按钮点击事件
	 $("#queryBtn").click (function(){
		parent.$("#message").text("加载中");
		parent.$("#messageModal").modal({backdrop:false});
        $.ajax({
            type:"post",
            url:'<%=baseUrl%>'+"/community/listTopics",
            data:{
            	content:$("#qryContent").val(),
            	isTop:$("#qryIstop").val(),
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	sel_id="";
            	loadTableData("topicTable", data.records);
            	
            	$('#paginator').bootstrapPaginator({ 
            		bootstrapMajorVersion:3,
            		currentPage:currentPage,
            		numberOfPages: 5, 
            		totalPages:data.totalCount == 0?1:Math.ceil(data.totalCount/pageSize),
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
            	
            	$("#topicTable tbody tr").css("cursor","pointer");
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
	 
	 $("#delBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 delTopic();
	 });
	 
	 $("#topBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $.ajax({
		        type:"post",
		        url:'<%=baseUrl%>'+"/community/top",
		        data:{id:sel_id},
		        dataType:"json",
		        success : function(data){
		        	if(data.success){
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
	 
	 $("#viewCommentsBtn").click (function(){
		 if(sel_id==""){
			 showMoment("请选择一条记录");
			 return;
		 }
		 $("#commentModal").modal("show");
		 loadComments();
	 });
	 
	 $("#queryBtn").trigger("click");
});

function viewTopic(el){
	$("#detailModal").modal("show");
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/community/getTopicInfo",
        data:{
        	id:$(el).parent().prev().html()
        },
        dataType:"json",
        success : function(data){
        	putJsonToForm("detailForm",data);
        	$("#createTime").val(formatTime(data.createTime));
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}

function delTopic(){
	 $.ajax({
	        type:"post",
	        url:'<%=baseUrl%>'+"/community/delTopic",
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
}

function loadComments(){
	$.ajax({
        type:"post",
        url:'<%=baseUrl%>'+"/community/listComments",
        data:{
        	topicId:sel_id,
        	pageSize:pageSize,
        	currentPage:currentPage
        },
        dataType:"json",
        success : function(data){
        	loadTableData("commentTable", data.records);
        	$('#paginator_comment').bootstrapPaginator({ 
        		bootstrapMajorVersion:3,
        		currentPage:currentPage,
        		numberOfPages: 5, 
        		totalPages:data.totalCount == 0?1:Math.ceil(data.totalCount/pageSize),
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
                	loadComments();
                }  
            });
        	$("#paginator_comment a").css("cursor","pointer");
        	
        	$("#commentTable tbody tr").css("cursor","pointer");
    		$("#commentTable tbody tr").click(function() {
    			sel_comment_id=$(this).children("td:eq(1)").text();
    			$(this).css("background","gray");
    			$(this).siblings().css("background","white");
    		});
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
 });
}

function delComment(){
	 $.ajax({
	        type:"post",
	        url:'<%=baseUrl%>'+"/community/delComment",
	        data:{id:sel_comment_id},
	        dataType:"json",
	        success : function(data){
	        	if(data.success){
	            	showMoment("操作成功");
	            	loadComments();
	            	sel_comment_id="";
	        	}else{
	        		showMoment(data.msg);
	        	}
	        	
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
	        }
	    });
}
</script> 
</body>
</html>