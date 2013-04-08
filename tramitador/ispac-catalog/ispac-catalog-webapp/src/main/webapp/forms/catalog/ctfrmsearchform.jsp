<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO" %>


<c:set var="entityId" value="-1"/>
<c:set var="regId" value="-1"/>
<logic:present parameter="entityId">
	<c:set var="entityId" value="${param.entityId}"/>
</logic:present>


<logic:present parameter="regId">
<c:set var="regId" value="${param.regId}"/>
</logic:present>

<jsp:useBean id="entityId" type="java.lang.String"/>
<jsp:useBean id="regId" type="java.lang.String"/>

<script>
	var controlPaso = true;
	var controlFile = "";

	function showReports(){
		var actionURL = '<c:url value="/frmSearchReportAction.do?method=show"/>';
		actionURL = actionURL + "&entityId=" + <%=entityId%>;
		actionURL = actionURL + "&regId=" + <%=regId%>;		 
		actionURL = actionURL + '&frmId=' + getValueOfElementByName('property(ID)');
		document.forms[0].action = actionURL;
		document.forms[0].submit();
			

	}
	function getValueOfElementByName(name){
	
		objects = window.document.getElementsByName(name);
		return objects[0].value;
	}
	
	function ispac_loadFile()
	{
		if (controlPaso) {
			if (document.getElementById("FileUp").value != "" && document.getElementById("FileUp").value != controlFile) {
				controlFile = document.getElementById("FileUp").value;
				controlPaso = false;
				document.forms[0].action ='<%= request.getContextPath() + "/loadFile.do"%>';
				document.forms[0].submit();
			}
		}
		else
		{
			controlFile = document.getElementById("FileUp").value;
			controlPaso = true;
		}
	}

		function deletePermission(myform)
		{
	    	var checked = false;
			if (typeof myform.uids == 'undefined')
			{
				jAlert('<bean:message key="catalog.searchfrm.alert.noResponsibles"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				
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
		    if (checked)
		    	myform.submit();
		    else
				jAlert('<bean:message key="catalog.searchfrm.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}	
</script>



<div id="navmenutitle">
	<bean:message key="form.search.mainTitle"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.search.subtitle"/>
</div>
<div id="navmenu" >
	<ul class="actionsMenuList">
		<logic:equal name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTFrmSearchList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
			</li>
		
		</logic:equal>
		<logic:notEqual name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href="javascript:showReports()">
					<nobr><bean:message key="forms.button.informe"/></nobr>
				</a>
			</li>
			<li>
				<a href='<%=request.getContextPath() + "/showCTFrmSearchList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
			</li>
			<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
		  	<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deleteCTEntity.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>
		  	</li>
		  	</ispac:hasFunction>
	  	</logic:notEqual>
	</ul>
</div>
<html:form action='/showCTSearchForm.do' enctype='multipart/form-data'>

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación
 	--%>
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>
	
<c:set var="idFrm" property="property(ID)"></c:set>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td>
												<logic:messagesPresent>
													<div id="formErrorsMessage">
														<bean:message key="forms.errors.formErrorsMessage"/>
													</div>
												</logic:messagesPresent>
												</td>
											</tr>
											<tr>
												<td>
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0">
																	<logic:notEqual name="uploadForm" property="key" value="-1">
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.search.propertyLabel.id" tooltipKey="form.search.propertyLabel.id.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																				<div id="formErrors">
																					<html:errors property="property(ID)"/>
																				</div>
																			</td>
																		</tr>
																	</logic:notEqual>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.search.propertyLabel.description" tooltipKey="form.search.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" styleClass="inputSelectS" readonly="false" maxlength="250"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.search.propertyLabel.author" tooltipKey="form.search.propertyLabel.author.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(AUTOR)" styleClass="inputSelectS" readonly="false" maxlength="100"/>
																				<div id="formErrors">
																					<html:errors property="property(AUTOR)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.search.propertyLabel.date" tooltipKey="form.search.propertyLabel.date.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(FECHA)" styleClass="inputReadOnly" size="25" readonly="true" maxlength="10"/>
																			
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<%-- Seleccion del tipo de Formulario de busqueda--%>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.type" tooltipKey="form.signProcess.propertyLabel.type.tooltip"/>
																			</td>
																			<td height="20">																				
																				&nbsp;&nbsp;<html:select property="property(TIPO)" styleClass="input">
																									<html:option value='<%=""+CTFrmBusquedaOrgDAO.GENERIC_TYPE%>' key="form.signProcess.generic"/>
																									<html:option value='<%=""+CTFrmBusquedaOrgDAO.SPECIFIC_TYPE%>' key="form.signProcess.specific"/>
																								</html:select>&nbsp;&nbsp;<bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(TIPO)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.search.propertyLabel.searchForm" tooltipKey="form.search.propertyLabel.searchForm.tooltip"/>
																			</td>
																			<td height="20">
																				<%--
																				&nbsp;&nbsp;<html:textarea property="property(FRM_BSQ)" styleClass="input" readonly="false" cols="72" rows="7"
																					styleId="texta" onkeypress="javascript:maxlength('texta', 16000)"/><br>
																				--%>
																				&nbsp;&nbsp;<html:textarea property="property(FRM_BSQ)" styleClass="input" readonly="false" cols="72" rows="7" styleId="texta"/><br>
																				<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
																				&nbsp;&nbsp;<html:file styleClass="input" property="uploadFile" styleId="FileUp" onchange="javascript:ispac_loadFile();"/><br>
																				</ispac:hasFunction>
																				<div id="formErrors">
																					<html:errors property="property(FRM_BSQ)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																	</html:form>																		
																		<logic:equal name="uploadForm" property="property(TIPO)" value='<%=""+CTFrmBusquedaOrgDAO.SPECIFIC_TYPE%>'>
																			<c:url value="deletePermissionObjectOrganization.do" var="_deletePermissionObjectOrganization">
																				<c:param name="id">
																					<bean:write name="uploadForm" property="property(ID)"/>
																				</c:param>
																			</c:url>
																			<jsp:useBean id="_deletePermissionObjectOrganization" type="java.lang.String"/>
																		
																		
																			<c:url value="showResponsiblesForSearchForm.do" var="_showResponsiblesForSearchForm">
																				<c:param name="view">
																					<logic:empty name="view">
																						organization
																					</logic:empty>
																					<logic:notEmpty name="view">
																						<bean:write name="view"/>
																					</logic:notEmpty>
																				</c:param>
																				<c:param name="id">
																					<bean:write name="uploadForm" property="property(ID)"/>
																				</c:param>
																				
																				
																				<logic:present name="uidGroup">
																					<c:param name="uidGroup">
																						<bean:write name="uidGroup" />
																					</c:param>
																				</logic:present>
																			</c:url>
																			<jsp:useBean id="_showResponsiblesForSearchForm" type="java.lang.String"/>
																		
																			<html:form action='<%=_deletePermissionObjectOrganization%>'>
																		
																				<%-- Asociacion de formulario a objeto de Organizacion --%>
																				<tr><td height="10px" colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
																				<tr>
																					<td class="boldlabel" colspan="2"><bean:message key="catalog.searchfrm.permission"/>:</td>
																				</tr>
																				<tr>
																					<td colspan="2">
																						<div class="buttonList">
																							<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
																							<ul>
																								<li>
																									<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showResponsiblesForSearchForm%>'/>',640,480)"><nobr><bean:message key="forms.button.add"/></nobr></a>
																								</li>
																								<c:choose>
																									<c:when test="${!empty CTFrmSearchOrgList}">
																										<li>
																											<a href="javascript:deletePermission(document.selectForm);" > 
																												<nobr><bean:message key="forms.button.delete"/></nobr>
																											</a>
																										</li>
																									</c:when>
																									<c:otherwise>
																										<li style="background-color: #ddd; color: #aaa; cursor: default;">
																											<nobr><bean:message key="forms.button.delete"/></nobr>
																										</li>
																									</c:otherwise>
																								</c:choose>
																			
																			
																							</ul>
																							</ispac:hasFunction>
																						</div>
																					</td>
																				</tr>
																				<tr>
																					<td width="85%" colspan="2">
																						<table width="100%" class="tableborder">
																							<tr>
																								<td>
																									<div class="scrollsupervision">
																										<table width="100%">
																												<logic:iterate id="orgObject" name="CTFrmSearchOrgList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																													<logic:iterate id="format" name="CTFrmSearchOrgListFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																														<logic:equal name="format" property="property" value="RESPNAME">
																															<tr>
																																<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
																																<td width="20px">
																																	<html:multibox property="uids">
																																		<bean:write name='orgObject' property='property(UID)' />
																																	</html:multibox>
																																</td>
																																</ispac:hasFunction>
																																<c:set var="isUser"><bean:write name='orgObject' property='property(USER)' /></c:set>
																																<c:set var="isGroup"><bean:write name='orgObject' property='property(GROUP)' /></c:set>
																																<c:set var="isOrguni"><bean:write name='orgObject' property='property(ORGUNIT)' /></c:set>
																																<td width="20px">
																																	<c:choose>
																																		<c:when test="${isUser=='true'}">
																																			<img src='<ispac:rewrite href="img/user.gif"/>'/>
																																		</c:when>
																																		<c:when test="${isGroup=='true'}">
																																			<img src='<ispac:rewrite href="img/group.gif"/>'/>
																																		</c:when>
																																		<c:when test="${isOrguni=='true'}">
																																			<img src='<ispac:rewrite href="img/org.gif"/>'/>
																																		</c:when>
																																	</c:choose>
																																</td>
																																<td class="ldapentry" title='<%= orgObject.getProperty("RESPNAME") %>'>
																																	<%=format.formatProperty(orgObject)%>
																																</td>
																															</tr>
																														</logic:equal>
																													</logic:iterate>
																												</logic:iterate>
																										</table>
																									</div>
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																				<tr><td height="10px" colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
																			</html:form>
																		</logic:equal>														
																	</table>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
										</table>
									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>