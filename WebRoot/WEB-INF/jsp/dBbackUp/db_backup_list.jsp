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
                          <div class="title-span left"><span>	<fmt:message key="backUp.table_title" /></span></div>
							<div class="right"><a class="btn btn-default " href="${path}/main/dBbackUp/add/view.do"><fmt:message key="btn.add"/></a></div>
                        </tr>
                    </table>
                </div>
				<div class="margin-lr-1">
					<form id="form1" action="${path}/main/dBbackUp/list.do" method="get">
						
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
                                                <input type="hidden" name="createUser.id" id='biUserInfoIdHid' value="${dBbackUp.createUser.id}"/><input value="${dBbackUp.createUser.name}" class="form-control" id="nameTxt" type="text" name="createUser.name" maxlength="150"/>
                                            </div>
                                        </td>
                                        <td class="col-xs-1">
                                            <div class="input-group">
                                                <span class="input-group-addon">开始日期</span>
                                                <input value="${dBbackUp.beginDateString}" tagname="dateInputYear" name="beginDateString" id="beginDateTxt" class="form-control" type="text" maxlength="255" />
                                            </div>
                                        </td>
                                        <td class="col-xs-1">
                                            <div class="input-group">
                                                <span class="input-group-addon">结束日期</span>
                                                <input value="${dBbackUp.endDateString}" tagname="dateInputYear" name="endDateString" id="endDateTxt" class="form-control" type="text" maxlength="255" />
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
							<table class="list-tb" width="100%">
							  <thead>
							  	<tr>
								  	<th></th>
										<th><fmt:message key="backUp.name" /></th>
										<th><fmt:message key="backUp.url" /></th>
										<th><fmt:message key="backUp.createUser" /></th>
										<th><fmt:message key="backUp.createDate" /></th>
										    <th><fmt:message key="list.columns.do" /></th>
								</tr>
							  </thead>
							  <tbody>
								  <c:forEach var="item" items='${pagedData.result}' varStatus="s">
										<tr>
											<td class="tb-left-bg" style="text-align: center">${pagedata.start+s.index+1}</td>
											<td>${item.name}</td>
												<td>${item.url}</td>
											<td>${item.createUser.name}</td>
											<td>${item.createDateString}</td>
											
											<td><a class="btn btn-default btn-lg " role="button" href="${path}/main/dBbackUp/recover.do?id=${item.id}"><span class="glyphicon glyphicon-refresh"></span><fmt:message key="btn.recover"/></a>&nbsp;&nbsp;&nbsp;
											<a class="btn btn-default btn-lg " href="${path}/main/dBbackUp/delete.do?id=${item.id}"><span class="glyphicon glyphicon-trash"></span>
											<fmt:message key="btn.del"/></a>
											</td>
										</tr>
									</c:forEach>
					  			</tbody>
					 		</table>
				  			
							</c:if>
						</div>
			<t:PageBar pageUrl="${path}/main/dBbackUp/list.do" pageAttrKey="pagedData"/>
		</div>
  	</div>
  	<t:Footer></t:Footer>
	</div>

</body>
</html>
