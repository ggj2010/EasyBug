﻿
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	 <script type="text/javascript">
		$(document).ready(function() {
			$('#form1').validate();
			if("??{messageCode}"=="01"){
				jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
			}
		});	
		
		function onSubmit() {
			$('#form1').submit();
		}
	</script>
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
                            <fmt:message key="biWebApp.table_title" /><fmt:message key="btn.add"/>
                        </td>
                        <td class="td-btn">
                        </td>
                        <td class="td-right">
                        </td>
                    </tr>
                </table>
            </div>
			<div class="margin-lr-1">
                <div class="main-page-230 over-flow-x-hidden">
                	<form id="form1" action="??{path}/main/biWebApp/update/save.do" method="post" >
					<table class="add-tb">
						<tr>
							<td class="td-title" colspan="4">
								<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
								<strong>biWebApp信息</strong>&nbsp;&nbsp;&nbsp; </font>
							</td>
						</tr>
						<input type="hidden" id="id" name="id" value="biWebApp.id"/>
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.name" />:
							</td>		
							<td class="rtd2">
					    <input id="nameTxt" type="text" autocomplete="off"  name="name" class="width-p60" maxlength="200" value="${biWebApp.name}"/>
						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.url" />:
							</td>		
							<td class="rtd2">
					    <input id="urlTxt" type="text" autocomplete="off"  name="url" class="width-p60" maxlength="65535" value="${biWebApp.url}"/>
						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.describes" />:
							</td>		
							<td class="rtd2">
					    <input id="describesTxt" type="text" autocomplete="off"  name="describes" class="width-p60" maxlength="1" value="${biWebApp.describes}"/>
						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.image" />:
							</td>		
							<td class="rtd2">
					    <input id="imageTxt" type="text" autocomplete="off"  name="image" class="width-p60" maxlength="1" value="${biWebApp.image}"/>
						
							</td>
						</tr>	
						<tr>	
							<td class="ltd2">
								<fmt:message key="biWebApp.briefContent" />:
							</td>		
							<td class="rtd2">
					    <input id="briefContentTxt" type="text" autocomplete="off"  name="briefContent" class="width-p60" maxlength="1" value="${biWebApp.briefContent}"/>
						
							</td>
						</tr>	
					
						<tr>
							<td style="text-align: center;" colspan="2">
								<a href="javascript:onSubmit();" class="btn1 btn-small"><fmt:message key="btn.save" /></a>
								<a href="javascript:back();" class="btn1 btn-small"><fmt:message key="btn.back" /></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<t:Footer></t:Footer>
	</div>
</body>
</html>
