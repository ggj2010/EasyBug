<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#form1").validate();
                if ("${messageCode}" == "01") {
                    winload("${path}/main/biMenu/show.html?id=${biMenu.id}");
                    parent.initTree();
                }
                if ('${biMenu.menuType}' != "") {
                    $('#menuTypeSelect').val('${biMenu.menuType}');
                }
                initTree();
            });
            
            /**
             * 功能：从cookies获取展开节点，展开该节点
             */
            function doRootExpand(){
                treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
                var nodes = treeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) {
                    if ($.cookie(nodes[i].id) == 'o') {
                        doChildrenExpand(nodes[i].children);
                        treeObj.expandNode(nodes[i], true, false, false);
                    }
                }
            }
            
            /**
             * 功能：递归子节点，从cookies获取展开节点，展开该节点
             * @param {Object} nodes
             */
            function doChildrenExpand(nodes){
                for (var i = 0; i < nodes.length; i++) {
                    if ($.cookie(nodes[i].id) == 'o') {
                        treeObj.expandNode(nodes[i], true, false, false);
                        doChildrenExpand(nodes[i].children);
                    }
                }
            }
            
            /**
             * 功能：
             */
            function initTree(){
                $.ajax({
                    beforeSend: function(){
                        showLoading();
                    },
                    url: '${path}/main/biMenu/getTreeData.do',
                    type: 'GET',
                    dataType: 'json',
                    
                    error: function(XMLHttpRequest, textStatus, errorThrown){
                        //alert(XMLHttpRequest.status);
                    },
                    success: function(data){
                        hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/index.do");}});
                        zNodes = data;
                    },
                    complete: function(XMLHttpRequest, textStatus){
                        doRootExpand();
                    }
                });
            }
            
            
            var setting = {
                data: {
                    key: {
                        title: "t"
                    },
                    simpleData: {
                        enable: true,
                        rootPId: null
                    }
                },
                callback: {
                    onClick: zTreeOnClick,
                    onExpand: zTreeOnExpand,
                    onCollapse: zTreeOnCollapse
                }
            };
            
            function zTreeOnExpand(event, treeId, treeNode){
                addCookiesObject(organTreeCookie, treeNode.id, 'o');
            };
            
            function zTreeOnCollapse(event, treeId, treeNode){
                addCookiesObject(organTreeCookie, treeNode.id, 'c');
            };
            
            function zTreeOnClick(event, treeId, treeNode, clickFlag){
                var zTree = $.fn.zTree.getZTreeObj("tree"), nodes = zTree.getSelectedNodes(), nameStr = "";
                idStr = "";
                for (var i = 0, l = nodes.length; i < l; i++) {
                    nameStr += nodes[i].name + ",";
                    idStr += nodes[i].id + ",";
                }
                if (nameStr.length > 0) {
                    nameStr = nameStr.substring(0, nameStr.length - 1);
                    idStr = idStr.substring(0, idStr.length - 1);
                }
                
                $("#preIdHid").val(idStr);
                $("#preNameTxt").val(nameStr);
            }
            
            
            function showTreeSelect(){
                var cityObj = $("#preNameTxt");
                var cityOffset = $("#preNameTxt").offset();
                $("#menuContent").css({
                    left: cityOffset.left + "px",
                    top: cityOffset.top + cityObj.outerHeight() + "px"
                }).slideDown("fast");
                $("body").bind("mousedown", onBodyDown);
            }
            
            function hideMenu(){
                $("#menuContent").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            
            function onBodyDown(event){
                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                    hideMenu();
                }
            }
            
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
                        <td class="td-left">
                        </td>
                        <td class="td-title">
                            <fmt:message key="biMenu.table_title" /><fmt:message key="btn.edit"/>
                        </td>
                        <td class="td-btn">
                        </td>
                        <td class="td-right">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="margin-lr-1">
                <form id="form1" action="${path}/main/biMenu/update/save.do" method="post">
                    <input type="hidden" name="id" id="idHid" value="${biMenu.id}" />
					<input type="hidden" value="${token}" name="token" />
                    <table class="add-tb">
                        <tr>
                            <td class="td-title" colspan="4">
                                <font size=2 color="black">
                                    <i class="icon  icon-chevron-down"></i>
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
                            </td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.preId" />
                            </td>
                            <td class="rtd2">
                                <input id="preNameTxt" type="text" autocomplete="off" disabled="true" value="${preBiMenu.name}" class="required width-p60" /><input id="preIdHid" type="hidden" value="${biMenu.preId}" class="required width-p60" name="preId" /><a class="btn btn-small" href="javascript:showTreeSelect();">选择</a>
                            </td>
                        </tr>
						<tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.sequence" />
                            </td>
                            <td class="rtd2">
                                <input id="sequenceTxt" type="text" autocomplete="off" value="${biMenu.sequence}" name="sequence" class="required width-p60" minlength="1" digits=true/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ltd2">
                                <fmt:message key="biMenu.url" />
                            </td>
                            <td class="rtd2">
                                <input id="urlTxt" type="text" autocomplete="off" value="${biMenu.url}" name="url" class="width-p60" />
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
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="menuContent" class="menuContent" style="display:none;border:solid 1px gray;  position: absolute; background-color:white; height:300px; width:200px; overflow-x:hidden; overflow-y:auto;">
                <ul id="tree" class="ztree" style="margin-top:0; width:180px;">
                </ul>
            </div>
            </body>
