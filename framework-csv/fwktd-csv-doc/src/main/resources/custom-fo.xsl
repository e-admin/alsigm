<?xml version="1.0"?>
<xsl:stylesheet xmlns:t="http://nwalsh.com/docbook/xsl/template/1.0"
	xmlns:param="http://nwalsh.com/docbook/xsl/template/1.0/param"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:d="http://docbook.org/ns/docbook"
	version="1.0">

	<xsl:import href="urn:docbkx:stylesheet" />
	
	<xsl:param name="basedir" />
	
	<!-- Colors -->
	<xsl:variable name="corporate.green.color" select="'#2f8926'" />
	<xsl:variable name="cover.title.color" select="'#7f7f7f'" />
	<xsl:variable name="programlisting.background.color" select="'#FFFFCC'" />
	
	<xsl:param name="draft.mode">no</xsl:param>
	<xsl:param name="fop1.extensions" select="1"></xsl:param>
	
	<!-- Global configuration -->
	<xsl:param name="hyphenate">false</xsl:param>
	<xsl:param name="l10n.gentext.default.language">es</xsl:param>
	<xsl:param name="double.sided" select="0"></xsl:param>
	
	<!-- Callout Images -->
	<xsl:param name="callout.graphics" select="1"></xsl:param>
	
	<!-- Admonitions -->
	<xsl:param name="admon.graphics" select="1"></xsl:param>
	<xsl:param name="admon.graphics.path" select="concat($basedir,'/src/main/resources/images/')"></xsl:param>
	<xsl:param name="admon.graphics.extension" select="'.png'"></xsl:param>
	<xsl:param name="admon.textlabel" select="0" />
	<xsl:attribute-set name="admonition.properties">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="graphical.admonition.properties">
		<xsl:attribute name="padding-left">5mm</xsl:attribute>
		<xsl:attribute name="padding-right">5mm</xsl:attribute>
		<xsl:attribute name="border-right-width">0.5pt</xsl:attribute>
		<xsl:attribute name="border-right-style">solid</xsl:attribute>
		<xsl:attribute name="border-right-color">#7f7f7f</xsl:attribute>
		<xsl:attribute name="space-after.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.maximum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">5mm</xsl:attribute>
	</xsl:attribute-set>
	
	<!-- Page layout -->
	<xsl:param name="page.margin.top">10mm</xsl:param>
	<xsl:param name="body.margin.top">30mm</xsl:param>
	<xsl:param name="region.after.extent">20mm</xsl:param>
	<xsl:param name="body.start.indent">0mm</xsl:param>
	<xsl:param name="body.end.indent">0mm</xsl:param>
	<xsl:param name="paper.type">A4</xsl:param>
	
	<!-- TOC -->
	<xsl:param name="toc.section.depth">6</xsl:param>

	<!-- Paragraph -->
	<xsl:param name="line-height">1.5</xsl:param>
		
	<!-- Objects layout -->
	<xsl:param name="formal.title.placement">
		figure after
		example after
		equation after
		table after
		procedure after
		task after
	</xsl:param>
	
	<!-- Typography -->
	<xsl:param name="body.font.family">Verdana</xsl:param>
	<xsl:param name="body.font.master">10</xsl:param>
	<xsl:param name="title.font.family">Verdana</xsl:param>
	<xsl:param name="symbol.font.family">Symbol,ZapfDingbats</xsl:param>
	<xsl:param name="dingbat.font.family">serif</xsl:param>
	
	<!-- Numbering -->
	<xsl:param name="chapter.autolabel">1</xsl:param>
	<xsl:param name="section.autolabel">1</xsl:param>
	
	<!-- Linking -->
	<xsl:param name="ulink.show" select="0"></xsl:param>
	
	<!-- Paragraph -->
	<xsl:attribute-set name="normal.para.spacing">
  		<xsl:attribute name="space-after.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.maximum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">5mm</xsl:attribute>
	</xsl:attribute-set>

	<!-- Lists -->
	<xsl:attribute-set name="itemizedlist.label.properties">		
	</xsl:attribute-set>
	
	<xsl:param name="itemizedlist.label.width">5mm</xsl:param>
	<xsl:param name="orderedlist.label.width">5mm</xsl:param>
	<xsl:variable name="variablelist.as.blocks" select="1"></xsl:variable>
	

	<!-- Figures, tables, ... properties -->
	<xsl:attribute-set name="formal.object.properties">
		<xsl:attribute name="space-before.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.maximum">5mm</xsl:attribute>
		<xsl:attribute name="keep-together.within-column">always</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>		
	</xsl:attribute-set>
	
	<xsl:attribute-set name="formal.title.properties" >
		<xsl:attribute name="font-size">9pt</xsl:attribute>
		<xsl:attribute name="font-style">italic</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>
		<xsl:attribute name="space-before.minimum">2mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">2mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">2mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="section.title.properties">
		<xsl:attribute name="font-family">Arial</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="section.title.level1.properties">
		<xsl:attribute name="font-size">18pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level2.properties">
		<xsl:attribute name="font-size">15pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level3.properties">
		<xsl:attribute name="font-style">italic</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="font-size">12pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level4.properties">
		<xsl:attribute name="font-weight">normal</xsl:attribute>
		<xsl:attribute name="font-size">10pt</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level5.properties">
		<xsl:attribute name="font-style">italic</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>
		<xsl:attribute name="font-size">10pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table.table.properties">
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="border-before-width.conditionality">retain</xsl:attribute>
		<xsl:attribute name="border-collapse">collapse</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<!--<xsl:attribute name="keep.together">true</xsl:attribute>-->
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table.cell.padding">
		<xsl:attribute name="padding-start">1mm</xsl:attribute>
		<xsl:attribute name="padding-end">1mm</xsl:attribute>
		<xsl:attribute name="padding-top">1mm</xsl:attribute>
		<xsl:attribute name="padding-bottom">1mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:param name="tablecolumns.extension" select="1"></xsl:param>
	<xsl:param name="use.extensions" select="1"></xsl:param>
	
	<xsl:param name="section.label.includes.component.label" select="1"></xsl:param>
	
	<xsl:attribute-set name="verbatim.properties">
		<xsl:attribute name="space-before.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-after.maximum">5mm</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="white-space-collapse">false</xsl:attribute>
		<xsl:attribute name="white-space-treatment">preserve</xsl:attribute>
		<xsl:attribute name="linefeed-treatment">preserve</xsl:attribute>
		<xsl:attribute name="text-align">justify</xsl:attribute>
		<xsl:attribute name="line-height">normal</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="monospace.verbatim.properties" use-attribute-sets="verbatim.properties monospace.properties">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:param name="shade.verbatim" select="1"/>

	<xsl:attribute-set name="shade.verbatim.style">
		<xsl:attribute name="background-color">
			<xsl:value-of select="$programlisting.background.color" />
		</xsl:attribute>
		<xsl:attribute name="border-width">0.5pt</xsl:attribute>
		<xsl:attribute name="border-style">solid</xsl:attribute>
		<xsl:attribute name="border-color">#7f7f7f</xsl:attribute>
		<xsl:attribute name="padding">3mm</xsl:attribute>
	</xsl:attribute-set>
		
	<!-- Header -->
	<xsl:attribute-set name="header.content.properties">
		<xsl:attribute name="font-family">
			<xsl:value-of select="'Verdana'"></xsl:value-of>
		</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:param name="header.column.widths">2 0 2</xsl:param>
	
	<xsl:template name="header.content">
		<xsl:param name="pageclass" select="''" />
		<xsl:param name="sequence" select="''" />
		<xsl:param name="position" select="''" />
		<xsl:param name="gentext-key" select="''" />

		<fo:block background-color="{$corporate.green.color}">
			<xsl:choose>
				<xsl:when test="$position='left'">
					<fo:block text-align="left" font-size="8pt" color="white" font-style="italic" margin-left="2mm" padding-top="2mm" padding-bottom="1mm">
						<xsl:value-of select="ancestor-or-self::d:book/d:info/d:abstract" />
					</fo:block>
				</xsl:when>
				<xsl:when test="$position='right'">
					<fo:block text-align="right" font-size="8pt" color="white" padding-top="2mm" padding-bottom="1mm" margin-right="2mm">
						<xsl:value-of select="ancestor-or-self::d:book/d:info/d:title" />
					</fo:block>
				</xsl:when>
			</xsl:choose>
		</fo:block>
	</xsl:template>
	<xsl:param name="header.rule" select="0"></xsl:param>
	<xsl:param name="headers.on.blank.pages" select="0"></xsl:param>
	
	<!-- Footer -->
	<xsl:template name="footer.content">
		<xsl:param name="pageclass" select="''" />
		<xsl:param name="sequence" select="''" />
		<xsl:param name="position" select="''" />
		<xsl:param name="gentext-key" select="''" />
		
		<fo:block>
			<xsl:choose>
				<xsl:when test="$position='left' and $pageclass != 'blank'">
					<xsl:apply-templates select="." mode="object.title.markup"/>
				</xsl:when>
				<xsl:when test="$position='right' and $pageclass != 'blank' ">
					P&#225;g. <fo:page-number/>
				</xsl:when>
			</xsl:choose>
		</fo:block>
	</xsl:template>
	
	<xsl:param name="footer.column.widths">3 0 1</xsl:param>
	
	<xsl:param name="footer.rule" select="0"></xsl:param>
	<xsl:param name="footers.on.blank.pages" select="1"></xsl:param>
	
	<!-- Footer rule -->
	<xsl:attribute-set name="footer.content.properties">
		<xsl:attribute name="font-family">Verdana</xsl:attribute>
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="border-top-width">0.5pt</xsl:attribute>
		<xsl:attribute name="border-top-style">solid</xsl:attribute>
		<xsl:attribute name="border-top-color">
			<xsl:value-of select="$corporate.green.color"/>
		</xsl:attribute>		
	</xsl:attribute-set>
		
	<!-- Book covers -->
	<!-- Step 1: Define global parameter for background image -->
	<xsl:param name="front.cover">
		<xsl:value-of select="concat($basedir,'/src/main/resources/images/front-cover.jpg')"/>
	</xsl:param>

	<!--
		Step 2: Define your own simple-page-master. Basically, set all
		margins to 0 so that the graphic will take up the entire page
	-->  
	<xsl:template name="user.pagemasters">

		<fo:simple-page-master master-name="copyright"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="{$page.margin.top}"
                           margin-bottom="{$page.margin.bottom}"
                           margin-left="{$page.margin.inner}"
                           margin-right="{$page.margin.outer}">
			<fo:region-body margin-bottom="0" margin-top="220mm"
						  column-gap="{$column.gap.back}"
						  column-count="{$column.count.back}">				
			</fo:region-body>
		</fo:simple-page-master>	
	
		<fo:simple-page-master master-name="front-cover"
                           page-width="{$page.width}"
                           page-height="{$page.height}"
                           margin-top="0"
                           margin-bottom="0"
                           margin-left="0"
                           margin-right="0">
			<fo:region-body margin-bottom="0" 
							margin-top="0"
							column-gap="{$column.gap.back}"
							column-count="{$column.count.back}"
							background-image="url({$front.cover})">
			</fo:region-body>			
		</fo:simple-page-master>
  
		<fo:simple-page-master master-name="back-cover"
							   page-width="{$page.width}"
							   page-height="{$page.height}"
							   margin-top="0"
							   margin-bottom="0"
							   margin-left="0"
							   margin-right="0">
			<fo:region-body margin-bottom="0" margin-top="0"
							column-gap="{$column.gap.back}"
							column-count="{$column.count.back}"
							background-color="white">
			</fo:region-body>
		</fo:simple-page-master>		
	</xsl:template>  

	
	<!-- Placeholder templates -->
	<xsl:template name="front.cover">
		<fo:page-sequence master-reference="front-cover" force-page-count="no-force">
			<fo:flow flow-name="xsl-region-body">
				<fo:block font-size="20pt" font-family="Arial" color="{$cover.title.color}" text-align="center" margin-top="90mm">
					<xsl:value-of
							select="concat(d:info/d:productname,' ',d:info/d:productnumber)" />					
				</fo:block>
				<fo:block font-size="20pt" font-family="Arial" color="{$cover.title.color}" text-align="center" margin-top="0mm">
					<xsl:value-of select="d:info/d:subtitle" />
				</fo:block>
				<fo:block font-size="20pt" font-family="Arial" font-weight="bold" color="black" text-align="center" margin-top="0mm">
					<xsl:value-of select="d:info/d:title" />
				</fo:block>
				<fo:block margin-top="55mm" margin-right="30mm">
					<fo:block font-size="14pt" font-family="Verdana" font-weight="bold" color="black" text-align="right" >
						<xsl:value-of select="d:info/d:author/d:orgname" />
					</fo:block>
					<fo:block font-size="14pt" font-family="Verdana" font-weight="bold" color="black" text-align="right">
						<xsl:value-of select="d:info/d:date" />
					</fo:block>
				</fo:block>
	  		</fo:flow>		
		</fo:page-sequence>
	</xsl:template>
	
	<xsl:template name="back.cover">		
		<fo:page-sequence master-reference="back-cover">
			<fo:flow flow-name="xsl-region-body">
				<fo:block margin-top="240mm" margin-left="85mm" background-color="white">
					<xsl:variable name="back.cover.image" select="concat($basedir,'/src/main/resources/images/logo-ieci.png')" />
					<fo:external-graphic src="{$back.cover.image}" />
				</fo:block>
				<fo:block background-color="black" color="white" text-align="center" font-family="Arial" font-size="8pt" font-weight="bold" margin-top="5mm">
					www.ieci.es
				</fo:block>
			</fo:flow>
		</fo:page-sequence>
	</xsl:template>
		
	<xsl:template name="select.user.pagemaster">
		<xsl:param name="element"/>
		<xsl:param name="pageclass"/>
		<xsl:param name="default-pagemaster"/>

		<xsl:choose>
			<xsl:when test="$default-pagemaster = 'titlepage'">
				<xsl:value-of select="'blank'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$default-pagemaster"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- Custom page layout -->
	<xsl:template match="d:book">
		<xsl:variable name="id">
			<xsl:call-template name="object.id"/>
		</xsl:variable>

		<xsl:variable name="preamble"
                select="d:title|d:subtitle|d:titleabbrev|d:bookinfo|d:info"/>

		<xsl:variable name="content"
                select="node()[not(self::d:title or self::d:subtitle
                            or self::d:titleabbrev
                            or self::d:info
                            or self::d:bookinfo)]"/>

		<xsl:variable name="titlepage-master-reference">
			<xsl:call-template name="select.pagemaster">
				<xsl:with-param name="pageclass" select="'copyright'"/>
			</xsl:call-template>
		</xsl:variable>

		<xsl:call-template name="front.cover"/>

		<xsl:if test="$preamble">
			<xsl:call-template name="page.sequence">
				<xsl:with-param name="master-reference"
                      select="$titlepage-master-reference"/>
				<xsl:with-param name="content">
					<fo:block id="{$id}">
						<xsl:apply-templates select="d:info/d:copyright" mode="titlepage.mode" />
						<xsl:apply-templates select="d:info/d:legalnotice" mode="titlepage.mode" />
					</fo:block>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>

		<xsl:apply-templates select="d:dedication" mode="dedication"/>
		<xsl:apply-templates select="d:acknowledgements" mode="acknowledgements"/>		
		
		<xsl:call-template name="make.book.tocs"/>

		<xsl:apply-templates select="$content"/>

		<xsl:call-template name="back.cover"/>
	</xsl:template>
	

	
	<xsl:attribute-set name="component.title.properties">
		<xsl:attribute name="font-size">30pt</xsl:attribute>
		<xsl:attribute name="font-family">Arial</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:value-of select="$corporate.green.color"/>
		</xsl:attribute>
		<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
		<xsl:attribute name="space-before.optimum">10mm</xsl:attribute>
		<xsl:attribute name="space-before.minimum">10mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">10mm</xsl:attribute>
		<xsl:attribute name="text-align">
			<xsl:choose>
				<xsl:when test="((parent::d:article | parent::d:articleinfo | parent::d:info/parent::d:article) and not(ancestor::d:book) and not(self::d:bibliography))  or (parent::d:slides | parent::d:slidesinfo)">center</xsl:when>
				<xsl:otherwise>end</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="start-indent">
			<xsl:value-of select="$title.margin.left"></xsl:value-of>
		</xsl:attribute>
		<xsl:attribute name="padding-bottom">0mm</xsl:attribute>
		<xsl:attribute name="padding-right">5mm</xsl:attribute>
		<xsl:attribute name="margin-top">80mm</xsl:attribute>
		<xsl:attribute name="border-right">1pt solid <xsl:value-of select="$corporate.green.color"/></xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="section.title.properties">
		<xsl:attribute name="font-family">Arial</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:value-of select="$corporate.green.color"/>
		</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
		<xsl:attribute name="space-before.minimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.optimum">5mm</xsl:attribute>
		<xsl:attribute name="space-before.maximum">5mm</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="start-indent"><xsl:value-of select="$title.margin.left"></xsl:value-of></xsl:attribute>
		<xsl:attribute name="padding-top">5mm</xsl:attribute>
	</xsl:attribute-set>
	
	<!-- Remarks and application name are bold font style -->
	<xsl:template match="d:remark | d:application">
		<fo:inline font-size="9pt">
			<xsl:call-template name="inline.boldseq"/>		
		</fo:inline>
	</xsl:template>
	
	<xsl:template match="d:orgname">
		<fo:inline font-family="Times New Roman">
			<xsl:call-template name="inline.charseq"/>
		</fo:inline>
	</xsl:template>
	
	<xsl:template match="d:holder" mode="titlepage.mode">
		<fo:inline font-family="Times New Roman">
			<xsl:call-template name="inline.charseq"/>
		</fo:inline>
	</xsl:template>
	
	<!-- Italic font style -->
	<xsl:template match="d:filename | d:guimenu | d:guimenuitem | d:guibutton | d:keycode | d:keycap | d:keysym | d:mousebutton">
		<xsl:call-template name="inline.italicseq"/>
	</xsl:template>
	
	<xsl:template match="d:emphasis[@role='important']">
		<fo:inline margin-right="5mm">
			<fo:external-graphic scaling="uniform" content-width="3mm">
				<xsl:attribute name="src">
					  <xsl:value-of select="concat($basedir,'/src/main/resources/images/star.png')" />
				</xsl:attribute>
			</fo:external-graphic>
		</fo:inline>
		<fo:inline color="red" font-size="9pt">
			<xsl:call-template name="inline.italicseq"/>
		</fo:inline>
	</xsl:template>

	<!-- URIs and links are underlined and blue color -->
	<xsl:template match="d:uri">
		<fo:inline text-decoration="underline" color="blue" font-size="9pt">
			<xsl:call-template name="inline.charseq"/>
		</fo:inline>
	</xsl:template>
	
	<xsl:template match="d:link" name="custom-link">
		<fo:inline text-decoration="underline" color="blue" font-size="9pt">
			<xsl:call-template name="link" />
		</fo:inline>
	</xsl:template>
 
	<!-- Table header -->
	<xsl:template match="d:thead">
		<xsl:variable name="tgroup" select="parent::*"/>

	  <fo:table-header start-indent="0pt" end-indent="0pt" background-color="#e8e9ee">
		<xsl:apply-templates select="d:row[1]">
		  <xsl:with-param name="spans">
			<xsl:call-template name="blank.spans">
			  <xsl:with-param name="cols" select="../@cols"/>
			</xsl:call-template>
		  </xsl:with-param>
		</xsl:apply-templates>
	  </fo:table-header>
	</xsl:template>
	
	<!-- Párrafos con más indentación a la izquierda -->
	<xsl:template match="d:para[@role='indent']">
		<fo:block start-indent="10mm">
			<xsl:apply-templates select="node()"></xsl:apply-templates>
		</fo:block>	
	</xsl:template>

</xsl:stylesheet>