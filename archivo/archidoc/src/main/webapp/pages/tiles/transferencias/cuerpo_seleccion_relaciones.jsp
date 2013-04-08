<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="tipoRelacion" value="${sessionScope[appConstants.transferencias.TIPO_RELACION_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.transferencias.LISTA_GESTORES_KEY]}" />
<c:set var="relaciones" value="${sessionScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />

<bean:struts id="actionMappingArchivo" mapping="/cesionRelacionesArchivo" />
<bean:struts id="actionMappingOrgRemitente" mapping="/cesionRelacionesOrgRemitente" />
<bean:struts id="actionMappingIngreso" mapping="/cesionIngresoDirecto" />

<c:choose>
	<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
		<c:set var="mappingName" value="${actionMappingArchivo.name}" />
		<c:set var="mappingPath" value="${actionMappingArchivo.path}" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
				<c:set var="mappingName" value="${actionMappingOrgRemitente.name}" />
				<c:set var="mappingPath" value="${actionMappingOrgRemitente.path}" />
			</c:when>
			<c:otherwise>
				<c:set var="mappingName" value="${actionMappingIngreso.name}" />
				<c:set var="mappingPath" value="${actionMappingIngreso.path}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>	

<script>
	function goOn() 
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "seleccionar";
		form.submit();
	}
	function reload()
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "verBuscadorGestor";
		form.submit();
	}
	function search()
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "buscar";
		form.submit();
	}
	function busquedaRelacionForm()
	{
		window.location = "<c:url value="/action${mappingPath}" />?method=verBuscador";
	}
	function busquedaRelacionPorGestorForm()
	{
		window.location = "<c:url value="/action${mappingPath}" />?method=verBuscadorGestor";
	}
	function busquedaIngresoForm()
	{
		window.location = "<c:url value="/action${mappingPath}" />?method=verBuscador";
	}
	function busquedaIngresoPorGestorForm()
	{
		window.location = "<c:url value="/action${mappingPath}" />?method=verBuscadorGestor";
	}
</script>

