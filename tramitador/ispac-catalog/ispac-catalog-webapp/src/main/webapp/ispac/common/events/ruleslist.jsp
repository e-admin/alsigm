<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>
<bean:define name="codEvent" id="codEvent"/>

<div id="navmenutitle">
	<bean:message key="select.rule.caption"/>
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<% String URL="showPEvents.do?TpObj=" + TpObj + "&IdObj=" + IdObj;%>
			<html:link action='<%=URL%>'><bean:message key="forms.button.cancel"/></html:link>
		</li>
	</ul>
</div>

<div id="bodycontainer" class="tableborder">

	<display:table name="RulesList" id="item" export="false" class="tableDisplay"
  		sort="list" requestURI='' style="width:95%;">

		<logic:iterate name="RulesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="LINK">
			
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>
					
					<center>
						<html:link action='<%=format.getUrl() + "?TpObj=" + TpObj + "&IdObj=" + IdObj + "&codEvent=" + codEvent%>' styleClass='<%=format.getStyleClass()%>'
							paramId='<%=format.getId()%>' paramName="item" paramProperty='<%=format.getPropertyLink() %>'>
							
							<bean:message key='<%=format.getPropertyValueKey()%>' />
							
			  			</html:link>
			  		</center>
			  			
				</display:column>
				
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="DATA">
			
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
						 		headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>
								
					<nobr><%=format.formatProperty(item)%></nobr>
					
				</display:column>
				
			</logic:equal>

		</logic:iterate>
		
	</display:table>
	
</div>