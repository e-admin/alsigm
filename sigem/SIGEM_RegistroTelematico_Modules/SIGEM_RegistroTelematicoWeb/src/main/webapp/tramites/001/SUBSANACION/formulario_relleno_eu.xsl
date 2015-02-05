<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_relleno.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.motivo" select="'EUS-Motivo'"/>
	<xsl:variable name="lang.actoImpugnado" select="'EUS-Acto impugnado'"/>
	<xsl:variable name="lang.expone" select="'EUS-Expone'"/>
	<xsl:variable name="lang.organoAsignado" select="'EUS-Órgano al que se dirige: Servicio de Relaciones con el Ciudadano'"/>
</xsl:stylesheet>