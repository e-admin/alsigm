<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/invesDoc">
<HTML>
<HEAD>
	<script language="javascript">
		document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
	</script>
   <LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css"/>
   <LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css"/>
   <link rel="stylesheet" TYPE="text/css" HREF="./css/font.css"/>
   <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/colors.js"></script>
   <SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/tbltext.js"></SCRIPT>
   <SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
   <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/tablebar.js"></script>
   <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></script>
   <script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
   <script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
   <script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
   <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.blockUI.js"></script>
   <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.cookie.js"></script>
</HEAD>
<body onload="OnWindowLoad();top.g_OpcAval=true;top.setFocus(document.getElementById('ResultsTable').rows[1]); modifImagesResponseServer();" ondragstart="return false;" onfocus="AsigFocus();" tabIndex="-1">
	<script>
		<xsl:comment>
			SetTotalRows(<xsl:value-of select="TableInfo/Header/TotalNumRows" />);
			SetFirstRow(<xsl:value-of select="TableInfo/Header/FirstRow" />);
			SetRowsVisible(<xsl:value-of select="TableInfo/TableText/Header/NumFolders" />);
			SetCaseSensitive('<xsl:value-of select="TableInfo/Header/CaseSensitive" />');
			SetAutoDist('<xsl:value-of select="TableInfo/Header/AutoDist" />');
		</xsl:comment>
	</script>

	<input id="orderByTable" name="orderByTable" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="TableInfo/Header/OrderByTable"/></xsl:attribute>
	</input>

	<div class="migas">
		<ul>
			<li><SCRIPT LANGUAGE="javascript">document.write( top.GetIdsLan( "IDS_INICIO" ) )</SCRIPT>
				<img id="flechas" src="images/flechitas_down.gif"/>
				<label align="left" id="ArchiveName" class="BookNameEx"></label>
				<img id="flechas" src="images/flechitas_down.gif"/>
				<SCRIPT LANGUAGE="javascript">document.write( top.GetIdsLan( "IDS_SEARCH_RESULT" ) )</SCRIPT>
			</li>
		</ul>
	</div>
	<table class="GreyToolbar" width="100%" height="24px" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr height="22" valign="middle" align="center" tabIndex="-1">
			<td width="232"></td>
			<td>
				<table width="100%" height="18" border="0" cellspacing="0" cellpadding="0">
					<tr tabIndex="-1">
						<td>
							<div name="nbsp" align="center"></div>
						</td>
						<td id="NewFolderBtn" width="120" class="Options" onmouseover="top.Over(this);" onmouseout="top.Out(this);" onclick="top.NewFolder(this);" tabIndex="1" onkeydown="DoOnKeyDown(this, event);">
							<div align="center"><SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_OPCNUEVA" ) )</SCRIPT></div>
						</td>
						<td id="FormBtn" width="80" class="Options" onmouseover="top.Over(this);" onmouseout="top.Out(this);" onclick="OnClickFormBtn();" onkeydown="DoOnKeyDown(this, event);" tabIndex="1">
							<div align="center"><SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_OPCFORMUL" ) )</SCRIPT></div>
						</td>
						<td id="QryBtn" width="80" class="Options" onmouseover="top.Over(this);" onmouseout="top.Out(this);" onclick="OnClickQryBtn();" onkeydown="DoOnKeyDown(this, event);" tabIndex="1">
							<div align="center"><SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_OPCVOLVER" ) )</SCRIPT></div>
						</td>
					</tr>
				</table>
			</td>
			<td width="5"></td>
		</tr>
	</table>

	<table class="SubMenu2" id="tabMenu" border="0" cellPadding="0" cellSpacing="0" width="100%" height="22px">
		<tr height="100%" valign="middle" align="rigth" tabIndex="-1">
			<td width="10"></td>
			<script language="javascript">document.write(GetBarText());</script>
			<td>
				<div name="nbsp" align="center"></div>
			</td>
			<td width="10"></td>
		</tr>
	</table>
	<a id="FolderCounterText" class="clsInfoTitle"></a>

	<table id="tblUtil" name="tblUtil" width="100%" cellspacing="0" cellpadding="0" align="right" border="0" tabindex="-1" style="border-collapse:collapse; margin-top:3px;">
		<TR tabindex="-1">
			<script language="javascript">document.write(GetBarText2());</script>
		</TR>
	</table>

	<br/>
	<br/>

	<div id="cargando" class="cargar" >
		<img src="./images/loading.gif"/>
		<br/>
		<SCRIPT language="javascript">
			document.write(top.GetIdsLan( "IDS_LOAD" ))
		</SCRIPT>
	</div>

	<div id="resultados">
		<div id="results" class="fixedHeaderTable">
			<table id="ResultsTable" name="ResultsTable" class="report" border="0" cellspacing="0" cellpadding="5">
				<thead tabIndex="-1">
					<tr align="center" tabIndex="-1" valign="bottom">
						<th class="report2">
							<A></A>
						</th>
						<th class="report2">
							<A></A>
						</th>
						<xsl:apply-templates select="/invesDoc/TableInfo/TableFormat/Columns/Column" />
					</tr>
				</thead>

				<tbody onkeydown="OnPressKey(event);">
					<xsl:apply-templates select="/invesDoc/TableInfo/TableText/FoldersInfo/FolderInfo" />
				</tbody>
			</table>
		</div>
	</div>

	<xsl:apply-templates select="/invesDoc/Rights/UpdProtectedFields"/>
	<xsl:apply-templates select="/invesDoc/Rights/CanDist"/>
	<xsl:apply-templates select="/invesDoc/Rights/CanOperationIR"/>
	<xsl:apply-templates select="/invesDoc/Rights/CanOpenReg"/>

	<br/>
	<IFRAME src="blank.htm" name="frmOperations" id="frmOperations" style="display:none"></IFRAME>
