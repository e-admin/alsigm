<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ page contentType="text/html; charset=ISO-8859-1" %>

<!-- <tiles:getAsString name="cuerpo" ignore="true"/> -->
<html>

	<head>

		<tiles:insert attribute="head" ignore="true" />
		<tiles:insert attribute="sessionAlive" ignore="true" />
		<tiles:importAttribute name="jsFiles" ignore="true" />
		<c:forEach var="aJSFile" items="${jsFiles}">
			<script language="JavaScript1.2" src="<c:url value="${aJSFile}" />" type="text/JavaScript"></script>
		</c:forEach>
		<tiles:importAttribute name="jsFilesPage" ignore="true" />
		<c:forEach var="aJSFilePage" items="${jsFilesPage}">
			<script language="JavaScript1.2" src="<c:url value="${aJSFilePage}" />" type="text/JavaScript"></script>
		</c:forEach>
		<jsp:include page="resizeTable.jsp"/>

		<script>
			function arrangeWorkspace() {
				var workspaceHeight = Math.max(330, (xClientHeight() - 152));
				xHeight('arbol', workspaceHeight);
				xHeight('layout', workspaceHeight);
				xHeight('basefrm', workspaceHeight + 22);

				reescalarPanelDatos();
			}

			function reescalarPanelDatos() {
				var frameContenido = document.getElementById("basefrm");
				var menu = document.getElementById("menuContainer");
				var treeView = document.getElementById("treeView");
				var areaContenido = document.getElementById("areaContenido");

				var menuWidth = menu.clientWidth;
				var treeviewWidth = treeView.clientWidth;
				var buttonsBarWidth = document.getElementById("buttonsBar").clientWidth;

				var visibilidadMenu = menu.style.display != 'none'
						|| treeView.style.display != 'none';
				var visibleArbol = treeView.style.display != 'none' ? true : false;

				var anchuraMenu = visibleArbol ? treeviewWidth : menuWidth;
				anchuraMenu = visibilidadMenu ? anchuraMenu : 0;

				var winheight = parseInt(document.documentElement.scrollHeight);
				var boheight = parseInt(document.body.scrollHeight);

				var hasVerticalScrollBar = Math.abs(boheight - winheight) > 0 ? true
						: false;

				if (parseInt(navigator.appVersion) > 3) {
					if (navigator.appName == "Netscape") {
						winW = window.innerWidth - 16;
					}
					if (navigator.appName.indexOf("Microsoft") != -1) {
						winW = document.body.offsetWidth - 20;
					}
				}
				frameContenido.style.width = winW - anchuraMenu - buttonsBarWidth - 16 + (document.all ? 0 : +17);
				areaContenido.style.width = winW - anchuraMenu - buttonsBarWidth - 13 + (document.all ? 0 : -5);
				frameContenido.style.visibility = "visible"; //para evitar que se vea mal durante una decima de segundo

				if(basefrm && (basefrm.resizeTable)) {
					basefrm.resizeTable();
				}
			}

			function showTreeView(url){
				var frameArbol = document.getElementById("arbol");
				frameArbol.src=url;
			}

			function updateTreeView(node){
				var frameArbol = document.getElementById("arbol");
				arbol.refreshWindow(node);
			}

			function showTreeContent(url){
				var frameContentArbol = document.getElementById("basefrm");
				frameContentArbol.src=url;
			}

			function updateTreeContent(url){
				var frameContentArbol = document.getElementById("basefrm");
				frameContentArbol.src=url;
			}

			function isFondosTree(){
				var frameArbol = document.getElementById("arbol");
				if (arbol && (arbol.isFondosTree))
					return arbol.isFondosTree();
				return false;
			}

			function isDepositoTree(){
				var frameArbol = document.getElementById("arbol");
				if (arbol && (arbol.isDepositoTree))
					return arbol.isDepositoTree();
				return false;
			}

			function isDocsTree(){
				var frameArbol = document.getElementById("arbol");
				if (arbol && (arbol.isDocsTree))
					return arbol.isDocsTree();
				return false;
			}

			function isOrganizacionTree(){
				var frameArbol = document.getElementById("arbol");
				if (arbol && (arbol.isOrganizacionTree))
					return arbol.isOrganizacionTree();
				return false;
			}

			function isSalasConsultaTree(){
				var frameArbol = document.getElementById("arbol");
				if (frameArbol && (frameArbol.isSalasConsultaTree))
					return frameArbol.isSalasConsultaTree();
				return false;
			}

			function getIframeWidthSize(){
				var widthIframe = xWidth('basefrm');
				return widthIframe - '70';
			}
		</script>
	</head>

	<body onload="javascript:arrangeWorkspace();">

		<c:set var="localeStruts" value="${sessionScope['org.apache.struts.action.LOCALE']}"/>
		<fmt:setLocale value="${localeStruts}" scope="session"/>

		<tiles:insert page="/pages/tiles/controls/workInProgress.jsp"/>
		<tiles:insert page="/pages/tiles/controls/workInProgressIFrame.jsp"/>

		<tiles:insert attribute="cabecera" ignore="true" />

		<div id="topContent" style="position:relative;padding-top:2px;padding-left:6px;padding-right:6px">

			<tiles:insert attribute="usuario" ignore="true" />

			<script>
				var openMenuImg = '<c:url value="/pages/images/menu_on.gif" />';
				var closedMenuImg = '<c:url value="/pages/images/menu_off.gif" />';
				var openExplorerImg = '<c:url value="/pages/images/explorer_on.gif" />';
				var closedExplorerImg = '<c:url value="/pages/images/explorer_off.gif" />';

				function hideMenu() {
					var menu = xGetElementById('menuContainer');
					xDisplay(menu, 'none');
					xGetElementById('areaContenido').style.paddingLeft = '25px';
					xGetElementById('menuControlImg').src = closedMenuImg;
				}

				function showMenu() {
					var menuContainer = xGetElementById('menuContainer');
					var treeView = xGetElementById('treeView');
					xDisplay(menuContainer, 'block');
					if (treeView.style.display != 'none') {
						xDisplay('treeView', 'none');
						xGetElementById('areaContenido').style.paddingLeft = '25px';
						xGetElementById('navigatorControlImg').src = closedExplorerImg;
					}
					var menuWidth = xWidth(menuContainer);
					var contentPadding = parseInt(xGetElementById('areaContenido').style.paddingLeft) + menuWidth;
					xGetElementById('areaContenido').style.paddingLeft = '' + contentPadding + 'px';
					xGetElementById('menuControlImg').src = openMenuImg;
				}

				function changeMenuVisibility() {
					var status = xGetElementById('menuContainer').style.display;
					document.getElementById('basefrm').style.visibility = "hidden";
					if (status == 'none')
						showMenu();
					else
						hideMenu();

					reescalarPanelDatos();
				}

				function hideNavigator() {
					var treeView = xGetElementById('treeView');
					xDisplay(treeView, 'none');
					xGetElementById('areaContenido').style.paddingLeft = '25px';
					xGetElementById('navigatorControlImg').src = closedExplorerImg;
				}

				var timer;
				function showNavigator() {
					var menuContainer = xGetElementById('menuContainer');
					var treeView = xGetElementById('treeView');
					xDisplay(treeView, 'block');
					if (menuContainer.style.display != 'none') {
						xDisplay('menuContainer', 'none');
						xGetElementById('areaContenido').style.paddingLeft = '25px';
						xGetElementById('menuControlImg').src = closedMenuImg;
					}

					if (!document.all) {
						if (treeView.style.display == 'none') {
							timer = setTimeout(refreshVistaArbol, 50);
						}
					}

					var treeViewWidth = xWidth(treeView);
					var contentPadding = parseInt(xGetElementById('areaContenido').style.paddingLeft) + treeViewWidth;
					xGetElementById('areaContenido').style.paddingLeft = '' + contentPadding + 'px';
					xGetElementById('navigatorControlImg').src = openExplorerImg;

				}

				function refreshVistaArbol() {
					if (!document.all) {
						var pageFrames = window.top.frames;
						for ( var i = 0; i < pageFrames.length; i++) {
							if (pageFrames[i] != window && pageFrames[i].refreshWindow) {
								pageFrames[i]
										.refreshWindow('<c:out value="${sessionScope[viewName].selectedNode.nodePath}" />');
							}
						}
					}
				}

				function changeNavigatorVisibility() {
					var status = xGetElementById('treeView').style.display;
					document.getElementById('basefrm').style.visibility = "hidden";
					if (status == 'none')
						showNavigator();
					else
						hideNavigator();
					reescalarPanelDatos();
				}
			</script>

			<div class="content_container">

				<div id="buttonsBar" style="position:absolute;z-index:10;top:32px">
					<a href="javascript:changeMenuVisibility()">
						<html:img page="/pages/images/menu_off.gif" border="0" styleId="menuControlImg" />
					</a><br>

						<a href="javascript:changeNavigatorVisibility()" style="display:none;" id="navigatorButtom">
							<html:img page="/pages/images/explorer_off.gif" border="0" style="position:relative;top:6px" styleId="navigatorControlImg" />
						</a>

				</div>

				<div id="menuContainer" style="display:none;position:absolute;left:26px;float:left;z-index:1;width:258px">
					<tiles:insert page="/pages/tiles/nuevoMenu.jsp" />
				</div>

				<div id="treeView" style="display:none;position:absolute;left:26px;float:left">
					<table id="layout" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div id="treeContainer" class="frameArbol"  style="padding:6px">
									<div id="barra_menu_frame">&nbsp;</div>
									<iframe name="arbol" id="arbol" class="bloque_frame" width="210" scrolling="auto" frameborder="0" src="javascript:''"></iframe>
								</div>
							</td>
							<td valign="middle">
								<div id="resizer">
									<script>
										var contentWidth = null;
										function hideContent() {
											var treeView = xGetElementById('treeContainer');
											var content = xGetElementById('basefrm');
											if (content.style.display == 'none')
												return;
											if (treeView.style.display == 'none') {
												xGetElementById('areaContenido').style.paddingLeft = '280px';
												xDisplay(treeView, 'block');
												xAddEventListener('resizer', 'mousedown', startTreePanelResizing,
														false);
											} else {
												contentWidth = xWidth(content);
												xDisplay(content, 'none');
												var currentWidth = xWidth('arbol');
												xWidth('arbol', currentWidth + contentWidth);
												xRemoveEventListener('resizer', 'mousedown',
														startTreePanelResizing, false);
											}
										}

										function hideTreeView() {
											var content = xGetElementById('basefrm');
											var treeView = xGetElementById('treeContainer');
											if (treeView.style.display == 'none')
												return;
											if (content.style.display == 'none') {
												xDisplay(content, 'block');
												xWidth('arbol', xWidth('arbol') - contentWidth);
												xAddEventListener('resizer', 'mousedown', startTreePanelResizing,
														false);

												//ocultar y mostrar el treeView (si no pierde los eventos encima de los input)
												var treeView1 = xGetElementById('treeView');
												xDisplay(treeView1, 'none');
												xDisplay(treeView1, 'block');

											} else {
												hideNavigator();
											}
											reescalarPanelDatos();
										}
									</script>
									<html:img page="/pages/images/expand_right.gif" onclick="hideContent()" styleClass="imgTextMiddleHand" /><br>
									<html:img page="/pages/images/resizer.gif" styleClass="imgTextMiddleHand"/><br>
									<html:img page="/pages/images/expand_left.gif" onclick="hideTreeView()" styleClass="imgTextMiddleHand" />
								</div>
							</td>
						</tr>
					</table>
				</div>

				<div id="areaContenido" style="padding-left:25px">

					<tiles:insert attribute="barra_navegacion" ignore="true" />

					<tiles:useAttribute name="urlIFrame" scope="request" ignore="true"/>
					<c:choose>
						<c:when test="${not empty urlIFrame}">
							<c:url var="urlIFrameContent" value="${urlIFrame}"/>
							<iframe name="basefrm" id="basefrm" marginwidth="0" src="<c:out value='${urlIFrameContent}' escapeXml='false'/>" scrolling="auto" frameborder="0"  style="width:100%"></iframe>
						</c:when>
						<c:otherwise>
							<iframe name="basefrm" id="basefrm" marginwidth="0" scrolling="auto" frameborder="0"  style="width:100%" src="javascript:''"></iframe>
						</c:otherwise>
					</c:choose>
					<tiles:insert attribute="pie_pagina" ignore="true" />
				</div>
			</div>

				<script>
					xAddEventListener('resizer', 'mousedown', startTreePanelResizing, false);
					function startTreePanelResizing(oEvent) {
						var evt = new xEvent(oEvent);
						xPreventDefault(oEvent);
						mouseX = evt.pageX;
						xAddEventListener(document, 'mousemove', resizingTreePanel, false);
						xAddEventListener(document, 'mouseup', endTreePanelResizing, false);
						dragging = 1;
					}

					function resizingTreePanel(oEvent) {
						var evt = new xEvent(oEvent);
						if (dragging) {
							xPreventDefault(oEvent);
							var treeView = document.getElementById("treeView");
							var antMouseX = treeView.clientWidth + treeView.offsetLeft
									+ treeView.style.paddingLeft;

							var dx = evt.pageX - antMouseX;
							antMouseX = evt.pageX;
							var currentWidth = xWidth('arbol') - 2;
							var currentTreeViewWidth = xWidth('treeView') - 2;
							if ((currentWidth + dx) > 80) {
								var areaContenido = xGetElementById('areaContenido');
								var paddingAreaContenido = areaContenido.style.paddingLeft;
								//alert(paddingAreaContenido+"+"+dx+"+"+(dx!=0?(document.all?0:(dx>0?2:-2)):0)));
								var newPadding = parseInt(paddingAreaContenido) + dx
										- (document.all ? 0 : 2);
								//newPadding = parseInt(paddingAreaContenido) + dx+ -2;
								//newPadding=0;
								areaContenido.style.paddingLeft = '' + newPadding + 'px';
								xWidth('arbol', currentWidth + dx);
								xWidth('treeView', currentTreeViewWidth + dx);
								reescalarPanelDatos();
							}

						}
					}

					function endTreePanelResizing(oEvent) {
						if (dragging) {
							xPreventDefault(oEvent);
							mouseX = dragging = 0;
							xRemoveEventListener(document, 'mouseup', endTreePanelResizing,
									false);
							xRemoveEventListener(document, 'mousemove', resizingTreePanel,
									false);
						}
					}
					var dragging = 0;
					var mouseX = 0;
				</script>
		</div>
	</body>
</html>
<!-- <tiles:getAsString name="cuerpo" ignore="true"/> -->