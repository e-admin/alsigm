<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="listas" value="${requestScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}"/>
<bean:struts id="actionMapping" mapping="/descripcionAut" />

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function clean()
	{
		document.forms['<c:out value="${actionMapping.name}" />'].nombre.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].texto.value = "";

		document.forms['<c:out value="${actionMapping.name}" />'].fechaComp.value = "=";
		document.forms['<c:out value="${actionMapping.name}" />'].fechaFormato.value = "AAAA";
		document.forms['<c:out value="${actionMapping.name}" />'].fechaD.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].fechaM.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].fechaA.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].fechaS.value = "";
		checkFechaComp(document.forms['<c:out value="${actionMapping.name}" />']);

		document.forms['<c:out value="${actionMapping.name}" />'].calificador.value = "";

		document.forms['<c:out value="${actionMapping.name}" />'].numeroComp.value = "=";
		document.forms['<c:out value="${actionMapping.name}" />'].numero.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].tipoMedida.value = "";
		document.forms['<c:out value="${actionMapping.name}" />'].unidadMedida.value = "";

		deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].listasDescriptoras);
	}

	function close(){
		<c:url var="closeURL" value="/action/descripcionAut">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${closeURL}" escapeXml="false"/>';
	}

	function buscarElementos(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgDescripcionesAutoridades"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms['<c:out value="${actionMapping.name}" />'].submit();
	}
</script>

