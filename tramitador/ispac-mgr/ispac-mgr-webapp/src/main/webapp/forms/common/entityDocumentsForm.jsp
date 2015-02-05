<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="blockdocs">
	<tiles:insert attribute="blockdocs" ignore="true" flush="false" />
</c:set>
<bean:define name="blockdocs" id="blockdocs"/>

<logic:equal name="defaultForm" property="readonly" value="false">

	<div id="form_DOCUMENT_ACTIONS" style="top: 10px; left: 10px;">
	
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="formsTitle" >
					<bean:message key="forms.tasks.generate.document"/>:
				</td>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				<td>
				
					<ispac:linkframe id="GenerarDocumentoPlantilla"
									 target="workframe"
									 action="selectTemplateDocumentEntity.do" 
									 titleKey="forms.tasks.generate.document.from.template" 
									 showFrame="true" 
									 inputField=""
									 styleClass="tdlink"
									 width="500"
									 height="400"
									 needToConfirm="true">
					</ispac:linkframe>
					
				</td>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				<td>
				
					<ispac:linkframe id="GenerarDocumentoAnexar"
									 target="workframe"
									 action="selectDocumentTypeEntity.do" 
									 titleKey="forms.tasks.generate.document.attach" 
									 showFrame="true" 
									 inputField=""
									 styleClass="tdlink"
									 width="500"
									 height="400"
									 needToConfirm="true">
					</ispac:linkframe>
					
				</td>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				<td>
				
					<ispac:linkframe id="GenerarDocumentoEscanear"
									 target="workframe"
									 action="selectDocumentTypeEntity.do?action=scanFiles.do" 
									 titleKey="forms.tasks.generate.document.scan" 
									 showFrame="true" 
									 inputField=""
									 styleClass="tdlink"
									 width="500"
									 height="400"
									 needToConfirm="true">
					</ispac:linkframe>
					
				</td>
				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				<td>
					<ispac:linkframe id="GenerarDocumentoRepositorio"
									 target="workframe"
									 action="selectDocumentTypeEntity.do?action=uploadRepoFiles.do" 
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
	
	</div>
	
</logic:equal>

