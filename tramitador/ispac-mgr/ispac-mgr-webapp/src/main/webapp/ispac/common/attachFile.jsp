<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>


    <script>
		function submitform() {
			document.searchForm.submit();
		}

		function limitAttach(form, file) {

			document.getElementById('btnOk').disabled = true;

			allowSubmit = false;
			if (!file) {
				jAlert('<bean:message key="msg.error.upload.noFile"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				document.getElementById('btnOk').disabled = false;
				return false;
			}

			while (file.indexOf("\\") != -1) {
				file = file.slice(file.indexOf("\\") + 1);
			}

			// Nombre del fichero a subir en un campo oculto
			document.forms[0].theFileName.value = file;

			// Se muestra la capa de operacion en progreso
			// y se oculta la capa del contenido movible
			document.getElementById('contenido').style.visibility = "hidden";
			document.body.scrollTop = 0;
			showLayer("waitOperationInProgress");
			
			document.getElementById('btnOk').disabled = false;
		}
   	</script>
  </head>

  <body>

	<div id="contenido" class="move">

		<div class="ficha">
		<html:form action="uploadFile.do" styleId="withoutPadding" method="post" enctype="multipart/form-data" styleClass="withoutPadding">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><nobr><bean:message key="upload.title"/></nobr></h4>
					<div class="acciones_ficha">

						<html:submit styleClass="btnOk" styleId="btnOk" onclick="javascript: return limitAttach(this.form, this.form.theFile.value);">
							<bean:message key="common.message.ok"/>
						</html:submit>
						<a href="#" id="btnCancel" onclick='<ispac:hideframe/>' class="btnCancel">
							<bean:message key="common.message.close" />
						</a>
						<html:hidden property="theFileName"/>
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
					<p>
						<label class="popUp"><nobr><bean:message key="upload.text"/></nobr></label>
						<div class="rightInput">
						<html:file property="theFile" size="60" styleClass="input"/>
						</div>
						<logic:present name="documentTypeId">
								<input type="hidden" name="documentTypeId" value='<bean:write name="documentTypeId"/>'/>
						</logic:present>
					</p>
				</div>
				</html:form>
	   		</div>
		</div>
	</div>
 </div>

  <ispac:layer id="waitOperationInProgress" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />

  </body>

</html>
<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
		$("#waitOperationInProgress").draggable();
	});
</script>
