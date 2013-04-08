<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div id="bgBusqAmbito">&nbsp;</div>
<div id="busqAmbito">

	<div class="block_title">
		<div class="block_title2">
			<div class="block_title_left">
				&nbsp;&nbsp;<bean:message key="archigest.archivo.cf.seleccionAmbito"/>
			</div>
			<div class="block_title_right">
				<a class="etiquetaAzul12Bold" href="javascript:hideBusqAmbitoDiv()">
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
		<iframe name="frmCuadroSeleccion" id="frmCuadroSeleccion"
			src="<c:url value="/action/cuadroSeleccion?actionToPerform=home"/>"
			width="100%" height="100%" scrolling="auto" frameborder="0">
		</iframe>
	</div>
</div>

<script language="JavaScript1.2" type="text/JavaScript">
	function cleanAmbito(form)
	{
		var td = document.getElementById("tdAmbitos");
		var divs = td.getElementsByTagName("div");
		if (divs)
		{
			for (var countDivs = divs.length-1; countDivs >= 0; countDivs--)
			{
				if (countDivs == 0)
				{
					var inputs = divs[countDivs].getElementsByTagName("input");
					for (var i = 0; i < inputs.length; i++)
					{
						if (inputs[i].name == "idObjetoAmbito")
							inputs[i].value = "";
						else if (inputs[i].name == "nombreObjetoAmbito")
							inputs[i].value = "";
					}
				}
				else if (divs[countDivs].id.search(/^divAmbito/) == 0)
					td.removeChild(divs[countDivs]);
			}
		}
	}
	function addAmbito()
	{
		var td = xGetElementById("tdAmbitos");
		var divAddAmbito = xGetElementById("divAddAmbito");
		if (td && divAddAmbito)
		{
			var uniqueId = getUniqueId();
			var divAmbito = creaElemento("<div id='divAmbito" + uniqueId + "'>");
			divAmbito.innerHTML = "<input type='hidden' name='idObjetoAmbito' />"
				+ "<input type='text' name='nombreObjetoAmbito' readonly='readonly' class='inputRO90'/>\n"
				+ "<a href=\"javascript:searchAmbito('divAmbito" + uniqueId + "')\">"
				+ "<img src='../images/expand.gif' class='imgTextMiddle' /></a>\n"
				+ "<a href=\"javascript:removeAmbito('divAmbito" + uniqueId + "');\">"
				+ "<img src='../images/Ok_No.gif' class='imgTextMiddle'/></a>";
			td.insertBefore(divAmbito, divAddAmbito);
		}
	}
	function removeAmbito(divId)
	{
		var td = document.getElementById("tdAmbitos");
		var divs = td.getElementsByTagName("div");
		if (divs.length == 2)
		{
			var inputs = divs[0].getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				if (inputs[i].name == "idObjetoAmbito")
					inputs[i].value = "";
				else if (inputs[i].name == "nombreObjetoAmbito")
					inputs[i].value = "";
			}
		}
		else
		{
			var div = xGetElementById(divId);
			if (div)
				td.removeChild(div);
		}
	}
	function showBusqAmbitoDiv()
	{
		var busqAmbito = xGetElementById('busqAmbito');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		var minWidth  = Math.max(500,clientWidth/2);
		var minHeight = Math.max(400,clientHeight/2);

		xHide(busqAmbito);
		xDisplay(busqAmbito, 'block');

		xHeight('bgBusqAmbito',clientHeight);
		xWidth('bgBusqAmbito',clientWidth-32);

		xHeight('busqAmbito', minHeight);
		xWidth('busqAmbito', minWidth);

		var top = Math.min(100,(clientHeight - xHeight(busqAmbito))/2);
		var left = (clientWidth - xWidth(busqAmbito))/2;
		xMoveTo(busqAmbito, left, top);
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "hidden";
		xShow("bgBusqAmbito");
		xShow(busqAmbito);
	}
	function hideBusqAmbitoDiv()
	{
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "visible";
		xHide("bgBusqAmbito");
		xHide(busqAmbito);
		xHeight('bgBusqAmbito',0);
		xWidth('bgBusqAmbito',0);
	}
	function searchAmbito(divId)
	{
		showBusqAmbitoDiv();
		if (divId)
			currentDivId = divId;
	}
	function setAmbito(code, value, tipoElemento)
	{
		hideBusqAmbitoDiv();
		if (currentDivId != null)
		{
			var div = xGetElementById(currentDivId);
			if (div)
			{
				var inputs = div.getElementsByTagName("input");
				for (var i = 0; i < inputs.length; i++)
				{
					if (inputs[i].name == "idObjetoAmbito")
						inputs[i].value = code;
					else if (inputs[i].name == "nombreObjetoAmbito")
						inputs[i].value = unescapeHTML(value);
					else if (inputs[i].name == "tipoObjetoAmbito")
						inputs[i].value = "";						
				}
			}
			currentDivId = null;
		}
	}
	var currentDivId = null;
</script>

<%-- Filtro por ámbitos --%>
<tr>
	<td class="tdTituloFicha" width="200px">
		<bean:message key="archigest.archivo.cf.ambito"/>:&nbsp;
	</td>
	<td class="tdDatosFicha" id="tdAmbitos">
		<c:set var="codRefsObjetoAmbito" value="${paramValues.idObjetoAmbito}"/>
		<c:set var="nombresObjetoAmbito" value="${paramValues.nombreObjetoAmbito}"/>
		<c:choose>
		<c:when test="${empty codRefsObjetoAmbito}">
			<c:set var="uniqueId" value="${appConstants.uniqueId}"/>
			<div id="divAmbito<c:out value="${uniqueId}"/>">
				<input type="hidden" name="idObjetoAmbito" />
				<input type="text" name="nombreObjetoAmbito"
					readonly="readonly" class="inputRO90"/>

				<a href="javascript:searchAmbito('divAmbito<c:out value="${uniqueId}"/>')"
					><html:img styleId="imgExpand"
						page="/pages/images/expand.gif"
						styleClass="imgTextMiddle" /></a>

				<a href="javascript:removeAmbito('divAmbito<c:out value="${uniqueId}"/>');"
				><html:img page="/pages/images/Ok_No.gif"
				        styleClass="imgTextMiddle"/></a>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="idObjetoAmbito" items="${codRefsObjetoAmbito}" varStatus="status">
				<c:set var="uniqueId" value="${appConstants.uniqueId}"/>
				<div id="divAmbito<c:out value="${uniqueId}"/>">
					<input type="hidden" name="idObjetoAmbito" value="<c:out value="${idObjetoAmbito}"/>"/>
					<input type="text" name="nombreObjetoAmbito"
						readonly="readonly" class="inputRO90"
						value="<c:out value="${paramValues.nombreObjetoAmbito[status.index]}"/>"/>
					<a href="javascript:searchAmbito('divAmbito<c:out value="${uniqueId}"/>')"
						><html:img styleId="imgExpand"
							page="/pages/images/expand.gif"
							styleClass="imgTextMiddle" /></a>

					<a href="javascript:removeAmbito('divAmbito<c:out value="${uniqueId}"/>');"
					><html:img page="/pages/images/Ok_No.gif"
					        styleClass="imgTextMiddle"/></a>
				</div>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		<div id="divAddAmbito">
			<a class="btLiso" href="javascript:addAmbito()">
				<bean:message key="archigest.archivo.anadir"/>
			</a>
		</div>
	</td>
</tr>
