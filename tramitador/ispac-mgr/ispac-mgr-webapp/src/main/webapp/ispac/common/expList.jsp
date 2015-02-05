<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<table border="0" width="100%">
	<tr>
		<td align="right">
			<%--
			<a href="javascript:;" onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/expList.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');" class="help">
				<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
				<bean:message key="header.help"/>
			</a>
			--%>
			<ispac:onlinehelp tipoObj="11" image="img/help.gif" titleKey="header.help"/>
		</td>
	</tr>
</table>

	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
<html:form action="showProcessList.do">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="box">
					<tr>
						<td class="title" height="18px">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
									<td width="100%" class="menuhead">
										<logic:iterate name="ExpStageList" id="expedient" length="1">
										<bean:message key="workList.procedimiento"/> '<bean:write name="expedient" property="property(EXPED:NOMBREPROCEDIMIENTO)"/>'
										</logic:iterate>
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="blank">

							<!-- displayTag con formateador -->
							<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

              				<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
              				<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

							<display:table name="ExpStageList"
										   id="expedient"
										   form="batchForm"
										   excludedParams="multibox"
										   decorator="checkboxDecorator"
										   requestURI="/showProcessList.do"
										   export='<%=formatter.getExport()%>'
										   class='<%=formatter.getStyleClass()%>'
										   sort='<%=formatter.getSort()%>'
										   pagesize='<%=formatter.getPageSize()%>'
										   defaultorder='<%=formatter.getDefaultOrder()%>'
										   defaultsort='<%=formatter.getDefaultSort()%>'>

								<%--
								<display:column title='' media='html'>
								    <div style="display:none">
								      <bean:write name="expedient" property="property(WORKLIST:ID)"/>
									</div>
								</display:column>
								--%>

								<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

									<logic:equal name="format" property="fieldType" value="CHECKBOX">

                    					<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

										<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.batchForm.multibox, this);\'/>"%>'
														media='<%=format.getMedia()%>'
														headerClass='<%=format.getHeaderClass()%>'
														sortable='<%=format.getSortable()%>'
														decorator='<%=format.getDecorator()%>'
														comparator='<%=format.getComparator()%>'
														class='<%=format.getColumnClass()%>'
														property="checkbox"/>
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

									  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="expedient"
									  			paramProperty='<%=format.getPropertyLink() %>'>

									  			<%=format.formatProperty(expedient)%>

									  		</html:link>

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

											<%=format.formatProperty(expedient)%>

										</display:column>

									</logic:equal>

								</logic:iterate>

							</display:table>
							<!-- displayTag -->

						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>