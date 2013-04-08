<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>

<c:set var="vFondo" value="${sessionScope[appConstants.fondos.FONDO_KEY]}"/>

<c:set var="vPadreElementoCF" value="${sessionScope[appConstants.fondos.PADRE_ELEMENTO_CF]}"/>

<c:set var="vIdTipoInstitucion" value="${appConstants.fondos.tiposEntidad.INSTITUCION.identificador}"/>
<jsp:useBean id="vIdTipoInstitucion" type="java.lang.Integer" />
<c:set var="vIdTipoFamilia" value="${appConstants.fondos.tiposEntidad.FAMILIA.identificador}"/>
<jsp:useBean id="vIdTipoFamilia" type="java.lang.Integer" />
<c:set var="vIdTipoPersona" value="${appConstants.fondos.tiposEntidad.PERSONA.identificador}"/>
<jsp:useBean id="vIdTipoPersona" type="java.lang.Integer" />

<c:set var="vListaPaises" value="${sessionScope[appConstants.fondos.LISTA_PAISES_KEY]}"/>

<bean:struts id="mappingGestionFondos" mapping="/gestionFondoAction" />
<c:set var="fondoFormBean" value="${requestScope[mappingGestionFondos.name]}" />

<script>
	function guardar() {
		var form = document.forms['<c:out value="${mappingGestionFondos.name}" />']
		form.submit();
	}
