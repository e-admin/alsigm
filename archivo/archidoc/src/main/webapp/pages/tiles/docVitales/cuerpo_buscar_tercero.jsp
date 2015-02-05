<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingGestionInteresados" mapping="/gestionDocumentosVitales" />

<c:set var="listadoTerceros" value="${sessionScope[appConstants.documentosVitales.RESULTADOS_BUSQUEDA_TERCEROS]}" />

<script>
	function aceptarBusqueda()
	{
		var form = document.forms['<c:out value="${mappingGestionInteresados.name}"/>'];
		form.method.value="anadirDocumentoPaso2";
		form.submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.docvitales.docVital.incorporarInteresado" /></tiles:put>
	<tiles:put name="buttonBar" direct="true">	
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   <TD>
				<table cellpadding=0 cellspacing=0>
					<tr>
						<td><a class="etiquetaAzul12Bold" href="javascript:aceptarBusqueda();">
							<html:img page="/pages/images/Next.gif" 
							altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente"
							styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.siguiente"/></a>
						</td>
						<td width="10">&nbsp;</td>
						<td>
							<tiles:insert definition="button.closeButton" flush="true">
								<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
								<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
								<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
							</tiles:insert>
						</td>
					</tr>
				</table>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
		<html:form action="/gestionDocumentosVitales">
		<input type="hidden" name="method" value="anadirDocumentoPaso1">
		<div id="InteresadoValidado" style="display:block;">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.docvitales.docVital.incorporarInteresado" />			
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
					<script>
						function buscarInteresados() 
						{
							var searchForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
							if (searchForm.nameSearchToken.value == ''
								&& searchForm.surname1SearchToken.value == ''
								&& searchForm.surname2SearchToken.value == ''
								&& searchForm.companySearchToken.value == ''
								&& searchForm.ifSearchToken.value == '') 
									alert("<bean:message key="errors.noSearchToken"/>");
							else
							{
								searchForm.method.value = 'buscarCiudadanosCreacion';
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
									var message = '<bean:message key="archigest.archivo.buscando.msgTerceros"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								searchForm.submit();
							}
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
				<tiles:insert page="/pages/tiles/controls/thirdPartySearchForm.jsp"/>
				
				<c:if test="${listadoTerceros != null}">	
					<div class="separador8">&nbsp;</div>
						
					<div class="titulo_left_bloque">
						<bean:message key="archigest.archivo.resultadosBusqueda" />:&nbsp;&nbsp;
					</div>
		
					<c:choose>
					<c:when test="${!empty listadoTerceros}">
					<display:table 
						name="pageScope.listadoTerceros" 
						id="interesado" 
						style="width:99%;margin-left:auto;margin-right:auto"
						pagesize="10"
						requestURI="/action/gestionDocumentosVitales">
						
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.transferencias.noInteresadoBD"/>
						</display:setProperty >
						
						<display:column title="&nbsp;" style="width:20px">
							<input type="radio" name="idBdTerceros" value="<c:out value="${interesado.id}"/>" />
						</display:column>
						<display:column titleKey="archigest.archivo.identificacion" property="identificacion" />
						<display:column titleKey="archigest.archivo.nombre" property="nombre" />
						<display:column titleKey="archigest.archivo.PrimerApellido" property="primerApellido" />
						<display:column titleKey="archigest.archivo.SegundoApellido" property="segundoApellido" />
					</display:table>
					</c:when>
					<c:otherwise>
					<div class="bloque" style="width:99%;color:#1083CF;background-color:#F0F0F2;padding:3px;text-align:left;margin-bottom:6px">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<bean:message key="archigest.archivo.docvitales.docVital.terceros.empty.msg"/>
					</div>
					</c:otherwise>
					</c:choose>		
				</c:if>
			</tiles:put>
		</tiles:insert>
		</div>
	
		</html:form>	
	</tiles:put>
</tiles:insert>	