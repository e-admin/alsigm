
//var g_ToolBarHeight = 62;
//var g_SepWidth  = 3;
//var g_ArchiveName = "";
//var g_MaxCharArchiveName = 30;
//var g_TableToolBarHeight = 22;

//var g_Maximized = false;
//var g_PrevFormTreeWidth = 250;

//var g_SessionPId     = "";
//var g_DATreePId      = 0;
//var g_DATreeId       = 0;
//var g_ArchivePId     = 0;
//var g_ArchiveId      = 0;
//var g_ArchiveName    = "";
//var g_FdrQryPId      = 0;
//var g_AppId          = 1;
//var g_FolderPId      = -1;
//var g_FdrReadOnly    = true;
//var g_CloseFolder    = true;

//var g_DATLoaded         = false;
//var g_QueryLoaded       = false;
//var g_TitleBarLoaded    = false;
//var g_ToolBarLoaded     = false;
//var g_WorkspaceLoaded   = false;
//var g_TableBarLoaded    = false;
//var g_TableDataLoaded   = false;
//var g_FormDataLoaded    = false;
//var g_FormTreeLoaded    = false;
//var g_FolderBarLoaded   = false;
//var g_FolderDataLoaded  = false;
//var g_FolderWorkspaceLoaded = false;
//var g_IsLdap            = false;
//var g_UseOSAuth         = false; //indica si usa la authenticación del SO en LDAP
//var g_UserDn            = ""; //userdn de Usuario

//var g_WndVld = null;
//var g_FormVld = null;

//var g_TreeFunc = true;
//var g_OpcAval;
//var g_sinPulsar=true;

//// Página seleccionada en la carpeta que se está visualizando
//var g_Page = 0;

//// Navegación y FolderViews
//var g_FolderView  = false;
//var g_Navi        = false;
//var g_ShowInForm  = 0;

//var g_URL = "";

//var g_FolderSel = -1;
//var g_FolderId  = -1;

//var g_BookPerms = 1; // por defecto tenemos permiso de consulta

//var g_RutaInicio = "";

//var g_flag          = true;

//// variables donde se almacenan los paths de la aplicación
//var g_PathTemp = "";
//var g_PathCache = "";
//var g_PathUpload = "";
//var g_MaxSizeFile = 0;

//// Array con los ficheros locales que se tienen que borrar despues del upload
//var g_ArrFilesToServer   = null;
//var g_CountFiles         = 0;

//// Id´s de busqueda documental
//var g_QryDocPId      = 0;
//var g_QryDocArchId   = 0;
//var g_QryDocArchName = "";
//var g_QryDocPageId   = 0;
//var g_QryDocIdDoc    = 0;
//var g_QryDocFile     = "";
//var g_QryDocInitRow  = 1;
//var g_QryDocLastRow  = 16;
//var g_QryDocMaxRows  = 16;

//function ToolBarLoaded()
//{
//   g_ToolBarLoaded = true;
//}

//function TitleBarLoaded()
//{
//   g_TitleBarLoaded = true;
//}

//function WorkspaceLoaded()
//{
//   g_WorkspaceLoaded = true;
//}

//function DATLoaded()
//{
//   g_DATLoaded = true;
//}

//function QueryLoaded()
//{
//   g_QueryLoaded = true;
//}

//function TableBarLoaded()
//{
//   g_TableBarLoaded = true;
//}

//function TableDataLoaded()
//{
//   g_TableDataLoaded = true;
//}

//function FormTreeLoaded()
//{
//   g_FormTreeLoaded = true;

//   if (g_FormDataLoaded && g_FormTreeLoaded)
//   {
//      if (g_Navi || g_FolderView)
//      {
//         SetArchiveName(g_ArchiveName);
//      }
//   
//      if ((top.g_Navi && top.g_ShowInForm) || top.g_FolderView)
//         window.open("frmt.asp?SessionPId=" + g_SessionPId + "&Row=1&FdrQryPId=" + g_FdrQryPId.toString() + "&FolderPId=" + g_FolderPId.toString(), "FolderFormTree");
//   }
//}

