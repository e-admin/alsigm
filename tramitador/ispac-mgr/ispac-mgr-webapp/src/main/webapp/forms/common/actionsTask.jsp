<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>




<table cellpadding="0" cellspacing="0" border="0" height="24px" width="100%">

	<tr>
		<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
		
		<!-- GUARDAR -->
		<logic:equal value="false" property="readonly" name="defaultForm">
		
			<td class="formaction">
				<html:link onclick="javascript: ispac_needToConfirm = false;" href="javascript:save();" styleClass="formaction">
					<bean:message key="forms.button.save"/>
				</html:link>
			</td>
			<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			
		</logic:equal>
		
		<td width="100%"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>

		<!-- CERRAR TRÁMITE -->
		<logic:equal value="false" property="readonly" name="defaultForm">
		
			<td class="formaction">
			
				<c:url value="closeTask.do" var="_link">
					<c:param name="idsTask">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)"/>
					</c:param>
				</c:url>
                <a href="javascript:sure('<c:out value="${_link}"/>','<bean:message key="ispac.action.task.close.msg"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')" class="formaction">
                	<nobr><bean:message key="forms.button.cerrartramite"/></nobr>
                </a>
                
			</td>
			<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			<td width="1px"><img height="20px" width="1px" src='<ispac:rewrite href="img/barra.gif"/>' align="absmiddle"/></td>
			<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			<td width="100%"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
		</logic:equal>

		<!-- ELIMINAR TRÁMITE -->
		<logic:notEqual name="expedientState" value="closed" >
			
			<logic:equal value="false" property="readonly" name="defaultForm">
				
				<td class="formaction">
				
					<c:url value="deleteTsk.do" var="_link">
						<c:param name="idsTask">
							<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)"/>
						</c:param>
					</c:url>
					<logic:notEqual name="existCircuit" value="true">					
		                <a href="javascript:sure('<c:out value="${_link}"/>','<bean:message key="ispac.action.task.delete.msg"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')" class="formaction">
		                	<nobr><bean:message key="forms.button.eliminartramite"/></nobr>
		                </a>
	                </logic:notEqual>	                
	                <logic:equal name="existCircuit" value="true">					
		                <a href="javascript:sure('<c:out value="${_link}"/>','<bean:message key="ispac.action.task.delete2.msg"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')" class="formaction">
		                	<nobr><bean:message key="forms.button.eliminartramite"/></nobr>
		                </a>
	                </logic:equal>
	                
				</td>
				<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
				<td width="1px"><img height="20px" width="1px" src='<ispac:rewrite href="img/barra.gif"/>' align="absmiddle"/></td>
				<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
				
	    	</logic:equal>
	            
		</logic:notEqual>
		
		<td width="100%"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
		
		<!-- DELEGAR TRÁMITE -->
		<logic:equal value="false" property="readonly" name="defaultForm">
		
			<td class="formaction">
			
				<bean:define name="defaultForm" property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)" id="idsTask" />
				<% String url = "javascript:showFrame(\"workframe\",\"viewUsersManager.do?captionkey=ispac.action.task.delegate&action=delegateResp.do&idsTask="+idsTask+"\");";%>
				
				<a href='<%=url%>' class="formaction">
					<bean:message key="forms.button.delegar"/>
				</a>								
			</td>
			
			<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			
		</logic:equal>
		<!-- DEVOLVER TRÁMITE -->
		<logic:equal value="false" property="readonly" name="defaultForm">
			<logic:present name="returnUID">
				<td class="formaction">
				
					<bean:define name="defaultForm" property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)" id="idsTask" />
					

						<c:url var="_link" value="delegateResp.do">
							<c:param name="uid" value="${returnUID}"/>
							<c:param name="idsTask" value="${idsTask}"/>
						</c:url>
						<a class="formaction" href='<c:out value="${_link}"/>'><bean:message key="forms.button.devolver"/></a>
				</td>
				<td width="4px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			</logic:present>			
		</logic:equal>

		
	</tr>
	
</table>

