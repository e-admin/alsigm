<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title></title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>

	<%
		String entity = request.getParameter("entity");
		// Nombre la variable de sesión donde se han salvado
		// los parámetros que utiliza el tag actionframe
		String parameters = request.getParameter("parameters");
		// Valor del campo implicado en la búsqueda
		String field = request.getParameter("field");
		if (field == null)
			field = "";
	%>
	<script language="javascript">
		<!--  
		function selectOrg(uid){
			document.formOrg.uid.value = uid;
			document.formOrg.submit();
		}
	
		function setOrg(uid){
			document.formOrg.uid.value = uid;
			document.formOrg.action = 'setOrg.do';
			document.formOrg.submit();	
		}
		-->
	</script>
	</head>
	<body>
		<form name="formOrg" action="selectOrg.do" method="post">
			<input type="hidden" name="entity" value='<%= entity%>'/>
			<input type="hidden" name="parameters" value='<%= parameters%>'/>
			<input type="hidden" name="field" value='<%= field%>'/>
			<input type="hidden" name="uid"/>		
		</form>

		<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
			<tr>
				<td width="100%">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td class="title" height="18px" width="80%">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr valign="bottom">
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
										<td width="100%" class="menuhead">
											<bean:message key="select.value.title"/>
										</td>
									</tr>
								</table>
							</td>
							<td width="20%" style="text-align:right">
								<%-- BOTON SALIR --%>
								<table border="0" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
									</tr>
									<tr>
										<td style="text-align:right">
											<input type="button" value='<bean:message key="common.message.cancel"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
											<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
										</td>
									</tr>
								</table>						
							</td>
						</tr>
						<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="100%" class="blank">
					<table border="0" cellspacing="2" cellpadding="2" width="100%">
						<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
						<tr>
							<td valign="middle" align="center" class="formsTitle" width="100%">
					    		<table cellpadding="0" cellspacing="1" border="0" width="98%" >
									<tr>
										<td class="index"><html:errors/></td>
									</tr>
									<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
		
				        			<c:set var="parentUid" value="${appConstants.actions.PARENT_UID}"/>
									<jsp:useBean id="parentUid" type="java.lang.String"/>
			
									<logic:present name='<%=parentUid%>'>
								      	<tr>
								        	<td>
												<c:set var="url">
													javascript:selectOrg("<bean:write name='<%=parentUid%>'/>");
												</c:set>
												<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="15px"/>
												<a class="tdlink" href='<c:out value="${url}"/>'><bean:message key="app.selectOrg.subirNivel"/></a>
									      	</td>
							        	</tr>
										<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
									</logic:present>
							      	<tr>
							        	<td>
		
						        			<c:set var="orgList" value="${appConstants.actions.ORG_UNITS}"/>
											<jsp:useBean id="orgList" type="java.lang.String"/>
		
						          			<!-- DisplayTag con formateador -->
											<display:table name='<%=orgList%>'
														   id="object"
														   export="false"
														   class="tableDisplay"
														   sort="list"
														   requestURI=''>
												
												<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
													<logic:equal name="format" property="fieldType" value="LINK">
													
													  	<display:column titleKey='<%=format.getTitleKey()%>' 
													  					media='<%=format.getMedia()%>' 
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
													  					decorator='<%=format.getDecorator()%>'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					class='<%=format.getColumnClass()%>'
													  					style="white-space: nowrap;">
													  					
															<c:set var="url">
																javascript:<%=format.getUrl()%>("<bean:write name="object" property='<%=format.getPropertyLink() %>'/>") 
															</c:set>
															<a class="menu" href='<c:out value="${url}"/>'>
																<%--
																<%=format.formatProperty(object)%>
																<bean:message key='<%=(String)format.formatProperty(object) %>'/>
																--%>
								    							<c:choose>
								    								<c:when test="${!empty format.propertyValueKey}">
									    								<bean:message key='<%=(String)format.formatProperty(object)%>'/>
									    							</c:when>
								    								<c:otherwise>
								    									<%=format.formatProperty(object)%>
								    								</c:otherwise>
								    							</c:choose>
															</a>
													  	</display:column>
													  	
													</logic:equal>
													<logic:equal name="format" property="fieldType" value="LIST">
													
														<display:column titleKey='<%=format.getTitleKey()%>' 
																		media='<%=format.getMedia()%>' 
																		headerClass='<%=format.getHeaderClass()%>'
																		sortable='<%=format.getSortable()%>' 
																		decorator='<%=format.getDecorator()%>'
																		sortProperty='<%=format.getPropertyName()%>'
																		class='<%=format.getColumnClass()%>'>
																		
															<%=format.formatProperty(object)%>

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
		
	</body>
</html>