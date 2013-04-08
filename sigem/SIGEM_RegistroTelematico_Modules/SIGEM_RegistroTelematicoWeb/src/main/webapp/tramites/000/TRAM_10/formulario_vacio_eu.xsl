<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:include href="../../actividad_solicitante_relacion_select_eu.xsl"/>
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de solicitud de Licencia de Apertura de Actividad Clasificada (RAMINP)'"/>
	<xsl:variable name="lang.datosSolicitante" select="'EUS-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'EUS-Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'EUS-Actividad'"/>
	<xsl:variable name="lang.clasificacion" select="'EUS-Clasificación'"/>
	<xsl:variable name="lang.emplazamiento" select="'EUS-Emplazamiento'"/>
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.fotocopiaDni" select="'EUS-Fotocopia del DNI/Escritura de Constitución y C.I.F.'"/>
	<xsl:variable name="lang.instancia" select="'EUS-Solicitud de licencia de apertura de actividad sujeta al RAMINP: formulario/instancia municipal'"/>
	<xsl:variable name="lang.proyecto" select="'EUS-Proyecto técnico y Memoria valorada (certif. instalación extintores, etc).'"/>
	<xsl:variable name="lang.croquis" select="'EUS-Croquis o planos de planta (con el máximo detalle posible) y situación del local'"/>
	<xsl:variable name="lang.justificante" select="'EUS-Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.arrendamiento" select="'EUS-Contrato de arrendamiento o escritura de la propiedad'"/>

	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.nota" select="'EUS-IMPORTANTE'"/>
	<xsl:variable name="lang.aclaracion" select="'EUS-Previo a conceder la Licencia de Apertura se deben haber obtenido previamente la Licencia de Instalación, la Licencia de Obra y el Informe Técnico pertinentes'"/>

	<xsl:variable name="lang.TIndustriales" select="'EUS-Industriales'"/>
	<xsl:variable name="lang.THosteleria" select="'EUS-Hostelería (CÍBER incluídos)'"/>
	<xsl:variable name="lang.TConcurrencia" select="'EUS-Concurrencia pública'"/>
	<xsl:variable name="lang.TTalleres" select="'EUS-Talleres'"/>
	<xsl:variable name="lang.TGarajes" select="'EUS-Garajes o gasolineras'"/>
	<xsl:variable name="lang.TCarnicerias" select="'EUS-Carnicerías y pescaderías'"/>
	<xsl:variable name="lang.TClinicas" select="'EUS-Clínicas médicas'"/>
	<xsl:variable name="lang.TVeterinarias" select="'EUS-Veterinarias'"/>
	<xsl:variable name="lang.TTiendas" select="'EUS-Tiendas de animales'"/>
	<xsl:variable name="lang.TAlmacenaje" select="'EUS-Almacenaje de materiales peligrosos'"/>
	<xsl:variable name="lang.TPeluquerias" select="'EUS-Peluquerías con solarium '"/>
	<xsl:variable name="lang.TOtros" select="'EUS-Otros'"/>

	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
</xsl:stylesheet>