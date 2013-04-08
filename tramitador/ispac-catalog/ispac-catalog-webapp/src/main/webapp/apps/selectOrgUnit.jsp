<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script>
	function search(uid) {
		document.selectObjForm.method.value = "show";
		document.selectObjForm.selectcode.value = uid;
		document.selectObjForm.submit();
	}
	function select(uid) {
		document.selectObjForm.method.value = "set";
		document.selectObjForm.selectcode.value = uid;
		document.selectObjForm.submit();
	}
</script>


<div id="navmenutitle">
	<bean:message name="CaptionKey"/>
</div>

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</li>
	</ul>
</div>

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>

<div id="bodycontainer">

	<html:form action="/selectOrgUnit.do" method="post">
	
		<html:hidden property="codetable"/>
		<html:hidden property="codefield"/>
		<html:hidden property="valuefield"/>
		<html:hidden property="decorator"/>
		<html:hidden property="parameters"/>
		<html:hidden property="querystring"/>
		<html:hidden property="caption"/>
		<html:hidden property="field"/>
		<html:hidden property="sqlquery"/>
		<input type="hidden" name="selectcode"/>
		<input type="hidden" name="method" value="show"/>

		<table cellpadding="0" cellspacing="0" width="95%" align="center">
			<tr>
				<td width="85%">
					<table width="100%" class="tableborder">			
						<tr>
							<td>	
		
								<logic:present name="ancestors">
									<table cellpadding="4" cellspacing="0">
										<tr>
											<td class="ldapentryGroupOrg">
												<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
													<logic:equal name="cnt" value="0">
														<bean:write name="ancestor" property="property(NAME)"/>
													</logic:equal>
													<logic:notEqual name="cnt" value="0">
														<c:out value=","/>
														<bean:define id="uidint" name="ancestor" property="property(UID)"/>
														<a href="javascript:search('<c:out value="${uidint}"/>')" 
																class="displayLink">
															<bean:write name="ancestor" property="property(NAME)"/>
														</a>
													</logic:notEqual>
												</logic:iterate>
											</td>
										</tr>
									</table>				
								</logic:present>
					
								<div class="stdform">
									<display:table name="responsibles" id="responsible" 
											export="false" class="tableDisplayUserManager" sort="list">
											
							
										<display:column titleKey="procedure.card.producer.orgunit.org"
														headerClass="sortable"
														sortable="false">
											<bean:define id="uidint" name="responsible" property="property(UID)"/>
												<a href="javascript:search('<c:out value="${uidint}"/>')" 
														class="displayLink">
													<img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>																		
									  				<bean:write name="responsible" property="property(NAME)"/>
												</a>
										</display:column>
										<display:column title="" 
														headerClass="sortable"
														sortable="false">
												<a href="javascript:select('<c:out value="${uidint}"/>')" 
														class="aActionButton">
									  			<bean:message key="select.button"/>
											</a>
										</display:column>
									</display:table>
								</div>
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</html:form>
	
</div>