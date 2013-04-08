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
	<html:form action="/signDocuments.do">
	<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
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
					
					<html:hidden property="method" value="sign"/>
					<html:hidden property="signs" styleId="sign"/>
					<html:hidden property="signCertificate" styleId="signCertificate" />
					<html:hidden property="signFormat" styleId="signFormat" />

					<div class="firma">
						<html:submit styleId="submit" styleClass="form_button_white" onclick="return doSign();">
							<bean:message key="forms.button.sign"/>
						</html:submit>
						<p id="enCurso" class="center" style="display:none;" >
							<label class="boldlabel">
								<bean:message key="msg.layer.signOperationInProgress"/>
							</label>
						</p>
					</div>
					<br/><br/>
					<c:set var="hashCodes" value="${requestScope.hashCodes}"/>
					<ispac:sign var="hashCodes" massive="true"/>
				</div>
	   		</div>
		</div>
	</div>
	</html:form>
	</body>
</html>

<script type="text/javascript" language="javascript">
//<!--
	function doInitSign() {
		if (typeof initSign != 'undefined') {
			initSign();
		}
	}

	function doSign() {
		if (typeof sign != 'undefined') {
			document.getElementById("submit").style.display='none';
			document.getElementById("enCurso").style.display='block';
			return sign();
		}
		return false;
	}

	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	doInitSign();
//--!>
</script>