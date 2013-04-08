
#include "stdafx.h" 
#include "iDocGear.h"
#include "AuxFnc.h"
#include "ifile.h"
#include "iFont.h"
#include "Errs.h"

#define NAMEFONT "Arial"
#define TYPE     IFE_NONE
#define TAM      8

void GetFitRect(const CSize& SizeToFit,const CRect& DestRect,
                CRect FAR* FitRect)
{
   int cx,cy;

   FitRect->left = DestRect.left;
   FitRect->top  = DestRect.top;

   if ((double)DestRect.Width() * SizeToFit.cy < (double)DestRect.Height() * SizeToFit.cx)
   {
      cx = DestRect.Width();
      cy = (int) (((double)DestRect.Width() * SizeToFit.cy) / SizeToFit.cx);
   }
   else
   {
      cx = (int) (((double)DestRect.Height() * SizeToFit.cx) / SizeToFit.cy);
      cy = DestRect.Height();
   }

   FitRect->right = FitRect->left + cx;
   FitRect->bottom = FitRect->top + cy;
}



LONG GetPrinterDefault(CString& Driver,CString& Printer,CString& Port)
{
   HKEY  hParentKey = HKEY_CURRENT_CONFIG;
   HKEY  hLocalKey = HKEY_LOCAL_MACHINE;
   HKEY  hSubKey = NULL;   
   DWORD ret = 0;
   DWORD dwType = 0;
   DWORD dwCount = 0;
   CString Default("System\\CurrentControlSet\\Control\\Print\\Printers");
   CString PortIni("System\\CurrentControlSet\\Control\\Print\\Printers\\");
   CString DriverIni("System\\CurrentControlSet\\Control\\Print\\Environments\\Windows 4.0\\Drivers\\");

   ret = RegOpenKeyEx(hParentKey, Default, 0, KEY_READ, &hSubKey);
   RegCloseKey(hParentKey);
   if (hSubKey != NULL)
   {     
      ret = RegQueryValueEx(hSubKey, "Default", NULL, &dwType,
                          NULL, &dwCount);       
      if (ret) goto End;

      ret = RegQueryValueEx(hSubKey, "Default", NULL, &dwType,
                         (LPBYTE)Printer.GetBuffer(dwCount/sizeof(TCHAR)), &dwCount);
      Printer.ReleaseBuffer();
      if (ret) goto End;
   }
   PortIni += Printer;

   hSubKey = NULL;   
   ret = RegOpenKeyEx(hLocalKey, PortIni, 0, KEY_READ, &hSubKey);
   RegCloseKey(hParentKey);
   if (hSubKey != NULL)
   {     
      ret =RegQueryValueEx(hSubKey, "Port", NULL, &dwType,
                          NULL, &dwCount);       
      if (ret) goto End;

      ret = RegQueryValueEx(hSubKey, "Port", NULL, &dwType,
                         (LPBYTE)Port.GetBuffer(dwCount/sizeof(TCHAR)), &dwCount);
      Port.ReleaseBuffer();
      if (ret) goto End;
   }

   DriverIni += Printer;

   hSubKey = NULL;
   ret = RegOpenKeyEx(hLocalKey, DriverIni, 0, KEY_READ, &hSubKey);
   RegCloseKey(hParentKey);
   if (hSubKey != NULL)
   {     
      ret =RegQueryValueEx(hSubKey, "Driver", NULL, &dwType,
                          NULL, &dwCount);       
      if (ret) goto End;

      ret = RegQueryValueEx(hSubKey, "Driver", NULL, &dwType,
                         (LPBYTE)Driver.GetBuffer(dwCount/sizeof(TCHAR)), &dwCount);
      Driver.ReleaseBuffer();
      if (ret) goto End;
   }

End:

   if (ret)
   {
      CString val;
      int     index;
      GetProfileString("windows","Device",(LPSTR)"",val.GetBuffer(MAX_PATH - 1),MAX_PATH - 1);
      ret = 0;
      val.ReleaseBuffer();
      if (!val.IsEmpty())
      {
         index = val.Find(",");
         if (index != -1)
         {
            Printer = val.Left(index);
            val = val.Right(val.GetLength() - (index + 1));
            index = val.Find(",");
            if (index != -1)
            {
               Driver = val.Left(index);
               Port = val.Right(val.GetLength() - (index + 1));
            }
         }
      }
      else
         ret = (DWORD)-1;

   }

   return(ret);
}



