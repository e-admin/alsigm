// PrintJob.cpp : Implementation of CPrintJob
#include "stdafx.h"
#include <afxinet.h>
#include "IDocImgX.h"
#include "iDocGear.h"
#include "PrintJob.h"
#include "AuxFnc.h"
#include "Errs.h"
#include "Misc.h"


#define EI_PRINTJOB  "PrintJob"
/////////////////////////////////////////////////////////////////////////////
// CPrintJob
/*
BOOL CPrintJob::URLDownLoad(CString& FileName)
{  
   CString CacheFileName;
   BOOL ret = FALSE;

   if ( FAILED(URLDownloadToCacheFile(
      NULL,                // control's IUnknown
      FileName,                         // URL to download
      CacheFileName.GetBuffer(MAX_PATH),// buffer to fill with name
      MAX_PATH,                               // buffer length
      0,                                      // reserved
      NULL )) )                               // no status information
   {      
      FileName.Empty();      
   } 
   else
   {
      CacheFileName.ReleaseBuffer();
      FileName = CacheFileName;
      ret = TRUE;
   }
      
   return(ret);
}
*/

STDMETHODIMP CPrintJob::PrintFile(BSTR FileName,VARIANT_BOOL Auto)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString Txt,NameFont;
   LONG    Enh = 0;
   LONG    Tam = 0;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
	CString Aux = File.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(File);
      if (Err) goto End; 
   }

   Err = PrintFile(File,Auto,Txt,NameFont,Enh,Tam);      

   End:

   DelFiles();

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"PrintFile",Err);
      hRes = E_FAIL;
   }
   
	return(hRes);
}

STDMETHODIMP CPrintJob::PrintFileWStamp(BSTR FileName,BSTR Stamp,VARIANT_BOOL Auto)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString Stp(Stamp);
   CString Txt,NameFont;
   LONG    Enh,Tam;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())
  
	  
   CString Aux = File.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(File);         
      if (Err) goto End;
   }

   Aux = Stp.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(Stp);
      if (Err) goto End;
   }

   Err = ReadStamp(Stp,Txt,NameFont,Enh,Tam);
   if (Err) goto End;

   Err = PrintFile(File,Auto,Txt,NameFont,Enh,Tam);   
   

   End:

   DelFiles();

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"PrintFileWStamp",Err);
      hRes = E_FAIL;
   }
   
	return(hRes);
}

STDMETHODIMP CPrintJob::PrintFileWTxt(BSTR FileName,BSTR FileTxt,VARIANT_BOOL Auto,
                                      VARIANT_BOOL Up)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString FTxt(FileTxt);
   CString NameFont;
   CStringArray TxtArr;
   LONG    Enh,Tam;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())
  
	  
   CString Aux = File.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(File);         
      if (Err) goto End;
   }

   Aux = FTxt.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(FTxt);
      if (Err) goto End;
   }

   Err = ReadTxt(FTxt,TxtArr,NameFont,Enh,Tam);
   if (Err) goto End;

   
   Err = PrintFileTxt(File,Auto,TxtArr,NameFont,Enh,Tam,Up);   
   

   End:

   DelFiles();

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"PrintFileWStamp",Err);
      hRes = E_FAIL;
   }
   
	return(hRes);
}

STDMETHODIMP CPrintJob::InitializePrint(BSTR JobName)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())
           
   Err = InitPrint(JobName);      
   
   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"InitializePrint",Err);
      hRes = E_FAIL;
   }

   return(hRes);
}

STDMETHODIMP CPrintJob::PagePrint(BSTR FileName,BSTR Stamp,VARIANT_BOOL Auto)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString STP(Stamp);
   CString Txt,NameFont;
   LONG    Enh,Tam;
   BOOL    IsSTPEmpty = TRUE;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
   CString Aux = File.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(File);
      if (Err) goto End; 
   }
   
   IsSTPEmpty = STP.IsEmpty();
   if (IsSTPEmpty == FALSE)
   {
      Aux = STP.Left(4);
      if (!Aux.CompareNoCase("http"))
      {
         Err = URLDownLoad(STP);
         if (Err) goto End; 
      }

   }

   if (!IsSTPEmpty)
   {
      Err = ReadStamp(STP,Txt,NameFont,Enh,Tam);
      if (Err) goto End;
   }

   Err = PagePrint(File,Auto,Txt,NameFont,Enh,Tam);

   End:

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"PagePrint",Err);
      hRes = E_FAIL;
   }

   return(hRes);
}

