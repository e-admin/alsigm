<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="actionMapping" mapping="/gestionValoracion" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion.editarValoracion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
				<script>
					function goOn() {
						document.forms[0].submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
					<html:img page="/pages/images/Ok_Si.gif" 
					          altKey="archigest.archivo.aceptar" 
					          titleKey="archigest.archivo.aceptar" 
					          styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.aceptar"/>&nbsp;
				</a>
				</td>
				<td width="10px">&nbsp;</div>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionValoracion">
	<input type="hidden" id="method" name="method" value="guardarInfoValoracion">
	<html:hidden property="titulo"/>
	<html:hidden property="id"/>
	<html:hidden property="estado"/>
	<html:hidden property="fechaEstado"/>
	
	<div class="bloque">	
	<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
	<TR>
		<TD class="tdTitulo" width="350px">
			<bean:message key="archigest.archivo.valoracion.titulo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${valoracionForm.titulo}" />
		</TD>
	</TR>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.ordenacionPrimerNivel"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:set var="tiposOrdenacionPrimerNivel" value="${requestScope[appConstants.valoracion.LISTA_ORDENACIONES_PRIMER_NIVEL_KEY]}"/>
			<html:select property="ordenacionSerie1" styleClass="input">
				<html:options collection="tiposOrdenacionPrimerNivel" property="value" labelProperty="label" />
			</html:select>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.ordenacionSegundoNivel"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:set var="tiposOrdenacionSegundoNivel" value="${requestScope[appConstants.valoracion.LISTA_ORDENACIONES_SEGUNDO_NIVEL_KEY]}"/>
			<html:select property="ordenacionSerie2" styleClass="input">
				<html:option key="archigest.archivo.valoracion.sinOrden" value="0"/>
				<html:options collection="tiposOrdenacionSegundoNivel" property="value" labelProperty="label" />
			</html:select>			
		</TD>
	</TR>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" style="vertical-align:top;">
			<bean:message key="archigest.archivo.valoracion.documentosRecopilatorios"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="documentosRecopilatorios" rows="3" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
		</TD>
	</TR>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.valorAdministrativo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<script>
				function checkValoresAdministrativos() {
					var combo = document.forms["<c:out value="${actionMapping.name}" />"].tipoValorAdministrativo;
					var value = combo[combo.selectedIndex].value;
					if (value == 1) {
						xDisplay('trPeriodoVigenciaAdministrativa', 'block');
						if(!document.all){
							xDisplay('trPeriodoVigenciaAdministrativa', 'table-row');	
						}
					} else {
						xDisplay('trPeriodoVigenciaAdministrativa', 'none');
					}
				}
			</script>
			<c:set var="valoresAdministrativos" value="${requestScope[appConstants.valoracion.LISTA_VALORES_ADMINISTRATIVOS_KEY]}"/>
			<html:select property="tipoValorAdministrativo" styleClass="input" onchange="javascript:checkValoresAdministrativos()">
				<option value="">--</option>
				<html:options collection="valoresAdministrativos" property="value" labelProperty="label" />
			</html:select>
		</TD>
	</TR>
	<TR id="trPeriodoVigenciaAdministrativa">
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.periodoVigenciaAdm"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:text property="anosVigenciaAdministrativa" size="4"/>&nbsp;
			<span class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.anios"/></span>
		</TD>
	</TR>
		<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="valorAdministrativo" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
		</TD>
	</TR>
	<script>checkValoresAdministrativos();</script>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.valorLegal"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<script>
				function checkValoresLegales() {
					var combo = document.forms["<c:out value="${actionMapping.name}" />"].tipoValorLegal;
					var value = combo[combo.selectedIndex].value;
					if (value == 1) {
						xDisplay('trPeriodoVigenciaLegal', 'block');
						if(!document.all){
							xDisplay('trPeriodoVigenciaLegal', 'table-row');	
						}
					} else {
						xDisplay('trPeriodoVigenciaLegal', 'none');
					}
				}
			</script>
			<c:set var="valoresLegales" value="${requestScope[appConstants.valoracion.LISTA_VALORES_LEGALES_KEY]}"/>
			<html:select property="tipoValorLegal" styleClass="input" onchange="javascript:checkValoresLegales()">
				<option value="">--</option>
				<html:options collection="valoresLegales" property="value" labelProperty="label" />
			</html:select>
		</TD>
	</TR>
	<TR id="trPeriodoVigenciaLegal">
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.periodoVigenciaLegal"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:text property="anosVigenciaLegal" size="4"/>&nbsp;
			<span class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.anios"/></span>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="valorLegal" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>

		</TD>
	</TR>
	<script>checkValoresLegales();</script>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.valorInformativo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<script>
				function valoracionConValorInformativo() {
				
					var radio = document.forms["<c:out value="${actionMapping.name}" />"].tipoValorInformativo;
					if (radio[0].checked) {
						xDisplay('valorInformativo', 'none');
						xDisplay('valoresInformativos', 'block');
					} else {
						xDisplay('valorInformativo', 'block');
						xDisplay('valoresInformativos', 'none');
						if(!document.all){
							xDisplay('valorInformativo', 'table-cell');
						}
					}
				}
			</script>
			<table>
				<tr>
					<td><html:radio property="tipoValorInformativo" 
						onclick="javascript:valoracionConValorInformativo()" 
						styleClass="radio" value="1"/></td>
					<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.si"/></td>
					<td><html:radio property="tipoValorInformativo" 
						onclick="javascript:valoracionConValorInformativo()" 
						styleClass="radio" value="2"/></td>
					<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.no" /></td>
				</tr>
			</table>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<span id="labelJustificacionValorInformativo"><bean:message key="archigest.archivo.valoracion.justificacion"/></span>
			<span id="labelValoresInformativos"><bean:message key="archigest.archivo.valoracion.valoresInformativos"/></span>
			:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="valorInformativo" cols="50" rows="2" styleId="justificacionValorInformativo" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>


			<table id="valoresInformativos" class="formulario">
				<tr>
					<TD class="tdTitulo" width="150">
						<bean:message key="archigest.archivo.valoracion.valorCientifico"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<table>
							<tr>
								<td><html:radio property="tipoValorCientifico" 
									styleClass="radio" value="1" /></td>
								<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.si"/></td>
								<td><html:radio property="tipoValorCientifico" 
									styleClass="radio" value="2" /></td>
								<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.no" /></td>
							</tr>
						</table>
						<html:textarea property="valorCientifico" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
					</TD>
				</tr>
				<tr>
					<TD class="tdTitulo" width="150">
						<bean:message key="archigest.archivo.valoracion.valorTecnologico"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<table>
							<tr>
								<td><html:radio property="tipoValorTecnologico" 
									styleClass="radio" value="1" /></td>
								<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.si" /></td>
								<td><html:radio property="tipoValorTecnologico" 
									 styleClass="radio" value="2" /></td>
								<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.no" /></td>
							</tr>
						</table>
						<html:textarea property="valorTecnologico" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
					</TD>
				</tr>
				<tr>
					<TD class="td2Titulop10" width="150">
						<bean:message key="archigest.archivo.valoracion.valorCultural"/>:&nbsp;
					</TD>
					<TD class="td2Datosp10" >
						<table><tr>
							<td><html:radio property="tipoValorCultural" 
								styleClass="radio" value="1" /></td>
							<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.si"/></td>
							<td><html:radio property="tipoValorCultural" 
								styleClass="radio" value="2" /></td>
							<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.no"/></td>
						</tr></table>
						<html:textarea property="valorCultural" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
					</TD>
				</tr>

			</table>
		</TD>
	</TR>
	<script>valoracionConValorInformativo();</script>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.regimenAcceso"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<script>
				function checkRegimenAcceso() {
					var combo = document.forms["<c:out value="${actionMapping.name}" />"].tipoRegimenAcceso;
					var value = combo[combo.selectedIndex].value;
					if (value == 2) {
						xDisplay('trPeriodoRegimenAcceso', 'block');
						xDisplay('trTipoRestringidoTemporal', 'block');
						if(!document.all){
							xDisplay('trPeriodoRegimenAcceso', 'table-row');
							xDisplay('trTipoRestringidoTemporal', 'table-row');
						}
						<c:if test="${valoracionForm.tipoRegimenAccesoTemporal == null || valoracionForm.tipoRegimenAccesoTemporal==0}">
							document.getElementById("temporalTotal").checked="true";
						</c:if>
					} else {
						xDisplay('trPeriodoRegimenAcceso', 'none');
						xDisplay('trTipoRestringidoTemporal', 'none');
					}
				}
			</script>
			<html:select property="tipoRegimenAcceso" styleClass="input" onchange="javascript:checkRegimenAcceso()">
				<option value="">--</option>
				<html:option key="archigest.archivo.valoracion.regimenAcceso3" value="3"/>
				<html:option key="archigest.archivo.valoracion.regimenAcceso2" value="2"/>
				<html:option key="archigest.archivo.valoracion.regimenAcceso1" value="1"/>
			</html:select>
		</TD>
	</TR>
	<TR id="trPeriodoRegimenAcceso">
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.periodoRegimenAcceso"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:text property="anosRegimenAcceso" size="4"/>&nbsp;
			<span class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.anios"/></span>
		</TD>
	</TR>
	<TR id="trTipoRestringidoTemporal">
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.cf.tipo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<table><tr>
				<td><html:radio property="tipoRegimenAccesoTemporal" 
					styleClass="radio" value="21" /></td>
				<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.valoracion.regimenAcceso.temporalParcial"/></td>
				<td><html:radio property="tipoRegimenAccesoTemporal" styleId="temporalTotal"
					styleClass="radio" value="22" /></td>
				<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.valoracion.regimenAcceso.temporalTotal"/></td>
			</tr></table>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="regimenAcceso" cols="50" rows="2" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/>
		</TD>
	</TR>
	<script>checkRegimenAcceso();</script>
	<TR><TD>&nbsp;</TD></TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.valoresDictamen"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<script>
				function checkValorDictamen() {
					var combo = document.forms["<c:out value="${actionMapping.name}" />"].valorDictamen;
					var value = combo[combo.selectedIndex].value;
					if (value == 2) {
						xDisplay('trTecnicasMuestreo', 'block');
						if(!document.all){
							xDisplay('trTecnicasMuestreo', 'table-row');
						}
					} else {
						xDisplay('trTecnicasMuestreo', 'none');
					}
				}
			</script>
			<c:set var="valoresDictamen" value="${requestScope[appConstants.valoracion.LISTA_VALORES_DICTAMEN_KEY]}"/>
			<html:select property="valorDictamen" styleClass="input" onchange="javascript:checkValorDictamen()">
				<option value="">--</option>
				<html:options collection="valoresDictamen" property="value" labelProperty="label" />
			</html:select>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<html:textarea property="valorDictamenValue" cols="50" rows="2" />
		</TD>
	</TR>
	<TR id="trTecnicasMuestreo">
		<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.valoracion.tecnicasMuestreo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:set var="tecnicasMuestreo" value="${requestScope[appConstants.valoracion.LISTA_TECNICAS_MUESTREO_KEY]}"/>
			<html:select property="tecnicaMuestreo" styleClass="input">
				<html:options collection="tecnicasMuestreo" property="value" labelProperty="label" />
			</html:select>
		</TD>
	</TR>
	<script>checkValorDictamen();</script>
	<c:set var="nNivelesArchivo" value="0"/>
	<c:set var="listaNivelesArchivo" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_ARCHIVO]}"/>
	<c:forEach var="nivel" items="${listaNivelesArchivo}" varStatus="num">
		<c:set var="nNivelesArchivo" value="${nNivelesArchivo+1}"/>
	</c:forEach>
	<c:if test="${nNivelesArchivo>1}">
		<TR id="trPlazosCambioFase">
			<TD class="tdTitulo" >
				<bean:message key="archigest.valoraciones.plazos.transferencias"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<script>	    
				    elemento = new ElementInfo();
				    elemento.id = "3";
			      	elemento.nestedElements[elemento.nestedElements.length] = "nivelArchivoOrigen";
			      	elemento.nestedElements[elemento.nestedElements.length] = "nivelArchivoDestino";
					elemento.nestedElements[elemento.nestedElements.length] = "plazo";
				    elementsInfo.addTableElementInfo(elemento);
				</script>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td><a class="etiquetaAzul12Bold" href="javascript:addTableRow('3','TABLA');">
							<img class="imgTextBottom" title="A&ntilde;adir" alt="A&ntilde;adir" border="0" src="../images/plus.gif"></img>&nbsp;A&ntilde;adir</a>
						</td>
						<td width="10">&nbsp;</td>
						<td><a class="etiquetaAzul12Bold" href="javascript:removeTableRows('3');">
							<img class="imgTextBottom" title="Eliminar" alt="Eliminar" border="0" src="../images/minus.gif"></img>&nbsp;Eliminar</a>
						</td>
					</tr>
				</table>
				<script>
					//lista con ids, ordenes. indices son los ordenes
					var idsNivelesArchivo=new Array();
					var nombresNivelesArchivo=new Array();
					<c:forEach var="nivel" items="${listaNivelesArchivo}" varStatus="num">
						idsNivelesArchivo[<c:out value="${nivel.orden-1}"/>]='<c:out value="${nivel.id}"/>';
						nombresNivelesArchivo[<c:out value="${nivel.orden-1}"/>]='<c:out value="${nivel.nombre}"/>';
					</c:forEach>

					function loadNivelesDestino(fila){
						var ordenSeleccionado=-1;
						var tabla=document.getElementById("tabla3");
						var tbody=tabla.getElementsByTagName("tbody")[0];
						var tr=tbody.getElementsByTagName("tr")[fila-1];
						var tdOrigen=tr.getElementsByTagName("td")[1];
						var tdDestino=tr.getElementsByTagName("td")[2];
						var selectOrigen=tdOrigen.getElementsByTagName("select")[0];
						var selectDestino=tdDestino.getElementsByTagName("select")[0];
						
						for(i=0;i<idsNivelesArchivo.length;i++){
							if(idsNivelesArchivo[i]==selectOrigen.value){
								 ordenSeleccionado=i;
								 break;
							}		
						}

						var optionsSelectDestino=selectDestino.getElementsByTagName("option");
						for(i=optionsSelectDestino.length-1;i>=0;i--) selectDestino.remove(optionsSelectDestino[i]);

						var objOption = document.createElement("option");
						objOption.text = ""
						objOption.value = ""
						selectDestino.appendChild(objOption);
						for(i=ordenSeleccionado+1;i<idsNivelesArchivo.length;i++){
							objOption = document.createElement("option");
							objOption.text = nombresNivelesArchivo[i];
							objOption.value = idsNivelesArchivo[i];
							if(document.all && !window.opera){selectDestino.add(objOption);}
							else{selectDestino.add(objOption, null); }
							//selectDestino.appendChild(objOption);
						}
					}
					//al cambiar el combo, tomar el id seleccionado -> obtener orden
					//vaciar de options el combo, y crear nuevas options a partir del orden anterior
				</script>
				<div id="divTblValoracion">
				<table style="width:60%" id="tabla3" class="tablaFicha">
					<thead>
						<tr>
							<th><img alt="Eliminar" src="../images/delete.gif"></th>
							<script>
							    elemento = new ElementInfo();
							    elemento.id = "nivelArchivoOrigen";
							    elemento.type = "1";
							    elemento.style = "";
							    elemento.mandatory = "N";
							    elemento.initialValue = "";
							    elemento.defaultOptionValue = "";
							    var counter = null;
							    <c:forEach var="nivel" items="${listaNivelesArchivo}">
						  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.id}"/>";
						  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.nombre}"/>";
							    </c:forEach>		    
							    elemento.defaultFormatValue = "";
							    elemento.refType = "";
							    elemento.defaultTipoMedida = "";
							    elemento.defaultUnidadMedida = "";
							    elemento.mostrarTipoMedida = "";
							    elemento.mostrarUnidadMedida = "";
							    elemento.validationTable= "";
							    elemento.editable="S";
							    elemento.optionsTextId=true;
							    elemento.usarMismoIdYNombre=true;
							    elemento.onChange="loadNivelesDestino";
							    elementsInfo.addFieldElementInfo(elemento);
							</script>
							<th width="" style="width:33.333333333333336%"><bean:message key="archigest.valoraciones.nivel.origen"/></th>
							<script>
							    elemento = new ElementInfo();
							    elemento.id = "nivelArchivoDestino";
							    elemento.type = "1";
							    elemento.style = "";
							    elemento.mandatory = "N";
							    elemento.initialValue = "";
							    elemento.defaultOptionValue = "";
							    var counter = null;
							    <c:forEach var="nivel" items="${listaNivelesArchivo}">
						  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.id}"/>";
						  	        elemento.options[elemento.options.length] = "<c:out value="${nivel.nombre}"/>";
							    </c:forEach>		    
							    elemento.defaultFormatValue = "";
							    elemento.refType = "";
							    elemento.defaultTipoMedida = "";
							    elemento.defaultUnidadMedida = "";
							    elemento.mostrarTipoMedida = "";
							    elemento.mostrarUnidadMedida = "";
							    elemento.validationTable= "";
							    elemento.editable="S";
							    elemento.optionsTextId=true;
							    elemento.usarMismoIdYNombre=true;
							    elementsInfo.addFieldElementInfo(elemento);
							</script>
							<th width="" style="width:33.333333333333336%"><bean:message key="archigest.valoraciones.nivel.destino"/></th>
							<script>
							    elemento = new ElementInfo();
							    elemento.id = "plazo";
							    elemento.type = "1";
							    elemento.style = "";
							    elemento.mandatory = "N";
							    elemento.initialValue = "";
							    elemento.defaultOptionValue = "";
							    var counter = null;		    
							    elemento.defaultFormatValue = "";
							    elemento.refType = "";
							    elemento.defaultTipoMedida = "";
							    elemento.defaultUnidadMedida = "";
							    elemento.mostrarTipoMedida = "";
							    elemento.mostrarUnidadMedida = "";
							    elemento.validationTable= "";
							    elemento.editable="S";
							    elementsInfo.addFieldElementInfo(elemento);
							</script>
							<th width="" style="width:33.333333333333336%"><bean:message key="archigest.valoraciones.plazo.años"/></th>
						</tr>
					</thead>
					<tbody>
							<c:if test="${not empty valoracionForm.campo_plazo}">
								<c:forEach var="plazo" items="${valoracionForm.campo_plazo}" varStatus="status">
									<c:set var="ordenOrigen" value="0"/>
									<tr valign="top" id="tr_3_<c:out value="${status.index+1}"/>" <c:if test="${status.index%2==0}">class='odd'</c:if><c:if test="${status.index%2==1}">class='even'</c:if> >
										<td nowrap><input onclick='javascript:checkIfSelectedToBeRemoved(this, "tr_3_<c:out value="${status.index+1}"/>");' name="eliminar_tabla_3" class="checkbox" type="checkbox" value="0" style="border: 0pt none ;"/></td>
										<td style="width:33.333333333333336%">
											<select name="campo_nivelArchivoOrigen" onchange="loadNivelesDestino(<c:out value="${status.index+1}"/>);">
												<option value=""></option>
												<c:forEach var="nivel" items="${pageScope.listaNivelesArchivo}">
													<option value="<c:out value="${nivel.id}"/>" <c:if test="${nivel.id==valoracionForm.campo_nivelArchivoOrigen[status.index]}">selected='selected' <c:set var="ordenOrigen" value="${nivel.orden}"/></c:if> ><c:out value="${nivel.nombre}"/></option>
												</c:forEach>
											</select>
										</td>
										<td style="width:33.333333333333336%">
											<select name="campo_nivelArchivoDestino" >
												<option value=""></option>
												<c:forEach var="nivel" items="${pageScope.listaNivelesArchivo}">
													<c:if test="${nivel.orden > ordenOrigen}">
													<option value="<c:out value="${nivel.id}"/>" <c:if test="${nivel.id==valoracionForm.campo_nivelArchivoDestino[status.index]}">selected='selected'</c:if> ><c:out value="${nivel.nombre}"/></option>
													</c:if>
												</c:forEach>
											</select>
										</td>
										<td style="width:33.333333333333336%">
											<input type="text" name="campo_plazo" id="campo_plazo_<c:out value="${status.index+1}"/>" size="7" maxlength="5" value="<c:out value="${plazo}"/>"/>
										</td>
									</tr>
								</c:forEach>
						</c:if>
					</tbody>
				</table>
				</div>
			</TD>
		</TR>
	</c:if>
	</TABLE>

	</div>
	

	<html:hidden property="id"/>
	<html:hidden property="idSerie"/>
	<html:hidden property="estado"/>
	<html:hidden property="idUsuarioGestor"/>
	</html:form>
	</tiles:put>
</tiles:insert>
