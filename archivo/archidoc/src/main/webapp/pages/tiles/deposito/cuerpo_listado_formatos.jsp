<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="formatos" value="${requestScope[appConstants.deposito.LISTA_FORMATOS]}" />

<bean:struts id="actionMapping" mapping="/gestionFormatosAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.listadoFormatosDisponibles"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.depositoActions.ALTA_FORMATO_ACTION}">
				<td noWrap>
					<c:url var="creacionURL" value="/action/gestionFormatosAction">
						<c:param name="method" value="alta" />
					</c:url>
					<a class="etiquetaAzul12Bold" 
						href="<c:out value="${creacionURL}" escapeXml="false"/>">
						<html:img page="/pages/images/newDoc.gif" 
							altKey="archigest.archivo.crear" 
							titleKey="archigest.archivo.crear" 
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.crear"/>
					</a>
				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
				<c:if test="${!empty formatos}">
				<security:permissions action="${appConstants.depositoActions.BAJA_FORMATO_ACTION}">
				<td noWrap>
					<script>
						function eliminar()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							if (form.formatosSeleccionados && elementSelected(form.formatosSeleccionados)) 
							{
								if (confirm("<fmt:message key='archigest.archivo.deposito.formato.delete.confirm.msg'/>")) 
									form.submit();
							}
							else
								alert("<fmt:message key='archigest.archivo.deposito.formato.delete.warning.msg'/>");
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:eliminar()">
						<html:img page="/pages/images/deleteDoc.gif" 
							altKey="archigest.archivo.eliminar" 
							titleKey="archigest.archivo.eliminar" 
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
				</c:if>
				<c:if test="${appConstants.configConstants.mostrarAyuda}">
					<td width="10">&nbsp;</td>
					<td>
						<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
						<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
						<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/deposito/DepositosFormatos.htm');">
						<html:img page="/pages/images/help.gif" 
						        altKey="archigest.archivo.ayuda" 
						        titleKey="archigest.archivo.ayuda" 
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
					</td>
					<td width="10">&nbsp;</TD>
				</c:if>				
				<td>
					<tiles:insert definition="button.closeButton" />	
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<div class="bloque">
					<div class="separador1">&nbsp;</div>
	
					<html:form action="/gestionFormatosAction">
					<input type="hidden" name="method" value="eliminarVarios"/>
	
					<display:table name="pageScope.formatos"
						id="formato" 
						style="width:99%;margin-left:auto;margin-right:auto"
						defaultorder="ascending"
						defaultsort="2" >
	
						<display:setProperty name="basic.msg.empty_list">
							<fmt:message key="archigest.archivo.deposito.formato.empty"/>
						</display:setProperty>
						
						<security:permissions action="${appConstants.depositoActions.BAJA_FORMATO_ACTION}">
						<display:column style="width:23px" headerClass="deleteDocIcon">
							<input type="checkbox" name="formatosSeleccionados" value="<c:out value="${formato.id}"/>"/>
						</display:column>
						</security:permissions>
						<display:column titleKey="archigest.archivo.nombre">
							<c:url var="verFormatoURL" value="/action/gestionFormatosAction">
								<c:param name="method" value="verFormato" />
								<c:param name="id" value="${formato.id}" />
							</c:url>
							<a class="tdlink" href='<c:out value="${verFormatoURL}" escapeXml="false"/>' >
								<c:out value="${formato.nombre}"/>
							</a>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.formato.vigente">
							<c:choose>
								<c:when test="${formato.vigente}">
									<bean:message key="archigest.archivo.si"/>
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					</display:table>
	
					</html:form>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>