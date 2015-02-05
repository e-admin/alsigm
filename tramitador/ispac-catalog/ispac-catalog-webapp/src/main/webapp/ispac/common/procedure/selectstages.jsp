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
			<bean:message key="catalog.createnewpcd"/>
			&nbsp;&gt;&nbsp;
			<bean:message key="procedure.wizard.create.stages"/></h4>
		<div class="acciones_ficha">
			
			<html:link styleClass="btnAnt" action='/selectStages.do?forward=prev'><bean:message key="wizard.button.prev"/></html:link>
			<html:link styleClass="btnSgt" action='/selectStages.do?forward=next'><bean:message key="wizard.button.next"/></html:link>
		</div>
	</div>
</div>






<div id="helpLink">
	<ispac:onlinehelp tipoObj="33" image="img/help.gif" titleKey="title.help"/>
</div>

<div id="navSubMenuTitle">
	<bean:message key="procedure.wizard.create.stagesSel"/>
</div>
<%-- 
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<html:link action='/selectStages.do?forward=prev'>
				<bean:message key="wizard.button.prev"/>
			</html:link>
		</li>
		<li>
			<html:link action='/selectStages.do?forward=next'>
				<bean:message key="wizard.button.next"/>
			</html:link>
		</li>
	</ul>
</div>
--%>
<div id="formErrors">
	<html:errors/>
</div>

<script>

	function submitFormAdd(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectStages.do?add=true";
		frm.submit();
	}
	
	function submitFormDelete(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectStages.do?del=true";
		frm.submit();
	} 
	
	function submitFormSubir(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectStages.do?modOrder=INC";
		frm.submit();
	} 
	
	function submitFormBajar(){
	
		var frm = document.getElementById('batchForm');
		frm.action = '<%=request.getContextPath()%>' + "/selectStages.do?modOrder=DEC";
		frm.submit();
	} 

</script>

<html:form styleId="batchForm" action='/selectStages.do'>

<div>

	<h4><bean:message key="procedure.wizard.create.selectedStages"/></h4>
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
			<tiles:put name="ItemListAttr" value="SelItemsList"/>
			<tiles:put name="ItemFormatterAttr" value="SelItemsListFormatter"/>
			<tiles:put name="ItemActionAttr" value="/selectStages.do"/>
		</tiles:insert>

	<h4><bean:message key="procedure.wizard.create.availableStages"/></h4>
	<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:submitFormAdd();">
				<bean:message key="forms.button.add"/>
			</a>
		</li>
	</ul>
	</div>

	<tiles:insert page="../tiles/displaytag.jsp" ignore="false" flush="false">
		<tiles:put name="ItemListAttr" value="ItemsList"/>
		<tiles:put name="ItemFormatterAttr" value="ItemsListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/selectStages.do"/>
	</tiles:insert>

</div>

</html:form>

</div>
<script>
$(document).ready(function(){
		$("#move").draggable();
	});
</script>