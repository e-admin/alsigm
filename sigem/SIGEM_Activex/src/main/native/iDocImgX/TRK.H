
#ifndef __TRK_H__
#define __TRK_H__

#define UPDATE_NULL 0
#define UPDATE_THIS 1

//////////////////////////////////////////////////////////////////////
//								    //
//  CTrk							    //
//								    //
//////////////////////////////////////////////////////////////////////

class CTrk : public CObject
{

   DECLARE_SERIAL(CTrk);

   public:

   CTrk();
   CTrk(LPCRECT pRect,
   int	 TrackStyle  = TS_Move|TS_Resize|TS_Invert,
   int	 DrawStyle   = DS_Border|DS_ResizeHandles,
   int	 UpdateStyle = US_Invalidate,
   int	 ExeMode     = XM_Edit);

   virtual ~CTrk();

   virtual void Serialize(CArchive& Arch);

   virtual void OnDraw(CDC* pDC,double Zoom = 1) const;

   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
	     double Zoom = 1) const;

   BOOL Track(POINT Point,UINT Flags,CWnd* pWnd,
	 double Zoom = 1,int UpdateMode = UPDATE_NULL);
   BOOL TrackRubberBand(POINT Point,UINT Flags,CWnd* pWnd,double Zoom = 1);

   void GetRect(LPRECT pRect) const;
   int	 GetTrackStyle() const;
   int	 GetDrawStyle() const;
   int	 GetUpdateStyle() const;
   int	 GetExeMode() const;
   void GetExtRect(LPRECT pRect) const;
   void GetIntRect(LPRECT pRect) const;
   void GetMinSize(LPSIZE pSize) const;
   void GetDefSize(LPSIZE pSize) const;

   void SetRect(LPCRECT pRect);
   void SetTrackStyle(int Style);
   void SetDrawStyle(int Style);
   void SetUpdateStyle(int Style);
   void SetExeMode(int Mode);
   void SetExtRect(LPCRECT pRect);
   void SetIntRect(LPCRECT pRect);
   void SetMinSize(SIZE Size);
   void SetDefSize(SIZE Size);

   BOOL CanMove() const;
   BOOL CanResize() const;
   BOOL CanInvert() const;
   BOOL HasLine() const;
   BOOL HasBorder() const;
   BOOL HasResizeHandles() const;
   BOOL UpdateRepainting() const;
   BOOL UpdateInvalidating() const;
   BOOL HasExtRect() const;
   BOOL HasIntRect() const;
   BOOL HasMinSize() const;
   BOOL HasDefSize() const;

   void EnableMove(BOOL Enable);
   void EnableResize(BOOL Enable);
   void EnableInvert(BOOL Enable);
   void EnableLine(BOOL Enable);
   void EnableBorder(BOOL Enable);
   void EnableResizeHandles(BOOL Enable);

   virtual int GetHitCode(POINT Point,CWnd* pWnd,double Zoom = 1) const;

   virtual BOOL NeedDraw(CDC* pDC,double Zoom = 1) const;

   virtual void GetTotalFrmRect(LPRECT pRect,CDC* pDC,
	    double Zoom = 1) const;
   virtual void GetTotalFrmRect(LPRECT pRect,CWnd* pWnd,
	    double Zoom = 1) const;
   virtual void GetVisibleFrmRect(LPRECT pRect,CDC* pDC,
	      double Zoom = 1) const;
   virtual void GetVisibleFrmRect(LPRECT pRect,CWnd* pWnd,
	      double Zoom = 1) const;

   virtual void LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom = 1) const;
   virtual void LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom = 1) const;
   virtual void LogToDev(LPSIZE pSize,CDC* pDC,double Zoom = 1) const;
   virtual void LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom = 1) const;
   virtual void LogToDev(LPRECT pRect,CDC* pDC,double Zoom = 1) const;
   virtual void LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom = 1) const;

   virtual void DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom = 1) const;
   virtual void DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom = 1) const;
   virtual void DevToLog(LPSIZE pSize,CDC* pDC,double Zoom = 1) const;
   virtual void DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom = 1) const;
   virtual void DevToLog(LPRECT pRect,CDC* pDC,double Zoom = 1) const;
   virtual void DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom = 1) const;

   virtual int GetFrameSize() const;
   virtual int GetResizeHandleSize() const;
   virtual int GetMinExtHitOff() const;
   virtual int GetMinIntHitOff() const;

   virtual void Draw(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;
   virtual void DrawLine(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawBorder(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawResizeHandles(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawTrueLine(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawTrueBorder(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawTrueResizeHandles(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawTrueResizeHandle(int Handle,CDC* pDC,
		 LPCRECT pDevRect) const;

   virtual void Update(int UpdateMode,CWnd* pWnd,double Zoom = 1) const;
   virtual void Invalidate(CWnd* pWnd,double Zoom = 1) const;

   BOOL TrackHandle(BOOL RubberBand,int Handle,POINT Point,CWnd* pWnd,
	  double Zoom = 1);

   virtual void AdjustPoint(int Handle,CWnd* pWnd,double Zoom,
	     LPPOINT pPoint) const;
   virtual void ClipCursor(POINT Point,int Handle,CWnd* pWnd,
	    double Zoom = 1) const;
   virtual void AdjustRect(int Handle,POINT Point,CWnd* pWnd,
	    double Zoom,LPRECT pActRect);
   virtual void DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
	       CWnd* pWnd,double Zoom,LPCRECT pRect) const;
   virtual void DrawTrueTrackRect(BOOL RubberBand,int Handle,POINT Point,
	      CWnd* pWnd,double Zoom,LPCRECT pRect) const;
   virtual void AdjustOffset(int Handle,POINT Point,CWnd* pWnd,
	      double Zoom,
	      LPCRECT pActRect,LPSIZE pOff) const;
   virtual BOOL EnforceRect(int Handle,POINT Point,CWnd* pWnd,double Zoom,
	     BOOL Changed,BOOL Aborted,BOOL RubberBand);
   virtual void SetClipCursorRect(POINT Point,int Handle,
	      CWnd* pWnd,double Zoom,
	      LPRECT pClipRect) const;

   void OffsetTrackRect(LPCRECT pSrcRect,int Handle,SIZE Off,
	 LPRECT pDstRect) const;
   void DefClip(POINT Point,int Handle,LPRECT pClipRect) const;
   void ClipToWnd(CWnd* pWnd,double Zoom,POINT Point,int Handle,
	LPRECT pClipRect) const;
   void ClipToInvert(CWnd* pWnd,double Zoom,POINT Point,int Handle,
	   LPRECT pClipRect) const;
   void ClipToExtRect(CWnd* pWnd,double Zoom,POINT Point,int Handle,
	    LPRECT pClipRect) const;
   void ClipToIntRect(CWnd* pWnd,double Zoom,POINT Point,int Handle,
	    LPRECT pClipRect) const;
   void ExtClip(LPCRECT pTrkRect,LPCRECT pExtRect,POINT Point,int Handle,
      LPRECT pClipRect) const;
   void IntClip(LPCRECT pTrkRect,LPCRECT pIntRect,POINT Point,int Handle,
      LPRECT pClipRect) const;
   void InvClip(LPCRECT pTrkRect,POINT Point,int Handle,
      LPRECT pClipRect) const;
   virtual BOOL EnforceMinSize(int Handle);
   virtual BOOL EnforceDefSize(POINT LogPoint);
   virtual void TrackHandleDone(BOOL Changed,int Inversion,BOOL RubberBand,
	    int Handle,POINT Point,
	    CWnd* pWnd,double Zoom = 1);

   public:

   enum TrackStyles
   {
      TS_None  = 0,
      TS_Move  = 1,
      TS_Resize = 2,
      TS_Invert = 4
   };

   enum DrawStyles
   {
      DS_None	      = 0,
      DS_Line	      = 1,
      DS_Border        = 2,
      DS_ResizeHandles = 4
   };

   enum UpdateStyles
   {
      US_Repaint    = 1,
      US_Invalidate = 2
   };

   enum ExeModes
   {
      XM_Edit = 1,
      XM_Run  = 2
   };

   enum HitCodes
   {
      HC_Outside     = -1,
      HC_Middle      =	0,
      HC_LeftTop     =	1,
      HC_Top	    =	2,
      HC_RightTop    =	3,
      HC_Right	    =	4,
      HC_RightBottom =	5,
      HC_Bottom      =	6,
      HC_LeftBottom  =	7,
      HC_Left	    =	8
   };

   enum InversionCodes
   {
      IC_None = 0,
      IC_Horz = 1,
      IC_Vert = 2,
      IC_Both = 3
   };

   protected:

   CRect m_Rect;
   int	  m_TrackStyle;
   int	  m_DrawStyle;
   int	  m_UpdateStyle;
   int	  m_ExeMode;
   CRect m_ExtRect;
   CRect m_IntRect;
   CSize m_MinSize;
   CSize m_DefSize;

};

//////////////////////////////////////////////////////////////////////
//			    //
//  CFixTrk			  //
//			    //
//////////////////////////////////////////////////////////////////////

class CFixTrk : public CTrk
{

   DECLARE_SERIAL(CFixTrk);

   public:

   CFixTrk();
   CFixTrk(LPCRECT pRect,
      int      TrackStyle  = TS_Move|TS_Resize|TS_Invert,
      int      DrawStyle   = DS_Border|DS_ResizeHandles,
      int      UpdateStyle = US_Repaint);

   virtual ~CFixTrk();

   virtual void Serialize(CArchive& Arch);

   virtual BOOL OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1,int UpdateMode = UPDATE_THIS);

};

//////////////////////////////////////////////////////////////////////
//			    //
//  CSelTrk			  //
//			    //
//////////////////////////////////////////////////////////////////////

class CSelTrk : public CTrk
{

   DECLARE_SERIAL(CSelTrk);

   public:

   CSelTrk();

   virtual ~CSelTrk();

   virtual void Serialize(CArchive& Arch);

   virtual BOOL OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1);

   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
	     double Zoom = 1) const;

};

//////////////////////////////////////////////////////////////////////
//			    //
//  CCutTrk			  //
//			    //
//////////////////////////////////////////////////////////////////////

class CCutTrk : public CTrk
{

   DECLARE_SERIAL(CCutTrk);

   public:

   CCutTrk();
   CCutTrk(LPCRECT pRect,
      int      TrackStyle  = TS_Move|TS_Resize|TS_Invert,
      int      DrawStyle   = DS_Border|DS_ResizeHandles,
      int      UpdateStyle = US_Repaint);

   virtual ~CCutTrk();

   virtual void Serialize(CArchive& Arch);

   virtual BOOL OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1,int UpdateMode = UPDATE_THIS);

};

//////////////////////////////////////////////////////////////////////
//			    //
//  CMoveTrk			     //
//			    //
//////////////////////////////////////////////////////////////////////

class CMoveTrk : public CTrk
{

   public:

   CMoveTrk();

   virtual ~CMoveTrk();

   void Add(LPCRECT pRect);

   BOOL TrackMove(POINT Point,UINT Flags,CWnd* pWnd,double Zoom = 1);

   protected:

   virtual void DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
			      CWnd* pWnd,double Zoom,LPCRECT pRect) const;

   virtual void DrawTrackRectList(CDC* pDC,SIZE Off) const;

   protected:

   CPtrList m_RectList;
   CRect    m_DevRect;
   double   m_Zoom;

};

