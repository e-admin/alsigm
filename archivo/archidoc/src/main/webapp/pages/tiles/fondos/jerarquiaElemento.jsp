<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<c:set var="jerarquiaElementoCF" value="${requestScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}" />
<c:if test="${empty jerarquiaElementoCF}">
	<c:set var="jerarquiaElementoCF" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}" />
</c:if>
<table cellpadding="0" cellspacing="0">
<c:forEach var="aAncestor" items="${jerarquiaElementoCF}" varStatus="nivel">
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
				<c:if test="${aAncestor.codigo != null && aAncestor.codigo!= ''}">
					<b><c:out value="${aAncestor.codigo}" />&nbsp;</b>
				</c:if>
				<c:out value="${aAncestor.titulo}" />
			</span>
		</td>
	 </tr>
</c:forEach>
</table>