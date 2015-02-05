<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<div id="navmenutitle">
	<bean:message key="title.docTemplates"/>
</div>

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_TEMPLATES_EDIT">
	<ul class="actionsMenuList">
		<li>
	  		<html:link action='/showCTTemplate.do?entityId=9&regId=-1'>
				<bean:message key="new.template"/>
	  		</html:link>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showCTTemplatesList.do'>

	<%-- BUSCADOR --%>
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="9"/>
	</tiles:insert>
	
	<%-- LISTADO DE PLANTILLAS --%>
	<display:table name="CTTemplatesList"
	               id="template"
	               export="true"
	               class="tableDisplay"
	               sort="list"
	               pagesize="45"
	               requestURI="/showCTTemplatesList.do">
		
		<logic:iterate name="CTTemplatesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
		
			<logic:equal name="format" property="fieldType" value="LINK">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'>
			  					
			  		<html:link action='<%=format.getUrl() + "?entity=9"%>'
			  						 styleClass='<%=format.getStyleClass()%>'
			  						 paramId='<%=format.getId()%>'
			  						 paramName="template"
			  						 paramProperty='<%=format.getPropertyLink() %>'>
						<%=format.formatProperty(template)%>
			  		</html:link>
			  </display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
					<%=format.formatProperty(template)%>
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="TEMPLATE_PROC_IMAGE">
				<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
							headerClass='<%=format.getHeaderClass()%>'
							class='<%=format.getColumnClass()%>'
							sortable='<%=format.getSortable()%>'
							decorator='<%=format.getDecorator()%>'>
					<% if ("true".equals(format.formatProperty(template))) { %>
						<bean:message key="bool.yes"/>
					<% } else { %>
						<bean:message key="bool.no"/>
					<% } %>
				</display:column>
			</logic:equal>
			
		</logic:iterate>
	</display:table>

</html:form>

