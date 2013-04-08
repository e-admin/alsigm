<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="organos" value="${sessionScope[appConstants.transferencias.LISTA_ORGANOS_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.transferencias .LISTA_GESTORES_KEY]}" />
<c:set var="previsiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionPrevisiones" />

<script>
	function goOn() 
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "seleccionarPrevision";
		form.submit();
	}
	function reload()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "verBuscadorPrevisionesGestor";
		form.submit();
	}
	function search()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "buscarPrevisiones";
		form.submit();
	}
</script>

<html:form action="/cesionPrevisiones">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="buscarPrevisiones"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.previsiones.cederControl"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img page="/pages/images/Next.gif" 
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
			<c:when test="${CesionPrevisionesForm.tipoBusqueda==1}">
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
						<c:url var="buscarPorPrevURL" value="/action/cesionPrevisiones">
							<c:param name="method" value="verBuscadorPrevisiones" />
						</c:url>
						<a href="<c:out value="${buscarPorPrevURL}" escapeXml="false"/>" 
							class="<c:out value="${classLink1}"/>">
							<bean:message key="archigest.archivo.transferencias.previsiones.buscarPrev"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTab2}"/>">
						<c:url var="buscarPorGestorURL" value="/action/cesionPrevisiones">
							<c:param name="method" value="verBuscadorPrevisionesGestor" />
						</c:url>
						<a href="<c:out value="${buscarPorGestorURL}" escapeXml="false"/>" 
							class="<c:out value="${classLink2}"/>">
							<bean:message key="archigest.archivo.transferencias.previsiones.buscarPrevGestor"/>
						</a>
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
				<c:when test="${CesionPrevisionesForm.tipoBusqueda==1}">
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.transferencias.previsiones.busqueda.tipo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:select property="tipo">
									<option value="">&nbsp;</option>
									<html:option key="archigest.archivo.transferencias.ord" value="1"/>
									<html:option key="archigest.archivo.transferencias.ext" value="2"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.organo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:select property="organo">
									<option value="">&nbsp;</option>
									<html:optionsCollection name="organos" label="nombre" value="idOrg"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.codigo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:text styleClass="input60" property="codigo" />
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.transferencias.previsiones.busqueda.tipo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:select property="tipo" onchange="javascript:reload()">
									<html:option key="archigest.archivo.transferencias.ord" value="1"/>
									<html:option key="archigest.archivo.transferencias.ext" value="2"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.organo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:select property="organo" onchange="javascript:reload()">
									<html:optionsCollection name="organos" label="nombre" value="idOrg"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.transferencias.userGestor"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${!empty gestores}">
										<html:select property="gestor">
											<option value="">&nbsp;</option>
											<html:optionsCollection name="gestores" label="nombreCompleto" value="id"/>
										</html:select>
									</c:when>
									<c:otherwise>
										<bean:message key="archigest.archivo.transferencias.previsiones.gestores.empty"/>
										<html:hidden property="gestor"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</div>

		<c:if test="${CesionPrevisionesForm.resultado}">
			<div class="separador8">&nbsp;</div>
			
			<div class="bloque">
				<table class="w100">
					<tr>
						<td width="10px">&nbsp;</td>
						<td class="etiquetaAzul12Bold">
							<bean:message key="archigest.archivo.transferencias.previsiones.msgSelPrevCeder"/>:
						</td>
					</tr>
				</table>
	
				<display:table name="pageScope.previsiones"
					id="prevision" 
					export="false"
					pagesize="15"
					sort="list"				
					requestURI="../../action/cesionPrevisiones"
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.previsiones.noPrevisionesACeder"/>
					</display:setProperty>

					<display:column title="" style="width:20px">
						<c:choose>
							<c:when test="${CesionPrevisionesForm.tipoBusqueda==1}">
								<input type="radio"
									name="previsionesSeleccionadas" 
									value="<c:out value="${prevision.id}"/>" />
							</c:when>
							<c:otherwise>
								<input type="checkbox"
									name="previsionesSeleccionadas" 
									value='<c:out value="${prevision.id}"/>' />
							</c:otherwise>
						</c:choose>
					</display:column>
	
					<display:column titleKey="archigest.archivo.transferencias.prevision">
						<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
							<c:param name="method" value="verprevision" />
							<c:param name="idprevision" value="${prevision.id}" />
						</c:url>
						<a class="tdlink" href='<c:out value="${verPrevisionURL}" escapeXml="false"/>' >
							<c:out value="${prevision.codigoTransferencia}"/>
						</a>
					</display:column>
					
					<display:column titleKey="archigest.archivo.transferencias.tipoTrans">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />		
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.gestor">
							<c:out value="${prevision.nombreUsuario}"/>
							<c:out value="${prevision.apellidosUsuario}"/>
					</display:column>
					
					<display:column titleKey="archigest.archivo.transferencias.estado" >
						<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
					</display:column>
	
					<display:column titleKey="archigest.archivo.transferencias.fEstado">
						<fmt:formatDate value="${prevision.fechaestado}" 
							pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
				</display:table>
				
				<div class="separador5">&nbsp;</div>
			</div>
		</c:if>

	</tiles:put>

</tiles:insert>
</html:form>