<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:hasFunction var="editionMode" functions="FUNC_INV_DOCTYPES_EDIT, FUNC_INV_TEMPLATES_EDIT" />

<c:set var="activeScreen" value="${sessionScope['ATTR_ACTIVE_SCREEN']}"/>

<script>
function editTemplate()
{
	<c:if test="${!empty NUM_PROC}">
		jConfirm('<bean:message key="form.template.edicionTemplateEspecifica"/>', '<bean:message key="common.confirm"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r) {
	</c:if>
	
	var url = '<%= request.getContextPath()%>' + '/editTemplate.do?template=' + '<bean:write name="uploadForm" property="key"/>';
	        
	document.forms[0].target = "editTemplateFrame";
	document.forms[0].action = url;
	document.forms[0].submit();

	<c:if test="${!empty NUM_PROC}">
		    }
		});	
	</c:if>
}

function saveTemplate() {
	try {
	    <c:choose>
	        <c:when test="${activeScreen == 'CT_TEMPLATES'}">
	            var url = '<%= request.getContextPath()%>' + '/storeCTTemplate.do';
	        </c:when>
	        <c:otherwise>
	            var url = '<%= request.getContextPath()%>' + '/storeTemplate.do?type=' + '<bean:write name="uploadForm" property="property(ID_TPDOC)"/>';
	        </c:otherwise>
	    </c:choose>
		        
		document.forms[0].target = "ParentWindow";
		document.forms[0].action = url;
  		document.forms[0].submit();
  	} catch(e) {
  		jAlert('<bean:message key="exception.template.document.invalidPathFile"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
  	}
}

function showTemplates() {
    <c:choose>
        <c:when test="${activeScreen == 'CT_TEMPLATES'}">
            var url = '<%= request.getContextPath()%>' + '/showCTTemplatesList.do';
        </c:when>
        <c:otherwise>
            var url = '<%= request.getContextPath()%>' + '/showTemplatesList.do?type=<bean:write name="uploadForm" property="property(ID_TPDOC)"/>';
        </c:otherwise>
    </c:choose>
	
	document.forms[0].target = "ParentWindow";
	document.forms[0].action = url;
	document.forms[0].submit();
}

function deleteTemplate(){
	<c:choose>
		<c:when test="${!empty NUM_PROC}">
			<c:set var="numProc"><c:out value="${NUM_PROC}"/></c:set>
			<bean:define id="numProc" name="numProc" type="java.lang.String"/>
			<c:set var="msgDelete"><bean:message key='form.template.asociatedTemplate.confirmDelete' arg0='<%=numProc%>'/></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="msgDelete"><bean:message key='form.template.delete.confirm'/></c:set>
		</c:otherwise>
	</c:choose>
	

	jConfirm('<c:out value="${msgDelete}"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){

			
		    <c:choose>
		        <c:when test="${activeScreen == 'CT_TEMPLATES'}">
		            var url = '<%= request.getContextPath()%>' + '/deleteCTTemplate.do';
		        </c:when>
		        <c:otherwise>
		            var url = '<%= request.getContextPath()%>' + '/deleteTemplate.do';
		        </c:otherwise>
		    </c:choose>
		
			document.forms[0].target = "ParentWindow";
			document.forms[0].action = url;
	    	document.forms[0].submit();
	    }
	});	
	
}

</script>

<div id="navmenutitle">
	<bean:message key="form.template.mainTitle"/>
</div>

<%-- BREAD CRUMBS --%>
<logic:present name="BreadCrumbs">
<tiles:insert page="/ispac/common/tiles/breadCrumbsTile.jsp" ignore="true" flush="false"/>
</logic:present>

<div id="navmenu">
	<ul class="actionsMenuList">
		<logic:equal name="uploadForm" property="property(ID)" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:saveTemplate()">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</c:if>
			<li>
				<a href="javascript:showTemplates()">
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
		  	</li>
		</logic:equal>
		<logic:notEqual name="uploadForm" property="property(ID)" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:saveTemplate()">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</c:if>
			<li>
				<a href="javascript:showTemplates()">
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
		  	</li>	  	
			<c:if test="${editionMode}">
			<li>
				<a href='javascript:editTemplate()'>
					<bean:message key="forms.button.edit"/>
				</a>
			</li>
			<li>
				<a href="javascript:deleteTemplate();">
					<nobr><bean:message key="forms.button.delete"/></nobr>
				</a>
			</li>
			</c:if>
		</logic:notEqual>
	</ul>
</div>

