<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:url var="cancelURI" value="/action/gestionTareasDigitalizacion">
	<c:param name="method" value="goReturnPoint"  />
</c:url>
<a href="<c:out value='${cancelURI}' escapeXml='false'/>" target="_self" id="temp" name="temp">a</a>
<script>
window.location= '<c:out value="${cancelURI}" escapeXml="false"/>';
//document.getElementById('temp').click();
</script>
