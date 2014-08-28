<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/tagslib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/header.jsp"%>
		<script type="text/javascript">
			function doImport(fileName,id){
				$.ajax({ beforeSend: function(){$('#'+id).html('亲！正在导入中，需要几分钟时间，请耐心等待！');},
		             url: '${path}/main/import_excel.do?fileName='+fileName,
		             type: 'GET',
		             dataType: 'html',
		             error: function(XMLHttpRequest, textStatus, errorThrown){
		                 //alert(XMLHttpRequest.status);
		             },
		             success: function(data){hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/index.do");}});
		                 $('#'+id).html(data);
		             },
		             complete: function(XMLHttpRequest, textStatus){
		             }
		         });
			
			}
			function doTest(fileName,id){
				$.ajax({ beforeSend: function(){$('#'+id).html('亲！正在测试中，需要几分钟时间，请耐心等待！');},
		             url: '${path}/main/test_excel.do?fileName='+fileName,
		             type: 'GET',
		             dataType: 'html',
		             error: function(XMLHttpRequest, textStatus, errorThrown){
		                 //alert(XMLHttpRequest.status);
		             },
		             success: function(data){hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/index.do");}});
		                 $('#'+id).html(data);
		             },
		             complete: function(XMLHttpRequest, textStatus){
		             }
		         });
			
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
                            	数据导入
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
					<table class="add-tb">
						<tr>
							<td class="td-title" colspan="4">
								<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
								<strong>已经上传文件</strong>&nbsp;&nbsp;&nbsp; </font>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="margin-lr-1">
										<c:if test='${fn:length(fileList)<1}'>
											<div class="no-data-div"><fmt:message key="msg.no_data" /></div>
										</c:if>
										<c:if test='${fn:length(fileList)>0}'>
											<table class="list-tb" width="100%">
												<thead>
													<tr>
														<th></th>
														<th>文件名</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="item" items='${fileList}' varStatus="s">
														<tr>
															<td class="tb-left-bg" style="text-align: center">${s.index+1}</td>
															<td>${item}
															</td>
															<td>
																<div id="file${s.index+1}"></div>
																<a class="btn2 btn-small" href="javascript:doImport('${item}','file${s.index+1}');">导入</a>  
																<a class="btn2 btn-small" href="javascript:doTest('${item}','file${s.index+1}');">测试</a>  
															</td>
														</tr>
													</c:forEach>
												</tbody>
								 			</table>
										</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td-title" colspan="4">
								<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
								<strong>上传文件</strong>&nbsp;&nbsp;&nbsp; </font>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="margin-lr-1">
									<form method="post" action="${path}/main/fileUpload.do"
										enctype="multipart/form-data">
										<input type="file" name="file" />
										<input type="submit" />
									</form>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		</div>

		
	</body>
</html>
