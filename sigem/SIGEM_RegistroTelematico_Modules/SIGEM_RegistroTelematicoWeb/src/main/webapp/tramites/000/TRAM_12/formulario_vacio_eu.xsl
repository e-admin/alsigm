<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de solicitud de Licencia de vado'"/>
	<xsl:variable name="lang.datosSolicitante" select="'EUS-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'EUS-Datos de la Solicitud'"/>
	<xsl:variable name="lang.ubicacion" select="'EUS-Ubicación'"/>
	<xsl:variable name="lang.tipoVado" select="'EUS-Tipo de vado'"/>
	<xsl:variable name="lang.TLaboral" select="'EUS-Laboral'"/>
	<xsl:variable name="lang.TPermanente" select="'EUS-Permanente'"/>
	<xsl:variable name="lang.actividad" select="'EUS-Actividad o uso del local'"/>
	<xsl:variable name="lang.numero" select="'EUS-Número de plazas para vehículos'"/>
	<xsl:variable name="lang.rebaje" select="'EUS-Rebaje'"/>
	<xsl:variable name="lang.TAceraAncha" select="'EUS-Acera ancha'"/>
	<xsl:variable name="lang.TAceraEstrecha" select="'EUS-Acera estrecha'"/>
	<xsl:variable name="lang.TMinusvalidos" select="'EUS-Minusválidos'"/>
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.licenciaVado" select="'EUS-Solicitud de licencia de vado'"/>
	<xsl:variable name="lang.licenciaObras" select="'EUS-Licencias de obras y apertura de los inmuebles a los que se accede'"/>
	<xsl:variable name="lang.planoSituacion" select="'EUS-Plano de situación a escala 1/200'"/>
	<xsl:variable name="lang.planoPlanta" select="'EUS-Plano de planta a escala 1/50'"/>
	<xsl:variable name="lang.justificante" select="'EUS-Justificante de reintegro de los derechos'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'EUS-Fotocopia del DNI'"/>
	
	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
</xsl:stylesheet>