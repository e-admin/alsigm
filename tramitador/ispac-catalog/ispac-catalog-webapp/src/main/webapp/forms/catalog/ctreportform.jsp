<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="ieci.tdw.ispac.ispaclib.dao.cat.CTReportDAO" %>


<script>
	var controlPaso = true;
	var controlFile = "";
			
	
	
							
	function showResponsables(){
	
	   if(document.getElementById("responsables")!=null){
			document.getElementById("responsables").style.display='block';
		}
		if(document.getElementById("tituloResponsables")!=null){
			document.getElementById("tituloResponsables").style.display='block';
		}
			
		if(document.getElementById("accionesResponsables")!=null){
			document.getElementById("accionesResponsables").style.display='block';
		}

	
	}
	function hideResponsables(){
	
		if(document.getElementById("responsables")!=null){
			document.getElementById("responsables").style.display='none';
		}
		if(document.getElementById("tituloResponsables")!=null){
			document.getElementById("tituloResponsables").style.display='none';
		}
			
		if(document.getElementById("accionesResponsables")!=null){
			document.getElementById("accionesResponsables").style.display='none';
		}

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
	// Responsables asociados al informe
	function deletePermission(myform)
	{
    	var checked = false;
		if (typeof myform.uids == 'undefined')
		{
			jAlert('<bean:message key="catalog.report.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			
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
	    else{
	   		 jAlert('<bean:message key="catalog.report.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			
			}
	}
		
</script>

<div id="navmenutitle">
	<bean:message key="form.report.mainTitle"/>
</div>

<div id="navSubMenuTitle">
</div>

<div id="navmenu" >
	<c:set var="tipoAsociativo"></c:set>
	<jsp:useBean id="tipoAsociativo" type="java.lang.String"></jsp:useBean>
	<logic:equal name="uploadForm" property="property(TIPO)"  value="2" >
		<c:set var="tipoAsociativo">true</c:set>
	</logic:equal>
	<logic:equal name="uploadForm" property="property(TIPO)"  value="4" >
		<c:set var="tipoAsociativo" >true</c:set>
	</logic:equal>
	
	<c:url value="showComponentsUseList.do" var="_inUse">
			<c:param name="entityId">
				<bean:write name="uploadForm" property="entity" />
			</c:param>
			<c:param name="regId">
				<bean:write name="uploadForm" property="key" />
			</c:param>
	</c:url>
	<ul class="actionsMenuList">
		<logic:equal name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_REPORTS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTReportsList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
			</li>
		</logic:equal>
		<logic:notEqual name="uploadForm" property="key" value="-1">
			<ispac:hasFunction functions="FUNC_COMP_REPORTS_EDIT">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntityUp.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</ispac:hasFunction>
			<li>
				<a href='<%=request.getContextPath() + "/showCTReportsList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
			</li>
			
			
		
			<c:if test='${tipoAsociativo}'>
			<li>
				<a href="javascript:submit('<c:out value="${_inUse}"/>')">
					<nobr><bean:message key="forms.button.use"/></nobr>
				</a>		
			</li>
			</c:if>
			
			<ispac:hasFunction functions="FUNC_COMP_REPORTS_EDIT">
		  	<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deleteCTEntity.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>
		  	</li>
		  	</ispac:hasFunction>
		  	<li>
		 	<c:url value="getCTAppXML.do" var="_link">
					<c:param name="reportId">
						<bean:write name="uploadForm" property="key" />
					</c:param>
			</c:url>
			<a  href='<c:out value="${_link}"/>' ><bean:message key="entityManage.title.show.form.downloadXML"/></a>
		  	</li>
	  	</logic:notEqual>
	</ul>
</div>
<html:form action='/showCTEntityUp.do' enctype="multipart/form-data">

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
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.id" tooltipKey="form.report.propertyLabel.id.tooltip"/>
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
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.name" tooltipKey="form.report.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="inputSelectS" readonly="false" maxlength="250"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.description" tooltipKey="form.report.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(DESCRIPCION)" styleClass="inputSelectS" readonly="false" rows="6" cols="40"
																					styleId="desc" onkeypress="javascript:maxlength('desc', 255)"/>
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
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.type" tooltipKey="form.report.propertyLabel.description.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;
																				<c:choose> 
																					<c:when test='${tipoAsociativo}'>
																						<html:hidden property="property(TIPO)"/>
																						<html:select styleClass="input" property="property(TIPO)" disabled="true">
																							<html:option value="1"><bean:message key="form.report.propertyLabel.generico"/></html:option>
																							<html:option value="2"><bean:message key="form.report.propertyLabel.especifico"/></html:option>
																							<html:option value="3"><bean:message key="form.report.propertyLabel.global"/></html:option>
																							<html:option value="4"><bean:message key="form.report.propertyLabel.busqueda"/></html:option>
																						</html:select>
																					</c:when>
																					<c:otherwise>
																						<html:select styleClass="input" property="property(TIPO)" >
																							<html:option value="1"><bean:message key="form.report.propertyLabel.generico"/></html:option>
																							<html:option value="2"><bean:message key="form.report.propertyLabel.especifico"/></html:option>
																							<html:option value="3"><bean:message key="form.report.propertyLabel.global"/></html:option>
																							<html:option value="4"><bean:message key="form.report.propertyLabel.busqueda"/></html:option>
																						</html:select>
																					</c:otherwise>
																				</c:choose>
																				<div id="formErrors">
																					<html:errors property="property(TIPO)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<logic:notEqual name="uploadForm" property="key" value="-1">
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.date" tooltipKey="form.report.propertyLabel.date.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(FECHA)" styleClass="inputReadOnly" size="25" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		</logic:notEqual>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.filas" tooltipKey="form.report.propertyLabel.filas.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(FILAS)" styleClass="input" readonly="false" size="5" maxlength="2"/>
																				<div id="formErrors">
																					<html:errors property="property(FILAS)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.columnas" tooltipKey="form.report.propertyLabel.columnas.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(COLUMNAS)" styleClass="input" readonly="false" size="5" maxlength="2"/>
																				<div id="formErrors">
																					<html:errors property="property(COLUMNAS)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.params" tooltipKey="form.report.propertyLabel.params.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(FRM_PARAMS)" styleClass="input" readonly="false" cols="72" rows="7" styleId="texta"/>
																				<div id="formErrors">
																					<html:errors property="property(FRM_PARAMS)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" style="vertical-align:top">
																				<ispac:tooltipLabel labelKey="form.report.propertyLabel.xml" tooltipKey="form.report.propertyLabel.xml.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:textarea property="property(XML)" styleClass="input" readonly="false" cols="72" rows="7" styleId="texta"/>&nbsp; <bean:message key="catalog.data.obligatory"/><br>
																				&nbsp;&nbsp;<html:file styleClass="input" property="uploadFile" styleId="FileUp" onchange="javascript:ispac_loadFile();"/><br>
																				<div id="formErrors">
																					<html:errors property="property(XML)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																	<logic:notEqual name="uploadForm" property="key" value="-1">
																		  <tr>
																		 	<td>
																				<html:radio property="property(VISIBILIDAD)" value="1" onclick="javascript:hideResponsables();"><bean:message key="catalog.report.visibilidad.publica"/></html:radio>
																			</td>
																			<td>
																				<html:radio   property="property(VISIBILIDAD)" value="0" onclick="javascript:showResponsables();"><bean:message key="catalog.report.visibilidad.restringida"/></html:radio>
																			</td>
																		</tr>
																	</logic:notEqual>
																	
																	<logic:equal name="uploadForm" property="key" value="-1">
																		  <tr>
																		 	<td>
																				<html:radio  property="property(VISIBILIDAD)" value="1"  disabled="true" ><bean:message key="catalog.report.visibilidad.publica"/></html:radio>
																			</td>
																			<td>
																				<html:radio   property="property(VISIBILIDAD)" value="" disabled="true"><bean:message key="catalog.report.visibilidad.restringida"/></html:radio>
																			</td>
																		</tr>
																	</logic:equal>
																	</html:form>
																	
																<logic:notEqual name="uploadForm" property="key" value="-1">
																<logic:present name="CTReportOrgList">
																		
																		<c:url value="showResponsiblesForReport.do" var="_showResponsiblesForReport">
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
																		<jsp:useBean id="_showResponsiblesForReport" type="java.lang.String"/>
																	
																		
																	
																		
																		
																		<html:form action="deleteResponsiblesReport.do">
																			<input type="hidden" name="id" value='<bean:write name="uploadForm" property="property(ID)"/>'/>

																			<%-- Asociacion de formulario a objeto de Organizacion --%>
																			<tr><td height="10px" colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
																			
																			
																			<c:set var="visibilidad"><bean:write name="uploadForm" property="property(VISIBILIDAD)"/></c:set>
																			<c:choose>
																				<c:when test="${visibilidad == 0}">
																					<c:set var="_showResponsibles">display:block</c:set>
																				</c:when>
																				<c:otherwise>
																					<c:set var="_showResponsibles">display:none</c:set>
																				</c:otherwise>
																			</c:choose>
																			
																			
																			<tr id="tituloResponsables" style='<c:out value="${_showResponsibles}"/> '>
																				<td class="boldlabel" colspan="2"><bean:message key="catalog.report.permission"/>:</td>
																			</tr>
																			
																			<ispac:hasFunction functions="FUNC_COMP_REPORTS_EDIT">
																			<tr>
																				<td colspan="2">
																					<div class="buttonList"  id="accionesResponsables"  style='<c:out value="${_showResponsibles}"/> '>
																						<ul>
																							<li>
																								<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showResponsiblesForReport%>'/>',640,480,'')"><nobr><bean:message key="forms.button.add"/></nobr></a>
																							</li>
																							<c:choose>
																								<c:when test="${!empty CTReportOrgList}">
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
																					</div>
																				</td>
																			</tr>
																			</ispac:hasFunction>
																
																			<tr>
																				<td width="85%" colspan="2">
																					<table width="100%" class="tableborder" id="responsables" style='<c:out value="${_showResponsibles}"/> '>
																						<tr>
																							<td>
																								<div class="scrollsupervision" >
																									<table width="100%">
																									
																											<logic:iterate id="orgObject" name="CTReportOrgList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																												<logic:iterate id="format" name="CTReportOrgListFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																													<logic:equal name="format" property="property" value="RESPNAME">
																														<tr>
																															<td width="20px">
																																<html:multibox property="uids">
																																	<bean:write name='orgObject' property='property(UID)' />
																																</html:multibox>
																															</td>
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
																					

																	</logic:present>
																	</logic:notEqual>
																
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

																	

	