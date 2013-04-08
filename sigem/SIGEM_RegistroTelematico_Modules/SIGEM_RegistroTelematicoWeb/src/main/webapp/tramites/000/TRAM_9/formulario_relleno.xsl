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
	<xsl:variable name="lang.documento" select="'Documento Anexados'"/>
	<xsl:variable name="lang.representante" select="'Datos del Representante'"/>
	<xsl:variable name="lang.situacion" select="'Situacion de la Finca'"/>
	
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
        <xsl:when test="$email=''"></xsl:when>
        <xsl:otherwise>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.email"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>
			</label>
			<br/>
		</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<br/>
		<xsl:variable name="nameres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Representante"/>		
		<xsl:variable name="nifres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nif_Representante"/>		
		<xsl:variable name="mailres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Representante"/>		
		<xsl:variable name="telres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Representante"/>	
		<xsl:variable name="domres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Representante"/>		
		<xsl:variable name="locres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Representante"/>		
		<xsl:variable name="provres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Representante"/>		
		<xsl:variable name="cpres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Representante"/>		
			
		
		<xsl:choose>
        <xsl:when test="$nameres='' and $nifres='' and $mailres='' and $telres='' and $domres='' and $locres='' and $provres='' and $cpres=''"></xsl:when>
        <xsl:otherwise>
        	<div class="submenu">
	   			<h1><xsl:value-of select="$lang.representante"/></h1>
	   		</div>
        </xsl:otherwise>
        </xsl:choose>
        
		<xsl:choose>
        <xsl:when test="$nameres=''"></xsl:when>
        <xsl:otherwise>
        	
	   		<br/>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$nifres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nif_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		
		<xsl:choose>
        <xsl:when test="$mailres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$telres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$domres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$locres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$provres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Representante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
				
		<xsl:choose>
        <xsl:when test="$cpres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Representante"/>
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
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.situacion"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Situacion_Inmueble"/>
			</label>
			<br/>
		</div>
		<!--  -->
		<br/><br/>
	</xsl:template>
</xsl:stylesheet>