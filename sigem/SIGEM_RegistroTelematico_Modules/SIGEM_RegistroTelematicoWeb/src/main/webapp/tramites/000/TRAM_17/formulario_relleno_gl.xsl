<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_relleno.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.datosSolicitante" select="'Recorrente'"/>
		<xsl:variable name="lang.nombreSolicitante" select="'Nome'"/>
		<xsl:variable name="lang.documentoIdentidadSolicitante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaSolicitante" select="'Cara á casa'"/>
		<xsl:variable name="lang.domicilioSolicitante" select="'Enderezo'"/>
		<xsl:variable name="lang.numeroSolicitante" select="'Nº de rexistro'"/>
		<xsl:variable name="lang.cpSolicitante" select="'Código postal'"/>
		<xsl:variable name="lang.telefonoSolicitante" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadSolicitante" select="'Local'"/>
		<xsl:variable name="lang.provinciaSolicitante" select="'Provincia'"/>
		<xsl:variable name="lang.plantaSolicitante" select="'Planta'"/>
		<xsl:variable name="lang.escaleraSolicitante" select="'Reto'"/>
		<xsl:variable name="lang.puertaSolicitante" select="'Porta'"/>
		<xsl:variable name="lang.emailSolicitante" select="'E_mail'"/>

	<xsl:variable name="lang.datosRepresentante" select="'Datos del Representante'"/>
		<xsl:variable name="lang.nombreRepresentante" select="'Nome'"/>
		<xsl:variable name="lang.documentoIdentidadRepresentante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaRepresentante" select="'Cara á casa'"/>
		<xsl:variable name="lang.domicilioRepresentante" select="'Enderezo'"/>
		<xsl:variable name="lang.numeroRepresentante" select="'Nº de rexistro'"/>
		<xsl:variable name="lang.cpRepresentante" select="'Código postal'"/>
		<xsl:variable name="lang.telefonoRepresentante" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadRepresentante" select="'Local'"/>
		<xsl:variable name="lang.provinciaRepresentante" select="'Provincia'"/>
		<xsl:variable name="lang.plantaRepresentante" select="'Planta'"/>
		<xsl:variable name="lang.escaleraRepresentante" select="'Reto'"/>
		<xsl:variable name="lang.puertaRepresentante" select="'Porta'"/>
		<xsl:variable name="lang.emailRepresentante" select="'E_mail'"/>

	<xsl:variable name="lang.datosANotificacion" select="'Datos de notificación'"/>
		<xsl:variable name="lang.nombreANotificacion" select="'Nome'"/>
		<xsl:variable name="lang.documentoIdentidadANotificacion" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaANotificacion" select="'Cara á casa'"/>
		<xsl:variable name="lang.domicilioANotificacion" select="'Enderezo'"/>
		<xsl:variable name="lang.numeroANotificacion" select="'Nº de rexistro'"/>
		<xsl:variable name="lang.cpANotificacion" select="'Código postal'"/>
		<xsl:variable name="lang.telefonoANotificacion" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadANotificacion" select="'Local'"/>
		<xsl:variable name="lang.provinciaANotificacion" select="'Provincia'"/>
		<xsl:variable name="lang.plantaANotificacion" select="'Planta'"/>
		<xsl:variable name="lang.escaleraANotificacion" select="'Reto'"/>
		<xsl:variable name="lang.puertaANotificacion" select="'Porta"/>
		<xsl:variable name="lang.emailANotificacion" select="'E_mail'"/>

</xsl:stylesheet>