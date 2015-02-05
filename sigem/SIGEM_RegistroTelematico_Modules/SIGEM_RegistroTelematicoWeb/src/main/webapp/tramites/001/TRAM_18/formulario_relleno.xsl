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
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
		<xsl:variable name="lang.representante" select="'Datos del Representante'"/>
		<xsl:variable name="lang.expone" select="'Expone: Necesita obtener información urbanística de:'"/>
			<xsl:variable name="lang.parcelaRustica" select="'Parcela Rústica'"/>
				<xsl:variable name="lang.numeroParcela" select="'Número de la parcela rústica'"/>			
				<xsl:variable name="lang.poligono" select="'Polígono'"/>
			<xsl:variable name="lang.parcelaUrbana" select="'Parcela Urbana'"/>
				<xsl:variable name="lang.sitoParcela" select="'Situación de la parcela'"/>
				<xsl:variable name="lang.refCatastral" select="'Referencia catastral'"/>
				<xsl:variable name="lang.otros" select="'Otro tipo de parcela'"/>
					<xsl:variable name="lang.indicar" select="'Indique'"/>
	<xsl:variable name="lang.motivos" select="'Indique los motivos de la solicitud'"/>
		<xsl:variable name="lang.motivoSolicitud" select="'Indique los motivos de la solicitud'"/>
	
	
	<xsl:template match="/">
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitante"/></h1>
   		</div>
   		<br/>
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
			<label class="gr" style="position: relative; width:500px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono">
			<div class="col" style="height: 35px;">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion">
			<div class="col" style="height: 35px;">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<div class="col">
			<label class="gr" style="position: relative; width:150px;">
				<xsl:value-of select="$lang.email"/>:	
			</label>
			<label class="gr" style="position: relative; width:500px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Correo_Electronico"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia"/>
				</label>
				<br/>
			</div>
		</xsl:if> 
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		
		
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
		
		
		
		
		
		<xsl:variable name="parcela" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Numero_parcela"/>		
		<xsl:variable name="catastral" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Ref_catastral"/>		
		<xsl:variable name="otroTipo" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Otro_tipo_parcela"/>
		
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<br/>
   		<xsl:choose>
        <xsl:when test="$parcela=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.parcelaRustica"/>:	
				</label>
				<br/>
			</div>
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.numeroParcela"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Numero_parcela"/>
				</label>
				<br/>
			</div>
			<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Poligono">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.poligono"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Poligono"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		</xsl:otherwise>
		</xsl:choose>

		
		<xsl:choose>
        <xsl:when test="$catastral=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.parcelaUrbana"/>:	
				</label>
				<br/>
			</div>
			<div class="col">
			
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.sitoParcela"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Sitio_parcela"/>
				</label>
				<br/>
			</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Ref_catastral">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.refCatastral"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Ref_catastral"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$otroTipo=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.otros"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Otro_tipo_parcela"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Motivo_solicitud">
			<div class="col">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.motivoSolicitud"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Motivo_solicitud"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<br/>
	</xsl:template>
</xsl:stylesheet>