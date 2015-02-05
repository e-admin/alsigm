<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<% request.setCharacterEncoding("ISO-8859-1"); %>

<table cellspacing="5" border="0" width="100%"><tr><td>
	<table class="box" width="90%" align="center" border="0" cellspacing="1" cellpadding="6">
	<tr>
		<td class="divCabecera">
			<div class="divTexto">
				<span class="label"><bean:message key="catalog.supervision.titleNavigator.title"/>:</span>
				<bean:message key="catalog.supervision.titleNavigator.groups"/>
			</div>
		</td> 
	</tr>
	<tr>
		<td class="divCabecera">
			<table cellpadding="0" cellspacing="0" width="90%" align="center">
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' height='5px'></td></tr>
				<logic:present name="groups">
					<tr>
						<td>
							<table cellpadding="4" cellspacing="0">
								<tr>
									<td class='ldapentry'>
										<span style='color:black;font-weight:bold;'> 
											<bean:message key="catalog.supervision.titleNavigator.noSelected"/>
										</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<div class="scroll">
								<table>
									<logic:iterate id="group" name="groups" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
										<tr>
											<td width='17px'><img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
											<td colspan='2' align='left' class='ldapentry' title='<%= group.getProperty("RESPNAME") %>'>
												<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
													<logic:equal name="format" property="property" value="RESPNAME">
														<%
															String urlGroup = "showInfoEntry.do?view=groups&uid=" + group.getProperty("UID");
														%>
														<html:link  href='<%= urlGroup %>' styleClass="resplink" >
															<%=format.formatProperty(group)%>
														</html:link>
													</logic:equal>
												</logic:iterate>
											</td>
										</tr>
									</logic:iterate>
								</table>
							</div>
						</td>
					<tr>
				</logic:present>
				<logic:notEqual name='isGroups' value='true'>
					<tr>
						<td>
							<table cellpadding="4" cellspacing="0">
								<tr>
									<td class='ldapentry'>
										<logic:present name="groupName">
											<c:url value="showInfoEntry.do?view=groups" var="_showInfoEntry">
												<c:param name="uid">
													<bean:write name="uidGroup" />
												</c:param>
											</c:url>
											/ <a href='<c:out value="${_showInfoEntry}" />' class="resplink">
												<bean:write name="groupName" />
											</a>
										</logic:present>
										<span style='color:black;font-weight:bold;'>
											/ <bean:write name="name" />
										</span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<div class='scroll'>
								<table>
									<logic:present name="users">
										<logic:iterate id="user" name="users" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
											<tr>
												<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
												<td width='17px'><img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
												<td align="left" class="ldapentry" title='<%= user.getProperty("RESPNAME") %>' >
													<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
														<logic:equal name="format" property="property" value="RESPNAME">
															<%
																String urlUser = "showInfoEntry.do?view=groups&uid=" + user.getProperty("UID") + "&uidGroup=" + request.getAttribute("uidGroup");
															%>
															<html:link href='<%= urlUser %>' styleClass="resplink" >
																	<%=format.formatProperty(user)%>
															</html:link>
														</logic:equal>
													</logic:iterate>
												</td>
											</tr>
										</logic:iterate>
									</logic:present>
								</table>
							</div>
						</td>
					</tr>
				</logic:notEqual>
			</table>
		</td>
	</tr>
	</table>
</td></tr></table>
