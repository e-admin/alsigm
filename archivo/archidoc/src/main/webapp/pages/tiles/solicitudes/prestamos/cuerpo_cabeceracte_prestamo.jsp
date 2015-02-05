<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<TABLE class="w98m1" cellpadding=0 cellspacing=2>
		<TR>
			<TD width="25%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.prestamos.prestamo"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
				<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].codigoTransferencia}"/>
				</span>
			</TD>
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.prestamos.estado"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:set var="keyEstado">
						archigest.archivo.solicitudes.estado.<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].estado}"/>
					</c:set>
					<fmt:message key="${keyEstado}" />
				</span>
			</TD>
			<TD width="20%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.prestamos.festado"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<fmt:formatDate value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].festado}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</span>
			</TD>
			<TD width="35%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.prestamos.gestorPrestamo"/>:&nbsp;
				<span class="etiquetaNegra11Normal">
					<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].usuarioGestor.nombreCompleto}"/>
				</span>
			</TD>
		</TR>
</TABLE>