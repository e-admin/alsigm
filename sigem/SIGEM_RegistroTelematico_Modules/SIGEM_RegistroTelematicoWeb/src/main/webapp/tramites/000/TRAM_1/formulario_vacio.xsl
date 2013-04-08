<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Solicitud de Reclamación, Quejas y Sugerencias (00001)'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.solicita" select="'Solicita'"/>
	<xsl:variable name="lang.expone" select="'Expone'"/>
	<xsl:variable name="lang.organoAsignado" select="'Órgano al que se dirige: Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
	<xsl:variable name="lang.servTramLicencias" select="'Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servicio de Secretaría'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'Documento'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.direccion.editar" select="'Editar'"/>
	<xsl:variable name="lang.direccion.seleccionar" select="'Seleccionar'"/>

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
			validar[6] = new Array('solicita','<xsl:value-of select="$lang.solicita"/>');
			validar[7] = new Array('expone','<xsl:value-of select="$lang.expone"/>');
			validar[8] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[9] = new Array('TRAM1D1','<xsl:value-of select="$lang.documento1"/>');

			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array(12);
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('solicita','Solicita');
			especificos[5] = new Array('expone','Expone');
			especificos[6] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[7] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[8] = new Array('telefono','Telefono');
			especificos[9] = new Array('expone','asunto_queja');
			especificos[10] = new Array('organoDestinatario','cod_organo');
			especificos[11] = new Array('emailSolicitante','Email_Solicitante');

			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);

			function verificacionesEspecificas() {
				return true;
			}

			var direcciones = new Array(<xsl:value-of select="count(Direccion)"/>);
			<xsl:for-each select="Datos_Registro/Datos_Tercero/Direcciones/Direccion">
				var id = '<xsl:value-of select="ID_Direccion" />';
				direcciones[id]=new Array();
				direcciones[id]['localidad'] = '<xsl:value-of select="Localidad" />';
				direcciones[id]['provincia'] = '<xsl:value-of select="Provincia" />';
				direcciones[id]['codigoPostal'] = '<xsl:value-of select="Codigo_Postal" />';
			</xsl:for-each>

			function actualizarDatosTercero(domicilio){

				var optionSelected = domicilio.options[domicilio.options.selectedIndex];
				var idDireccion = optionSelected.getAttribute('class');
				if(idDireccion==null)
				{
					idDireccion = optionSelected.className;
				}
				document.getElementById('codigoPostal').value=direcciones[idDireccion]['codigoPostal'];
				document.getElementById('provincia').value=direcciones[idDireccion]['provincia'];
				document.getElementById('localidad').value=direcciones[idDireccion]['localidad'];

				document.getElementById('codigoPostal').disabled='disabled';
				document.getElementById('provincia').disabled='disabled';
				document.getElementById('localidad').disabled='disabled';
			}
			function cambiarDireccionToEdit(){
				var inputText = document.getElementById('domicilioNotificacionText');
				var inputSelect = document.getElementById('domicilioNotificacion');

				var buttonSeleccionar = document.getElementById('seleccionarDireccionButton');
				var buttonCambiar = document.getElementById('cambiarDireccionButton');

				buttonCambiar.style.display='none';
				buttonSeleccionar.style.display='inline';

				inputSelect.style.display='none';
				inputText.style.display='inline';


				document.getElementById('codigoPostal').value='';
				document.getElementById('provincia').value='';
				document.getElementById('localidad').value='';

				document.getElementById('codigoPostal').removeAttribute('disabled');
				document.getElementById('provincia').removeAttribute('disabled');
				document.getElementById('localidad').removeAttribute('disabled');

				especificos[0] = new Array('domicilioNotificacionText','Domicilio_Notificacion');
			}

			function cambiarDireccionToSelect (){
				var inputText = document.getElementById('domicilioNotificacionText');
				var inputSelect = document.getElementById('domicilioNotificacion');

				var buttonSeleccionar = document.getElementById('seleccionarDireccionButton');
				var buttonCambiar = document.getElementById('cambiarDireccionButton');

				buttonCambiar.style.display='inline';
				buttonSeleccionar.style.display='none';

				inputSelect.style.display='inline';
				inputText.style.display='none';
				especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
				actualizarDatosTercero(inputSelect);
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
			<div class="col">
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
   			<div class="col"  style="height: 35px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:
				</label>

				<!-- Si hay direcciones en terceros, las mostramos -->
				<xsl:if test="count(//Direccion) &gt; 0">
					<select class="gr">
						<xsl:attribute name="style">position: relative; width:350px; display:inline; </xsl:attribute>
						<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
						<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
						<xsl:attribute name="onchange">javascript:actualizarDatosTercero(this);</xsl:attribute>
						<xsl:for-each select="Datos_Registro/Datos_Tercero/Direcciones/Direccion">
							 <xsl:variable name="porDefecto" select="Por_Defecto"/>
							 <option>
								<xsl:attribute name="value"><xsl:value-of select="Direccion_Completa"/></xsl:attribute>
								<xsl:if test="$porDefecto='true'">
									<xsl:attribute name="selected">selected</xsl:attribute>
								</xsl:if>
								<xsl:attribute name="class"><xsl:value-of select="ID_Direccion"/></xsl:attribute>
								<xsl:value-of select="Direccion_Completa"/>
							 </option>
						</xsl:for-each>
					</select>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:350px;display:none; </xsl:attribute>
						<xsl:attribute name="name">domicilioNotificacionText</xsl:attribute>
						<xsl:attribute name="id">domicilioNotificacionText</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
					</input>
					<img id="cambiarDireccionButton" style="display:inline; cursor: pointer;" src="./img/edit.gif" onclick="javascript:cambiarDireccionToEdit();">
						<xsl:attribute name="alt"><xsl:value-of select="$lang.direccion.editar" /></xsl:attribute>
					</img>
					<img id="seleccionarDireccionButton" style="display:none; cursor: pointer;" src="./img/direcciones.png" onclick="javascript:cambiarDireccionToSelect();">
						<xsl:attribute name="alt"><xsl:value-of select="$lang.direccion.seleccionar" /></xsl:attribute>
					</img>
				</xsl:if>
				<xsl:if test="count(//Direccion) = 0">
					<!-- Si no, caja de texto abierta -->
					<input type="text">
						<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
						<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
						<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
					</input>
				</xsl:if>
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

					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Datos_Tercero/Direcciones/Direccion[Por_Defecto='true']/Localidad"/></xsl:attribute>

					<xsl:if test="count(//Direccion) &gt; 0">
						<xsl:attribute name="disabled"></xsl:attribute>
					</xsl:if>

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

					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Datos_Tercero/Direcciones/Direccion[Por_Defecto='true']/Provincia"/></xsl:attribute>

					<xsl:if test="count(//Direccion) &gt; 0">
						<xsl:attribute name="disabled"></xsl:attribute>
					</xsl:if>

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

					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Datos_Tercero/Direcciones/Direccion[Por_Defecto='true']/Codigo_Postal"/></xsl:attribute>
					<xsl:if test="count(//Direccion) &gt; 0">
						<xsl:attribute name="disabled"></xsl:attribute>
					</xsl:if>

				</input>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
 			<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.expone"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:490px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">expone</xsl:attribute>
					<xsl:attribute name="id">expone</xsl:attribute>
					<xsl:attribute name="rows">5</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/Expone"/>
				</textarea>
			</div>
		   	<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.solicita"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:490px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">solicita</xsl:attribute>
					<xsl:attribute name="id">solicita</xsl:attribute>
					<xsl:attribute name="rows">5</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/Solicita"/>
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
				<xsl:variable name="clas" select="Datos_Registro/datos_especificos/cod_organo"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">organoDestinatario</xsl:attribute>
					<xsl:attribute name="id">organoDestinatario</xsl:attribute>
					<option value=""></option>
					<xsl:choose>
				       <xsl:when test="$clas='002'">
				           <option value="002" selected="selected"><xsl:value-of select="$lang.servTramLicencias"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="002"><xsl:value-of select="$lang.servTramLicencias"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
  				    <xsl:choose>
         			   <xsl:when test="$clas='003'">
           			      <option value="003" selected="selected"><xsl:value-of select="$lang.servSecretaria"/></option>
         			   </xsl:when>
         		       <xsl:otherwise>
           				  <option value="003"><xsl:value-of select="$lang.servSecretaria"/></option>
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
					<xsl:attribute name="name">TRAM1D1</xsl:attribute>
					<xsl:attribute name="id">TRAM1D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM1D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM1D1_Tipo</xsl:attribute>
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
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
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
	</xsl:template>
</xsl:stylesheet>