<html:form action='/showCTEntityUp.do' enctype="multipart/form-data">

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación
	--%>
	<html:hidden property="entityAppName" value="EditCTTemplate"/> 
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>

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
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<logic:notEqual name="uploadForm" property="key" value="-1">
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.id" tooltipKey="form.template.propertyLabel.id.tooltip"/>
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
                                                                            <td height="20" class="formsTitle" width="1%">
                                                                                <ispac:tooltipLabel labelKey="form.template.propertyLabel.type" tooltipKey="form.template.propertyLabel.type.tooltip"/>
                                                                            </td>
                                                                            <td height="20">
                                                                                <c:choose>
                                                                                    <c:when test="${activeScreen == 'CT_TEMPLATES'}">
                                                                                        &nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="valueExtra(NOMBRE_TPDOC)"
                                                                                                                readonly="true"
                                                                                                                readonlyTag="false"
                                                                                                                propertyReadonly="readonly"
                                                                                                                styleClass="input"
                                                                                                                styleClassReadonly="inputReadOnly"
                                                                                                                size="60"
                                                                                                                id="SELECT_DOCTYPE"
                                                                                                                target="workframe"
                                                                                                                action="selectObject.do?codetable=SPAC_CT_TPDOC&codefield=ID&valuefield=NOMBRE&caption=select.typeDoc.caption&sqlOrderBy=NOMBRE&decorator=/formatters/entities/selectctpdocformatter.xml&formName=uploadForm"
                                                                                                                image="img/search-mg.gif" 
                                                                                                                titleKeyLink="select.typeDoc" 
                                                                                                                showFrame="true">
                                                                                                                                              
                                                                                            <ispac:parameter name="SELECT_DOCTYPE" id="property(ID_TPDOC)" property="ID"/>
                                                                                            <ispac:parameter name="SELECT_DOCTYPE" id="valueExtra(NOMBRE_TPDOC)" property="NOMBRE"/>
                                                                                                                        
                                                                                        </ispac:htmlTextImageFrame></nobr>
                                                                                        <html:hidden property="property(ID_TPDOC)" />
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        &nbsp;&nbsp;<html:text property="property(ID_TPDOC)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            
                                                                                <div id="formErrors">
                                                                                        <html:errors property="property(ID_TPDOC)"/>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td colspan="2" height="20" class="formsTitle">
																				<hr size="2"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.name" tooltipKey="form.template.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(NOMBRE)" styleClass="input" size="50" readonly="false" maxlength="100"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																						<html:errors property="property(NOMBRE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.code" tooltipKey="form.template.propertyLabel.code.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(COD_PLANT)" styleClass="input" size="10" readonly="false" maxlength="16"/>
																				<div id="formErrors">
																						<html:errors property="property(COD_PLANT)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.typeTemplate" tooltipKey="form.template.propertyLabel.typeTemplate.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<input type="text" value='<bean:write name="uploadForm" property="entityApp.property(PROCEDURE_TEMPLATE)"/>' class="inputReadOnly" size="15" readonly="readonly">
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td class="formsTitle" style="vertical-align:top;">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.expression" tooltipKey="form.template.propertyLabel.expression.tooltip"/>
																			</td>
																			<td>
																				&nbsp;&nbsp;<html:textarea property="property(EXPRESION)" styleClass="input" readonly="false"
																					cols="60" rows="8"  styleId="textExp" onkeypress="javascript:maxlength('textExp', 2000)"/>
																				<div id="formErrors">
																						<html:errors property="property(EXPRESION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<logic:equal name="uploadForm" property="property(ID)" value="-1">
																		<tr>
																			<td class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.documento" tooltipKey="form.template.propertyLabel.documento.tooltip"/>
																			</td>
																			<td>
																				&nbsp;&nbsp;<html:file property="uploadFile" styleClass="input"/>
																				<div id="formErrors">
																						<html:errors property="uploadFile"/>
																				</div>
																			</td>
																		</tr>
																		</logic:equal>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.template.propertyLabel.creationDate" tooltipKey="form.template.propertyLabel.creationDate.tooltip"/>
																			</td>
																			<td height="20">
																			  &nbsp;&nbsp;<html:text property="property(FECHA)" styleClass="inputReadOnly" size="25" readonly="true" maxlength="10"/>
																			  
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
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
</html:form>

<iframe src='' id='editTemplateFrame' name='editTemplateFrame' style='visibility:visible;height:0px;margin:0px;padding:0px;border:none;' allowtransparency='true'></iframe>