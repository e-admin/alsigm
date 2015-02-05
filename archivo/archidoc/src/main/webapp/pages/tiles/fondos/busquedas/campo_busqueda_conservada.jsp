<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.cf.conservada"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
					<html:select property="conservada">
						<html-el:option value=""></html-el:option>
						<html-el:option value="${appConstants.fondos.UDOC_CONSERVADA}"><bean:message key="archigest.archivo.si"/></html-el:option>
						<html-el:option value="${appConstants.fondos.UDOC_NO_CONSERVADA}"><bean:message key="archigest.archivo.no"/></html-el:option>
					</html:select>
	</td>
</tr>