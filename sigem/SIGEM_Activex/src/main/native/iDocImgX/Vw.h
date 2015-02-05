#if !defined(AFX_VW_H__24C6D5A0_6D0D_11D4_8128_00C0F049167F__INCLUDED_)
#define AFX_VW_H__24C6D5A0_6D0D_11D4_8128_00C0F049167F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// Vw.h : header file

/////////////////////////////////////////////////////////////////////////////
// CVw view


typedef enum
{		
   FITWINDOW = 0,
   FITWIDTH  = 1,
   FITHEIGHT = 2,        
	FITNONE   = 3
} enumFitMode;

typedef enum
{		
   PRESERVE_WHITE = 0,
   PRESERVE_BLACK  = 1,
   SCALE_TO_GRAY = 2	
} enumDrawMode;

typedef struct
{
   HIGEAR    hIGear;
   HWND      hwnd;
   LPUINT    lpZoomLevel;
   LPAT_RECT lprcZoom;
   HINSTANCE hModGear;
}
MYSELECTDATA, FAR* LPMYSELECTDATA;

#define P_NONE  0
#define P_ZINC  1
#define P_ZDEC  2
#define P_NSIZ  3
#define P_BFIT  4
#define P_HFIT  5
#define P_VFIT  6
#define P_DRAG  7
#define P_ZRECT 8
#define P_SEL   9


class CPagirDlg;
class CConfgDlg;
class ICDocAnnDoc;
class CEndosoDlg;

class CVw : public CView
{
   protected:
	CVw();
	DECLARE_DYNCREATE(CVw)

   public:

	void Clear();
	long GetNumPages();
	LONG Display();
   void SelPage(LONG page);
   HIGEAR GetImage();
   void PaletteChanged(CWnd* pFocusWnd);
   BOOL QueryNewPalette();
   void ShowPal(BOOL Show); 
   BOOL IsEnablePrint();
   void SetEnablePrint(BOOL Enable);
   BOOL IsEnableSaveAs();
   void SetEnableSaveAs(BOOL Enable);
   BOOL IsEnableEditAnn();
   void SetEnableEditAnn(BOOL Enable);
   LONG SetRot(short Rotation); //0,90,180,270
   short GetRot();
   void SetFitMode(short FitMode);
   short GetFitMode();
   void SetDrawMode(short DrawMode);
   short GetDrawMode();
   void SetZoomImg(double Zoom);
   double GetZoom();  
   LONG  GetImgWidth();
   LONG  GetImgHeight();

   void SetDrag(BOOL Drag);
   BOOL IsDrag();


   LONG GetId();
   void SetId(LONG Id);
   
   BOOL SaveNotes();
   BOOL GetChangeAnn();
   BOOL GetAnyChangeAnn();
   LONG SaveNotesToFile(CString File);
   void DeleteAnn();

   BOOL ExistsSelection();
   LONG SaveSelectionToFile(CString FileName);
   LONG SaveFileWithRotation(CString FileName);
   LONG SaveFile(CString& FileName);
   LONG SaveFileAs(CString FileName);

   void ChangeSizeTiffG3Fax();
   void ReturnSizeTiffG3Fax(int Page);
      

	//{{AFX_VIRTUAL(CVw)
	public:
	virtual void OnInitialUpdate();
	protected:
	virtual void OnDraw(CDC* pDC);
	//}}AFX_VIRTUAL


   protected:

	virtual ~CVw();

