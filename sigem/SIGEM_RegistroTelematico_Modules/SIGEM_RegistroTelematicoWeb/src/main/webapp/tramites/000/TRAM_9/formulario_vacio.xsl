<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'Formulario de Solicitud de Certificado Urbanístico'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>
	
	<xsl:variable name="lang.representante" select="'Datos del Representante'"/>
	<xsl:variable name="lang.situacion" select="'Situacion de la Finca'"/>
	<xsl:variable name="lang.documento1" select="'Justificante de autoliquidacion de la tasa por emisión de certificados.'"/>
	<xsl:variable name="lang.documento2" select="'Plano de localización sobre cartografía oficial FIRMADO, en el que se refleje la posición exacta de la finca/parcela/edificio objeto de la solicitud.'"/>
	<xsl:variable name="lang.documento3" select="'Fotocopia del DNI del solicitante en el caso de personas físicas. En el caso de actuar en representación, fotocopia de los documentos de identidad del representante y del solicitante, con autorización escrita de este último o copia de poder para representarlo. En el caso de personas jurídicas, la persona que firma la solicitud adjuntará documentación acreditativa de su representación como puede ser una fotocopia pública de la escritura de constitución de la sociedad, poder, etc.'"/>
	<xsl:variable name="lang.documento4" select="'Acreditación de la condición de interesado (escrituras de propiedad,etc.).'"/>
	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>	
	<xsl:template match="/">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array(11);
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[6] = new Array('situacionInmueble','<xsl:value-of select="$lang.situacion"/>');
			validar[7] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[8] = new Array('TRAM9D2','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[9] = new Array('TRAM9D3','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[10] = new Array('TRAM9D4','<xsl:value-of select="$lang.documentoPDF"/>');
			
			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array(16);
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('telefono','Telefono');
			especificos[5] = new Array('nombreRepresentante','Nombre_Representante');
			especificos[6] = new Array('nifRepresentante','Nif_Representante');
			especificos[7] = new Array('domicilioRepresentante','Domicilio_Representante');
			especificos[8] = new Array('localidadRepresentante','Localidad_Representante');
			especificos[9] = new Array('provinciaRepresentante','Provincia_Representante');
			especificos[10] = new Array('codigoPostalRepresentante','Codigo_Postal_Representante');
			especificos[11] = new Array('telefonoRepresentante','Telefono_Representante');
			especificos[12] = new Array('emailRepresentante','Email_Representante');
			especificos[13] = new Array('situacionInmueble','Situacion_Inmueble');	
			especificos[14] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[15] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');	
			especificos[16] = new Array('emailSolicitante', "Email_Solicitante");	

			
			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
			
				if(!validarCampoNumerico(document.getElementById("telefono"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
				}
				
				var telefonoRepresentante = document.getElementById("telefonoRepresentante")
				if (telefonoRepresentante.value != "") {
					if(!validarCampoNumerico(telefonoRepresentante, '<xsl:value-of select="$lang.telefono"/>')) {
						return false;
					}
				}
				
				var email = document.getElementById("emailSolicitante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
				
				var email = document.getElementById("emailRepresentante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
				
				var nif = document.getElementById("nifRepresentante");
				
			    if( !validarNIF(nif) ) {
					alert("El campo '<xsl:value-of select="$lang.docIdentidad"/>' no es válido");
					nif.focus();
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
   			<h1><xsl:value-of select="$lang.representante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">nifRepresentante</xsl:attribute>
					<xsl:attribute name="id">nifRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nif_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">12</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">nombreRepresentante</xsl:attribute>
					<xsl:attribute name="id">nombreRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nombre_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">250</xsl:attribute>
				</input>
			</div>			
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">domicilioRepresentante</xsl:attribute>
					<xsl:attribute name="id">domicilioRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">128</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">localidadRepresentante</xsl:attribute>
					<xsl:attribute name="id">localidadRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">provinciaRepresentante</xsl:attribute>
					<xsl:attribute name="id">provinciaRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">64</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostalRepresentante</xsl:attribute>
					<xsl:attribute name="id">codigoPostalRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal_Representante"/></xsl:attribute>
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
					<xsl:attribute name="name">telefonoRepresentante</xsl:attribute>
					<xsl:attribute name="id">telefonoRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono_Representante"/></xsl:attribute>
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
					<xsl:attribute name="name">emailRepresentante</xsl:attribute>
					<xsl:attribute name="id">emailRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Representante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>

		<!--  -->
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
 			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.situacion"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">situacionInmueble</xsl:attribute>
					<xsl:attribute name="id">situacionInmueble</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Situacion_Inmueble"/></xsl:attribute>
					<xsl:attribute name="maxlength">500</xsl:attribute>
				</input>
			</div>
   		</div>
   		<br/>
		<!--  -->
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
				</label>
			</div>
			<br/><br/><br/>
			<div>
				<label class="gr">
				   	<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
			   		<ul><li><xsl:value-of select="$lang.documento2"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px;</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM9D2</xsl:attribute>
					<xsl:attribute name="id">TRAM9D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM9D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM9D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
   			<br/>
   			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento3"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM9D3</xsl:attribute>
					<xsl:attribute name="id">TRAM9D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM9D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM9D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
   			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px; size:400px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento4"/></li></ul>
				</label>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM9D4</xsl:attribute>
					<xsl:attribute name="id">TRAM9D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM9D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM9D4_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento1"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px;</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:</b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM9D1</xsl:attribute>
					<xsl:attribute name="id">TRAM9D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM9D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM9D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
   		</div>
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