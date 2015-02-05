
#include <afxwin.h>
#include <afxext.h>
#include <limits.h>
#include "geom.h"
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

IMPLEMENT_SERIAL(CTrkObjList,CObject,0)

CTrkObjList::CTrkObjList()
: m_List(),m_SelList(),m_CBList(),m_DefExtRect(0,0,0,0)
{
   m_Style   = STL_MultSel;
   m_ExeMode = XM_Edit;
}

CTrkObjList::~CTrkObjList()
{
   Term();
}

BOOL CTrkObjList::Term()
{

   BOOL Ret = TRUE;

   RemoveAll();
   RemoveAllSel();
   RemoveAllCB();

   m_DefExtRect.SetRectEmpty();

   return(Ret);

}

void CTrkObjList::Serialize(CArchive& Arch)
{
   CObject::Serialize(Arch);
   m_List.Serialize(Arch);
}

void CTrkObjList::OnDraw(CDC* pDC,double Zoom) const
{
   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      pObj->OnDraw(pDC,Zoom);
   }
}

BOOL CTrkObjList::OnSetCursor(CWnd* pWnd,UINT HitTest,
                              UINT Msg,double Zoom) const
{

   BOOL Set = FALSE;

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNextSel(Pos);
      Set = pObj->OnSetCursor(pWnd,HitTest,Msg,Zoom);
      if (Set) break;
   }

   return(Set);

}

BOOL CTrkObjList::OnLButtonDown(LONG ObjClassId,POINT Point,UINT Flags,
                                CWnd* pWnd,double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;

   if (ObjClassId == OCID_SELECTOR)
      Changed = OnSelector(Point,Flags,pWnd,Zoom,UpdateMode);
   else
      Changed = OnNewObj(ObjClassId,Point,Flags,pWnd,Zoom,UpdateMode);

   return(Changed);

}

void CTrkObjList::OnLButtonDblClk(POINT Point,UINT Flags,
                                  CWnd* pWnd,double Zoom)
{

   int HitCode;

   CTrkObj* pObj = GetHitObj(Point,pWnd,HitCode,Zoom);

   if (pObj != NULL)
      pObj->OnLButtonDblClk(Point,Flags,pWnd,Zoom);

}

void CTrkObjList::OnRButtonDown(POINT Point,UINT Flags,
                                CWnd* pWnd,double Zoom)
{

   int HitCode;

   CTrkObj* pObj = GetHitObj(Point,pWnd,HitCode,Zoom);

   if (pObj != NULL)
      pObj->OnRButtonDown(Point,Flags,pWnd,Zoom);

}

void CTrkObjList::OnEditCopy(CWnd* /*pWnd*/,double /*Zoom*/,int /*UpdateMode*/)
{
   CopyToCB();
}

