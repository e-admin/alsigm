<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div id="bgBusqUbicacion">&nbsp;</div>
<div id="busqUbicacion">

	<div class="block_title">
		<div class="block_title2">
			<div class="block_title_left">
				&nbsp;&nbsp;<bean:message key="archigest.archivo.deposito.busqueda.seleccionUbicacion"/>
			</div>
			<div class="block_title_right">
				<a class="etiquetaAzul12Bold" href="javascript:hideBusqUbicacionDiv()">
					<html:img page="/pages/images/close.gif"
						styleClass="imgTextMiddle"
						titleKey="archigest.archivo.cerrar"
						altKey="archigest.archivo.cerrar"/>
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>&nbsp;&nbsp;
			</div>
		</div>
	</div>

	<div class="block_content">
		<iframe name="frmVistaDeposito" id="frmVistaDeposito"
			src="<c:url value="/action/depositoSeleccion?actionToPerform=home"/>"
			width="100%" height="100%" scrolling="auto" frameborder="0">
		</iframe>
	</div>
</div>

<script language="JavaScript1.2" type="text/JavaScript">
	function showBusqUbicacionDiv()
	{
		var busqUbicacion = xGetElementById('busqUbicacion');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		var minWidth  = Math.max(500,clientWidth/2);
		var minHeight = Math.max(400,clientHeight/2);

		xHide(busqUbicacion);
		xDisplay(busqUbicacion, 'block');

		xHeight('bgBusqUbicacion',clientHeight);
		xWidth('bgBusqUbicacion',clientWidth-32);

		xHeight('busqUbicacion', minHeight);
		xWidth('busqUbicacion', minWidth);

		var top = Math.min(100,(clientHeight - xHeight(busqUbicacion))/2);
		var left = (clientWidth - xWidth(busqUbicacion))/2;
		xMoveTo(busqUbicacion, left, top);
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "hidden";
		xShow("bgBusqUbicacion");
		xShow(busqUbicacion);
	}
	function hideBusqUbicacionDiv()
	{
		var busqUbicacion = xGetElementById('busqUbicacion');
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "visible";
		xHide("bgBusqUbicacion");
		xHide(busqUbicacion);
	}
	function searchUbicacion(divId)
	{
		showBusqUbicacionDiv();
		if (divId)
			currentDivId = divId;
	}

	function clearUbicacion(divId)
	{
		if (divId)
			currentDivId = divId;
		setUbicacion("","");
		removeAmbitos();
	}

	function setUbicacion(code, value)
	{
		hideBusqUbicacionDiv();
		if (currentDivId != null)
		{
			var div = xGetElementById(currentDivId);
			if (div)
			{
				var inputs = div.getElementsByTagName("input");
				for (var i = 0; i < inputs.length; i++)
				{
					if (inputs[i].name == "idUbicacion")
						inputs[i].value = code;
					else if (inputs[i].name == "nombreUbicacion")
						inputs[i].value = unescapeHTML(value);
				}
				removeAmbitos();
			}
			currentDivId = null;
		}
	}
	var currentDivId = null;
</script>

<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.ubicacion"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>" id="tdUbicaciones">

		<c:set var="uniqueId" value="${appConstants.uniqueId}"/>
		<div id="divUbicacion<c:out value="${uniqueId}"/>">
			<input type="hidden" name="idUbicacion" id="idUbicacion"/>
			<input type="text" name="nombreUbicacion" id="nombreUbicacion" readonly class="inputRO90"/>
			<script>
				var campoTexto=document.getElementById("nombreUbicacion");
				campoTexto.readOnly=false;
				//campoTexto.innerText='<c:out value="${requestScope.DepositoForm.nombreUbicacion}" />';
				campoTexto.value='<c:out value="${requestScope.DepositoForm.nombreUbicacion}" escapeXml="false"/>';
				campoTexto.readOnly=true;

				campoTexto=document.getElementById("idUbicacion");
				campoTexto.value='<c:out value="${requestScope.DepositoForm.idUbicacion}" escapeXml="false"/>';

				//var debug="";
				//var i=0;
				//for(atr in campoTexto){
				//	debug+="Text."+atr+"='"+campoTexto[atr]+"'    ";
				//	if(i==5){ debug+="\n"; i=0; }
				//	i++;
				//}
				//alert(debug);
			</script>

			<a href="javascript:searchUbicacion('divUbicacion<c:out value="${uniqueId}"/>')"
				><html:img styleId="imgExpand"
					page="/pages/images/expand.gif"
					styleClass="imgTextMiddle" /></a>

			<a href="javascript:clearUbicacion('divUbicacion<c:out value="${uniqueId}"/>');"
				><html:img styleId="imgClear"
						   page="/pages/images/clear0.gif"
						   styleClass="imgTextMiddle"/></a>
		</div>

	</td>
</tr>

