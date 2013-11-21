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

	String requestUri = "/BusquedaBandejaEntradaIntercambioRegistral.do";
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

	<form action="BusquedaBandejaEntradaIntercambioRegistral.do" method="GET" id="formBandejaEntrada" name="formBandejaEntrada">

		<input type="hidden" name="requestDispatcherUrl" value="<%=requestUri%>"/>

		<%@include file="/WEB-INF/jsp/intercambio/include/migasBandejaIR.jsp" %>

		<div class="barraBandeja">
			<a class="linkBarraBandeja Options" href="BandejaEntradaIntercambioRegistral.do"><fmt:message key="intercambioRegistral.boton.busquedaSimple"/></a>
			<a class="linkBarraBandeja Options" href="frquery.htm"><fmt:message key="intercambioRegistral.boton.volver" /></a>
		</div>

		<br />


		<%@include file="/WEB-INF/jsp/intercambio/include/barraCabeceraIntercambios.jsp" %>

		<%@include file="/WEB-INF/jsp/intercambio/include/mensajesError.jsp" %>

		<%@include file="/WEB-INF/jsp/intercambio/include/capasIntercambio.jsp" %>

		<%@include file="/WEB-INF/jsp/intercambio/include/formularioBusquedaAvanzada.jsp" %>

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

		<script type="text/javascript">


		</script>

	</body>
</html>