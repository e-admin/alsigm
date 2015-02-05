<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>	

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>
<jsp:useBean id="action" type="java.lang.String"/>



<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="entity.wizard.title.step3.select.fields"/></h4>
		<div class="acciones_ficha">
			<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.accept"/></a>		
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>
<html:form action='<%=action%>'>

	<div id="bodycontainer">
	
		<tiles:insert page="../tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="FieldList"/>
			<tiles:put name="ItemFormatterAttr" value="FieldListFormatter"/>
			<tiles:put name="ItemActionAttr" value='<%=action%>'/>
		</tiles:insert>
		
	</div>
	
	<div id="navmenu">
	
		<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
			<%-- <ul class="actionsMenuList">
				<li>
					<a href="javascript:submitTop();"><nobr><bean:message key="forms.button.accept"/></nobr></a>
				</li>
				<li>
					<a href="javascript:cerrar();"><nobr><bean:message key="forms.button.cancel"/></nobr></a>
				</li>
			</ul>
			--%>
		</tiles>     
	
	</div>

</html:form>
</div>

<script language='JavaScript' type='text/javascript'><!--

	function getValueOfElementByName(name){
	
		objects = window.top.document.getElementsByName(name);
		if (objects) {
			if (objects.length) {
				return objects[0].value;
			} else {
				return objects.value;
			}
		}
	}
	
	function getValueOfCheckByName(name){
	
		value = "";
		objects = window.top.document.getElementsByName(name);
		if (objects) {
			if (objects.length) {
				if (objects[0].checked) {
					value = objects[0].value;
				}
			} else {
				if (objects.checked) {
					value = objects.value;
				}
			}
		}
		return value;
	}
	
	function submitTop(){

		tokenIdsSeleccionados = "";
		if (document.forms[0].multibox.length){
			for (i=0;i<document.forms[0].multibox.length;i++){
				if (document.forms[0].multibox[i].checked)
					tokenIdsSeleccionados = tokenIdsSeleccionados + "multibox=" + document.forms[0].multibox[i].value + "&";
			}
		}else{
			object = document.getElementsByName('multibox')[0];
			if (object.checked)
				tokenIdsSeleccionados = tokenIdsSeleccionados + "multibox=" + object.value + "&";
		
		}
		var actionURL = '<ispac:rewrite action='<%=action%>'/>' + '?method=addIndexField&'+tokenIdsSeleccionados;
		actionURL = actionURL + "&property(NAME)=" + getValueOfElementByName('property(NAME)');
		actionURL = actionURL + "&property(UNIQUE)=" + getValueOfCheckByName('property(UNIQUE)');
		//actionURL = actionURL + "&property(DOCUMENTAL)=" + getValueOfCheckByName('property(DOCUMENTAL)');
		window.top.document.forms[0].action = actionURL;
		window.top.document.forms[0].submit();
	}
	
	function cerrar(){
		<ispac:hideframe refresh="false"/>
	}
	
		
	$("#btnCancel").click(function() {
				cerrar();
	});
	
	$("#btnOk").click(function() {
				submitTop();
	});
	
			
	
	$(document).ready(function(){
		$("#move").draggable();
	});
	

//--></script>