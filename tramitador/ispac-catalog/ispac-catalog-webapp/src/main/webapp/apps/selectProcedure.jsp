<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<div id="move">

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message name="CaptionKey"/></h4>
		<div class="acciones_ficha">
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>





<div id="navmenu">
	<tiles:insert page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<div id="bodycontainer">

	<html:form action="/selectObject.do" method="post">

		<html:hidden property="codetable"/>
		<html:hidden property="codefield"/>
		<html:hidden property="valuefield"/>
		<html:hidden property="decorator"/>
		<html:hidden property="parameters"/>
		<html:hidden property="caption"/>

		<!--
			PRESENTACIÓN DEL ÁRBOL DE PROCEDIMIENTOS
		-->
		
		<div class="divTree">
			<ispac:listTree nameList="CTProceduresList" nameFormatter="CTProceduresListFormatter" styleButton="form_button_white" selectedId="idProc" select="true"/>
		</div>
	
	</html:form>

</div>
</div>

<script>
	function SelectObject(url)
	{
		document.selectObjForm.action = url;
		document.selectObjForm.submit();
	}
	
		$(document).ready(function(){
		$("#move").draggable();
	});
	
		$("#btnCancel").click(function() {
				<ispac:hideframe />
			});
</script>
