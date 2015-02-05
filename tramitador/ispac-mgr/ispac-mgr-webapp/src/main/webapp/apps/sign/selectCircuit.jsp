<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title></title>
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
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>

	</head>
	<body>

		<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="sign.document.circuit.available"/></h4>
					<div class="acciones_ficha">
						<a href="#" id="btnCancel" onclick='<ispac:hideframe/>' class="btnCancel">
							<bean:message key="common.message.close" />
						</a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
			<form>
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent>
					<p>

					<c:set var="action"><c:out value="${requestScope.action}" default="signDocument.do"/></c:set>
					
					<c:choose>
						<c:when test="${!empty requestScope[appConstants.actions.SIGN_CIRCUIT_LIST]}">
							<c:forEach items="${requestScope[appConstants.actions.SIGN_CIRCUIT_LIST]}" var="circuit">
							<c:choose>
								<c:when test="${!empty sessionScope['defaultPortafirmas']}">
									<c:url var="_link" value="${action}">
										<c:param name="method" value="${appConstants.actions.INIT_SIGN_CIRCUIT}"/>
										<c:param name="${appConstants.actions.SIGN_CIRCUIT_ID}"><bean:write name="circuit" property="property(ID_CIRCUITO)" /></c:param>
									</c:url>
									<a class="linkRight" href='<c:out value="${_link}"/>'><bean:write name="circuit" property="property(DESCRIPCION)"/></a><br/><br/>
								</c:when>
								<c:otherwise>
									<c:url var="_link" value="${action}">
										<c:param name="method" value="${appConstants.actions.SET_PROPERTIES_SIGN_CIRCUIT}"/>
										<c:param name="${appConstants.actions.SIGN_CIRCUIT_ID}"><bean:write name="circuit" property="property(ID_CIRCUITO)" /></c:param>
									</c:url>

									<a class="linkRight" href='<c:out value="${_link}"/>'><bean:write name="circuit" property="property(DESCRIPCION)"/></a><br/><br/>

								</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<label class="popUp"><bean:message key="sign.document.circuits.empty"/></label>
							<br/><br/>
						</c:otherwise>

					</c:choose>
					</p>
				</div>
				</form>
	   		</div>
		</div>
	</div>
	</body>
</html>


<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>