<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

	<!-- Espacio entre la cabecera y el contenido del documento -->
	<xsl:param name="body.margin.top">1.3in</xsl:param>

	<!-- Eliminar linea horizontal de la cabecera de las páginas -->
	<xsl:param name="header.rule" select="0"/>

	<!-- Solamente habilito la columna central -->
	<xsl:param name="header.column.widths">0 1 0</xsl:param>

	<!-- Logo de la cabecera -->
	<xsl:param name="imgPath"/>
	<xsl:template name="header.logo">
	    <fo:external-graphic src="{$imgPath}/logo.png"/>
	</xsl:template>

	<!-- Eliminar cabecera de las paginas -->
<!--	<xsl:template name="header.content"/>-->

	<xsl:attribute-set name="header.table.properties">
	  	<xsl:attribute name="table-layout">fixed</xsl:attribute>
	  	<xsl:attribute name="width">100%</xsl:attribute>
	</xsl:attribute-set>

	<!-- Cojo el titulo del documento para ponerlo en la cabecera -->
	<xsl:param name="title" select="book/bookinfo/title"/>

	<!-- Contenido de la cabecera -->
	<xsl:template name="header.content">
	    <xsl:param name="pageclass" select="''"/>
	    <xsl:param name="sequence" select="''"/>
	    <xsl:param name="position" select="''"/>
	    <xsl:param name="gentext-key" select="''"/>

	    <fo:block>
	      <xsl:choose>
	        <xsl:when test="$position='center'">
				<fo:table xsl:use-attribute-sets="header.table.properties">
      				<fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
				    <fo:table-body>
				    	<fo:table-row>
	          				<fo:table-cell text-align="left" display-align="left">
	          					<fo:block>
				    				<xsl:call-template name="header.logo"/>
				    			</fo:block>
				    		</fo:table-cell>
				    	</fo:table-row>
				    	<fo:table-row>
	          				<fo:table-cell text-align="left" display-align="left">
				    			<fo:block font-size="9pt" color="#C0C0C0" start-indent="0.1in" text-align="justify"
				    			background-image="url('{$imgPath}/flecha.png')"	background-repeat="no-repeat"
				    			background-position-horizontal="left" background-position-vertical="center">
										<fo:inline padding-left="8pt">
				    						<xsl:value-of select="$title"/>
				    					</fo:inline>
				    				</fo:block>
								<!--<fo:block font-size="9pt" color="#C0C0C0" start-indent="0.5in" text-align="justify">
				    				<xsl:value-of select="$title"/>
				    			</fo:block>-->
				    		</fo:table-cell>
				    	</fo:table-row>
      				</fo:table-body>
    			</fo:table>
	        </xsl:when>
	      </xsl:choose>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>