﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
模块名：	module查询列表页面
说明：		module查询列表页面
编写者：     	<author>
编写日期：	<2012/5/20>
修改信息：
-->
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                autoHeight($('#autoHeightDiv'), 296);
                $("#dialogDiv").dialog({
                    height: 600,
                    width: 700,
                    autoOpen: false,
                    modal: true,
                });
			
            });
            
            function doQuery(){
                $('#form1').submit();
            }
            
            function update(id){
                winload("${path}/main/biWebApp/update/view.do?id=" + id);
            }
            
            function del(id){
                if (confirm("确认删除")) {
                    winload("${path}/main/biWebApp/delete.do?id=" + id);
                }
            }
   
	
            function openSelectDialog(url){
                var htmlStr = "<iframe style=\"width:750px;height:600px;\" src=\"" + url + "\"> </iframe>";
                $("#dialogDiv").empty();
                $("#dialogDiv").append(htmlStr);
				
                $("#dialogDiv").dialog("open");
                
            }
			
        </script>
        <style>
            .active a {
                color: blue;
                font-weight: bold;
            }
            
            .button-c {
                padding: 1px 3px;
                border: 1px solid gray;
                background: #e4e4ef;
                color: black;
                text-decoration: none
            }
            
            .error {
                color: red;
                font-size: 14px
            }
            
            .success {
                color: green;
                font-size: 14px
            }
            
            .exttable tr {
                padding: 5px 0px
            }
            
            .exttable td {
                padding: 1px 5px 1px 10px
            }
            
            .exttable caption {
                background: #efefef;
                border-color: #CCC9C9;
                margin-left: 0
            }
            
            .exttable {
                border-color: #CCC9C9;
                border-top: none;
            }
            
            .exttable td {
                border-color: #CCC9C9
            }
            
            #webapps li {
                float: left;
                width: 25%;
                list-style: none
            }
            
            #webapps li table {
                border: 0px;
                width: 95%;
                border: 1px solid #ddd;
            }
            
            #webapps li table .webapp-name {
                font-size: 14px;
                color: #039;
                padding-top: 2px;
            }
            
            #webapps li table .webapp-icon {
                padding: 5px;
                vertical-align: middle;
                text-align: center
            }
            
            #webapps li table .webapp-info {
                color: #888;
                padding-top: 2px;
                height: 32px;
                white-space: pre-wrap;
            }
            
            #webapps li table .webapp-actions {
                padding-top: 5px;
                padding-bottom: 5px;
            }
            
            #webapps li table .webapp-actions .button-c {
                font-size: 12px;
            }
            
            small {
                font-weight: normal
            }
            
            #webapps .button-c {
                min-width: 0;
            }
        </style>
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
                                <fmt:message key="biWebApp.table_title" /><fmt:message key="btn.search" />
                            </td>
                            <td class="td-btn">
                            	<c:if test="${fn:contains(btnStr, 'q1')}" >
                                <a class="btn btn-default" href="${path}/main/biWebApp/add/view.do"><fmt:message key="btn.add"/></a>
                           			</c:if>
						    </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="margin-lr-1">
                    <table class="search-tb width-p100">
                        <form id="form1" action="${path}/main/biWebApp/list.do" method="get">
                            <tr>
                                <td class="left">
                                    <span class="glyphicon glyphicon-search left">查询选项</span>
                                </td>
                                <td class="center">
                                    <table class="row">
                                        <tr>
                                            <td class="col-xs-1">
                                                <div class="input-group">
                                                    <span class="input-group-addon">应用名称</span>
                                                    <input type="text" class="form-control" id="keyTxt" name="name">
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td class="td-btn">
                                    <a class="btn btn-default" href="javaScript:doQuery()"><fmt:message key="btn.search"/></a>
                                </td>
                            </tr>
                        </form>
                    </table>
                    <div id="autoHeightDiv" class="over-flow-x-hidden">
                        <c:if test="${pagedData.totalPageCount<1}">
                            <div class="no-data-div">
                                <fmt:message key="msg.no_data" />
                            </div>
                        </c:if>
                        <c:if test="${pagedData.totalPageCount>0}">
                        <tr valign='top'>
                            <td class='divider'>
                            </td>
                            <td>
                                <ul id='webapps'>
                                    <c:forEach var="item" items='${pagedData.result}' varStatus="s">
                                        <li>
                                            <table class='fixed exttable'>
                                                <tr>
                                                    <td rowspan='3' width='73' height='73' class='webapp-icon'>
                                                        <img src='${path}/${item.image}' width='72' height='72' />
                                                    </td>
                                                    <td class='webapp-name' title='${item.name}'>
                                                        ${item.name}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign='top'>
                                                       <div class='webapp-info' title='${item.briefContent}'>${item.briefContent}</div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div class='webapp-actions'>
                                                            <button class='btn btn-info' onclick="openSelectDialog('${item.url}')">运行</a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </div>
                    </c:if>
                </div>
                <div id="dialogDiv">
					</div>
                <t:PageBar pageUrl="${path}/main/biWebApp/list.do" pageAttrKey="pagedData"/>
            </div>
			  <t:Footer>
        </t:Footer>
        </div>
        </div>
    </body>
</html>
