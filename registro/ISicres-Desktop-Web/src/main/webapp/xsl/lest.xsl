<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<HTML>
		<HEAD>
			<script language="javascript">
				document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
			</script>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/list.css"/>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/lest.js"></SCRIPT>
			<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
    		<script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
    		<script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
		</HEAD>
		<BODY ondragstart="return false;" tabIndex="-1">
			<xsl:apply-templates select="invesicres/Messages/Message"/>
			<table id="tabBooks" width="100%" align="center" cellpadding="0" cellspacing="0" border="0">
				<tr height="7px" tabIndex="-1"><td/></tr>
				<tr height="18px" id="books" align="left" valign="middle" tabIndex="-1">
					<td class="books"><img src="./images/menu_down.gif" border="0" style="cursor:pointer;margin-left: 5px; margin-right: 5px" onclick="Expand(this);" onkeydown="OnKeyDownExpand(event, this);" tabIndex="1"/><xsl:value-of select="invesicres/BookTree/RootName"/></td>
				</tr>
				<tr height="10px" tabIndex="-1"><td/></tr>
				<xsl:apply-templates select="invesicres/BookTree/Node"/>
			</table>
			<xsl:apply-templates select="invesicres/Session"/>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="Session">
	<SCRIPT TYPE="text/javascript">
		SetIdentification("<xsl:value-of select="User"/>", "<xsl:value-of select="UserName"/>", "<xsl:value-of select="OfficeCode"/>", "<xsl:value-of select="OfficeName"/>");
		OnLESTLoad(<xsl:value-of select="OtherOffice"/>, '<xsl:value-of select="SessionId"/>');
		top.g_OfficeEnabled = '<xsl:value-of select="OfficeEnabled"/>';
		if (top.g_OfficeEnabled == 'false') {
			alert(top.GetIdsLan('IDS_OFFICE_NOT_ENABLED'));
		}
	</SCRIPT>
</xsl:template>

<xsl:template match="Node">
	<xsl:choose>
		<xsl:when test="Type[.='Dir']">
			<tr height="18px" align="left" valign="middle" tabIndex="-1" id="title">
				<td>
					<nobr>
						<img border="0" src="./images/datblank.gif"/>
						<xsl:choose>
							<xsl:when test="(./Node/Type)">
								<img src="./images/menu_down.gif" border="0" style="cursor:pointer;margin-left: 5px; margin-right: 5px" onClick="Expand(this);" onkeydown="OnKeyDownExpand(event, this);" tabIndex="1"/>
							</xsl:when>
							<xsl:otherwise>
								<img src="./images/menu_right.gif" border="0" style="cursor:pointer;margin-left: 5px; margin-right: 5px" tabIndex="1"/>
							</xsl:otherwise>
						</xsl:choose>
						<a class="Item" onclick="Expand(this);"><xsl:value-of select="Title"/></a>
					</nobr>
				</td>
			</tr>
			<xsl:apply-templates select="Node">
				<xsl:sort select="Title" order="ascending" data-type="text"/>
			</xsl:apply-templates>
			<tr height="10px" tabIndex="-1"><td/></tr>
        </xsl:when>
        <xsl:otherwise>
			<tr height="18px" align="left" valign="middle" tabIndex="-1" id="bookname">
				<td>
					<nobr>
						<IMG border="0" src="./images/datblank.gif"/>
						<IMG border="0" src="./images/datblank.gif"/>
						<xsl:choose>
							<xsl:when test="ReadOnly[.='0']">
								<IMG border="0" style="margin-right: 10px">
									<xsl:attribute name="bookType"><xsl:value-of select="BookType"/></xsl:attribute>
									<xsl:choose>
										<xsl:when test="BookType[.='1']">
											<!-- Libro de entrada -->
											<xsl:attribute name="src">./images/book-open-in.gif</xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<!-- Libro de salida -->
											<xsl:attribute name="src">./images/book-open-out.gif</xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
								</IMG>

								<A class="Item" onmouseover="OverArchive(this)" onmouseout="OutArchive(this)" tabIndex="1">
									<xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
									<xsl:attribute name="onclick">if(top.g_TreeFunc){top.g_TreeFunc=false;top.g_bIsLockBook=false;top.Main.Workspace.GotoQuery(this);}</xsl:attribute>
									<xsl:attribute name="onkeydown">if(top.g_TreeFunc){if(top.GetKeyCode(event)==13){top.g_TreeFunc=false;top.g_bIsLockBook=false;top.Main.Workspace.GotoQuery(this);}}</xsl:attribute>
									<xsl:value-of select="Title"/>
								</A>
							</xsl:when>
							<xsl:otherwise>
								<IMG border="0" style="margin-right: 5px">
									<xsl:attribute name="bookType"><xsl:value-of select="BookType"/></xsl:attribute>
									<xsl:choose>
										<xsl:when test="BookType[.='1']">
											<!-- Libro de entrada -->
											<xsl:attribute name="src">./images/book-close-in.gif</xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<!-- Libro de salida -->
											<xsl:attribute name="src">./images/book-close-out.gif</xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
								</IMG>
								<A class="Item" onmouseover="OverArchive(this)" onmouseout="OutArchive(this)" tabIndex="1">
									<xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
									<xsl:attribute name="onclick">if(top.g_TreeFunc){top.g_TreeFunc=false;top.g_bIsLockBook=true;top.Main.Workspace.GotoQuery(this);}</xsl:attribute>
									<xsl:attribute name="onkeydown">if(top.g_TreeFunc){if(top.GetKeyCode(event)==13){top.g_TreeFunc=false;top.g_bIsLockBook=true;top.Main.Workspace.GotoQuery(this);}}</xsl:attribute>
									<xsl:value-of select="Title"/>
								</A>
							</xsl:otherwise>
						</xsl:choose>
					</nobr>
				</td>
			</tr>
        </xsl:otherwise>
     </xsl:choose>
</xsl:template>

<xsl:template match="Message">
	<xsl:choose>
      <xsl:when test="@Type[.='0']">
			<SCRIPT TYPE="text/javascript">top.confirmMessage('<xsl:value-of  select="."/>','0');</SCRIPT>
      </xsl:when>
      <xsl:otherwise>
			<SCRIPT TYPE="text/javascript">top.confirmMessage('<xsl:value-of  select="."/>','1');</SCRIPT>
      </xsl:otherwise>
   </xsl:choose>
</xsl:template>

</xsl:stylesheet>