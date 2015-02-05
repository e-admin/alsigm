<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="vUsuario" value="${sessionScope[appConstants.controlAcceso.USUARIO_VER_USUARIO]}"/>

<div class="separador8">&nbsp;</div>
<div class="cabecero_bloque">
	<table class="w98m1" cellpadding="0" cellspacing="2">
		<tr>
			<td width="70%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.usuario"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<c:out value="${vUsuario.nombreCompleto}"/>
				</span>
			</td>
			<td width="30%" class="etiquetaAzul11Bold">
				<bean:message key="archigest.archivo.tipoUsuario"/>:&nbsp;
				<span class="etiquetaNegra12Normal">
					<fmt:message key="archigest.archivo.cacceso.tipoUsuario.${vUsuario.tipo}"/>
				</span>
			</td>
		</tr>
	</table>
</div>
<div class="separador8">&nbsp;</div>
