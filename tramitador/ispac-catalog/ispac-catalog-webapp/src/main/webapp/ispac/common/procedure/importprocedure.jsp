<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="ieci.tdw.ispac.ispaccatalog.action.procedure.ImportProcedureAction"%>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script language='JavaScript' type='text/javascript'><!--

	function activateRadios() {

		radio = document.uploadForm.elements[ 'property(CREATE_AS)' ];
		radio[0].disabled = false;
		radio[0].checked = true;
		radio[1].disabled = false;
	}

	function deleteProcedureParent() {

		if (document.uploadForm.elements[ 'property(ID_PCD_PARENT)' ].value != '') {

			document.uploadForm.elements[ 'property(ID_PCD_PARENT)' ].value = '';
			document.uploadForm.elements[ 'property(ID_PCD_PARENT_NAME)' ].value = '';

			radio = document.uploadForm.elements[ 'property(CREATE_AS)' ];
			radio[0].disabled = true;
			radio[0].checked = false;
			radio[1].disabled = true;
			radio[1].checked = false;
		}
	}

//--></script>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="catalog.importpcd"/></h4>
		<div class="acciones_ficha">

			<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.import"/></a>
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>



<%--
<div id="helpLink">
	<ispac:onlinehelp fileName="newProcedure" image="img/help.gif" titleKey="title.help"/>
</div>
--%>

<div id="navSubMenuTitle">
	<%--
	<bean:message key="procedure.import.subtitle"/>
	--%>
</div>


<%--

<div id="navmenu">
	<ul class="actionsMenuListWithoutBorder">

		<li>
			<a href="#" id="btnOk" class="btnOk"><bean:message key="forms.button.import"/></a>
		</li>
		<li>

			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</li>
	</ul>
</div>
--%>

<div id="navmenu">
	<tiles:insert page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<html:form action="importProcedure.do?method=upload" method="post" enctype="multipart/form-data">

	<ispac:message/>

	<div id="stdform">
		<table border="0" cellspacing="0" cellpadding="0" >

			<tr>
				<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
			</tr>

			<tr>
				<td height="20" class="formsTitle" width="1%">
					<ispac:tooltipLabel labelKey="procedure.import.procedure" tooltipKey="procedure.import.procedure.tooltip"/>
				</td>
				<td height="20">
					<html:hidden property="property(ID_PCD_PARENT)"/>
					<nobr><ispac:htmlTextImageFrame property="property(ID_PCD_PARENT_NAME)"
											readonly="true"
											readonlyTag="false"
											propertyReadonly="readonly"
											styleClass="input"
											styleClassReadonly="inputReadOnly"
											size="60"
											id="SELECT_PROCEDURE"
											target="workframe"
											action="selectProcedure.do?codetable=SPAC_P_PROCEDIMIENTOS&codefield=ID&valuefield=NOMBRE&caption=select.rootProcedure.caption&decorator=/formatters/selectproceduretreeuploadformatter.xml"
											image="img/search-mg.gif"
											titleKeyLink="select.rootProcedure"
											showFrame="true"
											width="550"
											height="400"
											jsDelete="deleteProcedureParent">

						<ispac:parameter name="SELECT_PROCEDURE" id="property(ID_PCD_PARENT)" property="ID"/>
						<ispac:parameter name="SELECT_PROCEDURE" id="property(ID_PCD_PARENT_NAME)" property="NOMBRE"/>
						<ispac:parameter name="SELECT_PROCEDURE" id="JAVASCRIPT" property="activateRadios"/>

					</ispac:htmlTextImageFrame></nobr>
				</td>
			</tr>

			<tr>
				<td height="20" class="formsTitle" width="1%">
					<ispac:tooltipLabel labelKey="procedure.import.procedure.create.as" tooltipKey="procedure.import.procedure.create.as.tooltip"/>
				</td>
				<td height="20">
					<span><html:radio disabled="true" style="vertical-align:middle;" property="property(CREATE_AS)" value='<%=ImportProcedureAction.IMPORT_PROCEDURE_AS_CHILD_VALUE%>' /><bean:message key="procedure.import.procedure.create.as.child"/></span><br/>
					<span><html:radio disabled="true" style="vertical-align:middle;" property="property(CREATE_AS)" value='<%=ImportProcedureAction.IMPORT_PROCEDURE_AS_VERSION_VALUE%>' /><bean:message key="procedure.import.procedure.create.as.version"/></span>
				</td>
			</tr>

			<tr>
				<td height="20" class="formsTitle" width="1%">
					<ispac:tooltipLabel labelKey="procedure.import.file" tooltipKey="procedure.import.file.tooltip"/>
				</td>
				<td height="20">
					<html:file property="uploadFile" size="30" styleClass="input"/>
					<input type="button" class="form_button_white" onclick="javascript:upload_submit('<%= request.getContextPath() + "/importProcedure.do?method=validate"%>');" value='<bean:message key="forms.button.validate"/>' />
				</td>
			</tr>

			<tr>
				<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
			</tr>

		</table>
	</div>

