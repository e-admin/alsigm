#if !defined(AFX_MF_H__24C6D59F_6D0D_11D4_8128_00C0F049167F__INCLUDED_)
#define AFX_MF_H__24C6D59F_6D0D_11D4_8128_00C0F049167F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// MF.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CMF frame

class CVw;
class CControl;

class CStaticBar : public CToolBar
{
   public:
	CStatic   m_StaticBox;	
};


class CMF : public CFrameWnd
{
	DECLARE_DYNCREATE(CMF)

   public:

	CMF();
   
	short  GetDrawMode();
	void   SetDrawMode(short DrawMode);
	double GetZoom();
	void   SetZoom(double Zoom);	   
	short  GetFitMode();
	void   SetFitMode(short FitMode);
	void   Clear();
	long   GetPage();
   void   Init();	   
	long   SetPage(long page);
	long   GetNumPages();
	long   Display();
	void   GetFileName(CString& FileName);	   
	void   SetFileName(CString& FileName);
   void   GetFileNote(CString& FileNote);
   void   SetFileNote(CString& FileNote);
   void   SetEnablePrint(BOOL enable);
   void   SetEnableSaveAs(BOOL enable);
   void   SetEnableEditAnn(BOOL enable);
   void   ShowToolBar(BOOL show);
   BOOL   IsToolBarVisible();
   BOOL   IsEnablePrint();
   BOOL   IsEnableSaveAs();
   BOOL   IsEnableEditAnn();
   LONG   SetRotation(short Rot);
   short  GetRotation();
   void   InitRotation(short Rot); //0,90,180,270
   void   SetDrag(BOOL Drag);
   BOOL   IsDrag();


   void LoadProcessing();
   BOOL SaveNotes();
   BOOL IsNotesModified();
   BOOL HasAnyNotesModified();
   void SetControl(CControl* pControl);
   LONG SaveNotesToFile(CString File);
   void EventModifyNotes();
   void EventSaveNotes();
   void DeleteSelectedAnn();
   BOOL ExistsSelection();
   LONG SaveSelectionToFile(CString File);
   LONG GetImgWidth();
   LONG GetImgHeight();
   LONG SaveFileWithRotation(CString FileName);
   LONG SaveFile(CString& FileName);


   //{{AFX_VIRTUAL(CMF)
	//}}AFX_VIRTUAL

   protected:

	virtual ~CMF();
	
	//{{AFX_MSG(CMF)  
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()

   public:

   CStringArray m_TempFiles;

   protected:

   CVw*      m_pVw; 
   CControl* m_pControl;

   public:

   CStaticBar m_wndToolBar;
   CToolBar   m_wndAnnBar;
   HMODULE    m_hMod;
   
   short      m_FitMode;
   BOOL       m_InitFit;
   LONG       m_Page;
   CString    m_FileName;
   CString    m_FileNote;
   double     m_ZoomImg;
   short      m_DrawMode;
   BOOL       m_InitDrawMode;
   BOOL       m_EnablePrint;
   BOOL       m_EnableSaveAs;
   BOOL       m_EnableEditAnn;
   BOOL       m_ShowToolbar;
   BOOL       m_InitTB;
   short      m_Rotation;
   BOOL       m_InitRot;
   BOOL       m_Drag;
 
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MF_H__24C6D59F_6D0D_11D4_8128_00C0F049167F__INCLUDED_)
