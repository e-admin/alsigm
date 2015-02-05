<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />

<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
	<TR>
		<TD class="tdTitulo" width="220px">
			<bean:message key="archigest.archivo.eliminacion.titulo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.titulo}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.estado"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:message key="archigest.archivo.eliminacion.estado${eliminacion.estado}" />
		</TD>
	</TR>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.valoracion.titulo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:url var="infoValoracionURL" value="/action/gestionValoracion">
				<c:param name="method" value="verValoracion" />
				<c:param name="id" value="${eliminacion.valoracion.id}" />
			</c:url>
			<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>" >
				<c:out value="${eliminacion.valoracion.titulo}" />
			</a>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.maxvigencia"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.maxAnosVigencia}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.notas"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.notas}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.eliminacion.tipoEliminacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:message key="archigest.archivo.eliminacion.tipoEliminacion${eliminacion.tipoEliminacion}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.eliminacion.fechaEjecucion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:formatDate value="${eliminacion.fechaEjecucion}" pattern="${appConstants.common.FORMATO_FECHA}" />
		</TD>
	</TR>

	<c:if test="${eliminacion.aprobada || eliminacion.pendienteDestruccion || eliminacion.destruida || eliminacion.finalizada || eliminacion.enDestruccion}">
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.eliminacion.fechaAprobacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:formatDate value="${eliminacion.fechaAprobacion}" pattern="${appConstants.common.FORMATO_FECHA}" />
		</TD>
	</TR>
	</c:if>
	<TR><TD>&nbsp;</TD></TR>
	<c:if test="${eliminacion.destruida || eliminacion.finalizada || eliminacion.enDestruccion}">
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.eliminacion.fechaDestruccion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:formatDate value="${eliminacion.fechaDestruccion}" pattern="${appConstants.common.FORMATO_FECHA}" />
		</TD>
	</TR>
	</c:if>
	<c:if test="${ eliminacion.finalizada || eliminacion.enDestruccion}">
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.eliminacion.fechaFinalizacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:formatDate value="${eliminacion.fechaFinalizacion}" pattern="${appConstants.common.FORMATO_FECHA}" />
		</TD>
	</TR>
	</c:if>

	<c:if test="${eliminacion.rechazada}">
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.eliminacion.motivoRechazo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.motivoRechazo}" />
		</TD>
	</TR>
	</c:if>

</TABLE>