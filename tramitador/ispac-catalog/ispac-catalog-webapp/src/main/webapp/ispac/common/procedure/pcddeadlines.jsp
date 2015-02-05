<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />
<bean:define name="pcdId" id="pcdId"/>
<bean:define name="entityId" id="entityId"/>

<script language=javascript type='text/javascript'>
	function onDeadlineTypeChange(){
		var miDiv = "div" + document.getElementById('selectBaseDateType').value;
		if(miDiv == "divENTITY"){
			document.getElementById('divENTITY').style.visibility = 'visible'; 
			document.getElementById('divENTITY').style.display = 'block'; 
			document.getElementById('divRULE').style.visibility = 'hidden';
			document.getElementById('divRULE').style.display = 'none';
			return;
		}
		if(miDiv == "divRULE"){
			document.getElementById('divRULE').style.visibility = 'visible';
			document.getElementById('divRULE').style.display = 'block';
			document.getElementById('divENTITY').style.visibility = 'hidden';
			document.getElementById('divENTITY').style.display = 'none'; 			
			return;
		}
		document.getElementById('divRULE').style.visibility = 'hidden';
		document.getElementById('divENTITY').style.visibility = 'hidden';					
		document.getElementById('divRULE').style.display = 'none';
		document.getElementById('divENTITY').style.display = 'none';				
	}
	function onCalendarTypeChange(){
		var miDiv = "div" + document.getElementById('selectCalendarType').value;
		if(miDiv == "divRULE"){
			document.getElementById('divRULECalendar').style.visibility = 'visible';
			document.getElementById('divRULECalendar').style.display = 'block';
			document.getElementById('divCatalogCalendar').style.visibility = 'hidden';
			document.getElementById('divCatalogCalendar').style.display = 'none';
			return;
		}
		if(miDiv == "divCALENDAR_CATALOG"){
			document.getElementById('divCatalogCalendar').style.visibility = 'visible';
			document.getElementById('divCatalogCalendar').style.display = 'block';
			document.getElementById('divRULECalendar').style.visibility = 'hidden';
			document.getElementById('divRULECalendar').style.display = 'none';
			return;
		}
		
		document.getElementById('divRULECalendar').style.visibility = 'hidden';
		document.getElementById('divRULECalendar').style.display = 'none';
		document.getElementById('divCatalogCalendar').style.visibility = 'hidden';
		document.getElementById('divCatalogCalendar').style.display = 'none';
		
	}

</script> 

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<a href="javascript:submit('<c:url value="/storeProcedure.do"/>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
		</li>

	</ul>
	</ispac:hasFunction>
</div>
<div class="tabContent">


