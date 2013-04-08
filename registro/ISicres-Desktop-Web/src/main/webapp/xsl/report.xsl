<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<HTML>
		<HEAD>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css" />
 			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/report.js"></SCRIPT>
 		</HEAD>
 		<BODY onload="OnWindowReportLoad();" tabIndex="-1">
 			<FORM NAME="ReportForm">
				<TABLE CLASS="report" NAME="oTableBut" ID="oTableBut" NOBR="true" WIDTH="99%">
					<THEAD>
						<TR TABINDEX="-1">
							<TH CLASS="report" ALIGN="left">
								<SCRIPT language="javascript">document.write( top.GetIdsLan("IDS_TITSELINF"))</SCRIPT>
							</TH>
						</TR>
					</THEAD>
					<TR TABINDEX="-1">
						<TD CLASS="report">
							<DIV class="label">
								<xsl:apply-templates select="Sicreslist/NODELIST/NODE"/>
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</FORM>
		</BODY>
   </HTML>
</xsl:template>

<xsl:template match="NODE">
	<xsl:choose>
		<xsl:when test="Id">
			<INPUT CLASS="radio" TYPE="radio" NAME="reportRadio" TABINDEX="1">
				<xsl:attribute name="value"><xsl:value-of select="Id"/></xsl:attribute>
			</INPUT>
			<xsl:value-of select="NAME"/>
			<BR/>
		</xsl:when>
		<xsl:otherwise>
			<script language="javascript">
				document.write(top.GetIdsLan("IDS_MSG_NOT_FOUND_REPORT"));
            </script>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
