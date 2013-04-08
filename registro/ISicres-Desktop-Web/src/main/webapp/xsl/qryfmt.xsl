<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<HTML>
		<HEAD>
			<script language="javascript">
				document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
			</script>
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css" />
			<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css" />
			<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/colors.js"></script>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/qryfmt.js"></SCRIPT>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
			<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
    		<script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
    		<script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
			<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
			<xsl:apply-templates select="iSicresQryFmt/Functions"/>
		</HEAD>
		<BODY onload="OnWindowLoad();top.Main.Workspace.EnabledTool();" onfocus="AsigFocus();" scroll="auto"
			ondragstart="return false;" tabIndex="-1">
			<xsl:apply-templates select="iSicresQryFmt/Dimensions"/>
			<xsl:apply-templates select="iSicresQryFmt/PARAMS"/>

			<FORM action="tbltext.jsp" method="post" id="QryFmtForm" name="QryFmtForm" target="TableData">
				<xsl:attribute name="onsubmit">if (top.g_TreeFunc){DoSubmit();}else{return false;}</xsl:attribute>
				<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){top.Main.Workspace.Query.document.getElementById('btSubmit').click();}</xsl:attribute>
				<div id="divSearch">
					<xsl:apply-templates select="iSicresQryFmt/Control/@Type[.='Static']"/>
					<xsl:apply-templates select="iSicresQryFmt/Control/@Type[.='ComboBox']"/>
					<xsl:apply-templates select="iSicresQryFmt/Control/@Type[.='Edit']"/>
				</div>
				<INPUT type="submit" tabIndex="1" id="btSubmit" style="display:none"></INPUT>

				<div id="divOrder" style="display:none">
					<table class="title" width="100%">
						<tr>
							<td>
								<SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_ORDER"));</SCRIPT>
							</td>
							<td width="3%">
								<img src="images/pcalclose.gif" id="btnClose"  name="btnClose" onclick="closeOrder(); getOrderQuery();"/>
								<script type="text/javascript">document.getElementById("btnClose").alt = top.GetIdsLan("IDS_OPCCERRAR");</script>
							</td>
						</tr>
					</table>


					<table width="100%" border="0">
						<tr height="10">
							<td colspan="4"/>
						</tr>
						<tr>
							<td width="6%"> </td>
							<td class="tdLabel" width="40%"><label class="label"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_AVAILABLEFIELDS"));</SCRIPT></label></td>
							<td width="5%"></td>
							<td class="tdLabel" width="40%"><label class="label"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_ORDERBY"));</SCRIPT></label></td>
							<td></td>
						</tr>
						<tr>
							<td> </td>
							<td class="tdBody">
								<select id="listFields" class="label" size="25" multiple="multiple" style="width:100%">
									<xsl:apply-templates select="iSicresQryFmt/Fields/Field[count(@Selected) = 0]"/>
								</select>
							</td>
							<td class="tdBody" valign="middle" align="center">
								<table>
									<tr>
										<td>
											<img src="images/flecha_dcha.png" id="btnAgreg"  name="btnAgreg" onclick="aggregateOrder(false);"/>
											<script type="text/javascript">document.getElementById("btnAgreg").alt = top.GetIdsLan("IDS_BTNAGREGAR");</script>
										</td>
									</tr>
									<tr>
										<td>
											<img src="images/flecha_doble_dcha.png" id="btnAgregAll"  name="btnAgregAll" onclick="aggregateOrder(true);"/>
											<script type="text/javascript">document.getElementById("btnAgregAll").alt = top.GetIdsLan("IDS_BTN_AGGREGALL");</script>
										</td>
									</tr>
									<tr height="30">
										<td></td>
									</tr>
									<tr>
										<td>
											<img src="images/flecha_izda.png" id="btnRemove"  name="btnRemove" onclick="removeOrder(false);"/>
											<script type="text/javascript">document.getElementById("btnRemove").alt = top.GetIdsLan("IDS_BTN_REMOVE");</script>
										</td>
									</tr>
									<tr>
										<td>
											<img src="images/flecha_doble_izda.png" id="btnRemoveAll"  name="btnRemoveAll" onclick="removeOrder(true);"/>
											<script type="text/javascript">document.getElementById("btnRemoveAll").alt = top.GetIdsLan("IDS_BTN_REMOVEALL");</script>
										</td>
									</tr>
								</table>
							</td>
							<td class="tdBody">
								<select id="listOrder" name="listOrder" class="label" multiple="multiple" size="25" style="width:100%" ondblclick="changeSense();">
									<xsl:apply-templates select="iSicresQryFmt/Fields/Field/@Selected[.='1']"/>
								</select>
							</td>
							<td align="left" valign="bottom">
								<table>
									<tr>
										<td>
											<img src="images/arriba.gif" id="btnUp"  name="btnUp" onclick="upOrder();" />
											<script type="text/javascript">document.getElementById("btnUp").alt = top.GetIdsLan("IDS_BTN_UP");</script>
										</td>
									</tr>
									<tr>
										<td>
											<img src="images/abajo.gif" id="btnDown"  name="btnDown" onclick="downOrder();"/>
											<script type="text/javascript">document.getElementById("btnDown").alt = top.GetIdsLan("IDS_BTN_DOWN");</script>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="12">
							<td colspan="4"></td>
						</tr>
					</table>
				</div>

				<input type="hidden" id="dataOrder" name="99999"></input>
			</FORM>
			<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no" style="display:none">
			</iframe>
			<xsl:apply-templates select="iSicresQryFmt/Messages/Message"/>
			<xsl:apply-templates select="iSicresQryFmt/DBaseType"/>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="iSicresQryFmt/Control/@Type[.='Static']">
	<div tabIndex="-1" class="label">
		<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
		<label class="field"><xsl:value-of select="../Text"/></label>
	</div>
