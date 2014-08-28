<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
            
            });
            
            function changeJob(id){
                if (confirm("确认切换到该岗位?")) {
                    winload("${path}/main/changeRole/save.do?id=" + id);
                }
            }
        </script>
    </head>
    <body>
        <div id="main-div" class="width-p100">
            <t:Top>
            </t:Top>
            <div class="content main-page-190">
                <div class="title">
                    <table class="title-tb" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="td-left">
                            </td>
                            <td class="td-title">
                                	切换岗位
                            </td>
                            <td class="td-btn">
                            </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="margin-lr-1">
                    <div class="main-page-270 over-flow-x-hidden">
                        <table class="add-tb">
                            <tr>
                                <td class="td-title" colspan="4">
                                    <font size=2 color="black">
                                        <i class=" icon-chevron-down"></i>
                                        <strong>员工信息</strong>&nbsp;&nbsp;&nbsp; 
                                    </font>
                                </td>
                            </tr>
                            <tr>
							    <td class="ltd4">
									<fmt:message key="biUserInfo.name" />
								</td>
								<td class="rtd4">
										${biUserInfo.name}
								</td>
								<td class="ltd4">
									<fmt:message key="biUserInfo.sex" />
								</td>
								<td class="rtd4">
									<c:if test="${biUserInfo.sex=='b'}">男</c:if>
									<c:if test="${biUserInfo.sex=='g'}">女</c:if>
								</td>
							</tr>
							<tr>
							    <td class="ltd4">
									<fmt:message key="biUserInfo.email" />
								</td>
								<td class="rtd4">
									${biUserInfo.email}
								</td>
								 <td class="ltd4">
									<fmt:message key="biUserInfo.phone" />
								</td>
								<td class="rtd4">
									${biUserInfo.phone}
								</td>
							</tr>
                            <tr>
                                <td class="rtd4" colspan="4">
                                <div class="margin-lr-1">
                                <c:if test='${fn:length(roleList)<1}'>
	                                <div class="no-data-div">
	                                   	 该员工没有任何岗位!
	                                </div>
	                            </c:if>
                                <table class="list-tb table table-hover" width="100%">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>项目</th>
                                            <th>岗位</th>
                                            <th>状态</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${roleList}" varStatus="s">
                                            <tr>
                                                <td class="tb-left-bg" style="text-align: center">
                                                    ${s.index+1}
                                                </td>
                                                <td class="width-p30">
                                                    ${item.project.name}
                                                </td>
                                                <td class="width-p30">
                                                    ${item.biRole.name}
                                                </td>
                                                <td class="width-p20">
                                                    <c:if test="${item.isDefault=='N'}">非默认</c:if>
                                                    <c:if test="${item.isDefault=='Y'}">默认</c:if>
                                                </td>
                                                <td class="width-p20">
                                                    <a class="btn btn-default" href="javascript:changeJob('${item.id}');"><span class="glyphicon glyphicon-screenshot">切换</span></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                </div>
                <t:Footer>
                </t:Footer>
            </div>
            </body>
        </html>
