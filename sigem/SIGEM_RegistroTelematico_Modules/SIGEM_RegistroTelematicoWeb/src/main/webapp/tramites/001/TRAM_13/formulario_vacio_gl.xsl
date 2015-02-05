<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:include href="../../actividad_solicitante_relacion_select_gl.xsl"/>
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'GAL-Formulario de solicitud de Licencia de Apertura de Actividad No Clasificada'"/>
	<xsl:variable name="lang.datosSolicitante" select="'GAL-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'GAL-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'GAL-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'GAL-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'GAL-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'GAL-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'GAL-Provincia'"/>
	<xsl:variable name="lang.cp" select="'GAL-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'GAL-Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'GAL-Actividad'"/>
	<xsl:variable name="lang.emplazamiento" select="'GAL-Emplazamiento'"/>
	<xsl:variable name="lang.anexar" select="'GAL-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'GAL-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'GAL-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'GAL-Fotocopia del DNI/Escritura de Constitución y C.I.F.'"/>
	<xsl:variable name="lang.instancia" select="'GAL-Solicitud de licencia de apertura de actividad sujeta al RAMINP: formulario/instancia municipal'"/>
	<xsl:variable name="lang.proyecto" select="'GAL-Proyecto técnico y Memoria valorada (certif. instalación extintores, etc).'"/>
	<xsl:variable name="lang.croquis" select="'GAL-Croquis o planos de planta (con el máximo detalle posible) y situación del local'"/>
	<xsl:variable name="lang.justificante" select="'GAL-Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.arrendamiento" select="'GAL-Contrato de arrendamiento o escritura de la propiedad'"/>

	<xsl:variable name="lang.envio" select="'GAL-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'GAL-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'GAL-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'GAL-Teléfono'"/>
	<xsl:variable name="lang.nota" select="'GAL-IMPORTANTE'"/>
	<xsl:variable name="lang.aclaracion" select="'GAL-Previo a conceder la Licencia de Apertura se deben haber obtenido previamente la Licencia de Instalación, la Licencia de Obra y el Informe Técnico pertinentes'"/>
	<xsl:variable name="lang.required" select="' GAL-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'GAL-Documento PDF'"/>
</xsl:stylesheet>