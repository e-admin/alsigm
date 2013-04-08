<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="deposito.actions.navegador.NavegadorDepositoAction" %>

<c:set var="pathKey" value="${sessionScope[appConstants.deposito.PATH_KEY]}"/>
<c:set var="showParent" value="${sessionScope[appConstants.deposito.SHOW_PARENT]}"/>
<c:set var="allowAllFormats" value="${sessionScope[appConstants.deposito.ALLOW_ALL_FORMATS]}"/>
<c:set var="comprobarHuecos" value="${sessionScope[appConstants.deposito.ALLOW_ALL_FORMATS]}"/>

<html>
<head>

<SCRIPT>
function submitUp(){
	document.forms[0].method.value=<%="\""+NavegadorDepositoAction.GET_PARENT_SIBLINGS+"\""%>;
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	document.getElementById("asignableSeleccionado").value = "";
	document.forms[0].submit();
}

function goUp(){
	document.forms[0].method.value=<%="\""+NavegadorDepositoAction.GO_TOP+"\""%>;
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	document.getElementById("asignableSeleccionado").value = "";
	document.forms[0].submit();
}


function submitDown(seleccionado){
	document.forms[0].method.value=<%="\""+NavegadorDepositoAction.GET_CHILDS+"\""%>;
	//comprobar que se puede hacer submit down
	document.getElementById("seleccionado").value = seleccionado;
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	document.getElementById("asignableSeleccionado").value = "";
	document.forms[0].submit();

	/*selIndex = document.getElementById("idSelect").selectedIndex;
	marcaId = document.getElementById("idSelect").options(selIndex).id;
	if (marcaId=="+"){
	}*/
}

function getValorSeleccionado(){
	var seleccion = document.getElementById("asignableSeleccionado").value;

	var huecosLibres = document.getElementById("numHuecosLibres").value;
	var huecosNecesarios = document.getElementById("numHuecosNecesarios").value;


	if(seleccion == "") return seleccion;

	if(seleccion != ""){
		var partesSeleccion = seleccion.split(":");
		if(huecosNecesarios > huecosLibres){
			alert('<bean:message key="archigest.archivo.deposito.noHuecos.desde.posicion"/>');
			return;
		}
		else{
			return seleccion;
		}
	}

	return seleccion;
}

