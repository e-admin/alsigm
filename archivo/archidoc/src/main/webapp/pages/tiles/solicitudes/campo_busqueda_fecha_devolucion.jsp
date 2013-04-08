<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.fecha.devolucion"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<html:select property="fechaCompFin" styleId="fechaCompFin" onchange="javascript:checkFechaCompSuffix(this.form,'fechaCompFin','Fin')">
						<html:option key="archigest.archivo.simbolo.igual.texto" value="="/>
						<html:option key="archigest.archivo.simbolo.mayor.texto" value="&gt;"/>
						<html:option key="archigest.archivo.simbolo.menor.texto" value="&lt;"/>
						<html:option key="archigest.archivo.simbolo.mayoroigual.texto" value="&gt;="/>
						<html:option key="archigest.archivo.simbolo.menoroigual.texto" value="&lt;="/>
						<html:option key="archigest.archivo.simbolo.rango.texto" value="[..]"/>
				    </html:select>
				</td>
			    <td width="10">&nbsp;</td>
				<td id="idFechaFin" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<html:select property="fechaFormatoFin" styleId="fechaFormatoFin" onchange="javascript:checkFechaFormatoSuffix(this, 'idFecha', 'Fin')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaDValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaDFin" styleId="fechaDFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaMValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaMFin" styleId="fechaMFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaAValorFin" style="display:none;">
						    	<html:text property="fechaAFin" styleId="fechaAFin" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaSValorFin" style="display:none;">
						    	<html:text property="fechaSFin" styleId="fechaSFin" size="5" maxlength="5"/>
						    </td>
					    </tr>
				    </table>
				</td>
				<td id="idRangoFechasFin" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
						    <td class="user_data"><bean:message key="archigest.archivo.desde"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaIniFormatoFin" styleId="fechaIniFormatoFin" onchange="javascript:checkFechaFormatoSuffix(this, 'idFechaIni','Fin')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaIniDValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniDFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniMValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniMFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniAValorFin" style="display:none;">
						    	<html:text property="fechaIniAFin" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaIniSValorFin" style="display:none;">
						    	<html:text property="fechaIniSFin" size="5" maxlength="5"/>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td class="user_data"><bean:message key="archigest.archivo.hasta"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaFinFormatoFin" styleId="fechaFinFormatoFin" onchange="javascript:checkFechaFormatoSuffix(this, 'idFechaFin','Fin')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaFinDValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinDFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinMValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinMFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinAValorFin" style="display:none;">
						    	<html:text property="fechaFinAFin" size="4" maxlength="4"/>
						    </td>
						    <td id="idFechaFinSValorFin" style="display:none;">
						    	<html:text property="fechaFinSFin" size="5" maxlength="5"/>
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaCompSuffix(document.forms[0],'fechaCompFin','Fin');</script>
	</td>
</tr>
