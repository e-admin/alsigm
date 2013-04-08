<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de solicitud de Licencia de vado'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.ubicacion" select="'Ubicación'"/>
	<xsl:variable name="lang.tipoVado" select="'Tipo de vado'"/>
	<xsl:variable name="lang.TLaboral" select="'Laboral'"/>
	<xsl:variable name="lang.TPermanente" select="'Permanente'"/>
	<xsl:variable name="lang.actividad" select="'Actividad o uso del local'"/>
	<xsl:variable name="lang.numero" select="'Número de plazas para vehículos'"/>
	<xsl:variable name="lang.rebaje" select="'Rebaje'"/>
	<xsl:variable name="lang.TAceraAncha" select="'Acera ancha'"/>
	<xsl:variable name="lang.TAceraEstrecha" select="'Acera estrecha'"/>
	<xsl:variable name="lang.TMinusvalidos" select="'Minusválidos'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.licenciaVado" select="'Solicitud de licencia de vado'"/>
	<xsl:variable name="lang.licenciaObras" select="'Licencias de obras y apertura de los inmuebles a los que se accede'"/>
	<xsl:variable name="lang.planoSituacion" select="'Plano de situación a escala 1/200'"/>
	<xsl:variable name="lang.planoPlanta" select="'Plano de planta a escala 1/50'"/>
	<xsl:variable name="lang.justificante" select="'Justificante de reintegro de los derechos'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'Fotocopia del DNI'"/>
	
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>
		
	<xsl:template match="/">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array();
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[6] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[7] = new Array('ubicacion','<xsl:value-of select="$lang.ubicacion"/>');
			validar[8] = new Array('tipoVado','<xsl:value-of select="$lang.tipoVado"/>');
			validar[9] = new Array('actividad','<xsl:value-of select="$lang.actividad"/>');
			validar[10] = new Array('numero','<xsl:value-of select="$lang.numero"/>');
			validar[11] = new Array('TRAM12D1','<xsl:value-of select="$lang.licenciaVado"/>');
			validar[12] = new Array('TRAM12D2','<xsl:value-of select="$lang.licenciaObras"/>');
			validar[13] = new Array('TRAM12D3','<xsl:value-of select="$lang.planoSituacion"/>');
			validar[14] = new Array('TRAM12D4','<xsl:value-of select="$lang.planoPlanta"/>');
			
			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array();
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('telefono','Telefono');
			especificos[5] = new Array('ubicacion','Ubicacion');	
			especificos[6] = new Array('tipoVado','Tipo_Vado');
			especificos[7] = new Array('actividad','Actividad');
			especificos[8] = new Array('numero','Numero');
			especificos[9] = new Array('rebaje','Rebaje');
			especificos[10] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[11] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[12] = new Array('emailSolicitante','Email_Solicitante');
			
			var validarNumero = new Array();
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
				var formulario = document.forms[0];
				
				var valor = formulario.telefono.value;
				if (valor != ""){
					valor = parseInt(valor);
					if (isNaN(valor)) {
						alert("El valor del campo '<xsl:value-of select="$lang.telefono"/>' no es numérico");
						document.getElementById('telefono').focus();
						return false;
					}
				}
				valor = formulario.numero.value;
				if (valor != ""){
					valor = parseInt(valor);
					if (isNaN(valor)) {
						alert("El valor del campo '<xsl:value-of select="$lang.numero"/>' no es numérico");
						document.getElementById('numero').focus();
						return false;
					}
				}
				
				var email = document.getElementById("emailSolicitante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
				
				return true;
			}
		</script>
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitante"/></h1>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Solicitante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.ubicacion"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">ubicacion</xsl:attribute>
					<xsl:attribute name="id">ubicacion</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Ubicacion"/></xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.tipoVado"/>:<font color="#950000">*</font>
				</label>
				<xsl:variable name="clas" select="Datos_Registro/datos_especificos/Tipo_Vado"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
					<xsl:attribute name="name">tipoVado</xsl:attribute>
					<xsl:attribute name="id">tipoVado</xsl:attribute>
					<option></option>
					<xsl:choose>
				       <xsl:when test="$clas='Laboral'">
				           <option value="Laboral" selected="selected"><xsl:value-of select="$lang.TLaboral"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Laboral"><xsl:value-of select="$lang.TLaboral"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas='Permanente'">
				           <option value="Permanente" selected="selected"><xsl:value-of select="$lang.TPermanente"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Permanente"><xsl:value-of select="$lang.TPermanente"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
				</select>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.actividad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">actividad</xsl:attribute>
					<xsl:attribute name="id">actividad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Actividad"/></xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.numero"/>:<font color="#950000">*</font>
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">numero</xsl:attribute>
					<xsl:attribute name="id">numero</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Numero"/></xsl:attribute>
					<xsl:attribute name="maxlength">2</xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.rebaje"/>:
				</label>
				<xsl:variable name="clas2" select="Datos_Registro/datos_especificos/Rebaje"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
					<xsl:attribute name="name">rebaje</xsl:attribute>
					<xsl:attribute name="id">rebaje</xsl:attribute>
					<option></option>
					<xsl:choose>
				       <xsl:when test="$clas2='Acera ancha'">
				           <option value="Acera ancha" selected="selected"><xsl:value-of select="$lang.TAceraAncha"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Acera ancha"><xsl:value-of select="$lang.TAceraAncha"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='Acera Estrecha'">
				           <option value="Acera Estrecha" selected="selected"><xsl:value-of select="$lang.TAceraEstrecha"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Acera Estrecha"><xsl:value-of select="$lang.TAceraEstrecha"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='Minusvalidos'">
				           <option value="Minusvalidos" selected="selected"><xsl:value-of select="$lang.TMinusvalidos"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Minusvalidos"><xsl:value-of select="$lang.TMinusvalidos"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
				</select>
			</div>
   		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:600px;height:35px</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
			</label>
			<br/><br/><br/>
   			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.licenciaVado"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D1</xsl:attribute>
					<xsl:attribute name="id">TRAM12D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.licenciaObras"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D2</xsl:attribute>
					<xsl:attribute name="id">TRAM12D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.planoSituacion"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D3</xsl:attribute>
					<xsl:attribute name="id">TRAM12D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.planoPlanta"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D4</xsl:attribute>
					<xsl:attribute name="id">TRAM12D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D4_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.justificante"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:</b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D5</xsl:attribute>
					<xsl:attribute name="id">TRAM12D5</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D5_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D5_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.fotocopiaDni"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:</b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM12D6</xsl:attribute>
					<xsl:attribute name="id">TRAM12D6</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM12D6_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM12D6_Tipo</xsl:attribute>
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