<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="depositos" value="${requestScope[appConstants.deposito.LISTA_DEPOSITOS_KEY]}" />

<bean:struts id="actionMapping" mapping="/gestionDepositosElectronicos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.electronico.home.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
				<td noWrap>
					<c:url var="creacionURL" value="/action/gestionDepositosElectronicos">
						<c:param name="method" value="form" />
					</c:url>
					<a class="etiquetaAzul12Bold" 
						href="<c:out value="${creacionURL}" escapeXml="false"/>">
						<html:img page="/pages/images/new.gif" 
							altKey="archigest.archivo.crear" 
							titleKey="archigest.archivo.crear" 
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.crear"/>
					</a>
				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
				<c:if test="${!empty depositos}">
				<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
				<td noWrap>
					<script>
						function eliminar()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							if (form.depositosSeleccionados && elementSelected(form.depositosSeleccionados)) 
							{
								if (confirm("<bean:message key="archigest.archivo.deposito.electronico.home.delete.confirm.msg"/>")) 
									form.submit();
							}
							else
								alert("<bean:message key='archigest.archivo.deposito.electronico.home.delete.warning.msg'/>");
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:eliminar()">
						<html:img page="/pages/images/delete.gif" 
							altKey="archigest.archivo.eliminar" 
							titleKey="archigest.archivo.eliminar" 
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
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
	
					<html:form action="/gestionDepositosElectronicos">
					<input type="hidden" name="method" value="remove"/>
	
					<display:table name="pageScope.depositos"
						id="deposito" 
						style="width:99%;margin-left:auto;margin-right:auto">
	
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.deposito.electronico.home.empty"/>
						</display:setProperty>
						
						<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
						<display:column style="width:23px" headerClass="deleteFolderIcon">
							<input type="checkbox" name="depositosSeleccionados" 
								value="<c:out value="${deposito.id}"/>"/>
						</display:column>
						</security:permissions>
						<display:column titleKey="archigest.archivo.nombre" style="width:200px">
							<c:url var="verDepositoURL" value="/action/gestionDepositosElectronicos">
								<c:param name="method" value="retrieve" />
								<c:param name="id" value="${deposito.id}" />
							</c:url>
							<a class="tdlink" href='<c:out value="${verDepositoURL}" escapeXml="false"/>' >
								<c:out value="${deposito.nombre}"/>
							</a>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.electronico.idExt" property="idExt" style="width:100px"/>
						<display:column titleKey="archigest.archivo.deposito.electronico.usoFirma" style="width:20px">
							<c:if test="${deposito.usoFirma=='S'}">
								<html:img page="/pages/images/certificate.gif"
									altKey="archigest.archivo.deposito.electronico.usoFirma.conFirma" 
									titleKey="archigest.archivo.deposito.electronico.usoFirma.conFirma" 
									styleClass="imgTextMiddle" />
							</c:if>
						</display:column>
						<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="150" />
						<display:column titleKey="archigest.archivo.deposito.electronico.espacioTotal" style="width:100px">
							<fmt:formatNumber value="${deposito.espacioTotal / 1048576}" pattern="#,###.##"/>
							<bean:message key="archigest.archivo.megabyte"/>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.electronico.espacioOcupado" style="width:100px">
							<fmt:formatNumber value="${deposito.espacioOcupado / 1048576}" pattern="#,###.##"/>
							<bean:message key="archigest.archivo.megabyte"/>
							<c:if test="${!empty deposito.espacioTotal && deposito.espacioTotal>0}">
								(<fmt:formatNumber value="${(deposito.espacioOcupado / deposito.espacioTotal)*100}" pattern="###.##"/>
								<bean:message key="archigest.archivo.simbolo.porcentaje"/>)
							</c:if>
						</display:column>
						<display:column titleKey="archigest.archivo.deposito.electronico.numFicheros" style="width:100px">
							<fmt:formatNumber value="${deposito.numeroFicheros}" pattern="#,###"/>
						</display:column>
					</display:table>
	
					</html:form>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>