<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de solicitud de Cambio de Titularidad'"/>
	<xsl:variable name="lang.datosInteresado" select="'Datos del Interesado Principal'"/>
	<xsl:variable name="lang.datosParticipante" select="'Datos del Participante'"/>
	<xsl:variable name="lang.nifCif" select="'NIF/CIF'"/>
	<xsl:variable name="lang.identidad" select="'Nombre'"/>
	<xsl:variable name="lang.TComprador" select="'Comprador'"/>
	<xsl:variable name="lang.relacion" select="'Relación'"/>
	<xsl:variable name="lang.TVendedor" select="'Vendedor'"/>
	<xsl:variable name="lang.TTitular" select="'Titular'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.denominacion" select="'Denominación'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'Actividad'"/>
	<xsl:variable name="lang.emplazamiento" select="'Emplazamiento'"/>
	<xsl:variable name="lang.fechaLicencia" select="'Fecha Licencia'"/>
	<xsl:variable name="lang.fechaAprobacion" select="'Fecha Aprobación'"/>
	<xsl:variable name="lang.fechaFormato" select="'Formato válido [DD/MM/AAAA]'" /> 
	<xsl:variable name="lang.tasas" select="'Tasas'" /> 
	<xsl:variable name="lang.euros" select="'&#8364;'" /> 
	<xsl:variable name="lang.organoSujeto_1" select="'Órgano/s o unidad/es municipal/es responsable/s del procedimiento'"/>
	<xsl:variable name="lang.organoResponsable" select="'Órgano Responsable'"/>
	<xsl:variable name="lang.gerenciasSubgerenciasCatastro" select="'Las Gerencias y Subgerencias del Catastro'"/>
	<xsl:variable name="lang.areaUrbanismo" select="'Área de Urbanismo'"/>	
	
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.instanciaCambioTitular" select="'Instancia/solictud de cambio de titular, firmada por ambos (actual titular y adquiriente)'"/>
	<xsl:variable name="lang.fotocopiaLicencia" select="'Fotocopia de la licencia'"/>
	<xsl:variable name="lang.fotocopiaIAE" select="'Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.contratoArrendamiento" select="'Contrato de Arrendamiento'"/>

	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>
		
	<xsl:template match="/">
	
		<link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua"></link>
	
		<script type="text/javascript" src="calendario/calendar.js"></script>
		<script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
		<script type="text/javascript" src="calendario/calendario.js"></script>
	
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array();
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.nifCif"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.identidad"/>');
			validar[2] = new Array('relacion','<xsl:value-of select="$lang.relacion"/>');
			validar[3] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[4] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[5] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[6] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[7] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[8] = new Array('documentoIdentidadParticipante', '<xsl:value-of select="$lang.nifCif"/>');
			validar[9] = new Array('nombreParticipante','<xsl:value-of select="$lang.identidad"/>');
			validar[10] = new Array('relacionParticipante','<xsl:value-of select="$lang.relacion"/>');
			validar[11] = new Array('domicilioNotificacionParticipante','<xsl:value-of select="$lang.domicilio"/>');
			validar[12] = new Array('localidadParticipante','<xsl:value-of select="$lang.localidad"/>');
			validar[13] = new Array('provinciaParticipante','<xsl:value-of select="$lang.provincia"/>');
			validar[14] = new Array('codigoPostalParticipante','<xsl:value-of select="$lang.cp"/>');
			validar[15] = new Array('telefonoParticipante','<xsl:value-of select="$lang.telefono"/>');
			
			//validar[3] = new Array('denominacion','<xsl:value-of select="$lang.denominacion"/>');
			//validar[4] = new Array('actividad','<xsl:value-of select="$lang.actividad"/>');
			//validar[5] = new Array('emplazamiento','<xsl:value-of select="$lang.emplazamiento"/>');
			//validar[6] = new Array('fechaAprobacion','<xsl:value-of select="$lang.fechaAprobacion"/>');
			//validar[7] = new Array('fechaLicencia','<xsl:value-of select="$lang.fechaLicencia"/>');
			//validar[3] = new Array('tasas','<xsl:value-of select="$lang.tasas"/>');
			validar[16] = new Array('TRAM8D1','<xsl:value-of select="$lang.instanciaCambioTitular"/>');
			validar[17] = new Array('TRAM8D2','<xsl:value-of select="$lang.fotocopiaLicencia"/>');
			validar[18] = new Array('TRAM8D3','<xsl:value-of select="$lang.fotocopiaIAE"/>');
			
			//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			var especificos = new Array();
			
			//Datos del Interesado
			especificos[0] = new Array('relacion','Relacion');
			especificos[1] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[2] = new Array('localidad','Localidad');
			especificos[3] = new Array('provincia','Provincia');
			especificos[4] = new Array('codigoPostal','Codigo_Postal');
			especificos[5] = new Array('telefono','Telefono');	
			especificos[6] = new Array('emailSolicitante','Email_Solicitante');	
			
			// Datos del Participante
			especificos[7] = new Array('relacionParticipante','Relacion_Participante');
			especificos[8] = new Array('documentoIdentidadParticipante','Documento_Identidad_Participante');
			especificos[9] = new Array('nombreParticipante','Nombre_Participante');
			especificos[10] = new Array('domicilioNotificacionParticipante','Domicilio_Notificacion_Participante');
			especificos[11] = new Array('localidadParticipante','Localidad_Participante');
			especificos[12] = new Array('provinciaParticipante','Provincia_Participante');
			especificos[13] = new Array('codigoPostalParticipante','Codigo_Postal_Participante');
			especificos[14] = new Array('telefonoParticipante','Telefono_Participante');
			especificos[15] = new Array('emailParticipante','Email_Participante');	
			
			// Datos de la solicitud
			especificos[16] = new Array('denominacion','Denominacion');
			especificos[17] = new Array('emplazamiento','Emplazamiento');
			especificos[18] = new Array('fechaLicencia','Fecha_Licencia');
			especificos[19] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[20] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[21] = new Array('relacionDescr','Relacion_Descr');
			especificos[22] = new Array('relacionParticipanteDescr','Relacion_Participante_Descr');
			especificos[23] = new Array('actividad','Actividad');
			especificos[24] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[25] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			
			
			//especificos[26] = new Array('fechaLicenciaFormat','Fecha_Licencia_Format');
			//especificos[27] = new Array('fechaAprobacion','Fecha_Aprobacion');
			//especificos[28] = new Array('tasas','Tasas');
			//especificos[29] = new Array('fechaAprobacionFormat','Fecha_Aprobacion_Format');
			//especificos[30] = new Array('organoResponsable','Organo_Responsable');
			//especificos[31] = new Array('organoResponsableDescr','Organo_Responsable_Descr');
			
			var validarNumero = new Array();
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			validarNumero[1] = new Array('codigoPostalParticipante','<xsl:value-of select="$lang.cp"/>',5);
			//validarNumero[2] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>',9);
			//validarNumero[3] = new Array('telefonoParticipante','<xsl:value-of select="$lang.telefono"/>',9);
			
			function verificacionesEspecificas() {
				var formulario = document.forms[0];
				formulario.relacionDescr.value = formulario.relacion.options[formulario.relacion.selectedIndex].text;
				formulario.relacionParticipanteDescr.value = formulario.relacionParticipante.options[formulario.relacionParticipante.selectedIndex].text;
				return validarFormulario();
			}
			
			function validarFormulario() {
				
				if(!validarCampoNumerico(document.getElementById("telefono"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
				}
				
				if(!validarCampoNumerico(document.getElementById("telefonoParticipante"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
				}
				
				var email = document.getElementById("emailSolicitante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
				
				var email = document.getElementById("emailParticipante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
				
				var nif = document.getElementById("documentoIdentidadParticipante");
				
			    if( !validarNIF(nif) ) {
					alert("El campo '<xsl:value-of select="$lang.nifCif"/>' no es válido");
					nif.focus();
					return false;
				}
				
				var fechaLic = document.getElementById("fechaLicencia");
				if (fechaLic.value != "") {
					if(!validarFormatoFecha(fechaLic, '<xsl:value-of select="$lang.fechaLicencia"/>')) {
						return false;
					}
				}
				
				var formulario = document.forms[0];
				var campo = document.getElementById("relacion");
				if( formulario.relacion.value == formulario.relacionParticipante.value) {
					alert("La Relación de Interesado Principal no puede ser la misma que la del Participante");
					campo.focus();
					return false;
				}
				
				return true;
			}
			
			
		</script>
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosInteresado"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nifCif"/>:<font color="#950000">*</font>
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
					<xsl:value-of select="$lang.identidad"/>:<font color="#950000">*</font>
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
					<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
					<xsl:attribute name="name">relacion</xsl:attribute>
					<xsl:attribute name="id">relacion</xsl:attribute>
					<option></option>
					<xsl:choose>
				       <xsl:when test="$clas='INT'">
				           <option value="INT" selected="selected"><xsl:value-of select="$lang.TVendedor"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="INT"><xsl:value-of select="$lang.TVendedor"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas='ADQU'">
           			      <option value="ADQU" selected="selected"><xsl:value-of select="$lang.TComprador"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="ADQU"><xsl:value-of select="$lang.TComprador"/></option> 
         			   </xsl:otherwise>
         			</xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas='PROP'">
           			      <option value="PROP" selected="selected"><xsl:value-of select="$lang.TTitular"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="PROP"><xsl:value-of select="$lang.TTitular"/></option> 
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
					<xsl:attribute name="maxlength">128</xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia"/></xsl:attribute>
					<xsl:attribute name="maxlength">64</xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Solicitante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosParticipante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nifCif"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidadParticipante</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidadParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Documento_Identidad_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">12</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.identidad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">nombreParticipante</xsl:attribute>
					<xsl:attribute name="id">nombreParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nombre_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>
			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.relacion"/>:<font color="#950000">*</font>
				</label>
				<xsl:variable name="clas2" select="Datos_Registro/datos_especificos/Relacion_Participante"/>
	   			<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
					<xsl:attribute name="name">relacionParticipante</xsl:attribute>
					<xsl:attribute name="id">relacionParticipante</xsl:attribute>
					<option></option>
					<xsl:choose>
				       <xsl:when test="$clas2='INT'">
				           <option value="INT" selected="selected"><xsl:value-of select="$lang.TVendedor"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="INT"><xsl:value-of select="$lang.TVendedor"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas2='ADQU'">
           			      <option value="ADQU" selected="selected"><xsl:value-of select="$lang.TComprador"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="ADQU"><xsl:value-of select="$lang.TComprador"/></option> 
         			   </xsl:otherwise>
         			</xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas2='PROP'">
           			      <option value="PROP" selected="selected"><xsl:value-of select="$lang.TTitular"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="PROP"><xsl:value-of select="$lang.TTitular"/></option> 
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
					<xsl:attribute name="name">domicilioNotificacionParticipante</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacionParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:<font color="#950000">*</font>	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">localidadParticipante</xsl:attribute>
					<xsl:attribute name="id">localidadParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">150</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">provinciaParticipante</xsl:attribute>
					<xsl:attribute name="id">provinciaParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">75</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:<font color="#950000">*</font>
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostalParticipante</xsl:attribute>
					<xsl:attribute name="id">codigoPostalParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal_Participante"/></xsl:attribute>
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
					<xsl:attribute name="name">telefonoParticipante</xsl:attribute>
					<xsl:attribute name="id">telefonoParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono_Participante"/></xsl:attribute>
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
					<xsl:attribute name="name">emailParticipante</xsl:attribute>
					<xsl:attribute name="id">emailParticipante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Participante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro">
 			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.denominacion"/>:
				</label>
				<input type="text" maxlength="254">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">denominacion</xsl:attribute>
					<xsl:attribute name="id">denominacion</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Denominacion"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.actividad"/>:	
				</label>
				<input type="text" maxlength="254">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">actividad</xsl:attribute>
					<xsl:attribute name="id">actividad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Actividad"/></xsl:attribute>
				</input>
			</div>
		   	<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.emplazamiento"/>:
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">emplazamiento</xsl:attribute>
					<xsl:attribute name="id">emplazamiento</xsl:attribute>
					<xsl:attribute name="rows">5</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/Emplazamiento"/>
				</textarea>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.fechaLicencia"/>:	
				</label>
				<input type="text" maxlength="10">
					<xsl:attribute name="style">position: relative; width:60px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">fechaLicencia</xsl:attribute>
					<xsl:attribute name="id">fechaLicencia</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Fecha_Licencia"/></xsl:attribute>
				</input>
				(dd/mm/aaaa)
				<!--
				<img src="img/calendario.bmp" onclick="return showCalendarEsp('370','fechaLicencia','%d-%m-%Y');" />
				<p name="Desde" id="f"></p>
				-->
			</div>
			<!-- 
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.fechaAprobacion"/>:*	
				</label>
				<input type="text" maxlength="10">
					<xsl:attribute name="style">position: relative; width:60px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">fechaAprobacion</xsl:attribute>
					<xsl:attribute name="id">fechaAprobacion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<img src="img/calendario.bmp" onclick="return showCalendarEsp('440','fechaAprobacion','%d-%m-%Y');" />
				<p name="Desde" id="f"></p>
			</div>
			-->
			<!-- 
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.tasas"/>:*	
				</label>
				<input type="text" maxlength="14" onkeypress="return permitirSoloImportes(event)">
					<xsl:attribute name="style">position: relative; width:100px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">tasas</xsl:attribute>
					<xsl:attribute name="id">tasas</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<label>
					<xsl:attribute name="style">width:200px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif; color:#006699;</xsl:attribute>
					<xsl:value-of select="$lang.euros"/>	
				</label>
			</div>
			 -->
   		</div>
   		<br/>
		<!-- 
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.organoSujeto_1"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.organoResponsable"/>:
				</label>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
					<xsl:attribute name="name">organoResponsable</xsl:attribute>
					<xsl:attribute name="id">organoResponsable</xsl:attribute>
					<option selected="selected" value=""></option>
					<option value="004"><xsl:value-of select="$lang.gerenciasSubgerenciasCatastro"/></option>
					<option value="005"><xsl:value-of select="$lang.areaUrbanismo"/></option>
				</select>
			</div>
   		</div>
   		<br/>-->
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
			</label>
			<br/><br/><br/>
			<div>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.instanciaCambioTitular"/></li></ul>
				</label>
			</div>
   			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM8D1</xsl:attribute>
					<xsl:attribute name="id">TRAM8D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM8D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM8D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div style="height:15px">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.fotocopiaLicencia"/></li></ul>
				</label>
			</div>
			<br/>
   			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM8D2</xsl:attribute>
					<xsl:attribute name="id">TRAM8D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM8D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM8D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div style="height:15px">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.fotocopiaIAE"/></li></ul>
				</label>
			</div>
			<br/>
   			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM8D3</xsl:attribute>
					<xsl:attribute name="id">TRAM8D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM8D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM8D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div style="height:15px">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.contratoArrendamiento"/></li></ul>
				</label>
			</div>
			<br/>
   			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:</b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM8D4</xsl:attribute>
					<xsl:attribute name="id">TRAM8D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM8D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM8D4_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
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
					<xsl:value-of select="$lang.deu"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:200px; </xsl:attribute>
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
			<div class="col" style="display:none; visibility: hidden" >
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px; </xsl:attribute>
					<xsl:attribute name="name">relacionDescr</xsl:attribute>
					<xsl:attribute name="id">relacionDescr</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px; </xsl:attribute>
					<xsl:attribute name="name">relacionParticipanteDescr</xsl:attribute>
					<xsl:attribute name="id">relacionParticipanteDescr</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<!-- 
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px; </xsl:attribute>
					<xsl:attribute name="name">organoResponsableDescr</xsl:attribute>
					<xsl:attribute name="id">organoResponsableDescr</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px; </xsl:attribute>
					<xsl:attribute name="name">fechaLicenciaFormat</xsl:attribute>
					<xsl:attribute name="id">fechaLicenciaFormat</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				 -->
			</div>
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