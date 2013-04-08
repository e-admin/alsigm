<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="mappingGestionEstadoHuecos" mapping="/gestionEstadoHuecosAction" />
<c:set var="elementoAsignable" value="${requestScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />
<c:set var="EDITABLE_NUMERACION" value="${requestScope[appConstants.deposito.EDITABLE_NUMERACION_KEY]}" />
<c:set var="hayElementoASeleccionar" value="${requestScope[mappingGestionEstadoHuecos.name].hayElementoASeleccionar}" />


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.editarNumeracion" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<c:if test="${hayElementoASeleccionar}">
				<td>
					<script>
						function go() {
							var form = document.forms['<c:out value="${mappingGestionEstadoHuecos.name}" />'];
							if (numElementsSelected(form.numeroOrdenHueco) > 1)
								alert("<bean:message key='archigest.archivo.deposito.seleccionarSoloUnHueco'/>");							
							else if (form.numeroOrdenHueco && elementSelected(form.numeroOrdenHueco)) 
								form.submit();
							else alert("<bean:message key='archigest.archivo.deposito.esNecesarioSeleccionarAlMenosUnHueco'/>");
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:go()" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</c:if>
				<td noWrap>
					<tiles:insert definition="button.closeButton">
						<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
						<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.datosBalda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${elementoAsignable.pathName}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.longitud}" /> <bean:message key="archigest.archivo.cm"/>.
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.formato.nombre}" /> 
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.huecos"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.numhuecos}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>
			<html:form action="/gestionEstadoHuecosAction">
				<input type="hidden" name="method" value="setNumeracion">
				<html:hidden property="idAsignable" />
			
				<div class="cabecero_bloque">
					<table class="w98m1" cellpadding=0 cellspacing=0 height="100%">
						<tr>
							<td class="etiquetaAzul12Bold" width="40%">
								<bean:message key="archigest.archivo.deposito.seleccionHuecoAEditar" />								
							</td>
						</tr>
					</table>
				</div> <%--cabecero bloque --%>
			
				<div class="bloque">			
				<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
					<TABLE class="tblHuecos" style="width:98%;table-layout:auto;margin-left:5px">
						<div class="separador5">&nbsp;</div>
						<tr>
						<c:set var="listaHuecos" value="${requestScope[appConstants.deposito.LISTA_HUECOS_KEY]}" />
						<c:forEach var="hueco" items="${listaHuecos}" varStatus="loopStatus">
							<%--SALTO de FILA --%>
							<c:if test="${loopStatus.index !=0 && loopStatus.index % 8 == 0}"></TR><TR></c:if>
							<td class="tdTituloFichaSinBorde" noWrap>
								<c:if test="${hueco.estado == appConstants.estadosHueco.LIBRE_STATE}">
									<c:set var="numeroHueco" value="${hueco.numorden}" />
									<jsp:useBean id="numeroHueco" type="java.lang.Integer" />
									<c:set var="formulario" value="${requestScope[mappingGestionEstadoHuecos.name]}"/>
									<input name="numeroOrdenHueco" type="checkbox" value="<%=numeroHueco%>"
									<c:if test="${formulario.numeroOrdenHueco==numeroHueco}">checked</c:if> 
									>	
								</c:if>		
								<bean:message key="archigest.archivo.deposito.inicialHueco" />											
								<b><c:out value="${hueco.numorden}" />
									<c:if test="${EDITABLE_NUMERACION && hueco.estado != appConstants.estadosHueco.OCUPADO_STATE}">
										<c:out value="${appConstants.common.SEPARADOR_NUM_HUECOS}"/> <c:out value="${hueco.numeracion}" />
									</c:if>							
								</b>	
				
								<c:choose>
									<c:when test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}">
										<c:url var="imgHueco" value="/pages/images/huecoAzul.gif" />
										<c:set var="altKey" value="archigest.archivo.deposito.ocupado" />
									</c:when>
									<c:when test="${hueco.estado == appConstants.estadosHueco.RESERVADO_STATE}">
										<c:url var="imgHueco" value="/pages/images/huecoRojo.gif" />
										<c:set var="altKey" value="archigest.archivo.deposito.reservado" />
									</c:when>
									<c:when test="${hueco.estado == appConstants.estadosHueco.INUTILIZADO_STATE}">
										<c:url var="imgHueco" value="/pages/images/huecoNegro.gif" />
										<c:set var="altKey" value="archigest.archivo.deposito.inutilizado" />
									</c:when>
									<c:otherwise>
										<c:url var="imgHueco" value="/pages/images/huecoBlanco.gif" />
										<c:set var="altKey" value="archigest.archivo.deposito.libre" />
									</c:otherwise>
								</c:choose>
								<img src="<c:out value="${imgHueco}" />" alt="<fmt:message key="${altKey}" />" title="<fmt:message key="${altKey}" />"/>
								<c:if test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}"> 
									<br>
									<c:url var="urlVerUdocs" value="/action/verHuecoAction">
										<c:param name="method" value="listadoudocs"/>
										<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
									</c:url>
									<a href="javascript:window.location='<c:out value="${urlVerUdocs}" escapeXml="false"/>'" class="hueco">
										<c:out value="${hueco.signaturaUI}"/>
									</a>
								</c:if> 
								<c:if test="${(hueco.estado == appConstants.estadosHueco.RESERVADO_STATE)}"> 
									<br>
									<c:url var="urlVerRelacion" value="/action/verHuecoAction">
										<c:param name="method" value="verrelacion"/>
										<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
									</c:url>
									<a href="javascript:window.location='<c:out value="${urlVerRelacion}" escapeXml="false"/>'" class="huecoReservado">
										<c:out value="${hueco.codigoTransferencia}"/>
									</a>
								</c:if> 
							</TD>
						</c:forEach>
				
						<c:if test="${elementoAsignable.espacioSobrante > 0}">
							<td class="sobrante">
								<bean:message key="archigest.archivo.deposito.sobrante" />:<br>
								<c:out value="${elementoAsignable.espacioSobrante}" /> <bean:message key="archigest.archivo.cm" />
							</td>
						</c:if>
				
						</tr>
					</table>
				</div>
				</div>				
			</html:form>	
	</tiles:put>
</tiles:insert>