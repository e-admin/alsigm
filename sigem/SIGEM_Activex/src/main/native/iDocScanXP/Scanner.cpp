// Scanner.cpp : Implementation of CScanner
#include "stdafx.h"
#include "Defs.h"
#include "Errs.h"
#include "Misc.h"
#include "iGral.h"
#include "IDocScanXP.h"
#include "iDocScan.h"
#include "Scanner.h"
#include "iDocGear.h"

#define EI_CN "Scanner"

/////////////////////////////////////////////////////////////////////////////
// CScanner

CScanner::CScanner()
{
   m_pScanMgr  = NULL;
   m_pFrameWnd = NULL;
   m_Init      = FALSE;
}

CScanner::~CScanner()
{
   FinalRelease();
}


void CScanner::FinalRelease()
{

   m_Init = FALSE;   
   if (m_pScanMgr!=NULL)
   {      
      m_pScanMgr->Term();
      delete m_pScanMgr;
      m_pScanMgr=NULL;
   }

   if (m_pFrameWnd != NULL)
   { 
      delete m_pFrameWnd;
      m_pFrameWnd = NULL;
   }
}

STDMETHODIMP CScanner::InterfaceSupportsErrorInfo(REFIID riid)
{
   static const IID* arr[] = 
   {
      &IID_IScanner
   };
   for (int i=0; i < sizeof(arr) / sizeof(arr[0]); i++)
   {
      if (InlineIsEqualGUID(*arr[i],riid))
         return S_OK;
   }
   return S_FALSE;
}




STDMETHODIMP CScanner::Initialize()
{
   LONG    Err  = 0;
   HRESULT hRes = S_OK;

   IDOCSCANXTRY (m_pScanMgr = new CiDocScanMgr;)
   if (m_pScanMgr==NULL) { Err = IDOCSCANX_ERR_MEM; goto End; }

   if (m_pFrameWnd == NULL && m_Init == FALSE)
   {      
	  
      IDOCSCANXTRY (m_pFrameWnd = new CFrameWnd;)
      if (m_pFrameWnd==NULL) { Err = IDOCSCANX_ERR_MEM; goto End; }

      m_pFrameWnd->Create(NULL,"",WS_OVERLAPPEDWINDOW|WS_VISIBLE,CRect(-200,-300,0,0));

   }

   m_Init = TRUE;
   Err = m_pScanMgr->Init((HWND)m_pFrameWnd->m_hWnd);
   
   if (Err) goto End;
   

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"Initialize",Err); }

	return(hRes);
}


STDMETHODIMP CScanner::InitializeX(LONG hWnd)
{
   LONG    Err  = 0;
   HRESULT hRes = S_OK;

   IDOCSCANXTRY (m_pScanMgr = new CiDocScanMgr;)
   if (m_pScanMgr==NULL) { Err = IDOCSCANX_ERR_MEM; goto End; }

   if (hWnd == NULL)
   {
      Err = IDOCSCANX_ERR_PARAM_NO_VALID;
      goto End;
   }
   if (m_Init == FALSE)
      Err = m_pScanMgr->Init((HWND)hWnd);
   m_Init = TRUE;
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"InitializeX",Err); }

	return(hRes);
}

STDMETHODIMP CScanner::Configure()
{

   LONG    Err  = 0;
   HRESULT hRes = S_OK;
	

   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}

   Err = m_pScanMgr->ShowConfiguration();
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"Configure",Err); }

	return(hRes);
}

STDMETHODIMP CScanner::UINewFiles(VARIANT* Files)
{

   LONG         Err  = 0;
   HRESULT      hRes = S_OK;   
   CStringArray OutputFiles;

 
   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}

   Err = m_pScanMgr->ShowScan(OutputFiles);
   if (Err) goto End;
   
   Err = IDOCSCANXCopyVariantArray(OutputFiles,Files);
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"UINewFiles",Err); }

	return(hRes);

}

STDMETHODIMP CScanner::UIUpdateFile(BSTR File, VARIANT_BOOL* Updated)
{

   LONG         Err  = 0;
   HRESULT      hRes = S_OK;
   CString      Arg(File);
   CString      Old(File);
   
   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}
   // OJO que el File que se le pasa debe ser temporal
   
   Err = m_pScanMgr->ShowScan(Arg);
   if (Err) goto End;
  
   if (Arg.CompareNoCase(Old)==0)
      *Updated = (VARIANT_BOOL)0;
   else
   {
      if (!::CopyFile(Arg,Old,FALSE) ) { Err = IDOCSCAN_ERR_COPYTEMPFILE; goto End; }

      Err = m_pScanMgr->Flush();
      if (Err) goto End;

      *Updated = (VARIANT_BOOL)-1;;
   }


   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"UIUpdateFile",Err); }

	return(hRes);

}

STDMETHODIMP CScanner::NewFiles(LONG PagesToScan,VARIANT* Files)
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;   
   CStringArray OutputFiles;
   

   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}
    
   Err = m_pScanMgr->Acquire(PagesToScan,OutputFiles);
   
   if (Err) goto End;
      
   Err = IDOCSCANXCopyVariantArray(OutputFiles,Files);
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"NewFiles",Err); }

	return(hRes);

}


STDMETHODIMP CScanner::ConfigureScan()
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;
   

    //Se ha puesto Null en ventana para que sea modal a la web  
   Err = IDOCSCANShowConfigDlg(NULL,NULL);
   if (Err) goto End;

   
   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"ConfigureScan",Err); }

	return(hRes);
}

