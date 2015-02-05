<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />

<table class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
	<c:set var="resumen" value="${eliminacion.resumenUInst}"/>
	<tr>
		<td class="tdTitulo" nowrap="nowrap" width="200px">
			<bean:message key="archigest.archivo.seleccion.udoc.a.eliminar"/>:
		</td>
		<td class="tdDatos">
			<c:out value="${resumen.numUdocs}" />
		</td>
	</tr>

	<tr>
		<td class="tdTitulo" nowrap="nowrap">
			<bean:message key="archigest.archivo.seleccion.ui.afectadas"/>:
		</td>
		<td class="tdDatos">
			<c:out value="${resumen.total}" />
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap">
			&nbsp;
		</td>
		<td>
			<table>
				<c:if test="${resumen.completas > 0}" >
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.seleccion.ui.completas"/>:
					</td>
					<td class="tdDatos">
						<c:out value="${resumen.completas}" />
						<html:img page="/pages/images/eliminaciones/cajaCompleta.gif"
											        border="0"
											        altKey="archigest.archivo.seleccion.ui.completas"
											        titleKey="archigest.archivo.seleccion.ui.completas"
											        styleClass="imgTextMiddle"/>

					</td>
				</tr>
				</c:if>
				<c:if test="${resumen.parciales > 0}" >
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.seleccion.ui.parciales"/>:
					</td>
					<td class="tdDatos">
						<c:out value="${resumen.parciales}" />
								<html:img page="/pages/images/eliminaciones/cajaParcial.gif"
						        border="0"
						        altKey="archigest.archivo.seleccion.ui.parciales"
						        titleKey="archigest.archivo.seleccion.ui.parciales"
						        styleClass="imgTextMiddle"/>
					</td>
				</tr>
				</c:if>
			</table>
		</td>
	</tr>
</table>