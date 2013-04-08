<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="action" value="goBack" />
<c:set var="imgIcon" value="/pages/images/close.gif" />
<c:set var="labelKey" value="archigest.archivo.cerrar" />

<tiles:importAttribute name="action" ignore="true" />
<tiles:importAttribute name="imgIcon" ignore="true" />
<tiles:importAttribute name="labelKey" ignore="true" />

<archivo:closeButton action="${action}" imgIcon="${imgIcon}" labelKey="${labelKey}" titleKey="${labelKey}" />