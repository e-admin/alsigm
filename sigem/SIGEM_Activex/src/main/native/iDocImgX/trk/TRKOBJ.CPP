
#include <afxwin.h>
#include <afxext.h>
#include "geom.h"
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

IMPLEMENT_SERIAL(CTrkObj,CObject,0)

CTrkObj::CTrkObj()
: m_Rect(0,0,0,0),m_ExtRect(0,0,0,0),m_IntRect(0,0,0,0),
  m_MinSize(0,0),m_DefSize(0,0)
{
   m_Style   = STL_None;
   m_State   = STT_Null;
   m_UpdateStyle = UPD_Invalidate;
   m_ExeMode = XM_Edit | XM_InUse;
   m_OCID    = -1;
}

CTrkObj::CTrkObj(LONG ObjClassId)
: m_Rect(0,0,0,0),m_ExtRect(0,0,0,0),m_IntRect(0,0,0,0),
  m_MinSize(0,0),m_DefSize(0,0),m_OCID(ObjClassId)
{
   m_Style   = STL_None;
   m_State   = STT_Null;
   m_UpdateStyle = UPD_Invalidate;
   m_ExeMode    = XM_Edit | XM_InUse;
}

CTrkObj::CTrkObj(LONG ObjClassId, LPCRECT pRect,int Style,int State,int UpdateStyle,
int ExeMode) : m_Rect(pRect),m_ExtRect(0,0,0,0),m_IntRect(0,0,0,0),
  m_MinSize(0,0),m_DefSize(0,0),m_OCID(ObjClassId)
{
   m_Style   = Style;
   m_State   = State;
   m_UpdateStyle = UpdateStyle;
   m_ExeMode    = ExeMode;
   m_Rect.NormalizeRect();
}

CTrkObj::CTrkObj(LONG ObjClassId, CTrk* pTrk) : m_OCID(ObjClassId)
{

   pTrk->GetRect(m_Rect);

   m_Style   = pTrk->GetTrackStyle();
   m_State   = STT_Normal;
   m_UpdateStyle = pTrk->GetUpdateStyle();
   m_ExeMode    = pTrk->GetExeMode();

   pTrk->GetExtRect(m_ExtRect);
   pTrk->GetIntRect(m_IntRect);

   pTrk->GetMinSize(&m_MinSize);
   pTrk->GetDefSize(&m_DefSize);

}

CTrkObj::~CTrkObj()
{
}

void CTrkObj::Serialize(CArchive& Arch)
{
   CObject::Serialize(Arch);
   if ( Arch.IsStoring() )
      Arch << m_Rect;
   else
      Arch >> m_Rect;
}

void CTrkObj::OnDraw(CDC* pDC,double Zoom) const
{

   if (IsInUse())
   {
      CTrk* pTrk = SetupTrk();

      if ( pTrk->NeedDraw(pDC,Zoom) )
      {

         CRect DevRect(m_Rect);
         pTrk->LogToDev(DevRect,pDC,Zoom);

         int DCStat = pDC->SaveDC();

         pDC->SetMapMode(MM_TEXT);
         pDC->SetViewportOrg(0,0);
         pDC->SetWindowOrg(0,0);

         DrawTrueObj(pDC,DevRect,Zoom);

         pDC->RestoreDC(DCStat);

      }

      pTrk->OnDraw(pDC,Zoom);

      delete pTrk;

   }

}

BOOL CTrkObj::OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
           double Zoom) const
{

   BOOL Set = FALSE;

   if ( IsSelected() )
   {
      CTrk* pTrk = SetupTrk();
      Set = pTrk->OnSetCursor(pWnd,HitTest,Msg,Zoom);
      delete pTrk;
   }

   return(Set);

}

BOOL CTrkObj::OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
             double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;

   if ( IsSelected() )
   {

      CTrk* pTrk = SetupTrk();

      Changed = pTrk->Track(Point,Flags,pWnd,Zoom,UPDATE_NULL);

      if (Changed)
      {
    CRect PosRect;
    pTrk->GetRect(PosRect);
    Move(PosRect,pWnd,Zoom,UpdateMode);
    SetObjDefaults(pTrk);
      }

      delete pTrk;

   }

   return(Changed);

}

void CTrkObj::OnLButtonDblClk(POINT /*Point*/,UINT /*Flags*/,
               CWnd* /*pWnd*/,double /*Zoom*/)
{
}

void CTrkObj::OnRButtonDown(POINT /*Point*/,UINT /*Flags*/,
             CWnd* /*pWnd*/,double /*Zoom*/)
{
}

void CTrkObj::GetRect(LPRECT pRect) const
{
   *pRect = m_Rect;
}

int CTrkObj::GetStyle() const
{
   return(m_Style);
}

int CTrkObj::GetState() const
{
   return(m_State);
}

int CTrkObj::GetUpdateStyle() const
{
   return(m_UpdateStyle);
}

int CTrkObj::GetExeMode() const
{
   return(m_ExeMode);
}

