<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript">
//<!--
	function controlDiv(show, div){
		var elementDiv=document.getElementById(div);
		if (show){
			elementDiv.style.display = 'block';
		}else{
			elementDiv.style.display = 'none';
		}
	}

   function onChangeDocumentarySearch(obj){
  
   if (obj.checked){
		controlDiv(false,"divMulti");
		document.forms[0].elements["property(MULTIVALUE)"].value="false";
		}else{
			checkType(document.getElementById('tipo'));
			controlDiv(true, "divMulti");
		}
   }

	
	function onChangeMultivalue(obj){
		if (obj.checked){
			//ocultar los controles de tamaño, precision y rango y busqueda documental
			controlDiv(false,"divRango");
			controlDiv(false,"divPrecision");
			controlDiv(false,"divSize");
			controlDiv(false, "divSearch");	
			document.forms[0].elements["property(RANGOMAX)"].value="";
			document.forms[0].elements["property(RANGOMIN)"].value="";
			document.forms[0].elements["property(SIZE)"].value="";
			document.forms[0].elements["property(PRECISION)"].value="";
			
			
		}else{
			checkType(document.getElementById('tipo'));
		}
	}
	
	function checkType(select ) {

	var isMultivalue = document.getElementById('multivalue').checked;
	var elementDivRango=document.getElementById("divRango");
	elementDivRango.style.display='none';	
		if (select) {
			var form = select.form;
			if (form) {
		
				var elementDivSize = document.getElementById("divSize");
				var elementDivSearch=document.getElementById("divSearch");
			
			
				if (elementDivSize) {
					var type = select.options[select.selectedIndex].value;
					
					if((type==0|| type==1 )&& !document.getElementById('multivalue').checked){
				
						elementDivSearch.style.display='block';	
					
					}
					else{
					
					document.forms[0].elements["property(DOCUMENTARYSEARCH)"].value="false";	
					elementDivSearch.style.display='none';
					
					}
					if ((!isMultivalue) && (type==2 ||type==3 || type==4 || type==5)){
					    elementDivRango.style.display='block';	
					}
					
					if ((!isMultivalue) && (type==0 || type==2 || type==4)) {
						elementDivSize.style.display='block'
					} else {
						var elementSize = form.elements["property(SIZE)"];
						if (elementSize) {
							elementSize.value = "";
						}
						elementDivSize.style.display='none'
					}
				}
			
				var elementDivPrecision = document.getElementById("divPrecision");
				if (elementDivPrecision) {
					var type = select.options[select.selectedIndex].value;
					if ((!isMultivalue) && (type==4)) {
						elementDivPrecision.style.display='block'
					} else {
						var elementPrecision = form.elements["property(PRECISION)"];
						if (elementPrecision) {
							elementPrecision.value = "";
						}
						elementDivPrecision.style.display='none'
					}
				}
			
				/*
				var element = form.elements["property(SIZE)"];
				var elementPrecision = form.elements["property(PRECISION)"];
				if (element) {
					var type = select.options[select.selectedIndex].value;
					if (type==0 || type==2 || type==4) {
						element.className = "input";
						element.readOnly = false;
					} else {
						element.value = "";
						element.className = "inputReadOnly";
						element.readOnly = true;
					}
				}
				if (elementPrecision) {
					var type = select.options[select.selectedIndex].value;
					if (type==4) {
						elementPrecision.className = "input";
						elementPrecision.readOnly = false;
					} else {
						elementPrecision.value = "";
						elementPrecision.className = "inputReadOnly";
						elementPrecision.readOnly = true;
					}
				}
				*/
			}
		}
	}
//-->
</script>


<c:set var="_localReadonly"><%= request.getAttribute("readOnly")%></c:set>
<jsp:useBean id="_localReadonly" type="java.lang.String" />
<c:set var="_style">inputSelectS</c:set>
<c:if test='${_localReadonly}'>
	<c:set var="_style">inputReadOnly</c:set>
</c:if>
<jsp:useBean id="_style" type="java.lang.String" />
<logic:present name="activateDocumentarySearch">
	<c:set var="_activateDocumentarySearch"><%= request.getAttribute("activateDocumentarySearch")%></c:set>
