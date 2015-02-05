<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<bean:struts id="mappingGestionUdocs" mapping="/gestionUdocsRelacion" />

<c:set var="xml" value="${requestScope[appConstants.transferencias.IMPORT_RESULT_XML_KEY]}"/>
<c:set var="xsl" value="${requestScope[appConstants.transferencias.IMPORT_RESULT_XSL_KEY]}"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<div id="contenedor_ficha">
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
										<c:url var="volverURL" value="/action/gestionRelaciones">
											<c:param name="method" value="goBack" />
										</c:url>
										<a class="etiquetaAzul12Bold" href="<c:out value="${volverURL}" escapeXml="false"/>" >
											<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
											&nbsp;<bean:message key="archigest.archivo.cerrar"/>
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
				<div id="barra_errores">
						<archivo:errors />
				</div>
				<div class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
					<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
				</div> <%--primer bloque de datos (resumen siempre visible) --%>
								
				 <pa:transform xmlParamName="xml" xslParamName="xsl"/>
			</div> <%--cuerpo_drcha --%>
		</div> <%--cuerpo_izda --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>












