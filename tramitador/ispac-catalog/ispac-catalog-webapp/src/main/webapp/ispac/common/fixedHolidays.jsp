<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="form.calendar.fixedHolidays.mainTitle"/></h4>
		<div class="acciones_ficha">
			
			<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.accept"/></a>		
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>


<!-- Si se quiere mostrar el error arriba debajo del titulo
<logic:messagesPresent>
	<div id="formErrorsMessage">
		<html:errors />
	</div>	
</logic:messagesPresent>

-->

<div id="navSubMenuTitle">
</div>
<%--
<div id="navmenu">
	<ul class="actionsMenuList">
		<logic:notEmpty name="allXmlFixedHolidays">
			<li>
				<a href="javascript:doSubmit()">
					<bean:message key="forms.button.accept"/>
				</a>
			</li>
		</logic:notEmpty>
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</li>
	</ul>
</div>
--%>
<html:form action='/showCTCalendar.do?method=fixedHolydays'>
	
	<html:hidden property="entity"/>
	<html:hidden property="key"/>
	<html:hidden property="year"/>
	
	<div id="stdform">
	
		<table border="0" cellspacing="0" cellpadding="0">
		
			<logic:messagesPresent>
			<tr>
				<td colspan="2">
					<div id="formErrorsMessage">
						<bean:message key="forms.errors.formErrorsMessage"/>
					</div>
					<div id="formErrors">
						<html:errors property="allXmlFixedHolidays"/>
					</div>	
				</td>
			</tr>
			</logic:messagesPresent>
			
			<tr>
				<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
			</tr>
			
			<logic:empty name="allXmlFixedHolidays">
			
				<tr>
					<td height="20" class="formsTitle" width="1%">
					</td>
					<td height="20">
						<div id="formErrors">
							<html:errors/>
						</div>																				
					</td>
				</tr>
			
			</logic:empty>
									
			<logic:notEmpty name="allXmlFixedHolidays">
					
				<tr>
					<td height="20" class="formsTitle" width="1%">
						<ispac:tooltipLabel labelKey="form.calendar.fixedHolidays.selectYear" tooltipKey="form.calendar.fixedHolidays.selectYear.tooltip"/>
					</td>
					<td height="20">
						&nbsp;&nbsp;<html:text property="property(YEAR)" styleClass="inputSelect" readonly="false" maxlength="4"/>
						<div id="formErrors">
							<html:errors property="property(YEAR)"/>
						</div>																				
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
				</tr>
				
				<tr>
					<td colspan="2">
	
						<display:table name="allXmlFixedHolidays" 
									   id="field" 
									   class="tableDisplay" 
									   export="false" 
									   sort="list"
									   defaultsort="2"
									   requestURI='showCTCalendar.do?method=fixedHolydays'
									   excludedParams="property(CALENDARIO)"
									   >
									   																			
							<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].selectFixedHolidays, this);\'/>"%>'
											headerClass="headerDisplay"
											style="text-align:center">
											
								<html:multibox property="selectFixedHolidays">
									<c:out value="${field.date}"/><c:out value="---"/><c:out value="${field.name}"/>
								</html:multibox>
								
							</display:column>
							
							<display:column titleKey="form.calendar.holiday.propertyLabel.fecha" 
											property="date"
											headerClass="sortable"
											class="width20percent"
											comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparatorWithOutYear"
											sortable="true"
											/>																								
	
							<display:column titleKey="form.calendar.holiday.propertyLabel.name" 
											property="name" 
											headerClass="sortable"
											class="width80percent"
											sortable="true"/>									
																																								
						</display:table>
						
					</td>
				</tr>
			
			</logic:notEmpty>
			
		</table>
		
	</div>

</html:form>
</div>

<script language='JavaScript' type='text/javascript'><!--

	function getValueOfElementByName(name){

		objects = window.top.document.getElementsByName(name);
		return objects[0].value;
	}

	function doSubmit() {
	
		tokenIdsSeleccionados = "";		
		if (window.top.document.calendarForm.weekDaysSelect.length){
			for (i=0;i<window.top.document.calendarForm.weekDaysSelect.length;i++){
				element = window.top.document.calendarForm.weekDaysSelect[i];
				if (tokenIdsSeleccionados == "" && element.checked)
					tokenIdsSeleccionados = element.value;
				else if (element.checked)				
					tokenIdsSeleccionados = tokenIdsSeleccionados + "," + element.value;
			}
		}		
		
		actionURL = 'showCTCalendar.do?method=saveFixedHolidays&nombre=' + getValueOfElementByName('property(NOMBRE)')
				  + '&weekDaysSelect=' + tokenIdsSeleccionados;
				  
		document.calendarForm.action = actionURL;
		noSelectedDays(actionURL);		
	}

	function noSelectedDays(url) {
	
		if (checkboxElement(document.calendarForm.selectFixedHolidays) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			document.forms['calendarForm'].action = url;
			document.forms['calendarForm'].submit();
		}	
	}
	
	$(document).ready(function(){
		$("#move").draggable();
		$("#btnOk").click(function() {
				doSubmit();
		});
			 
		$("#btnCancel").click(function() {
				parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');
		});
	});
	
//--></script>
