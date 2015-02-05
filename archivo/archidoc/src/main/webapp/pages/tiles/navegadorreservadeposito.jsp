<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="deposito.actions.navegador.NavegadorReservaDepositoAction" %>


<bean:struts id="mapping" mapping="/navegadorReservaDepositoAction" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="hayMasHuecos" value="true"/>

<c:set var="IS_LAST_LEVEL" value="${sessionScope[appConstants.transferencias.IS_LAST_LEVEL]}"/>


<c:set var="numHuecosLibres" value="${form.numHuecosLibres}"/>

<c:set var="ES_ULTIMO_NIVEL_PERMITIDO" value="false"/>

<c:if test="${IS_LAST_LEVEL != null && IS_LAST_LEVEL == true}">
	<c:set var="ES_ULTIMO_NIVEL_PERMITIDO" value="true"/>
</c:if>

<head>
<SCRIPT>
function submitUp(){
	document.forms[0].method.value=<%="\""+NavegadorReservaDepositoAction.GET_PARENT_SIBLINGS+"\""%>;
	workingDiv();
	document.forms[0].submit();
}
function submitDown(seleccionado){
	document.forms[0].method.value=<%="\""+NavegadorReservaDepositoAction.GET_CHILDS+"\""%>;
	document.getElementById("seleccionado").value = seleccionado;
	workingDiv();
	document.forms[0].submit();
}


function getValorSeleccionado(){

	var ELEMENTO_DEPOSITO = '<c:out value="${appConstants.deposito.ID_TIPO_ELEMENTO_DEPOSITO}"/>';

	var huecosLibres = document.getElementById("numHuecosLibres").value;
	var huecosNecesarios = document.getElementById("numHuecosNecesarios").value;


	var seleccion = document.getElementById("seleccionadoinicial").value;


	if(seleccion != ""){
		var partesSeleccion = seleccion.split(":");

		if(partesSeleccion[1] != ELEMENTO_DEPOSITO && huecosNecesarios > huecosLibres){
			alert('<bean:message key="archigest.archivo.deposito.noHuecos.desde.posicion"/>');
			return "";
		}
		else{
			return seleccion;
		}
	}


	return seleccion;
}


/**
 * Devuelve un array con los datos de la ubicación seleccionada para la reserva.
 * Posicion 0: idDepositoReserva
 * Posicion 1: idTipoDepositoReserva
 * Posicion 2: idElementoSeleccionadoReserva
 * Posicion 3: idTipoElementoSeleccionadoReserva
 */
function getValoresReserva(){
	var retorno = new Array();

	retorno.push(document.getElementById("iddepositoseleccionado").value);
	retorno.push(document.getElementById("idtipodepositoseleccionado").value);
	retorno.push(document.getElementById("idseleccionado").value);
	retorno.push(document.getElementById("idtiposeleccionado").value);

	return retorno;
}


function setValorSeleccionado(valor, comprobarHuecos){
	document.getElementById("seleccionadoinicial").value = valor;
	document.getElementById("seleccionado").value = valor;

	if(comprobarHuecos){
		document.forms[0].method.value=<%="\""+NavegadorReservaDepositoAction.GET_NUM_HUECOS_DISPONIBLES+"\""%>;
		workingDiv();
		document.forms[0].submit();
	}
}

function workingDiv(){
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
}

</SCRIPT>

<style>
body{
	background-color: #F2F2F2;
	text-align:center;
}
.etiquetaAzul11Normal{
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #003399;
	white-space: nowrap;
}

.etiquetaAzul11Bold{
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	color: #003399;
	white-space: nowrap;
}

</style>

<link type="text/css"
	  rel="stylesheet"
	  href="../pages/css/displaytag.css" />

</head>
<body topmargin="0" bottommargin="0">
<html:form action="/navegadorReservaDepositoAction" styleId="formulario" >
<html:hidden property="method" styleId="method"/>
<html:hidden property="idtipopadre" styleId="idtipopadre"/>
<html:hidden property="idpadre" styleId="idpadre"/>
<html:hidden property="root" styleId="root"/>
<html:hidden property="filterByIdformato" styleId="filterByIdformato"/>
<html:hidden property="idTipoLastLevel" styleId="idTipoLastLevel"/>
<html:hidden property="hideHuecosLibres" styleId="hideHuecosLibres"/>
<html:hidden property="depositoseleccionado" styleId="depositoseleccionado"/>
<html:hidden property="iddepositoseleccionado" styleId="iddepositoseleccionado"/>
<html:hidden property="idtipodepositoseleccionado" styleId="idtipodepositoseleccionado"/>
<html:hidden property="seleccionado" styleId="seleccionado"/>
<html:hidden property="idseleccionado" styleId="idseleccionado"/>
<html:hidden property="idtiposeleccionado" styleId="idtiposeleccionado"/>
<html:hidden property="seleccionadoinicial" styleId="seleccionadoinicial"/>
<html:hidden property="numHuecosLibres" styleId="numHuecosLibres"/>
<html:hidden property="numHuecosNecesarios" styleId="numHuecosNecesarios"/>
<html:hidden property="existeReserva" styleId="existeReserva" />
<html:hidden property="recorrerDepositos" styleId="recorrerDepositos"/>


