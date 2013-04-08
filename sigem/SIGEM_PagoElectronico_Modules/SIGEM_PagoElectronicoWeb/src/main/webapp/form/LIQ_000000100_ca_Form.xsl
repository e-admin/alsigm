<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="LIQ_000000100_Form_template.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
<xsl:variable name="lang.periodo" select="'CAT-Periodo de pago'"/>
<xsl:variable name="lang.emisora" select="'CAT-Emisora'"/>
<xsl:variable name="lang.modelo" select="'CAT-Modelo'"/>
<xsl:variable name="lang.referencia" select="'CAT-Referencia'"/>
<xsl:variable name="lang.identificacion" select="'CAT-Identificación'"/>
<xsl:variable name="lang.importe" select="'CAT-Importe'"/>
<xsl:variable name="lang.concepto" select="'CAT-Concepto'"/>
<xsl:variable name="lang.ayuntamiento" select="'CAT-Ayuntamiento'"/>
<xsl:variable name="lang.desc" select="'CAT-Descripción'"/>
<xsl:variable name="lang.nif" select="'CAT-N.I.F. / C.I.F'"/>
<xsl:variable name="lang.nombre" select="'CAT-Apellidos y Nombre / Razón Social'"/>
<xsl:variable name="lang.medio" select="'CAT-Medio Pago'"/>
<xsl:variable name="lang.medio1" select="'CAT-Cargo en cuenta'"/>
<xsl:variable name="lang.medio2" select="'CAT-Pago con tarjeta'"/>
<xsl:variable name="lang.entidadFinanciera" select="'CAT-Entidad Financiera'"/>
<xsl:variable name="lang.domiciliacion" select="'CAT-Domiciliación'"/>
<xsl:variable name="lang.domiciliacion01" select="'CAT-Domiciliar'"/>
<xsl:variable name="lang.domiciliacion02" select="'CAT-No Domiciliar'"/>
<xsl:variable name="lang.ccc" select="'CAT-Código Cuenta Cliente'"/>
<xsl:variable name="lang.numeroTarjeta" select="'CAT-Número Tarjeta Crédito'"/>
<xsl:variable name="lang.fechaTarjeta" select="'CAT-Fecha de Caducidad'"/>
<xsl:variable name="lang.mensaje03" select="'CAT-Formato incorrecto. '"/>
<xsl:variable name="lang.mensaje04" select="'CAT-debe ser un valor númerico. '"/>
<xsl:variable name="lang.mensaje08" select="'CAT-Debe introducir un número de cuenta corriente.'"/>
<xsl:variable name="lang.mensaje09" select="'CAT-El número de cuenta debe tener 20 dígitos.'"/>
<xsl:variable name="lang.mensaje10" select="'CAT-El número de tarjeta debe tener 16 dígitos.'"/>
<xsl:variable name="lang.mensaje11" select="'CAT-Debe introducir la fecha de caducidad de la tarjeta.'"/>
<xsl:variable name="lang.mensaje12" select="'CAT-La fecha de caducidad de la tarjeta debe tener 4 dígitos.'"/>
<xsl:variable name="lang.mensaje13" select="'CAT-Debe introducir un número tarjeta.'"/>
</xsl:stylesheet>