//////////////////////////////////////////////////////////////////////
//								    //
//  CTrkObj							    //
//								    //
//////////////////////////////////////////////////////////////////////

class CTrkObj : public CObject
{

   DECLARE_SERIAL(CTrkObj);

   public:

   CTrkObj(LONG ObjClassId);
   CTrkObj(LONG ObjClassId, LPCRECT pRect,
      int      Style	   = STL_Move|STL_Resize|STL_Invert,
      int      State	   = STT_Normal,
      int      UpdateStyle = UPD_Invalidate,
      int      ExeMode	   = XM_Edit);
   CTrkObj(LONG ObjClassId, CTrk* pTrk);

   virtual ~CTrkObj();

   virtual void Serialize(CArchive& Arch);

   virtual void OnDraw(CDC* pDC,double Zoom = 1) const;

   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
	     double Zoom = 1) const;

   virtual BOOL OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1,int UpdateMode = UPDATE_THIS);

   virtual void OnLButtonDblClk(POINT Point,UINT Flags,
	    CWnd* pWnd,double Zoom = 1);

   virtual void OnRButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1);

   void GetRect(LPRECT pRect) const;
   int	 GetStyle() const;
   int	 GetState() const;
   int	 GetUpdateStyle() const;
   int	 GetExeMode() const;
   void GetExtRect(LPRECT pRect) const;
   void GetIntRect(LPRECT pRect) const;
   void GetMinSize(LPSIZE pSize) const;
   void GetDefSize(LPSIZE pSize) const;
   LONG GetObjClassId() const;

   virtual void SetRect(LPCRECT pRect);
   virtual void SetStyle(int Style);
   virtual void SetState(int State);
   virtual void SetUpdateStyle(int Style);
   virtual void SetExeMode(int Mode);
   virtual void SetExtRect(LPCRECT pRect);
   virtual void SetIntRect(LPCRECT pRect);
   virtual void SetMinSize(SIZE Size);
   virtual void SetDefSize(SIZE Size);
   virtual void SetInUse(BOOL InUse);

   BOOL CanMove() const;
   BOOL CanResize() const;
   BOOL CanInvert() const;
   BOOL IsNormal() const;
   BOOL IsSelected() const;
   BOOL IsInUse() const;
   BOOL UpdateRepainting() const;
   BOOL UpdateInvalidating() const;
   BOOL HasExtRect() const;
   BOOL HasIntRect() const;
   BOOL HasMinSize() const;
   BOOL HasDefSize() const;

   void EnableMove(BOOL Enable);
   void EnableResize(BOOL Enable);
   void EnableInvert(BOOL Enable);
   void SetNormal();
   void SetSelected();

   virtual BOOL IsHit(POINT Point,CWnd* pWnd,int& HitCode,
	    double Zoom = 1) const;

   virtual void Select(CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void Unselect(CWnd* pWnd,double Zoom = 1,
	  int UpdateMode = UPDATE_THIS);

   virtual void Move(LPCRECT pPosRect,CWnd* pWnd,double Zoom = 1,
	   int UpdateMode = UPDATE_THIS);

   BOOL Intersect(LPCRECT pRect) const;

   virtual CTrkObj* SetupClone() const;
   virtual CTrkObj* CreateClone() const;
   virtual void SetCloneDefaults(CTrkObj* pTrkObj) const;

   virtual CTrk* SetupTrk() const;
   virtual CTrk* CreateTrk() const;
   virtual void  SetTrkDefaults(CTrk* pTrk) const;

   virtual CTrk* SetupTrkNew() const;
   virtual CTrk* CreateTrkNew() const;
   virtual void  SetTrkNewDefaults(CTrk* pTrk) const;

   virtual void  SetObjDefaults(CTrk* pTrk);

   virtual void Update(int UpdateMode,CWnd* pWnd,double Zoom = 1) const;
   virtual void Invalidate(CWnd* pWnd,double Zoom = 1) const;

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;

   virtual void OnPosChanged(CWnd* pWnd);

   virtual void OffsetRect(const CSize& Off);

   public:

   enum Styles
   {
      STL_None	= 0,
      STL_Move	= 1,
      STL_Resize = 2,
      STL_Invert = 4
   };

   enum States
   {
      STT_Null	  = 0,
      STT_Normal   = 1,
      STT_Selected = 2
   };

   enum UpdateStyles
   {
      UPD_Repaint    = 1,
      UPD_Invalidate = 2
   };

   enum ExeModes
   {
      XM_Edit	 = 1,
      XM_Run	 = 2,
      XM_InUse	 = 4
   };

   protected:

   CTrkObj();

   protected:

   CRect m_Rect;
   int	 m_Style;
   int	 m_State;
   int	 m_UpdateStyle;
   int	 m_ExeMode;
   CRect m_ExtRect;
   CRect m_IntRect;
   CSize m_MinSize;
   CSize m_DefSize;
   LONG  m_OCID;

};