STDMETHODIMP CPrintJob::PageNormalSizePrint(BSTR FileName,BSTR Stamp)
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString STP(Stamp);
   CString Txt,NameFont;
   LONG    Enh,Tam;
   BOOL    IsSTPEmpty = TRUE;
   BOOL    Auto = FALSE;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
   CString Aux = File.Left(4);
   if (!Aux.CompareNoCase("http"))
   {
      Err = URLDownLoad(File);
      if (Err) goto End; 
   }
   
   IsSTPEmpty = STP.IsEmpty();
   if (IsSTPEmpty == FALSE)
   {
      Aux = STP.Left(4);
      if (!Aux.CompareNoCase("http"))
      {
         Err = URLDownLoad(STP);
         if (Err) goto End; 
      }

   }

   if (!IsSTPEmpty)
   {
      Err = ReadStamp(STP,Txt,NameFont,Enh,Tam);
      if (Err) goto End;
   }

   Err = PagePrint(File,Auto,Txt,NameFont,Enh,Tam,TRUE);
   

   End:

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"PagePrint",Err);
      hRes = E_FAIL;
   }

   return(hRes);
}

STDMETHODIMP CPrintJob::TerminatePrint()
{
   HRESULT hRes = S_OK;
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   DelFiles();
   Err = TermPrint();
   

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_PRINTJOB,"TerminatePrint",Err);
      hRes = E_FAIL;
   }

   return(hRes);

}



///////////////////////////FUNCIONES////////////////////////////////////////////

void CPrintJob::DelFiles()
{
   for (int i=0; i<m_DelFilesArr.GetSize(); i++)
   {
      CString File = m_DelFilesArr.GetAt(i);
      DeleteFile(File);
   }
   m_DelFilesArr.RemoveAll();
}



LONG CPrintJob::PrintFile(CString File, BOOL Auto, CString Txt,CString NameFont,LONG Enh,LONG Tam)
{ 
   HDC       hDC; 
   LONG      Err = 0;
   CString   Driver,Printer,Port;
   CDC*      pDC;
   HIGEAR    hIGear = 0;
   CRect     DCRect;
   UINT      bpp;
   int       InitY = 0;   
   UINT      NumPages;
   char      AuxFile[MAX_PATH];
   LPFNValid      lpfn;   
   LPFNDimensions lpfnDimensions;
   LPFNRotate     lpfnRotate;
   LPFNNumPages   lpfnNumPages;
   LPFNGetError   lpfnGetError;
 

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) {Err = -1; goto End;}
   lpfnNumPages = (LPFNNumPages)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnNumPages == NULL) {Err = -1; goto End;}
   lpfnGetError = (LPFNGetError)GetProcAddress(_Module.m_hModGear,"IG_error_get");
   if (lpfnGetError == NULL) {Err = -1; goto End;}

   

   Err = GetPrinterDefault(Driver,Printer,Port);
   if (Err)
   {      
      goto End;
   }
   
   hDC = ::CreateDC(Driver,Printer,Port,NULL);

   if (hDC != NULL)
   {      
      pDC = CDC::FromHandle(hDC);      

      DOCINFO docInfo;
	   memset(&docInfo, 0, sizeof(DOCINFO));
	   docInfo.cbSize = sizeof(DOCINFO);
	   docInfo.lpszDocName = File;

      pDC->StartDoc(&docInfo);
         
      lstrcpy(AuxFile,File);
      Err = lpfnNumPages(AuxFile,&NumPages);      
      if (Err)
      {
         lpfnGetError(0, NULL, 0, NULL, (LPAT_ERRCODE)&Err, NULL, NULL);
         goto End;
      }

      for (int k=1; k<= (int)NumPages; k++)
      {  
         
         TermImg(hIGear);
         Err = InitPageImg(File,k,&hIGear);
         if (Err)
            goto End;
         
         if (pDC->StartPage() < 0)
         {
            Err = IDOCIMGX_ERR_STARTPAGE;
            TermImg(hIGear);
            pDC->EndDoc();
            ::DeleteDC(hDC);            
            goto End;
         }          
         else
         {	               
            CRect FitRectDC;
            CSize ImgSize;

            if (lpfn(hIGear))
               lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);

            if (RotImg(pDC,ImgSize.cx,ImgSize.cy) && Auto)
            {               
               Err = lpfnRotate(hIGear,IG_ROTATE_90);
               if (Err)
               {
                  TermImg(hIGear);
                  pDC->EndDoc();
                  ::DeleteDC(hDC);                  
                  goto End;
               }
               lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);
            }
               
            InitY = SetText(pDC,Txt,NameFont,Enh,Tam);

            CRect ImgRect = CRect(CPoint(0,0),ImgSize);
            DCRect.left = 0;
            DCRect.top = InitY;
            DCRect.right = GetDeviceCaps(hDC, HORZRES);
            DCRect.bottom = GetDeviceCaps(hDC, VERTRES);

            GetFitRect(ImgSize,DCRect,&FitRectDC);
           
            Err = PrintPage(hIGear,pDC,FitRectDC,bpp);
                       
            if (Err != 0)
            {
               TermImg(hIGear);
               pDC->EndDoc();
               ::DeleteDC(hDC);               
               goto End;
            }
            

         }
         pDC->EndPage();

      }//fin del for		  

      TermImg(hIGear);

      pDC->EndDoc();

      ::DeleteDC(hDC);  
   }
   else
      Err  = -1;    

