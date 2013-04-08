<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table border="0" width="100%">
	<tr>
		<td align="right"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/>
			<%--
			<ispac:onlinehelp fileName="cover" image="img/help.gif" titleKey="header.help"/>
			--%>
		</td>
	</tr>
</table>

<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		  	<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
	  	  		<tr>
					<td class="title" height="18px" width="100%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
				  			<tr>
				  				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
				  				<td width="100%" class="menuhead"><bean:message key="milestone.title"/></td>
				  			</tr>
						</table>
			  		</td>
				</tr>
	  	  		<tr>
	  	  	  		<td class="blank">
	
						<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
							<tr>
								<td align="center">
									<!-- displayTag -->
									<display:table name="MilestoneList" 
								  				   id="milestone" 
								  				   export="true" 
								  				   class="tableDisplay"
								  				   sort="list" 
								  				   pagesize="20" 
								  				   requestURI='<%= request.getContextPath()+"/showMilestone.do" %>'>
								  				 
									  	<display:column titleKey="milestone.numexp"
									  					headerClass="sortable" 
									  					group="1"
									  					media="html">
									  					
								  			<logic:equal name="milestone" property="property(HITO)" value="1">
								  				<img src='<ispac:rewrite href="img/begin.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="2">
								  				<img src='<ispac:rewrite href="img/finalize.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="7">
								  				<img src='<ispac:rewrite href="img/relocate.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="8">
								  				<img src='<ispac:rewrite href="img/info.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="9">
								  				<img src='<ispac:rewrite href="img/suspend.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="10">
								  				<img src='<ispac:rewrite href="img/reactivate.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="11">
								  				<logic:empty name="milestone" property="property(FASE_NOMBRE)">
								  					<logic:empty name="milestone" property="property(TRAMITE_NOMBRE)">
								  						<img src='<ispac:rewrite href="img/delegate.gif"/>' border="0" />
								  					</logic:empty>
								  				</logic:empty>
								  			</logic:equal>
								  			
								  			<bean:write name="Expedient"/>
								  			
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.numexp" 
									  					headerClass="sortable" 
									  					group="1"
									  					media="xml excel csv pdf">
									  					
								  			<bean:write name="Expedient"/>
								  			
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.stage"
									  					headerClass="sortable" 
									  					group="2"
									  					media="html">
									  					
									  		<logic:equal name="milestone" property="property(HITO)" value="3">
								  				<img src='<ispac:rewrite href="img/begin.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="4">
								  				<img src='<ispac:rewrite href="img/finalize.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="11">
								  				<logic:empty name="milestone" property="property(TRAMITE_NOMBRE)">
								  					<img src='<ispac:rewrite href="img/delegate.gif"/>' border="0" />
								  				</logic:empty>
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="12">
								  				<img src='<ispac:rewrite href="img/relocate.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="20">
								  				<img src='<ispac:rewrite href="img/relocate.gif"/>' border="0" />
								  			</logic:equal>
								  			
								  			<bean:write name="milestone" property="property(FASE_NOMBRE)"/>
								  			
									  	</display:column>
									  	
									  	<display:column titleKey="milestone.stage"
									  					headerClass="sortable" 
									  					group="2"
									  					media="xml excel csv pdf">
									  					
								  			<bean:write name="milestone" property="property(FASE_NOMBRE)"/>
								  			
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.task"
									  					headerClass="sortable"
									  					media="html">
									  					
									  		<logic:equal name="milestone" property="property(HITO)" value="5">
								  				<img src='<ispac:rewrite href="img/begin.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="6">
								  				<img src='<ispac:rewrite href="img/finalize.gif"/>' border="0" />
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="11">
								  				<logic:notEmpty name="milestone" property="property(TRAMITE_NOMBRE)">
								  					<img src='<ispac:rewrite href="img/delegate.gif"/>' border="0" />
								  				</logic:notEmpty>
								  			</logic:equal>
								  			<logic:equal name="milestone" property="property(HITO)" value="13">
								  				<img src='<ispac:rewrite href="img/delete.gif"/>' border="0" />
								  			</logic:equal>
								  			
								  			<bean:write name="milestone" property="property(TRAMITE_NOMBRE)"/>
								  			
									  	</display:column>
									  	
									  	<display:column titleKey="milestone.task"
									  					headerClass="sortable"
									  					media="xml excel csv pdf">
									  					
								  			<bean:write name="milestone" property="property(TRAMITE_NOMBRE)"/>
								  			
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.milestone"
									  					headerClass="sortable">
									  					
									  		<c:set var="HITO_NOMBRE">event.milestone.type.<bean:write name="milestone" property="property(HITO)"/></c:set>
									  		<bean:define id="HITO_NOMBRE" name="HITO_NOMBRE" scope="page" type="java.lang.String"/>
									  		<bean:message key="<%=HITO_NOMBRE%>"/>
									  		
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.date"
									  					headerClass="sortable">
									  					
									  		<bean:write name="milestone" property="property(FECHA)" format="dd/MM/yyyy"/>
									  		
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.author"
									  					headerClass="sortable">
									  					
									  		<bean:write name="milestone" property="property(AUTOR_NOMBRE)"/>
									  		
									  	</display:column>
								  	
									  	<display:column titleKey="milestone.description"
									  					headerClass="sortable">
									  					
									  		<bean:write name="milestone" property="property(DESCRIPCION)"/>
									  		
									  	</display:column>
								  	
									</display:table>
									<!-- displayTag -->
								</td>
							</tr>
						</table>
	
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
