<%@page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
<h3><fmt:message key="intercambioRegistral.busquedaDestino.busquedaUnidadesTitulo" /></h3>
<div class="formRow">
<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoUnidad" /></label><input type="text" name="tramunitCodeToFind" id="tramunitCodeToFind" value="<c:out value='${tramunitCodeToFind}' />"/>
</div>
<div class="formRow">
<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreUnidad" /></label><input type="text" name="tramunitNameToFind" id="tramunitNameToFind" value="<c:out value='${tramunitNameToFind}' />"/>
</div>
<div class="formRow">
<input type="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.buscar" />" class="buscarUnidadesTramitacionButton button" />
<input type="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.cancelar" />" class="button" onclick="javascript:closeFancybox();return false;"/>
</div>
<br />
<div id="resultadosDCO">
<display:table  name="listaUnidadesTramitacionDCO" htmlId="unidadesTramitacion" id="unidadTramitacionDCO" requestURI="/BuscarUnidadesTramitacionDCO.do" pagesize="10">
 <display:column titleKey="intercambioRegistral.busquedaDestino.codigo">
					<a href="#" onclick="javascript:selecionarUnidadTramitacion('<c:out value="${unidadTramitacionDCO.id}"/>', '<c:out value='${unidadTramitacionDCO.nombre}'/>');return false;"><c:out value='${unidadTramitacionDCO.id}'/></a>
  </display:column>
  <display:column titleKey="intercambioRegistral.busquedaDestino.nombre">
					<a href="#" onclick="javascript:selecionarUnidadTramitacion('<c:out value="${unidadTramitacionDCO.id}"/>', '<c:out value='${unidadTramitacionDCO.nombre}'/>');return false;"><c:out value='${unidadTramitacionDCO.nombre}'/></a>
  </display:column>
    <display:column titleKey="intercambioRegistral.busquedaDestino.responsable">
					<a href="#" onclick="javascript:selecionarUnidadTramitacion('<c:out value="${unidadTramitacionDCO.id}"/>', '<c:out value='${unidadTramitacionDCO.nombre}'/>');return false;"><c:out value='${unidadTramitacionDCO.nombreUnidadOrganicaSuperior}'/></a>
  </display:column>

</display:table>
</div>
<script type="text/javascript">
<!--
function selecionarUnidadTramitacion(code, name){
	$('#tramunitCode').val(code);
	$('#tramunitName').val(name);
	closeFancybox();
}

//-->
</script>