End:

   return(Err);
}

LONG CPrintJob::PrintFileTxt(CString File, BOOL Auto, CStringArray& TxtArr,CString NameFont,LONG Enh,LONG Tam,BOOL Up)
{ 
   HDC       hDC; 
   LONG      Err = 0;
   CString   Driver,Printer,Port;
   CDC*      pDC;
   HIGEAR    hIGear = 0;
   CRect     DCRect;
   UINT      bpp;
   int       InitY = 0;   
   UINT      NumPages;
   char      AuxFile[MAX_PATH];
   LPFNValid      lpfn;   
   LPFNDimensions lpfnDimensions;
   LPFNRotate     lpfnRotate;
   LPFNNumPages   lpfnNumPages;
 

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) {Err = -1; goto End;}
   lpfnNumPages = (LPFNNumPages)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnNumPages == NULL) {Err = -1; goto End;}

   

   Err = GetPrinterDefault(Driver,Printer,Port);
   if (Err)
   {      
      goto End;
   }
   
   hDC = ::CreateDC(Driver,Printer,Port,NULL);

   if (hDC != NULL)
   {      
      pDC = CDC::FromHandle(hDC);      

      DOCINFO docInfo;
	   memset(&docInfo, 0, sizeof(DOCINFO));
	   docInfo.cbSize = sizeof(DOCINFO);
	   docInfo.lpszDocName = File;

      pDC->StartDoc(&docInfo);
         
      lstrcpy(AuxFile,File);
      lpfnNumPages(AuxFile,&NumPages);      

      for (int k=1; k<= (int)NumPages; k++)
      {  
         
         TermImg(hIGear);
         Err = InitPageImg(File,k,&hIGear);
         if (Err)
            goto End;
         
         if (pDC->StartPage() < 0)
         {
            Err = IDOCIMGX_ERR_STARTPAGE;
            TermImg(hIGear);
            pDC->EndDoc();
            ::DeleteDC(hDC);            
            goto End;
         }          
         else
         {	               
            CRect FitRectDC;
            CSize ImgSize;

            if (lpfn(hIGear))
               lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);

            if (RotImg(pDC,ImgSize.cx,ImgSize.cy) && Auto)
            {               
               Err = lpfnRotate(hIGear,IG_ROTATE_90);
               if (Err)
               {
                  TermImg(hIGear);
                  pDC->EndDoc();
                  ::DeleteDC(hDC);                  
                  goto End;
               }
               lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);
            }
               
            InitY = SetText(pDC,TxtArr,NameFont,Enh,Tam,Up);

            CRect ImgRect = CRect(CPoint(0,0),ImgSize);
            DCRect.left = 0;
            DCRect.top = 0;
            DCRect.right = GetDeviceCaps(hDC, HORZRES);
            DCRect.bottom = GetDeviceCaps(hDC, VERTRES);

            if (Up)
               DCRect.top = InitY + (InitY / TxtArr.GetSize());
            else
               DCRect.bottom = DCRect.bottom - (InitY + (InitY / TxtArr.GetSize()));


            GetFitRect(ImgSize,DCRect,&FitRectDC);
           
            Err = PrintPage(hIGear,pDC,FitRectDC,bpp);

            if (!Up)
            {
               FitRectDC.bottom = FitRectDC.bottom + (InitY / TxtArr.GetSize());
               Err = PrintText(pDC,FitRectDC.bottom ,TxtArr,NameFont,Enh,Tam);
            }
                       
            if (Err != 0)
            {
               TermImg(hIGear);
               pDC->EndDoc();
               ::DeleteDC(hDC);               
               goto End;
            }
            

         }
         pDC->EndPage();

      }//fin del for		  

      TermImg(hIGear);

      pDC->EndDoc();

      ::DeleteDC(hDC);  
   }
   else
      Err  = -1;    

End:

   return(Err);
}

