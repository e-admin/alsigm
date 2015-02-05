<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/gestionSalasAction" />

<c:set var="sala" value="${sessionScope[appConstants.salas.SALA_KEY]}" />
<c:set var="listaMesas" value="${sessionScope[appConstants.salas.LISTA_MESAS_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.sala"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<c:url var="verPadreURL" value="/action/gestionEdificiosAction">
					<c:param name="method" value="verEdificio" />
					<c:param name="idEdificio" value="${sala.idEdificio}"/>
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${verPadreURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cf.verPadre"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
				<security:permissions action="${appConstants.salasActions.MODIFICAR_SALA_ACTION}">
				<td>
					<c:url var="editarURL" value="/action/gestionSalasAction">
						<c:param name="method" value="edicion" />
						<c:param name="idSala" value="${sala.id}" />
						<c:param name="idEdificio" value="${sala.idEdificio}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${editarURL}" escapeXml="false"/>">
						<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<security:permissions action="${appConstants.salasActions.ELIMINAR_SALA_ACTION}">
				<td>
					<c:url var="eliminarURL" value="/action/gestionSalasAction">
						<c:param name="method" value="eliminar" />
						<c:param name="idSala" value="${sala.id}" />
					</c:url>
					<script>
						function eliminar()
						{
							if (confirm('<bean:message key="archigest.archivo.salas.eliminarSalaMsg"/>'))
								window.location = '<c:out value="${eliminarURL}" escapeXml="false"/>';
						}
					</script>
					<a class=etiquetaAzul12Bold href="javascript:eliminar()">
						<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
			<td nowrap>
				<tiles:insert definition="button.closeButton" />
			</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.ver.sala"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<tiles:insert name="salas.datos.sala" flush="true"/>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

	 	<div class="cabecero_bloque_sin_height">
			<table class="w98m1" cellpadding=0 cellspacing=0>
				<tr>
					<td class="etiquetaAzul12Bold" width="30%">
						<bean:message key="archigest.archivo.deposito.configuracion" />
						<bean:message key="archigest.archivo.salas.mesas" />
					</td>
					<td width="70%" align="right">
						<table cellpadding=0 cellspacing=0>
							<tr>
								<security:permissions action="${appConstants.salasActions.ALTA_MESA_ACTION}">
								<td nowrap>
									<c:url var="crearMesasURL" value="/action/gestionMesasAction">
										<c:param name="method" value="nuevo" />
										<c:param name="idSala" value="${sala.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="<c:out value="${crearMesasURL}" escapeXml="false"/>">
										<html:img page="/pages/images/mesa_crear.gif" altKey="archigest.archivo.salas.crear.mesas"
												  titleKey="archigest.archivo.salas.crear.mesas" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.salas.crear.mesas"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
								</security:permissions>
								<security:permissions action="${appConstants.salasActions.ELIMINAR_MESA_ACTION}">
								<td nowrap>
									<c:url var="eliminarMesasURL" value="/action/gestionMesasAction">
										<c:param name="method" value="eliminarMesas" />
										<c:param name="idSala" value="${sala.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="<c:out value="${eliminarMesasURL}" escapeXml="false"/>">
										<html:img page="/pages/images/mesa_borrar.gif" altKey="archigest.archivo.salas.eliminar.mesas"
												  titleKey="archigest.archivo.salas.eliminar.mesas" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.salas.eliminar.mesas"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
								</security:permissions>
								<security:permissions action="${appConstants.salasActions.MODIFICAR_MESA_ACTION}">
								<td nowrap>
									<c:url var="cambiarCodigosMesasURL" value="/action/gestionMesasAction">
										<c:param name="method" value="modificarMesas" />
										<c:param name="idSala" value="${sala.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="<c:out value="${cambiarCodigosMesasURL}" escapeXml="false"/>">
										<html:img titleKey="archigest.archivo.salas.cambiar.codigos.mesas"
										altKey="archigest.archivo.salas.cambiar.codigos.mesas" page="/pages/images/mesa_editar.gif" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.salas.cambiar.codigos.mesas"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
								<td nowrap>
									<c:url var="habilitarMesasURL" value="/action/gestionEstadoMesasAction">
										<c:param name="method" value="habilitarMesas" />
										<c:param name="idSala" value="${sala.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="<c:out value="${habilitarMesasURL}" escapeXml="false"/>">
										<html:img titleKey="archigest.archivo.deposito.habilitar" altKey="archigest.archivo.deposito.habilitar" page="/pages/images/mesaLibre.gif" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.deposito.habilitar"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
								<td nowrap>
									<c:url var="inhabilitarMesasURL" value="/action/gestionEstadoMesasAction">
										<c:param name="method" value="inhabilitarMesas" />
										<c:param name="idSala" value="${sala.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="<c:out value="${inhabilitarMesasURL}" escapeXml="false"/>">
										<html:img titleKey="archigest.archivo.deposito.inutilizar" altKey="archigest.archivo.deposito.inutilizar" page="/pages/images/mesaInutilizada.gif" styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.deposito.inutilizar"/>
									</a>
								</td>
								</security:permissions>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div> <%-- cabecero bloque --%>

		<div class="bloque">
			<div class="separador8">&nbsp;</div>
			<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
				<table class="tblMesas" style="width:98%;table-layout:auto;margin-left:5px">
					<tr>
						<c:forEach var="mesa" items="${listaMesas}" varStatus="loopStatus">
							<%--SALTO de FILA --%>
							<c:if test="${loopStatus.index !=0 && loopStatus.index % 8 == 0}"></TR><TR></c:if>
							<td class="tdTituloFichaSinBorde" nowrap>
								<b><c:out value="${mesa.codigo}" /></b>
								<c:choose>
									<c:when test="${mesa.estado == appConstants.estadosMesa.OCUPADA}">
										<c:url var="imgMesa" value="/pages/images/mesaOcupada.gif" />
										<c:set var="altKey" value="archigest.archivo.salas.mesa.ocupada" />
									</c:when>
									<c:when test="${mesa.estado == appConstants.estadosMesa.INUTILIZADA}">
										<c:url var="imgMesa" value="/pages/images/mesaInutilizada.gif" />
										<c:set var="altKey" value="archigest.archivo.salas.mesa.inutilizada" />
									</c:when>
									<c:otherwise>
										<c:url var="imgMesa" value="/pages/images/mesaLibre.gif" />
										<c:set var="altKey" value="archigest.archivo.salas.mesa.libre" />
									</c:otherwise>
								</c:choose>

								<img src="<c:out value="${imgMesa}" escapeXml="false"/>" alt="<fmt:message key="${altKey}" />" title="<fmt:message key="${altKey}" />"/>
								<c:if test="${mesa.estado == appConstants.estadosMesa.OCUPADA}">
									<br>
									<c:url var="verUsuarioURL" value="/action/gestionRegistroConsultaAction">
										<c:param name="method" value="verByUsuario"/>
										<c:param name="idUsuario" value="${mesa.idUsrCSala}"/>
									</c:url>
									<a href="javascript:window.location='<c:out value="${verUsuarioURL}" escapeXml="false"/>'" class="mesa">
										<c:out value="${mesa.nombreUsuario}"/> <c:out value="${mesa.apellidosUsuario}"/>
									</a>
								</c:if>
							</td>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
	</tiles:put>
</tiles:insert>