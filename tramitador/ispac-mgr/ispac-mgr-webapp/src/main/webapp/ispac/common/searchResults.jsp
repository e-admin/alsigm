<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tdw.ispac.api.ISPACEntities" %>

<bean:define name="propertiesHelper" id="_propertiesHelper" type="ieci.tdw.ispac.ispaclib.search.PropertiesHelper"/>

<table border="0" width="100%">
	<tr>
		<td align="right">
			<%--
			<a href="javascript:;"
				onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/searchResults.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');"
				class="help">
				<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
				<bean:message key="header.help"/>
			</a>
			--%>
			<c:set var="idForm"><c:out value="${requestScope.idObj}" /></c:set>
			<bean:define id="idForm" name="idForm" type="java.lang.String"/>
			 <ispac:onlinehelp tipoObj="8" idObj='<%= idForm %>'  image="img/help.gif" titleKey="header.help"/>

		</td>
	</tr>
</table>


<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
<html:form action="/searchForm" >
	<tr>
		<td>
			<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								<td width="100%" class="menuhead"><bean:message key="search.results"/></td>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">

						<!-- displayTag con formateador -->
						<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>
						<logic:present name="maxResultados">
 							 <c:set var="maxres" value="${requestScope['maxResultados']}"/>
 							 <c:set var="totalres" value="${requestScope['numTotalRegistros']}"/>
 							 <bean:define id="maxres" name="maxres" type="java.lang.String"></bean:define>
 							 <bean:define id="totalres" name="totalres" type="java.lang.String"></bean:define>
 								 <div class="aviso">
									<img src='<ispac:rewrite href="img/error.gif"/>' />
 									<bean:message  key="search.generic.hayMasResultado"  arg0='<%=maxres%>' arg1='<%=totalres%>' />
 		                          </div>
 		                 </logic:present>

						<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
						<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

						<display:table name="ResultsList"
									   id="object"
									   form="searchForm"
									   excludedParams="*"
									   decorator="checkboxDecorator"
			  				 		   requestURI="searchForm.do"
						  			   export='<%=formatter.getExport()%>'
							   		   class='<%=formatter.getStyleClass()%>'
									   sort='<%=formatter.getSort()%>'
					   				   pagesize='<%=formatter.getPageSize()%>'
									   defaultorder='<%=formatter.getDefaultOrder()%>'
									   defaultsort='<%=formatter.getDefaultSort()%>'>

	           				<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

								<logic:equal name="format" property="fieldType" value="CHECKBOX">

									<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />

									<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:_checkAll(document.searchForm.multibox, this);\'/>"%>'
													media="html"
													sortable="false"
													sortProperty='<%=format.getPropertyName()%>'
													decorator='<%=format.getDecorator()%>'
													comparator='<%=format.getComparator()%>'
													headerClass="headerDisplay"
													class='<%=format.getColumnClass()%>'
													property="checkbox">
									</display:column>

								</logic:equal>

								<!-- Si es un enlace con parametros -->
	           					<logic:equal name="format" property="fieldType" value="LINKPARAM">

								  	<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
								  					media='html'
								  					headerClass="sortable"
								  					sortable="true"
								  					sortProperty='<%=format.getPropertyName()%>'
								  					style="white-space: nowrap;"
								  					decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'>

								  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>'
								  			name="format" property='<%=format.prepareLinkParams(object)%>'>

											<%=format.formatProperty(object)%>

								  		</html:link>
								  	</display:column>
								 </logic:equal>

								<!-- Si es un enlace -->
					  			<logic:equal name="format" property="fieldType" value="LINK">

								  	<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
								  					media='html'
								  					headerClass="sortable"
								  					sortable="true"
								  					sortProperty='<%=format.getPropertyName()%>'
								  					style="white-space: nowrap;"
								  					decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'>

								  		<html:link action="selectAnActivity.do" styleClass="tdlink" paramId="numexp" paramName="object" paramProperty='<%= format.getPropertyLink() %>'>
											<%=format.formatProperty(object)%>
										</html:link>

								  	</display:column>

								  	<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
								  					media='csv excel xml pdf'
								  					headerClass="sortable"
								  					sortable="true"
								  					style="white-space: nowrap;">

										<%=format.formatProperty(object)%>

								  	</display:column>

				    			</logic:equal>

				   				<logic:equal name="format" property="fieldType" value="LIST" >

									<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='<%=format.getMedia()%>'
													decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'
													headerClass="sortable"
													sortable="true"
													sortProperty='<%=format.getPropertyName()%>'
													style="white-space: nowrap;">

										<%=format.formatProperty(object)%>

									</display:column>

				   				</logic:equal>

				   				<logic:equal name="format" property="fieldType" value="DATE">

									<display:column property='<%="property("+format.getProperty()+")"%>'
													title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='<%=format.getMedia()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'
													headerClass="sortable"
													sortable="true"
													style="white-space: nowrap;"
													decorator="ieci.tdw.ispac.ispacweb.decorators.DateColumnDecorator"/>

				   				</logic:equal>

								<logic:equal name="format" property="fieldType" value="BOOLEAN_INT_VALUE">

									<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='<%=format.getMedia()%>'
													decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'
													headerClass="sortable"
													sortable="true"
													style="white-space: nowrap;">

										<% if ("1".equals(format.formatProperty(object))) { %>
											<bean:message key="bool.yes"/>
										<% } else { %>
											<bean:message key="bool.no"/>
										<% } %>

									</display:column>

								</logic:equal>

								<logic:equal name="format" property="fieldType" value="TASK_STATE">

									<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='<%=format.getMedia()%>'
													decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'
													headerClass="sortable"
													sortable="true"
													style="white-space: nowrap;">

										<% if (String.valueOf(ISPACEntities.TASKSTATUS_OPEN).equals(format.formatProperty(object))) { %>
											<bean:message key="state.open"/>
										<% } else if (String.valueOf(ISPACEntities.TASKSTATUS_DELEGATE).equals(format.formatProperty(object))) { %>
											<bean:message key="state.delegate"/>
										<% } else if (String.valueOf(ISPACEntities.TASKSTATUS_CLOSE).equals(format.formatProperty(object))) { %>
											<bean:message key="state.close"/>
										<% } %>

									</display:column>

								</logic:equal>

								<%--
				   				<logic:equal name="format" property="fieldType" value="NREG">

									<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='html'
													headerClass="sortable"
													sortable="true"
													style="white-space: nowrap;"
													decorator='<%=format.getDecorator()%>'
								  					comparator='<%=format.getComparator()%>'
								  					class='<%=format.getColumnClass()%>'>

										<%=format.formatProperty(object)%>

									</display:column>

									<display:column title='<%=_propertiesHelper.getMessage(format.getTitleKey())%>'
													media='csv excel xml pdf'
													headerClass="sortable"
													sortable="true"
													style="white-space: nowrap;">

										<%=format.formatProperty(object)%>

									</display:column>

	   							</logic:equal>
	   							--%>

							</logic:iterate>

						</display:table>

					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</html:form>