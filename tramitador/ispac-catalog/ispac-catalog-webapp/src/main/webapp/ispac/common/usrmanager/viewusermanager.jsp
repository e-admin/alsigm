<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script language='JavaScript' type='text/javascript'><!--

	function noCheckedInputRadio(url) {	
	
		var noSelect = true;
		
		var elementos = document.getElementsByName("uid");
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}
		
		elementos = document.getElementsByName("multibox");
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}
		
		
		
		if (noSelect) {
			jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
		else {
			var frm = document.forms[0];		
			frm.action = url;
			
			frm.submit(url);
		}
	}
		function  hideShowFrame(target, action) {
			document.getElementById("move").style.visibility="hidden";
			document.getElementById("move").style.top="0px";
			document.getElementById("move").style.left="0px";
			window.parent.showFrame(target, action,'' ,'') ;
			
			
			
			
}
//--></script>
<div id="move">
<bean:define id="urlparams" name="URLParams"/>

<c:url value="/entrySearch.do" var="_search">
	<c:param name="view" value="showSearch"></c:param>


</c:url>
<div class="encabezado_ficha">
	<div class="titulo_ficha">
	<logic:present parameter="captionkey">
	
		  	<bean:parameter id="caption" name="captionkey"/>
			<h4><bean:message name="caption"/></h4>
		
	</logic:present>
	
		
		<div class="acciones_ficha">
			
			<c:url  var="urlviewUsersManager" value="/viewUsersManager.do?">
			</c:url> 
			<a class="btnOk" href="javascript:noCheckedInputRadio('<c:out value="${urlviewUsersManager}" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>')">
				<nobr><bean:message key="forms.button.accept"/></nobr>
			</a>
		
			<a class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		
		</div>
	</div>
</div>




<c:if test="${!(requestScope['noviewgroups'] == 'true')}">

	<table cellspacing="4" border="0" width="100%">
		<tr>
			<td class="boldlabel">&nbsp;&nbsp;
				<logic:equal scope="request" name="type" value="groups">
					<bean:message key="catalog.supervision.titleNavigator.groups"/>
				</logic:equal>
		
				<logic:equal scope="request" name="type" value="organization">
					<bean:message key="catalog.supervision.titleNavigator.organization"/>
				</logic:equal>
				
			</td>
		</tr>
	</table>
	
	<div id="navmenu">
		<ul class="actionsMenuList">			
			<li>
				<c:url var="urlOrganizationView" value="/viewUsersManager.do">
					<c:param name="type" value="organization"/>
				</c:url>	
				<a href='<c:out value="${urlOrganizationView}" escapeXml="false"/><c:out value="&" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>'>
					<nobr><bean:message key="catalog.supervision.organization"/></nobr>
				</a>
			</li>			
			<li>
				<c:url  var="urlGroupsView" value="/viewUsersManager.do">
					<c:param name="type" value="groups"/>
				</c:url>
				<a href='<c:out value="${urlGroupsView}" escapeXml="false"/><c:out value="&" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>'>
					<nobr><bean:message key="catalog.supervision.groups"/></nobr>
				</a>
			</li>
			
			<li><a href="javascript:hideShowFrame('workframesearch', '<c:out value="${_search}" escapeXml="false" /><c:out value="&actionSetResponsibles=/viewUsersManager.do&" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>')">
			<bean:message key="search.button"/></a></li>
		</ul>
	</div>	
</c:if>

<div id="navmenu">
	<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>

<html:form action='/viewUsersManager.do'>

	<table cellpadding="0" cellspacing="0" width="95%" align="center">
		<tr>
			<td>
				<table width="100%" class="tableborder">			
					<tr>
						<td>
						
							<logic:present name="ancestors">
							
								<table cellpadding="6px" cellspacing="0">
									<tr>
										<td class="ldapentryGroupOrg">
										
											<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
												<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
													<logic:equal name="format" property="fieldType" value="ANCESTOR">
														<logic:equal name="cnt" value="0"><span style='color:#4477aa;font-weight:bold;'><%=format.formatProperty(ancestor)%></span></logic:equal>
														<logic:notEqual name="cnt" value="0"><c:out value=","/>&nbsp;&nbsp;
															<html:link action='<%=format.getUrl() + "&" + urlparams%>' 
																	   styleClass="resplink" 
																	   paramId='<%=format.getId()%>' 
																	   paramName="ancestor"
													  				   paramProperty='<%=format.getPropertyLink() %>'>
																<%=format.formatProperty(ancestor)%>
															</html:link>
														</logic:notEqual>
													</logic:equal>
												</logic:iterate>
											</logic:iterate>
											
										</td>
									</tr>
								</table>
								
							</logic:present>
		
							
							<div class="stdform">
								
								<c:if test="${!empty requestScope.items}">
								
									<display:table name="items" 
												   id="item" 
												   export="false" 
												   class="tableDisplayUserManager"
								  				   sort="list" 
								  				   requestURI='<%= request.getContextPath()+"/viewUserManager.do" %>'
								  				   >
							
										<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
										
											<logic:equal name="format" property="fieldType" value="ITEMLINK">			
							           		
												<display:column titleKey='<%=format.getTitleKey()%>' 
																media='<%=format.getMedia()%>' 
																headerClass='<%=format.getHeaderClass()%>'
																sortable='<%=format.getSortable()%>'
																decorator='<%=format.getDecorator()%>'
																class='<%=format.getColumnClass()%>'
																comparator='<%=format.getComparator()%>'>
														
													<bean:define name="item" id="uid" property="property(UID)"/>	
													<c:choose>
														<c:when test="${requestScope['onlyselectusers'] == 'true'}">
															<logic:equal name="item" property="property(USER)" value="true">
																<logic:notPresent name="selVarios">															
																	<input type="radio" name="uid" value=<%=uid%>  > 
																</logic:notPresent>
																<logic:present name="selVarios">	
																	<input type="checkbox" value=<%=uid%> name="multibox"/>
																</logic:present>
															</logic:equal>
														</c:when>
		
														<c:otherwise>
															<logic:notPresent name="selVarios">															
																	<input type="radio" name="uid" value=<%=uid%>> 
																</logic:notPresent>
																<logic:present name="selVarios">	
																	<input type="checkbox" name="multibox" value=<%=uid%> />
																</logic:present>
														</c:otherwise>
													</c:choose>
													
													<logic:equal name="item" property="property(USER)" value="true">
													
													<img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
														<%=format.formatProperty(item)%>
														
													</logic:equal>
													
													<logic:equal name="item" property="property(GROUP)" value="true">
													
														<img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
														<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
										  					paramProperty='<%=format.getPropertyLink() %>'>
															<%=format.formatProperty(item)%>																		
														</html:link>
														
													</logic:equal>
								
													<logic:equal name="item" property="property(ORGUNIT)" value="true">
													
														<img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>									
														<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
										  					paramProperty='<%=format.getPropertyLink() %>'>
															<%=format.formatProperty(item)%>																		
														</html:link>
														
													</logic:equal>
					
												</display:column>
												
											</logic:equal>
								
										</logic:iterate>
										
										<display:setProperty name="css.tr.even" value="none"/>
										<display:setProperty name="css.tr.odd" value="none"/>
										<display:setProperty name="basic.show.header" value="false"/>
										
									</display:table>
									
								</c:if>
							</div>
						
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</html:form>
<div>

<script>
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>