<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'Formulario de Solicitud de Tarjeta de Estacionamiento para Minusválidos.'"/>
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
	<xsl:variable name="lang.tipo" select="'Tipo de Tarjeta Solicitada'"/>
	<xsl:variable name="lang.datosRepresentante" select="'Conductor Autorizado'"/>
	
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
		
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<br/>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.tipo"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tipo_Tarjeta"/>
			</label>
			<br/>
		</div>
		<br/>
		<xsl:variable name="tipo" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tipo_Tarjeta"/>		
		<xsl:choose>
        <xsl:when test="$tipo='Tarjeta de Estacionamiento'">
  
		<xsl:variable name="nom" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Representante"/>
		<xsl:variable name="domRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Representante"/>
		<xsl:variable name="locRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Representante"/>
		<xsl:variable name="provRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Representante"/>
		<xsl:variable name="codRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Representante"/>
		<xsl:variable name="telRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Representante"/>
		<xsl:variable name="emailRel" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Representante"/>		
								
		
		
		<xsl:choose>
        <xsl:when test="$nom='' and $domRel='' and $locRel='' and $provRel='' and $codRel='' and $telRel='' and $emailRel=''"></xsl:when>
        <xsl:otherwise>
			<div class="submenu">
	   			<h1><xsl:value-of select="$lang.datosRepresentante"/></h1>
	   		</div>
	   		<br/>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Representante"/>
				</label>
				<br/>
			</div>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Representante"/>
				</label>
				<br/>
			</div>

			<xsl:choose>
			<xsl:when test="$domRel=''"></xsl:when>
			<xsl:otherwise>
				<div class="col">
					<label class="gr" style="position: relative; width:200px;text-indent:10px">
						<xsl:value-of select="$lang.domicilio"/>:	
					</label>
					<label class="gr" style="position: relative; width:400px;">
						<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Representante"/>
					</label>
					<br/>
				</div>
			</xsl:otherwise>
			</xsl:choose>

			<xsl:choose>
			<xsl:when test="$locRel=''"></xsl:when>
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
			<xsl:when test="$provRel=''"></xsl:when>
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
			<xsl:when test="$codRel=''"></xsl:when>
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

			<xsl:choose>
			<xsl:when test="$telRel=''"></xsl:when>
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
	        <xsl:when test="$emailRel=''"></xsl:when>
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

		</xsl:otherwise>
		</xsl:choose>
		</xsl:when>
		</xsl:choose>
		<br/>
	</xsl:template>
</xsl:stylesheet>