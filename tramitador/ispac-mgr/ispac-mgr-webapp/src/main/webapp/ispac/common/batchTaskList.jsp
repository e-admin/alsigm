<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="javascript">
//<!--
	function deleteBatchTask() {
		var form = document.batchForm;
		if (checkboxElement(form.multibox) != "") {
		/*
			if (confirm()) {
				form.submit();
			}
			*/

			jConfirm('<bean:message key="forms.batchTask.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
				if (r) {
					form.action = '<%=request.getContextPath() + "/deleteBatchTasks.do"%>';
					form.submit();
				}
			});
		} else {
			jAlert('<bean:message key="forms.batchTask.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}
//-->
</script>


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
<html:form action="showBatchTaskList.do">
	<tr>
		<td>
			<!-- Panel de procedimientos -->
	        <table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="listaTramitesAgrupados.titulo"/>
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<c:set var="listaAgrupaciones" value="${requestScope[appConstants.actions.BATCH_TASK_LIST]}"/>
					<td class="blank">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td>
									<table height="18" cellspacing="2" border="0" width="100%">
										<c:if test="${!empty pageScope.listaAgrupaciones}">
											<tr><td>
												<img src='<ispac:rewrite href="img/pixel.gif"/>' width="20px"/>
												<html:button property="eliminarTramitacionesAgrupadas"
														styleClass="form_button_white"
														onclick="javascript:deleteBatchTask();">
													<bean:message key="forms.button.delete"/>
												</html:button>
											</td></tr>
										</c:if>
										<tr>
											<td colspan="2">

												<!-- displayTag con formateador -->
												<c:set var="Formatter" value="${appConstants.formatters.BatchTaskListFormatter}"/>
												<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

												<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
												<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

												<display:table name="pageScope.listaAgrupaciones"
															   id="object"
															   form="batchForm"
															   excludedParams="multibox"
															   decorator="checkboxDecorator"
															   requestURI="/showBatchTaskList.do"
															   export='<%=formatter.getExport()%>'
															   class='<%=formatter.getStyleClass()%>'
															   sort='<%=formatter.getSort()%>'
															   pagesize='<%=formatter.getPageSize()%>'
															   defaultorder='<%=formatter.getDefaultOrder()%>'
															   defaultsort='<%=formatter.getDefaultSort()%>'>

													<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

														<logic:equal name="format" property="fieldType" value="CHECKBOX">

															<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />
															<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.batchForm.multibox, this);\'/>"%>'
																			media='<%=format.getMedia()%>'
																			sortable='<%=format.getSortable()%>'
																			decorator='<%=format.getDecorator()%>'
																			comparator='<%=format.getComparator()%>'
																			headerClass='<%=format.getHeaderClass()%>'
																			class='<%=format.getColumnClass()%>'
																			property="checkbox">
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

													</logic:iterate>

												</display:table>

											<td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</td>
	</tr>
</html:form>
</table>