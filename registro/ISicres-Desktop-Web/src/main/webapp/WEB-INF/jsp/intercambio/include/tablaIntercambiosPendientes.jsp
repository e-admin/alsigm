<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}">
<%--Muestra una tabla con objetos de la clase BandejaEntradaItemVO--%>

	<display:table htmlId="bandejaEntrada" class="report" cellspacing="0" cellpadding="5" name="bandejaEntrada" id="row" requestURI="<%=requestUri%>"
		pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaEntradaTableDecorator">
		<display:column><input name="checkRegistro" type="checkbox" value="<c:out value='${row.idIntercambioInterno}'/>"/></display:column>
		<display:column sortable="true" property="numeroRegistroOriginal" titleKey="intercambioRegistral.tabla.numeroRegistro.original"></display:column>
		<display:column sortable="true" property="fechaRegistro" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>
		<display:column property="tipoLibro" titleKey="intercambioRegistral.tabla.tipoRegistro"></display:column>
		<display:column sortable="true" property="origenName" titleKey="intercambioRegistral.tabla.origen.entidadRegistral"></display:column>
		<display:column titleKey="intercambioRegistral.tabla.documentaionFisica">
			<c:out value='${row.documentacionFisicaIntercambioRegistral.name}'/>
			<input type="hidden" name="documentacionFisica" value="<c:out value='${row.documentacionFisicaIntercambioRegistral.value}'/>"/>
		</display:column>
		<display:column sortable="true" property="estado" titleKey="intercambioRegistral.tabla.estado"></display:column>
		<display:column property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaEstado"></display:column>


		<display:column style="width:40px;">
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
