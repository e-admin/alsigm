<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><bean:message key="head.title"/></title>
	<meta http-equiv="Content Type" content="text/html; charset=iso-8859-1" />
	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos.css"/>'/>
	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'/>
	<![endif]-->

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'/>
	<![endif]-->

	<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/RegisterAPI.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../../dwr/engine.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../../dwr/util.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>

	<script type="text/javascript">

        function errorHandler(message, exception) {
            jAlert(message, '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
        }

		function initValues() {
			var defBookId = '<bean:write name="defaultForm" property="property(BOOK_ID)"/>';
			var defOfficeCode = '<bean:write name="defaultForm" property="property(OFFICE_CODE)"/>';
			loadBooks(defBookId, defOfficeCode);
		}

		function loadBooks(defaultBookId, defaultOfficeCode) {
			RegisterAPI.getBooks(function(data) {
				if (data) {
					DWRUtil.removeAllOptions("bookSelect");
					DWRUtil.addOptions("bookSelect", data, "key", "value");
					if (defaultBookId) {
						$("#bookSelect").val(defaultBookId);
					}
					loadOffices($("#bookSelect").val(), defaultOfficeCode);
				}
			});
		}

		function loadOffices(bookId, defaultOfficeCode) {
			RegisterAPI.getOffices(bookId, function(data) {
				if (data) {
					DWRUtil.removeAllOptions("officeSelect");
					DWRUtil.addOptions("officeSelect", data, "key", "value");
					if (defaultOfficeCode) {
						$("#officeSelect").val(defaultOfficeCode);
					}
				}
			});
		}

		$(document).ready(function() {

            // Establecer el gestor de errores
            dwr.engine.setErrorHandler(errorHandler);

			$("#btnOk").click(function() {
				// Deshabilitar el click de los botones
				$("#btnOk").unbind('click');
				$("#btnCancel").unbind('click');
				document.defaultForm.submit();
				showMsgInProgress();
			});

			$("#btnCancel").click(function() {
				<ispac:hideframe/>
			});

			$("#bookSelect").change(function() {
				$("#bookSelect option:selected").each(function () {
					loadOffices($(this).val());
				});
			});

			$("#orgUnitCode").blur(function() {
				$("#orgUnitName").empty();
				RegisterAPI.getOrganization($(this).val(), function(data) {
					if (data) {
						$("#orgUnitName").val(data.value);
					}
				});
			});

			initValues();

			$("#contenido").draggable();
		});


		function showMsgInProgress(){
			document.getElementById('inProgress').style.display='block';
			document.getElementById('bookSelect').disabled=true;
			document.getElementById('officeSelect').disabled=true;
			document.getElementById('orgUnitCode').disabled=true;
		}

	</script>

</head>
<body onload="javascript:positionMiddleScreen('contenido');">

	<div id="contenido" class="move">

		<div class="ficha">

			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="registro.salida.form.title"/></h4>
					<div class="acciones_ficha">
						<a href="#" id="btnOk" class="btnOk"><bean:message key="common.message.ok"/></a>
						<a href="#" id="btnCancel" class="btnCancel"><bean:message key="common.message.cancel"/></a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">

					<div class="cabecera_seccion">
						<h4><bean:message key="registro.salida.form.subtitle"/></h4>
					</div>

					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent"/>
						</div>
					</logic:messagesPresent>

					<div class="infoError">
						<logic:notEmpty name="documentsRegistered">
							<bean:message key="forms.errors.register.registered"/>:<br/>
							<logic:iterate name="documentsRegistered" id="doc" type="ieci.tdw.ispac.api.item.IItem">
								- <bean:write name="doc" property="String(NOMBRE)"/>, <bean:write name="doc" property="String(DESCRIPCION)"/><br/>
							</logic:iterate>
						</logic:notEmpty>
						<logic:notEmpty name="documentsEntrada">
							<br/><bean:message key="forms.errors.register.tiporeg.entrada"/>:<br/>
							<logic:iterate name="documentsEntrada" id="doc" type="ieci.tdw.ispac.api.item.IItem">
								- <bean:write name="doc" property="String(NOMBRE)"/>, <bean:write name="doc" property="String(DESCRIPCION)"/><br/>
							</logic:iterate>
						</logic:notEmpty>
						<logic:notEmpty name="documentsSinDestino">
							<br/><bean:message key="forms.errors.register.sinDestino"/>:<br/>
							<logic:iterate name="documentsSinDestino" id="doc" type="ieci.tdw.ispac.api.item.IItem">
								- <bean:write name="doc" property="String(NOMBRE)"/>, <bean:write name="doc" property="String(DESCRIPCION)"/><br/>
							</logic:iterate>
						</logic:notEmpty>
					</div>


					<c:set var="action" value="${requestScope.action}"/>
					<jsp:useBean id="action" type="java.lang.String"/>



					<html:form action="<%=action%>" method="post">

						<input type="hidden" name="entityAppName" value="RegistroSalida">

						<input type="hidden" name="method" value="create"/>
						<html:hidden property="property(SPAC_DT_DOCUMENTOS:ID)"/>
						<html:hidden property="property(SPAC_DT_DOCUMENTOS:NOMBRE)"/>
						<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO_ID)"/>
						<html:hidden property="property(SPAC_DT_DOCUMENTOS:DESTINO)"/>

						<logic:present name="docIds" scope="request">
							<c:set var="docIds" value="${requestScope.docIds}"/>
							<jsp:useBean id="docIds" type="java.lang.String"/>
							<html:hidden property="property(multibox)" value="<%=docIds%>"/>
						</logic:present>

						<p>
							<label class="popUp"><nobr><bean:message key="registro.salida.form.field.book"/>:</nobr></label>
							<html:select styleId="bookSelect" property="property(BOOK_ID)">
							</html:select>
							<html:errors property="property(BOOK_ID)"/>
						</p>

						<p>
							<label class="popUp"><nobr><bean:message key="registro.salida.form.field.office"/>:</nobr></label>
							<html:select styleId="officeSelect" property="property(OFFICE_CODE)">
							</html:select>
							<html:errors property="property(OFFICE_CODE)"/>
						</p>

						<p class="clearfix">
							<label class="popUp"><nobr><bean:message key="registro.salida.form.field.orgUnit"/>:</nobr></label>
							<html:text styleId="orgUnitCode" styleClass="asociado" property="property(ORG_UNIT_CODE)" size="10"/>
							<html:textarea styleId="orgUnitName" property="property(ORG_UNIT_NAME)" cols="50" rows="2"
								styleClass="inputReadOnly" readonly="true"/>
							<html:errors property="property(ORG_UNIT_CODE)"/>
						</p>
					<!--  Capa para presentar el mensaje de Operacion en progreso al realizar la firma -->
					<div id="inProgress" style="display:none; text-align: center">
						<b><bean:message key="msg.layer.operationInProgress" /></b>
					</div>

					</html:form>

				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->

	<ispac:layer id="waitOperationInProgress" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />

</body>
</html>