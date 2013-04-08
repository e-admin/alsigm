// Control.cpp : Implementation of CControl

#include "stdafx.h"
#include <afxinet.h>
#include "ErrorAux.h"
#include "iDocGear.h"
#include "IDocImgX.h"
#include "Control.h"
#include "mf.h"
#include "Misc.h"
#include "Errs.h"
#include "AuxFnc.h"

#define EI_CONTROL  "Control"
/////////////////////////////////////////////////////////////////////////////
// CControl

CControl::~CControl()
{
   for (int i= 0; i < m_DelFileArr.GetSize(); i++)
   {
      CString File = m_DelFileArr.GetAt(i);
      DeleteFile(File);
   }
   m_DelFileArr.RemoveAll();
}

BOOL IsValidRotation(short rot)
{
   BOOL valid = FALSE;

   if (rot == 0 || rot == 90 || rot == 180 || rot == 270)
      valid = TRUE;

   return(valid);
}

HRESULT CControl::OnDraw(ATL_DRAWINFO& di)
{
	RECT& rc = *(RECT*)di.prcBounds;
	Rectangle(di.hdcDraw, rc.left, rc.top, rc.right, rc.bottom);

	SetTextAlign(di.hdcDraw, TA_CENTER|TA_BASELINE);
	LPCTSTR pszText = _T("ATL 3.0 : Control");
	TextOut(di.hdcDraw, 
		(rc.left + rc.right) / 2, 
		(rc.top + rc.bottom) / 2, 
		pszText, 
		lstrlen(pszText));

	return S_OK;
}

LRESULT CControl::OnCreate(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
   RECT  rc;
   CWnd* pWnd;  

	::GetClientRect(m_hWnd, &rc);    
   AFX_MANAGE_STATE(AfxGetStaticModuleState())
 

	m_pMF = new CMF;
   pWnd = CWnd::FromHandle(m_hWnd);

   if (!m_pMF->Create(NULL,"Main Frame Ctrl",WS_CHILD,rc,pWnd,NULL,0))
   {
      TRACE("Error al crear el mainframe del control\n");
      return -1;
   }
   
   InitParams();
   
   m_pMF->SetControl(this);
   
   m_pMF->ShowWindow(SW_SHOW);   
   m_pMF->UpdateWindow(); 
   
   
   return S_OK;
}

LRESULT CControl::OnDestroy(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
   AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
	if (::IsWindow(m_pMF->m_hWnd))
   {      
      m_pMF->DestroyWindow();      
   }

   for (int i= 0; i < m_DelFileArr.GetSize(); i++)
   {
      CString File = m_DelFileArr.GetAt(i);
      DeleteFile(File);
   }
   m_DelFileArr.RemoveAll();

	return S_OK;
}

LRESULT CControl::OnSize(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	RECT rc;
   
      
	::GetClientRect(m_hWnd, &rc);  

	AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (::IsWindow(m_pMF->m_hWnd))
   {
      m_pMF->MoveWindow(0,0,rc.right,rc.bottom);           
   }
   
	return S_OK;
}

STDMETHODIMP CControl::TranslateAccelerator(MSG *pMsg)
{   
	if( (pMsg->wParam == VK_DELETE) )
   {
      AFX_MANAGE_STATE(AfxGetStaticModuleState())
      if (::IsWindow(m_pMF->m_hWnd))
      {
         m_pMF->DeleteSelectedAnn();	    
      }
	   return S_OK; 
   }
   
	return S_FALSE;
}

LRESULT CControl::OnMouseActivate(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	if(IsUserMode())
	{
		InPlaceActivate(OLEIVERB_UIACTIVATE);	
	}
	return 0;
}
LRESULT CControl::OnSetFocus(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	CComControl<CControl>::OnSetFocus (uMsg, wParam, lParam, bHandled);
		
	if (IsUserMode())
	{
		InPlaceActivate (OLEIVERB_UIACTIVATE);	
		
	}
	return 0;
}



