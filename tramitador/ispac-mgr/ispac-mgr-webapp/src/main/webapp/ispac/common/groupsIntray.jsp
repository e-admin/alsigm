<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%
    String register = request.getParameter( "register");
%>
<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script>

			var register = <%= register %>;

			function DistributeIntray( type, uid)
			{
				var url = '<%=request.getContextPath()%>'
								+ "/distributeIntray.do"
								+ "?type=" + type
								+ "&register=" + register;

				if (uid != null)
				{
					url += "&uid="+ uid;
				}

				document.defaultForm.action = url;
				document.defaultForm.submit();
			}

			function Distribute( uid)
			{
				var url = '<%=request.getContextPath()%>'
								+ "/distribute.do"
								+ "?uid=" + uid
								+ "&register=" + register;

				top.hideFrame("workframe",'<ispac:rewrite page="wait.jsp"/>');

				document.defaultForm.target = "ParentWindow";
				document.defaultForm.action = url;
				document.defaultForm.submit();
			}
    </script>
  </head>
  <body>
	<html:form action="distributeIntray.do">
		<table align="center" cellpadding="0" cellspacing="0" border="0" width="80%">
			<tr>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="32px"/></td>
			</tr>
			<tr>
				<td>
					<div id="header">
					  <ul id="menuList">
					    <li>
					    	<html:link href="javascript:DistributeIntray('unit',null);" title="Organizaci&oacute;n">
					    		<bean:message key="delegate.org"/>
					    	</html:link></li>
					    <li class="selected">
								<html:link href="javascript:DistributeIntray('group',null);" title="Grupos">
					    		<bean:message key="delegate.group"/>
								</html:link></li>
					  </ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="1" border="0" width="100%" class="tab">
						<tr>
							<td class="titleTab" height="16px">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td width="8px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px" width="8px"/>
										<td width="100%" class="titleTab">
											<logic:present name="groups">
												<img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px" width="8px"/>
											</logic:present>
											<logic:notPresent name="groups">
												<table cellpadding="4" cellspacing="0">
													<tr>
														<td width="14px" valign='middle'>
															<img src='<ispac:rewrite href="img/actual2.gif"/>' align="center" width="14px" height="12px"/></td>
														<td class="titleTab">
															<bean:write name="group" />
														</td>
													</tr>
												</table>
											</logic:notPresent>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="blank">
		          	<table cellpadding="4" cellspacing="0" border="0" width="90%" align="center">
		          		<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>'></td></tr>
									<tr>
										<td>
											<div class="scroll">
												<table>
													<logic:notPresent name="groups">
														<logic:empty name="users">
															<tr>
																<td colspan="4" class="elementTab"><bean:message key="delegate.message.nouser"/></td>
															</tr>
														</logic:empty>
														<logic:iterate id="user" name="users" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
															<%
																String _uid = user.getProperty("UID").toString();
																String _action = "javascript:Distribute('" + _uid + "');";
															%>
															<tr>
																<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
																<td width='17px'>
																	<img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
																<td align="left" class="elementTab" title='<%= user.getProperty("NAME") %>'>
																	<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																		<logic:equal name="format" property="property" value="NAME">
											    							<%=format.formatProperty(user)%>
																		</logic:equal>
																	</logic:iterate>
																</td>

																<td title="delegar">
																	<html:link href='<%= _action %>' >
																		<img src='<ispac:rewrite href="img/delegar.gif"/>' width="44px" height="11px" border="0"/>
																	</html:link>
																</td>
															</tr>
														</logic:iterate>
													</logic:notPresent>
													<logic:present name="groups">
														<logic:empty name="groups">
															<tr>
																<td colspan="4" class="elementTab"><bean:message key="delegate.message.nogroup"/></td>
															</tr>
														</logic:empty>
														<logic:iterate id="groupId" name="groups" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
															<%
																String uid = groupId.getProperty("UID").toString();
																String action = "javascript:DistributeIntray('group','" + uid + "');";
																String distribute = "javascript:Distribute('" + uid + "');";
															%>
															<tr>
																<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
																<td width='17px'><img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
																<td align="left" class="elementTab" title='<%= groupId.getProperty("NAME") %>'>
																	<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																		<logic:equal name="format" property="property" value="NAME">
																			<html:link href='<%= action %>' styleClass="elementTab">
											    							<%=format.formatProperty(groupId)%>
																			</html:link>
																		</logic:equal>
																	</logic:iterate>
																</td>
																<td title="delegar">
																	<html:link href='<%= distribute %>'>
																		<img src='<ispac:rewrite href="img/delegar.gif"/>' width="44px" height="11px" border="0"/>
																	</html:link>
																</td>
															</tr>
														</logic:iterate>
													</logic:present>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="4" cellpadding="0">
						<tr>
							<td>
					  		<html:checkbox property="property(REGISTER:ARCHIVE)"/>
							</td>
							<td>Archivar</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
			</tr>
			<tr>
				<td>
					<table border="0" cellpadding="4" cellpadding="0">
						<tr>
							<td>Mensaje</td>
							<td><html:text property="property(REGISTER:MESSAGE)" styleClass="input" size="64" maxlength="128"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
			</tr>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" align="center" valign="middle">
						<tr>
							<td>
								<input type="button" value='<bean:message key="exit.button"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</html:form>
  </body>
</html>