void CTrkObjList::OnUpdateEditCopy(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnEditCut(CWnd* pWnd,double Zoom,int UpdateMode)
{
   CopyToCB();
   OnEditClear(pWnd,Zoom,UpdateMode);
}

void CTrkObjList::OnUpdateEditCut(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnEditPaste(CWnd* pWnd,double Zoom,int UpdateMode)
{
   CopyFromCB(pWnd,Zoom,UpdateMode);
}

void CTrkObjList::OnUpdateEditPaste(CCmdUI* pCmdUI) const
{
   if ( GetCountCB() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnEditClear(CWnd* pWnd,double Zoom,int UpdateMode)
{

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNextSel(Pos);
      pObj->Update(UpdateMode,pWnd,Zoom);
      Remove(pObj);
   }

   RemoveAllSel();

   Pos = GetHeadPos();
   if (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      Select(pObj,pWnd,Zoom,UpdateMode);
   }

}

void CTrkObjList::OnUpdateEditClear(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnAlign(int AlignMode,CWnd* pWnd,double Zoom,
                          int UpdateMode)
{

   CTrkObj* pObj;
   CPoint   AlignVal;
   CSize    Off;
   CRect    NewPos;

   POSITION Pos = GetTailPosSel();
   if (Pos != NULL)
   {
      pObj     = GetPrevSel(Pos);
      AlignVal = GetAlignVal(AlignMode,pObj);
   }

   while (Pos != NULL)
   {
      pObj   = GetPrevSel(Pos);
      Off    = GetAlignOff(AlignMode,AlignVal,pObj);
      NewPos = GetAlignPos(pObj,Off);
      pObj->Move(NewPos,pWnd,Zoom,UpdateMode);
   }

}

void CTrkObjList::OnUpdateAlign(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 1 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnSameSize(int SameSizeMode,CWnd* pWnd,double Zoom,
                             int UpdateMode)
{

   CTrkObj* pObj;
   CSize    SameSizeVal;
   CSize    Off;
   CRect    NewPos;

   POSITION Pos = GetTailPosSel();
   if (Pos != NULL)
   {
      pObj        = GetPrevSel(Pos);
      SameSizeVal = GetSameSizeVal(SameSizeMode,pObj);
   }

   while (Pos != NULL)
   {
      pObj   = GetPrevSel(Pos);
      Off    = GetSameSizeOff(SameSizeMode,SameSizeVal,pObj);
      NewPos = GetSameSizePos(pObj,Off);
      pObj->Move(NewPos,pWnd,Zoom,UpdateMode);
   }

}

void CTrkObjList::OnUpdateSameSize(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 1 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnCenter(int CenterMode,LPCRECT pRect,
                           CWnd* pWnd,double Zoom,int UpdateMode)
{

   CRect SelRect;
   GetBoundRectSel(SelRect);

   CPoint SelVal  = GetCenterVal(CenterMode,SelRect);
   CPoint RectVal = GetCenterVal(CenterMode,pRect);

   CSize Off;
   Off.cx = RectVal.x - SelVal.x;
   Off.cy = RectVal.y - SelVal.y;

   CTrkObj* pObj;
   CRect    NewPos;

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      pObj   = GetNextSel(Pos);
      NewPos = GetAlignPos(pObj,Off);
      pObj->Move(NewPos,pWnd,Zoom,UpdateMode);
   }

}

void CTrkObjList::OnUpdateCenter(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnSameSpace(int SameSpaceMode,CWnd* pWnd,
                              double Zoom,int UpdateMode)
{

   CTrkObj* pFirstObj = GetFirstInPos(SameSpaceMode);
   CTrkObj* pLastObj  = GetLastInPos(SameSpaceMode);

   int InterSpace = GetInterSpace(SameSpaceMode,pFirstObj,pLastObj);

   if (InterSpace > 0)
   {

      CTrkObj* pRefObj = pFirstObj;
      CTrkObj* pNextObj;
      CSize    Off;
      CRect    RefRect,NextRect,NewPos;

      while (pRefObj != pLastObj)
      {

         pNextObj = GetNextInPos(SameSpaceMode,pRefObj);

         pRefObj->GetRect(RefRect);
         pNextObj->GetRect(NextRect);

         if (SameSpaceMode == SAME_SPACE_WIDTH)
         {
            Off.cx = RefRect.right  + InterSpace + 1 - NextRect.left;
            Off.cy = 0;
         }
         else
         {
            Off.cx = 0;
            Off.cy = RefRect.bottom + InterSpace + 1 - NextRect.top;
         }

         NewPos = GetAlignPos(pNextObj,Off);
         pNextObj->Move(NewPos,pWnd,Zoom,UpdateMode);

         pRefObj = pNextObj;

      }

   }

}

void CTrkObjList::OnUpdateSameSpace(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() > 2 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnSelectAll(CWnd* pWnd,double Zoom,
                              int UpdateMode)
{
   SelectAll(pWnd,Zoom,UpdateMode);
}

void CTrkObjList::OnUpdateSelectAll(CCmdUI* pCmdUI) const
{
   if ( GetCount() > 0 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

void CTrkObjList::OnObjToBack()
{

   CTrkObj* pSelObj;

   POSITION Pos = GetHeadPosSel();
   if (Pos != NULL)
      pSelObj = GetNextSel(Pos);
   else
      goto End;

   Pos = m_List.Find(pSelObj);

   m_List.RemoveAt(Pos);

   m_List.AddHead(pSelObj);

   End:;

}

void CTrkObjList::OnUpdateObjToBack(CCmdUI* pCmdUI) const
{
   if ( GetCountSel() == 1 )
      pCmdUI->Enable(TRUE);
   else
      pCmdUI->Enable(FALSE);
}

POSITION CTrkObjList::GetHeadPos() const
{
   return( m_List.GetHeadPosition() );
}

#pragma optimize("",off)

CTrkObj* CTrkObjList::GetNext(POSITION& Pos) const
{
   return( (CTrkObj*)(m_List.GetNext(Pos)) );
}

#pragma optimize("",on)

int CTrkObjList::GetCount() const
{
   return( m_List.GetCount() );
}

#pragma optimize("",off)

void CTrkObjList::Add(CTrkObj* pObj)
{
   m_List.AddTail(pObj);
}

#pragma optimize("",on)

void CTrkObjList::Remove(CTrkObj* pObj)
{
   POSITION Pos = m_List.Find(pObj);
   if (Pos != NULL)
   {
      m_List.RemoveAt(Pos);
      delete pObj;
   }
}

void CTrkObjList::RemoveAll()
{
   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      delete pObj;
   }
   m_List.RemoveAll();
}

POSITION CTrkObjList::GetHeadPosSel() const
{
   return( m_SelList.GetHeadPosition() );
}

CTrkObj* CTrkObjList::GetNextSel(POSITION& Pos) const
{
   return( (CTrkObj*)(m_SelList.GetNext(Pos)) );
}

POSITION CTrkObjList::GetTailPosSel() const
{
   return( m_SelList.GetTailPosition() );
}

CTrkObj* CTrkObjList::GetPrevSel(POSITION& Pos) const
{
   return( (CTrkObj*)(m_SelList.GetPrev(Pos)) );
}

int CTrkObjList::GetCountSel() const
{
   return( m_SelList.GetCount() );
}

void CTrkObjList::AddSel(CTrkObj* pObj)
{
   m_SelList.AddTail(pObj);
}

void CTrkObjList::RemoveSel(CTrkObj* pObj)
{
   POSITION Pos = m_SelList.Find(pObj);
   if (Pos != NULL)
      m_SelList.RemoveAt(Pos);
}

void CTrkObjList::RemoveAllSel()
{
   m_SelList.RemoveAll();
}

POSITION CTrkObjList::GetHeadPosCB() const
{
   return( m_CBList.GetHeadPosition() );
}

CTrkObj* CTrkObjList::GetNextCB(POSITION& Pos) const
{
   return( (CTrkObj*)(m_CBList.GetNext(Pos)) );
}

int CTrkObjList::GetCountCB() const
{
   return( m_CBList.GetCount() );
}

void CTrkObjList::AddCB(CTrkObj* pObj)
{
   m_CBList.AddTail(pObj);
}

void CTrkObjList::RemoveCB(CTrkObj* pObj)
{
   POSITION Pos = m_CBList.Find(pObj);
   if (Pos != NULL)
   {
      m_CBList.RemoveAt(Pos);
      delete pObj;
   }
}

void CTrkObjList::RemoveAllCB()
{
   POSITION Pos = GetHeadPosCB();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      delete pObj;
   }
   m_CBList.RemoveAll();
}

int CTrkObjList::GetStyle() const
{
   return(m_Style);
}

int CTrkObjList::GetExeMode() const
{
   return(m_ExeMode);
}

void CTrkObjList::GetDefExtRect(LPRECT pRect) const
{
   *pRect = m_DefExtRect;
}

void CTrkObjList::SetStyle(int Style)
{
   m_Style = Style;
}

void CTrkObjList::SetExeMode(int Mode)
{
   m_ExeMode = Mode;
}

void CTrkObjList::SetDefExtRect(LPCRECT pRect)
{
   m_DefExtRect.CopyRect(pRect);
   m_DefExtRect.NormalizeRect();
}

BOOL CTrkObjList::HasMultSel() const
{
   if (m_Style & STL_MultSel)
      return(TRUE);
   else
      return(FALSE);
}

void CTrkObjList::EnableMultSel(BOOL Enable)
{
   if (Enable)
   {
      if ( !HasMultSel() )
         m_Style = m_Style | STL_MultSel;
   }
   else
   {
      if ( HasMultSel() )
         m_Style = m_Style ^ STL_MultSel;
   }
}

BOOL CTrkObjList::HasDefExtRect() const
{
   if ( !m_DefExtRect.IsRectNull() )
      return(TRUE);
   else
      return(FALSE);
}

CTrkObj* CTrkObjList::GetHitObj(POINT Point,CWnd* pWnd,int& HitCode,
                                double Zoom) const
{

   CTrkObj* pHitObj = NULL;

   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      if ( pObj->IsHit(Point,pWnd,HitCode,Zoom) )
      {
         pHitObj = pObj;
         if ( pHitObj->IsSelected() ) break;
         //if ( pHitObj->IsSelected() && (HitCode != CTrk::HC_Middle) ) break;
      }
   }

   return(pHitObj);

}

void CTrkObjList::Select(CTrkObj* pObj,CWnd* pWnd,double Zoom,
                         int UpdateMode)
{
   if ( !pObj->IsSelected() )
   {
      pObj->Select(pWnd,Zoom,UpdateMode);
      if ( pObj->IsSelected() )
         AddSel(pObj);
   }
}

void CTrkObjList::Unselect(CTrkObj* pObj,CWnd* pWnd,double Zoom,
                           int UpdateMode)
{
   if ( pObj->IsSelected() )
   {
      pObj->Unselect(pWnd,Zoom,UpdateMode);
      RemoveSel(pObj);
   }
}

void CTrkObjList::SelectAll(CWnd* pWnd,double Zoom,int UpdateMode)
{
   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      Select(pObj,pWnd,Zoom,UpdateMode);
   }
}

void CTrkObjList::UnselectAll(CWnd* pWnd,double Zoom,int UpdateMode)
{
   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNextSel(Pos);
      Unselect(pObj,pWnd,Zoom,UpdateMode);
   }
}

void CTrkObjList::Select(CTrkObj* pObj,BOOL Add,CWnd* pWnd,
                         double Zoom,int UpdateMode)
{

   if ( !Add || !HasMultSel() )
      UnselectAll(pWnd,Zoom,UpdateMode);

   Select(pObj,pWnd,Zoom,UpdateMode);

}

void CTrkObjList::SelectInRect(LPCRECT pRect,CWnd* pWnd,double Zoom,
                               int UpdateMode)
{
   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNext(Pos);
      if ( pObj->IsInUse() && pObj->Intersect(pRect) )
         Select(pObj,pWnd,Zoom,UpdateMode);
   }
}

BOOL CTrkObjList::OnSelector(POINT Point,UINT Flags,CWnd* pWnd,
                             double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;
   int  HitCode;

   CTrkObj* pHitObj = GetHitObj(Point,pWnd,HitCode,Zoom);

   if (pHitObj == NULL)
   {
      UnselectAll(pWnd,Zoom,UpdateMode);
      CRect Rect;
      GetSelRect(Rect,Point,Flags,pWnd,Zoom);
      SelectInRect(Rect,pWnd,Zoom,UpdateMode);
      goto End;
   }

   SetObjDefaults(pHitObj);

   if ( !pHitObj->IsSelected() )
   {
      if (Flags & MK_SHIFT)
         Select(pHitObj,TRUE,pWnd,Zoom,UpdateMode);
      else
         Select(pHitObj,FALSE,pWnd,Zoom,UpdateMode);
   }
   else
   {
      if (Flags & MK_SHIFT)
      {
         Unselect(pHitObj,pWnd,Zoom,UpdateMode);
         goto End;
      }
      if ( (HitCode != CTrk::HC_Middle) && (GetCountSel() != 1) )
         Select(pHitObj,FALSE,pWnd,Zoom,UpdateMode);
   }

   if (GetCountSel() == 1)
      Changed = pHitObj->OnLButtonDown(Point,Flags,pWnd,Zoom,UpdateMode);
   else
      Changed = TrackSelObjs(Point,Flags,pWnd,Zoom,UpdateMode);

   End:

   return(Changed);

}

BOOL CTrkObjList::OnNewObj(LONG ObjClassId,POINT Point,UINT Flags,CWnd* pWnd,
                           double Zoom,int UpdateMode)
{

   BOOL Created = FALSE;

   UnselectAll(pWnd,Zoom,UpdateMode);

   CTrk* pTrk = SetupNewObjTrk(ObjClassId);

   if (pTrk != NULL)
      Created = pTrk->TrackRubberBand(Point,Flags,pWnd,Zoom);

   if (Created)
   {

      CTrkObj* pObj = CreateObj(ObjClassId,pTrk,pWnd);

      if (pObj != NULL)
      {
         Add(pObj);
         Select(pObj,pWnd,Zoom,UpdateMode);
      }

   }

   delete pTrk;

   return(Created);

}

CTrk* CTrkObjList::SetupSelTrk() const
{

   CTrk* pTrk = new CTrk(CRect(0,0,0,0),CTrk::TS_Resize|CTrk::TS_Invert,
                         CTrk::DS_None);

   pTrk->SetExtRect(m_DefExtRect);

   return(pTrk);

}

void CTrkObjList::GetSelRect(LPRECT pRect,POINT Point,UINT Flags,
                             CWnd* pWnd,double Zoom) const
{

   CTrk* pTrk = SetupSelTrk();

   pTrk->TrackRubberBand(Point,Flags,pWnd,Zoom);

   pTrk->GetRect(pRect);

   delete pTrk;

}

void CTrkObjList::SetObjDefaults(CTrkObj* pObj) const
{
   pObj->SetExtRect(m_DefExtRect);
}

void CTrkObjList::SetMoveTrkDefaults(CMoveTrk* pTrk) const
{
   pTrk->SetExtRect(m_DefExtRect);
}

BOOL CTrkObjList::TrackSelObjs(POINT Point,UINT Flags,CWnd* pWnd,double Zoom,
                               int UpdateMode)
{

   BOOL Changed = FALSE;

   CMoveTrk MoveTrk;

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pObj = GetNextSel(Pos);
      CRect Rect;
      pObj->GetRect(Rect);
      MoveTrk.Add(Rect);
   }

   SetMoveTrkDefaults(&MoveTrk);

   CRect OldRect;
   MoveTrk.GetRect(OldRect);

   Changed = MoveTrk.TrackMove(Point,Flags,pWnd,Zoom);

   if (Changed)
   {
      CRect NewRect;
      MoveTrk.GetRect(NewRect);
      CSize Off;
      Off.cx = NewRect.left - OldRect.left;
      Off.cy = NewRect.top  - OldRect.top;
      OffsetSelObjs(Off,pWnd,Zoom,UpdateMode);
   }

   return(Changed);

}

void CTrkObjList::OffsetSelObjs(SIZE Off,CWnd* pWnd,double Zoom,
                                int UpdateMode)
{
   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pSelObj = GetNextSel(Pos);
      CRect NewPos;
      pSelObj->GetRect(NewPos);
      NewPos.left   = NewPos.left   + Off.cx;
      NewPos.top    = NewPos.top    + Off.cy;
      NewPos.right  = NewPos.right  + Off.cx;
      NewPos.bottom = NewPos.bottom + Off.cy;
      pSelObj->Move(NewPos,pWnd,Zoom,UpdateMode);
   }
}

CTrk* CTrkObjList::SetupNewObjTrk(LONG ObjClassId) const
{
   CTrk* pTrk = CreateNewObjTrk(ObjClassId);
   SetNewObjTrkDefaults(ObjClassId,pTrk);
   return(pTrk);
}

CTrk* CTrkObjList::CreateNewObjTrk(LONG /*ObjClassId*/) const
{
   CTrk* pTrk = new CTrk();
   return(pTrk);
}

void CTrkObjList::SetNewObjTrkDefaults(LONG /*ObjClassId*/,CTrk* pTrk) const
{
   pTrk->SetExtRect(m_DefExtRect);
}

CTrkObj* CTrkObjList::CreateObj(LONG ObjClassId,CTrk* pTrk,
                                CWnd* /*pWnd*/)
{
   CTrkObj* pObj = new CTrkObj(ObjClassId, pTrk);
   return(pObj);
}

void CTrkObjList::GetBoundRect(LPRECT pRect) const
{

   CRect ObjRect;
   CRect BoundRect;

   if ( GetCount() > 0 )
      BoundRect = CRect(INT_MAX,INT_MAX,INT_MIN,INT_MIN);
   else
      BoundRect = CRect(0,0,0,0);

   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {

      CTrkObj* pObj = GetNext(Pos);

      pObj->GetRect(ObjRect);

      if (BoundRect.left   > ObjRect.left)
         BoundRect.left   = ObjRect.left;
      if (BoundRect.top    > ObjRect.top)
         BoundRect.top    = ObjRect.top;
      if (BoundRect.right  < ObjRect.right)
         BoundRect.right  = ObjRect.right;
      if (BoundRect.bottom < ObjRect.bottom)
         BoundRect.bottom = ObjRect.bottom;

   }

   *pRect = BoundRect;

}

void CTrkObjList::GetBoundRectSel(LPRECT pRect) const
{

   CRect ObjRect;
   CRect BoundRect;

   if ( GetCountSel() > 0 )
      BoundRect = CRect(INT_MAX,INT_MAX,INT_MIN,INT_MIN);
   else
      BoundRect = CRect(0,0,0,0);

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {

      CTrkObj* pObj = GetNextSel(Pos);

      pObj->GetRect(ObjRect);

      if (BoundRect.left   > ObjRect.left)
         BoundRect.left   = ObjRect.left;
      if (BoundRect.top    > ObjRect.top)
         BoundRect.top    = ObjRect.top;
      if (BoundRect.right  < ObjRect.right)
         BoundRect.right  = ObjRect.right;
      if (BoundRect.bottom < ObjRect.bottom)
         BoundRect.bottom = ObjRect.bottom;

   }

   *pRect = BoundRect;

}

void CTrkObjList::GetBoundRectCB(LPRECT pRect) const
{

   CRect ObjRect;
   CRect BoundRect;

   if ( GetCountCB() > 0 )
      BoundRect = CRect(INT_MAX,INT_MAX,INT_MIN,INT_MIN);
   else
      BoundRect = CRect(0,0,0,0);

   POSITION Pos = GetHeadPosCB();
   while (Pos != NULL)
   {

      CTrkObj* pObj = GetNextCB(Pos);

      pObj->GetRect(ObjRect);

      if (BoundRect.left   > ObjRect.left)
         BoundRect.left   = ObjRect.left;
      if (BoundRect.top    > ObjRect.top)
         BoundRect.top    = ObjRect.top;
      if (BoundRect.right  < ObjRect.right)
         BoundRect.right  = ObjRect.right;
      if (BoundRect.bottom < ObjRect.bottom)
         BoundRect.bottom = ObjRect.bottom;

   }

   *pRect = BoundRect;

}

CPoint CTrkObjList::GetAlignVal(int AlignMode,CTrkObj* pObj) const
{

   CPoint Val;

   CRect Rect;
   pObj->GetRect(Rect);

   switch (AlignMode)
   {
      case ALIGN_LEFT:
      {
         Val.x = Rect.left;
         Val.y = 0;
         break;
      }
      case ALIGN_TOP:
      {
         Val.x = 0;
         Val.y = Rect.top;
         break;
      }
      case ALIGN_RIGHT:
      {
         Val.x = Rect.right;
         Val.y = 0;
         break;
      }
      case ALIGN_BOTTOM:
      {
         Val.x = 0;
         Val.y = Rect.bottom;
         break;
      }
      case ALIGN_HCENTER:
      {
         Val.x = GetMiddle(Rect.left,Rect.right);
         Val.y = 0;
         break;
      }
      case ALIGN_VCENTER:
      {
         Val.x = 0;
         Val.y = GetMiddle(Rect.top,Rect.bottom);
         break;
      }
      default:
      {
         Val.x = 0;
         Val.y = 0;
         break;
      }
   }

   return(Val);

}

CSize CTrkObjList::GetAlignOff(int AlignMode,CPoint AlignVal,
                               CTrkObj* pObj) const
{

   CSize Off;

   CPoint ObjVal = GetAlignVal(AlignMode,pObj);

   Off.cx = AlignVal.x - ObjVal.x;
   Off.cy = AlignVal.y - ObjVal.y;

   return(Off);

}

CRect CTrkObjList::GetAlignPos(CTrkObj* pObj,CSize Off) const
{

   SetObjDefaults(pObj);

   CRect Rect;
   pObj->GetRect(Rect);

   int W = Rect.right  - Rect.left;
   int H = Rect.bottom - Rect.top;

   Rect.left   = Rect.left   + Off.cx;
   Rect.top    = Rect.top    + Off.cy;
   Rect.right  = Rect.right  + Off.cx;
   Rect.bottom = Rect.bottom + Off.cy;

   if ( pObj->HasExtRect() )
   {

      CRect ExtRect;
      pObj->GetExtRect(ExtRect);

      if (Rect.left < ExtRect.left)
      {
         Rect.left  = ExtRect.left;
         Rect.right = ExtRect.left + W;
      }

      if (Rect.top < ExtRect.top)
      {
         Rect.top    = ExtRect.top;
         Rect.bottom = ExtRect.top + H;
      }

      if (Rect.right > ExtRect.right)
      {
         Rect.right = ExtRect.right;
         Rect.left  = ExtRect.right - W;
      }

      if (Rect.bottom > ExtRect.bottom)
      {
         Rect.bottom = ExtRect.bottom;
         Rect.top    = ExtRect.bottom - H;
      }

   }

   return(Rect);

}

CSize CTrkObjList::GetSameSizeVal(int SameSizeMode,CTrkObj* pObj) const
{

   CSize Val;

   CRect Rect;
   pObj->GetRect(Rect);

   switch (SameSizeMode)
   {
      case SAME_SIZE_WIDTH:
      {
         Val.cx = Rect.right - Rect.left;
         Val.cy = 0;
         break;
      }
      case SAME_SIZE_HEIGHT:
      {
         Val.cx = 0;
         Val.cy = Rect.bottom - Rect.top;
         break;
      }
      case SAME_SIZE_BOTH:
      {
         Val.cx = Rect.right  - Rect.left;
         Val.cy = Rect.bottom - Rect.top;
         break;
      }
      default:
      {
         Val.cx = 0;
         Val.cy = 0;
         break;
      }
   }

   return(Val);

}

CSize CTrkObjList::GetSameSizeOff(int SameSizeMode,CSize SameSizeVal,
                                  CTrkObj* pObj) const
{

   CSize Off;

   CSize ObjVal = GetSameSizeVal(SameSizeMode,pObj);

   Off.cx = SameSizeVal.cx - ObjVal.cx;
   Off.cy = SameSizeVal.cy - ObjVal.cy;

   return(Off);

}

CRect CTrkObjList::GetSameSizePos(CTrkObj* pObj,CSize Off) const
{

   SetObjDefaults(pObj);

   CRect Rect;
   pObj->GetRect(Rect);

   Rect.right  = Rect.right  + Off.cx;
   Rect.bottom = Rect.bottom + Off.cy;

   int W = Rect.right  - Rect.left;
   int H = Rect.bottom - Rect.top;

   if ( pObj->HasExtRect() )
   {

      CRect ExtRect;
      pObj->GetExtRect(ExtRect);

      if (Rect.right > ExtRect.right)
      {
         Rect.right = ExtRect.right;
         Rect.left  = ExtRect.right - W;
      }

      if (Rect.bottom > ExtRect.bottom)
      {
         Rect.bottom = ExtRect.bottom;
         Rect.top    = ExtRect.bottom - H;
      }

   }

   return(Rect);

}

CPoint CTrkObjList::GetCenterVal(int CenterMode,LPCRECT pRect) const
{

   CPoint Val;

   switch (CenterMode)
   {
      case CENTER_WIDTH:
      {
         Val.x = GetMiddle(pRect->left,pRect->right);
         Val.y = 0;
         break;
      }
      case CENTER_HEIGHT:
      {
         Val.x = 0;
         Val.y = GetMiddle(pRect->top,pRect->bottom);
         break;
      }
      case CENTER_BOTH:
      {
         Val.x = GetMiddle(pRect->left,pRect->right);
         Val.y = GetMiddle(pRect->top,pRect->bottom);
         break;
      }
      default:
      {
         Val.x = 0;
         Val.y = 0;
         break;
      }
   }

   return(Val);

}

CTrkObj* CTrkObjList::GetFirstInPos(int SameSpaceMode) const
{

   CTrkObj* pFirstObj = NULL;
   CTrkObj* pObj;
   CRect    Rect;
   int      FirstVal = INT_MAX,Val;

   POSITION Pos = GetHeadPosSel();
   if (Pos != NULL)
   {
      pObj = GetNextSel(Pos);
      pObj->GetRect(Rect);
      if (SameSpaceMode == SAME_SPACE_WIDTH)
         FirstVal = Rect.left;
      else
         FirstVal = Rect.top;
      pFirstObj = pObj;
   }

   while (Pos != NULL)
   {
      pObj = GetNextSel(Pos);
      pObj->GetRect(Rect);
      if (SameSpaceMode == SAME_SPACE_WIDTH)
         Val = Rect.left;
      else
         Val = Rect.top;
      if (Val < FirstVal)
      {
         FirstVal  = Val;
         pFirstObj = pObj;
      }
   }

   return(pFirstObj);

}

CTrkObj* CTrkObjList::GetLastInPos(int SameSpaceMode) const
{

   CTrkObj* pLastObj = NULL;
   CTrkObj* pObj;
   CRect    Rect;
   int      LastVal = INT_MIN,Val;

   POSITION Pos = GetHeadPosSel();
   if (Pos != NULL)
   {
      pObj = GetNextSel(Pos);
      pObj->GetRect(Rect);
      if (SameSpaceMode == SAME_SPACE_WIDTH)
         LastVal = Rect.left;
      else
         LastVal = Rect.top;
      pLastObj = pObj;
   }

   while (Pos != NULL)
   {
      pObj = GetNextSel(Pos);
      pObj->GetRect(Rect);
      if (SameSpaceMode == SAME_SPACE_WIDTH)
         Val = Rect.left;
      else
         Val = Rect.top;
      if (Val >= LastVal)
      {
         LastVal  = Val;
         pLastObj = pObj;
      }
   }

   return(pLastObj);

}

CTrkObj* CTrkObjList::GetNextInPos(int SameSpaceMode,CTrkObj* pRefObj) const
{

   CTrkObj* pNextObj = NULL;
   CTrkObj* pObj;
   CRect    Rect;
   int      RefVal,ActVal = INT_MAX,Val;
   BOOL     RefPassed = FALSE;

   pRefObj->GetRect(Rect);
   if (SameSpaceMode == SAME_SPACE_WIDTH)
      RefVal = Rect.left;
   else
      RefVal = Rect.top;

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      pObj = GetNextSel(Pos);
      pObj->GetRect(Rect);
      if (SameSpaceMode == SAME_SPACE_WIDTH)
         Val = Rect.left;
      else
         Val = Rect.top;
      if ( (Val >= RefVal) && (Val < ActVal) )
      {
         if (pObj == pRefObj)
            RefPassed = TRUE;
         else
         {
            if ( (Val > RefVal) || RefPassed )
            {
               pNextObj = pObj;
               pNextObj->GetRect(Rect);
               if (SameSpaceMode == SAME_SPACE_WIDTH)
                  ActVal = Rect.left;
               else
                  ActVal = Rect.top;
            }
         }
      }
   }

   return(pNextObj);

}

