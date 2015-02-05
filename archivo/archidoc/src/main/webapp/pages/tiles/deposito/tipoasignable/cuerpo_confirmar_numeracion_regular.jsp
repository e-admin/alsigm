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

<bean:struts id="actionMappingGestionTipoAsignable" mapping="/gestionTipoAsignableAction" />

<html:form action="/gestionTipoAsignableAction">
				<input type="hidden" name="method" value="editarNumeracion" />
				<html:hidden property="id" />
				<html:hidden property="idPadre" />
				<html:hidden property="tipoElemento" />
				<html:hidden property="nombreTipoElemento" />
				<html:hidden property="numACrear" />
				<html:hidden property="selectedHueco"/>
				<html:hidden property="formatoRegular" />
				<html:hidden property="longitud" />
				<html:hidden property="numeroHuecos" />
				<html:hidden property="idFormato" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.crear"/>
		<bean:message key="archigest.archivo.deposito.descendientes"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td>
				<script>
					function aceptar(){
						document.forms['<c:out value="${actionMappingGestionTipoAsignable.name}" />'].method.value="modificarNumeracionRegular";
						document.forms['<c:out value="${actionMappingGestionTipoAsignable.name}" />'].submit();
					}
				</script>
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
	
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.deposito.datosElemAsignable" />
				</tiles:put>
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
					<table class="formulario"><tr>
						<td class="tdDatos" ><bean:message key="archigest.archivo.label.deposito.tipoNumeracion"/>:</td>
						<td class="tdDatos" style="width:200px">
							<html-el:radio property="tipoCambioNumeracion" value="C" style="border:0"/>
							<bean:message key="archigest.archivo.label.deposito.tipoNumeracion.conservar"/>
						</td>
						<td class="tdDatos" style="width:200px">
							<html-el:radio property="tipoCambioNumeracion" value="R" style="border:0"/>
							<bean:message key="archigest.archivo.label.deposito.tipoNumeracion.recalcular"/>
						</td>
					</tr></table>


					<c:set var="listaPathsHuecos" value="${sessionScope[appConstants.deposito.LISTA_PATHS_HUECOS_KEY]}"/>
					<display:table name="pageScope.listaPathsHuecos"
						style="width:99%;margin-left:auto;margin-right:auto"
						requestURI='<%=request.getContextPath()+"/action/gestionTipoAsignableAction?method=guardarAsignables"%>'
						id="pathHueco" 
						export="false">
						
						<display:column style="width:10px">
							<c:out value="${pathHueco_rowNum}"/>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.ubicacion" sortable="true" headerClass="sortable" style="width:400px">
							<c:out value="${pathHueco}"/>
						</display:column>
						<display:column titleKey="archigest.archivo.label.deposito.numeracion.actual" style="width:150px">
							<html-el:text property="mapFormValues(${pathHueco_rowNum})" styleClass="inputRO" disabled="true"/>
							<html-el:hidden property="mapFormValues(${pathHueco_rowNum})"/>
						</display:column>
						<display:column titleKey="archigest.archivo.label.deposito.numeracion.recalculada" style="width:150px">
							<html-el:text property="mapFormValues(R${pathHueco_rowNum})" styleClass="inputRO" disabled="true"/>
							<html-el:hidden property="mapFormValues(R${pathHueco_rowNum})" />
						</display:column>
					</display:table> 
				</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>