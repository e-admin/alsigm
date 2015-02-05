<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="vFondo" value="${requestScope[appConstants.fondos.FONDO_KEY]}"/>
<c:set var="vEntidad" value="${vFondo.entidadProductora}" />

<div> <%-- primer bloque de datos --%>
	<table class="formulario">
		<tr>
			<td width="200px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<c:out value="${vFondo.codReferencia}" />
			</td>
		</tr>
		<tr>
			<td width="200px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<c:out value="${vFondo.denominacion}"/>
			</td>
		</tr>
	</table>
	<div class="separador5">&nbsp;</div>

	<table class="formulario">
		<tr>
			<td class="tdTitulo" width="200px">
				<bean:message key="archigest.archivo.transferencias.pais"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${vFondo.pais.nombre}"/>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.comunidadAutonoma"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${vFondo.comunidad.nombre}"/>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.archivo"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${vFondo.archivo.nombre}"/>
			</td>
		</tr>
		<tr>
			<td width="100px" class="tdTitulo">
				<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<fmt:message key="archigest.archivo.cf.estadoElementoCF.${vFondo.estado}" />
			</td>
		</tr>
	</table>
	<table class="formulario" cellpadding=0 cellspacing=0>
		<tr>
			<td class="tdTitulo" width="200px">
				<bean:message key="archigest.archivo.cf.entidadProductora"/>&nbsp;
			</td>
			<td class="tdDatos">
			</td>
		</tr>
	</table>
		<table class="formulario" cellpadding=0 cellspacing=0>
			<tr>
				<td width="50px">
				</td>
				<td class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.cf.tipo" />:&nbsp;&nbsp;
				</td>
				<td class="tdDatos">
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.FAMILIA.identificador}">
						<bean:message key="archigest.archivo.cf.familia"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.INSTITUCION.identificador}">
						<bean:message key="archigest.archivo.cf.institucion"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.PERSONA.identificador}">
						<bean:message key="archigest.archivo.cf.persona"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="50px">
				</td>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.nombre" />:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vEntidad.nombre}" />
				</td>
			</tr>
		</table>
</div>
