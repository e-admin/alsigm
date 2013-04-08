<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!-- <tiles:getAsString name="cuerpo"/>	 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<?xml version="1.0" encoding="iso-8859-1"?>
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

	</head>
	<body style="margin:0px">

		<c:set var="localeStruts" value="${sessionScope['org.apache.struts.action.LOCALE']}"/>
		<fmt:setLocale value="${localeStruts}" scope="session"/>

		<tiles:insert page="/pages/tiles/controls/workInProgress.jsp"/>

		<tiles:insert attribute="cuerpo" ignore="true" />

		<script>

			if (window.top.hideWorkingDiv)
				window.top.hideWorkingDiv();
			if (window.top.hideWorkingDivIFrame){
				window.top.hideWorkingDivIFrame();
			}
		</script>
	</body>
</html>
<!-- <tiles:getAsString name="cuerpo"/>	 -->