</script>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.crear"/>
		<bean:message key="archigest.archivo.cf.fondo"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD>
					<script>
						function selecPais() {
							var form = document.forms['<c:out value="${mappingGestionFondos.name}" />']
							form.method.value = "seleccionarPais";
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD>

					<c:url var="cancelURI" value="/action/gestionFondoAction">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/gestionFondoAction">
		<input type="hidden" name="method" value="guardarFondo">
		<html:hidden property="idFondo" />
		<html:hidden property="idnivelpadre" />
		<html:hidden property="idclpadre" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockContent" direct="true">
			<c:set var="vTiposDeFondos" value="${sessionScope[appConstants.fondos.CHILD_TYPES_ELEMENTO_CF_KEY]}"/>
			<c:choose>
				<c:when test="${!empty vTiposDeFondos[1]}">
					<TABLE class="formulario" cellpadding="0" cellspacing="2">
						<TR>
							<TD width="150px" class="tdTitulo">
								<bean:message key="archigest.archivo.cf.tipoFondoCrear" />:&nbsp;
							</TD>
						</TR>
						<TR>
							<TD class="etiquetaAzul12Bold">
								<html:img page="/pages/images/pixel.gif" styleClass="imgTextBottom" width="50px" height="1"/>
								<c:forEach var="tipofondo" items="${vTiposDeFondos}" varStatus="ifondo">
									<INPUT type="radio" class="radio" name="idnivelacrear" value="<c:out value="${tipofondo.id}"/>"
									<c:if test="${param.idnivelacrear == tipofondo.id}"> checked="checked" </c:if>	>
									<c:out value="${tipofondo.nombre}"/>
									<html:img page="/pages/images/pixel.gif" styleClass="imgTextBottom" width="5px" height="1"/>
								</c:forEach>
							</TD>
						</TR>
					</TABLE>

				</c:when>
				<c:otherwise>
					<c:set var="tipoDeFondoACrear" value="${vTiposDeFondos[0]}" />
					<INPUT type="hidden" name="idnivelacrear" value="<c:out value="${tipoDeFondoACrear.id}"/>">
				</c:otherwise>
			</c:choose>
		
			<DIV class="separador5">&nbsp;</DIV>

			<c:if test="${vPadreElementoCF.codReferencia != null}">
			<TABLE class="formulario" cellpadding="0" cellspacing="2">
				<tr>
					<td width="200px" class="tdTitulo" >
						<bean:message key="archigest.archivo.cf.codReferenciaPadre"/>:
					</td>
					<td class="tdDatosBold" >
						<c:out value="${vPadreElementoCF.codReferencia}"/>
					</td>
				</tr>
			</TABLE>
			</c:if>
			<TABLE class="formulario" cellpadding="0" cellspacing="2">
				<c:choose>
				<c:when test="${vFondo == null || vFondo.permitidoModificarCodigo}">
				<TR>
					<TD width="200px" class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.pais"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
					<c:choose>
					<c:when test="${!empty vListaPaises[1]}">
					<select id="codigopais" name="codigopais" size="1" onchange="javascript:selecPais()">
						<c:forEach var="pais" items="${vListaPaises}" varStatus="ipais">
							<option value='<c:out value="${pais.codigo}"/>' <c:if test="${pais.codigo == param.codigopais}"> SELECTED</c:if> ><c:out value="${pais.nombre}" /></option>
						</c:forEach>
					</select>
					</c:when>
					<c:otherwise>
						<c:set var="paisFondo" value="${vListaPaises[0]}" />
						<input type="hidden" name="codigopais" value="<c:out value="${paisFondo.codigo}" />" />
						<c:out value="${paisFondo.nombre}" />
					</c:otherwise>
					</c:choose>
					</TD>
				</TR>
				<c:set var="vListaAutonomias" value="${sessionScope[appConstants.fondos.LISTA_COMUNIDADES_KEY]}"/>
				<c:if test="${!empty vListaAutonomias}">
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.comunidadAutonoma"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
					<c:choose>
					<c:when test="${!empty vListaAutonomias[1]}">
						<SELECT id="autonomias" name="codigoautonomia" SIZE="1" >
							<c:forEach var="autonomia" items="${vListaAutonomias}" >
								<OPTION value='<c:out value="${autonomia.codigocomunidad}"/>'
								<c:if test="${autonomia.codigocomunidad == param.codigoautonomia}">SELECTED</c:if>
								><c:out value="${autonomia.nombre}"/>
							</c:forEach>
						</SELECT>
					</c:when>
					<c:otherwise>
						<c:set var="autonomiaFondo" value="${vListaAutonomias[0]}" />
						<input type="hidden" name="codigoautonomia" value="<c:out value="${autonomiaFondo.codigocomunidad}" />" />
						<c:out value="${autonomiaFondo.nombre}" />
					</c:otherwise>
					</c:choose>
					</TD>
				</TR>
				</c:if>
				<tr>
					<td width="200px" class="tdTitulo">
						<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<html:text property="codigo" size="10" maxlength="128"/>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<TR>
						<TD width="200px" class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.pais"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vFondo.pais.nombre}" />
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo" >
							<bean:message key="archigest.archivo.comunidadAutonoma"/>:&nbsp;
						</TD>
						<td class="tdDatos">
							<c:out value="${vFondo.comunidad.nombre}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
						<input type="hidden" name="codigo" value="<c:out value="${vFondo.codigo}" />">

							<c:out value="${vFondo.codigo}" />
						</td>
					</tr>
				</c:otherwise>
				</c:choose>
				<tr>
					<td width="200px" class="tdTitulo">
						<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<html:text property="denominacion" styleClass="input99" maxlength="1024"/>
					</td>
				</tr>

				<TR>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.archivo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${not empty sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][1] && (vFondo == null || vFondo.permitidoModificarArchivo)}">
								<SELECT id="idcodigoarchivo" name="codigoarchivo" SIZE="1" >
									<c:forEach var="archivo" items="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY]}" >
									<OPTION value='<c:out value="${archivo.codigo}"/>'
									<c:if test="${archivo.codigo == FondoForm.codigoarchivo}">SELECTED</c:if>
									><c:out value="${archivo.nombre}"/>
								</c:forEach>
								</SELECT>
							</c:when>
							<c:otherwise>
								<c:out value="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][0].nombre}" />
								<input type="hidden" name="codigoarchivo" 
								value='<c:out value="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][0].codigo}"/>'>
							</c:otherwise>
						</c:choose>
					</td>
				</TR>
				<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<html:text property="codOrdenacion" maxlength="32"/>
						</td>
					</tr>
				</c:if>
			</TABLE>
		</tiles:put>
		</tiles:insert>


		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.selEntidadProductora"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<c:choose>
		<c:when test="${vFondo == null || vFondo.permitidoModificarEntidadProductora}">
		<TABLE class="formulario" cellspacing="2">
			<TR>
				<TD class="tdTitulo" width="200px">Tipo </TD>
				<TD class="tdDatos">
					<script>
					function seleccionarTipoEntidad(tipoEntidad) {
						var form = document.forms['<c:out value="${mappingGestionFondos.name}" />'];
						var tipoInstitucion = '<c:out value="${vIdTipoInstitucion}" />';
						if (tipoEntidad == tipoInstitucion){
							xDisplay('instdebdorganizacion', 'inline');
							if(!document.all){
								xDisplay('instdebdorganizacion', 'table-row');
							}
						}else {
							form.institucionEnSistemaExterno.checked = false;
							xDisplay('instdebdorganizacion', 'none');
						}
						form.nombreEntidadProductora.value = '';
						form.idInstitucionEnSistemaExterno.value = '';
						form.idDescriptorEntidadProductora.value = '';
						xDisplay('seleccionEntidadProductora', 'none');
						xDisplay('listaEntidades', 'none');
					}
					</script>
					<html:select  property="tipoEntidad"
							onchange="javascript:seleccionarTipoEntidad(this.options[this.selectedIndex].value)">
						<option value="<%=vIdTipoInstitucion%>"
						<c:if test="${vIdTipoInstitucion == FondoForm.tipoEntidad}">SELECTED</c:if>
						><fmt:message key="archigest.archivo.cf.institucion"/></option> 
						<option value="<%=vIdTipoFamilia%>"
						<c:if test="${vIdTipoFamilia == FondoForm.tipoEntidad}">SELECTED</c:if>
						><fmt:message key="archigest.archivo.cf.familia"/></option> 
						<option value="<%=vIdTipoPersona%>"
						<c:if test="${vIdTipoPersona == FondoForm.tipoEntidad}">SELECTED</c:if>
						><fmt:message key="archigest.archivo.cf.persona"/></option> 
					</html:select>
				</TD>
			</TR>
			<TR id="instdebdorganizacion" <c:if test="${fondoFormBean.tipoEntidad != vIdTipoInstitucion}">style="display:none"</c:if>>
				<TD class="tdTitulo"></TD>
				<TD class="tdTitulo">
					<script>
						function buscarInstitucionEnSistemaExterno(value) {
							hideSeleccionEntidad();
							var form = document.forms['<c:out value="${mappingGestionFondos.name}" />'];
							form.nombreEntidadProductora.value = '';
							form.idInstitucionEnSistemaExterno.value = '';
							form.idDescriptorEntidadProductora.value = '';
							xDisplay('seleccionEntidadProductora', 'none');
							xDisplay('listaEntidades', 'none');
						}
					</script>
					<html:checkbox styleClass="checkbox" property="institucionEnSistemaExterno" 
					onclick="buscarInstitucionEnSistemaExterno(this.checked)" />
					&nbsp;<bean:message key="archigest.archivo.cf.institucion.sistExterno"/>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo"><bean:message key="archigest.archivo.cf.entidadProductora"/></TD>
				<TD class="tdDatos">
					<html:hidden property="idDescriptorEntidadProductora" />
					<html:hidden property="idInstitucionEnSistemaExterno" />

					<html:text property="nombreEntidadProductora" styleClass="inputRO95" readonly="true" /> 
					<script>
						function mostrarListaEntidades() {
							var form = document.forms['<c:out value="${mappingGestionFondos.name}" />'];
							if (form.institucionEnSistemaExterno.checked)
							{ 
								document.getElementById("buscadorEntidadProductora").className="hidden";
								buscarEntidadProductora();
							}
							else {
								document.getElementById("buscadorEntidadProductora").className="visible";
								xDisplay('seleccionEntidadProductora', 'block');
								xDisplay('buscadorEntidadProductora', 'block');
								xDisplay('listaEntidades', 'none');
							}
						}
					</script>
					<a href="javascript:mostrarListaEntidades()">
						<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.cf.instituciones" altKey="archigest.archivo.cf.instituciones" styleClass="imgTextMiddle" />
					</a>

					<c:set var="listaPosiblesEntidades" value="${requestScope[appConstants.fondos.LISTA_ENTIDADES]}" />
					<div id="seleccionEntidadProductora" class="bloque_busquedas<c:if test="${listaPosiblesEntidades == null}">_oculto</c:if>"> 
						<div id="buscadorEntidadProductora" <c:if test="${param.institucionEnSistemaExterno}">style="display:none"</c:if>>
							<div class="cabecero_bloque_tab">
								<TABLE width="100%" cellpadding=0 cellspacing=0>
								  <TR>
									<TD width="100%" align="right">
									<TABLE cellpadding=0 cellspacing=0>
									  <TR>
											<TD>
											<script>
											function buscarEntidadProductora() {
												var form = document.forms['<c:out value="${mappingGestionFondos.name}" />']
												form.method.value = "buscarEntidadProductora";
												form.submit();
											}
											</script>
											<a class="etiquetaAzul12Normal" href="javascript:buscarEntidadProductora()">
												<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
												&nbsp;<bean:message key="archigest.archivo.buscar"/>
											</a>		
											</TD><td width="10px">&nbsp;</td>
									 </TR>
									</TABLE>
									</TD>
								</TR></TABLE>
							</div>
							<table class="formulario" width="99%">
								<tr>
									<td class="tdTitulo" width="120px">
										<bean:message key="archigest.archivo.cf.nombre"/>:&nbsp;
									</td>
									<td class="tdDatos"><html:text styleClass="input80" property="tokenBusquedaEntidad" /></td>
								</tr>
							</table>
						</div>
						<div id="listaEntidades" style="width:100%;height:100px;overflow:auto;background-color:#efefef" >
						<c:choose>
						<c:when test="${!empty listaPosiblesEntidades}">
							<script>
								function seleccionarEntidad(item) {
									var form = document.forms['<c:out value="${mappingGestionFondos.name}" />'];
									form.nombreEntidadProductora.value = item.getAttribute('nombreEntidad');
									if (form.institucionEnSistemaExterno.checked)
										form.idInstitucionEnSistemaExterno.value = item.id;
									else
										form.idDescriptorEntidadProductora.value = item.id;
									hideSeleccionEntidad();
								}
							</script>
							<c:forEach var="entidad" items="${listaPosiblesEntidades}">
								<div class="etiquetaGris12Normal" id='<c:out value="${entidad.key}" />' 
										nombreEntidad="<c:out value="${entidad.value}" />"
										onmouseOver="this.style.backgroundColor='#dedede'"
										onmouseOut="this.style.backgroundColor='#efefef'"
										onclick="seleccionarEntidad(this)" style='padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
									<c:out value="${entidad.value}" /></div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div style="color:red;padding:3px;padding-top:20px;text-align:left">
							<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
							<c:choose>
								<c:when test="${param.institucionEnSistemaExterno}">
									<bean:message key="archigest.archivo.cf.msg.noInstitucion.sistExterno"/>
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.cf.msg.noInstitucion.descriptores"/>
								</c:otherwise>
							</c:choose>
							</div>
						</c:otherwise>
						</c:choose>
						</div>
						<script>
							function hideSeleccionEntidad() {
								xDisplay('listaEntidades', 'none');
								xDisplay('seleccionEntidadProductora', 'none');
							}
						</script>
						<table cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #000000">
						<a class=etiquetaAzul12Bold href="javascript:hideSeleccionEntidad()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>
						</a>&nbsp;
						</td></tr></table>
					</div>
					<div class="separador5">&nbsp;</div>
				</TD>
			</TR>
		</TABLE>
			<script>
				var form = document.forms['<c:out value="${mappingGestionFondos.name}" />'];
				if (form.institucionEnSistemaExterno.checked)
					{ 
						document.getElementById("buscadorEntidadProductora").className="hidden";
					}
				else 
					{
						document.getElementById("buscadorEntidadProductora").className="visible";
					}
			</script>		
		</c:when>
		<c:otherwise>
		<c:set var="vEntidad" value="${vFondo.entidadProductora}" />
		<input type="hidden" name="idDescriptorEntidadProductora" value="<c:out value="${vEntidad.id}" />">
		<input type="hidden" name="tipoEntidad" value="<c:out value="${vEntidad.tipoentidad}" />">
		<table class="formulario" cellpadding=0 cellspacing=0>
			<tr>
				<td class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.cf.tipo" />:&nbsp;&nbsp;
				</td>
				<td class="tdDatos">
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.FAMILIA.identificador}">
						<bean:message key="archigest.archivo.cf.familia"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.INSTITUCION.identificador}">
						<bean:message key="archigest.archivo.cf.institucion"/>
					</c:if>
						<c:if test="${vEntidad.tipoentidad == appConstants.fondos.tiposEntidad.PERSONA.identificador}">
						<bean:message key="archigest.archivo.cf.persona"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.nombre" />:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${vEntidad.nombre}" />
				</td>
			</tr>
		</table>

		
		
		</c:otherwise>
		</c:choose>
		</tiles:put>
		</tiles:insert>
		</html:form>

	</tiles:put>
</tiles:insert>	