//function FormDataLoaded()
//{
//   g_FormDataLoaded = true;

//   // Lo hacemos aquí porque es cuando aseguramos que están todos los frames cargados   
//   if (g_FormDataLoaded && g_FormTreeLoaded)
//   {
//      /*
//      if (g_Navi || g_FolderView)
//      {
//         SetArchiveName(g_ArchiveName);
//      }
//      */

//      if ((top.g_Navi && top.g_ShowInForm) || top.g_FolderView)
//         window.open("frmt.asp?SessionPId=" + g_SessionPId + "&Row=1&FdrQryPId=" + g_FdrQryPId.toString() + "&FolderPId=" + g_FolderPId.toString(), "FolderFormTree");
//   }
//}

//function FolderBarLoaded()
//{
//   g_FolderBarLoaded = true;
//}

//function FolderDataLoaded()
//{
//   g_FolderDataLoaded = true;
//}

//function FolderWorkspaceLoaded()
//{
//   g_FolderWorkspaceLoaded = true;
//}

//function Load()
//{
//}

//function OnResizeWorkspace()
//{
//   var Width = top.document.body.clientWidth;
//   if (Width < 30) Width = 30;
//   var Height = top.document.body.clientHeight - g_ToolBarHeight;
//   if (Height < 30) Height = 30;

//   if (g_ToolBarLoaded)
//      top.Main.document.all.ToolBar.style.width = Width.toString() + "px";

//   if (g_TitleBarLoaded)
//      top.Main.document.all.TitleBar.style.width = Width.toString() + "px";

//   if (g_WorkspaceLoaded)
//   {
//      top.Main.document.all.Workspace.style.width  = Width.toString() + "px";
//      top.Main.document.all.Workspace.style.height = Height.toString() + "px";
//   }

//   if (g_DATLoaded)
//      top.Main.Workspace.document.all.DAT.style.height = Height.toString() + "px";

//   if (g_DATLoaded && g_QueryLoaded)
//   {
//      top.Main.Workspace.document.all.Query.style.left = (top.Main.Workspace.DAT.document.body.clientWidth + g_SepWidth).toString() + "px";
//      top.Main.Workspace.document.all.Query.style.width = (Width - top.Main.Workspace.DAT.document.body.clientWidth - g_SepWidth).toString() + "px";
//      top.Main.Workspace.document.all.Query.style.height = Height.toString() + "px";
//   }

//   if (g_TableBarLoaded && g_TableDataLoaded)
//   {
//      top.Main.document.all.Table.style.width  = Width.toString() + "px";
//      top.Main.document.all.Table.style.height  = Height.toString() + "px";
//      top.Main.Table.document.all.TableBar.style.width  = Width.toString() + "px";
//      top.Main.Table.document.all.TableData.style.width  = Width.toString() + "px";
//      top.Main.Table.document.all.TableData.style.height = (Height - g_TableToolBarHeight).toString() + "px";
//   }

//   if (g_FolderWorkspaceLoaded)
//   {
//      top.Main.document.all.Folder.style.width  = Width.toString() + "px";
//      top.Main.document.all.Folder.style.height  = Height.toString() + "px";
//   }

//   if (g_FolderBarLoaded && g_FolderDataLoaded)
//   {
//      top.Main.Folder.document.all.FolderBar.style.width  = Width.toString() + "px";
//      top.Main.Folder.document.all.FolderData.style.width  = Width.toString() + "px";
//      top.Main.Folder.document.all.FolderData.style.height = (Height - g_TableToolBarHeight).toString() + "px";
//   }

//}

//function SetArchiveName(ArchiveName)
//{
//   g_ArchiveName = ArchiveName;
//   
//   if (ArchiveName.length > g_MaxCharArchiveName)
//   {
//      ArchiveName = ArchiveName.substr(0, g_MaxCharArchiveName - 3) + "...";
//   }
//   
//   top.Main.ToolBar.document.all("ArchiveName").innerHTML = ArchiveName;
//}

