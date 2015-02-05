<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/sessionTag.tld" prefix="session"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html:xhtml/>
<html:base /> 		
<meta http-equiv="Cache-Control" content="no-cache" /> 
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Pragma" content="no-cache" />
<title>
	<c:set var="appUser" value="${sessionScope[appConstants.common.USUARIOKEY]}" />
	<c:choose>
		<c:when test="${appUser!=null}">
			<c:out value="${appUser.name}" /> <c:out value="${appUser.surname}" /> - <bean:message key="welcome.title"/>
		</c:when>
		<c:otherwise>
			<bean:message key="welcome.title"/>
		</c:otherwise>
	</c:choose>
</title>
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/general.css" />
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/archivoGeneral.css" />
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/position.css" /> 	 		      
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/archivoPosition.css" /> 	 		      
<link type="text/css"  		
	  rel="stylesheet"  		      
	  href="../css/displaytag.css" /> 
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/calendarTag.css" />
<link type="text/css" 
	  rel="stylesheet" 
	  href="../css/estilos.css" />	

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href="../css/estilos_ie5.css"/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href="../css/estilos_ie6.css">
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href="../css/estilos_ie7.css">
	<![endif]-->	

	<!--[if lte IE 6]>
		<script type="text/javascript" src="../../js/iepngfix_tilebg.js"></script>
	<![endif]-->