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
		
		<!-- javascript de internalizacion de mensajes -->
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>
				
		
	</head>
	
	<body>

		<APPLET MAYSCRIPT="true" id="appletFileChooser" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.fileChooser.applet.FileChooserApplet" 
			ALT="Applet Navegador Sistema de Ficheros"
			ARCHIVE="applets/fwktd-fileSystem-applet-0.6.jar;jsessionid=<%=session.getId()%>">
			<param name="returnJSFunction" value="fileChooser_returnFiles"/> 
			<param name="finishLoadJSFunction" value="finishLoadJSFunctionFileChooser"/>
			<param name="codebase" value="<%=SigemHttpUtils.getHttpCodeBase(request)%>"/> 
		</APPLET>
		
						
		<script type="text/javascript">
				
		var appletCargado=false;
		
		
		function finishLoadJSFunctionFileChooser(){

			//el applet ha sido cargado con exito
			appletCargado=true;
		}
				
		function fileChooser_returnFiles(files)
		{
			parent.FolderFormTree.examinar_returnFiles(files,appletFileChooser);
			
		}

		//funcion de espera a que cargue el applet
		// esta espera semiActiva se debe realizar para que funcione correctamente en ie9
		function waitUntilAppletLoaded(){
						
			if(appletCargado){
				appletFileChooser.selectFile();
			} else{
		
				setTimeout(waitUntilAppletLoaded,1000); 	
			}

		}

		waitUntilAppletLoaded();
		
		
		
				
	</script>
	
	
 	</body>
</html>


