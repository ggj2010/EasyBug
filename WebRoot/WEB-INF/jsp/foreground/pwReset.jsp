<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <script>
            $(function(){
                initDaoHang();
                initEmailAutoComplete();
				initCode();
            });
            
            function initDaoHang(){
                $("#daohang").empty();
                var htmlStr = "<div class=\"findpass\">	";
                if ("${step}" == "1") {
                    htmlStr += "	<div class=\"firstover\">	";
                    htmlStr += "	<span>确认账号</span>	"
                    htmlStr += "	</div>	";
                    htmlStr += "	<div class=\"second\">	";
                    htmlStr += "	<span>验证密码</span>		";
                    htmlStr += "  </div>	";
                    htmlStr += "	<div class=\"third\">	";
                    htmlStr += " 	<span>设置成功</span>		";
                    
                    $("#step").val(1);
                    $("#step2").empty();
                    $("#step3").empty();
                }
                else 
                    if ("${step}" == "2") {
                        htmlStr += "	<div class=\"first\">	";
                        htmlStr += "	<span>确认账号</span>	"
                        htmlStr += "	</div>	";
                        htmlStr += "	<div class=\"secondover\">	";
                        htmlStr += "	<span>安全验证</span>		";
                        htmlStr += "  </div>	";
                        htmlStr += "	<div class=\"third\">	";
                        htmlStr += " 	<span>设置成功</span>		";
                        
                        $("#step1").remove();
                        $("#step3").empty();
                    }
                    else 
                        if ("${step}" == "3") {
                            htmlStr += "	<div class=\"first\">	";
                            htmlStr += "	<span>确认账号</span>	"
                            htmlStr += "	</div>	";
                            htmlStr += "	<div class=\"second\">	";
                            htmlStr += "	<span>安全验证</span>		";
                            htmlStr += "  </div>	";
                            htmlStr += "	<div class=\"thirdover\">	";
                            htmlStr += " 	<span>设置成功</span>		";
                            
                            $("#step1").remove();
                            $("#step2").empty();
                            
                        }
                htmlStr += "</div></div>";
                $('#daohang').append(htmlStr);
                
                
                
                
                
            }
            
            function initEmailAutoComplete(){
                $("#email").autocomplete({
                    minLength: 1,
                    source: function(request, response){
                        var key = $("#email").val();
                        $.ajax({
                            beforeSend: function(){
                                showLoading();
                            },
                            cache: false,
                            type: 'post',
                            async: false,
                            data: {
                                "key": key
                            },
                            url: '${path}/biUserInfo/getEmail.do',
                            dataType: 'json',
                            error: function(XMLHttpRequest, textStatus, errorThrown){
                                //alert(XMLHttpRequest.status);
                            },
                            success: function(data){
                                hideLoading();
                                $(data).each(function(i, item){
                                    if (item.result == '102') {
                                        alert(item.errorInfo);
                                        winload("${path}/main/index.do");
                                    }
                                });
                                response($.map(data, function(item){
                                    return {
                                        label: key.split("@")[0] + "@" + item.email + "      " + item.name,
                                        text: key.split("@")[0] + "@" + item.email
                                    }
                                }))
                            }
                        })
                    },
                    select: function(event, ui){
                        $("#email").val(ui.item.text);
                        return false;
                    }
                })
            }
			
			
			function initCode(){
				 $('#dialog').on('show.bs.modal', function (e) {
            	initInputCodeValue();
            })
				
			}
			
			function initInputCodeValue(){
				var email="${biUserInfo.email}";
				var str="";
				$.ajax({
                        beforeSend: function(){
                        },
                        cache: false,
                        type: 'post',
                        async: false,
                        data: {"email":email },
                        url: '${path}/biUserInfo/getEmailCode.do',
                        dataType: 'json',
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                        },
                        success: function(data){
                           $.each(data, function(i,n){
								str=n.code;
							});
                        }
                    });
				$("#emailCode").val(str);
				
			}
			
			
			function chekCode(){
				var nowEmailCode=$("#nowEmailCode").val();
				var emailCode=$("#emailCode").val();
					if(nowEmailCode==emailCode){
						$("#codeSpan").html("验证码正确~请点击 【确定】");
						return
					}
					else{
						$("#codeSpan").html("亲，验证码错误(⊙o⊙)哦~");
						$("#nowEmailCode").val("");
					}
			}
			
			
			
			function checkPass(){
				var p1=$("#password1").val();
				var p2=$("#password2").val();
				if (p2 == "") {
				
				}
				else {
					if (p1 == p2) {
						//$("#pass2Span").html("亲，密码一致(⊙o⊙)哦~请点击【保存】");
					}
					else {
						$("#pass2Span").html("两次密码不一致，请重新输入");
						var p1 = $("#password1").val("");
						var p2 = $("#password2").val("");
						
					}
				}
				
				
			}
        </script>
    </head>
    <body class="background-white">
        <header class="background-green">
            <div class="navbar">
            <t:LoginTop></t:LoginTop>
            <div class="container">
                <div id="login-cta-content">
                    <div class="text-center">
                        <h1>找回密码噢~~ 
                            <div class="asset image-login-smiley">
                            </div>
                        </h1>
                    </div>
                </div>
            </div>
        </header>
        <form id="payment-form" method="post" action="${path}/biUserInfo/pwReset/view.do">
        	<input type="hidden" name="step" id="step" value="${biUserInfo.step}">
			<input type="hidden" id="emailCode">
            <div id="signup-content" ng-controller="SignupController" ng-init="setBasePlan('in0')">
                <div class="container padded">
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="form-group">
                            <!--导航-->
                                <div id="daohang"></div>
                            </div>
                            <!--去掉浏览器自动记录密码-->
                            <div style="display:none;"><input type='text'></div>
							
							<!--步骤1-->
                            <div id="step1">
                                <div class="form-group">
                                    <label for="id_password">
                                     	   请填写您需要找回的邮箱帐号:
                                    </label>
                                    <input id="email" type="email" class="form-control" name='email' required="required" autocomplete="off" placeholder="邮箱账号" required tabindex="3">
                                </div>
                                <div class="form-group">
                                    <div>
                                        <input id="nowCode" name="nowCode" type="text" class="form-control" required="required" placeholder="请输入验证码" tabindex="6">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div>
                                        <img alt="验证码" style="height:28px;border-radius: 5px 5px 5px 5px;" src="${path}/getImage.do" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="color-red">
                                        <c:if test="${messagecode == '02'}">
                                        	    该邮箱未注册，请检查是否拼写错误   Ⅹ
                                        </c:if>
                                        <c:if test="${messagecode == '03'}">
                                     	       验证码错误   Ⅹ
                                        </c:if>
                                    </label>
                                </div>
                                <div class="row" id="signup-bottom-row">
                                    <div class="col-lg-3">
                                        <button type="submit" class="btn btn-success btn-large submit-button">
                                     	       下一步
                                        </button>
                                        <p id="submit-loading" style="display: none; margin-top: 25px">
                                            Creating account...
                                        </p>
                                    </div>
                                </div>
                            </div>
							
							
							<!--步骤2-->
                            <div id="step2">
                            	<input type="hidden" name="email"  value="${biUserInfo.email}">
                                <div class="form-group">
                                    <label for="id_password">
                                        	您正在找回的帐号是：${biUserInfo.email}
                                    </label>
                                </div>
                                <div class="row" id="signup-bottom-row">
                                    <div class="col-lg-3">
                                        <button class="btn btn-success btn-large submit-button" data-toggle="modal" data-target="#dialog">
                                           	 点击发送验证码
                                        </button>
                                    </div>
                                </div>
								
								<!--隐藏dialog-->
                                <div class="modal fade" id="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria- hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                    ×
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel">找回密码</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <div>
                                                        <input id="nowEmailCode" type="text" class="form-control" required="required" placeholder="邮箱验证码"  onfocusout="javaScript:chekCode()"><span id="codeSpan"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                                   	 关闭
                                                </button>
                                                <button type="submit" class="btn  btn-success">
                                                  	  确定
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
							
							
							<!--步骤三-->
                            <div id="step3">
                            		<input type="hidden" name="email"  value="${biUserInfo.email}">
                                <div class="form-group">
                                    <label for="id_password">
                                        	您正在找回的帐号是：${biUserInfo.email}
                                    </label>
                                </div>
                                <div class="form-group">
                                    <div>
                                        <input id="password1" name="passWord" type="password" class="form-control" required="required" placeholder="新密码" onfocusout="javaScript:checkPass()">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div>
                                        <input id="password2"  type="password" class="form-control" required="required" placeholder="确认密码" onfocusout="javaScript:checkPass()"><span id="pass2Span"> </span>
                                    </div>
                                </div>
								<div class="row" id="signup-bottom-row">
                                    <div class="col-lg-3">
                                        <button type="submit" class="btn btn-success btn-large submit-button">
                                     	     保存
                                        </button>
                                    </div>
                                </div>
								
								
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
