<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mapping" mapping="/gestionRegistroConsultaAction" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="informeUsuarios" value="${sessionScope[appConstants.salas.INFORME_USUARIOS_KEY]}"/>

<c:set var="archivos" value="${sessionScope[appConstants.salas.LISTA_ARCHIVOS_KEY]}" />
<c:set var="tiposDoc" value="${sessionScope[appConstants.salas.LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY]}" />

<html:form action="/gestionRegistroConsultaAction" >
	<input type="hidden" name="method" value="listarBusquedaUsuarios">

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.registro.busqueda.usuario"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<script>
				function imprimirInforme(){
					var form = document.forms["<c:out value="${mapping.name}" />"];
					form.method.value= 'imprimirBusqueda';
					form.submit();
				}

				function buscar() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					form.submit();
				}

				function seleccionarUsuario() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					if (form.usuarioSeleccionado && elementSelected(form.usuarioSeleccionado)) {
						form.method.value = "seleccionarUsuario";
						form.submit();
					}else{
						alert("<bean:message key='archigest.archivo.salas.msgNoUsuarioSeleccionado'/>");
					}
				}

				function mostrarFechaSalida(){
					var areaFecha = xGetElementById('fechaSalida');
					var esRegistrado = xGetElementById('registrado');
					if (esRegistrado != null && esRegistrado.checked && areaFecha != null) {
						xDisplay(areaFecha, '');
					}
					else {
						xDisplay(areaFecha, 'none');
					}
				}
			</script>
			<table>
				<tr>
					<td nowrap>
						<c:choose>
							<c:when test="${informeUsuarios}">
								<a class="etiquetaAzul12Bold"
									href="javascript:imprimirInforme()">
									<html:img page="/pages/images/documentos/doc_pdf.gif"
										titleKey="archigest.archivo.imprimir"
										altKey="archigest.archivo.imprimir"
										styleClass="imgTextMiddle"/>&nbsp;
									<bean:message key="archigest.archivo.imprimir"/>
								</a>
							</c:when>
							<c:otherwise>
								<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
									<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.buscar"/>
								</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td width="10"></td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true"/>
					</td>
				</tr>
			</table>
		</tiles:put>

		<tiles:put name="boxContent" direct="true">
			<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.archivo"/>:</td>
						<td class="tdDatos">
							<html:select property="searchTokenArchivo" onchange="javascript:reload();">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="archivos" property="id" labelProperty="nombre"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.doc.identificativo"/>:</td>
						<td class="tdDatos">
							<html:select property="numeroDoc_like">
								<html:option key="archigest.archivo.igual" value="igual"/>
								<html:option key="archigest.archivo.contiene" value="contiene"/>
							</html:select>
							<html:text property='searchTokenNumDoc' styleClass="input" maxlength="32" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.salas.usuario.nombreApellidos"/>:</td>
						<td class="tdDatos">
							<html:text property="searchTokenNombreApellidos" styleClass="input" size="50" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.salas.mesa.asignada"/>:</td>
						<td class="tdDatos">
							<html:text property="searchTokenMesa" styleClass="input" size="50" />
						</td>
					</tr>

					<bean:define id="title" value="archigest.archivo.salas.registro.fechaEntrada" toScope="request"/>
					<bean:define id="fechaComp" value="fechaCompEntrada" toScope="request"/>
					<bean:define id="fechaFormato" value="fechaFormatoEntrada" toScope="request"/>
					<bean:define id="fechaD" value="fechaDEntrada" toScope="request"/>
					<bean:define id="fechaM" value="fechaMEntrada" toScope="request"/>
					<bean:define id="fechaA" value="fechaAEntrada" toScope="request"/>
					<bean:define id="fechaS" value="fechaSEntrada" toScope="request"/>

					<bean:define id="fechaIniFormato" value="fechaIniFormatoEntrada" toScope="request"/>
					<bean:define id="fechaIniD" value="fechaIniDEntrada" toScope="request"/>
					<bean:define id="fechaIniM" value="fechaIniMEntrada" toScope="request"/>
					<bean:define id="fechaIniA" value="fechaIniAEntrada" toScope="request"/>
					<bean:define id="fechaIniS" value="fechaIniSEntrada" toScope="request"/>

					<bean:define id="fechaFinFormato" value="fechaFinFormatoEntrada" toScope="request"/>
					<bean:define id="fechaFinD" value="fechaFinDEntrada" toScope="request"/>
					<bean:define id="fechaFinM" value="fechaFinMEntrada" toScope="request"/>
					<bean:define id="fechaFinA" value="fechaFinAEntrada" toScope="request"/>
					<bean:define id="fechaFinS" value="fechaFinSEntrada" toScope="request"/>

					<bean:define id="suffix" value="Entrada" toScope="request"/>
					<bean:define id="idTr" value="fechaEntrada" toScope="request"/>

					<tiles:insert name="salas.campo.fecha.registro" flush="true"/>

					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.salas.registro.cerrado"/>:</td>
						<td class="tdDatos">
							<html:checkbox styleId="registrado" styleClass="checkbox" property="registrado" onclick="javascript:mostrarFechaSalida();"/>
						</td>
					</tr>

					<bean:define id="title" value="archigest.archivo.salas.registro.fechaSalida" toScope="request"/>
					<bean:define id="fechaComp" value="fechaCompSalida" toScope="request"/>
					<bean:define id="fechaFormato" value="fechaFormatoSalida" toScope="request"/>
					<bean:define id="fechaD" value="fechaDSalida" toScope="request"/>
					<bean:define id="fechaM" value="fechaMSalida" toScope="request"/>
					<bean:define id="fechaA" value="fechaASalida" toScope="request"/>
					<bean:define id="fechaS" value="fechaSSalida" toScope="request"/>

					<bean:define id="fechaIniFormato" value="fechaIniFormatoSalida" toScope="request"/>
					<bean:define id="fechaIniD" value="fechaIniDSalida" toScope="request"/>
					<bean:define id="fechaIniM" value="fechaIniMSalida" toScope="request"/>
					<bean:define id="fechaIniA" value="fechaIniASalida" toScope="request"/>
					<bean:define id="fechaIniS" value="fechaIniSSalida" toScope="request"/>

					<bean:define id="fechaFinFormato" value="fechaFinFormatoSalida" toScope="request"/>
					<bean:define id="fechaFinD" value="fechaFinDSalida" toScope="request"/>
					<bean:define id="fechaFinM" value="fechaFinMSalida" toScope="request"/>
					<bean:define id="fechaFinA" value="fechaFinASalida" toScope="request"/>
					<bean:define id="fechaFinS" value="fechaFinSSalida" toScope="request"/>

					<bean:define id="suffix" value="Salida" toScope="request"/>
					<bean:define id="idTr" value="fechaSalida" toScope="request"/>

					<tiles:insert name="salas.campo.fecha.registro" flush="true"/>
				</table>
			</div>
		</tiles:put>
	</tiles:insert>
</html:form>
<script language="Javascript">
	mostrarFechaSalida();
</script>