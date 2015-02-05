<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="deposito" value="${sessionScope[appConstants.deposito.DEPOSITO_KEY]}" />

<bean:struts id="actionMapping" mapping="/gestionDepositosElectronicos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.electronico.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
				<td nowrap>
					<script>
						function remove()
						{
							<c:url var="eliminarURL" value="/action/gestionDepositosElectronicos">
								<c:param name="method" value="removeDeposito" />
								<c:param name="id" value="${deposito.id}" />
							</c:url>
							if (confirm("<bean:message key="archigest.archivo.deposito.electronico.delete.confirm.msg"/>"))
								window.location = "<c:out value="${eliminarURL}" escapeXml="false" />";
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
				<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>
								<c:url var="edicionURL" value="/action/gestionDepositosElectronicos">
									<c:param name="method" value="form" />
									<c:param name="id" value="${deposito.id}" />
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
							<bean:message key="archigest.archivo.deposito.electronico.idExt"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${deposito.idExt}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${deposito.nombre}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.descripcion"/>:&nbsp;				
						</td>
						<td class="tdDatos"><c:out value="${deposito.descripcion}" /></td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.electronico.usoFirma"/>:&nbsp;				
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${deposito.usoFirma=='S'}">
									<bean:message key="archigest.archivo.deposito.electronico.usoFirma.conFirma" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.deposito.electronico.usoFirma.sinFirma" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.electronico.espacioTotal"/>:&nbsp;				
						</td>
						<td class="tdDatos">
							<fmt:formatNumber value="${deposito.espacioTotal / 1048576}" pattern="#,###.##"/>
							<bean:message key="archigest.archivo.megabyte"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.electronico.espacioOcupado"/>:&nbsp;				
						</td>
						<td class="tdDatos">
							<fmt:formatNumber value="${deposito.espacioOcupado / 1048576}" pattern="#,###.##"/>
							<bean:message key="archigest.archivo.megabyte"/>
							<c:if test="${!empty deposito.espacioTotal && deposito.espacioTotal>0}">
								(<fmt:formatNumber value="${(deposito.espacioOcupado / deposito.espacioTotal)*100}" pattern="###.##"/>
								<bean:message key="archigest.archivo.simbolo.porcentaje"/>)
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.electronico.numFicheros"/>:&nbsp;				
						</td>
						<td class="tdDatos">
							<fmt:formatNumber value="${deposito.numeroFicheros}" pattern="#,###"/>
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.electronico.webservice.wsdl"/>:&nbsp;				
						</td>
						<td class="tdDatos"><c:out value="${deposito.uri}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.usuario"/>:&nbsp;				
						</td>
						<td class="tdDatos"><c:out value="${deposito.usuario}" /></td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
