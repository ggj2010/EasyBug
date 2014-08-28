<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp"%>
<% 
	request.setAttribute("path",request.getContextPath());
	String path = request.getContextPath();
%>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Bug_V101</title>
      <link href="${path}/themes/css/loginStyle.css" type="text/css" rel="stylesheet">
        <script type="text/javascript">
            $(document).ready(function(){
            })
        </script>
        <body class="background-white">
            <header class="background-green">
                <div class="navbar">
                	<t:LoginTop></t:LoginTop>
                <div class="container">
                    <div id="login-cta-content">
                        <div class="text-center">
                            <h1>欢迎回来,主人 
                                <div class="asset image-login-smiley">
                                </div>
                            </h1>
                        </div>
                    </div>
                </div>
            </header>
            <div class="container padded">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="login">
                            <form method="post" action="${path}/dologin.do" id='form1'>
                                <div class="form-group ${emailStyle}">
                                    <input class="form-control bordered" autocomplete="off" onfocus="this.type='text'" required="required"  type="text" name="email" placeholder="账户名" value="${email}">
                               		<c:if test="${messagecode=='01'}">
                               			<span class="help-block"><ul class="errorlist"><li>账户不存在</li></ul></span>
                               		</c:if>
							    </div><!--去掉浏览器自动记录密码-->
                                <div style="display:none;">
                                    <input type='text'>
                                </div>
                                <div class="form-group  ${passStyle}">
                                    <input class="form-control bordered" autocomplete="off" onfocus="this.type='password'" required="required" type="password" name="passWord" placeholder="密码">
	                                <c:if test="${messagecode=='02'}">
	                               			<span class="help-block"><ul class="errorlist"><li>密码不正确</li></ul></span>
	                               		</c:if>
									</div>
                                <div class="form-group">
									<a href="${path}/biUserInfo/pwReset/view.do">忘记密码或账户名？ </a>
                                    <!--<a href="${path}/biUserInfo/pwReset/view.do">忘记密码?</a>-->
                                    <button class="btn btn-success btn-large pull-right" type="submit" data-loading-text="正在加载...">
                                        <span class="login-button-icon"></span>登陆
                                    </button>
                                </div>
                                <hr>
                                <div class="form-group bottom-group">
                                   	 没有账号? <a href="${path}/biUserInfo/singUp/view.do">现在注册吧~</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
			    <img title='中文' src="${path}/themes/images/cn.gif" /> <a href="?locale=zh_CN" style="text-decoration: none; margin-top: 12px;color: #777;">简体中文</a>
    <img title='英文' src="${path}/themes/images/gb.gif" /> <a href="?locale=en_US" style="text-decoration: none; margin-top: 12px;color: #777;">English</a> 
        </body>
    </html>
