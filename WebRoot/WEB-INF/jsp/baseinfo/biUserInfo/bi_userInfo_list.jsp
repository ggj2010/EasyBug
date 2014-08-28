﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
        <script type="text/javascript">
            $(document).ready(function(){
				
				autoHeight($('#autoHeightDIV'),295);
				initAutoComplete();
				 $("#keyTxt").val("${biUserInfo.name}");
            })
			
            function initAutoComplete(){
                $("#keyTxt").autocomplete({
                    minLength: 1,
                    source: function(request, response){
                        var key = $("#keyTxt").val();
                        $.ajax({
                            beforeSend: function(){
                                showLoading();
                            },
                            cache: false,
                            type: 'post',
                            async: false,
                            data: {
                                "key": key
                            },
                            url: '${path}/biUserInfo/getAjaxUserList.do',
                            dataType: 'json',
                            error: function(XMLHttpRequest, textStatus, errorThrown){
                                //alert(XMLHttpRequest.status);
                            },
                            success: function(data){
                                hideLoading();
                                $(data).each(function(i, item){
                                    if (item.result == '102') {
                                        alert(item.errorInfo);
                                        winload("${path}/main/index.do");
                                    }
                                });
                                response($.map(data, function(item){
                                    var sex = item.sex == 'F' ? "女" : "男";
                                    return {
                                        label: item.name + " , " + sex + ", " + item.phone,
                                        text: item.name
                                    }
                                }))
                            }
                        })
                    },
                    focus: function(event, ui){
                        $("#keyTxt").val(ui.item.text);
                        return false;
                    },
                    select: function(event, ui){
                        $("#keyTxt").val(ui.item.text);
                        return false;
                    }
                });
            }
			
			
			function doQuery(){
				$("#form1").submit();
			}
			
			
        </script>
		 
    </head>
    <body>
        <div id="main-div" class="width-p100">
            <t:Top>
            </t:Top>
            <div class="content-1 main-page-190">
            	<div class="title">
            		<div class="title-span left"><span><fmt:message key="biUserInfo.table_title"></fmt:message><fmt:message key="btn.search" /></span></div>
					<div class="right"><a class="btn btn-default " href="${path}/main/biUserInfo/addOne/view.do"><fmt:message key="btn.add"/></a></div>
            	</div>
                <div class="margin-lr-1">
                <form id="form1" action="${path}/main/biUserInfo/list.do" method="get">
                <table class="search-tb width-p100">
                    <tr>
                        <td>
                            <span class="glyphicon glyphicon-search left">查询选项</span>
                        </td>
                        <td class="center">
                            <table class="row">
                                <tr>
                                    <td class="col-xs-1">
                                        <div class="input-group">
                                            <span class="input-group-addon">姓名/账号/手机</span>
                                            <input type="text" class="form-control" id="keyTxt" name="name">
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
						
                        <td>
                            <a class="btn btn-default btn-lg " role="button" href="javaScript:doQuery()"><fmt:message key="btn.search"/></a>
                        </td>
                        <td>
                            <a class="btn btn-default btn-lg " href="javaScript:doSave()"><fmt:message key="btn.save"/></a>
                        </td>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                  	  我的查询 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li>
                                        <a href="#">查询条件1</a>
                                    </li>
                                    <li>
                                        <a href="#">查询条件2</a>
                                    </li>
                                    <li>
                                        <a href="#">查询条件3</a>
                                    </li>
                                    <li class="divider">
                                    </li>
                                    <li>
                                        <a href="#">查询条件4</a>
                                    </li>
                                </ul>
                            </div>
                        </td>
                        <td>
                            <a class="glyphicon  glyphicon-remove btn-lg" href="#"></a>
                        </td>
                    </tr>
                </table>
                <div id="autoHeightDIV" class="over-flow-x-hidden">
                    <c:if test='${pageData.totalPageCount<1}'>
                        <div class="no-data-div">
                            <fmt:message key="msg.no_data" />
                        </div>
                    </c:if>
                    <c:if test='${pageData.totalPageCount>0}'>
                        <table class="list-tb width-p100 table table-hover">
                            <thead>
                                <tr>
                                    <th>
                                    </th>
                                    <th>
                                        <fmt:message key="biUserInfo.name" />
                                    </th>
                                    <th>
                                        <fmt:message key="biUserInfo.sex" />
                                    </th>
                                    <th>
                                        <fmt:message key="biUserInfo.phone" />
                                    </th>
                                    <th>
                                        <fmt:message key="biUserInfo.createDate" />
                                    </th>
                                    <th>
                                       <fmt:message key="list.columns.do" />
                                    </th>
                                </tr>
                            </thead>
                                <c:forEach var="item" items='${pageData.result}' varStatus="s">
                                    <tr>
                                        <td  >
                                            ${pageData.start+s.index+1}
                                        </td>
                                        <td>
                                            ${item.name}
                                        </td>
                                        <td>
											<c:if test="${item.sex=='g'}">
												女
											</c:if>
											<c:if test="${item.sex=='b'}">
												男
											</c:if>
                                        </td>
                                        <td>
                                            ${item.phone}
                                        </td>
                                        <td>
                                            ${item.createDateString}
                                        </td>
                                        <td>
                                            <a class="btn btn-default btn-lg " role="button" href="${path}/main/biUserInfo/update/view.do?id=${item.id}"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>&nbsp;&nbsp;&nbsp;<a class="btn btn-default btn-lg " href="${path}/main/biUserInfo/delete.do?id=${item.id}"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
                                        </td>
								    </tr>
                                </c:forEach>
                        </table>
                    </c:if>
                </div>
						  <t:PageBar pageUrl="${path}/main/biUserInfo/list.do" pageAttrKey="pageData"/>
						   </div>
				
            </div>
				<t:Footer></t:Footer>
        </div>
    </body>
</html>
