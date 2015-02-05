<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="documentoVital" value="${requestScope[appConstants.documentosVitales.DOCUMENTO_VITAL_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.docVital.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.tipoDocumento"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.nombreTipoDocVit}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.documento"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:url var="downloadURL" value="/action/gestionDocumentosVitales">
								<c:param name="method" value="download"/>
							</c:url>
							<a class="tdlink" href="<c:out value="${downloadURL}" escapeXml="false"/>">
								<c:out value="${documentoVital.nombreCompletoFichero}"/>
							</a>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.tamano"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatNumber value="${documentoVital.tamanoFich/1024}" pattern="#,###.##"/>&nbsp;
							<bean:message key="archigest.archivo.documentos.docVital.tamano.unidades"/>
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.estado"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:message key="archigest.archivo.docvitales.estado.${documentoVital.estadoDocVit}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.fechaCaducidad"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatDate value="${documentoVital.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.identidad}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.identificacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.numIdentidad}"/>
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.usuarioCaptura"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.nombreUsuarioCr}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.fechaCaptura"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatDate value="${documentoVital.fechaCr}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</td>
					</tr>
					<c:if test="${documentoVital.estadoDocVit != appConstants.estadosDocumentosVitales.PENDIENTE_VALIDACION}">
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.usuarioVigencia"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.nombreUsuarioVig}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.fechaVigencia"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatDate value="${documentoVital.fechaVig}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.numAccesos"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.numAccesos}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.fechaUltimoAcceso"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:formatDate value="${documentoVital.fechaUltAcceso}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</td>
					</tr>
					</c:if>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.observaciones"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${documentoVital.observaciones}"/>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

		<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
		<c:choose>
			<c:when test="${documentoVital.imagen}">
				<div class="separador8">&nbsp;</div>
		
				<c:if test="${appConstants.configConstants.usarVisorOcx}">
					<c:set var="obtenerDocumentoURL"><c:out value="${requestURI}" escapeXml="false"/>/action/gestionDocumentosVitales?method=download</c:set>
					<object id="ImageViewer" 
						classid="CLSID:24C6D59E-6D0D-11D4-8128-00C0F049167F" 
						codebase="<c:url value="/plugin/archi.cab" />#version=3,3,0,0" 
						width="100%" height="560">
						<param name="FileName" value="<c:out value="${obtenerDocumentoURL}" escapeXml="false"/>">
						<param name="FitMode" value="1"><%-- Ajusta la imagen al visualizador: 0=Vertical, 1=Horizontal --%>
						<param name="Enhancement" value="2">
						<param name="ShowToolbar" value="1">
						<param name="EnablePrint" value="0">
						<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left">
							<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
							<bean:message key="archigest.archivo.documentos.documento.errorVisorImagenes.msg"/>
						</div>
					</object>
				</c:if>
				
				<c:if test="${appConstants.configConstants.usarVisorOcx == appConstants.common.FALSE_STRING}">
					<c:set var="obtenerDocumentoIFrameURL"><c:out value="${requestURI}" escapeXml="false"/>/action/gestionDocumentosVitales?method=downloadIFrame</c:set>
					<div class="iFrameDiv">
						<iframe width="100%" height="560" src="<c:out value="${obtenerDocumentoIFrameURL}" escapeXml="false"/>" name="visor">
							<bean:message key="iframes.not.supported"/>
						</iframe>
					</div>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="obtenerDocumentoIFrameURL"><c:out value="${requestURI}" escapeXml="false"/>/action/gestionDocumentosVitales?method=downloadIFrame</c:set>
				<div class="iFrameDiv">
					<iframe width="100%" height="560" src="<c:out value="${obtenerDocumentoIFrameURL}" escapeXml="false"/>" name="visor">
						<bean:message key="iframes.not.supported"/>
					</iframe>
				</div>
			</c:otherwise>
		</c:choose>
			
	</tiles:put>
</tiles:insert>
