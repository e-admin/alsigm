<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
		
<c:choose>
	<c:when test="${isPopup}">		
		<c:set var="listadoTerceros" value="${sessionScope[appConstants.transferencias.RESULTADOS_BUSQUEDA_INTERESADOS]}" />
	</c:when>
	<c:otherwise>
		<c:set var="listadoTerceros" value="${sessionScope[appConstants.transferencias.RESULTADOS_BUSQUEDA_INTERESADOS]}" />
	</c:otherwise>
</c:choose>	

<bean:struts id="mappingGestionInteresados" mapping="/gestionInteresados" />
<c:set var="listadoTercerosSize" value="${requestScope[appConstants.transferencias.LISTADO_TERCEROS_SIZE]}"/>
<c:set var="interesadoFormName" value="${mappingGestionInteresados.name}" />
<c:set var="interesadoForm" value="${requestScope[interesadoFormName]}" />

<c:choose>
	<c:when test="${interesadoForm.tipoInteresado=='0'}">
		<c:set var="noValidado" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="noValidado" value="false"/>
	</c:otherwise>
</c:choose>

<script>

function cambioTipoIdentificacion(valor){
	var valor = document.getElementById("tipoIdentificacion").value;
	if(valor == 2){
		document.getElementById("numeroIdentificacion").maxLength=32;
		
	}
	else{
		document.getElementById("numeroIdentificacion").maxLength=9;
	}
}

window.onunload=function cerrarVentana(){
		
		var linkPressed=document.getElementById("linkPressed");
		if(linkPressed.value!="buscarInteresados" &&
		   linkPressed.value!="incorporarInteresadoNoValidado" &&
		   linkPressed.value!="incorporarInteresados"){
			idRow=document.getElementById("idRow");
			if(window.opener && window.opener.removeTableRow){
				window.opener.removeTableRow(idRow.value);
			}
		}
	}
</script>
		
