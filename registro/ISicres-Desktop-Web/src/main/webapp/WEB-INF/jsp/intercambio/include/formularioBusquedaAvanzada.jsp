<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="formularioBusqueda" style="width:100%; margin-left:0%;">

		<table class="tablaBusqueda">
			<tr>
				<td width="10%"></td>
				<td width="10%">
					<label for="tipoBandeja"><fmt:message key="intercambioRegistral.label.bandeja"/></label>
				</td>
			<td>
			<select id="tipoBandeja" style="display:inline;" onchange="javascript:changeSelectEstados()" name="tipoBandeja">
				<option value="0"><fmt:message key="intercambioRegistral.bandeja.salida" /></option>
				<option value="1"><fmt:message key="intercambioRegistral.bandeja.entrada" /></option>
			</select>
			</td>
			<td><label><fmt:message key="intercambioRegistral.label.estado"/></label></td>
			<td>
				<select id="estadosSalida" style="display:none;" name="estadosSalida">
					<option value="1"><fmt:message key="intercambioRegistral.estados.enviados" /></option>
					<option value="5"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
					<option value="4"><fmt:message key="intercambioRegistral.estados.devueltos" /></option>
				</select>

				<select id="estadosEntrada" style="display:inline;" name="estadosEntrada">
					<option value="${ESTADO_ENTRADA_PENDIENTE}"><fmt:message key="intercambioRegistral.estados.pendientes" /></option>
					<option value="${ESTADO_ENTRADA_ACEPTADO}"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
					<option value="${ESTADO_ENTRADA_RECHAZADO}"><fmt:message key="intercambioRegistral.estados.rechazados" /></option>
					<option value="${ESTADO_ENTRADA_REENVIADO}"><fmt:message key="intercambioRegistral.estados.reenviados" /></option>
				</select>
			</td>
			<td width="10%"></td>
			<td width="10%"></td>

			</tr>

			<tr>
				<td width="10%"></td>

				<td colspan="1">
					<label for="identificadorIntercambio"><fmt:message key="intercambioRegistral.label.identificadorIntercambio" /></label>
				</td>
				<td colspan="1">
					<input type="text" id="identificadorIntercambio" name="identificadorIntercambio" value="<c:out value="${identificadorIntercambio}"/>" class="inputText"/>
				</td>
				<td width="5%">
				<label for="libroSeleccionado" id="labelLibroSeleccionado" style="display:none;">
					<fmt:message key="intercambioRegistral.label.libro"/>
				</label>
				</td>
				<td width="15%">
				<select id="libroSeleccionado"  name="libroSeleccionado" style="display:none;">
					<c:forEach items="${librosIntercambio}" var="libro">
						<option value="<c:out value='${libro.id}' />"><c:out value='${libro.name}' /></option>
					</c:forEach>
				</select>
				</td>
				<td colspan="1">
				<input type="submit" value='<fmt:message key="intercambioRegistral.boton.buscar"/>' class="button" onclick="javascript:submitRefrescarBusquedaAvanzadaIR();"/>
				</td>
				<td width="10%"></td>
			</tr>

		</table>
		<!--
		<table class="Style11"><tr><td height="3"></td></tr></table>
		-->

</div>