<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="ThirdPartyList" value="${requestScope.ThirdPartyList}"/>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title><bean:message key="terceros.title"/></title>
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
		<script type="text/javascript"
		src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
		<script type="text/javascript"
		src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
		<ispac:javascriptLanguage/>
		<script>
			function initValues(){
				
				document.thirdPartyForm.nombre.value ='<%=request.getAttribute("nombre")%>' ;
				document.thirdPartyForm.apellido1.value ='<%=request.getAttribute("apellido1")%>' ;
				document.thirdPartyForm.apellido2.value ='<%=request.getAttribute("apellido2")%>' ;
			}
			
			

		$(document).ready(function() {
			$("#contenido").draggable();
			 positionMiddleScreen('contenido');
		});
		
		 

		</script>
	
	</head>
	<body onload="javascript:showParentFrame('workframe'); initValues();">
	<div id="contenido" class="move" >
	<html:form action="searchThirdParty.do?searchByNombre=1" >
		
			<div class="ficha">
				<div class="encabezado_ficha">
					<div class="titulo_ficha">
						<h4><bean:message key="select.thirdParty.title" /></h4>
						<div class="acciones_ficha">
							<html:submit styleId="submit" styleClass="btnSearch" >
													<bean:message key="search.button.search"/>
							</html:submit>
							<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe/>'/>				
						</div><%--fin acciones ficha --%>
					</div><%--fin titulo ficha --%>
				</div><%--fin encabezado ficha --%>
	
				<div class="cuerpo_ficha">
					<div class="seccion_ficha">
						<input type="hidden" name="parameters" value='<c:out value="${param.parameters}"/>'/>
						<input type="hidden" name="field" value='<c:out value="${param.field}"/>'/>
						<input type="hidden" name="searchByNombre" value='<c:out value="${param.searchByNombre}"/>'/>
						<input type="hidden" name="id" />
						<input type="hidden" name="defaultValues" value='<c:out value="${param.defaultValues}"/>'/>
							
							<p class="fila">
									<label class="mid"><nobr><bean:message key="forms.searchthirdparty.title.nombre" />:</nobr></label>
									<input class="input" type="text" name="nombre" size="50"/>
							</p>
							
							
							<p class="fila">				
								<label class="mid"><nobr><bean:message key="forms.searchthirdparty.title.apellido1"  />:</nobr></label>
								<input class="input" type="text" name="apellido1" size="50"/>
							</p>
							
							<p class="fila">			
								<label class="mid"><nobr><bean:message key="forms.searchthirdparty.title.apellido2" />:</nobr></label>
								<input class="input" type="text" name="apellido2" size="50"/>	
							</p>
										
					</div>
				</div> 
			</div>       			
		</html:form>
	</div>
</body>

</html>