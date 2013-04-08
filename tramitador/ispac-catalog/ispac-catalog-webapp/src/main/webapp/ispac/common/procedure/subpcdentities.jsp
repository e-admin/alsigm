<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define name="pcdId" id="pcdId"/>
<bean:define name="entityId" id="entityId"/>

<div class="tabButtons">
	&nbsp;
</div>

<div class="tabContent">

	<html:form action='/selectEntities.do'>
	
		<display:table name="ItemsList" 
					   id="item" 
					   export="true" 
					   class="tableDisplay"
	  				   sort="list" 
	  				   pagesize="45" 
	  				   requestURI="/showProcedureEntity.do">
	
			 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	
				<logic:equal name="format" property="fieldType" value="LINK">
				
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>
				  					
				  		<center>
				  		
					  		<html:link action='<%=format.getUrl() + "&regId=" + pcdId + "&entityId=" + entityId%>' 
					  				   styleClass='<%=format.getStyleClass()%>' 
					  				   paramId='<%=format.getId()%>' 
					  				   paramName="item"
					  				   paramProperty='<%=format.getPropertyLink() %>'>
		
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
									
						<%=format.formatProperty(item)%>
						
					</display:column>
					
				</logic:equal>
			
				<logic:equal name="format" property="fieldType" value="FORM">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
							 		headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
						
						<%=format.formatProperty(item)%>
						
						<logic:notEmpty name="item" property='property(NOMBRE_APP_KEY)'> 
							<bean:define type="java.lang.String" id="valueKey" name="item" property='property(NOMBRE_APP_KEY)' />
							<bean:message key='<%=valueKey%>' />
						</logic:notEmpty>
	
					</display:column>
					
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="MODORDER">
				
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
	
							<center>
							
								<html:link
									action='<%=format.getUrl() + "&pcdId=" + pcdId + "&entityId=" + entityId + "&modOrder=INC"%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
								<html:link
									action='<%=format.getUrl() + "&pcdId=" + pcdId + "&entityId=" + entityId + "&modOrder=DEC"%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>' >-</html:link>
									
							</center>
							
					</display:column>
					
				</logic:equal>
	
				<logic:equal name="format" property="fieldType" value="LINKFRM">
				
				  	<display:column titleKey='<%=format.getTitleKey()%>' 
				  					media='<%=format.getMedia()%>' 
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>' 
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>
				  					
						<bean:define id="ENTITY_ID" name="item" property="property(CTENTITY:ID)" />
						<bean:define id="fieldlink" name="item" property="property(PENTITIES:ID)" />
						
						<center>
						
							<ispac:linkframe id="ENTITYAPPMANAGER"
											 styleClass='<%=format.getStyleClass()%>'
				     						 target="workframe"
											 action='<%="selectObject.do?noSearch=true&codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&caption=select.entityForm.caption&decorator=/formatters/entities/entitiesappformatter2.xml&" + format.getId() + "=" + fieldlink.toString()+"&sqlquery=WHERE ID IN (SELECT ID FROM SPAC_CT_APLICACIONES WHERE ENT_PRINCIPAL_ID="+ENTITY_ID+")"%>'
											 titleKey="procedure.card.entities.linkLabel.assign.form"
											 width="640"
											 height="480"
											 showFrame="true">
							</ispac:linkframe>
					  					
					  		<html:link action='<%=format.getUrl() + "&entityId=" + entityId+"&appId=" %>'
					  				   styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
					  				   paramProperty='<%=format.getPropertyLink() %>'>
					  				   
								<bean:message key="procedure.button.entity.form.procedure"/>
								
					  		</html:link>
				  		
				  		</center>
	
				  	</display:column>
				  	
				 </logic:equal>
	
			</logic:iterate>
			
		</display:table>
	
	</html:form>

</div>