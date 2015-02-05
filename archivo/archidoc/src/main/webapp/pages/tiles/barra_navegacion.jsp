<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>

<div id="barra_miga_pan">
	<div class="barra_titulo" >
	<c:forEach var="node" items="${sessionScope[appConstants.common.INVOCATION_STACK_KEY].navigationToolBar}" varStatus="status">
	<c:if test="${!status.last}">
		<c:if test="${node.linkable}">
		<a target="<c:out value='${node.target}' escapeXml='false'/>"
		href="<c:url value="${node.uri}"/>" class="barra_nav">
		</c:if>
	</c:if>

		<span style="font-size:12">
		<c:set var="title"><fmt:message key="${node.resourceMessage}"/></c:set>
		<c:choose>
			<c:when test="${!empty title}">
				<fmt:message key="${node.resourceMessage}"/>
			</c:when>
			<c:otherwise>
				<c:out value ="${node.resourceMessage}"/>
			</c:otherwise>
		</c:choose>
		</span>
	<c:if test="${!status.last}">
		</a>&nbsp;&gt;&nbsp;
	</c:if>
	</c:forEach>
	</div>
</div>