void TermImg(HIGEAR hIGear)
{
   LPFNValid lpfn;
   LPFNDelete lpfndelete;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfndelete = (LPFNDelete)GetProcAddress(_Module.m_hModGear,"IG_image_delete");
   if (lpfndelete == NULL) return;

   if (lpfn(hIGear))
   {
      lpfndelete(hIGear);            
   }

}

LONG InitPageImg(CString FileName,LONG Page,HIGEAR* phIGear)
{
   LONG        Err = 0;
   LONG        ErrCount = 0;       
   LPFNLoad    lpfnload;     
   char        name[MAX_PATH];
   HANDLE      fd = 0;
     
      
   lpfnload = (LPFNLoad)GetProcAddress(_Module.m_hModGear,"IG_load_FD");
   if (lpfnload == NULL) {Err = -1; goto End;}   
   
   lstrcpy(name,FileName);   
   
   fd = CreateFile(name,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,
              FILE_ATTRIBUTE_NORMAL,NULL);
   
   if (fd != INVALID_HANDLE_VALUE)
   {
      ErrCount = lpfnload((int)fd,0,Page,1,phIGear);
      if (ErrCount != IGE_SUCCESS)
      {
         Err = GetError();              
      }

      CloseHandle(fd);
   }
   else
   {      
      Err = (LONG)GetLastError();    
   }
   
   End:
      
   return Err;
         
}

LONG InitPageImgX(CString FileName,LONG Page,HIGEAR* phIGear, ULONG FAR* TypeSave)
{

   LONG        Err = 0;
   LONG        ErrCount = 0;       
   LPFNLoad    lpfnload;   
   LPFNInfo    lpfnInfo;
   char        name[MAX_PATH];
   HANDLE      fd = 0;
   LONG        FileType;
   LONG        FileCompress;   
   AT_DIB      dibInfo;  
      
   lpfnload = (LPFNLoad)GetProcAddress(_Module.m_hModGear,"IG_load_FD");
   if (lpfnload == NULL) {Err = -1; goto End;}  
   lpfnInfo = (LPFNInfo)GetProcAddress(_Module.m_hModGear,"IG_info_get_FD");
   if (lpfnInfo == NULL) {Err = -1; goto End;}
   
   lstrcpy(name,FileName);   
   
   fd = CreateFile(name,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,
              FILE_ATTRIBUTE_NORMAL,NULL);
   
   if (fd != INVALID_HANDLE_VALUE)
   {
      ErrCount = lpfnload((int)fd,0,Page,1,phIGear);
      if (ErrCount != IGE_SUCCESS)
      {
         Err = GetError();              
      }
      else
      {         
         lpfnInfo((int)fd,0,Page,(LPAT_MODE)&FileType,(LPAT_MODE)&FileCompress,&dibInfo);
         GetTypeSave(FileType,FileCompress,TypeSave);
      }

      CloseHandle(fd);
   }
   else
   {      
      Err = (LONG)GetLastError();    
   }
   
   End:
      
   return Err;
         
}



LONG PrintPage(HIGEAR hIGear,CDC* pDC,CRect FitRectDC,UINT bpp)
{
   LONG              Err = 0;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;
   LPFNValid         lpfn;
   LPFNDeviceRectSet lpfnDevRS;
   LPFNrunToDIB      lpfnRToDIB;
   LPFNPrint         lpfnPrint;

   ASSERT( FitRectDC != NULL );

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}     
   lpfnDevRS = (LPFNDeviceRectSet)GetProcAddress(_Module.m_hModGear,"IG_device_rect_set");
   if (lpfnDevRS == NULL) {Err = -1;goto End;}
   lpfnRToDIB = (LPFNrunToDIB)GetProcAddress(_Module.m_hModGear,"IG_IP_convert_runs_to_DIB");
   if (lpfnRToDIB == NULL) {Err = -1;goto End;}
   lpfnPrint = (LPFNPrint)GetProcAddress(_Module.m_hModGear,"IG_print_image");
   if (lpfnPrint == NULL) {Err = -1;goto End;}

	if (!FitRectDC.IsRectEmpty())
	{   
      Errcount = lpfnDevRS(hIGear,(LPAT_RECT)&FitRectDC);
      if (Errcount)
      {
         Err = GetError();
         goto End;
      }
      
      if (bpp == 1)      
      {
         lpfnRToDIB(hIGear);
      } 
											 
      Errcount = lpfnPrint(hIGear,pDC->GetSafeHdc(),TRUE);
      if (Errcount) Err = GetError();
	   
   }
		
