#include "stdafx.h"
#include <winver.h>
#include "iregini.h"
//#include "strmgr.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif



ICRegIni::ICRegIni()
{
   m_Product     = "iDocImgX";
   m_General     = "General";         
   m_Palette     = FALSE;
   m_DrawMode    = 0;
   m_Fit         = 0; 
   m_Rot         = 0;
}

ICRegIni::~ICRegIni()
{   
}

//////////////////////////////////////////////////////////////////////
//
//   Inicialización.
//
///////////////////////////////////////////////////////////////////////


BOOL ICRegIni::LoadSettings() 
{
   bool Exists;

   m_Software = "Software";
   m_Company  = "IECISA";  

   
   HKEY hParentKey = HKEY_CURRENT_USER;
   HKEY hSubKey = NULL;
   CString DeftValue;
   
   
   hSubKey = CreateKey(hParentKey, m_Software, &Exists);  
   if (hSubKey == NULL) { return FALSE; } 

   RegCloseKey(hParentKey);
   hParentKey = hSubKey;
   hSubKey    = NULL;

   hSubKey = CreateKey(hParentKey, m_Company, &Exists); 
   if (hSubKey == NULL) { return FALSE; } 

   RegCloseKey(hParentKey);
   hParentKey = hSubKey;
   hSubKey    = NULL;

   m_hProductKey = CreateKey(hParentKey, m_Product, &Exists); 
   if (m_hProductKey == NULL) { return FALSE; } 

   RegCloseKey(hParentKey);

   
   if ( GetRegPalette() == FALSE ) { return FALSE; }
   if ( GetRegDrawMode() == FALSE ) { return FALSE; }
   if ( GetRegFit() == FALSE ) { return FALSE; }
   if ( GetRegRot() == FALSE ) { return FALSE; }
   

   return TRUE;
}

HKEY ICRegIni::OpenKey(HKEY hParentKey, LPCSTR KeyString)
{
   HKEY  hSubKey = NULL;
   DWORD error = 0;

   error = RegOpenKeyEx(hParentKey, KeyString, 0, KEY_WRITE | KEY_READ,
                        &hSubKey);

   return (hSubKey);
}

HKEY ICRegIni::CreateKey(HKEY hParentKey, LPCSTR KeyString, bool FAR* Exists)
{
   HKEY  hSubKey = NULL;
   DWORD error, dw = 0;

   error = RegCreateKeyEx(hParentKey, KeyString, 0, REG_NONE,
                          REG_OPTION_NON_VOLATILE, KEY_WRITE | KEY_READ, NULL,
                          &hSubKey, &dw);

 
   if(dw != REG_CREATED_NEW_KEY) {*Exists = true;}

   return (hSubKey);
}
 
CString ICRegIni::GetEntryString(HKEY hParentkey, LPCTSTR pszEntry, LPCTSTR strDefault, BOOL Default)
{
   ASSERT(hParentkey != NULL);
	ASSERT(pszEntry != NULL);

   DWORD  dwType  = 0;
   DWORD  dwCount = 0;

   CString strValue;

   strValue.Empty();

   DWORD error = RegQueryValueEx(hParentkey, (LPTSTR)pszEntry, NULL, &dwType,
                          NULL, &dwCount);

   if(!error)
      error = RegQueryValueEx(hParentkey, (LPTSTR)pszEntry, NULL, &dwType,
                         (LPBYTE)strValue.GetBuffer(dwCount/sizeof(TCHAR)), &dwCount);
   else
      WriteEntryString(hParentkey, pszEntry, strDefault);

   strValue.ReleaseBuffer();

   if(Default)
   {
      if(strValue.IsEmpty()) strValue = strDefault;
   }
           
   return (strValue);

}

BOOL ICRegIni::GetRegPalette()
{
   HKEY    hKeyGeneral = NULL;
   bool    Exists = false;
   DWORD   error = 0;
   CString DeftValue;
   CString Palette;
   BOOL    Ret = TRUE; 

   
   hKeyGeneral = CreateKey(m_hProductKey, m_General, &Exists);
   
   if( hKeyGeneral == NULL ) {return FALSE; }

   
   if(!Exists)
   {   
      m_Palette = TRUE;
      RegCloseKey(hKeyGeneral);
      return TRUE;
   }
   
   
   Palette = GetEntryString(hKeyGeneral, "Palette", "0", TRUE); 
   if(Palette.IsEmpty()) {Ret = FALSE; goto End;}

   if (atoi(Palette))
      m_Palette = TRUE;
   else
      m_Palette = FALSE;


End:
   
   RegCloseKey(hKeyGeneral);
   return Ret;
            
}