<html:form action="/gestionInteresados">
		
	<input type="hidden" name="method" styleId="method"/>
	<html:hidden property="validacionCorrecta" styleId="validacionCorrecta"/>
	<input type="hidden" id="contextPath" name="contextPath" value="<c:out value="${interesadoForm.contextPath}"/>"/>
	<html:hidden property="ref"/>
	<html:hidden property="descripcionIdentidad" styleId="descripcionIdentidad"/>
	<html:hidden property="refDescripcionIdentidad" styleId="refDescripcionIdentidad"/>
	<html:hidden property="descripcionNumIdentidad" styleId="descripcionNumIdentidad"/>
	<html:hidden property="refDescripcionNumIdentidad" styleId="refDescripcionNumIdentidad"/>
	<html:hidden property="descripcionRol" styleId="descripcionRol"/>
	<html:hidden property="refDescripcionRol" styleId="refDescripcionRol"/>
	<html:hidden property="descripcionValidado" styleId="descripcionValidado"/>
	<html:hidden property="refDescripcionValidado" styleId="refDescripcionValidado"/>	
	<html:hidden property="descripcionIdTercero" styleId="descripcionIdTercero"/>
	<html:hidden property="refDescripcionIdTercero" styleId="refDescripcionIdTercero"/>	
	<html:hidden property="listasDescripcionIdentidad" styleId="listasDescripcionIdentidad"/>
	<html:hidden property="refTypeDescripcionIdentidad" styleId="refTypeDescripcionIdentidad"/>
	<html:hidden property="idRow" styleId="idRow"/>
	<html:hidden property="linkPressed" styleId="linkPressed"/>
	<div class="etiquetaAzul12Bold">
	
		<script>
		
			function showIntValForm()
			{
				//document.getElementById('nombre').value = '';
				//document.getElementById('numeroIdentificacion').value = '';
				document.getElementById('InteresadoNoValidado').style.display = 'none'; 
				document.getElementById('InteresadoValidado').style.display = 'block';
				<c:choose>
				<c:when test="${!empty listadoTerceros}">
					document.getElementById('tdIncorporarInteresado').style.display = 'block';
				</c:when>
				<c:otherwise>
					document.getElementById('tdIncorporarInteresado').style.display = 'none';
				</c:otherwise>
				</c:choose>

				var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
			}
			
			function showIntNotValForm(copiarDatos)
			{
				var separadorApellidos = '<bean:message key="archigest.archivo.transferencias.caracterseparador.interesados"/>'
				
				if(copiarDatos){
					document.getElementById('numeroIdentificacion').value = document.getElementById('niSearchToken').value;
					var nombreCompleto = "";
					var nomb =  document.getElementById('nameSearchToken').value;
					var apel1 = document.getElementById('surname1SearchToken').value;
					var apel2 = document.getElementById('surname2SearchToken').value;
					
					if(apel1 != ""){
						nombreCompleto += apel1;
					}
					
					
					
					if(apel2 != ""){
						if(nombreCompleto != ""){
							nombreCompleto += " ";
						}
						nombreCompleto += apel2;
					}
					
					if(nomb != ""){
						nombreCompleto += separadorApellidos;
						if(nombreCompleto != ""){
							nombreCompleto += " ";
						}					
						nombreCompleto += nomb;
					}
					
					document.getElementById('nombre').value = nombreCompleto;
					document.getElementById('tipoIdentificacion').value = "0";
				}
				
				
				document.getElementById('InteresadoNoValidado').style.display = 'block';
				document.getElementById('InteresadoValidado').style.display = 'none';
				document.getElementById('tdIncorporarInteresado').style.display = 'none';

				var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
			}
		</script>
		<div class="separador8">&nbsp;</div>
		&nbsp;&nbsp; 
		<html:radio property="tipoInteresado" styleId="tipoInteresado" value="1" onclick="javascript:showIntValForm()"></html:radio>
		<bean:message key="archigest.archivo.transferencias.interesadoValidado" />
		<html:radio property="tipoInteresado" styleId="tipoInteresado" value="0" onclick="javascript:showIntNotValForm(true)"></html:radio>
		<bean:message key="archigest.archivo.transferencias.interesadoNoValidado" />
		

	</div>
	<div class="separador8">&nbsp;</div>

	<div id="InteresadoValidado" 
		<c:choose>
			<c:when test="${noValidado}">							
				style="display:none;"
			</c:when>
			<c:otherwise>
				style="display:block;"
			</c:otherwise>
		</c:choose>			
	>

	<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding="0" cellspacing="0">
			  <TR>
				<script>
					function buscarInteresados() {
						var searchForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
						searchForm.method.value = '<c:out value="${methodBusqueda}"/>';
						<c:if test="${isPopup}">
							if (window.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
								var message = '<bean:message key="archigest.archivo.buscando.msgTerceros"/>';
								var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
								window.showWorkingDiv(title, message, message2);
							}
						</c:if>
						<c:if test="${!isPopup}">
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
								var message = '<bean:message key="archigest.archivo.buscando.msgTerceros"/>';
								var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
								window.top.showWorkingDiv(title, message, message2);
							}
						</c:if>
						document.getElementById("linkPressed").value="buscarInteresados";
						searchForm.submit();
					}
				</script>

				<TD>
				<a class="etiquetaAzul12Normal" href="javascript:buscarInteresados()">
					<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/>
				</a>		
				</TD>
		     </TR>
			</TABLE>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div class="titulo_left_bloque">
				<bean:message key="archigest.archivo.transferencias.buscarPor" />:&nbsp;&nbsp;
			</div>
			<table class="formulario" cellpadding=0 cellspacing=0>
				<tr>
					<td class="tdTitulo" colspan="2">
						<html:radio property="buscarPor" value="NOMBRE" styleClass="radio"/> 
						<bean:message key="archigest.archivo.transferencias.nombreApellidos"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="padding-left:30px">
					<table class="formulario" cellpadding=0 cellspacing=0>
						<tr>
						<td class="tdTitulo" width="220px">
							<bean:message key="archigest.archivo.nombre" />: 
						</td>
						<td class="tdDatos">
							<html:text property="nameSearchToken" size="20" styleId="nameSearchToken" onclick="this.form.buscarPor[0].checked = true"  />
						</td>
						</tr>
						<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.PrimerApellido" />: 
						</td>
						<td class="tdDatos">
							<html:text property="surname1SearchToken"  styleId="surname1SearchToken" size="40" onclick="this.form.buscarPor[0].checked = true" />
						</td>
						</tr>
						<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.SegundoApellido" />: 
						</td>
						<td class="tdDatos">
							<html:text property="surname2SearchToken" styleId="surname2SearchToken" size="40" onclick="this.form.buscarPor[0].checked = true" />
						</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<table class="formulario" cellpadding=0 cellspacing=0>
				<tr>
					<td class="tdTitulo" width="250px">
						<html:radio property="buscarPor" value="RAZON_SOCIAL" styleClass="radio"/> 
						<bean:message key="archigest.archivo.razonSocial" />: 
					</td>
					<td class="tdDatos" align="left"><html:text property="companySearchToken" styleClass="input99" onclick="this.form.buscarPor[1].checked = true"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="250px">
						<html:radio property="buscarPor" value="IF" styleClass="radio"/> 
						<bean:message key="archigest.archivo.transferencias.numIdentidad"/>:
					</td>
					<td class="tdDatos" align="left">
						<html:text property="niSearchToken" styleId="niSearchToken" size="15" onclick="this.form.buscarPor[2].checked = true"  />
					</td>
				</tr>
			</table>
			<c:if test="${listadoTerceros != null}">		
				<div class="titulo_left_bloque">
					<bean:message key="archigest.archivo.resultadosBusqueda" />:&nbsp;&nbsp;
				</div>
	
				<c:choose>
				<c:when test="${!empty listadoTerceros}">
				<c:url var="listaTercerosPaginationURL" value="/action/gestionInteresados">
					<c:param name="method" value="verPaginaListadoTerceros" />
					<c:param name="buscarPor" value="${param.buscarPor}" />
					<c:param name="nameSearchToken" value="${param.nameSearchToken}" />
					<c:param name="surname1SearchToken" value="${param.surname1SearchToken}" />
					<c:param name="surname2SearchToken" value="${param.surname2SearchToken}" />
					<c:param name="companySearchToken" value="${param.companySearchToken}" />
					<c:param name="niSearchToken" value="${param.niSearchToken}" />
				</c:url>
				<jsp:useBean id="listaTercerosPaginationURL" type="java.lang.String" />
				<div id="idListadoTercerosScroll" class="bloqueConScroll175">
				
				<div style="height:5px">&nbsp;</div>
				<display:table 
					name="pageScope.listadoTerceros" id="interesado" 
					style="width:99%;margin-left:auto;margin-right:auto;margin-bottom:auto;margin-top:auto"
					pagesize="0"
					requestURI='<%=listaTercerosPaginationURL%>'>
					
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.noInteresadoBD"/>
					</display:setProperty>
					
					<display:column title="&nbsp;" style="width:20px">
						<input type="checkbox" name="seleccionTerceros" 
							value="<%=interesado_rowNum.intValue()-1%>" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.identificacion" property="identificacion" />
					<display:column titleKey="archigest.archivo.nombre" property="nombre" />
					<display:column titleKey="archigest.archivo.PrimerApellido" property="primerApellido" />
					<display:column titleKey="archigest.archivo.SegundoApellido" property="segundoApellido" />
				</display:table>
				<div style="height:5px">&nbsp</div>
				</div>
				</c:when>
				<c:otherwise>
				<div class="bloque" style="width:99%;color:#1083CF;background-color:#F0F0F2;padding:3px;text-align:left;margin-bottom:6px">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<bean:message key="archigest.archivo.transferencias.msgNoTerceroBusqueda"/>
				</div>
				</c:otherwise>
				</c:choose>	
				<c:if test="${listadoTercerosSize<10}">
					<script>
						document.getElementById("idListadoTercerosScroll").className="";
					</script>
				</c:if>	
			</c:if>
		</tiles:put>
	</tiles:insert>
	</div>

	<div id="InteresadoNoValidado" 
		<c:choose>
			<c:when test="${!noValidado}">							
				style="display:none;"
			</c:when>
			<c:otherwise>
				style="display:block;"
			</c:otherwise>
		</c:choose>	
	>
	<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<a class="etiquetaAzul12Normal" href="javascript:incorporarInteresadoNoValidado();">
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			 </TR>
			</TABLE>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<TR>
				<TD class="tdTitulo" width="170px">
					<bean:message key="archigest.archivo.transferencias.apellidosNombre"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${isPopup}">
							<html:text property="nombre" styleId="nombre" styleClass="input90" maxlength="254"/>
							<html:hidden property="nombre_idref" styleId="nombre_idref"/>
							<html:hidden property="nombre_tiporef" styleId="nombre_tiporef"/>
							<a href="javascript:popupReferencePage('nombre','<c:out value="${interesadoForm.refTypeDescripcionIdentidad}"/>','<c:out value="${interesadoForm.listasDescripcionIdentidad}"/>');">
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextTop" />													
					        </a>
						</c:when>
						<c:otherwise>
							<html:text property="nombre" styleId="nombre" styleClass="input99" maxlength="254"/>
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.numIdentidad"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:select property="tipoIdentificacion" styleId="tipoIdentificacion" onchange="cambioTipoIdentificacion()">
						<html:option value="0"><bean:message key="archigest.archivo.transferencias.nif"/></html:option>
						<html:option value="1"><bean:message key="archigest.archivo.transferencias.cif"/></html:option>
						<html:option value="2"><bean:message key="archigest.archivo.transferencias.otro"/></html:option>
					</html:select>
					<html:text property="numeroIdentificacion" styleId="numeroIdentificacion" size="16" maxlength="9" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.actividad"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="listaRoles" value="${sessionScope[appConstants.transferencias.LISTA_ROLES_INTERESADO]}" />
					<html:select property="rol" styleId="rol">
						<option value="" />
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
		</tiles:put>
	</tiles:insert>
	<script language="javascript">
		cambioTipoIdentificacion();
		
		var validacionCorrecta = document.getElementById('validacionCorrecta');
		if ((validacionCorrecta.value != null) && (validacionCorrecta.value == "true")) {
		
			var identidad = document.getElementById('descripcionIdentidad');
			var refIdentidad = document.getElementById('refDescripcionIdentidad');			
			if ((identidad!=null) && (identidad.value!="") && (identidad.value!="undefined")&&
				(refIdentidad!=null) && (refIdentidad.value!="") && (refIdentidad.value!="undefined")){

				if (opener.document.getElementById(refIdentidad.value))
				{
					window.opener.document.getElementById(refIdentidad.value).value = identidad.value;
				}
			}
			
			var numidentidad = document.getElementById('descripcionNumIdentidad');
			var refNumIdentidad = document.getElementById('refDescripcionNumIdentidad');			
			if ((numidentidad!=null) && (numidentidad.value!="") && (numidentidad.value!="undefined")&&
				(refNumIdentidad!=null) && (refNumIdentidad.value!="") && (refNumIdentidad.value!="undefined")){						

				if (opener.document.getElementById(refNumIdentidad.value))
				{
					window.opener.document.getElementById(refNumIdentidad.value).value = numidentidad.value;
				}
			}
			
			var idTercero = document.getElementById('descripcionIdTercero');
			var refIdTercero = document.getElementById('refDescripcionIdTercero');	
			if ((idTercero!=null) && (idTercero.value!="") && (idTercero.value!="undefined")&&
				(refIdTercero!=null) && (refIdTercero.value!="") && (refIdTercero.value!="undefined")){						
				if (opener.document.getElementById(refIdTercero.value))
				{
					window.opener.document.getElementById(refIdTercero.value).value = idTercero.value;
				}
			}
			
			var validado = document.getElementById('descripcionValidado');
			var refValidado = document.getElementById('refDescripcionValidado');			
			if ((validado!=null) && (validado.value!="") && (validado.value!="undefined")&&
				(refValidado!=null) && (refValidado.value!="") && (refValidado.value!="undefined")){						

				if (opener.document.getElementById(refValidado.value))
				{
					window.opener.document.getElementById(refValidado.value).selectedIndex = validado.value;
				}
			}
			
			var rol = document.getElementById('descripcionRol');
			var refRol = document.getElementById('refDescripcionRol');			
			if ((rol!=null) && (rol.value!="") && (rol.value!="undefined")&&
				(refRol!=null) && (refRol.value!="") && (refRol.value!="undefined")){						

				if (opener.document.getElementById(refRol.value))
				{
					window.opener.document.getElementById(refRol.value).selectedIndex = rol.value;
				}
			}
			
			window.close();
		}
	</script>	
	</div>
</html:form>

