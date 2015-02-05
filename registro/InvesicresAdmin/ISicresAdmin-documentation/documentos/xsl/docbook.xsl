<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

  	<!-- imports the original docbook stylesheet -->
  	<xsl:import href="urn:docbkx:stylesheet"/>

	<!-- Para poner por defecto lenguaje español -->
	<xsl:param name="l10n.gentext.default.language">es</xsl:param>

	<!-- Para indicar que numere las secciones -->
	<xsl:param name="section.autolabel" select="1"/>
	<xsl:param name="section.label.includes.component.label" select="1"/>

	<!-- Fuente y Tamaño de letra de todo el documento -->
	<xsl:param name="body.font.master">11</xsl:param>
	<xsl:param name="body.font.family">Helvetica</xsl:param>
  	<xsl:param name="dingbat.font.family">Helvetica</xsl:param>
  	<xsl:param name="monospace.font.family">monospace</xsl:param>
  	<xsl:param name="sans.font.family">Helvetica</xsl:param>
  	<xsl:param name="title.font.family">Helvetica</xsl:param>
  	<xsl:param name="symbol.font.family">Helvetica</xsl:param>

	<xsl:param name="paper.type" select="'A4'"/>
  	<xsl:param name="double.sided" select="0"/>
  	<xsl:param name="fop.extensions" select="0"/>
  	<xsl:param name="fop1.extensions" select="1"/>
  	<xsl:param name="passivetex.extensions" select="0"/>
  	<xsl:param name="draft.watermark.image" select="''"/>
	<xsl:param name="draft.mode" select="'no'"/>
	<xsl:param name="hyphenate">false</xsl:param>

  	<!-- Numeración de las páginas -->
	<xsl:template name="initial.page.number">
	  	<xsl:param name="element" select="local-name(.)"/>
	  	<xsl:param name="master-reference" select="''"/>
	  	<xsl:choose>
	    	<xsl:when test="$double.sided != 0">auto-odd</xsl:when>
	    	<xsl:otherwise>auto</xsl:otherwise>
	   	</xsl:choose>
	</xsl:template>
	<xsl:template name="page.number.format">1</xsl:template>

	<!-- Poner negrita las variablelist -->
	<xsl:attribute-set name="variablelist.term.properties">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>

   	<xsl:param name="page.margin.inner">
	   <xsl:choose>
	     <xsl:when test="$double.sided != 0">1.0in</xsl:when>
	     <xsl:otherwise>0.75in</xsl:otherwise>
	   </xsl:choose>
	</xsl:param>

  	<xsl:param name="page.margin.outer">
       <xsl:choose>
     	 <xsl:when test="$double.sided != 0">0.5in</xsl:when>
      	 <xsl:otherwise>0.75in</xsl:otherwise>
       </xsl:choose>
 	</xsl:param>

	<xsl:param name="body.start.indent">
	  <xsl:choose>
	    <xsl:when test="$fop.extensions != 0">0pt</xsl:when>
	    <xsl:when test="$fop1.extensions != 0">0pt</xsl:when>
	    <xsl:when test="$passivetex.extensions != 0">0pt</xsl:when>
	    <xsl:otherwise>4pc</xsl:otherwise>
	  </xsl:choose>
	</xsl:param>

	<!-- PARAMETERS INTRODUCED BY US -->
	<xsl:param name="fop-093" select="1"/>
	<xsl:param name="firebird.orange" select="'#FB2400'"/>
	<xsl:param name="highlevel.title.color" select="'#404090'"/>
	<xsl:param name="midlevel.title.color"  select="'#404090'"/>
	<xsl:param name="lowlevel.title.color"  select="'#404090'"/>
	<xsl:param name="link.color"  select="'darkblue'"/>
	<xsl:param name="special-hyph.char" select="'&#x200B;'"/>
	<xsl:param name="special-hyph.min-before" select="3"/>
	<xsl:param name="special-hyph.min-after" select="2"/>
	<xsl:param name="ulink.hyphenate" select="$special-hyph.char"/>
	<xsl:param name="hyphenate.verbatim" select="1"/>
	<xsl:param name="digits" select="'0123456789'"/>
	<xsl:param name="url-hyph.char"             select="$special-hyph.char"/>
	<xsl:param name="url-hyph.before"           select="concat($digits, '?&amp;')"/>
	<xsl:param name="url-hyph.after"            select="concat($digits, '/.,-=:;_@')"/>
	<xsl:param name="url-hyph.not-before"       select="'/'"/>
	<xsl:param name="url-hyph.not-after"        select="''"/>
	<xsl:param name="url-hyph.not-between"      select="'./'"/>
	<xsl:param name="url-hyph.min-before"       select="$special-hyph.min-before"/>
	<xsl:param name="url-hyph.min-after"        select="$special-hyph.min-after"/>
	<xsl:param name="title.margin.left">0pc</xsl:param>
	<xsl:param name="variablelist.as.blocks" select="1"/>
	<xsl:param name="segmentedlist.as.table" select="1"/>
	<xsl:param name="ulink.show" select="0"/>
	<xsl:param name="admon.textlabel" select="1"/>
	<xsl:param name="make.index.markup" select="0"/>
	<xsl:param name="shade.verbatim" select="1"/>

	<!-- Params for filename breaking -->
	<xsl:param name="filename-hyph.char"        select="$special-hyph.char"/>
	<xsl:param name="filename-hyph.before"      select="concat($digits, '?&amp;')"/>
	<xsl:param name="filename-hyph.after"       select="concat($digits, '\/.,-+=:;_')"/>
	<xsl:param name="filename-hyph.not-before"  select="'\/'"/>
	<xsl:param name="filename-hyph.not-after"   select="''"/>
	<xsl:param name="filename-hyph.not-between" select="'./\'"/>
	<xsl:param name="filename-hyph.min-before"  select="$special-hyph.min-before"/>
	<xsl:param name="filename-hyph.min-after"   select="$special-hyph.min-after"/>

	<!-- ATTRIBUTE SETS -->
	<xsl:attribute-set name="monospace.properties">
	  <xsl:attribute name="font-family">
	    <xsl:value-of select="$monospace.font.family"/>
	  </xsl:attribute>
	  <xsl:attribute name="font-size">0.97em</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="verbatim.properties">
	  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
	  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
	  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
	  <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.optimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="monospace.verbatim.properties">
	  <xsl:attribute name="font-size">0.9em</xsl:attribute>
	  <xsl:attribute name="wrap-option">wrap</xsl:attribute>
	  <xsl:attribute name="hyphenation-character">►</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="shade.verbatim.style">
	  <xsl:attribute name="padding">2pt</xsl:attribute>
	  <xsl:attribute name="background-color">#FFFFEC</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="shade.screen.style">
	  <xsl:attribute name="background-color">#D8F8F0</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="shade.literallayout.style">
	  <xsl:attribute name="padding">0pt</xsl:attribute>
	  <xsl:attribute name="background-color">#FFFFFF</xsl:attribute>
	</xsl:attribute-set>

	<xsl:param name="table.frame.border.thickness">1pt</xsl:param>

	<xsl:attribute-set name="table.cell.padding">
	  <xsl:attribute name="padding-left">4pt</xsl:attribute>
	  <xsl:attribute name="padding-right">4pt</xsl:attribute>
	  <xsl:attribute name="padding-top">4pt</xsl:attribute>
	  <xsl:attribute name="padding-bottom">4pt</xsl:attribute>
	</xsl:attribute-set>

	<!-- titles for table, equation, example, etc.: -->
	<xsl:attribute-set name="blockquote.properties">
	  <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.optimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="list.block.spacing">
	  <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
	  <xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
	  <xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
	  <xsl:attribute name="space-after.optimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="list.item.spacing">
	  <xsl:attribute name="space-before.minimum">0.0em</xsl:attribute>
	  <xsl:attribute name="space-before.optimum">0.3em</xsl:attribute>
	  <xsl:attribute name="space-before.maximum">0.3em</xsl:attribute>
	  <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
	  <xsl:attribute name="space-after.optimum">0.2em</xsl:attribute>
	  <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
	</xsl:attribute-set>

	<!-- Admonitions -->
	<xsl:attribute-set name="admonition.title.properties">
	  <xsl:attribute name="font-size">
	    <xsl:value-of select="$body.font.master"/>
	    <xsl:text>pt</xsl:text>
	  </xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="admonition.properties">
	  <xsl:attribute name="font-size">
	    <xsl:value-of select="0.9*$body.font.master"/>
	    <xsl:text>pt</xsl:text>
	  </xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="xref.properties">
	  <xsl:attribute name="color"><xsl:value-of select="$link.color"/></xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="index.page.number.properties">
	  <xsl:attribute name="color"><xsl:value-of select="$link.color"/></xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="figure.properties">
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>

	<!-- Para poner el titulo en la parte inferior de la figure y centrado  -->
	<xsl:param name="formal.title.placement">
		figure after
		example after
		table before
		procedure after
	</xsl:param>

	<xsl:attribute-set name="formal.title.properties" use-attribute-sets="normal.para.spacing">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="font-size">
	    	<xsl:value-of select="$body.font.master"/>
	    	<xsl:text>pt</xsl:text>
	  	</xsl:attribute>
		<xsl:attribute name="space-before.minimum">0</xsl:attribute>
	  	<xsl:attribute name="space-before.optimum">0</xsl:attribute>
	  	<xsl:attribute name="space-before.maximum">0</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="normal.para.spacing">
     	<xsl:attribute name="space-before.optimum">0.2em</xsl:attribute>
     	<xsl:attribute name="space-before.minimum">0.2em</xsl:attribute>
     	<xsl:attribute name="space-before.maximum">0.2em</xsl:attribute>
 	</xsl:attribute-set>

 	<xsl:attribute-set name="compact.list.item.spacing">
		<xsl:attribute name="space-before.optimum">0.2em</xsl:attribute>
		<xsl:attribute name="space-before.minimum">0.2em</xsl:attribute>
		<xsl:attribute name="space-before.maximum">0.2em</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="list.block.properties">
	  	<xsl:attribute name="provisional-label-separation">0.6em</xsl:attribute>
	  	<xsl:attribute name="provisional-distance-between-starts">2em</xsl:attribute>
	</xsl:attribute-set>

	<!-- Tabla de revisiones del documento -->
	<xsl:attribute-set name="revhistory.title.properties">
	  <xsl:attribute name="font-size">12pt</xsl:attribute>
	  <xsl:attribute name="font-weight">bold</xsl:attribute>
	  <xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="revhistory.table.properties">
	  <xsl:attribute name="border">0.5pt solid black</xsl:attribute>
	  <xsl:attribute name="background-color">#EEEEEE</xsl:attribute>
	  <xsl:attribute name="width">50%</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="revhistory.table.cell.properties">
	  <xsl:attribute name="border">0.5pt solid black</xsl:attribute>
	  <xsl:attribute name="font-size">9pt</xsl:attribute>
	  <xsl:attribute name="padding">4pt</xsl:attribute>
	</xsl:attribute-set>


