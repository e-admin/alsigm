<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-menu-el.tld" prefix="menu" %>

<div class="content_container">

<div class="ficha" style="width:250px;">

<h3><span>&nbsp;</span></h3>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">
	<div class="bloque_menu"> <%-- primer bloque de datos --%>
		<div class="separador8">&nbsp;</div>
		<div class="separador8">&nbsp;</div>
		<c:set var="topMenus" value="MenuFondos,MenuTransferencias,MenuDescripcion,MenuServicios,MenuDigitalizacion,MenuDocumentosVitales,MenuAdministracion" />
		<c:set var="menus" value="${sessionScope[appConstants.common.USER_AVAILABLE_MENUS]}" />
		<c:if test="${!empty menus}">
			<table cellpadding="0" cellspacing="0"><tr><td>
			<menu:useMenuDisplayer name="ExpandedClasifiers" config="resources.DropDownDisplayerStrings" bundle="org.apache.struts.action.MESSAGE">
				<c:forTokens items="${topMenus}" delims="," var="unMenu">
				<c:if test="${!empty menus[unMenu]}">
					<jsp:useBean id="unMenu" type="java.lang.String" />
					<menu:displayMenu name="<%=unMenu%>" target="basefrm"/>
				</c:if>
				</c:forTokens>
			</menu:useMenuDisplayer>
			</td></tr></table>
		</c:if>
		<div class="separador8">&nbsp;</div>
		<div class="separador8">&nbsp;</div>
	</div> 


</div> <%-- contenido --%>
</div> <%-- cuerpo --%>

<h2><span>&nbsp;</span></h2>

</div> <%-- ficha --%>
</div>
<script>
	<c:set var="menuKey" value="net.sf.navigator.menu.MENU_REPOSITORY" />
	<c:set var="menuRepository" value="${sessionScope[menuKey]}" />
	<c:choose>
		<c:when test="${empty menuRepository.selectedMenu}">
			selectedMenu = null;
		</c:when>
		<c:otherwise>
			selectedMenu = xGetElementById('<c:out value="${menuRepository.selectedMenu.parent.name}" />');
		</c:otherwise>
	</c:choose>
</script>