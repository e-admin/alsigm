<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


function restringuePrivilegios()
{
	<c:if test="${not user.isSystemXSuperUser}">
		disable(eval(system)); // Deshabilita la casilla de sistema, si no es SYS_XSUPERUSER		
		disable(eval(volume)); // Deshabilita la casilla de volume, si no es SYS_XSUPERUSER		
		<c:choose> <%-- Privilegios sobre el producto IUSER --%>
			<c:when test="${sessionScope.user.userProfile == 1}"> <%-- Es IUSER_STANDARD, luego solo puede consultar el administrador de usuarios --%>
				// No en ldap disableUserGeneral(); // Deshabilito la pestaña de general
				for (i = 0; i < user.length; i ++ )
					disable(eval(user[i])); // y las del producto Administrador de usuarios
			</c:when>

			<c:when test="${sessionScope.user.userProfile eq 3}"> <%-- Es IUSER_SUPERUSER --%>
				<c:if test="${sessionScope.user.userProfile > requestScope.userDTO.userProfile }">
					disable(eval(user[0]) ); // IUSER_SUPERUSER no puede dar su mismo nivel de privilegio a otros usuarios
				</c:if>	
				<c:if test="${requestScope.userDTO.userProfile == sessionScope.user.userProfile}">
					for (i = 0; i < user.length; i ++ )
						disable(eval(user[i])); // IUSER_SUPERUSER no puede modificar a otro IUSER_SUPERUSER 
				</c:if>
			</c:when>		
		</c:choose>
		
		<c:choose> <%-- Privilegios sobre el producto IDOC --%>
			<c:when test="${sessionScope.user.idocProfile == 1}"> <%-- Es IDOC_STANDARD --%>
				disablePermission(); // Los IDOC_STANDARD solo pueden ver
				for (i = 0; i < idoc.length; i++)
					disable(eval(idoc[i]));						
			</c:when>
			
			<c:when test="${sessionScope.user.idocProfile == 3}"> <%-- Es IDOC_SUPERUSER --%>
				<c:if test="${sessionScope.user.idocProfile > requestScope.userDTO.idocProfile }">
					disable(eval(idoc[0]) ); // IDOC_SUPERUSER no puede dar su mismo nivel de privilegio a otros usuarios
				</c:if>	
				<c:if test="${requestScope.userDTO.idocProfile == sessionScope.user.idocProfile}">
					for (i = 0; i < idoc.length; i ++ )
						disable(eval(idoc[i])); // IDOC_SUPERUSER no puede modificar a otro IUSER_SUPERUSER 
				</c:if>
			</c:when>
		</c:choose>
	</c:if>
}