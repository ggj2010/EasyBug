<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#form1").validate();
            });
            function onSubmit(){
                $("#form1").submit();
            }
        </script>
    </head>
    <body style="background:white; min-width:800px;">
        <div id="frame-div">
            <div class="title">
            	<table class="title-tb" cellpadding="0" cellspacing="0">
            		<tr>
            			<td class="td-left"></td>
            			<td class="td-title">
            				<fmt:message key="biMenu.table_title" /><fmt:message key="btn.add"/>
						</td>
            			<td class="td-btn">
						</td>
						<td class="td-right"></td>
            		</tr>
            	</table>
            </div>
            <div class="margin-lr-1">
                <form id="form1" action="${path}/main/biMenu/add/save.do" method="post">
                	<input type="hidden" value="${token}" name="token" />
                    <table class="add-tb">
                        <tr>
                            <td class="td-title" colspan="4">
                                <font size=2 color="black">
                                    <i class="icon icon-chevron-down"></i>
                                    <strong>菜单信息</strong>&nbsp;&nbsp;&nbsp; 
                                </font>
                            </td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.name" />
                            </td>
                            <td class="rtd2">
                                <input id="nameTxt" type="text" autocomplete="off" value="${biMenu.name}" name="name" class="required width-p60" minlength="2" />
                            	<font class="color-red">*</font>
							</td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.preId" />
                            </td>
                            <td class="rtd2">
                                <input id="preIdTxt" type="hidden" type="text" autocomplete="off" value="${preBiMenu.id}" name="preId" class="required width-p60" />${preBiMenu.name}
                            </td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.sequence" />
                            </td>
                            <td class="rtd2">
                                <input id="sequenceTxt" type="text" autocomplete="off" value="${biMenu.sequence}" name="sequence" class="required width-p60" minlength="1" digits=true/>
                            	<font class="color-red">*</font>
							</td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.url" />
                            </td>
                            <td class="rtd2">
                                <input id="urlTxt" type="text" autocomplete="off" value="${biMenu.url}" name="url" class="width-p60" minlength="1"/>
								<font class="color-red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.menuType" />
                            </td>
                            <td class="rtd2">
                                <select id="menuTypeSelect" name="menuType" class="required width-p60">
                                    <option value="01">菜单夹</option>
                                    <option value="02">菜单</option>
                                    <option value="03">按钮</option>
                                    <option value="04">按钮组</option>
                                </select>
                            </td>
                        </tr>
						<tr>
	                        <td class="ltd2">
	                            <fmt:message key="biMenu.buttonCode" />
	                        </td>
	                        <td class="rtd2">
	                        	<input id="buttonCodeTxt" type="text" autocomplete="off" value="${biMenu.buttonCode}" name="buttonCode" class="width-p60" />
	                        </td>
	                    </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.memo" />:
                            </td>
                            <td class="rtd2">
                                <textarea rows="3" id="memoTxt" value="${biMenu.memo}" name="memo" cols="" class="width-p60"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center;" colspan="2">
                                <a class="btn btn-default" href="javascript:onSubmit();"><fmt:message key="btn.save" /></a>
                                <a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back" /></a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            </body>