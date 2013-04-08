<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

	<!-- Propiedades de las diferentes secciones del documento (hasta el nivel 6) -->
	<xsl:attribute-set name="section.title.properties">
	  	<xsl:attribute name="color"><xsl:value-of select="$lowlevel.title.color"/></xsl:attribute>
	  	<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
	  	<xsl:attribute name="hyphenate">false</xsl:attribute>
	  	<xsl:attribute name="text-align">start</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level1.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 7"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="text-align">left</xsl:attribute>
	  	<xsl:attribute name="padding">2pt</xsl:attribute>
	  	<xsl:attribute name="font-weight">bold</xsl:attribute>
	  	<xsl:attribute name="color">#777777</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level2.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 4"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="font-style">italic</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="font-weight">bold</xsl:attribute>
	  	<xsl:attribute name="color">#777777</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level3.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 3"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="font-weight">bold</xsl:attribute>
	  	<xsl:attribute name="color">#777777</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level4.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 2"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="color">#008000</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level5.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 1"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="font-style">italic</xsl:attribute>
	  	<xsl:attribute name="color">#87765D</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level6.properties">
	  	<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master + 1"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
	  	<xsl:attribute name="space-before.minimum">0.65em</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0.7em</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0.7em</xsl:attribute>
	  	<xsl:attribute name="font-style">italic</xsl:attribute>
	  	<xsl:attribute name="color">#87765D</xsl:attribute>
	</xsl:attribute-set>

</xsl:stylesheet>