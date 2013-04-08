<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosInteresado" select="'Datos del Interesado Principal'"/>
	<xsl:variable name="lang.datosParticipante" select="'Datos del Participante'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.nifCif" select="'NIF/CIF'"/>
	<xsl:variable name="lang.identidad" select="'Nombre'"/>
	<xsl:variable name="lang.relacion" select="'Relación'"/>
	<xsl:variable name="lang.TComprador" select="'Comprador'"/>
	<xsl:variable name="lang.TVendedor" select="'Vendedor'"/>
	<xsl:variable name="lang.TTitular" select="'Titular'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.denominacion" select="'Denominación'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'Actividad'"/>
	<xsl:variable name="lang.emplazamiento" select="'Emplazamiento'"/>
	<xsl:variable name="lang.fechaLicencia" select="'Fecha Licencia'"/>
	<xsl:variable name="lang.fechaAprobacion" select="'Fecha Aprobación'"/>
	<xsl:variable name="lang.fechaFormato" select="'Formato válido [DD/MM/AAAA]'" /> 
	<xsl:variable name="lang.tasas" select="'Tasas'" /> 
	<xsl:variable name="lang.euros" select="'&#8364;'" /> 
	<xsl:variable name="lang.organoSujeto_1" select="'Órgano/s o unidad/es municipal/es responsable/s del procedimiento'"/>
	<xsl:variable name="lang.organoResponsable" select="'Órgano Responsable'"/>
	<xsl:variable name="lang.gerenciasSubgerenciasCatastro" select="'Las Gerencias y Subgerencias del Catastro'"/>
	<xsl:variable name="lang.areaUrbanismo" select="'Área de Urbanismo'"/>	
	
	<xsl:variable name="lang.instanciaCambioTitular" select="'Instancia/solictud de cambio de titular, firmada por ambos (actual titular y adquiriente)'"/>
	<xsl:variable name="lang.fotocopiaLicencia" select="'Fotocopia de la licencia'"/>
	<xsl:variable name="lang.fotocopiaIAE" select="'Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.contratoArrendamiento" select="'Contrato de Arrendamiento'"/>

	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	
	<xsl:template match="/">
		
		<!-- DATOS DEL INTERESADO -->
	
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosInteresado"/></h1>
   		</div>
   		<br/>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.nifCif"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.identidad"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descr">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.relacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descr"/>
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
				<label class="gr" style="position: relative; width:400px">
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
		<!-- DATOS DEL PARTICIPANTE -->
		
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosParticipante"/></h1>
   		</div>
   		<br/>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.nifCif"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.identidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Participante_Descr">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.relacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Participante_Descr"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Participante">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		
		<xsl:variable name="emailPart" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Participante"/>		
		<xsl:choose>
        <xsl:when test="$emailPart=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Participante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		<br/>
		<!-- DATOS DE LA SOLICITUD -->
		
		<xsl:variable name="denom" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Denominacion"/>		
		<xsl:variable name="act" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Actividad"/>		
		<xsl:variable name="empl" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Emplazamiento"/>		
		<xsl:variable name="fchLic" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Licencia"/>		
		
		<xsl:choose>
        <xsl:when test="$denom='' and $act ='' and $empl='' and $fchLic=''"></xsl:when>
        <xsl:otherwise>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<br/>
   		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$denom=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.denominacion"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Denominacion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$act=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.actividad"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Actividad"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$empl=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:50px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.emplazamiento"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Emplazamiento"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		
		<xsl:choose>
        <xsl:when test="$fchLic=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.fechaLicencia"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Licencia"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		<!-- 
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Aprobacion">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.fechaAprobacion"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Aprobacion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tasas">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.tasas"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tasas"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		-->
		<!-- ORGANO RESPONSABLE -->
		<!-- 
		<label class="gr" style="position: relative; width:650px;font-weight:bold;text-decoration:underline;">
			<xsl:value-of select="$lang.organoSujeto_1"/>:<br/><br/>	
		</label>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Organo_Responsable_Descr">
			<div class="col">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.organoResponsable"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Organo_Responsable_Descr"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		 -->
		<br/>
	</xsl:template>
</xsl:stylesheet>