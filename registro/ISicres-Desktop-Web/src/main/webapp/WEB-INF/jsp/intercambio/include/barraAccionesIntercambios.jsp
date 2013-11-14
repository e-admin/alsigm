<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
	<div class="barraAcciones">
			<img align="middle" src="images/accept.png" style="margin-top:-5px;" />&nbsp;
			<a href="#" onclick="javascript:aceptarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.aceptar" /></a>
			<img align="middle" src="images/refresh.gif" style="margin-top:-5px;" />&nbsp;
			<a href="#" onclick="javascript:reenviarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.reenviar" /></a>
			<img align="middle" src="images/rechazar.png" style="margin-top:-5px;" />&nbsp;
			<a href="#" onclick="javascript:rechazarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.rechazar" /></a>
	</div>
</c:if>

<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
	<div id="tblUtil" style="text-align:right;">
		<input onclick="javascript:seleccionarODeseleccionarTodos();" name="seleccionarTodos" id="seleccionarTodos" type="checkbox" value="1"/>
		<label for="seleccionarTodos"><fmt:message key="intercambioRegistral.check.seleccionarTodos" /></label>

		<input onclick="javascript:seleccionarODeseleccionarTodos();" name="deseleccionarTodos" id="deseleccionarTodos" type="checkbox" value="0"/>
		<label for="deseleccionarTodos"><fmt:message key="intercambioRegistral.check.quitarSeleccion" /></label>
	</div>
</c:if>