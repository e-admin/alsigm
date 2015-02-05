<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
<!--
	function validate(){
		var form = document.defaultForm;
		document.defaultForm.name = "IntervaloFechas";
		var res = validateIntervaloFechas(document.defaultForm);
		if(res){
		form.action=form.action+"?method=list";
			form.submit();
			}



	}
	function deleteExpsTrash() {
		var form = document.defaultForm;


		if (checkboxElement(form.multibox) != "") {

			jConfirm('<bean:message key="forms.expsTrash.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
						if(r){
						form.action=form.action+"?method=deleteExps";
						form.submit();
						showLayer("waitOperationInProgress");
						}
			});
		} else {
			jAlert('<bean:message key="forms.expsTrash.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	function restoreExpsTrash() {
		var form = document.defaultForm;

		if (checkboxElement(form.multibox) != "") {


			jConfirm('<bean:message key="forms.expsTrash.confirm.restore"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
						if(r){
						form.action=form.action+"?method=restoreExps";
						form.submit();
						showLayer("waitOperationInProgress");
						}
			});
		} else {
			jAlert('<bean:message key="forms.expsTrash.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}
//-->
</script>
<html:errors/>
<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

	<table border="0" width="100%">
		<tr>
			<td align="right">
				<%--
				<a href="javascript:;"
					onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/terms.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');"
					class="help">
					<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
					<bean:message key="header.help"/>
				</a>
				--%>
				<ispac:onlinehelp tipoObj="6" image="img/help.gif" titleKey="header.help"/>
			</td>
		</tr>
	</table>
	<html:form action="showExpedientsSentToTrash.do" >
<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
		<c:set var="listaExps" value="${requestScope[appConstants.actions.EXPS_TRASH_LIST]}"/>
		<td>
  	 	<!-- datos de información-->

			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="listaExpsTrash.titulo"/>
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr><td colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="6px"/></td></tr>
							<tr>
								<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<td>
									<table height="18" cellspacing="2" border="0"  width="100%" align="right">
										<tr>
											<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>


												<html:javascript formName="IntervaloFechas"/>
												<!-- Nombre de Aplicación.
													 Necesario para realizar la validación -->
												<html:hidden property="entityAppName"/>
												<logic:present name="procedimientos">

												<td valign="top" height="18" class="formsTitleB">
													<bean:message key="workList.procedimiento"/>
													<html:select styleClass="input" property="property(PROCESOS:ID_PCD)"  >
													<%-- 	<html:optionsCollection name='<%=procedimientos%>' label="property(NOMBRE)" value="property(ID)"/>--%>
													<html:option value=""></html:option>
													<html:options collection="procedimientos" property="property(ID)" labelProperty="property(NOMBRE)" />
													</html:select>
												</td>
												</tr>
												<tr>
												<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
												</logic:present>
												<td valign="top" height="18" class="formsTitleB">
													<bean:message key="forms.trash.intervalo"/>&nbsp;&nbsp;
													<html:text property="property(FECHAINICIO)" styleClass="input" size="14"/>&nbsp;
													<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAINICIO)" format="dd/mm/yyyy" enablePast="true"/>
													&nbsp;&nbsp;<bean:message key="forms.terms.and"/>&nbsp;&nbsp;<html:text property="property(FECHAFIN)" styleClass="input" size="14"/>&nbsp;
													<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAFIN)" format="dd/mm/yyyy" enablePast="true"/>&nbsp;&nbsp;
													<html:button property="search"
														styleClass="form_button_white"
														onclick="javascript:validate();">
													<bean:message key="common.message.consultar"/>
												</html:button>
											<td>
										</tr>

									</table>
								</td>
								<td width="8px"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
							<tr>

								<td width="95%" height="1px" class="formsTitleB" colspan="2" style="padding-left: 10px;">
									<hr/></hr>
								</td>
							</tr>
							<tr>
								<td><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								<td>
							<table height="18" cellspacing="2" border="0" width="100%">
									<logic:notPresent name="onlyRead">
										<c:if test="${!empty pageScope.listaExps}">
											<tr><td>
												<img src='<ispac:rewrite href="img/pixel.gif"/>' width="20px"/>
												<html:button  property="restaurar"
														styleClass="form_button_white"
														onclick="javascript:restoreExpsTrash();">
													<bean:message key="forms.button.restore"/>
												</html:button>
												<html:button property="eliminar"
														styleClass="form_button_white"
														onclick="javascript:deleteExpsTrash();">
													<bean:message key="forms.button.delete"/>
												</html:button>
											</td></tr>
										</c:if>
										</logic:notPresent>
									   	<tr>
											<td colspan="2">

												<!-- displayTag con formateador -->
												<c:set var="Formatter" value="${requestScope['Formatter']}"/>
												<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>
													<logic:present name="maxResultados">
							 							 <c:set var="maxres" value="${requestScope['maxResultados']}"/>
							 							 <c:set var="totalres" value="${requestScope['numTotalRegistros']}"/>
							 							 <bean:define id="maxres" name="maxres" type="java.lang.String"></bean:define>
							 							 <bean:define id="totalres" name="totalres" type="java.lang.String"></bean:define>
							 								 <div class="aviso">
																<img src='<ispac:rewrite href="img/error.gif"/>' />
							 									<bean:message  key="trash.generic.hayMasResultado"  arg0='<%=maxres%>' arg1='<%=totalres%>' />
							 		                          </div>
							 		                 </logic:present>

												<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
												<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

												<display:table name="pageScope.listaExps"
															   id="object"
															   form="defaultForm"
															   excludedParams="multibox property(PROCESOS:ID_PCD) property(FECHAINICIO) property(FECHAFIN)"
															   decorator="checkboxDecorator"
															   requestURI="/showExpedientsSentToTrash.do"
															   export='<%=formatter.getExport()%>'
															   class='<%=formatter.getStyleClass()%>'
															   sort='<%=formatter.getSort()%>'
															   pagesize='<%=formatter.getPageSize()%>'
															   defaultorder='<%=formatter.getDefaultOrder()%>'
															   defaultsort='<%=formatter.getDefaultSort()%>'>

													<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

														<logic:equal name="format" property="fieldType" value="CHECKBOX">

															<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

															<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.defaultForm.multibox, this);\'/>"%>'
																			media='<%=format.getMedia()%>'
																			sortable='<%=format.getSortable()%>'
																			decorator='<%=format.getDecorator()%>'
																			comparator='<%=format.getComparator()%>'
																			headerClass='<%=format.getHeaderClass()%>'
																			class='<%=format.getColumnClass()%>'
																			property="checkbox">
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

														<logic:equal name="format" property="fieldType" value="LINK">

														  	<display:column titleKey='<%=format.getTitleKey()%>'
														  					media='<%=format.getMedia()%>'
																			class='<%=format.getColumnClass()%>'
																			headerClass='<%=format.getHeaderClass()%>'
														  					sortable='<%=format.getSortable()%>'
														  					sortProperty='<%=format.getPropertyName()%>'
														  					decorator='<%=format.getDecorator()%>'
														  					comparator='<%=format.getComparator()%>'>

														  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>'
														  			paramName="object" paramProperty='<%=format.getPropertyLink() %>'>
														  			<%=format.formatProperty(object)%>
														  		</html:link>


														  	</display:column>

														</logic:equal>
													</logic:iterate>

												</display:table>

											<td>
										</tr>
									</table>
								</td>
								<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