End:

	return (Err);

}

LONG AddPageToFile(HIGEAR hIGear, int fd, ULONG TypeToSave)
{
   LONG              Err = 0;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;   
   LPFNValid         lpfn;
   LPFNSave          lpfnSave;   

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;} 
      
   lpfnSave = (LPFNSave)GetProcAddress(_Module.m_hModGear,"IG_save_FD");
   if (lpfnSave == NULL) {Err = -1; goto End;}  
   
   Errcount = lpfnSave(hIGear,(int)fd,0,0,TypeToSave);
   if (Errcount != IGE_SUCCESS)
   {
      Err = GetError();
   }


   End:

   return Err;
   

}

BOOL RotImg(CDC* pDC,int W,int H)
{
   int XPrn,YPrn;

   XPrn = pDC->GetDeviceCaps(HORZRES);
   YPrn = pDC->GetDeviceCaps(VERTRES);

   if (XPrn > YPrn)
   {
      if ((W / H) == 0)
         return TRUE;
   }
   else
   {
      if ((H / W) == 0)
         return TRUE;
   }

   return FALSE;
}

LONG SetText(CDC* pDC,CString& Txt,CString& NameFont,LONG Enh,LONG Tam)
{
   LONG   InitPrint = 0;
   CFont* pOldFont = NULL;
   CFont* pTxtFont = NULL;
   CSize  sizeChar;
   int    W;

   if (Txt.IsEmpty())
      goto End;

   if (!NameFont.IsEmpty())
   {
      pTxtFont = new ICFont(NameFont,Tam,Enh,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }

   W = pDC->GetDeviceCaps(HORZRES);
   
   sizeChar = pDC->GetTextExtent(Txt);
   if (sizeChar.cx > W)
   {
      if (pTxtFont != NULL)
      {
         pDC->SelectObject(pOldFont);
         delete pTxtFont;
         pTxtFont = NULL;
      }

      pTxtFont = new ICFont(NAMEFONT,TAM,TYPE,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }
   

   InitPrint = sizeChar.cy;

   pDC->TextOut(0,0,Txt);

   if (pOldFont != NULL)
      pDC->SelectObject(pOldFont);

   if (pTxtFont != NULL)
      delete pTxtFont;

   End:
 
   return(InitPrint);

}

LONG ReadStamp(CString Stp,CString& Txt,CString& NameFont,LONG& Enh,LONG& Tam)
{

   LONG    Err = 0;
   ICFile  File;
   CString Line;
   int     index;
   CString AuxEnh,AuxTam;
   BOOL    EndOfFile = FALSE;

   Err = File.Open(Stp,OF_READ);
   if (Err) goto End;

   Err = File.ReadLine(Line,&EndOfFile);
   if (Err) goto End;

   while (!EndOfFile)
   {
      CString Aux = Line;
      Aux.MakeUpper();
      index = Aux.Find("FUENTE:");
      if (index != -1)
      {
         Line = Line.Right(Line.GetLength() - (index + 7));
         GetFontFromLine(Line,NameFont,Enh,Tam);
         
      }
      else
      {
         index = Aux.Find("SELLO:");
         if (index != -1)
         {
            Txt = Line.Right(Line.GetLength() - (index + 6));
         }
      }

      Err = File.ReadLine(Line,&EndOfFile);
      if (Err) goto End;
   }
   
      
   
   End:

   File.Close();

   return(Err);
}

void GetFontFromLine(CString Line,CString& NameFont,LONG& Enh,LONG& Tam)
{

   int     index;
   CString Aux(Line);
   CString AuxEnh,AuxTam;

   
   index = Aux.Find(';');
   if (index != -1)
   {
      NameFont = Aux.Left(index);
      Aux = Aux.Right(Aux.GetLength() - (index + 1));

      index = Aux.Find(';');
      if (index != -1)
      {
         AuxEnh = Aux.Left(index);
         AuxTam = Aux.Right(Aux.GetLength() - (index + 1));
         Tam = atol(AuxTam);
         if (!AuxEnh.CompareNoCase("Normal"))
            Enh = IFE_NONE;
         else if (!AuxEnh.CompareNoCase("Cursiva"))
            Enh = IFE_ITALIC;
         else if (!AuxEnh.CompareNoCase("Negrita"))
            Enh = IFE_BOLD;
         else if (!AuxEnh.CompareNoCase("Negrita cursiva"))
            Enh = IFE_BOLD |IFE_ITALIC;
         else
            Enh = IFE_NONE;

      }
      else
      {
         NameFont.Empty();
         Enh = Tam = 0;
      } 
   }
   else
   {
      NameFont.Empty();
      Enh = Tam = 0;
   }

   return;
}

LONG GetError()
{
	AT_ERRCOUNT  Errcount = 0;
	AT_ERRCODE   iCode    = 0;
	LONG         Err;
   LPFNGetError lpfnGetError;
   LPFNClearErr lpfnclear;

   lpfnGetError = (LPFNGetError)GetProcAddress(_Module.m_hModGear,"IG_error_get");
   if (lpfnGetError == NULL) {Err = -1; goto End;}
   lpfnclear = (LPFNClearErr)GetProcAddress(_Module.m_hModGear,"IG_error_clear");
   if (lpfnGetError == NULL) {Err = -1; goto End;}

	
	lpfnGetError(0, NULL, 0, NULL, (LPAT_ERRCODE)&iCode, NULL, NULL);
	lpfnclear();

   Err = (LONG)iCode;
		
End:

   return(Err);
}

LONG GetEndoso(LPCSTR FileName,CString& Endoso) 
{
   LONG           Err = 0L;   
   UINT           Ret = 0;    
   LONG           OneDir;    
   LONG           value;
   short          NumTag,i; 
   unsigned short TypeTag = 0;
   short          DataTag; 
   short          DataSize;   
   char           D;
   OFSTRUCT       OFS;
   HFILE          hFile;
   
   Endoso.Empty();

   hFile = OpenFile(FileName,&OFS,OF_READWRITE);
   if (hFile == HFILE_ERROR)
   { Err = hFile;  goto End; }

   Err =_llseek(hFile,4,1); //Ir a la posición 4
   if (Err == HFILE_ERROR) goto End;
   
   //Leo el puntero a la primera dirección
   Ret = _lread(hFile,&OneDir,4); 
   if (Ret < 1)
   { Err = hFile;  goto End; }   
   
   // Me posiciono en esa dirección y leo el número de tags
   Err = _llseek(hFile,OneDir,0);   
   if (Err == HFILE_ERROR) goto End; 
      
   Ret = _lread((int)hFile,&NumTag,2); 
   if (Ret < 1)
   { Err = hFile;  goto End; }         

   // Busco el tag de DocumentName
   for (i=0; i<NumTag; i++)
   {
      Ret = _lread(hFile,&TypeTag,2); 
      if (Ret < 1)
      { Err = (LONG)Ret; goto End; } 

      if (TypeTag == 269)
         break;
      
      Err = _llseek(hFile,10,1);   
      if (Err == HFILE_ERROR) goto End;
   }


   if (TypeTag == 269)
   {
      Ret = _lread(hFile,&DataTag,2);
      if(DataTag == 2)
      {//el tipo de tag es un ASCCI
         Ret = _lread(hFile,&DataSize,2);
         if (Ret < 1)
         { Err = (LONG)Ret; goto End; }

         Err = _llseek(hFile,2,1);
         if (Err == HFILE_ERROR) goto End;

         Ret = _lread(hFile,&value,4); 
         if (Ret < 1)
         { Err = (LONG)Ret; goto End; }

         Err = _llseek(hFile,value,0);
         if (Err == HFILE_ERROR) goto End;

         for (int j = 1; j<= DataSize; j++)
         {
            _lread(hFile,(LPVOID)&D,1); 
            Endoso = Endoso + D;
         }
      }
   }
   

   End:
      
   if (hFile != HFILE_ERROR)
      _lclose(hFile);

   return(Err);
   
}

LONG GetBitPerPixel(HIGEAR hIGear,UINT& bpp)
{
   LONG           Err = 0;    
   AT_DIMENSION   W,H;
   LPFNValid      lpfn = NULL;
   LPFNDimensions lpfnDimensions = NULL;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;} 
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}

   
   if (lpfn(hIGear))
   {
      Err = lpfnDimensions(hIGear,&W,&H,&bpp);
      if (Err) Err = GetError();
   }

   End:

   return (Err);   
}

