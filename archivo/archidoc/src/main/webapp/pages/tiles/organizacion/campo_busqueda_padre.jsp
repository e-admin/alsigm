<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingGestionOrganizacion" mapping="/gestionOrganizacionAction" />
<c:set var="gestionOrganizacionFormName" value="${mappingGestionOrganizacion.name}" />
<c:set var="gestionOrganizacionForm" value="${requestScope[gestionOrganizacionFormName]}" />	
<c:set var="padre" value="${sessionScope[appConstants.organizacion.PADRE_ELEMENTO_ORG]}"/>
<c:set var="esOrgano" value="${organizacionForm.tipo==2}" />

<div id="bgBusqPadre" style="visibility:hidden; background-image:url(<c:url value="/pages/images/fondo.gif" />);z-index:99;position:absolute;top:0px;left:0px;"></div>
<div id="busqPadre" style="display:none;z-index:100;position:absolute;width:600px;height:500px;top:0px;left:0px;">
	
	<div class="block_title" style="-moz-box-sizing: border-box;width:100%;display:table;_display:block">
		<div style="width:100%;display:table-row;_display:block">
			<div style="width:50%;text-align:left;vertical-align:middle;display:table-cell;_display:inline">
				&nbsp;&nbsp;<c:out value="${classTitulo}"/>
			</div>
			<div style="width:49%;text-align:right;vertical-align:middle;display:table-cell;_display:inline">
				<a class="etiquetaAzul12Bold" href="javascript:hideBusqPadreDiv()">
					<html:img page="/pages/images/close.gif" 
						styleClass="imgTextMiddle" 
						titleKey="organizacion.cerrar"  
						altKey="organizacion.cerrar"/>
					&nbsp;<bean:message key="organizacion.cerrar"/>
				</a>&nbsp;&nbsp;
			</div>
		</div>
	</div>

	<div class="block_content" style="background: #FFFFFF;padding-right:10px;_padding-bottom:0;height:100%;">
		<iframe name="frmVistaOrganizacion" id="frmVistaOrganizacion" 
			src="<c:url value="/action/manageVistaOrganizacionSeleccion?actionToPerform=home"/>" 
			width="100%" height="100%" scrolling="automatic" 
			frameborder="0" style="overflow:visible;_width:580px;_height:385px;">
		</iframe>
	</div>
</div>

<script language="JavaScript1.2" type="text/JavaScript">
	
	function showBusqPadreDiv() {
		var busqPadre = xGetElementById('busqPadre');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		var minWidth  = Math.max(500,clientWidth/2);
		var minHeight = Math.max(400,clientHeight/2);

		xHide(busqPadre);
		xDisplay(busqPadre, 'block');

		xHeight('bgBusqPadre',clientHeight-60);
		xWidth('bgBusqPadre',clientWidth-90);

		xHeight('busqPadre', minHeight);
		xWidth('busqPadre', minWidth);

		var top = Math.min(100,(clientHeight - xHeight(busqPadre))/2);
		var left = (clientWidth - xWidth(busqPadre))/2;
		xMoveTo(busqPadre, left, top);
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "hidden";
		xShow("bgBusqPadre");
		xShow(busqPadre);
	}
	
	function hideBusqPadreDiv()
	{
		var busqPadre = xGetElementById('busqPadre');
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "visible";
		xHide("bgBusqPadre");
		xHide(busqPadre);
	}
	
	function searchOrganizacion(divId)
	{
		showBusqPadreDiv();
		if (divId)
			currentDivId = divId;
	}
	
	function clearOrganizacion(divId)
	{
		if (divId)
			currentDivId = divId;
		setOrganizacion("","");
	}
	
	function setOrganizacion(code, value)
	{
		hideBusqPadreDiv();
		if (currentDivId != null)
		{
			var div = xGetElementById(currentDivId);
			if (div)
			{
				var inputs = div.getElementsByTagName("input");
				for (var i = 0; i < inputs.length; i++)
				{
					if (inputs[i].name == "idPadre")
						inputs[i].value = code;
					else if (inputs[i].name == "nombrePadre")
						inputs[i].value = unescapeHTML(value);
				}
			}
			currentDivId = null;
		}
	}
	var currentDivId = null;	
</script>

<%-- Filtro por Organizaciones --%>
<tr>
	<td class="tdTitulo" width="150">
		<c:out value="${classTdTituloCampo}"/>:&nbsp;
	</td>

	<td class="tdDatos" id="tdPadres">
		<c:set var="uniqueId" value="${appConstants.uniqueId}"/>
		<div id="divPadre<c:out value="${uniqueId}"/>">
			<html:hidden property="idPadre" styleId="idPadre"/>
			<html:text property="nombrePadre" styleId="nombrePadre" readonly="readonly" styleClass="inputRO90"/>
		
			<c:if test="${!esOrgano}">
				<a href="javascript:searchOrganizacion('divPadre<c:out value="${uniqueId}"/>')"
					><html:img styleId="imgExpand"
						page="/pages/images/expand.gif" 
						styleClass="imgTextMiddle" /></a>	
					
				<a href="javascript:clearOrganizacion('divPadre<c:out value="${uniqueId}"/>');"
					><html:img styleId="imgClear" 
					   page="/pages/images/clear0.gif" 
					   styleClass="imgTextMiddle"/></a>		
			</c:if>
		</div>
	</td>
</tr>