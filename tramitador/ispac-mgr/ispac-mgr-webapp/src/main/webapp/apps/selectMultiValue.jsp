<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>Página de búsqueda</title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
		
		<%
			String entity = request.getParameter("entity");
			String description = request.getParameter("description");
			if (description == null)
			{
				description = "";
			}
			// Nombre la variable de sesión donde se han salvado
			// los parámetros que utiliza el tag actionframe
			String parameters = request.getParameter("parameters");
			// Valor del campo implicado en la búsqueda
			String field = request.getParameter("field");
			if (field == null)
			{
				field = "";
			}
		%>
		
		<ispac:rewrite id="selectMultiValue" action="selectMultiValue.do"/>
		
		<script language='JavaScript' type='text/javascript'><!--

			function search() {
			
				document.entityBatchForm.action = '<%=selectMultiValue%>' + "?action=search";
				document.entityBatchForm.submit();
			}
			
		//--></script>
		
	</head>
	
	<body>
	
		<html:form action="setMultiValue.do">
		
			<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
				<tr>
					<td width="100%" class="blank">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td class="title" height="18px">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
											<td width="100%" class="menuhead"><bean:message key="select.value.title"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
							</tr>
							<tr>
								<td>
	            					<table width="80%" border="0" cellspacing="1" cellpadding="0" class="boxform" align="center">
										<tr>
											<td class="titlebox" width="100%">
												<html:hidden property="property(entity)"/>
												<html:hidden property="property(parameters)"/>
												<html:hidden property="property(field)"/>
												<table border="0" cellspacing="0" cellpadding="0" align="center">
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td class="index"><html:errors/></td>
													</tr>
													<tr>
														<td width="50%">
														<%--												
															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
																	<td height="20px" class="formsTitle">
																		<bean:message key="select.value.value" bundle="FORMS"/>
																		<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
																	<td><input class="input" type="text" name="description" value='<%= description %>'/></td>
																</tr>
															</table>
														--%>													
														</td>
														<td width="50%">
															<table border="0" cellspacing="0" cellpadding="0" align="center" valign="middle">
																<tr>
																	<td>
																		<%--<input type="button" value="Buscar" class="form_button_white" onclick="javascript:search();"/>--%>
																	</td>
																	<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="8px"/></td>
																	<td>
																	
																		<input type="button" value='<bean:message key="common.message.ok"/>' class="form_button_white" onclick="javascript:submit();"/>
																	</td>
																	<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="8px"/></td>
																	<td>
																		<input type="button" value='<bean:message key="exit.button"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<logic:present name="ValueList">
				<table cellpadding="5" cellspacing="0" border="0" width="100%">
					<tr>
				  		<td>
				    		<table cellpadding="0" cellspacing="1" border="0" width="100%" >
				      			<tr>
				        			<td class="box">
				          				<table cellpadding="1" cellspacing="1" border="0" width="100%" id="t1" class="sortable">
							        		<tr>
												<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
					              					<td class='<%=format.getHeaderClass()%>' align="center" height="18px">
					              						<bean:write name="format" property="title"/>
					              						
					              					</td>
								        		</logic:iterate>
							        		</tr>
			            					<logic:iterate id="value" name="ValueList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
												<bean:define id="valor" name="value" property="property(VALOR)" />
			              						<tr>
			                						<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
							    						<logic:equal name="format" property="fieldType" value="LINK">
											            	<td class="element" align="left">
												        		<a class="element" href="javascript:SelectValue('<%= valor.toString() %>');">
												        			<%=format.formatProperty(value)%>
														    	</a>
													    	</td>
												    	</logic:equal>
												        <logic:equal name="format" property="fieldType" value="LIST">
								                			<td class="element" align="left">
								                				<%=format.formatProperty(value)%>
															</td>
								                		</logic:equal>
											          	<logic:equal name="format" property="fieldType" value="CHECKBOX">
											            	<td class="element" align="left">
					    										<html:multibox property="multibox">
					    											<%=format.formatProperty(value)%>
					    										</html:multibox>
											              	</td>
											          	</logic:equal>
													</logic:iterate>
			  									</tr>
			            					</logic:iterate>
			          					</table>
			        				</td>
			      				</tr>
			    			</table>
			  			</td>
					</tr>
				</table>
			</logic:present>
			
		</html:form>

	</body>
	
</html>