<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />

<script language=javascript type='text/javascript'>
	function onDeadlineTypeChange(){
		var miDiv = "div" + document.getElementById('selectBaseDateType').value;
		if(miDiv == "divENTITY"){
			document.getElementById('divENTITY').style.visibility = 'visible'; 
			document.getElementById('divENTITY').style.display = ''; 
			document.getElementById('divRULE').style.visibility = 'hidden';
			document.getElementById('divRULE').style.display = 'none';
			return;
		}
		if(miDiv == "divRULE"){
			document.getElementById('divRULE').style.visibility = 'visible';
			document.getElementById('divRULE').style.display = '';
			document.getElementById('divENTITY').style.visibility = 'hidden';
			document.getElementById('divENTITY').style.display = 'none'; 			
			return;
		}
		document.getElementById('divRULE').style.visibility = 'hidden';
		document.getElementById('divENTITY').style.visibility = 'hidden';					
	}

</script> 

<div style="display:none" id="block2" class="tabFormtable">
	<html:hidden property="property(SPAC_P_PLAZO:ID)"/>
	<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
		<tr>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
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
						<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
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
								&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_ID)" styleClass="inputSelectScustomWidth" size="61" readonly="false" maxlength="250"/>
							</td>
						</tr>
						<tr>
							<td height="20" class="formsTitle" width="30%">
								<ispac:tooltipLabel labelKey="procedure.plazos.propertyLabel.entityField" tooltipKey="procedure.plazos.propertyLabel.entityField.tooltip"/>
							</td>
							<td height="20">
								&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)" styleClass="inputSelectScustomWidth" size="61" readonly="false" maxlength="250"/>
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
								&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_RULE_ID)" styleClass="inputSelectScustomWidth" size="61" readonly="false" maxlength="250"/>
							</td>
						</tr>																		
						<tr>
							<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>


<script>
	onDeadlineTypeChange();		
</script>