<%--Muestra una fila con una tabla con información de las trazas del historial del intercambio --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<tr valign="middle" align="left" class="Style4">
	<td width="4%"> </td>
	
	
	<td colspan="25">
		<div class="fixedHeaderTable">
		<table id="<c:out value='detalle${status.index}'/>" cellspacing="0" cellpadding="2" width="100%" align="center">
		<!--<caption>Trazas</caption>-->	
			<thead class="Style1">
				<tr class="Style6">
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
					<td colspan="16"></td>
				</tr>
				<c:forEach items="${infoEstado.trazas}" var="traza" varStatus="statusListaTraza">
					<tr class="Style5">
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
				</c:forEach>
		
			</tbody>
			</table>
			
			</div>
					
		</td>
</tr>
	<script type="text/javascript">
	/*
	$('detalle<c:out value="${status.index}"/>').fixedHeaderTable({ footer: false, cloneHeadToFoot: false, fixedColumn: false });
	*/
	</script>