void CTrkObj::GetExtRect(LPRECT pRect) const
{
   *pRect = m_ExtRect;
}

void CTrkObj::GetIntRect(LPRECT pRect) const
{
   *pRect = m_IntRect;
}

void CTrkObj::GetMinSize(LPSIZE pSize) const
{
   *pSize = m_MinSize;
}

void CTrkObj::GetDefSize(LPSIZE pSize) const
{
   *pSize = m_DefSize;
}

LONG CTrkObj::GetObjClassId() const
{
   return(m_OCID);
}

void CTrkObj::SetRect(LPCRECT pRect)
{
   m_Rect.CopyRect(pRect);
   m_Rect.NormalizeRect();
}

void CTrkObj::SetStyle(int Style)
{
   m_Style = Style;
}

void CTrkObj::SetState(int State)
{
   m_State = State;
}

void CTrkObj::SetUpdateStyle(int Style)
{
   m_UpdateStyle = Style;
}

void CTrkObj::SetExeMode(int Mode)
{
   m_ExeMode = Mode;
}

void CTrkObj::SetExtRect(LPCRECT pRect)
{
   m_ExtRect.CopyRect(pRect);
   m_ExtRect.NormalizeRect();
}

void CTrkObj::SetIntRect(LPCRECT pRect)
{
   m_IntRect.CopyRect(pRect);
   m_IntRect.NormalizeRect();
}

void CTrkObj::SetMinSize(SIZE Size)
{
   m_MinSize = Size;
}

void CTrkObj::SetDefSize(SIZE Size)
{
   m_DefSize = Size;
}

