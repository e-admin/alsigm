<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_relleno.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosSolicitante" select="'Zergadunaren datuak'"/>
		<xsl:variable name="lang.nombreSolicitante" select="'izen-deiturak / izen-abizenak'"/>
		<xsl:variable name="lang.documentoIdentidadSolicitante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaSolicitante" select="'Bide etxekoa'"/>
		<xsl:variable name="lang.domicilioSolicitante" select="'Bizileku'"/>
		<xsl:variable name="lang.numeroSolicitante" select="'Zenbaki etxekoa'"/>
		<xsl:variable name="lang.cpSolicitante" select="'Posta kodea'"/>
		<xsl:variable name="lang.telefonoSolicitante" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadSolicitante" select="'Egoizterria'"/>
		<xsl:variable name="lang.provinciaSolicitante" select="'Herrialde'"/>
		<xsl:variable name="lang.plantaSolicitante" select="'Pisu'"/>
		<xsl:variable name="lang.escaleraSolicitante" select="'Eskailera'"/>
		<xsl:variable name="lang.puertaSolicitante" select="'Ate'"/>
		<xsl:variable name="lang.emailSolicitante" select="'E_mail'"/>

	<xsl:variable name="lang.datosRepresentante" select="'Zergadunaren datuak'"/>
		<xsl:variable name="lang.nombreRepresentante" select="'izen-deiturak / izen-abizenak'"/>
		<xsl:variable name="lang.documentoIdentidadRepresentante" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaRepresentante" select="'Bide etxekoa'"/>
		<xsl:variable name="lang.domicilioRepresentante" select="'Bizileku'"/>
		<xsl:variable name="lang.numeroRepresentante" select="'Zenbaki etxekoa'"/>
		<xsl:variable name="lang.cpRepresentante" select="'Posta kodea'"/>
		<xsl:variable name="lang.telefonoRepresentante" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadRepresentante" select="'Helburu(ko) herria'"/>
		<xsl:variable name="lang.provinciaRepresentante" select="'Herrialde'"/>
		<xsl:variable name="lang.plantaRepresentante" select="'Pisu'"/>
		<xsl:variable name="lang.escaleraRepresentante" select="'Eskailera'"/>
		<xsl:variable name="lang.puertaRepresentante" select="'Ate'"/>
		<xsl:variable name="lang.emailRepresentante" select="'E_mail'"/>

	<xsl:variable name="lang.datosANotificacion" select="'Datos de notificación'"/>
		<xsl:variable name="lang.nombreANotificacion" select="'izen-deiturak / izen-abizenak'"/>
		<xsl:variable name="lang.documentoIdentidadANotificacion" select="'D.N.I '"/>
		<xsl:variable name="lang.tipoviaANotificacion" select="'Bide etxekoa'"/>
		<xsl:variable name="lang.domicilioANotificacion" select="'Bizileku'"/>
		<xsl:variable name="lang.numeroANotificacion" select="'Zenbaki etxekoa'"/>
		<xsl:variable name="lang.cpANotificacion" select="'Posta kodea'"/>
		<xsl:variable name="lang.telefonoANotificacion" select="'Teléfono'"/>
		<xsl:variable name="lang.localidadANotificacion" select="'bidalketa-herria'"/>
		<xsl:variable name="lang.provinciaANotificacion" select="'Herrialde'"/>
		<xsl:variable name="lang.plantaANotificacion" select="'Pisu'"/>
		<xsl:variable name="lang.escaleraANotificacion" select="'Eskailera'"/>
		<xsl:variable name="lang.puertaANotificacion" select="'Ate'"/>
		<xsl:variable name="lang.emailANotificacion" select="'E_mail'"/>

</xsl:stylesheet>