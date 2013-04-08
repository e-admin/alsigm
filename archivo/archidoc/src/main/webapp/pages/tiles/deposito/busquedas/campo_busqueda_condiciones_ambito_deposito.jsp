<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div id="bgBusqAmbitoDeposito">&nbsp;</div>
<div id="busqAmbitoDeposito" style="display:none">

	<div class="block_title">
		<div class="block_title2">
			<div class="block_title_left">
				&nbsp;&nbsp;<bean:message key="archigest.archivo.cf.seleccionAmbito"/>
			</div>
			<div class="block_title_right">
				<a class="etiquetaAzul12Bold" href="javascript:hideBusqAmbitoDepositoDiv()">
					<html:img page="/pages/images/close.gif"
						styleClass="imgTextMiddle"
						titleKey="archigest.archivo.cerrar"
						altKey="archigest.archivo.cerrar"/>
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>&nbsp;&nbsp;
			</div>
		</div>
	</div>

	<c:set var="uniqueIdAmbitoDeposito" value="${appConstants.uniqueId}"/>

	<div class="block_content">
		<c:url var="subtreeURLAmbitoDeposito" value="/action/ambitoDepositoSeleccion">
			<c:param name="actionToPerform" value="homeSubtree"/>
			<c:param name="idUbicacion" value=""/>
		</c:url>
		<iframe name="frmDepositoSubtreeSeleccion" id="frmDepositoSubtreeSeleccion"
			src="<c:out value="${subtreeURLAmbitoDeposito}"/>"
			width="100%" height="100%" scrolling="auto" frameborder="0">
		</iframe>
	</div>
</div>

