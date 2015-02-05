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
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/jquery.blockUI.js"></script>
		<script type="text/javascript">
			document.title = top.GetIdsLan("IDS_TIT_HIST_DISTR");

			function ampliarCapaPadre(img){
				var capaPadre = img.parentNode.parentNode;

				if(capaPadre.style.overflow == "visible"){
					capaPadre.style.overflow = "hidden";
					img.src="./images/datplus.gif";
				}else{
					capaPadre.style.overflow = "visible";
					img.src="./images/datminus.gif";
				}
			}
		</script>
	</HEAD>
	<BODY tabIndex="-1">
	   <xsl:if test="Sicreslist/CONTEXT/TOTAL[.>'0']">
	      <xsl:attribute name="onload"></xsl:attribute>
	   </xsl:if>
	   <xsl:apply-templates select="Sicreslist/PARAMS"/>

	   	<div class="migas">
			<ul>
				<li><xsl:value-of select="Sicreslist/HEAD/NameArch" />
					&#160;
					<img id="flechas" src="images/flechitas_down.gif"/>
					&#160;
			         <script language="javascript">
			            document.write(top.GetIdsLan("IDS_REPORT_NUM_REG"));
			         </script>
			         &#160;
					<xsl:value-of select="Sicreslist/HEAD/FolderNumber" />
				</li>
			</ul>
		</div>

	   <xsl:choose>
	   <xsl:when test="Sicreslist/CONTEXT/TOTAL[.>'0']">
	     <div class="historialDist">
	         <TABLE class="Style2" width="100%" cellspacing="0" cellpadding="0" align="center">
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
		      <TABLE class="Style3" width="100%" align="center">
	            <TR class="Style3" height="1" valign="middle" tabIndex="-999">
	               <td></td>
	            </TR>
		      </TABLE>
	     </div>
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
   <xsl:choose>
		<xsl:when test="@TextLong[.='1'] and (string-length(.)>26)">
		   <TD width="25%" valing="top" style="padding-top:4px" >
				<div id="textLong">
					<xsl:attribute name="style">position:relative; overflow: hidden; width:230px ;height:13px;</xsl:attribute>
					<div style="position:relative; float:left; margin-top: -1px;">
						<img src="./images/datplus.gif" onclick="ampliarCapaPadre(this)" valign="middle"/>
					 </div>
					 <div style="position:relative; float: right; width:210px; margin-right: 2px;" >
						<xsl:value-of select="."/>
					</div>
				</div>
			</TD>
		</xsl:when>
		<xsl:otherwise>
			 <TD valign="top" style="padding-top:4px">
				<xsl:value-of select="."/>
			 </TD>
		</xsl:otherwise>
	</xsl:choose>
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
      <TR class="Style3" align="left" valign="top" tabIndex="-1">
         <TD align="center" height="25" width="4%" style="padding-top:3px">
            <img src="./images/datplus.gif" onclick="Expand(top.GetSrcElement(event));" style="cursor:pointer" tabIndex="1">
               <xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){Expand(top.GetSrcElement(event));}</xsl:attribute>
            </img>
         </TD>
         <xsl:apply-templates select="HEAD/COL"/>
      </TR>
      <TR class="Style4" valign="top" tabIndex="-1" style="display:none;">
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
	   <TR class="Style4" height="2" valign="top" tabIndex="-1">
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
