<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:parameter id="typedocument" name="type"/>

<script>
	function showTemplates()
	{
		var url = '<%=request.getContextPath()%>' + '/showTemplatesList.do?type=' + '<%=typedocument%>';
		document.forms[0].action = url;
	  document.forms[0].submit();
	}

	function newTemplate()
	{
		var url = '<%=request.getContextPath()%>' + '/showTemplate.do?tipo=generica&entityId=<bean:write name="defaultForm" property="entity"/>&regId=-1&type=' + '<%=typedocument%>';

		document.forms[0].action = url;
	  document.forms[0].submit();
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.docTemplates"/>
</div>

<!-- BREAD CRUMBS -->
	<tiles:insert page="tiles/breadCrumbsTile.jsp" ignore="true" flush="false"/>
<!-- ------------ -->

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_DOCTYPES_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:newTemplate()">
				<bean:message key="new.template"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showTemplatesList.do'>

	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >
		<tiles:put name="suggestStrutsAction" value="/ajaxSearchTemplateSuggest.do"/>
		<tiles:put name="suggestEntityId" value='<%=typedocument%>'/>
	</tiles:insert>

	  <%
	  	String URI = request.getContextPath() + "/showTemplatesList.do?type=" + typedocument;
	  %>
		<display:table name="TemplatesList"
									 id="template"
									 export="true"
									 class="tableDisplay"
									 sort="list"
									 pagesize="45"
									 requestURI='<%=URI%>'>

			<logic:iterate name="TemplatesListFormatter"
										 id="format"
										 type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				<logic:equal name="format" property="fieldType" value="CHECKBOX">
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>' 
									sortable='<%=format.getSortable()%>' 
									class='<%=format.getColumnClass()%>'
									decorator='<%=format.getDecorator()%>'>
						<html:multibox property="multibox">
												<%=format.formatProperty(template)%>
						</html:multibox>
					</display:column>
			  </logic:equal>
				<logic:equal name="format" property="fieldType" value="LINK">
					<%
						String action = format.getUrl() + "?entity=9&type=" + typedocument;
					%>
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'>
				  					
				  	<html:link action='<%=action%>'
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
									decorator='<%=format.getDecorator()%>'>
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

	<html:hidden property="type" value='<%=typedocument%>' />
</html:form>

