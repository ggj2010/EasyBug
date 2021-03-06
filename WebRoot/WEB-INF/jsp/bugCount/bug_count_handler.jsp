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
            $('#project').val("${bugCountVO.projectId}");
            initParam();
			 initPage();
           
        });
			
			function changeProject(){
				initParam();
			}
			
			
			function open(flag){
				var paramsStr=getSearchParam();
				if(paramsStr!=""){paramsStr="?"+paramsStr.substring(1,paramsStr.length);}
				if(flag=='project'){winload('${path}/main/count/bugContent/project.do'+paramsStr)}
				if(flag=='status'){winload('${path}/main/count/bugContent/status.do'+paramsStr)}
				if(flag=='create'){winload('${path}/main/count/bugContent/create.do'+paramsStr)}
				if(flag=='assigner'){winload('${path}/main/count/bugContent/assigner.do'+paramsStr)}
				if(flag=='handler'){winload('${path}/main/count/bugContent/handler.do'+paramsStr)}
				if(flag=='module'){winload('${path}/main/count/bugContent/module.do'+paramsStr)}
				if(flag=='version'){winload('${path}/main/count/bugContent/version.do'+paramsStr)}
			}
			
			function view(id){
				 var project="${bugCountVO.projectId}";
				 var level="${bugCountVO.level}";
				 var module="${bugCountVO.moduleId}";
				 var version="${bugCountVO.versionId}";
				 var handler=id;
				 var status="${bugCountVO.status}";
				
				var params="?projectId="+project;
				params+="&level="+level;
				params+="&moduleId="+module;
				params+="&versionId="+version;
				params+="&userHandlerId="+handler;
				params+="&status="+status;
				
				
                winload("${path}/main/count/bugContent/show.do" + params);
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
                        hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/index.do");}});
                        $(data).each(function(i, item){
							if(item.type=='version'){
								$(item.version).each(function(i,n){
								  	  $('#version').append("<option value='" + n.id + "'>" + n.name + "</option>");
								  });
							}else if(item.type=='user'){
								  	$(item.user).each(function(i,n){
								  	  $('#userHandler').append("<option value='" + n.id + "'>" + n.name + "</option>");
								  });
							}else if(item.type=='module'){
								 	$(item.module).each(function(i,n){
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
			
            function doQuery(){
                $('#form1').submit();
            }
        </script>
		<style>
			.tabs-btn{width:60px; height:30px;margin:2px;line-height:30px; display:block;float:left;border:solid 1px green;text-align:center; background:green; color:white;}
			.tabs-btn-current{width:60px; height:30px;margin:2px;line-height:30px;display:block;float:left;border:solid 1px red; background:red; color:white;text-align:center;}
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
                               	在校生统计
                            </td>
                            <td class="td-btn">
                            </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="margin-lr-1">
                	<form id="form1" action="${path}/main/count/bugContent/handler.do" method="get">
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
								<font size=2 color="black"> <i class=" icon-chevron-down"></i> 
								<strong>汇总</strong>&nbsp;&nbsp;&nbsp; </font>
							</td>
						</tr>
                		<tr>
							<td class="ltd2" colspan="2">
								<div class="margin-lr-1">
									<div class="left">
									<a class="btn btn-default" href="javaScript:open('project');" >项目</a>
									<a class="btn btn-default" href="javaScript:open('status');" >bug状态</a>
									<a class="btn btn-default" href="javaScript:open('create');" >Bug创建者</a>
									<a class="btn btn-default" href="javaScript:open('assigner');" >Bug分配者 </a>
									<a class="btn btn-danger" href="javaScript:open('handler');" >Bug解决者</a>
									<a class="btn btn-default" href="javaScript:open('module');" >模块</a>
									<a class="btn btn-default" href="javaScript:open('version');" >版本</a>
									</div>
									<c:if test='${fn:length(sumList)<1}'>
				                        <div class="no-data-div">
				                            <fmt:message key="msg.no_data" />
				                        </div>
				                    </c:if>
				                    <c:if test='${fn:length(sumList)>0}'>
				                        <table class="list-tb" width="100%">
				                            <thead>
				                                <tr>
													<th> 
                                                    	<span id="biOrganNamseTitle">姓名</span>
													</a>
													</th>
													<th id="numberTh"> 
													<span id="numberTitle">处理BUG总数</span>
													</a>
				                                    </th>
				                                    <th>
				                                        <fmt:message key="list.columns.do" />
				                                    </th>
				                                </tr>
				                            </thead>
				                            <tbody>
				                                <c:forEach var="item" items='${sumList}' varStatus="s">
				                                    <tr>
														<td>
															${item[1]}
				                                        </td>
														<td>
															${item[2]}
				                                        </td>
				                                        <td class="width-p10">
				                                        	<a class="btn btn-default" href="javascript:view('${item[0]}');">明细</a>
				                                        </td>
				                                    </tr>
				                                </c:forEach>
												<tr>
														<td colspan="3" class="width-p10">
															<font size="3" weight="800">总计BUG&nbsp;&nbsp;${sum}&nbsp;&nbsp;数量</font>
														</td>
													</tr>
				                            </tbody>
				                        </table>
				                    </c:if>
								</div>
							</td>
						</tr>
                	</table>
                </div>
                </td>
            </tr>
            </table>
        </div>
        </div>
        <t:Footer>
        </t:Footer>
    </div>
    </body>
</html>