</logic:present>
<logic:notPresent name="activateDocumentarySearch">
	<c:set var="_activateDocumentarySearch" value="false"/>
</logic:notPresent>


<jsp:useBean id="_activateDocumentarySearch" type="java.lang.String" />

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>
<jsp:useBean id="action" type="java.lang.String"/>


<html:form action='<%=action%>'>
	<c:if test='${_localReadonly}'>
		<html:hidden property="property(TYPE)"/>
	</c:if>

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
									<td height="5px" colspan="3">
										
									</td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
										
											<logic:messagesPresent>
												<tr>
													<td>
														<div id="formErrorsMessage">
															<bean:message key="forms.errors.formErrorsMessage"/>
														</div>	
													</td>
												</tr>
											</logic:messagesPresent>
											
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
											<tr>
												<td>
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">
																		<tr>
																			<td height="20" width="200px" class="formsTitle">
																				<div id="form.entity.field.propertyLabel.logicalName">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.logicalName" tooltipKey="form.entity.field.propertyLabel.logicalName.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(LOGICALNAME)" styleClass="<%=_style%>" 	readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="250" />
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(LOGICALNAME)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.field.propertyLabel.physicalName">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.physicalName" tooltipKey="form.entity.field.propertyLabel.physicalName.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(PHYSICALNAME)" styleClass="<%=_style%>" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="30"/>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(PHYSICALNAME)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.field.propertyLabel.descripcion">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.descripcion" tooltipKey="form.entity.field.propertyLabel.descripcion.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:text property="property(DESCRIPCION)" styleClass="<%=_style%>" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="250"/>
																				&nbsp; 
																				<div id="formErrors">
																					<html:errors property="property(DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>	
																
																	<tr >
																		<td colspan="2">
																		
																				<div id="divMulti" style="display:block;">
																		<table border="0" cellspacing="0" cellpadding="0" width="100%">
																						<tr> 
																			<td height="20" width="200px" class="formsTitle"> 
																				<div id="form.entity.field.propertyLabel.multivalue">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.multivalue" tooltipKey="form.entity.field.propertyLabel.multivalue.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				
																				&nbsp;&nbsp;<html:checkbox  property="property(MULTIVALUE)" styleClass="checkbox" disabled='<%=(new Boolean(_localReadonly).booleanValue())%>' value="S" styleId="multivalue" onclick="javascript:onChangeMultivalue(this);" onchange="javascript:onChangeMultivalue(this);"/>
																				<div id="formErrors">
																					<html:errors property="property(MULTIVALUE)"/>
																				</div>
																			</td>
																		</tr>
																		<tr >
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		</table>
																		</div>
																		</td>
																		</tr>
																		
																		<tr>
																			<td height="20" class="formsTitle">
																				<div id="form.entity.field.propertyLabel.type">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.type" tooltipKey="form.entity.field.propertyLabel.type.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				&nbsp;&nbsp;<html:select property="property(TYPE)" styleId="tipo" styleClass="<%=_style%>"  disabled='<%=(new Boolean(_localReadonly).booleanValue())%>' onchange="javascript:checkType(this)">
																						<html:option value="0" key="field.type.0"/>
																						<html:option value="1" key="field.type.1"/>
																						<html:option value="2" key="field.type.2"/>
																						<html:option value="3" key="field.type.3"/>
																						<html:option value="4" key="field.type.4"/>
																						<html:option value="5" key="field.type.5"/>
																						<html:option value="6" key="field.type.6"/>
																						<html:option value="13" key="field.type.13"/>
																						<!--<html:option value="7" key="field.type.7"/>-->
																						<html:option value="8" key="field.type.8"/>
																						<html:option value="9" key="field.type.9"/>
																						<html:option value="10" key="field.type.10"/>
																						<html:option value="11" key="field.type.11"/>
																						<html:option value="12" key="field.type.12"/>
																					</html:select>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(TYPE)"/>
																				</div>	
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr >
																		<td colspan="2">
																		
																				<div id="divSearch" style="display:block;">
																		<table border="0" cellspacing="0" cellpadding="0" width="100%">
																						<tr> 
																			<td height="20" width="200px" class="formsTitle"> 
																				<div id="form.entity.field.propertyLabel.documentarySearch">
																					<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.documentarySearch" tooltipKey="form.entity.field.propertyLabel.documentarySearch.tooltip"/>
																				</div>
																			</td>
																			<td height="20" class="formsTitle">
																				
																				&nbsp;&nbsp;<html:checkbox onclick="javascript:onChangeDocumentarySearch(this);" onchange="javascript:onChangeDocumentarySearch(this);" disabled='<%=(new Boolean(_activateDocumentarySearch).booleanValue())%>' property="property(DOCUMENTARYSEARCH)"/>
																				&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(DOCUMENTARYSEARCH)"/>
																				</div>
																			</td>
																		</tr>
																		<tr >
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		</table>
																		</div>
																		</td>
																		</tr>
																		<tr>
																			<td colspan="2">
																		
																				<div id="divSize" style="display:block;">
																				
																					<table border="0" cellspacing="0" cellpadding="0" width="100%">
																						<tr>
																							<td height="20" width="200px" class="formsTitle">
																								<div id="form.entity.field.propertyLabel.size">
																									<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.size" tooltipKey="form.entity.field.propertyLabel.size.tooltip"/>
																								</div>
																	     					</td>
																							<td height="20" class="formsTitle">
																								&nbsp;&nbsp;<html:text property="property(SIZE)" styleClass="<%=_style%>" size="10" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="5"/>
																								&nbsp; <bean:message key="catalog.data.obligatory"/>
																								<div id="formErrors">
																									<html:errors property="property(SIZE)"/>
																								</div>
																							</td>
																						</tr>
																						<tr>
																							<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																						</tr>
																					</table>
																					
																				</div>
																				
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2">
																		
																				<div id="divPrecision" style="display:none;">
																				
																					<table border="0" cellspacing="0" cellpadding="0" width="100%">
																						<tr>
																							<td height="20" width="200px" class="formsTitle">
																								<div id="form.entity.field.propertyLabel.precision">
																									<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.precision" tooltipKey="form.entity.field.propertyLabel.precision.tooltip"/>
																								</div>
																		     				</td>
																							<td height="20" class="formsTitle">
																								&nbsp;&nbsp;<html:text property="property(PRECISION)" styleClass="<%=_style%>" size="10" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="3"/>
																								&nbsp; <bean:message key="catalog.data.obligatory"/>
																								<div id="formErrors">
																									<html:errors property="property(PRECISION)"/>
																								</div>
																							</td>
																						</tr>
																						<tr>
																							<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																						</tr>
																					</table>
																					
																				</div>
																				
																		
																			</td>
																		</tr>
																		
																		<tr>
																		<td colspan="2">
																		<div id="divRango" style="display:block;">
																		<table border="0" cellspacing="0" cellpadding="0" width="100%">
																		<tr>
																			<td height="20" width="200px" class="formsTitle">
																			<div id="form.entity.field.propertyLabel.rangoMin">
																				<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.rangoMin" tooltipKey="form.entity.field.propertyLabel.rangoMin.tooltip"/>
																			</div>
																					
																	     	</td>
																			<td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(RANGOMIN)" styleClass="<%=_style%>" size="10" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="10"/>
																					<div id="formErrors">
																									<html:errors property="property(RANGOMIN)"/>
																								</div>
																			</td>
																		</tr>
																		
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		
																		<tr>
																			<td height="20" width="200px" class="formsTitle">
																				<div id="form.entity.field.propertyLabel.rangoMax">
																						<ispac:tooltipLabel labelKey="form.entity.field.propertyLabel.rangoMax" tooltipKey="form.entity.field.propertyLabel.rangoMax.tooltip"/>
																				</div>
																		     </td>
																				 <td height="20" class="formsTitle">
																					&nbsp;&nbsp;<html:text property="property(RANGOMAX)" styleClass="<%=_style%>" size="10" readonly='<%=new Boolean(_localReadonly).booleanValue()%>' maxlength="10"/>
																					<div id="formErrors">
																									<html:errors property="property(RANGOMAX)"/>
																								</div>		
																				
																				</td>
																		</tr>
																		</table>
																		
																		
																		</div>
																		</td>
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

<script language="JavaScript">
checkType(document.getElementById('tipo'));
</script>