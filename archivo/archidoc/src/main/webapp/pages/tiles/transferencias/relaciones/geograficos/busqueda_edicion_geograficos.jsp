<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingComposicionEmplazamiento" mapping="/composicionEmplazamiento" />
<c:set var="formName" value="${mappingComposicionEmplazamiento.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<script>

	function buscarPais() {
		document.getElementById("linkPressed").value="buscar";
		if(isValidado()){
			var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
			<c:if test="${isPopup}">
				if (window.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.showWorkingDiv(title, message, message2);
				}
				form.method.value = "buscarPaisesDescripcion";
			</c:if>
			<c:if test="${!isPopup}">
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.top.showWorkingDiv(title, message, message2);
				}
				form.method.value = "buscarPaises";
			</c:if>	
			form.submit();
		}
	}
	
	function buscarProvincia() {
		document.getElementById("linkPressed").value="buscar";		
		if(isValidado()){
			var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
			<c:if test="${isPopup}">
				if (window.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.showWorkingDiv(title, message, message2);
				}			
				form.method.value = "buscarProvinciasDescripcion";
			</c:if>
			<c:if test="${!isPopup}">
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.top.showWorkingDiv(title, message, message2);
				}			
				form.method.value = "buscarProvincias";
			</c:if>			
			
			form.submit();
		}
	}			
	
	function buscarMunicipio() {
		document.getElementById("linkPressed").value="buscar";
		if(isValidado()){		
			var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
			<c:if test="${isPopup}">
				if (window.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.showWorkingDiv(title, message, message2);
				}			
				form.method.value = "buscarConcejoDescripcion";
			</c:if>
			<c:if test="${!isPopup}">
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.top.showWorkingDiv(title, message, message2);
				}
				form.method.value = "buscarConcejo";
			</c:if>			
			form.submit();
		}
	}
	
	function buscarPoblacion() {
		document.getElementById("linkPressed").value="buscar";
		if(isValidado()){
			var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
			<c:if test="${isPopup}">
				if (window.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.showWorkingDiv(title, message, message2);
				}				
				form.method.value = "buscarPoblacionDescripcion";
			</c:if>
			<c:if test="${!isPopup}">
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
					var message = '<bean:message key="archigest.archivo.buscando.msgGenericos"/>';
					var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
					window.top.showWorkingDiv(title, message, message2);
				}
				form.method.value = "buscarPoblacion";
			</c:if>			
			form.submit();
		}
	}

	function seleccionarPais(codigo, nombre) {
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		
		var codigoPais = form.codigoPais.value;
		
		form.pais.value = nombre;
		form.codigoPais.value = codigo;

		//Si cambia el pais, se borra la provincia.
		if(codigoPais != codigo){
			limpiarProvincia();
		}
		
		hideListaPaises();
	}
	
	function seleccionarProvincia(codigo, nombre) {
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		
		var codigoProvincia = form.codigoProvincia.value;
		
		form.provincia.value = nombre;
		form.codigoProvincia.value = codigo;

		//Si cambia la provincia, se borra el municipio.
		if(codigoProvincia != codigo){
			limpiarMunicipio();
		}	
			
		hideListaProvincias();
	}		
	
	function seleccionarMunicipio(codigo, nombre) {
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		
		var codigoMunicipio = form.codigoMunicipio.value;
		
		form.municipio.value = nombre;
		form.codigoMunicipio.value = codigo;

		//Si cambia el municipio, se borra la poblacion.
		if(codigoMunicipio != codigo){
			limpiarPoblacion();			
		}	

		hideListaMunicipios();
	}

	function seleccionarPoblacion(codigo,poblacion) {
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		form.poblacion.value = poblacion;
		form.codigoPoblacion.value=codigo;
		hideListaPoblaciones();
	}
	
	
	function limpiarPais(){
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		form.codigoPais.value = "";
		form.pais.value = "";
		limpiarProvincia();
	}
	
	function limpiarProvincia(){
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		form.codigoProvincia.value = "";
		form.provincia.value = "";
		limpiarMunicipio();
	}	
	
	function limpiarMunicipio(){
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		form.codigoMunicipio.value = "";
		form.municipio.value = "";
		limpiarPoblacion();
	}

	function limpiarPoblacion(){
		var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
		form.codigoPoblacion.value = "";
		form.poblacion.value = "";
	}

	function cambioValidado(){
		if(xGetElementById("emplazamientoValidado").checked){
			limpiarPais(); 			
			validado(true);
				document.getElementById("pais").value	=   document.getElementById("paisDefecto").value;						
				document.getElementById("provincia").value =   document.getElementById("provinciaDefecto").value;			
				document.getElementById("codigoPais").value	=   document.getElementById("codigoPaisDefecto").value;     
				document.getElementById("codigoProvincia").value =   document.getElementById("codigoProvinciaDefecto").value;
		}
		else{
			validado(false);
		}
	}
	
	function isValidado(){
		return xGetElementById("emplazamientoValidado").checked;
	}
	
	function validado(valor,noocultardivs){

		var cssClass = "inputRO";
		var visibilidad = "inline";
		var novisibilidad = "none";
		
		if(!valor){
			cssClass="input";
			visibilidad = "none";
			novisibilidad = "inline";			
		}
		
		xGetElementById("pais").className= cssClass;
		xGetElementById("provincia").className= cssClass;
		xGetElementById("municipio").className= cssClass;
		xGetElementById("poblacion").className= cssClass;
		
		xGetElementById("pais").readOnly		= valor;
		xGetElementById("provincia").readOnly 	= valor;
		xGetElementById("municipio").readOnly 	= valor;
		xGetElementById("poblacion").readOnly 	= valor;
		
		xDisplay("btnPais",visibilidad);
		xDisplay("btnProvincia",visibilidad);
		xDisplay("btnMunicipio",visibilidad);
		xDisplay("btnPoblacion",visibilidad);
		
		if (document.getElementById("btnDescrPais"))
			xDisplay("btnDescrPais",novisibilidad);		

		if (document.getElementById("btnDescrProvincia"))
			xDisplay("btnDescrProvincia",novisibilidad);	
			
		if (document.getElementById("btnDescrMunicipio"))
			xDisplay("btnDescrMunicipio",novisibilidad);	
			
		if (document.getElementById("btnDescrPoblacion"))
			xDisplay("btnDescrPoblacion",novisibilidad);	

		if (!noocultardivs){
			hideListaMunicipios();
			hideListaPaises();
			hideListaProvincias();
			hideListaMunicipios();
			hideListaPoblaciones();
		}
	}

