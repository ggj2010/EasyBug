<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/tagslib.jsp" %>
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
       
        <script type="text/javascript">
            var treeObj;
            var zNodes;
            $(document).ready(function(){
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
                    url: '${path}/main/biMenu/getTreeData.do?'+numRand(),
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
                    }
                },
                callback: {
                    onClick: zTreeOnClick,
                    onExpand: zTreeOnExpand,
                    onCollapse: zTreeOnCollapse
                }
            };
            
            /**
             * 功能：tree节点关闭回调事件
             * @param {Object} event
             * @param {Object} treeId
             * @param {Object} treeNode
             */
            function zTreeOnExpand(event, treeId, treeNode){
                if (treeNode.isParent) {
                    addCookies(treeNode.id, 'o');
                }
            };
            
            /**
             * 功能：tree节点展开回调事件
             * @param {Object} event
             * @param {Object} treeId
             * @param {Object} treeNode
             */
            function zTreeOnCollapse(event, treeId, treeNode){
                if (treeNode.isParent) {
                    addCookies(treeNode.id, 'c');
                }
            };
            
            /**
             * 功能：tree节点单击事件
             * @param {Object} event
             * @param {Object} treeId
             * @param {Object} treeNode
             * @param {Object} clickFlag
             */
            function zTreeOnClick(event, treeId, treeNode, clickFlag){
                $('#tree-left-fra').attr('src', '${path}/main/biMenu/show.do?id=' + treeNode.id);
            }
            
            /**
             * 功能：展开全部
             */
            function expandAll(){
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                treeObj.expandAll(true);
                
            }
            
            /**
             * 功能：关闭全部
             */
            function collapseAll(){
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                treeObj.expandAll(false);
            }
        </script>
    </head>
    <body>
        <div id="main-div" class="width-p100">
            <t:Top></t:Top>
            <div class="content-left main-page-190">
                <div class="title">
                    <table style="width:100%;">
                        <tr>
                            <td class="ltd2" style="text-indent:1em;color:white;">
                                <fmt:message key="biMenu.table_title" />
                            </td>
                            <td class="rtd2" style="width:50%; text-align:right; ">
                                <img onclick="javascript:expandAll();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tOpen.gif" alt="" />
								<img onclick="javascript:collapseAll();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tClose.gif" alt="" />
								<img onclick="javascript:initTree();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tRefresh.gif" alt="" />
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="overflow-1 margin-lr-3">
					<div id="tree" class="ztree" style="overflow:auto;width:95%;height:95%;"></div>
				</div>
            </div>
			<div class="content-right main-page-190">
				<iframe id="tree-left-fra" class="main-page-190 over-flow-hidden width-p100" name="fra" frameborder="0"  ></iframe>
			</div>
			<t:Footer></t:Footer>
        </div>
    </body>
</html>
