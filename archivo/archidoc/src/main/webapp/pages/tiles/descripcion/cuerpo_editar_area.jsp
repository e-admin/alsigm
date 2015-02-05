<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld"
	prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<bean:struts id="actionMapping" mapping="/gestionArea" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />



<html:form action="/gestionArea">

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.cacceso.gestionArea" />
		</tiles:put>

		<tiles:put name="buttonBar" direct="true">
			<table>
				<tr>
					<td><a class="etiquetaAzul12Bold"
						href="javascript:create(document.forms['<c:out value="${actionMapping.name}" />'],'<bean:message key='archigest.archivo.descripcion.areas.form.nombre.empty.msg'/>')"><input
						type="image" src="../images/Ok_Si.gif"
						alt="<bean:message key="archigest.archivo.aceptar"/>"
						title="<bean:message key="archigest.archivo.aceptar"/>"
						class="imgTextMiddle" />&nbsp;<bean:message
						key="archigest.archivo.aceptar" /></a></td>
					<td width="10">&nbsp;</td>
					<td><a class="etiquetaAzul12Bold"
						href="javascript:call(document.forms['areasForm'],'goBack')"><html:img
						page="/pages/images/Ok_No.gif" border="0"
						altKey="archigest.archivo.cancelar"
						titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />&nbsp;<bean:message
						key="archigest.archivo.cancelar" /></a></td>
				</tr>
			</table>
		</tiles:put>


		<tiles:put name="boxContent" direct="true">
			<div id="barra_errores"><archivo:errors /></div>
			<div class="bloque"><%-- bloque datos tabla --%>
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="150"><bean:message
						key="archigest.archivo.identificador" />:&nbsp;</td>
					<td class="tdDatos"><html:hidden property="id" /> <c:choose>
						<c:when test="${empty formBean.id}">
							<html:text property="guid" maxlength="32" size="40" />
						</c:when>
						<c:otherwise>
							<c:out value="${formBean.id}" />
						</c:otherwise>
					</c:choose></td>
				</tr>

				<tr>
					<td class="tdTitulo" width="150"><bean:message
						key="archigest.archivo.descripcion.areas.form.nombre" />:&nbsp;</td>
					<td class="tdDatos"><html:text property="nombre"
						styleClass="input99" maxlength="128" />&nbsp;</td>
				</tr>

				<tr>
					<td class="tdTitulo" width="150"><bean:message
						key="archigest.archivo.descripcion.areas.form.tipoNorma" />:&nbsp;</td>
					<td class="tdDatos"><html:select property="tipoNorma"
						styleClass="input">
						<html:optionsCollection
							name="DescripcionConstants_LISTA_TIPO_NORMAS_KEY" value="value"
							label="label" />
					</html:select></td>
				</tr>

				<tr>
					<td class="tdTitulo" width="150" style="vertical-align: top;"><bean:message
						key="archigest.archivo.descripcion.areas.form.descripcion" />:&nbsp;</td>
					<td class="tdDatos"><html:textarea property="descripcion"
						rows="10" onchange="maxlength(this,254,true)"
						onkeypress="maxlength(this,254,false)" />&nbsp;</td>
				</tr>
			</table>
			</div>

			<c:set var="listaCamposDato"
							value="${sessionScope[appConstants.controlAcceso.LISTA_CAMPOS_DATO]}" />


			<c:if test ="${not empty listaCamposDato}">

			<div class="separador8"></div>
				<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true">
						<bean:message key="archigest.archivo.menu.admin.campos" />
					</tiles:put>

					<tiles:put name="blockContent" direct="true">
						<div class="separador8"></div>

						<c:url var="paginationURL"
							value="/action/gestionArea?method=retrieveFromList" />
						<jsp:useBean id="paginationURL" type="java.lang.String" />

						<display:table name="pageScope.listaCamposDato" id="campo"
							style="width:98%;margin-left:auto;margin-right:auto" sort="page"
							requestURI='<%=paginationURL%>' export="true" pagesize="15"
							excludedParams="method">

							<display:column
								titleKey="archigest.archivo.menu.admin.gestionCampo.area"
								property="nombreArea" sortable="true" headerClass="sortable"
								media="csv excel pdf xml" />

							<display:column titleKey="archigest.archivo.identificador"
								property="id" sortProperty="id" sortable="true"
								headerClass="sortable" />
							<display:column
								titleKey="archigest.archivo.menu.admin.gestionCampo.nombre"
								property="nombre" sortable="true" />
							<display:column
								titleKey="archigest.archivo.menu.admin.gestionCampo.tipo"
								property="tipoText" sortable="true" headerClass="sortable" />
							<display:column
								titleKey="archigest.archivo.menu.admin.gestionCampo.norma"
								property="tipoNormaText" sortable="true" headerClass="sortable" />

							<display:column titleKey="archigest.archivo.etiqueta" property="etiquetaXmlText" sortable="true" headerClass="sortable"/>

							<display:column
								titleKey="archigest.archivo.campo.contenido.en.tabla"
								property="nombreTabla" sortable="true" headerClass="sortable" />

						</display:table>
					</tiles:put>
				</tiles:insert>

				</c:if>

			</tiles:put>
	</tiles:insert>
</html:form>