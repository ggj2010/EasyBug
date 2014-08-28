<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<% 
	request.setAttribute("path",request.getContextPath());
	String path = request.getContextPath();
%>

<title>BUG管理系统V1.0</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />



<link href="${path}/themes/css/loginStyle.css" type="text/css" rel="stylesheet">
<link href="${path}/themes/css/style.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${path}/themes/jqueryui/south-street/jquery.ui.all.css">


<!--topMenu,topJquery顺序要对-->
<script type="text/javascript" src="${path}/themes/js/topMenu.js"></script>
<script type="text/javascript" src="${path}/themes/js/topJquery.js"></script>

<script type="text/javascript" src="${path}/themes/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${path}/themes/jqueryui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${path}/themes/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>



<script type="text/javascript" src="${path}/themes/js/global.js"></script>
<script type="text/javascript" src="${path}/themes/js/bootstrap.js"></script>


<script type="text/javascript" src="${path}/themes/validate/js/jquery.validate.js"></script>



<!--必须要放下面-->
<script type="text/javascript" src="${path}/themes/js/jquery.cookie.js"></script>




<script type="text/javascript">
    function showLoading(){
        $('#loadding').show();
        $('#fullbg').show();
    }
    
    function hideLoading(){
        $('#loadding').hide();
        $('#fullbg').hide();
    }
	
	function back(){
		winload('${path}/main/back.do');
	}
	
	function back2(){
		winload('${path}/main/back2.do');
	}
	$(document).ready(function() {
		if("${messageCode}"=="PARAM_ERROR"){jqueryUIAlert("操作失败，缺少参数或者参数错误!");}
		if("${messageCode}"=="NOT_FOUND_DATA"){jqueryUIAlert("操作失败，未找到相应的数据");}
		if("${messageCode}"=="STUENROLL_ALREADY_OWNFEE"){jqueryUIAlert("操作失败，欠款已经交清");}
		if("${messageCode}"=="STUENROLL_HAPPEND"){jqueryUIAlert("操作失败，该交易已经存在上课记录,不能进行作废操作!");}
		if("${messageCode}"=="IS_EXIST"){jqueryUIAlert("操作失败，数据已经存在");}
		if("${messageCode}"=="BIORGAN_PRESTR_IS_EXIST"){jqueryUIAlert("操作失败，校区前缀字段已经存在,请重新确认以后重试");}
		
		if("${messageCode}"=="STU_IS_IN_CLASS"){jqueryUIAlert("操作失败，该学生已经存在您所选择的班级中，不能进行定金交费操作，请重新选班后重试！");}
		if("${messageCode}"=="STUEARNEST_IS_USED"){jqueryUIAlert("操作失败，您所操作的定金业务已经被报名使用，不能进行作废或者退费操作！");}
		if("${messageCode}"=="STUEARNEST_IS_NOT_DEFAULT"){jqueryUIAlert("操作失败，您所操作的定金业务已经作废或者退费，不能再次进行作废或者退费操作！");}
		if("${messageCode}"=="stu is not in class"){jqueryUIAlert("操作失败，该学生已经不在该班级！请检查该生是否已经退学或、转出或者休学");}
		if("${messageCode}"=="STUENROLL_IS_NOT_DEFAULT"){jqueryUIAlert("操作失败，您所操作的报名收费业务已经作废或者退费，不能再次进行作废或者退费操作！");}
		if("${messageCode}"=="OWN_FEE_IS_EXIST"){jqueryUIAlert("操作失败，您所操作学生存在欠款，不能进行退费操作！");}
		
		if("${messageCode}"=="TPC_STU_ATTEND_ENROLL_ISUSED"){jqueryUIAlert("操作失败，您所修改的考勤相关的交费记录金额已经用完，不能进行修改操作！");}
		if("${messageCode}"=="TM_CLASS_NATURE_LEAVE"){jqueryUIAlert("操作失败，您所操作的学生还有没使用完的收费记录或者存在欠款，不能进行自然退学操作！");}
	});
	
	function jqueryUIAlert(msg){
		$('#msgDialog').append("<div id=\"dialog-message\" title=\"系统消息\"></div>");
		$('#dialog-message').empty();
		$('#dialog-message').append("<p><span class=\"ui-icon ui-icon-circle-check\" style=\"float: left; margin: 0 7px 20px 0;\"></span>");
		$('#dialog-message').append(msg);
		$('#dialog-message').append("</p>");
		$("#dialog-message").dialog({
			modal: true,
            buttons: {
                "确定": function(){
                    $(this).dialog("close");
					back();
                }
            }
        });
	}
	
	
</script>
<div id="loadding" style="display:none; background-color:white; z-index:9999;padding:5px;text-align:center; color:black;  position:absolute;top:40%;right:40%;width:20%;height:30px;line-height:30px;">
 	正在努力加载数据中，请等待……
</div>
<div id="msgDialog"></div>
<div id="fullbg" style=" background-color: Gray; display: none; z-index: 9998; position: absolute; left: 0px; top: 0px; width:100%; height:100%; filter: Alpha(Opacity  = 30);-moz-opacity: 0.4; opacity: 0.4;" >
</div>
