<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="JavaScript" type="text/javascript">

	function downloadDocuments() {
		var data = checkboxElement(document.forms["defaultForm"].multibox);
		if (data != "") {
      		var oldTarget = document.forms["defaultForm"].target;
      		var oldAction = document.forms["defaultForm"].action;

			document.forms["defaultForm"].action = "downloadDocuments.do";
      		document.forms["defaultForm"].target = "workframe";
			document.forms["defaultForm"].submit();

      		document.forms["defaultForm"].target = oldTarget;
      		document.forms["defaultForm"].action = oldAction;
		} else {
			jAlert('<bean:message key="forms.listdoc.descargarDocumentos.empty"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	function convertDocuments2PDF() {
		var data = checkboxElement(document.forms["defaultForm"].multibox);
		if (data != "") {
            var oldTarget = document.forms["defaultForm"].target;
            var oldAction = document.forms["defaultForm"].action;

			document.forms["defaultForm"].action = "convertDocuments2PDF.do";
            document.forms["defaultForm"].target = "workframe";
			showLayer("waitInProgress");
			document.forms["defaultForm"].submit();

            document.forms["defaultForm"].target = oldTarget;
            document.forms["defaultForm"].action = oldAction;
		} else {
			jAlert('<bean:message key="forms.listdoc.convertDocuments2PDF.empty"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

</script>

<table cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
	</tr>
	<tr>
		<td class="textbar">

			<table cellspacing="0" cellpadding="0" border="0" width="100%">

				<tr>
					<td class="textbar">
						<bean:message key="forms.tasks.attached.documents"/>:
					</td>
				</tr>

			</table>

		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">

			<table style=" background-color:#FFFFFF;" class="caja" cellspacing="0" cellpadding="0" width="100%">

				<tr>
					<td height="8px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
				</tr>

				<logic:equal value="false" property="readonly" name="defaultForm">

					<tr>
						<td align="right" class="formsTitleB">

							<table border="0" cellspacing="0" cellpadding="0">
								<tr>

									<logic:notEmpty  name="defaultForm" property="entityApp.property(GENERATE_BOOK)">


									<c:set var="action"  ><bean:write name="defaultForm" property="entityApp.property(ACTION)"/></c:set>
										<td class="tdlink" >
											<a onclick="javascript:generateLibro('<c:out value="${action}"/>');"  class="formaction">
												<nobr><bean:message key="forms.tasks.generate.libro"/></nobr>
											</a>
										</td>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>

									</logic:notEmpty>
									<td class="formsTitle" >
										<bean:message key="forms.tasks.generate.document"/>:
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
									<td>
										<ispac:linkframe 	id="GenerarDocumentoPlantilla"
												target="workframe"
												action="selectTemplateDocumentType.do"
												titleKey="forms.tasks.generate.document.from.template"
												showFrame="true"
												inputField=""
												styleClass="tdlink"
												width="500"
												height="300"
												needToConfirm="true">
										</ispac:linkframe>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
									<td>
										<ispac:linkframe 	id="GenerarDocumentoAnexar"
												target="workframe"
												action="selectDocumentType.do"
												titleKey="forms.tasks.generate.document.attach"
												showFrame="true"
												inputField=""
												styleClass="tdlink"
												width="500"
												height="300"
												needToConfirm="true">
										</ispac:linkframe>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
									<td>
										<ispac:linkframe 	id="GenerarDocumentoEscanear"
												target="workframe"
												action="selectDocumentType.do?action=scanFiles.do"
												titleKey="forms.tasks.generate.document.scan"
												showFrame="true"
												inputField=""
												styleClass="tdlink"
												width="500"
												height="300"
												needToConfirm="true">
										</ispac:linkframe>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
									<td>
										<ispac:linkframe id="GenerarDocumentoRepositorio"
												 target="workframe"
												 action="selectDocumentType.do?action=uploadRepoFiles.do"
												 titleKey="forms.tasks.generate.document.from.repository"
												 showFrame="true"
												 inputField=""
												 styleClass="tdlink"
												 width="640"
												 height="480"
												 needToConfirm="true">
										</ispac:linkframe>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
								</tr>
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="5px"/></td>
								</tr>
							</table>

						</td>
					</tr>
					<tr>
						<td align="right" class="formsTitleB">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="formsTitleB">
										<a   href="javascript: deleteDocument();" class="tdlink">
											<bean:message key="forms.tasks.delete.document"/>
										</a>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
								</tr>
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					</logic:equal>

		        <tr>
		          <td align="right" class="formsTitleB">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="formsTitle"><bean:message
									key="forms.listdoc.descargarDocumentos" />:</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
									border="0" width="10px" /></td>
								<td><a href="javascript:downloadDocuments();" class="tdlink">
								<bean:message key="forms.listdoc.descargarDocumentos" /> </a></td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
									border="0" width="10px" /></td>
								<td><a href="javascript:convertDocuments2PDF();"
									class="tdlink"> <bean:message
									key="forms.listdoc.convertDocuments2PDF" /> </a></td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>'
									border="0" width="10px" /></td>
							</tr>
						</table>
					</td>
		        </tr>

				<tr>
					<td>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
								<td style="border-bottom: 1px DOTTED #5C65A0;" width="100%"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="5px"/></td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="5px"/></td>
							</tr>
						</table>
					</td>
				</tr>

		        <tr>
					<td>

						<!-- displayTag con formateador -->
						<bean:define name="defaultForm" property="formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

						<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
						<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

						<display:table name="sessionScope.defaultForm.items"
									   id="document"
									   form="defaultForm"
									   excludedParams="*"
									   decorator="checkboxDecorator"
									   export='<%=formatter.getExport()%>'
							   		   class='<%=formatter.getStyleClass()%>'
									   sort='<%=formatter.getSort()%>'
									   pagesize='<%=formatter.getPageSize()%>'
									   defaultorder='<%=formatter.getDefaultOrder()%>'
									   defaultsort='<%=formatter.getDefaultSort()%>'
									   requestURI="/showTask.do">

							<logic:iterate name="defaultForm" property="formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

								<logic:equal name="format" property="fieldType" value="CHECKBOX">


										<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

										<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.defaultForm.multibox, this);\' name=\'allbox\'/>"%>'
														media='<%=format.getMedia()%>'
														headerClass='<%=format.getHeaderClass()%>'
														sortable='<%=format.getSortable()%>'
														sortProperty='<%=format.getPropertyName()%>'
														decorator='<%=format.getDecorator()%>'
														class='<%=format.getColumnClass()%>'
														comparator='<%=format.getComparator()%>'
														property="checkbox">
										</display:column>



							    </logic:equal>

								<logic:equal name="format" property="fieldType" value="LINK">

								  	<display:column titleKey='<%=format.getTitleKey()%>'
								  					media='<%=format.getMedia()%>'
								  					headerClass='<%=format.getHeaderClass()%>'
								  					sortable='<%=format.getSortable()%>'
								  					sortProperty='<%=format.getPropertyName()%>'
								  					decorator='<%=format.getDecorator()%>'
								  					class='<%=format.getColumnClass()%>'
								  					comparator='<%=format.getComparator()%>'>

										<c:set var="extension" value="unknown"/>
										<logic:notEmpty name="document" property="property(EXTENSION_RDE)">
											<c:set var="extension">
												<bean:write name="document" property="property(EXTENSION_RDE)" />
											</c:set>
										</logic:notEmpty>
										<logic:empty name="document" property="property(EXTENSION_RDE)">
											<logic:notEmpty name="document" property="property(EXTENSION)">
												<c:set var="extension">
													<bean:write name="document" property="property(EXTENSION)" />
												</c:set>
											</logic:notEmpty>
										</logic:empty>
										<bean:define id="extension" name="extension" type="java.lang.String"/>
										<bean:define id="_link" value='<%=format.getUrl()%>'></bean:define>
										<c:url value="${_link}" var="link">
											<c:if test="${!empty param.taskId}">
												<c:param name="taskId" value='${param.taskId}'/>
											</c:if>
											<c:param name="key" value="${document.keyProperty}"/>
											<c:param name="entity" value="2"/>
											<c:if test="${!empty param.readonly}">
												<c:param name="readonly" value='${param.readonly}'/>
											</c:if>
										</c:url>

										<a href='<c:out value="${link}"/>' class='<%=format.getStyleClass()%>'>

											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
								  			<%=format.formatProperty(document)%>

										</a>

										<%--
								  		<html:link href='<%=format.getUrl() + "?taskId=" + request.getParameter("taskId") + "&entity=2"%>'
								  				   styleClass='<%=format.getStyleClass()%>'
								  				   paramId='<%=format.getId()%>'
								  				   paramName="document"
								  				   paramProperty='<%=format.getPropertyLink() %>'>

											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
								  			<logic:notEmpty name="document" property='<%= format.getPropertyName() %>'>

								  		</html:link>
								  		--%>

								  	</display:column>

								 </logic:equal>

								<logic:equal name="format" property="fieldType" value="LIST">

									<display:column titleKey='<%=format.getTitleKey()%>'
													media='<%=format.getMedia()%>'
													headerClass='<%=format.getHeaderClass()%>'
													sortable='<%=format.getSortable()%>'
													sortProperty='<%=format.getPropertyName()%>'
													decorator='<%=format.getDecorator()%>'
													class='<%=format.getColumnClass()%>'
													comparator='<%=format.getComparator()%>'>

										<%=format.formatProperty(document)%>

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

										<logic:notEmpty name="document" property='<%= format.getPropertyName() %>'>

											<c:set var="_documentId"><bean:write name="document" property="property(ID)"/></c:set>
											<ispac:showdocument document='<%=(String)pageContext.getAttribute("_documentId")%>'
																image='img/viewDoc.gif'
																styleClass="menuhead"
																titleKeyLink="forms.button.show.document"
																message=""/>

										</logic:notEmpty>

									</display:column>

								</logic:equal>

							</logic:iterate>

						</display:table>

					</td>
				</tr>
				<tr>
					<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
				</tr>

			</table>

		</td>
	</tr>
	<tr>
		<td height="4px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
	</tr>

</table>

<ispac:layer id="waitInProgress" msgKey="libro.generate.wait" showCloseLink="false" styleClassMsg="messageShowLayer" />

<logic:equal value="false" property="readonly" name="defaultForm">

	<script language="JavaScript" type="text/javascript">

		if (document.defaultForm['multibox'] == undefined) {
			if (document.defaultForm['allbox'] != undefined) {
				document.defaultForm['allbox'].disabled = true;
			}
		}

		function generateLibro(action) {
      		var oldTarget = document.forms["defaultForm"].target;
      		var oldAction = document.forms["defaultForm"].action;

			document.forms["defaultForm"].action = action;
			showLayer("waitInProgress");
			document.forms["defaultForm"].submit();

      		document.forms["defaultForm"].target = oldTarget;
      		document.forms["defaultForm"].action = oldAction;
		}

	</script>

</logic:equal>
