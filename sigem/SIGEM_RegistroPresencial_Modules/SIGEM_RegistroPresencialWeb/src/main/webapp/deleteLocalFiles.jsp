<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ieci.tecdoc.sgm.registropresencial.utils.SigemHttpUtils"%>
<html>
	<head>
		<script language="javascript">
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');

		</script>

		<LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/table.css" />

		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery-1.6.2.min.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.blockUI.js"></script>

		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/folderbar.js"></script>
		<!-- javascript de internalizacion de mensajes -->
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>



	</head>

	<body >
	<script type="text/javascript">

 		function finishLoadSendFilesAppletJSFunction(){
 	 		var appletSendDeleteFiles=document.getElementById("appletSendDeleteFiles");
 	 		//llama a la funcion que se encuentra en el fichero folderbar.js
			ExecuteDeleteScanFiles(appletSendDeleteFiles);

 		}

 	</script>




		<!-- applet de escaneo-->
		<APPLET MAYSCRIPT="true" id="appletSendDeleteFiles" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet"
			ALT="Applet de escaneo"
			ARCHIVE="applets/fwktd-sendFiles-applet-0.8.jar;jsessionid=<%=session.getId()%>">
	        <PARAM name="codebase_lookup" value="false"/>
	        <param name="codebase" value="<%=SigemHttpUtils.getHttpCodeBase(request)%>"/>
	    </APPLET>



 	</body>


</html>


