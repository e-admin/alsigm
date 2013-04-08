<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ page import="deposito.global.Constants" %>
<%@ page import="deposito.forms.HuecoForm" %>
<%@ page import="deposito.vos.HuecoVO"%>
<%@ page import="deposito.actions.reubicacion.ReubicacionWizardAction" %>

<SCRIPT>
	function getStateColor(state) {
		var ret="color:green";
		if (state=="L") ret="color:white";
		if (state=="O") ret="color:blue";
		if (state=="R") ret="color:red";
		if (state=="I") ret="color:black";
		return ret;
	}

	function prevState(form){
	form.wizardNextState.value=(0+form.wizardState.value)-1;
	form.submit();
	}
	
	function paintState(state,valueToPaint){
	var color=getStateColor(state);
	document.write("<b style='"+color+"'>"+valueToPaint+"</b>");
	}
	
	function getImage(state) {
		var ret="";
		if (state=="L") ret="huecoBlanco.gif";
		if (state=="O") ret="huecoAzul.gif";
		if (state=="R") ret="huecoRojo.gif";
		if (state=="I") ret="huecoNegro.gif";
		return ret;
	}
	
	function getEstadoAlt(state) {
		var ret="";
		if (state=="L") ret="Disponible";
		if (state=="O") ret="Ocupado";
		if (state=="R") ret="Reservado";
		if (state=="I") ret="Inutilizable";
		return ret;
	}
	
	function paintImage(state){
		var image=getImage(state);
		var alt=getEstadoAlt(state);
		if (image!="") document.write("&nbsp;&nbsp;<img src='../images/"+image+"' alt='"+alt+"' title='"+alt+"' align='absmiddle'/>");
	}

	function checkAll(){
		for(i=0;i<arguments.length;i++){
			document.getElementById(arguments[i]).checked=true;
		}
	}

	function uncheckAll(){
		for(i=0;i<arguments.length;i++){
			document.getElementById(arguments[i]).checked=false;
		}
	}

	function doCancelSubmit(){
		document.forms[0].elements['cancel'].value='true';
		document.forms[0].submit();
	}

</SCRIPT>

<bean:size id="numeroHuecos" name="ReubicacionWizardForm" property="huecosorigen"/>

