<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="suffix" value="_${elemento.nombre}"/>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><fmt:message key="${elemento.tituloKey}"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">

		<input type="text" name="genericoTextoLargo" id='genericoTextoLargo<c:out value="${suffix}"/>' class="input99"  value='<c:out value="${paramValues.genericoTextoLargo[elemento.index]}"/>'/>

		<input type="hidden" name="genericoIdTextoLargo" value='<c:out value="${elemento.valor}"/>'/>
		<input type="hidden" name="genericoEtiquetaTextoLargo" value='<c:out value="${elemento.tituloKey}"/>'/>

		<script language="javascript">
			camposConfigurablesTextoLargo.push('<c:out value="${suffix}"/>');
		</script>
	</td>
</tr>
