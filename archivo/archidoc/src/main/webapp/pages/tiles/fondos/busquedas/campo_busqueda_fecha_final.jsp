<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.fecha.final"/>:&nbsp;</td>
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
						    <td id="idFechaAValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaAFin" styleId="fechaAFin" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaSValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaSFin" styleId="fechaSFin" size="5" maxlength="5"/>&nbsp;
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
						    	<html:text property="fechaIniDFin" styleId="fechaIniDFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniMValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniMFin" styleId="fechaIniMFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaIniAValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniAFin" styleId="fechaIniAFin" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaIniSValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaIniSFin" styleId="fechaIniSFin" size="5" maxlength="5"/>&nbsp;
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
						    	<html:text property="fechaFinDFin" styleId="fechaFinDFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinMValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinMFin" styleId="fechaFinMFin" size="2" maxlength="2"/>-
						    </td>
						    <td id="idFechaFinAValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinAFin" styleId="fechaFinAFin" size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id="idFechaFinSValorFin" style="display:none;" nowrap="nowrap">
						    	<html:text property="fechaFinSFin" styleId="fechaFinSFin" size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaCompSuffix(document.forms['<c:out value="${actionMappingName}" />'],'fechaCompFin','Fin');</script>
	</td>
</tr>

<c:if test="${elemento.mostrarCalificadores == 'S'}">
	<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.calificadores.final"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<html:select property="calificadorFinal" styleId="calificadorFinal">
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

