<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
		<td align="right">
			<ispac:onlinehelp tipoObj="13" image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="tramites.seleccionProcedimiento.titulo" />
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
						<display:table name="InstProcedureList" id="procedure" export="false" class="tableDisplay"
							sort="list" pagesize="15" requestURI=''>
				
							<logic:iterate name="FormatterInstProcedure" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				
								<logic:equal name="format" property="fieldType" value="CHECKBOX">
									<display:column titleKey='<%=format.getTitleKey()%>' 
													media='<%=format.getMedia()%>'
													headerClass='<%=format.getHeaderClass()%>' 
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'>
							            <table cellpadding="1" cellspacing="1" border="0" width="100%">
								            <tr>
					  							<td align="center" valign="middle">
					  								<img src='<ispac:rewrite href="img/folder.gif"/>' align="center" valign="top" width="16" height="16" border="0"/>
					  							</td>
					  							<td align="center" valign="middle">
					    							<html:multibox property="multibox">
								              	  		<%=format.formatProperty(procedure)%>
					    							</html:multibox>
					  							</td>
											</tr>
								          </table>
									</display:column>
							    </logic:equal>
				
								<logic:equal name="format" property="fieldType" value="LINK">
								  	<display:column titleKey='<%=format.getTitleKey()%>'
								  					media='<%=format.getMedia()%>'
								  					headerClass='<%=format.getHeaderClass()%>'
								  					sortable='<%=format.getSortable()%>'
								  					sortProperty='<%=format.getPropertyName()%>'
								  					decorator='<%=format.getDecorator()%>'>

										<c:choose>
											<c:when test="${appConstants.config['CONFIRM_EXPEDIENT_INITIATION'] == 'true'}">
												<%
													String pcdName = (String)format.formatProperty(procedure);
													pcdName = ieci.tdw.ispac.ispaclib.utils.StringUtils.escapeJavaScript(pcdName);
												%>
										  		<a href="javascript:sure('<%=format.getUrl()%>?<%=format.getId()%>=<bean:write name="procedure" property='<%=format.getPropertyLink()%>'/>','<bean:message key="formatter.createProcess.confirm" arg0='<%=pcdName%>' />','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')" 
										  			class='<%=format.getStyleClass()%>' >
													<%=format.formatProperty(procedure)%>
										  		</a>
											</c:when>
											<c:otherwise>
										  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="procedure"
										  			paramProperty='<%=format.getPropertyLink() %>'>
							              	  		<%=format.formatProperty(procedure)%>
										  		</html:link>
											</c:otherwise>
								  		</c:choose>
								  	</display:column>
								 </logic:equal>
				
								<logic:equal name="format" property="fieldType" value="DATA">
									<display:column titleKey='<%=format.getTitleKey()%>' 
													media='<%=format.getMedia()%>' 
													headerClass='<%=format.getHeaderClass()%>'
													sortable='<%=format.getSortable()%>' 
													decorator='<%=format.getDecorator()%>'>
				              	  		<%=format.formatProperty(procedure)%>
				
									</display:column>
								</logic:equal>
				
							</logic:iterate>
						</display:table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
