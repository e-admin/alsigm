<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.tpdocslist"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu" >
	<ispac:hasFunction functions="FUNC_INV_DOCTYPES_EDIT">
	<ul class="actionsMenuList">
       	<li>
			<a href="javascript:redirect('/showCTEntity.do?entityId=7&regId=-1')">
				<bean:message key="new.formtype"/>
			</a>
	    </li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTTPDocsList.do'>

	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="7"/>
	</tiles:insert>
	
	<!-- LISTADO DE TIPOS DE DOCUMENTOS -->
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTTPDocsList"/>
		<tiles:put name="ItemFormatterAttr" value="CTTPDocsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTTPDocsList.do"/>
	</tiles:insert>
</html:form>

