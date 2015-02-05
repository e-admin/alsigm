<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@page import="ieci.tdw.ispac.ispaclib.sign.SignDetailEntry"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalles de firma de un documento</title>

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
					<h4><bean:message key="sign.detail.title"/></h4>
					<div class="acciones_ficha">
						<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" id="btnCancel" onclick='<ispac:hideframe/>'/>
					</div>
				</div>
			</div>
		
			<div class="cuerpo_ficha">
			
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent> 
					
						<bean:define id="stateDescripcionS"><bean:message key="sign.detail.stateDescription"/></bean:define>
						<bean:define id="signDateS"><bean:message key="sign.detail.signDate"/></bean:define>
						<bean:define id="integrityS"><bean:message key="sign.detail.integrity"/></bean:define>
						<bean:define id="authorS"><bean:message key="sign.detail.author"/></bean:define>
						<display:table name="details" id="detail" export="true" class="tableDisplay" sort="list" pagesize="40" style="width:95%" 
			requestURI='<%=request.getContextPath() + "/selectObject.do"%>'  >
									
									<display:column title="<%=stateDescripcionS%>" >
											<c:set var="estado"><%=((SignDetailEntry)detail).isFirmado() %></c:set>
											<c:if test="${estado}">
												<bean:message key="sign.detail.state.sign"/>
											</c:if>
												<c:if test="${!estado}">
												<bean:message key="sign.detail.state.notSign"/>
											</c:if>
						
									</display:column>
											
											
									<display:column property="signDate" title="<%=signDateS%>" />	
											
									<display:column title="<%=integrityS%>">
										<bean:message key='<%=((SignDetailEntry)detail).getIntegrity() %>'/>
									</display:column>	
											
									<display:column property="author" title="<%=authorS%>" />	
											
						</display:table>	
						<br/>
				</div>
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