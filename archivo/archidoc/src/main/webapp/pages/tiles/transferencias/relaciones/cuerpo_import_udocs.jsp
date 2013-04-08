<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<bean:struts id="mappingGestionUdocs" mapping="/gestionUdocsRelacion" />
	
<div id="contenedor_ficha">

<html:form action="/gestionUdocsRelacion" enctype="multipart/form-data">
<input type="hidden" name="method" value="importUdocs">


<div class="ficha">

<h1><span>
	<div class="w100">
		<table class="w98" cellpadding="0" cellspacing="0">
			<tr>
			    <td class="etiquetaAzul12Bold" height="25px">
					<bean:message key="archigest.archivo.transferencias.import.udocs"/>
				</td>
			    <td align="right">
					<table cellpadding="0" cellspacing="0">
						<tr>
						   	<td width="10">&nbsp;</td>
						   	<td>	
								<script>
									function importar() {
										var form = document.forms['<c:out value="${mappingGestionUdocs.name}" />'];

										if (window.top.showWorkingDiv) {
											var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
											var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
											window.top.showWorkingDiv(title, message);
										}
										
										form.submit();
									}
								</script>
						   		<a class="etiquetaAzul12Bold" href="javascript:importar()" >
									<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.aceptar"/>
								</a>
							</td>						
						   <td width="10">&nbsp;</td>
						   <td>
								<c:url var="volverURL" value="/action/gestionRelaciones">
									<c:param name="method" value="goBack" />
								</c:url>
								<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>">
									<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
						   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
						   		</a>
						   	</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

<div class="bloque"> <%--primer bloque de datos --%>

		<TABLE class="formulario" cellpadding=0 cellspacing=0>

			<tr>
				<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.transferencias.import.file"/>:&nbsp;</td>
				<td class="tdDatos"><html:file property="fichero" size="60" style="height:20px;"/>&nbsp;</td>
			</tr>	
			
		</TABLE>	
</div>



</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>












