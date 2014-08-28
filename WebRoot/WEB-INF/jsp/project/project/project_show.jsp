<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script src="http://code.jquery.com/jquery-1.7.2.min.js">
        </script>
        <script type="text/javascript">
            var treeObj;
            
            $(document).ready(function(){
                treeObj = window.parent.treeObj;
                initPage();
                initTab();
            });
            function initPage(){
                if ("${projectVO.depth}" == "1") {
                    $("#depth").empty();
                }
                
            }
            function initTab(){
                $("#content").find("[id^='tab']").hide(); // Hide all content
                $("#tabs li:first").attr("id", "current"); // Activate the first tab
                $("#content #tab1").fadeIn(); // Show first tab's content
                $('#tabs a').click(function(e){
                    e.preventDefault();
                    if ($(this).closest("li").attr("id") == "current") { //detection for current tab
                        return;
                    }
                    else {
                        $("#content").find("[id^='tab']").hide(); // Hide all content
                        $("#tabs li").attr("id", ""); //Reset id's
                        $(this).parent().attr("id", "current"); // Activate this
                        $('#' + $(this).attr('name')).fadeIn(); // Show content for the current tab
                    }
                });
            }
            
            /**
             * 功能：添加项目
             * @param {Object} id
             */
            function addProject(id){
                winload("${path}/main/project/add/view.do?id=" + id);
                parent.initProjectTree();
            }
            
            /**
             * 功能：更新项目
             * @param {Object} id
             */
            function updateProject(id){
                winload("${path}/main/project/update/view.do?id=" + id);
                parent.initProjectTree();
            }
            
            /**
             * 功能：删除项目
             * @param {Object} id
             */
            function deleteProject(){
                if (confirm("确认要删除吗?")) {
					return true;
                   // winload("${path}/main/project/delete.do?id=" + id);
                    //parent.initProjectTree();
                }
				return false
            }
            
			
			
            /**
             * 功能：添加人员
             * @param {Object} id
             */
            function addUser(id){
                winload("${path}/main/projectUser/add/view.do?id=" + id);
                parent.initProjectTree();
            }
            /**
             * 功能：添加模块
             * @param {Object} id
             */
            function addModule(id){
                winload("${path}/main/projectModule/add/view.do?id=" + id);
                parent.initProjectTree();
            }
            /**
             * 功能：添加版本
             * @param {Object} id
             */
            function addVersion(id){
                winload("${path}/main/projectVersion/add/view.do?id=" + id);
                parent.initProjectTree();
            }
			
			function editVersion(id){
				$("#"+id).empty();
				updateRowIndex("versionTab");
			}
			
			function editModule(id){
				
			}
			
			//删除版本
            function delVersion(id,index){
                if (confirm("确认要删除吗?")) {
					doDeleteAjax(id,"Version",index);
                    parent.initProjectTree();
                }
            }
			//删除模块
			function delModule(id,index){
                if (confirm("确认要删除吗?")) {
					doDeleteAjax(id,"Module",index);
                    parent.initProjectTree();
                }
            }
			//删除用户
            function delUser(id,index){
                if (confirm("确认要删除吗?")) {
					
					doDeleteAjax(id,"User",index);
                    parent.initProjectTree();
                }
            }
			
			
			//删除员工，模块，版本。
			function doDeleteAjax(id,str,index){
				var alertValue;
				var version;
				var user;
				var module;
				if(str=="User"){
					user=id;
					alertValue="此员工存在被分配的BUG╭(╯3╰)╮,删除失败";
				}else if(str=="Version"){
					version=id;
					alertValue="此版本下存在关联的BUG╭(╯3╰)╮,删除失败";
				}else{
					module=id;
					alertValue="改模块下存在关联的Bug ╭(╯3╰)╮,删除失败";
				}
				
				
                $.ajax({
                    beforeSend: function(){
                        showLoading();
                    },
					data: {"userHandler.id" :user,"projectVersion.id":version,"projectModule.id":module,"id":id},
                    url: "${path}/main/project" + str + "/ajaxDelte.do",
                    type: 'GET',
                    dataType: 'json',
                    error: function(XMLHttpRequest, textStatus, errorThrown){
                        //alert(XMLHttpRequest.status);
                    },
                    success: function(data){
                        hideLoading();
                        $(data).each(function(i, item){
							if(item.type=="01"){
                                $("#" + id).empty();
                                updateRowIndex(str+"Tab",index);
							}else{
								alert(alertValue);
							}
							
                        });
                    },
                    complete: function(XMLHttpRequest, textStatus){
                    },
                })
				
				
			}
			/**
			 * 删除时候更改 序列号
			 * @param {Object} str
			 */
            function updateRowIndex(str,index){
				var index=index-1;
                $('#' + str).find('tr').each(function(i, item){
					if (index<i) {
						$(item).find("td:eq(0)").html(i - 1);
					}
                });
            }
        </script>
    </head>
    <body style="background:white ">
        <input type="hidden" id="idHid" value="${biMenu.id}" />
        <div id="frame-div" class="width-p100">
            <div class="title">
                <table class="title-tb" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="td-left">
                        </td>
                        <td class="td-title left">
                            <fmt:message key="project.table_title" /><fmt:message key="btn.view"/>
                        </td>
                        <td class="td-btn">
                            <c:choose>
                                <c:when test="${projectVO.depth==1}">
                                    <a class="btn btn-default "href="javaScript:addProject('${projectVO.id}')">新增项目</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-default" href="javaScript:updateProject('${projectVO.id}')">修改</a>
                                    <a class="btn btn-default" href="${path}/main/project/delete.do?id=${projectVO.id}" target="_top" onclick="return deleteProject()" >删除</a>
                                    <a class="btn btn-default" href="javaScript:addUser('${projectVO.id}')"><span class="glyphicon glyphicon-plus"></span>人员</a>
                                    <a class="btn btn-default" href="javaScript:addVersion('${projectVO.id}')"><span class="glyphicon glyphicon-plus"></span>版本</a>
                                    <a class="btn btn-default" href="javaScript:addModule('${projectVO.id}')"><span class="glyphicon glyphicon-plus"></span>模块</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="td-right">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="margin-lr-1 main-page-40 over-flow-x-hidden" id="depth">
                <table class="add-tb">
                    <tr>
                        <td class="td-title" colspan="4">
                            <font size=2 color="black">
                                <i class="icon icon-chevron-down"></i>
                                <strong>项目信息</strong>&nbsp;&nbsp;&nbsp; 
                            </font>
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd4">
                          	 项目名称
                        </td>
                        <td class="rtd4">
                        	${projectVO.project.name}
                        </td>
                        <td class="ltd4">
                          	 项目代号
                        </td>
                        <td class="rtd4">
                           ${projectVO.project.codeNumber}
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd4">
                         	  项目周期
                        </td>
                        <td class="rtd4">
                        	 (${projectVO.project.beginDateString}-- ${projectVO.project.endDateString})
                        </td>
                        <td class="ltd4">
                          	项目负责人
                        </td>
                        <td class="rtd4">
                         <abbr title="Name">N:</abbr><strong> ${projectVO.project.managerUser.name}</strong><br>
						   <abbr title="Phone">P:</abbr> ${projectVO.project.managerUser.phone}  
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <ul id="tabs">
                                <li>
                                    <a href="#" name="tab1">人员信息</a>
                                </li>
                                <li>
                                    <a href="#" name="tab2">版本信息</a>
                                </li>
                                <li>
                                    <a href="#" name="tab3">模块信息</a>
                                </li>
                            </ul>
							
                        </td>
						
						 <td class="ltd4">
                          	项目描述contents
                        </td>
                        <td class="rtd4">
                        	<c:set var="content" value="${projectVO.project.content}"/>
							<abbr title="${projectVO.project.content}"> 
						<c:out value="${fn:substring(content,0,20)}"/><br> 
						</td>
						
						
                    </tr>
                </table>
				
                <div id="content">
                	
                <div id="tab1">
                    <table class="add-tb">
                        <tr>
                            <td class="td-title">
                                <font size=2 color="black">
                                    <i class=" icon-chevron-down"></i>
                                    <strong>项目人员</strong>&nbsp;&nbsp;&nbsp; 
                                </font>
                            </td>
                        </tr>
                        <tr>
                            <td class="rtd4" colspan="4">
                                <div class="margin-lr-1">
                                <c:if test='${fn:length(projectVO.userList)<1}'>
                                    <div class="no-data-div">
                                        	项目木有添加人员╮(╯▽╰)╭
                                    </div>
                                </c:if>
							 	<c:if test='${fn:length(projectVO.userList)>0}'>
							 		<c:if test='${fn:length(projectVO.userList)>0}'>
		                                <table  class="list-tb width-p100 table table-hover" id="UserTab">
		                                        <tr>
		                                            <th></th>
		                                            <th>用户</th>
		                                            <th> 角色</th>
		                                            <th>加盟日</th>
		                                            <th><fmt:message key="list.columns.do" /></th>
		                                        </tr>
                                                <c:forEach var="item" items='${projectVO.userList}' varStatus="s">
                                                    <tr id="${item.id}">
                                                      	<td>${s.index+1}</td>
			                                            <td>${item.name}</td>
			                                            <td>${item.biRole.name}</td>
														<td>${item.createDateString} </td>
														<td>
														  <a class="btn btn-default btn-lg " href="javascript:delUser('${item.id}','${s.index+1}')"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
														</td>
                                                    </tr>
                                                </c:forEach>
		                                </table>
									</c:if>
								</c:if>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                	
               
                <div id="tab2">
                    <table class="add-tb">
                        <tr>
                            <td class="td-title">
                                <font size=2 color="black">
                                    <i class=" icon-chevron-down"></i>
                                    <strong>项目版本</strong>&nbsp;&nbsp;&nbsp; 
                                </font>
                            </td>
                        </tr>
                        <tr>
                            <td class="rtd4" colspan="4">
                                <div class="margin-lr-1">
                                <c:if test='${fn:length(projectVO.versionList)<1}'>
                                    <div class="no-data-div">
                                        	项目木有添加版本╮(╯▽╰)╭
                                    </div>
                                </c:if>
							 	<c:if test='${fn:length(projectVO.versionList)>0}'>
							 		<c:if test='${fn:length(projectVO.versionList)>0}'>
		                                <table  class="list-tb width-p100 table table-hover" id="VersionTab">
		                                        <tr>
		                                            <th></th>
		                                            <th>版本</th>
		                                            <th><fmt:message key="list.columns.do" /></th>
		                                        </tr>
                                                <c:forEach var="item" items='${projectVO.versionList}' varStatus="s">
                                                    <tr id="${item.id}">
                                                      	<td>${s.index+1}</td>
			                                            <td id="${item.id}_Vname">${item.name}</td>
														<td>
														   <a class="btn btn-default btn-lg " href="javascript:editVersion('${item.id}')"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>
														  &nbsp;&nbsp;&nbsp;
														  <a class="btn btn-default btn-lg " href="javascript:delVersion('${item.id}','${s.index+1}')"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
														</td>
                                                    </tr>
                                                </c:forEach>
		                                </table>
									</c:if>
								</c:if>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                	
               
                <div id="tab3">
                    <table class="add-tb">
                        <tr>
                            <td class="td-title">
                                <font size=2 color="black">
                                    <i class=" icon-chevron-down"></i>
                                    <strong>项目模块</strong>&nbsp;&nbsp;&nbsp; 
                                </font>
                            </td>
                        </tr>
                        <tr>
                            <td class="rtd4" colspan="4">
                                <div class="margin-lr-1">
                                <c:if test='${fn:length(projectVO.moduleList)<1}'>
                                    <div class="no-data-div">
                                        	项目木有添加模块╮(╯▽╰)╭
                                    </div>
                                </c:if>
							 	<c:if test='${fn:length(projectVO.moduleList)>0}'>
							 		<c:if test='${fn:length(projectVO.moduleList)>0}'>
		                                <table  class="list-tb width-p100 table table-hover" id="ModuleTab">
		                                        <tr>
		                                            <th></th>
		                                            <th>模块</th>
		                                            <th>处理人</th>
		                                            <th><fmt:message key="list.columns.do" /></th>
		                                        </tr>
                                                <c:forEach var="item" items='${projectVO.moduleList}' varStatus="s">
                                                    <tr id="${item.id}">
                                                      	<td>${s.index+1}</td>
			                                            <td id="${item.id}_Mname">${item.name}</td>
			                                            <td id="${item.id}_dealUser">${item.dealUser.name}</td>
														<td>
														  <a class="btn btn-default btn-lg " href="javascript:editModule('${item.id}')"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>
														  &nbsp;&nbsp;&nbsp;
														  <a class="btn btn-default btn-lg " href="javascript:delModule('${item.id}','${s.index+1}')"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
														</td>
                                                    </tr>
                                                </c:forEach>
		                                </table>
									</c:if>
								</c:if>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                	
               
					
                   
					
                </div>
				
            </div>
        </div>
    </body>
</html>
