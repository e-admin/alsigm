<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
	<head>
		<script language="javascript">														
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/listview.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/cons_tam.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/vldbuscinter.js"></script>
	   <script language="javascript">
         var oArrInfo = top.getInfoClient();
         var TAM_VERT_LIST = TAM_VERT_LIST_DIR;
         var TAM_VERT_DIV = TAM_VERT_DIV_DIR;
         if (oArrInfo[0] == "Windows")
         {
            if ( (oArrInfo[1] == "9x") || (oArrInfo[1] == "ME") ) 
            {
               TAM_VERT_LIST = TAM_VERT_LIST_DIR_W95;
               TAM_VERT_DIV = TAM_VERT_DIV_DIR_W95;
            }
         }
         delete oArrInfo;
         oArrInfo = null;
      </script>
	</head>
	<body onload="SetRadioFocus();" ondragstart="return false;" onunload="OnUnloadListBusc();" tabindex="-1">
      <div id="oDivTitleBusc" class="divTitle" style="position:absolute;top:2;left:10;width:425;Height:15;">
            <p style="margin:0">
				<script language="javascript">document.write(top.GetIdsLan("IDS_BUSCAR_INTER"));</script>               
            </p>
      </div>
      <div id="oDivBodyBusc" class="div" style="position:absolute;top:17;left:10;width:425;Height:223;">
         <form id="oFrmBuscInter" name="oFrmBuscInter" action="vldbuscinter.jsp" method="post" onsubmit="return false;">
            <input class="radio" id="oDNI" type="radio" name="oRadioBus" 
				onclick="top.setFocus(document.getElementById('oBuscName'));parent.iTipoBusc=0;document.getElementById('oBuscName2').disabled=true;document.getElementById('oBuscName2').value='';"
				value="1" tabindex="5" checked="true">
                  <script language="javascript">document.write(top.GetIdsLan("IDS_DNI_CIF"));
                  </script>
            </input>
            <input class="radio" id="oRazon" type="radio" name="oRadioBus" 
				onclick="top.setFocus(document.getElementById('oBuscName'));parent.iTipoBusc=1;document.getElementById('oBuscName2').disabled=false;"
				tabindex="6" value="2">
                  <script language="javascript">document.write(top.GetIdsLan("IDS_RAZON_SOCIAL"));
                  </script>
            </input>
            <input class="input" ID="oBuscName" Type="text" style="position:absolute;top:27;left:5;width:200;"
               Name="oBuscName" Maxlength="25" tabindex="1" onblur="doBlur(this);" onkeyup="PushUpInBuscar(event);" 
               onpropertychange="OnChangeFind(this);">
            </input>
            <input class="input" ID="oBuscName2" Type="text" disabled="true" 
				style="position:absolute;top:47;left:5;width:200;"	Name="oBuscName2" Maxlength="25" tabindex="1"
				onblur="doBlur(this);">
			</input>
            <img id="btnBuscar" Name="btnBuscar" src="images/lupadis.gif" style="position:absolute; top:28; left:208;"
               WIDTH="18" HEIGHT="15" tabindex="3"></img>
            <script language="javascript">
               if (top.g_CreateInterPerms == 0)
               {
                  document.write('<img Name="btnNewInter" src="images/newinterdis.gif" style="position:absolute; top:28; left:230;" WIDTH="18" HEIGHT="15" tabindex="4"></img>');
               }
               else
               {
                  document.write('<img Name="btnNewInter" src="images/newinter.gif" style="position:absolute; top:28; left:230;" WIDTH="18" HEIGHT="15" onmouseover="mouseOver();" onmouseout="mouseOut();" onclick="CreateNewInt();" tabindex="4"></img>');
               }
            </script>
            <xsl:choose>
               <xsl:when test="Personas/Persona">
                  <input class="button" id="btnAddInter" style="position:absolute;top:50;left:342;width:70;"
                     name="btnAddInter" type="Button" value="" onclick="AgregarInter();" tabindex="2"></input>
                  <script language="javascript">
                     document.getElementById("btnAddInter").value = top.GetIdsLan("IDS_BTNAGREGAR");
                     // Creamos el objeto ListView
                     var oListBusc = new ListView("ListaDirInteresados", "oListBusc", "oFrmBuscInter");
                     oListBusc.CreateHeadList("75", "5", "140", "15", top.GetIdsLan("IDS_LABEL_DOCUMENT"), "6699CC", "6699CC");
                     oListBusc.CreateHeadList("75", "143", "270", "15", top.GetIdsLan("IDS_ETQNOMBRE"), "6699CC", "6699CC");
                  </script>
	               <xsl:apply-templates select="Personas"/>
	            </xsl:when>
	            <xsl:otherwise>
	               <script>
                     <xsl:comment>ShowMessageConfirm(document.getElementById("oBuscName"), CreateNewInt);</xsl:comment>
                  </script>
               </xsl:otherwise>
	         </xsl:choose>
         </form>
	   </div>
	</body>
	</html>   
</xsl:template>	

<xsl:template match="Personas">
   <script language="javascript">
      // Generamos los arrays de eventos
      var oArrEventsList = new Array();
      oArrEventsList[0] = new ArrOfEvents("ondblclick", "AgregarInter();");
      oArrEventsList[1] = new ArrOfEvents("onchange", "DisabledButs();");
      oListBusc.CreateListIz("140", TAM_VERT_DIV, "90", "5", TAM_VERT_LIST, "140", "6699CC", oArrEventsList);
      oListBusc.CreateListDe("270", TAM_VERT_DIV, "90", "143", TAM_VERT_LIST, "270", "6699CC", oArrEventsList);
      // Borramos los arrays de eventos
      delete oArrEventsList[0];
      delete oArrEventsList[1];
      oArrEventsList = null;

      <xsl:for-each select="Persona">
         <xsl:choose>
            <xsl:when test="Tipo[.='1']">
               AddInterFis("<xsl:value-of select='position()'/>", "<xsl:value-of select='Id'/>", "<xsl:value-of select='TipoDoc'/>", "<xsl:value-of select='Tipo'/>", "<xsl:value-of select='Nombre'/>", "<xsl:value-of select='Apellido1'/>", "<xsl:value-of select='Apellido2'/>", "<xsl:value-of select='NIF'/>");
            </xsl:when>
            <xsl:otherwise>
               AddInterJur("<xsl:value-of select='position()'/>", "<xsl:value-of select='Id'/>", "<xsl:value-of select='TipoDoc'/>", "<xsl:value-of select='Tipo'/>", "<xsl:value-of select='NIF'/>", "<xsl:value-of select='Nombre'/>");
            </xsl:otherwise>
         </xsl:choose>
      </xsl:for-each>
   </script>
</xsl:template>
</xsl:stylesheet>