BOOL CControl::IsUserMode()
{
	BOOL bUserMode = TRUE;
	HRESULT hRet = GetAmbientUserMode(bUserMode);

	if (FAILED(hRet) || bUserMode)
	{
		return TRUE;			
	}
	return FALSE;
}


STDMETHODIMP CControl::get_FileName(BSTR *pVal)
{
   HRESULT hRes = S_OK;
   CString Name;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER;goto End;}
	
	
   if (m_pMF != NULL)
   {
      m_pMF->GetFileName(Name);
      *pVal = Name.AllocSysString();
   }

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_FileName(BSTR newVal)
{
   HRESULT hRes = S_OK;
   CString Aux;
   
	AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
   m_FileName = CString(newVal);
   Aux = m_FileName.Left(4);
   
   if (!Aux.CompareNoCase("http"))
      URLDownLoad(m_FileName);
   
   
	if (m_pMF != NULL)
   {    
      m_pMF->SetFileName(m_FileName);        
   }
  
	return (hRes);
}

STDMETHODIMP CControl::Display()
{
   HRESULT hRes = S_OK;
   LONG Err = 0;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
   {
      Err = m_pMF->Display();
      if (Err) goto End;         
   }

   End:

   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_CONTROL,"Display",Err);
      hRes = E_FAIL;
   }

	return (hRes);
}

STDMETHODIMP CControl::get_Page(long *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
      *pVal = m_pMF->GetPage();

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_Page(long newVal)
{

	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SetPage(newVal);

	return S_OK;
}

STDMETHODIMP CControl::get_PageCount(long *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
   {
      *pVal = m_pMF->GetNumPages();
   }
  
   End:

	return (hRes);
}

STDMETHODIMP CControl::Clear()
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->Clear();

	return S_OK;
}

STDMETHODIMP CControl::get_FitMode(short *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER;goto End;}	

	if (m_pMF != NULL)
      *pVal = m_pMF->GetFitMode();

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_FitMode(short newVal)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SetFitMode(newVal);
   else
      m_InitFit = TRUE;
      m_FitMode = newVal;

	return S_OK;
}

STDMETHODIMP CControl::get_Zoom(double *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
      *pVal = m_pMF->GetZoom();

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_Zoom(double newVal)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SetZoom(newVal);

	return S_OK;
}

STDMETHODIMP CControl::get_Enhancement(short *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
      *pVal = m_pMF->GetDrawMode();
      

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_Enhancement(short newVal)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SetDrawMode(newVal);
   else
   {
      m_InitEnh = TRUE;
      m_Enhancement = newVal;
   }

	return S_OK;
}


STDMETHODIMP CControl::put_EnablePrint(VARIANT_BOOL newVal)
{   
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SetEnablePrint((BOOL)newVal);
   else
      m_EnablePrint = (BOOL)newVal;

	return S_OK;
}

