<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Anexado de Documentos'"/>
	<xsl:variable name="lang.datosRegistro" select="'Datos del Registro'"/>
	<xsl:variable name="lang.expediente" select="'Número de expediente'"/>
	<xsl:variable name="lang.registro" select="'Número de registro'"/>
	<xsl:variable name="lang.fechaPresentacion" select="'Fecha de presentación'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.asunto" select="'Asunto'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero, pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.anexarInfo3" select="'3.- Si desea anexar más de 3 ficheros, comprimalos para anexar varios juntos.'"/>
	<xsl:variable name="lang.documento1" select="'Documento #1'"/>
	<xsl:variable name="lang.documento2" select="'Documento #2'"/>
	<xsl:variable name="lang.documento3" select="'Documento #3'"/>

	<xsl:template match="/">
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosRegistro"/></h1>
   		</div>
   		<div class="cuadro" style="">
	   		<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.asunto"/>:
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Asunto/Descripcion"/>
				</label>
				<br/>
			</div>
	   		<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.expediente"/>:
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Numero_Expediente"/>
				</label>
				<br/>
			</div>
			<xsl:variable name="numRegistroInicial" select="Solicitud_Registro/Datos_Registro/Numero_Registro"/>
			<xsl:if test="$numRegistroInicial != ''">
	   			<div class="col">
					<label class="gr" style="position: relative; width:150px;">
						<xsl:value-of select="$lang.registro"/>:
					</label>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
						<xsl:value-of select="Solicitud_Registro/Datos_Registro/Numero_Registro"/>
					</label>
					<br/>
				</div>
	   			<div class="col">
					<label class="gr" style="position: relative; width:150px;">
						<xsl:value-of select="$lang.fechaPresentacion"/>:
					</label>
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
						<xsl:value-of select="Solicitud_Registro/Datos_Registro/Hora_Presentacion"/>,
						<xsl:value-of select="Solicitud_Registro/Datos_Registro/Fecha_Presentacion"/>
					</label>
					<br/>
				</div>
			</xsl:if>
   			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.docIdentidad"/>:
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
				</label>
				<br/>
			</div>
   			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.nombre"/>:
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
				</label>
				<br/>
			</div>
   			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.email"/>:
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:500px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Correo_Electronico"/>
				</label>
				<br/>
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
		   			<xsl:value-of select="$lang.anexarInfo3"/><br/>
			</label>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.documento1"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">SUBSANACIOND1</xsl:attribute>
					<xsl:attribute name="id">SUBSANACIOND1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.documento2"/>:
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">SUBSANACIOND2</xsl:attribute>
					<xsl:attribute name="id">SUBSANACIOND2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.documento3"/>:
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">SUBSANACIOND3</xsl:attribute>
					<xsl:attribute name="id">SUBSANACIOND3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
   		</div>
	</xsl:template>
	<xsl:template name="transformaFecha">
		<xsl:param name="node"/>
		<xsl:variable name="date" select="concat(substring(string($node),9,2),'-',substring(string($node),6,2),'-',substring(string($node),1,4))"/>
		<xsl:value-of select="$date"/>
	</xsl:template>
</xsl:stylesheet>