</xsl:template>

<xsl:template match="iSicresQryFmt/Control/@Type[.='ComboBox']">
	<SELECT tabIndex="1" class="combo">
		<xsl:choose>
			<xsl:when test="../FldId[.='503']">
				<xsl:attribute name="style">display:none; position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>

		<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
		<xsl:attribute name="comboFldId"><xsl:value-of select="../FldId"/></xsl:attribute>
		<xsl:apply-templates select="../Operator"/>
	</SELECT>

	<xsl:if test="../FldId[.='503']">
			<!-- Combo para inter. registral (no tiene ninguna funcionalidad más que ser visible para el usuario)-->
			<SELECT tabIndex="1" class="combo" disabled="disabled">
				<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
				<xsl:apply-templates select="../Operator"/>
			</SELECT>
	</xsl:if>

</xsl:template>

<xsl:template match="Operator">
	<OPTION>
		<xsl:attribute name="value"><xsl:value-of select="Value"/></xsl:attribute>
		<xsl:if test="@Selected[.='1']">
			<xsl:attribute name="selected">true</xsl:attribute>
		</xsl:if>
		<xsl:value-of select="Value"/>
	</OPTION>
</xsl:template>

<xsl:template match="iSicresQryFmt/Control/@Type[.='Edit']">
	<xsl:choose>
		<xsl:when test="../FldId[.='503']">
			<!-- Se inserta un combo donde el usuario solo puede indicar si esta involucrado en intercambio registral -->
			<select tabIndex="1" class="combo">
				<xsl:attribute name="id">combo_<xsl:value-of select="../FldId"/></xsl:attribute>
				<xsl:attribute name="onchange">changeComboIntRegistral(<xsl:value-of select="../FldId"/>)</xsl:attribute>
				<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
				<option value=""></option>
				<option value="0"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_NO"));</SCRIPT></option>
				<option value="1"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_SI"));</SCRIPT></option>
			</select>
			<!-- Ocultamos el campo input que contendrá el valor de la búsqueda -->
			<INPUT type="text" tabIndex="1" class="input">
				<xsl:attribute name="id"><xsl:value-of select="../FldId"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="../Text"/></xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
				<xsl:attribute name="style">display:none;position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			</INPUT>
		</xsl:when>
		<xsl:otherwise>
			<INPUT type="text" tabIndex="1" class="input" onkeydown="PulsarTecla(this, event);">
				<xsl:attribute name="id"><xsl:value-of select="../FldId"/></xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="../Text"/></xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
				<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
				<xsl:attribute name="Validated"><xsl:value-of select="../TblVal"/></xsl:attribute>
				<xsl:attribute name="onhelp">return VldHelp(event, '<xsl:value-of select="../FldId"/>','<xsl:value-of select="../TblFldId"/>','<xsl:value-of select="../TblVal"/>','<xsl:value-of select="../CaseSensitive"/>');</xsl:attribute>
				<xsl:choose>
					<xsl:when test="../CaseSensitive[.='CI']">
						<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
						<xsl:attribute name="onblur">OnBlurField(this);</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="style">text-transform:uppercase;position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
						<xsl:attribute name="onblur">this.value=this.value.toUpperCase();OnBlurField(this);</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</INPUT>
		</xsl:otherwise>
	</xsl:choose>

	<xsl:if test="../TblVal[.>'0']">
		<IMG src="./images/buscar2.gif" tabIndex="1">
			<xsl:attribute name="onclick">VldHelp(event, '<xsl:value-of select="../FldId"/>','<xsl:value-of select="../TblFldId"/>','<xsl:value-of select="../TblVal"/>','<xsl:value-of select="../CaseSensitive"/>');</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="style">position:absolute;cursor:pointer;<xsl:value-of select="../Style"/>;display:none</xsl:attribute>
			<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){this.onclick();}</xsl:attribute>
		</IMG>
	</xsl:if>
	<xsl:if test="../DataType[.='2' or .='3']">
		<IMG src="./images/calendarM.gif" tabIndex="1">
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="onclick">showCalendarEx(this, document.getElementById('<xsl:value-of select="../FldId"/>'), top.Idioma);</xsl:attribute>
			<xsl:attribute name="style">position:absolute;cursor:pointer;display:none;<xsl:value-of select="../Style"/></xsl:attribute>
		</IMG>
	</xsl:if>
