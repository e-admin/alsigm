<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<tiles:insert template="/pages/tiles/SimpleLayout.jsp">
  <tiles:put name="head" value="headconectado.jsp" />
  <tiles:put name="cabecera" value="cabecera.jsp" />
  <tiles:put name="pie_pagina" value="pie_pagina.jsp" />

  <tiles:put name="cuerpo" direct="true">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" >
		<tr>
			<td width="100%">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" >
					<TR>
						<TD><html:img page="/pages/images/pixel.gif" width="1" height="140"/></TD>
					</TR>
					<tr>
					  <td width="100%">
					    <table style = "background: url(../images/logo/logo_archivo.jpg) no-repeat" cellpadding="7" cellspacing="2" border="0"  align="center" >
					      <tr>
							<td width="50%"></td>
					        <td>
				              <table border="0" cellpadding="0" cellspacing="2">
				                <tr>
				                  <td class="etiquetaLoginBig"><bean:message key="archigest.archivo.conectado.msgConectado"/></td>
				                </tr>
								<tr>
									<td><hr class="hrLogin"/></td>
								</tr>
				              </table>
				              <table border="0" cellpadding="0" cellspacing="4">
				                <tr>
								  <c:set var="appUser" value="${sessionScope[appConstants.common.USUARIOKEY]}" />
				                  <td colspan="2" class="etiquetaLoginSmall"><bean:message key="archigest.archivo.conectado.nombreUsuario"/>:</td>
				                </tr>
				                <tr>
				                  <td width="10px"></td>
				                  <td class="etiquetaNegra11Normal"><c:out value="${appUser.name}" /> <c:out value="${appUser.surname}" /></td>
				                </tr>
				                <tr>
								  <jsp:useBean id="now" class="java.util.Date" />
				                  <td colspan="2" class="etiquetaLoginSmall"><bean:message key="archigest.archivo.conectado.fechaConexion"/>:</td>
				                </tr>
				                <tr>
				                  <td width="10px"></td>
				                  <td class="etiquetaNegra11Normal"><fmt:formatDate value="${now}" pattern="${appConstants.common.FORMATO_FECHA_DETALLADA}" /></td>
				                </tr>
								<TR>
									<TD><html:img page="/pages/images/pixel.gif" width="1" height="30"/></TD>
								</TR>
				              </table>
					        </td>
					      </tr>
					    </table>
					  </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

  </tiles:put>
</tiles:insert>

<c:set var="appUser" value="${sessionScope[appConstants.common.USUARIOKEY]}" />

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