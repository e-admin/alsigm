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
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/dtrlist.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmdist.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
				/* Cebreado con jQuery */
				$("#tblMain tbody tr:odd").addClass("Style5 odd");
			});

		</SCRIPT>
	</HEAD>
	<BODY tabIndex="-1" onload="EnabledToolbar(); loadImagenesOrderDistribution();" style="cursor:default">
		<xsl:apply-templates select="Sicreslist/CONTEXT"/>
		<xsl:apply-templates select="Sicreslist/Perms"/>
		<xsl:choose>
			<xsl:when test="Sicreslist/CONTEXT/TOTAL[.>'0']">
				<div id="results" class="fixedHeaderTableDistrib">
					<TABLE id="tblMain" name="tblMain" class="report" width="99%" cellspacing="0" cellpadding="5" align="left" border="0" tabindex="-1" style="border-collapse:collapse;">
						<thead tabindex="-1">
							<TR valign="middle" tabIndex="-1">
								<TH class="report4"/>
								<TH class="report4" align="left" height="15" width="1%"></TH>
								<TH class="report4" align="left" height="15" width="1%"></TH>
								<xsl:apply-templates select="Sicreslist/HEADMINUTA/COL"/>
							</TR>
						</thead>
						<tbody>
       						<xsl:apply-templates select="Sicreslist/BODYMINUTA/ROW"/>
       						<TR>
       							<td colspan="12"> </td>
       						</TR>
       					</tbody>
					</TABLE>
				</div>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="Sicreslist/BODYMINUTA/Message"/>
			</xsl:otherwise>
		</xsl:choose>
	</BODY>
</HTML>
</xsl:template>

<xsl:template match="HEADMINUTA/COL">
	<TH class="report2" align="left">
		<xsl:attribute name="width"><xsl:value-of select='@Width' /></xsl:attribute>
		<xsl:if test="@Fld[.!='']">
			<xsl:attribute name="order"><xsl:value-of select='@Fld' /></xsl:attribute>
			<a href="#">
				<IMG src="./images/bg.gif" border="0">
					<xsl:attribute name="Id">ordenCampo<xsl:value-of select="@Fld" /></xsl:attribute>
					<xsl:attribute name="onclick">ordernarDistribucion("<xsl:value-of select="@Fld" />")</xsl:attribute>
				</IMG>
			</a>
		</xsl:if>
		<xsl:value-of select="." />
	</TH>
</xsl:template>

<xsl:template match="COLDATA">
   <TD>
      <xsl:value-of select="."/>
   </TD>
</xsl:template>

<xsl:template match="ROW">
      <xsl:if test="@Id[.!=-1]">
         <TR height="25" class="Style5" onmouseover="onrowover(this)" onmouseout="onrowout(this)" onfocus="onrowsel(this);" onblur="onrowunsel(this);" tabIndex="-1">
            <xsl:attribute name="ondblclick">
				<xsl:choose>
					<xsl:when test="@State[.!=2]">
						OpenFolderDtr("<xsl:value-of select='@NameArch'/>", <xsl:value-of select="@IdFdr"/>, <xsl:value-of select="@IdArch"/>, false);
					</xsl:when>
					<xsl:otherwise>
						OpenFolderDtr("<xsl:value-of select='@NameArch'/>", <xsl:value-of select="@IdFdr"/>, <xsl:value-of select="@IdArch"/>, true);
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>

            <xsl:attribute name="oncontextmenu">return ShowRemarksCtx();</xsl:attribute>
            <TD>
               <input type="checkbox" name="checkrow" onclick="CheckSel();" tabIndex="1">
                  <xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){CheckSel();return false;}</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Id"/></xsl:attribute>
                  <xsl:attribute name="IdArch"><xsl:value-of select="@IdArch"/></xsl:attribute>
                  <xsl:attribute name="IdFdr"><xsl:value-of select="@IdFdr"/></xsl:attribute>
                  <xsl:attribute name="State"><xsl:value-of select="@State"/></xsl:attribute>
                  <xsl:attribute name="Remarks"><xsl:value-of select="Remarks"/></xsl:attribute>
                  <xsl:attribute name="CaseSensitive"><xsl:value-of select="CaseSensitive"/></xsl:attribute>
                  <xsl:attribute name="DistType"><xsl:value-of select="@DistType"/></xsl:attribute>
               </input>
            </TD>
			<TD width="1%">
				<A href="#" tabIndex="1">
					<xsl:attribute name="onclick">ShowHistoryDistribution(<xsl:value-of select="@IdFdr" />,<xsl:value-of select="@IdArch" />);</xsl:attribute>
					<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){ShowHistoryDistribution(<xsl:value-of select="@IdFdr" />,<xsl:value-of select="@IdArch" />);return false;}</xsl:attribute>

					<SCRIPT language="javascript">
						document.write( '<img src="./images/legend.gif" title="' + top.GetIdsLan('IDS_TIT_HIST_DISTR') + '" alt="' + top.GetIdsLan('IDS_TIT_HIST_DISTR') + '" border="0"/>' );
					</SCRIPT>
				</A>

			</TD>
            <TD width="1%">
				<xsl:if test="@OutOfDate[.=1]">
					<SCRIPT language="javascript">
						document.write( '<img src="./images/clock_error.png" title="' + top.GetIdsLan('IDS_TIME_OUT_DIST_ACEPT') + '" alt="' + top.GetIdsLan('IDS_TIME_OUT_DIST_ACEPT') + '" />' )
                  	</SCRIPT>
				</xsl:if>
            </TD>
            <xsl:apply-templates select="COLDATA"/>
         </TR>

      </xsl:if>
</xsl:template>

<xsl:template match="CONTEXT">
	<LABEL id="Init" style="display:none"><xsl:value-of select="INIT"/></LABEL>
	<LABEL id="Paso" style="display:none"><xsl:value-of select="PASO"/></LABEL>
	<LABEL id="Total" style="display:none"><xsl:value-of select="TOTAL"/></LABEL>
	<LABEL id="Fin" style="display:none"><xsl:value-of select="END"/></LABEL>
	<script language="javascript">
		SetNavegador(<xsl:value-of select="INIT"/>, <xsl:value-of select="END"/>, <xsl:value-of select="TOTAL"/>);
	</script>
</xsl:template>

<xsl:template match="Message">
	<table align="center" height="50%">
		<tr><td></td></tr>
		<tr><td><h4><xsl:value-of select="."/></h4></td></tr>
	</table>
</xsl:template>

<xsl:template match="Perms">
	<script language="javascript">
		/*SetPerms(<xsl:value-of select="CanAccept"/>, <xsl:value-of select="CanReject"/>, <xsl:value-of select="CanArchive"/>, <xsl:value-of select="CanChangeDest"/>, <xsl:value-of select="CanChangeDestReject"/>, <xsl:value-of select="CanDist"/>);*/
		SetPerms(<xsl:value-of select="CanAccept"/>, <xsl:value-of select="CanReject"/>, <xsl:value-of select="CanArchive"/>, <xsl:value-of select="CanChangeDest"/>, <xsl:value-of select="CanChangeDestReject"/>);
	</script>
</xsl:template>

</xsl:stylesheet>
