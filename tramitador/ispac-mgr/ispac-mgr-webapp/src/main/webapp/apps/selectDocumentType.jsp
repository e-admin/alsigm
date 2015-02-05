<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Búsqueda</title>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
	
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	
</head>

<body>

<div id="contenido" class="move">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="selectDocument.title"/></h4>
				<div class="acciones_ficha">
					<a href="#" id="btnCancel"onclick='<ispac:hideframe />' class="btnCancel">
						<bean:message key="common.message.close" />
					</a>	
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
	 	</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
		
				<logic:messagesPresent>
					<div class="infoError"><bean:message key="forms.errors.messagesPresent" /></div>
				</logic:messagesPresent>
				<logic:present name="ltDocumentTypes">

					<logic:iterate name="ltDocumentTypes" id="documentType">
						
							<c:set var="action" value="${requestScope.action}"/>
							<c:url value="${action}" var="_link">
								<c:param name="documentTypeId">
									<bean:write name="documentType" property="property(ID)" />
								</c:param>
							</c:url>
							<p>
								<label class="popUp">
									<a class="link" href='<c:out value="${_link}"/>'><bean:write name="documentType" property="property(NOMBRE)" /></a>
								</label>
							</p>
						
					</logic:iterate>
					
				</logic:present>
																		
		</div> <%--seccion ficha --%>
	</div><%--fin cuerpo ficha --%>
		
</div><%--fin  ficha --%>
<div><%--fin contenido --%>


</body>

</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>