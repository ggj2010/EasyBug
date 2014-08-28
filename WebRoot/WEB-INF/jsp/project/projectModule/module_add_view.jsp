<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                
                if ("${messageCode}" == "01") {
                    jqueryUIAlert('恭喜您操作成功！点击确定将回到上一操作页面');
                    winload("${path}/main/project/show.do?id=${projectModule.project.id}");
                    parent.initProjectTree();
                }
				
				initAutoComplete();
				
            });
			
            
            function initAutoComplete(){
                $("#keyTxt").autocomplete({
                    minLength: 1,
                    source: function(request, response){
                        var key = $("#keyTxt").val();
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
                            url: '${path}/biUserInfo/getAjaxUserList.do',
                            dataType: 'json',
                            error: function(XMLHttpRequest, textStatus, errorThrown){
                                //alert(XMLHttpRequest.status);
                            },
                            success: function(data){
                                hideLoading();
                                $(data).each(function(i, item){
                                    if (item.result == '102') {
                                        alert(item.errorInfo);
                                        winload("${path}/main/main/index.do");
                                    }
                                });
                                response($.map(data, function(item){
                                    var sex = item.sex == 'F' ? "女" : "男";
                                    return {
                                        label: item.name + " , " + sex + ", " + item.phone,
                                        text: item.name,
                                        id: item.id
                                    }
                                }))
                            }
                        })
                    },
                    focus: function(event, ui){
                        $("#keyTxt").val(ui.item.text);
                        $("#dealUserId").val(ui.item.id);
                        return false;
                    },
                    select: function(event, ui){
                        $("#keyTxt").val(ui.item.text);
                        $("#dealUserId").val(ui.item.id);
                        
                        return false;
                    }
                });
            }
            
            function onSubmit(){
                $('#form1').submit();
            }
			
			
        </script>
    </head>
    <body style="background:white; min-width:800px;">
        <div id="frame-div">
        <div class="title">
            <table class="title-tb" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="td-left">
                    </td>
                    <td class="td-title">
                        <fmt:message key="project.table_title" /><fmt:message key="btn.add"/>
                    </td>
                    <td class="td-btn">
                    </td>
                    <td class="td-right">
                    </td>
                </tr>
            </table>
        </div>
        <div class="margin-lr-1">
            <form id="form1" action="${path}/main/projectModule/add/save.do" method="post">
            <input type="hidden" value="${projectVO.id}" name="project.id" />
                <table class="add-tb">
                    <tr>
                        <td class="td-title">
                            <span class="glyphicon glyphicon-list"></span>
                            <strong>模块信息</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-12">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所在项目</span>
                                <input type="text" class="form-control" readonly="readonly" value="${projectVO.project.name}">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-12">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模块名称</span>
                                <input type="text" class="form-control" required="required" name="name">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-6">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模处理人</span>
                                <input class="form-control" type="text" id="keyTxt" required="required" >
								<input type="hidden" id="dealUserId" name="dealUser.id">
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
                </table>
            </form>
        </div>
    </body>
	