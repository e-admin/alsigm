<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />

<div id="contenedor_ficha">

<html:form action="/gestionReservaRelaciones">
<input type="hidden" name="method" value="vernavegador" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reservas" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<bean:struts id="mappingRecepcionRelaciones" mapping="/gestionReservaRelaciones" />
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<c:if test="${!empty listaRelaciones}">
			<TD noWrap>
				<script>
					function goOn()
					{
						var form = document.forms['<c:out value="${mappingRecepcionRelaciones.name}" />'];
						if (FormsToolKit.getObjectSelected(form, "idrelacionseleccionada"))
							form.submit();
						else
							alert("<bean:message key="archigest.archivo.transferencias.relaciones.msgSelRel"/>");
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn();" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</TD>
			</c:if>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<c:url var="urlCancelar" value="/action/gestionReservaRelaciones">
					<c:param name="method" value="goBack"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${urlCancelar}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.relReservar"/></tiles:put>
		<tiles:put name="blockContent" direct="true">

			<c:set var="seleccionRelacionForm" value="${requestScope[mappingRecepcionRelaciones.name]}" />
			<c:url var="paginationURI" value="/action/gestionReservaRelaciones">
				<c:param name="method" value="${param.method}" />
			</c:url>
			<jsp:useBean id="paginationURI" type="java.lang.String" />
			<display:table name="pageScope.listaRelaciones"
				id="listaRegistros"
				pagesize="20"
				requestURI="<%=paginationURI%>"
				sort="list"
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noRel"/>
				</display:setProperty>

				<display:column title="&nbsp;">
					<INPUT type=radio name="idrelacionseleccionada" value='<c:out value="${listaRegistros.id}"/>'
						<c:if test="${seleccionRelacionForm.idrelacionseleccionada == listaRegistros.id}">
							<c:out value="checked"/>
						</c:if>
					>
				</display:column>
				<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable">
					<c:url var="verRelacionURL" value="/action/gestionRelaciones">
						<c:param name="method" value="verrelacion" />
						<c:param name="idrelacionseleccionada" value="${listaRegistros.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
						<c:out value="${listaRegistros.codigoTransferencia}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.estado" property="nombreestado" sortable="true" headerClass="sortable"/>

				<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.tipo" sortable="true" headerClass="sortable">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.relaciones.busqueda.gestorArchivo" sortable="true" headerClass="sortable">
					<c:set var="gestorEnArchivo" value="${listaRegistros.gestorEnArchivo}" />
					<c:if test="${!empty gestorEnArchivo}" >
						<c:out value="${gestorEnArchivo.nombreCompleto}"/>
					</c:if>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.formato" sortable="true" headerClass="sortable">
					<c:out value="${listaRegistros.nombreFormato}" />--
				</display:column>
			</display:table>

		</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>
</html:form>