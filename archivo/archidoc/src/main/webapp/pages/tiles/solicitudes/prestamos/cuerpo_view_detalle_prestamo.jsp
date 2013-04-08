<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="detalle" value="${sessionScope[appConstants.prestamos.DETALLE_PRESTAMO_KEY]}"/>
<c:set var="prestamosNoDisponibles" value="${requestScope[appConstants.prestamos.LISTA_PRESTAMOS_NO_DISPONIBLES_KEY]}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.prestamos.datosUnidadDocumental"/>
    </TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
	      	  <TD>
				<%--boton Cerrar --%>
				<c:url var="volver2URL" value="/action/gestionPrestamos">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img 
						page="/pages/images/close.gif" border="0" 
						altKey="archigest.archivo.cerrar" 
						titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
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

<DIV id="barra_errores"><archivo:errors/></DIV>

<div class="separador1">&nbsp;</div>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
			<jsp:include page="cuerpo_cabeceracte_prestamo.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%></div>

<DIV class="bloque"> <%--segundo bloque de datos --%>	
	
<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.solicitudes.signaturaudoc"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.signaturaudoc}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.expedienteudoc"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${detalle.subtipoCaja}">
							<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
							<c:if test="${!empty detalle.nombreRangos}">
								<c:out value="${detalle.nombreRangos}"/><br/>
							</c:if>
							<c:out value="${detalle.expedienteudoc}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${detalle.expedienteudoc}"/>
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.solicitudes.titulo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.titulo}"/>		
				</TD>
			</TR>
			<TR><TD class="separador8">&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.solicitudes.fondo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.fondo}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.solicitudes.sistemaProductor"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.sistemaProductor}"/>		
				</TD>
			</TR>	
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.estado"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="keyEstado">
						archigest.archivo.solicitudes.detalle.estado.<c:out value="${detalle.estado}"/>
					</c:set>
					<fmt:message key="${keyEstado}" />								
				</TD>
			</TR>
			<c:if test="${detalle.estado==6}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:formatDate value="${detalle.festado}" pattern="${appConstants.common.FORMATO_FECHA}"/>	
				</TD>
			</TR>
			</c:if>
			<c:if test="${detalle.motivorechazo!=null}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.motivorechazo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.motivorechazo}"/>		
				</TD>
			</TR>
			</c:if>
		</TABLE>

</div> <%--bloque --%>

	<c:if test="${!empty prestamosNoDisponibles}">
	<div class="separador8"></div>
	<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.prestamos.solicitudes_con_udocs"/>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.prestamosNoDisponibles"
				id="prestamoNoDisponible" 
				style="width:99%;margin-left:auto;margin-right:auto">
	
				<display:column titleKey="archigest.archivo.solicitudes.idsolicitud">
					<c:out value="${prestamoNoDisponible.codigoSolicitud}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.solicitante" property="solicitanteSolicitud"/>
				<display:column titleKey="archigest.archivo.solicitudes.estado">
					<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${prestamoNoDisponible.estadoUdoc}" />
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.finicialuso">
					<fmt:formatDate value="${prestamoNoDisponible.fechaInicialUsoUdoc}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.ffinaluso">
					<fmt:formatDate value="${prestamoNoDisponible.fechaFinalUsoUdoc}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.observaciones" property="observaciones"/>
			</display:table>
			<div class="separador8"></div>
		</tiles:put>
	</tiles:insert>
	</c:if>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>