<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/tagslib.jsp" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%@ include file="/common/header.jsp" %>
        <script type="text/javascript">
            var treeObj;
            var zNodes;
            $(document).ready(function(){
				//autoHeight($('#tree'),240);
                initProjectTree();
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
            function initProjectTree(){
                $.ajax({
                    beforeSend: function(){
                        showLoading();
                    },
                    url: '${path}/main/project/getTreeData.do?' + numRand(),
                    type: 'GET',
                    dataType: 'json',
                    error: function(XMLHttpRequest, textStatus, errorThrown){
                        //alert(XMLHttpRequest.status);
                    },
                    success: function(data){
                        hideLoading();$(data).each(function(i, item){if(item.result=='102'){alert(item.errorInfo);winload("${path}/main/main/index.do");}});
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
                 $('#tree-left-fra').attr('src', '${path}/main/project/show.do?id=' + treeNode.id);
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
        <div id="main-div">
            <t:Top></t:Top>
            <div class="content-left ">
                <div class="title">
                	<table class="title-tb" cellpadding="0" cellspacing="0">
	            		<tr>
	            			<td class="td-left"></td>
	            			<td class="td-title">
	            				●<fmt:message key="project.table_title" />
							</td>
	            			<td class="td-btn">
	            				<img onclick="javascript:expandAll();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tOpen.gif" alt="" />
								<img onclick="javascript:collapseAll();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tClose.gif" alt="" />
								<img onclick="javascript:initProjectTree();" style=" cursor:pointer; margin:4px;" src="${path}/themes/images/tRefresh.gif" alt="" />
							</td>
							<td class="td-right"></td>
	            		</tr>
	            	</table>
                </div>
                <div class="overflow-1 margin-lr-3">
                    <div id="tree" class="ztree" style="overflow:auto;width:100%;">
                    </div>
                </div>
            </div>
            <div class="content-right ">
                <iframe id="tree-left-fra"  style="height:490px;" class="over-flow-hidden width-p100" name="fra" frameborder="0"></iframe>
            </div>
            <t:Footer></t:Footer>
        </div>
    </body>
</html>