</body>
</HTML>
</xsl:template>

<xsl:template match="Column">
	<th align="left">
			<xsl:attribute name="FldId"><xsl:value-of select="@FldId"/></xsl:attribute>
			<xsl:attribute name="class">ordenarResults</xsl:attribute>
			<xsl:value-of select="Title" />
	        <xsl:if test="IsExtended[.='0']">
	        	<a href="#">
	            	<IMG src="./images/bg.gif" border="0">
	                	<xsl:attribute name="Id">ordenCampo<xsl:value-of select="@FldId"/></xsl:attribute>
	                  	<xsl:attribute name="onclick">clickOrderCampoTable(<xsl:value-of select="@FldId"/>)</xsl:attribute>
	               	</IMG>
	            </a>
	        </xsl:if>
	        <script language="javascript">
				validarNavegador(<xsl:value-of select="@FldId"/>);
			</script>

	</th>
</xsl:template>

<xsl:template match="FolderInfo">
	<tr class="Style5" tabindex="1">
		<xsl:attribute name="Id"><xsl:value-of select="Id"/></xsl:attribute>
		<xsl:attribute name="onclick">
			SetRowSel(<xsl:number value="position()"/>, <xsl:value-of select="Id"/>)
		 	OpenExistFolder(<xsl:value-of select="Id"/>, top.Main.Table.TableData.g_FirstRow + <xsl:number value="position()"/> - 1, top.Main.Table.TableData.g_TotalRows)
		</xsl:attribute>
		<xsl:attribute name="onkeydown">
			if (top.GetKeyCode(event)==13){OpenExistFolder(<xsl:value-of select="Id"/>, top.Main.Table.TableData.g_FirstRow + <xsl:number value="position()"/> - 1, top.Main.Table.TableData.g_TotalRows)}
		</xsl:attribute>
		<xsl:attribute name="Row">
			<xsl:number value="position()"/>
		</xsl:attribute>

		<xsl:if test="(position() mod 2) != 1">
			<xsl:attribute name="class">odd</xsl:attribute>
		</xsl:if>

		<TD align="left">
		<!--
			<A tabindex="-1" target="_self">
				<xsl:attribute name="href">javascript:OpenExistFolder(<xsl:value-of select="Id"/>, top.Main.Table.TableData.g_FirstRow + <xsl:number value="position()"/> - 1, top.Main.Table.TableData.g_TotalRows)</xsl:attribute>
				<xsl:choose>
					<xsl:when test="(@OrigDocs='1')">
						<IMG src="./images/docful.gif" border="0"/>
					</xsl:when>
					<xsl:otherwise>
						<IMG src="./images/docemp.gif" border="0"/>
					</xsl:otherwise>
				</xsl:choose>
			</A>
		-->
		</TD>
		<TD>
			<INPUT class="checkbox" type="checkbox" name="checkrow" tabIndex="1">
				<xsl:attribute name="value"><xsl:value-of select="Id"/></xsl:attribute>
				<xsl:attribute name="onclick">OnClickCheck(this, event);</xsl:attribute>
				<xsl:attribute name="ondblclick">OnDblClick(event);</xsl:attribute>
			</INPUT>
        </TD>
		<xsl:for-each select="Values/Text">
			<TD class="seleccion">
				<xsl:if test="/invesDoc/TableInfo/Header/CaseSensitive = 'CS'">
					<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="." />
				<xsl:if test=".=''">&#160;</xsl:if>   <!--&#160 es un blanco-->
			</TD>
		</xsl:for-each>
	</tr>
</xsl:template>

<xsl:template match="UpdProtectedFields">
	<input id="UpdProtectedFlds" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="CanDist">
	<input id="CanDist" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="CanOpenReg">
	<input id="CanOpenReg" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="CanOperationIR">
	<input id="CanOperationIR" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

</xsl:stylesheet>