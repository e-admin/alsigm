<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

 	 		var appletSendFiles=document.getElementById("appletSendFiles");
 	 		//llama a la funcion que se encuentra en el fichero folderbar.js

			ExecuteSendScanFilesToServer(appletSendFiles);

 		}
		</script>
		<!-- applet de escaneo-->
		<APPLET MAYSCRIPT="true" id="appletSendFiles" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet"
			ALT="If you could run this applet, you d see some animation"
			ARCHIVE="applets/fwktd-sendFiles-applet-0.8.jar;jsessionid=<%=session.getId()%>">
			<PARAM name="codebase_lookup" value="false"/>
			<%-- <PARAM name="SESSIONID" value="<%=session.getId()%>"/> --%>
		</APPLET>


 	</body>











</html>


