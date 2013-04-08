<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<html>
	<head>
		<tiles:insert attribute="head" ignore="true" />
		<tiles:insert attribute="sessionAlive" ignore="true" />
	</head>
	<body>	
			<c:set var="localeStruts" value="${sessionScope['org.apache.struts.action.LOCALE']}"/>		
			<fmt:setLocale value="${localeStruts}" scope="session"/>
					
			<%-- Cabecera de la pagina --%>		
			<tiles:insert attribute="cabecera" ignore="true" />

			<%-- Cuerpo principal de la página --%>
			<tiles:insert attribute="cuerpo" ignore="true" />
			
			<%-- Pie de página --%>
			<tiles:insert attribute="pie_pagina" ignore="true" />
	</body>
</html>