int CTrkObjList::GetTotalSpace(int SameSpaceMode,
                               CTrkObj* pFirstObj,CTrkObj* pLastObj) const
{

   int   Space;
   CRect FirstRect,LastRect;

   pFirstObj->GetRect(FirstRect);
   pLastObj->GetRect(LastRect);

   if (SameSpaceMode == SAME_SPACE_WIDTH)
      Space = LastRect.left - FirstRect.right  - 1;
   else
      Space = LastRect.top  - FirstRect.bottom - 1;

   return(Space);

}

int CTrkObjList::GetTakenSpace(int SameSpaceMode,
                               CTrkObj* pFirstObj,CTrkObj* pLastObj) const
{

   int      TakenSpace = 0,Space;
   CTrkObj* pObj;
   CRect    Rect;

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {

      pObj = GetNextSel(Pos);

      if ( (pObj != pFirstObj) && (pObj != pLastObj) )
      {
         pObj->GetRect(Rect);
         if (SameSpaceMode == SAME_SPACE_WIDTH)
            Space = Rect.right  - Rect.left + 1;
         else
            Space = Rect.bottom - Rect.top  + 1;
         TakenSpace = TakenSpace + Space;
      }

   }

   return(TakenSpace);

}

int CTrkObjList::GetInterSpace(int SameSpaceMode,
                               CTrkObj* pFirstObj,CTrkObj* pLastObj) const
{

   int InterSpace,TotalSpace,TakenSpace,Count;

   TotalSpace = GetTotalSpace(SameSpaceMode,pFirstObj,pLastObj);
   TakenSpace = GetTakenSpace(SameSpaceMode,pFirstObj,pLastObj);

   Count = GetCountSel() - 1;

   InterSpace = (TotalSpace - TakenSpace) / Count;

   return(InterSpace);

}

