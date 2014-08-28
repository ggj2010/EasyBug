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
				
				if("${biUserInfo.sex}"=="g"){
					$('#sex').val("g");
				}else{
					$('#sex').val("b");
				}
				
				if("${biUserInfo.isReceiveEmail}"=="Y"){
					$("#isReceiveEmail").prop("checked", "true");
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
                    <span> <fmt:message key="biUserInfo.table_title"></fmt:message><fmt:message key="btn.edit" /></span>
                </div>
                <div class="right">
                    <a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back"/></a>
                </div>
            </div>
            <div class="main-page-230 margin-lr-1 over-flow-x-hidden">
                <form id="form1" action="${path}/main/biUserInfo/update/save.do"  method="post">
                	<input type="hidden" name="id" value="${biUserInfo.id}">
                <table class="add-tb">
                    <tr>
                        <td class="td-title" colspan="2">
                            <span class="glyphicon glyphicon-user"></span>
                            <strong>基本信息</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">姓名</span>
                                <input type="text" class="form-control" required="required" name="name" value="${biUserInfo.name}">
                            </div>
                        </td>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">性别</span>
                                <select type="text" class="form-control" name="sex" id="sex" >
                                    <option value="b">男</option>
                                    <option value="g">女</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">邮箱</span>
                                <input class="form-control" type="email" required="required" name="email" value="${biUserInfo.email}">
                            </div>
                        </td>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                            <span class="input-group-addon"><input type="checkbox" name="isReceiveEmail" id="isReceiveEmail" value="Y"></span>
                               <input type="text" class="form-control" readonly="readonly" placeholder="是否接收邮件">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">手机</span>
                                <input class="form-control" type="text" required="required" name="phone" value="${biUserInfo.phone}">
                            </div>
                        </td>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">密码</span>
                                <input class="form-control" type="text" name="passWord" >
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
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
