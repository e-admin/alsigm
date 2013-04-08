<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
	<c:set var="isPrestamo" value="${sessionScope[appConstants.fondos.IS_PRESTAMO]}" />
	<table class="formulario">

		<c:if test="${isPrestamo}">
			<bean:define id="busquedaForm" name="detallePrestamoForm" toScope="request"/>
		</c:if>
		<c:if test="${not isPrestamo}">
			<bean:define id="busquedaForm" name="detalleConsultaForm" toScope="request"/>
		</c:if>
		<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
		<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
		<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
		<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
		<bean:define id="sizeCampo" value="300" toScope="request"/>
		<bean:define id="methodBuscarProcedimiento" value="buscarProcedimiento" toScope="request"/>
		<bean:define id="methodLoadFicha" value="nuevoDetalle" toScope="request"/>
		<c:set var="listaFondos" value="${sessionScope[appConstants.fondos.LISTA_FONDOS_KEY]}"/>
		<c:if test="${listaFondos != null}">
			<bean:define id="listaFondos" name="listaFondos" toScope="request"/>
		</c:if>
		<c:set var="campoSignatura" value="${beanBusqueda.mapEntrada[appConstants.prestamos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA]}"/>
		<c:if test="${campoSignatura.mostrarCalificadores != null}">
			<bean:define id="mostrarCalificadorSignatura" name="campoSignatura" property="mostrarCalificadores" toScope="request"/>
		</c:if>
		<c:set var="campoNumeroExpediente" value="${beanBusqueda.mapEntrada[appConstants.prestamos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE]}"/>
		<c:if test="${campoNumeroExpediente.mostrarCalificadores != null}">
			<bean:define id="mostrarCalificadorNumeroExpediente" name="campoNumeroExpediente" property="mostrarCalificadores" toScope="request"/>
		</c:if>

		<c:forEach var="elementos" items="${beanBusqueda.listaCamposEntrada}">
			<bean:define id="elemento" name="elementos" toScope="request"/>
			<tiles:insert name="campos.busqueda" flush="true"/>
		</c:forEach>
	</table>