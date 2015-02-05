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
		
		<!-- javascript de internalizacion de mensajes -->
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>
				
		
	</head>
	
	<body >
		
		<!-- applet de escaneo-->
		<APPLET MAYSCRIPT="true" id="appletScanEscanear" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.scan.applet.IdocAppletLauncher" 
			ALT="If you could run this applet, you'd see some animation"
			ARCHIVE="applets/fwktd-scan-applet-0.7.jar;jsessionid=<%=session.getId()%>">
			<param name="java_arguments" value="-Xmx512m"/>
			<param name="returnJSFunction" value="scanEscaner"/> 
		</APPLET>
		
						
		<script type="text/javascript">
			
			//declaramos esta funcion que usara el objeto javascript para escanear, se necesita porque sino el applet necesita declarar la funcion a la que invocar
			function scanEscaner(files){
				var appletScanEscanear=document.getElementById("appletScanEscanear");
				//llama a la funcion definida en el fichero clfdoc.js
				top.Main.Folder.FolderData.FolderFormTree.scannedFiles(files,appletScanEscanear);
			}
			
			function finishLoadJSFunction(){
				//llamamos al proceso de escaneo 
				var appletScanEscanear=document.getElementById("appletScanEscanear");
				appletScanEscanear.startScanApplet(true, true, true);
			}
			
		</script>
	
	
 	</body>
</html>


