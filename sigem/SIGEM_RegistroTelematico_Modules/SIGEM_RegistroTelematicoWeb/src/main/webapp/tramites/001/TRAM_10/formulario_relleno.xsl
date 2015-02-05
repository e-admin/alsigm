<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.relacion" select="'Relacion'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'Actividad'"/>
	<xsl:variable name="lang.clasificacion" select="'Clasificación'"/>
	<xsl:variable name="lang.emplazamiento" select="'Emplazamiento'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	
	<xsl:template match="/">
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitante"/></h1>
   		</div>
   		<br/>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.docIdentidad"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.nombre"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descripcion">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.relacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descripcion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		
		<xsl:variable name="email" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>		
		<xsl:choose>
		<xsl:when test="$email!=''">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px;">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>
				</label>
				<br/>
			</div>
		</xsl:when>
		</xsl:choose>
		<br/>
		
		<xsl:variable name="act" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Actividad"/>		
		<xsl:variable name="clas" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Clasificacion_Descripcion"/>		
		<xsl:variable name="empl" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Emplazamiento"/>		
		
		<xsl:choose>
		<xsl:when test="$act!='' or $clas!='' or $empl!='' ">
			<div class="submenu">
   				<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   			</div>
	   		<br/>
   		</xsl:when>
   		</xsl:choose>
   		
   		<xsl:choose>
        <xsl:when test="$act!=''">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.actividad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Actividad"/>
				</label>
				<br/>
			</div>
		</xsl:when>
		</xsl:choose>
		
		<xsl:choose>
		<xsl:when test="$clas!=''">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.clasificacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Clasificacion_Descripcion"/>
				</label>
				<br/>
			</div>
		</xsl:when>
		</xsl:choose>
		
		<xsl:choose>
		<xsl:when test="$empl!=''">
			<div class="col" style="height:35px;">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.emplazamiento"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Emplazamiento"/>
				</label>
			</div>
		</xsl:when>
		</xsl:choose>
		<br/>
	</xsl:template>
</xsl:stylesheet>