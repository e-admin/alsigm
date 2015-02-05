<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="suffix" value="_${elemento.nombre}"/>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><fmt:message key="${elemento.tituloKey}"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">

		<c:choose>
			<c:when test="${elemento.mostrarOperadores == 'S'}">
				<select name="genericoOperadorCampoNumerico" id='genericoOperadorCampoNumerico<c:out value="${suffix}"/>' onchange="javascript:checkGenericoOperadorNumerico('<c:out value="${suffix}"/>')">
					<option value="=" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '='}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.igual.texto"/> 
					</option>
					<option value="&gt;" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '&gt;'}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.mayor.texto" />
					</option>
					<option value="&lt;" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '&lt;'}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.menor.texto" />
					</option>
					<option value="&gt;=" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '&gt;='}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.mayoroigual.texto" />
					</option>
					<option value="&lt;=" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '&lt;='}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.menoroigual.texto" />
					</option>
					<option value="[..]" <c:if test="${paramValues.genericoOperadorCampoNumerico[elemento.index] == '[..]'}">selected</c:if>>
						<bean:message key="archigest.archivo.simbolo.rango.texto"/>
					</option>
				</select>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="genericoOperadorCampoNumerico" id='genericoOperadorCampoNumerico<c:out value="${suffix}"/>' value="="/>
			</c:otherwise>
		</c:choose>

		<input type="text" name="genericoCampoNumerico" id='genericoCampoNumerico<c:out value="${suffix}"/>' size="7" value='<c:out value="${paramValues.genericoCampoNumerico[elemento.index]}"/>'/>
		<input type="text" name="genericoCampoNumericoFin" id='genericoCampoNumericoFin<c:out value="${suffix}"/>' size="7" value='<c:out value="${paramValues.genericoCampoNumericoFin[elemento.index]}"/>'/>
		
		<input type="hidden" name="genericoIdCampoNumerico" value='<c:out value="${elemento.valor}"/>'/>
		<input type="hidden" name="genericoEtiquetaCampoNumerico" value='<c:out value="${elemento.tituloKey}"/>'/>

		<script language="javascript">
			checkGenericoOperadorNumerico('<c:out value="${suffix}"/>');
			camposConfigurablesNumericos.push('<c:out value="${suffix}"/>');
		</script>
	</td>
</tr>

