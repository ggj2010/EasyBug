<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
        <script type="text/javascript">
            $(document).ready(function(){
				//$("#form1").validate();
                if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                }
            })
			
            function onSubmit(){
                $('#form1').submit();
            }
			
        </script>
		 
    </head>
    <body>
    <div id="main-div" class="width-p100">
        <t:Top>
        </t:Top>
        <div class="content-1">
            <div class="title">
                <div class="title-span left">
                    <span> <fmt:message key="backUp.table_title" /><fmt:message key="btn.add" /></span>
                </div>
                <div class="right">
                    <a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                </div>
            </div>
            <div class="main-page-230 margin-lr-1 over-flow-x-hidden">
                <form id="form1" action="${path}/main/dBbackUp/add/save.do" method="post">
                <table class="add-tb">
                    <tr>
                        <td class="td-title" colspan="2">
                            <span class="glyphicon glyphicon-user"></span>
                            <strong>备份信息</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">备份名称</span>
                                <input class="form-control" type="text" required="required" name="name">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">备注</span>
                                <input class="form-control" type="text" required="required" name="memo">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="center">
                             <button type="submit" class="btn btn-default"> <fmt:message key="btn.save"/></button>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                        </div>
                        </td>
                    </tr>
                </table><!--
                -->
                </form>
            </div>


			
        </div>
	
		 <t:Footer>
    </t:Footer>
    </div>
    </body>
</html>