LONG InvertPalette(HIGEAR hIGear)
{
   LONG        Err = 0;
   AT_ERRCOUNT ret;

   LPFNValid     lpfn = NULL;
   LPFNImgInvert lpfnImgInvert = NULL;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;} 
   lpfnImgInvert = (LPFNImgInvert)GetProcAddress(_Module.m_hModGear,"IG_IP_contrast_invert");
   if (lpfnImgInvert == NULL) {Err = -1; goto End;};

   if (lpfn(hIGear))
   {
      ret = lpfnImgInvert(hIGear,NULL,IG_CONTRAST_PALETTE);
      if (ret)
         Err = GetError();
   }
   
   End:

   return (Err);

}
   
LONG ReadTxt(CString FTxt,CStringArray& TxtArr,CString& NameFont,LONG& Enh,LONG& Tam)
{

   LONG    Err = 0;
   ICFile  File;
   CString Line;
   int     index;
   CString Txt;
   CString AuxEnh,AuxTam;
   BOOL    EndOfFile = FALSE;

   Err = File.Open(FTxt,OF_READ);
   if (Err) goto End;

   Err = File.ReadLine(Line,&EndOfFile);
   if (Err) goto End;

   while (!EndOfFile)
   {
      CString Aux = Line;
      Aux.MakeUpper();
      index = Aux.Find("FUENTE:");
      if (index != -1)
      {
         Line = Line.Right(Line.GetLength() - (index + 7));
         GetFontFromLine(Line,NameFont,Enh,Tam);
         
      }
      else
      {
         index = Aux.Find("SELLO:");
         if (index != -1)
         {
            Txt = Line.Right(Line.GetLength() - (index + 6));
            TxtArr.Add(Txt);
         }
      }

      Err = File.ReadLine(Line,&EndOfFile);
      if (Err) goto End;
   }
   
      
   
   End:

   File.Close();

   return(Err);
}

