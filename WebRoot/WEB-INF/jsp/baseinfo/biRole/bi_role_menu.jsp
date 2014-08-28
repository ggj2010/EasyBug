<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/tagslib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			if("${messageCode}"=="01"){
				jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
			}
			initAllCheckBox();
		});
		
        function initAllCheckBox(){
            var ids = eval('${idjson}');
            $.each(ids, function(i, n){
                $("#_" + n.menuId).prop("checked", "true");
            });
            var menus = $("input[type='checkbox']");
            $("#allSelect").prop("checked", "true");
            for (var i = 0; i < menus.length; i++) {
                if (!menus[i].checked) {
                    $("#allSelect").removeAttr("checked");
                    continue;
                }
            }
        }
		
		function checkAll(box){
			if(box.checked){
				$("input[type='checkbox']").prop("checked","true"); 
			} else {
				$("input[type='checkbox']").removeAttr("checked");
			}
		}
		
		function checkSome(box,id){
			if(box.checked){
				$("input[name*='"+id+"']").each(function(){
					$(this).prop("checked","true");
				});
			} else {
				$("input[name*='"+id+"']").each(function(){
					$(this).removeAttr("checked"); 
				});
			}
		}
		
		/**
		 * 功能：保存菜单
		 */
		function saveMenu(){
		    var menus=$("input[type='checkbox']");
			var menuStr="";
			for(var i=0;i<menus.length;i++){
			   	if(menus[i].checked&&menus[i].name!=""){
			    	menuStr+=menus[i].value+",";
			   }
			}
			menuStr=menuStr.substring(0,menuStr.length-1);
			$('#menuStrHid').val(menuStr);
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
                            </td>
                            <td class="td-title">
                               	设置菜单
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
						<form id="form1" action="${path}/main/biRole/menu/save.do" method="post" >
						  <input type="hidden" value="${token}" name="token" />
						  <input type="hidden" id="biRoleIdHid" name="roleId" value="${biRole.id}" />
						  <input type="hidden" id="menuStrHid" name="menuStr" />
							<table class="add-tb" >
								<tr>
									<td class="td-title" colspan="4">
										<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
										<strong>岗位信息</strong>&nbsp;&nbsp;&nbsp; </font>
									</td>
								</tr>
								<tr>
									<td class="ltd4">岗位名称</td>
									<td class="rtd4">${biRole.name}</td>
									<td class="ltd4">岗位说明 </td>
									<td class="rtd4">${biRole.discription}</td>
								</tr>
								<tr>
									<td class="td-title" colspan="4">
										<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
										<strong><input id="allSelect" class="input-checkbox" type="checkbox"  onclick="javascript:checkAll(this);"/>全选</strong>&nbsp;&nbsp;&nbsp; </font>
									</td>
								</tr>
								<c:forEach var="item" items="${menuList}" varStatus="s">
									<c:if test="${item.menuType=='01'&& item.preId!='-1'}">
										<tr>
											<td class="td-title" colspan="4">
												<font size=2 color="black"> <i class="icon icon-chevron-down"></i> 
													<strong>
														<input type="checkbox" class="input-checkbox" id="_${item.id}"  name="_${item.id}"  onclick="javascript:checkSome(this,'${item.id}');" value="${item.id }"/>${item.name}
													</strong>
												</font>
											</td>
										</tr>
										<c:forEach var="inItem" items="${menuList}" varStatus="s1">
	                                        <c:if test="${inItem.preId==item.id}">
	                                            <tr>
	                                                <td class="ltd4">
	                                                    <input class="input-checkbox" id="_${inItem.id}"  name="_${item.id}_${inItem.id}"   onclick="javascript:checkSome(this,'${inItem.id}');"  type="checkbox" value="${inItem.id}" />${inItem.name}
	                                                </td>
	                                                <td class="rtd4" colspan="3">
	                                                	<div style="width:100%; height:30px; ">
		                                                    <c:forEach var="btnItem" items="${btnList}" varStatus="s1">
		                                                        <c:if test="${btnItem.preId==inItem.id}">
		                                                            <c:if test="${btnItem.menuType=='03'}">
		                                                                <div style="width:130px; float:left; display:block;">
		                                                                    <input class="input-checkbox" id="_${btnItem.id}" name="_${item.id}_${inItem.id}_${btnItem.id}"  type="checkbox" value="${btnItem.id}" />${btnItem.name}
		                                                                </div>
		                                                            </c:if>
		                                                        </c:if>
		                                                    </c:forEach>
														</div>
	                                                    <c:forEach var="btnGroupItem" items="${btnGroupList}" varStatus="s1">
                                                        	<c:if test="${btnGroupItem.preId==inItem.id}">
	                                                            <c:if test="${btnGroupItem.menuType=='04'}">
	                                                            	<div style="width:100%; height:30px;">	
		                                                            	<div style="width:130px; float:left; display:block;  background-color:#f2f2f2;">
		                                                            		<input class="input-checkbox" id="_${btnGroupItem.id}" name="_${item.id}_${inItem.id}_${btnGroupItem.id}"  onclick="javascript:checkSome(this,'${btnGroupItem.id}');"    type="checkbox" value="${btnGroupItem.id}" />${btnGroupItem.name}
																		</div>
																		<c:forEach var="btnItem" items="${btnList}" varStatus="s1">
					                                                        <c:if test="${btnItem.preId==btnGroupItem.id}">
				                                                                <div style="width:130px; float:left; display:block;">
				                                                                    <input class="input-checkbox" id="_${btnItem.id}" name="_${item.id}_${inItem.id}_${btnGroupItem.id}_${btnItem.id}"  type="checkbox" value="${btnItem.id}" />${btnItem.name}
				                                                                </div>
					                                                        </c:if>
					                                                    </c:forEach>
																	</div>
	                                                            </c:if>
	                                                        </c:if>
	                                                    </c:forEach>
	                                                </td>
	                                            </tr>
	                                        </c:if>
										</c:forEach>
			  						</c:if>
					        	</c:forEach>
								<tr>
									<td style="text-align: center;" colspan="4">
                                   		<a class="btn btn-default" href="javascript:saveMenu();"><fmt:message key="btn.save"/></a>
                                   		<a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
									</td>
								</tr>
							</table>
						 </form>
					</div>
		      </div>
        </div>
		<t:Footer></t:Footer>
 	 </div>
  </body>
</html>
