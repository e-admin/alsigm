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
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/colors.js"></script>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/origdoc.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/frmdata.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/frmint.js"></SCRIPT>
	</HEAD>
	<BODY tabIndex="-1" style="cursor:default">
		<xsl:attribute name="onload">ActivateFrmtMenu();</xsl:attribute>
		<BR/>
		<NOBR>
			<xsl:choose>
				<xsl:when test="OrigDocs/Libro[@Tipo='1']">
					<FORM method="post" onsubmit="return sendData();" name="frmOrigDocs" id="frmOrigDocs">
						<TABLE class="Style2" width="95%" cellspacing="0" cellpadding="0" align="center">
							<xsl:apply-templates select="OrigDocs"/>
						</TABLE>
						<TABLE class="Style3" width="95%" align="center">
							<TR class="Style3" height="1" valign="middle" tabIndex="-1">
								<TD></TD>
							</TR>
						</TABLE>
						<BR/>
						<BR/>
						<TABLE width="95%" align="left">
							<TR height="15" valign="middle" tabIndex="-1">
								<TD width="60%"></TD>
								<TD>
									<input class="button" type="submit" tabIndex="2" name="btnSave" id="btnSave" disabled="true">
										<xsl:attribute name="style">cursor:pointer;font-family:'sans-serif,';font-size:8pt;width:80;</xsl:attribute>
										<script language="javascript">
											document.getElementById("btnSave").value = top.GetIdsLan("IDS_OPCGUARDAR");
											if (top.g_FdrReadOnly == true){	document.getElementById("btnSave").style.visibility = "hidden";}
										</script>
									</input>
								</TD>
								<TD width="1%"></TD>
								<TD>
									<input class="button" type="button" tabIndex="2" name="btnAdd" id="btnAdd">
										<xsl:attribute name="style">cursor:pointer; font-family:'sans-serif,'; font-size:8pt; width:80;</xsl:attribute>
										<xsl:attribute name="onclick">OnClickAdd(<xsl:value-of select="OrigDocs/Registro/Destino/@Propio"/>);</xsl:attribute>
										<script language="javascript">
											document.getElementById("btnAdd").value = top.GetIdsLan("IDS_BTNANADIR");
											if (top.g_FdrReadOnly == true){document.getElementById("btnAdd").style.visibility = "hidden";}
										</script>
										<xsl:if test="(OrigDocs/Registro/Destino[@Id='0']) or (OrigDocs/Libro[@CanAdd='0'])">
											<script language="javascript">document.getElementById("btnAdd").disabled = true;</script>
										</xsl:if>
									</input>
								</TD>
								<TD width="1%"></TD>
								<TD>
									<input class="button" type="button" tabIndex="3" name="btnDel" id="btnDel">
										<xsl:attribute name="style">cursor:pointer; font-family:'sans-serif,'; font-size:8pt; width:80;</xsl:attribute>
										<xsl:attribute name="onclick">OnClickDel();</xsl:attribute>
										<script language="javascript">
											document.getElementById("btnDel").value = top.GetIdsLan("IDS_BTNELIMINAR");
											if (top.g_FdrReadOnly == true){document.getElementById("btnDel").style.visibility = "hidden";}
										</script>
										<xsl:if test="not(OrigDocs//Proc) or (OrigDocs/Registro/Destino[@Id='0']) or (OrigDocs/Libro[@CanAdd='0'])">
											<script language="javascript">document.getElementById("btnDel").disabled = true;</script>
										</xsl:if>
									</input>
  								</TD>
 							</TR>
						</TABLE>
						<INPUT type="hidden" name="CanDel" id="CanDel" tabIndex="1">
							<xsl:attribute name="value"><xsl:value-of select="OrigDocs/Libro/@CanDel"/></xsl:attribute>
						</INPUT>
						<INPUT type="hidden" name="datas" id="datas" tabIndex="1"></INPUT>
					</FORM>
					<xsl:if test="(OrigDocs/Docs/Proc[@IdProc='-1'])">
						<script language="javascript">
							alert(top.GetIdsLan("IDS_MSG_ERR_VALID_TYPEPROC"));
						</script>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<script language="javascript">
						alert(top.GetIdsLan("IDS_ORIGDOC_NOT_FOR_EXIT"));
						top.Main.Folder.FolderData.FolderFormTree.OpenPageDataFromDtr();
					</script>
				</xsl:otherwise>
			</xsl:choose>
		</NOBR>
	</BODY>
</HTML>
</xsl:template>

<xsl:template match="Cols">
	<THEAD>
        <TR class="Style1" align="left" valign="middle" tabIndex="-1">
			<TD width="1%"></TD>
		    <TD width="5%"></TD>
				<xsl:apply-templates select="Col"/>
        </TR>
 		<TR class="Style2" height="1">
			<TD/>
		</TR>
    </THEAD>
</xsl:template>

<xsl:template match="Col">	
	<TD align="left">
       	<xsl:value-of select="."/>
    </TD>
	<TD width="1%"></TD>
</xsl:template>

<xsl:template match="Docs">
	<TBODY ID="tabData">         
		<xsl:apply-templates select="Proc"/>
    	</TBODY>
</xsl:template>

<xsl:template match="Proc">
	<TR height="20" class="Style5" tabIndex="-1" align="left" onmouseover="onrowover(this)" onmouseout="onrowout(this)" onfocus="onrowsel(this);" onblur="onrowunsel(this);">
		<xsl:if test="(@IdProc='-1')">
			<xsl:attribute name="style">color:red;</xsl:attribute>
		</xsl:if>
		<TD width="1%">
			<INPUT type="hidden" name="IdProc" tabIndex="1">
				<xsl:attribute name="value"><xsl:value-of select="@IdProc"/></xsl:attribute>
			</INPUT>
			<INPUT type="hidden" name="IsNew" tabIndex="1">
				<xsl:if test="(@IdProc='-1')">
					<xsl:attribute name="value">1</xsl:attribute>
				</xsl:if>
				<xsl:if test="not(@IdProc='-1')">
					<xsl:attribute name="value">0</xsl:attribute>
				</xsl:if>
			</INPUT>
		</TD>
		<TD width="5%"> 
	    		<INPUT class="checkbox" type="checkbox" name="checkrow" tabIndex="1">
			</INPUT>
            	</TD>
 	    	<TD align="left"><xsl:number count="*"/></TD>
        	<TD width="1%"></TD>
        	<TD align="left" width="85%"><xsl:value-of select="."/></TD>
    	</TR>
</xsl:template>

</xsl:stylesheet>
