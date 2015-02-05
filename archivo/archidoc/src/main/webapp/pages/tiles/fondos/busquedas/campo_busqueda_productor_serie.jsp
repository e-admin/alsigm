<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="mostrarSinProductor" value="${sessionScope[appConstants.fondos.MOSTRAR_SIN_PRODUCTOR]}"/>
<c:set var="listaProductores" value="${sessionScope[appConstants.fondos.LISTA_PRODUCTORES_KEY]}"/>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>"><bean:message key="archigest.archivo.busqueda.form.productor"/>:&nbsp;</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<html:select property="productores" styleId="productores">
		   <html:option value=""><bean:message key="archigest.archivo.udoc.productor.todos"/></html:option>
		   <html:optionsCollection name="listaProductores" label="value" value="key"/>
	    </html:select>
	</td>
</tr>
