<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script language="javascript">


function limitUserPrivilege()
{
// <c:out value="mgrDeptId: ${param.mgrDeptId}"/>

	<c:if test="${not user.isSystemXSuperUser && not user.isSystemSuperUser}"> <%-- Perfil de usuario Normal --%>
		disable(eval(system)); // Deshabilita la casilla de sistema, si no es SYS_XSUPERUSER || SYS_SUPERUSER
		disable(eval(volume)); // Deshabilita la casilla de volume, si no es SYS_XSUPERUSER	|| SYS_SUPERUSER	
		<c:choose> <%-- Privilegios sobre el producto IUSER --%>
			<c:when test="${sessionScope.user.userProfile == 1}"> <%-- Es IUSER_STANDARD, luego solo puede consultar el administrador de usuarios --%>
				disableUserGeneral(); // Deshabilito la pestaña de general
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
		<c:if test="${param.method != 'ldap'}"> <%-- Si es LDAP el perfil IUSER_MANAGER no existe --%>
			<c:if test="${sessionScope.user.userProfile == 2}"> <%-- Es IUSER_MANAGER --%>
				<c:choose>
					<c:when test="${param.mgrDeptId eq sessionScope.user.id}"> <%-- Es manager Del Dtpo --%>
						// Es manager Del Dtpo al q pertenece el usuario
						<c:choose>
							<c:when test="${requestScope.userDTO.userProfile > sessionScope.user.userProfile}">
								for (i = 0; i < user.length; i ++ )
									disable(eval(user[i])); // IUSER_MANAGER no puede modificar a un IUSER_SUPERUSER
							</c:when>
							<c:otherwise>
								disable(eval(user[0]) ); // IUSER_MANAGER no puede dar privilegio de IUSER_SUPERUSER a otros usuarios, pero si de IUSER_MANAGER 								
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise> <%-- No es manager del Dpto, luego tiene los mismos privilegios que un IUSER_STANDARD --%>
						disableUserGeneral(); // Deshabilito la pestaña de general
							for (i = 0; i < user.length; i ++ )
						disable(eval(user[i])); // y las del producto Administrador de usuarios
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
		
		<c:choose> <%-- Privilegios sobre el producto IDOC --%>
			<c:when test="${sessionScope.user.idocProfile == 1}"> <%-- Es IDOC_STANDARD --%>
				disablePermission(); // Los IDOC_STANDARD solo pueden ver
				for (i = 0; i < idoc.length; i++)
					disable(eval(idoc[i]));						
			</c:when>
			<c:when test="${sessionScope.user.idocProfile == 2}"> <%-- Es IDOC_MANAGER --%>
				<c:choose>
					<c:when test="${param.mgrDeptId eq sessionScope.user.id}"> <%-- Es manager Del Dtpo --%>
						<c:choose>
							<c:when test="${requestScope.userDTO.idocProfile > sessionScope.user.idocProfile}">
								for (i = 0; i < idoc.length; i ++ )
									disable(eval(idoc[i])); // IDOC_MANAGER no puede modificar a un IDOC_SUPERUSER
							</c:when>
							<c:otherwise>
								disable(eval(idoc[0]) ); // IDOC_MANAGER no puede dar privilegio de IDOC_SUPERUSER a otros usuarios, pero si de IDOC_MANAGER 								
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise> <%-- No es manager del Dpto, luego tiene los mismos privilegios que un IDOC_STANDARD --%>
						disablePermission(); 
						for (i = 0; i < idoc.length; i++)
							disable(eval(idoc[i]));		
					</c:otherwise>
				</c:choose>
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
	
	<c:if test="${user.isSystemSuperUser}">
		<%-- 
			Si el user connected es SYSTEM_SUPERUSER puede hacerlo todo menos asignar otros usuarios "system" 
			y modificar a otros SYSTEM_SUPERUSER
		--%>
		disable(eval(system)); // No puede asignar system a nadie
		<c:if test="${requestScope.userDTO.isSystemSuperUser}" >
			// No puede modificar a otro  SYSTEM_SUPERUSER
			disable(eval(volume));
			for (i = 0; i < idoc.length; i++)
				disable(eval(idoc[i]));
			for (i = 0; i < user.length; i ++ )
				disable(eval(user[i])); 
			disablePermission();
		</c:if>
	</c:if>

}
</script>


