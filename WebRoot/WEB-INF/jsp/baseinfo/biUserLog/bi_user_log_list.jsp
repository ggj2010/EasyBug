<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
			autoHeight($('#autoHeightDiv'),290);
			initAutoComplete()
		});
			function initAutoComplete(){
			 $("#nameTxt").autocomplete({
                minLength: 1,
                source: function(request, response){
                    var cnName = $("#nameTxt").val();
                    $.ajax({
                        beforeSend: function(){
                            showLoading();
                        },
                        cache: false,
                        type: 'post',
                        async: false,
                        data: {"cnName": cnName},
                        url: '${path}/biUserInfo/getAjaxUserList.do',
                        dataType: 'json',
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                            //alert(XMLHttpRequest.status);
                        },
                        success: function(data){
                            hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/index.do");}});
                            response($.map(data, function(item){
			                    var sex = item.sex == 'F' ? "女" : "男";
								return {
					                label: item.name+ " , " + sex +", " + item.phone,
					                text: item.name,
					                value: item.id
					              }
                            }))
                        }
                    })
                },
                focus: function(event, ui){
                   $("#biUserInfoIdHid" ).val(ui.item.value); 
					 $("#nameTxt").val(ui.item.text);
                    return false;
                },
                select: function(event, ui){
					$("#biUserInfoIdHid" ).val(ui.item.value); 
                    $("nameTxt").val(ui.item.text);
                    return false;
                }
            });
		}
		function doQuery(){
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
                            		<fmt:message key="biUserLog.table_title" />
                            </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
				<div class="margin-lr-1">
					<form id="form1" action="${path}/main/biUserLog/list.do" method="get">
						
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
                                                <input type="hidden" name="BiUserInfo.id" id='biUserInfoIdHid' value="${biUserLog.biUserInfo.id}"/><input value="${biUserLog.biUserInfo.name}" class="form-control" id="nameTxt" type="text" name="biUserInfo.name" maxlength="150"/>
                                            </div>
                                        </td>
                                        <td class="col-xs-1">
                                            <div class="input-group">
                                                <span class="input-group-addon">开始日期</span>
                                                <input value="${biUserLog.beginDateString}" tagname="dateInputYear" name="beginDateString" id="beginDateTxt" class="form-control" type="text" maxlength="255" />
                                            </div>
                                        </td>
                                        <td class="col-xs-1">
                                            <div class="input-group">
                                                <span class="input-group-addon">结束日期</span>
                                                <input value="${biUserLog.endDateString}" tagname="dateInputYear" name="endDateString" id="endDateTxt" class="form-control" type="text" maxlength="255" />
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="td-btn">
                                <a class="btn btn-default" href="javaScript:doQuery()"><fmt:message key="btn.search" /></a>
                            </td>
                        </tr>
                    </table>
					</form>
					<div id="autoHeightDiv" class="over-flow-x-hidden">
						<c:if test='${pagedData.totalPageCount<1}'>
							<div class="no-data-div"><fmt:message key="msg.no_data" /></div>
						</c:if>
						<c:if test='${pagedData.totalPageCount>0}'>
							<table class="list-tb table table-hover" width="100%">
							  <thead>
							  	<tr>
								  	<th></th>
										<th><fmt:message key="biUserLog.url" /></th>
										<th><fmt:message key="biUserLog.userName" /></th>
										<th><fmt:message key="biUserLog.ip" /></th>
										<th><fmt:message key="biUserLog.date" /></th>
								</tr>
							  </thead>
							  <tbody>
								  <c:forEach var="item" items='${pagedData.result}' varStatus="s">
										<tr>
											<td class="tb-left-bg" style="text-align: center">${pagedata.start+s.index+1}</td>
											<td>${item.url}</td>
											<td>${item.biUserInfo.name}</td>
											<td>${item.customIP}</td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createDate}"/></td>
										</tr>
									</c:forEach>
					  			</tbody>
					 		</table>
				  			
							</c:if>
						</div>
			<t:PageBar pageUrl="${path}/main/biUserLog/list.do" pageAttrKey="pagedData"/>
		</div>
  	</div>
  	<t:Footer></t:Footer>
	</div>

</body>
</html>
