<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
request.setAttribute("sum", request.getSession().getAttribute("sum")); 
request.setAttribute("sumList", request.getSession().getAttribute("notFixed")); 
request.setAttribute("sumList", request.getSession().getAttribute("solved")); 
request.setAttribute("sumList", request.getSession().getAttribute("fixed")); 
%>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
        <script type="text/javascript">
            $(document).ready(function(){
				$("#projectUser").val("${userRole.id}");
            })
			
			function changeProject(){
				var id=$("#projectUser").val();
				winload("${path}/main/changeRole/save.do?id=" + id);
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
                     <select class="Select" onchange="changeProject();" id="projectUser">
                         <c:forEach var="item" items='${projectUserList}' varStatus="s">
                             <option value="${item.id}">${item.project.name}</option>
                         </c:forEach>
                     </select>
	                 </span>
				     <t:Menu>
                     </t:Menu>
                       <div class="Contents">
                           <div class="Right Sub Sar">
                               <div class="Bugicon Midbox">
                                	   项目概况
                               </div>
                               <div class="SearchBox">
                               <div class="bugs-total">Bug总数:<span class="num">${sum}</span></div>
                               <img src="${path}/themes/images/line.gif" class="SLine" />
                               	 <div class="bugs-unfix" >未修复的Bug数:<span class="num">${notFixed}</span></div>
                                       <img src="${path}/themes/images/line.gif" class="SLine" /><div class="bugs-approval">待审核的Bug数:<span class="num">${solved}</span></div>
                                       <img src="${path}/themes/images/line.gif" class="SLine" /><div href="javascript:SubForm(3)" class="bugs-fix">已解决的Bug数:<span class="num">${fixed}</span></div>
                                       <img src="${path}/themes/images/line.gif" class="SLine" />
                                  <a href="${path}/main/bugContent/add/view.do" class="button-create" style="margin-top: 10px;"><span>提交新Bug</span></a>
                                 </div>
								 </div>
                       </div>
                   </div>
            </div>
				<t:Footer></t:Footer>
        </div>
    </body>
</html>