LONG SetText(CDC* pDC,CStringArray& TxtArr,CString& NameFont,LONG Enh,LONG Tam,BOOL Up)
{
   LONG   InitPrint = 0;
   LONG   InitPrintLine = 0;
   CFont* pOldFont = NULL;
   CFont* pTxtFont = NULL;
   CString Txt;
   CSize  sizeChar(0,0);
   int    W,i;

   
   if (!NameFont.IsEmpty())
   {
      pTxtFont = new ICFont(NameFont,Tam,Enh,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }

   W = pDC->GetDeviceCaps(HORZRES);
   
   for (i=0; i<TxtArr.GetSize(); i++)
   {
      CSize auxSize;
      Txt = TxtArr.GetAt(i);
      
      auxSize = pDC->GetTextExtent(Txt);
      if (auxSize.cx > sizeChar.cx)
      {
         sizeChar = auxSize;
         sizeChar.cx = auxSize.cx;
         sizeChar.cy = auxSize.cy;
      }
   }
   if (sizeChar.cx > W)
   {
      if (pTxtFont != NULL)
      {
         pDC->SelectObject(pOldFont);
         delete pTxtFont;
         pTxtFont = NULL;
      }

      pTxtFont = new ICFont(NAMEFONT,TAM,TYPE,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }
   
   
   InitPrint = sizeChar.cy * TxtArr.GetSize();

   if (Up)
   {
      for(i=0; i <TxtArr.GetSize(); i++)
      {
         Txt = TxtArr.GetAt(i);
         pDC->TextOut(0,InitPrintLine,Txt);
         InitPrintLine = InitPrintLine + sizeChar.cy;
      }
   }

   if (pOldFont != NULL)
      pDC->SelectObject(pOldFont);

   if (pTxtFont != NULL)
      delete pTxtFont;

    
   return(InitPrint);

}

LONG PrintText(CDC* pDC,int InitPrint,CStringArray& TxtArr,CString& NameFont,LONG Enh,LONG Tam)
{

   LONG   Err = 0;
   LONG   TamPrint = 0;
   CFont* pOldFont = NULL;
   CFont* pTxtFont = NULL;
   CString Txt;
   CSize  sizeChar(0,0);
   int    W,i;

   
   if (!NameFont.IsEmpty())
   {
      pTxtFont = new ICFont(NameFont,Tam,Enh,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }

   W = pDC->GetDeviceCaps(HORZRES);
   
   for (i=0; i<TxtArr.GetSize(); i++)
   {
      CSize auxSize;
      Txt = TxtArr.GetAt(i);
      
      auxSize = pDC->GetTextExtent(Txt);
      if (auxSize.cx > sizeChar.cx)
      {
         sizeChar = auxSize;
         sizeChar.cx = auxSize.cx;
         sizeChar.cy = auxSize.cy;
      }
   }
   if (sizeChar.cx > W)
   {
      if (pTxtFont != NULL)
      {
         pDC->SelectObject(pOldFont);
         delete pTxtFont;
         pTxtFont = NULL;
      }

      pTxtFont = new ICFont(NAMEFONT,TAM,TYPE,pDC);
      if (pTxtFont != NULL)
         pOldFont = pDC->SelectObject(pTxtFont);
   }

   TamPrint = sizeChar.cy;
   for(i=0; i <TxtArr.GetSize(); i++)
   {
      Txt = TxtArr.GetAt(i);
      pDC->TextOut(0,InitPrint,Txt);
      InitPrint = InitPrint + TamPrint;
   }

   if (pOldFont != NULL)
      pDC->SelectObject(pOldFont);

   if (pTxtFont != NULL)
      delete pTxtFont;

    
   return(Err);

}

void GetTypeSave(int Type,int Compress,ULONG FAR* TypeSave)
{

   switch (Type)
   {
      case IG_FORMAT_UNKNOWN:  {*TypeSave = IG_SAVE_UNKNOWN;                  break;}
      case IG_FORMAT_BMP:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_BRK:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_CAL:      {*TypeSave = IG_SAVE_CAL;                      break;}
      case IG_FORMAT_CLP:      {*TypeSave = IG_SAVE_CLP;                      break;}
      case IG_FORMAT_DCX:      {*TypeSave = IG_SAVE_DCX;                      break;}
      case IG_FORMAT_EPS:      {*TypeSave = IG_SAVE_EPS;                      break;}
      case IG_FORMAT_ICA:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_ICO:      {*TypeSave = IG_SAVE_ICO;                      break;}
      case IG_FORMAT_IMT:      {*TypeSave = IG_SAVE_IMT;                      break;}
      case IG_FORMAT_IFF:      {*TypeSave = IG_SAVE_IFF;                      break;}
      case IG_FORMAT_JPG:      {*TypeSave = IG_SAVE_JPG;                      break;}
     // case IG_FORMAT_PJPEG:    {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_MOD:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_PCT:      {*TypeSave = IG_SAVE_PCT;                      break;}
      case IG_FORMAT_PCX:      {*TypeSave = IG_SAVE_PCX;                      break;}
      case IG_FORMAT_PNG:      {*TypeSave = IG_SAVE_PNG;                      break;}
      case IG_FORMAT_PSD:      {*TypeSave = IG_SAVE_PSD;                      break;}
      case IG_FORMAT_RAS:      {*TypeSave = IG_SAVE_RAS;                      break;}
      case IG_FORMAT_SGI:      {*TypeSave = IG_SAVE_SGI;                      break;}
      case IG_FORMAT_TGA:      {*TypeSave = IG_SAVE_TGA;                      break;}
      case IG_FORMAT_NCR:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_TIF:      {*TypeSave = (Type|((AT_MODE)Compress << 16)); break;}
      case IG_FORMAT_XBM:      {*TypeSave = IG_SAVE_XBM;                      break;}
      case IG_FORMAT_XPM:      {*TypeSave = IG_SAVE_XPM;                      break;}
      case IG_FORMAT_XWD:      {*TypeSave = IG_SAVE_XWD;                      break;}
      default:                 {*TypeSave = IG_SAVE_BMP_UNCOMP;               break;}
      
   }

}

LONG GetTempFile(CString& TempFile)
{

   char TmpDir[MAX_PATH];
   char TmpFile[MAX_PATH];
   LONG Err = 0;
   int  Ret;


   Ret = GetTempPath(MAX_PATH, TmpDir);
   if(Ret == 0) { Err = IDOCIMGX_ERR_CREATE_TEMP_DIR; goto End; }

   Ret = GetTempFileName(TmpDir,"IMG",0,TmpFile);
   if (Ret == 0)
   {
      Err = GetLastError();
      goto End;
   }

   TempFile = TmpFile;

   End: 

   return (Err);

}

LONG GetNumPagesFile(CString FileName, LONG& NumPages)
{

   LPFNPage    lpfnpage; 
   AT_ERRCOUNT ErrCount = 0;
   LONG        Err = 0;
   char*       file = NULL;

   lpfnpage = (LPFNPage)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnpage == NULL) {Err = -1; goto End;}
   
   file = FileName.GetBuffer(FileName.GetLength());
   ErrCount = lpfnpage(file,(unsigned int*) &NumPages);
   FileName.ReleaseBuffer();
   if (ErrCount)
   {
      Err = GetError();
   }

   End:

   return Err;

}