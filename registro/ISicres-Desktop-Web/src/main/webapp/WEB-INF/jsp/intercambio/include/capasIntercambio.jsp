<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="capaBloqueo" style="display:none;"></div>

<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
		<div id="librosEntrada" style="display:none;">
			<h5><fmt:message key="intercambioRegistral.seleccionarLibro" /></h5>
			<c:forEach items="${librosEntrada}"   var="libro">
				<input name="idLibro" type="radio" value="<c:out value='${libro.id}'/>"/> <c:out value="${libro.name}"/>
				<br />
			</c:forEach>
			<br/>
			 <input type="button" onclick="javascript:submitAceptarIntercambiosRegistrales(this);" value="<fmt:message key='intercambioRegistral.boton.aceptar' />" / >
			 <input type="button" onclick="javascript:ocultarLibros();"  value="<fmt:message key='intercambioRegistral.boton.cancelar' />" />
		</div>

		<div id="llegoDocumentacionFisica" style="display:none;">
			<h5><fmt:message key="intercambioRegistral.preguntaLlegoDocumentacionFisica" /></h5>
			<input type="button" onclick="javascript:continuarDocRecibida();" value="<fmt:message key='intercambioRegistral.boton.continuarDocRecibida' />" / >
			<input style="display:none;" onclick="javascript:continuarDocNoRecibida();" id="buttonContinuarDocNoRecibida" type="button"  value="<fmt:message key='intercambioRegistral.boton.continuarDocNoRecibida' />" / >
			<input type="button" onclick="javascript:ocultarConfirmacionDocumentacionFisica();"  value="<fmt:message key='intercambioRegistral.boton.cancelarDocNoRecibida' />" / >
		</div>

		<div id="buscarNuevoDestino" style="display:none;">
				<h2><fmt:message key="intercambioRegistral.unidad.destinataria.dco"/></h2>
				<h4><fmt:message key="intercambioRegistral.busquedaDestino.entidadRegistral"/></h4>
				<div class="formRow">
					<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoEntidad"/></label><input type="text"  class="inputRO" name="entityCode" id="entityCode" value="<c:out value='${defaultEntityCode}' />"/><img src="images/buscar2.gif" class="buscarEntidadesRegistralesLupa imageClickable"></img>
				</div>
				<div class="formRow">
					<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreEntidad"/></label><input type="text"  class="inputRO" name="entityName" id="entityName" style="width:320px;" value="<c:out value='${defaultEntityName}' />"/>
				</div>

				<h4><fmt:message key="intercambioRegistral.busquedaDestino.unidadTramitacion"/></h4>

				<div class="formRow">
					<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoUnidad"/></label><input type="text"  class="inputRO" name="tramunitCode" id="tramunitCode" value="<c:out value='${defaultTramunitCode}' />" /><img src="images/buscar2.gif" class="buscarUnidadesTramitacionLupa imageClickable"></img>
				</div>
				<div class="formRow">
					<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreUnidad"/></label><input type="text"  class="inputRO" name="tramunitName" id="tramunitName" style="width:320px;" value="<c:out value='${defaultTramunitName}' />"/>
				</div>

				<div class="formRow">
				<label class="label" style="width:140px;float:left;font-weight: bold;" for="observaciones"><fmt:message key="intercambioRegistral.observaciones"/></label>
				<input type="text" id="observaciones" name="observaciones" />
				</div>

				<p>
					<input type="button" onclick="javascript:submitReenviarIntercambios(this);" value="<fmt:message key='intercambioRegistral.boton.aceptar' />" / >
					<input type="button" onclick="javascript:cancelarReenvio();"  value="<fmt:message key='intercambioRegistral.boton.cancelar' />" / >
				</p>
		</div>
		<div id="divMotivoRechazo" style="display:none;z-index:1000;">
			<h5><fmt:message key="intercambioRegistral.motivoRechazo" /></h5>
			<p>
				<textarea rows="3" cols="30" name="motivoRechazo" style="width:90%"></textarea>
			</p>
			 <input type="button" onclick="javascript:submitRechazarIntercambiosRegistrales(this);" value="<fmt:message key='intercambioRegistral.boton.rechazar' />" / >
			 <input type="button" onclick="javascript:ocultarMotivoRechazo();"  value="<fmt:message key='intercambioRegistral.boton.cancelarMotivoRechazo' />" / >
		</div>
</c:if>