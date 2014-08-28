<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>

        <script type="text/javascript">
            $(document).ready(function(){
				if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                }
				initPage();
            })
			
			function initPage(){
					
					$("input[name='level'][value=${bugContent.level}]").attr("checked",true); 
                    
					
					$('#projectVersion').val("${bugContent.projectVersion.id}");
					$('#projectModule').val("${bugContent.projectModule.id}");
					$('#userAssigner').val("${bugContent.userAssigner.id}");
			}
			
        </script>
		 
    </head>
    <body>
        <div id="main-div" class="width-p100">
        	    <t:Top>
            </t:Top>
            <div class="content">
				 <div class="SubHeader">
	                 <span class="left"><img src="${path}/themes/images/dog.png" class="BlueBorder icon_biger user-logo-define" />
                     <c:forEach var="item" items='${projectUserList}' varStatus="s">
	                     <select class="Select" onchange="ChangePrj();">
	                         <option value="${item.project.id}" selected='selected'>${item.project.name}</option>
	                     </select>
						 </c:forEach>
	                 </span>
                     <div class="right">
                         <ul class="Menus">
                             <li>
                                 <a href="/Project/ProjectInfo/10105" class="menu1">项目概况</a>
                             </li>
                             <li>
                                 <a href="/Bug/MyBug/10105" class="menu2">分配给我的Bug</a>
                             </li>
                             <li>
                                 <a href="/Bug/MyCreateBug/10105" class="menu3">我创建的Bug</a>
                             </li>
                             <li>
                                 <a href="/Bug/AllBug/10105" class="menu4 ">所有Bug</a>
                             </li>
                             <li>
                                 <a href="/Bug/BugStat/10105" class="menu5 ">Bug统计</a>
                             </li>
                         </ul>
                     </div>
                     <div class="Contents">
                         <div class="Right Sub Sar">
                             <div class="Bugicon Midbox">
                                 	项目:${projectVO.project.name}
                             </div>
                         </div>
                         <div class="Solution Insert ">
                         <form action="${path}/main/bugContent/update/save.do" method="post">
                         	<input type="hidden" name="id" value="${bugContent.id}">
                             <table class="width-p100">
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">标题</span>
                                             <input type="text" class="form-control" required="required" name="name" value="${bugContent.name}">
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">版本</span>
                                             <select class="form-control" name="projectVersion.id" id="projectVersion">
                                                 <option>--请选择--</option>
                                                 <c:forEach var="item" items='${projectVO.versionList}' varStatus="s">
                                                     <option value="${item.id}">${item.name}</option>
                                                 </c:forEach>
                                             </select>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">模块</span>
                                             <select class="form-control" name="projectModule.id" id="projectModule">
                                                 <option>--请选择--</option>
                                                 <c:forEach var="item" items='${projectVO.moduleList}' varStatus="s">
                                                     <option value="${item.id}">${item.name}</option>
                                                 </c:forEach>
                                             </select>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">级别</span>
                                             &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" value="01" name="level" >低&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="02" name="level" >中&nbsp;&nbsp;&nbsp;<input type="radio" value="03" name="level" >高&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="04" name="level" >紧急&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="05" name="level" >严重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">审核人</span>
                                             <select class="form-control" name="userAssigner.id" id="userAssigner">
                                                 <option>--请选择--</option>
                                                 <c:forEach var="item" items='${projectVO.userList}' varStatus="s">
                                                     <option value="${item.id}">${item.name}</option>
                                                 </c:forEach>
                                             </select>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">描述与截图</span>
                                             <textarea name="describe" cols="20" rows="2" class="ckeditor">
                                             	${bugContent.describe}
                                             </textarea>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td>
                                         <div class="center">
                                             <button type="submit" class="btn btn-default">
                                                 <fmt:message key="btn.save"/>
                                             </button>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                                         </div>
                                     </td>
                                 </tr>
                             </table>
                         </form>
							 
							 
                         </div>
						 
						 
                     </div>
					 
					 
					 
                     </div>
                    
                    
           
            
            </div>
				<t:Footer></t:Footer>
        </div>
    </body>
</html>
