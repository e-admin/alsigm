<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script language='JavaScript' type='text/javascript'><!--
	function deleteValues(url){
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}else {
			jConfirm('<bean:message key="hierarchicalTable.confirm.value.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {if(r){ispac_submit(url);}});
		}
	}
//--></script>


<html:form action="/hierarchicalTableManage.do">


	<display:table name="CTHierarchicalTableValueList" 
				   id='itemobj' 
				   export="true" 
				   class="tableDisplay"
				   sort="list" 
				   requestURI="/hierarchicalTableManage.do?method=valuelist"
				   defaultsort="1">
				   
	<logic:present name="itemobj">
	
		<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
	
		 <logic:iterate name="CTHierarchicalTableValueListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
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