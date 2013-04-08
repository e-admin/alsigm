<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<HTML>
			<HEAD>
				<script language="javascript">
					document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
				</script>
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css" />
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css" />
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css" />
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/qryfmt.js"></SCRIPT>
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
			</HEAD>
			<BODY onload="OnWindowLoad();"
				onfocus="AsigFocus();" scroll="auto"
				ondragstart="return false;" tabIndex="-1">
				<xsl:apply-templates select="iSicresQryFmt/Dimensions"/>
				<xsl:apply-templates select="iSicresQryFmt/PARAMS"/>
				<FORM action="GetQryWhere.jsp" method="post" id="QryFmtForm" name="QryFmtForm" target="GetResp">
					<xsl:attribute name="onsubmit">DisableQryControls(true);DoSubmitReportFmt();</xsl:attribute>
					<xsl:for-each select="iSicresQryFmt/Control">
						<xsl:choose>
							<xsl:when test="@Type[.='Static']">
								<DIV tabIndex="-1">
									<xsl:attribute name="class">label</xsl:attribute>
									<xsl:attribute name="style">position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
									<label class="field"><xsl:value-of select="Text"/></label>
								</DIV>
							</xsl:when>
							<xsl:when test="@Type[.='ComboBox']">
								<SELECT tabIndex="1">
									<xsl:attribute name="class">combo</xsl:attribute>
									<xsl:attribute name="comboFldId"><xsl:value-of select="FldId"/></xsl:attribute>

									<xsl:choose>
										<xsl:when test="FldId[.='503']">
											<xsl:attribute name="style">display:none; position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<xsl:attribute name="style">position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>

									<xsl:attribute name="name"><xsl:value-of select="Id"/></xsl:attribute>
									<xsl:if test="./Disabled='1'">
										<xsl:attribute name="disabled">true</xsl:attribute>
									</xsl:if>

									<xsl:for-each select="Operator">
										<OPTION>
											<xsl:attribute name="value"><xsl:value-of select="Value"/></xsl:attribute>
											<xsl:if test="@Selected[.='1']">
												<xsl:attribute name="selected">true</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="Value"/>
										</OPTION>
									</xsl:for-each>
								</SELECT>

								<xsl:if test="FldId[.='503']">
										<!-- Combo para inter. registral (no tiene ninguna funcionalidad más que ser visible para el usuario)-->
										<SELECT tabIndex="1" class="combo" disabled="disabled">
											<xsl:attribute name="style">position:absolute;<xsl:value-of select="Style"/></xsl:attribute>
											<xsl:for-each select="Operator">
												<OPTION>
													<xsl:attribute name="value"><xsl:value-of select="Value"/></xsl:attribute>
													<xsl:if test="@Selected[.='1']">
														<xsl:attribute name="selected">true</xsl:attribute>
													</xsl:if>
													<xsl:value-of select="Value"/>
												</OPTION>
											</xsl:for-each>
										</SELECT>
								</xsl:if>

							</xsl:when>
							<xsl:when test="@Type[.='Edit']">
								<xsl:choose>
									<xsl:when test="FldId[.='503']">
										<!-- Se inserta un combo donde el usuario solo puede indicar si esta involucrado en intercambio registral -->
										<select tabIndex="1" class="combo">
											<xsl:attribute name="id">combo_<xsl:value-of select="FldId"/></xsl:attribute>
											<xsl:attribute name="onchange">changeComboIntRegistral(<xsl:value-of select="FldId"/>)</xsl:attribute>
											<xsl:attribute name="style">position:absolute;<xsl:value-of select="Style"/></xsl:attribute>
											<option value=""></option>
											<option value="0"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_NO"));</SCRIPT></option>
											<option value="1"><SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_SI"));</SCRIPT></option>
										</select>
										<!-- Ocultamos el campo input que contendrá el valor de la búsqueda -->
										<INPUT type="text" tabIndex="1">
											<xsl:attribute name="class">input</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="Text"/></xsl:attribute>
											<xsl:attribute name="id"><xsl:value-of select="FldId"/></xsl:attribute>
											<xsl:attribute name="name"><xsl:value-of select="Id"/></xsl:attribute>
											<xsl:attribute name="style">display:none;position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
										</INPUT>
									</xsl:when>
									<xsl:otherwise>
										<INPUT type="text" tabIndex="1">
											<xsl:attribute name="class">input</xsl:attribute>
											<xsl:attribute name="value"><xsl:value-of select="Text"/></xsl:attribute>
											<xsl:attribute name="id"><xsl:value-of select="FldId"/></xsl:attribute>
											<xsl:attribute name="name"><xsl:value-of select="Id"/></xsl:attribute>
											<xsl:attribute name="Validated"><xsl:value-of select="TblVal"/></xsl:attribute>
											<xsl:attribute name="onhelp">imgHelpRpt(event, '<xsl:value-of select="Id"/>', '<xsl:value-of select="FldId"/>','<xsl:value-of select="TblFldId"/>','<xsl:value-of select="TblVal"/>')</xsl:attribute>
											<xsl:attribute name="onkeydown">PulsarTecla(this, event)</xsl:attribute>
											<xsl:if test="./Disabled='1'">
												<xsl:attribute name="disabled">true</xsl:attribute>
											</xsl:if>
											<xsl:choose>
												<xsl:when test="CaseSensitive[.='CI']">
													<xsl:attribute name="style">position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
													<xsl:attribute name="onblur">OnBlurField(this)</xsl:attribute>
												</xsl:when>
												<xsl:otherwise>
													<xsl:attribute name="style">text-transform:uppercase;position: absolute;<xsl:value-of select="Style"/></xsl:attribute>
													<xsl:attribute name="onblur">this.value=this.value.toUpperCase();OnBlurField(this)</xsl:attribute>
												</xsl:otherwise>
											</xsl:choose>
										</INPUT>


										<xsl:if test="TblVal[.>'0'] and not(./Disabled='1')">
											<IMG src="./images/buscar2.gif" tabIndex="1">
												<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){onclick();}</xsl:attribute>
												<xsl:attribute name="onclick">imgHelpRpt(event, '<xsl:value-of select="Id"/>', '<xsl:value-of select="FldId"/>','<xsl:value-of select="TblFldId"/>','<xsl:value-of select="TblVal"/>','<xsl:value-of select="CaseSensitive"/>')</xsl:attribute>
												<xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
												<xsl:attribute name="style">position: absolute; cursor:pointer;<xsl:value-of select="Style"/>;display:none</xsl:attribute>
												<xsl:attribute name="isHelp">true</xsl:attribute>
											</IMG>
										</xsl:if>
										<xsl:if test="DataType[.='2' or .='3']">
											<IMG src="./images/calendarM.gif" tabIndex="1">
												<xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
												<xsl:attribute name="onclick">showCalendarEx(this, document.getElementById('<xsl:value-of select="FldId"/>'), top.Idioma);</xsl:attribute>
												<xsl:attribute name="style">position: absolute;cursor:pointer;display:none;<xsl:value-of select="Style"/></xsl:attribute>
											</IMG>
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
				</xsl:choose>
			</xsl:for-each>
			<INPUT type="submit" tabIndex="1" id="btSubmit" style="display:none"></INPUT>
		</FORM>
		<iframe id="GetResp" name="GetResp" SRC="blank.htm" frameborder="0" scrolling="no"
			style="HEIGHT: 345px; LEFT: 5px; POSITION: absolute; TOP: 5px; WIDTH: 450px; visibility:hidden">
		</iframe>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="Dimensions">
   <script language="javascript">
      getFormTam('<xsl:value-of select="Width"/>','<xsl:value-of select="Height"/>');
   </script>
</xsl:template>

<xsl:template match="PARAMS">
   <script language="javascript">
      getParams('<xsl:value-of select="ArchiveName"/>', <xsl:value-of select="ArchivePId"/>, <xsl:value-of select="ArchiveId"/>, <xsl:value-of select="FdrQryPId"/>, <xsl:value-of select="Permisos"/>);
   </script>
</xsl:template>

</xsl:stylesheet>