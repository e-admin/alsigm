// MF.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "IDocImgX.h"
#include "MF.h"
#include "Control.h"
#include "iDocGear.h"
#include "trk.h"
#include "trksel.h"
#include "vw.h"
#include "ifile.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


#define IFE_NONE      0L
#define IFE_BOLD      1L
#define IFE_ITALIC    2L

/////////////////////////////////////////////////////////////////////////////
// CMF

IMPLEMENT_DYNCREATE(CMF, CFrameWnd)

CMF::CMF()
{
   m_pVw      = NULL;
   m_pControl = NULL;
   Init();
}

CMF::~CMF()
{
   for (int i=0; i< m_TempFiles.GetSize(); i++)
   {
      CString FileName = m_TempFiles.GetAt(i);
      DeleteFile(FileName);
   }
   m_TempFiles.RemoveAll();
}


BEGIN_MESSAGE_MAP(CMF, CFrameWnd)
	//{{AFX_MSG_MAP(CMF)
	ON_WM_CREATE()
	ON_WM_ERASEBKGND()
   ON_WM_PALETTECHANGED()
	ON_WM_QUERYNEWPALETTE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMF message handlers

void CMF::Init()
{   
   m_Page = 1; 
   m_FileName.Empty();
   m_FileNote.Empty();
   m_FitMode = FITNONE;
   m_DrawMode = PRESERVE_WHITE;
   m_ZoomImg = 1;
   m_EnablePrint = TRUE;
   m_EnableSaveAs = FALSE;
   m_EnableEditAnn = TRUE;
   m_ShowToolbar = FALSE;
   m_Rotation = 0;
   m_InitFit = FALSE;
   m_InitDrawMode = FALSE;
   m_InitRot = FALSE;
   m_InitTB = FALSE;
   m_Drag   = FALSE;
}

int CMF::OnCreate(LPCREATESTRUCT lpCreateStruct) 
{
   CRect rect;

	if (CFrameWnd::OnCreate(lpCreateStruct) == -1)
		return -1;
	
	CRuntimeClass* pVwClass = RUNTIME_CLASS(CVw);
   CCreateContext Context;
   Context.m_pNewViewClass = pVwClass;
   Context.m_pCurrentDoc = NULL;
   Context.m_pNewDocTemplate = NULL;
   Context.m_pLastView = NULL;
   Context.m_pCurrentFrame = NULL;

  

   m_pVw=(CVw*)CreateView(&Context);
   if (m_pVw == NULL)
      return -1;

   m_pVw->OnInitialUpdate();  
   
   LoadProcessing();

   if (m_hMod == NULL)
   {
      if (!m_wndToolBar.Create(this) ||
		   !m_wndToolBar.LoadToolBar(IDR_MAINFRAME))
	   {
		   TRACE0("Failed to create toolbar\n");
		   return -1;      // fail to create
	   } 

      //nuevo
      m_wndToolBar.SetButtonInfo(17,IDW_STATIC,TBBS_SEPARATOR,70);
      m_wndToolBar.GetItemRect(17,&rect);
      rect.top = rect.top + 3;
   
      if (!m_wndToolBar.m_StaticBox.Create("",WS_CHILD|WS_VISIBLE|SS_CENTER,rect,&m_wndToolBar,IDW_STATIC))
         return -1;
      ///////////////
   }
   else
   {
      if (!m_wndToolBar.Create(this) ||
         !m_wndToolBar.LoadToolBar(IDR_TRMAINFRAME))
      {
         TRACE0("Failed to create toolbar\n");
         return -1;
      }
      m_wndToolBar.SetButtonInfo(18,IDW_STATIC,TBBS_SEPARATOR,70);
      m_wndToolBar.GetItemRect(18,&rect);
      rect.top = rect.top + 3;
   
      if (!m_wndToolBar.m_StaticBox.Create("",WS_CHILD|WS_VISIBLE|SS_CENTER,rect,&m_wndToolBar,IDW_STATIC))
         return -1;
   }

   /////////// PALETA DE ANNOTACIONES //////////////
   if (!m_wndAnnBar.Create(this,WS_CHILD | CBRS_BOTTOM) ||
		!m_wndAnnBar.LoadToolBar(IDR_ANNOTATION))
	{
		TRACE0("Failed to create toolbar\n");
		return -1;      // fail to create
	}
		
	m_wndAnnBar.SetBarStyle(m_wndAnnBar.GetBarStyle()|CBRS_TOOLTIPS);


   ///////////////////////////////////////////////
   

   ShowControlBar(&m_wndToolBar,FALSE,FALSE);   
   SetActiveView(m_pVw,TRUE);
   
	
	return S_OK;
}

