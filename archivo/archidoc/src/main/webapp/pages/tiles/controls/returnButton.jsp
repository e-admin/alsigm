<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="action" value="goBack" />
<c:set var="imgIcon" value="/pages/images/close.gif" />
<c:set var="labelKey" value="archigest.archivo.cerrar" />
<c:set var="posFormulario" value="0"/>

<tiles:importAttribute name="action" ignore="true" />
<tiles:importAttribute name="posFormulario" ignore="true"/>
<tiles:importAttribute name="imgIcon" ignore="true" />
<tiles:importAttribute name="labelKey" ignore="true" />

<script language="javascript">
	function returnButton() {
		var form = document.forms[<c:out value="${posFormulario}" />];
		form.method.value="<c:out value="${action}" />";
		form.submit();
	}
</script>

<a class=etiquetaAzul12Bold href="javascript:returnButton()">
	<html-el:img page="${imgIcon}" border="0" altKey="${labelKey}" titleKey="${labelKey}" styleClass="imgTextBottom" />
	&nbsp;<fmt:message key="${labelKey}" />
</a>
