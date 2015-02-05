<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

  	<!-- Eliminar etiqueta "Chapter" -->
	<xsl:param name="local.l10n.xml" select="document('')"/>
	<l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
	  	<l:l10n language="es">
	   		<l:context name="title-numbered">
	     		<l:template name="chapter" text="%n.&#160;%t"/>
	    	</l:context>
	  	</l:l10n>
	</l:i18n>

	<!-- Propiedades de los Capítulos -->
	<xsl:attribute-set name="chapter.label-plus-title.properties">
		<xsl:attribute name="background-color">#D0D0D0</xsl:attribute>
	  	<xsl:attribute name="padding">2pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="chapter.label.properties">
	  	<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
	  	<xsl:attribute name="text-align">start</xsl:attribute>
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master * 1.2"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="chapter.title.properties">
	  	<xsl:attribute name="space-before.minimum">0.48em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.60em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.72em</xsl:attribute>
	  	<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
	  	<xsl:attribute name="text-align">center</xsl:attribute>
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master * 2.0"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="chapter.subtitle.properties">
	  	<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
	  	<xsl:attribute name="text-align">center</xsl:attribute>
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master * 1.2"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	</xsl:attribute-set>

</xsl:stylesheet>