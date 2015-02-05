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
	  <h4><bean:message key="catalog.createnewsubpcd"/></h4>	
		<div class="acciones_ficha">
			<a  class="btnCancel" href="javascript:top.ispac_hideFrame('workframerefresh',true,'<ispac:rewrite page="wait.jsp"/>')"><bean:message key="wizard.button.exit"/></a>
			<logic:present name="ErrorMessage">
				<html:link  styleClass="btnAnt" action='/overviewCreateSubProcedure.do'>
					<bean:message key="wizard.button.prev"/>
				</html:link>
			</logic:present>
			<logic:notPresent name="ErrorMessage">
				<bean:define id="URL" name="NewSubPcdURL" toScope="page"/>
				<a class="btnOk" href="javascript:ispac_mainload('workframerefresh','<%=URL.toString()%>')">
					<bean:message key="subprocedure.wizard.create.goToNewSubProc"/>
				</a>
		</logic:notPresent>
		</div>
	</div>
</div>


<div id="helpLink">
	<ispac:onlinehelp tipoObj="41" image="img/help.gif" titleKey="title.help"/>
</div>

<div id="navSubMenuTitle">
	<bean:message name="Message"/>
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