<!-- xsl:template name="next.itemsymbol">
  <xsl:param name="itemsymbol" select="'default'"/>
  <xsl:choose>
    <xsl:when test="$itemsymbol = 'circle'">circle</xsl:when>
    <xsl:when test="$itemsymbol = 'square'">square</xsl:when>
    <xsl:otherwise>disc</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="itemizedlist.label.markup">
  <xsl:param name="itemsymbol" select="'disc'"/>

  <xsl:choose>
    <xsl:when test="$itemsymbol='none'"></xsl:when>
    <xsl:when test="$itemsymbol='disc'">&#x2022;</xsl:when>
    <xsl:when test="$itemsymbol='bullet'">&#x2022;</xsl:when>
    <xsl:when test="$itemsymbol='endash'">&#x2013;</xsl:when>
    <xsl:when test="$itemsymbol='emdash'">&#x2014;</xsl:when>
    <xsl:when test="$itemsymbol='square'">&#x25A0;</xsl:when>
    <xsl:when test="$itemsymbol='box'">&#x25A0;</xsl:when>
    <xsl:when test="$itemsymbol='smallblacksquare'">&#x25AA;</xsl:when>
    <xsl:when test="$itemsymbol='circle'">&#x25CB;</xsl:when>
    <xsl:when test="$itemsymbol='opencircle'">&#x25CB;</xsl:when>
    <xsl:when test="$itemsymbol='whitesquare'">&#x25A1;</xsl:when>
    <xsl:when test="$itemsymbol='smallwhitesquare'">&#x25AB;</xsl:when>
    <xsl:when test="$itemsymbol='round'">&#x25CF;</xsl:when>
    <xsl:when test="$itemsymbol='blackcircle'">&#x25CF;</xsl:when>
    <xsl:when test="$itemsymbol='whitebullet'">&#x25E6;</xsl:when>
    <xsl:when test="$itemsymbol='triangle'">&#x2023;</xsl:when>
    <xsl:when test="$itemsymbol='point'">&#x203A;</xsl:when>
    <xsl:when test="$itemsymbol='hand'"><fo:inline font-family="Wingdings 2">A</fo:inline></xsl:when>
    <xsl:otherwise>&#x2022;</xsl:otherwise>
  </xsl:choose>
</xsl:template-->

</xsl:stylesheet>