<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
        <script type="text/javascript">
            $(document).ready(function(){
				
				autoHeight($('#autoHeightDIV'),300);
				initAutoComplete();
				initParam();
				
            })
			
            function initParam(){
                $("#title").val("${bugContent.name}");
                $("#level").val("${bugContent.level}");
                $("#isOpen").val("${bugContent.isOpen}");
                
               
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
            	</div>
                <div class="margin-lr-1">
                <form id="form1" action="${path}/main/bugContent/assign/list.do" method="get">
                
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
                                    <td>
                                        <div class="input-group">
                                            <span class="input-group-addon">是否分配</span>
                                            <select class="form-control" name="isOpen" id="isOpen">
                                                <option value="">--请选择--</option>
                                                <option value="01">已分配</option>
                                                <option value="02">未分配</option>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
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
                                        <c:if test="${item.isClosed=='Y'&&item.isOpen=='N'}">
                                		<tr class="tr-gray warning">
                                	</c:if>
                                	<c:if test="${item.isClosed=='N'}">
                                		<tr>
                                	</c:if>
                                        <td  >
                                            ${pageData.start+s.index+1}
                                        </td>
                                        <td class="width-p20">
                                           <c:set var="title" value=" ${item.name}"/>
                                                <abbr title=" ${item.name}">
                                                <c:out value="${fn:substring(title,0,30)}"/>
												</abbr>
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
											<c:choose>
												<c:when test="${item.userHandler.name eq null}">
													未分配
												</c:when>
												<c:otherwise>
													${item.userHandler.name}
												</c:otherwise>
											</c:choose>
                                        </td>
										<td>${item.userAssigner.name}</td>
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
                                        	<c:if test="${item.isClosed=='N'&&item.userHandler eq null}">
                                          	<c:if test="${fn:contains(btnStr, 'a1')}" >  <a class="btn btn-default btn-lg " role="button" href="${path}/main/bugContent/assign/view.do?id=${item.id}"><span class="glyphicon glyphicon-cog"></span><fmt:message key="btn.assing"/></a>&nbsp;&nbsp;&nbsp;
                                         </c:if>
										   </c:if>
										   <c:if test="${item.isClosed=='Y'&&item.isOpen=='N'}">
										   	未打开
										   	</c:if>
										   <c:if test="${item.isClosed=='Y'&&item.isOpen=='Y'}">
										   	已关闭
										   	</c:if>
											 <c:if test="${item.isClosed=='N'&&item.isOpen=='Y'}">
										   	处理中
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
