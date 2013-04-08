<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="organizacion" value="${requestScope[appConstants.organizacion.RESUMEN_ORGANIZACION]}" />
<bean:struts id="mappingGestionOrganizacion" mapping="/gestionOrganizacionAction" />
<c:set var="borrador" value="${organizacion.estado==1}" />
<c:set var="vigente" value="${organizacion.estado==2}" />
<c:set var="historico" value="${organizacion.estado==3}" />
<c:set var="institucion" value="${organizacion.tipo==1}" />
<c:set var="jerarquiaElementoOrg" value="${sessionScope[appConstants.organizacion.JERARQUIA_ELEMENTO_ORGANIZACION]}" />
<fmt:setLocale value="${pageContext.response.locale}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${institucion}">
				<c:set var="tipo" value="institucion" />
			</c:when>
			<c:otherwise>
				<c:set var="tipo" value="organo" />
			</c:otherwise>
		</c:choose>
		<fmt:message key="organizacion.org.tipo.${tipo}"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">

		<table cellpadding=0 cellspacing=0>
		<tr>
			<c:if test="${!historico}">
				<c:set var="treeView" value="${sessionScope[appConstants.organizacion.ORGANIZACION_VIEW_NAME]}" />
				<c:set var="parentNode" value="${treeView.selectedNode.parent}" />

				<c:if test="${!institucion && (not empty parentNode.nodePath)}">
					<td width="10">&nbsp;</td>
					<td>
						<c:url var="viewPadreURL" value="/action/manageVistaOrganizacion">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${parentNode.nodePath}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${viewPadreURL}" escapeXml="false"/>" target="_self" >
							<html:img page="/pages/images/treePadre.gif" altKey="organizacion.verPadre" titleKey="organizacion.verPadre" styleClass="imgTextMiddle" />
							<bean:message key="organizacion.verPadre"/>
						</a>
					</td>
				</c:if>
				<c:if test="${!institucion}">
					<td width="10">&nbsp;</td>
					<td>
						<c:url var="moverOrganizacionURL" value="/action/gestionOrganizacionAction">
							<c:param name="method" value="seleccionarNuevoPadre" />
							<c:param name="idOrganizacion" value="${organizacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${moverOrganizacionURL}" escapeXml="false"/>" target="_self" >
							<html:img page="/pages/images/treeMover.gif" altKey="organizacion.mover" titleKey="organizacion.mover" styleClass="imgTextMiddle" />
							<bean:message key="organizacion.mover"/>
						</a>
					</td>
				</c:if>
				<td width="10">&nbsp;</td>
				<c:choose>
					<c:when test="${borrador}">
						<td>
							<c:url var="cambiarEstadoOrgURL" value="/action/gestionOrganizacionAction">
								<c:param name="method" value="cambiarEstadoVigencia" />
								<c:param name="idOrganizacion" value="${organizacion.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoOrgURL}" escapeXml="false"/>" target="_self">
								<html:img page="/pages/images/vigente.gif" altKey="organizacion.vigente" titleKey="organizacion.vigente" styleClass="imgTextMiddle" />
									<bean:message key="organizacion.vigente"/>
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:url var="cambiarEstadoOrgURL" value="/action/gestionOrganizacionAction">
								<c:param name="method" value="cambiarEstadoHistorico" />
								<c:param name="idOrganizacion" value="${organizacion.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${cambiarEstadoOrgURL}" escapeXml="false"/>" target="_self">
								<html:img page="/pages/images/go.gif" altKey="organizacion.historico" titleKey="organizacion.historico" styleClass="imgTextMiddle" />
									<bean:message key="organizacion.historico"/>
							</a>
						</td>
					</c:otherwise>
				</c:choose>
				<td width="10">&nbsp;</td>
				<td>
					<c:url var="editarOrganizacionURL" value="/action/gestionOrganizacionAction">
						<c:param name="method" value="editarOrganizacion" />
						<c:param name="idOrganizacion" value="${organizacion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${editarOrganizacionURL}" escapeXml="false"/>" target="_self">
						<html:img page="/pages/images/edit.gif" altKey="organizacion.editar" titleKey="organizacion.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="organizacion.editar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<c:url var="eliminarOrganizacionURL" value="/action/gestionOrganizacionAction">
						<c:param name="method" value="eliminarOrganizacion" />
						<c:param name="idOrganizacion" value="${organizacion.id}" />
					</c:url>
					<script>
						function eliminar() {
							if (confirm("<bean:message key="organizacion.eliminarOrganizacionMsg"/>"))
								window.location = "<c:out value="${eliminarOrganizacionURL}" escapeXml="false" />";
						}



					</script>
					<a class=etiquetaAzul12Bold href="javascript:eliminar()" target="_self">
						<html:img page="/pages/images/delete.gif" altKey="organizacion.eliminar" titleKey="organizacion.eliminar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="organizacion.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
			</c:if>
			<td nowrap>
				<tiles:insert definition="button.closeButton" flush="false" />
			</td>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="organizacion.org.datos"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<c:if test="${not empty jerarquiaElementoOrg}">
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="organizacion.org.ancestors"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<tiles:insert definition="organizacion.jerarquiaElemento" flush="false" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="organizacion.org.codigo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${organizacion.codigo}" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="organizacion.org.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${organizacion.nombre}" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="organizacion.org.estado"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:choose>
							<c:when test="${organizacion.estado == 1}">
								<c:set var="estado" value="borrador" />
							</c:when>
							<c:when test="${organizacion.estado == 2}">
								<c:set var="estado" value="vigente" />
							</c:when>
							<c:otherwise>
								<c:set var="estado" value="historico" />
							</c:otherwise>
						</c:choose>
						<fmt:message key="organizacion.org.estado.${estado}"/>
					</td>
				</tr>
				<c:if test="${organizacion.estado == 2 || historico}">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="organizacion.org.fechainicio"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:if test="${empty organizacion.finiciovigencia}">-</c:if>
						<fmt:formatDate value="${organizacion.finiciovigencia}" pattern="${FORMATO_FECHA}"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${organizacion.estado == 3}">
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="organizacion.org.fechafin"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:if test="${empty organizacion.ffinvigencia}">-</c:if>
							<fmt:formatDate value="${organizacion.ffinvigencia}" pattern="${FORMATO_FECHA}"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="organizacion.org.descripcion"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:if test="${empty organizacion.descripcion}">-</c:if>
						<c:out value="${organizacion.descripcion}" />
					</td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>

		<c:if test="${vigente}">
			<div class="separador8">&nbsp;</div>

			<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="organizacion.org.usuariosasociados"/>
				</tiles:put>
				<c:set var="listaUsuarios" value="${requestScope[appConstants.organizacion.LISTA_USUARIOS_KEY]}" />
				<tiles:put name="buttonBar" direct="true">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>

								<c:url var="agregarUsuarioAOrganoURL" value="/action/gestionUsuariosOrganizacionAction">
									<c:param name="method" value="busquedaUsuario" />
									<c:param name="idOrgano" value="${organizacion.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${agregarUsuarioAOrganoURL}" escapeXml="false"/>" target="_self">
								<html:img page="/pages/images/addDoc.gif" altKey="organizacion.asociar.usuario" titleKey="organizacion.asociar.usuario" styleClass="imgTextMiddle" />
								<bean:message key="organizacion.asociar.usuario"/></a>
							</td>

							<c:if test="${not empty listaUsuarios}">
							<td width="10px"></td>
							<td nowrap>
								<script language="javascript">
								function desasociarUsuario() {
									if (confirm("<bean:message key="organizacion.desasociar.usuario.msg"/>")){
										enviarFormulario("formulario", "quitarUsuariosDeOrgano")
									}
								}
								</script>

								<a class="etiquetaAzul12Bold" href="javascript:desasociarUsuario()" >
								<html:img page="/pages/images/delDoc.gif" altKey="organizacion.desasociar.usuario" titleKey="organizacion.desasociar.usuario" styleClass="imgTextMiddle" />
								<bean:message key="organizacion.desasociar.usuario"/></a>
							</td>
							</c:if>
						</tr>
					</table>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
					<html:form action="/gestionOrganizacionAction" styleId="formulario">
						<input type="hidden" name="method" id="method" value="quitarUsuariosDeOrgano">

						<display:table name="pageScope.listaUsuarios"
								id="usuario"
								style="width:99%; margin-top:10px; margin-bottom:10px;margin-left:auto;margin-right:auto"
								sort="list">
							<display:setProperty name="basic.msg.empty_list">
								<div class="separador8">&nbsp;</div>
								<bean:message key="organizacion.org.msgNoUsuariosOrgano"/>
								<div class="separador8">&nbsp;</div>
							</display:setProperty>
							<display:column title="" style="width:15px">
								<input type="checkbox" name="usuariosSeleccionados" value="<c:out value="${usuario.idUsuario}" />">
							</display:column>
							<display:column titleKey="organizacion.user.nombre">
								<c:out value="${usuario.nombreUsuario}" />
							</display:column>
							<%--<display:column titleKey="organizacion.user.sistExt">
								<c:out value="${usuario.sistemaExt}" />
							</display:column>--%>
						</display:table>
					</html:form>
					<div style="display:none;"></div>
				</tiles:put>
			</tiles:insert>
		</c:if>

		<div class="separador8">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="organizacion.org.listadescendientes"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<td>
						<c:url var="createURL" value="/action/gestionOrganizacionAction">
							<c:param name="method" value="altaOrgano" />
							<c:param name="idOrgano" value="${organizacion.id}"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" target="_self">
							<html:img page="/pages/images/new.gif" altKey="organizacion.crear.organo" titleKey="organizacion.crear.organo" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="organizacion.crear.descendiente"/>
						</a>
					</td>
				</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>

				<c:set var="treeView" value="${sessionScope[appConstants.organizacion.ORGANIZACION_VIEW_NAME]}" />
				<c:set var="selectedNode" value="${treeView.selectedNode}" />
				<c:set var="listaDescendientes" value="${sessionScope[appConstants.organizacion.LISTA_DESCENDIENTES_KEY]}" />
				<display:table name="pageScope.listaDescendientes" id="descendiente" style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<c:if test="${institucion}">
							<bean:message key="organizacion.institucion.nodescendientes"/>
						</c:if>
						<c:if test="${!institucion}">
							<bean:message key="organizacion.organo.nodescendientes"/>
						</c:if>
					</display:setProperty>
					<display:column titleKey="organizacion.org.codigonombre">
						<c:url var="viewElementURL" value="/action/manageVistaOrganizacion">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${selectedNode.nodePath}/item${descendiente.id}" />
							<c:param name="idOrganizacion" value="${descendiente.id}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="tdlink" href='<c:out value="${viewElementURL}" escapeXml="false"/>' target="_self">
							<c:out value="${descendiente.codigo}"/>&nbsp;<c:out value="${descendiente.nombre}"/>
						</a>
					</display:column>
				</display:table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>