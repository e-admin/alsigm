<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_relleno.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosSolicitante" select="'Dades del Sol.licitant'"/>
		<xsl:variable name="lang.nombreSolicitante" select="'Nom'"/>
		<xsl:variable name="lang.documentoIdentidadSolicitante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaSolicitante" select="'Via del domicili'"/>
		<xsl:variable name="lang.domicilioSolicitante" select="'Domicili'"/>
		<xsl:variable name="lang.numeroSolicitante" select="'Nº del domicili'"/>
		<xsl:variable name="lang.cpSolicitante" select="'Codi postal'"/>
		<xsl:variable name="lang.telefonoSolicitante" select="'Teléfon'"/>
		<xsl:variable name="lang.localidadSolicitante" select="'Localitat'"/>
		<xsl:variable name="lang.provinciaSolicitante" select="'Provincia'"/>
		<xsl:variable name="lang.plantaSolicitante" select="'Planta'"/>
		<xsl:variable name="lang.escaleraSolicitante" select="'Escala'"/>
		<xsl:variable name="lang.puertaSolicitante" select="'Porta'"/>
		<xsl:variable name="lang.emailSolicitante" select="'E_mail'"/>

	<xsl:variable name="lang.datosRepresentante" select="'Datos del Representante'"/>
		<xsl:variable name="lang.nombreRepresentante" select="'Nom'"/>
		<xsl:variable name="lang.documentoIdentidadRepresentante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaRepresentante" select="'Via del domicili'"/>
		<xsl:variable name="lang.domicilioRepresentante" select="'Domicili'"/>
		<xsl:variable name="lang.numeroRepresentante" select="'Nº del domicili'"/>
		<xsl:variable name="lang.cpRepresentante" select="'Código postal'"/>
		<xsl:variable name="lang.telefonoRepresentante" select="'Teléfon'"/>
		<xsl:variable name="lang.localidadRepresentante" select="'Localitat'"/>
		<xsl:variable name="lang.provinciaRepresentante" select="'Provincia'"/>
		<xsl:variable name="lang.plantaRepresentante" select="'Planta'"/>
		<xsl:variable name="lang.escaleraRepresentante" select="'Escala'"/>
		<xsl:variable name="lang.puertaRepresentante" select="'Porta'"/>
		<xsl:variable name="lang.emailRepresentante" select="'E_mail'"/>

	<xsl:variable name="lang.datosANotificacion" select="'Datos de notificación'"/>
		<xsl:variable name="lang.nombreANotificacion" select="'Nom'"/>
		<xsl:variable name="lang.documentoIdentidadANotificacion" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaANotificacion" select="'Via del domicili'"/>
		<xsl:variable name="lang.domicilioANotificacion" select="'Domicili"/>
		<xsl:variable name="lang.numeroANotificacion" select="'Nº del domicili'"/>
		<xsl:variable name="lang.cpANotificacion" select="'Codi postal'"/>
		<xsl:variable name="lang.telefonoANotificacion" select="'Teléfon"/>
		<xsl:variable name="lang.localidadANotificacion" select="'Localitat'"/>
		<xsl:variable name="lang.provinciaANotificacion" select="'Provincia'"/>
		<xsl:variable name="lang.plantaANotificacion" select="'Planta'"/>
		<xsl:variable name="lang.escaleraANotificacion" select="'Escala'"/>
		<xsl:variable name="lang.puertaANotificacion" select="'Porta'"/>
		<xsl:variable name="lang.emailANotificacion" select="'E_mail'"/>
</xsl:stylesheet>