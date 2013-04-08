<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="LIQ_000000200001_Resume_template.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
<xsl:variable name="lang.fechaDevengo" select="'GAL-Fecha de Devengo'"/>
<xsl:variable name="lang.emisora" select="'GAL-Emisora'"/>
<xsl:variable name="lang.modalidad" select="'GAL-Modalidad'"/>
<xsl:variable name="lang.modelo" select="'GAL-Modelo'"/>
<xsl:variable name="lang.referencia" select="'GAL-Referencia'"/>
<xsl:variable name="lang.concepto" select="'GAL-Concepto'"/>
<xsl:variable name="lang.ayuntamiento" select="'GAL-Ayuntamiento'"/>
<xsl:variable name="lang.desc" select="'GAL-Descripción'"/>
<xsl:variable name="lang.nif" select="'GAL-N.I.F. / C.I.F'"/>
<xsl:variable name="lang.nombre" select="'GAL-Apellidos y Nombre / Razón Social'"/>
<xsl:variable name="lang.especifico" select="'GAL-Dato Especifico'"/>
<xsl:variable name="lang.importe" select="'GAL-Importe'"/>
<xsl:variable name="lang.medio" select="'GAL-Medio Pago'"/>
<xsl:variable name="lang.medio1" select="'GAL-Cargo en cuenta'"/>
<xsl:variable name="lang.medio2" select="'GAL-Pago con tarjeta'"/>
<xsl:variable name="lang.entidadFinanciera" select="'GAL-Entidad Financiera'"/>
<xsl:variable name="lang.ccc" select="'GAL-Código Cuenta Cliente'"/>
<xsl:variable name="lang.numeroTarjeta" select="'GAL-Número Tarjeta Crédito'"/>
<xsl:variable name="lang.fechaTarjeta" select="'GAL-Fecha de Caducidad'"/>
<xsl:variable name="lang.mensaje01" select="'GAL-Formato de fecha incorrecto. El formato correcto es 12/12/2000.'"/>
<xsl:variable name="lang.mensaje02" select="'GAL-Formato de importe incorrecto. El formato correcto es 1500,90'"/>
<xsl:variable name="lang.mensaje03" select="'GAL-Formato incorrecto. '"/>
<xsl:variable name="lang.mensaje04" select="'GAL-debe ser un valor númerico. '"/>
<xsl:variable name="lang.mensaje05" select="'GAL-Debe indicar una fecha de devengo.'"/>
<xsl:variable name="lang.mensaje06" select="'GAL-Debe indicar los datos específicos.'"/>
<xsl:variable name="lang.mensaje07" select="'GAL-Debe indicar el importe.'"/>
<xsl:variable name="lang.mensaje08" select="'GAL-Debe introducir un número de cuenta corriente.'"/>
<xsl:variable name="lang.mensaje09" select="'GAL-El número de cuenta debe tener 20 dígitos.'"/>
<xsl:variable name="lang.mensaje10" select="'GAL-El número de tarjeta debe tener 16 dígitos.'"/>
<xsl:variable name="lang.mensaje11" select="'GAL-Debe introducir la fecha de caducidad de la tarjeta.'"/>
<xsl:variable name="lang.mensaje12" select="'GAL-La fecha de caducidad de la tarjeta debe tener 4 dígitos.'"/></xsl:stylesheet>