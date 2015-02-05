<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de Reclamación económico-administrativa de Tributos'"/>
	<xsl:variable name="lang.datosSolicitante" select="'EUS-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.datosReclamacion" select="'EUS-Datos de la Reclamación'"/>
	<xsl:variable name="lang.descripcion" select="'EUS-Descripción'"/>
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.escrito" select="'EUS-Escrito de Interposición (solicitud)'"/>
	<xsl:variable name="lang.justificante" select="'EUS-Documentos justificantes del motivo de la reclamación (recibo duplicado, cambio de titularidad, datos erróneos, etc.)'"/>
	
	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
</xsl:stylesheet>