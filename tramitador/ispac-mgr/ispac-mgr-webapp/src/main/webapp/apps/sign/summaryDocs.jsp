<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><bean:message key="head.title"/></title>
	<meta http-equiv="Content Type" content="text/html; charset=iso-8859-1" />
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos.css"/>'/>
	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'/>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'/>
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
					<div class="acciones_ficha">
						<a href="#" id="btnCancel" class="btnCancel"><bean:message key="common.message.close" /></a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">

					<logic:notEmpty name="SIGN_ERROR">
						<p class="fila_sub clearfix">
							<nobr><bean:write name="SIGN_ERROR"/></nobr>
						</p>
					</logic:notEmpty>
					<logic:notEmpty name="DOCUMENTS_OK">
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="sign.documents.sign.ok"/>:</nobr></label>
							<br/><br/>
							<ul>
							<logic:iterate name="DOCUMENTS_OK" id="document">
								<li><bean:write name="document" property="property(NOMBRE)"/> [<bean:write name="document" property="property(DESCRIPCION)"/>]</li>
							</logic:iterate>
							</ul>
						</p>
					</logic:notEmpty>
					
					<logic:notEmpty name="DOCUMENTS_KO">
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="sign.documents.sign.ko"/>:</nobr></label>
							<br/><br/>
							<ul>
							<logic:iterate name="DOCUMENTS_KO" id="info">
								<bean:define id="documento" name="info" property="documento"/>
								<li><bean:write name="documento" property="property(NOMBRE)"/> [<bean:write name="documento" property="property(DESCRIPCION)"/>]: <bean:write name="info" property="error"/></li>
							</logic:iterate>
							</ul>
						</p>
					</logic:notEmpty>

					<logic:notEmpty name="CIRCUIT_DOCUMENTS_OK">
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="sign.documents.circuit.ok"/>:</nobr></label>
							<br/><br/>
							<ul>
							<logic:iterate name="CIRCUIT_DOCUMENTS_OK" id="document">
								<li><bean:write name="document" property="property(NOMBRE)"/> [<bean:write name="document" property="property(DESCRIPCION)"/>]</li>
							</logic:iterate>
							</ul>
						</p>
					</logic:notEmpty>
					
					<logic:notEmpty name="CIRCUIT_DOCUMENTS_KO">
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="sign.documents.circuit.ko"/>:</nobr></label>
							<br/><br/>
							<ul>
							<logic:iterate name="CIRCUIT_DOCUMENTS_KO" id="info">
								<bean:define id="documento" name="info" property="documento"/>
								<li><bean:write name="documento" property="property(NOMBRE)"/> [<bean:write name="documento" property="property(DESCRIPCION)"/>]: <bean:write name="info" property="error"/></li>
							</logic:iterate>
							</ul>
						</p>
					</logic:notEmpty>
				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->
</body>
</html>

<script type="text/javascript">
	$(document).ready(function() {
		$("#btnCancel").click(function() {
			<ispac:hideframe refresh="true"/>
		});
		 
		$("#btnClose").click(function() {
			<ispac:hideframe/>
		});
	});
	
	$("#contenido").draggable();
	positionMiddleScreen('contenido');
</script>	