function setValorSeleccionado(valor){
	document.getElementById("asignableSeleccionado").value = valor;
	var huecosNecesarios = document.getElementById("numHuecosNecesarios").value;

	if(huecosNecesarios > 0){
		document.forms[0].method.value="getNumHuecosLibresDesdePosicion";
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

function refrescarFormatos() {

	document.forms[0].method.value="refrescarFormatos";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	document.forms[0].submit();
}

</SCRIPT>

<style>
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
.etiquetaBlanca12Bold, .etiquetaAzul12Bold, .etiquetaGris12Bold,
.etiquetaGris12Normal, .etiquetaAzul12Normal, .etiquetaNegra12Normal{
	FONT-SIZE: 12px;
	FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
	padding:0;
}

</style>

<link type="text/css"
	  rel="stylesheet"
	  href="../pages/css/displaytag.css" />

</head>
<body topmargin="0" bottommargin="0">

<html:form action="/navegadorDepositoTableAction" >
<html:hidden property="method" styleId="method"/>
<html:hidden property="idtipopadre" styleId="idtipopadre"/>
<html:hidden property="idpadre" styleId="idpadre"/>
<html:hidden property="root" styleId="root"/>
<html:hidden property="filterByIdformato" styleId="filterByIdformato"/>
<html:hidden property="idTipoLastLevel" styleId="idTipoLastLevel"/>
<html:hidden property="hideHuecosLibres" styleId="hideHuecosLibres"/>
<html:hidden property="seleccionado" styleId="seleccionado"/>
<html:hidden property="seleccionadoinicial" styleId="seleccionadoinicial"/>
<html:hidden property="asignableSeleccionado" styleId="asignableSeleccionado"/>
<html:hidden property="checkHasHuecos" styleId="comprobarNumHuecos"/>
<html:hidden property="numHuecosLibres" styleId="numHuecosLibres"/>
<html:hidden property="numHuecosNecesarios" styleId="numHuecosNecesarios"/>
<html:hidden property="allowAllFormats" styleId="allowAllFormats"/>

<TABLE cellpadding="2" width="100%" style="text-align:center;" >
<TR>
	<TD style="text-align:left;" class="etiquetaAzul11Bold">
		<bean:message key="archigest.archivo.deposito.situacionActual" />:
		<span class="etiquetaAzul11Normal">
			<c:if test="${showParent == true}">
				&nbsp;<a href="javascript:goUp()"><html:img page="/pages/images/subir.gif" border="0" altKey="archigest.archivo.subir.elemento.raiz" titleKey="archigest.archivo.subir.elemento.raiz" styleClass="imgTextBottom" /></a>
			</c:if>
			<c:out value="${pathKey}"/>
			<c:if test="${showParent == true}">
				&nbsp;<a href="javascript:submitUp()"><html:img page="/pages/images/folder-up.gif" border="0" altKey="archigest.archivo.subir" titleKey="archigest.archivo.subir" styleClass="imgTextBottom" /></a>
			</c:if>
		</span>
	</TD>
	<TD>
		<div class="etiquetaAzul12Normal">
			<c:if test="${allowAllFormats == true}">
			<html:checkbox property="todosFormatos" styleClass="checkbox" styleId="mostrarTodosFormatos" onclick="javascript:refrescarFormatos();"/>
				<bean:message key="archigest.archivo.deposito.mostrar.elementos.todos.formatos"/>
			</c:if>
		</div>
	</TD>
</TR>

<TR>
	<TD width="95%" class="etiquetaAzul11Bold" colspan="2">
				<c:set var="listaDescendientes" value="${requestScope[appConstants.deposito.LISTA_DESCENDIENTES_KEY]}" />

				<display:table name="pageScope.listaDescendientes"
					id="descendiente"
					style="width:99%;margin-left:auto;margin-right:auto"
					>
						<display:column style="width:20px">
						<c:choose>
							<c:when test="${descendiente.mostrarRadio}">
								<input type="radio" name="sel" onclick="setValorSeleccionado(this.value)" value="<bean:write name="descendiente" property="id"/>:<bean:write name="descendiente" property="idTipoElemento"/>" <c:if test="${descendiente.id==NavegadorDepositoForm.idAsignableSeleccionado}">checked</c:if>>
							</c:when>
						</c:choose>
						</display:column>
						<display:column titleKey="archigest.archivo.nombre">
							<c:choose>
								<c:when test="${descendiente.mostrarLink}">
									<a class="tdlink" href='javascript:submitDown("<c:out value="${descendiente.id}"/>:<c:out value="${descendiente.idTipoElemento}"/>")' >
										<c:out value="${descendiente.nombre}"/>
									</a>
								</c:when>
								<c:otherwise>
									<c:out value="${descendiente.nombre}"/>
								</c:otherwise>
							</c:choose>
								<c:if test="${descendiente.id==requestScope[appConstants.deposito.ID_ELEMENTO_SIN_HUECOS_DISPONIBLES]}" >
									&nbsp;<font color="red">
										<html:img page="/pages/images/error.gif" width="14" height="12" border="0"  />&nbsp;<bean:message key="archigest.archivo.deposito.noHuecos.desde.posicion"/></font>
									</c:if>
						</display:column>
				</display:table>
	</TD>
</TR>

</TABLE>
<script language="javascript">
	if (window.top.hideWorkingDiv) {
		window.top.hideWorkingDiv();
	}
</script>
</html:form>
</body>

</html>

<%-- navegadordepositotable.jsp --%>