<TABLE cellpadding="2" width="100%" style="text-align:center;" >
<TR>
	<TD style="text-align:left;" class="etiquetaAzul11Bold" colspan="2">
		<bean:message key="archigest.archivo.deposito.situacionActual" />:
		<span class="etiquetaAzul11Normal">
			<logic:present name="<%=NavegadorReservaDepositoAction.PATH_KEY%>">
				<bean:write name="<%=NavegadorReservaDepositoAction.PATH_KEY%>" />
				<logic:present name="<%=NavegadorReservaDepositoAction.PARENT_NAME%>" >
					 &nbsp;<a href="javascript:submitUp()"><html:img page="/pages/images/folder-up.gif" border="0" altKey="archigest.archivo.subir" titleKey="archigest.archivo.subir" styleClass="imgTextBottom" />
				</logic:present>
			</logic:present>
		</span>
	</TD>
</TR>
<TR>
	<TD width="95%">
				<c:set var="listaDescendientes" value="${sessionScope[appConstants.deposito.LISTA_DESCENDIENTES_KEY]}" />
				<display:table name="pageScope.listaDescendientes"
					id="descendiente"
					style="width:99%;margin-left:auto;margin-right:auto"

					>

				<display:column style="width:20px">
						<c:set var="txtComprobarHuecos" value ="true"/>
						<c:set var="numHuecosNecesarios" value="-1"/>
						<c:choose>
							<c:when test="${!empty descendiente.asignable and descendiente.asignable=='true' and descendiente.huecosLibres>0 }">
								<input type="radio" id="elementoSeleccionado" name="elementoSeleccionado" value="<bean:write name="descendiente" property="id"/>:<bean:write name="descendiente" property="idTipoElemento"/>" onclick="setValorSeleccionado(this.value,<c:out value="${txtComprobarHuecos}"/>)" <c:if test="${descendiente.id==form.idseleccionadoinicial}">checked</c:if>>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${descendiente.huecosLibres >0  }">
										<input type="radio" id="elementoSeleccionado" name="elementoSeleccionado" value="<bean:write name="descendiente" property="id"/>:<bean:write name="descendiente" property="idTipoElemento"/>" onclick="setValorSeleccionado(this.value,<c:out value="${txtComprobarHuecos}"/>)" <c:if test="${descendiente.id==form.idseleccionadoinicial}">checked</c:if>>
									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/pixel.gif" width="19" height="19" border="0" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
				</display:column>

				<display:column titleKey="archigest.archivo.nombre">
					<c:choose>
						<c:when test="${!ES_ULTIMO_NIVEL_PERMITIDO and descendiente.huecosLibres>0  and (empty descendiente.asignable or descendiente.asignable=='false')}">
							<a class="tdlink" href='javascript:submitDown("<c:out value="${descendiente.id}"/>:<c:out value="${descendiente.idTipoElemento}"/>")' >
								<c:out value="${descendiente.nombre}"/>
							</a>
						</c:when>
						<c:otherwise>
							<c:out value="${descendiente.nombre}"/>
						</c:otherwise>
					</c:choose>
						<c:if test="${descendiente.id==form.idseleccionadoinicial && form.numHuecosNecesarios > form.numHuecosLibres}" >
								&nbsp;<font color="red">
									<html:img page="/pages/images/error.gif" width="14" height="12" border="0"  />&nbsp;<bean:message key="archigest.archivo.deposito.noHuecos.desde.posicion"/></font>
						</c:if>

				</display:column>

				<c:if test="${empty form.hideHuecosLibres or form.hideHuecosLibres=='false'}">
				<display:column style="width:30px" titleKey="archigest.archivo.deposito.libres">
					<c:out value="${descendiente.huecosLibres}"/>
				</display:column>
				</c:if>

				</display:table>

	</TD>
</TR>
</TABLE>
<%-- navegadorreservadeposito.jsp --%>
<script language="javascript">
	if (window.top.hideWorkingDiv) {
		window.top.hideWorkingDiv();
	}
</script>
</html:form>
</body>
<%-- navegadorreservadeposito.jsp --%>