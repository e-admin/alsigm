<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="usuario" value="${sessionScope[appConstants.salas.USUARIO_ARCHIVO_KEY]}" />


<table class="formulario">

	<tr>
		<td width="200px" class="tdTitulo">
			<fmt:message key="archigest.archivo.salas.usuario.aplicacion"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:choose>
				<c:when test="${not empty usuario.nombreCompletoUsuarioAplicacion}">
										<html:img page="/pages/images/checkbox-yes.gif"
					altKey="archigest.archivo.si"
					titleKey="archigest.archivo.si"
					styleClass="imgTextMiddle" />&nbsp;<c:out value="${usuario.nombreCompletoUsuarioAplicacion}"/>
				</c:when>
				<c:otherwise>
					<html:img page="/pages/images/checkbox-no.gif"
					altKey="archigest.archivo.no"
					titleKey="archigest.archivo.no"
					styleClass="imgTextMiddle" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>



	<tr>
		<td width="200px" class="tdTitulo">
			<fmt:message key="archigest.archivo.doc.identificativo"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.numDocIdentificacion}"/>&nbsp;
			<fmt:message key="archigest.archivo.tipodoc.${usuario.tipoDocIdentificacion}"/>
		</td>
	</tr>

	<tr>
		<td  class="tdTitulo">
			<fmt:message key="archigest.archivo.nombre"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.nombre}"/>
		</td>
	</tr>

	<tr>
		<td  class="tdTitulo">
			<fmt:message key="archigest.archivo.apellidos"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.apellidos}"/>
		</td>
	</tr>

	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.nacionalidad"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.nacionalidad}"/>
		</td>
	</tr>

	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.telefonos"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.telefonos}"/>
		</td>
	</tr>

	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.email"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.email}"/>
		</td>
	</tr>

	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.direccion"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.direccion}"/>
		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.salas.usuario.vigente"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:choose>
				<c:when test="${usuario.vigente == 'S'}">
					<html:img page="/pages/images/checkbox-yes.gif"
									altKey="archigest.archivo.si"
									titleKey="archigest.archivo.si"
									styleClass="imgTextMiddle" />
				</c:when>
				<c:otherwise>
					<html:img page="/pages/images/checkbox-no.gif"
							altKey="archigest.archivo.no"
							titleKey="archigest.archivo.no"
							styleClass="imgTextMiddle" />
				</c:otherwise>

			</c:choose>

		</td>
	</tr>
	<tr>
		<td class="tdTitulo">
			<bean:message key="archigest.archivo.fecha.alta"/>:&nbsp;
		</td>
		<td class="tdDatos">
			<c:out value="${usuario.fechaAltaString}"/>
		</td>
	</tr>
</table>