STDMETHODIMP CControl::get_EnablePrint(VARIANT_BOOL *pVal)
{
   BOOL    Enable;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
      Enable = m_pMF->IsEnablePrint();
   else
      Enable = m_EnablePrint;

   if (Enable)
      *pVal = -1;
   else
      *pVal = 0;
      

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_EnableSaveAs(VARIANT_BOOL newVal)
{   
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

   m_EnableSaveAs = (BOOL)newVal;
	if (m_pMF != NULL)
      m_pMF->SetEnableSaveAs((BOOL)newVal);     
   
	return S_OK;
}

STDMETHODIMP CControl::get_EnableSaveAs(VARIANT_BOOL *pVal)
{
   BOOL    Enable;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
      Enable = m_pMF->IsEnableSaveAs();
   else
      Enable = m_EnableSaveAs;

   if (Enable)
      *pVal = -1;
   else
      *pVal = 0;
      

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_EnableEditAnn(VARIANT_BOOL newVal)
{   
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

   m_EnableEditAnn = (BOOL)newVal;
	if (m_pMF != NULL)
      m_pMF->SetEnableEditAnn((BOOL)newVal);     
   
	return S_OK;
}

STDMETHODIMP CControl::get_EnableEditAnn(VARIANT_BOOL *pVal)
{
   BOOL    Enable;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
      Enable = m_pMF->IsEnableEditAnn();
   else
      Enable = m_EnableEditAnn;

   if (Enable)
      *pVal = -1;
   else
      *pVal = 0;
      

   End:

	return (hRes);
}



STDMETHODIMP CControl::put_ShowToolbar(VARIANT_BOOL newVal)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->ShowToolBar((BOOL)newVal);   
   else
   {
      m_InitTB = TRUE;
      m_ShowToolBar = (BOOL)newVal;
   }

	return S_OK;

}

STDMETHODIMP CControl::get_ShowToolbar(VARIANT_BOOL *pVal)
{
   BOOL    Visible;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
      Visible = m_pMF->IsToolBarVisible();
   else
      Visible = m_ShowToolBar;

   if (Visible)
      *pVal = -1;
   else
      *pVal = 0;
      
   End:

	return (hRes);
}

STDMETHODIMP CControl::get_Rotation(short *pVal)
{
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
      *pVal = m_pMF->GetRotation();

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_Rotation(short newVal)
{
   HRESULT hRes = S_OK;
   LONG Err = 0;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (IsValidRotation(newVal))
   {
	   if (m_pMF != NULL)
         Err = m_pMF->SetRotation(newVal);
      else
      {
         m_InitRot = TRUE;
         m_Rotation = newVal;
      }
   }
   if (Err)
   {
      ::IDOCIMGXSetErrorInfo(EI_CONTROL,"Rotation",Err);
      hRes = E_FAIL;
   }

	return (hRes);
}

STDMETHODIMP CControl::get_FileNote(BSTR *pVal)
{
   HRESULT hRes = S_OK;
   CString Name;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
   {
      m_pMF->GetFileNote(Name);
      *pVal = Name.AllocSysString();
   }

   End:

	return (hRes);
}

STDMETHODIMP CControl::put_FileNote(BSTR newVal)
{
   char TempPath[MAX_PATH];
   char TempFile[MAX_PATH];

	AFX_MANAGE_STATE(AfxGetStaticModuleState())
   
   m_FileNote = CString(newVal);
   CString Aux = m_FileNote.Left(4);
   
   if (!Aux.CompareNoCase("http"))
      URLDownLoad(m_FileNote);   
   
   GetTempPath(MAX_PATH,TempPath);
   GetTempFileName(TempPath,"ANN",0,TempFile);
   CopyFile(m_FileNote, TempFile,FALSE);
   m_FileNote = TempFile;
   
   
	if (m_pMF != NULL)
   {      
      m_pMF->SetFileNote(m_FileNote);        
   }

	return S_OK;
}

STDMETHODIMP CControl::SaveNotes()
{
   HRESULT hRes = S_OK;

	AFX_MANAGE_STATE(AfxGetStaticModuleState())

	if (m_pMF != NULL)
      m_pMF->SaveNotes();


	return (hRes);
}

STDMETHODIMP CControl::IsNotesModified(VARIANT_BOOL* Modified)
{
   BOOL    MDF;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (Modified == NULL) {hRes = E_POINTER; goto End;}	

	if (m_pMF != NULL)
      MDF = m_pMF->IsNotesModified();
   
   if (MDF)
      *Modified = -1;
   else
      *Modified = 0;
      

   End:

	return (hRes);
}

STDMETHODIMP CControl::HasAnyNotesModified(VARIANT_BOOL* Modified)
{
   BOOL    MDF;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (Modified == NULL) {hRes = E_POINTER; goto End;}
   
	if (m_pMF != NULL)
      MDF = m_pMF->HasAnyNotesModified();
   
   if (MDF)
      *Modified = -1;
   else
      *Modified = 0;
      

   End:

	return (hRes);
}

STDMETHODIMP CControl::SaveNotesToFile(BSTR FileName)
{
   HRESULT hRes = S_OK;
   CString File(FileName);
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      Err = m_pMF->SaveNotesToFile(File);
      if (Err) {hRes = E_FAIL; goto End;}
   }
      
   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL,"SaveNotesToFile",Err);
    

   return (hRes);
}