BOOL CTrkObj::CanMove() const
{
   if (m_Style & STL_Move)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::CanResize() const
{
   if (m_Style & STL_Resize)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::CanInvert() const
{
   if (m_Style & STL_Invert)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::IsNormal() const
{
   if (m_State & STT_Normal)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::IsSelected() const
{
   if (m_State & STT_Selected)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::IsInUse() const
{
   if (m_ExeMode & XM_InUse)
      return(TRUE);
   else
      return(FALSE);
}

void CTrkObj::SetInUse(BOOL InUse)
{
   if ( InUse )
   {
      if ( !IsInUse() )
         m_ExeMode = m_ExeMode | XM_InUse;
   }
   else
   {
      if ( IsInUse() )
         m_ExeMode = m_ExeMode ^ XM_InUse;
   }
}

BOOL CTrkObj::UpdateRepainting() const
{
   if (m_UpdateStyle == UPD_Repaint)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::UpdateInvalidating() const
{
   if (m_UpdateStyle == UPD_Invalidate)
      return(TRUE);
   else
      return(FALSE);

}

BOOL CTrkObj::HasExtRect() const
{
   if ( !m_ExtRect.IsRectNull() )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::HasIntRect() const
{
   if ( !m_IntRect.IsRectNull() )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::HasMinSize() const
{
   if ( !IsSizeNull(m_MinSize) )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrkObj::HasDefSize() const
{
   if ( !IsSizeNull(m_DefSize) )
      return(TRUE);
   else
      return(FALSE);
}

void CTrkObj::EnableMove(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanMove() )
    m_Style = m_Style | STL_Move;
   }
   else
   {
      if ( CanMove() )
    m_Style = m_Style ^ STL_Move;
   }
}

void CTrkObj::EnableResize(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanResize() )
    m_Style = m_Style | STL_Resize;
   }
   else
   {
      if ( CanResize() )
    m_Style = m_Style ^ STL_Resize;
   }
}

void CTrkObj::EnableInvert(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanInvert() )
    m_Style = m_Style | STL_Invert;
   }
   else
   {
      if ( CanInvert() )
    m_Style = m_Style ^ STL_Invert;
   }
}

void CTrkObj::SetNormal()
{
   if ( !IsNormal() )
      m_State = m_State | STT_Normal;
   if ( IsSelected() )
      m_State = m_State ^ STT_Selected;
}

void CTrkObj::SetSelected()
{
   if ( !IsSelected() )
      m_State = m_State | STT_Selected;
   if ( IsNormal() )
      m_State = m_State ^ STT_Normal;
}

BOOL CTrkObj::IsHit(POINT Point,CWnd* pWnd,int& HitCode,double Zoom) const
{

   BOOL IsHit = FALSE;

   if (IsInUse())
   {

      CTrk* pTrk = SetupTrk();

      HitCode = pTrk->GetHitCode(Point,pWnd,Zoom);

      if (HitCode != CTrk::HC_Outside)
         IsHit = TRUE;

      delete pTrk;

   }

   return(IsHit);

}

void CTrkObj::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() )
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}

void CTrkObj::Unselect(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( IsSelected() )
   {
      Update(UpdateMode,pWnd,Zoom);
      SetNormal();
      Update(UpdateMode,pWnd,Zoom);
   }

}

void CTrkObj::Move(LPCRECT pPosRect,CWnd* pWnd,double Zoom,int UpdateMode)
{
   Update(UpdateMode,pWnd,Zoom);
   m_Rect = *pPosRect;
   Update(UpdateMode,pWnd,Zoom);
   OnPosChanged(pWnd);
}

BOOL CTrkObj::Intersect(LPCRECT pRect) const
{
   CRect AuxRect;
   if ( InterxRect(m_Rect,pRect,AuxRect) )
      return(TRUE);
   else
      return(FALSE);
}

CTrkObj* CTrkObj::SetupClone() const
{
   CTrkObj* pTrkObj = CreateClone();
   SetCloneDefaults(pTrkObj);
   return(pTrkObj);
}

CTrkObj* CTrkObj::CreateClone() const
{
   CTrkObj* pTrkObj = new CTrkObj(m_OCID);
   return(pTrkObj);
}

void CTrkObj::SetCloneDefaults(CTrkObj* pTrkObj) const
{
   pTrkObj->m_Rect        = m_Rect;
   pTrkObj->m_Style       = m_Style;
   pTrkObj->m_State       = m_State;
   pTrkObj->m_UpdateStyle = m_UpdateStyle;
   pTrkObj->m_ExeMode     = m_ExeMode;
   pTrkObj->m_ExtRect     = m_ExtRect;
   pTrkObj->m_IntRect     = m_IntRect;
   pTrkObj->m_MinSize     = m_MinSize;
   pTrkObj->m_DefSize     = m_DefSize;
}

CTrk* CTrkObj::SetupTrk() const
{
   CTrk* pTrk = CreateTrk();
   SetTrkDefaults(pTrk);
   return(pTrk);
}

CTrk* CTrkObj::CreateTrk() const
{
   CTrk* pTrk = new CTrk(m_Rect,CTrk::TS_None,CTrk::DS_None,
          CTrk::US_Invalidate);
   return(pTrk);
}

void CTrkObj::SetTrkDefaults(CTrk* pTrk) const
{

   if ( CanMove() )   pTrk->EnableMove(TRUE);
   if ( CanResize() ) pTrk->EnableResize(TRUE);
   if ( CanInvert() ) pTrk->EnableInvert(TRUE);

   if ( IsSelected() )
   {
      pTrk->EnableBorder(TRUE);
      if ( CanResize() )
    pTrk->EnableResizeHandles(TRUE);
   }

   pTrk->SetUpdateStyle(m_UpdateStyle);
   pTrk->SetExeMode(m_ExeMode);

   pTrk->SetExtRect(m_ExtRect);
   pTrk->SetIntRect(m_IntRect);
   pTrk->SetMinSize(m_MinSize);
   pTrk->SetDefSize(m_DefSize);

}

CTrk* CTrkObj::SetupTrkNew() const
{
   CTrk* pTrk = CreateTrkNew();
   SetTrkNewDefaults(pTrk);
   return(pTrk);
}

CTrk* CTrkObj::CreateTrkNew() const
{
   CTrk* pTrk = new CTrk(m_Rect,CTrk::TS_None,CTrk::DS_None,
          CTrk::US_Invalidate);
   return(pTrk);
}

void CTrkObj::SetTrkNewDefaults(CTrk* pTrk) const
{

   if ( CanMove() )   pTrk->EnableMove(TRUE);
   if ( CanResize() ) pTrk->EnableResize(TRUE);
   if ( CanInvert() ) pTrk->EnableInvert(TRUE);

   if ( IsSelected() )
   {
      pTrk->EnableBorder(TRUE);
      if ( CanResize() )
    pTrk->EnableResizeHandles(TRUE);
   }

   pTrk->SetUpdateStyle(m_UpdateStyle);
   pTrk->SetExeMode(m_ExeMode);

   pTrk->SetExtRect(m_ExtRect);
   pTrk->SetIntRect(m_IntRect);
   pTrk->SetMinSize(m_MinSize);
   pTrk->SetDefSize(m_DefSize);

}

void CTrkObj::SetObjDefaults(CTrk* /*pTrk*/)
{
}

void CTrkObj::Update(int UpdateMode,CWnd* pWnd,double Zoom) const
{
   if ( !m_Rect.IsRectNull() && (UpdateMode != UPDATE_NULL) )
      Invalidate(pWnd,Zoom);
}

void CTrkObj::Invalidate(CWnd* pWnd,double Zoom) const
{
   CTrk* pTrk = SetupTrk();
   pTrk->Invalidate(pWnd,Zoom);
   delete pTrk;
}

void CTrkObj::DrawTrueObj(CDC* /*pDC*/,LPCRECT /*pDevRect*/,
           double /*Zoom*/) const
{
}

void CTrkObj::OnPosChanged(CWnd* /*pWnd*/)
{
}

void CTrkObj::OffsetRect(const CSize& Off)
{
   m_Rect.left   = m_Rect.left   + Off.cx;
   m_Rect.top    = m_Rect.top    + Off.cy;
   m_Rect.right  = m_Rect.right  + Off.cx;
   m_Rect.bottom = m_Rect.bottom + Off.cy;
}

