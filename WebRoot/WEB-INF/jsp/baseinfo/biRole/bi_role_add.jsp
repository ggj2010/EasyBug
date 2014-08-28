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
                if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                }
            });
            
            function onSubmit(){
                $("#form1").submit();
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
                               <fmt:message key="biRole.table_title" /><fmt:message key="btn.add" />
                            </td>
                            <td class="td-btn">
                            </td>
                            <td class="td-right">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="margin-lr-1">
                    <div class="main-page-270 over-flow-x-hidden">
                        <form id="form1" action="${path}/main/biRole/add/save.do" method="post">
                            <table class="add-tb">
                                <tr>
                                    <td class="td-title">
                                        <font size=2 color="black">
                                            <span class="glyphicon glyphicon-heart"></span> 
                                            <strong>岗位信息</strong>&nbsp;&nbsp;&nbsp; 
                                        </font>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="biRole.name" /></span>
											<input class="form-control" type="text" id="keyTxt" required="required" name="name">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-12">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="biRole.discription" /></span>
                                            <textArea class="form-control" rows="2" cols="2" name="discription">
                                            </textArea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div>
                                            <button type="submit" class="btn btn-default">
                                                <fmt:message key="btn.save"/>
                                            </button>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
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