<form name="<c:out value="${mappingName}" />" action="../../action<c:out value="${mappingPath}" escapeXml="false"/>">
<input type="hidden" name="tipoBusqueda" value="<c:out value="${CesionRelacionesForm.tipoBusqueda}" />" />
<input type="hidden" name="method" value="buscar"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
				<bean:message key="archigest.archivo.transferencias.relaciones.archivo.cederControl"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
						<bean:message key="archigest.archivo.transferencias.relaciones.orgrem.cederControl"/>
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.fondos.ingreso.directo.cederControl"/>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>	
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img 
					page="/pages/images/Next.gif" 
					altKey="archigest.archivo.siguiente" 
					titleKey="archigest.archivo.siguiente" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
			</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<c:choose>
			<c:when test="${CesionRelacionesForm.tipoBusqueda==1}">
				<c:set var="classTab1" value="tabActual" />
				<c:set var="classLink1" value="textoPestana" />
				<c:set var="classTab2" value="tabSel" />
				<c:set var="classLink2" value="textoPestanaSel" />
			</c:when>
			<c:otherwise>
				<c:set var="classTab1" value="tabSel" />
				<c:set var="classLink1" value="textoPestanaSel" />
				<c:set var="classTab2" value="tabActual" />
				<c:set var="classLink2" value="textoPestana" />
			</c:otherwise>
		</c:choose>

		<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
				<td class="<c:out value="${classTab1}"/>">
					<c:choose>
						<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<a href="javascript:busquedaIngresoForm()" class="<c:out value="${classLink1}"/>">
								<bean:message key="archigest.archivo.fondos.ingreso.directo"/>
							</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:busquedaRelacionForm()" class="<c:out value="${classLink1}"/>">
								<bean:message key="archigest.archivo.relacion"/>
							</a>						
						</c:otherwise>
					</c:choose>
				</td>
				<td width="5px">&nbsp;</td>
				<td class="<c:out value="${classTab2}"/>">
					<c:choose>
						<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<a href="javascript:busquedaIngresoPorGestorForm()" class="<c:out value="${classLink2}"/>">
								<bean:message key="archigest.archivo.fondos.ingreso.buscarIngresoGestor"/>
							</a>						
						</c:when>
						<c:otherwise>
							<a href="javascript:busquedaRelacionPorGestorForm()" class="<c:out value="${classLink2}"/>">
								<bean:message key="archigest.archivo.transferencias.relaciones.buscarRelGestor"/>
							</a>
						</c:otherwise>
					</c:choose>
				</td>
				</tr>
			</table>
		</div>

		<div class="bloque_tab">
			<div class="cabecero_bloque_tab">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TR>
					<TD width="100%" align="right">
						<a class="etiquetaAzul12Normal" 
							href="javascript:search()">
							<html:img titleKey="archigest.archivo.buscar" 
								altKey="archigest.archivo.buscar" 
								page="/pages/images/buscar.gif" 
								styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.buscar"/>
						</a>		
					</TD>
				  </TR>
				</TABLE>
			</div>
			
			<table class="w100">
				<tr>
					<td width="10px">&nbsp;</td>
					<td class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.buscarPor"/>:&nbsp;
					</td>
				</tr>
			</table>

			<c:choose>
				<c:when test="${CesionRelacionesForm.tipoBusqueda==1}">
					<table class="formulario" width="99%">
						<c:if test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
							<tr>
								<td width="20px">&nbsp;</td>
								<td class="tdTitulo" width="200px">
									<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.tipo"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<select name="tipo">
										<option value="">&nbsp;</option>
										<option value="1"
											<c:if test="${CesionRelacionesForm.tipo==1}">
											selected="true"
											</c:if>
										><bean:message key="archigest.archivo.transferencias.ord" /></option>
										<option value="2"
											<c:if test="${CesionRelacionesForm.tipo==2}">
											selected="true"
											</c:if>
										><bean:message key="archigest.archivo.transferencias.ext" /></option>
									</select>
								</td>
							</tr>
						</c:if>
						<c:if test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<input type="hidden" name="tipo" value="3"/>
						</c:if>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<c:choose>
									<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
										<bean:message key="archigest.archivo.archivoCustodia"/>:&nbsp;			
									</c:when>
									<c:otherwise>
										<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.organo"/>:&nbsp;
									</c:otherwise>				
								</c:choose>
							</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ARCHIVO}">
										<input type="text" name="organo" 
											value="<c:out value="${CesionRelacionesForm.organo}" />" 
											class="input60" />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
												
												<c:set var="listaArchivos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY]}" />
												<select name="archivo">
													<option value="">&nbsp;</option>
													<c:forEach var="archivo" items="${listaArchivos}">
													<option value="<c:out value="${archivo.id}" />"
														<c:if test="${CesionRelacionesForm.archivo==archivo.id}">
														selected="true"
														</c:if>
														>
														<c:out value="${archivo.nombre}" />
													</option>
													</c:forEach>
												</select>
												
												
												
												
											</c:when>
											<c:otherwise>
												<c:set var="organos" value="${sessionScope[appConstants.transferencias.LISTA_ORGANOS_KEY]}" />
												<select name="organo">
													<option value="">&nbsp;</option>
													<c:forEach var="organo" items="${organos}">
													<option value="<c:out value="${organo.idOrg}" />"
														<c:if test="${CesionRelacionesForm.organo==organo.idOrg}">
														selected="true"
														</c:if>
														>
														<c:out value="${organo.nombre}" />
													</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>	
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.codigo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<input type="text" name="codigo" 
									value="<c:out value="${CesionRelacionesForm.codigo}" />" 
									class="input60" />
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
					<table class="formulario" width="99%">
						<c:if test="${tipoRelacion == appConstants.transferencias.RELACION_EN_ORG_REMITENTE}">
							<tr>
								<td width="20px">&nbsp;</td>
								<td class="tdTitulo" width="200px">
									<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.tipo"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<select name="tipo" onchange="javascript:reload()">
										<option value="1"
											<c:if test="${CesionRelacionesForm.tipo==1}">
											selected="true"
											</c:if>
										><bean:message key="archigest.archivo.transferencias.ord" /></option>
										<option value="2"
											<c:if test="${CesionRelacionesForm.tipo==2}">
											selected="true"
											</c:if>
										><bean:message key="archigest.archivo.transferencias.ext" /></option>
									</select>
								</td>
							</tr>
						</c:if>
						<c:if test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<input type="hidden" name="tipo" value="3"/>
						</c:if>						
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.transferencias.userGestor"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${!empty gestores}">
										<select name="gestor">
											<option value="">&nbsp;</option>
											<c:forEach var="gestor" items="${gestores}">
											<option value="<c:out value="${gestor.id}" />"
												<c:if test="${CesionRelacionesForm.gestor==gestor.id}">
												selected="true"
												</c:if>
												>
												<c:out value="${gestor.nombreCompleto}" />
											</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<bean:message key="archigest.archivo.transferencias.relaciones.gestores.empty"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		
		<input type="hidden" name="resultado" value="<c:out value="${CesionRelacionesForm.resultado}" />" />
		<c:if test="${CesionRelacionesForm.resultado}">
			<div class="separador8">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<c:choose>
					<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<bean:message key="archigest.archivo.fondos.ingreso.directo.msgSelCeder" />			
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.transferencias.relaciones.msgSelRelCeder" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">	
				<jsp:useBean id="mappingPath" type="java.lang.String" />
				
				<c:if test="${(relaciones != null) && (not empty relaciones)}">
						<div class="separador8">&nbsp;</div>
						<table cellpadding=0 cellspacing=0 class="w100">
						  <tr>
						   <td align="right">
								<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].relacionesSeleccionadas);" >
									<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
									<bean:message key="archigest.archivo.selTodos"/>&nbsp;
								</a>&nbsp;
								<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].relacionesSeleccionadas);" >
									<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
								</a>&nbsp;&nbsp;
						   </td>
						 </tr>
						</table>
				</c:if>
				<display:table name="pageScope.relaciones"
					id="relacion" 
					export="false"
					pagesize="15"
					sort="list"
					requestURI='<%="../../action" + mappingPath%>'
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<c:choose>
							<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
								<bean:message key="archigest.archivo.fondos.ingreso.directo.noIngresosACeder" />			
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.relaciones.noRelacionesACeder"/>
							</c:otherwise>
						</c:choose>					
					</display:setProperty>

					<display:column title="" style="width:20px">
						<input type="checkbox" name="relacionesSeleccionadas" 
							value='<c:out value="${relacion.id}"/>' />
					</display:column>

					<c:choose>					
						<c:when test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
							<display:column titleKey="archigest.archivo.fondos.ingreso.directo" sortable="true" headerClass="sortable" >
								<c:url var="verURL" value="/action/gestionRelaciones">
									<c:param name="method" value="verrelacion" />
									<c:param name="idrelacionseleccionada" value="${relacion.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
									<c:out value="${relacion.codigoTransferencia}"/>
								</a>
							</display:column>						
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" >
								<c:url var="verURL" value="/action/gestionRelaciones">
									<c:param name="method" value="verrelacion" />
									<c:param name="idrelacionseleccionada" value="${relacion.id}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
									<c:out value="${relacion.codigoTransferencia}"/>
								</a>
							</display:column>
						</c:otherwise>
					</c:choose>
					<display:column titleKey="archigest.archivo.transferencias.relaciones.usuarioRemitente">
						<c:if test="${!empty relacion.apellidosusuario}">
							<c:out value="${relacion.apellidosusuario}"/>,
						</c:if>
						<c:out value="${relacion.nombreusuario}"/>
					</display:column>
					
					<c:if test="${tipoRelacion != appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<display:column titleKey="archigest.archivo.transferencias.relaciones.usuarioArchivo">
							<c:out value="${relacion.gestorEnArchivo.nombreCompleto}"/>
						</display:column>
					</c:if>
					
					<c:if test="${tipoRelacion != appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<display:column titleKey="archigest.archivo.transferencias.tipoTrans" sortable="true" headerClass="sortable">
							<c:set var="keyTitulo">
								archigest.archivo.transferencias.tipotrans<c:out value="${relacion.tipotransferencia}"/>
							</c:set>
							<fmt:message key="${keyTitulo}" />
						</display:column>
					</c:if>
					<c:if test="${tipoRelacion == appConstants.transferencias.INGRESO_DIRECTO_KEY}">
						<display:column titleKey="archigest.archivo.archivoCustodia" sortable="true" headerClass="sortable">
							<c:out value="${relacion.nombreArchivoReceptor}"/>
						</display:column>					
					</c:if>					
					<display:column titleKey="archigest.archivo.transferencias.estado" sortable="true" headerClass="sortable">
						<c:set var="keyTituloEstado">
							archigest.archivo.transferencias.estadoRelacion.<c:out value="${relacion.estado}"/>
						</c:set>
						<fmt:message key="${keyTituloEstado}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.organo" sortable="true" headerClass="sortable">
						<c:out value="${relacion.organoRemitente.nombreLargo}"/>
					</display:column>
				</display:table>
				
				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>

</tiles:insert>
</form>