void CMF::LoadProcessing()
{
   TCHAR   PathName[_MAX_PATH];
   HMODULE hInst;
   CString Name;
   int     index;
   CString CurrLibName;
   CString FindLibName;

   #ifdef _DEBUG
      CurrLibName = "iDocImgXd.dll";
   #else
      CurrLibName = "iDocImgX.dll";
   #endif

   #ifdef _DEBUG
      FindLibName = "TratsIGd.dll";
   #else
      FindLibName = "TratsIG.dll";
   #endif

   hInst = ::GetModuleHandle(CurrLibName);
   ::GetModuleFileName(hInst,PathName,_MAX_PATH);
   Name = PathName;   
   
   index = Name.ReverseFind('\\');
   if (index != -1)
   {
      Name = Name.Left(index+1);
      Name += FindLibName;
      m_hMod = LoadLibrary(Name);
   }

}

///////////////// Funciones //////////////////////////
void CMF::SetFileName(CString& FileName)
{  
   m_FileName = FileName;
}

void CMF::GetFileName(CString &FileName)
{
   FileName = m_FileName;
}

void CMF::SetFileNote(CString& FileNote)
{
   m_FileNote = FileNote;
}

void CMF::GetFileNote(CString& FileNote)
{
   FileNote = m_FileNote;
}

long CMF::Display()
{
   long Err = 0;

   if (m_pVw != NULL)
      Err = m_pVw->Display();

   return(Err);
}

long CMF::GetNumPages()
{
   long pages = 0;
   
   if (m_pVw != NULL)
      pages = m_pVw->GetNumPages();

   return(pages);
}

long CMF::SetPage(long page)
{
   long Err = 0;

   if (m_pVw != NULL && page <= m_pVw->GetNumPages())
      m_Page = page;
   else
      Err = -1;

   return(Err);
}

long CMF::GetPage()
{
   return(m_Page);
}

void CMF::Clear()
{
   if (m_pVw != NULL)
      m_pVw->Clear();
}

void CMF::SetFitMode(short FitMode)
{
   switch(FitMode)
   {
      case FITWINDOW:
      case FITWIDTH:
      case FITHEIGHT:
	   case FITNONE:
      {
         m_FitMode = FitMode;
         if (m_pVw != NULL)
            m_pVw->SetFitMode(m_FitMode);
      }   
   }
}

short CMF::GetFitMode()
{
   if (m_pVw != NULL)
      m_FitMode = m_pVw->GetFitMode();
   return(m_FitMode);
}

void CMF::SetZoom(double Zoom)
{
   m_ZoomImg = Zoom;
   m_FitMode = FITNONE;
   if (m_pVw != NULL)
      m_pVw->SetZoomImg(Zoom);
}

double CMF::GetZoom()
{
   if (m_pVw != NULL)
      m_ZoomImg = m_pVw->GetZoom();

   return(m_ZoomImg);
}

void CMF::SetDrawMode(short DrawMode)
{
   switch(DrawMode)
   {
      case PRESERVE_WHITE:
      case PRESERVE_BLACK:
      case SCALE_TO_GRAY:	   
      {
         m_DrawMode = DrawMode;
         if (m_pVw != NULL)
            m_pVw->SetDrawMode(m_DrawMode);         
      }   
   }  
}

short CMF::GetDrawMode()
{
   if (m_pVw != NULL)
      m_DrawMode = m_pVw->GetDrawMode();

   return(m_DrawMode);
}

BOOL CMF::OnEraseBkgnd(CDC* pDC) 
{
	return FALSE;
	
	//return CFrameWnd::OnEraseBkgnd(pDC);
}

void CMF::SetEnablePrint(BOOL enable)
{
   m_EnablePrint = enable;  
   if(m_pVw != NULL)
      m_pVw->SetEnablePrint(enable);
}

void CMF::SetEnableSaveAs(BOOL enable)
{
   m_EnableSaveAs = enable;
   if (m_pVw != NULL)
      m_pVw->SetEnableSaveAs(enable);
}

void CMF::SetEnableEditAnn(BOOL enable)
{
   m_EnableEditAnn = enable;
   if (m_pVw != NULL)
      m_pVw->SetEnableEditAnn(enable);
}