LONG CPrintJob::InitPrint(CString JobName)
{
   LONG    Err = 0;
   CString Driver,Printer,Port;
   DOCINFO docInfo;


   if (m_hDC != NULL)
   {
      Err = IDOCIMGX_ERR_ALREADY_INIT;
      goto End;
   }

   Err = GetPrinterDefault(Driver,Printer,Port);
   if (Err) goto End;

   m_hDC = ::CreateDC(Driver,Printer,Port,NULL);
   if (m_hDC == NULL)
   {      
      Err = IDOCIMGX_ERR_INITPRINT;
      goto End;
   }

   m_pDC = CDC::FromHandle(m_hDC);
   memset(&docInfo,0,sizeof(DOCINFO));
   docInfo.cbSize = sizeof(DOCINFO);
   docInfo.lpszDocName = JobName;

   if (m_pDC->StartDoc(&docInfo) == -1)
   {      
      Err = IDOCIMGX_ERR_INITPRINT;
   }

   End:

   if (Err)
   {
      if (Err != IDOCIMGX_ERR_ALREADY_INIT)
      {
         if (m_hDC != NULL)
         {
            ::DeleteDC(m_hDC);
            m_pDC = NULL;
            m_hDC = NULL;
         }
      }
   }

   return(Err);
}




LONG CPrintJob::PagePrint(CString File,BOOL Auto,CString Txt,CString NameFont,LONG Enh,LONG Tam,BOOL NormalSize)
{
   LONG           Err = 0;
   UINT           NumPages = 1;
   int            i;
   UINT           bpp;
   LONG           InitY;
   CRect          DCRect;
   char           AuxFile[MAX_PATH];
   LPFNNumPages   lpfnNumPages;
   LPFNValid      lpfn;
   LPFNDimensions lpfnDimensions;
   LPFNRotate     lpfnRotate;
   LPFNResolution lpfnResolution;
   HIGEAR         hIGear = 0;


   if (m_hDC == NULL)
   {
      Err = IDOCIMGX_ERR_NOT_INITPRINT;
      goto End;
   }

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) {Err = -1; goto End;}
   lpfnNumPages = (LPFNNumPages)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnNumPages == NULL) {Err = -1; goto End;}
   lpfnResolution = (LPFNResolution)GetProcAddress(_Module.m_hModGear,"IG_image_resolution_get");
   if (lpfnResolution == NULL) {Err = -1; goto End;}

   lstrcpy(AuxFile,File);
   
   Err = lpfnNumPages(AuxFile,&NumPages);
   if (Err) 
   {
      Err = GetError();
      goto End;
   }

   
   for (i=1; i<= (int)NumPages; i++)
   {
      TermImg(hIGear);
      Err = InitPageImg(File,i,&hIGear);
      if (Err) goto End;
      
      if (m_pDC->StartPage() < 0)
      {         
         Err = IDOCIMGX_ERR_STARTPAGE;
         TermImg(hIGear);
         m_pDC->EndDoc();
         ::DeleteDC(m_hDC);
         m_hDC = NULL;
         m_pDC = NULL;
      }
      else
      {
         CRect FitRectDC,ImgRect;
         CSize ImgSize;         

         if (lpfn(hIGear))
         {
            lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);

            if (RotImg(m_pDC,ImgSize.cx,ImgSize.cy) && Auto)
            {
               Err = lpfnRotate(hIGear,IG_ROTATE_90);
               if (Err)
               {
                  TermImg(hIGear);
                  m_pDC->EndPage();
                  goto End;
               }
               lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);
            }

            InitY = SetText(m_pDC,Txt,NameFont,Enh,Tam);

            ImgRect = CRect(CPoint(0,0),ImgSize);

            DCRect.left = 0;
            DCRect.top = InitY;
            DCRect.right = GetDeviceCaps(m_hDC,HORZRES);
            DCRect.bottom = GetDeviceCaps(m_hDC,VERTRES);

            if(!CanNormalSize(m_hDC,hIGear,ImgRect))
               NormalSize = FALSE; //Si no es posible se ajusta al papel
            
            if (!NormalSize)
            {
               GetFitRect(ImgSize,DCRect,&FitRectDC);
            }
            else
            {
               LONG XResN,XResD,YResN,YResD,Units;
               LONG WImg,HImg;

               int RDCX = ::GetDeviceCaps(m_hDC,LOGPIXELSX);               
               int RDCY = ::GetDeviceCaps(m_hDC,LOGPIXELSY);

               lpfnResolution(hIGear,&XResN,&XResD,&YResN,&YResD,(LPAT_MODE)&Units);
               
               WImg = (LONG)((double)ImgSize.cx * ((double)RDCX / XResN));
               HImg = (LONG)((double)ImgSize.cy * ((double)RDCY / YResN));      

               FitRectDC.left = 0;
               FitRectDC.top = InitY;
               FitRectDC.right = FitRectDC.left + WImg;
               FitRectDC.bottom = FitRectDC.top + HImg;
            }


            Err = PrintPage(hIGear,m_pDC,FitRectDC,bpp);
            if (Err)
            {
               TermImg(hIGear);
               m_pDC->EndPage();
               goto End;
            }
            
         }

      }

      m_pDC->EndPage();
      TermImg(hIGear);
   }

   End:

   return(Err);
}


