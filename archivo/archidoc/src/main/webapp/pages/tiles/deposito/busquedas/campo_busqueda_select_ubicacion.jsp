<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.ubicacion"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<c:set var="ubicaciones" value="${sessionScope[appConstants.deposito.LISTA_UBICACIONES_KEY]}" />
		<html:select property="idUbicacion" styleId="idUbicacion">
			<html:option key="archigest.archivo.todas" value=""/>
			<html:options collection="ubicaciones" property="key" labelProperty="value" />
		</html:select>
	</td>
</tr>
