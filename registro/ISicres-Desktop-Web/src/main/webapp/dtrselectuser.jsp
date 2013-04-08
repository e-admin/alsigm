<%@ page language="java" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>

<html>
	<head>
		<title>&nbsp;</title>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/tablebar.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript">
			top.Idioma = top.GetDlgParam(4);
			//obtenemos el valor de la variable top.GetDlgParam(6)
			//que nos indica si la pantalla origen es redistribuir o distribucion manual
			var redistribuir = top.GetDlgParam(6);

			if(top.redistribuir){
				document.title = top.GetIdsLan( "IDS_BTN_REDISTRIBUIR" );
			} else {
				document.title = top.GetIdsLan( "IDS_BTNDIST" );
			}
		</SCRIPT>
		<script language="javascript">
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css">
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css">
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css">
		<script language="javascript">
			var caseSensitive = top.GetDlgParam(5);
			function OnOK()
			{
				if ((document.getElementById("oMensaje").value == null) || (document.getElementById("oMensaje").value.length < 1)){
					alert(top.GetIdsLan("IDS_ERRORNOMSG"));
					document.getElementById("oMensaje").focus();
					return;
				}

				if ((document.getElementById("oSelectDeptGroupUser").value == null) || (document.getElementById("oSelectDeptGroupUser").value.length < 1)){
					alert(top.GetIdsLan("IDS_ERRORNODESTINO"));
					document.getElementById("oSelectDeptGroupUser").focus();
					return;
				}

				if (caseSensitive == 'CS'){
					document.getElementById("oMensaje").value = top.miTrim(document.getElementById("oMensaje").value.toUpperCase());
				} else {
					document.getElementById("oMensaje").value = top.miTrim(document.getElementById("oMensaje").value);
				}
				if (document.getElementById("oMensaje").value.length > 250){
					alert(top.GetIdsLan("IDS_LENGTH_EXCEDEED"));
					document.getElementById("oMensaje").focus();
					return;
				}
				document.getElementById("btnDist").disabled = true;
				document.getElementById("btnCancel").disabled = true;

				var oForm = document.getElementById("frmDistSelectUser");

				//comprobamos desde que pantalla se invoca: si desde la bandeja de distribucion
				// o desde resultado de búsqueda de registros
				if(redistribuir){
					//se invoca desde la bandeja de distribucion
					var typeDest = document.getElementById("oSelectUsersType").value;
					var idDest = document.getElementById("oSelectDeptGroupUser").value;
					var mensaje = document.getElementById("oMensaje").value;

					if(typeDest!=null && idDest!=null){
						//concatenamos los datos y los retornamos en respuesta
						top.returnValue = typeDest + "||" + idDest + "||" + mensaje;
						top.close();
					}
				}else{
					//se invoca desde la búsqueda de registros
					oForm.action = top.GetDlgParam(0) + "/dtrdist.jsp?SessionPId=" + top.g_SessionPId + "&List=" + top.GetDlgParam(3);
					oForm.submit();
					top.returnValue = "";
				}
			}

			function OnCancel()
			{
				top.returnValue = top.g_regDist;
				top.close();
			}

			function CargarTiposUsuarios() {

				var userTypes = document.getElementById("oSelectUsersType");

				if (userTypes != null) {
					// Opcion departamentos (solo para autenticacion invesdoc)
					<%
						UseCaseConf useCaseConf = (UseCaseConf) request.getSession().getAttribute(Keys.J_USECASECONF);
						if (useCaseConf.getUseLdap().booleanValue() == false) { %>

							var opt = document.createElement("OPTION");
							userTypes.appendChild(opt);
							opt.text = top.GetIdsLan( "IDS_USER_DEPARTS" );
							opt.value = 2;

					<%}%>
					// Opcion grupos
					var opt = document.createElement("OPTION");
					userTypes.appendChild(opt);
					opt.text = top.GetIdsLan( "IDS_USER_GROUPS" );
					opt.value = 3;
					opt.selected =true;

					// Opcion usuarios
					var opt = document.createElement("OPTION");
					userTypes.appendChild(opt);
					opt.text = top.GetIdsLan( "IDS_USER_USERS" );
					opt.value = 1;
				}
			}

 			function OnLoad()
			{
				if (caseSensitive == 'CS'){
					document.getElementById("oMensaje").style.textTransform = "uppercase";
				}

				top.XMLHTTPRequestGet(top.GetDlgParam(0) + "/getdeptsgroupsusers.jsp?SessionPId=" + top.ParamValue(document.location.search,"SessionPId")+ "&UserType=3", ResponseLoadDeptsGroupsUsers, true);

				CargarTiposUsuarios();
			}

			function ResponseLoadDeptsGroupsUsers()
			{
				if (top.g_oXMLHTTPRequest.readyState != 4){
					return;
				}

				if (top.g_oXMLHTTPRequest.status != 200){
					alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
				return;
				}

				var XMLText = top.g_oXMLHTTPRequest.responseText;

				if (XMLText.indexOf("alert(") != -1){
					evalAlert(XMLText);
				}
				else{
					var XMLDoc = top.g_oXMLHTTPRequest.responseXML;
					var datas = XMLDoc.documentElement.getElementsByTagName("User");

					if (datas != null){
						for (var i = 0; i < datas.length; i++) {
							var opt = document.createElement("OPTION");

							document.getElementById("oSelectDeptGroupUser").appendChild(opt);

							opt.text = datas[i].getElementsByTagName("Name")[0].firstChild.data;
							opt.value = datas[i].getElementsByTagName("Id")[0].firstChild.data;
							if (i == 0){
								opt.selected =true;
							}
						}
					}

				}
			}
			// Funcion que recarga el formulario de tipos de usuarios
			function CargarUsuarios()
			{
				var oList = document.getElementById("oSelectUsersType");
				var index = document.getElementById("oSelectUsersType").selectedIndex;

				EraseUsers();
				if (oList.options[index].value != "0")  {
					var valor = oList.options[index].text;
					var URL = "getdeptsgroupsusers.jsp?SessionPId=" + top.ParamValue(top.document.location.search,  "SessionPId") + "&UserType=" 							+ oList.options[index].value;

					top.XMLHTTPRequestGet(URL, ResponseLoadDeptsGroupsUsers, true);
				}

				return;
			}

			function EraseUsers()
			{
				for (var i = document.getElementById("oSelectDeptGroupUser").options.length - 1; i >= 0; i--){
					if (document.getElementById("oSelectDeptGroupUser").options[i].value != "0"
					|| (document.getElementById("oSelectDeptGroupUser").options[i].value == "0"
					&& document.getElementById("oSelectDeptGroupUser").options[i].text != "")){
						document.getElementById("oSelectDeptGroupUser").options[i]= null;
					}
				}
			}
			function HabilitarAceptar(oField, oEvent)
			{

				if (top.miTrim(top.Replace(oField.value)) != ""){
					document.getElementById("btnDist").disabled = false;
				}
				else {
					document.getElementById("btnDist").disabled = true;
				}

				return;
			}
			function Response()
			{
				var docFrmUpdate = document.getElementById("frmDistResponse").contentWindow.document;
				var response = docFrmUpdate.body.innerHTML;
				if (response != ""){
					if (response.indexOf("alert(") != -1){
							evalAlert(response);
					}
				}

				docFrmUpdate.body.innerHTML = "";
				document.getElementById("btnDist").disabled = false;
				document.getElementById("btnCancel").disabled = false;
			}

			/**
			 * Busca en una cadena un mensaje de Alert y lo ejecuta.
			 * Se usa para poder mostrar los mensajes de error cuando hay una respuesta que lo incluya, independientemente de su posicion.
			 * @param message Cadena que incluye el mensaje de error.
			 */
			function evalAlert(message) {
				var startAlert = 0;
				var endAler = 0;

				startAlert = message.indexOf("alert(");
				if (startAlert != -1) {
					endAlert = message.indexOf(");", startAlert);
					eval(message.substr(startAlert, endAlert - startAlert + 2));
				}
			}
		</script>

	</head>
	<body tabIndex="-1" onload="OnLoad()">
		<iframe id="frmDistResponse" name="distResponse" SRC="blank.htm" frameborder="0" scrolling="no" style="DISPLAY:none" onload="Response();"></iframe>
		<form name="frmDistSelectUser" id="frmDistSelectUser" method="post" target="distResponse" action="">
			<div id="oDivSelectUser" class="div" style="position:absolute;top:0;left:0;width:100%;height:380;">
				<label class="label" name="oTiposUser" style="position:absolute;top:20;left:10;">
					<script language="javascript">document.write(top.GetIdsLan( "IDS_USERS_TYPE" ));</script>
				</label>
				<select class="combo" name="oSelectUsersType" id="oSelectUsersType" style="position:absolute;top:35;left:10;width:470;" onchange="CargarUsuarios();" tabindex="1">
				</select>
				<label class="label" name="oListUser" style="position:absolute;top:70;left:10;">
					<script language="javascript">document.write(top.GetIdsLan( "IDS_USERS_LIST" ));</script>
				</label>
				<select class="combo" name="oSelectDeptGroupUser" id="oSelectDeptGroupUser" size = 12 style="position:absolute;top:85;left:10;width:470;" tabindex="1">
				</select>
				<label class="label" name="oLblComentario" style="position:absolute;top:265;left:10;">
					<script language="javascript">document.write(top.GetIdsLan( "IDS_DIST_MSG" ));</script>
				</label>
				<textarea class="textarea" name="oMensaje" id="oMensaje" style="position:absolute;top:280;left:10;width:470;height:90;overflow-y:auto;overflow-x:hidden;" onkeyup="LimitMaxLength(event,250);" tabindex="1"></textarea>
			</div>
			<div id="oDivButt" style="position:absolute;top:385;left:0;width:100%;height:20;">
				<table width="99%" height="10%">
					<tr>
						<td align="right">
							<input id="btnDist" class="button" type="button" onclick="OnOK()" tabindex="1">
								<SCRIPT TYPE="text/javascript">
									if(redistribuir){
										document.getElementById("btnDist").value = top.GetIdsLan( "IDS_BTNACEPTAR" );
									} else {
										document.getElementById("btnDist").value = top.GetIdsLan( "IDS_BTNDIST" );
									}
								</SCRIPT>
							</input>
							<input id="btnCancel" class="button" type="button" onclick="OnCancel()" tabindex="1">
								<SCRIPT TYPE="text/javascript">document.getElementById("btnCancel").value = top.GetIdsLan("IDS_OPCCERRAR");</SCRIPT>
							</input>
						</td>
					</tr>
				</table>
			</div>

		</form>

	</body>
</html>

