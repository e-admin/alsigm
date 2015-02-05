<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/gestionReemplazarValores" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function cleanForm(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];

		cleanAmbito(form);

		cleanCondicionesAvanzadas(form);
	}

	function aceptar(formName){
		var fichaObligatoria = false;

		var checkBox=document.getElementById('checkboxCamposVacios');
	    var selectFormas = document.getElementById("formaReemplazo");

		if((checkBox && checkBox.checked) || (selectFormas && selectFormas.value != 0)){
			fichaObligatoria = true;
		}


		var comboFicha=document.getElementById('idFicha');
		if(fichaObligatoria && comboFicha.value=="")
			alert('<bean:message key="archigest.reemplazoSimple.error.noFichaDeterminada.valoresVacios"/>');
		else{
			var select = document.getElementsByName("campo")[0];
			var idCampo = select[select.selectedIndex].value;

			var form=document.forms[formName];

			if(idCampo != null && idCampo != "")
				form.submit();
			else
				alert('<bean:message key="archigest.descripcion.reemplazo.seleccionCampo.obligatoria"/>');
		}
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.descripcion.reemplazo.titulo"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<TR>
			    <TD>
					<bean:struts id="mappingReemplazo" mapping="/gestionReemplazarValores" />
					<a class="etiquetaAzul12Bold" href="javascript:aceptar('<c:out value="${mappingReemplazo.name}" />');" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>

				<TD>
					<c:url var="cancelURI" value="/action/gestionReemplazarValores">
						<c:param name="method" value="goBack"  />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>" >
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="regModificados" value="${sessionScope[appConstants.descripcion.DESCRIPCION_NUM_REG_MODIFICADOS]}"/>
		<c:if test="${regModificados!=null && regModificados > 0}">
			<ul class="warning_ul">
				<li class="warning_li">
					<bean:message key="archigest.descripcion.reemplazo.correcto"/>
				</li>
			</ul>
		</c:if>
		<c:if test="${regModificados!=null && regModificados <= 0}">
			<ul class="warning_ul">
				<li class="warning_li">
					<bean:message key="archigest.descripcion.reemplazo.incorrecto"/>
				</li>
			</ul>
		</c:if>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">

			<tiles:insert page="/pages/tiles/fondos/busquedas/div_busqueda_descriptores.jsp" flush="true"/>

			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.descripcion.reemplazo.campos.busqueda"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR><TD>
						<script>
							function verElementosAfectados() {
								var form = document.forms['<c:out value="${mappingReemplazo.name}" />'];
								form.method.value = "verElementosAfectados";

								var select = document.getElementsByName("campo")[0];
								var idCampo = select[select.selectedIndex].value;

								if(idCampo != null && idCampo != ""){
								    if (window.top.showWorkingDiv) {
								      var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
								      var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
								      var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
								      window.top.showWorkingDiv(title, message, message2);
								    }
									form.submit();
								}
								else
									alert('<bean:message key="archigest.descripcion.reemplazo.seleccionCampo.obligatoria"/>');
							}
						</script>
						<a class="etiquetaAzul12Normal" href="javascript:verElementosAfectados()">
							<html:img titleKey="archigest.descripcion.reemplazo.verElementosAReemplazar" altKey="archigest.descripcion.reemplazo.verElementosAReemplazar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
							<bean:message key="archigest.descripcion.reemplazo.verElementosAReemplazar"/>
						</a>
					</TD></TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<html:form action="/gestionReemplazarValores">
					<input type="hidden" name="method" value="checkReemplazo"/>
					<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_FONDOS_AVANZADA}"/>"/>
					<input type="hidden" name="reemplazoSimple" value="true"/>

					<table class="formulario" style="table-layout:auto">

						<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
						<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
						<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
						<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
						<bean:define id="sizeCampo" value="80" toScope="request"/>

						<tiles:insert page="../fondos/busquedas/campo_busqueda_condiciones_ambito.jsp" flush="true"/>
					</table>
					<table class="formulario">
						<bean:define id="methodLoadFicha" value="initReemplazoSimple" toScope="request"/>
						<tiles:insert page="../descripcion/campo_reemplazo_simple.jsp" flush="true"/>
					</table>
				</html:form>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>