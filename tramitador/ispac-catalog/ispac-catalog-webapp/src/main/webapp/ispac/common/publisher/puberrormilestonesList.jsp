<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>
<div id="navmenutitle">
	<bean:message key="title.pubErrorMilestones"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
</div>

<html:form action='/showPubErrorMilestonesList.do'>

	<display:table name="PubErrorMilestonesList" 
				   id='itemobj' 
				   export="false" 
				   class="tableDisplay"
				   sort="list"
				   pagesize="45" 
				   requestURI="/showPubErrorMilestonesList.do"
				   defaultsort="1">
				   
	<logic:present name="itemobj">
	
		<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
	
		 <logic:iterate name="PubErrorMilestonesListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	
			<logic:equal name="format" property="fieldType" value="ID_PCD">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="1">
								
					<ispac:lookUp name="ProceduresMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" msgAllKey="form.pubRule.msg.all" />
					
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="ID_FASE">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="2">
								
					<ispac:lookUp name="StagesMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all.a" msgNoSelectedKey="form.pubRule.msg.noSelected" />
					
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="ID_TRAMITE">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="3">
					
					<ispac:lookUp name="TasksMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all" msgNoSelectedKey="form.pubRule.msg.noSelected" />
					
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="TIPO_DOC">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="4">
					
					<ispac:lookUp name="TypeDocsMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all" msgNoSelectedKey="form.pubRule.msg.noSelected" />
					
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'>
			  		
	  				<bean:define name="item" id="applicationId" property="property(ID_APLICACION)"/>
					<bean:define name="item" id="systemId" property="property(ID_SISTEMA)"/>
								
			  		<html:link action='<%=format.getUrl() + "?applicationId=" + applicationId + "&systemId=" + systemId%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>
	
						<%=format.formatProperty(item)%>
	
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
								
					<%=format.formatProperty(item)%>
					
				</display:column>
			</logic:equal>
	
		</logic:iterate>
	
	</logic:present>
	
	</display:table>
	<!-- displayTag -->
	
</html:form>