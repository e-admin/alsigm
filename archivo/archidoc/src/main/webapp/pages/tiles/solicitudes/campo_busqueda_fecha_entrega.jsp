<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.fechaEntrega"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<html:select property="fechaCompIni" styleId="fechaCompIni" onchange="javascript:checkFechaCompSuffix(this.form,'fechaCompIni','Ini')">
						<html:option key="archigest.archivo.simbolo.igual.texto" value="="/>
						<html:option key="archigest.archivo.simbolo.mayor.texto" value="&gt;"/>
						<html:option key="archigest.archivo.simbolo.menor.texto" value="&lt;"/>
						<html:option key="archigest.archivo.simbolo.mayoroigual.texto" value="&gt;="/>
						<html:option key="archigest.archivo.simbolo.menoroigual.texto" value="&lt;="/>
						<html:option key="archigest.archivo.simbolo.rango.texto" value="[..]"/>
				    </html:select>

				</td>
				<td width="10">&nbsp;</td>
				<td id="idFechaIni" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<html:select property="fechaFormatoIni" styleId="fechaFormatoIni" onchange="javascript:checkFechaFormatoSuffix(this, 'idFecha', 'Ini')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaDValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text styleId="fechaDIni" property="fechaDIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text styleId="fechaMIni" property="fechaMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaAValorIni" style="display:none;">
						    	<html:text styleId="fechaAIni" property="fechaAIni" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaSValorIni" style="display:none;">
						    	<html:text styleId="fechaSIni" property="fechaSIni" size="5" maxlength="5"/>
						    </td>
					    </tr>
				    </table>
				</td>
				<td id="idRangoFechasIni" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
						    <td class="user_data"><bean:message key="archigest.archivo.desde"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaIniFormatoIni" styleId="fechaIniFormatoIni" onchange="javascript:checkFechaFormatoSuffix(this, 'idFechaIni','Ini')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaIniDValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniDIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniAValorIni" style="display:none;">
						    	<html:text property="fechaIniAIni" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaIniSValorIni" style="display:none;">
						    	<html:text property="fechaIniSIni" size="5" maxlength="5"/>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td class="user_data"><bean:message key="archigest.archivo.hasta"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaFinFormatoIni" styleId="fechaFinFormatoIni" onchange="javascript:checkFechaFormatoSuffix(this, 'idFechaFin','Ini')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaFinDValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinDIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinAValorIni" style="display:none;">
						    	<html:text property="fechaFinAIni" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaFinSValorIni" style="display:none;">
						    	<html:text property="fechaFinSIni" size="5" maxlength="5"/>
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaCompSuffix(document.forms[0],'fechaCompIni','Ini');</script>
	</td>
</tr>