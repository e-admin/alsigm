<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="fichas" value="${requestScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
<c:set var="campos" value="${requestScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>

<c:if test="${fichas == null}">
	<c:set var="fichas" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_KEY]}"/>
</c:if>
<c:if test="${campos == null}">
	<c:set var="campos" value="${sessionScope[appConstants.fondos.LISTA_CAMPOS_FICHA_KEY]}"/>
</c:if>

<script language="JavaScript1.2" type="text/JavaScript">
	function buscarElementos()
	{
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgFondos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		form.submit();
	}
	function cambioFicha()
	{
		recargar();
	}

	function recargar(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		cleanCondicionesAvanzadas('<c:out value="${actionMappingName}" />');
		form.method.value = '<c:out value="${methodLoadFicha}" />';
		form.submit();
	}


	function hideAllDivs()
	{
		showSelects();
		xHide("busqDescriptores");
	}
	function showBusqDescDiv()
	{
		var busqDescriptores = xGetElementById('busqDescriptores');
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		xHide(busqDescriptores);
		xDisplay(busqDescriptores, 'block');
		var top = (clientHeight - xHeight(busqDescriptores))/2;
		var left = (clientWidth - xWidth(busqDescriptores))/2;
		xMoveTo(busqDescriptores, left, top);
		hideSelects();
		xShow(busqDescriptores);
	}
	function selectDescriptor(id, nombre)
	{
		hideAllDivs();
		if (currentIndex != null)
		{
			var tabla = document.getElementById("tblCondiciones");
			var tbody = tabla.getElementsByTagName("tbody").item(0);
			var tr = tbody.rows[currentIndex-1];
			var tdValor1 = tr.cells[5];
			var inputs = tdValor1.getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++)
			{
				if (inputs[i].name == "valor1")
					inputs[i].value = id;
				else if (inputs[i].name == "nombreDesc")
					inputs[i].value = nombre;
			}
			currentIndex = null;
		}
	}
	function selectElement(id, nombre)
	{
		selectDescriptor(id, nombre);
	}

	function showSearchObjectForm(index)
	{
		var selects = document.getElementsByName("campo");
		if (selects && index && index <= selects.length) {
			var select = selects[index - 1];
			var idCampo = select[select.selectedIndex].value;
			var campo = null;
			for (var i = 0; campo == null && i < CAMPOS.length; i++) {
				if (idCampo == CAMPOS[i][0]) {
					campo = CAMPOS[i];
				}
			}

			if (campo != null) {
				var form = document.forms['<c:out value="${actionMappingName}" />'];
				var frame = document.getElementById("frmSeleccionDescriptor");
				if (frame) {
				    if (campo[3] == 1 || campo[3] == 3) {
				        frame.src = "<c:url value="/action"/>/descriptores?method=formBusqDescriptor&tipo=" + campo[3] + "&idsListas=" + campo[4];
				    } else if (campo[3] == 2) {
				        frame.src = "<c:url value="/action"/>/elementos?method=formBusquedaElem";
			        }
				}
				showBusqDescDiv();
				currentIndex = index;
			}
		}
	}
	var currentIndex = null;


	function recargarListaFichas(){
		recargar();
	}
</script>

