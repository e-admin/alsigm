<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title></title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
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
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
	
	</head>
	<body>
		<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
						<a href="#" id="btnCancel" onclick='<ispac:hideframe refresh="true"/>' class="btnCancel">
							<bean:message key="common.message.close" />
						</a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
			<form>
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent> 
					<p>
						<c:choose>
							<c:when test="${!empty requestScope[appConstants.actions.SIGN_DOCUMENT_LIST]}"> 
								<br/>
								<label class="popUpCentrado"><bean:message key="sign.document.process.ok"/></label>
								<br/>
								<label class="popUpCentrado"><bean:message key="sign.message.documents.signed"/>:</label>
								
								<c:set var="_list" value="${appConstants.actions.SIGN_DOCUMENT_LIST}"/>
								<jsp:useBean id="_list" type="java.lang.String"/>
								<c:set var="_formatter" value="${appConstants.actions.FORMATTER}"/>
								<jsp:useBean id="_formatter" type="java.lang.String"/>
								<display:table	name='<%=_list%>' 
														id="object" 
														export="false" 
														class="tableDisplay"
														sort="list" 
														requestURI=''
														style="width:95%">
														
						       				<logic:iterate name='<%=_formatter%>' scope="request" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
						       				
												<logic:equal name="format" property="fieldType" value="LIST" >
												
													<display:column	titleKey='<%=format.getTitleKey()%>' 
																	media='<%=format.getMedia()%>' 
																	sortable='<%=format.getSortable()%>'
																	sortProperty='<%=format.getPropertyName()%>'
												  					decorator='<%=format.getDecorator()%>'
												  					headerClass='<%=format.getHeaderClass()%>'
												  					class='<%=format.getColumnClass()%>'>
												  		
												  		<%=format.formatProperty(object)%>
												  		
													</display:column>
													
												</logic:equal>
												
											</logic:iterate>
											
										</display:table>
										
									</c:when>
									
									<c:otherwise>
									
										<label class="popUp"><bean:message key="sign.document.circuit.init.ok"/></label>
									
									
									</c:otherwise>
								
							</c:choose>
							<br/><br/>
						</p>
				</div>
				</form>
	   		</div>
		</div>
	</div>

	</body>
</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
			$("#contenido").draggable();
		});
</script>