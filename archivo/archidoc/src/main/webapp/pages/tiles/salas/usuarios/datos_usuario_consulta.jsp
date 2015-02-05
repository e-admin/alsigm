<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="usuarioConsulta" value="${sessionScope[appConstants.salas.USUARIO_CONSULTA_KEY]}" />

<div class="bloque">
	<table class="w98m1" cellpadding="0" cellspacing="2">
		<tr>
			<td width="50%" class="etiquetaAzul11Bold">
				<fmt:message key="archigest.archivo.doc.identificativo"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:out value="${usuarioConsulta.numDocIdentificacion}"/>&nbsp;
					<fmt:message key="archigest.archivo.tipodoc.${usuarioConsulta.tipoDocIdentificacion}"/>
				</span>
			</td>
			<td width="50%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.salas.usuario.nombreApellidos"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:out value="${usuarioConsulta.nombre}"/>&nbsp;<c:out value="${usuarioConsulta.apellidos}"/>
				</span>
			</td>
		</tr>
	</table>
</div>

<div class="bloque" id="divUser" style="display:none;">
	<table class="formulario">
		<tr>
			<td width="180px" class="tdTitulo">
				<bean:message key="archigest.archivo.nacionalidad"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${usuarioConsulta.nacionalidad}"/>
			</td>
		</tr>

		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.telefonos"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${usuarioConsulta.telefonos}"/>
			</td>
		</tr>

		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.email"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${usuarioConsulta.email}"/>
			</td>
		</tr>

		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.direccion"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${usuarioConsulta.direccion}"/>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.salas.usuario.vigente"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:choose>
					<c:when test="${usuarioConsulta.vigente == 'S'}">
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
				<c:out value="${usuarioConsulta.fechaAltaString}"/>
			</td>
		</tr>
	</table>
</div>