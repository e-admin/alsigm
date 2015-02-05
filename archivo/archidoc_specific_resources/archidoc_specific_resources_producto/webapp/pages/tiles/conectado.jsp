<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<tiles:insert template="/pages/tiles/SimpleLayout.jsp">
  <tiles:put name="head" value="headconectado.jsp" />
  <tiles:put name="cabecera" value="cabecera.jsp" />
  <tiles:put name="pie_pagina" value="pie_pagina.jsp" />

  <c:set var="appUser" value="${sessionScope[appConstants.common.USUARIOKEY]}" />
  <jsp:useBean id="now" class="java.util.Date" />

  <tiles:put name="cuerpo" direct="true">
	<div id="contenido_centrado">
		<div class="ficha_fixed">
			<div class="encabezado"><div class="left_encabezado"><h3>&nbsp;</h3></div></div> <!-- fin encabezado -->
			<div class="cuerpo">
				<div class="contenido_cuerpo_login">
					<div class="seccion_conectado">
						<p class="etiquetaConectado" style="margin-top:20px; FONT-WEIGHT: normal; COLOR: #8A1A13; FONT-SIZE: 18px; FONT-FAMILY: Verdana, Helvetica, sans-serif; ">
							<bean:message key="archigest.archivo.conectado.msgConectado"/>
						</p>
						<hr class="hrLogin"/>
						<p class="fila_sub_conectado">
							<label class="etiquetaUser1"><bean:message key="archigest.archivo.conectado.nombreUsuario"/>:</label>
							<span class="dato_conectado"><c:out value="${appUser.name}" /> <c:out value="${appUser.surname}" /></span>
						</p>
						<p class="fila_sub_conectado2">
							<label class="etiquetaUser1"><bean:message key="archigest.archivo.conectado.fechaConexion"/>:</label>
							<span class="dato_conectado"><fmt:formatDate value="${now}" pattern="${appConstants.common.FORMATO_FECHA_DETALLADA}" /></span>
						</p>
					</div> <!-- fin seccion -->
				</div> <!-- contenido_cuerpo -->
			</div> <!-- fin cuerpo -->
		</div> <!-- fin ficha_fixed -->
	</div> <!-- fin contenido_centrado -->
  </tiles:put>
</tiles:insert>

<script>
	var WBwidth = self.screen.availWidth - 10;
	var WBheight = self.screen.availHeight - 64;
	var userIP = "<c:out value="${appUser.ip}" />";
	userIP = userIP.replace(/\./g , "_");
	userIP = userIP.replace(/\:/g , "_");
	var WBName = '<c:out value="WB_${appUser.id}" />' + '_' + userIP;
	window.name = 'Opener_' + WBName;
	var dashboard = window.open('<c:url value="/action/homepage?method=verBandeja" />',WBName,'status=yes,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,top=0,left=0,width='+WBwidth+',height='+WBheight);
	if(dashboard){
		dashboard.resizeTo(self.screen.availWidth, self.screen.availHeight);
		dashboard.focus();
	}
	else{
		alert('<bean:message key="errors.window.open"/>');
	}
</script>