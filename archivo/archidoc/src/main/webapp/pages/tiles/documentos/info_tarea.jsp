<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="tarea" value="${sessionScope[appConstants.documentos.TAREA_KEY]}"/>

<c:set var="dockableContent" value="true" />

<tiles:importAttribute name="dockableContent" ignore="true" />


<c:if test="${!empty tarea}">

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp" flush="false">
		<tiles:put name="blockName" direct="true">tarea</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.documentos.digitalizacion.datosTarea"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true"><c:out value="${dockableContent}"/></tiles:put>
		<tiles:put name="dockableContent" direct="true">

				<TABLE class="formulario">
					<TR>
						<TD class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.info.tarea.objeto"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:message key="${tarea.nombreTipoObjeto}"/>
						</TD>
					</TR>
					<c:choose>
						<c:when test="${tarea.codRefObj!=null}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.info.tarea.codigo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${tarea.codReferenciaPersonalizado}"/>
							</TD>
						</TR>
						</c:when>
					</c:choose>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.info.tarea.titulo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.tituloObj}"/>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.info.tarea.user"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.usuarioCaptura.nombreCompleto}"/>
						</TD>
					</TR>
					<TR><TD class="separador5" colspan="2">&nbsp;</TD></TR>
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.info.tarea.observacion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.observaciones}"/>
						</TD>
					</TR>
					<c:if test="${!empty tarea.motivoError}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.info.tarea.motivo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${tarea.motivoError}"/>
						</TD>
					</TR>
					</c:if>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.info.tarea.estado"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:message key="${tarea.nombreEstado}"/>
						</TD>
					</TR>
				</TABLE>
		</tiles:put>
	</tiles:insert>
	<div class="separador8">&nbsp;</div>

</c:if>