</html:form>

<ispac:layer/>
<ispac:rewrite id="waitPage" page="wait.jsp"/>

<div id="dialogworkframe" class="roundTop" style="display:none;z-index:1024">
  <table class="dialog" border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
    <%-- <tr>
      <td width="100%" align="right">
        <img style='margin:4px 4px 4px'
        		 src='<ispac:rewrite href="img/close.png"/>'
        		 onclick="javascript:hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')"/>
      </td>
    </tr>
   --%>
    <tr>
      <td width="100%" height="100%">
        <table class="content"  width="100%" height="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>

      	      	<iframe id="workframe"
      	      				name="workframe"
							frameborder="0"
							margin="5px 5px"
							src='<%=waitPage%>'
							allowTransparency="true"
							style="height:100%;width:100%">

				</iframe>
      	    </td>
      	  </tr>
      	</table>
      </td>
    </tr>
  </table>
</div>
</div>

<script language='JavaScript' type='text/javascript'><!--

	if (document.uploadForm.elements[ 'property(ID_PCD_PARENT)' ].value != '') {

		radio = document.uploadForm.elements[ 'property(CREATE_AS)' ];
		radio[0].disabled = false;
		radio[1].disabled = false;
	}

	function import_submit(url)
	{
		var msgConfirm = "";
		var idPcd = document.uploadForm.elements[ 'property(ID_PCD_PARENT)' ].value;
		if (idPcd != '') {

			if (document.uploadForm.elements[ 'property(CREATE_AS)' ][0].checked) {

				msgConfirm = '<bean:message key="procedure.import.confirm.rootProcedure.begin.child"/>'
						   + document.uploadForm.elements[ 'property(ID_PCD_PARENT_NAME)' ].value
						   + '<bean:message key="procedure.import.confirm.rootProcedure.end"/>';
			}
			else {
				msgConfirm = '<bean:message key="procedure.import.confirm.rootProcedure.begin.version"/>'
						   + document.uploadForm.elements[ 'property(ID_PCD_PARENT_NAME)' ].value
						   + '<bean:message key="procedure.import.confirm.rootProcedure.end"/>';
			}
		}
		else {
			msgConfirm = '<bean:message key="procedure.import.confirm.root"/>';
		}
		jConfirm(msgConfirm, '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(!r){

			return;
		}
		else{

			divAppErrors = document.getElementById('appErrors');
			if (divAppErrors != null) {
				divAppErrors.style.display = "none";
			}
			upload_submit(url);
		 }

	});

	}

	function upload_submit(url)
	{
  	   	ispac_needToConfirm=false;
		document.uploadForm.action = url;
		try {
			document.uploadForm.submit();
	  	}
	  	catch(e) {
	  		jAlert('<bean:message key="error.formFile.invalidPath"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
	  	}
		ispac_needToConfirm=true;
	}

	$(document).ready(function() {

			$("#move").draggable();

			$("#btnOk").click(function() {
				import_submit('<%= request.getContextPath() + "/importProcedure.do?method=upload"%>');
			});

			$("#btnCancel").click(function() {
				<ispac:hideframe id='workframerefresh' refresh='true'/>
			});



			$("#contenido").draggable();
		});


//--></script>