<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="relaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>

<script>
	<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
	function eliminarRelaciones(){
		var formularioSeleccion = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if (formularioSeleccion.relacionesseleccionadas && elementSelected(formularioSeleccion.relacionesseleccionadas)) {
				if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.eliminacionWarning'/>")) {
					formularioSeleccion.method.value="eliminarrelaciones";
					formularioSeleccion.submit();
				}
			} else
				alert("<bean:message key='errors.relaciones.esNecesarioSeleccionarAlMenosUnaRelacion'/>");
	}

</script>

<div id="contenedor_ficha">
	<div class="ficha">
		<h1><span>
		<div class="w100">
		<table class="w98" cellpadding="0" cellspacing="0">
		<tr>
			<td class="etiquetaAzul12Bold" height="25px">
				<bean:message key="archigest.archivo.transferencias.relaciones"/>
			</td>
			<td align="right">
				<table cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${!empty relaciones}">
				       <td align="right">
							<a class="etiquetaAzul12Bold" href="javascript:eliminarRelaciones()" >
								<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
								&nbsp;<bean:message key="archigest.archivo.eliminar" />
							</a>
					   </td>
					   <td width="10">&nbsp;</td>
			       </c:if>
					<td><html:link styleClass="etiquetaAzul12Bold" action="buscarRelaciones?method=goBack">
						<html:img page="/pages/images/close.gif"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar"
						styleClass="imgTextMiddle" />&nbsp;
						<bean:message key="archigest.archivo.cerrar"/></html:link>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
		</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<div id="barra_errores"><archivo:errors /></div>
		   	<div class="separador1">&nbsp;</div>

			<div class="bloque" id="divTbl">

			<html:form action="/gestionRelaciones">
				<input type="hidden" name="method">
				<display:table name="pageScope.relaciones"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="relacion"
					pagesize="15"
					sort="list"
					requestURI="../../action/buscarRelaciones?method=buscar"
					export="true">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.relaciones.listado.vacio"/>
					</display:setProperty>

					<display:column media="html">
						<c:if test="${relacion.puedeSerEliminada || relacion.puedeSerEnviada}">
							<input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${relacion.id}"/>' >
						</c:if>
					</display:column>

					<display:column titleKey="archigest.archivo.relacion" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
						<c:url var="verURL" value="/action/gestionRelaciones">
							<c:param name="method" value="verrelacion" />
							<c:param name="idrelacionseleccionada" value="${relacion.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
							<c:out value="${relacion.codigoTransferencia}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.relacion" media="csv excel xml pdf">
						<c:out value="${relacion.codigoTransferencia}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.prevision" sortProperty="codigoPrevision" sortable="true" headerClass="sortable" media="html">
						<c:url var="verURLPrev" value="/action/gestionPrevisiones">
							<c:param name="method" value="verprevision" />
							<c:param name="idprevision" value="${relacion.idprevision}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURLPrev}" escapeXml="false"/>" >
							<c:out value="${relacion.prevision.codigoTransferencia}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.prevision" media="csv excel xml pdf">
						<c:out value="${relacion.prevision.codigoTransferencia}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.usuario" sortProperty="usuario" sortable="true" headerClass="sortable">
						<c:out value="${relacion.apellidosusuario}"/><c:if test="${!empty relacion.apellidosusuario && !empty relacion.nombreusuario}">, </c:if> <c:out value="${relacion.nombreusuario}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.tipoTrans" sortProperty="tipoTransferencia" sortable="true" headerClass="sortable">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipotrans<c:out value="${relacion.tipotransferencia}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.estado" sortProperty="estado" sortable="true" headerClass="sortable">
						<c:set var="keyTituloEstado">
							archigest.archivo.transferencias.estadoRelacion.<c:out value="${relacion.estado}"/>
						</c:set>
						<fmt:message key="${keyTituloEstado}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.fEstado" sortProperty="fechaestado" sortable="true" headerClass="sortable">
						<fmt:formatDate value="${relacion.fechaestado}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>

					<security:permissions permission="${appConstants.permissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR}|${appConstants.permissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR}">
					<display:column titleKey="archigest.archivo.transferencias.orgRem" sortProperty="organo" sortable="true" headerClass="sortable">
						<c:out value="${relacion.nombreorgano}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.serie" sortProperty="serie" sortable="true" headerClass="sortable">
						<c:out value="${relacion.codigoserie}"/> <c:out value="${relacion.tituloserie}"/>
					</display:column>
					</security:permissions>

					<security:permissions permission="${appConstants.permissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE}">
					<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortProperty="procedimiento" sortable="true" headerClass="sortable">
						<c:out value="${relacion.procedimiento.id}"/> <c:out value="${relacion.procedimiento.nombre}"/>
					</display:column>
					</security:permissions>
				</display:table>
				</html:form>
			</div> <%--bloque --%>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>

	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
