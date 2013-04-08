// iRegIni.h : header file
//

//Clase que controla el manejo del regedit
//

#ifndef __IREGINI_H__
#define __IREGINI_H__


class ICRegIni
{
   public:

   ICRegIni();
   ~ICRegIni();

   public:
   
   BOOL  LoadSettings();
   BOOL  GetFrame();
   BOOL  GetRegPalette();
   LONG  GetRegDrawMode();
   LONG  GetRegFit();   
   LONG  GetRegRot();   
   BOOL  GetPalette();
   LONG  GetDrawMode();
   LONG  GetFit();
   LONG  GetRot();
   BOOL  SetPalette(BOOL Palette);
   BOOL  SetDrawMode(LONG DrawMode);
   BOOL  SetFit(LONG FitMode);
   BOOL  SetRot(LONG Rot);

   protected:
   
   HKEY    OpenKey(HKEY hParentKey, LPCSTR KeyString);
   HKEY    CreateKey(HKEY hParentKey, LPCSTR KeyString, bool FAR* Exists);
   CString GetEntryString(HKEY hParentkey, LPCTSTR pszEntry, LPCTSTR strDefault, BOOL Default);
   BOOL    WriteEntryString(HKEY hkey, LPCTSTR pszEntry, LPCTSTR pszValue);
   void    GetRectValues();
   void    GetDefaultFrame();
   

   protected:
  
   CString m_Product;
   BOOL    m_Palette;
   LONG    m_DrawMode;
   LONG    m_Fit;
   LONG    m_Rot;
   HKEY    m_hProductKey;
   CString m_Software;
   CString m_Company;
   CString m_General;

};

#endif // __IREGINI_H__