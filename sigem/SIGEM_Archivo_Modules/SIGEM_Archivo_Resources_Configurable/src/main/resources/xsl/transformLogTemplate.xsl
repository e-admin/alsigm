<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes"/>

<xsl:template match="/">
	<xsl:for-each select="detalles/detalle">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="80px"></td>
				<td class="tdTitulo" width="280px">
				  <xsl:value-of select="tipo_dato"/>:&nbsp;
				</td>
				<td width="10px" class="tdDatos">&nbsp;</td>
				<td class="tdDatos">
				  <xsl:value-of select="valor"/>&nbsp;
				</td>
			</tr>
		</table>
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet>