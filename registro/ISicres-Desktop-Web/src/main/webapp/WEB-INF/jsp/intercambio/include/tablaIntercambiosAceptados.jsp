<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:if test="${estado==ESTADO_ENTRADA_ACEPTADO}">
	<!-- Aceptados. OJO. Se añade el evento abrirRegistroOInfoIntercambioRegistral sobre esta tabla por el id -->
	<display:table htmlId="bandejaEntradaAceptados" name="bandejaEntrada" class="report" cellspacing="0" cellpadding="5"
		id="row" requestURI="<%=requestUri%>" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaEntradaTableDecorator">

		<display:column><input id="idLibroParaRegistro_<c:out value='${row.idRegistro}'/>" type="hidden" value="<c:out value='${row.idLibro}'/>"/></display:column>
		<display:column property="numeroRegistro" titleKey="intercambioRegistral.tabla.numeroRegistro"></display:column>
		<!-- Fecha de estado es igual a Fecha de registro, por tanto, si indicamos la fecha de estado es posible ordenar la bandeja -->
		<display:column sortable="true" property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>
		<%-- Entidad Origen --%>
		<display:column sortable="true" property="nombreEntidadTramitacion" titleKey="intercambioRegistral.tabla.origen.entidadRegistral"></display:column>
		<%--Unidad Origen --%>
		<display:column sortable="true" property="nombreUnidadTramitacion" titleKey="intercambioRegistral.tabla.origen.unidadTramitacion"></display:column>

		<display:column property="estado" titleKey="intercambioRegistral.tabla.estado"></display:column>
		<display:column sortable="true" property="fechaIntercambioRegistral" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaIntercambio"></display:column>
		<display:column sortable="true" property="idIntercambioRegistral" titleKey="intercambioRegistral.tabla.idIntercambioRegistral"></display:column>
		<display:column sortable="true" property="username" titleKey="intercambioRegistral.tabla.usuario"></display:column>
		<display:column  style="width:40px;">
			<a class="linkInfoSolicitudIntercambio" href="MostrarIntercambioRegistral.do?idIntercambio=<c:out value='${row.idIntercambioInterno}'/>">
				<img src="./images/information.png" alt="<fmt:message key="intercambioRegistral.tabla.masInfo"/>"/>
			</a>
		</display:column>
		<display:setProperty name="basic.msg.empty_list">
			<fmt:message key="intercambioRegistral.tabla.vacia" />
		</display:setProperty>
		<display:setProperty name="paging.banner.item_name">
			<fmt:message key="intercambioRegistral.tabla.item_name" />
		</display:setProperty>
		<display:setProperty name="paging.banner.items_name">
			<fmt:message key="intercambioRegistral.tabla.items_name" />
		</display:setProperty>

	</display:table>
</c:if>
