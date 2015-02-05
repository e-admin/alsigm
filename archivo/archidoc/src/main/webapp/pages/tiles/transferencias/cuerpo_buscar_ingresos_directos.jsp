<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mapping" mapping="/buscarRelaciones" />
<c:set var="formBean" value="${requestScope[mapping.name]}"/>


<c:set var="tipos" value="${requestScope[appConstants.transferencias.LISTA_TIPOS_KEY]}"/>
<c:set var="estados" value="${requestScope[appConstants.transferencias.LISTA_ESTADOS_KEY]}"/>

<div id="contenedor_ficha">
	<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
	<script language="JavaScript1.2" type="text/JavaScript">
		function clean()
		{
			var form = document.forms['<c:out value="${mapping.name}" />'];
			form.codigo.value = "";
			form.organo.value = "";
			form.gestorArchivo.value = "";
			form.anio.value = "";
			form.codigoSerie.value = "";
			form.serie.value = "";
			
			form.fechaComp.value = "=";
			form.fechaFormato.value = "AAAA";
			form.fechaD.value = "";
			form.fechaM.value = "";
			form.fechaA.value = "";
			form.fechaS.value = "";
			checkFechaComp(form);

			form.fechaIniFormato.value = "AAAA";
			form.fechaIniD.value = "";
			form.fechaIniM.value = "";
			form.fechaIniA.value = "";
			form.fechaIniS.value = "";
	
			form.fechaFinFormato.value = "AAAA";
			form.fechaFinD.value = "";
			form.fechaFinM.value = "";
			form.fechaFinA.value = "";
			form.fechaFinS.value = "";
			
			deseleccionarCheckboxSet(form.tipos);
			deseleccionarCheckboxSet(form.estados);
		}

		function buscar(){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.buscando.msgRelaciones"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			document.forms[0].submit();
		}
	</script>
	
	<html:form action="/buscarRelaciones">
	<input type="hidden" name="method" value="buscar"/>
	<html:hidden property="ingresoDirecto"/> 

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.transferencias.ingresos.directos.busqueda.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold" href="javascript:buscar();">
										<html:img page="/pages/images/buscar.gif" 
										        altKey="archigest.archivo.buscar" 
										        titleKey="archigest.archivo.buscar" 
										        styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold" href="javascript:clean()">
										<html:img page="/pages/images/clear0.gif" 
										        altKey="archigest.archivo.limpiar" 
										        titleKey="archigest.archivo.limpiar" 
										        styleClass="imgTextMiddle"/>
										&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<TD>
										<c:url var="cancelURL" value="/action/buscarPrestamos">
											<c:param name="method" value="goBack" />
										</c:url>
										<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
										<html:img page="/pages/images/close.gif" 
											altKey="archigest.archivo.cerrar" 
											titleKey="archigest.archivo.cerrar" 
											styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
								   	</TD>
								</tr> 	
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>
		
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
				
			<div id="barra_errores"><archivo:errors/></div>
		
			<div class="bloque">
				<table class="formulario">
					<c:set var="archivos" value="${sessionScope[appConstants.deposito.LISTA_ARCHIVOS_USUARIO_KEY]}" />
					<tr>
						<td class="tdTituloFicha" width="200px">
							<bean:message key="archigest.archivo.archivoCustodia"/>:&nbsp;
						</td>
						<td class="tdDatosFicha"">
							<c:choose>
								<c:when test="${!empty archivos}">
									<html:select property="idArchivo">
										<html:option key="archigest.archivo.todos" value=""/>
										<html:options collection="archivos" labelProperty="nombre" property="id" />
									</html:select>
								</c:when>
								<c:otherwise>
									<html:text property="nombreArchivo" readonly="readonly" styleClass="inputRO95"/>
									<html:hidden property="idArchivo"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha" width="200px">
							<bean:message key="archigest.archivo.deposito.formato"/>:&nbsp;
						</td>
						<td class="tdDatosFicha"">
							<c:set var="formatos" value="${sessionScope[appConstants.deposito.LISTA_FORMATOS]}" />
							<html:select property="idFormato">
								<html:option key="archigest.archivo.todos" value=""/>
								<html:options collection="formatos" labelProperty="nombre" property="id" />
							</html:select>
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha" width="200px">
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="codigo" />
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>

					<security:permissions permission="${appConstants.permissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE}|${appConstants.permissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR}|${appConstants.permissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR}">
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.organo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="organo" styleClass="input60"/>
						</td>
					</tr>
					</security:permissions>
							
					<security:permissions permission="${appConstants.permissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR}">
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.gestorArchivo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="gestorArchivo" styleClass="input60"/>
						</td>
					</tr>
					</security:permissions>

					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.Codserie"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="codigoSerie" />
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.serie"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="serie" styleClass="input60"/>
						</td>
					</tr>
					
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="observaciones" styleClass="input60"/>
						</td>
					</tr>

					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.anio"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="anio" size="4" maxlength="4"/>
						</td>
					</tr>

					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"  >
							<bean:message key="archigest.archivo.transferencias.relaciones.busqueda.estado"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<table class="formulario">
									<tr>
										<td align="right">
											<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].estados);" 
								 			><html:img page="/pages/images/checked.gif" 
												    border="0" 
												    altKey="archigest.archivo.selTodos"
												    titleKey="archigest.archivo.selTodos"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
											&nbsp;
											<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].estados);" 
								 			><html:img page="/pages/images/check.gif" 
												    border="0" 
												    altKey="archigest.archivo.quitarSel"
												    titleKey="archigest.archivo.quitarSel"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
											&nbsp;&nbsp;
									   </td>
									</tr>
							</table>
							<table cellpadding="0" cellspacing="0"><tr><td>
								<logic:iterate id="estado" name="estados" indexId="rowNum">
								<% if ((rowNum.intValue() % 3) == 0) out.println("<tr>"); %>
									<td>
										<html:multibox style="border:0" property="estados">
											<bean:write name="estado" property="id"/>
										</html:multibox>
									</td>
									<td class="tdDatos">
										<bean:write name="estado" property="description"/>
									</td>
									<td width="10">&nbsp;</td>
								<% if ((rowNum.intValue() % 3) == 2) out.println("</tr>"); %>
								</logic:iterate>
							</td></tr></table>
						</td>
					</tr>
					<%--Filtro por fechas de recepción --%>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha"><bean:message key="archigest.archivo.transferencias.relaciones.busqueda.fecha.estado"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<html:select styleId="fechaComp" property="fechaComp" styleClass="input" onchange="javascript:checkFechaComp(document.forms[0])">
											<html:option key="archigest.archivo.simbolo.igual" value="="/>
											<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
											<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
											<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
											<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
											<html:option key="archigest.archivo.simbolo.rango" value="[..]"/>
									    </html:select>
									</td>
								    <td width="10">&nbsp;</td>
									<td id="idFecha" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<html:select property="fechaFormato" styleId="fechaFormato" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFecha')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaD" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaM" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaAValor" style="display:none;">
											    	<html:text property="fechaA" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaSValor" style="display:none;">
											    	<html:text property="fechaS" size="5" maxlength="5" styleClass="input"/>
											    </td>
										    </tr>
									    </table>
									</td>
									<td id="idRangoFechas" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<tr>
											    <td class="user_data"><bean:message key="archigest.archivo.desde"/></td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaIniFormato" styleId="fechaIniFormato" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaIni')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaIniDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaIniD" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaIniMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaIniM" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaIniAValor" style="display:none;">
											    	<html:text property="fechaIniA" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaIniSValor" style="display:none;">
											    	<html:text property="fechaIniS" size="5" maxlength="5" styleClass="input"/>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td class="user_data"><bean:message key="archigest.archivo.hasta"/></td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaFinFormato" styleId="fechaFinFormato" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaFin')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaFinDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaFinD" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaFinMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaFinM" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaFinAValor" style="display:none;">
											    	<html:text property="fechaFinA" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaFinSValor" style="display:none;">
											    	<html:text property="fechaFinS" size="5" maxlength="5" styleClass="input"/>
											    </td>
										    </tr>
									    </table>
									</td>
								</tr>
							</table>
							<script>checkFechaComp(document.forms[0]);</script>
						</td>
					</tr>
					<c:set var="itemsProductores" value="${formBean.productoresSeleccionados}" scope="request"/>
					<tiles:insert page="campo_busqueda_productores_relacion.jsp" ignore="true"/>	
				</table>
			</div>
		</div> 
		</div> 
		<h2><span>&nbsp;</span></h2>
	</div> 
	</html:form>
</div> 


