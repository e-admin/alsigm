<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'CAT-Formulario de Solicitud de Reclamación, Quejas y Sugerencias'"/>
	<xsl:variable name="lang.datosSolicitante" select="'CAT-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'CAT-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'CAT-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'CAT-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'CAT-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'CAT-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'CAT-Provincia'"/>
	<xsl:variable name="lang.cp" select="'CAT-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'CAT-Datos de la Solicitud'"/>
	<xsl:variable name="lang.tipoSubvencion" select="'CAT-Tipo de Subvención'"/>
	<xsl:variable name="lang.TSinvestigaciones" select="'CAT-Investigaciones'"/>
	<xsl:variable name="lang.TSinnovacionTecnologica" select="'CAT-Innovación Tecnológica'"/>
	<xsl:variable name="lang.TSactividadPromocional" select="'CAT-Actidad Promocional'"/>
	<xsl:variable name="lang.TSobrasMenores" select="'CAT-Obras Menores'"/>
	<xsl:variable name="lang.resumenProyecto" select="'CAT-Resumen del Proyecto'"/>
	<xsl:variable name="lang.organoAsignado" select="'CAT-Órgano al que se dirige: Servicio de Secretaría'"/>
	<xsl:variable name="lang.organoAlternativo" select="'CAT-Órgano alternativo'"/>
	<xsl:variable name="lang.servTramLicencias" select="'CAT-Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'CAT-Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.anexar" select="'CAT-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'CAT-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'CAT-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'CAT-Documento'"/>
	<xsl:variable name="lang.envio" select="'CAT-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'CAT-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'CAT-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'CAT-Teléfono'"/>
</xsl:stylesheet>