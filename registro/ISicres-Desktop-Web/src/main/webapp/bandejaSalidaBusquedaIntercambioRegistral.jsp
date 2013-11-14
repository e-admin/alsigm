<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO" %>

<%
	// seteamos los valores constantes de los estados disponibles
    session.setAttribute("ESTADO_SALIDA_ENVIADO", EstadoIntercambioRegistralSalidaEnumVO.ENVIADO_VALUE);
	session.setAttribute("ESTADO_SALIDA_ACEPTADO", EstadoIntercambioRegistralSalidaEnumVO.ACEPTADO_VALUE);
	session.setAttribute("ESTADO_SALIDA_DEVUELTO", EstadoIntercambioRegistralSalidaEnumVO.DEVUELTO_VALUE);

	String requestUri = "/BusquedaBandejaSalidaIntercambioRegistral.do";
	String actionForm = "BusquedaBandejaSalidaIntercambioRegistral.do";
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

	<form action="<%=actionForm%>" method="GET" id="formBandejaSalida" name="formBandejaSalida">

	<%@include file="/WEB-INF/jsp/intercambio/include/migasBandejaIR.jsp" %>


	<div class="barraBandeja">
		<a class="linkBarraBandeja Options" href="BandejaEntradaIntercambioRegistral.do"><fmt:message key="intercambioRegistral.boton.busquedaSimple"/></a>
		<a class="linkBarraBandeja Options" href="frquery.htm"><fmt:message key="intercambioRegistral.boton.volver" /></a>
	</div>

	<br />

	<%@include file="/WEB-INF/jsp/intercambio/include/barraCabeceraIntercambios.jsp" %>

	<%@include file="/WEB-INF/jsp/intercambio/include/mensajesError.jsp" %>

	<div id="capaBloqueo" style="display:none;z-index:9998;"></div>

	<%@include file="/WEB-INF/jsp/intercambio/include/formularioBusquedaAvanzadaSalida.jsp" %>

	<%@include file="/WEB-INF/jsp/intercambio/include/tablaIntercambiosBandejaSalida.jsp" %>


	</form>
	<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
					style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
	</iframe>
	<script type="text/javascript">


	</script>
</body>
</html>