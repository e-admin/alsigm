<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'CAT-Formulario de solicitud de Licencia de vado'"/>
	<xsl:variable name="lang.datosSolicitante" select="'CAT-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'CAT-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'CAT-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'CAT-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'CAT-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'CAT-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'CAT-Provincia'"/>
	<xsl:variable name="lang.cp" select="'CAT-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'CAT-Datos de la Solicitud'"/>
	<xsl:variable name="lang.ubicacion" select="'CAT-Ubicación'"/>
	<xsl:variable name="lang.tipoVado" select="'CAT-Tipo de vado'"/>
	<xsl:variable name="lang.TLaboral" select="'CAT-Laboral'"/>
	<xsl:variable name="lang.TPermanente" select="'CAT-Permanente'"/>
	<xsl:variable name="lang.actividad" select="'CAT-Actividad o uso del local'"/>
	<xsl:variable name="lang.numero" select="'CAT-Número de plazas para vehículos'"/>
	<xsl:variable name="lang.rebaje" select="'CAT-Rebaje'"/>
	<xsl:variable name="lang.TAceraAncha" select="'CAT-Acera ancha'"/>
	<xsl:variable name="lang.TAceraEstrecha" select="'CAT-Acera estrecha'"/>
	<xsl:variable name="lang.TMinusvalidos" select="'CAT-Minusválidos'"/>
	<xsl:variable name="lang.anexar" select="'CAT-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'CAT-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'CAT-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.licenciaVado" select="'CAT-Solicitud de licencia de vado'"/>
	<xsl:variable name="lang.licenciaObras" select="'CAT-Licencias de obras y apertura de los inmuebles a los que se accede'"/>
	<xsl:variable name="lang.planoSituacion" select="'CAT-Plano de situación a escala 1/200'"/>
	<xsl:variable name="lang.planoPlanta" select="'CAT-Plano de planta a escala 1/50'"/>
	<xsl:variable name="lang.justificante" select="'CAT-Justificante de reintegro de los derechos'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'CAT-Fotocopia del DNI'"/>
	
	<xsl:variable name="lang.envio" select="'CAT-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'CAT-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'CAT-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'CAT-Teléfono'"/>
	<xsl:variable name="lang.required" select="' CAT-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'CAT-Documento PDF'"/>
</xsl:stylesheet>