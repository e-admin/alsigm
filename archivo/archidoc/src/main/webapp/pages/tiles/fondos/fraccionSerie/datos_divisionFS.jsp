<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
<c:set var="estadoAbierta"><c:out value="${appConstants.fondos.estadosDivisionFS.ABIERTA}"/></c:set>

	<TABLE class="formulario">
		<TR>
			<TD class="tdTitulo" width="250px">
				<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:choose>
					<c:when test="${divisionFraccionSerie.estado == estadoAbierta}">
						<c:url var="verFSURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${divisionFraccionSerie.idFS}" />
						</c:url>
						<a href="<c:out value="${verFSURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${divisionFraccionSerie.codReferencia}" />
						</a>
					</c:when>
					<c:otherwise>
						<c:out value="${divisionFraccionSerie.codReferencia}" />
					</c:otherwise>
				</c:choose>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" width="250px">
				<bean:message key="archigest.archivo.divisionfs.fEstado"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<fmt:formatDate value="${divisionFraccionSerie.fechaEstado}" pattern="${FORMATO_FECHA}" />
			</TD>
		</TR>		
		<c:if test="${!empty divisionFraccionSerie.nivelDocumental}">		
			<TR>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.cf.nivelDocumental"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${divisionFraccionSerie.nombreNivel}"/>
				</TD>
			</TR>
		</c:if>
		<c:if test="${!empty divisionFraccionSerie.nombreFicha}">
			<TR>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.cf.ficha"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${divisionFraccionSerie.nombreFicha}" />
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD colspan="2"><html:img page="/pages/images/pixel.gif" width="1" height="5"/></TD>
		</TR>
	</TABLE>