<html:hidden property="contadorDetallesAvanzados" styleId="contadorDetallesAvanzados" />

	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" width="<c:out value="${sizeCampo}"/>">
			<bean:message key="archigest.archivo.cf.ficha"/>:&nbsp;
		</td>
		<td class="<c:out value="${classTdCampo}"/>" style="border-bottom: 0;">
			<html:select property="idFicha" onchange="javascript:cambioFicha()">
				<html:option value="0"><bean:message key="archigest.archivo.todas"/></html:option>
				<html:optionsCollection name="fichas" label="value" value="key"/>
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="<c:out value="${classTdTituloCampoSinBorde}"/>" width="<c:out value="${sizeCampo}"/>">
			<bean:message key="archigest.archivo.cf.condiciones"/>:&nbsp;
		</td>
		<td align="right">
			<c:choose>
			<c:when test="${empty campos}">
				&nbsp;
			</c:when>
			<c:otherwise>
				<a class="btLiso" href="javascript:insertTableRow()"><bean:message key="archigest.archivo.insertarAntes"/></a>
				&nbsp;&nbsp;
				<a class="btLiso" href="javascript:addTableRow()"><bean:message key="archigest.archivo.anadir"/></a>
				&nbsp;&nbsp;
				<a class="btLiso" href="javascript:removeTableRows()"><bean:message key="archigest.archivo.eliminar"/></a>
				&nbsp;&nbsp;
			</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<tr>
		<td class="<c:out value="${classTdCampo}"/>" id="camposFicha" style="border-bottom: 0;" colspan="2">
			<c:choose>
				<c:when test="${empty campos}">
					<bean:message key="archigest.archivo.cf.msg.busqueda.emptyCampos"/>
				</c:when>
				<c:otherwise>
					<script language="JavaScript1.2" src="<c:url value="/js/advsearch.js" />" type="text/JavaScript"></script>
					<script>
						var BOOLEANOS = [
							['Y', '<bean:message key="archigest.archivo.booleano.and" />', true],
							['O', '<bean:message key="archigest.archivo.booleano.or" />', false],
							['Y NO', '<bean:message key="archigest.archivo.booleano.andNot" />', false]
						];

						var OPERADORES = [
							['=', '<bean:message key="archigest.archivo.simbolo.igual" />'],
							['<', '<bean:message key="archigest.archivo.simbolo.menor" />'],
							['<=', '<bean:message key="archigest.archivo.simbolo.menoroigual" />'],
							['>', '<bean:message key="archigest.archivo.simbolo.mayor" />'],
							['>=', '<bean:message key="archigest.archivo.simbolo.mayoroigual" />'],
							['[..]', '<bean:message key="archigest.archivo.simbolo.rango" />'],
							['QC', '<bean:message key="archigest.archivo.simbolo.quecontenga" />'],
							['QCC', '<bean:message key="archigest.archivo.simbolo.quecomiencecon" />'],
							['QCN', '<bean:message key="archigest.archivo.simbolo.quecontengan" />'],
							['EX', '<bean:message key="archigest.archivo.simbolo.exacta" />']
						];

						var CAMPOS = [
						<c:forEach var="campo" items="${campos}">
							['<c:out value="${campo.id}"/>', '<c:out value="${campo.nombre}"/>', <c:out value="${campo.tipo}"/>, <c:out value="${campo.tipoReferencia}"/>, '<c:out value="${campo.idsListasDescriptoras}"/>'],
						</c:forEach>
							['-100', '<bean:message key="archigest.archivo.campo.descriptor"/>', 5, 1, '']
						];

						var TIPOSFECHA =[
							['DDMMAAAA', '<bean:message key="archigest.archivo.formato.DDMMAAAA" />'],
							['MMAAAA', '<bean:message key="archigest.archivo.formato.MMAAAA" />'],
							['AAAA', '<bean:message key="archigest.archivo.formato.AAAA" />'],
							['S', '<bean:message key="archigest.archivo.formato.S" />']
						];
					</script>

					<div id="divTbl">
					<table id="tblCondiciones" class="its" style="width:98%;table-layout:auto;">
						<thead>
							<tr>
								<th width="20px">&nbsp;</th>
								<th width="10px"><bean:message key="archigest.archivo.cf.busqueda.union"/></th>
								<th width="10px"><bean:message key="archigest.archivo.cf.busqueda.parentesisAbierto"/></th>
								<th><bean:message key="archigest.archivo.campo"/></th>
								<th width="50px"><bean:message key="archigest.archivo.cf.busqueda.operador"/></th>
								<th width="120px"><bean:message key="archigest.archivo.cf.busqueda.valor1"/></th>
								<th width="120px"><bean:message key="archigest.archivo.cf.busqueda.valor2"/></th>
								<th width="40px"><bean:message key="archigest.archivo.cf.busqueda.parentesisCerrado"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty paramValues.campo}">

									<tr valign="top">
										<td nowrap>
											<input type="checkbox" name="check" style="border:0;" value="0"/>
											<input type='hidden' size='1' name='formatoFechaSel1' id='formatoFechaSel1_0' value='0'/>
											<input type='hidden' size='1' name='formatoFechaSel2' id='formatoFechaSel2_0' value='0'/>
										</td>
										<td>
											<select name="booleano" id="booleano_0">
												<option value=""></option>
												<option value="Y"><bean:message key="archigest.archivo.booleano.and"/></option>
												<option value="O"><bean:message key="archigest.archivo.booleano.or"/></option>
												<option value="Y NO"><bean:message key="archigest.archivo.booleano.andNot"/></option>
											</select>
										</td>
										<td>
											<select name="abrirpar" id="abrirpar_0">
												<option value=""></option>
												<option value="("><bean:message key="archigest.archivo.simbolo.abrirparentesis"/></option>
											</select>
										</td>
										<td>
											<select name="campo" id="campo_0" onchange="javascript:cambioEnCampo(this,0)">
												<option value=""></option>
												<c:forEach var="campo" items="${campos}">
													<option value="<c:out value="${campo.id}"/>"><c:out value="${campo.nombre}"/></option>
												</c:forEach>
												<option value="-100"><bean:message key="archigest.archivo.campo.descriptor"/></option>
											</select>
											<input type="hidden" name="tipoCampo" id="tipoCampo_0"/>
											<input type="hidden" name="tiposReferencia" id="tipoReferencia_0"/>
										</td>
										<td>
											<select name="operador" id="operador_0" onchange="javascript:cambioEnOperador(this,0)">
												<option value=""></option>
											</select>
										</td>
										<td nowrap="nowrap">
											<input type="hidden" name="formatoFecha1" id="formatoFecha1_0">
											<input type="hidden" name="valor1" id="valor1_0"/>
											<input type="hidden" name="valor1D" id="valor1D_0"/>
											<input type="hidden" name="valor1M" id="valor1M_0"/>
											<input type="hidden" name="valor1A" id="valor1A_0"/>
											<input type="hidden" name="valor1S" id="valor1S_0"/>


											<input type="hidden" name="nombreDesc" id="nombreDesc_0"/>
											<input type="hidden" name="idRelacionesDesc" id="idRelacionesDesc_0"/>
										</td>
										<td nowrap="nowrap">
											<input type="hidden" name="formatoFecha2" id="formatoFecha2_0">
											<input type="hidden" name="valor2"  id="valor2_0"/>
											<input type="hidden" name="valor2D" id="valor2D_0"/>
											<input type="hidden" name="valor2M" id="valor2M_0"/>
											<input type="hidden" name="valor2A" id="valor2A_0"/>
											<input type="hidden" name="valor2S" id="valor2S_0"/>


										</td>
										<td>
											<select name="cerrarpar" id="cerrarpar_0">
												<option value=""></option>
												<option value=")"><bean:message key="archigest.archivo.simbolo.cerrarparentesis"/></option>
											</select>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="campo" items="${paramValues.campo}" varStatus="status">
										<tr valign="top">
											<td>
												<input type="checkbox" name="check" style="border:0;"
													value="<c:out value="${status.index}"/>"/>
												<input type='hidden' size='1' name='formatoFechaSel1' id='formatoFechaSel1_<c:out value="${status.index}"/>' value='<c:out value="${paramValues.formatoFechaSel1[status.index]}"/>'/>
												<input type='hidden' size='1' name='formatoFechaSel2' id='formatoFechaSel2_<c:out value="${status.index}"/>' value='<c:out value="${paramValues.formatoFechaSel2[status.index]}"/>'/>

											</td>
											<td>
												<select name="booleano"  id="booleano_<c:out value="${status.index}"/>">
													<option value=""></option>
													<option value="Y" <c:if test="${paramValues.booleano[status.index]=='Y'}">selected="true"</c:if>><bean:message key="archigest.archivo.booleano.and"/></option>
													<option value="O" <c:if test="${paramValues.booleano[status.index]=='O'}">selected="true"</c:if>><bean:message key="archigest.archivo.booleano.or"/></option>
													<option value="Y NO" <c:if test="${paramValues.booleano[status.index]=='Y NO'}">selected="true"</c:if>><bean:message key="archigest.archivo.booleano.andNot"/></option>
												</select>
											</td>
											<td>
												<select  name="abrirpar"  id="abrirpar_<c:out value="${status.index}"/>">
													<option value=""></option>
													<option value="(" <c:if test="${paramValues.abrirpar[status.index]=='('}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.abrirparentesis"/></option>
												</select>
											</td>
											<td>
												<select name="campo" id="campo_<c:out value="${status.index}"/>" onchange="javascript:cambioEnCampo(this,<c:out value="${status.index}"/>)">
													<option value="">&nbsp;&nbsp;&nbsp;</option>
													<c:forEach var="campo" items="${campos}">
													<option value="<c:out value="${campo.id}"/>"
														<c:if test="${paramValues.campo[status.index]==campo.id}">selected="true"</c:if>
														><c:out value="${campo.nombre}"/></option>
													</c:forEach>
													<option value="-100"
														<c:if test="${paramValues.campo[status.index]=='-100'}">selected="true"</c:if>
														><bean:message key="archigest.archivo.campo.descriptor"/></option>
												</select>
												<input type="hidden" name="tipoCampo" id="tipoCampo_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.tipoCampo[status.index]}"/>"/>
												<input type="hidden" name="tiposReferencia" id="tipoReferencia_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.tiposReferencia[status.index]}"/>"/>
											</td>
											<td>
												<select name="operador" id="operador_<c:out value="${status.index}"/>"  onchange="javascript:cambioEnOperador(this,<c:out value="${status.index}"/>)">
													<c:choose>
														<c:when test="${empty paramValues.tipoCampo[status.index] || paramValues.tipoCampo[status.index] == 0}">
															<option value=""></option>
														</c:when>
														<c:when test="${paramValues.tipoCampo[status.index] == 5}">
															<option value="=" <c:if test="${paramValues.operador[status.index]=='='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.igual" /></option>
														</c:when>
														<c:when test="${paramValues.tipoCampo[status.index] == 3}">
															<option value="=" <c:if test="${paramValues.operador[status.index]=='='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.igual" /></option>
															<option value="&lt;" <c:if test="${paramValues.operador[status.index]=='&lt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menor" /></option>
															<option value="&lt;=" <c:if test="${paramValues.operador[status.index]=='&lt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menoroigual" /></option>
															<option value="&gt;" <c:if test="${paramValues.operador[status.index]=='&gt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayor" /></option>
															<option value="&gt;=" <c:if test="${paramValues.operador[status.index]=='&gt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayoroigual" /></option>
															<option value="[..]" <c:if test="${paramValues.operador[status.index]=='[..]'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.rango" /></option>
															<option value="QCN" <c:if test="${paramValues.operador[status.index]=='QCN'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.quecontengan" /></option>
															<option value="EX" <c:if test="${paramValues.operador[status.index]=='EX'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.exacta" /></option>
														</c:when>
														<c:when test="${paramValues.tipoCampo[status.index] == 4}">
															<option value="=" <c:if test="${paramValues.operador[status.index]=='='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.igual" /></option>
															<option value="&lt;" <c:if test="${paramValues.operador[status.index]=='&lt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menor" /></option>
															<option value="&lt;=" <c:if test="${paramValues.operador[status.index]=='&lt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menoroigual" /></option>
															<option value="&gt;" <c:if test="${paramValues.operador[status.index]=='&gt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayor" /></option>
															<option value="&gt;=" <c:if test="${paramValues.operador[status.index]=='&gt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayoroigual" /></option>
															<option value="[..]" <c:if test="${paramValues.operador[status.index]=='[..]'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.rango" /></option>
														</c:when>
														<c:when test="${paramValues.tipoCampo[status.index] == 2}">
															<option value="QC" <c:if test="${paramValues.operador[status.index]=='QC'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.quecontenga" /></option>
														</c:when>
														<c:when test="${paramValues.tipoCampo[status.index] == 1}">
															<option value="=" <c:if test="${paramValues.operador[status.index]=='='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.igual" /></option>
															<option value="&lt;" <c:if test="${paramValues.operador[status.index]=='&lt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menor" /></option>
															<option value="&lt;=" <c:if test="${paramValues.operador[status.index]=='&lt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.menoroigual" /></option>
															<option value="&gt;" <c:if test="${paramValues.operador[status.index]=='&gt;'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayor" /></option>
															<option value="&gt;=" <c:if test="${paramValues.operador[status.index]=='&gt;='}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.mayoroigual" /></option>
															<option value="QC" <c:if test="${paramValues.operador[status.index]=='QC'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.quecontenga" /></option>
															<option value="QCC" <c:if test="${paramValues.operador[status.index]=='QCC'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.quecomiencecon" /></option>
														</c:when>
													</c:choose>
												</select>
											</td>
											<td nowrap="nowrap">
											    <c:set var="idRelDesc" value="${paramValues.idRelacionesDesc[status.index]}"/>
												<input type="hidden" id="idRelacionesDesc_<c:out value="${status.index}"/>" name="idRelacionesDesc" value="<c:out value="${idRelDesc}"/>"/>
												<c:choose>
													<c:when test="${paramValues.operador[status.index] == 'QC' || paramValues.operador[status.index] == 'QCC'}">
														<input type="text" name="valor1" value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
														<input type="hidden" name="valor1D" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>
														<input type="hidden" name="valor1M" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>
														<input type="hidden" name="valor1A" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
														<input type="hidden" name="valor1S" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>
														<input type="hidden" name="formatoFecha1" id="formatoFecha1_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
														<input type="hidden" name="nombreDesc" value="<c:out value="${paramValues.nombreDesc[status.index]}"/>"/>
													</c:when>
													<c:when test="${paramValues.operador[status.index] == '=' || paramValues.operador[status.index] == '<' || paramValues.operador[status.index] == '<=' || paramValues.operador[status.index] == '>' || paramValues.operador[status.index] == '>=' || paramValues.operador[status.index] == '[..]'}">
														<c:choose>
															<c:when test="${paramValues.tipoCampo[status.index] == 5}">
																<input type="text" name="nombreDesc" class="inputRO90" readonly="readonly" value='<c:out value="${paramValues.nombreDesc[status.index]}"/>'/>
																<input type="hidden" name="formatoFecha1" id="formatoFecha1_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
																<input type="hidden" name="valor1"  id="valor1_<c:out value="${status.index}"/>"  value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
																<input type="hidden" name="valor1D" id="valor1D_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>
																<input type="hidden" name="valor1M" id="valor1M_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>
																<input type="hidden" name="valor1A" id="valor1A_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
																<input type="hidden" name="valor1S" id="valor1S_<c:out value="${status.index}"/>" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>

																<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="2px"
																		/><a href="javascript:showSearchObjectForm(<c:out value="${status.index+1}"/>)"
																	><html:img styleId="imgExpandDescriptor"
																		page="/pages/images/expand.gif"
																		styleClass="imgTextMiddle" /></a>
															</c:when>
															<c:when test="${paramValues.tipoCampo[status.index] == 5 || paramValues.tipoCampo[status.index] == 4 || paramValues.tipoCampo[status.index] == 1}">
																<input type="hidden" name="formatoFecha1">
																<input type="text" name="valor1" value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
																<input type="hidden" name="valor1D" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>
																<input type="hidden" name="valor1M" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>
																<input type="hidden" name="valor1A" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
																<input type="hidden" name="valor1S" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>

																<input type="hidden" name="nombreDesc" value="<c:out value="${paramValues.nombreDesc[status.index]}"/>"/>
															</c:when>
															<c:otherwise>
																<select name="formatoFecha1" id="formatoFecha1_<c:out value="${status.index}"/>" onchange="cambioTipoFecha(this,1,<c:out value="${status.index}"/>)">
																	<option value="DDMMAAAA"<c:if test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.DDMMAAAA" /></option>
																	<option value="MMAAAA" 	<c:if test="${paramValues.formatoFecha1[status.index]=='MMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.MMAAAA" /></option>
																	<option value="AAAA" 	<c:if test="${paramValues.formatoFecha1[status.index]=='AAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.AAAA" /></option>
																	<option value="S" 		<c:if test="${paramValues.formatoFecha1[status.index]=='S'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.S" /></option>
																</select>
																<input type="hidden" name="valor1"/>

																<c:choose>
																<c:when test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA'}">
																	<input type="text" name="valor1D" id="valor1D_" size="2" maxlength="2" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>-
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor1D" id="valor1D<c:out value="${status.index}"/>" />
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA' or paramValues.formatoFecha1[status.index]=='MMAAAA'}">
																	<input type="text" name="valor1M" id="valor1M_" size="2" maxlength="2" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>-
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor1M" id="valor1M<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${(paramValues.formatoFecha1[status.index]=='DDMMAAAA') or (paramValues.formatoFecha1[status.index]=='MMAAAA') or (paramValues.formatoFecha1[status.index]=='AAAA')}">
																	<input type="text" name="valor1A" id="valor1A_" size="4" maxlength="4" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor1A" id="valor1A<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${paramValues.formatoFecha1[status.index]=='S'}">
																	<input type="text" name="valor1S" id="valor1S_" size="5" maxlength="5" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor1S" id="valor1S<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>

																<input type="hidden" name="nombreDesc" value="<c:out value="${paramValues.nombreDesc[status.index]}"/>"/>


															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${paramValues.operador[status.index] == 'QCN' || paramValues.operador[status.index] == 'EX'}">
														<select name="formatoFecha1" id="formatoFecha1_<c:out value="${status.index}"/>" onchange="cambioTipoFecha(this,1,<c:out value="${status.index}"/>)">
															<option value="DDMMAAAA" <c:if test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.DDMMAAAA" /></option>
															<option value="MMAAAA" <c:if test="${paramValues.formatoFecha1[status.index]=='MMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.MMAAAA" /></option>
															<option value="AAAA" <c:if test="${paramValues.formatoFecha1[status.index]=='AAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.AAAA" /></option>
															<option value="S" <c:if test="${paramValues.formatoFecha1[status.index]=='S'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.S" /></option>
														</select>
														<input type="hidden" name="valor1"/>
														<c:choose>
														<c:when test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA'}">
															<input type="text" name="valor1D" id="valor1D_" size="2" maxlength="2" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>-
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor1D" id="valor1D<c:out value="${status.index}"/>" />
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA' or paramValues.formatoFecha1[status.index]=='MMAAAA'}">
															<input type="text" name="valor1M" id="valor1M_" size="2" maxlength="2" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>-
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor1M" id="valor1M<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha1[status.index]=='DDMMAAAA' or paramValues.formatoFecha1[status.index]=='MMAAAA' or paramValues.formatoFecha1[status.index]=='AAAA'}">
															<input type="text" name="valor1A" id="valor1A_" size="4" maxlength="4" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor1A" id="valor1A<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha1[status.index]=='S'}">
															<input type="text" name="valor1S" id="valor1S_" size="4" maxlength="4" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor1S" id="valor1S<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>

														<input type="hidden" name="nombreDesc" value="<c:out value="${paramValues.nombreDesc[status.index]}"/>"/>
													</c:when>
													<c:otherwise>
														<input type="hidden" name="formatoFecha1" id="formatoFecha1_<c:out value="${status.index}"/>">
														<input type="hidden" name="valor1" value="<c:out value="${paramValues.valor1[status.index]}"/>"/>
														<input type="hidden" name="valor1D" value="<c:out value="${paramValues.valor1D[status.index]}"/>"/>
														<input type="hidden" name="valor1M" value="<c:out value="${paramValues.valor1M[status.index]}"/>"/>
														<input type="hidden" name="valor1A" value="<c:out value="${paramValues.valor1A[status.index]}"/>"/>
														<input type="hidden" name="valor1S" value="<c:out value="${paramValues.valor1S[status.index]}"/>"/>
														<input type="hidden" name="nombreDesc" value="<c:out value="${paramValues.nombreDesc[status.index]}"/>"/>
													</c:otherwise>
												</c:choose>


											</td>
											<td nowrap="nowrap">
												<c:choose>
													<c:when test="${paramValues.operador[status.index] == '[..]'}">
														<c:choose>
															<c:when test="${paramValues.tipoCampo[status.index] == 4 || paramValues.tipoCampo[status.index] == 1}">
																<input type="text" name="valor2" value="<c:out value="${paramValues.valor2[status.index]}"/>"/>
																<input type="hidden" name="formatoFecha2" id="formatoFecha2_<c:out value="${status.index}"/>">
																<input type="hidden" name="valor2D" value="<c:out value="${paramValues.valor2D[status.index]}"/>"/>
																<input type="hidden" name="valor2M" value="<c:out value="${paramValues.valor2M[status.index]}"/>"/>
																<input type="hidden" name="valor2A" value="<c:out value="${paramValues.valor2A[status.index]}"/>"/>
																<input type="hidden" name="valor2S" value="<c:out value="${paramValues.valor2S[status.index]}"/>"/>
															</c:when>
															<c:otherwise>
																<select name="formatoFecha2" id="formatoFecha2_<c:out value="${status.index}"/>" onchange="cambioTipoFecha(this,2,<c:out value="${status.index}"/>)">
																	<option value="DDMMAAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.DDMMAAAA" /></option>
																	<option value="MMAAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='MMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.MMAAAA" /></option>
																	<option value="AAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='AAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.AAAA" /></option>
																	<option value="S" <c:if test="${paramValues.formatoFecha2[status.index]=='S'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.S" /></option>
																</select>
																<input type="hidden" name="valor2"/>

																<c:choose>
																<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA'}">
																	<input type="text" name="valor2D" id="valor2D_" size="2" maxlength="2" value="<c:out value="${paramValues.valor2D[status.index]}"/>"/>-
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor2D" id="valor2D<c:out value="${status.index}"/>" />
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA' or paramValues.formatoFecha2[status.index]=='MMAAAA'}">
																	<input type="text" name="valor2M" id="valor2M_" size="2" maxlength="2" value="<c:out value="${paramValues.valor2M[status.index]}"/>"/>-
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor2M" id="valor2M<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA' or paramValues.formatoFecha2[status.index]=='MMAAAA' or paramValues.formatoFecha2[status.index]=='AAAA'}">
																	<input type="text" name="valor2A" id="valor2A_" size="4" maxlength="4" value="<c:out value="${paramValues.valor2A[status.index]}"/>"/>
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor2A" id="valor2A<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>

																<c:choose>
																<c:when test="${paramValues.formatoFecha2[status.index]=='S'}">
																	<input type="text" name="valor2S" id="valor2S_" size="4" maxlength="4" value="<c:out value="${paramValues.valor2S[status.index]}"/>"/>
																</c:when>
																<c:otherwise>
																	<input type="hidden" name="valor2S" id="valor2S<c:out value="${status.index}"/>"/>
																</c:otherwise>
																</c:choose>


															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${paramValues.operador[status.index] == 'QCN' || paramValues.operador[status.index] == 'EX'}">
														<select name="formatoFecha2" id="formatoFecha2_<c:out value="${status.index}"/>" onchange="cambioTipoFecha(this,2,<c:out value="${status.index}"/>)">
															<option value="DDMMAAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.DDMMAAAA" /></option>
															<option value="MMAAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='MMAAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.MMAAAA" /></option>
															<option value="AAAA" <c:if test="${paramValues.formatoFecha2[status.index]=='AAAA'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.AAAA" /></option>
															<option value="S" <c:if test="${paramValues.formatoFecha2[status.index]=='S'}">selected="true"</c:if>><bean:message key="archigest.archivo.formato.S" /></option>
														</select>
														<input type="hidden" name="valor2"/>
														<c:choose>
														<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA'}">
															<input type="text" name="valor2D" id="valor2D_" size="2" maxlength="2" value="<c:out value="${paramValues.valor2D[status.index]}"/>"/>-
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor2D" id="valor2D<c:out value="${status.index}"/>" />
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA' or paramValues.formatoFecha2[status.index]=='MMAAAA'}">
															<input type="text" name="valor2M" id="valor2M_" size="2" maxlength="2" value="<c:out value="${paramValues.valor2M[status.index]}"/>"/>-
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor2M" id="valor2M<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha2[status.index]=='DDMMAAAA' or paramValues.formatoFecha2[status.index]=='MMAAAA' or paramValues.formatoFecha2[status.index]=='AAAA'}">
															<input type="text" name="valor2A" id="valor2A_" size="4" maxlength="4" value="<c:out value="${paramValues.valor2A[status.index]}"/>"/>
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor2A" id="valor2A<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>

														<c:choose>
														<c:when test="${paramValues.formatoFecha2[status.index]=='S'}">
															<input type="text" name="valor2S" id="valor2S_" size="4" maxlength="4" value="<c:out value="${paramValues.valor2S[status.index]}"/>"/>
														</c:when>
														<c:otherwise>
															<input type="hidden" name="valor2S" id="valor2S<c:out value="${status.index}"/>"/>
														</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<input type="hidden" name="formatoFecha2">
														<input type="hidden" name="valor2" value="<c:out value="${paramValues.valor2[status.index]}"/>"/>
														<input type="hidden" name="valor2D" value="<c:out value="${paramValues.valor2D[status.index]}"/>"/>
														<input type="hidden" name="valor2M" value="<c:out value="${paramValues.valor2M[status.index]}"/>"/>
														<input type="hidden" name="valor2A" value="<c:out value="${paramValues.valor2A[status.index]}"/>"/>
														<input type="hidden" name="valor2S" value="<c:out value="${paramValues.valor2S[status.index]}"/>"/>
													</c:otherwise>
												</c:choose>

											</td>
											<td>
												<select name="cerrarpar">
													<option value=""></option>
													<option value=")" <c:if test="${paramValues.cerrarpar[status.index]==')'}">selected="true"</c:if>><bean:message key="archigest.archivo.simbolo.cerrarparentesis"/></option>
												</select>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					</div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

