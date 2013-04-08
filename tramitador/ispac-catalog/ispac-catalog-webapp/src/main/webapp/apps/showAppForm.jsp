<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>






<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:write name="Caption"/></h4>
		<div class="acciones_ficha">
				<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
			
		</div>
	</div>
</div>




<div id="navmenu">
	<tiles:insert page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<html:form action="uploadCTAppForm.do" method="post" enctype="multipart/form-data">

	<html:hidden property="key"/>
	<input type="hidden" name="upload"/>
	
	<div id="stdform">
	
		<table border="0" cellspacing="0" cellpadding="0">
		
			<logic:present name="upload2Form" property="key">
				<tr>
					<td height="20px" class="formsTitle" width="1%" align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				</tr>
				<tr>
					<td height="20px">
						<c:url value="getCTAppForm.do" var="_link">
							<c:param name="appId">
								<bean:write name="upload2Form" property="key" />
							</c:param>
						</c:url>
						<a class="form_button_white" href='<c:out value="${_link}"/>' class="tdlink"><bean:message key="entityManage.title.show.form.downloadDesign"/></a>
					</td>
				</tr>
			</logic:present>
			<tr>
				<td height="20px" class="formsTitle" width="1%" align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
			</tr>
			<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
			<tr>
				<td height="20px" class="formsTitle">
					<bean:message key="entityManage.title.show.form.newDesign"/>
					<html:file property="uploadFile" size="30" styleClass="input"/>
					<html:submit styleClass="form_button_white" onclick="javascript: return uploadFileSubmit('design');">
						<bean:message key="entityManage.title.show.form.update"/>
					</html:submit>
				</td>
			</tr>
			<tr>
				<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/><hr/></td>
			</tr>
			</ispac:hasFunction>
			<logic:present name="upload2Form" property="key">
				<tr>
					<td height="20px" class="formsTitle" width="1%" align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
				</tr>
				<tr>
					<td height="20px">
						<c:url value="getCTAppForm.do?unload=code" var="_link">
							<c:param name="appId">
								<bean:write name="upload2Form" property="key" />
							</c:param>
						</c:url>
						<a class="form_button_white" href='<c:out value="${_link}"/>' class="tdlink"><bean:message key="entityManage.title.show.form.downloadCode"/></a>
					</td>
				</tr>
			</logic:present>
			<tr>
				<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
			</tr>
			<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
			<tr>
				<td height="20px" class="formsTitle">
					<bean:message key="entityManage.title.show.form.newCode"/>
					<html:file property="uploadFile2" size="30" styleClass="input"/>
					<html:submit styleClass="form_button_white" onclick="javascript: return uploadFileSubmit('code');">
						<bean:message key="entityManage.title.show.form.update"/>
					</html:submit>		
				</td>
			</tr>
			<tr>
				<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
			</tr>
			</ispac:hasFunction>
		</table>
		
	</div>

</html:form>

</div>

<script language='JavaScript' type='text/javascript'>


	function uploadFileSubmit(uploadValue)
	{
		document.forms[0].upload.value = uploadValue;
	}
	
	$(document).ready(function() {
			
			$("#move").draggable();
			
			$("#btnCancel").click(function() {
				<ispac:hideframe />
			});
			
		});

</script>