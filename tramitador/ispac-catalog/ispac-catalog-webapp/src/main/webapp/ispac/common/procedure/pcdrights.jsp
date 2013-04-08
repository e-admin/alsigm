<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<%String URL = "viewUsersManager.do?captionkey=usrmgr.permisos&action=selectTypePermissions.do&procId=" + request.getParameter("regId");%>
			<ispac:linkframe id="LDAP_USER"
						styleClass=""
		     			target="workframe"
						action='<%=URL%>'
						titleKey="usrmgr.choose.userpermissions"
						width="640"
						height="480"
						showFrame="true">
			</ispac:linkframe>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<div class="tabContent">
<html:form action="/showProcedureEntity.do">
<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
  	<td align="center">
			<display:table name="ProceduresList" 
						   id="procedure" 
						   export="true" 
						   class="tableDisplay" 
						   sort="list" 
						   pagesize="10" 
						   requestURI="/showProcedureEntity.do">

           		<logic:iterate name="ProceduresListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	  				<logic:equal name="format" property="fieldType" value="DELETE">
	  				
						<display:column titleKey='<%=format.getTitleKey()%>' 
										sortable='<%=format.getSortable()%>' 
										media='<%=format.getMedia()%>' 
										headerClass="headerDisplay">									
						 				

						  	
						  	<bean:parameter id="entityId" name="entityId"/>
							 <bean:define id="uid_usr" name="procedure" property="property(UID_USR)"/>
							 <bean:define id="permiso" name="procedure" property="property(PERMISO)"/>
							  <bean:define id="procId" name="procedure" property="property(ID_PCD)"/>
			
		 					<c:url var="eliminatePermission" value="/permissions.do">
		 					
								<c:param name="method" value="delete"/>
								<c:param name="procId" value="${procId}"/>
								<c:param name="entityId" value="${entityId}"/>	
								<c:param name="uid" value="${uid_usr}"/>	
								<c:param name="permiso" value="${permiso}"/>
												
							</c:url>	 				

							<center>														
								<a href = "javascript:messageConfirm('<c:out value="${eliminatePermission}" escapeXml="false"/>','<bean:message key="usrmgr.delete.confirm"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>');" class='<%=format.getStyleClass()%>'/>								  
									<bean:message key="forms.button.delete"/>
								
								</a>												  					  								  							  		
							</center>	 
						</display:column>
						
   					</logic:equal>
   					<logic:equal name="format" property="fieldType" value="DATAB">
   					
						<display:column titleKey='<%=format.getTitleKey()%>' 
										group='1'>
						<%=format.formatProperty(procedure)%>
						</display:column>
					</logic:equal>
					<logic:equal name="format" property="fieldType" value="DATA">
					
						<display:column titleKey='<%=format.getTitleKey()%>'>
								<bean:message key='<%=""+format.formatProperty(procedure)%>'/>	
						</display:column>
						
					</logic:equal>
					<%--<logic:equal name="format" property="fieldType" value="UPDATE">
					
						<display:column titleKey='<%=format.getTitleKey()%>' 
										sortable='<%=format.getSortable()%>' 
										media='<%=format.getMedia()%>' 
										group='2' 
										headerClass="headerDisplay">
							<bean:define id="fieldlink" name="procedure" property="property(UID_USR)" />
							<center>
								<ispac:linkframe id="Update"
									styleClass='<%=format.getStyleClass()%>'
									target="workframe"
									action='<%=format.getUrl() + "?procId=" + request.getParameter("regId") + "&" + format.getId() + "=" + fieldlink.toString() %>'
									showFrame="true"
									titleKey="usrmgr.userpermissions.mod">
								</ispac:linkframe>
							</center>
						</display:column>
						
   					</logic:equal>
   					--%>
				</logic:iterate>
				
			</display:table>
  		</td>
	</tr>
</table>
</html:form>
</div>