<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/time.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/print.js"/>'> </script>
	<script type="text/javascript" >
	//<!--
		function printDocumentCallback(printed, id, exception) {
			var image = document.getElementById("IMG_" + id);
			if (image) {
				if (printed) {
					image.src = '<ispac:rewrite href="img/icon_ok.gif"/>';
				} else {
					image.src = '<ispac:rewrite href="img/icon_error.gif"/>';
					if (exception != null) {
						image.alt = exception.name + ': ' + exception.message;
					}
				}
			}
		}
		
		function printDocuments() {
			<logic:present name="Documents">
				<logic:iterate name="Documents" id="document" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
					<ispac:printdocument document='<%= document.getKeyProperty().toString() %>' 
						callback="printDocumentCallback" inline="true"/>
				</logic:iterate>
			</logic:present>
		}
	//-->
	</script>
</head>
<body onload="javascript:printDocuments();">
  	
	<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
		<tr>
			<td width="100%">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="title" height="18px" width="80%">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr valign="bottom">
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
									<td width="100%" class="menuhead">
										<bean:message key="forms.listdoc.imprimirDocumentos.title"/>
									</td>
								</tr>
							</table>
						</td>
						<td width="20%" style="text-align:right">
							<%-- BOTON SALIR --%>
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td colspan="4"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
								</tr>
								<tr>
									<td style="text-align:right">
										<input type="button" value='<bean:message key="common.message.close"/>' class="form_button_white" onclick='<ispac:hideframe/>'/>&nbsp;
									</td>
								</tr>
							</table>						
						</td>
					</tr>
					<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%" class="blank">

				<table border="0" cellspacing="2" cellpadding="2" width="100%">
					<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
					<tr>
						<td valign="middle" align="center" class="formsTitle">
				    		<table cellpadding="0" cellspacing="1" border="0" width="98%" >
								<tr>
									<td class="index"></td>
								</tr>
								<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
						      	<tr>
						        	<td>
							        	
										<table cellpadding="0" cellspacing="0" width="100%" border="0">
											<tr>
												<td class="formsTitle">
													<bean:message key="forms.listdoc.imprimirDocumentos.text"/>
												</td>
											</tr>
										</table>
						
										<display:table	name="requestScope.Documents"
														id="document" 
														class="tableDisplay"
														decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">

											<display:column	titleKey="formatter.documents.documento" 
															class="width25percent">
															
												<c:set var="extension" value="unknown"/>
												<logic:notEmpty name="document" property="property(EXTENSION)">
													<c:set var="extension">
														<bean:write name="document" property="property(EXTENSION)" />
													</c:set>
												</logic:notEmpty>
												<bean:define id="extension" name="extension" type="java.lang.String"/>
												
												<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
												<bean:write name="document" property="property(NOMBRE)"/>
											</display:column>
													
											<display:column	titleKey="formatter.documents.descripcion" 
															property="property(DESCRIPCION)"
															class="width25percent"/>
						
											<display:column	titleKey="formatter.documents.fechaGeneracion" 
															class="width25percent">
												<bean:write name="document" property="property(FDOC)" format="dd/MM/yyyy"/>
											</display:column>

											<display:column	class="width5percent">
												<img id='IMG_<bean:write name="document" property="property(ID)"/>' 
													src='<ispac:rewrite href="img/pixel.gif"/>'/>
											</display:column>
						
										</display:table>
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
