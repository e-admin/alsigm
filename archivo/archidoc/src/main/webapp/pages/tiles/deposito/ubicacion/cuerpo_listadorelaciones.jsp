<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<div id="contenedor_ficha">

<html:form action="/gestionUbicacionRelaciones">
<input type="hidden" name="method" value="comprobarreserva">
<div class="ficha">
<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			&nbsp;<bean:message key="archigest.archivo.deposito.ubicar" />
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<c:if test="${!empty requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}">
				<bean:struts id="mappingUbicacionRelacion" mapping="/gestionUbicacionRelaciones" />
				<TD noWrap>
					<script>
							function ubicarRelacion() {
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.operacion.realizandoCHuecos"/>';
									var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoCHuecos"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								document.forms['<c:out value="${mappingUbicacionRelacion.name}" />'].submit();
							}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:ubicarRelacion();" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</TD>
				</c:if>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<c:url var="urlCancelar" value="/action/gestionUbicacionRelaciones">
						<c:param name="method" value="goBack"/>
					</c:url>
					<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlCancelar}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	</div>
</span></h1>


<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<div class="bloque"> <%--primer bloque de datos --%>

	<div class="titulo_left_bloque">
	<bean:message key="archigest.archivo.deposito.relUbicar"/>
	</div>

	<c:set var="relaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>
	<c:url var="vistaRelacionesPaginationURI" value="/action/gestionUbicacionRelaciones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="vistaRelacionesPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.relaciones"
		id="listaRegistros"
		pagesize="20"
		requestURI="<%=vistaRelacionesPaginationURI%>"
		sort="list"
		export="false"
		style="width:99%;margin-left:auto;margin-right:auto">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noRel"/>
		</display:setProperty>

		<display:column title="&nbsp;">
			<INPUT type=radio name="idrelacionseleccionada" value='<c:out value="${listaRegistros.id}"/>'
				<c:if test="${ReservaForm.idrelacionseleccionada == listaRegistros.id}">
					<c:out value="checked"/>
				</c:if>
			>
		</display:column>
		<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" >
			<c:url var="verRelacionURL" value="/action/gestionRelaciones">
				<c:param name="method" value="verrelacion" />
				<c:param name="idrelacionseleccionada" value="${listaRegistros.id}" />
			</c:url>
			<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
				<c:out value="${listaRegistros.codigoTransferencia}"/>
			</a>
		</display:column>
		<display:column titleKey="archigest.archivo.transferencias.nUndInstal">
			<c:out value="${listaRegistros.textoNumeroUIs}"/>
		</display:column>
		<display:column titleKey="archigest.archivo.estado" sortable="true" headerClass="sortable">
			<c:out value="${listaRegistros.nombreestado}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable">
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.relaciones.busqueda.gestorArchivo" sortable="true" headerClass="sortable">
			<c:if test="${listaRegistros.idusrgestorarchivorec !=null}" >
			<c:set var="gestor" value="${listaRegistros.gestorEnArchivo}" />
				<c:if test="${!empty gestor.apellidos}">
					<c:out value="${gestor.apellidos}"/>,
				</c:if>
				<c:out value="${gestor.nombre}"/>
			</c:if>
		</display:column>
		<display:column titleKey="archigest.archivo.deposito.formato" sortable="true" headerClass="sortable">
			<c:out value="${listaRegistros.nombreFormato}"/>
		</display:column>
	</display:table>


</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>