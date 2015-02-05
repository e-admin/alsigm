<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'GAL-Formulario de Solicitud de Certificado Urbanístico'"/>
	<xsl:variable name="lang.datosSolicitante" select="'GAL-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'GAL-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'GAL-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'GAL-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'GAL-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'GAL-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'GAL-Provincia'"/>
	<xsl:variable name="lang.cp" select="'GAL-Código postal'"/>
	<xsl:variable name="lang.telefono" select="'GAL-Teléfono'"/>
	<xsl:variable name="lang.datosSolicitud" select="'GAL-Datos de la Solicitud'"/>
	<xsl:variable name="lang.anexar" select="'GAL-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'GAL-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'GAL-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'GAL-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'GAL-D.E.U.'"/>
	<xsl:variable name="lang.documentoPDF" select="'GAL-Documento PDF'"/>
	<xsl:variable name="lang.documentoDOC" select="'GAL-Documento DOC'"/>
	
	<xsl:variable name="lang.representante" select="'GAL-Datos del Representante'"/>
	<xsl:variable name="lang.situacion" select="'GAL-Situacion de la Finca'"/>
	<xsl:variable name="lang.documento1" select="'GAL-Justificante de autoliquidacion de la tasa por emisión de certificados.'"/>
	<xsl:variable name="lang.documento2" select="'GAL-Plano de localización sobre cartografía oficial FIRMADO, en el que se refleje la posición exacta de la finca/parcela/edificio objeto de la solicitud.'"/>
	<xsl:variable name="lang.documento3" select="'GAL-Fotocopia del DNI del solicitante en el caso de personas físicas. En el caso de actuar en representación, fotocopia de los documentos de identidad del representante y del solicitante, con autorización escrita de este último o copia de poder para representarlo. En el caso de personas jurídicas, la persona que firma la solicitud adjuntará documentación acreditativa de su representación como puede ser una fotocopia pública de la escritura de constitución de la sociedad, poder, etc.'"/>
	<xsl:variable name="lang.documento4" select="'GAL-Acreditación de la condición de interesado (escrituras de propiedad,etc.).'"/>
	<xsl:variable name="lang.required" select="' GAL-Campos obligatorios'"/>
</xsl:stylesheet>