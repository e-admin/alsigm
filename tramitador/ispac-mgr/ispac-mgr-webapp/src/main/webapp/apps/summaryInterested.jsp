<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="ThirdPartyBean" value="${appConstants.actions.THIRDPARTY}" />
<jsp:useBean id="ThirdPartyBean" type="java.lang.String" />


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title><bean:message key="terceros.title" /></title>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
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
</head>
<body onload="javascript:showParentFrame('workframe')">



<div id="contenido" class="move">
		<c:url value="searchThirdParty.do" var="returnURI">
				<c:param name="field"  value="${param.field}"></c:param>
				<c:param name="parameters" value="${param.parameters}"></c:param>
				<c:param name="nombre" value="${param.nombre}"></c:param>
				<c:param name="apellido1"  value="${param.apellido1}"></c:param>
				<c:param name="apellido2" value="${param.apellido2}" ></c:param>
				<c:param name="nif" value="${param.nif}" ></c:param>
				<c:if test="${!empty param.searchByNombre}">
					<c:param name="searchByNombre"  value="${param.searchByNombre}"></c:param>
				</c:if>
		</c:url>
		<jsp:useBean id="returnURI" type="java.lang.String"/>
		<div class="ficha"> 
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="select.thirdParty.title" /></h4>
					<div class="acciones_ficha">
					
						<logic:present name="return">
								<td style="text-align:right">
									<a target="_self" href='<%=returnURI%>' class="btnReturn"><bean:message key="common.message.return"/></a>
								</td>
								<td>
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
								</td>
						</logic:present>
						<input type="button"
									value='<bean:message key="common.message.ok"/>'
									class="btnOk" 
									onclick='javascript:document.thirdPartyForm.submit();' />
						<input type="button"
									value='<bean:message key="common.message.cancel"/>'
									class="btnCancel" 
									onclick='<ispac:hideframe/>' />
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				
					<div class="cabecera_seccion">
						<h4><bean:message key="select.thirdParty.subtitle"/></h4>
					</div>

					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent"/>
						</div>
					</logic:messagesPresent>
				<html:form action="setInterested.do">
							<input type="hidden" name="parameters" value='<c:out value="${param.parameters}"/>' />
							<input type="hidden" name="id" value='<bean:write name="<%=ThirdPartyBean%>" property="idExt" />'/>
							<input type="hidden" name="nombre"/> 
                            <input type="hidden" name="apellido1"/> 
							<input type="hidden" name="apellido2"/> 
							 <input type="hidden" name="nif"/> 
							<label class="menuhead"><bean:message key="forms.searchthirdparty.title.name"/>: <b><bean:write name='<%=ThirdPartyBean%>' property="nombreCompleto" /></b></label>
							<br/>
							<label class="menuhead"><bean:message key="forms.searchthirdparty.title.nifCif"/>: <b><bean:write name='<%=ThirdPartyBean%>' property="identificacion" /></b></label>
							<br/><br/>
							<label class="menuhead"><bean:message key="forms.searchthirdparty.title.postal.address"/></label>

							<display:table name='<%=ThirdPartyBean+".direccionesPostales"%>'
								id="postalAddress" class="sortable" style="width: 100%;">
								<display:column titleKey="forms.exp.title.postal.address"
									headerClass="title" sortable="true" class="element"
									style="width: 55%;">
									<c:choose>
										<c:when test="${postalAddress_rowNum == 1}">
											<input type="radio" name="postalAddressId" checked="checked"
												value='<%=((ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter)postalAddress).getId()%>' />
											<c:out value="${postalAddress.direccionPostal}" />
										</c:when>
										<c:otherwise>
											<input type="radio" name="postalAddressId"
												value='<%=((ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter)postalAddress).getId()%>' />
											<c:out value="${postalAddress.direccionPostal}" />
										</c:otherwise>
									</c:choose>
								</display:column>
								<display:column titleKey="forms.exp.title.region"
									property="provincia" headerClass="title" sortable="true"
									class="element" style="width: 15%;" />
								<display:column titleKey="forms.exp.title.city"
									property="municipio" headerClass="title" sortable="true"
									class="element" style="width: 15%;" />
								<display:column titleKey="forms.exp.title.cpostal"
									property="codigoPostal" headerClass="title" sortable="true"
									class="element" style="width: 15%;" />
							</display:table>
							<br/><br/>
							<label class="menuhead"><bean:message key="forms.searchthirdparty.title.electronic.address"/></label>
							<display:table name='<%=ThirdPartyBean+".direccionesElectronicas"%>'
								id="electronicAddress" class="sortable" style="width: 100%;">
								<display:column titleKey="forms.exp.title.electronic.address"
									headerClass="title" sortable="true" class="element"
									style="width: 55%;">
									<c:choose>
										<c:when test="${electronicAddress_rowNum == 1}">
											<input type="radio" name="electronicAddressId"
												checked="checked"
												value='<%=((ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter)electronicAddress).getId()%>' />
											<c:out value="${electronicAddressId.direccion}" />
										</c:when>
										<c:otherwise>
											<input type="radio" name="electronicAddressId"
												value='<%=((ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter)electronicAddress).getId()%>' />
											<c:out value="${electronicAddressId.direccion}" />
										</c:otherwise>
									</c:choose>
									<c:out value="${electronicAddress.direccion}" />
								</display:column>
							</display:table>
							<%--
							<html:submit styleClass="form_button_white" ><bean:message key="common.message.ok"/></html:submit>
							--%>
						</html:form>
					
					
					
					
				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->
	
	

</div>
</body>
</html>
<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>