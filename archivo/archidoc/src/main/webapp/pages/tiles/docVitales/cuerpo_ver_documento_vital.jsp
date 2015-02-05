<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<html:xhtml/>

<c:set var="documentoVital" value="${sessionScope[appConstants.documentosVitales.DOCUMENTO_VITAL_KEY]}"/>
<c:set var="documentoVitalVigente" value="${sessionScope[appConstants.documentosVitales.DOCUMENTO_VITAL_VIGENTE_KEY]}"/>
<c:set var="tiposDocumentosVitales" value="${sessionScope[appConstants.documentosVitales.TIPOS_DOCUMENTOS_VITALES_KEY]}"/>
<c:set var="listaPosiblesCiudadanos" value="${sessionScope[appConstants.documentosVitales.RESULTADOS_BUSQUEDA_TERCEROS]}" />

<bean:struts id="actionMapping" mapping="/gestionDocumentosVitales" />

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="modo" value="CONSULTA"/>
<c:if test="${documentoVital.estadoDocVit == appConstants.estadosDocumentosVitales.PENDIENTE_VALIDACION}">
<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
<c:set var="modo" value="EDICION"/>
</security:permissions>
</c:if>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.docVital.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
				<c:if test="${documentoVital.estadoDocVit == appConstants.estadosDocumentosVitales.PENDIENTE_VALIDACION}">
				<td nowrap>
					<script>
						function validar()
						{
							if (confirm("<bean:message key="archigest.archivo.docvitales.docVital.validar.confirm.msg"/>"))
							{
								var form = document.forms["<c:out value="${actionMapping.name}" />"];
								form.submit();
							}
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:validar()">
						<html:img page="/pages/images/aceptar.gif" 
						titleKey="archigest.archivo.validar" 
						altKey="archigest.archivo.validar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.validar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<script>
						function rechazar()
						{
							<c:url var="rechazarURL" value="/action/gestionDocumentosVitales">
								<c:param name="method" value="rechazar" />
								<c:param name="id" value="${documentoVital.id}" />
							</c:url>
							if (confirm("<bean:message key="archigest.archivo.docvitales.docVital.rechazar.confirm.msg"/>"))
								window.location = "<c:out value="${rechazarURL}" escapeXml="false"/>";
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:rechazar()">
						<html:img page="/pages/images/rechazar.gif" 
						titleKey="archigest.archivo.rechazar" 
						altKey="archigest.archivo.rechazar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.rechazar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</c:if>
				<c:if test="${documentoVital.estadoDocVit == appConstants.estadosDocumentosVitales.VIGENTE}">
				<td nowrap>
					<script>
						function pasarAHistorico()
						{
							<c:url var="pasarAHistoricoURL" value="/action/gestionDocumentosVitales">
								<c:param name="method" value="pasarAHistorico" />
								<c:param name="id" value="${documentoVital.id}" />
							</c:url>
							if (confirm("<bean:message key="archigest.archivo.docvitales.docVital.pasarAHistorico.confirm.msg"/>"))
								window.location = "<c:out value="${pasarAHistoricoURL}" escapeXml="false"/>";
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:pasarAHistorico()">
						<html:img page="/pages/images/go.gif" 
						titleKey="archigest.archivo.docvitales.docVital.pasoAHistorico.caption" 
						altKey="archigest.archivo.docvitales.docVital.pasoAHistorico.caption"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.docvitales.docVital.pasoAHistorico.caption"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</c:if>
				</security:permissions>
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
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${!empty documentoVitalVigente && documentoVital.estadoDocVit == appConstants.estadosDocumentosVitales.PENDIENTE_VALIDACION}">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td nowrap>
								<c:url var="docVitalVigenteURL" value="/action/gestionDocumentosVitales">
									<c:param name="method" value="verDocVitalVigente" />
									<c:param name="id" value="${documentoVitalVigente.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${docVitalVigenteURL}" escapeXml="false"/>">
									<html:img page="/pages/images/go.gif" 
										titleKey="archigest.archivo.docvitales.docVital.verDocVigente.caption" 
										altKey="archigest.archivo.docvitales.docVital.verDocVigente.caption"
										styleClass="imgTextTop"/>
									<bean:message key="archigest.archivo.docvitales.docVital.verDocVigente.caption"/>
								</a>
							</td>
						</tr>
					</table>
				</c:if>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<html:form action="/gestionDocumentosVitales">
				<table class="formulario">
					
					<input type="hidden" name="method" value="validar">
					<html:hidden property="id"/>
					<html:hidden property="idBdTerceros"/>

					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.tipoDocumento"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
							<c:when test="${modo == 'EDICION'}">
								<html:select property="idTipoDocVit">
									<html:options collection="tiposDocumentosVitales" property="id" labelProperty="nombre" />
								</html:select>
							</c:when>
							<c:otherwise>
								<c:out value="${documentoVital.nombreTipoDocVit}"/>
							</c:otherwise>
							</c:choose>						
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
							<c:choose>
							<c:when test="${modo == 'EDICION'}">
								<input type="text" size="14" maxlength="10" name="fechaCad" value="<fmt:formatDate value="${documentoVitalForm.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />"/>
								<archigest:calendar 
									image="../images/calendar.gif"
									formId="documentoVitalForm" 
									componentId="fechaCad" 
									format="dd/mm/yyyy" 
									enablePast="false" />
							</c:when>
							<c:otherwise>
								<fmt:formatDate value="${documentoVital.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />
							</c:otherwise>
							</c:choose>						
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
							<c:when test="${modo == 'EDICION'}">
		  						<html:text property="identidad" styleClass="inputRO95" readonly="true" /> 
		  						<script>
		  							function mostrarListaTerceros() 
		  							{
		  								xDisplay('seleccionCiudadano', 'block');
		  								xDisplay('listaCiudadanos', 'none');
		  							}
		  						</script>
		  						<a href="javascript:mostrarListaTerceros()">
		  							<html:img page="/pages/images/expand.gif" 
		  								titleKey="archigest.archivo.cf.instituciones" 
		  								altKey="archigest.archivo.cf.instituciones" 
		  								styleClass="imgTextMiddle" />
		  						</a>
		  						<div id="seleccionCiudadano" class="input95" <c:if test="${listaPosiblesCiudadanos == null}">style="display:none"</c:if>> 
		  							<div id="buscadorCiudadano">
		  								<div class="cabecero_bloque_tab">
		  									<TABLE width="100%" cellpadding="0" cellspacing="0">
		  									  <TR>
		  										<TD width="100%" align="right">
		  										<TABLE cellpadding="0" cellspacing="0">
		  										  <TR>
		  												<TD>
		  												<script>
		  												function buscarCiudadano() 
		  												{
		  													var form = document.forms['<c:out value="${actionMapping.name}" />'];
		  													if (form.nameSearchToken.value == ''
		  														&& form.surname1SearchToken.value == ''
		  														&& form.surname2SearchToken.value == ''
		  														&& form.companySearchToken.value == ''
		  														&& form.ifSearchToken.value == '') 
		  														alert("<bean:message key="errors.noSearchToken"/>");
		  													else
		  													{
		  														form.method.value = "buscarCiudadanos";
		  														form.submit();
		  													}
		  												}
		  												</script>
		  												<a class="etiquetaAzul12Normal" 
		  													href="javascript:buscarCiudadano()">
		  													<html:img page="/pages/images/buscar.gif"
		  														titleKey="archigest.archivo.buscar" 
		  														altKey="archigest.archivo.buscar" 
		  														styleClass="imgTextMiddle" />
		  													&nbsp;<bean:message key="archigest.archivo.buscar"/>
		  												</a>		
		  												</TD>
		  												<td width="10px">&nbsp;</td>
		  										 </TR>
		  										</TABLE>
		  										</TD>
		  									</TR></TABLE>
		  								</div>
										<tiles:insert page="/pages/tiles/controls/thirdPartySearchForm.jsp"/>
		  							</div>
		  							<div id="listaCiudadanos" style="width:100%;height:100px;overflow:auto;background-color:#efefef" >
		  								<c:choose>
		  								<c:when test="${!empty listaPosiblesCiudadanos}">
		  									<script>
		  										function seleccionarTercero(item) 
		  										{
		  											var form = document.forms['<c:out value="${actionMapping.name}" />'];
		  											form.identidad.value = item.getAttribute('identidad');
		  											form.idBdTerceros.value = item.id;
		  											form.numIdentidad.value = item.getAttribute('numIdentidad');
		  										}
		  									</script>
		  									<c:forEach var="tercero" items="${listaPosiblesCiudadanos}">
		  										<div class="etiquetaGris12Normal" 
		  											id='<c:out value="${tercero.id}" />' 
		  											identidad="<c:out value="${tercero.nombre}" /> <c:out value="${tercero.primerApellido}" /> <c:out value="${tercero.segundoApellido}" />"
		  											numIdentidad="<c:out value="${tercero.identificacion}" />"
		  											onmouseOver="this.style.backgroundColor='#dedede'"
		  											onmouseOut="this.style.backgroundColor='#efefef'"
		  											onclick="seleccionarTercero(this)" 
		  											style='padding:4px; cursor:hand; cursor:pointer; text-align:left;'>
		  											<c:out value="${tercero.nombre}" /> <c:out value="${tercero.primerApellido}" /> <c:out value="${tercero.segundoApellido}" />
		  										</div>
		  									</c:forEach>
		  								</c:when>
		  								<c:otherwise>
		  									<div style="color:red;padding:3px;padding-top:20px;text-align:left">
		  										<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
		  										<bean:message key="archigest.archivo.docvitales.docVital.terceros.empty.msg"/>
		  									</div>
		  								</c:otherwise>
		  								</c:choose>
		  							</div>
		  							<script>
		  								function hideSeleccionCiudadano() {
		  									xDisplay('seleccionCiudadano', 'none');
		  								}
		  							</script>
		  							<table cellpadding="0" cellspacing="0" width="100%">
		  								<tr>
		  									<td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #000000">
		  										<a class="etiquetaAzul12Bold"
		  											href="javascript:hideSeleccionCiudadano()">
		  											<html:img page="/pages/images/close.gif" 
		  												altKey="archigest.archivo.cerrar" 
		  												titleKey="archigest.archivo.cerrar" 
		  												styleClass="imgTextMiddle" />
		  											&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		  										</a>&nbsp;
		  									</td>
		  								</tr>
		  							</table>
		  						</div>
		  						<div class="separador5">&nbsp;</div>
							</c:when>
							<c:otherwise>
								<c:out value="${documentoVital.identidad}"/>
							</c:otherwise>
							</c:choose>						
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.identificacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
							<c:when test="${modo == 'EDICION'}">
								<html:text property="numIdentidad" styleClass="inputRO" readonly="true" /> 
							</c:when>
							<c:otherwise>
								<c:out value="${documentoVital.numIdentidad}"/>
							</c:otherwise>
							</c:choose>						
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
							<c:choose>
							<c:when test="${modo == 'EDICION'}">
								<html:textarea property="observaciones"/>
							</c:when>
							<c:otherwise>
								<c:out value="${documentoVital.observaciones}"/>
							</c:otherwise>
							</c:choose>						
						</td>
					</tr>
					
					
					
				</table>
				
				</html:form>
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
						<param name="FitMode" value="1"> <%-- Ajusta la imagen al visualizador: 0=Vertical, 1=Horizontal --%>
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
