<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="tiposDocumentosVitales" value="${requestScope[appConstants.documentosVitales.TIPOS_DOCUMENTOS_VITALES_KEY]}"/>
<c:set var="estadosDocumentosVitales" value="${requestScope[appConstants.documentosVitales.ESTADOS_DOCUMENTOS_VITALES_KEY]}"/>

<bean:struts id="actionMapping" mapping="/buscarDocumentosVit" />

<script>
	function checkFechaComp(seed)
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.elements["fecha" + seed + "Comp"].value == "[..]")
		{
			document.getElementById("idFecha" + seed).style.display = 'none';
			document.getElementById("idRangoFechas" + seed).style.display = 'block';

			checkFechaFormato(form.elements["fecha" + seed + "IniFormato"], "idFecha" + seed + "Ini");
			checkFechaFormato(form.elements["fecha" + seed + "FinFormato"], "idFecha" + seed + "Fin");
		}
		else
		{
			document.getElementById("idRangoFechas" + seed).style.display = 'none';
			document.getElementById("idFecha" + seed).style.display = 'block';

			checkFechaFormato(form.elements["fecha" + seed + "Formato"], "idFecha" + seed);
		}
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.busqueda.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<script>
						function search()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:search()">
						<html:img page="/pages/images/buscar.gif" 
						titleKey="archigest.archivo.buscar" 
						altKey="archigest.archivo.buscar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.buscar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<script>
						function clean()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.numIdentidad.value = "";
							form.identidad.value = "";
							
							deseleccionarCheckboxSet(form.tiposDocumentos);
							deseleccionarCheckboxSet(form.estados);
					
							form.fechaCaducidadComp.value = "=";
							form.fechaCaducidadFormato.value = "AAAA";
							form.fechaCaducidadD.value = "";
							form.fechaCaducidadM.value = "";
							form.fechaCaducidadA.value = "";
							checkFechaComp("Caducidad")
					
							form.numAccesosComp.value = "=";
							form.numAccesos.value = "";

							form.fechaUltimoAccesoComp.value = "=";
							form.fechaUltimoAccesoFormato.value = "AAAA";
							form.fechaUltimoAccesoD.value = "";
							form.fechaUltimoAccesoM.value = "";
							form.fechaUltimoAccesoA.value = "";
							checkFechaComp("UltimoAcceso")
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:clean()">
						<html:img page="/pages/images/clear0.gif" 
						titleKey="archigest.archivo.limpiar" 
						altKey="archigest.archivo.limpiar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.limpiar"/>
					</a>
				</td>
				<td width="10px">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">		
			<table class="formulario">
				<html:form action="/buscarDocumentosVit">
				<input type="hidden" name="method" value="buscar">

				<tr>
					<td class="tdTituloFicha" width="200px">
						<bean:message key="archigest.archivo.docvitales.docVital.identificacion"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<html:text property="numIdentidad" size="20" />
					</td>
				</tr>
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.nombre"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<html:text property="identidad" styleClass="input90" />
					</td>
				</tr>
				
				<c:if test="${!empty tiposDocumentosVitales}">
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.tipoDocumento"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<table class="formulario">
							<tr>
								<td align="right">
									<a class="etiquetaAzul12Bold" 
									href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].tiposDocumentos);" 
						 			><html:img page="/pages/images/checked.gif" 
						 			altKey="archigest.archivo.selTodos"
						 			titleKey="archigest.archivo.selTodos"
						 			styleClass="imgTextBottom" />
						 			&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
						 			&nbsp;&nbsp;
									<a class="etiquetaAzul12Bold" 
									href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].tiposDocumentos);" 
						 			><html:img page="/pages/images/check.gif" 
						 			altKey="archigest.archivo.quitarSel"
						 			titleKey="archigest.archivo.quitarSel"
						 			styleClass="imgTextBottom" />
						 			&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
									&nbsp;&nbsp;
							   </td>
							</tr>
						</table>
						<table cellpadding="0" cellspacing="0">
							<c:forEach var="tipo" items="${tiposDocumentosVitales}">
							<tr>
								<td>
									<html:multibox style="border:0" property="tiposDocumentos">
										<c:out value="${tipo.id}"/>
									</html:multibox>
								</td>
								<td class="tdDatos">
									<c:out value="${tipo.nombre}"/>
								</td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.estado"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<table class="formulario">
							<tr>
								<td align="right">
									<a class="etiquetaAzul12Bold" 
									href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].estados);" 
						 			><html:img page="/pages/images/checked.gif" 
						 			altKey="archigest.archivo.selTodos"
						 			titleKey="archigest.archivo.selTodos"
						 			styleClass="imgTextBottom" />
						 			&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
						 			&nbsp;&nbsp;
									<a class="etiquetaAzul12Bold" 
									href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].estados);" 
						 			><html:img page="/pages/images/check.gif" 
						 			altKey="archigest.archivo.quitarSel"
						 			titleKey="archigest.archivo.quitarSel"
						 			styleClass="imgTextBottom" />
						 			&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
									&nbsp;&nbsp;
							   </td>
							</tr>
						</table>
						<table cellpadding="0" cellspacing="0">
							<c:forEach var="estado" items="${estadosDocumentosVitales}" varStatus="rowNum">
							<c:if test="${rowNum.count % 3 == 1}"><tr></c:if>
								<td>
									<html:multibox style="border:0" property="estados">
										<c:out value="${estado.id}"/>
									</html:multibox>
								</td>
								<td class="tdDatos">
									<c:out value="${estado.description}"/>
								</td>
								<td width="10px">&nbsp;</td>
							<c:if test="${rowNum.count % 3 == 0}"></tr></c:if>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.fechaCaducidad"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<html:select property="fechaCaducidadComp" onchange="javascript:checkFechaComp('Caducidad')">
										<html:option key="archigest.archivo.simbolo.igual" value="="/>
										<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
										<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
										<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
										<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
										<html:option key="archigest.archivo.simbolo.rango" value="[..]"/>
									</html:select>
								</td>
							    <td width="10">&nbsp;</td>
								<td id="idFechaCaducidad" style="display:none;">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<html:select property="fechaCaducidadFormato" styleId="fechaCaducidadFormato" onchange="javascript:checkFechaFormato(this, 'idFechaCaducidad')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaCaducidadDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadAValor" style="display:none;">
										    	<html:text property="fechaCaducidadA" maxlength="4" size="4"/>
										    </td>
									    </tr>
								    </table>
								</td>
								<td id="idRangoFechasCaducidad" style="display:none;">
									<table cellpadding="0" cellspacing="0">
										<tr>
										    <td class="user_data"><bean:message key="archigest.archivo.desde"/></td>
										    <td width="5">&nbsp;</td>
											<td>
												<html:select property="fechaCaducidadIniFormato" styleId="fechaCaducidadIniFormato" onchange="javascript:checkFechaFormato(this, 'idFechaCaducidadIni')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaCaducidadIniDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadIniD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadIniMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadIniM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadIniAValor" style="display:none;">
										    	<html:text property="fechaCaducidadIniA" maxlength="4" size="4"/>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td class="user_data"><bean:message key="archigest.archivo.hasta"/></td>
										    <td width="5">&nbsp;</td>
											<td>
												<html:select property="fechaCaducidadFinFormato" styleId="fechaCaducidadFinFormato" onchange="javascript:checkFechaFormato(this, 'idFechaCaducidadFin')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaCaducidadFinDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadFinD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadFinMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaCaducidadFinM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaCaducidadFinAValor" style="display:none;">
										    	<html:text property="fechaCaducidadFinA" maxlength="4" size="4"/>
										    </td>
									    </tr>
								    </table>
								</td>
							</tr>
						</table>
						<script>checkFechaComp('Caducidad');</script>
					</td>
				</tr>
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.numAccesos"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<html:select property="numAccesosComp">
										<html:option key="archigest.archivo.simbolo.igual" value="="/>
										<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
										<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
										<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
										<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
									</html:select>
								</td>
							    <td width="10">&nbsp;</td>
								<td><html:text property="numAccesos" size="6"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tdTituloFicha">
						<bean:message key="archigest.archivo.docvitales.docVital.fechaUltimoAcceso"/>:&nbsp;
					</td>
					<td class="tdDatosFicha">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<html:select property="fechaUltimoAccesoComp" onchange="javascript:checkFechaComp('UltimoAcceso')">
										<html:option key="archigest.archivo.simbolo.igual" value="="/>
										<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
										<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
										<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
										<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
										<html:option key="archigest.archivo.simbolo.rango" value="[..]"/>
									</html:select>
								</td>
							    <td width="10">&nbsp;</td>
								<td id="idFechaUltimoAcceso" style="display:none;">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<html:select property="fechaUltimoAccesoFormato" styleId="fechaUltimoAccesoFormato" onchange="javascript:checkFechaFormato(this, 'idFechaUltimoAcceso')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaUltimoAccesoDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoAValor" style="display:none;">
										    	<html:text property="fechaUltimoAccesoA" maxlength="4" size="4"/>
										    </td>
									    </tr>
								    </table>
								</td>
								<td id="idRangoFechasUltimoAcceso" style="display:none;">
									<table cellpadding="0" cellspacing="0">
										<tr>
										    <td class="user_data"><bean:message key="archigest.archivo.desde"/></td>
										    <td width="5">&nbsp;</td>
											<td>
												<html:select property="fechaUltimoAccesoIniFormato" styleId="fechaUltimoAccesoIniFormato" onchange="javascript:checkFechaFormato(this, 'idFechaUltimoAccesoIni')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaUltimoAccesoIniDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoIniD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoIniMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoIniM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoIniAValor" style="display:none;">
										    	<html:text property="fechaUltimoAccesoIniA" maxlength="4" size="4"/>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td class="user_data"><bean:message key="archigest.archivo.hasta"/></td>
										    <td width="5">&nbsp;</td>
											<td>
												<html:select property="fechaUltimoAccesoFinFormato" styleId="fechaUltimoAccesoIniFormato" onchange="javascript:checkFechaFormato(this, 'idFechaUltimoAccesoFin')">
													<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
													<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
													<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
												</html:select>
										    </td>
										    <td width="10">&nbsp;</td>
										    <td id="idFechaUltimoAccesoFinDValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoFinD" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoFinMValor" style="display:none;" nowrap="true">
										    	<html:text property="fechaUltimoAccesoFinM" maxlength="2" size="2"/>-
										    </td>
										    <td id="idFechaUltimoAccesoFinAValor" style="display:none;">
										    	<html:text property="fechaUltimoAccesoFinA" maxlength="4" size="4"/>
										    </td>
									    </tr>
								    </table>
								</td>
							</tr>
						</table>
						<script>checkFechaComp('UltimoAcceso');</script>
					</td>
				</tr>
				</html:form>
			</table>
		</div>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
