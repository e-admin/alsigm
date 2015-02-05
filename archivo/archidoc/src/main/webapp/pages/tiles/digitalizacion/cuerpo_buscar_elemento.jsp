<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionTareasDigitalizacion" />
<c:set var="beanForm" value="${sessionScope[actionMapping.name]}"/>

<script>
function realizarBusqueda(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="buscarElementoDeTarea";
	form.submit();
}
function aceptarBusqueda(){
	var form = document.forms['<c:out value="${actionMapping.name}"/>'];
	form.method.value="anadirTareaPaso2";
	form.submit();
}

</script>

<div id="contenedor_ficha" style="-moz-box-sizing: border-box;">

<html:form action="/gestionTareasDigitalizacion">
<input type="hidden" name="method"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
						<td class="etiquetaAzul12Bold" height="25px">
							<bean:message key="archigest.archivo.documentos.digitalizacion.altaTarea"/>
						</td>
						<td align="right">
							<c:url var="cancelURI" value="/action/gestionTareasDigitalizacion">
								<c:param name="method" value="goBack"  />
							</c:url>
							<table cellpadding=0 cellspacing=0>
								<tr>
									<td><a class="etiquetaAzul12Bold" href="javascript:aceptarBusqueda();">
										<html:img page="/pages/images/Next.gif"
										altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente"
										styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.siguiente"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<td><a class="etiquetaAzul12Bold" href="<c:out value="${cancelURI}" escapeXml="false"/>">
										<html:img page="/pages/images/Ok_No.gif"
										altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar"
										styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
									</td>
									<c:if test="${appConstants.configConstants.mostrarAyuda}">
										<td width="10">&nbsp;</td>
										<td>
											<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
											<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
											<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/digitalizacion/CrearTareaDigitalizacion.htm');">
											<html:img page="/pages/images/help.gif"
												altKey="archigest.archivo.ayuda"
												titleKey="archigest.archivo.ayuda"
												styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
										</td>
									</c:if>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>


			<div class="cabecero_bloque">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TR>
					<TD class="etiquetaAzul12Bold" width="50%">
						<bean:message key="archigest.archivo.documentos.digitalizacion.seleccioneElementoSobreQRealizarTarea"/>
					</TD>
					<TD width="50%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					  <TR>

							<TD>
							<a class="etiquetaAzul12Bold" href="javascript:realizarBusqueda()">
								<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar"
								page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.buscar"/>
							</a>
							</TD>
					 </TR>
					</TABLE>
					</TD>
				</TR></TABLE>
			</div>

			<div class="bloque">
				<table class="w100">
					<tr>
						<td width="10px">&nbsp;</td>
						<td class="etiquetaAzul12Bold" width="140px"><bean:message key="archigest.archivo.buscarPor"/>:&nbsp;</td>
						<script>
							function mostrar(criterio){
								if (criterio=='CF'){
									document.getElementById('idDESCR').style.display = 'none';
									document.getElementById('idCF').style.display = 'block';
								}
								if (criterio=='DESCR'){
									document.getElementById('idCF').style.display = 'none';
									document.getElementById('idDESCR').style.display = 'block';
								}
							}
							function ocultarResultados(){
								if (document.getElementById('idRES'))
								document.getElementById('idRES').style.display = 'none';
							}
						</script>
						<TD class="etiquetaAzul11Normal">
							<c:set var="valueDescriptor" value="${appConstants.documentos.BUSQUEDA_X_DESCRIPTOR}"/>
							<c:set var="valueElementoCuadro" value="${appConstants.documentos.BUSQUEDA_X_EL_CUADRO}"/>
							<input type="radio" class="radio" name="buscarPor" id="buscarPor" value="<c:out value="${valueDescriptor}"/>"
							onClick="ocultarResultados(); mostrar('DESCR');"
							<c:if test="${beanForm.buscarPor==valueDescriptor}">
									checked
							</c:if>
							/>&nbsp;<bean:message key="archigest.archivo.descriptores"/>&nbsp;
							<input type="radio"  class="radio" name="buscarPor" id="buscarPor" value="<c:out value="${valueElementoCuadro}"/>"
								onClick="ocultarResultados(); mostrar('CF');"
							<c:if test="${beanForm.buscarPor==valueElementoCuadro}">
									checked
							</c:if>
							/>&nbsp;<bean:message key="archigest.archivo.cf"/>
						</TD>
					</tr>
				</table>
				<div class="separador5"></div>

				<div id="idCF" style="display:none">
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="140px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
							<td class="tdDatos">
							<c:set var="bListaFondosKey" value="${appConstants.documentos.LISTA_FONDOS_KEY}"/>
							<jsp:useBean id="bListaFondosKey" type="java.lang.String"/>
							<html:select property='idFondo' size="1" styleClass="input">
								<html:option value="" key="archigest.archivo.cf.fondosDocumentales"/>
								<html:optionsCollection name="<%=bListaFondosKey%>" label="label" value="id"/>
							</html:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.codigo"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text property="codigo" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.cf.titulo"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text property="titulo" styleClass="input80"/></td>
						</tr>
					</table>
				</div>
				<div id="idDESCR" style="display:block">
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="140px">
								Lista Descriptora:
							</td>
							<td class="tdDatos">

							<c:set var="bListaDescriptorasKey" value="${appConstants.documentos.LISTA_LISTAS_DESCRIPTORAS_KEY}"/>
							<jsp:useBean id="bListaDescriptorasKey" type="java.lang.String"/>
							<html:select property='idLista' size="1" styleClass="input">
								<option value="" >--</option>
								<html:optionsCollection name="<%=bListaDescriptorasKey%>" label="nombre" value="id"/>
							</html:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.cf.titulo"/>:&nbsp;
							</td>
							<td class="tdDatos"><html:text styleClass="input80" property="tituloDescriptor"/></td>
						</tr>
					</table>
				</div>
			</div>

			<div class="separador5"></div>

			<c:set var="listaRespuestaBusquedaCuadro" value="${sessionScope[appConstants.documentos.RESPUESTA_BUSQUEDA_CUADRO]}"/>
			<c:set var="listaRespuestaBusquedaEntidades" value="${sessionScope[appConstants.documentos.RESPUESTA_BUSQUEDA_ENTIDADES]}"/>

			<c:if test="${listaRespuestaBusquedaEntidades != null ||listaRespuestaBusquedaCuadro!=null}">

			<div id="idRES" style="display:block">
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>

				<c:if test="${listaRespuestaBusquedaCuadro != null}">

					<tiles:put name="blockContent" direct="true">

					<div style="padding-top:6px;padding-bottom:6px">
						<display:table name="pageScope.listaRespuestaBusquedaCuadro"
							id="elemento"
							pagesize="15"
							requestURI="/action/gestionTareasDigitalizacion?method=buscarElementoDeTarea"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.documentos.digitalizacion.noExistenResultadosParaLaBusqueda"/>
							</display:setProperty>
							<display:column style="width:15px">
								<input type="radio" name="idElemento" value="<c:out value="${elemento.id}"/>"/>
							</display:column>
							<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.codigo}" />
							</display:column>
							<display:column titleKey="archigest.archivo.titulo" sortProperty="titulo"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.titulo}" />
							</display:column>
							<display:column titleKey="archigest.archivo.estado" sortProperty="estado"  sortable="true" headerClass="sortable">
								<fmt:message key="archigest.archivo.cf.estadoElementoCF.${elemento.estado}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.fondo" sortProperty="nombreFondo"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.nombreFondo}" />
							</display:column>
						</display:table>
					</div>

					</tiles:put>

				</c:if>

				<c:if test="${listaRespuestaBusquedaEntidades != null}">
					<tiles:put name="blockContent" direct="true">

					<div style="padding-top:6px;padding-bottom:6px">
						<display:table name="pageScope.listaRespuestaBusquedaEntidades"
							id="elemento"
							pagesize="15"
							requestURI="/action/gestionTareasDigitalizacion?method=buscarElementoDeTarea"
							style="width:99%;margin-left:auto;margin-right:auto">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.documentos.digitalizacion.noExistenResultadosParaLaBusqueda"/>
							</display:setProperty>
							<display:column style="width:15px">
								<input type="radio" name="idElemento" value="<c:out value="${elemento.id}"/>"/>
							</display:column>
							<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre"  sortable="true" headerClass="sortable">
								<c:out value="${elemento.nombre}" />
							</display:column>d
						</display:table>
					</div>
					</tiles:put>
				</c:if>

			</tiles:insert>
			</div>

			</c:if>

		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%-- ficha --%>

	<script>
		<c:if test="${beanForm.buscarPor==valueDescriptor}">
			mostrar('DESCR');
		</c:if>
		<c:if test="${beanForm.buscarPor==valueElementoCuadro}">
			mostrar('CF');
		</c:if>
	</script>
</html:form>
</div>
