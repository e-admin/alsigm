<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'GAL-Formulario de Expediente Sancionador'"/>
	<xsl:variable name="lang.docIdentidad" select="'GAL-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'GAL-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'GAL-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'GAL-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'GAL-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'GAL-Provincia'"/>
	<xsl:variable name="lang.cp" select="'GAL-Código postal'"/>
	<xsl:variable name="lang.telefono" select="'GAL-Teléfono'"/>
	<xsl:variable name="lang.envio" select="'GAL-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'GAL-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'GAL-D.E.U.'"/>
	<xsl:variable name="lang.datosDenunciante" select="'GAL-Datos del Denunciante'"/>
	<xsl:variable name="lang.datosDenunciado" select="'GAL-Datos del Denunciado'"/>
	<xsl:variable name="lang.datosDenunciados" select="'GAL-Hechos Denunciados'"/>
	<xsl:variable name="lang.domicilioDenunciado" select="'GAL-Domicilio'"/>
	<xsl:variable name="lang.required" select="' GAL-Campos obligatorios'"/>
	<xsl:variable name="lang.datosSolicitud" select="'GAL-Datos de la Solicitud'"/>
</xsl:stylesheet>