<div id="contenedor_ficha">

	<div class="ficha">
		<html:form action="/descripcionAut?method=busq">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.busqueda.aut.form.caption"/></td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:buscarElementos();"
										><input type="image"
												src="<%=request.getContextPath()%>/pages/images/buscar.gif"
										        border="0"
										        alt="<bean:message key="archigest.archivo.buscar"/>"
										        title="<bean:message key="archigest.archivo.buscar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:clean()"
										><html:img page="/pages/images/clear0.gif"
										        border="0"
										        altKey="archigest.archivo.limpiar"
										        titleKey="archigest.archivo.limpiar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td><a class="etiquetaAzul12Bold"
										   href="javascript:close()"
										><html:img page="/pages/images/close.gif"
										        border="0"
										        altKey="archigest.archivo.cerrar"
										        titleKey="archigest.archivo.cerrar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors /></div>

			<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.nombre"/>:&nbsp;</td>
						<td class="tdDatosFicha"><html:text property="nombre" size="50" styleClass="input99"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.texto"/>:&nbsp;</td>
						<td class="tdDatosFicha"><html:textarea property="texto"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:select property="descripcion">
								<html:option key="archigest.archivo.busqueda.aut.form.descripcion.todas" value=""/>
								<html:option key="archigest.archivo.busqueda.aut.form.descripcion.conDescripcion" value="S"/>
								<html:option key="archigest.archivo.busqueda.aut.form.descripcion.sinDescripcion" value="N"/>
							</html:select>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.fecha"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<html:select styleId="fechaComp" property="fechaComp" onchange="javascript:checkFechaComp(this.form)">
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
													<html:select property="fechaFormato" styleId="fechaFormato" onchange="javascript:checkFechaFormato(this, 'idFecha')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaD" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaM" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaAValor" style="display:none;">
											    	<html:text property="fechaA" size="4" maxlength="4"/>
											    </td>
											    <td id="idFechaSValor" style="display:none;">
											    	<html:text property="fechaS" size="5" maxlength="5"/>
											    </td>
										    </tr>
									    </table>
									</td>
									<td id="idRangoFechas" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<tr>
											    <td class="user_data"><bean:message key="archigest.archivo.desde"/>:</td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaIniFormato" styleId="fechaIniFormato" onchange="javascript:checkFechaFormato(this, 'idFechaIni')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaIniDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaIniD" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaIniMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaIniM" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaIniAValor" style="display:none;">
											    	<html:text property="fechaIniA" size="4" maxlength="4"/>
											    </td>
											    <td id="idFechaIniSValor" style="display:none;">
											    	<html:text property="fechaIniS" size="5" maxlength="5"/>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td class="user_data"><bean:message key="archigest.archivo.hasta"/>:</td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaFinFormato" styleId="fechaFinFormato" onchange="javascript:checkFechaFormato(this, 'idFechaFin')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaFinDValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaFinD" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaFinMValor" style="display:none;" nowrap="nowrap">
											    	<html:text property="fechaFinM" size="2" maxlength="2"/>-
											    </td>
											    <td id="idFechaFinAValor" style="display:none;">
											    	<html:text property="fechaFinA" size="4" maxlength="4"/>
											    </td>
											    <td id="idFechaFinSValor" style="display:none;">
											    	<html:text property="fechaFinS" size="5" maxlength="5"/>
											    </td>
										    </tr>
									    </table>
									</td>
								</tr>
							</table>
							<script>checkFechaComp(document.forms['<c:out value="${actionMapping.name}" />']);</script>
						</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.calificadores"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:select property="calificador">
								<html:option key="archigest.archivo.busqueda.aut.form.calificadores.value.todos" value=""/>
								<html:option key="archivo.calificador.ant" value="ant"/>
								<html:option key="archivo.calificador.pos" value="pos"/>
								<html:option key="archivo.calificador.apr" value="apr"/>
								<html:option key="archivo.calificador.con" value="con"/>
								<html:option key="archivo.calificador.sup" value="sup"/>
								<html:option key="archivo.calificador.sic" value="sic"/>
								<html:option key="archivo.calificador.a.C." value="a.C."/>
								<html:option key="archivo.calificador.p.m." value="p.m."/>
								<html:option key="archivo.calificador.s.m." value="s.m."/>
								<html:option key="archivo.calificador.p.t." value="p.t."/>
								<html:option key="archivo.calificador.s.t." value="s.t."/>
								<html:option key="archivo.calificador.u.t." value="u.t."/>
								<html:option key="archivo.calificador.c." value="c."/>
								<html:option key="archivo.calificador.p." value="p."/>
								<html:option key="archivo.calificador.m." value="m."/>
								<html:option key="archivo.calificador.f." value="f."/>
								<html:option key="archivo.calificador.sf" value="sf"/>
							</html:select>
							&nbsp;
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.numero"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<html:select property="numeroComp">
											<html:option key="archigest.archivo.simbolo.igual" value="="/>
											<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
											<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
											<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
											<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
											<html:option key="archigest.archivo.simbolo.diferente" value="!="/>
									    </html:select>
									</td>
								    <td width="10">&nbsp;</td>
									<td><html:text property="numero" size="20"/></td>
								</tr>
							</table>
							<table>
								<tr>
									<td class="tdDatos"><bean:message key="archigest.archivo.busqueda.aut.form.tipoMedida"/>:</td>
									<td class="tdDatos" width="10">&nbsp;</td>
									<td class="tdDatos">
										<html:select property="tipoMedida">
											<html:option key="" value=""/>
											<html:option key="archigest.archivo.medida.longitud" value="1"/>
											<html:option key="archigest.archivo.medida.peso" value="2"/>
											<html:option key="archigest.archivo.medida.volumen" value="3"/>
									    </html:select>
									</td>
								</tr>
								<tr>
									<td class="tdDatos"><bean:message key="archigest.archivo.busqueda.aut.form.unidadMedida"/>:</td>
									<td class="tdDatos" width="10">&nbsp;</td>
									<td class="tdDatos"><html:text property="unidadMedida" size="10" maxlength="16"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.busqueda.aut.form.listasDescriptoras"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<logic:present scope="page" name="listas">
								<table class="formulario">
									<tr>
										<td align="right">
											<a class="etiquetaAzul12Bold"
											   href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].listasDescriptoras);"
								 			><html:img page="/pages/images/checked.gif"
												    border="0"
												    altKey="archigest.archivo.selTodas"
												    titleKey="archigest.archivo.selTodas"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodas"/></a>
											&nbsp;
											<a class="etiquetaAzul12Bold"
											   href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].listasDescriptoras);"
								 			><html:img page="/pages/images/check.gif"
												    border="0"
												    altKey="archigest.archivo.quitarSel"
												    titleKey="archigest.archivo.quitarSel"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
											&nbsp;&nbsp;
									   </td>
									</tr>
								</table>
								<table cellpadding="0" cellspacing="0">
								<logic:iterate id="lista" scope="page" name="listas" indexId="rowNum">
								<% if ((rowNum.intValue() % 3) == 0) out.println("<tr>"); %>
									<td><html:multibox style="border:0" property="listasDescriptoras"><bean:write name="lista" property="id"/></html:multibox></td>
									<td class="tdDatos"><bean:write name="lista" property="nombre"/></td>
									<td width="10">&nbsp;</td>
								<% if ((rowNum.intValue() % 3) == 2) out.println("<tr>"); %>
								</logic:iterate>
								</table>
							</logic:present>
						</td>
					</tr>
				</table>
			</div>

		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
		</html:form>
	</div> <%-- ficha --%>

</div> <%-- contenedor_ficha --%>


