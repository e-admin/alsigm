<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/displayConsultasTags.tld" prefix="consultas"%>

<c:set var="detalle" value="${sessionScope[appConstants.consultas.DETALLE_CONSULTA_KEY]}"/>
<c:set var="consultasNoDisponibles" value="${requestScope[appConstants.consultas.LISTA_CONSULTAS_NO_DISPONIBLES_KEY]}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.consultas.datosUnidadDocumental"/>
	    </TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
		      	<%--boton Cerrar --%>
				<TD>
					<c:url var="volver2URL" value="/action/gestionConsultas">
						<c:param name="method" value="goBack" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<%--<a class=etiquetaAzul12Bold href="javascript:history.go(-1)">--%>
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

<DIV class="cabecero_bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cuerpo_cabeceracte_consulta.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%></div>

<DIV class="bloque"> <%--segundo bloque de datos --%>

<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="180px">
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
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.titulo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.titulo}"/>		
				</TD>
			</TR>
			<TR><TD class="separador8">&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.fondo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.fondo}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.sistemaProductor"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.sistemaProductor}"/>		
				</TD>
			</TR>
			<TR><TD class="separador8">&nbsp;</TD></TR>
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
			<c:if test="${sessionScope[appConstants.prestamos.DETALLE_CONSULTA_KEY].estado==6}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:formatDate value="${detalle.festado}" pattern="${appConstants.common.FORMATO_FECHA}"/>	
				</TD>
			</TR>
			</c:if>
			<c:if test="${sessionScope[appConstants.prestamos.DETALLE_CONSULTA_KEY].motivorechazo!=null}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.motivorechazo"/>:
				</TD>
				<TD class="tdDatos">
					<c:out value="${detalle.motivorechazo}"/>		
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.solicitudes.informacion"/>:
				</TD>
				<TD class="tdDatos">
				  <table cellpadding="0" cellspacing="0">
				    <tr>
				      <td class="etiquetaAzul11Bold">
				        <bean:message key="archigest.archivo.consultas.copiasSimples"/>:&nbsp;&nbsp;
				      </td>
				      <td class="etiquetaNegra12Normal">
				        <c:out value="${detalle.numeroCopiasSimples}"/>
				      </td>
				    </tr>
				    <tr>
				      <td class="etiquetaAzul11Bold">
				        <bean:message key="archigest.archivo.consultas.copiasCertificadas"/>:&nbsp;&nbsp;
				      </td>
				      <td class="etiquetaNegra12Normal">
				        <c:out value="${detalle.numeroCopiasCertificadas}"/>
				      </td>
				    </tr>
				  </table>
				</TD>
			</TR>
		</TABLE>
	
</div> <%--bloque --%>

	<c:if test="${!empty consultasNoDisponibles}">
	<div class="separador8"></div>
	<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.prestamos.solicitudes_con_udocs"/>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.consultasNoDisponibles"
				id="consultaNoDisponible" 
				style="width:99%;margin-left:auto;margin-right:auto">
	
				<display:column titleKey="archigest.archivo.solicitud">
					<c:out value="${consultaNoDisponible.codigoSolicitud}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.solicitante" property="solicitanteSolicitud"/>
				<display:column titleKey="archigest.archivo.solicitudes.estado">
					<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${consultaNoDisponible.estadoUdoc}" />
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.finicialuso">
					<fmt:formatDate value="${consultaNoDisponible.fechaInicialUsoUdoc}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
	
				<display:column titleKey="archigest.archivo.solicitudes.ffinaluso">
					<fmt:formatDate value="${consultaNoDisponible.fechaFinalUsoUdoc}" pattern="${appConstants.common.FORMATO_FECHA}" />
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