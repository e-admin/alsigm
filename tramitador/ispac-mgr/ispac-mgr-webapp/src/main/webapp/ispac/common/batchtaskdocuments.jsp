<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
     <link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>

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
<SCRIPT>
function doRedirectURL(url){
	frm = document.getElementById('frmActual');
	frm.action = url;
	doSubmit();
}
function doSubmit(){
	document.forms[0].submit();
}
function actualizaVistaDesdeTramites(){
	document.getElementById('tpDocId').value='';
	document.getElementById('template').value='';
	doSubmit();
}
function actualizaVistaDesdeTpDocs(){
	document.getElementById('template').value='';
	doSubmit();
}
function actualizaVistaDesdeTemplates(){
	doSubmit();
}
function editTemplate()
{
	tipoDocument = document.getElementById('tpDocId').value;
	templateId = document.getElementById('template').value;
	url = '<%= request.getContextPath()%>'	+'/batchTaskDocuments.do?documentId='+tipoDocument+'&templateId='+templateId+'&tipoAccion=edit';
	doRedirectURL(url);
}
function generateDocument(){
	tipoDocument = document.getElementById('tpDocId').value;
	templateId = document.getElementById('template').value;
	/*cadenaMensajes="<bean:message key="element.noSelect"/>"+","+ "<bean:message key="common.alert"/>"+","+ "<bean:message key="common.message.ok"/>"+","+ "<bean:message key="common.message.cancel"/>";*/
	url = '<%= request.getContextPath()%>'	+'/generateDocumentsFromBatch.do?documentId='+tipoDocument+'&templateId='+templateId;
	doRedirectURL(url);
	showMsgInProgress();
}

function showMsgInProgress(){
	document.getElementById('inProgress').style.display='block';
	document.getElementById('btnOk').disabled=true;
	document.getElementById('btnCancel').disabled=true;
	document.getElementById('btnEditTemplate').disabled=true;
	obj = document.getElementById('print')
	if (obj != null){
		obj.disabled=true;
	}

}

</SCRIPT>
<%-- Arranca Word con la plantilla --%>
<logic:equal name="batchTaskForm" property="tipoAccion" value="edit">
	<%-- Identificador del documento --%>
	<bean:define name="batchTaskForm" property="file" id="file" />
	<%-- Arranca Word con la plantilla --%>
	<ispac:edittemplate template='<%= file.toString() %>'/>
</logic:equal>
<html:form styleId="frmActual" action="generateDocumentsFromBatch.do">
<html:hidden property="batchTaskId" />
<html:hidden property="tipoAccion" value=''/>
<html:hidden property="file" />
<html:hidden styleId="template" property="template" />
<html:hidden styleId="tpDocId" property="tpDocId" />
<html:hidden property="multiboxString" />
<html:hidden property="taskIdsString" />
<html:hidden property="taskPcdId" />


<div id="contenido" class="move" >
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="prepare.documents.title"/></h4>
				<div class="acciones_ficha">
					<html:button styleId="btnOk" property="generarDocumento" styleClass="btnOk" onclick="javascript:generateDocument();">
							<bean:message key="common.message.ok"/>
					</html:button>
					<input id="btnCancel" type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" onclick='<ispac:hideframe refresh="true"/>'/>
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
			<p class="center" >
			<html:button styleId="btnEditTemplate" property="editarPlantilla" styleClass="form_button_white" onclick="javascript:editTemplate();">
						<bean:message key="forms.batchTaskForm.editarPlantilla"/>
			</html:button>
				</p>
			<script language="javascript">
											//<!--
												if (isIEWord()) {
													document.write("<p class='center'><input id='print' type='checkbox' name='print' value='on'/>");
													document.write('<bean:message key="forms.batchTaskForm.imprimirDocumento"/>');
													document.write("</p>");
												}
											//-->
			</script>

					<!--  Capa para presentar el mensaje de Operacion en progreso al realizar la firma -->
					<div id="inProgress" style="display:none; text-align: center">
						<b><bean:message key="msg.layer.operationInProgress" /></b>
					</div>


			</div>
		</div>
	</div>
</div>



</html:form>

<script>

positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});

</script>
