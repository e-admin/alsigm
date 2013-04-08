<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="formato" value="${sessionScope[appConstants.deposito.INFO_FORMATO]}" />

<bean:struts id="actionMapping" mapping="/gestionFormatosAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.formato.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.depositoActions.BAJA_FORMATO_ACTION}">
				<td nowrap>
					<script>
						function remove()
						{
							<c:url var="eliminarURL" value="/action/gestionFormatosAction">
								<c:param name="method" value="eliminar" />
								<c:param name="formatoSeleccionado" value="${formato.id}" />
							</c:url>
							if (confirm("<fmt:message key="archigest.archivo.deposito.formato.delete.confirm.msg"/>"))
								window.location = "<c:out value="${eliminarURL}" escapeXml="false"/>";
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:remove()">
						<html:img page="/pages/images/delete.gif" 
						titleKey="archigest.archivo.eliminar" 
						altKey="archigest.archivo.eliminar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<security:permissions action="${appConstants.depositoActions.MODIFICAR_FORMATO_ACTION}">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>
								<c:url var="edicionURL" value="/action/gestionFormatosAction">
									<c:param name="method" value="edicion" />
									<c:param name="id" value="${formato.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" 
									href="<c:out value="${edicionURL}" escapeXml="false"/>">
									<html:img page="/pages/images/editDoc.gif" 
									titleKey="archigest.archivo.editar" 
									altKey="archigest.archivo.editar"
									styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.editar"/>
								</a>
							</td>
						</tr>
					</table>
				</security:permissions>
			</tiles:put>


			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.tipo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${formato.regular}">
									<fmt:message key="archigest.archivo.deposito.formato.regular" />
								</c:when>
								<c:otherwise>
									<fmt:message key="archigest.archivo.deposito.formato.irregular" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${formato.nombre}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.multidoc"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${formato.multidoc}">
									<fmt:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<fmt:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				<c:if test="${formato.regular}">
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${formato.longitud}" />&nbsp;<fmt:message key="archigest.archivo.cm"/>
						</td>
					</tr>
				</c:if>					
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.vigente"/>:&nbsp;
						</td>
						<td class="tdDatos">						
							<c:choose>
								<c:when test="${formato.vigente}">
									<fmt:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<fmt:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
