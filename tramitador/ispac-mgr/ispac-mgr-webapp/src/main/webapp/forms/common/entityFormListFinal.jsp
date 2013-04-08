<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

									<logic:notEmpty name="defaultForm" property="items">
										
										<br/>
										
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
										
											<tr>
												<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
											<tr>
												<td>

													<table width="100%" border="0" cellspacing="0" cellpadding="0"> <!-- 9 -->

														<tr>
															<td colspan="3" align="center">
															
																<table width="95%" cellpadding="0" cellspacing="0">
																
																	<tr>			 
																		<td width="95%" class="formsTitleB" >
																			<b><nobr>
																				<c:set var="_lista"><bean:write name="defaultForm" property="entityApp.label(LISTA)"/></c:set>
																				<jsp:useBean id="_lista" type="java.lang.String"/>
																				<logic:notEmpty name="_lista">
																					<bean:write name="_lista"/>
																				</logic:notEmpty>
																				<logic:empty name="_lista">
																					<bean:message key="forms.label.list"/>
																				</logic:empty>
																			</nobr></b>
																		</td>
																	</tr>
																	<tr>
																		<td width="95%" height="2px" class="formsTitleB">
																			<hr class="formbar"/>
																		</td>
																	</tr>
																	<tr>
																		<td>
																		
																		  	<c:set var="_imagen"><bean:write name="defaultForm" property="entityApp.label(IMAGEN)"/></c:set>
																			<jsp:useBean id="_imagen" type="java.lang.String"/>
																			
																			<!-- displayTag con formateador -->
																			<bean:define name="defaultForm" property="formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>
																		
																			<display:table 	name="sessionScope.defaultForm.items"
																							id="object"
																							export='<%=formatter.getExport()%>'
																							class='<%=formatter.getStyleClass()%>'
																							sort='<%=formatter.getSort()%>'
																							pagesize='<%=formatter.getPageSize()%>'
																							defaultorder='<%=formatter.getDefaultOrder()%>'
																							defaultsort='<%=formatter.getDefaultSort()%>'
																							requestURI='<%=formatter.getRequestURI()%>'
																							excludedParams="d-*"
																							decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">
																							
																				<c:url value="${urlExpDisplayTagOrderParams}" var="link">
																					<c:if test="${!empty param.stageId}">
																						<c:param name="stageId" value='${param.stageId}'/>
																					</c:if>
																					<c:if test="${!empty param.taskId}">
																						<c:param name="taskId" value='${param.taskId}'/>
																					</c:if>
																					<c:if test="${!empty param.activityId}">
																						<c:param name="activityId" value='${param.activityId}'/>
																					</c:if>
																					<c:if test="${!empty param.numexp}">
																						<c:param name="numexp" value="${param.numexp}"/>
																					</c:if>
																					<c:choose>
																						<c:when test="${!empty defaultForm.secondaryEntity && defaultForm.secondaryEntity != 0}">
																							<c:param name="entity" value="${defaultForm.secondaryEntity}"/>
																						</c:when>
																						<c:otherwise>
																							<c:param name="entity" value="${defaultForm.entity}"/>
																						</c:otherwise>
																					</c:choose>
																					<c:param name="key" value="${object.keyProperty}"/>
																					<c:if test="${!empty param.form}">
																						<c:param name="form" value='${param.form}'/>
																					</c:if>
																				</c:url>

																			  	<display:setProperty name="export.csv" value="false"/>
																			  	<display:setProperty name="export.xml" value="false"/>
																			  	<display:setProperty name="export.pdf" value="false"/>

																				<logic:notEmpty name="_imagen">
																				  	
																			       	<display:column headerClass="headerDisplay" media="html">
																		                <a href='<c:out value="${link}"/>'><img height="16" width="16px" src='<ispac:rewrite href='<%="img/" + _imagen + ".gif"%>'/>' border="0"/></a>
																			       	</display:column>
																			       	
																			    </logic:notEmpty>
																		       	
																				<c:set var="_entityApp" value="${defaultForm.entityApp}"/>
																				<jsp:useBean id="_entityApp" type="ieci.tdw.ispac.ispaclib.app.EntityApp"/>
																		       	
																		       	<logic:iterate name="defaultForm" property="formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																		       																			       	
																					<!-- ENLACE -->
																					<logic:equal name="format" property="fieldType" value="LINK">
																					
																					  	<display:column title='<%=format.getTitle(_entityApp)%>'
																					  					media='<%=format.getMedia()%>'
																					  					headerClass='<%=format.getHeaderClass()%>'
																					  					sortable='<%=format.getSortable()%>'
																					  					sortProperty='<%=format.getPropertyName()%>'
																					  					class='<%=format.getColumnClass()%>'
																					  					decorator='<%=format.getDecorator()%>'
																					  					comparator='<%=format.getComparator()%>'>

																			                <a href='<c:out value="${link}"/>' class="tdlink">
																			                	<%=format.formatProperty(object)%>
																			                </a>
																			                
																					  	</display:column>
																					  	
																		   			</logic:equal>

																					<!-- ENLACE CON ICONO-->
																					<logic:equal name="format" property="fieldType" value="ICON_LINK">
																					
																					  	<display:column title='<%=format.getTitle(_entityApp)%>'
																					  					media='<%=format.getMedia()%>'
																					  					headerClass='<%=format.getHeaderClass()%>'
																					  					class='<%=format.getColumnClass()%>'
																					  					>
																			                <a href='<c:out value="${link}"/>' class="tdlink">
																			                	<img src='<ispac:rewrite href="img/link.gif"/>' border="0"/>
																			                </a>
																					  	</display:column>
																		   			</logic:equal>

																		   			
																		   			<!-- DATO DE LA LISTA -->
																		   			<logic:equal name="format" property="fieldType" value="LIST">
																		   			
																						<display:column title='<%=format.getTitle(_entityApp)%>'
																									    media='<%=format.getMedia()%>'
																					  					headerClass='<%=format.getHeaderClass()%>'
																					  					sortable='<%=format.getSortable()%>'
																					  					sortProperty='<%=format.getPropertyName()%>'
																					  					class='<%=format.getColumnClass()%>'
																					  					decorator='<%=format.getDecorator()%>'
																					  					comparator='<%=format.getComparator()%>'>
																					  					
																							<%=format.formatProperty(object)%>
																							
																						</display:column>
																						
																		   			</logic:equal>
																		   			
																		   			<!-- MONEDA -->
																		   			<logic:equal name="format" property="fieldType" value="CURRENCY">
																		   			
																						<display:column title='<%=format.getTitle(_entityApp)%>'
																									    media='<%=format.getMedia()%>'
																					  					headerClass='<%=format.getHeaderClass()%>'
																					  					sortable='<%=format.getSortable()%>'
																					  					sortProperty='<%=format.getPropertyName()%>'
																					  					class='<%=format.getColumnClass()%>'
																					  					decorator='<%=format.getDecorator()%>'
																					  					comparator='<%=format.getComparator()%>'>
																					  		
																					  		<logic:notEmpty name="object" property='<%= format.getPropertyName() %>'>
																								<nobr><%=format.formatProperty(object)%>&nbsp;<bean:message key="moneda" /></nobr>
																							</logic:notEmpty>

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
													
												</td>
											</tr>
											
										</table>
										
									</logic:notEmpty>

									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
		
	</table>
	
</html:form>

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>
