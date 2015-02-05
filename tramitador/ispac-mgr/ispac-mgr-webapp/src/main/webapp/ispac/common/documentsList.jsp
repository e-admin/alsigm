<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
  </head>
<body>
	<table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%">
		<tr>
		  <td>
				<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%" height="100%">
				 	<tr>
					  <td class="title" height="18px" width="100%">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
				  			<tr>
				  				<td><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
				  				<td width="100%" class="menuhead"><bean:message key="generate.documents.text"/></td>
				  			</tr>
							</table>
				 		</td>
					</tr>
				  <tr>
				    <td class="blank">
							<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
								<html:form action="showDocuments.do">
								<!-- Mantiene el tipo de documento -->
								<html:hidden property="documentId" />
								<!-- Mantiene los elementos seleccionadas -->
								<logic:notEmpty name="documentsForm" property="multibox">
									<logic:iterate name="documentsForm"
																 property="multibox"
																 id="element"
																 type="java.lang.String">
											<html:hidden property="multibox" value='<%= element %>'/>
									</logic:iterate>
								</logic:notEmpty>
								<tr>
									<td align="center">
										<display:table name="DocumentsList"
																	 id="document"
																	 export="true"
																	 class="tableDisplay"
																	 sort="list"
																	 requestURI="/showDocuments.do">

											<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
												<logic:equal name="format" property="fieldType" value="CHECKBOX">
													<display:column titleKey='<%=format.getTitleKey()%>'
																	media='<%=format.getMedia()%>'
																	headerClass='<%=format.getHeaderClass()%>'
																	sortable='<%=format.getSortable()%>'>
								            <table cellpadding="1" cellspacing="1" border="0" width="100%">
									            <tr>
						  									<td align="center" valign="middle">
						  										<img src='<ispac:rewrite href="img/folder.gif"/>' align="center" valign="top" width="16" height="16" border="0"/>
						  									</td>
						  									<td align="center" valign="middle">
						    									<html:multibox property="multidoc">
											            	  		<%=format.formatProperty(document)%>
						    									</html:multibox>
						  									</td>
															</tr>
									          </table>
													</display:column>
											  </logic:equal>
												<logic:equal name="format" property="fieldType" value="LINK">
											  	<display:column titleKey='<%=format.getTitleKey()%>'
											  					media='<%=format.getMedia()%>'
											  					headerClass='<%=format.getHeaderClass()%>'
											  					sortable='<%=format.getSortable()%>'
											  					sortProperty='<%=format.getPropertyName()%>'>
											  					
											  		<html:link action='<%=format.getUrl()%>'
											  							 styleClass='<%=format.getStyleClass()%>'
											  							 paramId='<%=format.getId()%>'
											  							 paramName="document"
											  							 paramProperty='<%=format.getPropertyLink() %>'>
								            	  		<%=format.formatProperty(document)%>
											  		</html:link>
											  	</display:column>
												</logic:equal>
												<logic:equal name="format" property="fieldType" value="LIST">
													<display:column titleKey='<%=format.getTitleKey()%>'
																	media='<%=format.getMedia()%>'
																	headerClass='<%=format.getHeaderClass()%>'
																	sortable='<%=format.getSortable()%>'>
								            	  		<%=format.formatProperty(document)%>
													</display:column>
												</logic:equal>
											</logic:iterate>
										</display:table>
										</td>
								</tr>
								</html:form>
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="18px"/></td>
								</tr>
								<tr>
									<td>
										<table border="0" cellspacing="0" cellpadding="0" align="center" valign="middle">
											<tr>
												<td>
													<input type="button" value='<bean:message key="exit.button"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>
												</td>
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
	</table>
</body>
</html>
