<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.fecha.inicial"/>:&nbsp;</td>
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
						    	<html:text property="fechaDIni" styleId="fechaDIni"  size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaMIni" styleId="fechaMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaAValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaAIni" styleId="fechaAIni" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaSValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaSIni" styleId="fechaSIni" size="5" maxlength="5"/>&nbsp;
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
						    	<html:text property="fechaIniDIni" styleId="fechaIniDIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniMIni" styleId="fechaIniMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniAValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniAIni" styleId="fechaIniAIni" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaIniSValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniSIni" styleId="fechaIniSIni" size="5" maxlength="5"/>&nbsp;
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
						    	<html:text property="fechaFinDIni" styleId="fechaFinDIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinMValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinMIni" styleId="fechaFinMIni" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinAValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinAIni" styleId="fechaFinAIni" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaFinSValorIni" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinSIni" styleId="fechaFinSIni" size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaCompSuffix(document.forms['<c:out value="${actionMappingName}" />'],'fechaCompIni','Ini');</script>
	</td>
</tr>

<c:if test="${elemento.mostrarCalificadores == 'S'}">
	<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.calificadores.inicial"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<html:select property="calificadorInicial" styleId="calificadorInicial">
			<html:option key="archigest.archivo.busqueda.form.calificadores.value.todos" value=""/>
			<html:option key="archivo.calificador.ant" value="ant"/>
			<html:option key="archivo.calificador.pos" value="pos"/>
			<html:option key="archivo.calificador.apr" value="apr"/>
			<html:option key="archivo.calificador.con" value="con"/>
			<html:option key="archivo.calificador.sup" value="sup"/>
			<html:option key="archivo.calificador.sic" value="sic"/>
			<html:option key="archivo.calificador.a.C." value="a.C."/>
			<html:option key="archivo.calificador.p.m." value="p.m."/>
			<html:option key="archivo.calificador.s.m." value="s.m."/>
			<html:option key="archivo.calificador.p.t." value="p.t."/>
			<html:option key="archivo.calificador.s.t." value="s.t."/>
			<html:option key="archivo.calificador.u.t." value="u.t."/>
			<html:option key="archivo.calificador.c." value="c."/>
			<html:option key="archivo.calificador.p." value="p."/>
			<html:option key="archivo.calificador.m." value="m."/>
			<html:option key="archivo.calificador.f." value="f."/>
			<html:option key="archivo.calificador.sf" value="sf"/>
		</html:select>
		&nbsp;
	</td>
	</tr>
</c:if>

