<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Solicitude de ocupación de público'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Detalles do candidato'"/>
	<xsl:variable name="lang.docIdentidad" select="'DNI'"/>
	<xsl:variable name="lang.nombre" select="'Nome'"/>
	<xsl:variable name="lang.domicilio" select="'Dirección para notificación'"/>
	<xsl:variable name="lang.email" select="'E-mail'"/>
	<xsl:variable name="lang.localidad" select="'Local'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código Postal'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Exposé: Quen está interesado en ocupar temporalmente o dominio público, localizada en:'"/>
	<xsl:variable name="lang.preferenciaa" select="'1º preferencia Calle'"/>
	<xsl:variable name="lang.preferenciab" select="'2º preferencia Calle'"/>
	<xsl:variable name="lang.preferenciac" select="'3º preferencia Calle'"/>
	<xsl:variable name="lang.metros" select="'Extensión para ocupar'"/>
	<xsl:variable name="lang.periodo" select="'Período'"/>	
	<xsl:variable name="lang.otros" select="'Outro'"/>
	<xsl:variable name="lang.ocupacion" select="'A ocupación produciuse: (indicar)'"/>
	<xsl:variable name="lang.ventaambulante" select="'Fútil'"/>
	<xsl:variable name="lang.indicaractividad" select="'actividade comercial'"/>
	<xsl:variable name="lang.atraccionferia" select="'Atracción de feria'"/>
	<xsl:variable name="lang.otrosa" select="'Outro'"/>
	<xsl:variable name="lang.indicarotrosa" select="'Vendo'"/>
	<xsl:variable name="lang.organoAsignado" select="'Órgano ao que el se dirixe: Licenza de Servizo de Procesamento'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Serras'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Relacións co cidadán'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servizo de Secretaría'"/>
	<xsl:variable name="lang.anexar" select="'Adxuntar arquivos: (se profesional ou comercial)'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para agregar un arquivo (*. pdf, *. doc ,...), prema no botón Buscar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione o arquivo que desexa engadir ao seu pedido.'"/>
	<xsl:variable name="lang.documento1" select="'Recorrente NIF'"/>
	<xsl:variable name="lang.documento2" select="'Alta en el I.A.E'"/>
	<xsl:variable name="lang.documento3" select="'Alta en la Seguridad Social'"/>
	<xsl:variable name="lang.documento4" select="'Manipulador de tarxetas de alimentos (no caso de venda e/ou venda efectiva de produtos alimentarios'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificacións'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
</xsl:stylesheet>