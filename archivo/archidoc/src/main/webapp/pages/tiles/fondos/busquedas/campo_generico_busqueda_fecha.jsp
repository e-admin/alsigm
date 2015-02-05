<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="suffix" value="_${elemento.nombre}"/>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><fmt:message key="${elemento.tituloKey}"/>:&nbsp;</td>
	<td class="tdDatosFicha">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
				    <input type="hidden" name="genericoIdFecha" value='<c:out value="${elemento.valor}"/>'/>
					<input type="hidden" name="genericoEtiquetaFecha" value='<c:out value="${elemento.tituloKey}"/>'/>

					<select name="genericoFechaComp" id="genericoFechaComp<c:out value="${suffix}"/>" onchange="javascript:checkGenericoFechaComp('<c:out value="${suffix}"/>')"/>
						<option value="=" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '='}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.igual.texto"/>
						</option>
						<option value="&gt;" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '>'}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.mayor.texto"/>
						</option>
						<option value="&lt;" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '<'}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.menor.texto"/>
						</option>
						<option value="&gt;=" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '>='}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.mayoroigual.texto"/>
						</option>
						<option value="&lt;=" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '<='}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.menoroigual.texto"/>
						</option>
						<option value="[..]" <c:if test="${paramValues.genericoFechaComp[elemento.index] == '[..]'}">selected</c:if>>
							<bean:message key="archigest.archivo.simbolo.rango.texto"/>
						</option>
					</select>
				</td>
				<td width="10">&nbsp;</td>
				<td id='idFecha<c:out value="${suffix}"/>' style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<select name="genericoFechaFormato" id="genericoFechaFormato<c:out value="${suffix}"/>" onchange="javascript:checkGenericoFechaFormato(this, 'idFecha','<c:out value="${suffix}"/>')">
									<option value="AAAA" <c:if test="${paramValues.genericoFechaFormato[elemento.index] == 'AAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.AAAA"/>
									</option>
									<option value="MMAAAA" <c:if test="${paramValues.genericoFechaFormato[elemento.index] == 'MMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.MMAAAA"/>
									</option>
									<option value="DDMMAAAA" <c:if test="${paramValues.genericoFechaFormato[elemento.index] == 'DDMMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.DDMMAAAA"/>
									</option>
									<option value="S" <c:if test="${paramValues.genericoFechaFormato[elemento.index] == 'S'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.S"/>
									</option>
								</select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id='idFechaDValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaD" id='genericoFechaD<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaD[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaMValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaM" id='genericoFechaM<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaM[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaAValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaA" id='genericoFechaA<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaA[elemento.index]}"/>' size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id='idFechaSValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaS" id='genericoFechaS<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaS[elemento.index]}"/>' size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>

				<td id='idRangoFechas<c:out value="${suffix}"/>' style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
						    <td class="user_data">&nbsp;<bean:message key="archigest.archivo.desde"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<select name="genericoFechaIniFormato" id="genericoFechaIniFormato<c:out value="${suffix}"/>" onchange="javascript:checkGenericoFechaFormato(this, 'idFechaIni','<c:out value="${suffix}"/>')">
									<option value="AAAA" <c:if test="${paramValues.genericoFechaIniFormato[elemento.index] == 'AAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.AAAA"/>
									</option>
									<option value="MMAAAA" <c:if test="${paramValues.genericoFechaIniFormato[elemento.index] == 'MMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.MMAAAA"/>
									</option>
									<option value="DDMMAAAA" <c:if test="${paramValues.genericoFechaIniFormato[elemento.index] == 'DDMMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.DDMMAAAA"/>
									</option>
									<option value="S" <c:if test="${paramValues.genericoFechaIniFormato[elemento.index] == 'S'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.S"/>
									</option>
								</select>
						    </td>
						  	<td width="10">&nbsp;</td>
						    <td id='idFechaIniDValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaIniD" id='genericoFechaIniD<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaIniD[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaIniMValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaIniM" id='genericoFechaIniM<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaIniM[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaIniAValor<c:out value="${suffix}"/>' style="display:none;">
						    	<input type="text" name="genericoFechaIniA" id='genericoFechaIniA<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaIniA[elemento.index]}"/>' size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id='idFechaIniSValor<c:out value="${suffix}"/>' style="display:none;">
						    	<input type="text" name="genericoFechaIniS" id='genericoFechaIniS<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaIniS[elemento.index]}"/>' size="5" maxlength="5"/>&nbsp;
						    </td>

							<td width="10">&nbsp;</td>
						    <td class="user_data"><bean:message key="archigest.archivo.hasta"/>:</td>
						    <td width="5">&nbsp;</td>
							<td>
								<select name="genericoFechaFinFormato" id="genericoFechaFinFormato<c:out value="${suffix}"/>" onchange="javascript:checkGenericoFechaFormato(this, 'idFechaFin','<c:out value="${suffix}"/>')">
									<option value="AAAA" <c:if test="${paramValues.genericoFechaFinFormato[elemento.index] == 'AAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.AAAA"/>
									</option>
									<option value="MMAAAA" <c:if test="${paramValues.genericoFechaFinFormato[elemento.index] == 'MMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.MMAAAA"/>
									</option>
									<option value="DDMMAAAA" <c:if test="${paramValues.genericoFechaFinFormato[elemento.index] == 'DDMMAAAA'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.DDMMAAAA"/>
									</option>
									<option value="S" <c:if test="${paramValues.genericoFechaFinFormato[elemento.index] == 'S'}">selected</c:if>>
										<bean:message key="archigest.archivo.formato.S"/>
									</option>
								</select>
						    </td>
						  	<td width="10">&nbsp;</td>
						    <td id='idFechaFinDValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaFinD" id='genericoFechaFinD<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaFinD[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaFinMValor<c:out value="${suffix}"/>' style="display:none;" nowrap="nowrap">
						    	<input type="text" name="genericoFechaFinM" id='genericoFechaFinM<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaFinM[elemento.index]}"/>' size="2" maxlength="2"/>-
						    </td>
						    <td id='idFechaFinAValor<c:out value="${suffix}"/>' style="display:none;">
						    	<input type="text" name="genericoFechaFinA" id='genericoFechaFinA<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaFinA[elemento.index]}"/>' size="4" maxlength="4"/>&nbsp;
						    </td>
						    <td id='idFechaFinSValor<c:out value="${suffix}"/>' style="display:none;">
						    	<input type="text" name="genericoFechaFinS" id='genericoFechaFinS<c:out value="${suffix}"/>' value='<c:out value="${paramValues.genericoFechaFinS[elemento.index]}"/>' size="5" maxlength="5"/>&nbsp;
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>

		checkGenericoFechaComp('<c:out value="${suffix}"/>');
		camposConfigurablesFecha.push('<c:out value="${suffix}"/>');
		</script>
	</td>
</tr>



<c:choose>
<c:when test="${elemento.mostrarCalificadores == 'S'}">
	<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.calificadores"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<select name="genericoFechaCalificador" id='genericoFechaCalificador<c:out value="${suffix}"/>'>
			<option value="">
				<bean:message key="archigest.archivo.busqueda.form.calificadores.value.todos" />
			</option>
			<option value="ant" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'ant'}">selected</c:if>>
				<bean:message key="archivo.calificador.ant"/>
			</option>

			<option value="pos" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'pos'}">selected</c:if>>
				<bean:message key="archivo.calificador.pos"/>
			</option>

			<option value="apr" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'apr'}">selected</c:if>>
				<bean:message key="archivo.calificador.apr"/>
			</option>

			<option value="con" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'con'}">selected</c:if>>
				<bean:message key="archivo.calificador.con"/>
			</option>

			<option value="sup" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'sup'}">selected</c:if>>
				<bean:message key="archivo.calificador.sup"/>
			</option>

			<option value="sic" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'sic'}">selected</c:if>>
				<bean:message key="archivo.calificador.sic"/>
			</option>

			<option value="a.C." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'a.C.'}">selected</c:if>>
				<bean:message key="archivo.calificador.a.C."/>
			</option>

			<option value="p.m." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'p.m.'}">selected</c:if>>
				<bean:message key="archivo.calificador.p.m." />
			</option>

			<option value="s.m." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 's.m.'}">selected</c:if>>
				<bean:message key="archivo.calificador.s.m." />
			</option>

			<option value="p.t."  <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'p.t.'}">selected</c:if>>
				<bean:message key="archivo.calificador.p.t."/>
			</option>

			<option value="s.t."  <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 's.t.'}">selected</c:if>>
				<bean:message key="archivo.calificador.s.t."/>
			</option>

			<option value="u.t." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'u.t.'}">selected</c:if>>
				<bean:message key="archivo.calificador.u.t."/>
			</option>

			<option value="c." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'c.'}">selected</c:if>>
				<bean:message key="archivo.calificador.c."/>
			</option>
			<option value="p." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'p.'}">selected</c:if>>
				<bean:message key="archivo.calificador.p."/>
			</option>

			<option value="m." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'm.'}">selected</c:if>>
				<bean:message key="archivo.calificador.m."/>
			</option>

			<option value="f." <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'f.'}">selected</c:if>>
				<bean:message  key="archivo.calificador.f."/>
			</option>
			<option value="sf" <c:if test="${paramValues.genericoFechaCalificador[elemento.index] == 'sf'}">selected</c:if>>
				<bean:message key="archivo.calificador.sf"/>
			</option>
		</select>
	</td>
	</tr>
</c:when>
<c:otherwise>
	<input type="hidden" name="genericoFechaCalificador" id='genericoFechaCalificador<c:out value="${suffix}"/>'>
</c:otherwise>
</c:choose>
