    // 对Date的扩展，将 Date 转化为指定格式的String   
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
    // 例子：   
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
    Date.prototype.format = function(fmt)   
    { //author: meizz   
      var o = {   
        "M+" : this.getMonth()+1,                 //月份   
        "d+" : this.getDate(),                    //日   
        "h+" : this.getHours(),                   //小时   
        "m+" : this.getMinutes(),                 //分   
        "s+" : this.getSeconds(),                 //秒   
        "q+" : Math.floor((this.getMonth()+3)/3), //季度   
        "S"  : this.getMilliseconds()             //毫秒   
      };   
      if(/(y+)/.test(fmt))   
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
      for(var k in o)   
        if(new RegExp("("+ k +")").test(fmt))   
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
      return fmt;   
    }; 

/**
 * 加载表数据
 * @param tableId 表ID
 * @param data 数据
 */
function loadTableData(tableId,data){
	var ths=$("#"+tableId).children().eq(0).children().eq(0).children();
	var fields=[];
	var el;
	for(var i=0,len=ths.length;i<len;i++){
		var obj={};
		el= ths.eq(i);
		obj["dataIndex"]=el.attr("dataIndex");
		obj["innerHtml"]=el.html();
		obj["style"]=el.attr("style");
		obj["renderFunc"]=el.attr("renderFunc");
		obj["type"]=el.attr("type");
		obj["group"]=el.attr("group");
		fields.push(obj);
	}
	var tbody=$("#"+tableId).children().eq(1);
	tbody.empty();
	var html="";
	for(var i=0,len=data.length;i<len;i++){
		html+="<tr>";
		temp=data[i];
		for(var j=0,len1=fields.length;j<len1;j++){
			obj=fields[j];
			if((typeof obj["style"]) != "undefined")
				html= html+"<td style='" +obj["style"]+"'>";
			else
				html+="<td>";
			if((typeof obj["renderFunc"]) != "undefined")
				html= html+"<a href='javascript:void(0);'  onclick='" +obj["renderFunc"]+"'>";
			
			if((typeof obj["dataIndex"]) != "undefined"){
				if(obj["dataIndex"] == "_index"){
					html += (i+1);
				}else{
					if(typeof temp[obj["dataIndex"]] != "undefined") {
						if(typeof obj["type"] != "undefined")
						{
							if(obj["type"] == "time")
							{
								html += formatTime(temp[obj["dataIndex"]]);
							}else if(obj["type"] == "combo")
							{
								html += getComboValue(obj["group"],temp[obj["dataIndex"]]);
							}
						}else
							html += temp[obj["dataIndex"]];
					} else {
						html += "";
					}
					
				}
			}
				
			if(obj["innerHtml"].indexOf("input")!=-1){
				html=html+obj["innerHtml"];
			}
			if((typeof obj["renderFunc"]) != "undefined")
				html= html+"</a>";
			html+="</td>";
		}
		html+="</tr>";
	}
	tbody.append(html);
}

/**
 * 加载下拉框数据
 * @param id 下拉框ID
 * @param data 数据
 */
function loadComboxData(id,data){
	$("#"+id).empty();
	var html="";
	for(var i=0,len=data.length;i<len;i++){
		html=html+"<option value='"+data[i].id+"'>"+data[i].text+"</option>";
	}
	$("#"+id).append(html);
}

/**
 * 判断是是否为整数
 * @param s
 * @returns {Boolean}
 */
function isNum(s) {
	 var regu = "^[1-9][0-9]*$"; // 小数测试
	 var re = new RegExp(regu);
	 if (s.match(re) == null)
	  return false;
	 else
	  return true;
}

/**
 * 提示消息，会自动关闭
 * @param text
 */
function showMoment(text){
	parent.$("#message").text(text);
	parent.$("#messageModal").modal({backdrop:false});
	setTimeout("parent.$('#messageModal').modal('hide')",1000);
}

/**
 * 将指定form中的元素转化为jsonObj
 * @param formId
 * @param jsonObj
 */