<div id="contenedor_ficha">

	<html:form action="/reubicacionWizardAction" >

	<%--NECESARIO POR NO USAR SESSION--%>
	<html:hidden property="wizardState" />
	<html:hidden property="wizardNextState" />
	<html:hidden property="asignableorigen"/>
	<html:hidden property="tipoasignableorigen" />
	<html:hidden property="pathorigen" />
	<html:hidden property="tipoasignableorigen" />
	<html:hidden property="idformatoasignableorigen"/>

	<html:hidden property="cancel" value="false"/>

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
	    	<bean:message key="archigest.archivo.deposito.reubicar" />:
	    	<bean:message key="archigest.archivo.paso" />
			<bean:write name="ReubicacionWizardForm" property="wizardState" />
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			<logic:equal name="ReubicacionWizardForm" property="wizardState" value="1">
				<TD noWrap>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:doCancelSubmit(document.forms[0])">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</logic:equal>

			<logic:equal name="ReubicacionWizardForm" property="wizardState" value="2">
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:prevState(document.forms[0])">
						<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.atras"/>
			   		</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<script>
						function recogerAsignableSeleccionado(fieldToSet,frameSeleccion) {
							var campoSelect = frameSeleccion.document.forms[0].seleccionado;
							if (campoSelect.selectedIndex > -1) {
								fieldToSet.value = campoSelect.options[campoSelect.selectedIndex].value;
							}
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:recogerAsignableSeleccionado(document.forms[0].asignabledestino,document.frames['navegador']);document.forms[0].submit();" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:doCancelSubmit(document.forms[0])">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</logic:equal>

			<logic:equal name="ReubicacionWizardForm" property="wizardState" value="3">
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:prevState(document.forms[0])">
						<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.atras"/>
			   		</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit();" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:doCancelSubmit(document.forms[0])">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</logic:equal>

			<logic:equal name="ReubicacionWizardForm" property="wizardState" value="4">
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:prevState(document.forms[0])">
						<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.atras"/>
			   		</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit();" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:doCancelSubmit(document.forms[0])">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</logic:equal>

			<logic:equal name="ReubicacionWizardForm" property="wizardState" value="5">
				<TD noWrap>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit();" >
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>
				</TD>
			</logic:equal>

			</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

	<div class="separador1">&nbsp;</div>

			<logic:notEqual name="ReubicacionWizardForm" property="wizardState" value="1">
				<%--para guardar su estado (pq no se usa session)--%>
				<logic:iterate id="element" name="ReubicacionWizardForm" property='huecosorigen'  >
					<logic:present name="element">
							<%--guardamos solo la seleccion para no recorrer todos los heucos en busca de cual esta seleccionado --%>
							<html:hidden property='<%="huecoorigen("+element+")"%>'  />
					</logic:present >
				</logic:iterate>
			</logic:notEqual>

			<logic:notEqual name="ReubicacionWizardForm" property="wizardState" value="2">
				<html:hidden property='asignabledestino' />
			</logic:notEqual>

			<%--para guardar su estado (pq no se usa session) --%>
			<logic:notEqual name="ReubicacionWizardForm" property="wizardState" value="3">
				<logic:iterate id="element" name="ReubicacionWizardForm" property='huecosdestino'  >
					<logic:present name="element">
							<%--guardamos solo la seleccion para no recorrer todos los heucos en busca de cual esta seleccionado --%>
							<html:hidden property='<%="huecodestino("+element+")"%>'  />
					</logic:present >
				</logic:iterate>
			</logic:notEqual>

			<%--para guardar su estado (pq no se usa session)--%>
			<logic:iterate id="element" name="ReubicacionWizardForm" property='posiblesuinsorigen'  >
				<logic:present name="element">
						<%--guardamos solo la seleccion para no recorrer todos los heucos en busca de cual esta seleccionado --%>
						<html:hidden property='<%="posibleuinsorigen("+element+")"%>'  />
				</logic:present >
			</logic:iterate>

		<logic:equal name="ReubicacionWizardForm" property="wizardState" value="1">

		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.infoReubicar"/> 
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="bloque">
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.deposito.origReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="ReubicacionWizardForm" property="pathorigen"/>
					</td>
				</tr>
			</table>
		</div>

		<div class="separador5">&nbsp;</div>
		
		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.huecosReubicar"/>
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD noWrap>
			   		<a class="etiquetaAzul12Normal" 
			   			href="javascript:checkAll(
							<c:set var="primerHueco" value="true"/>
							<logic:iterate id="element" name='<%=Constants.LISTA_HUECOS_ORIGEN_KEY%>'  indexId="i" >
								<logic:present name="element">
									<logic:equal name="element" property="estado" value="<%=HuecoVO.OCUPADO_STATE%>">					
										<c:if test="${!primerHueco}">
											<c:out value=","/>
										</c:if>
										'<%="huecoorigen("+((HuecoForm)element).getIdhueco()+")"%>'
										<c:set var="primerHueco" value="false"/>
									</logic:equal>
								</logic:present>
							</logic:iterate>
							)">
						<html:img page="/pages/images/checked.gif" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.selTodos" />

			   		</a>
				</TD>
				<TD width="20">&nbsp;</TD>
				<TD noWrap>
			   		<a class="etiquetaAzul12Normal" 
			   			href="javascript:uncheckAll(
							<c:set var="primerHueco" value="true"/>
							<logic:iterate id="element" name='<%=Constants.LISTA_HUECOS_ORIGEN_KEY%>'  indexId="i" >
								<logic:present name="element">
									<logic:equal name="element" property="estado" value="<%=HuecoVO.OCUPADO_STATE%>">					
										<c:if test="${!primerHueco}">
											<c:out value=","/>
										</c:if>
										'<%="huecoorigen("+((HuecoForm)element).getIdhueco()+")"%>'
										<c:set var="primerHueco" value="false"/>
									</logic:equal>
								</logic:present>
							</logic:iterate>
							)">
						<html:img page="/pages/images/check.gif" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
			   		</a>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="separador5">&nbsp;</div>

		<div class="bloque">
		<div class="separador5">&nbsp;</div>
		<TABLE width="98%" class="tblHuecos" cellpadding="2px">
			<TR>
			<logic:iterate id="element" name='<%=Constants.LISTA_HUECOS_ORIGEN_KEY%>'  indexId="i" >
				<logic:present name="element">
						<%--SALTO--%>
						<% if(((i.intValue()%8)==0)) out.print("</TR><TR>");%>
						<TD>
							<logic:equal name="element" property="estado" value="<%=HuecoVO.OCUPADO_STATE%>">					
							<%--guardamos solo la seleccion para no recorrer todos los huecos en busca de cual esta seleccionado --%>
								<html:checkbox property='<%="huecoorigen("+((HuecoForm)element).getIdhueco()+")"%>' value="<%=((HuecoForm)element).getIdhueco()%>" />
							</logic:equal>
							<bean:message key="archigest.archivo.deposito.inicialHueco" />
							<b><bean:write name="element" property="numorden"/></b>
						<SCRIPT>
							<%--Pintado de numeros de orden en color segun su estado --%>
							paintImage('<bean:write name="element" property="estado"/>');
						</SCRIPT>
						</TD>
				</logic:present >
			</logic:iterate> 
            </TR>
		</TABLE>
		<div class="separador5">&nbsp;</div>

		</div>

		</logic:equal>

     	<logic:equal name="ReubicacionWizardForm" property="wizardState" value="2">

		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.infoReubicar"/> 
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="bloque">
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.deposito.origReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="ReubicacionWizardForm" property="pathorigen"/>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.numero"/> 
						<bean:message key="archigest.archivo.deposito.huecosReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="numeroHuecos" />
					</td>
				</tr>
			</table>
		</div>

		<div class="separador5">&nbsp;</div>

		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.destinoReubicar"/> 
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="bloque">
		<TABLE class="w100">
			<TR>
				<td>
					<input type="hidden" name="asignabledestino" value="">
					<bean:define id="asignableOrigen" name="ReubicacionWizardForm" property="asignableorigen" />
					<bean:define id="tipoAsignableOrigen" name="ReubicacionWizardForm" property="tipoasignableorigen" />
					<bean:define id="formatoAsignableOrigen" name="ReubicacionWizardForm" property="idformatoasignableorigen" />
					<script>
						function resizeNavegador() {
							var frameNavegador = document.getElementById("navegador");
							if (frameNavegador.contentDocument && frameNavegador.contentDocument.body.offsetHeight) //ns6 syntax
								frameNavegador.height = frameNavegador.contentDocument.body.offsetHeight; 
							else if (frameNavegador.Document && frameNavegador.Document.body.scrollHeight) //ie5+ syntax
								frameNavegador.height = frameNavegador.Document.body.scrollHeight;
						}
					</script>
				<c:url var="urlNavegador" value="/action/navegadorDepositoAction">
					<c:param name="method" value="initial"/>
					<c:param name="seleccionado" value="${asignableOrigen}:${tipoAsignableOrigen}"/> 
					<c:param name="filterByIdformato" value="${formatoAsignableOrigen}"/> 
				</c:url>
				
				<iframe id="navegador" src='<c:out value="${urlNavegador}" escapeXml="false"/>' frameborder="0" height="100%" width="100%">
				</iframe>

					<script>
						
						var frameNavegador = document.getElementById("navegador");
						if (frameNavegador.addEventListener)
							frameNavegador.addEventListener("load", resizeNavegador, false);
						else if (frameNavegador.attachEvent) {
							frameNavegador.detachEvent("onload", resizeNavegador); // Bug fix line
							frameNavegador.attachEvent("onload", resizeNavegador);
						}
					</script>
				</td>
            </TR>
		</TABLE>
		</div>
		</logic:equal>

		<logic:equal name="ReubicacionWizardForm" property="wizardState" value="3">

		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.infoReubicar"/> 
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="bloque">
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.deposito.origReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="ReubicacionWizardForm" property="pathorigen"/>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.deposito.destinoReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="ReubicacionWizardForm" property="pathdestino"/>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						&nbsp;<bean:message key="archigest.archivo.numero"/> 
						<bean:message key="archigest.archivo.deposito.huecosReubicar"/>:
					</td>
					<td class="tdDatos">
						<bean:write name="numeroHuecos" />
					</td>
				</tr>
			</table>
		</div>

		<div class="separador5">&nbsp;</div>

		<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.huecosDestino"/> 
			</TD>
		    <TD width="2%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
			</TABLE>  
		   </TR>
		</TABLE>
		</div>

		<div class="bloque">

			<div class="separador5">&nbsp;</div>
			<TABLE class="tblHuecos">
				<TR>
				<logic:iterate id="element" name='<%=Constants.LISTA_HUECOS_DESTINO_KEY%>'  indexId="i" >
					<logic:present name="element">
							<%--SALTO--%>
							<% if(((i.intValue()%8)==0)) out.print("</TR><TR>");%>
							<TD>
								<logic:equal name="element" property="estado" value="<%=HuecoVO.LIBRE_STATE%>">					
								<%--guardamos solo la seleccion para no recorrer todos los huecos en busca de cual esta seleccionado --%>
									<html:checkbox styleId='<%="huecodestino("+((HuecoForm)element).getIdhueco()+")"%>' property='<%="huecodestino("+((HuecoForm)element).getIdhueco()+")"%>' value="<%=((HuecoForm)element).getIdhueco()%>" />
								</logic:equal>
								<bean:message key="archigest.archivo.deposito.inicialHueco" />
								<b><bean:write name="element" property="numorden"/></b>
							<SCRIPT>
								<%--Pintado de numeros de orden en color segun su estado --%>
								paintImage('<bean:write name="element" property="estado"/>');
							</SCRIPT>
							</TD>
					</logic:present>
				</logic:iterate> 
	            </TR>
			</TABLE>
			<div class="separador5">&nbsp;</div>

		</div>
		</logic:equal>

		<logic:equal name="ReubicacionWizardForm" property="wizardState" value="4">

		<div class="bloque" >

		<div class="titulo_left_bloque" >
			<bean:message key="archigest.archivo.deposito.listaReubicaciones" />
		</div>


		<display:table name='<%="sessionScope."+ReubicacionWizardAction.LISTA_CONFIRMACION %>'
			id="listaRegistros" 
			export="false"
			style="width:98%;margin-left:auto;margin-right:auto" >
				<display:column title="&nbsp;"><%=listaRegistros_rowNum%></display:column>
				<display:column titleKey="archigest.archivo.deposito.rutaOrigen" property="pathorigen"/>
				<display:column titleKey="archigest.archivo.deposito.unidInstalacion" property="signaturauinsorigen"/>
				<display:column titleKey="archigest.archivo.deposito.rutaDestino" property="pathdestino"/>
		</display:table>

		<div class="separador5">&nbsp;</div>

		</div>

		</logic:equal>

		<logic:equal name="ReubicacionWizardForm" property="wizardState" value="5">


		<div class="bloque">

		<div class="titulo_left_bloque">
			<bean:message key="archigest.archivo.deposito.La" />
			<bean:message key="archigest.archivo.deposito.reubicacion" />
			<bean:message key="archigest.archivo.deposito.finOk" />
		</div>

		<div class="separador5">&nbsp;</div>

		<display:table name='<%="sessionScope."+ReubicacionWizardAction.LISTA_CONFIRMACION %>' 
			id="listaRegistros" 
			export="false"
			style="width:98%;margin-left:auto;margin-right:auto" >
				<display:column title="&nbsp;"><%=listaRegistros_rowNum%></display:column>
				<display:column titleKey="archigest.archivo.deposito.rutaOrigen" property="pathorigen"/>
				<display:column titleKey="archigest.archivo.deposito.unidInstalacion" property="signaturauinsorigen"/>
				<display:column titleKey="archigest.archivo.deposito.rutaDestino" property="pathdestino"/>
		</display:table>

		<div class="separador5">&nbsp;</div>

		</div>
		</logic:equal>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form> 
</div> <%--contenedor_ficha --%>
