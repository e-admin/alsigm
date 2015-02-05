<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="deposito.forms.HuecoForm" %>
<%@ page import="deposito.forms.GestionEstadoHuecoForm" %>
<%@ page import="deposito.actions.hueco.GestionEstadoHuecosAction" %>
<%@ page import="deposito.vos.HuecoVO"%>
<SCRIPT>
var selected=0;
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
		if (image!="") document.write("&nbsp;&nbsp;<img src='../images/"+image+"' alt='"+alt+"'align='absmiddle'/>");
	}
	
	function doCancelSubmit(){
		document.forms[0].elements['cancel'].value='true';
		document.forms[0].submit();
	}

	function doOKSubmit(){
		if (selected>0){
			document.forms[0].submit();
		}else alert('<bean:message key="archigest.archivo.deposito.esNecesarioSeleccionarAlMenosUnHueco"/>');
	}
	function incCountSelected(nameCheck){
		oCheck = document.getElementById(nameCheck);
		if (oCheck){
			if (oCheck.checked){
				selected++;
			}else{
				selected--;
			}
		}
	}


</SCRIPT>

<div id="contenedor_ficha">

	<html:form action="/gestionEstadoHuecosAction" >
	<html:hidden property="cancel" value="false"/>

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<logic:equal name="GestionEstadoHuecoForm" property="newstate" value="<%=HuecoVO.LIBRE_STATE%>">
				<bean:message key="archigest.archivo.deposito.habilitar" />
				<bean:message key="archigest.archivo.deposito.huecos" />
			</logic:equal>
			<logic:equal name="GestionEstadoHuecoForm" property="newstate" value="<%=HuecoVO.INUTILIZADO_STATE%>">
				<bean:message key="archigest.archivo.deposito.inutilizar" />
				<bean:message key="archigest.archivo.deposito.huecos" />
			</logic:equal>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:doOKSubmit()" >
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

<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="98%" >
				&nbsp;<bean:message key="archigest.archivo.deposito.huecos"/> 
				'<bean:write name="GestionEstadoHuecoForm" property="pathelemento" />'
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
			<html:hidden property="calltype" value='<%=GestionEstadoHuecoForm.SET_STATE%>'/>
			<logic:iterate id="element" name='<%=GestionEstadoHuecosAction.LISTA_HUECOS_KEY%>'  indexId="i" >
				<logic:present name="element">
					<%--SALTO--%>
					<% if(((i.intValue()%8)==0)) out.print("</TR><TR>");%>

					<TD noWrap>
						<logic:equal name="GestionEstadoHuecoForm" property="newstate" value="<%=HuecoVO.LIBRE_STATE%>">
							<logic:equal name="element" property="estado" value="<%=HuecoVO.INUTILIZADO_STATE%>">					
								<%--guardamos solo la seleccion para no recorrer todos los heucos en busca de cual esta seleccionado --%>
								<c:set var="tmp" value="huecoseleccionado(${element.idhueco})" />
								<input type = "checkbox" id='<%="huecoseleccionado("+((HuecoForm)element).getIdhueco()+")"%>' name='<%="huecoseleccionado("+((HuecoForm)element).getIdhueco()+")"%>' value="<%=((HuecoForm)element).getIdhueco()%>" 
								onClick="javascript:incCountSelected('<c:out value="${tmp}"/>');" 
								/>
							</logic:equal>
						</logic:equal>
						<logic:equal name="GestionEstadoHuecoForm" property="newstate" value="<%=HuecoVO.INUTILIZADO_STATE%>">
							<logic:equal name="element" property="estado" value="<%=HuecoVO.LIBRE_STATE%>">					
								<%--guardamos solo la seleccion para no recorrer todos los heucos en busca de cual esta seleccionado --%>
								<c:set var="tmp" value="huecoseleccionado(${element.idhueco})" />
								<input type="checkbox" 
								id='<%="huecoseleccionado("+((HuecoForm)element).getIdhueco()+")"%>'
								name='<%="huecoseleccionado("+((HuecoForm)element).getIdhueco()+")"%>' 
								value="<%=((HuecoForm)element).getIdhueco()%>" 
								onClick="javascript:incCountSelected('<c:out value="${tmp}"/>');" 
								/>
							</logic:equal>
						</logic:equal>
						<bean:message key="archigest.archivo.deposito.inicialHueco" />
						<b><bean:write name="element" property="numorden"/></b>
						<SCRIPT>
							<%--Pintado de numeros de orden en color segun su estado --%>
							<%--paintState('<bean:write name="element" property="estado"/>','<bean:write name="element" property="numorden"/>');--%>
							paintImage('<bean:write name="element" property="estado"/>');
						</SCRIPT>
					</TD>
				</logic:present >
			</logic:iterate> 
		</TR>
		</TABLE>
		<div class="separador5">&nbsp;</div>
</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

<div style="display:none;"></html:form></div>

	

	

