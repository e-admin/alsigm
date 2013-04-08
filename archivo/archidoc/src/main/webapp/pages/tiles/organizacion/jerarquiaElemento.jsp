<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<c:set var="jerarquiaElementoOrg" value="${sessionScope[appConstants.organizacion.JERARQUIA_ELEMENTO_ORGANIZACION]}" />

<table cellpadding="0" cellspacing="0">
<c:forEach var="aAncestor" items="${jerarquiaElementoOrg}" varStatus="nivel">
	<tr>
		 <td style="vertical-align:top;">
			<span style="padding-left:<c:out value="${(nivel.count-1)*10}px"/>;" class="user_data">
			    <c:choose>
					<c:when test="${nivel.last}">
						<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
				    </c:when>
					<c:when test="${nivel.first}">
						<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="6px"/>
						<html:img page="/pages/images/padre.gif" styleClass="imgTextMiddle"/>
				    </c:when>
					<c:otherwise>
						<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
					</c:otherwise>
				</c:choose>
				<c:out value="${aAncestor.codigo}" />&nbsp;<c:out value="${aAncestor.nombre}" />
			</span>
		</td>
	 </tr>
</c:forEach>
</table>