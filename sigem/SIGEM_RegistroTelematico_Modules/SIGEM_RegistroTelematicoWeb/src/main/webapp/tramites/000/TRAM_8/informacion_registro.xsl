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
	<xsl:variable name="lang.email" select="'Email'"/>
	<xsl:variable name="lang.numRegistro" select="'Número de registro'"/>
	<xsl:variable name="lang.fechaPresentacion" select="'Fecha de presentación'"/>
	<xsl:variable name="lang.fechaEfectiva" select="'Fecha efectiva'"/>
	<xsl:variable name="lang.asunto" select="'Asunto'"/>
	<xsl:variable name="lang.destino" select="'Destino'"/>
	
	<xsl:template match="/">
		<div class="col" xml:space="preserve">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.nifCif"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.identidad"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
			</label>
			<br/>
		</div>
		
		<xsl:variable name="email" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>		
		<xsl:choose>
        <xsl:when test="$email=''"></xsl:when>
        <xsl:otherwise>
			<div class="col">
				<label class="gr" style="position: relative; width:170px;">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:460px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.numRegistro"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Numero_Registro"/>
			</label>
			<input type="hidden" id="numeroRegistro" name="numeroRegistro">
				<xsl:attribute name="value"><xsl:value-of select="Solicitud_Registro/Datos_Registro/Numero_Registro"/></xsl:attribute>
			</input>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.fechaPresentacion"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:460px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Hora_Presentacion"/>, 
				<xsl:call-template name="transformaFecha">
				   <xsl:with-param name="node" select="Solicitud_Registro/Datos_Registro/Fecha_Presentacion"/>
				</xsl:call-template>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.fechaEfectiva"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:460px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Hora_Efectiva"/>, 
				<xsl:call-template name="transformaFecha">
				   <xsl:with-param name="node" select="Solicitud_Registro/Datos_Registro/Fecha_Efectiva"/>
				</xsl:call-template>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.destino"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/descr_organo"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.asunto"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Asunto/Descripcion"/>
			</label>
			<br/>
		</div><br/>
	</xsl:template>
	<xsl:template name="transformaFecha">
		<xsl:param name="node"/>
		<xsl:variable name="date" select="concat(substring(string($node),9,2),'-',substring(string($node),6,2),'-',substring(string($node),1,4))"/>
		<xsl:value-of select="$date"/>
	</xsl:template>	
</xsl:stylesheet>