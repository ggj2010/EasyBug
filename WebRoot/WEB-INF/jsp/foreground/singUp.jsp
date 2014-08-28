<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/tagslib.jsp" %>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
  <script type="text/javascript">
  	 $(document).ready(function(){
	 	 initEmailAutoComplete();
	})
	
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
	
	
	
	
  </script>
  
  <body class="background-white">
     <header class="background-green">
      <div class="navbar">
        	<t:LoginTop></t:LoginTop>
      <div class="container">
<div id="login-cta-content">
  <div class="text-center">
    <h1>创建你的账户 <div class="asset image-login-smiley"></div></h1>
  </div>
</div>
      </div>
    </header>

    
<form id="payment-form" method="post" action="${path}/biUserInfo/singUp/save.do">
  <div id="signup-content" ng-controller="SignupController" ng-init="setBasePlan('in0')">
    <div class="container padded">
      <div class="row">
        <div class="col-lg-8">
          <div class="form-group">
            <label for="id_lastname">姓名</label>
            <input id="id_lastname" name='name' type="text" class="form-control form-control-bordered" placeholder="姓名"  required="required"  autocomplete="off"  tabindex="1">
          </div>
          <div class="form-group">
            <label for="id_email">邮箱</label>
            <input id="email"  type="email" name='email' class="form-control form-control-bordered" placeholder="邮箱"   required="required"  autocomplete="off" tabindex="2">
          </div>
		  <!--去掉浏览器自动记录密码-->
        <div style="display:none;"><input type='text'> </div>
          <div class="form-group">
            <label for="id_password">密码</label>
            <input id="id_password"  type="password" name='passWord' class="form-control form-control-bordered" required="required" autocomplete="off" placeholder="密码" required tabindex="3">
          </div>
          <div class="form-group">
              <label>
              	性别
              </label>
              <select type="text" class="form-control" name="sex">
                  <option value="b">男</option>
                  <option value="g">女</option>
              </select>
          </div>
          <div class="form-group">
            <label for="id_phone">手机号码</label>
            <input id="id_phone" name="phone" type="text" class="form-control form-control-bordered" placeholder="手机号码" tabindex="6">
          </div>
        </div>
        <div class="col-lg-3 col-lg-offset-1">
    		<img src="${path}/themes/images/dog.png" style="width:370px;height:350px">
        </div>
      </div>
			<div class="row" id="signup-bottom-row">
              <div class="col-lg-3">
                <button type="submit" class="btn btn-success btn-large submit-button">创建账户</button>
                <p id="submit-loading" style="display: none; margin-top: 25px">
                  Creating account...
                </p>
              </div>
			  

  </body>
</html>
