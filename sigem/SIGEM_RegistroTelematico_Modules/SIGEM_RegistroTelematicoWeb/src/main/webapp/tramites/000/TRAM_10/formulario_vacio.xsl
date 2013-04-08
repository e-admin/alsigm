<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:include href="../../actividad_solicitante_relacion_select.xsl"/>

	<xsl:variable name="lang.titulo" select="'Formulario de solicitud de Licencia de Apertura de Actividad Clasificada (RAMINP)'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'Actividad'"/>
	<xsl:variable name="lang.clasificacion" select="'Clasificación'"/>
	<xsl:variable name="lang.emplazamiento" select="'Emplazamiento'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'Fotocopia del DNI/Escritura de Constitución y C.I.F.'"/>
	<xsl:variable name="lang.instancia" select="'Solicitud de licencia de apertura de actividad sujeta al RAMINP: formulario/instancia municipal'"/>
	<xsl:variable name="lang.proyecto" select="'Proyecto técnico y Memoria valorada (certif. instalación extintores, etc).'"/>
	<xsl:variable name="lang.croquis" select="'Croquis o planos de planta (con el máximo detalle posible) y situación del local'"/>
	<xsl:variable name="lang.justificante" select="'Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.arrendamiento" select="'Contrato de arrendamiento o escritura de la propiedad'"/>

	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.nota" select="'IMPORTANTE'"/>
	<xsl:variable name="lang.aclaracion" select="'Previo a conceder la Licencia de Apertura se deben haber obtenido previamente la Licencia de Instalación, la Licencia de Obra y el Informe Técnico pertinentes'"/>

	<xsl:variable name="lang.TIndustriales" select="'Industriales'"/>
	<xsl:variable name="lang.THosteleria" select="'Hostelería (CÍBER incluídos)'"/>
	<xsl:variable name="lang.TConcurrencia" select="'Concurrencia pública'"/>
	<xsl:variable name="lang.TTalleres" select="'Talleres'"/>
	<xsl:variable name="lang.TGarajes" select="'Garajes o gasolineras'"/>
	<xsl:variable name="lang.TCarnicerias" select="'Carnicerías y pescaderías'"/>
	<xsl:variable name="lang.TClinicas" select="'Clínicas médicas'"/>
	<xsl:variable name="lang.TVeterinarias" select="'Veterinarias'"/>
	<xsl:variable name="lang.TTiendas" select="'Tiendas de animales'"/>
	<xsl:variable name="lang.TAlmacenaje" select="'Almacenaje de materiales peligrosos'"/>
	<xsl:variable name="lang.TPeluquerias" select="'Peluquerías con solarium '"/>
	<xsl:variable name="lang.TOtros" select="'Otros'"/>

	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>

	<xsl:template match="/">
		<script language="Javascript">
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
			validar[8] = new Array('TRAM10D1','<xsl:value-of select="$lang.fotocopiaDni"/>');
			validar[9] = new Array('TRAM10D2','<xsl:value-of select="$lang.instancia"/>');
			validar[10] = new Array('TRAM10D3','<xsl:value-of select="$lang.proyecto"/>');
			validar[11] = new Array('TRAM10D4','<xsl:value-of select="$lang.croquis"/>');
			validar[12] = new Array('TRAM10D5','<xsl:value-of select="$lang.justificante"/>');

			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			var especificos = new Array();
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[5] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[6] = new Array('telefono','Telefono');
			especificos[7] = new Array('actividad','Actividad');
			especificos[8] = new Array('emplazamiento','Emplazamiento');
			especificos[9] = new Array('relacion','Relacion');
			especificos[10] = new Array('relacionDescr','Relacion_Descripcion');
			especificos[11] = new Array('clasificacionDescr','Clasificacion_Descripcion');
			especificos[12] = new Array('clasificacion','Clasificacion');
			especificos[13] = new Array('emailSolicitante','Email_Solicitante');

			var validarNumero = new Array();
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);

			function verificacionesEspecificas() {
				var formulario = document.forms[0];
				formulario.relacionDescr.value = formulario.relacion.options[formulario.relacion.selectedIndex].text;
				formulario.clasificacionDescr.value = formulario.clasificacion.options[formulario.clasificacion.selectedIndex].text;

				if(!validarCampoNumerico(document.getElementById("telefono"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
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
			<xsl:call-template name="Actividad_Solicitante_Relacion_Select"></xsl:call-template>
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
					<xsl:attribute name="maxlength">250</xsl:attribute>
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
					<xsl:value-of select="$lang.actividad"/>:
				</label>
				<input type="text" maxlength="254">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">actividad</xsl:attribute>
					<xsl:attribute name="id">actividad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Actividad"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.clasificacion"/>:
				</label>
				<xsl:variable name="clas2" select="Datos_Registro/datos_especificos/Clasificacion"/>
				<select class="gr">
					<xsl:attribute name="style">position: relative; width:400px; height:20px</xsl:attribute>
					<xsl:attribute name="name">clasificacion</xsl:attribute>
					<xsl:attribute name="id">clasificacion</xsl:attribute>
					<option value=""></option>
					<xsl:choose>
				       <xsl:when test="$clas2='01'">
				           <option value="01" selected="selected"><xsl:value-of select="$lang.TIndustriales"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="01"><xsl:value-of select="$lang.TIndustriales"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
				    <xsl:choose>
				       <xsl:when test="$clas2='02'">
				           <option value="02" selected="selected"><xsl:value-of select="$lang.THosteleria"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="02"><xsl:value-of select="$lang.THosteleria"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='03'">
				           <option value="03" selected="selected"><xsl:value-of select="$lang.TConcurrencia"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="03"><xsl:value-of select="$lang.TConcurrencia"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='04'">
				           <option value="04" selected="selected"><xsl:value-of select="$lang.TTalleres"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="04"><xsl:value-of select="$lang.TTalleres"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='05'">
				           <option value="05" selected="selected"><xsl:value-of select="$lang.TGarajes"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="05"><xsl:value-of select="$lang.TGarajes"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='06'">
				           <option value="06" selected="selected"><xsl:value-of select="$lang.TCarnicerias"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="06"><xsl:value-of select="$lang.TCarnicerias"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='07'">
				           <option value="07" selected="selected"><xsl:value-of select="$lang.TClinicas"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="07"><xsl:value-of select="$lang.TClinicas"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='08'">
				           <option value="08" selected="selected"><xsl:value-of select="$lang.TVeterinarias"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="08"><xsl:value-of select="$lang.TVeterinarias"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='09'">
				           <option value="09" selected="selected"><xsl:value-of select="$lang.TTiendas"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="09"><xsl:value-of select="$lang.TTiendas"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='10'">
				           <option value="10" selected="selected"><xsl:value-of select="$lang.TAlmacenaje"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="10"><xsl:value-of select="$lang.TAlmacenaje"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='11'">
				           <option value="11" selected="selected"><xsl:value-of select="$lang.TPeluquerias"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="11"><xsl:value-of select="$lang.TPeluquerias"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
					<xsl:choose>
				       <xsl:when test="$clas2='99'">
				           <option value="99" selected="selected"><xsl:value-of select="$lang.TOtros"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="99"><xsl:value-of select="$lang.TOtros"/></option>
				       </xsl:otherwise>
				    </xsl:choose>
				</select>
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
					<ul><li><xsl:value-of select="$lang.fotocopiaDni"/></li></ul>
				</label>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D1</xsl:attribute>
					<xsl:attribute name="id">TRAM10D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.instancia"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D2</xsl:attribute>
					<xsl:attribute name="id">TRAM10D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.proyecto"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D3</xsl:attribute>
					<xsl:attribute name="id">TRAM10D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.croquis"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D4</xsl:attribute>
					<xsl:attribute name="id">TRAM10D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D4_Tipo</xsl:attribute>
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
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D5</xsl:attribute>
					<xsl:attribute name="id">TRAM10D5</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D5_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D5_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<ul><li><xsl:value-of select="$lang.arrendamiento"/></li></ul>
				</label>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:</b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">TRAM10D6</xsl:attribute>
					<xsl:attribute name="id">TRAM10D6</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM10D6_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM10D6_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/><br/>
			<div style="height:35px;vertical-align:bottom;margin:10px">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:600px;</xsl:attribute>
					<b><xsl:value-of select="$lang.nota"/></b>: <xsl:value-of select="$lang.aclaracion"/>
				</label>
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
			<div class="col" style="visibility: hidden">
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">relacionDescr</xsl:attribute>
					<xsl:attribute name="id">relacionDescr</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">clasificacionDescr</xsl:attribute>
					<xsl:attribute name="id">clasificacionDescr</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
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