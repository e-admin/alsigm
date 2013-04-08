<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="suffix" value="_${elemento.nombre}"/>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><fmt:message key="${elemento.tituloKey}"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<c:choose>
			<c:when test="${elemento.mostrarOperadores == 'S'}">
				<select name="genericoOperadorTextoCorto" id='genericoOperadorTextoCorto<c:out value="${suffix}"/>'/>
					<option value="contiene" <c:if test="${paramValues.genericoOperadorTextoCorto[elemento.index] == 'contiene'}">selected</c:if>>
						<bean:message key="archigest.archivo.contiene"/>
					</option>
					<option value="igual"  <c:if test="${paramValues.genericoOperadorTextoCorto[elemento.index] == 'igual'}">selected</c:if>>
						<bean:message key="archigest.archivo.igual"/>
					</option>
					<option value="empieza" <c:if test="${paramValues.genericoOperadorTextoCorto[elemento.index] == 'empieza'}">selected</c:if>>
						<bean:message key="archigest.archivo.empiezaPor"/>
					</option>
					<option value="termina" <c:if test="${paramValues.genericoOperadorTextoCorto[elemento.index] == 'termina'}">selected</c:if>>
						<bean:message key="archigest.archivo.terminaPor"/>
					</option>
				</select>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="genericoOperadorTextoCorto" id='genericoOperadorTextoCorto<c:out value="${suffix}"/>' value="contiene"/>
			</c:otherwise>
		</c:choose>
		<input type="text" name="genericoTextoCorto" id='genericoTextoCorto<c:out value="${suffix}"/>' size="50" value='<c:out value="${paramValues.genericoTextoCorto[elemento.index]}"/>'/>

		<input type="hidden" name="genericoIdTextoCorto" value='<c:out value="${elemento.valor}"/>'/>
		<input type="hidden" name="genericoEtiquetaTextoCorto" value='<c:out value="${elemento.tituloKey}"/>'/>

		<script language="javascript">
			camposConfigurablesTextoCorto.push('<c:out value="${suffix}"/>');
		</script>
	</td>
</tr>
