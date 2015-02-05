<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'GAL-Formulario de Solicitud de Reclamación, Quejas y Sugerencias'"/>
	<xsl:variable name="lang.datosSolicitante" select="'GAL-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'GAL-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'GAL-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'GAL-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'GAL-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'GAL-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'GAL-Provincia'"/>
	<xsl:variable name="lang.cp" select="'GAL-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'GAL-Datos de la Solicitud'"/>
	<xsl:variable name="lang.solicita" select="'GAL-Solicita'"/>
	<xsl:variable name="lang.expone" select="'GAL-Expone'"/>
	<xsl:variable name="lang.organoAsignado" select="'GAL-Órgano al que se dirige: Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.organoAlternativo" select="'GAL-Órgano alternativo'"/>
	<xsl:variable name="lang.servTramLicencias" select="'GAL-Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.servSecretaria" select="'GAL-Servicio de Secretaría'"/>
	<xsl:variable name="lang.anexar" select="'GAL-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'GAL-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'GAL-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'GAL-Documento'"/>
	<xsl:variable name="lang.envio" select="'GAL-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'GAL-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'GAL-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'GAL-Teléfono'"/>
</xsl:stylesheet>