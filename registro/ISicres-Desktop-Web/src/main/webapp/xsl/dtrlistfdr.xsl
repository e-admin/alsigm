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
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/colors.js"></script>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/dtrlist.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmdata.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/frmint.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/jquery.blockUI.js"></script>
	</HEAD>
	<BODY tabIndex="-1">
	   <xsl:if test="Sicreslist/CONTEXT/TOTAL[.>'0']">
	      <xsl:attribute name="onload">ActivateFrmtMenu();</xsl:attribute>
	   </xsl:if>
	   <xsl:apply-templates select="Sicreslist/PARAMS"/>
	   <BR />
	   <xsl:choose>
	   <xsl:when test="Sicreslist/CONTEXT/TOTAL[.>'0']">
	      <nobr>
         <TABLE class="Style2" width="95%" cellspacing="0" cellpadding="0" align="center">
            <thead>
               <TR class="Style1" align="left" valign="middle" tabIndex="-1">
                  <TD align="center" height="15"></TD>
                  <xsl:apply-templates select="Sicreslist/HEADMINUTA/COL"/>
               </TR>
               <TR class="Style2" height="1">
                  <TD/>
               </TR>
            </thead>
            <tbody>
         	   <xsl:apply-templates select="Sicreslist/MINUTA"/>
         	</tbody>
	      </TABLE>
	      <TABLE class="Style3" width="95%" align="center">
            <TR class="Style3" height="1" valign="middle" tabIndex="-1">
               <td></td>
            </TR>
	      </TABLE>
	      </nobr>
      </xsl:when>
      <xsl:otherwise>
         <script language="javascript">
            alert(top.GetIdsLan("IDS_FDR_DTR_NO_ELEMS"));
            top.Main.Folder.FolderData.FolderFormTree.OpenPageDataFromDtr();
         </script>
      </xsl:otherwise>
      </xsl:choose>
</BODY>
</HTML>
</xsl:template>

<xsl:template match="COL">
   <TD>
      <xsl:value-of select="."/>
   </TD>
</xsl:template>

<xsl:template match="ROW">
      <xsl:if test="@Id[.!=-1]">
         <TR height="20" class="Style5" tabIndex="-1">
            <TD class="Style1" width="7%"></TD>
            <xsl:apply-templates select="COL"/>
         </TR>
      </xsl:if>
</xsl:template>

<xsl:template match="CONTEXT">
   <script>
      <xsl:comment>
            SetValues(<xsl:value-of select="INIT" />,
                     <xsl:value-of select="PASO" />,
                     <xsl:value-of select="TOTAL" />,
                     <xsl:value-of select="END"/>);
      </xsl:comment>
   </script>
</xsl:template>

<xsl:template match="MINUTA">
      <TR class="Style3" align="left" valign="middle" tabIndex="-1">
         <TD align="center" height="25" width="4%">
            <img src="./images/datplus.gif" onclick="Expand(top.GetSrcElement(event));" style="cursor:pointer" tabIndex="1">
               <xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){Expand(top.GetSrcElement(event));}</xsl:attribute>
            </img>
         </TD>
         <xsl:apply-templates select="HEAD/COL"/>
      </TR>
      <TR class="Style4" valign="middle" tabIndex="-1" style="display:none;">
         <xsl:attribute name="id"><xsl:value-of select="@Id"/></xsl:attribute>
         <TD width="4%"></TD>
         <TD colspan="100">
            <TABLE width="100%" align="center" cellspacing="0" cellpadding="2">
               <thead class="Style1">
                  <TR class="Style6" height="15" align="left" valign="middle" tabIndex="-1">
                     <TD class="Style1" width="7%"></TD>
                     <xsl:apply-templates select="../BODYMINUTA/COL"/>
                  </TR>
               </thead>
               <tbody class="Style1">
                  <xsl:apply-templates select="BODY/ROW"/>
               </tbody>
	         </TABLE>
	      </TD>
	   </TR>
	   <TR class="Style4" height="2" valign="middle" tabIndex="-1">
	      <TD></TD>
	   </TR>
</xsl:template>

<xsl:template match="PARAMS">
   <script>
      <xsl:comment>
         GetParams(<xsl:value-of select="DISTRPID"/>);
      </xsl:comment>
   </script>
</xsl:template>

</xsl:stylesheet>
