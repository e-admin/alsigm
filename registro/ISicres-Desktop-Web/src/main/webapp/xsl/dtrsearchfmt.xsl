<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<HTML>
	<HEAD>
		<script language="javascript">														
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css"/>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css"/>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/colors.js"></script>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/dtrlist.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/dtrtoolbar.js"></SCRIPT>
	</HEAD>
	<BODY tabIndex="-1" onload="top.Main.Distr.EnabledToolbar();top.Main.Distr.distQuery.style.display='block';" style="cursor:default">
		<TABLE id="tbSearch" class="tableTbSearch">
			<THEAD>
				<TR tabindex="-1">
					<TH CLASS="report" ALIGN="left" width="100%" colspan="4" style="padding:2px">
						<SCRIPT language="javascript">document.write( top.GetIdsLan("IDS_TITSELECT"))</SCRIPT>
					</TH>
				</TR>
			</THEAD>
			<TR></TR>
			<TR></TR>
			<xsl:apply-templates select="Fields/Field"/>
			<TR></TR>
			<TR>
				<TD colspan="4" align="right">
					<input id="SearchDist" type="button" class="button" style="width:80" onclick="SearchDist();">
						<SCRIPT language="javascript">document.getElementById("SearchDist").value = top.GetIdsLan("IDS_OPCBUSCAR")</SCRIPT>
					</input>
					<input id="Query" type="hidden" value=""></input>
				</TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
</xsl:template>

<xsl:template match="Field">
	<TR class="Style2">
		<TD tabindex="1" class="report"> 
			<input type="checkbox" class="checkbox" name="checkrow">
				<xsl:if test="@Selected">
					<xsl:attribute name="checked">true</xsl:attribute>	
				</xsl:if>
				<xsl:attribute name="param"><xsl:value-of select="@Param"/></xsl:attribute>	
			</input>
		</TD>
		<TD tabindex="-1" class="Label">
			<xsl:value-of select="Label"/>
		</TD>
		<xsl:apply-templates select="Operators"/>
		<TD tabindex = "1" class="report">
			<xsl:choose>
				<xsl:when test="Values">
					<xsl:apply-templates select="Values"/>
				</xsl:when>
				<xsl:otherwise>
					<input type="text" class="input" style="width:170">
						<xsl:attribute name="id"><xsl:value-of select="@Validate"/></xsl:attribute>
						<xsl:attribute name="FldId"><xsl:value-of select="@Validate"/></xsl:attribute>
						<xsl:attribute name="param"></xsl:attribute>
						<xsl:attribute name="onchange">this.param=this.value;</xsl:attribute>
						<xsl:if test="@IsDate">
							<xsl:attribute name="onblur">if (this.value!=""){top.ValidateDate(this);}onchange();</xsl:attribute>
						</xsl:if>
					</input>
					<xsl:if test="@Validate">
						<img src="./images/ayuda1.gif" tabIndex="1">
							<xsl:attribute name="style">position:relative;top:3px;left:-12px;cursor:pointer;</xsl:attribute>
							<xsl:attribute name="onclick">ShowValidateList(this, <xsl:value-of select="@Validate"/>);</xsl:attribute>
							<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){this.onclick();}</xsl:attribute>
						</img>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</TD>
	</TR>
	<TR></TR>
</xsl:template>

<xsl:template match="Operators">
	<TD tabindex="1" class="report">
		<select class="combo" style="width:130">
			<xsl:for-each select="Operator">
				<option>
					<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>	
					<xsl:value-of select="."/>
				</option>
			</xsl:for-each>
		</select>
	</TD>
</xsl:template>

<xsl:template match="Values">
	<select class="combo" style="width:170">
		<xsl:attribute name="onchange">this.param=this.selectedIndex.toString();</xsl:attribute>
		<xsl:attribute name="param">0</xsl:attribute>
		<xsl:for-each select="Value">
			<option>
				<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>	
				<xsl:value-of select="."/>
			</option>
		</xsl:for-each>
	</select>
</xsl:template>

</xsl:stylesheet>
