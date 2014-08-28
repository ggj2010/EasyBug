<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
                $('#project').val("${bugCountVO.projectId}");
                initParam();
                initPage();
                
            });
            
            function changeProject(){
                initParam();
            }
            
            
            function initParam(){
                $('#version').children("*").remove();
                $('#module').children("*").remove();
                $('#userHandler').children("*").remove();
                
                $('#version').append("<option value=\"\">--全部--</option>");
                $('#module').append("<option value=\"\">--全部--</option>");
                $('#userHandler').append("<option value=\"\">--全部--</option>");
                
                var projectId = $('#project').val();
                $.ajax({
                    beforeSend: function(){
                        showLoading();
                    },
                    url: '${path}/main/project/getProjectParam.do?id=' + projectId,
                    type: 'GET',
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
                        $(data).each(function(i, item){
                            if (item.type == 'version') {
                                $(item.version).each(function(i, n){
                                    $('#version').append("<option value='" + n.id + "'>" + n.name + "</option>");
                                });
                            }
                            else 
                                if (item.type == 'user') {
                                    $(item.user).each(function(i, n){
                                        $('#userHandler').append("<option value='" + n.id + "'>" + n.name + "</option>");
                                    });
                                }
                                else 
                                    if (item.type == 'module') {
                                        $(item.module).each(function(i, n){
                                            $('#module').append("<option value='" + n.id + "'>" + n.name + "</option>");
                                        });
                                    }
                        });
                    },
                    complete: function(XMLHttpRequest, textStatus){
                        $('#version').val("${bugCountVO.versionId}");
                        $('#module').val("${bugCountVO.moduleId}");
                        $('#userHandler').val("${bugCountVO.userHandlerId}");
                        $('#level').val("${bugCountVO.level}");
                        $('#status').val("${bugCountVO.status}");
                    }
                });
            }
            
            function view(id){
                winload("${path}/main/tmClassStu/show.do?id=" + id);
            }
            
            
            function doQuery(){
                $('#form1').submit();
            }
        </script>
        <style>
            .tabs-btn {
                width: 60px;
                height: 30px;
                margin: 2px;
                line-height: 30px;
                display: block;
                float: left;
                border: solid 1px green;
                text-align: center;
                background: green;
                color: white;
            }
            
            .tabs-btn-current {
                width: 60px;
                height: 30px;
                margin: 2px;
                line-height: 30px;
                display: block;
                float: left;
                border: solid 1px red;
                background: red;
                color: white;
                text-align: center;
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
                                BUG统计--明细
                            </td>
                            <td class="td-btn">
                                <a class="btn btn-default" href="javascript:back2();">返回</a>
                            </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="margin-lr-1">
                    <form id="form1" action="${path}/main/count/bugContent/show.do" method="get">
                        <table class="search-tb width-p100">
                        <tr>
                            <td>
                                <span class="glyphicon glyphicon-search left">查询选项 </span>
                            </td>
                            <td>
                                <table class="row">
                                    <tr>
                                        <td>
                                            <div class="input-group">
                                                <span class="input-group-addon">项目</span>
                                                <select class="form-control" name="projectId" id="project" onchange="javascript:changeProject();">
                                                    <option value="">--全部--</option>
                                                    <c:forEach var="item" items='${projectVO.projectList}' varStatus="s">
                                                        <option value="${item.id}">${item.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="input-group">
                                                <span class="input-group-addon">版本</span>
                                                <select class="form-control" name="versionId" id="version" value="${bugCountVO.versionId}">
                                                    <option value="">--全部--</option>
                                                </select>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="input-group">
                                                <span class="input-group-addon">模块</span>
                                                <select class="form-control" name="moduleId" id="module">
                                                    <option value="">--全部--</option>
                                                </select>
                                            </div>
                                        </td>
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
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="input-group">
                                                <span class="input-group-addon">优先级</span>
                                                <select class="form-control" name="level" id="level">
                                                    <option value="">--全部--&nbsp;</option>
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
                                                <span class="input-group-addon">解决者</span>
                                                <select class="form-control" name="userHandlerId" id="userHandler">
                                                </select>
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
                    </form>
                    <div class="main-page-290 over-flow-x-hidden">
                        <table class="add-tb">
                            <tr>
                                <td class="td-title" colspan="2">
                                    <font size=2 color="black">
                                        <i class=" icon-chevron-down"></i>
                                        <strong>汇总</strong>&nbsp;&nbsp;&nbsp; 
                                    </font>
                                </td>
                            </tr>
                            <tr>
                                <td class="ltd2" colspan="2">
                                    <div class="margin-lr-1">
                                        <c:if test='${pagedData.totalPageCount<1}'>
                                            <div class="no-data-div">
                                                <fmt:message key="msg.no_data" />
                                            </div>
                                        </c:if>
                                        <c:if test='${pagedData.totalPageCount>0}'>
                                            <table class="list-tb table table-hover" width="100%">
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
                                                <tbody>
                                                    <c:forEach var="item" items='${pagedData.result}' varStatus="s">
                                                        <tr>
                                                            <td>
                                                                ${pageData.start+s.index+1}
                                                            </td>
                                                            <td class="width-p20">
                                                                <c:set var="title" value=" ${item.name}"/><span title=" ${item.name}"><c:out value="${fn:substring(title,0,30)}"/></span>
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
                                                                <c:if test="${item.status=='fixed'}">
                                                                 	   已解决
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
                                                                <a class="btn btn-default btn-lg " role="button" href="${path}/main/bugContentProcess/process/view.do?id=${item.id}"><span class="glyphicon glyphicon-refresh"></span>跟踪</a>
                                                                <a class="btn btn-default btn-lg " role="button" href="${path}/main/bugContent/update/view.do?id=${item.id}"><span class="glyphicon glyphicon-wrench"></span><fmt:message key="btn.edit"/></a>&nbsp;&nbsp;&nbsp;<a class="btn btn-default btn-lg " href="${path}/main/bugContent/delete.do?id=${item.id}"><span class="glyphicon glyphicon-trash"></span><fmt:message key="btn.del"/></a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table><t:PageBar pageUrl="${path}/main/count/bugContent/show.do" pageAttrKey="pagedData"/>
                                        </c:if>
                                    </div>
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
