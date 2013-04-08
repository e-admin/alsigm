<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Sol.licitud d´Ocupació del domini públic'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Dades del Sol.licitant'"/>
	<xsl:variable name="lang.docIdentidad" select="'DNI'"/>
	<xsl:variable name="lang.nombre" select="'Nom'"/>
	<xsl:variable name="lang.domicilio" select="'Domicili a efectes de notificació'"/>
	<xsl:variable name="lang.email" select="'Correu electrónic'"/>
	<xsl:variable name="lang.localidad" select="'Localitat'"/>
	<xsl:variable name="lang.provincia" select="'Província'"/>
	<xsl:variable name="lang.cp" select="'Codi postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Exposa: Que está interessat a ocupar temporal el domini públic, situat a:'"/>
	<xsl:variable name="lang.preferenciaa" select="'1º preferéncia Carrer'"/>
	<xsl:variable name="lang.preferenciab" select="'2º preferéncia Carrer'"/>
	<xsl:variable name="lang.preferenciac" select="'3º preferéncia Carrer'"/>
	<xsl:variable name="lang.metros" select="'Extensió a ocupar'"/>
	<xsl:variable name="lang.periodo" select="'Període'"/>	
	<xsl:variable name="lang.otros" select="'Altres'"/>
	<xsl:variable name="lang.ocupacion" select="'L´ocupació es realitzará: (marqueu segons escaigui)'"/>
	<xsl:variable name="lang.ventaambulante" select="'Venda ambulant'"/>
	<xsl:variable name="lang.indicaractividad" select="'Activitat de la venda ambulant'"/>
	<xsl:variable name="lang.atraccionferia" select="'Atracció de fira'"/>
	<xsl:variable name="lang.otrosa" select="'Altres'"/>
	<xsl:variable name="lang.indicarotrosa" select="'Indicar'"/>
	<xsl:variable name="lang.organoAsignado" select="'Órgan al qual s´adreça: Servei de Tramitació de Llicéncies'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgan alternatiu'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servei de Relacions amb el Ciutadá'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servei de Secretaria'"/>
	<xsl:variable name="lang.anexar" select="'Annexar fitxers: (si és activitat empresarial o comercial)'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Per adjuntar un fitxer (*. pdf, *. doc ,...), premi el botó examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccioneu el fitxer que voleu annexar a la sol licitud.'"/>
	<xsl:variable name="lang.documento1" select="'Nif del sol.licitant'"/>
	<xsl:variable name="lang.documento2" select="'Alta al I.A.E'"/>
	<xsl:variable name="lang.documento3" select="'Alta a la Seguretat Social'"/>
	<xsl:variable name="lang.documento4" select="'Carnet de Manipulador d´aliments (en cas de venda ambulant i/o vetna de productes d´aliments)'"/>
	<xsl:variable name="lang.envio" select="'Enviament de notificacions'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Demano l´enviament de notificacions per mitjans telemátics'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
</xsl:stylesheet>