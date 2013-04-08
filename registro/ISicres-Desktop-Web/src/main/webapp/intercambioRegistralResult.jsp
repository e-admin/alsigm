<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${pageContext.response.locale.language}" lang="${pageContext.response.locale.language}">
	<head>
		<html:base />

		<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
		<meta http-equiv="Content-Style-Type" content="text/css" />
		<link rel="stylesheet" href="./css/terceros/table.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="./css/intercambioRegistral.css" type="text/css" media="screen" />

	</head>
	<body>
	<div style="padding:60px 0 0 50px; font-size: 15px;text-align: center;"><c:out value='${successMessage}' /></div>
	<div style="padding:60px 0 0 50px;color:red; font-size: 15px;text-align: center;"><c:out value='${errorMessage}' /></div>

<div style="text-align:center;">
<input type="button" value="Aceptar" class="button" onclick="javascript:closeIntercambioRegistral();return false;"/>
</div>

<script type="text/javascript">
<!--


function closeIntercambioRegistral(){
	// Cerramos los frames
	top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");

	top.g_WndVld.document.getElementById("Vld").style.display = "none";

	// Volvemos a mostrar el formulario
	if(top.g_FormVld){
		//recargamos la pantalla del registro - refrescamos datos
		top.Main.Folder.FolderData.FolderFormTree.ReLoad();
	}
}


//-->
</script>

	</body>
</html>
