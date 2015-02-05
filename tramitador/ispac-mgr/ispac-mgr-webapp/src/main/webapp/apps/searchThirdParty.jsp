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

		<script language='JavaScript' type='text/javascript'>
		//<!--
		
			function selectThirdParty(id) {
				document.thirdPartyForm.id.value = id;
				document.thirdPartyForm.submit();
			}
			$(document).ready(function() {
				$("#contenido").draggable();
				 positionMiddleScreen('contenido');
			});
			
		//--></script>
	</head>
	<body onload="javascript:showParentFrame('workframe')">
		<div id="contenido" class="move">
		
			
			<c:url value="searchThirdParty.do" var="displayTagURI">
				<c:param name="field"  value="${param.field}"></c:param>
				<c:param name="parameters" value="${param.parameters}"></c:param>
				<c:param name="setAction" value="searchThirdParty.do"></c:param>
				<c:param name="searchAction" value="searchThirdParty.do"></c:param>
				<c:param name="nombre" value="${param.nombre}"></c:param>
				<c:param name="apellido1"  value="${param.apellido1}"></c:param>
				<c:param name="apellido2" value="${param.apellido2}" ></c:param>
				<logic:present name="searchByNombre"  >
					<c:param name="searchByNombre"  value="1"></c:param>
				</logic:present>
			</c:url>
			<jsp:useBean id="displayTagURI" type="java.lang.String"/>
			<c:url value="searchThirdParty.do" var="returnURI">
				<c:param name="field"  value="${param.field}"></c:param>
				<c:param name="search">1</c:param>
				<c:param name="parameters" value="${param.parameters}"></c:param>
				<c:param name="nombre" value="${param.nombre}"></c:param>
				<c:param name="apellido1"  value="${param.apellido1}"></c:param>
				<c:param name="apellido2" value="${param.apellido2}" ></c:param>
			</c:url>
			<jsp:useBean id="returnURI" type="java.lang.String"/>
		
		
			<div class="ficha">
				<div class="encabezado_ficha">
					<div class="titulo_ficha">
						<h4><bean:message key="select.thirdParty.title"/></h4>
						<div class="acciones_ficha">
							<logic:present name="searchByNombre">
								<a target="_self" href='<%=returnURI%>' class="btnReturn"><bean:message key="common.message.return"/></a>
							</logic:present>
							<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe/>'/>
						</div><%--fin acciones ficha --%>
					</div><%--fin titulo ficha --%>
				</div><%--fin encabezado ficha --%>
	
				<div class="cuerpo_ficha">
					<div class="seccion_ficha">
							<html:form action="searchThirdParty.do">
								<input type="hidden" name="parameters" value='<c:out value="${param.parameters}"/>'/>
								<input type="hidden" name="id" />
								<input type="hidden" name="nif" value='<c:out value="${requestScope.nif}"/>'/>
								<input type="hidden" name="defaultValues" value='<c:out value="${param.defaultValues}"/>'/>
					        	<input type="hidden" name="nombre" value='<c:out value="${param.nombre}"/>'/>
								<input type="hidden" name="apellido1" value='<c:out value="${param.apellido1}"/>'/>
								<input type="hidden" name="apellido2" value='<c:out value="${param.apellido2}"/>'/>
								<input type="hidden" name="field" value='<c:out value="${param.field}"/>'/>
								
								<c:if test="${!empty param.searchByNombre}">
									<input type="hidden" name="searchByNombre" value='<c:out value="${param.searchByNombre}"/>'/>
								</c:if>
							
								<display:table name="ThirdPartyList" 
											   id="thirdParty"
											   class="sortable"
											   style="width: 100%;"
											   pagesize="15"
											   requestURI='<%=displayTagURI%>'>

									<display:column titleKey="forms.exp.title.nif"
													headerClass="title"
													sortable="true"
													class="element"
													style="width: 15%;">
							              	<a class="element" href='<c:out value="javascript:selectThirdParty(\"${thirdParty.idExt}\");"/>'>
					              					<c:out value="${thirdParty.identificacion}"/>
								            </a>
									</display:column>
									<display:column titleKey="forms.searchthirdparty.identidad"
													headerClass="title"
													sortable="true"
													class="element"
													style="width: 50%;">
							              	<a class="element" href='<c:out value="javascript:selectThirdParty(\"${thirdParty.idExt}\");"/>'>
					              					<c:out value="${thirdParty.nombreCompleto}"/>
								            </a>
									</display:column>
							   </display:table>
							</html:form>	        		
					</div>
				</div>
			</div>
			</div>
	</body>

</html>