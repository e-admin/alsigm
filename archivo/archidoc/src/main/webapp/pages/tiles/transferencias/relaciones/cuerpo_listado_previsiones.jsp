<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="listaPrevisiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.crearRel" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<c:if test="${!empty listaPrevisiones}">
		       <TD>
					<script>
						<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
						function seleccionarPrevision() {
							var selectionForm = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
							if (selectionForm && selectionForm.idprevisionseleccionada) {
								var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"idprevisionseleccionada");
								if (nSelected == 0)
									alert("<bean:message key='archigest.archivo.transferencias.selPrev'/>")
								else
									selectionForm.submit();
							}
						}
					</script>

			   		<a class=etiquetaAzul12Bold href="javascript:seleccionarPrevision()" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextTop" />
			   		 	&nbsp;<bean:message key="archigest.archivo.siguiente"/>
			   		</a>
		       </TD>
		       <TD width="10">&nbsp;</TD>
			</c:if>
		   <%-- CERRAR --%>
	       <TD>
				<c:url var="volverURL" value="/action/gestionRelaciones?method=goBack" />
		   		<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		   </TD>
	   		<c:if test="${appConstants.configConstants.mostrarAyuda}">
				<td width="10">&nbsp;</td>
				<td>
					<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
					<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
					<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/transferencias/creacionRelacion.html');">
					<html:img page="/pages/images/help.gif" 
					        altKey="archigest.archivo.ayuda" 
					        titleKey="archigest.archivo.ayuda" 
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
				</td>
			</c:if>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">Selección de previsión</tiles:put>
		<tiles:put name="blockContent" direct="true">

			<html:form action="/gestionRelaciones" >
			<input type="hidden" name="method" value="nueva" />

			<c:url var="listaPrevisionesPaginationURI" value="/action/gestionRelaciones">
				<c:param name="method" value="${param.method}" />
			</c:url>
			<jsp:useBean id="listaPrevisionesPaginationURI" type="java.lang.String" />
				
			<display:table name="pageScope.listaPrevisiones"
				id="prevision" 
				decorator="transferencias.decorators.ViewPrevisionDecorator"
				export="false"
				pagesize="10"
				requestURI='<%=listaPrevisionesPaginationURI%>'
				sort="list"
				style="width:99%;margin-left:auto;margin-right:auto"
				defaultorder="ascending"
				defaultsort="2" >
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.msgNoPrevCrearRel" />
				</display:setProperty>

				<display:column title="" >
				<c:choose>
				  <c:when test="${RelacionForm.idprevisionseleccionada == prevision.id}">
					<INPUT type=radio name="idprevisionseleccionada" value="<c:out value="${prevision.id}" />" checked >
				  </c:when>
				  <c:otherwise>
					<INPUT type=radio name="idprevisionseleccionada" value="<c:out value="${prevision.id}" />">
				  </c:otherwise>
				</c:choose>
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" >
					<c:url var="urlVerPrevision" value="/action/gestionPrevisiones">
						<c:param name="method" value="verprevision"/>
						<c:param name="idprevision" value="${prevision.id}"/>
					</c:url>
					<a class="tdlink" href='<c:out value="${urlVerPrevision}" escapeXml="false"/>' >
						<c:out value="${prevision.codigoTransferencia}"/>
					</a>

				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.gestor" sortable="true" headerClass="sortable">
					<c:out value="${prevision.gestor.nombreCompleto}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.orgRem" sortable="true" headerClass="sortable" style="width:400px">
					<c:out value="${prevision.organoRemitente.nombreLargo}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.FIT">
					<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.FFT">
					<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
				</display:column>
			</display:table>  
			</html:form>

		</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>	