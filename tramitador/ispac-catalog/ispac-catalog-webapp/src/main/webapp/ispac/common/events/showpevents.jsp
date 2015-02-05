<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>

<div id="navmenutitle">
	<bean:write name="Title" />
</div>
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<% String URL="addPEvents.do?TpObj=" + TpObj + "&IdObj=" + IdObj;%>
			<html:link action='<%=URL%>'><bean:message key="procedure.tabs.events.add"/></html:link>
		</li>
	</ul>
</div>

<div id="bodycontainer">

		<display:table name="EventRulesList" id="eventrule" export="false" class="tableDisplay"
	  		sort="list" requestURI='<%= request.getContextPath()+"/showPEvents.do" %>'>

			<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="EVENT">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'
									group='1'>
						<%=format.formatProperty(eventrule)%>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="ADDRULE">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'
									group='3' >
							<html:link action='<%=format.getUrl() + "?TpObj=" + TpObj + "&IdObj=" + IdObj%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="eventrule"
				  			paramProperty='<%=format.getPropertyLink() %>'>
					  			<nobr>
								<%=format.formatProperty(eventrule)%>
				  				</nobr>
				  			</html:link>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="DELEVENT">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'
									group='2' >
							<html:link action='<%=format.getUrl() + "?TpObj=" + TpObj + "&IdObj=" + IdObj%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="eventrule"
				  			paramProperty='<%=format.getPropertyLink() %>'>
					  			<nobr>
									<%=format.formatProperty(eventrule)%>
						  		</nobr>
				  			</html:link>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="RULE">
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>
							<%=format.formatProperty(eventrule)%>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="MODORDER">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>

							<bean:define name="eventrule" id="event" property="property(EVENTO)"/>
							<bean:define name="eventrule" id="orden" property="property(ORDEN)"/>

							<center>
								<html:link
									action='<%=format.getUrl() + "?ModOrden=INC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&EventCod=" + event + "&Order=" + orden%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="eventrule" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
								<html:link
									action='<%=format.getUrl() + "?ModOrden=DEC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&EventCod=" + event + "&Order=" + orden%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="eventrule" paramProperty='<%=format.getPropertyLink() %>'>-</html:link>
							</center>
					</display:column>
				</logic:equal>
				<logic:equal name="format" property="fieldType" value="DELRULE">

					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>

							<bean:define id="delevent" name="eventrule" property="property(EVENTO)"/>
							<bean:define id="delorder" name="eventrule" property="property(ORDEN)"/>

							<center>
								<html:link action='<%=format.getUrl() + "?TpObj=" + TpObj + "&IdObj=" + IdObj +
														"&delEvent=" + delevent+"&delOrder=" + delorder%>'
										   styleClass='<%=format.getStyleClass()%>'
										   paramId='<%=format.getId()%>' paramName="eventrule" paramProperty='<%=format.getPropertyLink() %>'>
									<nobr>
										<%=format.formatProperty(eventrule)%>
									</nobr>
					  			</html:link>
				  			</center>
					</display:column>
				</logic:equal>
			</logic:iterate>
		</display:table>

</div>
