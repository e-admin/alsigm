<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionDetallePrestamo" mapping="/gestionDetallesPrestamos" />
<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}" scope="session"/>
<c:set var="prestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="envioFinalizado" value="${requestScope[appConstants.solicitudes.ENVIO_FINALIZADO_KEY]}"/>

<script>
		function aceptar() {
			var form = document.forms['<c:out value="${mappingGestionDetallePrestamo.name}" />'];

			if (form.detallesseleccionados) {
				if (elementSelected(form.detallesseleccionados)) {
					form.submit();
				}
				else {
					alert("<bean:message key='errors.solicitudes.prestamos.seleccionarAlMenosUnExp'/>");
				}
			}
		}
</script>

	<div id="contenedor_ficha_centrada">
		<c:set var="actionPath" value="${mappingGestionDetallePrestamo.path}" />
		<c:set var="actionMethod" value="crearDetalleUDocRel" />
		<bean:define id="actionPath" name="actionPath" scope="page" toScope="request" />
		<bean:define id="actionMethod" name="actionMethod" scope="page" toScope="request" />
		<bean:define id="beanBusqueda" name="beanBusqueda" scope="session" toScope="request" />

		<tiles:insert page="../ver_udocs_relacionadas_form.jsp" flush="true"/>
	</div>

	<script type="text/javascript">
		var cerrar = "<c:out value="${envioFinalizado}"/>";
		if (cerrar == "true") {
			window.opener.refrescar('<c:out value="${prestamo.id}"/>');
			window.close();
		}
	</script>

