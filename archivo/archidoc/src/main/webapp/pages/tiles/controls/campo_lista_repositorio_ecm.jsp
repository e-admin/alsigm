<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld"
	prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="repositoriosEcm"
	value="${sessionScope[appConstants.fondos.REPOSITORIOS_ECM_KEY]}" />

<c:set var="nombreCampo" value="${requestScope['nombreCampo']}"/>

<c:choose>
	<c:when test="${not empty repositoriosEcm}">
		<html-el:select property="${nombreCampo}">
			<html-el:option value=""/>
			<html:optionsCollection name="repositoriosEcm" label="nombre" value="id" />
		</html-el:select>
	</c:when>
	<c:otherwise>
		<html-el:hidden property="${nombreCampo}" /><bean:message key="error.repositorios.ecm.no.configurados"/>
	</c:otherwise>
</c:choose>
