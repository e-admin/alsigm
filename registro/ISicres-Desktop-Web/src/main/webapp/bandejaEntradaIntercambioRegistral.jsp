<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO" %>
<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO" %>

<%
	// seteamos los valores constantes de los estados disponibles
    session.setAttribute("ESTADO_ENTRADA_PENDIENTE", EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE_VALUE);
	session.setAttribute("ESTADO_ENTRADA_ACEPTADO", EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE);
	session.setAttribute("ESTADO_ENTRADA_RECHAZADO", EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO_VALUE);
	session.setAttribute("ESTADO_ENTRADA_REENVIADO", EstadoIntercambioRegistralEntradaEnumVO.REENVIADO_VALUE);

	String requestUri = "/BandejaEntradaIntercambioRegistral.do";
%>

<html>
	<head>

	<%@include file="/WEB-INF/jsp/intercambio/include/headBandejaIntercambio.jsp" %>

	<script type="text/javascript">
	var estado='<c:out value="${estado}" />';
	var tipoBandeja='<c:out value="${tipoBandeja}" />';
	var aceptar = false;
	window.onload=init;

	function init(){
		selectState();
		setRowsEvents();
		jQuery("a.linkInfoSolicitudIntercambio").fancybox({
			'hideOnContentClick': true
			});
	}
	</script>

	</head>
	<body class="cabecera">
	<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
	<c:if test="${ message == null }">
	    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
	</c:if>

	<form action="BandejaEntradaIntercambioRegistral.do" method="GET" id="formBandejaEntrada" name="formBandejaEntrada">

		<%@include file="/WEB-INF/jsp/intercambio/include/migasBandejaIR.jsp" %>

		<div class="barraBandeja">

			<select id="tipoBandeja" style="display:inline;" onchange="javascript:changeSelectEstados();submitRefrescarBandejaEntradaOSalida();" name="tipoBandeja">
				<option value="0"><fmt:message key="intercambioRegistral.bandeja.salida" /></option>
				<option value="1"><fmt:message key="intercambioRegistral.bandeja.entrada" /></option>
			</select>

			<select id="estadosSalida" style="display:none;"    name="estadosSalida" onchange="submitRefrescarBandejaEntradaOSalida();">
				<option value="1"><fmt:message key="intercambioRegistral.estados.enviados" /></option>
				<option value="5"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
				<option value="4"><fmt:message key="intercambioRegistral.estados.devueltos" /></option>
			</select>

			<select id="estadosEntrada" style="display:inline;" name="estadosEntrada" onchange="submitRefrescarBandejaEntradaOSalida();">
				<option value="${ESTADO_ENTRADA_PENDIENTE}"><fmt:message key="intercambioRegistral.estados.pendientes" /></option>
				<option value="${ESTADO_ENTRADA_ACEPTADO}"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
				<option value="${ESTADO_ENTRADA_RECHAZADO}"><fmt:message key="intercambioRegistral.estados.rechazados" /></option>
				<option value="${ESTADO_ENTRADA_REENVIADO}"><fmt:message key="intercambioRegistral.estados.reenviados" /></option>
			</select>

			<select id="libroSeleccionado"  name="libroSeleccionado" style="display:none;" onchange="submitRefrescarBandejaEntradaOSalida();">
				<c:forEach items="${librosIntercambio}" var="libro">
					<option value="<c:out value='${libro.id}' />"><c:out value='${libro.name}' /></option>
				</c:forEach>
			</select>
			<a href="#" onclick="javascript:submitRefrescarBandejaEntradaOSalida();" class="Options linkBarraBandeja"><fmt:message key="intercambioRegistral.boton.refrescar" /></a>
			<a class="linkBarraBandeja Options" href="BusquedaBandejaEntradaIntercambioRegistral.do"><fmt:message key="intercambioRegistral.boton.busquedaAvanzada"/></a>
			<a class="linkBarraBandeja Options" href="frquery.htm"><fmt:message key="intercambioRegistral.boton.volver" /></a>
		</div>

		<br />

		<%@include file="/WEB-INF/jsp/intercambio/include/mensajesError.jsp" %>

		<%@include file="/WEB-INF/jsp/intercambio/include/capasIntercambio.jsp" %>

		<%@include file="/WEB-INF/jsp/intercambio/include/barraAccionesIntercambios.jsp" %>


		<!-- tabla resultados estado pendiente -->
		<%@include file="/WEB-INF/jsp/intercambio/include/tablaIntercambiosPendientes.jsp" %>

		<!-- tabla resultados estado aceptado -->
		<%@include file="/WEB-INF/jsp/intercambio/include/tablaIntercambiosAceptados.jsp" %>

		<!-- tabla resultados estado rechazado -->
		<%@include file="/WEB-INF/jsp/intercambio/include/tablaIntercambiosRechazados.jsp" %>

		<input type="hidden" id="docRecibida" name="docRecibida" value="false" />

		</form>
		<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
			style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
		</iframe>
	</body>
	</html>