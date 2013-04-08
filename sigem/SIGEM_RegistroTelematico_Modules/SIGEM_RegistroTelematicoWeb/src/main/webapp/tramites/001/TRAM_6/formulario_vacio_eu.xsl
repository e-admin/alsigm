<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Solicitud de Ocupación del Dominio Público'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Expone: Que está interesado en ocupar temporal el dominio público, sito en:'"/>
	<xsl:variable name="lang.preferenciaa" select="'1º preferencia Calle'"/>
	<xsl:variable name="lang.preferenciab" select="'2º preferencia Calle'"/>
	<xsl:variable name="lang.preferenciac" select="'3º preferencia Calle'"/>
	<xsl:variable name="lang.metros" select="'Extensión a ocupar'"/>
	<xsl:variable name="lang.periodo" select="'Periodo'"/>	
	<xsl:variable name="lang.otros" select="'Otros'"/>
	<xsl:variable name="lang.ocupacion" select="'La ocupación se realizara: (marque según proceda)'"/>
	<xsl:variable name="lang.ventaambulante" select="'Venta ambulante'"/>
	<xsl:variable name="lang.indicaractividad" select="'Actividad de la venta ambulante'"/>
	<xsl:variable name="lang.atraccionferia" select="'Atracción de feria'"/>
	<xsl:variable name="lang.otrosa" select="'Otros'"/>
	<xsl:variable name="lang.indicarotrosa" select="'Indicar'"/>
	<xsl:variable name="lang.organoAsignado" select="'Órgano al que se dirige: Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servicio de Secretaría'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros: (si es actividad empresarial o comercial)'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf, *.doc,...), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'Nif del solicitante'"/>
	<xsl:variable name="lang.documento2" select="'Alta en el I.A.E'"/>
	<xsl:variable name="lang.documento3" select="'Alta en la Seguridad Social'"/>
	<xsl:variable name="lang.documento4" select="'Carné de Manipulador de alimentos (en caso de venta ambulante y/o vetna de productos de alimentos'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>

</xsl:stylesheet>