//function ShowNewFolderBtn(Show)
//{
//   var Value;
//   
//   if ( (Show) && ((top.g_BookPerms >> (top.POS_PERM_CREAR)) % 2 != 0) )
//      Value = "visible";
//   else
//      Value = "hidden";

//   top.Main.ToolBar.document.all("NewFolderBtn").style.visibility = Value;
//}

//function ToggleMaximized()
//{
//   if (g_Maximized)
//   {
//      top.Main.Folder.FolderData.FolderFSet.cols = g_PrevFormTreeWidth.toString() + "px, *";
//      top.Main.ToolBar.document.all("ToggleBtn").innerHTML = "<div align=\"center\">" + top.GetIdsLan( "IDS_OPCMAXIMIZAR" ) + "</div>";
//   }
//   else
//   {
//      top.Main.ToolBar.document.all("ToggleBtn").innerHTML = "<div align=\"center\">" + top.GetIdsLan( "IDS_OPCRESTAURAR" ) + "</div>";
//      g_PrevFormTreeWidth = top.Main.Folder.FolderData.document.all("FolderFormTree").offsetWidth;
//      top.Main.Folder.FolderData.FolderFSet.cols = "0, *";
//   }
//   
//   g_Maximized = !g_Maximized;
//}


//function ShowTableWorkspace(Show)
//{
//   var Value;
//   
//   if (Show)
//      Value = "visible";
//   else
//      Value = "hidden";
//      
//   // Mostramos tabla
////   top.Main.document.all.Table.style.visibility = Value;

//   if (Show)
//      top.Main.document.all.Table.style.left = "0px";
//   else
//      top.Main.document.all.Table.style.left = "-10000px";
//}

//function ShowQueryWorkspace(Show)
//{
//   var Value;
//   
//   if (Show)
//      Value = "visible";
//   else
//      Value = "hidden";

//   // Ocultamos workspace de archivador/query
////   top.Main.document.all.Workspace.style.visibility = Value;
//   if (Show)
//      top.Main.document.all.Workspace.style.left = "0px";
//   else
//      top.Main.document.all.Workspace.style.left = "-10000px";
//}

//function ShowFormWorkspace(Show)
//{
//   var Value;
//   
//   if (Show)
//      Value = "visible";
//   else
//      Value = "hidden";
//   // Mostramos tabla
//   
////   top.Main.document.all.Folder.style.visibility = Value;

//   if (Show)
//      top.Main.document.all.Folder.style.left = "0px";
//   else
//      top.Main.document.all.Folder.style.left = "-10000px";
//}

//function OpenNewWindow(URL, Name, Width, Height, Scroll, Params) 
//{
//   if( Width > screen.availWidth )
//   {
//      Width = screen.availWidth;
//   }
//   if( Height > screen.availHeight )
//   {
//      Height = screen.availHeight;
//   }
//   var winl = (screen.availWidth - Width) / 2;
//   var wint = (screen.availHeight - Height) / 2;
//   Props = 'height='+Height+',width='+Width+',top='+wint+',left='+winl+',scrollbars='+Scroll+',resizable=yes' + Params;
//   win = window.open(URL, Name, Props);
//   if (parseInt(navigator.appVersion) >= 4) 
//   { 
//      win.window.focus(); 
//   }
//}

//function ShowQuery(Show)
//{
//   ShowQueryWorkspace(Show);
//   ShowTableWorkspace(!Show);
//   ShowFormWorkspace(!Show);
//   
//   window.open("tb_query.htm", "ToolBar");
//   
//   top.Main.Workspace.Query.OnWindowLoad(false);
//   
//}

//function ShowTable(Show)
//{
//   ShowTableWorkspace(Show);
//   ShowQueryWorkspace(!Show);
//   ShowFormWorkspace(!Show);
//   top.g_OpcAval=true;
//}

