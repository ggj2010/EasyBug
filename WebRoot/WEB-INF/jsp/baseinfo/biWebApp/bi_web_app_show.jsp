
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
模块名：		module添加页面
说明：		module添加页面
编写者：     	<author>
编写日期：	<2012/5/20>
修改信息：
-->
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
    </head>
    <body>
    <div id="main-div" class="width-p100">
        <t:Top></t:Top>
        <div class="content main-page-190">
            <div class="title">
                <table class="title-tb" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="td-left">
                        </td>
                        <td class="td-title">
                            <fmt:message key="biWebApp.table_title" /><fmt:message key="btn.show" />
                        </td>
                        <td class="td-btn">
                        </td>
                        <td class="td-right">
                            <a href="javascript:back();" class="btn2 btn-small"><fmt:message key="btn.back" /></a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="margin-lr-1">
                <div class="main-page-270 over-flow-x-hidden">
                    <table class="add-tb">
                    	<tr>
							<td class="td-title" colspan="4">
								<font size=2 color="black"> <i class=" icon-chevron-down"></i> 
								<strong>biWebApp信息</strong>&nbsp;&nbsp;&nbsp; </font>
							</td>
						</tr>
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.name" />:
							</td>		
							<td class="rtd2">
${biWebApp.name}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.url" />:
							</td>		
							<td class="rtd2">
${biWebApp.url}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.flag" />:
							</td>		
							<td class="rtd2">
${biWebApp.flag}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.openStyle" />:
							</td>		
							<td class="rtd2">
${biWebApp.openStyle}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.describes" />:
							</td>		
							<td class="rtd2">
${biWebApp.describes}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.image" />:
							</td>		
							<td class="rtd2">
${biWebApp.image}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.briefContent" />:
							</td>		
							<td class="rtd2">
${biWebApp.briefContent}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.createDate" />:
							</td>		
							<td class="rtd2">
${biWebApp.createDateString}						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.createUser" />:
							</td>		
							<td class="rtd2">
${biWebApp.createUser}						
							</td>
						</tr>	
						
                    </table>
                </div>
            </div>
        </div>
        <t:Footer></t:Footer>
    </div>
</body>
</html>
