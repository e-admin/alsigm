<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mapping" mapping="/gestionRegistroConsultaAction" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="listaUsuariosConsulta" value="${sessionScope[appConstants.salas.LISTA_USUARIOS_CONSULTA_KEY]}" />
<c:set var="tiposDoc" value="${sessionScope[appConstants.salas.LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY]}" />

<html:form action="/gestionRegistroConsultaAction" >
	<input type="hidden" name="method" value="buscarUsuarios">

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.registro.busqueda.usuario"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					form.submit();
				}

				function seleccionarUsuario() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					if (form.usuarioSeleccionado && elementSelected(form.usuarioSeleccionado)) {
						form.method.value = "seleccionarUsuario";
						form.submit();
					}else{
						alert("<bean:message key='archigest.archivo.salas.msgNoUsuarioSeleccionado'/>");
					}
				}
			</script>
			<table>
				<tr>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
						<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.buscar"/></a>
					</td>
					<c:if test="${not empty listaUsuariosConsulta}">
						<td width="10"></td>
						<td nowrap>
							<a class="etiquetaAzul12Bold" href="javascript:seleccionarUsuario()" >
							<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.siguiente"/></a>
						</td>
					</c:if>
					<td width="10"></td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true"/>
					</td>
				</tr>
			</table>
		</tiles:put>

		<tiles:put name="boxContent" direct="true">
			<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.doc.identificativo"/>:</td>
						<td class="tdDatos">
							<html:select property="searchTokenTipoDoc">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="tiposDoc" property="id" labelProperty="nombre" />
							</html:select>
							<html:text property='searchTokenNumDoc' styleClass="input" maxlength="32" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.nombre"/>:</td>
						<td class="tdDatos">
							<html:text property="searchTokenNombre" styleClass="input" size="50" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.apellidos"/>:</td>
						<td class="tdDatos">
							<html:text property="searchTokenApellidos" styleClass="input90"/>
						</td>
					</tr>
				</table>
			</div>

			<c:if test="${listaUsuariosConsulta != null}">
				<div class="separador8">&nbsp;</div>
				<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true">
						<bean:message key="archigest.archivo.resultadosBusqueda"/>
					</tiles:put>
					<tiles:put name="blockContent" direct="true">

						<c:url var="paginationURL" value="/action/gestionRegistroConsultaAction" />
						<jsp:useBean id="paginationURL" type="java.lang.String" />

						<display:table name="pageScope.listaUsuariosConsulta"
								id="usuario"
								style="width:98%;margin-left:auto;margin-right:auto"
								sort="list"
								requestURI="<%=paginationURL%>"
								pagesize="10">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.cacceso.busquedaUsuarioSinResultado"/>
							</display:setProperty>

							<display:column style="width:10px" title="">
								<html-el:radio property="usuarioSeleccionado" value="${usuario.id}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.nombre"
								property="nombre" sortProperty="nombre" sortable="true" headerClass="sortable"/>
							<display:column titleKey="archigest.archivo.apellidos"
								property="apellidos" sortProperty="apellidos" sortable="true" headerClass="sortable"/>
							<display:column titleKey="archigest.archivo.numdoc"
								property="numDocIdentificacion" sortProperty="numDocIdentificacion" sortable="true" headerClass="sortable"/>
						</display:table>
					</tiles:put>
				</tiles:insert>
			</c:if>
		</tiles:put>
	</tiles:insert>
</html:form>