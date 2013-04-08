<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:include href="../../actividad_solicitante_relacion_select_ca.xsl"/>
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'CAT-Formulario de solicitud de Licencia de Apertura de Actividad No Clasificada'"/>
	<xsl:variable name="lang.datosSolicitante" select="'CAT-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'CAT-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'CAT-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'CAT-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'CAT-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'CAT-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'CAT-Provincia'"/>
	<xsl:variable name="lang.cp" select="'CAT-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'CAT-Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'CAT-Actividad'"/>
	<xsl:variable name="lang.emplazamiento" select="'CAT-Emplazamiento'"/>
	<xsl:variable name="lang.anexar" select="'CAT-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'CAT-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'CAT-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'CAT-Fotocopia del DNI/Escritura de Constitución y C.I.F.'"/>
	<xsl:variable name="lang.instancia" select="'CAT-Solicitud de licencia de apertura de actividad sujeta al RAMINP: formulario/instancia municipal'"/>
	<xsl:variable name="lang.proyecto" select="'CAT-Proyecto técnico y Memoria valorada (certif. instalación extintores, etc).'"/>
	<xsl:variable name="lang.croquis" select="'CAT-Croquis o planos de planta (con el máximo detalle posible) y situación del local'"/>
	<xsl:variable name="lang.justificante" select="'CAT-Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.arrendamiento" select="'CAT-Contrato de arrendamiento o escritura de la propiedad'"/>

	<xsl:variable name="lang.envio" select="'CAT-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'CAT-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'CAT-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'CAT-Teléfono'"/>
	<xsl:variable name="lang.nota" select="'CAT-IMPORTANTE'"/>
	<xsl:variable name="lang.aclaracion" select="'CAT-Previo a conceder la Licencia de Apertura se deben haber obtenido previamente la Licencia de Instalación, la Licencia de Obra y el Informe Técnico pertinentes'"/>
	<xsl:variable name="lang.required" select="' CAT-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'CAT-Documento PDF'"/>
</xsl:stylesheet>