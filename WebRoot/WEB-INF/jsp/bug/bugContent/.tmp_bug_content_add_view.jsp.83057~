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
            })
        </script>
		 
    </head>
    <body>
        <div id="main-div" class="width-p100">
        	    <t:Top>
            </t:Top>
            <div class="content">
				 <div class="SubHeader">
	                 <span class="left"><img src="${path}/themes/images/dog.png" class="BlueBorder icon_biger user-logo-define" />
                  
	                     <select class="Select" onchange="ChangePrj();">
	                     	   <c:forEach var="item" items='${projectUserList}' varStatus="s">
	                         <option value="${item.project.id}" selected='selected'>${item.project.name}</option>
							 </c:forEach>
	                     </select>
						
	                 </span>
              <t:Menu>
                     </t:Menu>
                     <div class="Contents">
                         <div class="Right Sub Sar">
                             <div class="Bugicon Midbox">
                                 	项目:${projectVO.project.name}
                             </div>
                         </div>
                         <div class="Solution Insert ">
                         <form action="${path}/main/bugContent/add/save.do" method="post">
                         	<input type="hidden" name="project.id" value="${projectVO.project.id}">
                             <table class="width-p100">
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">标题</span>
                                             <input type="text" class="form-control" required="required" name="bugContentList[0].name">
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">版本</span>
                                             <select class="form-control" name="bugContentList[0].projectVersion.id">
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
                                             <select class="form-control" name="bugContentList[0].projectModule.id">
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
                                             &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" value="01" name="bugContentList[0].level">低&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="02" name="bugContentList[0].level">中&nbsp;&nbsp;&nbsp;<input type="radio" value="03" name="bugContentList[0].level">高&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="04" name="bugContentList[0].level">紧急&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="05" name="bugContentList[0].level">严重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">审核人</span>
                                             <select class="form-control" name="bugContentList[0].userAssigner.id">
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
                                             <textarea name="bugContentList[0].describe" cols="20" rows="2" class="ckeditor">
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