STDMETHODIMP CControl::ExistsSelection(VARIANT_BOOL* Exists)
{
   BOOL    ESel = FALSE;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (Exists == NULL) {hRes = E_POINTER; goto End;}
   
	if (m_pMF != NULL)
      ESel = m_pMF->ExistsSelection();
   
   if (ESel)
      *Exists = -1;
   else
      *Exists = 0;
      

   End:

	return (hRes);

}

STDMETHODIMP CControl::SaveSelectionToFile(BSTR FileName)
{
   HRESULT hRes = S_OK;
   CString File(FileName);
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      Err = m_pMF->SaveSelectionToFile(File);
      if (Err) {hRes = E_FAIL; goto End;}
   }
      
   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL,"SaveSelectionToFile",Err);
    

   return (hRes);
}

STDMETHODIMP CControl::SaveFileWithRotation(BSTR FileName)
{
   HRESULT hRes = S_OK;
   CString File(FileName);
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      Err = m_pMF->SaveFileWithRotation(File);
      if (Err) {hRes = E_FAIL; goto End;}
   }
      
   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL,"SaveFileWithRotation",Err);
    

   return (hRes);
}


STDMETHODIMP CControl::SaveFile(BSTR *pVal)
{

   HRESULT hRes = S_OK;
   CString FileName;
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      Err = m_pMF->SaveFile(FileName);
      if (Err) {hRes = E_FAIL; goto End;}
      *pVal = FileName.AllocSysString();
   }
      
   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL,"SaveFile",Err);
    

   return (hRes);
}

STDMETHODIMP CControl::ExtractPagesToFile(BSTR FileName, BSTR Pages, BSTR *pVal)
{

   HRESULT hRes = S_OK;
   CString FileDst;
   CString FileOrg(FileName);
   CString Pgs(Pages);
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   Err = ExtractPages(FileOrg, Pages, FileDst);
   if (Err) {hRes = E_FAIL; goto End;}

   *pVal = FileDst.AllocSysString();

   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL, "ExtractPagesToFile", Err);
   
   return(hRes);

}

STDMETHODIMP CControl::JoinFiles(BSTR FileNameFirst, BSTR FileNameSecond, BSTR *pVal)
{

   HRESULT hRes = S_OK;
   CString FileDst;
   CString FileFirst(FileNameFirst);
   CString FileSecond(FileNameSecond);
   LONG    Err = 0;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   Err = Join(FileFirst,FileSecond, FileDst);
   if (Err) { hRes = E_FAIL; goto End;}
   
   *pVal = FileDst.AllocSysString();

   End:

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL, "JoinFiles", Err);
   
   return (hRes);

}

STDMETHODIMP CControl::DeletePages(BSTR FileName, BSTR Pages)
{

   HRESULT hRes = S_OK;
   LONG    Err = 0;
   CString File(FileName);
   CString pgs(Pages);

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   Err = DeletePagesFromFile(File,pgs);
   if (Err) hRes = E_FAIL;

   if (Err) ::IDOCIMGXSetErrorInfo(EI_CONTROL, "DeleteFiles", Err);

   return (hRes);

}

STDMETHODIMP CControl::get_ImgWidth(long *pVal)
{
   HRESULT hRes = S_OK;
   LONG    W = 0;   

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      W = m_pMF->GetImgWidth();      
   }
      
   *pVal = W;

   return (hRes);
}

STDMETHODIMP CControl::get_ImgHeight(long *pVal)
{
   
   HRESULT hRes = S_OK;
   LONG    H = 0;   

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (m_pMF != NULL)
   {
      H = m_pMF->GetImgHeight(); 
   }
      
   *pVal = H;

   return (hRes);
}

