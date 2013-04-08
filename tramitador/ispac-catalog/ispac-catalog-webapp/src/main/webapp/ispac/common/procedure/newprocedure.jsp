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
		<h4><bean:message key="catalog.createnewpcd"/></h4>
		<div class="acciones_ficha">	
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
			<a href="#" id="btnSgt" class="btnSgt"><bean:message key="wizard.button.next"/></a>	
		</div>
	</div>
</div>

<div id="helpLink">
	<ispac:onlinehelp tipoObj="32" image="img/help.gif" titleKey="title.help"/>
</div>

<logic:equal name="draftExists" value="true">
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');parent.hideFrame('workframerefresh','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="wizard.button.cancel"/>
			</a>
		</li>
	</ul>
</div>

<div style="margin: 5px;">
	<div id="appErrors">
		<br/>
		<bean:message key="procedure.card.error.draftExists"/>
		<br/>
		<br/>
	</div>
</div>
</logic:equal>

<logic:notEqual name="draftExists" value="true">
<div id="navSubMenuTitle">
	<bean:message key="procedure.new.subtitle"/>
</div>
<%-- 
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');parent.hideFrame('workframerefresh','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="wizard.button.cancel"/>
			</a>
		</li>
		<li>
			<a href="javascript:ispac_submit('<%= request.getContextPath() + "/newProcedure.do?forward=next"%>')">
				<bean:message key="wizard.button.next"/>
			</a>
		</li>
	</ul>
</div>
--%>
<html:form action='/newProcedure.do?forward=next'>

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
					<ispac:tooltipLabel labelKey="procedure.wizard.create.procName" tooltipKey="procedure.wizard.create.procName.tooltip"/>
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
			<logic:equal name="newProcedureForm" property="pcdtype" value="clone">
				<tr>
					<td height="20" class="formsTitle" width="1%">
						<ispac:tooltipLabel labelKey="procedure.wizard.create.where" tooltipKey="procedure.wizard.create.where.tooltip"/>
					</td>
					<td class="formsTitle">
					    	&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="1"/>
					    	<span> <bean:message key="procedure.wizard.create.asRoot"/></span><br/>
							&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="2"/>
							<span> <bean:message key="procedure.wizard.create.asChild"/></span><br/>
							&nbsp;<html:radio style="vertical-align:text-top;" property="parent" value="3"/>
							<span> <bean:message key="procedure.wizard.create.asEqual"/></span><br/>
					</td>
				</tr>
			</logic:equal>
			<logic:equal name="newProcedureForm" property="pcdtype" value="new">
				<tr>
					<td height="20" class="formsTitle" width="1%">
						<ispac:tooltipLabel labelKey="procedure.wizard.create.blank" tooltipKey="procedure.wizard.create.blank.tooltip"/>
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
$(document).ready(function() {

			$("#move").draggable();
			
			$("#btnSgt").click(function() {
				ispac_submit('<%= request.getContextPath() + "/newProcedure.do?forward=next"%>');
			});
			 
			$("#btnCancel").click(function() {
				parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');
				parent.hideFrame('workframerefresh','<ispac:rewrite page="wait.jsp"/>');
			});
			
			
			
			
		});

	
</script>