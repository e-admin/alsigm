<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionOrganos" mapping="/gestionOrganos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.altaOrgano"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">

	<script>
		function seleccionarOrgano() {
			var form = document.forms['<c:out value="${mappingGestionOrganos.name}" />'];
			form.method.value = 'seleccionOrgano';
			form.submit();
		}
	</script>

		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:seleccionarOrgano()" >
			<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.siguiente"/></a>
		</td>		
		<td width="10px"></td>		
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true">
				<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
				<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
			</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionOrganos" >
		<div class="separador1">&nbsp;</div>
		<div class="cabecero_bloque">
			<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<tr>
					<input type="hidden" name="method" value="buscarEnSistemaExterno">
	
					<TD class="etiquetaAzul12Bold" width="250px">
						<bean:message key="archigest.archivo.cacceso.buscarOrgano"/>&nbsp;
					</TD>
					<td align="right">
						<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionOrganos.name}" />'].submit()">
							<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.buscar"/>
						</a>		
					</td>
					<td width="10"></td>
				</tr>	
			</table>
		</div>
		<div class="bloque">
			<table  class="formulario" align="center" style="width:99%">			
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.sistExterno"/>:
					</td>
					<td class="tdDatos">
						<c:set var="sistemasExternos" value="${requestScope[appConstants.controlAcceso.LISTA_SISTEMAS_ORGANIZACION]}" />
						<html:select property="sistemaExterno">
							<html:options collection="sistemasExternos" property="id" labelProperty="nombre" />
						</html:select>
					</td>
				</tr>	
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.nombre"/>:
					</td>
					<td class="tdDatos">
						<html:text styleClass="input80" maxlength="254" property="nombre" />
					</td>
				</tr>		
			</table>
		</div>

		<c:set var="listaOrganos" value="${requestScope[appConstants.controlAcceso.LISTA_ORGANOS]}" />
		<c:if test="${listaOrganos != null}">

			<div class="separador8"></div>

			<div class="cabecero_bloque">
				<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.resultadosBusqueda"/>
				</td>		
				</tr></table>
			</div>

			<div class="bloque" style="padding-top:8px;padding-bottom:8px">
				<c:url var="listaOrganosPaginationURI" value="/action/gestionOrganos">
					<c:param name="method" value="${param.method}" />
				</c:url>
				<jsp:useBean id="listaOrganosPaginationURI" type="java.lang.String" />

				<display:table name="pageScope.listaOrganos" 
						id="organo" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						requestURI='<%=listaOrganosPaginationURI%>'
						pagesize="10">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="errors.gcontrol.altaOrgano.noOrganosSistExternoSinIncorporar"/>
					</display:setProperty>
	
					<display:column style="width:10px">
							<input type="radio" name="idOrganoSeleccionado" value="<c:out value="${organo.id}" />" 
								<c:if test="${organo_rowNum==1}">checked="true"</c:if>
							/>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre">
						<table width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td style="border:0;"><c:out value="${organo.nombre}"/></td>
								<td width="20px" style="border:0;text-align:right;">
									<c:url var="URL_EXPAND_ORGANO" value="/action/gestionOrganos">
										<c:param name="method" value="expandOrgano" />
										<c:param name="idOrgDesplegado" value="${organo.id}" />
										<c:param name="sistemaExterno" value="${organoForm.sistemaExterno}" />
									</c:url>
									<a href="<c:out value="${URL_EXPAND_ORGANO}" escapeXml="false"/>"
										><html:img page="/pages/images/expand.gif"
										altKey="archigest.archivo.desplegar"
										titleKey="archigest.archivo.desplegar"
										styleClass="imgTextMiddleHand"/></a>
								</td>
							</tr>
							<c:if test="${param.idOrgDesplegado == organo.id}">
							<tr>
								<td style="border:0;">
									<c:set var="antecesores" value="${requestScope[appConstants.controlAcceso.LISTA_ORGANOS_ANTECESORES]}" />
									<table cellpadding="0" cellspacing="0">
										<c:forEach var="antecesor" items="${antecesores}" varStatus="nivel">
										<tr>
											 <td width="100%" style="border:0;vertical-align:top;">
												<span style="padding-left:<c:out value="${nivel.count*10}px"/>;" class="user_data" >
													<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="8px"/>
						  						    <c:choose>
														<c:when test="${nivel.last}">
															<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
													    </c:when>
													  	<c:otherwise>
															<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
														</c:otherwise>
													</c:choose>
													<c:out value="${antecesor.nombre}" />
												</span>
											</td>
										 </tr>
										</c:forEach>
									</table>
								</td>
								<td style="border:0;">&nbsp;</td>
							</tr>
							</c:if>
						</table>
					</display:column>
				</display:table>
			</div>
		</c:if>
	</html:form>

	</tiles:put>
</tiles:insert>