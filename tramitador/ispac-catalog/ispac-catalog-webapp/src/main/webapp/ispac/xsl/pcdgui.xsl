<?xml version="1.0" encoding="UTF-8" ?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<xsl:output method="xml" encoding="UTF-8" media-type="text/hml" indent="yes" />

<xsl:param name="staticContext">/ispaccatalog</xsl:param>
<xsl:param name="dynamicContext">/ispaccatalog</xsl:param>



<xsl:variable name="stage_width">40</xsl:variable>
<xsl:variable name="stage_height">40</xsl:variable>

<xsl:variable name="node_width">20</xsl:variable>
<xsl:variable name="node_height">20</xsl:variable>

<xsl:template match="/procedure">
	<div class="procedure">
 	 	<h1><xsl:value-of select="name" /></h1>
 	 	<xsl:apply-templates select="./stages"/>
 	 	<xsl:apply-templates select="./syncnodes"/>
 	 	<xsl:apply-templates select="./flows"/>
	</div>
</xsl:template>

<xsl:template match="stages">
	<h2>Fases</h2>

		<xsl:apply-templates select="./stage"/>
	
</xsl:template>

<xsl:template match="stage">
<div class="mover" onmousedown="mover(event,id)">
<xsl:attribute name="id"><xsl:value-of select="@id" /></xsl:attribute>
<table class="estiloTabla"  >
		<tr>
			<td>
			  <xsl:value-of select="name" />
		
			  
			</td>
		</tr>
		<tr><td><hr/></td></tr>
		<tr><td>Tramite1</td></tr>
</table>
</div>
<br/><br/>
</xsl:template>

<xsl:template match="syncnodes">
	<h2>Nodos de sincronización</h2>
	<ul>
		<xsl:apply-templates select="./syncnode"/>
	</ul>
</xsl:template>

<xsl:template match="syncnode">
	<li><xsl:value-of select="name" /></li>
</xsl:template>

<xsl:template match="flows">
	<h2>Transiciones</h2>
	<ul>
		<xsl:apply-templates select="./flow"/>
	</ul>
</xsl:template>

<xsl:template match="flow">
	<li><xsl:value-of select="@id" /></li>
</xsl:template>

</xsl:transform>