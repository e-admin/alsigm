<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table border="0" width="100%">
	<tr>
		<td align="right">
			<ispac:onlinehelp tipoObj="5"  image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
</table>

<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="tramites.nuevoTramite.titulo" />
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
					
						<display:table name="ExpTaskList" 
									   id="object" 
									   export="true" 
									   class="tableDisplay"
									   sort="list" 
									   pagesize="15" 
									   requestURI=''>
	
							<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
							
								<logic:equal name="format" property="fieldType" value="LINK">
								
									<display:column titleKey='<%=format.getTitleKey()%>' 
													media='<%=format.getMedia()%>' 
													sortable='<%=format.getSortable()%>'
													sortProperty='<%=format.getPropertyName()%>'
													decorator='<%=format.getDecorator()%>'
													class='<%=format.getColumnClass()%>'>
													
										<c:url value="createTsk.do" var="link">
											<logic:notPresent name="multibox" scope="request">
												<c:param name="stageId" value="${stageId}"/>
											</logic:notPresent>
		
											<logic:present name="multibox" scope="request">
														<logic:iterate name="multibox" id="id">
															<c:param name="multibox" >
																<bean:write name="id"/>
															</c:param>
														</logic:iterate>
														
											</logic:present>
		
											<c:param name="taskPcdId" >
												<bean:write name="object" property="property(ID)"/>
											</c:param>
										</c:url>
										<table border="0" width="100%" cellpadding="1" cellspacing="1">
											<tr>
												<td width="23px" height="17px" align="center">
													<logic:notEmpty name="object" property="property(ID_PCD_SUB)">
														<img src='<ispac:rewrite href="img/procedimiento.gif"/>'/>
													</logic:notEmpty>
													<logic:empty name="object" property="property(ID_PCD_SUB)">
														<img src='<ispac:rewrite href="img/tramiteestado1.gif"/>'/>
													</logic:empty>
												</td>
												<td>
													<bean:define id="iniciable" name="object" property="property(INICIABLE)"/>
													<c:choose>
														<c:when test="${iniciable}">
															<nobr><a href='<c:out value="${link}"/>' class="displayLink"><%=format.formatProperty(object)%></a></nobr>
														</c:when>
														<c:otherwise>
															<nobr><%=format.formatProperty(object)%></nobr>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</table>
										<%--
										<nobr>
										<a href='<c:out value="${link}"/>' class="displayProc">
											<!--  img src='<ispac:rewrite href="img/new.gif"/>' border=0 alt="Crear" title="Crear"/-->
											<%=format.formatProperty(object)%>
										</a></nobr>
										--%>
										
									</display:column>
									
								 </logic:equal>
	
								<logic:equal name="format" property="fieldType" value="LIST">
								
									<display:column titleKey='<%=format.getTitleKey()%>' 
													media='<%=format.getMedia()%>' 
													class='<%=format.getColumnClass()%>'
													sortable='<%=format.getSortable()%>' 
													decorator='<%=format.getDecorator()%>'>
													
										<%=format.formatProperty(object)%>
										
									</display:column>
									
								</logic:equal>
								
								<logic:equal name="format" property="fieldType" value="BOOLEAN">
									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'>
										<% if ("1".equals(format.formatProperty(object))) { %>
											<bean:message key="bool.yes"/>
										<% } else { %>
											<bean:message key="bool.no"/>
										<% } %>
									</display:column>
								</logic:equal>
								
							</logic:iterate>
	
							<display:column title='' media='html' class="width5percent">
							</display:column>					
						  
							<display:column titleKey='formatter.newTask.creado' media='html' class="width10percent">
								<bean:define id="creado" name="object" property="property(CREADO)"/>
								<c:choose>
									<c:when test="${creado == '1'}">
										<bean:message key="bool.yes"/>
									</c:when>
									<c:otherwise>
										<bean:message key="bool.no"/>
									</c:otherwise>
								</c:choose>
							</display:column>					
							
							<display:column titleKey='formatter.newTask.dependencias' media='html' class="width20percent">
								<bean:define id="dependencias" name="object" property="property(DEPENDENCIAS)"/>
								<c:if test="${!empty dependencias}">
									<ul class="list">
										<c:forEach var="dependencia" items="${dependencias}">
											<li><bean:write name="dependencia" property="property(SPAC_P_TRAMITES:NOMBRE)"/></li>
										</c:forEach>
									</ul>
								</c:if>
							</display:column>
							
						</display:table>
					
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

