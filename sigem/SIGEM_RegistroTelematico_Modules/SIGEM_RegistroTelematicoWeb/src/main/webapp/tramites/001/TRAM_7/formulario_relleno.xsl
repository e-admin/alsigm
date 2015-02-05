<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de Expediente Sancionador'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.datosDenunciante" select="'Datos del Denunciante'"/>
	<xsl:variable name="lang.datosDenunciado" select="'Datos del Denunciado'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.datosDenunciados" select="'Hechos Denunciados'"/>
	<xsl:variable name="lang.domicilioDenunciado" select="'Domicilio'"/>
	
	<xsl:template match="/">
		<div class="submenu">
			<h1><xsl:value-of select="$lang.datosDenunciante"/></h1>
		</div>
		<br/>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.docIdentidad"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Denunciante"/>
			</label>
			<br/>
		</div>
 
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.nombre"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Denunciante"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Denunciante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Denunciante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:variable name="email" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Correo_Electronico_Denunciante"/>		
		<xsl:choose>
        <xsl:when test="$email=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Correo_Electronico_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Denunciante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Denunciante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Denunciante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Denunciante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<br/>
		<div class="submenu">
			<h1><xsl:value-of select="$lang.datosDenunciado"/></h1>
		</div>
		<br/>
		<xsl:variable name="NIF" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/NIF_DEN"/>		
		<xsl:choose>
        <xsl:when test="$NIF=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/NIF_DEN"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:variable name="name" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_DEN"/>		
		<xsl:choose>
        <xsl:when test="$name=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_DEN"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
 
		<xsl:variable name="tlf" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono"/>		
		<xsl:choose>
        <xsl:when test="$tlf=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:variable name="casa" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion"/>		
		<xsl:choose>
        <xsl:when test="$casa=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilioDenunciado"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>

		<xsl:variable name="email2" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Correo_Electronico_Solicitante"/>		
		<xsl:choose>
        <xsl:when test="$email2=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Correo_Electronico_Solicitante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:variable name="localidad" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad"/>		
		<xsl:choose>
        <xsl:when test="$localidad=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>

		<xsl:variable name="provincia" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia"/>		
		<xsl:choose>
        <xsl:when test="$provincia=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>

		<xsl:variable name="CP" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal"/>		
		<xsl:choose>
        <xsl:when test="$CP=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>

		<br/>
		<div class="submenu">
			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
		</div>
		<br/>
		
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Hechos_Denunciados">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.datosDenunciados"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Hechos_Denunciados"/>
				</label>
				<br/>
				<br/>
			</div>
		</xsl:if>

		<br/>
	</xsl:template>
</xsl:stylesheet>