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


<script>
	function accept() {
		setValues();
		//acceptRegistry();
	}
</script>


<!-- ESTABLECER AL ACEPTAR LOS VALORES DEL REGISTRO ENCONTRADO -->
<logic:present name="Value">
	<ispac:updatefields name="Value" parameters='SEARCH_SPAC_REGISTROS_ES_NREG' function="true"/>
</logic:present>


</head>

<body>

	<div id="contenido" class="move">

		<div class="ficha"> 
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="registro.busqueda.form.title"/></h4>
					<div class="acciones_ficha">
							<a href="#" id="btnOk" onclick="javascript:ispac_needToConfirm=false;accept();" class="btnOk"><bean:message key="common.message.ok"/></a>
							<a href="#" id="btnClose" class="btnClose"><bean:message key="common.message.close"/></a>
						
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">

					<div class="seccion_ficha">
					
						<div class="cabecera_seccion">
							<h4><bean:message key="registro.busqueda.detalles"/></h4>
						</div>
					
						<!-- NÚMERO DE REGISTRO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.numero"/></nobr></label>
							<bean:write name="NUMERO_REGISTRO"/>
						</p>
								
						<!-- FECHA DE REGISTRO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.fecha"/></nobr></label>
							<bean:write name="FECHA_REGISTRO"/>
						</p>
								
						<!-- USUARIO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.usuario"/></nobr></label>
							<logic:present name="USUARIO">
								<bean:write name="USUARIO"/>
							</logic:present>
						</p>
						
						<p class="fila"/>
					
						<!-- DESTINATARIOS -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.destinatarios"/></nobr></label>
							<logic:present name="DESTINATARIOS">
								<bean:write name="DESTINATARIOS"/>
							</logic:present>
						</p>

						<!-- DESTINATARIOS -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.remitentes"/></nobr></label>
							<logic:present name="REMITENTES">
								<bean:write name="REMITENTES"/>
							</logic:present>
						</p>
						
						<p class="fila"/>
								
						<!-- OFICINA REGISTRO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.oficina.registro"/></nobr></label>
							<logic:present name="OFICINA_REGISTRO">
								<bean:write name="OFICINA_REGISTRO"/>
							</logic:present>
						</p>
								
						<!-- FECHA DE CREACIÓN -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.fecha"/></nobr></label>
							<logic:present name="FECHA_CREACION">
								<bean:write name="FECHA_CREACION"/>
							</logic:present>
						</p>
						
						<p class="fila"/>				
								
						<!-- ORGANISMO ORIGEN -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.origen"/></nobr></label>
							<logic:present name="ORGANISMO_ORIGEN">
								<bean:write name="ORGANISMO_ORIGEN"/>
							</logic:present>
						</p>
					
						<!-- ORGANISMO DESTINO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.destino"/></nobr></label>
							<logic:present name="ORGANISMO_DESTINO">
								<bean:write name="ORGANISMO_DESTINO"/>
							</logic:present>
						</p>
						
						<p class="fila"/>
					
						<!-- ASUNTO -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.tipo.asunto"/></nobr></label>
							<logic:present name="ASUNTO">
								<bean:write name="ASUNTO"/>
							</logic:present>
						</p>				
								
						<!-- RESUMEN -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.resumen"/></nobr></label>
							<logic:present name="RESUMEN">
								<bean:write name="RESUMEN"/>
							</logic:present>
						</p>

						<!-- DOCUMENTOS -->
						<p class="fila_sub clearfix">
							<label class="mid"><nobr><bean:message key="registro.numDocumentos"/></nobr></label>
							<logic:present name="DOCUMENTOS">
								<bean:write name="DOCUMENTOS"/>
							</logic:present>
						</p>

					</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->
</body>
</html>


	<script type="text/javascript">

		$(document).ready(function() {

			
			$("#btnOk").click(function() {
				parent.ispac_needToConfirm=false;
				<ispac:hideframe save='true'/>
			});
			
			$("#btnClose").click(function() {
				<ispac:hideframe/>
			});
		});
			$("#contenido").draggable();
		  positionMiddleScreen('contenido');
	</script>