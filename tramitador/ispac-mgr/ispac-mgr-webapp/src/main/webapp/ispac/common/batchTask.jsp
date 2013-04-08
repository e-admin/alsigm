<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript" type="text/javascript">

	function doSubmit() {
		//document.getElementById('refresherId').value='true';
		document.forms['batchTaskForm'].action = 'showBatchTask.do?batchTaskId=<bean:write name="batchTaskForm" property="batchTaskId"/>';
		//la llamada a generateDocuments cambia el target!!! Dejarlo para que trabaje sobre la ventana principal
		document.forms['batchTaskForm'].target='ParentWindow';
		document.forms['batchTaskForm'].submit();
	}

	function actualizaVistaDesdeTramites() {
		// Ocultar el valor para la recarga
		document.getElementById('tpDocId').name = "tpDocIdDeleted";
		document.getElementById('template').name = "templateDeleted";

		doSubmit();
	}

	function actualizaVistaDesdeTpDocs() {
		// Ocultar el valor para la recarga
		document.getElementById('template').name = "templateDeleted";

		doSubmit();
	}

	function actualizaVistaDesdeTemplates() {
		doSubmit();
	}

	function editTemplate() {
		var Element;
		Element = document.getElementById('tipoAccion');
		if (Element.selectedIndex == -1) {
			return;
		}
		Element.value='edit';

		doSubmit();
	}

	function generateDocument() {
		tipoDocument = document.getElementById('tpDocId').value;
		templateId = document.getElementById('template').value;
		cadena="batchTaskDocuments.do?documentId="+tipoDocument+"&templateId="+templateId+"&refresh=true";
		/*generateDocuments( cadena, <bean:message key="element.noSelect"/> ,<bean:message key="common.alert"/>,<bean:message key="common.message.ok"/>,<bean:message key="common.message.cancel"/>);
		*/

  		var oldTarget = document.forms["batchTaskForm"].target;
  		var oldAction = document.forms["batchTaskForm"].action;

		generateDocuments(cadena,'<bean:message key="element.noSelect"/>' ,'<bean:message key="common.alert"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');

  		document.forms["batchTaskForm"].target = oldTarget;
  		document.forms["batchTaskForm"].action = oldAction;
	}

	function downloadDocuments() {
		var data = checkboxElement(document.forms["batchTaskForm"].multibox);
		if (data != "") {
	  		var oldTarget = document.forms["batchTaskForm"].target;
	  		var oldAction = document.forms["batchTaskForm"].action;

			document.forms["batchTaskForm"].action = "downloadBatchTaskDocuments.do";
			document.forms["batchTaskForm"].submit();
			showLayer("waitInProgress");

	  		document.forms["batchTaskForm"].target = oldTarget;
	  		document.forms["batchTaskForm"].action = oldAction;
		} else {
			jAlert('<bean:message key="forms.batchTask.exp.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	function generateTask() {
		taskPcdId='';
		if(document.forms['batchTaskForm'].taskPcdId!=null) {
			taskPcdId = document.forms['batchTaskForm'].taskPcdId.value;
		}
		if(taskPcdId==null || taskPcdId=='') {
			jAlert('<bean:message key="task.noSelect"/>' ,'<bean:message key="common.alert"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>' );
		}
		else {
	  		var oldTarget = document.forms["batchTaskForm"].target;
	  		var oldAction = document.forms["batchTaskForm"].action;

			takeElementInFormWorkFrame('createTsk.do?idBatchTask=<c:out value="${batchTaskForm.batchTaskId}"/>','','batchTaskForm','<bean:message key="element.noSelect"/>' ,'<bean:message key="common.alert"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>' );

	  		document.forms["batchTaskForm"].target = oldTarget;
	  		document.forms["batchTaskForm"].action = oldAction;
		}
	}

</script>

<%-- Arranca Word con la plantilla --%>
<logic:equal name="batchTaskForm" property="tipoAccion" value="edit">
	<%-- Identificador del documento --%>
	<bean:define name="batchTaskForm" property="file" id="file" />
	<%-- Arranca Word con la plantilla --%>
	<ispac:edittemplate template='<%= file.toString() %>'/>
</logic:equal>
<%-- Imprime los documentos generados --%>
<logic:present name="DocumentsList">
	<logic:iterate name="DocumentsList"
								 id="document"
								 type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
		<ispac:printdocument document='<%= document.getKeyProperty().toString() %>' />
	</logic:iterate>
	<%request.getSession().removeAttribute("DocumentsList"); %>
</logic:present>
<logic:present name="FilesList">
	<logic:iterate name="FilesList"
								 id="file"
								 type="java.lang.String">
		<ispac:printfile file='<%= file %>' delete="true"/>
	</logic:iterate>
	<%request.getSession().removeAttribute("FilesList"); %>
</logic:present>

<table border="0" width="100%">
	<tr>
		<td align="right"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/>
			<%--
			<ispac:onlinehelp fileName="searchResults" image="img/help.gif" titleKey="header.help"/>
			--%>
		</td>
	</tr>
</table>

<table cellpadding="5" cellspacing="0" border="0" width="100%">

<c:url value="showBatchTask.do" var="actionForm">
	<c:if test="${!empty param.batchTaskId}">
		<c:param name="batchTaskId" value='${param.batchTaskId}'/>
	</c:if>
</c:url>
<bean:define id="actionForm" name="actionForm" type="java.lang.String"/>

<html:form action='<%=actionForm%>'>

<html:hidden property="batchTaskId" />
<html:hidden property="tipoAccion" value=''/>
<html:hidden property="file" />

<%--
<c:set var="refresherParamToken" value="${appConstants.actions.PARAM_FORM_REFRESHER}"/>
<jsp:useBean id="refresherParamToken" type="java.lang.String" />
<input id="refresherId" type='hidden' name='<%=refresherParamToken%>' value="true"/>
--%>

	<tr>
		<td>

			<!-- Panel de procedimientos -->
	        <table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="forms.batchTaskForm.batchExps"/>
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr valign="middle">
								<td height="24px" width="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="10px"/></td>
								<td height="18px" width="150px" class="formsTitle">&nbsp;<bean:message key="forms.doctram.title.name"/>:&nbsp;</td>
								<td>
									<c:set var="listaPosiblesTramitesName" value="${appConstants.actions.POSIBLES_TRAMITES_DE_FASE}"/>
									<jsp:useBean id="listaPosiblesTramitesName" type="java.lang.String"/>
									<html:select property="taskPcdId" styleClass="input" onchange="javascript:actualizaVistaDesdeTramites()">>
										<html:optionsCollection name='<%=listaPosiblesTramitesName%>' label="property(NOMBRE)" value="property(ID)"/>
									</html:select>&nbsp;
								</td>
								<td width="210px">
									<c:set var="nuevoTramiteJavascriptCode">javascript:generateTask();</c:set>
									<jsp:useBean id="nuevoTramiteJavascriptCode" type="java.lang.String"/>
									<html:button  property="nuevoTramite" styleClass="form_button_white" onclick='<%=nuevoTramiteJavascriptCode%>'>
											<bean:message key="ispac.action.batchtask.nuevo"/>
									</html:button>&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr valign="middle">
								<td height="24px" width="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="10px"/></td>
								<td height="18px" width="150px" class="formsTitle">&nbsp;<bean:message key="forms.batchTaskForm.tpDocsList.title.name"/>:&nbsp;</td>
								<td>
									<c:set var="listaTpDocs" value="${appConstants.actions.TPDOCS_LIST}"/>
									<jsp:useBean id="listaTpDocs" type="java.lang.String"/>
									<html:select styleId="tpDocId" property="tpDocId" styleClass="input" onchange="javascript:actualizaVistaDesdeTpDocs()">>
										<html:optionsCollection name='<%=listaTpDocs%>' label="property(CT_TPDOC:NOMBRE)" value="property(TASKTPDOC:ID_TPDOC)"/>
									</html:select>
								</td>
								<td width="210px">
									<nowrap>
									<html:button property="generarDocumento" styleClass="form_button_white" onclick="javascript:generateDocument();">
										<bean:message key="forms.batchTaskForm.generarDocumento"/>
									</html:button>&nbsp;&nbsp;
									</nowrap>
								</td>
							</tr>
							<tr>
								<td height="24px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="10px"/></td>
								<td height="18px" class="formsTitle">&nbsp;<bean:message key="forms.batchTaskForm.templateList.title.name"/>:&nbsp;</td>
								<td>
									<c:set var="listaTemplates" value="${appConstants.actions.TEMPLATES_LIST}"/>
									<jsp:useBean id="listaTemplates" type="java.lang.String"/>
									<html:select styleId="template" property="template" styleClass="input" onchange="javascript:actualizaVistaDesdeTemplates()">>
										<html:optionsCollection name='<%=listaTemplates%>' label="property(NOMBRE)" value="property(ID)"/>
									</html:select>&nbsp;&nbsp;
								</td>
								<td width="100px">
									<nowrap>
									<html:button property="descargarDocumento" styleClass="form_button_white" onclick="javascript:downloadDocuments();">
										<bean:message key="forms.batchTaskForm.descargarDocumentos"/>
									</html:button>&nbsp;&nbsp;
									</nowrap>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td class="blank">

						<!-- displayTag con formateador -->
						<c:set var="Formatter" value="${appConstants.formatters.BatchTaskFormatter}"/>
						<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

						<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
						<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

						<c:set var="listaExpedientes" value="${requestScope[appConstants.actions.BATCH_TASK_EXPS]}"/>
						<display:table name="pageScope.listaExpedientes"
									   id="object"
									   form="batchTaskForm"
									   excludedParams="*"
									   decorator="checkboxDecorator"
									   requestURI="/showBatchTask.do"
									   export='<%=formatter.getExport()%>'
									   class='<%=formatter.getStyleClass()%>'
									   sort='<%=formatter.getSort()%>'
									   pagesize='<%=formatter.getPageSize()%>'
									   defaultorder='<%=formatter.getDefaultOrder()%>'
									   defaultsort='<%=formatter.getDefaultSort()%>'>

							<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

								<logic:equal name="format" property="fieldType" value="CHECKBOX">

									<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

									<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.batchTaskForm.multibox, this);\'/>"%>'
													media='<%=format.getMedia()%>'
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'
													property="checkbox">
						  				<c:set var="taskId"><%=format.formatProperty(object)%></c:set>
									</display:column>

								</logic:equal>

								<logic:equal name="format" property="fieldType" value="LINK">

								  	<display:column titleKey='<%=format.getTitleKey()%>'
									  				media='<%=format.getMedia()%>'
									  				sortable='<%=format.getSortable()%>'
								 					sortProperty='<%=format.getPropertyName()%>'
								 					decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'>

								  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>'
								  			paramName="object" paramProperty='<%=format.getPropertyLink() %>'>

								  			<%=format.formatProperty(object)%>

								  		</html:link>

								  	</display:column>

								 </logic:equal>

								<logic:equal name="format" property="fieldType" value="LIST">

									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													sortable='<%=format.getSortable()%>'
													sortProperty='<%=format.getPropertyName()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'>

										<%=format.formatProperty(object)%>

									</display:column>

								</logic:equal>

								<logic:equal name="format" property="fieldType" value="BOOLEAN">

									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'>

										<c:set var="VALUE"><%=format.formatProperty(object)%></c:set>
										<c:choose>
										<c:when test="${VALUE}">
											<bean:message key="forms.si"/>
										</c:when>
										<c:otherwise>
											<bean:message key="forms.guion"/>
										</c:otherwise>
										</c:choose>

									</display:column>

								</logic:equal>

								<logic:equal name="format" property="fieldType" value="BOOLEAN_HIDDEN_VALUE">

									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'>

										<c:set var="VALUE"><%=format.formatProperty(object)%></c:set>
										<c:if test="${empty VALUE}">
											<c:set var="VALUE">0</c:set>
										</c:if>
										<jsp:useBean id="VALUE" type="java.lang.String"/>
										<c:choose>
										<c:when test="${VALUE>0}">
											<bean:message key="forms.si"/>
										</c:when>
										<c:otherwise>
											<bean:message key="forms.no"/>
										</c:otherwise>
										</c:choose>

										<jsp:useBean id="taskId" type="java.lang.String"/>
										<input name="taskIds" type="hidden" value='<%=taskId%>:<%=VALUE%>' />

									</display:column>

								</logic:equal>

								<logic:equal name="format" property="fieldType" value="BOOLEAN_HIDDEN_VALUE_EXPORT">

									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													sortable='<%=format.getSortable()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass='<%=format.getHeaderClass()%>'
													class='<%=format.getColumnClass()%>'>

										<c:set var="VALUE2"><%=format.formatProperty(object)%></c:set>
										<c:if test="${empty VALUE2}">
											<c:set var="VALUE2">0</c:set>
										</c:if>
										<jsp:useBean id="VALUE2" type="java.lang.String"/>
										<c:choose>
										<c:when test="${VALUE2>0}">
											<bean:message key="forms.si"/>
										</c:when>
										<c:otherwise>
											<bean:message key="forms.no"/>
										</c:otherwise>
										</c:choose>

									</display:column>

								</logic:equal>

							</logic:iterate>

						</display:table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</html:form>
</table>
