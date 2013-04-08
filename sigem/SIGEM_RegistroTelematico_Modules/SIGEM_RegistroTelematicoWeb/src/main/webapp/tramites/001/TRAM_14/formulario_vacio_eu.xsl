<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de Solicitud de Tarjeta de Estacionamiento para Minusválidos.'"/>
	<xsl:variable name="lang.datosSolicitante" select="'EUS-Datos del Solicitante'"/>
	<xsl:variable name="lang.datosRepresentante" select="'EUS-Conductor Autorizado'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'EUS-Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>

	<xsl:variable name="lang.documentoPDF" select="'EUS-Documento PDF'"/>
	<xsl:variable name="lang.documentoJPG" select="'EUS-Documento Imagen'"/>
	<xsl:variable name="lang.documento1" select="'EUS-Una solicitud debidamente cumplimentada, en impreso normalizado que será facilitado en el departamento correspondiente (servicios sociales, circulación, vehículos y transportes, etc.) del ayuntamiento.'"/>
	<xsl:variable name="lang.documento2" select="'EUS-DNI del solicitante'"/>
	<xsl:variable name="lang.documento3" select="'EUS-Certificado de minusvalía expedido por el equipo de valoración de la Xunta de Galicia, o por los equipos autorizados en otras CC.AA.'"/>
	<xsl:variable name="lang.documento4" select="'EUS-Certificado de empadronamiento.'"/>
	<xsl:variable name="lang.documento5" select="'EUS-Fotografía tamaño carnet.'"/>
	<xsl:variable name="lang.documento6" select="'EUS-Permiso de conducir bien del minusválido, en el caso de que sea éste el conductor del vehículo o bien de la persona que lo transporte habitualmente.'"/>
	<xsl:variable name="lang.documento7" select="'EUS-DNI de la persona que lo represente(según caso).'"/>
	<xsl:variable name="lang.documento11" select="'EUS-Declaración jurada del conductor habitual del vehículo alegando dicha condición, en el supuesto de que no sea el propio minusválido. '"/>
	<xsl:variable name="lang.documento8" select="'EUS-Permiso de circulación del vehículo.'"/>
	<xsl:variable name="lang.documento9" select="'EUS-Justificante de pago del último recibo del impuesto municipal sobre vehículos de tracción mecánica.'"/>
	<xsl:variable name="lang.documento10" select="'EUS-En los casos de invalidez temporal: informe médico que acredite su problema de movilidad, su evolución y pronóstico, así como la necesidad de utilizar silla de ruedas, muletas, bastones o cualquier otra ayuda técnica para minusválidos.'"/>
	<xsl:variable name="lang.tipo" select="'EUS-Tipo de Tarjeta Solicitada'"/>
	<xsl:variable name="lang.tipo1" select="'EUS-Tarjeta de Estacionamiento'"/>
	<xsl:variable name="lang.tipo2" select="'EUS-Tarjeta de Accesibilidad'"/>
	<xsl:variable name="lang.solicita" select="'EUS-Solicita'"/>
	<xsl:variable name="lang.nota" select="'EUS-*NOTA: A rellenar en el caso de ser conductor autorizado (distinto del solicitante)'"/>	
	<xsl:variable name="lang.required" select="' EUS-Campos obligatorios'"/>
	<xsl:variable name="lang.datosSolicitud" select="'EUS-Datos de la Solicitud'"/>
</xsl:stylesheet>