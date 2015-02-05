<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="LIQ_000000200001_Form_template.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
<xsl:variable name="lang.fechaDevengo" select="'CAT-Fecha de Devengo'"/>
<xsl:variable name="lang.emisora" select="'CAT-Emisora'"/>
<xsl:variable name="lang.modalidad" select="'CAT-Modalidad'"/>
<xsl:variable name="lang.modelo" select="'CAT-Modelo'"/>
<xsl:variable name="lang.referencia" select="'CAT-Referencia'"/>
<xsl:variable name="lang.concepto" select="'CAT-Concepto'"/>
<xsl:variable name="lang.ayuntamiento" select="'CAT-Ayuntamiento'"/>
<xsl:variable name="lang.desc" select="'CAT-Descripción'"/>
<xsl:variable name="lang.nif" select="'CAT-N.I.F. / C.I.F'"/>
<xsl:variable name="lang.nombre" select="'CAT-Apellidos y Nombre / Razón Social'"/>
<xsl:variable name="lang.especifico" select="'CAT-Dato Especifico'"/>
<xsl:variable name="lang.importe" select="'CAT-Importe'"/>
<xsl:variable name="lang.medio" select="'CAT-Medio Pago'"/>
<xsl:variable name="lang.medio1" select="'CAT-Cargo en cuenta'"/>
<xsl:variable name="lang.medio2" select="'CAT-Pago con tarjeta'"/>
<xsl:variable name="lang.entidadFinanciera" select="'CAT-Entidad Financiera'"/>
<xsl:variable name="lang.ccc" select="'CAT-Código Cuenta Cliente'"/>
<xsl:variable name="lang.numeroTarjeta" select="'CAT-Número Tarjeta Crédito'"/>
<xsl:variable name="lang.fechaTarjeta" select="'CAT-Fecha de Caducidad'"/>
<xsl:variable name="lang.mensaje01" select="'CAT-Formato de fecha incorrecto. El formato correcto es 12/12/2000.'"/>
<xsl:variable name="lang.mensaje02" select="'CAT-Formato de importe incorrecto. El formato correcto es 1500,90'"/>
<xsl:variable name="lang.mensaje03" select="'CAT-Formato incorrecto. '"/>
<xsl:variable name="lang.mensaje04" select="'CAT-debe ser un valor númerico. '"/>
<xsl:variable name="lang.mensaje05" select="'CAT-Debe indicar una fecha de devengo.'"/>
<xsl:variable name="lang.mensaje06" select="'CAT-Debe indicar los datos específicos.'"/>
<xsl:variable name="lang.mensaje07" select="'CAT-Debe indicar el importe.'"/>
<xsl:variable name="lang.mensaje08" select="'CAT-Debe introducir un número de cuenta corriente.'"/>
<xsl:variable name="lang.mensaje09" select="'CAT-El número de cuenta debe tener 20 dígitos.'"/>
<xsl:variable name="lang.mensaje10" select="'CAT-El número de tarjeta debe tener 16 dígitos.'"/>
<xsl:variable name="lang.mensaje11" select="'CAT-Debe introducir la fecha de caducidad de la tarjeta.'"/>
<xsl:variable name="lang.mensaje12" select="'CAT-La fecha de caducidad de la tarjeta debe tener 4 dígitos.'"/>
<xsl:variable name="lang.mensaje13" select="'CAT-Debe introducir un número tarjeta.'"/>
</xsl:stylesheet>