<script language="JavaScript1.2" type="text/JavaScript">

	var divIdAmbitoDeposito="";
	var currentDivIdAmbitoDeposito = null;

	function addAmbitoDeposito()
	{
		var td = xGetElementById("tdAmbitosDeposito");
		var divAddAmbitoDeposito = xGetElementById("divAddAmbitoDeposito");
		if (td && divAddAmbitoDeposito)
		{
			var uniqueIdAmbitoDeposito = getUniqueId();
			var divAmbitoDeposito = creaElemento("<div id='divAmbitoDeposito" + uniqueIdAmbitoDeposito + "'>");
			divAmbitoDeposito.innerHTML = "<input type='hidden' name='idAmbitoDeposito' /><input type='hidden' name='codOrdenAmbitoDeposito' />"
				+ "<input type='text' name='nombreAmbitoDeposito' readonly='readonly' class='inputRO90'/>\n"
				+ "<a href=\"javascript:searchAmbitoDeposito('divAmbitoDeposito" + uniqueIdAmbitoDeposito + "')\">"
				+ "<img src='../images/expand.gif' class='imgTextMiddle' /></a>\n"
				+ "<a href=\"javascript:removeAmbitoDeposito('divAmbitoDeposito" + uniqueIdAmbitoDeposito + "');\">"
				+ "<img src='../images/Ok_No.gif' class='imgTextMiddle'/></a>";
			td.insertBefore(divAmbitoDeposito, divAddAmbitoDeposito);
		}
	}

	function removeAmbitoDeposito(divIdAmbitoDeposito){
		var td = document.getElementById("tdAmbitosDeposito");
		var divs = td.getElementsByTagName("div");
		if (divs.length == 2){
			var inputs = divs[0].getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				if (inputs[i].name == "idAmbitoDeposito")
					inputs[i].value = "";
				else if (inputs[i].name == "nombreAmbitoDeposito")
					inputs[i].value = "";
			}
		}else{
			var div = xGetElementById(divIdAmbitoDeposito);
			if (div)
				td.removeChild(div);
		}
	}

	function removeAmbitosDeposito(){
		var td = document.getElementById("tdAmbitosDeposito");
		var divs = td.getElementsByTagName("div");

		for(var i=0;i<divs.length;i++){
			var divAmbitoDeposito=divs[i];
			if (!divAmbitoDeposito) continue;
			if(divAmbitoDeposito.id.indexOf("divAmbitoDeposito")==-1) continue;
			td.removeChild(divAmbitoDeposito);
			i--;
		}
		addAmbitoDeposito();
	}

	function trimAmbitoDeposito(cadena){
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

	function checkAmbitosDeposito(nombreToCheck){
		//comprobar que la ruta añadida no sea un hijo de alguna ya existente
		var td = document.getElementById("tdAmbitosDeposito");
		var divs = td.getElementsByTagName("div");

		for(var i=0;i<divs.length;i++){
			var divAmbitoDeposito=divs[i];
			if (!divAmbitoDeposito) continue;
			if(divAmbitoDeposito.id.indexOf("divAmbitoDeposito")==-1) continue;
			//alert(divAmbitoDeposito.id+"="+currentDivIdAmbitoDeposito);
			if(divAmbitoDeposito.id==currentDivIdAmbitoDeposito) continue;
			var inputs = divs[i].getElementsByTagName("input");
			for (var j = 0; j < inputs.length; j++){
				if (inputs[j].name == "nombreAmbitoDeposito"){
					var pos=nombreToCheck.indexOf(inputs[j].value);
					if(trimAmbitoDeposito(inputs[j].value)!="" && (pos!=-1
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

	function showBusqAmbitoDepositoDiv() {

		var busqAmbitoDeposito = xGetElementById('busqAmbitoDeposito');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();

		var minWidth  = Math.max(500,clientWidth/2);
		var minHeight = Math.max(400,clientHeight/2);

		xHide(busqAmbitoDeposito);
		xDisplay(busqAmbitoDeposito, 'block');

		xHeight('bgBusqAmbitoDeposito',clientHeight);
		xWidth('bgBusqAmbitoDeposito',clientWidth-32);

		xHeight('busqAmbitoDeposito', minHeight);
		xWidth('busqAmbitoDeposito', minWidth);

		var top = Math.min(100,(clientHeight - xHeight(busqAmbitoDeposito))/2);
		var left = (clientWidth - xWidth(busqAmbitoDeposito))/2;

		xMoveTo(busqAmbitoDeposito, left, top);
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "hidden";


		xShow("bgBusqAmbitoDeposito");
		xShow(busqAmbitoDeposito);
	}
	function hideBusqAmbitoDepositoDiv()
	{
		var busqAmbitoDeposito = xGetElementById('busqAmbitoDeposito');
		var selectElements =document.getElementsByTagName("SELECT");
		for (i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "visible";
		xHide("bgBusqAmbitoDeposito");
		xHide(busqAmbitoDeposito);
		xHeight('bgBusqAmbitoDeposito',0);
		xWidth('bgBusqAmbitoDeposito',0);
	}


	function iframeCargadoAmbitoDeposito(evt) {
  		if(pendienteDeCargarAmbitoDeposito){
			pendienteDeCargarAmbitoDeposito=false;
			showBusqAmbitoDepositoDiv();
			if (divIdAmbitoDeposito) currentDivIdAmbitoDeposito = divIdAmbitoDeposito;
		}
   	}

	function searchAmbitoDeposito(paramDivId){
		if(document.getElementById("idUbicacion").value==""){
			alert('<bean:message key="archigest.archivo.deposito.busquedaAmbito.sinUbicacion"/>');
		}else{
			var ifrmSubtreeAmbitoDeposito=document.getElementById("frmDepositoSubtreeSeleccion");
			ifrmSubtreeAmbitoDepositoSrc=ifrmSubtreeAmbitoDeposito.src;
			//buscar el caracter '='
			var pos=ifrmSubtreeAmbitoDepositoSrc.lastIndexOf('=');
			divIdAmbitoDeposito=paramDivId;
			if(document.all){
				 ifrmSubtreeAmbitoDeposito.detachEvent("onload", iframeCargadoAmbitoDeposito);
				 ifrmSubtreeAmbitoDeposito.attachEvent("onload", iframeCargadoAmbitoDeposito);
			}else{
				ifrmSubtreeAmbitoDeposito.removeEventListener( "load",iframeCargadoAmbitoDeposito, false );
				ifrmSubtreeAmbitoDeposito.addEventListener ("load", iframeCargadoAmbitoDeposito, false);
			}
			pendienteDeCargarAmbitoDeposito=true;
			var newSrc=ifrmSubtreeAmbitoDepositoSrc.substring(0,pos+1)+document.getElementById('idUbicacion').value;
			ifrmSubtreeAmbitoDeposito.src=newSrc;
		}
	}

	function setAmbitoDeposito(code, value,codOrden, type){
		hideBusqAmbitoDepositoDiv();
		if (currentDivIdAmbitoDeposito != null){
			var div = xGetElementById(currentDivIdAmbitoDeposito);
			if (div){
				var inputs = div.getElementsByTagName("input");
				inputs[0].value=""; inputs[1].value="";
				if(checkAmbitosDeposito(value)){
					for (var i = 0; i < inputs.length; i++){
						if (inputs[i].name == "idAmbitoDeposito")
							inputs[i].value = code;
						else if (inputs[i].name == "nombreAmbitoDeposito")
							inputs[i].value = unescapeHTML(value);
						else if (inputs[i].name == "codOrdenAmbitoDeposito")
							inputs[i].value = unescapeHTML(codOrden);
						else if (inputs[i].name == "codOrdenAmbitoDeposito")
							inputs[i].value = unescapeHTML(type);
					}
				}
			}
			currentDivIdAmbitoDeposito = null;
		}
	}

</script>

<%-- Filtro por ámbitos --%>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.ambito.deposito"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>" id="tdAmbitosDeposito">
		<c:set var="idsAmbitoDeposito" value="${paramValues.idAmbitoDeposito}"/>
		<c:set var="nombresAmbitoDeposito" value="${paramValues.nombreAmbitoDeposito}"/>
		<c:set var="codsOrdenAmbitoDeposito" value="${paramValues.codOrdenAmbitoDeposito}"/>
		<c:choose>
		<c:when test="${empty idsAmbitoDeposito}">

			<div id="divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>">
				<input type="hidden" name="idAmbitoDeposito" />
				<input type="hidden" name="codOrdenAmbitoDeposito" />
				<input type="text" name="nombreAmbitoDeposito"
					readonly="readonly" class="inputRO90"/>

				<a href="javascript:searchAmbitoDeposito('divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>')"
					><html:img styleId="imgExpand"
						page="/pages/images/expand.gif"
						styleClass="imgTextMiddle" /></a>

				<a href="javascript:removeAmbitoDeposito('divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>');"
				><html:img page="/pages/images/Ok_No.gif"
				        styleClass="imgTextMiddle"/></a>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="idAmbitoDeposito" items="${idsAmbitoDeposito}" varStatus="status">
				<c:set var="uniqueIdAmbitoDeposito" value="${appConstants.uniqueIdAmbitoDeposito}"/>
				<div id="divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>">
					<input type="hidden" name="idAmbitoDeposito" value="<c:out value="${idAmbitoDeposito}"/>"/>
					<input type="hidden" name="codOrdenAmbitoDeposito" value="<c:out value="${codsOrdenAmbitoDeposito[status.index]}"/>"/>
					<input type="text" name="nombreAmbitoDeposito" readonly="readonly" class="inputRO90" value="<c:out value="${paramValues.nombreAmbitoDeposito[status.index]}"/>"/>
					<a href="javascript:searchAmbitoDeposito('divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>')"
						><html:img styleId="imgExpand"
							page="/pages/images/expand.gif"
							styleClass="imgTextMiddle" /></a>

					<a href="javascript:removeAmbitoDeposito('divAmbitoDeposito<c:out value="${uniqueIdAmbitoDeposito}"/>');"
					><html:img page="/pages/images/Ok_No.gif"
					        styleClass="imgTextMiddle"/></a>
				</div>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		<div id="divAddAmbitoDeposito">
			<a class="btLiso" href="javascript:addAmbitoDeposito()">
				<bean:message key="archigest.archivo.anadir"/>
			</a>
		</div>
	</td>
</tr>

