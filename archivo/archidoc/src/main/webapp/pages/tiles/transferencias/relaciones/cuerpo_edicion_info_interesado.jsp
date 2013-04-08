<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="interesado" value="${requestScope[appConstants.transferencias.INFO_INTERESADO]}" />



<bean:struts id="mappingGestionInteresados" mapping="/gestionInteresados" />
<c:set var="interesadoFormName" value="${mappingGestionInteresados.name}" />
<c:set var="interesadoForm" value="${requestScope[interesadoFormName]}" />

<script language="javascript">
function cambioTipoIdentificacion(valor){
	var valor = document.getElementById("tipoIdentificacion").value;
	if(valor == 2){
		document.getElementById("numeroIdentificacion").maxLength=32;
		
	}
	else{
		document.getElementById("numeroIdentificacion").maxLength=9;
	}
}
</script>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.editInteresado"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">	
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		    <TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionInteresados.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionInteresados">
					<c:param name="method" value="volverAvistaUnidadDocumental"  />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
		<DIV id="barra_errores">
				<archivo:errors />
		</DIV>
		
		<tiles:insert definition="transferecias.unidadDocumental.contextoUdoc" />					
		
		<html:form action="/gestionInteresados">
		<input type="hidden" name="method" value="guardarInteresado">
		<input type="hidden" name="itemIndex" value="<c:out value="${param.itemIndex}" />">
		
		<div class="bloque"> <%-- primer bloque de datos --%>
			<c:set var="datosUsuarioNoEditables" value="${false}" />
			<c:set var="campoNoEditable" value="${datosUsuarioNoEditables || interesado.validado}" />
	
			<TABLE class="formulario" cellpadding=0 cellspacing=0>
				<TR>
					<TD class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.transferencias.apellidosNombre"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:choose>
							<c:when test="${campoNoEditable}">
								<html:text property="nombre" styleClass="inputRO99" readonly="true" />
							</c:when>
							<c:otherwise>
								<html:text property="nombre" styleClass="input99" />
							</c:otherwise>
						</c:choose>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.numIdentidad"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:choose>
							<c:when test="${campoNoEditable}">
								<html:select property="tipoIdentificacion" styleId="tipoIdentificacion" styleClass="inputRO" disabled="true">
									<html:option value="0"><bean:message key="archigest.archivo.transferencias.nif"/></html:option>
									<html:option value="1"><bean:message key="archigest.archivo.transferencias.cif"/></html:option>
									<html:option value="2"><bean:message key="archigest.archivo.transferencias.otro"/></html:option>
								</html:select>
								<html:text property="numeroIdentificacion" styleId="numeroIdentificacion" size="16" styleClass="inputRO" readonly="true" maxlength="9"/>
							</c:when>
							<c:otherwise>
								<html:select property="tipoIdentificacion" styleId="tipoIdentificacion" onchange="cambioTipoIdentificacion()">
									<html:option value="0"><bean:message key="archigest.archivo.transferencias.nif"/></html:option>
									<html:option value="1"><bean:message key="archigest.archivo.transferencias.cif"/></html:option>
									<html:option value="2"><bean:message key="archigest.archivo.transferencias.otro"/></html:option>
								</html:select>
								<html:text property="numeroIdentificacion" styleId="numeroIdentificacion" size="16" maxlength="9"/>
							</c:otherwise>
						</c:choose>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.actividad"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="listaRoles" value="${sessionScope[appConstants.transferencias.LISTA_ROLES_INTERESADO]}" />
						<html:select property="rol" disabled="${datosUsuarioNoEditables}">
							<c:forEach var="rol" items="${listaRoles}" varStatus="nRol">
								<option value="<c:out value="${rol}" />"
								<c:if test="${interesadoForm.rol == rol}">
									selected
								</c:if>
								><c:out value="${rol}" />
							</c:forEach>
						</html:select>
					</TD>
				</TR>
			</TABLE>
		</div> <%-- bloque --%>
		</html:form>
	<script language="javascript">
		cambioTipoIdentificacion();
	</script>
	
	</tiles:put>
</tiles:insert>		