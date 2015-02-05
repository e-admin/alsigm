<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="documentTypeId" value="${requestScope.documentTypeId}"/>

<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
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
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/scanner.js"/>'> </script>
    
    <style>
		#scanner { 
			padding: 10px 10px 10px 10px;
			text-align: center;
			visibility: hidden;
		}

		#uploadlist {
		  border-top: 1px solid blue;
		  align:left;
		}
		
		#uploadlist img {
			border:none;
		  	vertical-align:middle;
		}
		
		tr.scanner {
			background-color:#639ACE;
			color:white; 
		}
		
    </style>
    
    <object id="iDocScanXP" 
    	classid="clsid:936846A9-D889-48FF-AD95-084662394143" 
    	codebase='<ispac:rewrite href="../applets/scanner/idocscan.cab"/>'></object>
    
    <script>

		function displayFileQueue() {
			var myapplet = scan.getApplet();
			
			if (!myapplet) {
				return;
			}
			
			if (!myapplet.jsIsReady()) {
				return;
			}

			clearTbody();
			
			var number = myapplet.jsGetFileNumber();
			if (number > 0) {
				var i = 0;
				for (i=0; i < number; i=i+1) {
					addTbodyCell(myapplet, i);
				}
			}
		}	  

		function clearTbody() {
			var tbody = document.getElementById("uploadlistbody");
			while (tbody.childNodes.length > 0) {
		  		tbody.removeChild(tbody.firstChild);
			}
		}
		
		function addTbodyCell(myapplet, i) {
		
		  	var theTableBody = document.getElementById("uploadlistbody");
	  		var newRow = theTableBody.insertRow(theTableBody.rows.length);
		   	if (i%2!=0) {
	   			newRow.style.background='#EEE';
	   		}

		   	var ext  = myapplet.jsGetFileExt(i);
		   	var name = "File"+(i+1)+"."+ext;
		   	var size = myapplet.jsGetFileSize(i);
		   	var unit = 'B';
	   	
			if(size>1024) {
		 		size=size/1024;
				unit='KB';
			}
			if(size>1024) {
				size=size/1024;
				unit='MB';
			}
			if(size>1024) {
				size=size/1024;
				unit='GB';
			}

			size = Math.round(size*100)/100;

			newCell = newRow.insertCell(0);
		   	newCell.innerHTML = "<img src='<ispac:rewrite href='img/file.png'/>'> ";
		   	newCell = newRow.insertCell(1);
		   	newCell.align='left';
		   	newCell.innerHTML = name;
		   	newCell = newRow.insertCell(2);
		   	newCell.align = 'center';
		  	newCell.innerHTML = size + '' + unit;
		   	newCell = newRow.insertCell(3);
		   	newCell.align = 'center';
		   	var myappletname = scan.getAppletName ();
		   	newCell.innerHTML = "<a href='javascript:void(null);' onclick='scan.removeFile(" + i + ");displayFileQueue();'> <img src='<ispac:rewrite href="img/deleteFile.gif"/>' alt='<bean:message key="scanner.delete.file"/>'></a>";   	   	
		}

		function doUpload() {
			var myapplet = scan.getApplet();
			if (myapplet.jsGetFileNumber() > 0) {
				myapplet.jsSetDocumentTypeId('<c:out value="${documentTypeId}"/>');
				var ret = scan.uploadFiles();
				if (ret > 0) {
					window.location.href = '<ispac:rewrite page="common/uploadScannedFiles.jsp"/>';
				} else {
					scan.showMessage('<bean:message key="scanner.error.upload"/>');
				}
			} else {
				
				jAlert('<bean:message key="scanner.error.noFiles"/>', "<bean:message key="common.alert"/>");
			}
		}
   	</script>
  </head>
  
  <body onload="javascript:init()">
