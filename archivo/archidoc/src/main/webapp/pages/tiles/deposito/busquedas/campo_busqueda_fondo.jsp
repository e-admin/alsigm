<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fondos" value="${sessionScope[appConstants.deposito.LISTADO_FONDOS_BUSQUEDA]}"/>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.fondo"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<html:select property="fondo" styleId="fondo">
			<html:option key="archigest.archivo.busqueda.form.value.todos" value=""/>
			<html:optionsCollection name="fondos" label="label" value="id"/>
		</html:select>
		&nbsp;
	</td>
</tr>
