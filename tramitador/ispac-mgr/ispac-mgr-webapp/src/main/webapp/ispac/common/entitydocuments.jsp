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
    <link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    
  	<script>
  	
		function generate()
		{
		  var url;
		  
		  var Template = document.getElementById('template');
		  var Save = document.getElementById('save');
		  var Print = document.getElementById('print');
		  
		  if (Save.checked == false && Print.checked == false)
		  {
		  	
		  	
		  	jAlert('<bean:message key="document.info.noSelect"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		  	return;
		  }

			if (Template.selectedIndex == -1)
		  {
		  	jAlert('<bean:message key="template.info.noAssociated"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			  return;
		  }
		  
			document.entityDocumentsForm.submit();
		}
		function changeTemplate()
		{
		  var file = document.getElementById('file');
		  if (file != undefined) {
		  	file.value = "";
		  }
		}
		function changeEntity()
		{
		  var url;
		  var Element;

		  Element = document.getElementById('entity');

			if (Element.selectedIndex == -1)
		  {
			  return;
		  }

			url = '<%=request.getContextPath()%>'	+ "/entityDocuments.do?action=entity";
			
			document.entityDocumentsForm.target = "workframe";
			document.entityDocumentsForm.action = url;
			document.entityDocumentsForm.submit();
		}
		function editTemplate()
		{
		  var url;
		  var Element;

		  Element = document.getElementById('template');

			if (Element.selectedIndex == -1)
		  {
			  return;
		  }

			url = '<%=request.getContextPath()%>' + "/entityDocuments.do?action=edit";
	
      document.entityDocumentsForm.action = url;
      document.entityDocumentsForm.submit();
		}
		</script>
		<%-- Arranca Word con la plantilla --%>
		<logic:equal name="entityDocumentsForm" property="action" value="edit">
			<%-- Identificador del documento --%>
			<bean:define name="entityDocumentsForm" property="file" id="file" />
			<%-- Arranca Word con la plantilla --%>
		<ispac:edittemplate template='<%= file.toString() %>'/>
		</logic:equal>
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
					  				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
					  				<td width="100%" class="menuhead"><bean:write name="DocumentType" property="property(NOMBRE)"/></td>
					  			</tr>
								</table>
					 		</td>
						</tr>
					  <tr>
					    <td class="blank">
								<html:form action="generateDocuments.do">
								<!-- Tipo de documento -->
								<html:hidden property="documentId" />
								<!-- Nombre del fichero temporal -->
								<logic:notEmpty name="entityDocumentsForm" property="file">
									<html:hidden property="file" styleId="file"/>
								</logic:notEmpty>
								<!-- Mantiene las fases/tareas seleccionadas -->
								<logic:notEmpty name="entityDocumentsForm" property="multibox">
									<logic:iterate name="entityDocumentsForm" 
																 property="multibox" 
																 id="element" 
																 type="java.lang.String">
											<html:hidden property="multibox" value='<%= element %>'/>
									</logic:iterate>
								</logic:notEmpty>
					  	  <table cellpadding="0" cellspacing="4" border="0" width="80%" height="80%" align="center" valign="middle">
									<tr>
								 	  <td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
									</tr>
									<logic:present name="DocumentTemplates">
									 	<tr>
							  	    <td colspan="2" align="left">
							  	      <bean:message key="templates.document"/>
						 					</td>
										</tr>
									 	<tr>
						          <td>
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
										 	   	  <td align="left">
										  			  <html:select property="template" 
										  			  						 styleId="template" 
										  			  						 styleClass="input" 
										  			  						 onchange="javascript:changeTemplate();">
									               <html:options collection="DocumentTemplates" 
									               							 property="property(ID)" 
									               							 labelProperty="property(NOMBRE)"/>
															</html:select>
														</td>
														<td><img src='<ispac:rewrite href="img/pixel.gif"/>' width="16px"/></td>
														<td align="left" width="100%">
															<html:button property="edit" styleClass="form_button_white" onclick="javascript:editTemplate();">
																<bean:message key="edit.template.document"/>
															</html:button>
														</td>
													</tr>
												</table>
						 					</td>
										</tr>
									</logic:present>
								 	<tr>
								 	  <td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
									</tr>
									<!-- Entidades -->
									<logic:present name="EntitiesList">
									 	<tr>
							  	    <td align="left">
							  	      <bean:message key="entities.document"/>
						 					</td>
										</tr>
										<tr>
										  <td align="left">
											  <html:select property="entity"
											  						 styleId="entity"
											  						 styleClass="input" 
											  						 onchange="javascript:changeEntity();">
						              <html:options collection="EntitiesList" 
						                						 property="property(ID)" 
						                						 labelProperty="property(DESCRIPCION)"/>
	
												</html:select>
											</td>
										</tr>
								 		<tr>
								 		  <td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
										</tr>
										<!-- Registros de la entidad -->
										<logic:present name="ElementsEntity">
											<logic:notEmpty name="ElementsEntity">
										 		<tr>
								  	   	  <td align="left">
								  	   	    <bean:message key="records.document"/>
							 						</td>
												</tr>
												<tr>
												 	<td align="left" width="100%">
													  	<display:table name="ElementsEntity"
													                   id="element"
													                   export="false"
													                  class="tableDisplay">
													                 
														  	<display:column title="Generar" 
														  				  	media="html"
														  				  	headerClass="headerDisplay">
														   		<html:multibox property="multikey">
																	<bean:write name="element" property='property(SCHEME_ID)'/>
														  		</html:multibox>
														  	</display:column>
														  
														  	<display:column title="Título" 
														  					media="html" 
														  					headerClass="headerDisplay">
															  	<bean:write name="element" property='property(SCHEME_EXPR)'/>
															</display:column>
															
														</display:table>
													</td>
												</tr>
										 		<tr>
										 		  <td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="16px"/></td>
												</tr>
											</logic:notEmpty>
										</logic:present>
									</logic:present>
									<tr>
									  <td align="left" valign="middle">
											<table border="0" cellpadding="4" cellpadding="0">
												<tr>
													<td>
											  		<html:checkbox property="save" styleId="save"/>
													</td>
													<td><bean:message key="save.documents"/></td>
												</tr>
											</table>
										</td>
								  </tr>
									<tr>
									  <td align="left" valign="middle">
											<table border="0" cellpadding="4" cellpadding="0">
												<tr>
													<td>
											  		<html:checkbox property="print" styleId="print"/>
													</td>
													<td><bean:message key="print.documents"/></td>
												</tr>
											</table>
									 	</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="32px"/></td>
									</tr>
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td align="center">

														<html:button property="generateDocument" styleClass="form_button_white" onclick="javascript:generate();">
															<bean:message key="generate.document.button"/>
														</html:button>
													</td>
												</tr>
											</table>
									  </td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="100%"/></td>
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
				        </html:form>
				  	  </td>
				    </tr>
				  </table>
			  </td>
		  </tr>
	  </table>
  </body>
</html>
