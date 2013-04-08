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
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/cons_tam.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/listview.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/vlddirinter.js"></script>
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
	<body onunload="OnUnloadNewDir();" tabindex="-1">
   <form name="oFrmDirInter" onsubmit="return false;">
      <input class="button" ID="btnSubir" style="position:absolute; top:45; left:470; width:20;" Name="btnSubir" Type="Button" value="+" 
         onclick="oListNewDir.UpRowSelected();" disabled="true" tabindex="4"></input>
      <input class="button" ID="btnBajar" style="position:absolute; top:115; left:470; width:20;" Name="btnBajar" Type="Button" value="-"
         onclick="oListNewDir.DownRowSelected();" disabled="true" tabindex="5"></input>
      <input class="button" ID="btnModificar" style="position:absolute; top:0; left:148; width:80;"
          		Name="btnModificar" Type="Button" value="" tabindex="1"
          		onclick="ModificarDir(); top.setFocus(parent.VldNewInterPob.document.getElementById('oTxtDir'));" disabled="true"></input>
      <script language="javascript">document.getElementById("btnModificar").value = top.GetIdsLan("IDS_MODIFICAR");
      </script>
      <input class="button" ID="btnAdd" style="position:absolute; top:0; left:230; width:80;"
          		Name="btnAdd" Type="button" value="" tabindex="3"
          		onclick="AgregarDireccion();" disabled="true"></input>
      <script language="javascript">
         document.getElementById("btnAdd").value = top.GetIdsLan("IDS_BTNAGREGAR");
         // Creamos el objeto ListView
         var oListNewDir = new ListView("ListaDirNewInter", "oListNewDir", "oFrmDirInter");
         
         oListNewDir.CreateHeadList("25", "5", "140", "15", top.GetIdsLan("IDS_DIRECCION"), "6699CC", "6699CC");
         oListNewDir.CreateHeadList("25", "143", "80", "15", top.GetIdsLan("IDS_COD_POSTAL"), "6699CC", "6699CC");
         oListNewDir.CreateHeadList("25", "221", "110", "15", top.GetIdsLan("IDS_CIUDAD"), "6699CC", "6699CC");
         oListNewDir.CreateHeadList("25", "329", "135", "15", top.GetIdsLan("IDS_POBLACION"), "6699CC", "6699CC");
      </script>
	   <xsl:apply-templates select="Persona/Domicilios"/>
	   <xsl:apply-templates select="Persona"/>
   </form>
	</body>
	</html>   
</xsl:template>	

<xsl:template match="Domicilios">
   <script language="javascript">
      // Generamos los arrays de eventos
      var oArrEventsList = new Array();
      //oArrEventsList[0] = new ArrOfEvents("onchange", "HabilitarBotones();");
      oListNewDir.CreateListIz("140", TAM_VERT_DIV, "40", "5", TAM_VERT_LIST, "140", "6699CC", oArrEventsList);
      oListNewDir.CreateListIz("80", TAM_VERT_DIV, "40", "143", TAM_VERT_LIST, "80", "6699CC", oArrEventsList);
      oListNewDir.CreateListIz("110", TAM_VERT_DIV, "40", "221", TAM_VERT_LIST, "110", "6699CC", oArrEventsList);
      oListNewDir.CreateListDe("135", TAM_VERT_DIV, "40", "329", TAM_VERT_LIST, "135", "6699CC", oArrEventsList);   
      //delete oArrEventsList[0];
      oArrEventsList = null;
      <xsl:for-each select="Domicilio">
            AddNewDir("<xsl:value-of select='Id'/>", "<xsl:value-of select='Direccion'/>", "<xsl:value-of select='Preference'/>", "<xsl:value-of select='CodPostal'/>", "<xsl:value-of select='Poblacion'/>", "<xsl:value-of select='Provincia'/>");
      </xsl:for-each>
   </script>
</xsl:template>

<xsl:template match="Persona">
   <script language="javascript">
      InsertInForm("<xsl:value-of select='Tipo'/>", "<xsl:value-of select='TipoDoc'/>", "<xsl:value-of select='NIF'/>", "<xsl:value-of select='Apellido1'/>", "<xsl:value-of select='Apellido2'/>", "<xsl:value-of select='Nombre'/>" , "<xsl:value-of select='Nombre'/>" );
   </script>
</xsl:template>

</xsl:stylesheet>
