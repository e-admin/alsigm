<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><bean:message key="head.title"/></title>
	<meta http-equiv="Content Type" content="text/html; charset=iso-8859-1" />
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
					<h4><bean:message key="registro.salida.enBloque.form.title"/></h4>
					<div class="acciones_ficha">

						<logic:notPresent name="ERROR">
							<a href="#" id="btnCloseAndRefresh" class="btnClose"><bean:message key="common.message.close"/></a>
						</logic:notPresent>
						
						<logic:present name="ERROR">
							<a href="#" id="btnClose" class="btnClose"><bean:message key="common.message.close"/></a>
						</logic:present>
						
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">

				<%-- ERROR EN REGISTRO DE SALIDA --%>
				<logic:present name="ERROR">
					<logic:equal name="ERROR" value="ERROR_REGISTRO" scope="request">
						<div class="seccion_ficha popUp">
							<p class="error">
								<span><bean:message key="registro.salida.notcreate"/></span>
							</p>
						</div>
					</logic:equal>
				</logic:present>

				<%-- REGISTROS DE SALIDA CREADOS --%>
				<logic:notPresent name="ERROR">
					<div class="seccion_ficha">
					
						<div class="cabecera_seccion">
							<h4><bean:message key="registro.salida.enBloque.resumen"/></h4>
						</div>
					
						<!-- NÚMEROS DE REGISTRO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.salida.registros.enBloque.ok"/></nobr></label>
							<logic:iterate name="REGISTERS_OK" id="register">
								<br/><br/><bean:write name="register"/>
							</logic:iterate>
						</p>
						
						<logic:notEmpty name="REGISTERS_KO">
							<p class="fila_sub clearfix">
								<label class="mid"><nobr><bean:message key="registro.salida.registros.enBloque.ko"/></nobr></label>
								<logic:iterate name="REGISTERS_KO" id="register">
									<br/><br/><bean:write name="register"/>
								</logic:iterate>
							</p>
						</logic:notEmpty>
					</div> <!-- fin seccion ficha-->
				</logic:notPresent>

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->
</body>
</html>


	<script type="text/javascript">

		$(document).ready(function() {
			$("#btnCloseAndRefresh").click(function() {
				<ispac:hideframe refresh="true"/>
			});
			 
			$("#btnClose").click(function() {
				<ispac:hideframe/>
			});
		});
			$("#contenido").draggable();
		  positionMiddleScreen('contenido');
	</script>