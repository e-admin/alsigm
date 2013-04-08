<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mapping" mapping="/gestionOrganos" />

<c:set var="organos" value="${requestScope[appConstants.controlAcceso.LISTA_ORGANOS]}" />

<html:form action="/gestionOrganos" styleId="formulario">
<input type="hidden" name="method" id="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.busquedaOrganos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					form.submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="120px">
							<bean:message key="archigest.archivo.codigo"/>:
						</td>
						<td class="tdDatos">
							<html:text property="codigo" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.nombre"/>:
						</td>
						<td class="tdDatos">
							<html:text property="nombre" styleClass="input80"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="organizacion.org.estado.vigente"/>:
						</td>
						<td class="tdDatos">
							<html:select property="vigente" styleId="vigente">
								<html:option value="S"><bean:message key="archigest.archivo.si"/></html:option>
								<html:option value="N"><bean:message key="archigest.archivo.no"/></html:option>
								<html:option value=""><bean:message key="archigest.archivo.todos"/></html:option>
							</html:select>
						</td>
					</tr>
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${organos != null}">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<display:table name="pageScope.organos"
					id="organo"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI="../../action/gestionOrganos"
					pagesize="10">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.cacceso.busquedaOrganosSinResultado"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" style="width:150">
					<c:out value="${organo.codigo}"> -- </c:out>
				</display:column>
				<display:column titleKey="archigest.archivo.organo" sortProperty="nombre" sortable="true" headerClass="sortable">
					<c:url var="verURL" value="/action/gestionOrganos">
						<c:param name="method" value="infoOrgano" />
						<c:param name="idOrgano" value="${organo.idOrg}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${organo.nombreLargo}"> -- </c:out>
					</a>
				</display:column>
				<display:column titleKey="organizacion.org.estado.vigente" sortProperty="vigente" sortable="true" headerClass="sortable" style="width:30px;text-align:center">
				  <c:choose>
				    <c:when test="${organo.organoVigente}">
				    <html:img page="/pages/images/checkbox-yes.gif"
				        altKey="archigest.archivo.si"
  				        titleKey="archigest.archivo.si"
				        styleClass="imgTextMiddle"/>
				    </c:when>
				    <c:otherwise>
				      <html:img page="/pages/images/checkbox-no.gif"
				        altKey="archigest.archivo.no"
  				        titleKey="archigest.archivo.no"
				        styleClass="imgTextMiddle"/>
				    </c:otherwise>
				    </c:choose>
				</display:column>

			</display:table>
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>