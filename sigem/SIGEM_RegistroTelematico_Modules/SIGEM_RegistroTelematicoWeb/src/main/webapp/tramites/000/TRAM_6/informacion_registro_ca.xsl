<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="informacion_registro.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.docIdentidad" select="'Dni'"/>
	<xsl:variable name="lang.nombre" select="'Nom'"/>
	<xsl:variable name="lang.email" select="'Correu electrònic'"/>
	<xsl:variable name="lang.numRegistro" select="'Número de registre'"/>
	<xsl:variable name="lang.fechaRegistro" select="'Data de registre'"/>
	<xsl:variable name="lang.asunto" select="'Assumpte'"/>
</xsl:stylesheet>