void CControl::InitParams()
{
   if (!m_FileName.IsEmpty())
   {
      CString Aux = m_FileName.Left(4);

      if (!Aux.CompareNoCase("http"))
         URLDownLoad(m_FileName);
      m_pMF->SetFileName(m_FileName);
      m_pMF->m_InitFit = m_InitFit;
      m_pMF->SetFitMode(m_FitMode);
      m_pMF->m_InitDrawMode = m_InitEnh;
      m_pMF->SetDrawMode(m_Enhancement);
      m_pMF->SetEnablePrint(m_EnablePrint); 
      m_pMF->SetEnableSaveAs(m_EnableSaveAs);
      m_pMF->SetEnableEditAnn(m_EnableEditAnn);
      m_pMF->m_InitTB = m_InitTB;
      m_pMF->m_ShowToolbar = m_ShowToolBar;
      m_pMF->ShowToolBar(m_ShowToolBar);
      m_pMF->m_InitRot = m_InitRot;
      m_pMF->InitRotation(m_Rotation);
      m_pMF->SetDrag(m_Drag);
      Aux = m_FileNote.Left(4);
      if (!Aux.CompareNoCase ("http"))
         URLDownLoad(m_FileNote);
      m_pMF->SetFileNote(m_FileNote);      
      
      m_pMF->Display();   
 
   }

}
/*
BOOL CControl::URLDownLoad(CString& FileName)
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
      CString Msg;
      Msg.LoadString(IDS_ERR_DOWNLOAD_FILE);
      AfxMessageBox(Msg);     
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

BOOL CControl::URLDownLoad(CString& FileName)
{
   BOOL     ret = FALSE;
   CString  CacheFileName;
   CString  TempDir;
   char     buff[_MAX_PATH];
    

   buff[0] = '\0';
   GetTempPath(_MAX_PATH,buff);
   TempDir = buff;

   char* val = CacheFileName.GetBuffer(_MAX_PATH);
   GetTempFileName(TempDir,"img",0,val);
   CacheFileName.ReleaseBuffer();
   

   ret = GetFile(FileName,CacheFileName);
   if (!ret) goto End;

   FileName = CacheFileName;

   End:
   
   return(ret);
}


void CControl::EventModifyNotes()
{
   Fire_IsModifyNotes();
}

void CControl::EventSaveNotes()
{
   Fire_IsSaveNotes();
}

BOOL CControl::GetFile(CString& szUrl,CString& Dest)
{

   TCHAR            sz[1024];
  // CInternetSession session(_T("IECISA agent"),1,INTERNET_OPEN_TYPE_DIRECT);
   CInternetSession session(_T("IECISA agen"),1,INTERNET_OPEN_TYPE_PRECONFIG);
   CStdioFile*      pFile = NULL;
   char             szHead[] = "Accept: */*\r\n\r\n";
   DWORD            nRead;
   CFile            mFile;
   CFileException   fileExc;
   BOOL             ret = TRUE;


   if ( !mFile.Open( Dest, CFile::modeCreate | CFile::modeReadWrite, &fileExc) )
   {
      AfxMessageBox(fileExc.m_cause);
      ret = FALSE;
      goto End;      
   }

   try
   { // 12045 - ERROR_INTERNET_INVALID_CA
      pFile = session.OpenURL(szUrl,1,INTERNET_FLAG_RELOAD | INTERNET_FLAG_TRANSFER_BINARY,
                              szHead, -1L);
   }
   catch(CInternetException* pEx)
   {
      CString msg;      
      
      if (pEx->m_dwError == ERROR_INTERNET_INVALID_CA)
         msg.LoadString(IDS_ERR_CA);
      else
      {         
         msg.Format("Error en OpenURL: %d",pEx->m_dwError);
      }  
      mFile.Close(); 
      DeleteFile(Dest);

      AfxMessageBox(msg);
      ret = FALSE;
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
      ret = FALSE;

   m_DelFileArr.Add(Dest);
   
   End:

   session.Close();

   return ret;

}

