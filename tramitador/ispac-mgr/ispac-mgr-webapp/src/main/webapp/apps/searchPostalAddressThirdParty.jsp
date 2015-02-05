<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="PostalAddressesThirdPartyList" value="${appConstants.actions.THIRDPARTY_POSTAL_ADDRESSES_LIST}"/>
<jsp:useBean id="PostalAddressesThirdPartyList" type="java.lang.String"/>

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
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
 		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	    <ispac:javascriptLanguage/>
	
	</head>
	<body onload="javascript:showParentFrame('workframe')">
		<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="forms.searchthirdparty.title.postal.address"/></h4>
					<div class="acciones_ficha">		
						<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe/>'/>					
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
					<html:form action="setPostalAddress.do">
						<br/>
						<input type="hidden" name="parameters" value='<c:out value="${param.parameters}"/>'/>
						<input type="hidden" name="id" />
						<display:table name='<%=PostalAddressesThirdPartyList%>'
									   id="postalAddress"
									   class="sortable"
									   style="width: 100%;">
							<display:column titleKey="forms.exp.title.postal.address"
											headerClass="title"
											sortable="true"
											class="element"
											style="width: 55%;">
					     		<a class="element" href='<c:out value="javascript:selectPostalAddressesThirdParty(\"${postalAddress.id}\");"/>'>
			              			<c:out value="${postalAddress.direccionPostal}"/>
						    	</a>
							</display:column>
							<display:column titleKey="forms.exp.title.region"
											property="provincia"
											headerClass="title"
											sortable="true"
											class="element"
											style="width: 15%;"/>
							<display:column titleKey="forms.exp.title.city"
											property="municipio"
											headerClass="title"
											sortable="true"
											class="element"
											style="width: 15%;"/>
							<display:column titleKey="forms.exp.title.cpostal"
											property="codigoPostal"
											headerClass="title"
											sortable="true"
											class="element"
											style="width: 15%;"/>
					   </display:table>
					   <br/>
					</html:form>		
				</div>
	   		</div>
		</div>
	</div>
</body>
</html>

<script language='JavaScript' type='text/javascript'>
		//<!--
			function selectPostalAddressesThirdParty(id) {
		
				document.defaultForm.id.value = id;
				document.defaultForm.submit();
			}
		$(document).ready(function(){
			$("#contenido").draggable();
		});
		
		positionMiddleScreen('contenido');
		//--></script>

</script>