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
		<h4><bean:message key="catalog.createnewpcd"/></h4>
		<div class="acciones_ficha">
			
		<a  class="btnCancel" href="javascript:top.ispac_hideFrame('workframe',true,'<ispac:rewrite page="wait.jsp"/>')"><bean:message key="wizard.button.exit"/></a>
		<logic:notPresent name="ErrorMessage">
			<bean:define id="URL" name="NewPcdURL" toScope="page"/>
			
				<a class="btnOk" href="javascript:ispac_mainload('workframe','<%=URL.toString()%>')">
					<bean:message key="procedure.wizard.create.goToNewProc"/>
				</a>
		
		</logic:notPresent>
		</div>
	</div>
</div>


<div id="navmenutitle">
	<bean:message key="catalog.createnewpcd"/>
</div>

<div id="helpLink">
	<ispac:onlinehelp tipoObj="37" image="img/help.gif" titleKey="title.help"/>
</div>

<div id="navSubMenuTitle">
	<bean:message name="Message"/>
</div>


<div id="navmenu">
<%-- 
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:top.ispac_hideFrame('workframe',true,'<ispac:rewrite page="wait.jsp"/>')"><bean:message key="wizard.button.exit"/></a>
		</li>
		<logic:notPresent name="ErrorMessage">
			<bean:define id="URL" name="NewPcdURL" toScope="page"/>
			<li>
				<a href="javascript:ispac_mainload('workframe','<%=URL.toString()%>')">
					<bean:message key="procedure.wizard.create.goToNewProc"/>
				</a>
			</li>
		</logic:notPresent>
	</ul>
	--%>
</div>

<logic:present name="ErrorMessage">
	<div class='default'>
		<div id="appErrors">
			<bean:write name="ErrorMessage"/>
		</div>
	</div>
</logic:present>
</div>
<script>
function ispac_mainload(frame,url)
{
	//top.hideFrame(frame,'<ispac:rewrite page="wait.jsp"/>');
	top.window.location.href='<%=request.getContextPath()%>'+url;
}
$(document).ready(function(){
		$("#move").draggable();
	});
</script>