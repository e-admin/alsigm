<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>

<script>
	function submitPermission(myform)
	{
    	var checked = false;
		if (typeof myform.uids == 'undefined')
		{
			jAlert('<bean:message key="catalog.searchfrm.permission.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			return;
		}

		mymultibox = myform.uids;

		// cuando tenemos un checkbox
		if (typeof mymultibox.length == 'undefined')
		{
      		if (mymultibox.checked)
      			checked = true;
      	}
      	else
	    {
	    	for (var i=0; i < mymultibox.length; i++)
		        if (myform[i].checked)
		        	checked = true;
	    }
	    if (checked){
	    	myform.submit();
	    } else {
			jAlert('<bean:message key="catalog.searchfrm.permission.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	function  hideShowFrame(target, action) {


			document.getElementById("move").style.visibility="hidden";
			document.getElementById("move").style.top="0px";
			document.getElementById("move").style.left="0px";
			window.parent.document.getElementById(target).style.display="block";
			window.parent.showFrame(target, action,'' ,'') ;
					
	}

	$(document).ready(function(){
		$("#move").draggable();
	});	
</script>
<div id="move">
<c:url value="showCTSearchForm.do" var="_return">
	<c:param name="view">
		<bean:write name="view"/>
	</c:param>

	<c:param name="id">
		<bean:write name="id"/>
	</c:param>
	
	<logic:present name="uidGroup">
		<c:param name="uidGroup">
			<bean:write name="uidGroup" />
		</c:param>
	</logic:present>
</c:url>
<jsp:useBean id="_return" type="java.lang.String"/>
<c:url value="/entrySearch.do" var="_search">
	<c:param name="view" value="showSearch">
	</c:param>

	<c:param name="id">
		<bean:write name="id"/>
	</c:param>
	
	
	<c:param name="mode" value="Multi"/>
	<c:param name="actionSetResponsibles" value="/addResponsiblesSearchForm.do"/>

</c:url>

<c:url value="${actionShow}" var="_showResponsiblesForSearchForm">
	<c:param name="view">
		<bean:write name="view"/>
	</c:param>
	<c:param name="id">
		<bean:write name="id"/>
	</c:param>
	<logic:present name="uidGroup">
		<c:param name="uidGroup">
			<bean:write name="uidGroup" />
		</c:param>
	</logic:present>
	<logic:present name="captionkey">
		<c:param name="captionkey">
			<bean:write name="captionkey"/>
		</c:param>
	</logic:present>
	<logic:present name="kindOfSuperv">
		<c:param name="kindOfSuperv">
			<bean:write name="kindOfSuperv"/>
		</c:param>
	</logic:present>
</c:url>
<jsp:useBean id="_showResponsiblesForSearchForm" type="java.lang.String"/>

<div class="encabezado_ficha">
	<div class="titulo_ficha">
	  <h4><bean:message key="catalog.searchfrm.permission.selectResponsibles"/></h4>
	<div class="acciones_ficha">
			
		<a  class="btnOk" href='javascript:submitPermission(document.selectForm);'><bean:message key="forms.button.accept"/></a>
		<a  class="btnCancel" href='javascript:parent.hideFrame("workframe","<ispac:rewrite page="wait.jsp"/>")'>
				<bean:message key="forms.button.cancel"/>
		</a>
		</div>
	</div>
</div>

<table cellspacing="4" border="0" width="100%">
	<tr>
		<td class="boldlabel">&nbsp;&nbsp;
			<c:set var="vTitleNavigator" value="${titleNavigator}"/>
			<jsp:useBean id="vTitleNavigator" type="java.lang.String"/> 
			<ispac:tooltipLabel labelKey='<%=vTitleNavigator%>' labelEnd='' tooltipKey='<%=vTitleNavigator+".tooltip"%>'/>
		</td>
	</tr>
</table>

<div id="navmenu">
	<ul class="actionsMenuList">
	
		<li><a href='<%=_showResponsiblesForSearchForm + "&supervDirView=organization"%>' id="current"><bean:message key="catalog.searchfrm.organization"/></a></li>
		<li><a href='<%=_showResponsiblesForSearchForm + "&supervDirView=groups"%>'><bean:message key="catalog.searchfrm.groups"/></a></li>
		<li><a href="javascript:hideShowFrame('workframesearch', '<c:out value="${_search}" escapeXml="false" />')">
			<bean:message key="search.button"/></a></li>
	</ul>
</div>

<div id="navmenu">
	<logic:messagesPresent>
		<div id="formErrorsMessage">
			<html:errors/>
		</div>
	</logic:messagesPresent>
</div>

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>


<table cellpadding="0" cellspacing="0" width="95%" align="center">

	<%-- Guardar las selecciones --%>
	<c:choose>
	<c:when test="${!empty action}">
		<c:set var="actionAdd" value="${action}"/>
	</c:when>
	<c:otherwise>
		<c:set var="actionAdd" value="addResponsiblesSearchForm.do"/>
	</c:otherwise>
	</c:choose>
	
	<c:url value="${actionAdd}" var="_addResponsiblesSearchForm">
		<c:param name="view">
			<bean:write name="view"/>
		</c:param>

		<c:param name="id">
			<bean:write name="id"/>
		</c:param>
		
		<logic:present name="uidGroup">
			<c:param name="uidGroup">
				<bean:write name="uidGroup" />
			</c:param>
		</logic:present>
		<logic:present name="captionkey">
			<c:param name="captionkey">
				<bean:write name="captionkey"/>
			</c:param>
		</logic:present>
		<logic:present name="kindOfSuperv">
			<c:param name="kindOfSuperv">
				<bean:write name="kindOfSuperv"/>
			</c:param>
		</logic:present>
	</c:url>
	<jsp:useBean id="_addResponsiblesSearchForm" type="java.lang.String"/>

	<!-- DIRECTORIO ORGANIZACION -->
	<logic:present name="supervAncestors">
	
		<% String dirUid = null; %>
		<tr>
			<td>
			
				<table width="100%" class="tableborder">

					<tr>
						<td>
						
							<!-- ANCESTROS -->
							<table cellpadding="6px" cellspacing="0">
								<tr>
									<td class="ldapentryGroupOrg">
									
										<logic:iterate id="ancestor" name="supervAncestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
												<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
													<logic:equal name="format" property="property" value="RESPNAME">
														<logic:equal name="cnt" value="0">
															<span style='color:#4477aa;font-weight:bold;'>
																<%=format.formatProperty(ancestor)%>
																<% dirUid = ancestor.getProperty("UID").toString(); %>
															</span>
														</logic:equal>
														<logic:notEqual name="cnt" value="0">
															<%out.print(',');%>&nbsp;&nbsp;
															<%
																String orgUrl2 = _showResponsiblesForSearchForm + "&supervDirView=organization";
																orgUrl2 += "&dirUid=" + ancestor.getProperty("UID");
															%>
															<html:link href='<%= orgUrl2 %>' styleClass="resplink">
																<%=format.formatProperty(ancestor)%>
															</html:link>
														</logic:notEqual>
													</logic:equal>
												</logic:iterate>
										</logic:iterate>
										
									</td>
								</tr>
							</table>

							<div class="stdform">

								<!-- USUARIOS Y UNIDADES ORGANIZATIVAS -->
								<html:form action='<%=_addResponsiblesSearchForm + "&supervDirView=organization&dirUid=" + dirUid%>'>

									<table class="tableDisplayUserManager">
									
										<logic:iterate id="user" name="supervUsers" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
											<tr>
												<td width='17px'>
													<html:multibox property="uids">
														<bean:write name='user' property='property(UID)' />
													</html:multibox>
												</td>
												<td width='17px'><img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
												<td align="left" class="ldapentry" title='<%= user.getProperty("RESPNAME") %>'>
													<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
														<logic:equal name="format" property="property" value="RESPNAME">
															<%=format.formatProperty(user)%>
														</logic:equal>
													</logic:iterate>
												</td>
											</tr>
										</logic:iterate>
										<logic:iterate id="orgunit" name="supervOrgunits" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
											<tr>
												<td width='17px'>
													<html:multibox property="uids">
														<bean:write name='orgunit' property='property(UID)' />
													</html:multibox>
												</td>
												<td width='20px'><img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
												<td align="left" class="ldapentry" title='<%= orgunit.getProperty("RESPNAME") %>'>
													<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
														<logic:equal name="format" property="property" value="RESPNAME">
															<%
																String orgURL3 = _showResponsiblesForSearchForm + "&supervDirView=organization";
																orgURL3 += "&dirUid=" + orgunit.getProperty("UID");
															%>
															<html:link href='<%= orgURL3 %>' styleClass="resplink">
																<%=format.formatProperty(orgunit)%>
															</html:link>
														</logic:equal>
													</logic:iterate>
												</td>
											</tr>
										</logic:iterate>
										
									</table>

								</html:form>
								
							</div>
								
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
	</logic:present>

	<!-- DIRECTORIO GRUPOS -->
	<logic:notPresent name="supervAncestors">
	
		<tr>
			<td>
			
				<table width="100%" class="tableborder">
				
					<tr>
						<td>
						
							<!-- GRUPO -->
							<table cellpadding="6px" cellspacing="0">
								<tr>
									<td class="ldapentryGroupOrg">
									
										<logic:present name="groupName">
											<span style='color:#4477aa;font-weight:bold;'>
												<bean:write name="groupName" />
											</span>
										</logic:present>
										
									</td>
								</tr>
							</table>
							
							<div class="stdform">
							
								<html:form action='<%=_addResponsiblesSearchForm + "&supervDirView=groups"%>'>
							
									<table class="tableDisplayUserManager">
									
										<logic:present name="supervUsers">
											<logic:iterate id="user" name="supervUsers" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
												<tr>
													<td width='17px'>
														<html:multibox property="uids">
															<bean:write name='user' property='property(UID)' />
														</html:multibox>
													</td>
													<td width='17px'><img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
													<td align="left" class="ldapentry" title='<%= user.getProperty("RESPNAME") %>'>
														<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
															<logic:equal name="format" property="property" value="RESPNAME">
																<%=format.formatProperty(user)%>
															</logic:equal>
														</logic:iterate>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
										<logic:present name="supervGroups">
											<logic:iterate id="group" name="supervGroups" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
												<tr>
													<td width='17px'>
														<html:multibox property="uids">
															<bean:write name='group' property='property(UID)' />
														</html:multibox>
													</td>
													<td width='17px'><img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
													<td colspan='2' align='left' class='ldapentry' title='<%= group.getProperty("RESPNAME") %>'>
														<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
															<logic:equal name="format" property="property" value="RESPNAME">
																<%
																	String grp = _showResponsiblesForSearchForm + "&supervDirView=groups";
																	grp += "&dirUid=" + group.getProperty("UID");
																%>
																<html:link href='<%= grp %>' styleClass="resplink">
																	<%=format.formatProperty(group)%>
																</html:link>
															</logic:equal>
														</logic:iterate>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
										
									</table>
									
								</html:form>
							
							</div>
						
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
	</logic:notPresent>
</table>
</div>
