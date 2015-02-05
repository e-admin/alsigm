<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script type="text/javascript">
<!--


	
	function goback() {
		document.defaultForm.method.value = "form";
		document.defaultForm.submit();
	}
	function selectOrgUnit(uidint) {
		document.defaultForm.action = '<c:url value="/producers.do"/>';
		document.defaultForm.method.value = "showOrgUnits";
		document.defaultForm.uidint.value = uidint;
		document.defaultForm.submit();
	}
	function noCheckedInputRadio(url)
	{	
		var noSelect=true;
		var elementos=document.getElementsByName("property(UID)");	
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}	
		if (noSelect) {
			jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			var frm = document.forms[0];		
			frm.action = url;
			frm.submit(url);
			
		}
	}
	
	$(document).ready(function(){
		$("#move").draggable();
	});
	
	

		
//-->
</script>
<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="procedure.card.producer.orgunit.select.caption"/></h4>
		<div class="acciones_ficha">
			<c:url  var="url" value="/producers.do">
				<c:param name="method" value="form"></c:param>
			</c:url> 
			<a href="#" id="btnOk" onclick="javascript:noCheckedInputRadio('<c:out value="${url}" escapeXml="false"/>');" class="btnOk"><bean:message key="forms.button.accept"/></a>
			<a href="#" id="btnCancel" onclick="javascript:goback();"class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>

<html:form action="/producers.do">

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>


	<input type="hidden" name="method" value="showOrgUnits"/>
	<input type="hidden" name="retEntityId" value='<c:out value="${param.retEntityId}"/>'/>
	<input type="hidden" name="retKeyId" value='<c:out value="${param.retKeyId}"/>'/>
	<input type="hidden" name="block" value='<c:out value="${param.block}"/>'/>
	<input type="hidden" name="uidint" />
	<html:hidden property="property(COD_PCD)"/>
	<html:hidden property="property(ID_UNIDAD_TRAMITADORA)"/>
	<html:hidden property="property(NOMBRE_UNIDAD_TRAMITADORA)"/>
	<html:hidden property="property(FECHA_INI_PROD)"/>


<table cellpadding="0" cellspacing="0" width="95%" align="center">
	<tr>
		<td>
			<table width="100%" class="tableborder">			
				<tr>
					<td>	
				
						<logic:present name="ancestors">
						
							<table cellpadding="6" cellspacing="0">
								<tr>
									<td class="ldapentryGroupOrg">
			
										<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.producers.vo.Producer" indexId="cnt">
											<logic:equal name="cnt" value="0">
												<span style='color:#4477aa;font-weight:bold;'>
													<bean:write name="ancestor" property="name"/>
												</span>
											</logic:equal>
											<logic:notEqual name="cnt" value="0">
												<c:out value=","/>
												<bean:define id="uidint" name="ancestor" property="id"/>
												<a href="javascript:selectOrgUnit('<c:out value="${uidint}"/>')" 
														class="respLink">
													<bean:write name="ancestor" property="name"/>
												</a>
											</logic:notEqual>
										</logic:iterate>
										
									</td>
								</tr>
							</table>
							
						</logic:present>
				
						<div class="stdform">
						
							<c:if test="${!empty requestScope.responsibles}">
							
								<display:table name="responsibles" 
											   id="responsible" 
											   export="false" 
											   class="tableDisplayUserManager" 
											   sort="list"
											   cellpadding="4px">
						
									<display:column titleKey="procedure.card.producer.orgunit.org"
													headerClass="sortable"
													sortable="false">
													
										<bean:define id="uidint" name="responsible" property="id"/>
										<input type="radio" name="property(UID)" value=<%=uidint%>>
										<a href="javascript:selectOrgUnit('<c:out value="${uidint}"/>')" 
												class="respLink">
											<img src='<ispac:rewrite href="img/org.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/>																
								  			<bean:write name="responsible" property="name"/>
										</a>
										
									</display:column>
									
									<display:setProperty name="css.tr.even" value="none"/>
									<display:setProperty name="css.tr.odd" value="none"/>
																		
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
</div>