</xsl:template>

<xsl:template match="Dimensions">
   <script language="javascript">
      getFormTam('<xsl:value-of select="Width"/>','<xsl:value-of select="Height"/>');
   </script>
</xsl:template>

<xsl:template match="PARAMS">
   <script language="javascript">
      getParams('<xsl:value-of select="ArchiveName"/>', <xsl:value-of select="ArchivePId"/>, <xsl:value-of select="ArchiveId"/>, <xsl:value-of select="FdrQryPId"/>, <xsl:value-of select="Permisos"/>, <xsl:value-of select="BookAdm"/>);
   </script>
</xsl:template>

<xsl:template match="Message">
	<SCRIPT TYPE="text/javascript">
		alert('<xsl:value-of select="."/>');
	</SCRIPT>
</xsl:template>

<xsl:template match="Field/@Selected[.='1']">
	<option>
		<xsl:attribute name="value"><xsl:value-of select="../Name"/></xsl:attribute>
		<xsl:attribute name="label"><xsl:value-of select="../Label"/> (ASC)</xsl:attribute>
		<xsl:attribute name="sense">ASC</xsl:attribute>
		<xsl:value-of select="../Label"/> (ASC)
	</option>
</xsl:template>

<xsl:template match="Field">
	<option>
		<xsl:attribute name="value"><xsl:value-of select="Name"/></xsl:attribute>
		<xsl:attribute name="sense">ASC</xsl:attribute>
		<xsl:value-of select="Label"/>
	</option>
</xsl:template>

<xsl:template match="Event">
	<script language="javascript">
		top.AttachEvent(document.getElementById('<xsl:value-of select="../Id"/>'), '<xsl:value-of select="EventName"/>', <xsl:value-of select="EventInfo"/>);
	</script>
</xsl:template>

<xsl:template match="Functions">
   <script language="javascript">
      <xsl:comment>
         <xsl:value-of select="."/>
      </xsl:comment>
   </script>
</xsl:template>

</xsl:stylesheet>