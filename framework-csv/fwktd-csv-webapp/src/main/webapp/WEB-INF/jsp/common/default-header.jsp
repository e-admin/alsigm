<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div id="logos" class="threecol">
	<div class="column first">
	</div>
	<div class="column">
	</div>
	<div class="column last">
		<img alt="logo" src="img/inicio-nombre.png">
		<c:set var="logoutUrl" value="${configProperties['fwktd-csv-webapp.logout.url']}" />
		<c:if test="${!empty logoutUrl}">
		<spring:url value="${logoutUrl}" var="url" />
		<a class="salir" id="logout" href="${url}" title='<spring:message code="label.app.logout"/>'>
			<span>
				<spring:message code="label.salir"/>
			</span>
		</a>
		</c:if>
	</div>
</div>
