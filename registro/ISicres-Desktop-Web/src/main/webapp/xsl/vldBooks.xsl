<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<HTML>
		<HEAD></HEAD>
		<script language="javascript">														
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/list.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/table.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
		<BODY tabIndex="-1">
			<DIV style="width:'100%';height='200px';top='0px';left='0px';overflow:'auto';border='1'" tabindex="-1">
				<TABLE class="report" id="tbBooks" width="98%" align="left" cellpadding="0" cellspacing="0" border="0" tabindex="-1" style="border-collapse:collapse">
					<THEAD tabindex="-1">
						<TR>
							<TH class="report" width="5%"/>
							<TH id="Name" class="report" align="left" width="95%"></TH>
						</TR>
					</THEAD>
					<TBODY tabindex="1">
						<xsl:apply-templates select="Sicreslist/Book"/>
					</TBODY>
				</TABLE>
			</DIV>
			<DIV style="width:'100%';height='50px';top='200px';left='0px'">
				<TABLE id="tbButtons" width="100%" align="left" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
					<TR class="Style5" align="center" id="rowButtons">
						<TD width="80%"/>
						<TD width="10%">
							<INPUT class="button" type="button" value="" onclick="OnOK();" tabindex="1">
								<xsl:attribute name="style">cursor:pointer; font-family:'sans-serif,'; font-size:8pt; width:80;</xsl:attribute>
							</INPUT>
						</TD>
						<TD width="10%">
							<INPUT class="button" type="button" value="" onclick="OnCancel();" tabindex="1">
								<xsl:attribute name="style">cursor:pointer; font-family:'sans-serif,'; font-size:8pt; width:80;</xsl:attribute>
							</INPUT>
						</TD>
					</TR>
				</TABLE>
			</DIV>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="Book">
	<TR id="rowDatas" name="rowDatas" class="Style5" onclick="OnDataRowClick(this);" ondblclick="OnOK();">
		<xsl:attribute name="BookId"><xsl:value-of select="Id"/></xsl:attribute>
		<TD width="5%"><img src="images/select.gif" width="16" height="16"/></TD>
		<TD width="80%" colspan="3"><xsl:value-of select="NAME"/></TD>
	</TR>
</xsl:template>

</xsl:stylesheet>
