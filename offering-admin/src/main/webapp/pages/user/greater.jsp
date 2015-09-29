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
	        </div>
          </div>
          <div class="form-group">
	      	<div class="col-sm-10">
	        	<label for="userName" class="col-sm-1 control-label">用户名:</label>
			    <div class="col-sm-3">
			      <input class="form-control" id="userName">
			    </div>
			    <!-- <label for="qryStatus" class="col-sm-2 control-label">状态:</label>
			    <div class="col-sm-3">
			       <select class="form-control" id="qryStatus">
                      <option value="">全部</option>
                      <option value="0">启用</option>
                      <option value="1">停用</option>
                    </select>
			    </div> -->
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
					<th width="100px" dataIndex="post">岗位</th>
					<th width="100px" dataIndex="tags">标签</th>
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
	        <form id="detailForm" class="form-horizontal">
	        	<div class="row">
			     <div class="col-sm-3">
			     	<div class="form-group">
					    <label for="url" class="col-sm-3 control-label">头像:</label>
					    <div class="col-sm-9">
					      	<img id="url" class="img-circle img-responsive">
					    </div>
					  </div>
			     </div>
			     <div class="col-sm-9">
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
					    <label for="post" class="col-sm-3 control-label">岗位:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="post">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="tags" class="col-sm-3 control-label">标签:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="tags">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="experience" class="col-sm-3 control-label">工作经历:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="experience">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="specialty" class="col-sm-3 control-label">学校背景:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="specialty">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="job" class="col-sm-3 control-label">心得分享:</label>
					    <div class="col-sm-9">
					      	<input type="text" class="form-control" id="job">
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
            url:'<%=baseUrl%>'+"/greater/listGreaters",
            data:{
            	nickname:$("#userName").val(),
            	type : '<%=GloabConstant.USER_TYPE_GREATER%>',
            	pageSize:pageSize,
            	currentPage:currentPage
            },
            dataType:"json",
            success : function(data){
            	sel_id="";
            	loadTableData("adminTable", data.records);
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
            	
            	$("#adminTable tbody tr").css("cursor","pointer");
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
		 //$("#phone").removeAttr("disabled");
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
</script> 
</body>
</html>