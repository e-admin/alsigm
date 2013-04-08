<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<bean:define id="draftExists" name="draftExists" />
<div id="move">


<div class="encabezado_ficha">
	<div class="titulo_ficha">
	  <h4><bean:message key="catalog.createnewsubpcd"/></h4>	
		<div class="acciones_ficha">
			
		<a  class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="wizard.button.cancel"/>
			</a>
		<a class="btnSgt" href="javascript:ispac_submit('<%= request.getContextPath() + "/newSubProcedure.do?forward=next"%>')">
				<bean:message key="wizard.button.next"/>
			</a>
		</div>
	</div>
</div>

<div id="helpLink">
	<ispac:onlinehelp tipoObj="38" image="img/help.gif" titleKey="title.help"/>
</div>

<logic:equal name="draftExists" value="true">
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="wizard.button.cancel"/>
			</a>
		</li>
	</ul>
</div>

<div style="margin: 5px;">
	<div id="appErrors">
		<br/>
		<bean:message key="subprocedure.card.error.draftExists"/>
		<br/>
		<br/>
	</div>
</div>
</logic:equal>

<logic:notEqual name="draftExists" value="true">
<div id="navSubMenuTitle">
	<bean:message key="subprocedure.new.subtitle"/>
</div>


<html:form action='/newSubProcedure.do?forward=next'>

	<html:hidden property="pcdId"/>
	<html:hidden property="pcdtype"/>
	<html:hidden property="forward" value="next"/>

	<div id="formErrors">
		<html:errors />
	</div>

	<div id="stdform">
		<table border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td height="20" class="formsTitle" width="1%">
					<ispac:tooltipLabel labelKey="subprocedure.wizard.create.name" tooltipKey="subprocedure.wizard.create.name.tooltip"/>
				</td>
				<td height="20">
					<logic:equal value="false" name="ReadOnly">
							&nbsp;&nbsp;<html:text property="name" size="40"  maxlength="250"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
					</logic:equal>
					<logic:equal value="true" name="ReadOnly">
							&nbsp;&nbsp;<html:text property="name" size="40"  maxlength="250" styleClass="inputReadOnly" readonly="true"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
					</logic:equal>
				</td>
			</tr>
			<%--
			<logic:equal name="newProcedureForm" property="pcdtype" value="clone">
				<tr>
					<td height="20" class="formsTitle" width="1%">
						<ispac:tooltipLabel labelKey="subprocedure.wizard.create.where" tooltipKey="subprocedure.wizard.create.where.tooltip"/>
					</td>
					<td class="formsTitle">
					    	&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="1"/>
					    	<span> <bean:message key="subprocedure.wizard.create.asRoot"/></span><br/>
							&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="2"/>
							<span> <bean:message key="subprocedure.wizard.create.asChild"/></span><br/>
							&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="3"/>
							<span> <bean:message key="subprocedure.wizard.create.asEqual"/></span><br/>
					</td>
				</tr>
			</logic:equal>
			--%>
			<logic:equal name="newProcedureForm" property="pcdtype" value="new">
				<tr>
					<td height="20" class="formsTitle" width="1%">
						<ispac:tooltipLabel labelKey="subprocedure.wizard.create.blank" tooltipKey="subprocedure.wizard.create.blank.tooltip"/>
					</td>
					<td class="formsTitle">
					    	&nbsp;<html:checkbox property="blank" styleClass="checkbox" value="true"/>
					</td>
				</tr>
			</logic:equal>
		</table>
	</div>

</html:form>
</logic:notEqual>
</div>

<script>
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>