LONG ICRegIni::GetRegDrawMode()
{
   HKEY    hKeyGeneral = NULL;
   bool    Exists = false;
   DWORD   error = 0;
   CString DeftValue;
   CString DrawMode;
   BOOL    Ret = TRUE; 

   
   hKeyGeneral = CreateKey(m_hProductKey, m_General, &Exists);

   if( hKeyGeneral == NULL ) {return FALSE; }
   
   
   if(!Exists)
   {   
      m_DrawMode = 0;
      RegCloseKey(hKeyGeneral);
      return TRUE;
   }
   
   
   DrawMode = GetEntryString(hKeyGeneral, "DrawMode", "0", TRUE); 
   if(DrawMode.IsEmpty()) {Ret = FALSE; goto End;}

   m_DrawMode = atol(DrawMode);
   
End:
   
   RegCloseKey(hKeyGeneral);
   return Ret;
            
}

LONG ICRegIni::GetRegFit()
{
   HKEY    hKeyGeneral = NULL;
   bool    Exists = false;
   DWORD   error = 0;
   CString DeftValue;
   CString FitMode;
   BOOL    Ret = TRUE; 

  
   hKeyGeneral = CreateKey(m_hProductKey, m_General, &Exists);
   
   if( hKeyGeneral == NULL ) {return FALSE; }
  
   
   if(!Exists)
   {   
      m_Fit = -1;
      RegCloseKey(hKeyGeneral);
      return TRUE;
   }
   
   
   FitMode = GetEntryString(hKeyGeneral, "Fit", "3", TRUE); 
   if(FitMode.IsEmpty()) {Ret = FALSE; goto End;}

   m_Fit = (atol(FitMode));
   
End:
   
   RegCloseKey(hKeyGeneral);
   return Ret;
            
}

LONG ICRegIni::GetRegRot()
{
   HKEY    hKeyGeneral = NULL;
   bool    Exists = false;
   DWORD   error = 0;
   CString DeftValue;
   CString Rot;
   LONG    AuxRot;
   BOOL    Ret = TRUE; 

  
   hKeyGeneral = CreateKey(m_hProductKey, m_General, &Exists);
   
   if( hKeyGeneral == NULL ) {return FALSE; }
  
   
   if(!Exists)
   {   
      m_Rot = 0;
      RegCloseKey(hKeyGeneral);
      return TRUE;
   }
   
   
   Rot = GetEntryString(hKeyGeneral, "Rot", "0", TRUE); 
   if(Rot.IsEmpty()) {Ret = FALSE; goto End;}

   AuxRot = (atol(Rot));
   switch(AuxRot)
   {
      case 0:   m_Rot = 0; break;
      case 90:  m_Rot = 1; break;
      case 180: m_Rot = 2; break;
      case 270: m_Rot = 3; break;
   }
   
End:
   
   RegCloseKey(hKeyGeneral);
   return Ret;
            
}


BOOL ICRegIni::WriteEntryString(HKEY hkey, LPCTSTR pszEntry, LPCTSTR pszValue)
{
   ASSERT(hkey != NULL);
	ASSERT(pszEntry != NULL);

   DWORD error = RegSetValueEx(hkey, pszEntry, NULL, REG_SZ,
                               (LPBYTE)pszValue, (strlen(pszValue) + 1)*sizeof(TCHAR));
      
   SetLastError(error); 

	return (error == ERROR_SUCCESS);
}


BOOL ICRegIni::GetPalette()
{
   return(m_Palette);
}

LONG ICRegIni::GetDrawMode()
{
   return(m_DrawMode);
}

LONG ICRegIni::GetFit()
{
   return(m_Fit);
}

LONG ICRegIni::GetRot()
{
   return(m_Rot);
}

BOOL ICRegIni::SetPalette(BOOL Palette)
{
   HKEY hKeyGeneral;
   CString ShowPal;   

   hKeyGeneral = OpenKey(m_hProductKey, m_General);
   if(hKeyGeneral == NULL) { return FALSE; }

   ShowPal.Format("%i",Palette);
   

   return (WriteEntryString(hKeyGeneral,"Palette",ShowPal));
}

BOOL ICRegIni::SetDrawMode(LONG DrawMode)
{
   HKEY hKeyGeneral;
   CString DrawM;   

   hKeyGeneral = OpenKey(m_hProductKey, m_General);
   if(hKeyGeneral == NULL) { return FALSE; }

   DrawM.Format("%i",DrawMode);
   

   return (WriteEntryString(hKeyGeneral,"DrawMode",DrawM));
}


BOOL ICRegIni::SetFit(LONG FitMode)
{
   HKEY hKeyGeneral;
   CString Fit;   

   hKeyGeneral = OpenKey(m_hProductKey, m_General);
   if(hKeyGeneral == NULL) { return FALSE; }

   Fit.Format("%i",FitMode);
   

   return (WriteEntryString(hKeyGeneral,"Fit",Fit));
}

BOOL ICRegIni::SetRot(LONG Rot)
{
   HKEY hKeyGeneral;
   CString sRot;   

   hKeyGeneral = OpenKey(m_hProductKey, m_General);
   if(hKeyGeneral == NULL) { return FALSE; }

   switch(Rot)
   {
      case 0: sRot = "0"; break;
      case 1: sRot = "90"; break;
      case 2: sRot = "180"; break;
      case 3: sRot = "270"; break;
      default: sRot = "0";break;
   }
   

   return (WriteEntryString(hKeyGeneral,"Rot",sRot));
}

