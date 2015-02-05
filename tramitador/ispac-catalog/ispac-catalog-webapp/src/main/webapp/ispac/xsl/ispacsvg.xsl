<?xml version="1.0" encoding="UTF-8" ?>
<xsl:transform version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:ispac="http://www.ieci.es/tecdoc/ispac"
	xmlns="http://www.w3.org/2000/svg" >


<xsl:output method = "xml" encoding="UTF-8" media-type="image/svg+xml"  indent="yes" />

<xsl:param name="staticContext">/ispaccatalog</xsl:param>
<xsl:param name="dynamicContext">/ispaccatalog</xsl:param>

<xsl:variable name="stage_width">40</xsl:variable>
<xsl:variable name="stage_height">40</xsl:variable>

<xsl:variable name="node_width">20</xsl:variable>
<xsl:variable name="node_height">20</xsl:variable>

<xsl:template match="/procedure">
  <svg	id="main"
  	version="1.1"
	baseProfile="full"
	width="100%" height="100%"
	xmlns="http://www.w3.org/2000/svg"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:ispac="http://www.ieci.es/tecdoc/ispac"
	onload="javascript:SVGUtils.init('main','background');">

	<script xlink:href="{$staticContext}/ispac/scripts/svg/dragsvg.js" id="draggableLibrary"/>
	<script xlink:href="{$staticContext}/ispac/scripts/svg/svgutils.js" id="ispacLibrary"/>

	<style type="text/css">

		.bkgenabled  {
			fill:white;
		}
		.bkgdisabled  {
			fill:none;
		}

		.flow { fill:none; stroke:black; stroke-width:2; }

		.nodetext {
			fill: navy;
			font-size: 12px ;
			text-anchor: middle;
		}

	</style>


	 <defs>
	 	<marker id="arrowhead"
			viewBox="0 0 10 10" refX="8" refY="5"
			markerUnits="strokeWidth"
			markerWidth="4" markerHeight="7"
			orient="auto">
			<path d="M 0 0 L 10 5 L 0 10 z" />
		</marker>

		<g id="stage_tmpl">
			<rect style="fill:yellow;stroke:#c84600;stroke-width:0.86307895;stroke-linecap:butt;stroke-linejoin:round;stroke-opacity:1;"
			 y="0" x="0" ry="1.3" rx="1.3" height="40" width="40"/>
		</g>

		<g id="node_tmpl">
			<circle r="10" cx="10" cy="10" style="fill:yellow;stroke:#c84600;stroke-width:0.86307895;stroke-opacity:1;"/>
		</g>

	</defs>

	<rect id="background" class="bkgdisabled"  x="0" y="0" height="2000" width="2000"/>
	<text id="debug" x="0" y="500"/>

	<g id="flows">
		<xsl:apply-templates select="./flows/flow" mode="svg"/>
	</g>

	<g id="stages">
		<xsl:apply-templates select="./stages/stage" mode="svg" />
	</g>

	<g id="syncnodes">
		<xsl:apply-templates select="./syncnodes/syncnode" mode="svg" />
	</g>

	</svg>
</xsl:template>

<xsl:template match="flow" mode="svg">
	<path  class="flow" d="M0,0 L0,0" marker-end="url(#arrowhead)">
		<xsl:attribute name="id">flow<xsl:value-of select="@id" /></xsl:attribute>
		<xsl:attribute name="ispac:orig">node<xsl:value-of select="@orig" /></xsl:attribute>
		<xsl:attribute name="ispac:end">node<xsl:value-of select="@dest" /></xsl:attribute>
		<xsl:attribute name="ispac:type">flow</xsl:attribute>
	</path>
</xsl:template>