<div id="form_ENTITY_DOCUMENTS_LIST" style=" top: 30px; left: 10px;">

	<%
		// Mantener activo el tab de documentos al interactuar con la lista
		 String displayTagURI = (String) request.getAttribute("urlExp");
		//String displayTagURI = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		
		if ((queryString == null) || (queryString.indexOf("blockdocs") == -1)) {
			
			displayTagURI += "?blockdocs=" + blockdocs;
		}
	%>

	<!-- displayTag con formateador -->
	<bean:define name="defaultForm" property="entityDocumentsFormatter" id="documentsFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

	<display:table	name="sessionScope.defaultForm.entityDocuments"
					export='false'
					class='<%=documentsFormatter.getStyleClass()%>'
					sort='<%=documentsFormatter.getSort()%>'
					pagesize='<%=documentsFormatter.getPageSize()%>'
					defaultorder='<%=documentsFormatter.getDefaultOrder()%>'
					defaultsort='<%=documentsFormatter.getDefaultSort()%>'
					requestURI='<%=displayTagURI%>'
					excludedParams="block d-*"
					decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator"
					uid='uid_documents'>
	
		<logic:iterate name="defaultForm" property="entityDocumentsFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	
			<logic:equal name="format" property="fieldType" value="LINK">
			
				<display:column	titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>' 
								headerClass='<%=format.getHeaderClass()%>' 
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								comparator='<%=format.getComparator()%>'>
					
					<c:set var="extension" value="unknown"/>
					<logic:notEmpty name="uid_documents" property="property(EXTENSION_RDE)">
						<c:set var="extension">
							<bean:write name="uid_documents" property="property(EXTENSION_RDE)" />
						</c:set>
					</logic:notEmpty>
					<logic:empty name="uid_documents" property="property(EXTENSION_RDE)">
						<logic:notEmpty name="uid_documents" property="property(EXTENSION)">
							<c:set var="extension">
								<bean:write name="uid_documents" property="property(EXTENSION)" />
							</c:set>
						</logic:notEmpty>
					</logic:empty>
					<bean:define id="extension" name="extension" type="java.lang.String"/>
									
					<c:set var="_activityId"><c:out value="${param.activityId}"/></c:set>
					<c:set var="_taskId"><c:out value="${param.taskId}"/></c:set>
					<c:set var="_docTaskId"><bean:write name="uid_documents" property="property(ID_TRAMITE)" /></c:set>
					<c:set var="_stageId"><c:out value="${param.stageId}"/></c:set>
					<c:if test="${empty _stageId}">
						<c:set var="_stageId"><c:out value="${requestScope.stageId}"/></c:set>
					</c:if>
					<c:set var="_docStageId" ><bean:write name="uid_documents" property="property(ID_FASE)" /></c:set>
					
					<c:choose>
					
						<c:when test="${empty _stageId}">
						
							<c:url value="/showData.do" var="_showExpedient">
							<c:if test="${!empty param.numexp}">
								<c:param name="numexp" value="${param.numexp}"/>
							</c:if>
								<c:if test="${!empty requestScope.stagePcdId}">
									<c:param name="stagePcdId" value="${requestScope.stagePcdId}"/>
								</c:if>
								<c:param name="entity" value="2"/>
								<c:param name="key" value="${uid_documents.keyProperty}"/>
							</c:url>
							
						</c:when>
						
						<c:otherwise>
						
							<c:url value="/showExpedient.do" var="_showExpedient">
								<c:param name="stageId" value='${_stageId}'/>
								<c:if test="${!empty requestScope.stagePcdId}">
									<c:param name="stagePcdId" value="${requestScope.stagePcdId}"/>
								</c:if>
								<c:param name="entity" value="2"/>
								<c:param name="key" value="${uid_documents.keyProperty}"/>
							</c:url>
							
						</c:otherwise>
					
					</c:choose>
									
					<c:url value="/showTask.do" var="_showTask">
						<c:param name="taskId" value='${_docTaskId}'/>
						<c:if test="${!empty param.numexp}">
							<c:param name="numexp" value="${param.numexp}"/>
						</c:if>
						<c:param name="entity" value="2"/>
						<c:param name="key" value="${uid_documents.keyProperty}"/>
					</c:url>
					
					<c:url value="/showTask.do" var="_showTaskInShowFrame">
						<c:if test="${!empty param.taskId}">
							<c:param name="taskId" value='${param.taskId}'/>
						</c:if>
						<c:if test="${!empty param.numexp}">
							<c:param name="numexp" value="${param.numexp}"/>
						</c:if>
						<c:param name="entity" value="2"/>
						<c:param name="key" value="${uid_documents.keyProperty}"/>
						<c:param name="block" value="1"/>
						<c:param name="form" value="single"/>
						<c:param name="nodefault" value="true"/>
					</c:url>
					
					<nobr>
					
						<c:choose>
							
						
							<c:when test="${_docStageId == _stageId}">
							
								<c:choose>
									
									<c:when test="${empty _taskId}">
									
										<c:choose>
		
											<c:when test="${_docTaskId > 0}">
																					
												<a href='<c:out value="${_showTask}"/>' class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
										
											</c:when>
									
											<c:otherwise>
									
												<a href='<c:out value="${_showExpedient}"/>' class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
										
											</c:otherwise>
											
										</c:choose>
									
									</c:when>
									
									<c:otherwise>
									
										<c:choose>
										
											<c:when test="${_docTaskId > 0}">
			
												<a href='<c:out value="${_showTask}"/>' class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
										
											</c:when>
									
											<c:otherwise>
											
												<a href="javascript: showFrame('workframe', '<c:out value="${_showTaskInShowFrame}"/>', 800, 600, '', false);" class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
										
											</c:otherwise>
											
										</c:choose>									
											
									</c:otherwise>
									
								</c:choose>
								
							</c:when>
							
							
							<c:when test="${empty _stageId}">
							
								<c:choose>
									
									<c:when test="${_docTaskId > 0}">
																		
										<a href='<c:out value="${_showTask}"/>&readonly=4' class="tdlink">
											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
											<%=format.formatProperty(uid_documents)%>
										</a>
																	
									</c:when>
									
									<c:otherwise>
										
										<a href='<c:out value="${_showExpedient}"/>' class="tdlink">
											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
											<%=format.formatProperty(uid_documents)%>
										</a>
								
									</c:otherwise>
									
								</c:choose>
										
							</c:when>
							
							
							<c:otherwise>
							
								<c:choose>
									
									<c:when test="${empty _taskId}">
								
										<a href='<c:out value="${_showExpedient}"/>' class="tdlink">
											<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
											<%=format.formatProperty(uid_documents)%>
										</a>
									
									</c:when>
									
									<c:otherwise>
									
										<c:choose>
										
											<c:when test="${empty _activityId}">
											
												<a href="javascript: showFrame('workframe', '<c:out value="${_showTaskInShowFrame}"/>', 800, 600, '', false);" class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
												
											</c:when>
											
											<c:otherwise>
											
												<c:url value="/showSubProcess.do" var="_link">
													<c:param name="stageId" value='${_stageId}'/>
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
													<c:param name="entity" value="2"/>
													<c:param name="key" value="${uid_documents.keyProperty}"/>
												</c:url>
											
												<a href='<c:out value="${_link}"/>' class="tdlink">
													<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
													<%=format.formatProperty(uid_documents)%>
												</a>
											
											</c:otherwise>
											
										</c:choose>
								
									</c:otherwise>
									
								</c:choose>
										
							</c:otherwise>
							
						</c:choose>
									
					</nobr>
	
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
								
					<%=format.formatProperty(uid_documents)%>
					
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
					
					<logic:notEmpty name="uid_documents" property='<%= format.getPropertyName() %>'>
					
						<c:set var="_documentId"><bean:write name="uid_documents" property="property(ID)"/></c:set>
						<ispac:showdocument document='<%=(String)pageContext.getAttribute("_documentId")%>' 
											image='img/viewDoc.gif'
											styleClass="menuhead" 
											titleKeyLink="forms.button.show.document"
											message=""/>
																																
					</logic:notEmpty>
					
				</display:column>
				
			</logic:equal>
	
			<logic:equal name="format" property="fieldType" value="ICON">
			
				<display:column	titleKey='<%=format.getTitleKey()%>' 
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>' 
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								decorator='<%=format.getDecorator()%>'
								class='<%=format.getColumnClass()%>'
								comparator='<%=format.getComparator()%>'>
					
					<logic:notEmpty name="uid_documents" property="property(INFOPAG)">
					
						<logic:equal name="uid_documents" property='<%= format.getPropertyName() %>' value='false'>
						
							<c:set var="_documentId"><bean:write name="uid_documents" property="property(ID)"/></c:set>
							<ispac:getdocument 	document='<%=(String)pageContext.getAttribute("_documentId")%>'  
												image='img/editDoc.gif'
												styleClass="menuhead" 
												message=""/>
												
						</logic:equal> 
						
						<logic:notEqual name="uid_documents" property='<%= format.getPropertyName() %>' value='false'>
						
							<c:set var="_documentId"><bean:write name="uid_documents" property="property(ID)"/></c:set>
							<ispac:getdocument 	document='<%=(String)pageContext.getAttribute("_documentId")%>' 
												readonly="true"
												image='img/glass.gif'
												styleClass="menuhead" 
												message=""/>
						
						</logic:notEqual>
						
					</logic:notEmpty>
					
				</display:column>
				
			</logic:equal>
			
		</logic:iterate>
	
	</display:table>

</div>

<iframe src='' id='workframe_document' name='workframe_document' style='visibility:visible;height:0px;margin:0px;padding:0px;border:none;' allowtransparency='true'></iframe>

<logic:equal name="defaultForm" property="readonly" value="true">

	<script language='JavaScript' type='text/javascript'><!--
	
		document.getElementById('form_ENTITY_DOCUMENTS_LIST').style.top = '10px';
	
	//--></script>
	
</logic:equal>
