<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Bonificaciones/Exenciones Fiscales'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.beneficioFiscal" select="'Beneficio fiscal'"/>
	<xsl:variable name="lang.impuesto" select="'Impuesto'"/>
	<xsl:variable name="lang.hechos" select="'Hechos'"/>			
	<xsl:variable name="lang.organoAsignado" select="'Órgano al que se dirige: Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servicio de Secretaría'"/>
	<xsl:variable name="lang.exencionFiscal" select="'Exencion fiscal'"/>
	<xsl:variable name="lang.bonificacionFiscal" select="'Bonificacion fiscal'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'Documento'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.iae" select="'Impuesto de Actividades Económicas'"/>
	<xsl:variable name="lang.ibi" select="'Impuesto sobre Bienes Inmuebles'"/>
	<xsl:variable name="lang.icio" select="'Impuesto sobre Construcciones Instalaciones y Obras'"/>
	<xsl:variable name="lang.iivtnu" select="'Impuesto de Incremento Valor sobre Naturaleza Urbana'"/>
	<xsl:variable name="lang.ivtm" select="'Impuesto sobre Vehiculos de Tracción Mecánica'"/>

		
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
			validar[6] = new Array('beneficioFiscal','<xsl:value-of select="$lang.beneficioFiscal"/>');
			validar[7] = new Array('hechos','<xsl:value-of select="$lang.hechos"/>');
			validar[8] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[9] = new Array('TRAM5D1','<xsl:value-of select="$lang.documento1"/>');
			validar[10] = new Array('impuesto','<xsl:value-of select="$lang.impuesto"/>');


			
			//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			var especificos = new Array(10);
			especificos[0] = new Array('telefono','Telefono');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('beneficioFiscal','Beneficio_fiscal');
			especificos[5] = new Array('hechos','Hechos');
			especificos[6] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[7] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[8] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[9] = new Array('impuesto','Impuesto');
			
			
			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
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
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Documento_Identificacion/Numero"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="id">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Nombre"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col" style="height: 35px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.telefono"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">telefono</xsl:attribute>
					<xsl:attribute name="id">telefono</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" style="height: 35px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.email"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">emailSolicitante</xsl:attribute>
					<xsl:attribute name="id">emailSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Correo_Electronico"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">localidad</xsl:attribute>
					<xsl:attribute name="id">localidad</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">provincia</xsl:attribute>
					<xsl:attribute name="id">provincia</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostal</xsl:attribute>
					<xsl:attribute name="id">codigoPostal</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
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
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.beneficioFiscal"/>:
				</label>

				<select class="gr">
					<xsl:attribute name="style">position: relative; width:130px; </xsl:attribute>
					<xsl:attribute name="name">beneficioFiscal</xsl:attribute>
					<xsl:attribute name="id">beneficioFiscal</xsl:attribute>
					<option selected="selected" value=""></option>
					<option value="Exencion"><xsl:value-of select="$lang.exencionFiscal"/></option>
					<option value="Bonificacion"><xsl:value-of select="$lang.bonificacionFiscal"/></option>
				</select>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.impuesto"/>:*
				</label>
				
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:300px; </xsl:attribute>
					<xsl:attribute name="name">impuesto</xsl:attribute>
					<xsl:attribute name="id">impuesto</xsl:attribute>
					<option selected="selected" value=""></option>
					<option value="IAE"><xsl:value-of select="$lang.iae"/></option>
					<option value="IBI"><xsl:value-of select="$lang.ibi"/></option>
					<option value="ICIO"><xsl:value-of select="$lang.icio"/></option>
					<option value="IIVTNU"><xsl:value-of select="$lang.iivtnu"/></option>
					<option value="IVTM"><xsl:value-of select="$lang.ivtm"/></option>


				</select>
			</div>

 			<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.hechos"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:350px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">hechos</xsl:attribute>
					<xsl:attribute name="id">hechos</xsl:attribute>
					<xsl:attribute name="rows">3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</textarea>
			</div>
   		</div>
   		<br/>
		
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
			</label>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.documento1"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">TRAM5D1</xsl:attribute>
					<xsl:attribute name="id">TRAM5D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM5D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM5D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf</xsl:attribute>
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
   			<input class="gr" type="checkbox" id="solicitarEnvio" onclick="activarDEU();" style="border:0px; width:20px;" />
   			<label for="solicitarEnvio" onclick="activarDEU();">  <xsl:value-of select="$lang.solicitoEnvio"/></label>
   			<br/><br/>
	   		<div class="col" style="visibility: hidden" id="DEU" name="DEU">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="id">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>