<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Página de Registro de Entrada</title>
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
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	

<%
	// Nombre de la variable de sesion donde se han salvado
	// los parametros que utiliza el tag actionframe
	String parameters = request.getParameter("parameters");
%>
</head>

<body>
<div id="move">
<!-- ESTABLECER AL ACEPTAR LOS VALORES DEL REGISTRO ENCONTRADO -->
<logic:present name="Value">
	<ispac:updatefields name="Value" parameters='<%=parameters %>' function="true"/>
</logic:present>

<div class="ficha"> 
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
						<a href="#" id="btnOkShowRegister"  onclick='setValues();<ispac:hideframe/>' class="btnOk"><bean:message key="common.message.ok"/></a>			
						<a href="#" id="btnCancelShowRegister"  onclick='<ispac:hideframe/>' class="btnCancel"><bean:message key="common.message.cancel"/></a>				
					</div>
					
				</div>
			</div>
			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				
					<div class="cabecera_seccion">
						<h4><bean:message key="registro.entrada.datos"/></h4>
					</div>

<p>
						<!-- REGISTRO DE SALIDA NO ENCONTRADO -->
						<logic:equal name="REG_FOUND" value="false" scope="request">
							<div>
								<bean:message key="registro.entrada.notfound"/>
								<bean:write name="NUMERO_REGISTRO"/>
							</div>			
						</logic:equal>
						
						<!-- REGISTRO DE SALIDA ENCONTRADO -->
						<logic:equal name="REG_FOUND" value="true" scope="request">
						
							<!-- NUMERO DE REGISTRO -->
							<div>	
								
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.numero"/></nobr></label>
								<label><bean:write name="NUMERO_REGISTRO"/></label>
							
							</div>
							<!-- FECHA CREACION -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.fecha.trabajo"/></nobr></label>	
								<label><bean:write name="FECHA_CREACION"/></label>
							</div>
							<!-- USUARIO -->
							<div>
								
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.usuario"/></nobr></label>
								<logic:present name="USUARIO">
									<label><bean:write name="USUARIO"/></label>
								</logic:present>
							</div>		
						
							<!-- REMITENTE -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.remitente"/></nobr></label>
									<logic:present name="REMITENTE">
										<label><bean:write name="REMITENTE"/></label>
									</logic:present>
							</div>
							<!-- OFICINA REGISTRO -->
							<div>	
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.oficina.registro"/></nobr></label>
									<logic:present name="OFICINA_REGISTRO">
										<label><bean:write name="OFICINA_REGISTRO"/></label>
									</logic:present>
							</div>
							<!-- FECHA DE REGISTRO -->
							
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.fecha"/></nobr></label>
									<logic:present name="FECHA_REGISTRO">
										<label><bean:write name="FECHA_REGISTRO"/></label>
									</logic:present>
							</div>		
							
							<!-- ORGANISMO ORIGEN -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.origen"/></nobr></label>
									<logic:present name="ORGANISMO_ORIGEN">
										<label><bean:write name="ORGANISMO_ORIGEN"/></label>
									</logic:present>
							</div>		
							<!-- ORGANISMO DESTINO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.destino"/></nobr></label>
									<logic:present name="ORGANISMO_DESTINO">
									<label>	<bean:write name="ORGANISMO_DESTINO"/></label>
									</logic:present>
							</div>	
							<!-- ASUNTO -->
							<div>
								<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.tipo.asunto"/></nobr></label>
									<logic:present name="ASUNTO">
										<label><bean:write name="ASUNTO"/></label>
									</logic:present>
							</div>	
							<!-- RESUMEN -->
							<div>
							<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.resumen"/></nobr></label>
								<logic:present name="RESUMEN">
									<label><bean:write name="RESUMEN"/></label>
								</logic:present>
							</div>	


							<!-- DOCUMENTOS -->
							<div>
							<label class="popUpInfo" ><nobr><bean:message key="registro.entrada.numDocumentos"/></nobr></label>
								<logic:present name="DOCUMENTOS">
									<bean:write name="DOCUMENTOS"/>
								</logic:present>
							</div>	

						</logic:equal>
	
						</p>

				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

</div>
</body>

</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>


							