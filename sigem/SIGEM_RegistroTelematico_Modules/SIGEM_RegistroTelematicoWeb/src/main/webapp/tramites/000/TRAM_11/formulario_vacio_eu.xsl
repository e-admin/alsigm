<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de Solicitud de Certificado Urbanístico'"/>
	<xsl:variable name="lang.datosSolicitante" select="'EUS-Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.datosSolicitud" select="'EUS-Datos de la Solicitud'"/>
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
	<xsl:variable name="lang.documentoDOC" select="'EUS-Documento DOC'"/>
	
	<xsl:variable name="lang.representante" select="'EUS-Datos del Representante'"/>
	<xsl:variable name="lang.situacion" select="'EUS-Situacion de la Finca'"/>
	<xsl:variable name="lang.documento1" select="'EUS-Justificante de autoliquidacion de la tasa por emisión de certificados.'"/>
	<xsl:variable name="lang.documento2" select="'EUS-Plano de localización sobre cartografía oficial FIRMADO, en el que se refleje la posición exacta de la finca/parcela/edificio objeto de la solicitud.'"/>
	<xsl:variable name="lang.documento3" select="'EUS-Fotocopia del DNI del solicitante en el caso de personas físicas. En el caso de actuar en representación, fotocopia de los documentos de identidad del representante y del solicitante, con autorización escrita de este último o copia de poder para representarlo. En el caso de personas jurídicas, la persona que firma la solicitud adjuntará documentación acreditativa de su representación como puede ser una fotocopia pública de la escritura de constitución de la sociedad, poder, etc.'"/>
	<xsl:variable name="lang.documento4" select="'EUS-Acreditación de la condición de interesado (escrituras de propiedad,etc.).'"/>
	<xsl:variable name="lang.documento5" select="'EUS-Plano de situación de la parcela a escala 1:1000.'"/>
	<xsl:variable name="lang.documento6" select="'EUS-Documentación gráfica de la edificación (planta y secciones transversales).'"/>
	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
</xsl:stylesheet>