<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding='utf-8'/>

<xsl:template match="/">
	<HTML>
		<HEAD>
			<script language="javascript">														
				document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
			</script>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css"/>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmdata.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmdist.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/report.js"></SCRIPT>
		</HEAD>
		<BODY tabIndex="-1" style="cursor:default" onunload="top.EnableEvents(window.opener);">
			<xsl:apply-templates select="Sicreslist/Title"/>			
			<table class="report" width="99%">
				<xsl:apply-templates select="Sicreslist/RegFields"/>
			</table>
			<table id="tbButtons" width="99%" align="left" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;">
				<tr class="Style5" align="center" id="rowButtons">
					<td width="80%"/>
					<td width="10%">
						<input type="button" class="button" id="btnBuscar" name="btnBuscar" tabindex="1" STYLE="width:80" onclick="SearchRegs(0, ResponseSearch);">
							<SCRIPT LANGUAGE="javascript">
								document.getElementById("btnBuscar").value = top.GetIdsLan("IDS_OPCBUSCAR");
							</SCRIPT>
						</input>
					</td>
					<td width="10%">
						<input type="button" class="button" id="btnCerrar" name="btnCerrar" tabindex="1" STYLE="width:80" onclick="top.close();">
							<SCRIPT LANGUAGE="javascript">
								document.getElementById("btnCerrar").value = top.GetIdsLan("IDS_OPCCERRAR");
							</SCRIPT>
						</input>
					</td>
				</tr>
			</table>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="Title">
	<script language="javascript">
		document.title = '<xsl:value-of select="."/>';		
		top.g_SessionPId = top.GetDlgParam(1);
		top.g_ArchivePId = top.GetDlgParam(2);
		top.Idioma = top.GetDlgParam(3);
		top.g_ArchiveId = top.GetDlgParam(4);
		top.g_FolderId = top.GetDlgParam(5);
		top.g_URL = top.GetDlgParam(0);
	</script>
</xsl:template>

<xsl:template match="RegFields">
	<xsl:apply-templates select="NAME"/>
	<tr>
		<td class="report" width="100%">
			<TABLE><xsl:apply-templates select="Field"/></TABLE>
		</td>
	</tr>
</xsl:template>

<xsl:template match="NAME">
	<thead>
		<tr><th CLASS="report" ALIGN="left" width="100%"><xsl:value-of select="."/></th></tr>
	</thead>
</xsl:template>

<xsl:template match="Field">
	<tr>
		<td width="45%"><div class="label" tabIndex="-1"><xsl:apply-templates select="FieldLabel"/></div></td>
		<td width="10%">
			<select tabindex="1" class="combo" style="width:150" id="Operators" name="Operators">
				<xsl:attribute name="FldName"><xsl:value-of select='FieldName'/></xsl:attribute>
				<xsl:apply-templates select="Operators/Operator"/>
			</select>
		</td>
		<td width="45%">
			<xsl:choose>
				<xsl:when test="Values">
					<select tabindex="1" class="combo" style="width:160">
						<xsl:attribute name="name"><xsl:value-of select='@Id'/></xsl:attribute>
						<xsl:attribute name="FldName"><xsl:value-of select='FieldName'/></xsl:attribute>
						<xsl:apply-templates select="Values/Value"/>
					</select>
				</xsl:when>
				<xsl:otherwise>
					<input type="text" class="input" style="width:160" tabIndex="1" onblur="FormatFieldAsocRegs(this);">
						<xsl:attribute name="name"><xsl:value-of select='@Id'/></xsl:attribute>
						<xsl:attribute name="FldId"><xsl:value-of select='@FldId'/></xsl:attribute>
						<xsl:attribute name="FldName"><xsl:value-of select='FieldName'/></xsl:attribute>
						<xsl:attribute name="DataType"><xsl:value-of select='@DataType'/></xsl:attribute>
						<xsl:attribute name="Validated"><xsl:value-of select='@Validation'/></xsl:attribute>
						<xsl:if test="@CaseSensitive[.='CS']">
							<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							<xsl:attribute name="onblur">this.value=this.value.toUpperCase();FormatFieldAsocRegs(this);</xsl:attribute>
						</xsl:if>
						<xsl:if test="@Validation[.='1']">
							<xsl:attribute name="onblur">ValidateBookSelected(this.value);</xsl:attribute>
						</xsl:if>
					</input>
					<xsl:if test="@Validation[.='1']">
						<IMG src="./images/buscar2.gif" tabIndex="1" style="cursor:pointer;position:relative;left:2;top:2">
							<xsl:attribute name="onclick">ShowValidationListAsocRegs("<xsl:value-of select='@Id'/>", <xsl:value-of select='@TValid'/>, <xsl:value-of select='@FldId'/>,'<xsl:value-of select="@CaseSensitive"/>');</xsl:attribute>
							<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){this.onclick();}</xsl:attribute>							
						</IMG>						
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</td>
		<td>
			<xsl:if test="@Validation[.='1']">
				<IMG src="./images/invalid.gif" style="cursor:pointer;visibility:hidden">
					<xsl:attribute name="FldName"><xsl:value-of select='FieldName'/></xsl:attribute>
				</IMG>
			</xsl:if>
		</td>
	</tr>
</xsl:template>

<xsl:template match="Operator">
	<option>
		<xsl:value-of select="."/>
	</option>
</xsl:template>

<xsl:template match="Value">
	<option>
		<xsl:attribute name="value"><xsl:value-of select='@ValueId'/></xsl:attribute>
		<xsl:value-of select="."/>
	</option>
</xsl:template>

</xsl:stylesheet>
