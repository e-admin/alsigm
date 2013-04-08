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
	<ispac:message/>

	<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					
					<div class="acciones_ficha">
						<a href="#"  id="btnCancel" class="btnCancel" onclick='<ispac:hideframe refresh="true"/>'><bean:message key="common.message.close"/></a>
					</div>
				</div>
			</div>	
			
			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				  <form>
				  <p>
				  <nobr>
					<logic:empty name="typeInvalid">
							<label class="popUp"><bean:message key="sign.message.error.failure"/></label>
					</logic:empty>
					<logic:notEmpty name="typeInvalid">
							<label class="popUp"><bean:message key="sign.message.error.typeInvalid"/></label>
					</logic:notEmpty>
					<logic:present name="SIGN_ERROR">
							<label class="popUp"><bean:write name="SIGN_ERROR"/></label>
					</logic:present>					
				  </nobr>
					</p>
					</form>
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