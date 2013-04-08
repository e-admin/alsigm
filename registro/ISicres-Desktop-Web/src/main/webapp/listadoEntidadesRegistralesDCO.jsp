<%@page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>

<h3><fmt:message key="intercambioRegistral.busquedaDestino.busquedaEntidadesTitulo" /></h3>
<div class="formRow">
<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoEntidad" /></label><input type="text" name="codeEntityToFind" id="codeEntityToFind" value="<c:out value='${codeEntityToFind}' />"/>
</div>
<div class="formRow">
<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreEntidad" /></label><input type="text" name="nameEntityToFind" id="nameEntityToFind" value="<c:out value='${nameEntityToFind}' />"/>
</div>
<div class="formRow">
<input type="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.buscar" />" class="buscarEntidadesRegistralesButton button" />
<input type="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.cancelar" />" class="button" onclick="javascript:closeFancybox();return false;"/>
</div>
<br />
<div id="resultadosDCO">
<display:table  name="listaEntidadesRegistralesDCO" htmlId="entidadesRegistrales" id="entidadRegistralDCO" requestURI="/BuscarEntidadesRegistralesDCO.do" pagesize="10">
 <display:column titleKey="intercambioRegistral.busquedaDestino.codigo">
					<a href="#" onclick="javascript:selecionarEntidad('<c:out value="${entidadRegistralDCO.id}"/>', '<c:out value='${entidadRegistralDCO.nombre}'/>');return false;"><c:out value='${entidadRegistralDCO.id}'/></a>
  </display:column>
  <display:column titleKey="intercambioRegistral.busquedaDestino.nombre">
					<a href="#" onclick="javascript:selecionarEntidad('<c:out value="${entidadRegistralDCO.id}"/>', '<c:out value='${entidadRegistralDCO.nombre}'/>');return false;"><c:out value='${entidadRegistralDCO.nombre}'/></a>
  </display:column>
    <display:column titleKey="intercambioRegistral.busquedaDestino.responsable">
					<a href="#" onclick="javascript:selecionarEntidad('<c:out value="${entidadRegistralDCO.id}"/>', '<c:out value='${entidadRegistralDCO.nombre}'/>');return false;"><c:out value='${entidadRegistralDCO.nombreUnidadResponsable}'/></a>
  </display:column>
</display:table>
</div>
<script type="text/javascript">
<!--
function selecionarEntidad(code, name){
	$('#entityCode').val(code);
	$('#entityName').val(name);
	closeFancybox();
}
function closeFancybox()
{
	$.fancybox.close();
}

//-->
</script>
