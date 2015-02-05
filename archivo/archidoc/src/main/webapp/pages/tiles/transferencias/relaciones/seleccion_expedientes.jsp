<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>


<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="listaExpedientes" value="${sessionScope[appConstants.transferencias.LISTA_EXPEDIENTES]}" />
<c:set var="numExpedientes" value="${sessionScope[appConstants.transferencias.NUM_EXPEDIENTES]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.anadirUDoc"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
	       <TD>
				<script>
					function anadirExps() {
						var form = document.forms[0];
						if (form && form.expediente && elementSelected(form.expediente)) {
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}
							form.submit();
						} else
							alert("<bean:message key='archigest.archivo.solicitudes.expedienteNoSeleccionado'/>");
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:anadirExps();" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					<bean:message key="archigest.archivo.aceptar"/>&nbsp;
				</a>
		   </TD>
	       <TD width="10">&nbsp;</TD>
	       <TD>
				<c:url var="cancelURI" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				   	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
		   </TD>
	     </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

					<TABLE class="w98m1" cellpadding=0 cellspacing=2>
						<TR>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.codigoTransferencia}"/>
								</span>
							</TD>
							<TD width="25%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.nombreestado}"/>
								</span>
							</TD>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
								</span>
							</TD>
							<TD width="35%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
								<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
								<span class="etiquetaNegra11Normal">
									<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
								</span>
							</TD>
						</TR>
					</TABLE>

			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>	

		<div class="separador8">&nbsp; </div>

		<div class="cabecero_bloque"> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>
			<bean:define id="showPrevNext" value="false" toScope="request"/>
			<jsp:include page="contador_unidaddocre.jsp" flush="true" />
		</div> <%--segundo bloque de datos (Contador de unidades documentales siempre visible) --%>
	
		<div class="separador8">&nbsp; </div>

		<html:form action="/gestionUdocsRelacion">
		<input type="hidden" name="method" value="seleccionExpedientes">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.anadirUDoc"/></tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<c:if test="${!empty listaExpedientes}">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
			   <TD>
					<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].expediente);" >
						<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
						<bean:message key="archigest.archivo.selTodos"/>&nbsp;
					</a>
			   </TD>
			   <TD width="20">&nbsp;</TD>
			   <TD>
					<c:url var="cancelURI" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="cancelarAccion"  />
					</c:url>
					<a class=etiquetaAzul12Normal href="javascript:deseleccionarCheckboxSet(document.forms[0].expediente);" >
						<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
					</a>
			   </TD>
			 </TR>
			</TABLE>
			</c:if>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<c:url var="listaExpedientesPaginationURI" value="/action/gestionUdocsRelacion">
				<c:param name="method" value="${param.method}" />
			</c:url>
			<jsp:useBean id="listaExpedientesPaginationURI" type="java.lang.String" />
	
			<c:if test="${!empty listaExpedientes}">
				<table class="tablaAncho99">
					<tr class="filaAlineadaDerecha">
						<td class="etiquetaAzul11Bold">
							<c:out value="${numExpedientes}"/>&nbsp;<bean:message key="archigest.archivo.unidadesDocumentales"/>
						</td>
					</tr>
				</table>
			</c:if>
			
			<display:table name="pageScope.listaExpedientes"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="expediente" 
				sort="list"
				pagesize="0"
				requestURI='<%=listaExpedientesPaginationURI%>'			
				export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noExpsParaRelacion"/>
				</display:setProperty>

				<display:column style="width:20px">
					<input type="checkbox" name="expediente" value="<c:out value="${expediente_rowNum - 1}" />" />
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.nExp" sortable="true" headerClass="sortable" >
					<c:out value="${expediente.numExp}" />
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.asunto" sortable="true" headerClass="sortable" >
					<c:out value="${expediente.datosIdentificativos}" />
				</display:column>
			</display:table>

		</tiles:put>
		</tiles:insert>
		</html:form>

	</tiles:put>
</tiles:insert>	