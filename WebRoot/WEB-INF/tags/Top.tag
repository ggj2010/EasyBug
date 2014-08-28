<%@tag import="com.framework.constant.Global" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%
request.setAttribute("allMenuList", request.getSession().getAttribute(Global.USER_ALL_MENU));%>

<div class="header">
	<table class="header-tb" cellspacing=0 cellpadding=0>
		<tr style="height: 82px;">
			<td style="text-align: center" class="header-left width-p20">
				<img width="150px" src="${path}/themes/images/logo.png" />
			</td>
			<td class="header-right width-p80">
				<table>
					<tr height="23px">
						<td>
							<p style="font-size: 15px;">
								${user.name }
								<small> 欢迎回来╮(╯▽╰)╭</small>
							</p>
						</td>
						<td>
						</td>
					</tr>
					<tr height="23px">
						<td>
							<a class="btn btn-default"
								href="${path}/main/baseInfo/updatePassWord/view.do"
								style="COLOR: #fff">修改密码</a>
							<a class="btn btn-default"
								href="${path}/main/projectUser/changeRole/view.do"
								style="COLOR: #fff">切换岗位</a>
							<a class="btn btn-default"
								onclick="if (confirm('确定要退出吗？')) return true; else return false;"
								href="${path}/dologout.do" target=_top>退出系统</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<table class="header-tb" cellspacing=0 cellpadding=0>
		<tr height="25px" class="menu">
			<td>
				<div id="menu">
					<ul class="menu">


						<c:forEach var="item" items='${allMenuList}' varStatus="s">
							<c:if test="${item.biMenu.menuType=='01'}">
								<li>
									<a href="${path}${item.biMenu.url}"><span>${item.biMenu.name}</span></a>
									  <div>
									<ul>
										<c:forEach var="inItem" items="${allMenuList}" varStatus="s">
											<c:if test="${item.biMenu.id==inItem.biMenu.preId}">
												<li>
													<a href="${path}${inItem.biMenu.url}"><span>${inItem.biMenu.name}</span></a>
												</li>
											</c:if>
										</c:forEach>
										
									</ul>
									</div>
								</li>
							</c:if>
						</c:forEach>
						
						
						
						  
						
					</ul>
				</div>
			</td>
		</tr>
		<a href="http://apycom.com/"></a>
		</div>
	</table>
</div>