void CMF::ShowToolBar(BOOL show)
{
   if (m_pVw != NULL)
   {      
      m_pVw->ShowPal(show);     
      SetActiveView(m_pVw,TRUE);
   }
}

BOOL CMF::IsToolBarVisible()
{
   BOOL Visible = FALSE;

   Visible = m_wndToolBar.IsWindowVisible();

   return(Visible);
}

BOOL CMF::IsEnablePrint()
{
   BOOL Enable = TRUE;

   if (m_pVw != NULL)
      Enable = m_pVw->IsEnablePrint();

   return(Enable);
}

BOOL CMF::IsEnableSaveAs()
{
   BOOL Enable = FALSE;

   if (m_pVw != NULL)
      Enable = m_pVw->IsEnableSaveAs();

   return(Enable);
}

BOOL CMF::IsEnableEditAnn()
{
   BOOL Enable = FALSE;

   if (m_pVw != NULL)
      Enable = m_pVw->IsEnableEditAnn();

   return(Enable);
}

LONG CMF::SetRotation(short Rot)
{
   LONG Err = 0;

   if (m_pVw != NULL)
       Err = m_pVw->SetRot(Rot);

   return (Err);
}

short CMF::GetRotation()
{
   short rot = 0;

   if (m_pVw != NULL)
      rot = m_pVw->GetRot();

   return(rot);
}

void CMF::InitRotation(short Rot)
{
   switch(Rot)
   {
      case 0:  { m_Rotation = 0; break; }
      case 90:  { m_Rotation = 1; break; }
      case 180: { m_Rotation = 2; break; }
      case 270: { m_Rotation = 3; break; }
   }
   m_InitRot = TRUE;
}
BOOL CMF::SaveNotes()
{
   BOOL ret = TRUE;
   
   if (m_pVw != NULL)
      ret = m_pVw->SaveNotes();

   return (ret);
}

BOOL CMF::IsNotesModified()
{
   BOOL Modified = FALSE;

   if (m_pVw != NULL)
      Modified = m_pVw->GetChangeAnn();

   return(Modified);
}

BOOL CMF::HasAnyNotesModified()
{
   BOOL Modified = FALSE;

   if (m_pVw != NULL)
      Modified = m_pVw->GetAnyChangeAnn();

   return(Modified);
}

LONG CMF::SaveNotesToFile(CString File)
{
   LONG Err = 0;

   if (m_pVw != NULL)
      Err = m_pVw->SaveNotesToFile(File);

   return(Err);
}

void CMF::SetControl(CControl* pControl)
{
   m_pControl = pControl;
}

void CMF::EventModifyNotes()
{

   if (m_pControl != NULL)
      m_pControl->EventModifyNotes();

}

void CMF::EventSaveNotes()
{
   if (m_pControl != NULL)
      m_pControl->EventSaveNotes();
}

void CMF::DeleteSelectedAnn()
{
   if (m_pVw != NULL)
     m_pVw->DeleteAnn();
}

BOOL CMF::ExistsSelection()
{
   BOOL Exists = FALSE;

   if (m_pVw != NULL)
      Exists = m_pVw->ExistsSelection();

   return(Exists);
}

LONG CMF::SaveSelectionToFile(CString File)
{
   LONG Err = 0;

   if (m_pVw != NULL)
      Err = m_pVw->SaveSelectionToFile(File);

   return(Err);
}

LONG CMF::SaveFileWithRotation(CString FileName)
{
   LONG Err = 0;

   if (m_pVw != NULL)
      Err = m_pVw->SaveFileWithRotation(FileName);

   return(Err);
}

LONG CMF::SaveFile(CString& FileName)
{
   LONG Err = 0;

   if (m_pVw != NULL)
      Err = m_pVw->SaveFile(FileName);

   return(Err);
}

LONG CMF::GetImgWidth()
{
   LONG W = 0;

   if (m_pVw != NULL)
      W = m_pVw->GetImgWidth();

   return(W);
}

LONG CMF::GetImgHeight()
{
   LONG H = 0;

   if (m_pVw != NULL)
      H = m_pVw->GetImgHeight();

   return(H);
}

void CMF::SetDrag(BOOL Drag)
{

   m_Drag = Drag;

   if (m_pVw != NULL)
      m_pVw->SetDrag(m_Drag);         
      
}

BOOL CMF::IsDrag()
{

   if (m_pVw != NULL)
      m_Drag = m_pVw->IsDrag();

   return(m_Drag);

}