//function ShowForm(Show)
//{
//   ShowFormWorkspace(Show);
//   ShowQueryWorkspace(!Show);
//   ShowTableWorkspace(!Show);
//}

//function Over(obj)
//{
//   if (obj.className != "SubOptionsDisabled")
//	   obj.className="OptionsOver";
//}
//	
//function Out(obj)
//{
//   if (obj.className != "SubOptionsDisabled")
//   	obj.className="Options";
//}

//function NewFolder()
//{
//   g_TreeFunc = false;

//   var Params;

//   Params = "ArchiveId=" + g_ArchiveId.toString();
//   Params += "&SessionPId=" + g_SessionPId;
//   Params += "&ArchivePId=" + g_ArchivePId.toString();
//   Params += "&ArchiveName=" + g_ArchiveName.toString();
//   Params += "&Idioma=" + Idioma.toString();

//   window.open("newfolder.asp?" + Params, "Work"); 

//   top.g_TreeFunc = true;
//}

//function Logout()
//{
//   if (g_UseOSAuth)
//      window.open("about:blank","_top");
//   else
//      window.open("logout.asp?SessionPId=" + g_SessionPId
//                  + "&AppId=" + g_AppId.toString()
//		  + "&Ldap=" + g_IsLdap.toString()
//	          + "&UseOSAuth=" + g_UseOSAuth.toString()
//	          + "&Idioma=" + top.Idioma.toString(), "_top");
//}

//function CloseFolder()
//{
//   if (g_FolderPId != -1)
//      window.open("closefolder.asp?SessionPId=" + g_SessionPId + "&FolderPId=" + g_FolderPId.toString()+ "&FolderId=" + g_FolderId.toString()+ "&ArchiveId=" + g_ArchiveId.toString(), "", "left=10000");
//}

//function OpenFolder(FolderId)
//{
//   var Params;

////   if ((top.g_BookPerms >> (top.POS_PERM_MODIFICAR)) % 2 != 0)
////   {
//         if (top.g_QryDocPId > 0)
//         {
//            Params = "ArchiveId=" + g_QryDocArchId.toString();
//            Params += "&SessionPId=" + g_SessionPId;
//            Params += "&FolderId=" + FolderId.toString();
//            Params += "&ArchivePId=0";
//            Params += "&QryDocPageId=" + g_QryDocPageId.toString();
//            Params += "&QryDocIdDoc=" + g_QryDocIdDoc.toString();
//			Params += "&Idioma=" + top.Idioma;
//         }
//         else
//         {
//            Params = "ArchiveId=" + g_ArchiveId.toString();
//            Params += "&SessionPId=" + g_SessionPId;
//            Params += "&FolderId=" + FolderId.toString();
//            Params += "&ArchivePId=" + g_ArchivePId.toString();
//            Params += "&QryDocPageId=0&QryDocIdDoc=0";
//			Params += "&Idioma=" + top.Idioma;
//         }
//         window.open("openfolder.asp?" + Params, "Work"); 
////   }
//}

//function DeleteFolder()
//{
//   if( g_FolderId != -1 )
//   {
//      if( window.confirm( top.GetIdsLan( "IDS_QRYBORRACARPETA" ) ) )
//      {
//         var Params;
//            
//         Params = "SessionPId=" + g_SessionPId;
//         Params += "&ArchivePId=" + g_ArchivePId.toString();
//         Params += "&FolderId=" + g_FolderId.toString();
//         Params += "&ArchiveId=" + g_ArchiveId.toString();
//		 Params += "&Idioma=" + top.Idioma;

//         window.open("deletefolder.asp?" + Params, "TableData"); 
//         
//         return true;
//      }
//   }
//   return false;
//}

