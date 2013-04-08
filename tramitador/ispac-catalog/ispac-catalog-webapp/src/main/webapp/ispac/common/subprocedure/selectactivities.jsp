<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
	  <h4>
	  	<bean:message key="catalog.createnewsubpcd"/>
		&nbsp;&gt;&nbsp;
		<bean:message key="subprocedure.wizard.create.activities"/>
	 </h4>	
		<div class="acciones_ficha">
			<html:link  styleClass="btnAnt" action='/selectActivities.do?forward=prev'><bean:message key="wizard.button.prev"/></html:link>
			<html:link styleClass="btnSgt" action='/selectActivities.do?forward=next'><bean:message key="wizard.button.next"/></html:link>
		</div>
	</div>
</div>


<div id="helpLink">
	<ispac:onlinehelp tipoObj="40"  image="img/help.gif" titleKey="title.help"/>
</div>





<html:form styleId="batchForm" action='/selectActivities.do'>

<div id="formErrors">
	<html:errors/>
</div>

<div id="stdform">
	<table border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td height="20" class="formsTitle" width="1%">
				<ispac:tooltipLabel labelKey="subprocedure.wizard.create.activityName" tooltipKey="subprocedure.wizard.create.activityName.tooltip"/>
			</td>
			<td height="20">
				&nbsp;&nbsp;<input type="text" name="activityName" size="40" maxlength="250"/>
			</td>
			<td height="20">
				<input type="button" onclick="javascript:submitFormAdd()" class="form_button_white" value='<bean:message key="forms.button.add"/>'/>
			</td>
		</tr>
	</table>
</div>



<div>
	<h4><bean:message key="subprocedure.wizard.create.selectedActivities"/></h4>
	<div id="navmenu">
		<ul class="actionsMenuList">
			<li>
				<a href="javascript:submitFormDelete();">
					<bean:message key="forms.button.delete"/>
				</a>
			</li>
			<li>
				<a href="javascript:submitFormSubir();">
					<bean:message key="button.up"/>
				</a>
			</li>
			<li>
				<a href="javascript:submitFormBajar();">
					<bean:message key="button.down"/>
				</a>
			</li>
		</ul>
	</div>

	<tiles:insert page="../tiles/displaytag.jsp" ignore="false" flush="false">
		<tiles:put name="ItemListAttr" value="ActivitiesList"/>
		<tiles:put name="ItemFormatterAttr" value="ActivitiesListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/selectActivities.do"/>
	</tiles:insert>

</div>

</html:form>
</div>


<script>

	function submitFormAdd(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectActivities.do?add=true";
		frm.submit();
	}
	
	function submitFormDelete(){
		
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectActivities.do?del=true";
		frm.submit();
	}
	
	function submitFormSubir(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectActivities.do?modOrder=INC";
		frm.submit();
	}
	
	function submitFormBajar(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectActivities.do?modOrder=DEC";
		frm.submit();
	} 
	
		$(document).ready(function(){
		$("#move").draggable();
	});
	
</script>
