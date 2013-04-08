<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="regId" id="regId"/>
<bean:define name="entityId" id="entityId"/>

<div class="tabButtons">
	&nbsp;
</div>

<div class="tabContent">

	<c:url value="showPcdGraph.do" var="_showPcdGraph">
		<c:param name="pcdId">
			<c:out value="${regId}"/>
		</c:param>
	</c:url>

	<object type="image/svg+xml" width="100%" height="100%"
			data='<c:out value="${_showPcdGraph}"/>'>
		<bean:message key="procedure.graph.plugin.not.found"/>
	</object>
</div>
