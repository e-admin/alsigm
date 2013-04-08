<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>




<bean:define name="label" id="label"/>

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>

<jsp:useBean id="action" type="java.lang.String"/>
<html:form action='<%=action%>'>

	<html:hidden property="property(ITEMS)" value="FORMS" />
	
	<div class="buttonList">
		<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
		<ul>
			<li>
				<a href='<c:url value="${action}?method=form"/>'><nobr><bean:message key="forms.button.add"/></nobr></a>
			</li>
			<li>
				<a href="javascript: confirm_remake('remakejsp' , '<bean:message key="form.entity.forms.confirm.regenerateJsp"/>','<ispac:rewrite action="showCTEntityToManage.do"/>',330,230, '<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>', '<bean:message key="common.message.cancel"/>');"><nobr><bean:message key="entityManage.label.forms.remakeBaseForm"/></nobr></a>
			
			</li>
		</ul>
		</ispac:hasFunction>
	</div>
	
	<!-- ISPAC  displayTag con formateador -->
	<display:table name="FormList"
				   id="itemobj"
				   export="true" 
				   class="tableDisplay"
	  			   sort="list" 
	  			   pagesize="45" 
	  			   requestURI='<%=action%>'>
	
		<logic:present name="itemobj">
		
			<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
		
			 <logic:iterate name="FormListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
			 
				<logic:equal name="format" property="fieldType" value="DATA">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>
									
						<%=format.formatProperty(item)%>
							
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
				  					
				  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
				  			paramProperty='<%=format.getPropertyLink() %>'>
	
							<%=format.formatProperty(item)%>
		
				  		</html:link>
				  		
				  	</display:column>
				  	
				 </logic:equal>
				 
				 <logic:equal name="format" property="fieldType" value="LINK_JS">
				 
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>
				  		
				  		<logic:notEmpty name="item" property="property(FRM_VERSION)">
				  		
					  		<a href="javascript: showFrame('workframe','<%=format.getUrl()%>?<%=format.getId()%>=<bean:write name="item" property='<%=format.getPropertyLink()%>'/><%="&label=" + label%>',640,480)" class='<%=format.getStyleClass()%>' >	
								
								<%=format.formatProperty(item)%>

					  		</a>
					  		
				  		</logic:notEmpty>
				  		
				  		<logic:empty name="item" property="property(FRM_VERSION)">
				  		
							<%=format.formatProperty(item)%>

				  		</logic:empty>
				  		
				  	</display:column>
				  	
				 </logic:equal>
				 
				<logic:equal name="format" property="fieldType" value="VERSION">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>
									
						<logic:notEmpty name="item" property="property(FRM_VERSION)">
							
							<%=format.formatProperty(item)%>

						</logic:notEmpty>
						
				  		<logic:empty name="item" property="property(FRM_VERSION)">
							<bean:message key="form.entity.forms.staticForm"/>
				  		</logic:empty>
						
					</display:column>
					
				</logic:equal>
			 				
			</logic:iterate>
		
		</logic:present>
	
	</display:table>
	<!-- displayTag -->
	
</html:form>
