<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
<!--
	function validate(){
		document.defaultForm.name = "IntervaloFechas";
		return validateIntervaloFechas(document.defaultForm);
	}
//-->
</script>
<html:errors/>
<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

	<table border="0" width="100%">
		<tr>
			<td align="right">
				<%--
				<a href="javascript:;" 
					onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/terms.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');"
					class="help">
					<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
					<bean:message key="header.help"/>				
				</a>
				--%>
				<ispac:onlinehelp tipoObj="6" image="img/help.gif" titleKey="header.help"/>
			</td>
		</tr>
	</table>

<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
		<td>
  	 	<!-- datos de información-->
			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="forms.terms.title"/>
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr><td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="6px"/></td></tr>
							<tr>
								<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<td>
									<table height="18" cellspacing="2" border="0"  width="100%" align="right">
										<tr>
											<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											<td valign="top" height="18" class="formsTitleB">
												<html:form action="showExpiredTerms.do" onsubmit="javascript:return validate();">
													<html:javascript formName="IntervaloFechas"/>
													<!-- Nombre de Aplicación.
														 Necesario para realizar la validación -->
													<html:hidden property="entityAppName"/>
													<bean:message key="forms.terms.intervalo"/>&nbsp;&nbsp;
													<html:text property="property(FECHAINICIO)" styleClass="input" size="14"/>&nbsp; 
													<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAINICIO)" format="dd/mm/yyyy" enablePast="true"/>
													&nbsp;&nbsp;<bean:message key="forms.terms.and"/>&nbsp;&nbsp;<html:text property="property(FECHAFIN)" styleClass="input" size="14"/>&nbsp;
													<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAFIN)" format="dd/mm/yyyy" enablePast="true"/>&nbsp;&nbsp;
													<input type="submit" value='<bean:message key="common.message.consultar"/>' class="form_button_white"/>
											<td>
										</tr>
												</html:form>
									</table>
								</td>
								<td width="8px"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
							<tr>
								<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<td>
									<table height="18" cellspacing="2" border="0" width="100%">
										<tr>
											<td class="blank">
											
												<c:set var="_list" value="${appConstants.actions.TERM_LIST}"/>
												<jsp:useBean id="_list" type="java.lang.String"/>
												
												<!-- displayTag con formateador -->
												<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>
								
												<display:table name='<%=_list%>' 
												  			   id="object"
												  			   requestURI=''
												  			   export='<%=formatter.getExport()%>'
											   				   class='<%=formatter.getStyleClass()%>'
															   sort='<%=formatter.getSort()%>'
											   				   pagesize='<%=formatter.getPageSize()%>'
															   defaultorder='<%=formatter.getDefaultOrder()%>'
															   defaultsort='<%=formatter.getDefaultSort()%>'>
															   
													<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
													
														<logic:equal name="format" property="fieldType" value="LIST">
														
															<display:column titleKey='<%=format.getTitleKey()%>' 
																			media='<%=format.getMedia()%>' 
																			headerClass='<%=format.getHeaderClass()%>'
																			sortable='<%=format.getSortable()%>'
																			sortProperty='<%=format.getPropertyName()%>'
																			decorator='<%=format.getDecorator()%>'
																			comparator='<%=format.getComparator()%>'
																			class='<%=format.getColumnClass()%>'>
																			
																<%=format.formatProperty(object)%>
																
															</display:column>
															
														</logic:equal>
														
														<logic:equal name="format" property="fieldType" value="LINK">
														
														  	<display:column titleKey='<%=format.getTitleKey()%>' 
														  					media='<%=format.getMedia()%>' 
																			class='<%=format.getColumnClass()%>'
																			headerClass='<%=format.getHeaderClass()%>'
														  					sortable='<%=format.getSortable()%>'
														  					sortProperty='<%=format.getPropertyName()%>'
														  					decorator='<%=format.getDecorator()%>'
														  					comparator='<%=format.getComparator()%>'>
														  					
														  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' 
														  			paramName="object" paramProperty='<%=format.getPropertyLink() %>'>
														  			
														  			<%=format.formatProperty(object)%>
														  			
														  		</html:link>
														  	
														  	</display:column>
														  	
														</logic:equal>
														
													</logic:iterate>
														
														<%--
														<display:column titleKey="terms.numExp" media="html">
															<html:link href="selectAnActivity.do" paramId="numexp" paramName="object" paramProperty="property(NUMEXP)" paramScope="page">
																<bean:write name="object" property="property(NUMEXP)"/>
															</html:link>
														</display:column>
		
														<display:column titleKey="terms.numExp" media="excel">
															<bean:write name="object" property="property(NUMEXP)"/>
														</display:column>
		
														<display:column titleKey="terms.procedimiento">
															<bean:write name="object" property="property(NOMBRE_PCD)"/>
														</display:column>
		
														<display:column titleKey="terms.tipoPlazo">
															<bean:write name="object" property="property(DESCRIPCION)"/>
														</display:column>
		
														<display:column titleKey="terms.fechaLimite">
															<bean:write name="object" property="property(FECHA_LIMITE)" format="dd/MM/yyyy"/>
														</display:column>
														--%>
														
												  </display:table>

											  <td>
										</tr>
									</table>
								</td>
								<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