LONG CControl::ExtractPages(CString FileOrg, CString Pages, CString& FileDst)
{

   LONG        Err = 0;   
   int         Ret = 0;
   CDWordArray PGS;
   HIGEAR      hIGear = 0;   
   int         fd = -1;
   int         i;
   ULONG       TypeSave; 
   CString     TempFile;
   CString     Ext, AuxFile;
   BOOL        DeleteTemp = FALSE;

   //Formato de Pages rango de páginas 1-23, páginas sueltas 1,2,
   //   1-23,33,56
   Err = GetPages(Pages, PGS);

   Ext = FileOrg.Right(3);

   Err = GetTempFile(TempFile);
   if (Err) goto End;   

   AuxFile = TempFile.Left(TempFile.GetLength() - 3);
   AuxFile = AuxFile + Ext;

   if (Ext.CompareNoCase("Tmp"))
   {
      if (!CopyFile(TempFile, AuxFile, FALSE))
      {
         Err = GetLastError();
         goto End;
      } 
      DeleteTemp = TRUE;
   }
   
   for(i=0; i< PGS.GetSize(); i++)
   {
      DWORD Page = PGS.GetAt(i);

      fd = _lopen(AuxFile,OF_READWRITE);
      if (fd == -1) {Err = fd; goto End;}

      Err = InitPageImgX(FileOrg, Page, &hIGear, &TypeSave);
      if (Err) goto End;

      Err = AddPageToFile(hIGear,fd, TypeSave);
      if (Err) goto End;

      _lclose(fd);
      fd = -1;
      TermImg(hIGear);      
      hIGear = 0;

   }

   FileDst = AuxFile;

   m_DelFileArr.Add(FileDst);

   End:

   if (DeleteTemp)
      DeleteFile(TempFile);

   if (Err) DeleteFile(AuxFile);

   if (fd != -1)
      _lclose(fd);
   if (hIGear != 0)
      TermImg(hIGear);

   return (Err);

}

LONG CControl::Join(CString FileFirst, CString FileSecond, CString& FileDst)
{

   LONG    Err = 0;
   int     Ret = 0;
   CString TempFile;
   CString AuxFile, Ext;
   BOOL    DeleteTemp = FALSE;

   Err = GetTempFile(TempFile);

   Ext = FileFirst.Right(3);

   
   AuxFile = TempFile.Left(TempFile.GetLength() - 3);
   AuxFile = AuxFile + Ext;

   if (Ext.CompareNoCase("Tmp"))
   {
      if (!CopyFile(TempFile, AuxFile, FALSE))
      {
         Err = GetLastError();
         goto End;
      }
      DeleteTemp = TRUE;
   }
   
   Err = AddPagesFromFile(FileFirst, AuxFile);
   if (Err) goto End;

   Err = AddPagesFromFile(FileSecond, AuxFile);
   if (Err) goto End;

   FileDst = AuxFile;

   m_DelFileArr.Add(FileDst);
   
   End:

   if (DeleteTemp)
      DeleteFile(TempFile);

   if (Err) DeleteFile(AuxFile);
     

   return (Err);

}

LONG CControl::DeletePagesFromFile(CString File,CString pages)
{

   LONG        Err = 0;
   CDWordArray PGS;
   HIGEAR      hIGear = 0;   
   int         fd = -1;
   int         i;
   LONG        NumPages;
   ULONG       TypeSave; 
   CString     TempFile;

   //Formato de Pages rango de páginas 1-23, páginas sueltas 1,2,
   //   1-23,33,56
   Err = GetPages(pages, PGS);

   Err = GetTempFile(TempFile);
   if (Err) goto End;  
   
   Err = GetNumPagesFile(File, NumPages);
   if (Err) goto End;

   for (i=1; i <= NumPages; i++)
   {
      if (!HasDelPage(i, PGS))
      {
         fd = _lopen(TempFile,OF_READWRITE);
         if (fd == -1) {Err = fd; goto End;}

         Err = InitPageImgX(File, i, &hIGear, &TypeSave);
         if (Err) goto End;

         Err = AddPageToFile(hIGear,fd, TypeSave);
         if (Err) goto End;

         _lclose(fd);
         fd = -1;
         TermImg(hIGear);      
         hIGear = 0;
      }
   }

   if ( !CopyFile(TempFile,File,FALSE) )
      Err = GetLastError();
   
   End:

   if (fd != -1)
      _lclose(fd);
   if (hIGear != 0)
      TermImg(hIGear);

   DeleteFile(TempFile);

   return (Err);

}

