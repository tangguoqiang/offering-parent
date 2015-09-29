<%@page import="com.offering.bean.User"%>
<%
	String baseUrl = request.getContextPath();
	String userName = null,auth = null,userId = null; 
	if(request.getSession().getAttribute("user") != null){
		User user = (User)request.getSession().getAttribute("user");
		//用户名
		userName = user.getNickname();
		userId = user.getId();
		//权限
		//auth = user.getAuthority();
	}
%>
	<script src="<%=baseUrl%>/pages/common/media/js/jquery.min.js" type="text/javascript"></script>
	<script src="<%=baseUrl%>/pages/common/media/js/bootstrap.min.js" type="text/javascript"></script>
	<%-- <script src="<%=baseUrl%>/pages/common/media/js/json2.js" type="text/javascript"></script> --%>
	<script src="<%=baseUrl%>/pages/common/media/js/common.js" type="text/javascript"></script>   
	<%-- <script src="<%=baseUrl%>/pages/common/media/js/smartpaginator.js" type="text/javascript"></script> --%>  
	<script src="<%=baseUrl%>/pages/common/media/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>  
	<script src="<%=baseUrl%>/pages/common/media/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="<%=baseUrl%>/pages/common/media/js/bootstrap-paginator.min.js" type="text/javascript"></script>   

 	<link href="<%=baseUrl%>/pages/common/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=baseUrl%>/pages/common/media/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=baseUrl%>/pages/common/media/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
    <%-- <link href="<%=baseUrl%>/pages/common/media/css/style.css" rel="stylesheet" type="text/css"/> --%>
	<%-- <link href="<%=baseUrl%>/pages/common/media/css/smartpaginator.css" rel="stylesheet" type="text/css"/> --%>
<script type="text/javascript">
	var baseUrl = '<%=baseUrl%>';
	var userId = '<%=userId%>';
	var userName = '<%=userName%>';
	var pageSize = 10;//每页的条数
</script>