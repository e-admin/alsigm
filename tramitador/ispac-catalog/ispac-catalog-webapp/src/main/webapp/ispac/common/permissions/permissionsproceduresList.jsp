<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div id="navmenutitle">
	<bean:message key="usrmgr.userpermissions.mainTitle"/>
</div>
<!-- BREAD CRUMBS -->
	<tiles:insert page="../tiles/breadCrumbsTile.jsp" ignore="false" flush="false"/>
<!-- ------------ -->
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<%String URL = "viewUsersManager.do?mode=Simple&captionkey=usrmgr.permisos&action=selectTypePermissions.do&procId=" + request.getAttribute("procId");%>
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
</div>
<div>hola</div>
<html:form action="/showPermissions.do">
<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
  	<td align="center">
			<display:table name="ProceduresList" 
						   id="procedure" 
						   export="true" 
						   class="tableDisplay"
		  				   sort="list" 
		  				   pagesize="10" 
		  				   requestURI='<%= request.getContextPath()+"/showProceduresList.do" %>'>

           		<logic:iterate name="ProceduresListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	  				<logic:equal name="format" property="fieldType" value="DELETE">
	  				
						<display:column titleKey='<%=format.getTitleKey()%>' 
						 				sortable='<%=format.getSortable()%>' 
						 				headerClass="headerDisplay">
							<html:link action='<%=format.getUrl() + "?procId=" + request.getAttribute("procId")%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="procedure"
								paramProperty='<%=format.getPropertyLink() %>'>
								<bean:message key="forms.button.delete"/>
						  	</html:link>
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
							<%=format.formatProperty(procedure)%>
						</display:column>
						
					</logic:equal>
					<%-- 
					<logic:equal name="format" property="fieldType" value="UPDATE">
					
						<display:column titleKey='<%=format.getTitleKey()%>' 
										sortable='<%=format.getSortable()%>' 
										group='2' 
										headerClass="headerDisplay">
							<bean:define id="fieldlink" name="procedure" property="property(UID_USR)" />
							<ispac:linkframe id="Update"
								styleClass='<%=format.getStyleClass()%>'
								target="workframe"
								action='<%=format.getUrl() + "?procId=" + request.getAttribute("procId") + "&" + format.getId() + "=" + fieldlink.toString() %>'
								showFrame="true"
								titleKey="usrmgr.userpermissions.mod">
							</ispac:linkframe>
						</display:column>
						
   					</logic:equal>
   					--%> 
				</logic:iterate>
				
			</display:table>
  		</td>
	</tr>
</table>
</html:form>