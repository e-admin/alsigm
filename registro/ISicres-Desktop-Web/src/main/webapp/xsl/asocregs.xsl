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
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmdata.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmint.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/jquery.blockUI.js"></script>
	</HEAD>
	<BODY tabIndex="-1" style="cursor:default">
		<xsl:attribute name="onload">ActivateFrmtMenu();</xsl:attribute>
		<BR></BR>
		<NOBR>
			<TABLE class="Style11" width="95%" cellspacing="0" cellpadding="0" align="center" cols="6">
				<TR class="Style11" height="10" tabIndex="-1" align="right">
					<TD COLSPAN="6">
							<a href="#" onclick="AddAsocReg();"><img src="./images/asociar.jpg"/>
								<SCRIPT language="javascript">document.write(top.GetIdsLan("IDS_ASOCREG_NUEVOS"));</SCRIPT>
							</a>
					</TD>
				</TR>
			</TABLE>
			<xsl:choose>
				<xsl:when test="AsocRegs/RegisterParent or AsocRegs/Register">
					<xsl:if test="AsocRegs/RegisterParent">
						<TABLE class="Style3" width="95%" cellspacing="0" cellpadding="0" align="center">
							<TR class="Style3" height="4" tabIndex="-1">
								<TD></TD>
							</TR>
						</TABLE>
						<TABLE class="Style2" width="95%" cellspacing="0" cellpadding="0" align="center" cols="6">
							<TR height="5"><TD></TD></TR>
							<TR class="Style4" height="10" tabIndex="-1" align="center">
								<TD COLSPAN="6">
									<B>
										<script language="javascript">
											document.write( top.GetIdsLan("IDS_ASOCREG_PRINCIPAL"));
										</script>
									</B>
								</TD>
							</TR>
							<xsl:apply-templates select="AsocRegs/RegisterParent"/>
							<TR class="Style3" height="4" tabIndex="-1">
								<TD COLSPAN="6"></TD>
							</TR>
						</TABLE>
					</xsl:if>
					<xsl:if test="AsocRegs/Register">
						<TABLE class="Style3" width="95%" cellspacing="0" cellpadding="0" align="center">
							<TR class="Style3" height="4" tabIndex="-1">
								<TD></TD>
							</TR>
						</TABLE>
						<TABLE class="Style2" width="95%" cellspacing="0" cellpadding="0" align="center" cols="6">
							<xsl:apply-templates select="AsocRegs/Register"/>
						</TABLE>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<script language="javascript">
						alert(top.GetIdsLan("IDS_ASOCREGS_NOT_FOUND"));
						if (top.g_FdrReadOnly){
							top.Main.Folder.FolderData.FolderFormTree.OpenPageDataFromDtr();
						}
					</script>
				</xsl:otherwise>
			</xsl:choose>
		</NOBR>
	</BODY>
	</HTML>
</xsl:template>

<xsl:template match="RegisterParent">
	<xsl:apply-templates select="Book"/>
	<TR height="5"><TD></TD></TR>
	<TR class="Style5" height="20" tabIndex="-1" align="left">
		<TD width="3%"></TD>
		<TD width= "33%"><xsl:apply-templates select="Field[position()=3]"/></TD>
		<TD width="1%"></TD>
		<TD width= "45%"><xsl:apply-templates select="Field[position()=4]"/></TD>
		<TD width="1%"></TD>
		<TD width= "22%"><xsl:apply-templates select="Field[position()=5]"/></TD>
	</TR>
	<xsl:apply-templates select="Field[position()>=6]"/>
	<TR height="5"><TD></TD></TR>
<!--	<TR class="Style5" height="20" tabIndex="-1" align="right">
		<TD COLSPAN="6">
			<IMG src="./images/asociar.jpg" tabIndex="1" style="cursor:pointer;position:relative;">
				<xsl:attribute name="onclick">AddAsocReg();</xsl:attribute>
			</IMG>
			<script language="javascript">
				document.write("Asociar Registros 3");
			</script>
		</TD>
	</TR> -->
	<TR width="95%" class="Style3" height="4" tabIndex="-1">
		<TD COLSPAN="6"></TD>
	</TR>
</xsl:template>

<xsl:template match="Register">
	<xsl:apply-templates select="Book"/>
	<TR height="5"><TD></TD></TR>
	<TR class="Style5" height="20" tabIndex="-1" align="left">
		<TD width="3%"></TD>
		<TD width= "33%"><xsl:apply-templates select="Field[position()=3]"/></TD>
		<TD width="1%"></TD>
		<TD width= "45%"><xsl:apply-templates select="Field[position()=4]"/></TD>
		<TD width="1%"></TD>
		<TD width= "22%"><xsl:apply-templates select="Field[position()=5]"/></TD>
	</TR>
	<xsl:apply-templates select="Field[position()>=6]"/>
	<TR height="5"><TD></TD></TR>
	<TR class="Style12" height="20" tabIndex="-1" align="right">
		<TD COLSPAN="6">
			<a href="#">
				<xsl:attribute name="onclick">DelAsocReg(<xsl:apply-templates select="Field[position()=1]"/>,<xsl:apply-templates select="Field[position()=2]"/>);</xsl:attribute>
				<img src="./images/desasociar.jpg"/>
				<SCRIPT language="javascript">document.write(top.GetIdsLan("IDS_ASOCREG_ELIMINAR"));</SCRIPT>
			</a>
		</TD>
	</TR>
	<TR width="95%" class="Style3" height="4" tabIndex="-1">
		<TD COLSPAN="6"></TD>
	</TR>
</xsl:template>

<xsl:template match="Book">
	<TR height="5"><TD></TD></TR>
	<TR class="Style5" height="10" tabIndex="-1" align="left">
		<TD COLSPAN="6"><B><U><xsl:value-of select="Name"/></U></B></TD>
	</TR>
</xsl:template>

<xsl:template match="Field[position()=1]">
	<xsl:value-of select="Value"/>
</xsl:template>

<xsl:template match="Field[position()=2]">
	<xsl:value-of select="Value"/>
</xsl:template>

<xsl:template match="Field[position()>=6]">
	<xsl:if test="string-length(Value) > 0">
		<TR height="5"><TD></TD></TR>
		<TR class="Style5" height="20" tabIndex="-1" align="left">
			<TD width="3%"></TD>
			<TD width="97%" COLSPAN="5">
				<I><B>
					<xsl:value-of select="Label"/>
				</B></I> :
				<xsl:value-of select="Value"/>
			</TD>
		</TR>
	</xsl:if>
</xsl:template>

<xsl:template match="Field">
	<I><B><xsl:value-of select="Label"/></B></I> : <xsl:value-of select="Value"/>
</xsl:template>

</xsl:stylesheet>
