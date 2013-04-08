<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xalan/java"
    exclude-result-prefixes="java">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Subvención'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.tipoSubvencion" select="'Tipo de Subvención'"/>
	<xsl:variable name="lang.TSinvestigaciones" select="'Investigaciones'"/>
	<xsl:variable name="lang.TSinnovacionTecnologica" select="'Innovación Tecnológica'"/>
	<xsl:variable name="lang.TSactividadPromocional" select="'Actidad Promocional'"/>
	<xsl:variable name="lang.TSobrasMenores" select="'Obras Menores'"/>
	<xsl:variable name="lang.resumenProyecto" select="'Resumen del Proyecto'"/>
	<xsl:variable name="lang.organoAsignado" select="'Órgano al que se dirige: Servicio de Secretaría'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
	<xsl:variable name="lang.servTramLicencias" select="'Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'Documento'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>

	<xsl:variable name="entidad" select="'001'"/>
	<xsl:variable name="language" select="'es'"/>
	<xsl:variable name="tramiteId" select="'TRAM_3'"/>

	<xsl:variable name="organismosLoader" select="java:ieci.tecdoc.sgm.registro.form.loaders.SelectOptionsOrganismosLoader.new($entidad,$language)"/>

	<xsl:template match="/">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array(10);
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[6] = new Array('tipoSubvencion','<xsl:value-of select="$lang.tipoSubvencion"/>');
			validar[7] = new Array('resumenProyecto','<xsl:value-of select="$lang.resumenProyecto"/>');
			validar[8] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[9] = new Array('TRAM3D1','<xsl:value-of select="$lang.documento1"/>');

			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array(13);
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('tipoSubvencion','tipo_subvencion');
			especificos[5] = new Array('resumenProyecto','resumen_proyecto');
			especificos[6] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[7] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[8] = new Array('telefono','Telefono');
			especificos[9] = new Array('organoDestinatario','cod_organo');
			especificos[10] = new Array('emailSolicitante','Email_Solicitante');
			especificos[11] = new Array('tipoSubvencion','tipo_subvencion');
			especificos[12] = new Array('resumenProyecto','resumen_proyecto');

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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono"/></xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad"/></xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia"/></xsl:attribute>
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
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal"/></xsl:attribute>
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
					<xsl:value-of select="$lang.tipoSubvencion"/>:*
				</label>
				<xsl:variable name="clas" select="Datos_Registro/datos_especificos/tipo_subvencion"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">tipoSubvencion</xsl:attribute>
					<xsl:attribute name="id">tipoSubvencion</xsl:attribute>
					<option value=""></option>
					<xsl:choose>
				       <xsl:when test="$clas='INV'">
				           <option value="INV" selected="selected"><xsl:value-of select="$lang.TSinvestigaciones"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="INV"><xsl:value-of select="$lang.TSinvestigaciones"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
  				    <xsl:choose>
         			   <xsl:when test="$clas='TIC'">
           			      <option value="TIC" selected="selected"><xsl:value-of select="$lang.TSinnovacionTecnologica"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="TIC"><xsl:value-of select="$lang.TSinnovacionTecnologica"/></option>
         			   </xsl:otherwise>
         			</xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas='PRO'">
           			      <option value="PRO" selected="selected"><xsl:value-of select="$lang.TSactividadPromocional"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="PRO"><xsl:value-of select="$lang.TSactividadPromocional"/></option>
         			   </xsl:otherwise>
         			</xsl:choose>
         			<xsl:choose>
         			   <xsl:when test="$clas='OBR'">
           			      <option value="OBR" selected="selected"><xsl:value-of select="$lang.TSobrasMenores"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="OBR"><xsl:value-of select="$lang.TSobrasMenores"/></option>
         			   </xsl:otherwise>
         			</xsl:choose>
				</select>
			</div>
 			<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.resumenProyecto"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:490px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">resumenProyecto</xsl:attribute>
					<xsl:attribute name="id">resumenProyecto</xsl:attribute>
					<xsl:attribute name="rows">5</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/resumen_proyecto"/>
				</textarea>
			</div>
   		</div>
   		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.organoAsignado"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.organoAlternativo"/>:
				</label>
				<xsl:variable name="organo" select="Datos_Registro/datos_especificos/cod_organo"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">organoDestinatario</xsl:attribute>
					<xsl:attribute name="id">organoDestinatario</xsl:attribute>
					<option value=""></option>

					<xsl:variable name="options" select="java:createOptions($organismosLoader,$tramiteId)"/>
					<xsl:for-each select="$options/option">
						<option>
							<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
							<xsl:value-of select="text"/>
						</option>
					</xsl:for-each>

					<!--
					<xsl:choose>
				       <xsl:when test="$organo='001'">
				           <option value="001" selected="selected"><xsl:value-of select="$lang.servRelacionesCiudadano"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="001"><xsl:value-of select="$lang.servRelacionesCiudadano"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
  				    <xsl:choose>
         			   <xsl:when test="$organo='002'">
           			      <option value="002" selected="selected"><xsl:value-of select="$lang.servTramLicencias"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="002"><xsl:value-of select="$lang.servTramLicencias"/></option>
         			   </xsl:otherwise>
         			</xsl:choose>
         			-->
				</select>
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
					<xsl:attribute name="name">TRAM3D1</xsl:attribute>
					<xsl:attribute name="id">TRAM3D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM3D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM3D1_Tipo</xsl:attribute>
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
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="id">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Direccion_Electronica_Unica"/></xsl:attribute>
				</input>
				<xsl:choose>
				   <xsl:when test="$deu!=''">
				       <script language="Javascript">activarDEU();</script>
				   </xsl:when>
			    </xsl:choose>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>