void CTrkObjList::SetInUse(LONG ObjClassId,BOOL InUse,CWnd* pWnd,double Zoom,
                             int UpdateMode)
{

   CTrkObj* pObj;
   POSITION Pos = GetHeadPos();
   while (Pos != NULL)
   {

      pObj = GetNext(Pos);
      if ( (pObj != NULL) && (pObj->GetObjClassId() == ObjClassId) )
      {
         if ( InUse )
         {
            pObj->SetInUse(TRUE);
            Unselect(pObj,pWnd,Zoom,UpdateMode);
         }
         else
            pObj->SetInUse(FALSE);
      }

   }

}

void CTrkObjList::ClearCB()
{
   RemoveAllCB();
}

void CTrkObjList::CopyToCB()
{

   ClearCB();

   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      CTrkObj* pObj   = GetNextSel(Pos);
      CTrkObj* pClone = pObj->SetupClone();
      AddCB(pClone);
   }

}

void CTrkObjList::CopyFromCB(CWnd* pWnd,double Zoom,int UpdateMode)
{

   UnselectAll(pWnd,Zoom,UpdateMode);

   CSize Off;
   GetCBOff(Off);

   POSITION Pos = GetHeadPosCB();
   while (Pos != NULL)
   {
      CTrkObj* pObj   = GetNextCB(Pos);
      CTrkObj* pClone = pObj->SetupClone();
      pClone->SetNormal();
      pClone->OffsetRect(Off);
      Add(pClone);
      Select(pClone,pWnd,Zoom,UpdateMode);
   }

}

void CTrkObjList::GetCBOff(CSize& Off) const
{

   Off = CSize(10,10);

   if ( HasDefExtRect() )
   {

      CRect CBRect;
      GetBoundRectCB(CBRect);

      CRect ExtRect;
      GetDefExtRect(ExtRect);

      if (CBRect.right + Off.cx > ExtRect.right)
      {
         if (CBRect.left - Off.cx < ExtRect.left)
            Off.cx = 0;
         else
            Off.cx = -Off.cx;
      }

      if (CBRect.bottom + Off.cy > ExtRect.bottom)
      {
         if (CBRect.top - Off.cy < ExtRect.top)
            Off.cy = 0;
         else
            Off.cy = -Off.cy;
      }

   }

}