#define OCID_NULL     -1
#define OCID_SELECTOR  0

#define ALIGN_LEFT     1
#define ALIGN_TOP      2
#define ALIGN_RIGHT    3
#define ALIGN_BOTTOM   4
#define ALIGN_HCENTER  5
#define ALIGN_VCENTER  6

#define SAME_SIZE_WIDTH   1
#define SAME_SIZE_HEIGHT  2
#define SAME_SIZE_BOTH	  3

#define CENTER_WIDTH   1
#define CENTER_HEIGHT  2
#define CENTER_BOTH    3

#define SAME_SPACE_WIDTH   1
#define SAME_SPACE_HEIGHT  2

//////////////////////////////////////////////////////////////////////
//								    //
//  CTrkObjList 						    //
//								    //
//////////////////////////////////////////////////////////////////////

class CTrkObjList : public CObject
{

   DECLARE_SERIAL(CTrkObjList);

   public:

   CTrkObjList();

   virtual ~CTrkObjList();

   BOOL Term();

   virtual void Serialize(CArchive& Arch);

   virtual void OnDraw(CDC* pDC,double Zoom = 1) const;

   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
	     double Zoom = 1) const;

   virtual BOOL OnLButtonDown(LONG ObjClassId,POINT Point,UINT Flags,
	       CWnd* pWnd,double Zoom = 1,
	       int UpdateMode = UPDATE_THIS);

   virtual void OnLButtonDblClk(POINT Point,UINT Flags,
	    CWnd* pWnd,double Zoom = 1);

   virtual void OnRButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
	       double Zoom = 1);

   virtual void OnEditCopy(CWnd* pWnd,double Zoom = 1,
			   int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateEditCopy(CCmdUI* pCmdUI) const;

   virtual void OnEditCut(CWnd* pWnd,double Zoom = 1,
			  int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateEditCut(CCmdUI* pCmdUI) const;

   virtual void OnEditPaste(CWnd* pWnd,double Zoom = 1,
			    int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateEditPaste(CCmdUI* pCmdUI) const;

   virtual void OnEditClear(CWnd* pWnd,double Zoom = 1,
			    int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateEditClear(CCmdUI* pCmdUI) const;

   virtual void OnAlign(int AlignMode,CWnd* pWnd,double Zoom = 1,
	 int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateAlign(CCmdUI* pCmdUI) const;

   virtual void OnSameSize(int SameSizeMode,CWnd* pWnd,double Zoom = 1,
	    int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSameSize(CCmdUI* pCmdUI) const;

   virtual void OnCenter(int CenterMode,LPCRECT pRect,CWnd* pWnd,
	  double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateCenter(CCmdUI* pCmdUI) const;

   virtual void OnSameSpace(int SameSpaceMode,CWnd* pWnd,
	     double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSameSpace(CCmdUI* pCmdUI) const;

   virtual void OnSelectAll(CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSelectAll(CCmdUI* pCmdUI) const;

   virtual void OnObjToBack();
   virtual void OnUpdateObjToBack(CCmdUI* pCmdUI) const;

   POSITION GetHeadPos() const;
   CTrkObj* GetNext(POSITION& Pos) const;
   int	    GetCount() const;
   void     Add(CTrkObj* pObj);
   void     Remove(CTrkObj* pObj);
   void     RemoveAll();

   POSITION GetHeadPosSel() const;
   CTrkObj* GetNextSel(POSITION& Pos) const;
   POSITION GetTailPosSel() const;
   CTrkObj* GetPrevSel(POSITION& Pos) const;
   int	    GetCountSel() const;
   void     AddSel(CTrkObj* pObj);
   void     RemoveSel(CTrkObj* pObj);
   void     RemoveAllSel();

   POSITION GetHeadPosCB() const;
   CTrkObj* GetNextCB(POSITION& Pos) const;
   int	    GetCountCB() const;
   void     AddCB(CTrkObj* pObj);
   void     RemoveCB(CTrkObj* pObj);
   void     RemoveAllCB();

   int	GetStyle() const;
   int	GetExeMode() const;
   void GetDefExtRect(LPRECT pRect) const;
   void SetStyle(int Style);
   void SetExeMode(int Mode);
   void SetDefExtRect(LPCRECT pRect);
   BOOL HasMultSel() const;
   void EnableMultSel(BOOL Enable);
   BOOL HasDefExtRect() const;

   virtual CTrkObj* GetHitObj(POINT Point,CWnd* pWnd,int& HitCode,
	       double Zoom = 1) const;

   virtual void Select(CTrkObj* pObj,CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void Unselect(CTrkObj* pObj,CWnd* pWnd,double Zoom = 1,
	  int UpdateMode = UPDATE_THIS);
   virtual void SelectAll(CWnd* pWnd,double Zoom = 1,
	   int UpdateMode = UPDATE_THIS);
   virtual void UnselectAll(CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void Select(CTrkObj* pObj,BOOL Add,CWnd* pWnd,
	     double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual void SelectInRect(LPCRECT pRect,CWnd* pWnd,double Zoom = 1,
	      int UpdateMode = UPDATE_THIS);

   virtual BOOL OnSelector(POINT Point,UINT Flags,CWnd* pWnd,
	    double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual BOOL OnNewObj(LONG ObjClassId,POINT Point,UINT Flags,CWnd* pWnd,
	  double Zoom = 1,int UpdateMode = UPDATE_THIS);

   virtual CTrk* SetupSelTrk() const;
   virtual void  GetSelRect(LPRECT pRect,POINT Point,UINT Flags,
	     CWnd* pWnd,double Zoom = 1) const;

   virtual void SetObjDefaults(CTrkObj* pObj) const;

   virtual void SetMoveTrkDefaults(CMoveTrk* pTrk) const;
   virtual BOOL TrackSelObjs(POINT Point,UINT Flags,CWnd* pWnd,
	      double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual void OffsetSelObjs(SIZE Off,CWnd* pWnd,double Zoom = 1,
	       int UpdateMode = UPDATE_THIS);

   virtual CTrk* SetupNewObjTrk(LONG ObjClassId) const;
   virtual CTrk* CreateNewObjTrk(LONG ObjClassId) const;
   virtual void  SetNewObjTrkDefaults(LONG ObjClassId,CTrk* pTrk) const;

   virtual CTrkObj* CreateObj(LONG ObjClassId,CTrk* pTrk,CWnd* pWnd);

   void GetBoundRect(LPRECT pRect) const;
   void GetBoundRectSel(LPRECT pRect) const;
   void GetBoundRectCB(LPRECT pRect) const;

   virtual CPoint GetAlignVal(int AlignMode,CTrkObj* pObj) const;
   virtual CSize  GetAlignOff(int AlignMode,CPoint AlignVal,
	       CTrkObj* pObj) const;
   virtual CRect  GetAlignPos(CTrkObj* pObj,CSize Off) const;

   virtual CSize GetSameSizeVal(int SameSizeMode,CTrkObj* pObj) const;
   virtual CSize GetSameSizeOff(int SameSizeMode,CSize SameSizeVal,
	    CTrkObj* pObj) const;
   virtual CRect GetSameSizePos(CTrkObj* pObj,CSize Off) const;

   virtual CPoint GetCenterVal(int CenterMode,LPCRECT pRect) const;

   virtual CTrkObj* GetFirstInPos(int SameSpaceMode) const;
   virtual CTrkObj* GetLastInPos(int SameSpaceMode) const;
   virtual CTrkObj* GetNextInPos(int SameSpaceMode,CTrkObj* pRefObj) const;

   virtual int GetTotalSpace(int SameSpaceMode,
	      CTrkObj* pFirstObj,CTrkObj* pLastObj) const;
   virtual int GetTakenSpace(int SameSpaceMode,
	      CTrkObj* pFirstObj,CTrkObj* pLastObj) const;
   virtual int GetInterSpace(int SameSpaceMode,
	      CTrkObj* pFirstObj,CTrkObj* pLastObj) const;

   virtual void SetInUse(LONG ObjClassId,BOOL InUse,CWnd* pWnd,double Zoom = 1,
			   int UpdateMode = UPDATE_THIS);

   virtual void ClearCB();
   virtual void CopyToCB();
   virtual void CopyFromCB(CWnd* pWnd,double Zoom = 1,
			   int UpdateMode = UPDATE_THIS);
   virtual void GetCBOff(CSize& Off) const;

   public:

   enum Styles
   {
      STL_None	 = 0,
      STL_MultSel = 1,
   };

   enum ExeModes
   {
      XM_Edit  = 1,
      XM_Run   = 2
   };

   protected:

   CObList m_List;
   CObList m_SelList;
   CObList m_CBList;
   int	   m_Style;
   int	   m_ExeMode;
   CRect   m_DefExtRect;

};

//////////////////////////////////////////////////////////////////////
//			    //
//  CTrkObjForm 		     //
//			    //
//////////////////////////////////////////////////////////////////////

class CTrkObjForm : public CObject
{

   DECLARE_SERIAL(CTrkObjForm);

   public:

   CTrkObjForm();
   CTrkObjForm(CTrkObj* pExtObj,CTrkObjList* pIntObjList);

   virtual void Init(LONG ObjClassId);

   virtual ~CTrkObjForm();

   virtual void Serialize(CArchive& Arch);

   virtual void OnDraw(CDC* pDC,double Zoom = 1) const;

   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
	     double Zoom = 1) const;

   virtual BOOL OnLButtonDown(LONG ObjClassId,POINT Point,UINT Flags,
	       CWnd* pWnd,double Zoom = 1,
	       int UpdateMode = UPDATE_THIS);

   virtual void OnLButtonDblClk(POINT Point,UINT Flags,
	    CWnd* pWnd,double Zoom = 1);

   virtual void OnEditClear(CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateEditClear(CCmdUI* pCmdUI) const;

   virtual void OnAlign(int AlignMode,CWnd* pWnd,double Zoom = 1,
	 int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateAlign(CCmdUI* pCmdUI) const;

   virtual void OnSameSize(int SameSizeMode,CWnd* pWnd,double Zoom = 1,
	    int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSameSize(CCmdUI* pCmdUI) const;

   virtual void OnCenter(int CenterMode,CWnd* pWnd,double Zoom = 1,
	  int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateCenter(CCmdUI* pCmdUI) const;

   virtual void OnSameSpace(int SameSpaceMode,CWnd* pWnd,
	     double Zoom = 1,int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSameSpace(CCmdUI* pCmdUI) const;

   virtual void OnSelectAll(CWnd* pWnd,double Zoom = 1,
	     int UpdateMode = UPDATE_THIS);
   virtual void OnUpdateSelectAll(CCmdUI* pCmdUI) const;

   virtual void Syncr();

   virtual void InitExtObj(LONG ObjClassId);
   virtual void InitIntObjList();

   virtual void SetupExtObj(LONG ObjClassId);
   virtual void CreateExtObj(LONG ObjClassId);
   virtual void SetExtObjDefaults();

   virtual void SetupIntObjList();
   virtual void CreateIntObjList();
   virtual void SetIntObjListDefaults();

   virtual void OnExtObjChanged();
   virtual void OnIntObjListChanged();

   virtual void ChangeExtRect();
   virtual void ChangeIntRect();

   protected:

   CTrkObj* m_pExtObj;
   CTrkObjList* m_pIntObjList;

};

#endif // __TRK_H__

