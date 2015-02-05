<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="excepcion" value="${requestScope[appConstants.common.JAVAX_SERVLET_ERROR_EXCEPCION]}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<?xml version="1.0" encoding="iso-8859-1"?>
<html:html xhtml="true">
	<head>
		<html:base />
		<title>
			<bean:message key="welcome.title"/>
		</title>
		<link type="text/css" 
			  rel="stylesheet" 
			  href="pages/css/general.css" />
		<link type="text/css" 
			  rel="stylesheet" 
			  href="pages/css/archivoGeneral.css" />
		<link type="text/css" 
			  rel="stylesheet" 
			  href="pages/css/position.css" /> 	 		      
		<link type="text/css" 
			  rel="stylesheet" 
			  href="pages/css/archivoPosition.css" /> 	 		      
		<link type="text/css"  		
			  rel="stylesheet"  		      
			  href="pages/css/displaytag.css" /> 	
		<meta http-equiv="Cache-Control" content="no-cache" /> 
		<meta http-equiv="Expires" content="-1" />
		<meta http-equiv="Pragma" content="no-cache" /> 
	</head>
	<body>
		<div id="contenedor_ficha_centrada">
			<div class="ficha">
				<h1><span>
				<div class="w100"> 
					<TABLE class="w98" cellpadding="0" cellspacing="0">
					  <TR>
					    <TD class="etiquetaAzul12Bold" height="25px">
							Información de Errores
						</TD>
					    <TD align="right">
						<TABLE cellpadding=0 cellspacing=0>
						  <TR>
					       <TD>
								<c:url var="logoutURL" value="/action/loginAction"/>
								<a href="<c:out value="${logoutURL}" escapeXml="false"/>" class="etiquetaAzul12Bold">
									<html:img page="/pages/images/close.gif" styleClass="imgTextMiddle"/> 
									<bean:message key="archigest.archivo.volver" />
								</a>
						   </TD>
					     </TR>
						</TABLE>
						</TD>
					  </TR>
					</TABLE>
				</div>
				</span></h1>

				<div class="cuerpo_drcha">
				<div class="cuerpo_izda">

					<div class="separador1">&nbsp;</div>

					<div class="bloque">
						<c:choose>
							<c:when test="${!empty excepcion}">
								<table width="90%" cellspacing="0" cellpadding="2">
								  <tr>
									<td class="etiquetaAzul12Bold">
										<bean:message key="global.error.body"/>
									</td>
								  </tr>
								  <tr>
									<td>
										<archivo:errors />
									</td>
								  </tr>
								  <c:if test="${!empty excepcion.message}">
								  <tr>
									<td class="etiquetaAzul11Normal">
										- <c:out value="${excepcion.message}" />
									</td>
								  </tr>
								  </c:if>
								</table>
							
								<div class="separador8">&nbsp;</div>
							
								<table width="90%" cellspacing="0" cellpadding="1">
							  	  <c:set var="stackElements" value="${excepcion.stackTrace}" />
								  <c:forEach var="stackElement" items="${stackElements}">
								  <tr>
									<td class="etiquetaAzul11Normal">
										- <c:out value="${stackElement}" />
									</td>
								  </tr>
								  </c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<table width="90%" cellspacing="0" cellpadding="2">
								  <tr>
									<td class="etiquetaAzul12Bold">
										<bean:message key="global.error.internal"/>
									</td>
								  </tr>
								</table>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="separador5"></div>

				</div> <%--cuerpo_izda --%>
				</div> <%--cuerpo_drcha --%>
		
				<h2><span>&nbsp;</span></h2>
		
			</div> <%--ficha --%>
		</div> <%--contenedor_ficha_centrada --%>
	</body>
</html:html>