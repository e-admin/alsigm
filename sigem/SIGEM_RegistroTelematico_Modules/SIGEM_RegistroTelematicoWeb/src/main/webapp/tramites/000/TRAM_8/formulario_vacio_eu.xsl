<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de solicitud de Cambio de Titularidad'"/>
	<xsl:variable name="lang.datosInteresado" select="'EUS-Datos del Interesado Principal'"/>
	<xsl:variable name="lang.datosParticipante" select="'EUS-Datos del Participante'"/>
	<xsl:variable name="lang.nifCif" select="'EUS-NIF/CIF'"/>
	<xsl:variable name="lang.identidad" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.TComprador" select="'EUS-Comprador'"/>
	<xsl:variable name="lang.relacion" select="'EUS-Relación'"/>
	<xsl:variable name="lang.TVendedor" select="'EUS-Vendedor'"/>
	<xsl:variable name="lang.TTitular" select="'EUS-Titular'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.denominacion" select="'EUS-Denominación'"/>
	<xsl:variable name="lang.datosSolicitud" select="'EUS-Datos de la Solicitud'"/>
	<xsl:variable name="lang.actividad" select="'EUS-Actividad'"/>
	<xsl:variable name="lang.emplazamiento" select="'EUS-Emplazamiento'"/>
	<xsl:variable name="lang.fechaLicencia" select="'EUS-Fecha Licencia'"/>
	<xsl:variable name="lang.fechaAprobacion" select="'EUS-Fecha Aprobación'"/>
	<xsl:variable name="lang.fechaFormato" select="'EUS-Formato válido [DD/MM/AAAA]'" /> 
	<xsl:variable name="lang.tasas" select="'EUS-Tasas'" /> 
	<xsl:variable name="lang.euros" select="'EUS-&#8364;'" /> 
	<xsl:variable name="lang.organoSujeto_1" select="'EUS-Órgano/s o unidad/es municipal/es responsable/s del procedimiento'"/>
	<xsl:variable name="lang.organoResponsable" select="'EUS-Órgano Responsable'"/>
	<xsl:variable name="lang.gerenciasSubgerenciasCatastro" select="'EUS-Las Gerencias y Subgerencias del Catastro'"/>
	<xsl:variable name="lang.areaUrbanismo" select="'EUS-Área de Urbanismo'"/>	
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.instanciaCambioTitular" select="'EUS-Instancia/solictud de cambio de titular, firmada por ambos (actual titular y adquiriente)'"/>
	<xsl:variable name="lang.fotocopiaLicencia" select="'EUS-Fotocopia de la licencia'"/>
	<xsl:variable name="lang.fotocopiaIAE" select="'EUS-Justificante/fotocopia de alta en I.A.E.'"/>
	<xsl:variable name="lang.contratoArrendamiento" select="'EUS-Contrato de Arrendamiento'"/>
	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
</xsl:stylesheet>