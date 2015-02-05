<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
	<head>
		<script language="javascript">														
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/tables.css" />
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/colors.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/cons_tam.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/listview.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/vlddirinter.js"></script>
		<script language="javascript">
			top.Idioma = top.ParamValue(document.location.search,"Idioma");
			top.numIdioma = top.ParamValue(document.location.search,"numIdioma");
			top.SessionPId = top.ParamValue(document.location.search,"SessionPId");
			document.title = top.GetIdsLan("IDS_TIT_DIRINTER");

			var rowSelected = -1;

			function OnLoad()
			{
				var tabData = document.getElementById("tbDoms");

				if (tabData != null) {
					var rowNull = document.getElementById("null");
					var space = (document.body.clientHeight) - tabData.clientHeight + rowNull.clientHeight - 32;

					if (space > 13)	{
						rowNull.height = space.toString();
					}
					else {
						rowNull.height = "13";
					}
					
					rowSelected = 1;
					OnRowClick(tabData.rows[rowSelected]);
					
				}
				else {
					document.getElementById("btnOK").style.display = "none";
					document.getElementById("btnOK").disabled = true;
				}
			}

			function OnRowClick(row)
			{
				if (rowSelected != -1) {
					document.getElementById("tbDoms").rows[rowSelected].style.backgroundColor = "#fafcff";
					document.getElementById("tbDoms").rows[rowSelected].cells[0].firstChild.checked = false;
				}

				row.style.backgroundColor = g_color4;
				row.cells[0].firstChild.checked = true;
				rowSelected = row.rowIndex;
				top.SetTableFocus(row);
			}

			function OnRowDblClick(row)
			{
				OnRowClick(row);
				OnOK();
			}

			function OnOK()
			{
				if (!document.getElementById("btnOK").disabled)  {
					var tabData = document.getElementById("tbDoms");
					var idAddress = tabData.rows[rowSelected].getAttribute("idAddress");
					var strAddr = top.GetInnerText(tabData.rows[rowSelected].cells[1]);
					var strCod = top.GetInnerText(tabData.rows[rowSelected].cells[2]);
					var strCiu = top.GetInnerText(tabData.rows[rowSelected].cells[3]);
					var strProv = top.GetInnerText(tabData.rows[rowSelected].cells[4]);
		
					window.returnValue = idAddress + "|" + strAddr + "|" + strCod + "|" + strCiu + "|" + strProv + "||";
					window.close();
				}
	
				return;
			}

			function OnPressKey(aEvent)
			{
				var key = top.GetKeyCode(aEvent);
				
				switch(key) {
					case 13: {
						document.getElementById("btnOK").click();
						break;
					}
					case 27: {
						document.getElementById("btnClose").click();
						break;
					}
				}
			}

						
	   	</script>
	</head>
	<body onload="OnLoad();" onunload="top.EnableEvents(window.opener);" tabindex="-1" onkeydown="OnPressKey(event);">
		<TABLE width="100%" height="100%" border="0">
      			<xsl:choose>
         			<xsl:when test="Domicilios/Domicilio">
					<tr><td>
						<div id="listDomici" class="dirInterScrollbarY">
							<TABLE id="tbDoms" class="report" width="99%" align="left" cellpadding="0" cellspacing="1" border="0" tabindex="-1" style="border-collapse:collapse">
								<THEAD tabindex="-1">
									<TR>
										<TH class="report" width="5"></TH>
										<TH class="report" align="left" width="140">
											<script language="javascript">
												document.write(top.GetIdsLan("IDS_DIRECCION"));
											</script>
										</TH>
										<TH class="report" align="left" width="80">
											<script language="javascript">
												document.write(top.GetIdsLan("IDS_COD_POSTAL_EX"));
											</script>
										</TH>
										<TH class="report" align="left" width="120">
											<script language="javascript">
												document.write(top.GetIdsLan("IDS_CIUDAD"));
											</script>
										</TH>
										<TH class="report" align="left" width="130">
											<script language="javascript">
												document.write(top.GetIdsLan("IDS_POBLACION"));
											</script>
										</TH>
									</TR>
								</THEAD>
								<TBODY tabindex="-1">
									<xsl:apply-templates select="Domicilios/Domicilio"/>
									<tr id="null" class="Style5">
										<td class="report" width="5"></td>
										<td class="report" width="140"></td>
										<td class="report" width="80"></td>
										<td class="report" width="120"></td>
										<td class="report" width="130"></td>
									</tr>
								</TBODY>
							</TABLE>
						</div>
					</td></tr>
	      			</xsl:when>
	      			<xsl:otherwise>
					<tr height="89%"><td>
						<table><tr>
							<td width="1%"></td>
							<td align="center">
								<h4><script language="javascript">
									document.write(top.GetIdsLan("IDS_NOT_DIRINTER") + " " + top.GetIdsLan("IDS_NOT_DIR_SEL"));
								</script></h4>
							</td>
							<td width="1%"></td>
						</tr></table>
					</td></tr>
         			</xsl:otherwise>
	   		</xsl:choose>
			<tr>
				<td align="right">
					<input id="btnOK" class="button" type="button" onclick="OnOK();">
						<SCRIPT TYPE="text/javascript">document.getElementById("btnOK").value = top.GetIdsLan("IDS_BTNACEPTAR");</SCRIPT>
					</input>
					<input id="btnClose" class="button" type="button" onclick="window.close();window.returnValue='';">
						<SCRIPT TYPE="text/javascript">document.getElementById("btnClose").value = top.GetIdsLan("IDS_OPCCERRAR");</SCRIPT>
					</input>
				</td>
			</tr>
   		</TABLE>
	</body>
	</html>   
</xsl:template>	

<xsl:template match="Domicilios/Domicilio">
	<tr id="rowDatas" onclick="OnRowClick(this);" ondblclick="OnRowDblClick(this);" class="Style5">
		<xsl:attribute name="idAddress"><xsl:value-of select="Id"/></xsl:attribute>
		<td class="report" width="5"><input type="radio" id="checkrow"></input></td>
		<td class="report" width="140"><xsl:value-of select='Direccion'/></td>
		<td class="report" width="80"><xsl:value-of select='CodPostal'/></td>
		<td class="report" width="120"><xsl:value-of select='Poblacion'/></td>
		<td class="report" width="130"><xsl:value-of select='Provincia'/></td>
	</tr>
</xsl:template>
</xsl:stylesheet>
