<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>

        <script type="text/javascript">
            $(document).ready(function(){
                if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                }
                
                //打开bug 关闭事情
                $('#open').on('hidden.bs.modal', function(e){
                    $("input[name='isOpen'][value='Y']").attr("checked", false);
                    
                })
                //关闭bug 关闭dialog事件
                $('#close').on('hidden.bs.modal', function(e){
                    $("input[name='isOpen'][value='N']").attr("checked", false);
                })
                
                $('#close').on('show.bs.modal', function(e){
                    $("#opDescribe").val("");
                    $("#opIsEmail").removeAttr("checked");
                })
                
                $('#open').on('show.bs.modal', function(e){
                
                    $("#clDescribe").val("");
                })
                
            })
            
            function removeParam(str){
                if (str == "open") {
                    $("#selectUser").empty();
                }
                else {
                    $("#selectStatus").empty();
                    
                }
                return true;
            }
        </script>
		 
    </head>
    <body>
    	<div class="Wrapper">
        <div id="main-div" class="width-p100">
        	    <t:Top>
            </t:Top>
            <div class="content">
				 <div class="SubHeader">
	                 <span class="left"><img src="${path}/themes/images/dog.png" class="BlueBorder icon_biger user-logo-define" />
                     <c:forEach var="item" items='${projectUserList}' varStatus="s">
	                     <select class="Select" onchange="ChangePrj();">
	                         <option value="${item.project.id}" selected='selected'>${item.project.name}</option>
	                     </select>
						 </c:forEach>
	                 </span>
                      <t:Menu>
                     </t:Menu>
                     <div class="Contents">
                         <div class="Right Sub Sar">
                             <div class="Bugicon Midbox">
                                 	项目:${projectVO.project.name}
                             </div>
                         </div>
                         <div class="Solution Insert ">
                         <form action="${path}/main/bugContent/assign/save.do" method="post">
                         	<input type="hidden" name="id" value="${bugContent.id}">
                             <table class="width-p100">
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">标题</span>
                                             <span class="form-control">${bugContent.name}</span>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">版本</span>
											 <span class="form-control">${bugContent.projectVersion.name}</span>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">模块</span>
											  <span class="form-control">${bugContent.projectModule.name}</span>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">级别</span>
											 <span class="form-control">
												 <c:if test="${bugContent.level=='01'}"><fmt:message key="bugContent.01"></fmt:message></c:if>
												 <c:if test="${bugContent.level=='02'}"><fmt:message key="bugContent.02"></fmt:message></c:if>
												 <c:if test="${bugContent.level=='03'}"><fmt:message key="bugContent.03"></fmt:message></c:if>
												 <c:if test="${bugContent.level=='04'}"><fmt:message key="bugContent.04"></fmt:message></c:if>
												 <c:if test="${bugContent.level=='05'}"><fmt:message key="bugContent.05"></fmt:message></c:if>
											</span>                                        
										 </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">审核人</span>
											  <span class="form-control">${bugContent.userAssigner.name} </span>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">描述与截图</span>
                                             <span class="form-control">${bugContent.describe}</span>
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">处理过程</span>
                                             <span class="form-control">
                                                 <div class="Gray box Detail clear">
                                                     <div class="Solution left">
                                                         <table border="0" width="100%"  class="bugdetail box">
                                                             <tr class="titles">
                                                                 <td width="12%">
                                                                     <span class="BlueBox">已创建</span>
                                                                 </td>
                                                                 <td width="20%">
                                                                     ${bugContent.createUser.name} → (${bugContent.userAssigner.biRole.name})${bugContent.userAssigner.name}
                                                                 </td>
                                                                 <td class="">
                                                                 </td>
                                                                 <td width="14%">
                                                                     ${bugContent.createDate}
                                                                 </td>
                                                             </tr>
                                                             <tr class="arrow">
                                                                 <td colspan="4">
                                                                 </td>
                                                             </tr>
                                                             <tr class="last">
                                                                 <td>
                                                                     <span class="RedBox">待审核</span>
                                                                 </td>
                                                                 <td>
                                                                    (${bugContent.userAssigner.biRole.name}) ${bugContent.userAssigner.name}
                                                                 </td>
                                                                 <td class="">
                                                                 </td>
                                                                 <td>
                                                                 </td>
                                                             </tr>
                                                         </table>
                                                     </div>
                                                 </div>
                                             </span>
                                         </div>
                                     </td>
                                 </tr>
								 
								 <tr>
                                     <td class="col-12">
                                         <div class="input-group">
                                             <span class="input-group-addon">状态</span>
                                             	<div class="form-control">关闭<input type="radio" name="isOpen" value="N" data-target="#close" data-toggle="modal" required="required">
												
													打开<input type="radio" name="isOpen" value="Y" data-target="#open" data-toggle="modal" required="required">
												</div>
											
												
												
												
                                             	
												
                                                
                                         </div>
                                     </td>
                                 </tr>
								 
								 
                                 <tr>
                                     <td>
                                         <div class="center">
                                             <button type="submit" class="btn btn-default">
                                                 <fmt:message key="btn.save"/>
                                             </button>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                                         </div>
                                     </td>
                                 </tr>
                             </table>
							 
							 
                         </div>
						 
						 
                     </div>
					 
					 
					 
                     </div>
                    
                    
           
            
            </div>
				<t:Footer></t:Footer>
        </div>
		
		
		
        <div class="modal fade" id="open" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">${bugContent.name}</h4>
                    </div>
                    <div class="modal-body">
                    <table class="width-p100">
                        <tr>
                            <td class="col-12">
                                <div class="input-group">
                                    <span class="input-group-addon">派发给</span>
                                    <div id="selectUser">
                                        <select class="form-control" name="userHandler.id" required="required" id="opUserHandLer">
                                            <option value="">--请选择--</option>
                                            <c:forEach var="item" items='${projectVO.userList}' varStatus="s">
                                                <option value="${item.id}">${item.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
						<tr>
							<td class="col-12">
								<div class="input-group">
								<span class="input-group-addon">邮件通知组员？</span><input type="checkbox" class="form-control" name="isEmail" value="Y" id="opIsEmail">
								</div>
							</td>
						</tr>
						
                        <tr>
                            <td class="col-12">
                                <div class="input-group">
                                    <span class="input-group-addon">备注</span>
                                    <textarea name="describe" cols="20" rows="2" class="ckeditor" id="opDescribe">
                                    </textarea>
                                </div>
                            </td>
                        </tr>
						
                    </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Close
                        </button>
                        <button type="submit" class="btn btn-primary" onclick="return removeParam('close')">
                            Save changes
                        </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
		
        <div class="modal fade" id="close" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">${bugContent.name}</h4>
                    </div>
                    <div class="modal-body">
                    <table class="width-p100">
                        <tr>
                            <td class="col-12">
                                <div class="input-group">
                                    <span class="input-group-addon">关闭原因</span>
									<div id="selectStatus">
                                    <select class="form-control" name="status" id="clStatus" required="required">
                                    	 <option value="">--请选择--</option>
                                        <option value="invalid">无效的</option>
                                        <option value="repeate">重复的</option>
                                    </select>
									<div id="selectUser">
                                </div>
                            </td>
                        </tr>
						
                      <tr>
                            <td class="col-12">
                                <div class="input-group">
                                    <span class="input-group-addon">备注</span>
                                    <textarea name="describe" cols="20" rows="2" class="ckeditor" id="clDescribe">
                                    </textarea>
                                </div>
                            </td>
                        </tr>
						
                    </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Close
                        </button>
                        <button type="submit" class="btn btn-primary" onclick="return removeParam('open')">
                            Save changes
                        </button>
                    </div>
                </div>
            </div>
        </div>
		
		
		
		
    </body>
</html>
