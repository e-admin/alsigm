<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<display:table  htmlId="bandejaSalida" name="sessionScope.bandejaSalida" class="report" cellspacing="0" cellpadding="5"
	id="row" requestURI="<%=requestUri %>" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaSalidaTableDecorator">

	<display:column><input id="idLibroParaRegistro_<c:out value='${row.idRegistro}'/>" type="hidden" value="<c:out value='${row.idLibro}'/>"/></display:column>

	<display:column sortable="true" property="numeroRegistro" titleKey="intercambioRegistral.tabla.numeroRegistro"></display:column>

	<display:column sortable="true" property="fechaRegistro" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>

	<display:column sortable="true" property="nameEntity" titleKey="intercambioRegistral.tabla.destino.entidadRegistral"></display:column>

	<display:column sortable="true" property="nameTramunit" titleKey="intercambioRegistral.tabla.destino.unidadTramitacion"></display:column>

	<display:column sortable="true" property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaIntercambio"></display:column>

	<display:column sortable="true" property="idIntercambioRegistral" titleKey="intercambioRegistral.tabla.idIntercambioRegistral"></display:column>

	<c:if test="${estado==ESTADO_SALIDA_DEVUELTO}">
		<display:column sortable="true" property="comentarios" titleKey="intercambioRegistral.tabla.comentarios"></display:column>
	</c:if>

	<display:column style="width:40px;">
		<a class="linkInfoSolicitudIntercambio" href="MostrarIntercambioRegistral.do?idIntercambio=<c:out value='${row.idIntercambioInterno}'/>">
			<img src="./images/information.png" alt="<fmt:message key="intercambioRegistral.tabla.masInfo"/>"/>
		</a>
	</display:column>

	<display:setProperty name="paging.banner.item_name">
		<fmt:message key="intercambioRegistral.tabla.item_name" />
	</display:setProperty>

	<display:setProperty name="paging.banner.items_name">
		<fmt:message key="intercambioRegistral.tabla.items_name" />
	</display:setProperty>

</display:table>
