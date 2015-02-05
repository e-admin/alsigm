<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

	<%-- Javascript para el tratamiento del campo editable formato de elemento asignable --%>
	function modificarCampo(nombre){
		var divLabelCampo="divLabel"+nombre;
		var selectCampo="select"+nombre;
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre;
		
		divAceptarCancelarCampo="divAceptarCancelar"+nombre;
		divLabelCampo="divLabel"+nombre;
		
		document.getElementById(divAceptarCancelarCampo).className="visible";
		document.getElementById(divLabelCampo).className="hidden";		
		document.getElementById("valor"+nombre).value=selectCampo.value;
	}
	
	function cancelarModificacionCampo(nombre){
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre;
		var divLabelCampo="divLabel"+nombre;
		document.getElementById(divAceptarCancelarCampo).className="hidden";
		document.getElementById(divLabelCampo).className="visible";
	}
	
	function aceptarModificacionCampo(nombre){
		var selectCampo="select"+nombre;
		
		if(document.getElementById(selectCampo).value==document.getElementById("valor"+nombre).value){
			cancelarModificacionCampo(nombre);
		}
		else{
			<c:url var="URL" value="/action/gestionTipoAsignableAction">
			  	<c:param name="method" value="actualizarCampoFormato"/>
			</c:url>
			window.location = '<c:out value="${URL}"/>'+
				'&valorCampoFormato='+document.getElementById(selectCampo).value;
		}
	}

</script>

<input type="hidden" id="valorCampoFormato" name="valorCampoFormato" />
<input type="hidden" name="method" value="actualizarCampoFormato" />

<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />

<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.datosElemAsignable"/>
	</tiles:put>
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
					<c:out value="${elementoAsignable.longitud}" />
					<bean:message key="archigest.archivo.cm"/>.
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</td>
				<td class="tdDatos">	
					<div id="divLabelCampoFormato" name="divLabelCampoFormato">
						<c:out value="${elementoAsignable.formato.nombre}" /> 
						<c:if test="${elementoAsignable.formato.regular}">
							<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
								<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('CampoFormato')">
									<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
								</a>
							</security:permissions>
						</c:if>
					</div>
					<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
						<c:if test="${elementoAsignable.formato.regular}">
							<div id="divAceptarCancelarCampoFormato" class="hidden" name="divAceptarCancelarCampoFormato">
								<c:set var="fmtRegIgualLong" value="${sessionScope[appConstants.deposito.LISTA_FMT_REG_IGUAL_LONG]}" />
								<select name="selectCampoFormato" id="selectCampoFormato" class="input">
					           		<c:forEach items="${fmtRegIgualLong}" var="formato">
				    	       			<option value="<c:out value="${formato.id}"/>" <c:if test="${elementoAsignable.formato.id==formato.id}"> selected</c:if> >
				        	   				<c:out value="${formato.nombre}"/>
				          	  			</option>
				            		</c:forEach>
								</select>
								<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampo('CampoFormato')">
									<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								</a>	
								<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('CampoFormato')">
									<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
								</a>
							</div>
						</c:if>
					</security:permissions>
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