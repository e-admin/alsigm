<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="urlparams" name="URLParams"/>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<title><bean:message key="select.unidadTramitadora"/></title>
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	<script language='JavaScript' type='text/javascript'>
	//<!--
		function checkSelection() {	
			var data = $('input:radio[name=finalId]:checked').val()
			if (data == null) {
				jAlert('<bean:message key="error.users.noSelected"/>', 
					'<bean:message key="common.alert"/>', 
					'<bean:message key="common.message.ok"/>' , 
					'<bean:message key="common.message.cancel"/>');
				return false;
			}
		}
		
		function hide() {
			<ispac:hideframe id="workframe"/>
		}
	//-->
	</script>
</head>

<body>
	<html:form action='<%="/producersWizard.do?" + urlparams%>'>
	<div id="contenido" class="move">
		<div class="ficha">
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="select.unidadTramitadora"/></h4>
					<div class="acciones_ficha">
						<input type="submit" value='<bean:message key="forms.button.aceptar"/>' 
							class="btnOk" id="btnOk" onclick="javascript:return checkSelection();"/>
						<input type="button" value='<bean:message key="common.message.cancel"/>' 
							class="btnCancel" id="btnCancel" onclick="javascript:hide();"/>
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
					
						<c:set var="ancestors" value="${requestScope.ancestors}"/>
						<c:if test="${!empty ancestors}">
							<div class="cabecera_seccion">
							<c:forEach items="${ancestors}" var="ancestor" varStatus="status">
								<c:choose>
									<c:when test="${status.count == 1}">
										<c:out value="${ancestor.name}" />
									</c:when>
									<c:otherwise>
										, <html:link action='<%="/producersWizard.do?" + urlparams%>' 
												styleClass="resplink" 
												paramId="navigationId"
												paramName="ancestor"
												paramProperty="id">
											<c:out value="${ancestor.name}" />
										</html:link>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</div>
						</c:if>
					
						<c:if test="${!empty requestScope.children}">
							<display:table name="children" 
								id="child" 
								export="false" 
								class="tableDisplayUserManager"
								sort="list" 
								requestURI="/producersWizard.do">

								<display:setProperty name="css.tr.even" value="none"/>
								<display:setProperty name="css.tr.odd" value="none"/>
								<display:setProperty name="basic.show.header" value="false"/>
																	
								<display:column titleKey='mgr.common.nombre' 
										headerClass='headerDisplay'
										class='width100percent'>
									
									<input type="radio" name="finalId" value='<c:out value="${child.id}"/>'/>
									<img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>
									<html:link action='<%="/producersWizard.do?" + urlparams%>' 
											styleClass="resplink"
											paramId="navigationId"
											paramName="child"
											paramProperty="id">
										<c:out value="${child.name}"/>
									</html:link>
								</display:column>
							</display:table>
						</c:if>
					
				</div>
			</div>
		</div>
	</div>
	</html:form>

</body>
</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>