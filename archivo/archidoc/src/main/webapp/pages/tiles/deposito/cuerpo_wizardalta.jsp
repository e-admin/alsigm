<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page import="deposito.global.Constants" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<SCRIPT>
function prevState(form){
form.wizardNextState.value=(0+form.wizardState.value)-1;
form.submit();
}

function doCancelSubmit(){
	document.forms[0].elements['cancel'].value='true';
	document.forms[0].submit();
}


</SCRIPT>


<div id="contenedor_ficha">

<html:form action="/wizardAltaAction" >

<html:hidden property="wizardState" />
<html:hidden property="wizardNextState" />
<html:hidden property="idpadre" />
<html:hidden property="nombrepadre" />
<html:hidden property="uricancel" />
<html:hidden property="idtipopadre" />
<html:hidden property="path" />

<html:hidden property="cancel" value="false"/>

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			&nbsp;<bean:message key="archigest.archivo.crear"/>
			<bean:message key="archigest.archivo.deposito.descendientes"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			<logic:equal name="WizardAltaForm" property="wizardState" value="1">
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			</logic:equal>	
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="cancelURI" value="/action/wizardAltaAction">
						<c:param name="method" value="goBack"/>					
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
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

	<div class="bloque"> <%--primer bloque de datos --%>

		<logic:equal name="WizardAltaForm" property="wizardState" value="1">

			<table class="formulario">
				<tr>
					<td width="220px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.elementoPadre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<bean:write name="WizardAltaForm" property="path" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.tipoElemCrear"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:select property="idTipoelementoseleccionado" size="1">
							<html:optionsCollection name="<%=Constants.LISTA_TIPO_ELEMENTO_KEY%>" label="nombre" value="id"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.numElemCrear"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text property="numACrear" size="5" maxlength="5"/>
					</td>
				</tr>
				</TR>
			</TABLE>

		</logic:equal>
	</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>

