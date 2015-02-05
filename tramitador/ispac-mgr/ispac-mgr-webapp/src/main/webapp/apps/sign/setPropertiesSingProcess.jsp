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


	<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>



<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

	<c:set var="action"><c:out value="${requestScope.action}" default="signDocument.do"/></c:set>
	<c:set var="formAction" value="/${action}?method=${appConstants.actions.INIT_SIGN_CIRCUIT}&${appConstants.actions.SIGN_CIRCUIT_ID}=${requestScope[appConstants.actions.SIGN_CIRCUIT_ID]}"></c:set>


	<jsp:useBean id="formAction" type="java.lang.String"/>


	<html:form action='<%=formAction%>'>
		<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="sign.document.circuit.properties"/></h4>
					<div class="acciones_ficha">

						<input type="button" value='<bean:message key="common.message.ok"/>' class="btnOk" onclick="javascript:validar();"/>
						<a href="#" id="btnCancel" onclick='<ispac:hideframe/>' class="btnCancel">
							<bean:message key="common.message.close" />
						</a>

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
					<ispac:rewrite id="imgcalendar" href="img/calendar/" />
					<ispac:rewrite id="jscalendar" href="../scripts/calendar.js" />
					<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif" />

							<p>
								<label class="popUpInfo" ><nobr><bean:message key="sign.document.circuit.properties.asunto"/>:</nobr></label>
							    <html:text  styleId="subject" property="subject"   styleClass="inputSelectS" readonly="false" size="90" ></html:text>
							</p>
							<p>
								<label class="popUpInfo" ><nobr><bean:message key="sign.document.circuit.properties.fechaInic"/>:</nobr></label>
								<ispac:htmlTextCalendar
											property="fstart"
											readonly="false"
											formId="signForm"
											propertyReadonly="readonly" styleClass="input"
											styleClassReadonly="inputReadOnly" size="12" maxlength="10"
											image='<%= buttoncalendar %>' format="dd/mm/yyyy"
											enablePast="true" />
							</p>
							<p>
								<label class="popUpInfo" ><nobr><bean:message key="sign.document.circuit.properties.fechaExpiracion"/>:</nobr></label>



									<ispac:htmlTextCalendar
											property="fexpiration"
											readonly="false"
											formId="signForm"
											propertyReadonly="readonly" styleClass="input"
											styleClassReadonly="inputReadOnly" size="12" maxlength="10"
											image='<%= buttoncalendar %>' format="dd/mm/yyyy"
											enablePast="true" />
							</p>
							<p>
								<label class="popUpInfo" ><nobr><bean:message key="sign.document.circuit.properties.nivelImp"/>:</nobr></label>

							    <html:select styleId="levelOfImportance"  property="levelOfImportance"  value="0" styleClass="inputSelectS" >
									<option value="NONE"></option>
									<html:option value="4" key="sign.document.circuit.properties.propiedad.urgente"></html:option>
									<html:option value="3" key="sign.document.circuit.properties.propiedad.muyalta"></html:option>
									<html:option value="2" key="sign.document.circuit.properties.propiedad.alta"></html:option>
									<html:option value="1" key="sign.document.circuit.properties.propiedad.normal"></html:option>
						</html:select>
							</p>
							<p>
								<label class="popUpInfo" ><nobr><bean:message key="sign.document.circuit.properties.contenido"/>:</nobr></label>
							    <html:textarea styleId="contentId"   rows="4" cols="90" property="content"  styleClass="inputSelectS" readonly="false"  ></html:textarea>
							</p>




				</div>

	   		</div>
		</div>
	</div>
	</html:form>
	</body>
</html>


<script>
function  validar(){

	  var levelOfImportance=$("#levelOfImportance").find(':selected').val();
	  var subject=document.getElementById("subject").value;
	  var contenido = $("#contentId").val();
		if(levelOfImportance=='NONE' ||levelOfImportance=="" || levelOfImportance==null || levelOfImportance=='undefined'){
				jAlert('<bean:message key="aviso.portafirmas.levelOfImportance.obligatorio"/>' , '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>', '<bean:message key="common.message.cancel"/>');
		}
		else if (subject=="" || subject==null || subject=='undefined'){
			jAlert('<bean:message key="aviso.portafirmas.subject.obligatorio"/>' , '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>', '<bean:message key="common.message.cancel"/>');
		}
		else if(contenido=="" || contenido==null || contenido=='undefined'){
			jAlert('<bean:message key="aviso.portafirmas.content.obligatorio"/>' , '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>', '<bean:message key="common.message.cancel"/>');
		}
		else{
			document.forms[0].submit();
		}
	}

	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});




</script>