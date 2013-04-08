<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoListaAcceso" value="${sessionScope[appConstants.controlAcceso.INFO_LISTA_ACCESO]}" />
<c:set var="tiposLista" value="${appConstants.tiposListasAcceso}" />

<bean:struts id="mappingGestionListaAcceso" mapping="/gestionListasAcceso" />

<c:set var="formBean" value="${sessionScope[mappingGestionListaAcceso.name]}" />
<c:choose>
	<c:when test="${param.method=='verUsuarios'}">
		<c:set var="verUsuarios" value="true"/>
		<c:set var="classTabDatosUsuario" value="tabActual" />
		<c:set var="classTabOrganos" value="tabSel" />
		<c:set var="classTabGrupos" value="tabSel" />
	</c:when>
	<c:when test="${param.method=='verOrganos'  || param.method=='verListaAcceso' }">
		<c:set var="verOrganos" value="true"/>
		<c:set var="classTabDatosUsuario" value="tabSel" />
		<c:set var="classTabOrganos" value="tabActual" />
		<c:set var="classTabGrupos" value="tabSel" />
	</c:when>
	<c:when test="${param.method=='verGrupos'}">
		<c:set var="verGrupos" value="true"/>
		<c:set var="classTabDatosUsuario" value="tabSel" />
		<c:set var="classTabOrganos" value="tabSel" />
		<c:set var="classTabGrupos" value="tabActual" />
	</c:when>
</c:choose>

<script>
	<c:url var="eliminarURL" value="/action/gestionListasAcceso">
		<c:param name="method" value="eliminarListaAcceso" />
		<c:param name="idListaAcceso" value="${infoListaAcceso.id}" />
	</c:url>
	function eliminarLista() {
		if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmListaEliminar'/>"))
			window.location = '<c:out value="${eliminarURL}" escapeXml="false"/>';
	}

	function eliminarElemento(){
		var form = document.forms['<c:out value="${mappingGestionListaAcceso.name}" />'];
		form.method.value = 'eliminarElementoDeLista';
		form.submit();
	}
</script>

