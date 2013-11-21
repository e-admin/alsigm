<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO" %>

<%
	// seteamos los valores constantes de los estados disponibles
    session.setAttribute("ESTADO_SALIDA_ENVIADO", EstadoIntercambioRegistralSalidaEnumVO.ENVIADO_VALUE);
	session.setAttribute("ESTADO_SALIDA_ACEPTADO", EstadoIntercambioRegistralSalidaEnumVO.ACEPTADO_VALUE);
	session.setAttribute("ESTADO_SALIDA_DEVUELTO", EstadoIntercambioRegistralSalidaEnumVO.DEVUELTO_VALUE);
	String requestUri ="/BandejaSalidaIntercambioRegistral.do";
%>

<html>
	<head>

	<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
	<c:if test="${ message == null }">
	    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
	</c:if>

	<%@include file="/WEB-INF/jsp/intercambio/include/headBandejaIntercambio.jsp" %>

	<script type="text/javascript">
		var estado='<c:out value="${estado}" />';
		var tipoBandeja='<c:out value="${tipoBandeja}" />';
		var libroSeleccionado='<c:out value="${libroSeleccionado}" />';
		window.onload=init;
		function init(){
			selectStateSalida();
			setRowsEventsSalida();
			selectBookIR();
			jQuery("a.linkInfoSolicitudIntercambio").fancybox({
				'hideOnContentClick': true
				});
		}
	</script>

	</head>
<body class="cabecera">


	<form action="BandejaSalidaIntercambioRegistral.do" method="GET" id="formBandejaSalida" name="formBandejaSalida">

	<%@include file="/WEB-INF/jsp/intercambio/include/migasBandejaIR.jsp" %>

	<div class="barraBandeja">
		<select id="tipoBandeja" style="display:inline;"  onchange="javascript:changeSelectEstados();submitRefrescarBandejaEntradaOSalida();" name="tipoBandeja">
			<option value="0"><fmt:message key="intercambioRegistral.bandeja.salida" /></option>
			<option value="1"><fmt:message key="intercambioRegistral.bandeja.entrada" /></option>
		</select>

		<select id="estadosSalida" style="display:inline;" name="estadosSalida" onchange="submitRefrescarBandejaEntradaOSalida();">
			<option value="1"><fmt:message key="intercambioRegistral.estados.enviados" /></option>
			<option value="5"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
			<option value="4"><fmt:message key="intercambioRegistral.estados.devueltos" /></option>

		</select>

		<select id="estadosEntrada" style="display:none;" name="estadosEntrada" onchange="submitRefrescarBandejaEntradaOSalida();">
			<option value="0"><fmt:message key="intercambioRegistral.estados.pendientes" /></option>
			<option value="1"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
			<option value="2"><fmt:message key="intercambioRegistral.estados.rechazados" /></option>
		</select>
		<select id="libroSeleccionado"  name="libroSeleccionado" style="display:inline;" onchange="submitRefrescarBandejaEntradaOSalida();">
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

	<div id="capaBloqueo" style="display:none;z-index:9998;"></div>


	<%@include file="/WEB-INF/jsp/intercambio/include/tablaIntercambiosBandejaSalida.jsp" %>

	</form>

	<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
					style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
	</iframe>
	<script type="text/javascript">

	</script>
</body>
</html>