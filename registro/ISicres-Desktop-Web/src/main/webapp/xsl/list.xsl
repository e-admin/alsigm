<?xml version="1.0" ?>
	<!--
		Pinta la pantalla de tablas de validacion (origen, destino, oficina, tipo de
		libro, tipo de asunto, tipo de transporte...)
	-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<HTML>
			<HEAD>
				<script language="javascript">
					document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');
				</script>
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css" />
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/list.css" />
				<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css" />
				<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/colors.js"></script>
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
				<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/list.js"></SCRIPT>
			</HEAD>
			<BODY onload="onLoadWin()" ondragstart="return false;" onfocus="top.SetTableFocus(document.getElementById('tblValidateList').rows[1]);" tabIndex="-1">
				<xsl:apply-templates select="Sicreslist/CONTEXT" />
				<xsl:apply-templates select="Sicreslist/FieldsInfo/FieldInfo" />
				<script>
					<xsl:comment>
         SetValues(<xsl:value-of select="Sicreslist/CONTEXT/INIT" />,
                     <xsl:value-of select="Sicreslist/CONTEXT/PASO" />,
                     <xsl:value-of select="Sicreslist/CONTEXT/TOTAL" />);
         parent.VldListPId = <xsl:value-of select="Sicreslist/PARAMS/LISTPID" />;
         parent.VldFrmDataBusc = <xsl:value-of select="Sicreslist/CONTEXT/FrmData" />;
         parent.VldFrmData = <xsl:value-of select="Sicreslist/CONTEXT/FrmData" />;
		 <xsl:if test="Sicreslist/CONTEXT/idCrl">
			parent.VldIdCrl= <xsl:value-of select="Sicreslist/CONTEXT/idCrl" />;
		 </xsl:if>
		 <xsl:if test="Sicreslist/CONTEXT/typeId">
			parent.VldTypeId = <xsl:value-of select="Sicreslist/CONTEXT/typeId" />;
		 </xsl:if>
		 <xsl:if test="Sicreslist/CONTEXT/typeBusc">
			parent.VldTypeBusc = <xsl:value-of select="Sicreslist/CONTEXT/typeBusc" />;
		 </xsl:if>
         parent.VldFrmDataFldId = <xsl:value-of select="Sicreslist/CONTEXT/FldId" />;
         top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "<xsl:value-of select='Sicreslist/CONTEXT/NAME' />");
      </xsl:comment>
				</script>
				<TABLE width="100%" align="center" cellpadding="0" cellspacing="0" border="0" id="tblValidateList" name="tblValidateList">
					<xsl:apply-templates select="Sicreslist/NODELIST/NODE" />
				</TABLE>
			</BODY>
		</HTML>
	</xsl:template>
	<xsl:template match="NODE">
		<tr height="5px" tabIndex="-1">
			<td colspan="4" />
		</tr>
		<xsl:choose>
			<xsl:when test="TYPE[.=0]">
				<TR class="Sep" valign="middle" onmouseover="onrowover(this,false);top.SetTableFocus(this.cells[1]);" onmouseout="onrowout(this)" onfocus="onrowover(this,true)" onblur="onrowout(this)" tabIndex="1">
					<TD width="15px" />
					<TD>
						<img src="images/arbol.gif" width="16" height="16" />
						<xsl:value-of select="NAME" />
					</TD>
					<TD width="0px" />
					<TD width="15px">
						<A href="#" tabIndex="1">
							<xsl:attribute name="onclick">DownValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;</xsl:attribute>
							<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){DownValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;}</xsl:attribute>
							<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
								<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							</xsl:if>
							<SCRIPT language="javascript"> document.write( '<img src="images/downarrow.gif" width="15" height="15" title="'
                  + top.GetIdsLan('IDS_ETQSIGNIVEL') + '" border="0" />' )</SCRIPT>
						</A>
					</TD>
				</TR>
			</xsl:when>
			<xsl:when test="TYPE[.&gt; 0]">
				<TR class="Sep" valign="middle" onmouseover="onrowover(this,true);top.SetTableFocus(this.cells[1]);" onmouseout="onrowout(this)" ondblclick="AsigValue(this)" onfocus="onrowover(this,true)" onblur="onrowout(this)" tabIndex="-1">
					<xsl:attribute name="Code">
						<xsl:value-of select="CODE" />
					</xsl:attribute>
					<xsl:attribute name="Description">
						<xsl:value-of select="NAME" />
					</xsl:attribute>
					<xsl:attribute name="ParentName">
						<xsl:value-of select="PARENTNAME" />
					</xsl:attribute>
					<xsl:attribute name="numDest">
						<xsl:value-of select="Id" />
					</xsl:attribute>
					<TD width="15px" />
					<TD tabIndex="1">
						<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){AsigValue(this.parentNode);return false;}</xsl:attribute>
						<xsl:attribute name="onfocus">onrowover(this.parentNode,true)</xsl:attribute>
						<xsl:attribute name="onblur">onrowout(this.parentNode,true)</xsl:attribute>
						<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
							<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="ENABLE[.='false']">
								<img src="images/unselect.gif" width="16" height="16" />
								<xsl:value-of select="CODE" /> - <xsl:value-of select="NAME" />
							</xsl:when>
							<xsl:otherwise>
								<img src="images/select.gif" width="16" height="16" />
								<xsl:value-of select="CODE" /> - <xsl:value-of select="NAME" />
							</xsl:otherwise>
						</xsl:choose>
					</TD>
					<TD>
						<xsl:if test="PARENTNAME[.!='']">
							<SCRIPT language="javascript">
								document.write( '<img src="images/treePadre.gif" width="15" height="15" title="'
									+ top.GetIdsLan('IDS_UNID_ADMIN_PADRE') + '" alt="'
									+ top.GetIdsLan('IDS_UNID_ADMIN_PADRE') + '" border="0" />' )
							</SCRIPT>
							<xsl:value-of select="PARENTNAME" />
						</xsl:if>
					</TD>
					<TD width="15px">
						<A href="#" tabIndex="1">
							<xsl:attribute name="onclick">UpValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;</xsl:attribute>
							<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){UpValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;}</xsl:attribute>
							<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
								<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							</xsl:if>
							<SCRIPT language="javascript">document.write( '<img src="images/uparrow.gif" width="15" height="15" title="'
                  + top.GetIdsLan('IDS_ETQNIVELANT') + '" border="0" />' )</SCRIPT>
						</A>
					</TD>

					<TD width="15px">
						<A href="#" tabIndex="1">
							<xsl:attribute name="onclick">DownValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;</xsl:attribute>
							<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){DownValues(<xsl:value-of select="TYPE" />,<xsl:value-of select="Id" />);return false;}</xsl:attribute>
							<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
								<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							</xsl:if>
							<SCRIPT language="javascript">document.write( '<img src="images/downarrow.gif" width="15" height="15" title="'
                  + top.GetIdsLan('IDS_ETQSIGNIVEL') + '" border="0" />' )</SCRIPT>
						</A>
					</TD>
				</TR>
			</xsl:when>
			<xsl:when test="TYPE[.=-1]">
				<TR class="Sep" valign="middle" onmouseover="onrowover(this,false);top.SetTableFocus(this.cells[1]);" onmouseout="onrowout(this)" onfocus="onrowover(this,false)" onblur="onrowout(this)" tabIndex="1">
					<TD width="15px" />
					<TD> No hay valores </TD>
					<TD width="15px">
						<A href="#" tabIndex="1">
							<xsl:attribute name="onclick">UpValues(-1,-1);return false;</xsl:attribute>
							<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){UpValues(-1,-1);return false;}</xsl:attribute>
							<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
								<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							</xsl:if>
							<SCRIPT language="javascript">document.write( '<img src="images/uparrow.gif" width="15" height="15" title="'
                  + top.GetIdsLan('IDS_ETQNIVELANT') + '" border="0" />' )</SCRIPT>
						</A>
					</TD>
					<TD width="15px" />
				</TR>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="Id">
						<TR class="Sep" height="18px" valign="middle" onmouseover="onrowover(this,true);" onmouseout="onrowout(this)" ondblclick="AsigValue(this)" onfocus="onrowover(this,true)" onblur="onrowout(this)" tabIndex="1">
							<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){AsigValue(this);return false;}</xsl:attribute>
							<xsl:if test="/Sicreslist/CONTEXT/CaseSensitive = 'CS'">
								<xsl:attribute name="style">text-transform:uppercase;</xsl:attribute>
							</xsl:if>
							<xsl:attribute name="Code">
								<xsl:value-of select="CODE" />
							</xsl:attribute>
							<xsl:attribute name="Description">
								<xsl:value-of select="NAME" />
							</xsl:attribute>
							<xsl:attribute name="numDest">
								<xsl:value-of select="Id" />
							</xsl:attribute>
							<TD width="15px" />
							<TD>
								<xsl:choose>
									<xsl:when test="ENABLE[.='false']">
										<img src="images/unselect.gif" width="16" height="16" />
										<xsl:if test="CODE=NAME">
											<xsl:value-of select="CODE" />
										</xsl:if>
										<xsl:if test="CODE!=NAME">
											<xsl:value-of select="CODE" /> - <xsl:value-of select="NAME" />
										</xsl:if>
									</xsl:when>
									<xsl:otherwise>
										<img src="images/select.gif" width="16" height="16" />
										<xsl:if test="CODE=NAME">
											<xsl:value-of select="CODE" />
										</xsl:if>
										<xsl:if test="CODE!=NAME">
											<xsl:value-of select="CODE" /> - <xsl:value-of select="NAME" />
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
							</TD>
							<TD width="0px" />
							<TD width="0px" />
						</TR>
					</xsl:when>
					<xsl:otherwise>
						<TR class="Sep" height="20px" valign="middle" onmouseover="onrowover(this,false);top.SetTableFocus(this.cells[1]);" onmouseout="onrowout(this)" onfocus="onrowover(this,false)" onblur="onrowout(this)" tabIndex="1">
							<TD width="15px" />
							<TD>
								<SCRIPT language="javascript">document.write( top.GetIdsLan( 'IDS_ERRORNOVALOR' ) )</SCRIPT>
							</TD>
							<TD width="0px" />
							<TD width="0px" />
						</TR>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="CONTEXT">
		<xsl:if test="PARENTNAME[.!='']">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr height="25px" valign="middle" align="center" class="Style7" tabIndex="-1">
					<td>
						<xsl:choose>
							<xsl:when test="PARENTREF[.!='']">
								<SCRIPT language="javascript">document.write("<xsl:value-of select="PARENTREF" />")</SCRIPT>
							</xsl:when>
							<xsl:otherwise>
								<SCRIPT language="javascript">document.write("<xsl:value-of select="PARENTNAME" />")</SCRIPT>
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
			</table>
			<input id="ParentName" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="PARENTREF" />
				</xsl:attribute>
			</input>
		</xsl:if>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="25px" valign="middle" align="center" class="Style8" tabIndex="-1">
				<td>
					<img src="images/trans.gif" width="1" height="1" />
				</td>
				<td width="18">
					<A href="#" onclick="FirtValues();return false;" tabIndex="1">
						<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){FirtValues();return false;}</xsl:attribute>
						<SCRIPT language="javascript">document.write( '<img src="images/firstarrow.gif" width="10" height="11" title="'
						+ top.GetIdsLan('IDS_ETQPRINCIPIO') + '" border="0" id="FirstImg"/>' )</SCRIPT>
					</A>
				</td>
				<td width="18">
					<A href="#" onclick="PrevValues();return false;" tabIndex="1">
						<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){PrevValues();return false;}</xsl:attribute>
						<SCRIPT language="javascript">document.write( '<img src="images/leftarrow.gif" width="10" height="11" title="'
						+ top.GetIdsLan('IDS_ETQANTERIOR') + '" border="0" id="PrevImg"/>' )
					</SCRIPT>
					</A>
				</td>
				<td width="140">
					<NOBR>| <xsl:value-of select="INIT" /> - <xsl:value-of select="END" />
					<SCRIPT language="javascript">document.write(' ' + top.GetIdsLan('IDS_PREPDE') + ' ')</SCRIPT>
					<xsl:value-of select="TOTAL" /> |
				</NOBR>
				</td>
				<td width="18">
					<A href="#" onclick="NextValues();return false;" tabIndex="1">
						<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){NextValues();return false;}</xsl:attribute>
						<SCRIPT language="javascript">document.write( '<img src="images/rightarrow.gif" width="10" height="11" title="'
						+ top.GetIdsLan('IDS_ETQSIGUIENTE') + '" border="0" id="NextImg"/>' )
					</SCRIPT>
					</A>
				</td>
				<td width="18">
					<A href="#" onclick="LastValues();return false;" tabIndex="1">
						<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){LastValues();return false;}</xsl:attribute>
						<SCRIPT language="javascript">document.write( '<img src="images/lastarrow.gif" width="10" height="11" title="'
						+ top.GetIdsLan('IDS_ETQFINAL') + '" border="0" id="LastImg"/>' )
					</SCRIPT>
					</A>
				</td>
				<td>
					<img src="images/trans.gif" width="1" height="1" />
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template match="FieldInfo">
		<SCRIPT language="javascript">SetSeekingField(<xsl:value-of select="/Sicreslist/FieldsInfo/FieldsNumber" />,'<xsl:value-of select="FieldLabel" />', '<xsl:value-of select="FieldName" />');</SCRIPT>
	</xsl:template>
</xsl:stylesheet>
