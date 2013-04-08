<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<html:errors />
<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
		
	
	function deleteEntity(url)
	{
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			var frm = document.forms[0];
			var url = '<%=request.getContextPath()%>' + '/showCTEntityToManage.do?method=deleteEntity';
		
		jConfirm('<bean:message key="entity.confirm.eliminarEntidad"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
			jConfirm('<bean:message key="entity.confirm.eliminarTablaFisicaEntidad"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){
				url = url + '&deleteTableBD=true';
				frm.action = url;
				frm.submit();
			}
			else{
				frm.action = url;
				frm.submit();
			}				
		});	
		
		}
							
		});	
		
		}

		
	}
	
</script>
<div id="navmenutitle"><bean:message key="title.entitieslist" /></div>
<div id="navSubMenuTitle"></div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showEntityWizard.do?method=init')">
				<bean:message key="new.entity" />
			</a>
		</li>
		<li>
			<a href="javascript:deleteEntity()">
				<bean:message key="delete.entity" />
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTEntitiesList.do'>

	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true"
		flush="false">
		<tiles:put name="suggestEntityId" value="1" />
		<tiles:put name="containsTextKey"
			value="catalog.search.entity.containsText" />
		<tiles:put name="suggestSqlQuery" value="TIPO=1" />
	</tiles:insert>

	<tiles:insert page="tiles/displaytagList.jsp" ignore="true"
		flush="false">
		<tiles:put name="ItemListAttr" value="CTEntitiesList" />
		<tiles:put name="ItemFormatterAttr" value="CTEntitiesListFormatter" />
		<tiles:put name="ItemActionAttr" value="/showCTEntitiesList.do" />
	</tiles:insert>

</html:form>
