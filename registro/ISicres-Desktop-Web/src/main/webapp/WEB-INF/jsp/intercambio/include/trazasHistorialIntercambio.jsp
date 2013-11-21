<%--Muestra una fila con una tabla con información de las trazas del historial del intercambio --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<c:if test="${requestScope.msg!=null}">
	<div style="color:blue;">
		<c:out value="${requestScope.msg}" ></c:out>
	</div>
</c:if>

<c:if test="${requestScope.error!=null}">
	<script type="text/javascript">showError('<c:out value="${requestScope.error}"/>');</script>
</c:if>

<c:if test="${ empty trazasIntercambio}">
	<table width="95%" align="center" class="Style2">

	<tbody>
		<tr class="Style3">
			<td>Trazas del intercambio registral</td>
		</tr>
		<tr class="Style5" align="left" valign="middle">
			<td>No existe información acerca de las trazas asociadas a este intercambio registral</td>
		</tr>
	</table>
	<table class="Style11" width="95%" align="center">
	<tr height="1"><td colspan="6"></td></tr>
	</table>
</c:if>

<c:if test="${ not empty trazasIntercambio}">


	<div class="fixedHeaderTableX">
	<table width="95%" align="center" class="Style2" cellspacing="0" cellpadding="2">
	<!--<caption>Trazas</caption>-->
		<thead class="Style1">
			<tr class="Style1">
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigo"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.descripcion"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigoError"/></td>
				<!--<td>Desc. Error Alternativa</td>-->
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigoErrorServicio"/></td>
				<!--<td>Error Servicio</td>-->
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigoIntercambio"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigoEntReg.origen"/></td>
				<!--<td>Ent. Reg. Origen</td>-->
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.codigoEntReg.destino"/></td>
				<!--<td>Ent. Reg. Destino</td>-->
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.estado"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.nodo"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.ficheroIntercambio"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.motivoRechazo"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.fechaAlta"/></td>
				<td><fmt:message key="intercambioRegitral.historial.tabla.header.fechaModificacion"/></td>
			</tr>
		</thead>
		<tbody>
			<tr class="Style2" height="1">
				<td colspan="13"></td>
			</tr>
			<c:forEach items="${trazasIntercambio}" var="traza" varStatus="statusListaTraza">
				<tr class="Style3">
					<td><c:out value="${traza.codigo}"></c:out></td>
					<td><c:out value="${traza.descripcion}"></c:out></td>
					<td>
						<a title="${traza.descripcionErrorAlternativa}">
							<c:out value="${traza.codigoError}"></c:out>
						</a>
					</td>
					<!--<td><c:out value="${traza.descripcionErrorAlternativa}"></c:out></td>-->
					<td>
						<a title="${traza.descripcionErrorServicio}">
							<c:out value="${traza.codigoErrorServicio}"></c:out>
						</a>
					</td>
					<!--<td><c:out value="${traza.descripcionErrorServicio}"></c:out></td>-->
					<td><c:out value="${traza.codigoIntercambio}"></c:out></td>

					<td>
						<a title="${traza.descripcionEntidadRegistralOrigen}">
							<c:out value="${traza.codigoEntidadRegistralOrigen}"></c:out>
						</a>
					</td>
					<!--<td><c:out value="${traza.descripcionEntidadRegistralOrigen}"></c:out></td>-->
					<td>
						<a title="${traza.descripcionEntidadRegistralDestino}">
							<c:out value="${traza.codigoEntidadRegistralDestino}"></c:out>
						</a>
					</td>
					<!--<td><c:out value="${traza.descripcionEntidadRegistralDestino}"></c:out></td>-->

					<td><fmt:message key="asientoRegitral.tipoEstado.${traza.codigoEstado}"/></td>
					<td><c:out value="${traza.codigoNodo}"></c:out></td>
					<td><c:out value="${traza.nombreFicheroIntercambio}"></c:out></td>
					<td><c:out value="${traza.motivoRechazo}"></c:out></td>

					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${traza.fechaAlta}" /></td>
					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${traza.fechaModificacion}"/></td>

				</tr>
				<tr class="Style2" height="1"><td colspan="13"></td></tr>
			</c:forEach>

		</tbody>
		</table>

		</div>

</c:if>


	<script type="text/javascript">
	/*
	$('detalle<c:out value="${status.index}"/>').fixedHeaderTable({ footer: false, cloneHeadToFoot: false, fixedColumn: false });
	*/
	</script>
