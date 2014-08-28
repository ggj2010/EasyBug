<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#form1").validate();
                
                if ("${messageCode}" == "01") {
                    parent.initTree();
                }
            });
			
			
			
            
            /**
             * 功能：添加机构
             * @param {Object} id
             */
            function addBiMenu(id){
                winload("${path}/main/biMenu/add/view.do?id=" + id);
            }
            
            /**
             * 功能：更新机构
             * @param {Object} id
             */
            function updateBiMenu(id){
                winload("${path}/main/biMenu/update/view.do?id=" + id);
            }
			
			
            function deleteMenu(){
                if (confirm("确认要删除吗?")) {
                    return true;
                }
                return false
            }
            
			
        </script>
    </head>
    <body style="background:white; min-width:800px;">
        <input type="hidden" id="idHid" value="${biMenu.id}" />
        <div id="frame-div" class="width-p100">
            <div class="title">
            	<table class="title-tb" cellpadding="0" cellspacing="0">
            		<tr>
            			<td class="td-left"></td>
            			<td class="td-title">
            				<fmt:message key="biMenu.table_title" /><fmt:message key="btn.view"/>
						</td>
            			<td class="td-btn">
            				<a class="btn btn-default" href="javaScript:addBiMenu('${biMenu.id}')"><fmt:message key="btn.tree.addnext"/></a>
							<a class="btn btn-default" href="javaScript:updateBiMenu('${biMenu.id}')"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>
							<a class="btn btn-default" href="${path}/main/biMenu/delete.do?id=${biMenu.id}" target="_top" onclick="return deleteMenu()">删除</a>
						</td>
						<td class="td-right"></td>
            		</tr>
            	</table>
            </div>
            <div class="margin-lr-1 main-page-40 over-flow-x-hidden">
                <table class="add-tb">
                    <tr>
                        <td class="td-title" colspan="2">
                            <font size=2 color="black">
                                <i class="icon icon-chevron-down"></i>
                                <strong>菜单信息</strong>&nbsp;&nbsp;&nbsp;
                            </font>
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.name" />
                        </td>
                        <td class="rtd2">
                            ${biMenu.name}
                        </td>
					</tr>
					<tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.sequence" />
                        </td>
                        <td class="rtd2">
                            ${biMenu.sequence}
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.url" />
                        </td>
                        <td class="rtd2">
                            ${biMenu.url}
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.menuType" />
                        </td>
                        <td class="rtd2">
                            <c:if test="${biMenu.menuType=='01'}">
								 菜单夹
                            </c:if>
                            <c:if test="${biMenu.menuType=='02'}">
								菜单
                            </c:if>
                            <c:if test="${biMenu.menuType=='03'}">
								按钮
                            </c:if>
                            <c:if test="${biMenu.menuType=='04'}">
								按钮组
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.buttonCode" />
                        </td>
                        <td class="rtd2">
                            ${biMenu.buttonCode}
                        </td>
                    </tr>
                    <tr>
                        <td class="ltd2">
                            <fmt:message key="biMenu.memo" />
                        </td>
                        <td class="rtd2">
                            ${biMenu.memo}
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