   #ifdef _DEBUG
	   virtual void AssertValid() const;
	   virtual void Dump(CDumpContext& dc) const;
   #endif
   
  
   protected:
	//{{AFX_MSG(CVw)
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
   afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message); 
   afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
   afx_msg void OnLButtonDblClk(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);  
   afx_msg void OnContextMenu(CWnd* pWnd, CPoint point);
   afx_msg void OnHand();
   afx_msg void OnShowpal();
   afx_msg void OnFithorz();
	afx_msg void OnFitvert();
	afx_msg void OnFitwindow();
	afx_msg void OnNormalsize();
   afx_msg void OnZoomdecr();
   afx_msg void OnZoomincr();
   afx_msg void OnZoomRect();
   afx_msg void OnBlackdraw();
   afx_msg void OnGraydraw();
   afx_msg void OnWhitedraw();
   afx_msg void OnFirstp();
   afx_msg void OnLastp();
   afx_msg void OnNextp();
   afx_msg void OnPrevp();
   afx_msg void OnGoto();
   afx_msg void OnRotd90();
   afx_msg void OnRoti90();
   afx_msg void OnPrintImg();
   afx_msg void OnConfig();
   afx_msg void OnIncrCLocal();
   afx_msg void OnIncrGC();
   afx_msg void OnRTD();
   afx_msg void OnTWidth();
   afx_msg void OnSupFD();
   afx_msg void OnSupMTC();
   afx_msg void OnSupMTO();
   afx_msg void OnSupTT();
   afx_msg void OnTF();
   afx_msg void OnRel();
   afx_msg void OnUndo();
   afx_msg void OnSelect();
   afx_msg void OnEditAnn();
   afx_msg void OnSaveAnn();
   afx_msg void OnShowPalAnn();
   afx_msg void OnAnnSelector();
   afx_msg void OnAnnText();
   afx_msg void OnAnnNote();
   afx_msg void OnAnnHighline();
   afx_msg void OnAnnLine();
   afx_msg void OnAnnArrow();
   afx_msg void OnAnnMark();
   afx_msg void OnMProperty();
   afx_msg void OnDeleteAnn();
   afx_msg void OnSaveAs();
   afx_msg void OnEndoso();
   afx_msg void OnInvert();
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()

   protected:

   LONG OpenFile(CString File);
   void ShowScroll(BOOL enable);
   void GetZoomImg();
   LONG SetZoom();
   LONG SetDrawMode();
   void Term();
   LONG Fit();   
   void Init();

   void SetScroll(UINT SBCode,UINT Pos, BOOL Horz);
   void SetStateToolBar();
   void SavePointToDrag(CPoint point);
   void EmptyPointDrag();
   void DragImg(CPoint point);       
   void SetZoomRect(CPoint point);
   void CheckPagesTool();   
   BOOL GetSelRect(CPoint point, CRect& SelRect);
   void Select();
   void DrawSelRect();
   LONG SetTMT();
   LONG GetError();
   LONG GetDIB(LPBITMAPINFOHEADER FAR* pDIB);
   LONG SetDIB(LPBITMAPINFOHEADER pDIB);
   LONG Rot(UINT Angle,BOOL open = FALSE); //Angle = 0,1,2,3
   void PrintFile();

   LONG PagePrint(HIGEAR hIGear,CDC* pDC,CRect FitRectDC,UINT bpp);

   HCURSOR GetZoomCursor();
   HCURSOR GetZoomRectCursor();
   HCURSOR GetDragCursor();

   void NormalizeRot(LONG Rot);

   void SetStateAnnBar();

   void DeleteArrAnnot();
   BOOL ReadFileAnn();
   void DrawIcons(CDC* pDC,HIGEAR& hIGear,LONG Page);
   void RotateIcons(short Angle);
   
   BOOL IsSelectedAnns();
   BOOL SaveAnn(CString FileName);

      
   protected:

   HIGEAR       m_hIGear;
   UINT         m_Zoom;    
   enumFitMode  m_FitMode; 
   enumDrawMode m_DrawMode;
   UINT         m_NumPages;
   UINT         m_OpenPag;
   double       m_ZoomImg; 
   BOOL         m_showScroll;
   CString      m_FileName; 
   CString      m_FileNote; //Fichero de anotaciones
   LONG         m_Rot;
   BOOL         m_EditAnn; //Editar anotaciones
   int          m_PalTool; //anotaciones
   ICDocAnnDoc* m_pAnnDoc; //estructura invesDoc de anotaciones
   CPtrList     m_ListPages;   //lista de punteros a anotaciones de páginas
   BOOL         m_TermAnn;  //indica si se debe borrar el fichero de anotaciones
   BOOL         m_ChangeAnn;  //indica cambios en las anotaciones   
   BOOL         m_AnyChangeAnn; //indica modificación de las anotaciones desde su apertura
   BOOL         m_RotIcon;
   CPoint       m_ptoContext;
   LONG         m_FileType;
   LONG         m_FileCompress;

   BOOL         m_IsGray;
   int          m_Tool;
   CPoint       m_PointAntDrag;
   LPMYSELECTDATA m_lpSelectData;
   int          m_TMT;
   BOOL         m_paint;
   BOOL         m_Undo;
   BOOL         m_Print;
   BOOL         m_SaveAs;
   BOOL         m_open;
   BOOL         m_SetZoomRectNow;
   BOOL         m_DoUndo;
   CString      m_Endoso;
   BOOL         m_CanEditAnn;

   public:

   CPagirDlg*   m_pIrPag;
   CConfgDlg*   m_pConfig;
   CSelGearTrk  m_DCTrkRect;
   CRect        m_SelRect;
   CEndosoDlg*  m_pEndoso;

   LONG         m_IdAnn; //identificador para anotaciones nuevas.

};




/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_VW_H__24C6D5A0_6D0D_11D4_8128_00C0F049167F__INCLUDED_)