<div id="contenido" class="move">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><nobr><bean:message key="scanner.title"/></nobr></h4>
					<div class="acciones_ficha">
					
						<input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel"  onclick='<ispac:hideframe/>'/>
								
					</div>
				</div>
			</div><!-- Fin encabezado ficha -->
		
			<div class="cuerpo_ficha">
			
				<div class="seccion_ficha">
	<table cellpadding="0" cellspacing="1" border="0"  width="99%" style="margin-top:6px; margin-left:4px">
	
		<tr>
			<td width="100%" >
				<table border="0" cellspacing="2" cellpadding="2" width="100%">
					<tr>
						<td width="100%" style="text-align:center">
							<input type="button" value='<bean:message key="scanner.button.config"/>' class="form_button_white" onclick="javascript:scan.configScanner()"/>
							<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
							<input type="button" value='<bean:message key="scanner.button.scan"/>' class="form_button_white" onclick="javascript:scan.scanDocument()"/>
							<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
						</td>
					</tr>
					<tr id="scanner">
						<td width="100%">
						  	<table id="uploadlist" border="0" cellpadding="2" cellspacing="2" width="100%">
						   		<thead>
						    		<tr class="scanner">
							    		<td>&nbsp;</td>
							    		<td align="left"><b><bean:message key="scanner.scanned.files"/></b></td>
							    		<td align="center"><b><bean:message key="scanner.fileSize"/></b></td>
							    		<td>&nbsp;</td>
							    	</tr>
							   </thead>
							   <tbody id="uploadlistbody">
							   </tbody>
							   		<tr>
							   			<td align="right" colspan="4">
											<hr style="color: grey;">
							    		</td>
							   		</tr>
							   		<tr>
							    		<td align="right" colspan="4">
											<a href="javascript:doUpload();" 
												class="rename"><bean:message key="scanner.confirm.upload"/> 
												&nbsp;<img src='<ispac:rewrite href="img/addFile.gif"/>' border="0" /></a>
							    		</td>
							   		</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<OBJECT classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
	    	codebase = "http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,0"
	    	WIDTH = "1" HEIGHT = "1" NAME = "invesdoc_ms" ALT = "Applet" >
	    <PARAM NAME = CODE VALUE = "ieci.tdw.applets.idocscan.InvesDocApplet" />
	    <PARAM NAME = ARCHIVE VALUE = '<ispac:rewrite href="../applets/scanner/idocscanapplet.jar"/>,<ispac:rewrite href="../applets/scanner/jawin.jar"/>,<ispac:rewrite href="../applets/scanner/commons-logging.jar"/>,<ispac:rewrite href="../applets/scanner/commons-codec-1.3.jar"/>,<ispac:rewrite href="../applets/scanner/commons-httpclient.jar"/>' />
	    <PARAM NAME = NAME VALUE = "invesdoc_ms" />
	    <PARAM NAME = MAYSCRIPT VALUE = "true" />
	    <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.4" />
	    <PARAM NAME = "scriptable" VALUE = "true" />
	    <PARAM NAME = "downloadBase" VALUE = "<ispac:rewrite href="../applets/scanner"/>" />
	    <PARAM NAME = "sessionID" VALUE = '<%=request.getSession().getId()%>' />
	    <COMMENT>
			<EMBED type = "application/x-java-applet;version=1.4" \
		           CODE = "ieci.tdw.applets.idocscan.InvesDocApplet" \
		           ARCHIVE = '<ispac:rewrite href="../applets/scanner/idocscanapplet.jar"/>,<ispac:rewrite href="../applets/scanner/jawin.jar"/>,<ispac:rewrite href="../applets/scanner/commons-logging.jar"/>,<ispac:rewrite href="../applets/scanner/commons-codec-1.3.jar"/>,<ispac:rewrite href="../applets/scanner/commons-httpclient.jar"/>' \
		           ALT = "Applet" \
		           NAME = "invesdoc_ntsc" \
		           WIDTH = "1" \
		           HEIGHT = "1" \
		           MAYSCRIPT = "true" \
			       scriptable = "true" \
			       pluginspage = "http://java.sun.com/products/plugin/index.html#download"
			       downloadBase = "<ispac:rewrite href="../applets/scanner"/>">
			    <NOEMBED>
		            <bean:message key="scanner.error.applet.ignored"/>
		    	</NOEMBED>
			</EMBED>
	    </COMMENT>
	</OBJECT>
	</div>
	</div>
	</div>
 </div>
  </body>
  
</html>

<script>
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>