<xsl:template match="stage[pos]" mode="svg">
	<g>
		<xsl:attribute name="id">node<xsl:value-of select="@id" /></xsl:attribute>
		<xsl:attribute name="ispac:enable">true</xsl:attribute>
		<xsl:attribute name="ispac:x"><xsl:value-of select="pos/@x" /></xsl:attribute>
		<xsl:attribute name="ispac:y"><xsl:value-of select="pos/@y" /></xsl:attribute>
		<xsl:attribute name="ispac:type">node</xsl:attribute>
		<xsl:attribute name="ispac:width"><xsl:value-of select="$stage_width" /></xsl:attribute>
		<xsl:attribute name="ispac:height"><xsl:value-of select="$stage_height" /></xsl:attribute>

		<use><xsl:attribute name="xlink:href">#stage_tmpl</xsl:attribute></use>
		<text class="nodetext" >
			<xsl:attribute name="x"><xsl:value-of select="$stage_width div 2" /></xsl:attribute>
			<xsl:attribute name="y"><xsl:value-of select="3* $stage_height div 2" /></xsl:attribute>
			<xsl:value-of select="name"/>
		</text>

	</g>
</xsl:template>

<xsl:template match="stage" mode="svg">
	<g>
		<xsl:attribute name="id">node<xsl:value-of select="@id" /></xsl:attribute>
		<xsl:attribute name="ispac:enable">true</xsl:attribute>
		<xsl:attribute name="ispac:x"><xsl:value-of select="50+position()*120" /></xsl:attribute>
		<xsl:attribute name="ispac:y">50</xsl:attribute>
		<xsl:attribute name="ispac:type">node</xsl:attribute>
		<xsl:attribute name="ispac:width"><xsl:value-of select="$stage_width" /></xsl:attribute>
		<xsl:attribute name="ispac:height"><xsl:value-of select="$stage_height" /></xsl:attribute>

		<use><xsl:attribute name="xlink:href">#stage_tmpl</xsl:attribute></use>
		<text class="nodetext" >
			<xsl:attribute name="x"><xsl:value-of select="$stage_width div 2" /></xsl:attribute>
			<xsl:attribute name="y"><xsl:value-of select="3* $stage_height div 2" /></xsl:attribute>
			<xsl:value-of select="name"/>
		</text>
	</g>
</xsl:template>


<xsl:template match="syncnode[pos]" mode="svg">
	<g>
		<xsl:attribute name="id">node<xsl:value-of select="@id" /></xsl:attribute>
		<xsl:attribute name="ispac:enable">true</xsl:attribute>
		<xsl:attribute name="ispac:x"><xsl:value-of select="pos/@x" /></xsl:attribute>
		<xsl:attribute name="ispac:y"><xsl:value-of select="pos/@y" /></xsl:attribute>
		<xsl:attribute name="ispac:type">node</xsl:attribute>
		<xsl:attribute name="ispac:width"><xsl:value-of select="$node_width" /></xsl:attribute>
		<xsl:attribute name="ispac:height"><xsl:value-of select="$node_height" /></xsl:attribute>

		<use><xsl:attribute name="xlink:href">#node_tmpl</xsl:attribute></use>
		<text class="nodetext" >
			<xsl:attribute name="x"><xsl:value-of select="$node_width div 2" /></xsl:attribute>
			<xsl:attribute name="y"><xsl:value-of select="3*$node_height div 2" /></xsl:attribute>
			<xsl:value-of select="name"/>
		</text>

	</g>
</xsl:template>

<xsl:template match="syncnode" mode="svg">
	<g>
		<xsl:attribute name="id">node<xsl:value-of select="@id" /></xsl:attribute>
		<xsl:attribute name="ispac:enable">true</xsl:attribute>
		<xsl:attribute name="ispac:x"><xsl:value-of select="20+position()*120" /></xsl:attribute>
		<xsl:attribute name="ispac:y">100</xsl:attribute>
		<xsl:attribute name="ispac:type">node</xsl:attribute>
		<xsl:attribute name="ispac:width"><xsl:value-of select="$node_width" /></xsl:attribute>
		<xsl:attribute name="ispac:height"><xsl:value-of select="$node_height" /></xsl:attribute>

		<use><xsl:attribute name="xlink:href">#node_tmpl</xsl:attribute></use>
		<text class="nodetext" >
			<xsl:attribute name="x"><xsl:value-of select="$node_width div 2" /></xsl:attribute>
			<xsl:attribute name="y"><xsl:value-of select="3*$node_height div 2" /></xsl:attribute>
			<xsl:value-of select="name"/>
		</text>
	</g>
</xsl:template>

</xsl:transform>