function parseFormToJson(formId){
	var obj = {};
	$.each($("#" + formId + " input"), function(i,el) {
		var value = $(el).val();
		obj[$(el).attr("id")] = value;
	});
	
	$.each($("#" + formId + " textarea"), function(i,el) {
		var value = $(el).val();
		obj[$(el).attr("id")] = value;
	});
	
	$.each($("#" + formId + " select"), function(i,el) {
		var value = $(el).val();
		obj[$(el).attr("id")] = value;
	});
	return obj;
}

/**
 * 将jsonObj转化为指定form中的元素值
 * @param formId
 * @param jsonObj
 */
function putJsonToForm(formId,jsonObj){
	$.each($("#" + formId + " input"), function(i,el) {
		$(el).val(jsonObj[$(el).attr("id")]);
	});
	
	$.each($("#" + formId + " select"), function(i,el) {
		$(el).val(jsonObj[$(el).attr("id")]);
		
	});
	$.each($("#" + formId + " textarea"), function(i,el) {
		$(el).val(jsonObj[$(el).attr("id")]);
	});
	
	
}

/**
 * 生成分页
 */
function createPage(currentPage) {
	var pre_html = "<li>"
		      		+ "<a href=\"javascript:void(0);\" aria-label=\"Previous\" onclick=\"page(this,'prev')\">"
		      		+ "<span aria-hidden=\"true\">&laquo;</span>"
		      		+ "</a>"
		      		+ "</li>";
	var next_html = "<li>"
					+ "<a href=\"javascript:void(0);\" aria-label=\"Next\" onclick=\"page(this,'next')\">"
					+ "<span aria-hidden=\"true\">&raquo;</span>"
					+ "</a>"
					+ "</li>";
	$(".pagination").children("li").remove();
	$(".pagination").append(pre_html);
	if(totalPage > 0) {
		for(var i = 0; i < totalPage; i++) {
			var html;
			if(currentPage == (i + 1)) {
				html = "<li class=\"active\"><a href=\"javascript:void(0);\" onclick=\"page(this,"+(i+1)+")\">"+ (i+1) +"</a></li>";
			} else {
				html = "<li><a href=\"javascript:void(0);\" onclick=\"page(this,"+(i+1)+")\">"+ (i+1) +"</a></li>";
			}
			$(".pagination").children("li:eq("+i+")").after(html);
		}
	}
	$(".pagination").append(next_html);
	
}

/**
 * 分页
 */
function page(el,flag){
	if("next" == flag)
		if($("#currentPage").val() >= totalPage)
			$("#currentPage").val(totalPage);
		else
			$("#currentPage").val(parseInt($("#currentPage").val())+1);
	else if("prev" == flag){
		if($("#currentPage").val() > 1)
			$("#currentPage").val(parseInt($("#currentPage").val()) - 1);
		else 
			$("#currentPage").val(1);
	} else {
		$("#currentPage").val(flag);
	}
		
	$("#queryBtn").trigger("click", [false]);
}

function resetForm(formId){
	$.each($("#" + formId + " input"), function(i,el) {
		$(el).val("");
	});
	
	$.each($("#" + formId + " textarea"), function(i,el) {
		$(el).val("");
	});
	
	$.each($("#" + formId + " select"), function(i,el) {
		$(el).val("0");
	});
}

function formatTime(value)
{
	if(typeof value=="undefined" || value.trim() == "")
		return "";
	var d = new Date();
	d.setTime(parseInt(value));
	return d.format("yyyy-MM-dd hh:mm");
}

function getComboValue(group,code)
{
	if(group == "ACTIVITY_TYPE")
	{
		if(code == '0')
			return "求职咨询";
		if(code == '1')
			return "线上宣讲会";
	}else if(group == "ACTIVITY_STATUS")
	{
		if(code == '0')
			return "未开始";
		if(code == '1')
			return "进行中";
		if(code == '2')
			return "已结束";
		if(code == '3')
			return "草稿";
	}
}

function initCombox(id,data){
	$("#"+id).empty();
	var temp;
	for(var i=0,len=data.length;i<len;i++){
		temp=data[i];
		$("#"+id).append("<option value='"+ temp.id +"'>"+temp.nickname+"</option>");
	}
}