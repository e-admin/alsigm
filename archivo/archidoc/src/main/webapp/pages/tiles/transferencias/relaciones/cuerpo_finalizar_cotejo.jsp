<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="patternFecha" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="bRelacion" value="${sessionScope[appConstants.transferencias.INFO_RELACION_KEY]}"/>


<div id="contenedor_ficha">

<html:form action="/cotejoysignaturizacionAction?method=guardarfinalizarcotejo" >
<html:hidden property="codigoseleccionada"/>

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
		<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.finalizarCotejo"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
					<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
						<c:param name="method" value="cancelar" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
		</TD>
		</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

<div class="bloque"> <%--primer bloque de datos --%>

	<div class="separador8">&nbsp;</div>

	<c:if test="${!empty requestScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}">

		<div class="titulo_center_bloque">&nbsp;
			<c:if test="${bRelacion.pendienteReserva}">
				<bean:message key="archigest.archivo.transferencias.laReservaEstaEnEstadoPendiente" />&nbsp;
			</c:if>
			<c:if test="${bRelacion.noSolicitadaReserva}">
				<bean:message key="archigest.archivo.transferencias.noSeHaSolicitadoReserva" />&nbsp;
			</c:if>
			<c:if test="${bRelacion.noSeHaPodidoHacerReserva}">
				<bean:message key="archigest.archivo.transferencias.noSeHaPodidoHacerReserva" />&nbsp;
			</c:if>
			<bean:message key="archigest.archivo.transferencias.selUbicacionParaRelacion" />:&nbsp;
		</div>

		<TABLE cellpadding=0 cellspacing=0 class="formulario">
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.deposito.edificio" />&nbsp;
			</TD>
		</TR>
		<c:forEach var="edificio" items="${requestScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}">
		<TR>

			<TD class="tdDatos">
				<input type=radio name="depositoseleccionado" value="<c:out value="${edificio.datosElemento.id}"/>" 
					<c:if test="${CotejoysignaturizacionForm.depositoseleccionado == edificio.datosElemento.id}">
					checked
					</c:if>
					class="radio" /> 
					<c:out value="/${edificio.datosElemento.path}" />&nbsp;
			</TD>
 		</TR>
		</c:forEach> 
		</TABLE>

	</c:if>

	<c:if test="${empty requestScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}">
		<html:hidden property="depositoseleccionado"/>

		<div class="separador8">&nbsp;</div>
		<c:if test="${bRelacion.reservada}">	
			<bean:message key="archigest.archivo.transferencias.depositoRel" />:&nbsp;
		</c:if>
		<c:if test="${!bRelacion.reservada}">	
			<bean:message key="archigest.archivo.transferencias.ubiRelacion" />:&nbsp;
		</c:if>
		<c:out value="${bRelacion.nombredeposito}" />
	</c:if>

	<div class="separador8">&nbsp;</div>

	</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
	
</html:form>
