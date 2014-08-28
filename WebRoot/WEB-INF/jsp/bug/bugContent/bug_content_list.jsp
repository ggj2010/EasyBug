﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
        <script type="text/javascript">
            $(document).ready(function(){
				
				autoHeight($('#autoHeightDIV'),330);
				initAutoComplete();
				initParam();
				
            })
			
			function initParam(){
			$("#title").val("${bugContent.name}");
			$("#module").val("${bugContent.projectModule.id}");
			$("#version").val("${bugContent.projectVersion.id}");
			$("#handler").val("${bugContent.userHandler.id}");
			$("#assinger").val("${bugContent.userAssigner.id}");
			$("#level").val("${bugContent.level}");
			}
			
			  function initAutoComplete(){
                $("#titleText").autocomplete({
                    minLength: 1,
                    source: function(request, response){
                        var key = $("#titleText").val();
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
                        	 url: '${path}/main/bugContent/getAjaxBugList.do',
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
                                    return {
                                        label: item.name,
                                        text: item.name
                                    }
                                }))
                            }
                        })
                    },
                    focus: function(event, ui){
                        $("titleText").val(ui.item.text);
                        return false;
                    },
                    select: function(event, ui){
                        $("#titleText").val(ui.item.text);
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
            		<div class="title-span left"><span><fmt:message key="bugContent.table_title"></fmt:message><fmt:message key="btn.search" /></span></div>
					<div class="right"><c:if test="${fn:contains(btnStr, 'i1')}" ><a class="btn btn-default " href="${path}/main/bugContent/add/view.do"><fmt:message key="btn.add"/></a></c:if></div>
            	</div>
                <div class="margin-lr-1">
                <form id="form1" action="${path}/main/bugContent/list.do" method="get">
                
                <table class="search-tb width-p100">
                    <tr>
                        <td>
                            <span class="glyphicon glyphicon-search left">查询选项</span>
                        </td>
                        <td>
                            <table class="row">
                                <tr>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">模块</span>
                                            <select class="form-control" name="projectModule.id" id="module">
                                                <option value="">--全部--</option>
                                                <c:forEach var="item" items='${projectVO.moduleList}' varStatus="s">
                                                    <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">分配人</span>
                                            <select class="form-control" name="userAssigner.id" id="assinger">
                                                <option value="">--全部--</option>
                                                <c:forEach var="item" items='${projectVO.userList}' varStatus="s">
                                                    <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">版本</span>
                                            <select class="form-control" name="projectVersion.id" id="version">
                                                <option value="">--全部--</option>
                                                <c:forEach var="item" items='${projectVO.versionList}' varStatus="s">
                                                    <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">优先级</span>
                                            <select class="form-control" name="level" id="level">
                                                <option value="">全部</option>
                                                <option value="01">低</option>
                                                <option value="02">中</option>
                                                <option value="03">高</option>
                                                <option value="04">紧急</option>
                                                <option value="05">严重</option>
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">状态</span>
                                            <select class="form-control" name="status" id="status">
                                                <option value="">--全部--</option>
                                                <option value="new">新录入</option>
                                                <option value="open">已打开</option>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">处理人</span>
                                            <select class="form-control" name="userHandler.id" id="handler">
                                                <option value="">--全部--</option>
                                                <c:forEach var="item" items='${projectVO.userList}' varStatus="s">
                                                    <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </td>
                                    <td colspan="2">
                                        <div class="input-group">
                                            <span class="input-group-addon">关键字</span>
                                            <input id="titleText" class="form-control" name="name" type="text" id="title">
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <a class="btn btn-default btn-lg " role="button" href="javaScript:doQuery()"><fmt:message key="btn.search"/></a>
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
                                        <fmt:message key="bugContent.name" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.level" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.status" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.userHandler" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.userAssigner" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.createUser" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.projectModule" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.projectVersion" />
                                    </th>
                                    <th>
                                        <fmt:message key="bugContent.createDate" />
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
                                        <td class="width-p20">
                                           <c:set var="title" value=" ${item.name}"/>
                                                <span title=" ${item.name}">
                                                <c:out value="${fn:substring(title,0,30)}"/>
												</span>
                                        </td>
                                        <td>
											<c:if test="${item.level=='01'}">
												 <fmt:message key="bugContent.01" />
											</c:if>
											<c:if test="${item.level=='02'}">
												 <fmt:message key="bugContent.02" />
											</c:if>
											<c:if test="${item.level=='03'}">
												 <fmt:message key="bugContent.03" />
											</c:if>
											<c:if test="${item.level=='04'}">
												 <fmt:message key="bugContent.04" />
											</c:if>
											<c:if test="${item.level=='05'}">
												 <fmt:message key="bugContent.05" />
											</c:if>
                                        </td>
                                        <td>
                                            <c:if test="${item.status=='new'}">
                                            	    新录入
                                            </c:if>
                                            <c:if test="${item.status=='open'}">
                                            	   已分配
                                            </c:if>
                                            <c:if test="${item.status=='resolved'}">
                                            	   重新分配
                                            </c:if>
                                            <c:if test="${item.status=='fixed'}">
                                            	    已解决
                                            </c:if>
                                            <c:if test="${item.status=='solved'}">
                                            	    待审核
                                            </c:if>
                                            <c:if test="${item.status=='repeate'}">
                                            	   重复的
                                            </c:if>
                                            <c:if test="${item.status=='invalid'}">
                                            	  无效的
                                            </c:if>
											 <c:if test="${item.status=='worksForMe'}">
                                            	  无法重新
                                            </c:if>
											 <c:if test="${item.status=='later'}">
                                            	  延期处理
                                            </c:if>
                                        </td>
                                        
                                        <td>
                                            ${item.userHandler.name}
                                        </td>
                                        <td>
                                            ${item.userAssigner.name}
                                        </td>
                                        <td>
                                            ${item.createUser.name}
                                        </td>
                                        <td>
                                            ${item.projectModule.name}
                                        </td>
                                        <td>
                                            ${item.projectVersion.name}
                                        </td>
                                        <td>
                                            ${item.createDateString}
                                        </td>
                                        <td>
                                        	<c:if test="${fn:contains(btnStr, 'i4')}" >
                                        	  <a class="btn btn-default btn-lg " role="button" href="${path}/main/bugContentProcess/process/view.do?id=${item.id}"><span class="glyphicon glyphicon-refresh"></span>跟踪</a>
                                           </c:if>
										    <c:if test="${fn:contains(btnStr, 'i3')}" >
											<a class="btn btn-default btn-lg " role="button" href="${path}/main/bugContent/update/view.do?id=${item.id}"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>
											 </c:if>
											&nbsp;&nbsp;&nbsp;
											<c:if test="${fn:contains(btnStr, 'i2')}" >
											<a class="btn btn-default btn-lg " href="${path}/main/bugContent/delete.do?id=${item.id}"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
                                        	 </c:if>
										</td>
								    </tr>
                                </c:forEach>
                        </table>
                    </c:if>
                </div>
						  <t:PageBar pageUrl="${path}/main/bugContent/list.do" pageAttrKey="pageData"/>
						   </div>
	
            </div>
				<t:Footer></t:Footer>
        </div>
    </body>
</html>
