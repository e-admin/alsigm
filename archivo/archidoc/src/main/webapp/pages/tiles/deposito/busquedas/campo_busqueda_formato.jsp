<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.deposito.formato"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<c:set var="formatos" value="${sessionScope[appConstants.deposito.LISTA_FORMATOS]}" />
		<html:select property="formato">
			<html:option key="archigest.archivo.todos" value=""/>
			<html:options collection="formatos" labelProperty="nombre" property="id" />
		</html:select>
	</td>
</tr>