LONG CControl::AddPagesFromFile(CString File, CString FileDst)
{

   LONG        Err = 0;   
   int         Ret = 0;   
   HIGEAR      hIGear = 0;   
   int         fd = -1;
   int         i;
   ULONG       TypeSave; 
   LONG        NumPages;
   
   
   Err = GetNumPagesFile(File,NumPages);
   if (Err) goto End;

   for(i=1; i<= NumPages; i++)
   {
      
      fd = _lopen(FileDst,OF_READWRITE);
      if (fd == -1) {Err = fd; goto End;}

      Err = InitPageImgX(File, i, &hIGear, &TypeSave);
      if (Err) goto End;

      Err = AddPageToFile(hIGear,fd, TypeSave);
      if (Err) goto End;

      _lclose(fd);
      fd = -1;
      TermImg(hIGear);      
      hIGear = 0;

   }   

   End:
   

   if (fd != -1)
      _lclose(fd);
   if (hIGear != 0)
      TermImg(hIGear);

   return (Err);

}

LONG CControl::GetPages(CString Pages, CDWordArray& PGS)
{

   LONG    Err = 0;
   CString Aux(Pages);
   CString aux1,aux2;
   int     idx, idx2;
   DWORD   N1, N2;

   idx = Aux.Find(",");

   while (idx != -1)
   {
      
      aux1 = Aux.Left(idx);

      idx2 = aux1.Find("-");
      if (idx2 != -1)
      {
         aux2 = aux1.Left(idx2);
         aux1 = aux1.Right(aux1.GetLength() - (idx2 + 1));
         N1 = atol(aux2);
         N2 = atol(aux1);

         if (N1 > N2)
         {
            Err = -1;
            goto End;
         }
         else
         {
            for (int i = N1; i <= (int)N2; i++)
            {
               PGS.Add(i);
            }
         }

      }
      else
      {
         N1 = atol(aux1);
         PGS.Add(N1);
      }


      Aux = Aux.Right(Aux.GetLength()- (idx + 1));
      idx = Aux.Find(",");

   }

   if (!Aux.IsEmpty())
   {
      idx2 = Aux.Find("-");
      if (idx2 != -1)
      {
         aux2 = Aux.Left(idx2);
         aux1 = Aux.Right(Aux.GetLength() - (idx2 + 1));
         N1 = atol(aux2);
         N2 = atol(aux1);

         if (N1 > N2)
         {
            Err = -1;
            goto End;
         }
         else
         {
            for (int i = N1; i <= (int)N2; i++)
            {
               PGS.Add(i);
            }
         }

      }
      else
      {
         N1 = atol(Aux);
         PGS.Add(N1);
      }
   }

   End:

   return Err;

}

BOOL CControl::HasDelPage(int page, CDWordArray& Pages)
{

   BOOL DelPage = FALSE;

   for (int i=0; i< Pages.GetSize(); i++)
   {
      int pg = Pages.GetAt(i);

      if (pg == page)
      {
         DelPage = TRUE;
         goto End;
      }
   }

   End:

   return (DelPage);   

}    

STDMETHODIMP CControl::put_Drag(VARIANT_BOOL newVal)
{   
	AFX_MANAGE_STATE(AfxGetStaticModuleState())

   m_Drag = (BOOL)newVal;
	if (m_pMF != NULL)
      m_pMF->SetDrag((BOOL)newVal);     
   
	return S_OK;
}

STDMETHODIMP CControl::get_Drag(VARIANT_BOOL *pVal)
{
   BOOL    Drag;
   HRESULT hRes = S_OK;

   AFX_MANAGE_STATE(AfxGetStaticModuleState())

   if (pVal == NULL) {hRes = E_POINTER; goto End;}
	
	if (m_pMF != NULL)
      Drag = m_pMF->IsDrag();
   else
      Drag = m_Drag;

   if (Drag)
      *pVal = -1;
   else
      *pVal = 0;
      

   End:

	return (hRes);
}