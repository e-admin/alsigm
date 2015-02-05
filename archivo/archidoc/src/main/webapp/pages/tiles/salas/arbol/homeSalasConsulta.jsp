<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<script>
	window.parent.showTreeView('<c:out value="${showTreeViewURL}" escapeXml="false"/>');
	window.parent.showTreeContent('<c:out value="${showContentURL}" escapeXml="false"/>');
</script>