//function ChkClose()
//{
//   try
//   {
//      if( document.frames.Main.document.frames.Folder.document.frames.FolderBar.document.all("SaveMenuBtn").className == "SubOptions" )
//      {
//         if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) )
//         {
//            return false;
//         }
//      }
//   }
//   catch(exc)
//   {
//      return true;
//   }
//   return true;
//}

//function setFormFocus(oForm)
//{
//   var iPosX = 32640;
//   var iPosY = 32640;
//   var iIndex = -1;
//   
//   for (var ii = 0; ii < oForm.length; ii++)
//   {
//      // Miramos si es el campo más a la izquierda para darle el foco
//      if ( (oForm[ii].type == "text") && !(oForm[ii].readOnly) )
//      {
//         if (oForm[ii].style.posLeft <= iPosX)
//         {
//            if (oForm[ii].style.posLeft == iPosX)
//            {
//               if (oForm[ii].style.posTop < iPosY)
//               {
//                  iIndex = ii;
//                  iPosY = oForm[ii].style.posTop;
//               }
//            }
//            else
//            {
//               iIndex = ii;
//               iPosY = oForm[ii].style.posTop;
//               iPosX = oForm[ii].style.posLeft;
//            }
//         }
//      }
//   }
//   if (iIndex > -1)
//   {
//      setFocus(oForm[iIndex]);
//   }
//   return;
//}

//function setFocus(Field)
//{
//   var strVer = navigator.appVersion;
//   var iPosVer = strVer.indexOf('MSIE') + 5; // Posición de la versión del explorer
//   // Si tenemos un Explorer 5.5 o superior hacemos activo el elemento
//   if ( (navigator.appName.indexOf('Microsoft') != 1) 
//      && (strVer.substr( iPosVer,strVer.indexOf(';',iPosVer) - iPosVer ) >= "5.5") )
//   {
//      try
//      {
//         Field.setActive();
//      }
//      catch( excep ){}
//   }
//   try
//   {
//      Field.focus();
//   }
//   catch( excep ){}
//   return;
//}

function ParamValue( Cadena, Param )
{
   if( eval( "Cadena.search( '/?" + Param + "=' )" ) != -1 )
   {
      var subCadena = Cadena.substr( eval( "Cadena.search( '/?" + Param + "=' )" ) );
   }
   else
   {
      if( eval( "Cadena.search( '/&" + Param + "=' )" ) != -1 )
      {
         var subCadena = Cadena.substr( eval( "Cadena.search( '/&" + Param + "=' )" ) );
      }
      else
      {
         return '';
      }
   }

   var posicion = subCadena.search( "=" );
   if ( subCadena.search( "&" ) != -1 )
   {
      var valor = subCadena.substring( posicion + 1 , subCadena.search( "&" ) );
   }
   else
   {
      var valor = subCadena.substr( posicion + 1 );
   }
   return valor;
}

//function Help()
//{
//	if (Idioma == "")
//	{
//		   OpenNewWindow("./help/Ayudadocnet.htm", "HelpiDoc", (window.screen.availWidth)*0.85, (window.screen.availHeight)*0.85, "auto","");
//	}
//	if (Idioma == "IN_")
//	{
//		   OpenNewWindow("./helpin/Ayudadocnet.htm", "HelpiDoc", (window.screen.availWidth)*0.85, (window.screen.availHeight)*0.85, "auto","");
//	}

//}

//// quita espacios en blanco por alante y por detras
//function miTrim( pcadena )
//{
//   if ( ( pcadena != "" ) && ( pcadena != null ) )
//   {
//      for( ; pcadena.charAt(0) == " "; )
//      {
//         pcadena = pcadena.substring( 1, pcadena.length );
//      }

//      for( ; pcadena.charAt( pcadena.length - 1 ) == " "; )
//      {
//         pcadena = pcadena.substring( 0, pcadena.length - 1 );
//      }
//   }
//   return pcadena;
//}