<html:form action="/gestionListasAcceso" >
	<input type="hidden" name="method" value="eliminarElementoDeLista">

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.cacceso.verListaAcceso"/>
		</tiles:put>

		<tiles:put name="buttonBar" direct="true">
			<table><tr>
				<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_LISTA_ACCESO}">
				<td nowrap>

					<a class="etiquetaAzul12Bold" href="javascript:eliminarLista()" >
					<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
				<td width="10px"></td>
				</security:permissions>
				<td nowrap>
					<c:url var="cancelURL" value="/action/gestionListasAcceso">
						<c:param name="method" value="goBack" />
					</c:url>

					<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr></table>
		</tiles:put>




		<tiles:put name="boxContent" direct="true">
			<div class="separador1"></div>

			<div class="cabecero_bloque">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				  <TR>
					<TD class="etiquetaAzul12Bold" width="40%" >
						<bean:message key="archigest.archivo.informacion"/>
					</TD>
					<security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_LISTA_ACCESO}">
				    <TD class="etiquetaAzul12Bold" align="right" >
						<TABLE cellpadding=0 cellspacing=0>
						<TR>
							<td nowrap>
								<c:url var="edicionURL" value="/action/gestionListasAcceso">
									<c:param name="method" value="edicionListaAcceso" />
									<c:param name="idListaAcceso" value="${infoListaAcceso.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${edicionURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.editar"/></a>
							</td>
						</TR>
						</TABLE>
					</TD>
					</security:permissions>
				  </TR>
				</TABLE>
			</div>

			<div class="bloque" style="margin-bottom:6px">

				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="120px">
							<bean:message key="archigest.archivo.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${infoListaAcceso.nombre}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo" width="120px"><bean:message key="archigest.archivo.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${infoListaAcceso.tipo==tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}">
									<bean:message key="archigest.archivo.elementoDelCuadro"/>
								</c:when>
								<c:when test="${infoListaAcceso.tipo==tiposLista['DESCRIPTOR']}">
									<bean:message key="archigest.archivo.descriptor"/>
								</c:when>
								<c:when test="${infoListaAcceso.tipo==tiposLista['FORMATO_FICHA']}">
									<bean:message key="archigest.archivo.formatoFicha"/>
								</c:when>
							</c:choose>

						</td>
					</tr>
					<tr>
						<td class="tdTitulo" style="vertical-align:top;">
							<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${infoListaAcceso.descripcion}" /></td>
					</tr>
				</table>
			</div>


			<div class="cabecero_tabs">
				<table cellspacing="0" cellpadding=0>
					<tr>

				    	<td class="<c:out value='${classTabOrganos}'/>" id="porganos">
							<c:url var="verOrganosURL" value="/action/gestionListasAcceso">
								<c:param name="method" value="verOrganos" />
							</c:url>
							<a href="<c:out value="${verOrganosURL}" escapeXml="false"/>" id="torganos" class="textoPestana">
								<bean:message key="archigest.archivo.organos"/>
							</a>
					    </td>
						<td width="5px">&nbsp;</td>
				    	<td class="<c:out value='${classTabDatosUsuario}'/>" id="pusuarios">
							<c:url var="verUsuariosURL" value="/action/gestionListasAcceso">
								<c:param name="method" value="verUsuarios" />
							</c:url>
							<a href="<c:out value="${verUsuariosURL}" escapeXml="false"/>" id="tusuarios" class="textoPestana">
								<bean:message key="archigest.archivo.usuarios"/>
							</a>
					    </td>
						<td width="5px">&nbsp;</td>

				    	<td class="<c:out value='${classTabGrupos}'/>" id="pgrupos">
							<c:url var="verGruposURL" value="/action/gestionListasAcceso">
								<c:param name="method" value="verGrupos" />
							</c:url>
							<a href="<c:out value="${verGruposURL}" escapeXml="false"/>" id="tgrupos" class="textoPestana">
								<bean:message key="archigest.archivo.grupos"/>
							</a>
					    </td>
					</tr>
				</table>
			</div>

			<div class="bloque_tab"> <%--primer bloque de datos --%>
			<%--Botones --%>

				<DIV class="cabecero_bloque_tab"> <%--cabecero primer bloque de datos --%>
					<c:url var="anadirURL" value="/action/gestionListasAcceso">
						<c:param name="method" value="inicioBusquedaDestinatarios" />
					</c:url>
					<c:set var="eliminarURL" value="javascript:eliminarElemento()"/>

					<c:choose>
						<c:when test="${verUsuarios}">
							<c:set var="imageAnadir" value="/pages/images/addDoc.gif"/>
							<c:set var="imageDelete" value="/pages/images/delDoc.gif"/>
							<c:set var="actionToValidate" value="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}"/>
						</c:when>
						<c:when test="${verGrupos}">
							<c:set var="imageAnadir" value="/pages/images/addDoc.gif"/>
							<c:set var="imageDelete" value="/pages/images/delDoc.gif"/>
							<c:set var="actionToValidate" value="${appConstants.controlAccesoActions.MODIFICACION_GRUPO}"/>
						</c:when>
						<c:when test="${verOrganos}">
							<c:set var="imageAnadir" value="/pages/images/addDoc.gif"/>
							<c:set var="imageDelete" value="/pages/images/delDoc.gif"/>
							<c:set var="actionToValidate" value="${appConstants.controlAccesoActions.MODIFICACION_ORGANO}"/>
						</c:when>
					</c:choose>
					<TABLE class="w98m1" cellpadding=0 cellspacing=0>
					  <TR>
						<TD class="etiquetaAzul12Bold" width="80%" >
								&nbsp;
						</TD>
					    <TD class="etiquetaAzul12Bold" align="right" >
						<security:permissions action="${actionToValidate}">
							<TABLE>
							<TR>
								<TD nowrap>
									<jsp:useBean id="imageAnadir" type="java.lang.String" />
									<a class="etiquetaAzul12Bold" href="<c:out value="${anadirURL}" escapeXml="false"/>" >
									<html:img page="<%=imageAnadir%>" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.anadir"/></a>
								</TD>
								<td width="10px">&nbsp;</td>
								<td nowrap>
									<jsp:useBean id="imageDelete" type="java.lang.String" />
									<a class="etiquetaAzul12Bold" href="<c:out value="${eliminarURL}" escapeXml="false"/>" >
									<html:img page="<%=imageDelete%>" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.eliminar"/></a>
								</td>
							</TR>
							</TABLE>
					</security:permissions>
						</TD>
					   </TR>
					</TABLE>
				</DIV>

				<c:set var="tipoDestinatario" value="${appConstants.tiposIntegrantesListaAcceso}" />

				<c:set var="usuarioValidado" value="false"/>
				<security:permissions action="${actionToValidate}">
				<c:set var="usuarioValidado" value="true"/>
				</security:permissions>

				<c:if test="${verUsuarios}">
					<input type="hidden" name="tipoElementoSeleccionado" value="<c:out value="${tipoDestinatario.USUARIO}" />">

					<div id="usuarios">
						<div class="separador8">&nbsp;</div>
						<c:set var="listaUsuarios" value="${infoListaAcceso.usuariosEnLista}" />
						<display:table name="pageScope.listaUsuarios"
								id="usuario"
								style="width:98%;margin-left:auto;margin-right:auto"
								sort="list">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.cacceso.msgNoUsersListaAcceso"/>
							</display:setProperty>
						<security:permissions action="${actionToValidate}">
							<display:column style="width:10px" title="">
								<input type="checkbox" name="destinatariosSeleccionadosABorrar" value="<c:out value="${usuario.id}" />" >
							</display:column>
						</security:permissions>
							<display:column titleKey="archigest.archivo.nombre">
								<c:url var="verURL" value="/action/gestionListasAcceso">
									<c:param name="method" value="verElementoLista" />
									<c:param name="idElementoSeleccionado" value="${usuario.id}" />
									<c:param name="disableBackButton" value="true"/>
									<c:if test="${!usuarioValidado}">
									<c:param name="modoVista" value="true"/>
									</c:if>
								</c:url>
								<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
								<c:out value="${usuario.nombreCompleto}" />
								</a>
							</display:column>
						</display:table>
						<div class="separador8">&nbsp;</div>
					</div>
				</c:if>


			<c:if test="${verOrganos}">
			<input type="hidden" name="tipoElementoSeleccionado" value="<c:out value="${tipoDestinatario.ORGANO}" />">
			<div id="organos" >
				<div class="separador8">&nbsp;</div>

				<c:set var="listaOrganos" value="${infoListaAcceso.organosEnLista}" />

				<display:table name="pageScope.listaOrganos"
						id="organo"
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoOrganosListaAcceso"/>
					</display:setProperty>
					<security:permissions action="${actionToValidate}">
						<display:column style="width:10px" title="">
							<input type="checkbox" name="destinatariosSeleccionadosABorrar" value="<c:out value="${organo.idOrg}" />" >
						</display:column>
					</security:permissions>
					<display:column titleKey="archigest.archivo.nombre">
						<c:url var="verURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verElementoLista" />
							<c:param name="idElementoSeleccionado" value="${organo.idOrg}" />
							<c:param name="disableBackButton" value="true"/>
							<c:if test="${!usuarioValidado}">
							<c:param name="modoVista" value="true"/>
							</c:if>
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${organo.nombreLargo}" />
						</a>
					</display:column>
				</display:table>

				<div class="separador8">&nbsp;</div>
			</div>
			</c:if>


			<c:if test="${verGrupos}">
			<input type="hidden" name="tipoElementoSeleccionado" value="<c:out value="${tipoDestinatario.GRUPO}" />">

			<div id="grupos" >

				<div class="separador8">&nbsp;</div>
				<c:set var="listaGrupos" value="${infoListaAcceso.gruposEnLista}" />
				<display:table name="pageScope.listaGrupos"
						id="grupo"
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoGruposListaAcceso"/>
					</display:setProperty>
					<c:if test="${usuarioValidado}">
						<display:column style="width:10px" title="">
							<input type="checkbox" name="destinatariosSeleccionadosABorrar" value="<c:out value="${grupo.id}" />" >
						</display:column>
					</c:if>
					<display:column titleKey="archigest.archivo.nombre">
						<c:url var="verURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verElementoLista" />
							<c:param name="idElementoSeleccionado" value="${grupo.id}" />
							<c:param name="disableBackButton" value="true"/>
							<c:if test="${!usuarioValidado}">
							<c:param name="modoVista" value="true"/>
							</c:if>
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${grupo.nombre}" />
						</a>
					</display:column>
				</display:table>
				<div class="separador8">&nbsp;</div>
			</div>
			</c:if>

	</tiles:put>
</tiles:insert>
</html:form>
