<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
<%-- 
	<tr>
		<td align="right"><ispac:onlinehelp fileName="clone" image="img/help.gif" titleKey="header.help"/></td>
	</tr>
--%>	


	<tr>
		<td>
			<br/>
			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="blank">
						<table cellpadding="5" cellspacing="1" border="0" width="100%">
							<tr>
								<td height="5px" colspan="3"><html:errors/></td>
							</tr>
							<tr>
								<td class="menuhead">
									<bean:message key="msg.clone.summary"/> '<c:out value="${requestScope[appConstants.actions.NUM_EXP_SOURCE_CLONE]}"/>':<br/>
								</td>
							</tr>
							<tr>
								<td>
									<c:set var="_list" value="${appConstants.actions.NEW_EXPEDIENTS_LIST}"/>
									<jsp:useBean id="_list" type="java.lang.String"/>
									<logic:iterate id="exp" name='<%=_list%>'>
										<c:url value="selectAnActivity.do" var="_link">
											<c:param name="numexp">
												<bean:write name="exp"/>
											</c:param>
										</c:url>
										<a href='<c:out value="${_link}"/>' class="tdlink"><bean:write name="exp"/></a><br/>
									</logic:iterate>
									
									<logic:empty name='<%=_list%>'>
										<bean:message key="msg.clone.summary.error"/>
									</logic:empty>
								</td>
							</tr>
							<tr>
								<td height="5px" colspan="3"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>