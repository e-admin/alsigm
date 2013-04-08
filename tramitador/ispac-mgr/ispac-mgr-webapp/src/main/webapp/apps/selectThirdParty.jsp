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
			String nombre = request.getParameter("nombre");
			if (nombre == null) nombre = "";
			String apellidos = request.getParameter("apellidos");
			if (apellidos == null) apellidos = "";
			
			// Nombre la variable de sesión donde se han salvado
			// los parámetros que utiliza el tag actionframe
			String parameters = request.getParameter("parameters");
			if (parameters == null) parameters = "";
			// Acción que se está realizando
			String option = request.getParameter("option");
			if (option == null) option = "select";
		%>
		
		<ispac:rewrite id="selectThirdParty" action="selectThirdParty.do"/>
		
		<script language='JavaScript' type='text/javascript'><!--
		
			function search() {
			
				document.forms.selectthirdparty.thirdparty.value = "";
				document.forms.selectthirdparty.action = '<%=selectThirdParty%>';
				document.forms.selectthirdparty.submit();
			}
			
			function selectthirdparty(id) {
			
				document.forms.selectthirdparty.thirdparty.value = id;
				document.forms.selectthirdparty.action = '<%=selectThirdParty%>';
				document.forms.selectthirdparty.submit();
			}
			
		//--></script>
		
	</head>

	<body>
	
		<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
			<tr>
				<td width="100%" class="blank">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td class="title" height="18px">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
										<td width="100%" class="menuhead"><bean:message key="selectThirdParty.title"/></td>
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
										
											<form name="selectthirdparty" method="post">
											
												<input type="hidden" name="option" value='<%= option%>'/>
												<input type="hidden" name="parameters" value='<%= parameters%>'/>
												<input type="hidden" name="thirdparty" />
												
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
																		<bean:message key="selectThirdParty.form.firstname"/>
																		<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
																	<td><input class="input" type="text" name="nombre" value='<%= nombre %>'/></td>
																</tr>
																<tr>
																	<td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																</tr>
																<tr>
																	<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
																	<td height="20px" class="formsTitle">
																		<bean:message key="selectThirdParty.form.lastname"/>
																		<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
																	<td><input class="input" type="text" name="apellidos" value='<%= apellidos %>'/></td>
																</tr>
															</table>
															--%>													
														</td>
														<td width="50%">
															<table border="0" cellspacing="0" cellpadding="0" align="center" valign="middle">
																<tr>
																	<td>
																	<%--
																	<input type="button" value="Buscar" class="form_button_white" onclick="javascript:search();"/>
																	--%>
																	</td>
																	<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="8px"/></td>
																	<td>
																		<input type="button" value="Salir" class="form_button_white" onclick='<ispac:hideframe/>'/>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
												</table>
												
											</form>
											
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
	
		<logic:present name="ParticipantList">
		
			<table cellpadding="5" cellspacing="0" border="0" width="100%">
				<tr>
				  	<td>
				    	<table cellpadding="0" cellspacing="1" border="0" width="100%" >
					      	<tr>
					        	<td class="box">
						          	<table cellpadding="1" cellspacing="1" border="0" width="100%" id="t1" class="sortable">
										<tr>
											<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
								              	<td class="title" align="center" width='<%= format.getWidth() %>' height="18px">
								              		<bean:write name="format" property="title"/>
								              	</td>
										  	</logic:iterate>
									  	</tr>
						            	<logic:iterate id="participant" name="ParticipantList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
						            		<bean:define name="participant" property="property(ID)" id="participantId"/>
						              		<tr>
						                		<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
										          	<logic:equal name="format" property="fieldType" value="LINK">
									              		<td class="element" align="center">
										              		<a class="element" href="javascript:selectthirdparty('<%= participantId.toString() %>');">
										              			<%=format.formatProperty(participant)%>
												            </a>
											          	</td>
										          	</logic:equal>
										          	<logic:equal name="format" property="fieldType" value="LIST">
						                				<td class="element" align="center">
						                					<%=format.formatProperty(participant)%>
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
	
	</body>

</html>