STDMETHODIMP CScanner::NewFilesToPath(BSTR Path, LONG PagesToScan,VARIANT* Files)
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;   
   CStringArray OutputFiles;
   CStringArray PathFiles;
   CString      PathAux(Path);

   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}
    
   Err = m_pScanMgr->Acquire(PagesToScan,OutputFiles);   
   if (Err) goto End;

   Err = CopyFileToPath(OutputFiles,PathFiles,PathAux);
   if (Err) goto End;
      
   Err = IDOCSCANXCopyVariantArray(PathFiles,Files);
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"NewFilesToPath",Err); }

	return(hRes);

}


STDMETHODIMP CScanner::UINewFilesToPath(BSTR Path, VARIANT* Files)
{

   LONG         Err  = 0;
   HRESULT      hRes = S_OK;   
   CStringArray OutputFiles;
   CStringArray PathFiles;
   CString      PathAux(Path);

 
   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}
   
   Err = m_pScanMgr->ShowScan(OutputFiles);
   if (Err) goto End;

   Err = CopyFileToPath(OutputFiles,PathFiles,PathAux);
   if (Err) goto End;
   
   Err = IDOCSCANXCopyVariantArray(PathFiles,Files);
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"UINewFilesToPath",Err); }

	return(hRes);

}

STDMETHODIMP CScanner::SelectDevice(BSTR Name)
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;
   CString      Dev(Name);   
   

   if (m_pScanMgr == NULL)
   {
      IDOCSCANXTRY (m_pScanMgr = new CiDocScanMgr;)
      if (m_pScanMgr==NULL) { Err = IDOCSCANX_ERR_MEM; goto End; }
   }

   Err = m_pScanMgr->SelectDevice(Dev);

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"SelectDevice",Err); }

	return(hRes);
}

STDMETHODIMP CScanner::SelectConfiguration(BSTR Name)
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;
   CString      Cfg(Name);   
   

   if (m_pScanMgr == NULL)
   {
      IDOCSCANXTRY (m_pScanMgr = new CiDocScanMgr;)
      if (m_pScanMgr==NULL) { Err = IDOCSCANX_ERR_MEM; goto End; }
   }

   Err = m_pScanMgr->SelectConfiguration(Cfg);

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"SelectConfiguration",Err); }

	return(hRes);
}

STDMETHODIMP CScanner::Terminate()
{
   LONG    Err = 0;
   HRESULT hRes = S_OK;


   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}

   Err = m_pScanMgr->Term();
   delete m_pScanMgr;
   m_pScanMgr=NULL;
   

   if (m_pFrameWnd != NULL)
   { 
      delete m_pFrameWnd;
      m_pFrameWnd = NULL;
   }

   m_Init = FALSE;


   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"Terminate",Err); }

   return(hRes);
}

STDMETHODIMP CScanner::UIConfig(VARIANT_BOOL StdDlg, VARIANT_BOOL InitCapture)
{
   LONG Err = 0;
   HRESULT hRes = S_OK;

  
   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}
   
   Err = m_pScanMgr->ConfigShowScan(StdDlg,InitCapture);


   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"UIConfig",Err); }

   return(hRes);
}

STDMETHODIMP CScanner::GetNumPages(BSTR File, LONG* NumPages)
{
   LONG    Err = 0;
   HRESULT hRes = S_OK;
   LONG    Pages;
   CString FileName(File);

   char* val = FileName.GetBuffer(FileName.GetLength());
   Err = IG_page_count_get((LPSTR)val,(unsigned int*)&Pages);   
   FileName.ReleaseBuffer();
   if (Err) goto End;

   *NumPages = Pages;

   End:

   if (Err) {hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"GetNumPages",Err); }

   return(hRes);
}

STDMETHODIMP CScanner::UINewFilesX(LONG NumPages, VARIANT* Files)
{
   LONG         Err  = 0;
   HRESULT      hRes = S_OK;   
   CStringArray OutputFiles;

 
   if (m_pScanMgr == NULL) {Err = IDOCSCANX_ERR_NOT_INITIALIZED; goto End;}

   Err = m_pScanMgr->ShowScan(NumPages, OutputFiles);
   if (Err) goto End;
   
   Err = IDOCSCANXCopyVariantArray(OutputFiles,Files);
   if (Err) goto End;

   End:

   if (Err) { hRes = E_FAIL; ::IDOCSCANXSetErrorInfo(EI_CN,"UINewFilesX",Err); }

	return(hRes);

}

///////////////////////////////////////////////////////////////////////////



LONG CopyFileToPath(CStringArray& OrgFiles, CStringArray& DstFiles, CString Path)
{
   LONG Err = 0; 
   LONG index;
   char TempP[MAX_PATH];
   
   DstFiles.RemoveAll();

   if (Path.IsEmpty())
   {
      GetTempPath(MAX_PATH,TempP);
      Path = TempP;
   }

   for (int i=0; i<OrgFiles.GetSize(); i++)
   {
      CString File,Aux1;      
      CString Aux = OrgFiles.GetAt(i);

      index = Aux.ReverseFind ('\\');
      if (index != -1)
      {
         Aux1 = Aux.Right(Aux.GetLength() - (index +1));
         File.Format("%s%s",Path,Aux1);
         if (CopyFile(Aux,File,FALSE) == 0)
         {
            Err = GetLastError();
            goto End;
         }
         DstFiles.Add(File);
      }

   }

   End:

   return (Err);
}