LONG CPrintJob::TermPrint()
{
   LONG Err = 0;

   if (m_hDC == NULL)
   {
      Err = IDOCIMGX_ERR_NOT_INITPRINT;
      goto End;
   }

   m_pDC->EndDoc();
   ::DeleteDC(m_hDC);
   m_hDC = NULL;
   m_pDC = NULL;

   End:

   return(Err);
}

LONG CPrintJob::URLDownLoad(CString& FileName)
{
   LONG     Err = 0;
   CString  CacheFileName;
   CString  TempDir;
   char     buff[_MAX_PATH];
    

   buff[0] = '\0';
   GetTempPath(_MAX_PATH,buff);
   TempDir = buff;

   char* val = CacheFileName.GetBuffer(_MAX_PATH);
   GetTempFileName(TempDir,"img",0,val);
   CacheFileName.ReleaseBuffer();
   

   Err = GetFile(FileName,CacheFileName);
   if (Err) goto End;

   FileName = CacheFileName;

   End:
   
   return(Err);
}


LONG CPrintJob::GetFile(CString& Url,CString& Dest)
{

   TCHAR            sz[1024];
   CInternetSession session(_T("IECISA agen"),1,INTERNET_OPEN_TYPE_PRECONFIG);
   CStdioFile*      pFile = NULL;
   char             szHead[] = "Accept: */*\r\n\r\n";
   DWORD            nRead;
   CFile            mFile;
   CFileException   fileExc;
   LONG             Err = 0;

   if ( !mFile.Open( Dest, CFile::modeCreate | CFile::modeReadWrite, &fileExc) )
   {
      Err = fileExc.m_lOsError;
      goto End;      
   }

   try
   { // 12045 - ERROR_INTERNET_INVALID_CA
      pFile = session.OpenURL(Url,1,INTERNET_FLAG_RELOAD | INTERNET_FLAG_TRANSFER_BINARY,
                              szHead, -1L);
   }
   catch(CInternetException* pEx)
   {
      CString msg;      
      
      if (pEx->m_dwError == ERROR_INTERNET_INVALID_CA)
         msg.LoadString(IDS_ERR_CA);
      else
      {         
         msg.Format("Error: %d",pEx->m_dwError);
      }  
      mFile.Close(); 
      DeleteFile(Dest);

      //AfxMessageBox(msg);
      Err = pEx->m_dwError;
      goto End;
   }

   if (pFile != NULL)
   {
      do
      {
         nRead = pFile->Read(sz,1023);
         if (nRead != 0)
            mFile.Write(sz,nRead);
      }while (nRead != 0);

      mFile.Close();
      pFile->Close();
      delete pFile;
   }
   else
      Err = IDOCIMGX_ERR_FILE_NOFOUND;

   m_DelFilesArr.Add(Dest);
   
   End:

   session.Close();

   return Err;

}


BOOL CPrintJob::CanNormalSize(HDC hDC, HIGEAR hIGear, CRect& ImgRect)
{
   LPFNResolution lpfnResolution;
   LONG           XResN,XResD,YResN,YResD,Units;
   LONG           WImg,HImg;
   BOOL           NormalSize = TRUE;
   LONG           Err = 0;  

   LONG DCWmm = GetDeviceCaps(m_hDC,HORZSIZE);
   LONG DCHmm = GetDeviceCaps(m_hDC,VERTSIZE);


   lpfnResolution = (LPFNResolution)GetProcAddress(_Module.m_hModGear,"IG_image_resolution_get");
   if (lpfnResolution == NULL) {Err = -1; goto End;}

   lpfnResolution(hIGear,&XResN,&XResD,&YResN,&YResD,(LPAT_MODE)&Units);

   WImg = (LONG)((double)((double)ImgRect.Width() * 25.4) / XResN);
   HImg = (LONG)((double)((double)ImgRect.Height() * 25.4) / YResN);

   if (WImg > DCWmm || HImg > DCHmm)
     NormalSize = FALSE;


   End:

   return (NormalSize);

}