//// Consigue el directorio virtual donde esta una aplicacion en un servidor
//function getVirtualRoot()
//{
//   var strPathName = top.document.location.pathname;
//   var iPosFin = 0;
//   // el fichero cuelga directamente del servidor
//   if (strPathName.substr(0,1) == "/")
//   {
//      iPosFin = strPathName.lastIndexOf("/");
//      if (iPosFin > 0)
//      {
//         strPathName = strPathName.substr(0,iPosFin);
//      }
//      else
//      {
//			strPathName = "";
//      }
//   }
//   else
//   {
//      strPathName = "";
//   }
//   return strPathName;
//}

//function GetNamePath( cadena )
//{
//   var ii = cadena.lastIndexOf( '\\' );
//   if( ii != -1 )
//   {
//      return cadena.substring( ii+1, cadena.length );
//   }
//   else
//   {
//      return cadena;
//   }
//}

//function ShowQryDocBtn(bShow)
//{
//   var strValue = "none";
//   if (bShow)
//   {
//      strValue = "inline";
//   }
//   top.Main.ToolBar.document.all("QryDocBtn").style.display = strValue;
//}

//// lanza la presentacion del formulario de busqueda documental
//function GotoQryDoc(oBtn)
//{
//   if (oBtn.className != "SubOptionsDisabled")
//   {
//      top.ShowNewFolderBtn(false);
//      top.ShowQryDocBtn(false);
//      top.ShowConfigBtn(false);
//      top.SetArchiveName("");
//      top.Main.Workspace.document.frames.DAT.SetUnselected();
//      var URL = "qrydoc.asp?SessionPId=" + top.g_SessionPId.toString();
//      window.open(URL, "Query");
//   }
//}

//// cierra la busqueda documental
//function CloseQryDoc()
//{
//   if (top.g_QryDocPId > 0)
//   {
//      top.g_QryDocArchId   = 0;
//      top.g_QryDocPageId   = 0;
//      window.open("qrydocclose.asp?SessionPId=" + top.g_SessionPId + "&QryDocPId=" + top.g_QryDocPId.toString(), "Work");
//      top.g_QryDocPId      = 0;
//      top.g_QryDocFile     = "";
//      top.g_QryDocInitRow  = 0;
//      top.g_QryDocInitRow  = 15;
//   }
//}

//// devuelve la extensión del fichero
//function getExtensionFile(strPathFile)
//{
//   var ii = strPathFile.lastIndexOf( '\.' );
//   if( ii != -1 )
//   {
//      return strPathFile.substring( ii, strPathFile.length );
//   }
//   else
//   {
//      return "";
//   }
//}

//// muestra el botón de las preferencias de formato.
//function ShowConfigBtn(bShow)
//{
//   var strValue = "none";
//   if (bShow)
//   {
//      strValue = "inline";
//   }
//   top.Main.ToolBar.document.all("ConfigBtn").style.display = strValue;
//}

//// lanza la presentacion del formulario de elección de formatos
//function GotoConfig(oBtn)
//{
//   if ( (oBtn.className != "SubOptionsDisabled")
//      && (window.confirm(top.GetIdsLan("IDS_CONFIG_QUESTION"))) )
//   {
//      top.ShowNewFolderBtn(false);
//      top.ShowConfigBtn(false);
//      top.CloseQryDoc();
//      var URL = "configFormat.asp?SessionPId=" + top.g_SessionPId.toString()
//            + "&ArchivePId=" + top.g_ArchivePId.toString()
//            + "&ArchiveId=" + top.g_ArchiveId.toString()
//		    + "&Idioma=" + top.Idioma;
//      window.open(URL, "Query");
//   }
//}

//function CloseMsgWindow()
//{
//	top.Main.document.all.Mensaje.style.left = "-10000px";	
//}

//function OpenMsgWindow()
//{
//	if ( (g_Navi == "1") || (g_FolderView == "1") )
//	{
//		top.Main.document.all.Mensaje.style.left = "400px";	
//	}
//	else
//	{
//		top.Main.document.all.Mensaje.style.left = "300px";	
//	}
//}