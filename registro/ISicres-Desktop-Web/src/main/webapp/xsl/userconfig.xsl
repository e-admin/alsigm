<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

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
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/folderbar.js"></SCRIPT>
			<SCRIPT LANGUAGE="javascript">
				top.Idioma = top.GetDlgParam(3);
				document.title = top.GetIdsLan( "IDS_LABEL_REGCONFIG" );

				function OnClickCheck(oField)
				{
					if (oField.src.indexOf("uncheck.gif") > 0){
						oField.src = "./images/check.gif";
					}
					else {
						oField.src = "./images/uncheck.gif";
					}
				
					document.getElementById("btnOK").disabled = false;
				}
			</SCRIPT>
		</HEAD>
		<BODY tabIndex="-1" style="cursor:default" onunload="top.EnableEvents(window.opener);">
			<table class="report" width="99%">
				<xsl:apply-templates select="UserConfig/Fields"/>
			</table>
			<table class="report" width="99%">
				<xsl:apply-templates select="UserConfig/Parameters"/>
			</table>
			<table id="tbButtons" width="99%" align="left" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;">
				<tr class="Style5" align="center" id="rowButtons">
					<td width="80%"/>
					<td width="10%">
						<input type="button" class="button" id="btnOK" name="btnOK" disabled="true" tabindex="1" STYLE="width:80" onclick="SaveConfig();">
							<SCRIPT LANGUAGE="javascript">
								document.getElementById("btnOK").value = top.GetIdsLan("IDS_BTNACEPTAR");
							</SCRIPT>
						</input>
					</td>
					<td width="10%">
						<input type="button" class="button" id="btnCancel" name="btnCancel" tabindex="1" STYLE="width:80" onclick="top.close();">
							<SCRIPT LANGUAGE="javascript">
								document.getElementById("btnCancel").value = top.GetIdsLan("IDS_BTNCANCEL");
							</SCRIPT>
						</input>
					</td>
				</tr>
			</table>
		</BODY>
	</HTML>
</xsl:template>	

<xsl:template match="Fields">
	<thead>
		<tr>
			<th id="titleFields" CLASS="report" ALIGN="left" width="100%">
				<SCRIPT LANGUAGE="javascript">
					top.SetInnerText(document.getElementById("titleFields"), top.GetIdsLan("IDS_LABEL_HOLDCONFIG"));
				</SCRIPT>
			</th>
		</tr>
	</thead>	
	<tr class="Style5" style="height:94%">
		<td class="report">
			<div style="width:100%;height:100%;overflow:auto;">
				<table width="90%" id="tblPersistFlds" name="tblPersistFlds">
					<xsl:apply-templates select="Field"/>
				</table>
			</div>
		</td>
	</tr>
</xsl:template>

<xsl:template match="Parameters">
	<thead>
		<tr>
			<th id="titleParameters" CLASS="report" ALIGN="left" width="100%">
				<SCRIPT LANGUAGE="javascript">
					top.SetInnerText(document.getElementById("titleParameters"), top.GetIdsLan("IDS_LABEL_OPCONFIG"));
				</SCRIPT>
			</th>
		</tr>
	</thead>	
	<tr class="Style5">
		<td class="report">
			<table width="90%" id="tblPersistFlds" name="tblPersistFlds">
				<xsl:apply-templates select="Parameter"/>
			</table>
		</td>
	</tr>
</xsl:template>

<xsl:template match="Field">
	<tr valign="middle">
      		<td width="5%" align="center">
			<xsl:apply-templates select="@Checked"/>
      		</td>
      		<td align="left" class="FldPersist">
         		<xsl:value-of select="Label"/>
      		</td>
   	</tr>
</xsl:template>


<xsl:template match="Parameter">
	<tr valign="middle">
      		<td width="5%" align="center">
			<xsl:apply-templates select="@Checked"/>
	        </td>
      		<td align="left" class="FldPersist">
         		<xsl:value-of select="Label"/>
      		</td>
   	</tr>
</xsl:template>

<xsl:template match="@Checked[.='0']">
	<img src="./images/uncheck.gif" onclick="top.OnClickCheck(this);">
        	<xsl:attribute name="Id"><xsl:value-of select="../@Id"/></xsl:attribute>
		<xsl:attribute name="Ind"><xsl:value-of select="../@Ind"/></xsl:attribute>
	</img>
</xsl:template>

<xsl:template match="@Checked[.='1']">
        <img src="./images/check.gif" onclick="top.OnClickCheck(this);">
		<xsl:attribute name="Id"><xsl:value-of select="../@Id"/></xsl:attribute>
		<xsl:attribute name="Ind"><xsl:value-of select="../@Ind"/></xsl:attribute>
        </img>
</xsl:template>

</xsl:stylesheet>