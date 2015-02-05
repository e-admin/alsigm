<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage/>


<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<script language='JavaScript' type='text/javascript'><!--

	function save() {

		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";

		<c:if test="${!empty param.form}">
			document.defaultForm.action = document.defaultForm.action + "?form=" + '<c:out value='${param.form}'/>';
		</c:if>

		<logic:notEmpty scope="request" name="displayTagOrderParams">

			<c:choose>
				<c:when test="${!empty param.form}">
					document.defaultForm.action = document.defaultForm.action + "&";
				</c:when>
				<c:otherwise>
					document.defaultForm.action = document.defaultForm.action + "?";
				</c:otherwise>
			</c:choose>

			document.defaultForm.action = document.defaultForm.action + '<bean:write scope="request" name="displayTagOrderParams" filter="false"/>';
		</logic:notEmpty>

		document.defaultForm.name = "ListaDocumentos";

		if (validateListaDocumentos(document.defaultForm)) {

			document.defaultForm.submit();
		}

		ispac_needToConfirm = true;
	}

	function downloadDocuments() {

		var data = checkboxElement(document.forms["documentsForm"].multibox);

		if (data != "") {

            var oldTarget = document.forms["documentsForm"].target;

			document.forms["documentsForm"].action = "downloadDocuments.do";
            document.forms["documentsForm"].target = "workframe";
			document.forms["documentsForm"].submit();

            document.forms["documentsForm"].target = oldTarget;
		}
		else {
			jAlert('<bean:message key="forms.listdoc.descargarDocumentos.empty"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	function convertDocuments2PDF() {

		var data = checkboxElement(document.forms["documentsForm"].multibox);

		if (data != "") {

            var oldTarget = document.forms["documentsForm"].target;

			document.forms["documentsForm"].action = "convertDocuments2PDF.do";
            document.forms["documentsForm"].target = "workframe";
			showLayer("waitInProgress");
			document.forms["documentsForm"].submit();

            document.forms["documentsForm"].target = oldTarget;
		}
		else {
			jAlert('<bean:message key="forms.listdoc.convertDocuments2PDF.empty"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}


	<%--
	function printDocuments() {

		if (isIE()) {
			var form = document.forms["documentsForm"];
			var data = checkboxElement(form.multibox);

			if (data != "") {
				var defaultTarget = form.target;
				showFrame("workframe", "printDocuments.do",
					640, 480, '<bean:message key="forms.listdoc.imprimirDocumentos.confirm"/>',
					true, false, form);
				form.target = defaultTarget;
			}
			else {
				alert("<bean:message key="forms.listdoc.descargarDocumentos.empty"/>");
			}
		}
	}
	--%>

//--></script>

<!-- BOTON DE CERRAR CUANDO SE ABRE EL FORMULARIO EN EL WORKFRAME -->
<c:if test="${(!empty param.form) && (param.form == 'single')}">
<div id="moveBig">

	<!-- Si el workframe se abre con needToConfirm="true"
	 cuando el formulario se está editando es necesario refrescar la página principal al cerrarlo
	 cuando el formulario es de sólo lectura sólo es necesario cerrarlo y no hace falta refrescar -->

	<div class="encabezado_ficha">
		<div class="titulo_ficha">

			<div class="acciones_ficha">
				<logic:equal value="false" property="readonly" name="defaultForm">
					<input  type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
				</logic:equal>
				<logic:notEqual value="false" property="readonly" name="defaultForm">
					<input  type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="false"/>'/>
				</logic:notEqual>
			</div>
		</div>
	</div>
</c:if>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0" >

				<%-- Presentamos el formulario con los datos de un documento en funcion de si nos vienen los datos de un documento --%>

				<logic:notEmpty name="defaultForm" property="items">

					<bean:define id="key" name="defaultForm" property="key"/>

					<tr>
						<td height="28px" class="title">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td>
													<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																<!-- ACCION SALVAR -->
																<logic:equal value="false" property="readonly" name="defaultForm">
																	<td class="formaction" height="28px">
																		<div style="display:none" id="blockSave">
																			<html:link onclick="javascript: ispac_needToConfirm = false;" href="javascript:save();" styleClass="formaction">
																				<bean:message key="forms.button.save"/>
																			</html:link>
																		</div>
																	</td>
																</logic:equal>
																<!-- ACCION MOSTRAR DOCUMENTO -->
																<%-- SI NO TIENE UN DOCUMENTO FISICO CREADO NO MOSTRAMOS ENLACE PARA ABRIR--%>
																<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:INFOPAG)">
																	<td class="formaction" height="28px">

																		<%--
																		<c:set var="pathImg"><ispac:rewrite href="img/pixel.gif"/></c:set>
																		<c:set var="_readonly"><bean:write name="defaultForm" property="entityApp.property(READONLY)"/></c:set>
																		<jsp:useBean id="_readonly" type="java.lang.String"/>
																		<ispac:getdocument 	document='<%= key.toString() %>'
																							readonly='<%=_readonly%>'
																							image='<%=(String)pageContext.getAttribute("pathImg")%>'
																							styleClass="formaction"
																							message="forms.button.show.document"
																		/>
																		--%>

																		<c:set var="_document"><bean:write name="defaultForm" property="entityApp.property(SPAC_DT_DOCUMENTOS:ID)"/></c:set>
																		<jsp:useBean id="_document" type="java.lang.String"/>
																		<ispac:showdocument document='<%=_document%>'
																							message="forms.button.show.document"
																							styleClass="formaction"
																							image=""/>

																	</td>
																</logic:notEmpty>

															<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>



														</tr>
													</table>
													<!-- FINAL DE LAS ACCIONES -->
												</td>
											</tr>
											<tr>
												<td ><html:errors/></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table  width="100%" border="0" cellspacing="2" cellpadding="2" style="border:0px solid yellow;">
								<tr>
									<%--
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
									--%>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>

								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">

										<%--
										<html:form action="showExpedient.do">
										--%>

										<html:form action="storeEntity.do">

											<!-- Nombre de Aplicación.
												 Necesario para realizar la validación -->
											<html:javascript formName="ListaDocumentos"/>

											<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID_TPDOC)"/>
											<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID)"/>
											<html:hidden property="property(SPAC_DT_DOCUMENTOS:ORIGEN_ID)" />
											<html:hidden property="property(SPAC_DT_DOCUMENTOS:ORIGEN_TIPO)" />
											<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)" />
											<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO_TIPO)" />

											<logic:notEmpty name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID)">

												<!-- PESTAÑAS -->
												<table border="0" cellspacing="0" cellpadding="0">

													<tr>
														<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
															<nobr><bean:message key="forms.listdoc.data" /></nobr>
														</td>

													</tr>

												</table>

												<!-- CONTENIDO DE LA PRIMERA PESTAÑA  -->
												<div style="display:block" id="block1">

													<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">

														<tr>
															<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														<tr>
															<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														<tr>
															<td>

																<tiles:insert template="/forms/common/dataDocument.jsp">
																	<%-- Si el documento esta asociado a un tramite se muestra en solo lectura--%>
																	<logic:notEqual name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">
																		<tiles:put name="localReadonly" direct="true">true</tiles:put>
																	</logic:notEqual>
																	<logic:equal name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ID_TRAMITE)" value="0">
																		<tiles:put name="localReadonly" direct="true"><bean:write name="defaultForm" property="readonly"/></tiles:put>
																	</logic:equal>
																	<tiles:put name="entryPoint" direct="true">DocumentList</tiles:put>
																</tiles:insert>

															</td>
														</tr>
														<tr>
															<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>

													</table>

													<%--
													<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
														<tr>
															<td>
																<table cellspacing="0" cellpadding="0" align="center" width="95%">
																	<tr>
																		<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
																	</tr>
																	<tr>
																		<td>
																			<table border="0" cellspacing="0" cellpadding="0" width="100%">
																				<tr>
																					<td height="20" width="140px" class="formsTitleB">
																						<nobr><bean:message key="forms.listdoc.nombre" />:</nobr>
																					</td>
																					<td>
																						<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:NOMBRE)"/>'  class="inputReadOnly" size="80" maxlength="100" readonly="true"/>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td>
																						<table border="0" cellspacing="0" cellpadding="0">
																							<tr>
																								<td class="formsTitleB" width="140px">
																									<nobr><bean:message key="forms.listdoc.fAprobacion" />:</nobr>
																								</td>
																								<td>
																									<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:FAPROBACION)"/>' class="inputReadOnly" size="12" maxlength="12" readonly="true"/>
																								</td>
																								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="80px" height="20px"/></td>
																								<td class="formsTitleB" width="120px">
																									<nobr><bean:message key="forms.listdoc.fNotificacion"/>:</nobr>
																								</td>
																								<td  >
																									<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:FNOTIFICACION)"/>' class="inputReadOnly" size="12" maxlength="12" readonly="true"/>
																								</td>
																								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="4px"/></td>
																							</tr>
																						</table>
																		</td>
																	</tr>

																	<tr>
																		<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																	</tr>
																	<tr>
																		<td>
																			<table border="0" cellspacing="0" cellpadding="0" width="100%">
																				<tr>
																					<td height="20" width="140px" class="formsTitleB" valign="top">
																						<nobr><bean:message key="forms.listdoc.descripcion" />:</nobr>
																					</td>
																					<td height="20">
																						<textarea class="inputReadOnly" rows="2" cols="80" readonly="true"><bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:DESCRIPCION)"/></textarea>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<c:set var="_tipoReg"><bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)"/></c:set>
																	<c:if test="${(!empty _tipoReg) && (_tipoReg != 'NINGUNO')}">

																		<tr>
																			<td height="4px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td>
																				<table border="0" cellspacing="0" cellpadding="0" width="100%">
																					<tr>
																						<td height="20" width="140px" class="formsTitleB">
																							<nobr><bean:message key="forms.listdoc.origen" />:</nobr>
																						</td>
																						<td height="20">
																							<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:ORIGEN)"/>' class="inputReadOnly" size="80" readonly="true" maxlength="128"/>
																						</td>
																						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
																					</tr>
																					<tr>
																						<td height="20" class="formsTitleB">
																							<nobr><bean:message key="forms.listdoc.destino" />:</nobr>
																						</td>
																						<td>
																							<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:DESTINO)"/>' class="inputReadOnly" size="80" readonly="true" maxlength="250"/>
																						</td>
																						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
																					</tr>
																				</table>
																			</td>
																		</tr>

																		<tr>
																			<td height="4px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																		</tr>
																		<tr>
																			<td>
																				<table border="0" cellspacing="0" cellpadding="0" width="100%">
																					<tr>
																						<td height="20" width="140px" class="formsTitleB">
																							<nobr><bean:message key="forms.listdoc.nRegistro" />:</nobr>
																						</td>
																						<td >
																							<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:NREG)"/>' class="inputReadOnly" size="50" readonly="true" maxlength="16"/>
																						</td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<table border="0" cellspacing="0" cellpadding="0">
																					<tr>
																						<td class="formsTitleB" width="140px">
																							<nobr><bean:message key="forms.listdoc.fRegistro" />:</nobr>
																						</td>
																						<td  >
																							<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:FREG)"/>' class="inputReadOnly" size="12" readonly="true" maxlength="10"/>
																						</td>
																						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="80px" height="20px"/></td>
																						<td class="formsTitleB" width="120px">
																							<nobr><bean:message key="forms.listdoc.tipoRegistro" />:</nobr>
																						</td>
																						<td  >
																							<input type="text" value='<bean:write name="defaultForm" property="property(SPAC_DT_DOCUMENTOS:TP_REG)"/>' class="inputReadOnly" size="12" readonly="true" maxlength="16"/>
																						</td>
																						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="4px"/></td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																	</c:if>
																	<tr>
																		<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
													--%>

												</div>

											</logic:notEmpty>

										</html:form>

									</td>
									<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- Fin del formulario de un documento -->

					<!-- Formulario para presentar el listado de registros -->
					<tr>
						<td class="blankSinTop">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
														<tr>
															<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														<tr>
															<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<td colspan="3" align="center">
																			<table width="95%" cellpadding="0" cellspacing="0">
																				<tr>
																					<td width="95%" class="formsTitleB" colspan="2">
																						<bean:message key="forms.listdoc.listaDocumentos" />
																					</td>
																				</tr>
																				<tr>
																					<td width="95%" height="2px" class="formsTitleB" colspan="2">
																						<hr class="formbar" /></hr>
																					</td>
																				</tr>
																				<tr height="30px">
																					<td height="2px" class="formsTitleB">
																						<html:button property="descargarDocumento" styleClass="form_button_white" onclick="javascript:downloadDocuments();">
																							<bean:message key="forms.listdoc.descargarDocumentos"/>
																						</html:button>&nbsp;&nbsp;
																						<%--
																						<script language='JavaScript' type='text/javascript'>
																						//<!--
																							if (isIE()) {
																								document.write("<input type='button' name='imprimitDocumentos' value='" + '<bean:message key="forms.listdoc.imprimirDocumentos"/>' + "' onclick='javascript:printDocuments();' class='form_button_white'/>");
																								document.write("&nbsp;&nbsp;");
																							}
																						//-->
																						</script>
																						--%>
																					</td>
																					<td height="2px" class="formsTitleB">
																						<html:button property="convertDocuments2PDF" styleClass="form_button_white" onclick="javascript:convertDocuments2PDF();">
																							<bean:message key="forms.listdoc.convertDocuments2PDF"/>
																						</html:button>&nbsp;&nbsp;
																					</td>
																				</tr>
																				<tr>
																					<td  colspan="2">
																						<html:form action="downloadDocuments.do">

																						<display:table	name="sessionScope.defaultForm.items"
																										id="object"
																										export="true"
																										class="tableDisplay"
																										sort="list"
																										requestURI=''
																										excludedParams="d-*"
																										decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">

																		       				<logic:iterate name="defaultForm" property="formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

																								<logic:equal name="format" property="fieldType" value="CHECKBOX">

																									<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.documentsForm.multibox, this);\'/>"%>'
																													media='<%=format.getMedia()%>'
																													sortable='<%=format.getSortable()%>'
																													decorator='<%=format.getDecorator()%>'
																													comparator='<%=format.getComparator()%>'
																													headerClass='<%=format.getHeaderClass()%>'
																													class='<%=format.getColumnClass()%>'>

																							            <table cellpadding="0" cellspacing="0" border="0" width="100%">
																								            <tr>
																					  							<td align="center" valign="middle">
																					  								<img src='<ispac:rewrite href="img/pixel.gif"/>' width="3" height="1" border="0"/>
																					  							</td>
																					  							<td align="center" valign="middle">
																					    							<html:multibox property="multibox">
																					    								<%=format.formatProperty(object)%>
																					    							</html:multibox>
																					  							</td>
																											</tr>
																								 		</table>

																									</display:column>

																								</logic:equal>

																								<!-- Si es un enlace -->
																								<logic:equal name="format" property="fieldType" value="LINK">

																									<display:column	titleKey='<%=format.getTitleKey()%>'
																													media='<%=format.getMedia()%>'
																													headerClass='<%=format.getHeaderClass()%>'
																													sortable='<%=format.getSortable()%>'
																													sortProperty='<%=format.getPropertyName()%>'
																													decorator='<%=format.getDecorator()%>'
																													class='<%=format.getColumnClass()%>'
																													comparator='<%=format.getComparator()%>'>

																										<c:url value="${urlExpDisplayTagOrderParams}" var="link">
																											<c:if test="${!empty param.stageId}">
																												<c:param name="stageId" value='${param.stageId}'/>
																											</c:if>
																											<c:if test="${!empty requestScope.stagePcdId}">
																												<c:param name="stagePcdId" value="${requestScope.stagePcdId}"/>
																											</c:if>
																											<c:if test="${!empty param.taskId}">
																												<c:param name="taskId" value='${param.taskId}'/>
																											</c:if>
																											<c:if test="${!empty param.activityId}">
																												<c:param name="activityId" value='${param.activityId}'/>
																											</c:if>
																											<c:if test="${!empty requestScope.activityPcdId}">
																												<c:param name="activityPcdId" value="${requestScope.activityPcdId}"/>
																											</c:if>
																											<c:if test="${!empty param.numexp}">
																												<c:param name="numexp" value="${param.numexp}"/>
																											</c:if>
																											<c:param name="entity" value="${defaultForm.entity}"/>
																											<c:param name="key" value="${object.keyProperty}"/>
																											<c:if test="${!empty param.form}">
																												<c:param name="form" value='${param.form}'/>
																											</c:if>
																											<c:if test="${!empty param.nodefault}">
																												<c:param name="nodefault" value='${param.nodefault}'/>
																											</c:if>
																										</c:url>

																										<c:set var="extension" value="unknown"/>
																										<logic:notEmpty name="object" property="property(EXTENSION)">
																											<c:set var="extension">
																												<bean:write name="object" property="property(EXTENSION)" />
																											</c:set>
																										</logic:notEmpty>
																										<bean:define id="extension" name="extension" type="java.lang.String"/>

																										<nobr><a href='<c:out value="${link}"/>' class="tdlink">
																											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
																											<%=format.formatProperty(object)%>

																											<%-- Documento asociado a fase --%>
																											<c:set var="_stagePcd"><c:out value="${requestScope['stagePcdId']}"/></c:set>
																											<jsp:useBean id="_stagePcd" type="java.lang.String"/>
																											<logic:equal name="object" property="property(ID_FASE_PCD)" value='<%=_stagePcd%>'>
																												<logic:equal name="object" property="property(ID_TRAMITE)" value="0">
																													<c:if test="${empty param.taskId}">
																														<img src='<ispac:rewrite href="img/fase.gif"/>' class="imgTextBottom"/>
																													</c:if>
																												</logic:equal>
																											</logic:equal>

																											<%-- Documento asociado a actividad --%>
																											<c:set var="_taskId"><c:out value="${param.taskId}"/></c:set>
																											<jsp:useBean id="_taskId" type="java.lang.String"/>
																											<c:set var="_activityPcdId"><c:out value="${requestScope['activityPcdId']}"/></c:set>
																											<jsp:useBean id="_activityPcdId" type="java.lang.String"/>
																											<logic:equal name="object" property="property(ID_TRAMITE)" value='<%=_taskId%>'>
																												<logic:equal name="object" property="property(ID_FASE_PCD)" value='<%=_activityPcdId%>'>
																													<img src='<ispac:rewrite href="img/fase.gif"/>' class="imgTextBottom"/>
																												</logic:equal>
																											</logic:equal>

																										</a></nobr>

																									</display:column>

																								</logic:equal>

																								<logic:equal name="format" property="fieldType" value="LIST" >

																									<display:column	titleKey='<%=format.getTitleKey()%>'
																													media='<%=format.getMedia()%>'
																													headerClass='<%=format.getHeaderClass()%>'
																													sortable='<%=format.getSortable()%>'
																													sortProperty='<%=format.getPropertyName()%>'
																													decorator='<%=format.getDecorator()%>'
																													class='<%=format.getColumnClass()%>'
																													comparator='<%=format.getComparator()%>'>

																										<%=format.formatProperty(object)%>

																									</display:column>

																								</logic:equal>

																								<logic:equal name="format" property="fieldType" value="SHOWDOCUMENT">

																									<display:column	titleKey='<%=format.getTitleKey()%>'
																													media='<%=format.getMedia()%>'
																													headerClass='<%=format.getHeaderClass()%>'
																													sortable='<%=format.getSortable()%>'
																													sortProperty='<%=format.getPropertyName()%>'
																													decorator='<%=format.getDecorator()%>'
																													class='<%=format.getColumnClass()%>'
																													comparator='<%=format.getComparator()%>'>

																										<logic:notEmpty name="object" property='<%= format.getPropertyName() %>'>

																											<c:set var="_documentId"><bean:write name="object" property="property(ID)"/></c:set>
																											<ispac:showdocument document='<%=(String)pageContext.getAttribute("_documentId")%>'
																																image='img/viewDoc.gif'
																																styleClass="menuhead"
																																titleKeyLink="forms.button.show.document"
																																message=""/>

																										</logic:notEmpty>

																									</display:column>

																								</logic:equal>

																								<%--
																								<logic:equal name="format" property="fieldType" value="ICON">

																									<display:column	titleKey='<%=format.getTitleKey()%>'
																													media='<%=format.getMedia()%>'
																													headerClass='<%=format.getHeaderClass()%>'
																													sortable='<%=format.getSortable()%>'
																													sortProperty='<%=format.getPropertyName()%>'
																													decorator='<%=format.getDecorator()%>'
																													class='<%=format.getColumnClass()%>'
																													comparator='<%=format.getComparator()%>'>

																										<logic:notEmpty name="object" property="property(INFOPAG)">

																											<logic:equal name="object" property='<%= format.getPropertyName() %>' value='false'>

																												<c:set var="_documentId"><bean:write name="object" property="property(ID)"/></c:set>
																												<ispac:getdocument 	document='<%=(String)pageContext.getAttribute("_documentId")%>'
																																	image='img/editDoc.gif'
																																	styleClass="menuhead"
																																	message=""/>

																											</logic:equal>

																											<logic:notEqual name="object" property='<%= format.getPropertyName() %>' value='false'>

																												<c:set var="_documentId"><bean:write name="object" property="property(ID)"/></c:set>
																												<ispac:getdocument 	document='<%=(String)pageContext.getAttribute("_documentId")%>'
																																	readonly="true"
																																	image='img/glass.gif'
																																	styleClass="menuhead"
																																	message=""/>

																											</logic:notEqual>

																										</logic:notEmpty>

																									</display:column>

																								</logic:equal>
																								--%>

																							</logic:iterate>

																						</display:table>
																						</html:form>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>

				</logic:notEmpty>

				<logic:empty name="defaultForm" property="items">

					<tr>
						<td height="28px" class="title">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td>
													<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>

															<!-- BOTON DE CERRAR CUANDO SE ABRE EL FORMULARIO EN EL WORKFRAME -->
															<c:if test="${(!empty param.form) && (param.form == 'single')}">

																<td class="formaction" height="28px" width="100%" style="text-align:right">
																	<!-- Si el workframe se abre con needToConfirm="true"
																		 cuando el formulario se está editando es necesario refrescar la página principal al cerrarlo
																		 cuando el formulario es de sólo lectura sólo es necesario cerrarlo y no hace falta refrescar -->
																	<logic:equal value="false" property="readonly" name="defaultForm">
																		<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
																	</logic:equal>
																	<logic:notEqual value="false" property="readonly" name="defaultForm">
																		<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
																	</logic:notEqual>

																	<%-- Si el workframe se abre con needToConfirm="false"
																		 sólo es necesario cerrar el formulario y no hace falta refrescar la página principal
																	<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='javascript:ispac_needToConfirm=false;<ispac:hideframe/>'/>
																	--%>

																</td>
																<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>

															</c:if>

														</tr>
													</table>
													<!-- FINAL DE LAS ACCIONES -->
												</td>
											</tr>
											<tr>
												<td ><html:errors/></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td height="21px" class="blank">
							<table  width="100%" border="0" cellspacing="2" cellpadding="2" style="border:0px solid yellow;">
								<tr>
									<td>
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td>
													<html:form action="showExpedient.do">
													</html:form>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<!-- FORMULARIO -->
					<tr>
						<td class="blankSinTop">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table style="border:0px solid cyan;" cellspacing="0" cellpadding="0" align="center" width="95%">
														<tr>
															<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
														</tr>
														<tr>
															<td>
																<table width="95%" cellpadding="0" cellspacing="0">
																	<tr>
																		<td width="95%" class="formsTitleB" >
																			<b><nobr><bean:message key="forms.listdoc.listaDocumentos"/></nobr></b>
																		</td>
																	</tr>
																	<tr>
																		<td width="95%" height="2px" class="formsTitleB">
																			<hr class="formbar" /></hr>
																		</td>
																	</tr>
																	<tr>
																		<td width="95%" height="2px" class="formsTitle">
																			<bean:message key="forms.listdoc.noDocumentos"/>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>

				</logic:empty>

			</table>
		</td>
	</tr>
</table>

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>

<!-- BOTON DE CERRAR CUANDO SE ABRE EL FORMULARIO EN EL WORKFRAME -->
<c:if test="${(!empty param.form) && (param.form == 'single')}">
</div>
</c:if>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#moveBig").draggable();
	});
</script>