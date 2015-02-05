<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script>
	function generarInformeResultadoBusqueda(){
		if (window.top.showWorkingDivIFrame) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoExportacion"/>';
			//var message = ''; //'<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			var message2 = ''; //'<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			window.top.showWorkingDivIFrame(title, message, message2);
		}

		initProgressBar('<c:out value="${resetProgressBarURL}"/>');
		document.forms['formExportarInformeListado'].submit();
	}
</script>

<bean:struts id="actionMapping" mapping="/exportarInformeListado" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.generar.listado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:generarInformeResultadoBusqueda();" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>

				<TD>
					<c:url var="cancelURI" value="/action/exportarInformeListado">
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
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<c:url var="exportarInformeListadoURL" value="/action/exportarInformeListado" scope="request"/>
				<form name="formExportarInformeListado" id="formExportarInformeListado" 
							action="<c:out value="${exportarInformeListadoURL}"/>">
					<html:hidden property="method" value="generate"/>
					<table class="formulario">
						<tr>
							<td class="tdTituloFichaSinBorde" width="250px">
								<bean:message key="archigest.label.generar.informe.con.formato"/>:&nbsp;
							</td>
							<td class="" style="border-bottom: 0;" width="400px">
								<select id="formatoListado" name="formatoListado">
									<option value="<c:out value="${appConstants.common.FORMATO_PDF}"/>"><bean:message key="archigest.formato.listado.pdf"/></option>
									<option value="<c:out value="${appConstants.common.FORMATO_TXT}"/>"><bean:message key="archigest.formato.listado.txt"/></option>
									<option value="<c:out value="${appConstants.common.FORMATO_XLS}"/>"><bean:message key="archigest.formato.listado.excel"/></option>						
								</select>
							</td>
						</tr>
					</table>
				</form>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>