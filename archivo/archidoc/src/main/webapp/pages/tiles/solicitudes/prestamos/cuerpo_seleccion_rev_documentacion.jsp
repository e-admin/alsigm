<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="gestores" value="${sessionScope[appConstants.prestamos.LISTA_GESTORES_KEY]}" />
<c:set var="revisionesDoc" value="${requestScope[appConstants.prestamos.LISTA_REVISIONES_DOC_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionRevisionDocumentacion" />

<script>
	function goOn() 
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "seleccionar";
		form.submit();
	}
	function reload()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "verBuscadorGestor";
		form.submit();
	}
	function search()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value = "buscar";
		form.submit();
	}
</script>

<html:form action="/cesionRevisionDocumentacion">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="buscar"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.revisiones.documentacion.cederControl"/>
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
			<c:when test="${cesionRevisionDocumentacionForm.tipoBusqueda==1}">
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
						<c:url var="buscarPorRevDocURL" value="/action/cesionRevisionDocumentacion">
							<c:param name="method" value="verBuscador" />
						</c:url>
						<a href="<c:out value="${buscarPorRevDocURL}" escapeXml="false"/>" 
							class="<c:out value="${classLink1}"/>">
							<bean:message key="archigest.archivo.prestamos.busquedaPorRevDocumentacion"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTab2}"/>">
						<c:url var="buscarPorGestorURL" value="/action/cesionRevisionDocumentacion">
							<c:param name="method" value="verBuscadorGestor" />
						</c:url>
						<a href="<c:out value="${buscarPorGestorURL}" escapeXml="false"/>" 
							class="<c:out value="${classLink2}"/>">
							<bean:message key="archigest.archivo.prestamos.busquedaPorGestor"/>
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
				<c:when test="${cesionRevisionDocumentacionForm.tipoBusqueda==1}">
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.titulo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:text styleClass="input60" property="titulo" />
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.signatura"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:text styleClass="input60" property="signatura" />
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.solicitudes.numExp"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:text styleClass="input60" property="expediente" />
							</td>
						</tr>
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.solicitudes.observaciones"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<html:text styleClass="input60" property="observaciones" />
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
					<table class="formulario" width="99%">
						<tr>
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.prestamos.gestor"/>:&nbsp;
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
										<bean:message key="archigest.archivo.prestamos.revisionesDocumentacion.gestores.empty"/>
										<html:hidden property="gestor"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</div>

		<c:if test="${cesionRevisionDocumentacionForm.resultado}">
			<div class="separador8">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.revisionDocumentacion.msgSelACeder"/></tiles:put>
				<tiles:put name="blockContent" direct="true">	
			
				<display:table name="pageScope.revisionesDoc"
					id="revDoc" 
					export="false"
					pagesize="15"
					sort="list"				
					requestURI="../../action/cesionRevisionDocumentacion"
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.prestamos.noRevisionesDocumentacionACeder"/>
					</display:setProperty>

					<display:column title="" style="width:20px">
						<input type="radio" name="revDocSeleccionada" 
							value="<c:out value="${revDoc.idRevDoc}"/>" />
					</display:column>
	
					<display:column titleKey="archigest.archivo.titulo" property="titulo"/>
					<display:column titleKey="archigest.archivo.solicitudes.numExp" property="expedienteUdoc"/>
					<display:column titleKey="archigest.archivo.signatura" property="signaturaUdoc"/>
					<display:column titleKey="archigest.archivo.solicitudes.observaciones" property="observaciones"/>
					<display:column titleKey="archigest.archivo.prestamos.gestor" property="nombreGestor"/>
				</display:table>
				<div class="separador5">&nbsp;</div>

				</tiles:put>
			</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>