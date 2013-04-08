<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<bean:struts id="actionMapping" mapping="/gestionTipoNoAsignableAction" />

<script>
	function generarCartelas()
	{
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if(form.idFormato.value == ""){
			alert('<bean:message key="archigest.archivo.deposito.noSelFormato"/>');
		}else{
			if(form.selHuecos[0].checked){
				form.textSignaturas.value = "";
			}
			if(form.selHuecos[1].checked){
				if (form.textSignaturas.value == ""){
					alert('<bean:message key="archigest.archivo.deposito.seleccionarSignaturas.msgSelAlgunasSignaturas"/>');
					return;
				}
			}
			form.submit();
		}
	}

	function selectHueco(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		form.selHuecos[1].checked = true;
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.generacionCartelas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   	<td>
				<a class="etiquetaAzul12Bold"
				   href="javascript:generarCartelas()"
				><html:img page="/pages/images/cartela.gif"
				        border="0"
				        altKey="archigest.archivo.deposito.generarCartelas"
				        titleKey="archigest.archivo.deposito.generarCartelas"
				        styleClass="imgTextBottom"/>&nbsp;
		        <bean:message key="archigest.archivo.deposito.generarCartelas"/></a>
			</td>
			<td width="10px"></td>
			<td nowrap>
				<tiles:insert definition="button.closeButton" />
			</td>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div id="barra_errores"><archivo:errors /></div>
		<bean:define id="informeOcupacion" value="false" toScope="request"/>
		<tiles:insert page="/pages/tiles/deposito/tiponoasignable/datos_tipoNoAsignable.jsp" />
		<div class="separador8">&nbsp;</div>

		<div class="bloque">
			<html:form action="/gestionTipoNoAsignableAction">
				<html:hidden property="id"/>
				<html:hidden property="idUbicacion"/>
				<input type="hidden" name="method" value="verCartelas"/>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td class="tdTitulo" colspan="3">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
							<c:set var="listaFormatos" value="${sessionScope[appConstants.deposito.LISTA_FORMATOS]}" />
							<html:select property="idFormato" size="1" styleClass="input" styleId="formatos">
								<html:option value="">&nbsp;</html:option>
								<html:optionsCollection name="listaFormatos" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
				</table>
				<table class="formulario" cellpadding=0 cellspacing=6>
					<tr>
						<td class="tdTitulo" colspan="3">
							<bean:message key="archigest.archivo.deposito.seleccionarHuecos.caption" />:
						</td>
					</tr>
				</table>
				<table class="formulario" cellpadding=0 cellspacing=0>
					<tr>
						<td width="50px"></td>
						<td width="20px">
							<html:radio property="selHuecos" value="1" styleId="selHuecos" styleClass="radio"/>
						</td>
						<td class="etiquetaAzul11Bold">
							&nbsp;<bean:message key="archigest.archivo.deposito.seleccionarHuecos.selTodosHuecos"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<html:radio property="selHuecos" value="2" styleId="selHuecos" styleClass="radio"/>
						</td>
						<td class="etiquetaAzul11Bold">
							&nbsp;<bean:message key="archigest.archivo.deposito.seleccionarSignaturas"/>:
							&nbsp;&nbsp;&nbsp;
							<html:text property="textSignaturas" name="NoAsignableForm" styleId="textSignaturas" size="40" onkeypress="javascript:selecthueco();"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td class="etiquetaAzul11Normal">
							<bean:message key="archigest.archivo.deposito.seleccionarSignaturas.msgSelAlgunasSignaturas"/>
						</td>
					</tr>

				</table>
			</html:form>
		 </div>

		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
