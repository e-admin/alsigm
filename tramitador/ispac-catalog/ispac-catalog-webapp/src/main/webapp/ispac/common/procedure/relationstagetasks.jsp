<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<c:set var="itemId" value="${param.itemId}"/>
<bean:define id="itemId" name="itemId" type="java.lang.String" toScope="page"/>

<script>
function submitFormAdd(){
var frm = document.getElementById('batchForm');
frm.action = '<%=request.getContextPath()%>' + "/relationStageTasks.do?add=true";
frm.submit();
} 
function submitFormDelete(){
var frm = document.getElementById('batchForm');
frm.action = '<%=request.getContextPath()%>' + "/relationStageTasks.do?del=true";
frm.submit();
} 
function submitFormSubir(){
var frm = document.getElementById('batchForm');
frm.action = '<%=request.getContextPath()%>' + "/relationStageTasks.do?modOrder=INC";
frm.submit();
} 
function submitFormBajar(){
var frm = document.getElementById('batchForm');
frm.action = '<%=request.getContextPath()%>' + "/relationStageTasks.do?modOrder=DEC";
frm.submit();
} 

</script>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="procedure.wizard.create.stage"/>&nbsp;'<bean:write name="CTStage" property='property(NOMBRE)'/>'
	&nbsp;&nbsp;<bean:message key="procedure.wizard.create.tasks"/></h4>
		<div class="acciones_ficha">
			
				<html:link  styleClass="btnAnt" action='<%="/relationStageTasks?forward=prev&itemId="+itemId%>'><bean:message key="forms.button.back"/></html:link>
	
		</div>
	</div>
</div>





<div id="helpLink">
	<ispac:onlinehelp tipoObj="35" image="img/help.gif" titleKey="title.help"/>
</div>

<div id="navSubMenuTitle">
	<bean:message key="procedure.wizard.create.stageTasksSel"/>
</div>



<html:errors/>
  
<div>
	<h4><bean:message key="procedure.wizard.create.selectedTasks"/></h4>
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

<html:form styleId="batchForm" action='/relationStageTasks.do'>

	<input type="hidden" name="itemId" value='<bean:write name="itemId"/>'/>

	<tiles:insert page="../tiles/displaytag.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="SelItemsList"/>
		<tiles:put name="ItemFormatterAttr" value="SelItemsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/relationStageTasks.do"/>
	</tiles:insert>

	<h4><bean:message key="procedure.wizard.create.availableTasks"/></h4>
		<div id="navmenu">
			<ul class="actionsMenuList">
				<li>
					<a href="javascript:submitFormAdd();">
						<bean:message key="forms.button.add"/>
					</a>
				</li>
			</ul>
		</div>
		<tiles:insert page="../tiles/displaytag.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="ItemsList"/>
			<tiles:put name="ItemFormatterAttr" value="ItemsListFormatter"/>
			<tiles:put name="ItemActionAttr" value="/relationStageTasks.do"/>
		</tiles:insert>
</html:form>
</div>
</div>

<script>
$(document).ready(function(){
		$("#move").draggable();
	});
</script>
