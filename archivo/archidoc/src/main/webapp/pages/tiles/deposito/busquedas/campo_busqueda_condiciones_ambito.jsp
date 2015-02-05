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

	<c:set var="uniqueId" value="${appConstants.uniqueId}"/>

	<div class="block_content">
		<c:url var="subtreeURL" value="/action/depositoSeleccion">
			<c:param name="actionToPerform" value="homeSubtree"/>
			<c:param name="idUbicacion" value=""/>
		</c:url>
		<iframe name="frmDepositoSubtreeSeleccion" id="frmDepositoSubtreeSeleccion"
			src="<c:out value="${subtreeURL}"/>"
			width="100%" height="100%" scrolling="auto" frameborder="0">
		</iframe>
	</div>
</div>

<script language="JavaScript1.2" type="text/JavaScript">
	function addAmbito()
	{
		var td = xGetElementById("tdAmbitos");
		var divAddAmbito = xGetElementById("divAddAmbito");
		if (td && divAddAmbito)
		{
			var uniqueId = getUniqueId();
			var divAmbito = creaElemento("<div id='divAmbito" + uniqueId + "'>");
			divAmbito.innerHTML = "<input type='hidden' name='idAmbito' /><input type='hidden' name='codOrdenAmbito' />"
				+ "<input type='text' name='nombreAmbito' readonly='readonly' class='inputRO90'/>\n"
				+ "<a href=\"javascript:searchAmbito('divAmbito" + uniqueId + "')\">"
				+ "<img src='../images/expand.gif' class='imgTextMiddle' /></a>\n"
				+ "<a href=\"javascript:removeAmbito('divAmbito" + uniqueId + "');\">"
				+ "<img src='../images/Ok_No.gif' class='imgTextMiddle'/></a>";
			td.insertBefore(divAmbito, divAddAmbito);
		}
	}

	function removeAmbito(divId){
		var td = document.getElementById("tdAmbitos");
		var divs = td.getElementsByTagName("div");
		if (divs.length == 2){
			var inputs = divs[0].getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				if (inputs[i].name == "idAmbito")
					inputs[i].value = "";
				else if (inputs[i].name == "nombreAmbito")
					inputs[i].value = "";
			}
		}else{
			var div = xGetElementById(divId);
			if (div)
				td.removeChild(div);
		}
	}

	function removeAmbitos(){
		var td = document.getElementById("tdAmbitos");
		var divs = td.getElementsByTagName("div");

		for(var i=0;i<divs.length;i++){
			var divAmbito=divs[i];
			if (!divAmbito) continue;
			if(divAmbito.id.indexOf("divAmbito")==-1) continue;
			td.removeChild(divAmbito);
			i--;
		}
		addAmbito();
	}

	function trim(cadena){
		for(i=0; i<cadena.length; ){
			if(cadena.charAt(i)==" ")
				cadena=cadena.substring(i+1, cadena.length);
			else
				break;
		}

		for(i=cadena.length-1; i>=0; i=cadena.length-1){
			if(cadena.charAt(i)==" ")
				cadena=cadena.substring(0,i);
			else
				break;
		}

		return cadena;
	}

	function checkAmbitos(nombreToCheck){
		//comprobar que la ruta añadida no sea un hijo de alguna ya existente
		var td = document.getElementById("tdAmbitos");
		var divs = td.getElementsByTagName("div");

		for(var i=0;i<divs.length;i++){
			var divAmbito=divs[i];
			if (!divAmbito) continue;
			if(divAmbito.id.indexOf("divAmbito")==-1) continue;
			//alert(divAmbito.id+"="+currentDivId);
			if(divAmbito.id==currentDivId) continue;
			var inputs = divs[i].getElementsByTagName("input");
			for (var j = 0; j < inputs.length; j++){
				if (inputs[j].name == "nombreAmbito"){
					var pos=nombreToCheck.indexOf(inputs[j].value);
					if(trim(inputs[j].value)!="" && (pos!=-1
					   //|| inputs[j].value.indexOf(nombreToCheck)!=-1
					 )){
					 	var subcadena=nombreToCheck.substring(pos+inputs[j].value.length);
					 	pos=subcadena.indexOf("/");
					 	if(pos!=-1) subcadena=nombreToCheck.substring(0,pos);
					 	//comprobar que no se este negando dep12 si existe dep1
					 	if(subcadena.length==0){
							alert('<bean:message key="archigest.archivo.deposito.busquedaUInst.AmbitoIncluidoEnOtroSeleccionado"/>');
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	function showBusqAmbitoDiv() {
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
		var busqAmbito = xGetElementById('busqAmbito');
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "visible";
		xHide("bgBusqAmbito");
		xHide(busqAmbito);
		xHeight('bgBusqAmbito',0);
		xWidth('bgBusqAmbito',0);
	}

	var divId="";
	function iframeCargado(evt) {
  		if(pendienteDeCargar){
			pendienteDeCargar=false;
			showBusqAmbitoDiv();
			if (divId) currentDivId = divId;
		}
   	}

	function searchAmbito(paramDivId){
		if(document.getElementById("idUbicacion").value==""){
			alert('<bean:message key="archigest.archivo.deposito.busquedaAmbito.sinUbicacion"/>');
		}else{
			var ifrmSubtree=document.getElementById("frmDepositoSubtreeSeleccion");
			ifrmSubtreeSrc=ifrmSubtree.src;
			//buscar el caracter '='
			var pos=ifrmSubtreeSrc.lastIndexOf('=');
			divId=paramDivId;
			if(document.all){
				 ifrmSubtree.detachEvent("onload", iframeCargado);
				 ifrmSubtree.attachEvent("onload", iframeCargado);
			}else{
				ifrmSubtree.removeEventListener( "load",iframeCargado, false );
				ifrmSubtree.addEventListener ("load", iframeCargado, false);
			}
			pendienteDeCargar=true;
			var newSrc=ifrmSubtreeSrc.substring(0,pos+1)+document.getElementById('idUbicacion').value;
			ifrmSubtree.src=newSrc;
		}
	}

	function setAmbito(code, value, codOrden){
		hideBusqAmbitoDiv();
		if (currentDivId != null){
			var div = xGetElementById(currentDivId);
			if (div){
				var inputs = div.getElementsByTagName("input");
				inputs[0].value=""; inputs[1].value="";
				if(checkAmbitos(value)){
					for (var i = 0; i < inputs.length; i++){
						if (inputs[i].name == "idAmbito")
							inputs[i].value = code;
						else if (inputs[i].name == "nombreAmbito")
							inputs[i].value = unescapeHTML(value);
						else if (inputs[i].name == "codOrdenAmbito")
							inputs[i].value = unescapeHTML(codOrden);
					}
				}
			}
			currentDivId = null;
		}
	}
	var currentDivId = null;
</script>

<%-- Filtro por ámbitos --%>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.cf.ambito"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>" id="tdAmbitos">
		<c:set var="idsAmbito" value="${paramValues.idAmbito}"/>
		<c:set var="nombresAmbito" value="${paramValues.nombreAmbito}"/>
		<c:set var="codsOrdenAmbito" value="${paramValues.codOrdenAmbito}"/>
		<c:choose>
		<c:when test="${empty idsAmbito}">

			<div id="divAmbito<c:out value="${uniqueId}"/>">
				<input type="hidden" name="idAmbito" />
				<input type="hidden" name="codOrdenAmbito" />
				<input type="text" name="nombreAmbito"
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
			<c:forEach var="idAmbito" items="${idsAmbito}" varStatus="status">
				<c:set var="uniqueId" value="${appConstants.uniqueId}"/>
				<div id="divAmbito<c:out value="${uniqueId}"/>">
					<input type="hidden" name="idAmbito" value="<c:out value="${idAmbito}"/>"/>
					<input type="hidden" name="codOrdenAmbito" value="<c:out value="${codsOrdenAmbito[status.index]}"/>"/>
					<input type="text" name="nombreAmbito"
						readonly="readonly" class="inputRO90"
						value="<c:out value="${paramValues.nombreAmbito[status.index]}"/>"/>
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

