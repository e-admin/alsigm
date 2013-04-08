<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="LIQ_000000200001_Form_template.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
<xsl:variable name="lang.fechaDevengo" select="'EUS-Fecha de Devengo'"/>
<xsl:variable name="lang.emisora" select="'EUS-Emisora'"/>
<xsl:variable name="lang.modalidad" select="'EUS-Modalidad'"/>
<xsl:variable name="lang.modelo" select="'EUS-Modelo'"/>
<xsl:variable name="lang.referencia" select="'EUS-Referencia'"/>
<xsl:variable name="lang.concepto" select="'EUS-Concepto'"/>
<xsl:variable name="lang.ayuntamiento" select="'EUS-Ayuntamiento'"/>
<xsl:variable name="lang.desc" select="'EUS-Descripción'"/>
<xsl:variable name="lang.nif" select="'EUS-N.I.F. / C.I.F'"/>
<xsl:variable name="lang.nombre" select="'EUS-Apellidos y Nombre / Razón Social'"/>
<xsl:variable name="lang.especifico" select="'EUS-Dato Especifico'"/>
<xsl:variable name="lang.importe" select="'EUS-Importe'"/>
<xsl:variable name="lang.medio" select="'EUS-Medio Pago'"/>
<xsl:variable name="lang.medio1" select="'EUS-Cargo en cuenta'"/>
<xsl:variable name="lang.medio2" select="'EUS-Pago con tarjeta'"/>
<xsl:variable name="lang.entidadFinanciera" select="'EUS-Entidad Financiera'"/>
<xsl:variable name="lang.ccc" select="'EUS-Código Cuenta Cliente'"/>
<xsl:variable name="lang.numeroTarjeta" select="'EUS-Número Tarjeta Crédito'"/>
<xsl:variable name="lang.fechaTarjeta" select="'EUS-Fecha de Caducidad'"/>
<xsl:variable name="lang.mensaje01" select="'EUS-Formato de fecha incorrecto. El formato correcto es 12/12/2000.'"/>
<xsl:variable name="lang.mensaje02" select="'EUS-Formato de importe incorrecto. El formato correcto es 1500,90'"/>
<xsl:variable name="lang.mensaje03" select="'EUS-Formato incorrecto. '"/>
<xsl:variable name="lang.mensaje04" select="'EUS-debe ser un valor númerico. '"/>
<xsl:variable name="lang.mensaje05" select="'EUS-Debe indicar una fecha de devengo.'"/>
<xsl:variable name="lang.mensaje06" select="'EUS-Debe indicar los datos específicos.'"/>
<xsl:variable name="lang.mensaje07" select="'EUS-Debe indicar el importe.'"/>
<xsl:variable name="lang.mensaje08" select="'EUS-Debe introducir un número de cuenta corriente.'"/>
<xsl:variable name="lang.mensaje09" select="'EUS-El número de cuenta debe tener 20 dígitos.'"/>
<xsl:variable name="lang.mensaje10" select="'EUS-El número de tarjeta debe tener 16 dígitos.'"/>
<xsl:variable name="lang.mensaje11" select="'EUS-Debe introducir la fecha de caducidad de la tarjeta.'"/>
<xsl:variable name="lang.mensaje12" select="'EUS-La fecha de caducidad de la tarjeta debe tener 4 dígitos.'"/>
<xsl:variable name="lang.mensaje13" select="'EUS-Debe introducir un número tarjeta.'"/>
</xsl:stylesheet>