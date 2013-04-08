<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.fecha"/>:&nbsp;</td>
	<td class="tdDatosFicha">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<html:select property="fechaComp" styleId="fechaComp" onchange="javascript:checkFechaComp(this.form)">
						<html:option key="archigest.archivo.simbolo.igual.texto" value="="/>
						<html:option key="archigest.archivo.simbolo.mayor.texto" value="&gt;"/>
						<html:option key="archigest.archivo.simbolo.menor.texto" value="&lt;"/>
						<html:option key="archigest.archivo.simbolo.mayoroigual.texto" value="&gt;="/>
						<html:option key="archigest.archivo.simbolo.menoroigual.texto" value="&lt;="/>
						<html:option key="archigest.archivo.simbolo.rango.texto" value="[..]"/>
				    </html:select>
				</td>
			    <td width="10">&nbsp;</td>
				<td id="idFecha" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<html:select property="fechaFormato" styleId="fechaFormato" onchange="javascript:checkFechaFormato(this, 'idFecha')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaDValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaD" styleId="fechaD" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaMValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaM" styleId="fechaM" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaAValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaA" styleId="fechaA" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaSValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaS" styleId="fechaS" size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>
				<td id="idRangoFechas" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
						    <td class="user_data"><bean:message key="archigest.archivo.desde"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaIniFormato" styleId="fechaIniFormato" onchange="javascript:checkFechaFormato(this, 'idFechaIni')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaIniDValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniD" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniMValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniM" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniAValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniA" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaIniSValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniS" size="5" maxlength="5"/>&nbsp;
						    </td>
						    <td width="10">&nbsp;</td>
						    <td class="user_data"><bean:message key="archigest.archivo.hasta"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html:select property="fechaFinFormato" styleId="fechaFinFormato" onchange="javascript:checkFechaFormato(this, 'idFechaFin')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
							    </html:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaFinDValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinD" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinMValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinM" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinAValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinA" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaFinSValor" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinS" size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaComp(document.forms['<c:out value="${actionMappingName}" />']);</script>
	</td>
</tr>

