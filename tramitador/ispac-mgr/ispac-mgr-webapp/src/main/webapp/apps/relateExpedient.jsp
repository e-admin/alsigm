<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="relations" value="${requestScope['LIST_RELATIONS']}"/>
<bean:define id="relations" name="relations" scope="page"/>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title><bean:message key="forms.expRelacionados.title"/></title>
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
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
		<ispac:javascriptLanguage/>
	
	</head>
	<body>
<div id="contenido" class="move padreFramesAnidados">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="forms.expRelacionar.title"/></h4>
				<div class="acciones_ficha">
					<a href="#" id="btnOk" onclick='javascript:relate()' class="btnOk">
						<bean:message key="common.message.ok" />
					</a>
					<a href="#" id="btnCancel"onclick='<ispac:hideframe refresh="true"/>' class="btnCancel">
						<bean:message key="common.message.close" />
					</a>	
				</div>
			</div>
	 	</div>
	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
			<html:form action='/relateExpedient.do?method=relate'>
			
				<logic:messagesPresent>
					<html:messages id="message">
						<div class="infoError"><bean:write name="message" /></div>
					</html:messages>
				</logic:messagesPresent>
				<p>
					<label class="popUp"><nobr><bean:message key="forms.expRelacionar.expediente"/>:</nobr></label>
					
					<ispac:htmlTextImageFrame property="property(NUMEXP_HIJO)"
						readonly="false"
						readonlyTag="false"
						styleClass="inputSelectS"
						id="SELECT_NUMEXP_HIJO"
						target="workframesearch"
						action="relateExpedient.do?method=form"
						image="img/search-mg.gif" 
						titleKeyLink="forms.expRelacionados.search.title" 
						showFrame="true"
						width="620"
						height="460"
						showDelete="false"
						jsShowFrame="hideShowFrame"
						>																																																
					</ispac:htmlTextImageFrame>
				</p>
				<p>
					<label class="popUp"><nobr><bean:message key="forms.expRelacionar.tipoRelacion"/>:</nobr></label>
					<html:select property="property(TIPO_RELACION)" styleClass="inputSelectS" onchange="javascript:checkType(this)">
						<html:option value="" key="title.link.data.selection"></html:option>
						<html:options collection="relations" property="property(ID)" labelProperty="property(VALOR)"/>
					</html:select>
				</p>
				<p>
					<label class="popUp"><nobr><bean:message key="forms.expRelacionar.otraRelacion"/>:</nobr></label>
					<html:text property="property(RELACION)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
				</p>	
				
				</html:form>																
		</div>
	</div>
</div>
<div>	
<ispac:layer />

 <ispac:frame id="workframesearch" />
  



</body>
</html>


<script language='JavaScript' type='text/javascript'><!--

			function checkType(select) {
				if (select) {
					var form = select.form;
					if (form) {
						var element = form.elements["property(RELACION)"];
						if (element) {
							var type = select.options[select.selectedIndex].value;
							if (type=='') {
								element.className = "inputSelectS";
								element.readOnly = false;
							} else {
								element.value = "";
								element.className = "inputReadOnlySelectS";
								element.readOnly = true;
							}
						}						
					}
				}
			}
			
			function relate() {
				document.searchForm.target = "_self";
				document.searchForm.action = '<c:url value="/relateExpedient.do?method=relate"/>';
				document.searchForm.submit();
			}
		function  hideShowFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {
			document.getElementById("contenido").style.visibility="hidden";
			document.getElementById("contenido").style.top="0px";
			document.getElementById("contenido").style.left="0px";
			document.getElementById("workframesearch").style.display="block";
			
			showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) ;
			
			
		}
		
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	

	
	checkType(document.searchForm.elements[ 'property(TIPO_RELACION)' ]);
//--></script>
	

												




