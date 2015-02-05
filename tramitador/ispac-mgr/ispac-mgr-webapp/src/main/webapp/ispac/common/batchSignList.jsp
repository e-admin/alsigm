<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
//<!--
	function validate(){
		document.defaultForm.name = "IntervaloFechas";
		return validateIntervaloFechas(document.defaultForm);
	}
//-->
</script>
<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<table border="0" width="100%">
	<tr>
		<td align="right">
			<%--ispac:onlinehelp fileName="signList"/--%>
			<%--
			<a href="javascript:;"
				onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/signList.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');"
				class="help">
				<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
				<bean:message key="header.help"/>
			</a>
			--%>
			<ispac:onlinehelp tipoObj="12" image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
</table>
<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead">
									<bean:message key="msg.documentos.pendientes.firma"/>
								</td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<%--
				<tr>
					<td class="blank">
						<html:form action="showSignHistoric.do" onsubmit="javascript:return validate();">
							<html:hidden property="method" value="historics"/>
							<html:javascript formName="IntervaloFechas"/>
							<!-- Nombre de Aplicación.
								 Necesario para realizar la validación -->
							<html:hidden property="entityAppName"/>
							<bean:message key="forms.batchSign.intervalo"/>
							<html:text property="property(FECHAINICIO)" styleClass="input" size="14"/>&nbsp;
							<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAINICIO)" format="dd/mm/yyyy" enablePast="true"/>
							<bean:message key="forms.terms.and"/>&nbsp;<html:text property="property(FECHAFIN)" styleClass="input" size="14"/>&nbsp;
							<ispac:calendar image='<%= buttoncalendar %>' formId="defaultForm" componentId="property(FECHAFIN)" format="dd/mm/yyyy" enablePast="true"/>&nbsp;&nbsp;
							<html:submit value="Consultar" styleClass="form_button_white"/>
						</html:form>
					</td>
				</tr>
				--%>
				<tr>
					<html:form action="showBatchSignList.do">

						<td class="blank">

							<c:set var="_list" value="${appConstants.actions.BATCH_SIGN_LIST}"/>
							<jsp:useBean id="_list" type="java.lang.String"/>

							<logic:empty name='<%=_list%>'>
								<span class="emptybanner"><bean:message key="forms.bathSign.noDocuments"/></span>
							</logic:empty>

							<logic:notEmpty name='<%=_list%>'>

								<!-- displayTag con formateador -->
								<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

								<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
								<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

								<display:table name='<%=_list%>'
											   id="object"
											   form="defaultForm"
											   excludedParams="multibox"
											   decorator="checkboxDecorator"
											   requestURI="/showBatchSignList.do"
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
															headerClass='<%=format.getHeaderClass()%>'
															sortable='<%=format.getSortable()%>'
															decorator='<%=format.getDecorator()%>'
															comparator='<%=format.getComparator()%>'
															class='<%=format.getColumnClass()%>'
                              property="checkbox">
											</display:column>

									    </logic:equal>

										<logic:equal name="format" property="fieldType" value="LIST">

											<display:column titleKey='<%=format.getTitleKey()%>'
															media='<%=format.getMedia()%>'
															headerClass='<%=format.getHeaderClass()%>'
															sortable='<%=format.getSortable()%>'
															sortProperty='<%=format.getPropertyName()%>'
															decorator='<%=format.getDecorator()%>'
															comparator='<%=format.getComparator()%>'
															class='<%=format.getColumnClass()%>'>

												<%=format.formatProperty(object)%>

											</display:column>

										</logic:equal>

										<logic:equal name="format" property="fieldType" value="LINK">

										  	<display:column titleKey='<%=format.getTitleKey()%>'
										  					media='<%=format.getMedia()%>'
										  					headerClass='<%=format.getHeaderClass()%>'
										  					sortable='<%=format.getSortable()%>'
										  					sortProperty='<%=format.getPropertyName()%>'
										  					decorator='<%=format.getDecorator()%>'
										  					comparator='<%=format.getComparator()%>'
										  					class='<%=format.getColumnClass()%>'>

										  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>'
										  			paramName="object" paramProperty='<%=format.getPropertyLink() %>' target="_blank">

										  			<%=format.formatProperty(object)%>
										  			<%--
										  			<bean:message key='<%= format.getPropertyValueKey() %>'/>
										  			--%>

										  		</html:link>

										  	</display:column>

										</logic:equal>

									</logic:iterate>

								</display:table>
								<!-- displayTag -->

							</logic:notEmpty>

						</td>

					</html:form>

				</tr>
			</table>
		</td>
	</tr>
</table>