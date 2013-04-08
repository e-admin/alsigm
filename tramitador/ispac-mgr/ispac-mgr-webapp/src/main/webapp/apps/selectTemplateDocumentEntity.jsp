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
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

</head>

<body>


<div id="contenido" class="move">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="selectTemplate.title"/></h4>
				<div class="acciones_ficha">

				<logic:present name="volver">
					<a target="_self" href='<ispac:rewrite action='<%="selectTemplateDocumentEntity.do"%>'/>' class="btnCancel"><bean:message key="common.message.return"/></a>
				</logic:present>

				<input type="button"  value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe/>'/>

				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
	 	</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
			<form>
				<logic:messagesPresent>
					<div class="infoError"><bean:message key="forms.errors.messagesPresent" /></div>
				</logic:messagesPresent>
				<logic:present name="ltDocumentTypesTemplates">

					<div class="cabecera_seccion">
						<h4><bean:message key="forms.exp.title.typedoc"/></h4>
					</div>

					<logic:iterate name="ltDocumentTypesTemplates" id="documentType">

						<label class="popUpInfo"><bean:write name="documentType" property="property(NOMBRE)" />:</label>

						<logic:iterate name="documentType" property="property(TEMPLATES)" id="template">
							<c:url value="generateDocumentFromTemplateEntity.do" var="_link">
								<logic:present name="documentId">
									<c:param name="documentId">
										<bean:write name="documentId" />
									</c:param>
								</logic:present>
								<logic:present name="volver">
								<c:param name="crearTramite"  value="true">
								</c:param>
								</logic:present>
								<logic:present name="idTaskPcd">
								<c:param name="idTaskPcd">
										<bean:write name="idTaskPcd" />
									</c:param>
								</logic:present>
								<c:param name="documentTypeId">
									<bean:write name="documentType" property="property(ID)" />
								</c:param>
								<c:param name="templateId" >
									<bean:write name="template" property="property(ID)" />
								</c:param>
							</c:url>
							<br/>
							<a href="javascript:sure('<c:out value="${_link}"/>','<bean:message key="common.needConfirm"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')" class="linkRight" ><bean:write name="template" property="property(NOMBRE)" /></a>
						</logic:iterate>
						<br/>

					</logic:iterate>

				</logic:present>

				<logic:present name="ltTasks">
					<div class="cabecera_seccion">
						<h4><bean:message key="forms.exp.title.task"/></h4>
					</div>

					<logic:iterate name="ltTasks" id="task">

						<c:url value="selectTemplateDocumentEntity.do" var="_link">
							<logic:present name="task">
								<c:param name="idTask">
									<bean:write name="task" property="property(ID_CTTRAMITE)" />
								</c:param>
									<c:param name="idTaskPcd">
									<bean:write name="task" property="property(ID)" />
								</c:param>
								<c:param name="volver" value="true"/>
							</logic:present>
						</c:url>
						<div>
						<a href='<c:out value="${_link}"/>' class="linkRight" ><bean:write name="task" property="property(NOMBRE)" /></a>
						</div>

					</logic:iterate>
					<p/>
				</logic:present>

		</div> <%--seccion ficha --%>
	</div><%--fin cuerpo ficha --%>
	</form>
</div><%--fin  ficha --%>
</div><%--fin contenido --%>

<ispac:layer id="waitOperationInProgress" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />

</body>
</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
		$("#waitOperationInProgress").draggable();
	});
</script>
