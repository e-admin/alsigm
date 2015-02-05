<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<html:errors/>
<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
	function deleteHierarchicalTable(url){
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>' ,'<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			var frm = document.forms[0];
			var url = '<%=request.getContextPath()%>' + '/hierarchicalTableManage.do?method=delete';
		
			jConfirm('<bean:message key="hierarchicalTable.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
				if(r){
					jConfirm('<bean:message key="entity.confirm.eliminarTablaFisicaEntidad"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(res) {
					if(res){
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

<div id="navmenutitle">
	<bean:message key="title.hierarchicaltableslist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_COMP_HIERARCHICAL_TABLES_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/hierarchicalTableManage.do?method=init')">
				<bean:message key="new.hierarchicaltable"/>
			</a>
		</li>
		<li>
			<a href="javascript:deleteHierarchicalTable()">
				<bean:message key="delete.hierarchicaltable"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTHierarchicalTablesList.do'>

	<display:table name="CTHierarchicalTablesList" 
				   id='itemobj' 
				   export="true" 
				   class="tableDisplay"
				   sort="list" 
				   requestURI="/showCTHierarchicalTablesList.do"
				   defaultsort="1">
				   
	<logic:present name="itemobj">
	
		<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
	
		 <logic:iterate name="CTHierarchicalTablesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
		 
			<logic:equal name="format" property="fieldType" value="CHECKBOX">
				<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>' 
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								style="text-align:center;width:30px;">
					<html:multibox property="multibox">
						<%=format.formatProperty(item)%>
					</html:multibox>
				</display:column>
		  	</logic:equal>
		  	
			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'><%=format.formatProperty(item)%></display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'>
			  					
			  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

						<%=format.formatProperty(item)%>
	
			  		</html:link>
			  	</display:column>
			 </logic:equal>
				
			<logic:equal name="format" property="fieldType" value="ID_ENTIDAD">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					
					<ispac:lookUp name="EntitiesMap" key='<%=(String)format.formatProperty(item)%>' property="ETIQUETA" />
					
				</display:column>
			</logic:equal>
	
		</logic:iterate>
	
	</logic:present>
	
	</display:table>
	<!-- displayTag -->

</html:form>