</script>

	<html:form action="/composicionEmplazamiento">
		<html:hidden property="codigoPaisDefecto" styleId="codigoPaisDefecto"/>
		<html:hidden property="paisDefecto" styleId="paisDefecto"/>
		<html:hidden property="codigoProvinciaDefecto" styleId="codigoProvinciaDefecto"/>
		<html:hidden property="provinciaDefecto" styleId="provinciaDefecto"/>
		<input type="hidden" id="contextPath" name="contextPath" value="<c:out value="${form.contextPath}"/>"/>
		<html:hidden property="ref" styleId="ref"/>
			
		<html:hidden property="refPais" styleId="refPais"/>	
		<html:hidden property="refProvincia" styleId="refProvincia"/>					
		<html:hidden property="refConcejo" styleId="refConcejo"/>	
		<html:hidden property="refPoblacion" styleId="refPoblacion"/>	
		<html:hidden property="refLocalizacion" styleId="refLocalizacion"/>	
		<html:hidden property="refValidado" styleId="refValidado"/>	
		
		<html:hidden property="validadoTextSi" styleId="validadoTextSi"/>
		<html:hidden property="validadoTextNo" styleId="validadoTextNo"/>
		
		<html:hidden property="listasPais" styleId="listasPais"/>	
		<html:hidden property="listasProvincia" styleId="listasProvincia"/>					
		<html:hidden property="listasConcejo" styleId="listasConcejo"/>	
		<html:hidden property="listasPoblacion" styleId="listasPoblacion"/>	
		<html:hidden property="listasLocalizacion" styleId="listasLocalizacion"/>		

		<html:hidden property="refTypePais" styleId="refTypePais"/>	
		<html:hidden property="refTypeProvincia" styleId="refTypeProvincia"/>					
		<html:hidden property="refTypeConcejo" styleId="refTypeConcejo"/>	
		<html:hidden property="refTypePoblacion" styleId="refTypePoblacion"/>	
		<html:hidden property="refTypeLocalizacion" styleId="refTypeLocalizacion"/>			
		<html:hidden property="idRow" styleId="idRow"/>
		<html:hidden property="linkPressed" styleId="linkPressed" value=""/>
		
		<div class="etiquetaAzul12Bold">
			&nbsp;&nbsp;
				<bean:message key="archigest.archivo.incorporar" />:&nbsp;&nbsp;
				<html:radio property="validado" value="S" onclick="cambioValidado()" styleId="emplazamientoValidado"/>
				<bean:message key="archigest.archivo.transferencias.emplzamientoValidado"/>&nbsp;&nbsp;
				<html:radio property="validado" value="N" onclick="cambioValidado()" styleId="emplazamientoNoValidado"/>
				<bean:message key="archigest.archivo.transferencias.emplzamientoNoValidado"/>
		</div>
		<div class="separador8">&nbsp;</div>
		

		<input type="hidden" name="method" value="guardarEmplazamiento">
		
		<div class="bloque"> <%-- primer bloque de datos --%>

		
		
		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<tr><td width="150px">&nbsp;</td></tr>
			<TR>
				<TD class="tdTitulo" width="150px" >
					<bean:message key="archigest.archivo.transferencias.pais"/>:&nbsp;
				</TD>
					
				<TD class="tdDatos">
					<script>
						var paisOculto = true;
						function showBuscadorPais() {
							if(isValidado()) xDisplay('buscadorPais', 'block');
						}
					</script>
					<html:hidden property="codigoPais" styleId="codigoPais" />
					<html:text property="pais" styleId="pais" size="60" maxlength="254" readonly="true" styleClass="inputRO"/>&nbsp;
					<div id="btnPais" style="display:inline">
						<a href="javascript:showBuscadorPais()" class="tdlink">
							<html:img page="/pages/images/expand.gif" styleClass="imgTextMiddle" styleId="imgBusqPais"/>
						</a>&nbsp;&nbsp;
						<a class="etiquetaAzul12Bold" href="javascript:limpiarPais()" class="tdlink">
							<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar" titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle" styleId="imgLimpiarPais" />
						</a>
					</div>
					<c:if test="${isPopup}">
						<div id="btnDescrPais" style="display:inline">
							<a href="javascript:popupReferencePage('pais','<c:out value="${form.refTypePais}"/>','<c:out value="${form.listasPais}"/>');" class="tdlink">
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />						
					        </a>
						</div>
					</c:if>
					<c:set var="paises" value="${requestScope[appConstants.transferencias.PAISES]}" />
					<c:set var="buscadorPaisesVisible" value="${paises != null}" />

					<div id="buscadorPais" class="bloque_busquedas<c:if test="${!buscadorPaisesVisible}">_oculto</c:if>" >
						<div class="cabecero_bloque_tab">
							<TABLE width="100%" cellpadding=0 cellspacing=0>
							  <TR>
								<TD width="100%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
								  <TR>
										<TD>
										<a class="etiquetaAzul12Normal" href="javascript:buscarPais()">
											<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.buscar"/>&nbsp;&nbsp;
										</a>
										</TD>
								 </TR>
								</TABLE>
								</TD>
							</TR></TABLE>
						</div>
						<table class="formulario" width="99%">
							<tr>
								<td class="tdTitulo" width="100px"><bean:message key="archigest.archivo.nombre"/></td>
								<td class="tdDatos"><input type="text" size="24" name="patternNombrePais" value="<c:out value="${param.patternNombrePais}" />"></td>
							</tr>
						</table>

						<c:if test="${param.method=='buscarPaises' || param.method=='buscarPaisesDescripcion'}">
						<div id="seleccionPais">
							<div id="listaPaises" class="etiquetaNegra11Normal" 
								style="height:180px;overflow:auto;background-color:#efefef">
								<c:choose>
								<c:when test="${!empty paises}">
									<TABLE width="95%" cellpadding="0" cellspacing="2" style="margin:5px;">
									<tr>
									<c:forEach var="pais" items="${paises}" varStatus="index">																			
										<c:if test="${soportaBusquedaExtendida}">
											<td class="etiquetaGris12Normal" onclick="seleccionarPais('<c:out value="${pais.idPais}" />', '<c:out value="${pais.namePais}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${pais.namePais}" /></td>
											</tr><tr>
										</c:if>
										<c:if test="${!soportaBusquedaExtendida}">
											<td class="etiquetaGris12Normal" onclick="seleccionarPais('<c:out value="${pais.codigo}" />', '<c:out value="${pais.nombre}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${pais.nombre}" /></td>
											<c:if test="${index.count%3 == 0}">
												</tr><tr>
											</c:if>									
										</c:if>	
									</c:forEach>
									</tr></table>
								</c:when>
								<c:otherwise>
									<div style="height: 20px">
									<bean:message key="archigest.archivo.transferencias.busqPais.vacio"/>
									</div>
								</c:otherwise>
								</c:choose>
							</div>
						</div>
						</c:if>

						<script>
							function hideListaPaises() {
								xGetElementById('buscadorPais').style.display='none';
							}
						</script>
						<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
						<a class=etiquetaAzul12Bold href="javascript:hideListaPaises()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
						</a>
						</td></tr></table>
					</div>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.provincia"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<script>
						var provinciaOculto = true;
						function showBuscadorProvincia() {
							if(isValidado()){
								var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];																				
								if (form.codigoPais.value == '' && <c:out value="${!soportaBusquedaExtendida}"/>)
									alert("<bean:message key='errors.relaciones.elegirProvinciaPrimeroPais'/>");
								else{
									 xDisplay('buscadorProvincia', 'block');
								}
							}
						}
					</script>
					<html:hidden property="codigoProvincia" styleId="codigoProvincia"/>
					<html:text property="provincia" styleId="provincia" size="60" maxlength="254" readonly="true" styleClass="inputRO"/>&nbsp;
					<div id="btnProvincia" style="display:inline">
						<a href="javascript:showBuscadorProvincia()" class="tdlink">
							<html:img page="/pages/images/expand.gif" styleClass="imgTextMiddle" styleId="imgBusqProvincia" />
						</a>&nbsp;&nbsp;
						<a class="etiquetaAzul12Bold" href="javascript:limpiarProvincia()" class="tdlink">
							<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar" titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle" styleId="imgLimpiarProvincia"/>
						</a>
					</div>
					<c:if test="${isPopup}">
						<div id="btnDescrProvincia" style="display:inline">
							<a href="javascript:popupReferencePage('provincia','<c:out value="${form.refTypeProvincia}"/>','<c:out value="${form.listasProvincia}"/>');" class="tdlink">
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />						
					        </a>
						</div>					
					</c:if>
					<c:set var="provincias" value="${requestScope[appConstants.transferencias.PROVINCIAS]}" />
					<c:set var="buscadorProvinciasVisible" value="${provincias != null}" />

					<div id="buscadorProvincia" class="bloque_busquedas<c:if test="${!buscadorProvinciasVisible}">_oculto</c:if>" >
						<div class="cabecero_bloque_tab">
							<TABLE width="100%" cellpadding=0 cellspacing=0>
							  <TR>
								<TD width="100%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
								  <TR>
										<TD>
										<a class="etiquetaAzul12Normal" href="javascript:buscarProvincia()">
											<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.buscar"/>&nbsp;&nbsp;
										</a>
										</TD>
								 </TR>
								</TABLE>
								</TD>
							</TR></TABLE>
						</div>
						<table class="formulario" width="99%">
							<tr>
								<td class="tdTitulo" width="100px">Nombre</td>
								<td class="tdDatos"><input type="text" size="24" name="patternNombreProvincia" value="<c:out value="${param.patternNombreProvincia}" />"></td>
							</tr>
						</table>

						<c:if test="${param.method=='buscarProvincias' || param.method=='buscarProvinciasDescripcion'}">
						<div id="seleccionProvincia">
							<div id="listaProvincias" class="etiquetaNegra11Normal" 
								style="height:180px;overflow:auto;background-color:#efefef">
								<c:choose>
								<c:when test="${!empty provincias}">
									<TABLE width="95%" cellpadding="0" cellspacing="2" style="margin:5px;">
									<tr>
									<c:forEach var="provincia" items="${provincias}" varStatus="index">
										<c:if test="${soportaBusquedaExtendida}">
											<td class="etiquetaGris12Normal" onclick="seleccionarPais('<c:out value="${provincia.idPais}" />', '<c:out value="${provincia.namePais}" />');seleccionarProvincia('<c:out value="${provincia.idProvincia}" />', '<c:out value="${provincia.nameProvincia}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${provincia.nameProvincia}" />  
											[ <c:out value="${provincia.namePais}" /> ]</td>
											</tr><tr>	
										</c:if>
										<c:if test="${!soportaBusquedaExtendida}">											
											<td class="etiquetaGris12Normal" onclick="seleccionarProvincia('<c:out value="${provincia.codigo}" />', '<c:out value="${provincia.nombre}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${provincia.nombre}" /></td>
											<c:if test="${index.count%3 == 0}">
												</tr><tr>
											</c:if>
										</c:if>
									</c:forEach>
									</tr></table>
								</c:when>
								<c:otherwise>
									<div style="height: 20px">
										<bean:message key="archigest.archivo.transferencias.busqComunidad.vacia"/>
									</div>
								</c:otherwise>
								</c:choose>
							</div>
						</div>
						</c:if>

						<script>
							function hideListaProvincias() {
								xGetElementById('buscadorProvincia').style.display='none';
							}
						</script>
						<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
						<a class=etiquetaAzul12Bold href="javascript:hideListaProvincias()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
						</a>
						</td></tr></table>
					</div>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.municipio"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<script>
						var municipioOculto = true;
						function showBuscadorMunicipio() {
							if(isValidado()){
								var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
								if (form.codigoProvincia.value == '' && <c:out value="${!soportaBusquedaExtendida}"/>)
									alert("<bean:message key='errors.relaciones.elegirMunicipioPrimeroProvincia'/>");
								else{
									 xDisplay('buscadorMunicipio', 'block');
								}
							}							
						}
					</script>
					<html:hidden property="codigoMunicipio"  />
					<html:text property="municipio" styleId="municipio" size="60" maxlength="254" readonly="true" styleClass="inputRO"/>&nbsp;
					<div id="btnMunicipio" style="display:inline">
						<a href="javascript:showBuscadorMunicipio()" class="tdlink">
							<html:img page="/pages/images/expand.gif" styleClass="imgTextMiddle" styleId="imgBusqMunicipio" />
						</a>&nbsp;&nbsp;
						<a class="etiquetaAzul12Bold" href="javascript:limpiarMunicipio()" class="tdlink">
							<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar" titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle" styleId="imgLimpiarMunicipio"/>
						</a>
					</div>
					<c:if test="${isPopup}">
						<div id="btnDescrMunicipio" style="display:inline">
							<a href="javascript:popupReferencePage('municipio','<c:out value="${form.refTypeConcejo}"/>','<c:out value="${form.listasConcejo}"/>');" class="tdlink">
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />						
					        </a>
						</div>					
					</c:if>
					<c:set var="municipios" value="${requestScope[appConstants.transferencias.CONCEJOS]}" />
					<c:set var="buscadorMunicipiosVisible" value="${municipios != null}" />

					<div id="buscadorMunicipio" class="bloque_busquedas<c:if test="${!buscadorMunicipiosVisible}">_oculto</c:if>" >
						<div class="cabecero_bloque_tab">
							<TABLE width="100%" cellpadding=0 cellspacing=0>
							  <TR>
								<TD width="100%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
								  <TR>
										<TD>
										<a class="etiquetaAzul12Normal" href="javascript:buscarMunicipio()">
											<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.buscar"/>&nbsp;&nbsp;
										</a>
										</TD>
								 </TR>
								</TABLE>
								</TD>
							</TR></TABLE>
						</div>
						<table class="formulario" width="99%">
							<tr>
								<td class="tdTitulo" width="100px"><bean:message key="archigest.archivo.nombre"/></td>
								<td class="tdDatos"><input type="text" size="24" name="patternNombreMunicipio" value="<c:out value="${param.patternNombreMunicipio}" />"></td>
							</tr>
						</table>
						<c:if test="${param.method=='buscarConcejo' || param.method=='buscarConcejoDescripcion'}">
						<div id="seleccionMunicipio">
							<div id="listaMunicipios" class="etiquetaNegra11Normal" 
								style="height:180px;overflow:auto;background-color:#efefef">
								<c:choose>
								<c:when test="${!empty municipios}">
									<TABLE width="95%" cellpadding="0" cellspacing="2" style="margin:5px;">
									<tr>
									<c:forEach var="municipio" items="${municipios}" varStatus="index">
										<c:if test="${soportaBusquedaExtendida}">
											<td class="etiquetaGris12Normal" onclick="seleccionarPais('<c:out value="${municipio.idPais}" />', '<c:out value="${municipio.namePais}" />');seleccionarProvincia('<c:out value="${municipio.idProvincia}" />', '<c:out value="${municipio.nameProvincia}" />');seleccionarMunicipio('<c:out value="${municipio.idMunicipio}" />', '<c:out value="${municipio.nameMunicipio}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${municipio.nameMunicipio}" />
											[ <c:out value="${municipio.namePais}" />  &gt;  <c:out value="${municipio.nameProvincia}" /> ]</td>
											</tr><tr>
										</c:if>
										<c:if test="${!soportaBusquedaExtendida}">
											<td class="etiquetaGris12Normal" onclick="seleccionarMunicipio('<c:out value="${municipio.codigo}" />', '<c:out value="${municipio.nombre}" />')" style="cursor:default" onmouseover="this.style.backgroundColor = '#B4B4B4'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${municipio.nombre}" /></td>
											<c:if test="${index.count%3 == 0}">
												</tr><tr>
											</c:if>
										</c:if>
									</c:forEach>
									</tr></table>
								</c:when>
								<c:otherwise>
									<div style="height: 20px">
									<bean:message key="archigest.archivo.transferencias.busqMunicipio.vacio"/>
									</div>
								</c:otherwise>
								</c:choose>
							</div>
						</div>
						</c:if>

						<script>
							function hideListaMunicipios() {
								xGetElementById('buscadorMunicipio').style.display='none';
							}
						</script>
						<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
						<a class=etiquetaAzul12Bold href="javascript:hideListaMunicipios()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
						</a>
						</td></tr></table>
					</div>
				</TD>
			</TR>	
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.poblacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<script>
						function showBuscadorPoblacion() {
							
							if(isValidado()){
								var form = document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'];
								if (form.codigoMunicipio.value == '' && <c:out value="${!soportaBusquedaExtendida}"/>)
									alert("<bean:message key='errors.relaciones.elegirPoblacionPrimeroMunicipio'/>");
								else{
									 xDisplay('buscadorPoblacion', 'block');
								}
							}
						}
					</script>
					<html:hidden property="codigoPoblacion" />
					<html:text property="poblacion" styleId="poblacion" size="60" maxlength="254" readonly="true" styleClass="inputRO"/>&nbsp;
					<div id="btnPoblacion" style="display:inline">
						<a href="javascript:showBuscadorPoblacion()" class="tdlink">
							<html:img page="/pages/images/expand.gif" styleClass="imgTextMiddle" styleId="imgBusqPoblacion" />
						</a>&nbsp;&nbsp;
						<a class=etiquetaAzul12Bold href="javascript:limpiarPoblacion()" class="tdlink">
							<html:img page="/pages/images/clear0.gif" altKey="archigest.archivo.limpiar" titleKey="archigest.archivo.limpiar" styleClass="imgTextMiddle" styleId="imgLimpiarPoblacion" />
						</a>
					</div>
					<c:if test="${isPopup}">
						<div id="btnDescrPoblacion" style="display:inline">
							<a href="javascript:popupReferencePage('poblacion','<c:out value="${form.refTypePoblacion}"/>','<c:out value="${form.listasPoblacion}"/>');" class="tdlink">
								<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />						
					        </a>
						</div>					
					</c:if>
					<c:set var="poblaciones" value="${requestScope[appConstants.transferencias.POBLACIONES]}" />
					<c:set var="buscadorPoblacionVisible" value="${poblaciones != null}" />

					<div id="buscadorPoblacion" class="bloque_busquedas<c:if test="${!buscadorPoblacionVisible}">_oculto</c:if>">
						<div class="cabecero_bloque_tab">
							<TABLE width="100%" cellpadding=0 cellspacing=0>
							  <TR>
								<TD width="100%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
								  <TR>
										<TD>
										<a class="etiquetaAzul12Normal" href="javascript:buscarPoblacion()">
											<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
											<bean:message key="archigest.archivo.buscar"/>&nbsp;
										</a>
										</TD>
								 </TR>
								</TABLE>
								</TD>
							</TR></TABLE>
						</div>
						<table class="formulario" width="99%">
							<tr>
								<td class="tdTitulo" width="100px"><bean:message key="archigest.archivo.nombre"/></td>
								<td class="tdDatos"><input type="text" size="24" name="patternNombrePoblacion" value="<c:out value="${param.patternNombrePoblacion}" />"></td>
							</tr>
						</table>

						<c:if test="${param.method=='buscarPoblacion' || param.method=='buscarPoblacionDescripcion'}">
						<div id="seleccionPoblacion">
							<div id="listaPoblaciones" class="etiquetaNegra11Normal" 
								style="height:180px;overflow:auto;background-color:#efefef; border-left:1px solid #BCBCBC;">
								<c:choose>
								<c:when test="${!empty poblaciones}">
									<TABLE width="95%" style="margin-left:6px; margin-top:6px; margin-bottom:2px;">
									<tr>
									<c:forEach var="poblacion" items="${poblaciones}" varStatus="index">
										<c:if test="${soportaBusquedaExtendida}">
											<td class="etiquetaAzul12Normal" onclick='seleccionarPais("<c:out value="${poblacion.idPais}" />","<c:out value="${poblacion.namePais}" />");seleccionarProvincia("<c:out value="${poblacion.idProvincia}" />","<c:out value="${poblacion.nameProvincia}" />");seleccionarMunicipio("<c:out value="${poblacion.idMunicipio}" />","<c:out value="${poblacion.nameMunicipio}" />");seleccionarPoblacion("<c:out value="${poblacion.idPoblacion}" />","<c:out value="${poblacion.namePoblacion}" />")' style="cursor:default" onmouseover="this.style.backgroundColor = '#a0a0a0'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${poblacion.namePoblacion}" />  
											[ <c:out value="${poblacion.namePais}" />  >  <c:out value="${poblacion.nameProvincia}" />  >  <c:out value="${poblacion.nameMunicipio}" /> ]</td>
											</tr><tr>
										</c:if>
										<c:if test="${!soportaBusquedaExtendida}">
											<td class="etiquetaAzul12Normal" onclick='seleccionarPoblacion("<c:out value="${poblacion.codigo}" />","<c:out value="${poblacion.nombre}" />")' style="cursor:default" onmouseover="this.style.backgroundColor = '#a0a0a0'" onmouseout="this.style.backgroundColor='#f2f2f2'"><c:out value="${poblacion.nombre}" /></td>
											<c:if test="${index.count%3 == 0}">
												</tr><tr>
											</c:if>
										</c:if>
									</c:forEach>
									</tr></table>	
								</c:when>
								<c:otherwise>
									<div style="height: 20px">
									<bean:message key="archigest.archivo.transferencias.busqPoblacion.vacio"/>
									</div>
								</c:otherwise>
								</c:choose>
							</div>
						</div>
						</c:if>	

						<script>
							function hideListaPoblaciones() {
								xGetElementById('buscadorPoblacion').style.display='none';
							}
						</script>
						<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
						<a class=etiquetaAzul12Bold href="javascript:hideListaPoblaciones()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;&nbsp;
						</a>

						</td></tr></table>
					</div>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<div class="separador8">&nbsp;</div>
					<bean:message key="archigest.archivo.transferencias.localizacion"/>:&nbsp;
					<div class="separador8">&nbsp;</div>
				</TD>
				<TD class="tdDatos">
					<div class="separador8">&nbsp;</div>
					<html:textarea property="direccion" rows="2" styleId="direccion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"/>
					<div class="separador8">&nbsp;</div>
				</TD>
			</TR>
		</TABLE>

	</div> <%-- bloque --%>
	
	<c:set var="parroquias" value="${requestScope[appConstants.transferencias.PARROQUIAS]}" />
	<c:if test="${!empty parroquias}">
		<div id="bloque">
	
			<div id="titulo">
				<bean:message key="archigest.archivo.transferencias.msgSelParroquia" />:
			</div>

			<TABLE width="95%" style="margin-left:6px; margin-top:6px; margin-bottom:2px;">
			<tr>
			<c:forEach var="parroquia" items="${parroquias}" varStatus="index">
				<td onclick="seleccionarParroquia('<c:out value="${parroquia.valor}" />')"><c:out value="${parroquia.valor}" /></td>
				<c:if test="${index.count%3 == 0}">
					</tr><tr>
				</c:if>
			</c:forEach>
			</tr></table>	
		</div>
	</c:if>
	
	<script>
		validado(true,true);
	</script>

	</html:form>