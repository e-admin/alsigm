<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define name="TpObj" id="TpObj"/>
<bean:define name="IdObj" id="IdObj"/>
<bean:define name="entityId" id="entityId"/>
<bean:define name="label" id="label"/>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script>
	
	
	$(document).ready(function(){
		$("#move").draggable();
	});
	
</script>
<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="events.obj.entity"/>
		<logic:notEmpty name="label">
			'<bean:write name="label"/>'
		</logic:notEmpty>
		</h4>
		<div class="acciones_ficha">
			<a class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</div>
	</div>
</div>

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="actionsMenuList">
		<li>
			<ispac:linkframe id="EVENTMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%="events.do?method=events&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&label=" + label%>'
					 titleKey="procedure.tabs.events.add"
					 width="550"
					 height="400"
					 showFrame="true">
			</ispac:linkframe>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<div id="bodycontainer" class="tableborder">

	<display:table name="EventRulesList" 
			id="eventrule" 
			export="false" 
			class="tableDisplay"
	  		sort="list" requestURI='' style="width:95%;">

		<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="EVENT">
			
				<display:column titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>' 
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								group='1'>
					
					<bean:message key='<%=(String)format.formatProperty(eventrule)%>' />
					
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
								 action='<%="events.do?method=rules&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&codEvent=" + eventCode + "&label=" + label%>'
								 titleKey="events.addRules"
								 width="550"
								 height="400"
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
		  			
		  			<c:url var="eliminateEvent" value="/events.do">
		  				<c:param name="method" value="delEvent"/>
						<c:param name="TpObj" value="${TpObj}"/>
						<c:param name="IdObj" value="${IdObj}"/>
						<c:param name="entityId" value="${entityId}"/>	
						<c:param name="delEvent" value="${delEvent}"/>
						<c:param name="label" value="${label}"/>
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
							action='<%=format.getUrl() + "&ModOrden=INC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&EventCod=" + event + "&Order=" + orden + "&label=" + label%>'
							styleClass="aOrderButton" paramId='<%=format.getId()%>'
							paramName="eventrule" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
						<html:link
							action='<%=format.getUrl() + "&ModOrden=DEC&TpObj=" + TpObj + "&IdObj=" + IdObj + "&entityId=" + entityId + "&EventCod=" + event + "&Order=" + orden + "&label=" + label%>'
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
		  			
		  			<c:url var="eliminateRule" value="/events.do">
		  				<c:param name="method" value="delEventRule"/>				  				
						<c:param name="TpObj" value="${TpObj}"/>
						<c:param name="IdObj" value="${IdObj}"/>
						<c:param name="entityId" value="${entityId}"/>									
						<c:param name="delEvent" value="${delEvent}"/>		
						<c:param name="delOrder" value="${delOrder}"/>	
						<c:param name="delRule" value="${delRule}"/>
						<c:param name="label" value="${label}"/>
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

</div>

<ispac:layer/>
<ispac:rewrite id="waitPage" page="wait.jsp"/>

<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
    <%-- <tr>
      <td width="100%" align="right">
        <img style='margin:4px 4px 4px'
        		 src='<ispac:rewrite href="img/close.png"/>'
        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
      </td>
    </tr>
    --%>
    <tr>
      <td width="100%" height="100%">
        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>

      	      	<iframe id="workframe"
      	      				name="workframe"
							frameborder="0"
							margin="5px 5px"
							allowTransparency="true"
							src='<%=waitPage%>'
							style="height:100%;width:100%">
				</iframe>
      	    </td>
      	  </tr>
      	</table>
      </td>
    </tr>
  </table>
</div>
</div>

