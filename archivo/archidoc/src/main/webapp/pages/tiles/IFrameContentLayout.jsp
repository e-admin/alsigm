<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!-- <tiles:getAsString name="cuerpo" ignore="true"/> -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ page contentType="text/html; charset=ISO-8859-1" %>


<html lang="es">

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
	</head>

	<body>
		<c:set var="localeStruts" value="${sessionScope['org.apache.struts.action.LOCALE']}"/>
		<fmt:setLocale value="${localeStruts}" scope="session"/>
		<c:url var="progressBarURL" value="/action/progressBarRefresher" scope="request">
			<c:param name="method" value="updateProgressBar"/>
		</c:url>
		<c:url var="resetProgressBarURL" value="/action/progressBarRefresher" scope="request">
			<c:param name="method" value="resetProgressBar"/>
		</c:url>
		<div id="contenedor">

			<div style="padding-top:2px;padding-left:6px;padding-right:6px">

				<div id="areaContenido" >

					<tiles:insert attribute="barra_navegacion" ignore="true" />

					<tiles:useAttribute name="useArbol" scope="request" ignore="true"/>

					<c:if test="${not empty useArbol}">
						<c:set var="showTreeViewAction">
							<tiles:insert attribute="showTreeViewAction" ignore="true" flush="false" />
						</c:set>
						<c:url var="showTreeViewURL" value="${showTreeViewAction}" scope="request">
							<c:param name="actionToPerform">
								<c:out value="${viewAction}">crearVista</c:out>
							</c:param>
							<c:if test="${!empty viewName}"><c:param name="viewName" value="${viewName}" /></c:if>
						</c:url>

						<c:choose>
							<c:when test="${empty showContentURL}">
								<c:set var="TREE_VIEW" value="${sessionScope[viewName]}" />
								<c:choose>
									<c:when test="${empty TREE_VIEW}">
										<c:set var="defaultViewURL">
											<tiles:insert attribute="defaultViewURL" ignore="true" flush="false" />
										</c:set>
										<c:url var="showContentURL" value="${defaultViewURL}" scope="request"/>
									</c:when>
									<c:otherwise>
										<c:url var="showContentURL" value="${showTreeViewAction}" scope="request">
											<c:param name="actionToPerform" value="showSelectedNode" />
											<c:param name="viewName" value="${viewName}" />
										</c:url>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:url var="showContentURL" value="${showContentURL}" scope="request"/>
							</c:otherwise>
						</c:choose>
					</c:if>

					<div>
						<tiles:insert attribute="cuerpo" ignore="true" />

						<tiles:insert attribute="pie_pagina" ignore="true" />

						<tiles:useAttribute name="frameRefresher" id="framRefresher" scope="request" ignore="true"/>

						<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>
						<c:choose>
							<c:when test="${not empty useArbol}">
								<c:set var="treeFondos" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}"/>
								<c:set var="treeDeposito" value="${sessionScope[appConstants.deposito.DEPOSITO_VIEW_NAME]}"/>
								<c:set var="treeDocs" value="${sessionScope[appConstants.documentos.DOCUMENT_TREE_KEY]}"/>
								<c:set var="treeOrganizacion" value="${sessionScope[appConstants.organizacion.ORGANIZACION_VIEW_NAME]}"/>
								<c:set var="treeSalasConsulta" value="${sessionScope[appConstants.salas.SALAS_CONSULTA_VIEW_NAME]}"/>
								<c:if test="${(treeFondos!=null) || (treeDeposito!=null) || (treeDocs!=null) || (treeOrganizacion!=null)  || (treeSalasConsulta != null)}">
									<tiles:insert attribute="frameRefresher" ignore="true" />
									<script>
										var navigatorButtom = parent.document.getElementById('navigatorButtom');
										if (navigatorButtom)
											navigatorButtom.style.display='block';
									</script>
								</c:if>

								<c:if test="${requestScope[appConstants.commonConstants.TREE_VIEW_RELOAD]}">
									<c:if test="${treeFondos!=null}">
										<script>
											if (window.parent.isFondosTree){
												if (!window.parent.isFondosTree()){
													if (window.parent.showTreeView) {
														window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
													}
												}
											}
										</script>
									</c:if>

									<c:if test="${treeDeposito!=null}">
										<script>
											if (window.parent.isDepositoTree){
												if (!window.parent.isDepositoTree()){
													if (window.parent.showTreeView) {
														window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
													}
												}
											}
										</script>
									</c:if>

									<c:if test="${treeDocs!=null}">
										<script>
											if (window.parent.isDocsTree){
												if (!window.parent.isDocsTree()){
													if (window.parent.showTreeView) {
														window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
													}
												}
											}
										</script>
									</c:if>

									<c:if test="${treeOrganizacion!=null}">
										<script>
											if (window.parent.isOrganizacionTree){
												if (!window.parent.isOrganizacionTree()){
													if (window.parent.showTreeView) {
														window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
													}
												}
											}
										</script>
									</c:if>

									<c:if test="${treeSalasConsulta!=null}">
										<script>
											if (window.parent.isSalasConsulta){
												if (!window.parent.isSalasConsulta()){
													if (window.parent.showTreeView) {
														window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
													}
												}
											}
										</script>
									</c:if>
								</c:if>
							</c:when>
							<c:otherwise>
								<script>
									var navigatorButtom = parent.document.getElementById('navigatorButtom');
									var treeContainer = parent.document.getElementById('treeView');

									if (navigatorButtom)
										navigatorButtom.style.display='none';

									if (window.parent.hideNavigator)
										window.parent.hideNavigator();

									<c:choose>
										<c:when test="${beanBandeja!=null}">
											if (window.parent.showMenu)
												window.parent.showMenu();
										</c:when>
										<c:otherwise>
											if (window.parent.hideMenu)
												window.parent.hideMenu();
										</c:otherwise>
									</c:choose>

									if (window.parent.arrangeWorkspace)
										window.parent.arrangeWorkspace();
								</script>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		<script>

			if (window.top.hideWorkingDiv)
				window.top.hideWorkingDiv();
			if (window.top.hideWorkingDivIFrame){
				window.top.hideWorkingDivIFrame();
			}
			resizeTable();
			endGeneracion();
		</script>
	</body>
</html>
<!-- <tiles:getAsString name="cuerpo" ignore="true"/> -->