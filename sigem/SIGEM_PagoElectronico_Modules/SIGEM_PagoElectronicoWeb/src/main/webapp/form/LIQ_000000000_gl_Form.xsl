<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="LIQ_000000000_Form_template.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
<xsl:variable name="lang.periodo" select="'GAL-Periodo de pago'"/>
<xsl:variable name="lang.emisora" select="'GAL-Emisora'"/>
<xsl:variable name="lang.modelo" select="'GAL-Modelo'"/>
<xsl:variable name="lang.referencia" select="'GAL-Referencia'"/>
<xsl:variable name="lang.identificacion" select="'GAL-Identificación'"/>
<xsl:variable name="lang.importe" select="'GAL-Importe'"/>
<xsl:variable name="lang.concepto" select="'GAL-Concepto'"/>
<xsl:variable name="lang.ayuntamiento" select="'GAL-Ayuntamiento'"/>
<xsl:variable name="lang.desc" select="'GAL-Descripción'"/>
<xsl:variable name="lang.nif" select="'GAL-N.I.F. / C.I.F'"/>
<xsl:variable name="lang.nombre" select="'GAL-Apellidos y Nombre / Razón Social'"/>
<xsl:variable name="lang.medio" select="'GAL-Medio Pago'"/>
<xsl:variable name="lang.medio1" select="'GAL-Cargo en cuenta'"/>
<xsl:variable name="lang.medio2" select="'GAL-Pago con tarjeta'"/>
<xsl:variable name="lang.entidadFinanciera" select="'GAL-Entidad Financiera'"/>
<xsl:variable name="lang.domiciliacion" select="'GAL-Domiciliación'"/>
<xsl:variable name="lang.domiciliacion01" select="'GAL-Domiciliar'"/>
<xsl:variable name="lang.domiciliacion02" select="'GAL-No Domiciliar'"/>
<xsl:variable name="lang.ccc" select="'GAL-Código Cuenta Cliente'"/>
<xsl:variable name="lang.numeroTarjeta" select="'GAL-Número Tarjeta Crédito'"/>
<xsl:variable name="lang.fechaTarjeta" select="'GAL-Fecha de Caducidad'"/>
<xsl:variable name="lang.mensaje03" select="'GAL-Formato incorrecto. '"/>
<xsl:variable name="lang.mensaje04" select="'GAL-debe ser un valor númerico. '"/>
<xsl:variable name="lang.mensaje08" select="'GAL-Debe introducir un número de cuenta corriente.'"/>
<xsl:variable name="lang.mensaje09" select="'GAL-El número de cuenta debe tener 20 dígitos.'"/>
<xsl:variable name="lang.mensaje10" select="'GAL-El número de tarjeta debe tener 16 dígitos.'"/>
<xsl:variable name="lang.mensaje11" select="'GAL-Debe introducir la fecha de caducidad de la tarjeta.'"/>
<xsl:variable name="lang.mensaje12" select="'GAL-La fecha de caducidad de la tarjeta debe tener 4 dígitos.'"/>
<xsl:variable name="lang.mensaje13" select="'GAL-Debe introducir un número tarjeta.'"/>
</xsl:stylesheet>