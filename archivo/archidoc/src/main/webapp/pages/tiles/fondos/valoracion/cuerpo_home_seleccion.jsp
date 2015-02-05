<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.series.seleccion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
				<c:url var="buscarSeleccionURL" value="/action/buscarEliminacion">
					<c:param name="method" value="formBusqueda" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${buscarSeleccionURL}" escapeXml="false"/>">
					<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.eliminacion.buscar" titleKey="archigest.archivo.eliminacion.buscar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminacion.buscar"/>
				</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="listaEliminaciones" value="${requestScope[appConstants.valoracion.LISTA_ELIMINACIONES_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.eliminacion.elimEnElaboracion"/></tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<security:permissions action="${appConstants.fondosActions.EDITAR_SELECCIONES_ACTION}">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap>
						<c:url var="iniciarSeleccionURL" value="/action/gestionEliminacion">
							<c:param name="method" value="nuevaEliminacion" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${iniciarSeleccionURL}" escapeXml="false"/>">
							<html:img page="/pages/images/new.gif" altKey="archigest.archivo.eliminacion.iniciar" titleKey="archigest.archivo.eliminacion.iniciar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.eliminacion.iniciar"/>
						</a>
						</td>
					</tr>
				</table>
				</security:permissions>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaEliminaciones"
				id="eliminacion"
				style="width:99%;margin-left:auto;margin-right:auto"
				requestURI="/action/gestionEliminacion?method=homeSeleccion"
				>

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.valoraciones.ningunaEliminacionEnElaboracion"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.codigo" sortable="true" headerClass="sortable" sortProperty="titulo">
					<c:url var="infoEliminacionURL" value="/action/gestionEliminacion">
						<c:param name="method" value="verEliminacion" />
						<c:param name="id" value="${eliminacion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${infoEliminacionURL}" escapeXml="false"/>"><c:out value="${eliminacion.titulo}" /></a>
				</display:column>
				<display:column titleKey="archigest.archivo.serie">
					<c:out value="${eliminacion.tituloSerie}" />
				</display:column>
				<display:column titleKey="archigest.archivo.valoracion">
					<c:out value="${eliminacion.tituloValoracion}" />
				</display:column>
				<display:column titleKey="archigest.archivo.estado">
					<fmt:message key="archigest.archivo.eliminacion.estado${eliminacion.estado}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaEliminaciones[0]}">
		<div class="pie_bloque_right">
			<c:url var="seleccionesEnElaboracionURL" value="/action/gestionEliminacion">
				<c:param name="method" value="eliminacionesEnElaboracion" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${seleccionesEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>

		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
				<TD class="etiquetaAzul12Bold" width="50%">
					<bean:message key="archigest.archivo.seleccion.buscador"/>
				</TD>
				<TD width="50%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
						<TD>
							<bean:struts id="mappingGestionEliminaciones" mapping="/gestionEliminacion" />
							<script>
								function buscarSelecciones()
								{
									var formularioSeleccion = document.forms['<c:out value="${mappingGestionEliminaciones.name}" />'];
									formularioSeleccion.submit();
								}
							</script>
							<a class="etiquetaAzul12Normal"
								href="javascript:buscarSelecciones()">
								<html:img
									titleKey="archigest.archivo.buscar"
									altKey="archigest.archivo.buscar"
									page="/pages/images/buscar.gif"
									styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.buscar"/>
							</a>
						</TD>
					 </TR>
					</TABLE>
				</TD>
			</TR></TABLE>
		</div>
		<div class="bloque">
			<html:form action="/gestionEliminacion">
			<input type="hidden" name="method" value="buscarSeleccionesConUdocs">
			<table class="formulario" width="99%">
				<tr>
					<td width="20px">&nbsp;</td>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:set var="listaFondos" value="${sessionScope[appConstants.valoracion.LISTA_FONDOS_KEY]}" />
						<select name="fondo">
							<option value=""> -- <bean:message key="archigest.archivo.cf.fondosDocumentales"/>-- </option>
							<c:forEach var="fondo" items="${listaFondos}">
							<option value="<c:out value="${fondo.id}" />"
							<c:if test="${fondo.id == param.fondo}">selected</c:if>>
							<c:out value="${fondo.label}" />
							</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20px">&nbsp;</td>
					<td class="tdTitulo"><bean:message key="archigest.archivo.seleccion.numeroMinimoUnidades"/>:&nbsp;</td>
					<td class="tdDatos"><input type="text" size="5" name="minNumUdocs" value="<c:out value="${param.minNumUdocs}" />"></td>
				</tr>
			</table>
		</html:form>
		</div>

	</tiles:put>
</tiles:insert>