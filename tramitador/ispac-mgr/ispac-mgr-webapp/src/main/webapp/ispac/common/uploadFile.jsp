<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

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
	
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
  </head>
  <body>
  
  	<div id="contenido" class="moveSmall">
	
		<div class="ficha">
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
						<input type="button"  class="btnOk"value='<bean:message key="common.message.ok"/>' onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
					</div>
				</div>
			</div>
		
			<div class="cuerpo_ficha">
		
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent> 
					<html:errors/>
					
					<p class="left">
					
						<logic:notPresent name="errors">
								
					        			<c:set var="okList" value="${appConstants.actions.OK_UPLOAD_FILES_LIST}"/>
										<jsp:useBean id="okList" type="java.lang.String"/>
										<logic:present name='<%=okList%>'>
											<label class="popUp"><bean:message key="upload.file.summary"/></label>
											<logic:notEmpty name='<%=okList%>'>
												<label class="popUp"><bean:message key="upload.files.ok.text"/>:<br/></label>
											   	<ul>
													<logic:iterate name='<%=okList%>' id='object'>
														<li><bean:write name="object"/></li>
													</logic:iterate>
												</ul>
											</logic:notEmpty>
						        			<c:set var="errorList" value="${appConstants.actions.ERROR_UPLOAD_FILES_LIST}"/>
											<jsp:useBean id="errorList" type="java.lang.String"/>
											<logic:notEmpty name='<%=errorList%>'>
												<label class="popUp"><bean:message key="upload.files.error.text"/>:<br/></label>
												<ul>
													<logic:iterate name='<%=errorList%>' id='object'>
														<li><bean:write name="object"/></li>
													</logic:iterate>
												</ul>
											</logic:notEmpty>
										</logic:present>
										<logic:notPresent name='<%=okList%>'>
											<label class="popUp"><bean:message key="upload.file.text"/></label>
										</logic:notPresent>
								
							</logic:notPresent>
						
					<br/><br/>	
					</p>
					
				</div>
			
	   		</div>
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