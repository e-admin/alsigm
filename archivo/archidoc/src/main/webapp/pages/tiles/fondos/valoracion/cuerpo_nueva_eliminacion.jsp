<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionEliminacion" mapping="/gestionEliminacion" />

<c:set var="serieValorada" value="${sessionScope[appConstants.valoracion.SERIE_KEY]}" />
<c:set var="listaSeries" value="${requestScope[appConstants.valoracion.LISTA_SERIES_KEY]}" />
<c:set var="listaArchivos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.altaSeleccion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">	
		<table>
			<tr>
				<c:if test="${!empty listaSeries}">
						<TD>
							<script>
							function seleccionarSerie() {
								var form = document.forms['<c:out value="${mappingGestionEliminacion.name}" />'];
								var campoSeleccionSerie = form.elements['idSerie'];
								var encontrado = false;						
								if (campoSeleccionSerie != null)
									if (campoSeleccionSerie.length) {
										for (var i=0; i<campoSeleccionSerie.length; i++)  
											if (campoSeleccionSerie[i].checked)  
												encontrado = true;
									} else
										if (campoSeleccionSerie.checked)
											encontrado = true;
										
										
								if (encontrado) {
									form.method.value = "seleccionarSerie";
									form.submit();
								} else 
									alert("<bean:message key="archigest.archivo.eliminacion.altaSeleccion.msgSelSeleccion"/>");
							}
							</script>
							<a class="etiquetaAzul12Normal" href="javascript:seleccionarSerie()">
								<html:img titleKey="wizard.siguiente" altKey="wizard.siguiente" page="/pages/images/Next.gif" styleClass="imgTextMiddle"/>
								<bean:message key="wizard.siguiente"/>
							</a>		
						</TD>
				</c:if>
				<c:if test="${param.method == 'seleccionarSerie'}">
			       	<TD width="10">&nbsp;</TD>
					<td nowrap>
						<c:url var="atrasURL" value="/action/gestionEliminacion">
							<c:param name="method" value="goBack" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${atrasURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/Previous.gif" altKey="wizard.atras" titleKey="wizard.atras" styleClass="imgTextMiddle" />
								<bean:message key="wizard.atras"/>&nbsp;
						</a>
					</td>
				</c:if>
				<c:if test="${!empty serieValorada}">
			       	<TD width="10">&nbsp;</TD>
					<td nowrap>
						<script>
							function crearEliminacion() {
								var form = document.forms['<c:out value="${mappingGestionEliminacion.name}" />'];
								form.method.value = 'crearEliminacion';
								form.submit();
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:crearEliminacion()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.aceptar"/>&nbsp;
						</a>
					</td>
				</c:if>
		       	<TD width="10">&nbsp;</TD>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
						<tiles:put name="action" direct="true">goBack</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionEliminacion">
	<input type="hidden" name="method" id="method" value="buscarSerie">

		<%-- Cuerpo del buscador de series --%>

		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			  <TR>
				<TD class="etiquetaAzul12Bold" width="50%">
					<bean:message key="archigest.archivo.datosDeLaSeleccion"/>					
				</TD>
				<TD width="50%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
						<TD>
						<a class="etiquetaAzul12Normal" href="javascript:document.getElementById('method').value='buscarSerie'; document.forms['eliminacionForm'].submit()">
							<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.buscar"/>
						</a>		
						</TD>
				 </TR>
				</TABLE>
				</TD>
			</TR>
			</TABLE>
		</div>
		<c:url var="gestionAsignacionSerie" value="/action/gestionEliminacion" />
		<div class="bloque">

			<div class="separador5"></div>
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.archivo"/>:&nbsp;</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${!empty listaArchivos[1]}">
								<html:select property="idArchivo">
									<html:options collection="listaArchivos" property="id" labelProperty="nombre" />
								</html:select>
							</c:when>
							<c:otherwise>
									<input type="hidden" name="idArchivo" value="<c:out value='${listaArchivos[0].id}'/>"/>
									<c:out value="${listaArchivos[0].nombre}" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
			
			<div class="separador5"></div>
			<table class="formulario">
				<tr>
					
					<td class="tdTituloFichaSinBorde"><bean:message key="archigest.archivo.eliminacion.msgSelSerieSeleccion"/>:&nbsp;</td>
				</tr>
			</table>
			<div class="separador5"></div>

			<table class="formulario" width="99%">
				<tr>
					<td width="20px">&nbsp;</td>
					<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
					<td class="tdDatos">
						<c:set var="listaFondos" value="${sessionScope[appConstants.valoracion.LISTA_FONDOS_KEY]}" />
						<select name="fondo">
							<option value=""> -- <bean:message key="archigest.archivo.cf.fondosDocumentales"/>-- </option>
							<c:forEach var="fondo" items="${listaFondos}">
							<option value="<c:out value="${fondo.id}" />" <c:if test="${fondo.id == param.fondo}">selected</c:if>> <c:out value="${fondo.titulo}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
					</td>
					<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
					</td>
					<td class="tdDatos"><input type="text" class="input80" name="tituloBuscar" value="<c:out value="${param.tituloBuscar}" />"></td>
				</tr>
			</table>
		</div>
		
		<%-- Display con las series a seleccionar --%>

		<c:if test="${listaSeries != null}">

		<div class="separador8"></div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<div id="resultados" style="padding-top:6px;padding-bottom:6px">

			<display:table name="pageScope.listaSeries"
				id="serie" 
				export="false"
				style="width:98%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.series.msgNoSeriesBusqueda"/>
				</display:setProperty>

				<display:column>
					<input type="radio" name="idSerie" 
						value="<c:out value="${serie.id}" />|<c:out value="${serie.valoracion.id}" />"
						<c:if test="${serie_rowNum==1}">checked="true"</c:if>
						/>
				</display:column>
			
				<display:column titleKey="archigest.archivo.codigo" property="codigo" />
				<display:column titleKey="archigest.archivo.series.titulo" property="titulo" />
				<display:column titleKey="archigest.archivo.estado">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</display:column>
				<display:column titleKey="archigest.archivo.fondo">
					<c:out value="${serie.fondo.titulo}" />
				</display:column>
				<display:column titleKey="archigest.archivo.tituloValoracion">
					<c:out value="${serie.valoracion.titulo}" />
				</display:column>
			</display:table>
			</div>
			</tiles:put>
		</tiles:insert>
		</c:if>
<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>