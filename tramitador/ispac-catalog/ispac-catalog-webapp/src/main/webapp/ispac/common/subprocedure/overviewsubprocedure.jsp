<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">

<div class="encabezado_ficha">
	<div class="titulo_ficha">
	  <h4>
	  	<bean:message key="catalog.createnewsubpcd"/>&nbsp;&gt;&nbsp;
	  	<logic:notEqual name="BlankSubPcd" value="true">
			<bean:message key="subprocedure.wizard.create.activities"/>
			&nbsp;&gt;&nbsp;
		</logic:notEqual>
		<bean:message key="subprocedure.wizard.create.resume"/></h4>	
		<div class="acciones_ficha">
			
		<html:link  styleClass="btnAnt" action='/overviewCreateSubProcedure.do?forward=prev'>
				<bean:message key="wizard.button.prev"/>
			</html:link>
		<html:link  styleClass="btnSgt" action='/overviewCreateSubProcedure.do?forward=next'>
				<bean:message key="subprocedure.wizard.create.subprocedure"/>
		</html:link>
		</div>
	</div>
</div>



<div id="helpLink">
	<ispac:onlinehelp tipoObj="40" image="img/help.gif" titleKey="title.help"/>
</div>



<logic:equal name="BlankSubPcd" value="true">

	<div id="navSubMenuTitle">
		<bean:message key="subprocedure.wizard.create.confirm.blank"/>
	</div>
	
</logic:equal>

<logic:notEqual name="BlankSubPcd" value="true">

	<div id="navSubMenuTitle">
		<bean:message key="subprocedure.wizard.create.confirm"/>:
	</div>
	
	<tiles:insert page="../tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="ActivitiesList"/>
		<tiles:put name="ItemFormatterAttr" value="ActivitiesListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/overviewCreateSubProcedure.do"/>
	</tiles:insert>
	
</logic:notEqual>
</div>

<script>	
		$(document).ready(function(){
		$("#move").draggable();
	});
	
</script>