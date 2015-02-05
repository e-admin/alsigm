<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table cellspacing="5" border="0" width="100%"><tr><td>
	<table class="box" width="90%" align="center" border="0" cellspacing="1" cellpadding="6">
	<tr>
		<td class="divCabecera">
			<div class="divTexto">
				<span class="label"><bean:message key="catalog.supervision.titleNavigator.title"/>:</span>
				<bean:message key="catalog.supervision.titleNavigator.organization"/>
			</div>
		</td> 
	</tr>
	<tr>
		<td class="divCabecera">
			<table cellpadding="0" cellspacing="0" width="90%" align="center">
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' height='5px'></td></tr>
				<tr>
					<td>
						<table cellpadding="4" cellspacing="0">
							<tr>
								<td class="ldapentry">
									
									<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
											<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
												<logic:equal name="format" property="property" value="RESPNAME">
													<c:if test="${cnt==numAncestors-1}">
														<span style='color:black;font-weight:bold;'>
															<%out.print('/');%>&nbsp;&nbsp;<%=format.formatProperty(ancestor)%>
														</span>
													</c:if>
													<c:if test="${cnt<numAncestors-1}">
														<%String url = "showInfoEntry.do?view=organization&uid=" + ancestor.getProperty("UID");%>
														<%out.print('/');%>&nbsp;&nbsp;<html:link href='<%= url %>' styleClass="resplink"><%=format.formatProperty(ancestor)%></html:link>
													</c:if>
												</logic:equal>
											</logic:iterate>
									</logic:iterate>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<div class="scroll">
							<table>
								<logic:iterate id="user" name="users" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
									<tr>
										<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
										<td width='17px'><img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="16px" border="0"/></td>
										<td align="left" class="ldapentry" title='<%= user.getProperty("RESPNAME") %>'>
											<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
												<logic:equal name="format" property="property" value="RESPNAME">
													<%
														String urlUser = "showInfoEntry.do?view=organization&uid=" + user.getProperty("UID");
													%>
													<html:link href='<%= urlUser %>' styleClass="resplink" >
													<%=format.formatProperty(user)%>
													</html:link>
												</logic:equal>
											</logic:iterate>
										</td>
									</tr>
								</logic:iterate>
								<logic:iterate id="orgunit" name="orgunits" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
									<tr>
										<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
										<td width='17px'><img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="16px" border="0"/></td>
										<td align="left" class="ldapentry" title='<%= orgunit.getProperty("RESPNAME") %>'>
											<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
												<logic:equal name="format" property="property" value="RESPNAME">
													<%
														String urlOrgUnit = "showInfoEntry.do?view=organization&uid=" + orgunit.getProperty("UID");
													%>
													<html:link href='<%= urlOrgUnit %>' styleClass="resplink">
														<%=format.formatProperty(orgunit)%>
													</html:link>
												</logic:equal>
											</logic:iterate>
										</td>
									</tr>
								</logic:iterate>
							</table>
						</div>
					</td>
				</tr>
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' height='10px'></td></tr>
			</table>
		</td>
	</tr>
	</table>
</td></tr></table>

