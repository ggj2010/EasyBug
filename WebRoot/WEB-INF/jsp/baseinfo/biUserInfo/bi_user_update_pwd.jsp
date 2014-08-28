<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
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
        <script type="text/javascript">
          $(document).ready(function(){
              if ("${messageCode}" == "01") {
                  jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                  winload('${path}/main/index.do');
              }
            })
			
			function checPass(){
				var ps1=$("#password1").val();
				var ps2=$("#password2").val();
				if(ps1!=""&&ps2!=""){
					if(ps1==ps2){
						$("#message").html("");
					}else{
						$("#message").html("两次密码不一致");
					}
				}
			}
			
			
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
                                <fmt:message key="密码修改" />
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
                        <form id="form1" action="${path}/main/baseInfo/updatePassWord/save.do" method="post">
                            <table class="add-tb">
                                <tr>
                                    <td class="td-title">
                                        <span class="glyphicon glyphicon-user"></span>
                                        <strong>基本信息</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-10">
                                        <div class="input-group input-group">
                                            <span class="input-group-addon">姓名</span>
                                            <span class="form-control">${biUserInfo.name}</span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-6">
                                        <div class="input-group input-group">
                                            <span class="input-group-addon">性别</span>
                                            <c:if test="${biUserInfo.sex=='b'}">
                                                <span class="form-control">男</span>
                                            </c:if>
                                            <c:if test="${biUserInfo.sex=='g'}">
                                                <span class="form-control">女</span>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-10">
                                        <div class="input-group input-group">
                                            <span class="input-group-addon">邮箱</span>
                                            <span class="form-control">${biUserInfo.email}</span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title">
                                        <font size=2 color="black">
                                            <span class="glyphicon glyphicon-warning-sign"></span>
                                            <strong>密码信息</strong>&nbsp;&nbsp;&nbsp; 
                                        </font>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-10">
                                        <div class="input-group input-group-lg ${passStyle}">
                                            <span class="input-group-addon">旧密码</span>
                                            <input id="password" type="password" name='oldPass' class="form-control form-control-bordered" required="required"  placeholder="旧密码" >
									    </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-10">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">新密码</span>
                                            <input id="password1" type="password" name='newPass' class="form-control form-control-bordered" required="required"  placeholder="新密码" onfocusout="javaScript:checPass()">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-10">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">再确认</span>
                                            <input type="password" id="password2"  name='againPass' class="form-control form-control-bordered" required="required"  placeholder="确认密码" onfocusout="javaScript:checPass()" >
										<span id="message" style="color:red">${message}</span>
										</div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">
                                    	<button class="btn btn-default" type="submit"><fmt:message key="btn.save" /></button>
										&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="btn btn-default" href="javascript:back();"><fmt:message key="btn.back" /></a>
										
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
