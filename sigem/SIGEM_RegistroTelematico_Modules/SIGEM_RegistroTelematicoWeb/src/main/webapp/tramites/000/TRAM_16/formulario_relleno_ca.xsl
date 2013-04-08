<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_relleno.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosContratante" select="'CAT-Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'CAT-Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'CAT-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'CAT-Nombre'"/>
	<xsl:variable name="lang.relacion" select="'CAT-Relación'"/>
	<xsl:variable name="lang.domicilio" select="'CAT-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'CAT-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'CAT-Provincia'"/>
	<xsl:variable name="lang.cp" select="'CAT-Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'CAT-Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'CAT-Tipo de Contrato'"/>
	
	<xsl:variable name="lang.formaAdjudicacion" select="'CAT-Forma de Adjudicación'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'CAT-Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'CAT-Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'CAT-Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'CAT-Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'CAT-Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'CAT-Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'CAT-Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'CAT-Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'CAT-Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'CAT-Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.telefono" select="'CAT-Teléfono'"/>
	<xsl:variable name="lang.email" select="'CAT-Correo electrónico'"/>	
	<xsl:variable name="lang.unidades" select="'CAT-Unidades de Plazo'"/>
</xsl:stylesheet>