<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
模块名：		module添加页面
说明：		module添加页面
编写者：     	<author>
编写日期：	<2012/5/20>
修改信息：
-->
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                $('#form1').validate();
                if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                }
            });
            
            function onSubmit(){
                $('#form1').submit();
            }
        </script>
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
                                <fmt:message key="biWebApp.table_title" /><fmt:message key="btn.add"/>
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
                        <form id="form1" action="${path}/main/biWebApp/add/save.do" method="post" enctype="multipart/form-data">
                            <table class="add-tb">
                                <tr>
                                    <td class="td-title">
                                        <font size=2 color="black">
                                           <span class="glyphicon glyphicon-plane"></span>
                                            <strong><fmt:message key="biWebApp.table_title" /><fmt:message key="msg.messages" /></strong>&nbsp;&nbsp;&nbsp; 
                                        </font>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group">
                                            <span class="input-group-addon"><fmt:message key="biWebApp.name" /></span><input id="nameTxt" type="text" autocomplete="off" name="name" class="form-control" maxlength="200" required="required" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group">
                                            <span class="input-group-addon"><fmt:message key="biWebApp.url" /></span><input  type="text" autocomplete="off" name="url" class="form-control" maxlength="200" required="required" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group">
                                            <span class="input-group-addon"><fmt:message key="biWebApp.briefContent" /></span><input id="nameTxt" type="text" autocomplete="off" name="briefContent" class="form-control" maxlength="30" />
                                        </div>
                                    </td>
                                </tr>
                         
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group">
                                            <span class="input-group-addon"><fmt:message key="biWebApp.describes" /></span>
											<textarea  type="text" autocomplete="off" name="describes" class="form-control"  />
											</textarea>
                                        </div>
                                    </td>
                                </tr>
								        <tr>
                                    <td class="col-12">
                                        <div class="input-group">
                                            <span class="input-group-addon"><fmt:message key="biWebApp.image" /></span>
											<input  type="file" class="form-control" name="file"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center" >
                                        <a href="javascript:onSubmit();" class="btn btn-default"><fmt:message key="btn.save" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:back();" class="btn btn-default"><fmt:message key="btn.back" /></a>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <t:Footer>
            </t:Footer>
        </div>
    </body>
</html>