<html:form action='/showProcedureEntity.do'>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID)"/>

	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:NVERSION)"/>

	<html:hidden property="property(SPAC_P_PLAZO:ID)"/>

	<!-- Información de las propiedades -->
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_RESP)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_CRT)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:TS_CRT)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_UPD)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:TS_UPD)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_PCD_BPM)"/>
	
	<input type="hidden" name="method" value="deadlines"/>

	<table width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr>
			<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td height="5px" colspan="3"><html:errors/></td>
					</tr>
					<tr>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="100%">
							<div style="display:block" id="page1" class="formtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="30%">
														<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.magnitud" tooltipKey="procedure.plazos.propertyLabel.magnitud.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PLAZO:MAGNITUDE)" styleClass="inputSelectScustomWidth" size="5" readonly="false" maxlength="5"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.unidades" tooltipKey="procedure.plazos.propertyLabel.unidades.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:select name="entapp" property="property(SPAC_P_PLAZO:UNITS)" styleClass="inputSelectScustomWidth">
														         <html:optionsCollection name="entapp" property="property(TIME_UNITS)"/>
														</html:select>
														<div id="formErrors">
															<html:errors property="property(SPAC_P_PLAZO:UNITS)"/>
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.selecBaseDate" tooltipKey="procedure.plazos.propertyLabel.selecBaseDate.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:select name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_TYPE)" styleClass="inputSelectScustomWidth" styleId="selectBaseDateType" onchange="javascript:onDeadlineTypeChange();">
														         <html:optionsCollection name="entapp" property="property(BASEDATE_TYPES)"/>
														</html:select>
														<div id="formErrors">
															<html:errors property="property(SPAC_P_PLAZO:BASEDATE_TYPE)"/>
														</div>
													</td>
												</tr>
											</table>
											<div id="divENTITY">
												<table border="0" cellpadding="0" cellspacing="0" width="100%">
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td height="20" class="formsTitle" width="30%">
															<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.entityId" tooltipKey="procedure.plazos.propertyLabel.entityId.tooltip"/>
														</td>
														<td height="20">
															<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_ID)" />
															&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(EXTRA:ETIQUETA)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_ENTI"
																				target="workframe"
																				action='<%="selectObject.do?codetable=SPAC_CT_ENTIDADES&codefield=ID&valuefield=NOMBRE&caption=select.entity.caption&decorator=/formatters/entities/entitiesdeadlinesformatter.xml&pcdId=" + pcdId + "&sqlquery=" + java.net.URLEncoder.encode("WHERE TIPO=1")%>'
																				image="img/search-mg.gif" 
																				titleKeyLink="select.entity" 
																				showFrame="true"
																				>
																<ispac:parameter name="SELECT_ENTI" id="property(SPAC_P_PLAZO:BASEDATE_ENTITY_ID)" property="ID"/>
																<ispac:parameter name="SELECT_ENTI" id="property(EXTRA:ETIQUETA)" property="ETIQUETA"/>
																<ispac:parameter name="SELECT_ENTI" id="property(EXTRA:ETIQUETA_CAMPO)" property=""/>
																<ispac:parameter name="SELECT_ENTI" id="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)" property=""/>
														</ispac:htmlTextImageFrame></nobr>
														</td>
													</tr>
													<tr>
														<td height="20" class="formsTitle">
															<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.entityField" tooltipKey="procedure.plazos.propertyLabel.entityField.tooltip"/>
														</td>
														<td height="20">
															<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)"/>
															&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(EXTRA:ETIQUETA_CAMPO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_FIELD"
																				target="workframe"
																				action='<%="selectFieldsEntity.do?decorator=/formatters/entities/entitiesfieldsdeadlinesformatter.xml"%>'
																				image="img/search-mg.gif" 
																				titleKeyLink="select.field" 
																				showFrame="true"
																				inputField="SPAC_P_PLAZO:BASEDATE_ENTITY_ID"
																				>
																<ispac:parameter name="SELECT_FIELD" id="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)" property="PHYSICALNAME"/>
																<ispac:parameter name="SELECT_FIELD" id="property(EXTRA:ETIQUETA_CAMPO)" property="ETIQUETA"/>
														</ispac:htmlTextImageFrame></nobr>
													</td>
													</tr>			
												</table>
											</div>
											
											<div id="divRULE">
												<table border="0" cellspacing="0" cellpadding="0" width="100%">
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td height="20" class="formsTitle" width="30%">
															<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.ruleId" tooltipKey="procedure.plazos.propertyLabel.ruleId.tooltip"/>
														</td>
														<td height="20">
															<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_RULE_ID)" />
															&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(EXTRA:RULE_NAME)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_RULE"
																				target="workframe"
																				action='<%="selectObject.do?codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.rule.caption&decorator=/formatters/events/chooserulesformatter.xml"%>'
																				image="img/search-mg.gif" 
																				showFrame="true"
																				titleKeyLink="select.rule"
																				>
																<ispac:parameter name="SELECT_RULE" id="property(SPAC_P_PLAZO:BASEDATE_RULE_ID)" property="ID"/>
																<ispac:parameter name="SELECT_RULE" id="property(EXTRA:RULE_NAME)" property="NOMBRE"/>
															</ispac:htmlTextImageFrame></nobr>	
														</td>
													</tr>																
												</table>
											</div>
											<div>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="30%">
														<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.selecCalendar" tooltipKey="procedure.plazos.propertyLabel.selecCalendar.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:select name="entapp" property="property(SPAC_P_PLAZO:CALENDAR_TYPE)" styleClass="inputSelectScustomWidth" 
															styleId="selectCalendarType" onchange="javascript:onCalendarTypeChange();">
														         <html:optionsCollection name="entapp" property="property(CALENDAR_TYPES)"/>
														</html:select>
														<div id="formErrors">
															<html:errors property="property(SPAC_P_PLAZO:CALENDAR_TYPE)"/>
														</div>
													</td>
												</tr>
											</table>
											</div>
											<div id="divRULECalendar">
												<table border="0" cellspacing="0" cellpadding="0" width="100%">
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td height="20" class="formsTitle" width="30%">
															<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.calendar.ruleId" tooltipKey="procedure.plazos.propertyLabel.calendar.ruleId.tooltip"/>
														</td>
														<td height="20">
															<html:hidden name="entapp" property="property(SPAC_P_PLAZO:CALENDAR_RULE_ID)" />
															&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(EXTRA:CALENDARRULE_NAME)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly" 
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_CALENDARRULE"
																				target="workframe"
																				action='<%="selectObject.do?codetable=SPAC_CT_REGLAS&codefield=ID&valuefield=NOMBRE&caption=select.rule.caption&decorator=/formatters/events/chooserulesformatter.xml"%>'
																				image="img/search-mg.gif" 
																				showFrame="true"
																				titleKeyLink="select.rule"
																				>
																<ispac:parameter name="SELECT_CALENDARRULE" id="property(SPAC_P_PLAZO:CALENDAR_RULE_ID)" property="ID"/>
																<ispac:parameter name="SELECT_CALENDARRULE" id="property(EXTRA:CALENDARRULE_NAME)" property="NOMBRE"/>
															</ispac:htmlTextImageFrame></nobr>	
														</td>
													</tr>																
												</table>
											</div>
											<div id="divCatalogCalendar">
												<table border="0" cellspacing="0" cellpadding="0" width="100%">
													<tr>
														<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
													</tr>
													<tr>
														<td height="20" class="formsTitle" width="30%">
															<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.CalendarId" tooltipKey="procedure.plazos.propertyLabel.CalendarId.tooltip"/>
														</td>
														<td height="20">
															<html:hidden name="entapp" property="property(SPAC_P_PLAZO:CALENDAR_ID)" />
															&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(EXTRA:CALENDAR_NAME)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly" 
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_CALENDARID"
																				target="workframe"
																				action='<%="selectObject.do?codetable=SPAC_CALENDARIOS&codefield=ID&valuefield=NOMBRE&caption=select.calendar.caption&decorator=/formatters/calendar/choosecalendarformatter.xml"%>'
																				image="img/search-mg.gif" 
																				showFrame="true"
																				titleKeyLink="select.calendar" 
																				>
																<ispac:parameter name="SELECT_CALENDARID" id="property(SPAC_P_PLAZO:CALENDAR_ID)" property="ID"/>
																<ispac:parameter name="SELECT_CALENDARID" id="property(EXTRA:CALENDAR_NAME)" property="NOMBRE"/>
															</ispac:htmlTextImageFrame></nobr>	
														</td>
													</tr>																
												</table>
											</div>
											
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="15px"/></td>
									</tr>
								</table>
								
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>

<script>
	onDeadlineTypeChange();		
	onCalendarTypeChange();
</script>
</div>