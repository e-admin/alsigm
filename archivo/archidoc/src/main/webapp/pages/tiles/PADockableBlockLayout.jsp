<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<div class="separador1">&nbsp;</div>

<c:set var="dockableContentVisibleValue"><tiles:insert attribute="dockableContentVisible" ignore="true" /></c:set>
<c:set var="dockableContentVisible" value="${dockableContentVisibleValue}" />
<c:set var="altKey" value="archigest.archivo.infoAmpliada" />
<c:choose>
	<c:when test="${dockableContentVisible}">
		<c:set var="displayValue" value="block" />
		<c:set var="displayerImg" value="../images/up.gif" />
	</c:when>
	<c:otherwise>
		<c:set var="displayValue" value="none" />
		<c:set var="displayerImg" value="../images/down.gif" />
	</c:otherwise>
</c:choose>

<div class="cabecero_bloque" style="width:100%;-moz-box-sizing: border-box">
<table class="w98m1" cellpadding=0 cellspacing=0 height="100%">
  <tr>
    <td class="etiquetaAzul12Bold">
		<c:set var="blockTitle"><tiles:insert attribute="blockTitle" ignore="true" /></c:set>
		<c:out value="${blockTitle}" />
	</td>
    <td nowrap align="right">
		<tiles:insert attribute="buttonBar" ignore="true" />
	</td>
	<td align="right" width="30px">
	<a href="javascript:switchDivVisibility('<tiles:insert attribute="blockName" />');">
		<img id="img<tiles:insert attribute='blockName'/>" 
			src="<c:out value='${displayerImg}' escapeXml='false'/>" 
			alt="<fmt:message key='${altKey}'/>" 
			title="<fmt:message key='${altKey}'/>"
			class="imgTextMiddle" />
	</a>
	</td>
  </tr></table>
</div> <%-- cabecero bloque --%>

<c:set var="visibleContent"><tiles:insert attribute="visibleContent" ignore="true" /></c:set>
<c:if test="${!empty visibleContent}">
<div class="bloque" style="width:100%;-moz-box-sizing: border-box; margin-top:-1px;"> 
	<c:out value="${visibleContent}" escapeXml="false" />
</div> 
</c:if>

<div class="bloque" <c:if test="${dockableContentVisible}">isOpen="true"</c:if> id="div<tiles:insert attribute="blockName" />"  style="width:100%;-moz-box-sizing: border-box;display:<c:out value="${displayValue}" />; margin-top:-1px;">
	<tiles:insert attribute="dockableContent" ignore="true" />
</div>