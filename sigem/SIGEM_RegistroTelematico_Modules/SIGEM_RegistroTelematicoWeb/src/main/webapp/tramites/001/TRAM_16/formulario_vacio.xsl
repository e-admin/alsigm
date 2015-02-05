<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Contratación administrativa mediante el procedimiento negociado'"/>
	<xsl:variable name="lang.datosContratante" select="'Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.relacion" select="'Relación'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'Tipo de Contrato'"/>
	
	<xsl:variable name="lang.TColaboracion" select="'Colaboración entre el sector público y el sector privado'" />
	<xsl:variable name="lang.TConcesion" select="'Concesión de obras públicas'" />
	<xsl:variable name="lang.TGestion" select="'Gestión de servicios públicos'" />
	<xsl:variable name="lang.TObras" select="'Obras'" />
	<xsl:variable name="lang.TServicio" select="'Servicio'" />
	<xsl:variable name="lang.TSuministros" select="'Suministros'" />
	
	<xsl:variable name="lang.formaAdjudicacion" select="'Forma de Adjudicación'"/>
	<xsl:variable name="lang.TConcurso" select="'Concurso'"/>
	<xsl:variable name="lang.TSubasta" select="'Subasta'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.unidades" select="'Unidades Plazo'"/>
	<xsl:variable name="lang.TDiasNaturales" select="'Días naturales'"/>
	<xsl:variable name="lang.TDiasLaborales" select="'Días Laborales'"/>
	<xsl:variable name="lang.TMeses" select="'Meses'"/>
	<xsl:variable name="lang.TAnos" select="'Años'"/>
	
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar al contrato'"/>
	
	<xsl:variable name="lang.escritura" select="'Escritura o documento de constitución'"/>
	<xsl:variable name="lang.obrar" select="'Acreditación de la capacidad de obrar'"/>
	<xsl:variable name="lang.estatutos" select="'Estatutos'"/>
	<xsl:variable name="lang.solvencia" select="'Acreditación de solvencia'"/>
	<xsl:variable name="lang.noConcurrencia" select="'Prueba de la no concurrencia de una prohibición de contratar'"/>
	<xsl:variable name="lang.seguro" select="'Declaraciones apropiadas de entidades financieras o, en su caso, justificante de la existencia de un seguro de indemnización por riesgos profesionales'"/>
	<xsl:variable name="lang.cuentas" select="'Las cuentas anuales presentadas en el Registro Mercantil o en el Registro oficial que corresponda'"/>
	<xsl:variable name="lang.volumen" select="'Declaración sobre el volumen global de negocios'"/>
	<xsl:variable name="lang.5anos" select="'Relación de las obras ejecutadas en el curso de los cinco últimos años'"/>
	<xsl:variable name="lang.unidadesTecnicas" select="'Declaración indicando los técnicos o las unidades técnicas de los que ésta disponga para la ejecución de las obras'"/>
	<xsl:variable name="lang.titulos" select="'Títulos académicos y profesionales del emepresario y de los directivos de la empresa y, en particular, del responsable o responsables de las obras'"/>
	<xsl:variable name="lang.medidas" select="'Indicación de las medidas de gestión medioambientales'"/>
	<xsl:variable name="lang.plantilla" select="'Declaración sobre la plantilla media anual de la empresa y la importancia de su personal directivo durante los tres últimos años, acompañada de la documentación justificada correspondiente'"/>
	<xsl:variable name="lang.material" select="'Declaración indicando la maquinaria, material y equipo técnico del que se dispondrá para la ejecución de las obras'"/>
	
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>	
	<xsl:variable name="lang.importante" select="'IMPORTANTE'"/>	
	<xsl:variable name="lang.nota" select="' Sólo podrán solicitar el contrato con el sector público aquellas personas naturales o jurídicas, españolas o extranjeras, que tengan plena capacidad de obrar, no estén incursas en una prohibición de contratar, y acrediten su solvencia económica, financiera y técnica o profesional o, en los casos en que así lo exija esta Ley, se encuentren debidamente clasificadas.'"/>	
	<xsl:variable name="lang.unidades" select="'Unidades de Plazo'"/>
	
	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>
	
	<xsl:variable name="lang.TResponsable" select="'Responsable del contrato'"/>
	<xsl:variable name="lang.TContratante" select="'Contratante'"/>
	<xsl:variable name="lang.TLicitador" select="'Licitador'"/>
	<xsl:variable name="lang.TAdjudicatario" select="'Adjudicatario'"/>
	
	<xsl:template match="/">
	
		<link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua"></link>
	
		<script type="text/javascript" src="calendario/calendar.js"></script>
		<script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
		<script type="text/javascript" src="calendario/calendario.js"></script>
	
		<script language="Javascript">

			var unidadesEjecucionRequired = false;
		
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array();
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('relacion','<xsl:value-of select="$lang.relacion"/>');
			validar[3] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[4] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[5] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[6] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[7] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[8] = new Array('documentoIdentidadContratado', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[9] = new Array('nombreContratado','<xsl:value-of select="$lang.nombre"/>');
			validar[10] = new Array('relacionContratado','<xsl:value-of select="$lang.relacion"/>');
			validar[11] = new Array('domicilioNotificacionContratado','<xsl:value-of select="$lang.domicilio"/>');
			validar[12] = new Array('localidadContratado','<xsl:value-of select="$lang.localidad"/>');
			validar[13] = new Array('provinciaContratado','<xsl:value-of select="$lang.provincia"/>');
			validar[14] = new Array('codigoPostalContratado','<xsl:value-of select="$lang.cp"/>');
			
	
			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array();
			
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[5] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[6] = new Array('telefono','Telefono');
			especificos[7] = new Array('emailSolicitante','Email_Solicitante');
						
			especificos[8] = new Array('nombreContratado','Nombre_Contratado');
			especificos[9] = new Array('documentoIdentidadContratado','Documento_Identidad_Contratado');
			especificos[10] = new Array('domicilioNotificacionContratado','Domicilio_Notificacion_Contratado');
			especificos[11] = new Array('localidadContratado','Localidad_Contratado');
			especificos[12] = new Array('provinciaContratado','Provincia_Contratado');
			especificos[13] = new Array('codigoPostalContratado','Codigo_Postal_Contratado');
			especificos[14] = new Array('telefonoContratado','Telefono_Contratado');			
			especificos[15] = new Array('emailContratado','Email_Contratado');
								
			especificos[16] = new Array('tipoContrato','Tipo_Contrato');
			especificos[17] = new Array('formaAdjudicacion','Forma_Adjudicacion');
			especificos[18] = new Array('presupuestoMaximo','Presupuesto_Maximo');
			especificos[19] = new Array('precioAdjudicacion','Precio_Adjudicacion');
			especificos[20] = new Array('plazoEjecucion','Plazo_Ejecucion');
			especificos[21] = new Array('relacionDesc','Relacion_Descripcion');
			especificos[22] = new Array('relacionContratadoDesc','Relacion_Contratado_Descripcion');
			especificos[23] = new Array('unidadesPlazoDesc','Unidades_Plazo_Descripcion');
			especificos[24] = new Array('unidadesPlazo','Unidades_Plazo');
			
			especificos[25] = new Array('relacion','Relacion');
			especificos[26] = new Array('relacionContratado','Relacion_Contratado');
			
			/*
			especificos[20] = new Array('aplicacionPresupuestaria','Aplicacion_Presupuestaria');
			especificos[25] = new Array('fechaFinEjecucion','Fecha_Final_Ejecucion');
			especificos[26] = new Array('fechaFinGarantia','Fecha_Final_Garantia');
			especificos[24] = new Array('clasificacion','Clasificacion');	
			especificos[22] = new Array('garantiaProvisional','Garantia_Provisional');
			especificos[23] = new Array('garantiaDefinitiva','Garantia_Definitiva');
			*/
			
			var validarNumero = new Array();
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			validarNumero[1] = new Array('codigoPostalContratado','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
				
				if(!validarCampoNumerico(document.getElementById("telefono"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
				}
				
				var telefonoContratado = document.getElementById("telefonoContratado")
				if (telefonoContratado.value != "") {
					if(!validarCampoNumerico(telefonoContratado, '<xsl:value-of select="$lang.telefono"/>')) {
						return false;
					}
				}
				
				var precioAdjudicacion = document.getElementById("precioAdjudicacion")
				if (precioAdjudicacion.value != "") {
					if(!validarCampoNumerico(precioAdjudicacion, '<xsl:value-of select="$lang.precioAdjudicacion"/>')) {
						return false;
					}
				}
				
				var presupuestoMaximo = document.getElementById("presupuestoMaximo")
				if (presupuestoMaximo.value != "") {
					if(!validarCampoNumerico(presupuestoMaximo, '<xsl:value-of select="$lang.presupuestoMax"/>')) {
						return false;
					}
				}
				/*
				if(!validarCampoNumerico(document.getElementById("garantiaProvisional"), '<xsl:value-of select="$lang.garantiaProv"/>')) {
					return false;
				}
				
				if(!validarCampoNumerico(document.getElementById("garantiaDefinitiva"), '<xsl:value-of select="$lang.garantiaDef"/>')) {
					return false;
				}
				*/
				var emailSol = document.getElementById("emailSolicitante");
				if (validarEmail(emailSol) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					emailSol.focus();
					return false;
				}
				
				var emailCont = document.getElementById("emailContratado");
				if (validarEmail(emailCont) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					emailCont.focus();
					return false;
				}
				/*
				if(!validarFormatoFecha(document.getElementById("fechaFinEjecucion"), '<xsl:value-of select="$lang.fechaFinEjecucion"/>')) {
					return false;
				}
				
				if(!validarFormatoFecha(document.getElementById("fechaFinGarantia"), '<xsl:value-of select="$lang.fechaFinGarantia"/>')) {
					return false;
				}*/
				
				var nifCont = document.getElementById("documentoIdentidadContratado");
				
				if ( !validarNIF(nifCont) ) {
					alert("El campo '<xsl:value-of select="$lang.docIdentidad"/>' no es válido");
					nifCont.focus();
					return false;
				}
				
				if(unidadesEjecucionRequired) {
					
					campo = document.getElementById("unidadesPlazo");
					valor = campo.value;
					if (valor == ""){
						alert("El campo '<xsl:value-of select="$lang.unidades"/>' es obligatorio");
						campo.focus();
						return false;
					}
				}

				campo = document.getElementById("relacion");
				if( document.forms[0].relacion.value == document.forms[0].relacionContratado.value) {
					alert("La Relación del Contratante no puede ser la misma que la del Contratado");
					campo.focus();
					return false;
				}
				document.forms[0].relacionDesc.value = document.forms[0].relacion.options[document.forms[0].relacion.selectedIndex].text;
				document.forms[0].relacionContratadoDesc.value = document.forms[0].relacionContratado.options[document.forms[0].relacionContratado.selectedIndex].text;
				document.forms[0].unidadesPlazoDesc.value = document.forms[0].unidadesPlazo.options[document.forms[0].unidadesPlazo.selectedIndex].text;
				return true;
			}
			
			function unidadesRequired() {
			
				var colorRequired = '<font color="#950000">*</font>';
				
				if(document.forms[0].plazoEjecucion.value != "") {
					document.getElementById("unidades").innerHTML = "<xsl:value-of select="$lang.unidades"/>" + ":" + colorRequired;
					unidadesEjecucionRequired = true;
				} else {
					document.getElementById("unidades").innerHTML = '<xsl:value-of select="$lang.unidades"/>' + ":";
					unidadesEjecucionRequired = false;
				}
			}			
		</script>
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosContratante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Documento_Identificacion/Numero"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="id">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Nombre"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.relacion"/>:<font color="#950000">*</font>
				</label>
				
				<xsl:variable name="clas" select="Datos_Registro/datos_especificos/Relacion"/>
				
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height: 20px</xsl:attribute>
					<xsl:attribute name="name">relacion</xsl:attribute>
					<xsl:attribute name="id">relacion</xsl:attribute>
					<option></option>
					<xsl:choose>
						<xsl:when test="$clas='INT'">
							<option value="INT" selected="selected"><xsl:value-of select="$lang.TResponsable"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="INT"><xsl:value-of select="$lang.TResponsable"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas='COMP'">
							<option value="COMP" selected="selected"><xsl:value-of select="$lang.TContratante"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="COMP"><xsl:value-of select="$lang.TContratante"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas='LIC'">
							<option value="LIC" selected="selected"><xsl:value-of select="$lang.TLicitador"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="LIC"><xsl:value-of select="$lang.TLicitador"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas='BENF'">
							<option value="BENF" selected="selected"><xsl:value-of select="$lang.TAdjudicatario"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="BENF"><xsl:value-of select="$lang.TAdjudicatario"/></option>
						</xsl:otherwise>
					</xsl:choose>
				</select>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:<font color="#950000">*</font>	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="maxlength">128</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:<font color="#950000">*</font>	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">localidad</xsl:attribute>
					<xsl:attribute name="id">localidad</xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">provincia</xsl:attribute>
					<xsl:attribute name="id">provincia</xsl:attribute>
					<xsl:attribute name="maxlength">64</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:<font color="#950000">*</font>
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostal</xsl:attribute>
					<xsl:attribute name="id">codigoPostal</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal"/></xsl:attribute>
					<xsl:attribute name="maxlength">5</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.telefono"/>:<font color="#950000">*</font>	
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">telefono</xsl:attribute>
					<xsl:attribute name="id">telefono</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono"/></xsl:attribute>
					<xsl:attribute name="maxlength">32</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">emailSolicitante</xsl:attribute>
					<xsl:attribute name="id">emailSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Correo_Electronico" /></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosContratado"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidadContratado</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidadContratado</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Documento_Identidad_Contratado"/></xsl:attribute>
					<xsl:attribute name="maxlength">12</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">nombreContratado</xsl:attribute>
					<xsl:attribute name="id">nombreContratado</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nombre_Contratado"/></xsl:attribute>
					<xsl:attribute name="maxlength">150</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.relacion"/>:<font color="#950000">*</font>
				</label>
				
				<xsl:variable name="clas2" select="Datos_Registro/datos_especificos/Relacion_Contratado"/>	
								
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height: 20px</xsl:attribute>
					<xsl:attribute name="name">relacionContratado</xsl:attribute>
					<xsl:attribute name="id">relacionContratado</xsl:attribute>
					<option></option>
					<xsl:choose>
						<xsl:when test="$clas2='INT'">
							<option value="INT" selected="selected"><xsl:value-of select="$lang.TResponsable"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="INT"><xsl:value-of select="$lang.TResponsable"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas2='COMP'">
							<option value="COMP" selected="selected"><xsl:value-of select="$lang.TContratante"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="COMP"><xsl:value-of select="$lang.TContratante"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas2='LIC'">
							<option value="LIC" selected="selected"><xsl:value-of select="$lang.TLicitador"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="LIC"><xsl:value-of select="$lang.TLicitador"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas2='BENF'">
							<option value="BENF" selected="selected"><xsl:value-of select="$lang.TAdjudicatario"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="BENF"><xsl:value-of select="$lang.TAdjudicatario"/></option>
						</xsl:otherwise>
					</xsl:choose>
				</select>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">domicilioNotificacionContratado</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacionContratado</xsl:attribute>
					<xsl:attribute name="maxlength">128</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion_Contratado"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">localidadContratado</xsl:attribute>
					<xsl:attribute name="id">localidadContratado</xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad_Contratado"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">provinciaContratado</xsl:attribute>
					<xsl:attribute name="id">provinciaContratado</xsl:attribute>
					<xsl:attribute name="maxlength">64</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia_Contratado"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:<font color="#950000">*</font>
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostalContratado</xsl:attribute>
					<xsl:attribute name="id">codigoPostalContratado</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal_Contratado"/></xsl:attribute>
					<xsl:attribute name="maxlength">5</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">telefonoContratado</xsl:attribute>
					<xsl:attribute name="id">telefonoContratado</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono_Contratado"/></xsl:attribute>
					<xsl:attribute name="maxlength">32</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">emailContratado</xsl:attribute>
					<xsl:attribute name="id">emailContratado</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Contratado"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosContrato"/></h1>
   		</div>
   		<div class="cuadro" style="">
 			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.tipoContrato"/>:
				</label>
				
				<xsl:variable name="clas3" select="Datos_Registro/datos_especificos/Tipo_Contrato"/>
				
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">tipoContrato</xsl:attribute>
					<xsl:attribute name="id">tipoContrato</xsl:attribute>
					<option value=""></option>
					<xsl:choose>
						<xsl:when test="$clas3='Colaboración entre el sector público y el sector privado'">
							<option value="Colaboración entre el sector público y el sector privado" selected='selected'><xsl:value-of select="$lang.TColaboracion"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Colaboración entre el sector público y el sector privado"><xsl:value-of select="$lang.TColaboracion"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas3='Concesión de obras públicas'">
							<option value="Concesión de obras públicas" selected='selected'><xsl:value-of select="$lang.TConcesion"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Concesión de obras públicas"><xsl:value-of select="$lang.TConcesion"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas3='Gestión de servicios públicos'">
							<option value="Gestión de servicios públicos" selected='selected'><xsl:value-of select="$lang.TGestion"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Gestión de servicios públicos"><xsl:value-of select="$lang.TGestion"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas3='Obras'">
							<option value="Obras" selected='selected'><xsl:value-of select="$lang.TObras"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Obras"><xsl:value-of select="$lang.TObras"/></option>
						</xsl:otherwise>		
					</xsl:choose>
					
					<xsl:choose>						
						<xsl:when test="$clas3='Servicio'">
							<option value="Servicio" selected='selected'><xsl:value-of select="$lang.TServicio"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Servicio"><xsl:value-of select="$lang.TServicio"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas3='Suministros'">
							<option value="Suministros" selected='selected'><xsl:value-of select="$lang.TSuministros"/></option>
						</xsl:when>		
						<xsl:otherwise>
							<option value="Suministros"><xsl:value-of select="$lang.TSuministros"/></option>
						</xsl:otherwise>								
					</xsl:choose>
				</select>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.formaAdjudicacion"/>:
				</label>
				
				<xsl:variable name="clas4" select="Datos_Registro/datos_especificos/Forma_Adjudicacion"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">formaAdjudicacion</xsl:attribute>
					<xsl:attribute name="id">formaAdjudicacion</xsl:attribute>
					<option value=""></option>
					<xsl:choose>
						<xsl:when test="$clas4='Concurso'">
							<option value="Concurso" selected='selected'><xsl:value-of select="$lang.TConcurso"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="Concurso"><xsl:value-of select="$lang.TConcurso"/></option>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:choose>
						<xsl:when test="$clas4='Subasta'">
							<option value="Subasta" selected='selected'><xsl:value-of select="$lang.TSubasta"/></option>
						</xsl:when>
						<xsl:otherwise>
							<option value="Subasta"><xsl:value-of select="$lang.TSubasta"/></option>
						</xsl:otherwise>					
					</xsl:choose>
				</select>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.presupuestoMax"/>:
				</label>
				<input type="text" onkeypress="return permitirSoloImportes(event)">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">presupuestoMaximo</xsl:attribute>
					<xsl:attribute name="id">presupuestoMaximo</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Presupuesto_Maximo"/></xsl:attribute>
					<xsl:attribute name="maxlength">20</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.precioAdjudicacion"/>:
				</label>
				<input type="text" onkeypress="return permitirSoloImportes(event)">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">precioAdjudicacion</xsl:attribute>
					<xsl:attribute name="id">precioAdjudicacion</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Precio_Adjudicacion"/></xsl:attribute>
					<xsl:attribute name="maxlength">20</xsl:attribute>
				</input>
			</div>
			<!-- 
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.aplicacion"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">aplicacionPresupuestaria</xsl:attribute>
					<xsl:attribute name="id">aplicacionPresupuestaria</xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>
			 -->
			<div class="col">
				<table cellpadding="0" cellspacing="0" border="0" style="font-size:100%">
					<tr>
						<td>
							<label class="gr">
								<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
								<xsl:value-of select="$lang.plazo"/>:
							</label>			
						</td>
						<td>
							<input type="text" onblur="unidadesRequired()" onkeypress="return permitirSoloNumericos(event)">
								<xsl:attribute name="style">position: relative; width:120px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
								<xsl:attribute name="name">plazoEjecucion</xsl:attribute>
								<xsl:attribute name="id">plazoEjecucion</xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Plazo_Ejecucion"/></xsl:attribute>
								<xsl:attribute name="maxlength">10</xsl:attribute>
							</input>
						</td>
						<td width="20"></td>
						<td>
							<label class="gr" id="unidades">
								<xsl:attribute name="style">position: relative; width:140px;</xsl:attribute>
								<xsl:value-of select="$lang.unidades"/>:
							</label>
						</td>
						<td>
						
							<xsl:variable name="clas4" select="Datos_Registro/datos_especificos/Unidades_Plazo"/>
							<select class="gr">
								<xsl:attribute name="style">position: relative; width:120px; height:20px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
								<xsl:attribute name="name">unidadesPlazo</xsl:attribute>
								<xsl:attribute name="id">unidadesPlazo</xsl:attribute>
								<option value=""></option>
								<xsl:choose>
									<xsl:when test="$clas4='1'">
										<option value="1" selected="selected"><xsl:value-of select="$lang.TDiasNaturales"/></option>		
									</xsl:when>
									<xsl:otherwise>
										<option value="1"><xsl:value-of select="$lang.TDiasNaturales"/></option>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:choose>	
									<xsl:when test="$clas4='2'">
										<option value="2" selected="selected"><xsl:value-of select="$lang.TDiasLaborales"/></option>	
									</xsl:when>
									<xsl:otherwise>
										<option value="2"><xsl:value-of select="$lang.TDiasLaborales"/></option>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:choose>									
									<xsl:when test="$clas4='3'">
										<option value="3" selected="selected"><xsl:value-of select="$lang.TMeses"/></option>		
									</xsl:when>
									<xsl:otherwise>
										<option value="3"><xsl:value-of select="$lang.TMeses"/></option>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:choose>									
									<xsl:when test="$clas4='4'">
										<option value="4" selected="selected"><xsl:value-of select="$lang.TAnos"/></option>	
									</xsl:when>
									<xsl:otherwise>
										<option value="4"><xsl:value-of select="$lang.TAnos"/></option>
									</xsl:otherwise>
								</xsl:choose>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<!-- 
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.garantiaProv"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">garantiaProvisional</xsl:attribute>
					<xsl:attribute name="id">garantiaProvisional</xsl:attribute>
					<xsl:attribute name="maxlength">20</xsl:attribute>
				</input>
			</div>
			 
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.garantiaDef"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">garantiaDefinitiva</xsl:attribute>
					<xsl:attribute name="id">garantiaDefinitiva</xsl:attribute>
					<xsl:attribute name="maxlength">20</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.clasificacion"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">clasificacion</xsl:attribute>
					<xsl:attribute name="id">clasificacion</xsl:attribute>
					<xsl:attribute name="maxlength">500</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td valign="top">
							<label class="gr">
								<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
								<xsl:value-of select="$lang.fechaFinEjecucion"/>:
							</label>
						</td>
						<td>
							<input type="text">
								<xsl:attribute name="style">position: relative; width:60px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
								<xsl:attribute name="name">fechaFinEjecucion</xsl:attribute>
								<xsl:attribute name="id">fechaFinEjecucion</xsl:attribute>
								<xsl:attribute name="maxlength">10</xsl:attribute>
							</input>
							<img src="img/calendario.bmp" onclick="return showCalendarEsp('470','fechaFinEjecucion','%d-%m-%Y');" />
							<p name="Desde" id="f"></p>
						</td>
						<td valign="top" align="right">
							<label class="gr">
								<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
								<xsl:value-of select="$lang.fechaFinGarantia"/>:
							</label>
						</td>
						<td width="5"></td>
						<td>
							<input type="text">
								<xsl:attribute name="style">position: relative; width:60px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
								<xsl:attribute name="name">fechaFinGarantia</xsl:attribute>
								<xsl:attribute name="id">fechaFinGarantia</xsl:attribute>
								<xsl:attribute name="maxlength">10</xsl:attribute>
							</input>
							<img src="img/calendario.bmp" onclick="return showCalendarEsp('470','fechaFinGarantia','%d-%m-%Y');" />
							<p name="Desde" id="f"></p>
						</td>
					</tr>
				</table>
			</div>
			-->
   		</div>
   		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;height:35px</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
			</label>

   			<div>   				
	   			<ul>
		   			<label class="gr">
						<xsl:attribute name="style">position: relative; width:550px;</xsl:attribute>
						<li><b><xsl:value-of select="$lang.obrar"/>:<br/><br/></b></li>
					</label>
				
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:550px;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.escritura"/>:</li></ul>
					</label>
					<br/><br/>
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D1</xsl:attribute>
							<xsl:attribute name="id">TRAM16D1</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D1_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D1_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:250px;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.estatutos"/>:</li></ul>
					</label>
					<br/><br/>
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D2</xsl:attribute>
							<xsl:attribute name="id">TRAM16D2</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D2_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D2_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
				</ul>
			</div>
			
			<div> 
   				<ul>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:550px;</xsl:attribute>
						<li><b><xsl:value-of select="$lang.noConcurrencia"/>:<br/></b></li><br/>
					</label>

   					<div class="col">
	   					<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
	   					<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D3</xsl:attribute>
							<xsl:attribute name="id">TRAM16D3</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D3_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D3_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
					
				</ul>					
   			</div>
   			
   			<br/>
   			
   			<div class="col">
   				<ul>
		   			<label class="style">
						<xsl:attribute name="style">position: relative; width:550px; color: #000000;</xsl:attribute>
						<li><b><xsl:value-of select="$lang.solvencia"/>:<br/><br/></b></li>
					</label>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.seguro"/>:</li></ul>
						<br/>
					</label>
				
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D4</xsl:attribute>
							<xsl:attribute name="id">TRAM16D4</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D4_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D4_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
				 	</div>

					<br/>
					
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.cuentas"/>:</li></ul>
						<br/>
					</label>
					
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D5</xsl:attribute>
							<xsl:attribute name="id">TRAM16D5</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D5_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D5_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.volumen"/>:</li></ul>
						<br/>
					</label>
					
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D6</xsl:attribute>
							<xsl:attribute name="id">TRAM16D6</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D6_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D6_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
					
					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.5anos"/>:</li></ul>
						<br/>
					</label>
					
							
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D7</xsl:attribute>
							<xsl:attribute name="id">TRAM16D7</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D7_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D7_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>

					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.unidadesTecnicas"/>:</li></ul>
						<br/>
					</label>

					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D8</xsl:attribute>
							<xsl:attribute name="id">TRAM16D8</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D8_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D8_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>

					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.titulos"/>:</li></ul>
						<br/>
					</label>
					
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D9</xsl:attribute>
							<xsl:attribute name="id">TRAM16D9</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D9_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D9_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>
					<br/>
					
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.medidas"/>:</li></ul>
						<br/>
					</label>
					
					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D10</xsl:attribute>
							<xsl:attribute name="id">TRAM16D10</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D10_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D10_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>

					<br/><br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.plantilla"/>:</li></ul>
						<br/>
					</label>

					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D11</xsl:attribute>
							<xsl:attribute name="id">TRAM16D11</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D11_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D11_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>

					<br/>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px; color: #000000;</xsl:attribute>
						<ul><li><xsl:value-of select="$lang.material"/>:</li></ul>
						<br/>
					</label>

					<div class="col">
						<label class="gr">
							<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
							<b><xsl:value-of select="$lang.documentoPDF"/></b>
						</label>
						<input type="file">
							<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
							<xsl:attribute name="name">TRAM16D12</xsl:attribute>
							<xsl:attribute name="id">TRAM16D12</xsl:attribute>
							<xsl:attribute name="value"></xsl:attribute>
						</input>
						<input type="hidden">
							<xsl:attribute name="name">TRAM16D12_Tipo</xsl:attribute>
							<xsl:attribute name="id">TRAM16D12_Tipo</xsl:attribute>
							<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
						</input>
					</div>

   				</ul>
   				<div style="height:65px">
   					<label class="gr">
						<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
						<b><xsl:value-of select="$lang.importante"/>:</b> <xsl:value-of select="$lang.nota"/>
					</label>
				</div>
			</div>
   		</div>
   		<br/>
   		<input type="hidden">
			<xsl:attribute name="name">datosEspecificos</xsl:attribute>
			<xsl:attribute name="id">datosEspecificos</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.envio"/></h1>
   		</div>
   		<div class="cuadro">
   			<xsl:variable name="deu" select="Datos_Registro/datos_especificos/Direccion_Electronica_Unica"/>
   			<input class="gr" type="checkbox" id="solicitarEnvio" onclick="activarDEU();" style="border:0px; width:20px;" >
   				<xsl:choose>
			       <xsl:when test="$deu!=''">
			           <xsl:attribute name="checked">true</xsl:attribute>
			       </xsl:when>
			    </xsl:choose>
   			</input>
   		
   			<label for="solicitarEnvio" onclick="activarDEU();">  <xsl:value-of select="$lang.solicitoEnvio"/></label>
   			<br/><br/>
	   		<div class="col" style="visibility: hidden" id="DEU" name="DEU">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:<font color="#950000">*</font>
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="id">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Direccion_Electronica_Unica"/></xsl:attribute>
				</input>
			</div>
			<xsl:choose>
			   <xsl:when test="$deu!=''">
			       <script language="Javascript">activarDEU();</script>
			   </xsl:when>
		    </xsl:choose>
		</div>
		<div class="col" style="visibility: hidden">
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">relacionDesc</xsl:attribute>
					<xsl:attribute name="id">relacionDesc</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">relacionContratadoDesc</xsl:attribute>
					<xsl:attribute name="id">relacionContratadoDesc</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">unidadesPlazoDesc</xsl:attribute>
					<xsl:attribute name="id">unidadesPlazoDesc</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
		<div>
			<table cellpadding="0" cellspacing="0" border="0" width="95%">
				<tr><td height="5" colspan="2"></td></tr>
				<tr>
					<td align="right">
			   			<font style="color:#950000; font-size:8pt">(*)</font><font style="color:#006699; font-size:8pt"> <xsl:value-of select="$lang.required"/></font>
					</td>
					<td width="10"></td>
				</tr>
			</table>
   		</div>
	</xsl:template>
</xsl:stylesheet>