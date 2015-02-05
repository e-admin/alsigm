<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

	<!-- Solamente habilito la columna central -->
	<xsl:param name="footer.column.widths">1 1 1</xsl:param>

	<!-- Eliminar pie de pagina -->
<!--	<xsl:template name="footer.content"/>-->

	<xsl:param name="imgPath"/>
	<xsl:template name="header.flecha">
	    <fo:external-graphic src="{$imgPath}/flecha.png"/>
	</xsl:template>

	<!-- Cojo la fecha del documento para ponerlo en el pie de pagina -->
	<xsl:param name="date" select="book/bookinfo/date"/>

	<!-- Cojo la versión del documento para ponerlo en el pie de pagina -->
	<xsl:param name="version" select="book/bookinfo/releaseinfo"/>

	<!-- Contenido de pie de pagina -->
	<xsl:template name="footer.content">
		<xsl:param name="pageclass" select="''" />
		<xsl:param name="sequence" select="''" />
		<xsl:param name="position" select="''" />
		<xsl:param name="gentext-key" select="''" />
		<fo:block font-size="9pt" color="#C0C0C0">
			<xsl:choose>
				<xsl:when test="$position = 'center'">
					<fo:table xsl:use-attribute-sets="header.table.properties">
						<fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
						<fo:table-column column-number="2" column-width="proportional-column-width(1)"/>
						<fo:table-column column-number="3" column-width="proportional-column-width(1)"/>
					    <fo:table-body>
					    	<fo:table-row>
		          				<fo:table-cell text-align="left" display-align="left">
		          					<fo:block>
					    				<xsl:value-of select="$date"/>
					    			</fo:block>
					    		</fo:table-cell>
					    		<fo:table-cell text-align="center" display-align="center">
		          					<fo:block>
					    				<xsl:text>Página </xsl:text><fo:page-number/>
					    			</fo:block>
					    		</fo:table-cell>
					    		<fo:table-cell text-align="right" display-align="right">
		          					<fo:block>
					    				<xsl:text>Versión </xsl:text><xsl:value-of select="$version"/>
					    			</fo:block>
					    		</fo:table-cell>
					    	</fo:table-row>
					    </fo:table-body>
					</fo:table>
				</xsl:when>
			</xsl:choose>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>