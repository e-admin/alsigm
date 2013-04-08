<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="formato" value="${sessionScope[appConstants.deposito.FORMATO_KEY]}"/>
<c:set var="EDITANDO" value="${sessionScope[appConstants.deposito.EDITANDO_KEY]}" />

<bean:struts id="actionMappingGestionTIpoAsignable" mapping="/gestionTipoAsignableAction" />

<html:form action="/gestionTipoAsignableAction">
				<input type="hidden" name="method" value="editarNumeracion" />
				<html:hidden property="id" />
				<html:hidden property="idPadre" />
				<html:hidden property="tipoElemento" />
				<html:hidden property="nombreTipoElemento" />
				<html:hidden property="numACrear" />
				<html:hidden property="formatoRegular" />
				<html:hidden property="longitud" />
				<html:hidden property="numeroHuecos" />
				<html:hidden property="idFormato" />
				<html:hidden property="numeracionAnteriorAEdionRegular" />
				<c:if test="${EDITANDO}"><html:hidden property="selectedHueco" /></c:if>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.crear"/>
		<bean:message key="archigest.archivo.deposito.descendientes"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<c:if test="${!EDITANDO}">
				<td>
					<c:choose>
						<c:when test="${AsignableForm.formatoRegular}">
							<script>
								function aceptar(){
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}

									document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].method.value="aceptarNumeracionRegular";
									document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].submit();
								}
							</script>
						</c:when>
						<c:otherwise>
							<script>
								function aceptar(){
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}
									document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].method.value="aceptarNumeracionIrregular";
									document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].submit();
								}
							</script>
						</c:otherwise>
					</c:choose>
					<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="cancelarURL" value="/action/gestionTipoAsignableAction">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cancelarURL}" escapeXml="false"/>">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cancelar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			</c:if>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.creacionElemAsignable"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:set var="elementoPadre" value="${sessionScope[appConstants.deposito.ELEMENTO_DEPOSITO_KEY]}" />
				<table class="formulario">
					<tr>
						<td width="220px" class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.elementoPadre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoPadre.pathName}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.tipoElemCrear"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${AsignableForm.nombreTipoElemento}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.deposito.datosElemAsignable" />
				</tiles:put>
				<c:if test="${AsignableForm.formatoRegular}">
					<tiles:put name="buttonBar" direct="true">
						<TABLE cellpadding=0 cellspacing=0>
						<TR>
							<c:choose>
								<c:when test="${!EDITANDO}">
									<td>
										<script>
											function editar(){
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].method.value="editarNumeracion";
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].submit();
											}
										</script>
										<a class="etiquetaAzul12Bold" href="javascript:editar()">
											<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.deposito.editarNumeracion" titleKey="archigest.archivo.deposito.editarNumeracion" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.deposito.editarNumeracion"/>
										</a>
									</td>
									<TD width="10">&nbsp;</TD>
								</c:when>
								<c:otherwise>
									<TD>
										<script>
											function aceptar(){
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].method.value="confirmarCambioNumeracion";
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].submit();
											}
										</script>
										<a class="etiquetaAzul12Bold" href="javascript:aceptar()">
											<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
											&nbsp;<bean:message key="archigest.archivo.aceptar"/>
										</a>
									</TD>
									<TD width="10">&nbsp;</TD>
									<TD>
										<script>
											function cancelar(){
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].method.value="cancelarCambioNumeracion";
												document.forms['<c:out value="${actionMappingGestionTIpoAsignable.name}" />'].submit();
											}
										</script>
										<a class="etiquetaAzul12Bold" href="javascript:cancelar()">
											<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
											&nbsp;<bean:message key="archigest.archivo.cancelar"/>
										</a>
									</TD>
									<TD width="10">&nbsp;</TD>
								</c:otherwise>
							</c:choose>
						</TR>
						</TABLE>
					</tiles:put>
				</c:if>
				<tiles:put name="blockContent" direct="true">
					<table class="formulario">
						<tr>
							<td width="220px" class="tdTitulo">
								<bean:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${AsignableForm.longitud}" /> <bean:message key="archigest.archivo.cm"/>.
							</td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${formato.nombre}" />
							</td>
						</tr>
					</table>

					<c:set var="listaPathsHuecos" value="${sessionScope[appConstants.deposito.LISTA_PATHS_HUECOS_KEY]}"/>
					<display:table name="pageScope.listaPathsHuecos"
						style="width:99%;margin-left:auto;margin-right:auto"
						requestURI='<%=request.getContextPath()+"/action/gestionTipoAsignableAction?method=guardarAsignables"%>'
						id="pathHueco"
						export="false"
						excludedParams="method">

						<display:column style="width:10px">
							<c:out value="${pathHueco_rowNum}"/>
						</display:column>
						<c:if test="${AsignableForm.formatoRegular}">
							<c:if test="${!EDITANDO}">
								<display:column style="width:10px">
									<html-el:radio property="selectedHueco" value="${pathHueco_rowNum}"/>
								</display:column>
							</c:if>
						</c:if>
						<display:column titleKey="archigest.archivo.deposito.ubicacion" sortable="true" headerClass="sortable" style="width:500px">
							<c:out value="${pathHueco}"/>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.numeracion" style="width:150px">
							<c:choose>
								<c:when test="${AsignableForm.formatoRegular}">
									<c:choose>
										<c:when test="${!EDITANDO}">
											<html-el:text property="mapFormValues(${pathHueco_rowNum})" styleClass="inputRO" disabled="true"/>
											<html-el:hidden property="mapFormValues(${pathHueco_rowNum})"/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${pathHueco_rowNum==AsignableForm.selectedHueco}">
													<html-el:text property="mapFormValues(${pathHueco_rowNum})" value="" maxlength="16"/>
												</c:when>
												<c:otherwise>
													<html-el:text property="mapFormValues(${pathHueco_rowNum})" styleClass="inputRO" disabled="true"/>
													<html-el:hidden property="mapFormValues(${pathHueco_rowNum})"/>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<html-el:text property="mapFormValues(${pathHueco_rowNum})" maxlength="16"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					</display:table>
				</tiles:put>

		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>