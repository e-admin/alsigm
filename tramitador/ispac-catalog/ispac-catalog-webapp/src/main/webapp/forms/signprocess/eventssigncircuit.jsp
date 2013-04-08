<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>
<bean:define name="entityId" id="entityId"/>


<div id="navmenutitle">
	<bean:message key="form.signProcess.mainTitle"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">

	<ul class="actionsMenuList">
		<ispac:hasFunction functions="FUNC_INV_SIGN_CIRCUITS_EDIT">
		<li>
			<ispac:linkframe id="EVENTMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%="events.do?method=events&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId%>'
					 titleKey="procedure.tabs.events.add"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
		</li>
		</ispac:hasFunction>

		<li>
			<a href='<%=request.getContextPath() + "/showCTEntity.do?entityId="+entityId+"&regId="+IdObj+""%>'>
				<nobr><bean:message key="forms.button.cancel"/></nobr>
			</a>
		</li>
	</ul>
</div>

	<display:table name="EventRulesList" 
			id="eventrule" 
			export="false" 
			class="tableDisplay"
	  		sort="list">

		<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="EVENT">
			
				<display:column titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								group='1'>
					
					<bean:message key='<%=(String)format.formatProperty(eventrule)%>'/>
					
				</display:column>
				
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="ADDRULE">
			
				<display:column titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								group='3'>
								
					<bean:define id="eventCode" name="eventrule" property="property(EVENTO)"/>
					
					<center>
						<ispac:linkframe id="EVENTMANAGER"
								 styleClass='<%=format.getStyleClass()%>'
							     target="workframe"
								 action='<%="events.do?method=rules&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&codEvent=" + eventCode%>'
								 titleKey="events.addRules"
								 width="640"
								 height="480"
								 showFrame="true">
						</ispac:linkframe>
					</center>
					
				</display:column>
				
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="DELEVENT">
			
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								group='2'>
						
		  			<bean:define id="delEvent" name="eventrule" property="property(EVENTO)"/>
		  			
		  			<c:url var="eliminateEvent" value="/eventsSingCircuit.do">
		  				<c:param name="method" value="delEvent"/>
						<c:param name="TpObj" value="${TpObj}"/>
						<c:param name="IdObj" value="${IdObj}"/>
						<c:param name="entityId" value="${entityId}"/>	
						<c:param name="delEvent" value="${delEvent}"/>							
					</c:url>	
						
					<center>											
						<a href = "javascript:messageConfirm('<c:out value="${eliminateEvent}" escapeXml="false"/>','<bean:message key="events.eliminateMessage.confirm"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>');" class='<%=format.getStyleClass()%>'/>								  
			  		    	<nobr><bean:message key='<%=format.getPropertyValueKey()%>'/></nobr>	
			  		   	</a>
			  		</center>
			  		
				</display:column>
				
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="RULE">
			
				<display:column titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>
								
					<%=format.formatProperty(eventrule)%>
					
				</display:column>
				
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="MODORDER">
			
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>

					<bean:define name="eventrule" id="event" property="property(EVENTO)"/>
					<bean:define name="eventrule" id="orden" property="property(ORDEN)"/>

					<center>
						<html:link
							action='<%=format.getUrl() + "&ModOrden=INC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&EventCod=" + event + "&Order=" + orden%>'
							styleClass="aOrderButton" paramId='<%=format.getId()%>'
							paramName="eventrule" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
						<html:link
							action='<%=format.getUrl() + "&ModOrden=DEC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&EventCod=" + event + "&Order=" + orden%>'
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
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'>

		  			<bean:define id="delEvent" name="eventrule"  property="property(EVENTO)"/>
		  			<bean:define id="delOrder" name="eventrule" property="property(ORDEN)"/>
		  			<bean:define id="delRule" name="eventrule" property="property(ID_REGLA)"/>
		  			
		  			<c:url var="eliminateRule" value="/eventsSingCircuit.do">
		  				<c:param name="method" value="delEventRule"/>				  				
						<c:param name="TpObj" value="${TpObj}"/>
						<c:param name="IdObj" value="${IdObj}"/>
						<c:param name="entityId" value="${entityId}"/>									
						<c:param name="delEvent" value="${delEvent}"/>		
						<c:param name="delOrder" value="${delOrder}"/>	
						<c:param name="delRule" value="${delRule}"/>			
					</c:url>	
						
					<center>											
						<a href = "javascript:messageConfirm('<c:out value="${eliminateRule}" escapeXml="false"/>','<bean:message key="rules.eliminateMessage.confirm"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>');" class='<%=format.getStyleClass()%>'/>								  
			  		    	<nobr><bean:message key='<%=format.getPropertyValueKey()%>'/></nobr>	
			  		   	</a>
			  		</center>							

				</display:column>
				
			</logic:equal>
			
		</logic:iterate>
		
	</display:table>

