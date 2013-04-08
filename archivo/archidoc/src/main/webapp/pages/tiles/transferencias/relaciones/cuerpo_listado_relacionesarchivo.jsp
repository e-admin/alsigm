<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="MOSTRAR_ELIMINAR" value="${requestScope[appConstants.transferencias.EXISTEN_RELACIONES_A_ELIMINAR]}"/>
<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>

<script>
	function eliminarRelaciones(){
		var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if (form.relacionesseleccionadas && elementSelected(form.relacionesseleccionadas)) {
			if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.eliminacionWarning'/>")) {
				form.method.value="eliminarrelaciones";
				form.submit();
			}
		} else{
			alert("<bean:message key='errors.relaciones.esNecesarioSeleccionarAlMenosUnaRelacion'/>");
		}
	}
</script>

<html:form action="/gestionRelaciones">
<input type="hidden" name="method">
<div id="contenedor_ficha">
<div class="ficha">
<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.transferencias.relacionesGestionar"/>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
	  	<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
		<c:if test="${MOSTRAR_ELIMINAR}">
       	<TD>
			<a class="etiquetaAzul12Bold" href="javascript:eliminarRelaciones()" >
				<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
				&nbsp;<bean:message key="archigest.archivo.eliminar" />
			</a>
	   	</TD>
	    <TD width="10">&nbsp;</TD>
        </c:if>
		</security:permissions>
	   <TD>
		<tiles:insert definition="button.closeButton" />
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

<span class="separador1"></span>
<DIV class="bloque">

	<c:url var="listaRelacionesPaginationURI" value="/action/gestionRelaciones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaRelacionesPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.listaRelaciones"
		id="listaRegistros"
		pagesize="10"
		requestURI="<%=listaRelacionesPaginationURI%>"
		decorator="transferencias.decorators.ViewRelacionEntregaDecorator"
		sort="list"
		export="true"
		style="width:99%;margin-left:auto;margin-right:auto"
		defaultorder="descending"
		defaultsort="4">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noRel"/>
		</display:setProperty>

		<display:column media="html">
			<c:if test="${listaRegistros.puedeSerEliminada}">
				<input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${listaRegistros.id}"/>' >
			</c:if>
		</display:column>

		<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="html" sortProperty="codigoTransferencia">
			<html:img page="/pages/images/pixel.gif" width="1" height="20" styleClass="imgTextMiddle"/>
			<c:url var="verRelacionURL" value="/action/gestionRelaciones">
				<c:param name="method" value="verrelacion" />
				<c:param name="idrelacionseleccionada" value="${listaRegistros.id}" />
			</c:url>
			<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>">
				<c:out value="${listaRegistros.codigoTransferencia}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.relacion" style="white-space: nowrap;" media="csv excel xml pdf">
			<c:out value="${listaRegistros.codigoTransferencia}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable" media="html"/>
		<display:column titleKey="archigest.archivo.transferencias.estado" media="csv excel xml pdf">
			<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${listaRegistros.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

		<display:column titleKey="archigest.archivo.transferencias.gestor" sortable="true" headerClass="sortable">
			<c:set var="gestorEnOrganoRemitente" value="${listaRegistros.gestorEnOrganoRemitente}" />
			<c:out value="${gestorEnOrganoRemitente.apellidos}"/><c:if test="${!empty gestorEnOrganoRemitente.apellidos && !empty gestorEnOrganoRemitente.nombre}">, </c:if> <c:out value="${gestorEnOrganoRemitente.nombre}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.gestorArchivo" sortable="true" headerClass="sortable">
			<c:if test="${!empty listaRegistros.idusrgestorarchivorec}" >
				<c:set var="gestorEnArchivo" value="${listaRegistros.gestorEnArchivo}" />
				<c:out value="${gestorEnArchivo.apellidos}"/><c:if test="${!empty gestorEnArchivo.apellidos && !empty gestorEnArchivo.nombre}">, </c:if> <c:out value="${gestorEnArchivo.nombre}"/>
			</c:if>
		</display:column>


		<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortable="true" headerClass="sortable" style="width:20%">
				<c:set var="procedimiento" value="${listaRegistros.procedimiento}" />
				<c:out value="${procedimiento.codigo}"/> <c:out value="${procedimiento.nombre}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.serie" sortable="true" headerClass="sortable" style="width:20%">
				<c:out value="${listaRegistros.serie.codigo}"/> <c:out value="${listaRegistros.serie.titulo}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.formato" sortable="true" headerClass="sortable">
			<c:out value="${listaRegistros.nombreFormato}"/>
		</display:column>

	</display:table>

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>