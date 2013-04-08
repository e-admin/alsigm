
#include <afxwin.h>
#include <afxext.h>
#include <limits.h>
#include "mapmode.h"
#include "geom.h"
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

IMPLEMENT_SERIAL(CTrk,CObject,0)

CTrk::CTrk()
: m_Rect(0,0,0,0),m_ExtRect(0,0,0,0),m_IntRect(0,0,0,0),
  m_MinSize(0,0),m_DefSize(0,0)
{
   m_TrackStyle  = TS_None;
   m_DrawStyle   = DS_None;
   m_UpdateStyle = US_Invalidate;
   m_ExeMode     = XM_Edit;
}

CTrk::CTrk(LPCRECT pRect,int TrackStyle,int DrawStyle,int UpdateStyle,int ExeMode)
: m_Rect(pRect),m_ExtRect(0,0,0,0),m_IntRect(0,0,0,0),
  m_MinSize(0,0),m_DefSize(0,0)
{
   m_TrackStyle  = TrackStyle;
   m_DrawStyle   = DrawStyle;
   m_UpdateStyle = UpdateStyle;
   m_ExeMode     = ExeMode;
   m_Rect.NormalizeRect();
}

CTrk::~CTrk()
{
}

void CTrk::Serialize(CArchive& Arch)
{
   CObject::Serialize(Arch);
   if ( Arch.IsStoring() )
      Arch << m_Rect;
   else
      Arch >> m_Rect;
}

void CTrk::OnDraw(CDC* pDC,double Zoom) const
{

   if ( NeedDraw(pDC,Zoom) )
   {

      CRect DevRect(m_Rect);
      LogToDev(DevRect,pDC,Zoom);

      int DCStat = pDC->SaveDC();

      pDC->SetMapMode(MM_TEXT);
      pDC->SetViewportOrg(0,0);
      pDC->SetWindowOrg(0,0);

      Draw(pDC,DevRect,Zoom);

      pDC->RestoreDC(DCStat);

   }

}

BOOL CTrk::OnSetCursor(CWnd* pWnd,UINT HitTest,UINT /*Msg*/,
                       double Zoom) const
{

   BOOL   Set = FALSE;
   int    HC;
   CPoint Point;

   if (HitTest != HTCLIENT)
      goto End;

   if ( m_Rect.IsRectNull() )
      goto End;

   ::GetCursorPos(&Point);
   pWnd->ScreenToClient(&Point);

   HC = GetHitCode(Point,pWnd,Zoom);

   if (HC == HC_Outside)
      goto End;

   if ( !CanResize() )
      HC = HC_Middle;

   Set = TRUE;

   switch (HC)
   {
      case HC_LeftTop:
      case HC_RightBottom:
      {
         ::SetCursor(::LoadCursor(NULL,IDC_SIZENWSE));
         break;
      }
      case HC_Top:
      case HC_Bottom:
      {
         ::SetCursor(::LoadCursor(NULL,IDC_SIZENS));
         break;
      }
      case HC_RightTop:
      case HC_LeftBottom:
      {
         ::SetCursor(::LoadCursor(NULL,IDC_SIZENESW));
         break;
      }
      case HC_Right:
      case HC_Left:
      {
         ::SetCursor(::LoadCursor(NULL,IDC_SIZEWE));
         break;
      }
      default:
      case HC_Middle:
      {
         if ( CanMove() )
            ::SetCursor(::LoadCursor(NULL,IDC_SIZEALL));
         else
            Set = FALSE;
         break;
      }
   }

   End:

   return(Set);

}

BOOL CTrk::Track(POINT Point,UINT /*Flags*/,CWnd* pWnd,
                 double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;

   int HC = GetHitCode(Point,pWnd,Zoom);

   if (HC == HC_Outside)
      Changed = FALSE;
   else
   {

      if ( !CanResize() )
         HC = HC_Middle;

      CRect OldRect(m_Rect);

      Changed = TrackHandle(FALSE,HC,Point,pWnd,Zoom);

      if (Changed && (UpdateMode != UPDATE_NULL))
      {
         CRect NewRect(m_Rect);
         m_Rect = OldRect;
         Update(UpdateMode,pWnd,Zoom);
         m_Rect = NewRect;
         Update(UpdateMode,pWnd,Zoom);
      }

   }

   return(Changed);

}

BOOL CTrk::TrackRubberBand(POINT Point,UINT /*Flags*/,CWnd* pWnd,double Zoom)
{

   BOOL Changed = FALSE;

   if ( HasExtRect() )
   {
      CRect ExtRect(m_ExtRect);
      LogToDev(ExtRect,pWnd,Zoom);
      if ( !ExtRect.PtInRect(Point) )
         goto End;
   }

   m_Rect.left   = Point.x;
   m_Rect.top    = Point.y;
   m_Rect.right  = Point.x;
   m_Rect.bottom = Point.y;

   DevToLog(m_Rect,pWnd,Zoom);

   Changed = TrackHandle(TRUE,HC_RightBottom,Point,pWnd,Zoom);

   End:

   return(Changed);

}

void CTrk::GetRect(LPRECT pRect) const
{
   *pRect = m_Rect;
}

int CTrk::GetTrackStyle() const
{
   return(m_TrackStyle);
}

int CTrk::GetDrawStyle() const
{
   return(m_DrawStyle);
}

int CTrk::GetUpdateStyle() const
{
   return(m_UpdateStyle);
}

int CTrk::GetExeMode() const
{
   return(m_ExeMode);
}

void CTrk::GetExtRect(LPRECT pRect) const
{
   *pRect = m_ExtRect;
}

void CTrk::GetIntRect(LPRECT pRect) const
{
   *pRect = m_IntRect;
}

void CTrk::GetMinSize(LPSIZE pSize) const
{
   *pSize = m_MinSize;
}

void CTrk::GetDefSize(LPSIZE pSize) const
{
   *pSize = m_DefSize;
}

void CTrk::SetRect(LPCRECT pRect)
{
   m_Rect.CopyRect(pRect);
   m_Rect.NormalizeRect();
}

void CTrk::SetTrackStyle(int Style)
{
   m_TrackStyle = Style;
}

void CTrk::SetDrawStyle(int Style)
{
   m_DrawStyle = Style;
}

void CTrk::SetUpdateStyle(int Style)
{
   m_UpdateStyle = Style;
}

void CTrk::SetExeMode(int Mode)
{
   m_ExeMode = Mode;
}

void CTrk::SetExtRect(LPCRECT pRect)
{
   m_ExtRect.CopyRect(pRect);
   m_ExtRect.NormalizeRect();
}

void CTrk::SetIntRect(LPCRECT pRect)
{
   m_IntRect.CopyRect(pRect);
   m_IntRect.NormalizeRect();
}

void CTrk::SetMinSize(SIZE Size)
{
   m_MinSize = Size;
}

void CTrk::SetDefSize(SIZE Size)
{
   m_DefSize = Size;
}

BOOL CTrk::CanMove() const
{
   if (m_TrackStyle & TS_Move)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::CanResize() const
{
   if (m_TrackStyle & TS_Resize)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::CanInvert() const
{
   if (m_TrackStyle & TS_Invert)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasLine() const
{
   if (m_DrawStyle & DS_Line)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasBorder() const
{
   if (m_DrawStyle & DS_Border)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasResizeHandles() const
{
   if (m_DrawStyle & DS_ResizeHandles)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::UpdateRepainting() const
{
   if (m_UpdateStyle == US_Repaint)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::UpdateInvalidating() const
{
   if (m_UpdateStyle == US_Invalidate)
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasExtRect() const
{
   if ( !m_ExtRect.IsRectNull() )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasIntRect() const
{
   if ( !m_IntRect.IsRectNull() )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasMinSize() const
{
   if ( !IsSizeNull(m_MinSize) )
      return(TRUE);
   else
      return(FALSE);
}

BOOL CTrk::HasDefSize() const
{
   if ( !IsSizeNull(m_DefSize) )
      return(TRUE);
   else
      return(FALSE);
}

void CTrk::EnableMove(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanMove() )
         m_TrackStyle = m_TrackStyle | TS_Move;
   }
   else
   {
      if ( CanMove() )
         m_TrackStyle = m_TrackStyle ^ TS_Move;
   }
}

void CTrk::EnableResize(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanResize() )
         m_TrackStyle = m_TrackStyle | TS_Resize;
   }
   else
   {
      if ( CanResize() )
         m_TrackStyle = m_TrackStyle ^ TS_Resize;
   }
}

void CTrk::EnableInvert(BOOL Enable)
{
   if (Enable)
   {
      if ( !CanInvert() )
         m_TrackStyle = m_TrackStyle | TS_Invert;
   }
   else
   {
      if ( CanInvert() )
         m_TrackStyle = m_TrackStyle ^ TS_Invert;
   }
}

void CTrk::EnableLine(BOOL Enable)
{
   if (Enable)
   {
      if ( !HasLine() )
         m_DrawStyle = m_DrawStyle | DS_Line;
   }
   else
   {
      if ( HasLine() )
         m_DrawStyle = m_DrawStyle ^ DS_Line;
   }
}

void CTrk::EnableBorder(BOOL Enable)
{
   if (Enable)
   {
      if ( !HasBorder() )
         m_DrawStyle = m_DrawStyle | DS_Border;
   }
   else
   {
      if ( HasBorder() )
         m_DrawStyle = m_DrawStyle ^ DS_Border;
   }
}

void CTrk::EnableResizeHandles(BOOL Enable)
{
   if (Enable)
   {
      if ( !HasResizeHandles() )
         m_DrawStyle = m_DrawStyle | DS_ResizeHandles;
   }
   else
   {
      if ( HasResizeHandles() )
         m_DrawStyle = m_DrawStyle ^ DS_ResizeHandles;
   }
}

int CTrk::GetHitCode(POINT Point,CWnd* pWnd,double Zoom) const
{

   int HC = HC_Outside;

   CRect DevRect(m_Rect);
   LogToDev(DevRect,pWnd,Zoom);

   CRect IntRect(DevRect);
   int IntOff = GetMinIntHitOff();
   ::InflRect(IntRect,-IntOff,IntRect);

   int ResizeHandleSize = GetResizeHandleSize();

   int Off = GetMinExtHitOff();
   if ( HasBorder() || HasResizeHandles() )
   {
      if (Off < ResizeHandleSize)
         Off = ResizeHandleSize;
   }

   CRect ExtRect;
   ::InflRect(DevRect,Off,ExtRect);

   if ( !::PointInRect(ExtRect,Point) )
      goto End;

   if ( ::PointInRect(IntRect,Point) )
   {
      HC = HC_Middle;
      goto End;
   }

   if (Point.x < IntRect.left)
   {
      if (Point.y < IntRect.top)
         HC = HC_LeftTop;
      else
      {
         if (Point.y > IntRect.bottom)
            HC = HC_LeftBottom;
         else
         {
            int YMiddle = ::GetMiddle(DevRect.bottom,DevRect.top);
            if ( (Point.y >= YMiddle - Off/2) && (Point.y <= YMiddle + Off/2) )
               HC = HC_Left;
         }
      }
   }
   else
   {
      if (Point.x > IntRect.right)
      {
         if (Point.y < IntRect.top)
            HC = HC_RightTop;
         else
         {
            if (Point.y > IntRect.bottom)
               HC = HC_RightBottom;
            else
            {
               int YMiddle = ::GetMiddle(DevRect.bottom,DevRect.top);
               if ( (Point.y >= YMiddle - Off/2) && (Point.y <= YMiddle + Off/2) )
                  HC = HC_Right;
            }
         }
      }
      else
      {
         int XMiddle = ::GetMiddle(DevRect.right,DevRect.left);
         if ( (Point.x >= XMiddle - Off/2) && (Point.x <= XMiddle + Off/2) )
         {
            if (Point.y < IntRect.top)
               HC = HC_Top;
            else
               HC = HC_Bottom;
         }
      }
   }

   if (HC == HC_Outside)
      HC = HC_Middle;

   End:

   return(HC);

}

BOOL CTrk::NeedDraw(CDC* pDC,double Zoom) const
{

   BOOL NeedDraw = FALSE;

   if ( !m_Rect.IsRectNull() )
   {

      CRect DCRect;
      pDC->GetClipBox(DCRect);

      CRect FrmRect;
      GetTotalFrmRect(FrmRect,pDC,Zoom);

      if ( DCRect.IntersectRect(DCRect,FrmRect) )
         NeedDraw = TRUE;

   }

   return(NeedDraw);

}

void CTrk::GetTotalFrmRect(LPRECT pRect,CDC* pDC,double Zoom) const
{

   int DCStat = pDC->SaveDC();

   pDC->SetMapMode(MM_TEXT);
   pDC->SetViewportOrg(0,0);
   pDC->SetWindowOrg(0,0);

   GetVisibleFrmRect(pRect,pDC,Zoom);

   pDC->RestoreDC(DCStat);

}

void CTrk::GetTotalFrmRect(LPRECT pRect,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   GetTotalFrmRect(pRect,&DC,Zoom);

}

void CTrk::GetVisibleFrmRect(LPRECT pRect,CDC* pDC,double Zoom) const
{

   *pRect = m_Rect;
   LogToDev(pRect,pDC,Zoom);

   int Off = GetFrameSize();
   ::InflRect(pRect,Off,pRect);

   (pRect->right)++;
   (pRect->bottom)++;

}

void CTrk::GetVisibleFrmRect(LPRECT pRect,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   GetVisibleFrmRect(pRect,&DC,Zoom);

}

void CTrk::LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   ::LogToDev(pPoint,pDC,Zoom);
}

void CTrk::LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pPoint,&DC,Zoom);

}

void CTrk::LogToDev(LPSIZE pSize,CDC* pDC,double Zoom) const
{
   ::LogToDev(pSize,pDC,Zoom);
}

void CTrk::LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pSize,&DC,Zoom);

}

void CTrk::LogToDev(LPRECT pRect,CDC* pDC,double Zoom) const
{
   ::LogToDev(pRect,pDC,Zoom);
}

void CTrk::LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pRect,&DC,Zoom);

}

void CTrk::DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   ::DevToLog(pPoint,pDC,Zoom);
}

void CTrk::DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pPoint,&DC,Zoom);

}

void CTrk::DevToLog(LPSIZE pSize,CDC* pDC,double Zoom) const
{
   ::DevToLog(pSize,pDC,Zoom);
}

void CTrk::DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pSize,&DC,Zoom);

}

void CTrk::DevToLog(LPRECT pRect,CDC* pDC,double Zoom) const
{
   ::DevToLog(pRect,pDC,Zoom);
}

void CTrk::DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom) const
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pRect,&DC,Zoom);

}

int CTrk::GetFrameSize() const
{

   int Size = 0;

   if ( HasBorder() || HasResizeHandles() )
      Size = GetResizeHandleSize();

   if ( HasLine() )
      Size++;

   return(Size);

}

int CTrk::GetResizeHandleSize() const
{
   return(4);
}

int CTrk::GetMinExtHitOff() const
{
   return(3);
}

int CTrk::GetMinIntHitOff() const
{
   return(2);
}

void CTrk::Draw(CDC* pDC,LPCRECT pDevRect,double /*Zoom*/) const
{

   if ( HasLine() )
      DrawLine(pDC,pDevRect);

   if ( HasBorder() )
      DrawBorder(pDC,pDevRect);

   if ( HasResizeHandles() )
      DrawResizeHandles(pDC,pDevRect);

}

void CTrk::DrawLine(CDC* pDC,LPCRECT pDevRect) const
{

   int OldROP;
   if ( UpdateInvalidating() )
      OldROP = pDC->SetROP2(R2_COPYPEN);
   else
      OldROP = pDC->SetROP2(R2_XORPEN);

   CPen    Pen(PS_DOT,1,RGB(0,0,0));
   CPen*   pOldPen   = (CPen*)pDC->SelectObject(&Pen);
   CBrush* pOldBrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH);

   CRect LineRect(pDevRect);
   LineRect.right  = LineRect.right  + 1;
   LineRect.bottom = LineRect.bottom + 1;

   DrawTrueLine(pDC,LineRect);

   pDC->SelectObject(pOldPen);
   pDC->SelectObject(pOldBrush);
   pDC->SetROP2(OldROP);

}

void CTrk::DrawBorder(CDC* pDC,LPCRECT pDevRect) const
{

   CPen*   pOldPen;
   CBrush* pOldBrush;
   int     OldROP;

   pOldPen = (CPen*)pDC->SelectStockObject(NULL_PEN);

   CBrush Brush(RGB(128,128,128));
   pOldBrush = (CBrush*)pDC->SelectObject(&Brush);

   if ( UpdateInvalidating() )
      OldROP = pDC->SetROP2(R2_COPYPEN);
   else
      OldROP = pDC->SetROP2(R2_XORPEN);

   DrawTrueBorder(pDC,pDevRect);

   pDC->SelectObject(pOldPen);
   pDC->SelectObject(pOldBrush);
   pDC->SetROP2(OldROP);

}

void CTrk::DrawResizeHandles(CDC* pDC,LPCRECT pDevRect) const
{

   CPen* pOldPen = (CPen*)pDC->SelectStockObject(NULL_PEN);

   int OldROP;
   COLORREF ColorRef;
   if ( UpdateInvalidating() )
   {
      ColorRef = RGB(0,0,0);
      OldROP = pDC->SetROP2(R2_COPYPEN);
   }
   else
   {
      ColorRef = RGB(255,255,255);
      OldROP = pDC->SetROP2(R2_XORPEN);
   }

   CBrush Brush(ColorRef);
   CBrush* pOldBrush = (CBrush*)pDC->SelectObject(&Brush);

   DrawTrueResizeHandles(pDC,pDevRect);

   CBrush Brush2(RGB(128,128,128));
   if ( UpdateRepainting() && HasBorder() )
   {
      pDC->SelectObject(&Brush2);
      DrawTrueResizeHandles(pDC,pDevRect);
   }

   pDC->SelectObject(pOldPen);
   pDC->SelectObject(pOldBrush);
   pDC->SetROP2(OldROP);

}

void CTrk::DrawTrueLine(CDC* pDC,LPCRECT pDevRect) const
{
   pDC->Rectangle(pDevRect);
}

void CTrk::DrawTrueBorder(CDC* pDC,LPCRECT pDevRect) const
{

   CRect IntRect;
   IntRect.left   = pDevRect->left;
   IntRect.top    = pDevRect->top;
   IntRect.right  = pDevRect->right  + 1;
   IntRect.bottom = pDevRect->bottom + 1;
   if ( HasLine() )
      ::InflRect(IntRect,1,IntRect);

   int ResizeHandleSize = GetResizeHandleSize();

   CRect ExtRect(IntRect);
   ::InflRect(ExtRect,ResizeHandleSize-1,ExtRect);

   CRect LineRect;

   LineRect.left   = ExtRect.left;
   LineRect.top    = ExtRect.top;
   LineRect.right  = ExtRect.right + 1;
   LineRect.bottom = IntRect.top  + 2;
   pDC->Rectangle(LineRect);

   LineRect.left   = IntRect.right - 1;
   LineRect.top    = ExtRect.top + ResizeHandleSize;
   LineRect.right  = ExtRect.right + 1;
   LineRect.bottom = ExtRect.bottom - ResizeHandleSize + 1;
   pDC->Rectangle(LineRect);

   LineRect.left   = ExtRect.left;
   LineRect.top    = IntRect.bottom - 1;
   LineRect.right  = ExtRect.right + 1;
   LineRect.bottom = ExtRect.bottom + 1;
   pDC->Rectangle(LineRect);

   LineRect.left   = ExtRect.left;
   LineRect.top    = ExtRect.top + ResizeHandleSize;
   LineRect.right  = IntRect.left + 2;
   LineRect.bottom = ExtRect.bottom - ResizeHandleSize + 1;
   pDC->Rectangle(LineRect);

}

void CTrk::DrawTrueResizeHandles(CDC* pDC,LPCRECT pDevRect) const
{

   CRect IntRect;
   IntRect.left   = pDevRect->left;
   IntRect.top    = pDevRect->top;
   IntRect.right  = pDevRect->right  + 1;
   IntRect.bottom = pDevRect->bottom + 1;
   if ( HasLine() )
      ::InflRect(IntRect,1,IntRect);

   int ResizeHandleSize = GetResizeHandleSize();

   CRect ExtRect(IntRect);
   ::InflRect(ExtRect,ResizeHandleSize,ExtRect);

   int XMiddle = GetMiddle(pDevRect->right,pDevRect->left);
   int YMiddle = GetMiddle(pDevRect->bottom,pDevRect->top);

   int   Handle;
   CRect SingleRect;

   Handle = HC_LeftTop;
   SingleRect.left   = ExtRect.left + 1;
   SingleRect.top    = ExtRect.top  + 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_Top;
   SingleRect.left   = XMiddle - (ResizeHandleSize / 2);
   SingleRect.top    = ExtRect.top + 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   if (pDevRect->right - pDevRect->left + 1 > 2 * ResizeHandleSize)
      DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_RightTop;
   SingleRect.left   = IntRect.right - 1;
   SingleRect.top    = ExtRect.top   + 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_Right;
   SingleRect.left   = IntRect.right - 1;
   SingleRect.top    = YMiddle - (ResizeHandleSize / 2);
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   if (pDevRect->bottom - pDevRect->top + 1 > 2 * ResizeHandleSize)
      DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_RightBottom;
   SingleRect.left   = IntRect.right  - 1;
   SingleRect.top    = IntRect.bottom - 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_Bottom;
   SingleRect.left   = XMiddle - (ResizeHandleSize / 2);
   SingleRect.top    = IntRect.bottom - 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   if (pDevRect->right - pDevRect->left + 1 > 2 * ResizeHandleSize)
      DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_LeftBottom;
   SingleRect.left   = ExtRect.left   + 1;
   SingleRect.top    = IntRect.bottom - 1;
   SingleRect.right  = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   DrawTrueResizeHandle(Handle,pDC,SingleRect);

   Handle = HC_Left;
   SingleRect.left = ExtRect.left + 1;
   SingleRect.top = YMiddle - (ResizeHandleSize / 2);
   SingleRect.right = SingleRect.left + ResizeHandleSize + 1;
   SingleRect.bottom = SingleRect.top + ResizeHandleSize + 1;
   if (pDevRect->bottom - pDevRect->top + 1 > 2 * ResizeHandleSize)
      DrawTrueResizeHandle(Handle,pDC,SingleRect);

}

void CTrk::DrawTrueResizeHandle(int /*Handle*/,CDC* pDC,LPCRECT pDevRect) const
{
   pDC->Rectangle(pDevRect);
}

void CTrk::Update(int UpdateMode,CWnd* pWnd,double Zoom) const
{
   if ( !m_Rect.IsRectNull() && (UpdateMode != UPDATE_NULL) )
      Invalidate(pWnd,Zoom);
}

void CTrk::Invalidate(CWnd* pWnd,double Zoom) const
{

   if ( !m_Rect.IsRectNull() )
   {

      if ( UpdateInvalidating() )
      {

         CRect InvalidRect;
         GetVisibleFrmRect(InvalidRect,pWnd,Zoom);

         pWnd->InvalidateRect(InvalidRect);

      }
      else
      {

         CClientDC DC(pWnd);
         if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
            ((CView*)pWnd)->OnPrepareDC(&DC);

         CRect DevRect(m_Rect);
         LogToDev(DevRect,&DC,Zoom);

         int DCStat = DC.SaveDC();

         DC.SetMapMode(MM_TEXT);
         DC.SetViewportOrg(0,0);
         DC.SetWindowOrg(0,0);

         Draw(&DC,DevRect);

         DC.RestoreDC(DCStat);

      }

   }

}

BOOL CTrk::TrackHandle(BOOL RubberBand,int Handle,POINT Point,CWnd* pWnd,
                       double Zoom)
{

   BOOL  Changed = FALSE,Aborted = FALSE,Cont = TRUE,First = TRUE;
   CSize Off(0,0);
   int   Inversion = IC_None;

   CRect OldRect(m_Rect);
   LogToDev(OldRect,pWnd,Zoom);
   CRect ActRect(0,0,0,0);
   CRect PrvRect(0,0,0,0);

   if (pWnd->GetCapture() != NULL)
      return(Changed);
   pWnd->SetCapture();

   AdjustPoint(Handle,pWnd,Zoom,&Point);

   ClipCursor(Point,Handle,pWnd,Zoom);

   if ( !RubberBand )
      OnSetCursor(pWnd,HTCLIENT,0,Zoom);

   while (Cont)
   {

      MSG Msg;
      ::GetMessage(&Msg,NULL,0,0);

      switch (Msg.message)
      {
         case WM_MOUSEMOVE:
         {

            if ( ((Handle == HC_Middle) && !CanMove()) ||
                 ((Handle != HC_Middle) && !CanResize()) )
               break;

            POINT Pos;
            Pos.x = (int)((short)(LOWORD(Msg.lParam)));
            Pos.y = (int)((short)(HIWORD(Msg.lParam)));

            Off.cx = Pos.x - Point.x;
            Off.cy = Pos.y - Point.y;

            PrvRect.CopyRect(ActRect);

            OffsetTrackRect(OldRect,Handle,Off,ActRect);

            AdjustRect(Handle,Point,pWnd,Zoom,ActRect);

            if ( !ActRect.EqualRect(PrvRect) )
            {
               if ( !PrvRect.IsRectNull() )
               {
                  if ( !First )
                     DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,PrvRect);
                  DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,ActRect);
                  First = FALSE;
               }
            }

            break;

         }
         case WM_LBUTTONUP:
         {
            if ( !First )
               DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,ActRect);
            Cont = FALSE;
            break;
         }
         case WM_KEYDOWN:
         {
            if (Msg.wParam == VK_ESCAPE)
            {
               if ( !First )
                  DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,ActRect);
               Aborted = TRUE;
               Cont    = FALSE;
            }
            break;
         }
         case WM_RBUTTONDOWN:
         {
            if ( !First )
               DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,ActRect);
            Aborted = TRUE;
            Cont    = FALSE;
            break;
         }
         default:
         {
            ::DispatchMessage(&Msg);
            break;
         }
      }

   }

   if (pWnd->GetCapture() == pWnd)
      ::ReleaseCapture();

   ::ClipCursor(NULL);

   if ( ((Handle == HC_Middle) && !CanMove()) ||
        ((Handle != HC_Middle) && !CanResize()) )
   {
      Off.cx = 0;
      Off.cy = 0;
   }
   else
   {
      DevToLog(&Off,pWnd,Zoom);
      AdjustOffset(Handle,Point,pWnd,Zoom,ActRect,&Off);
   }

   if ( !IsSizeNull(Off) && !Aborted )
   {
      OffsetTrackRect(m_Rect,Handle,Off,m_Rect);
      Changed = TRUE;
   }

   Inversion = IC_None;
   if (m_Rect.right  < m_Rect.left)
      Inversion = Inversion | IC_Horz;
   if (m_Rect.bottom < m_Rect.top)
      Inversion = Inversion | IC_Vert;

   Changed = EnforceRect(Handle,Point,pWnd,Zoom,Changed,Aborted,RubberBand);

   if (Changed)
      m_Rect.NormalizeRect();

   TrackHandleDone(Changed,Inversion,RubberBand,Handle,Point,pWnd,Zoom);

   return(Changed);

}

void CTrk::AdjustPoint(int /*Handle*/,CWnd* /*pWnd*/,double /*Zoom*/,
                       LPPOINT /*pPoint*/) const
{
}

void CTrk::AdjustRect(int /*Handle*/,POINT /*Point*/,CWnd* /*pWnd*/,
                      double /*Zoom*/,LPRECT /*pActRect*/)
{
}

void CTrk::DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
                         CWnd* pWnd,double Zoom,LPCRECT pRect) const
{

   if ( !IsRectNull(pRect) )
   {

      CRect Rect(pRect);
      Rect.NormalizeRect();

      Rect.right++;
      Rect.bottom++;

      DrawTrueTrackRect(RubberBand,Handle,Point,pWnd,Zoom,&Rect);

   }

}

void CTrk::DrawTrueTrackRect(BOOL /*RubberBand*/,int /*Handle*/,POINT /*Point*/,
                             CWnd* pWnd,double /*Zoom*/,LPCRECT pRect) const
{
   CClientDC DC(pWnd);
   DC.DrawFocusRect(pRect);
}

//
//   Off: coordenadas l¢gicas
//
//   Trata de evitar errores de redondeo en los extremos debido al zoom
//

void CTrk::AdjustOffset(int /*Handle*/,POINT Point,CWnd* pWnd,
                        double Zoom,
                        LPCRECT pActRect,LPSIZE pOff) const
{

   if ( HasExtRect() )
   {

      CRect ExtRect(m_ExtRect);
      LogToDev(ExtRect,pWnd,Zoom);

      if ( (pActRect->left <= ExtRect.left) &&
           (Point.x != ExtRect.left) )
         pOff->cx = m_ExtRect.left - m_Rect.left;

      if ( (pActRect->top <= ExtRect.top) &&
           (Point.y != ExtRect.top) )
         pOff->cy = m_ExtRect.top - m_Rect.top;

      if ( (pActRect->right >= ExtRect.right) &&
           (Point.x != ExtRect.right) )
         pOff->cx = m_ExtRect.right - m_Rect.right;

      if ( (pActRect->bottom >= ExtRect.bottom) &&
           (Point.y != ExtRect.bottom) )
         pOff->cy = m_ExtRect.bottom - m_Rect.bottom;

   }

   if ( HasIntRect() )
   {

      CRect IntRect(m_IntRect);
      LogToDev(IntRect,pWnd,Zoom);

      if ( (pActRect->left >= IntRect.left) &&
           (Point.x != IntRect.left) )
         pOff->cx = m_IntRect.left - m_Rect.left;

      if ( (pActRect->top >= IntRect.top) &&
           (Point.y != IntRect.top) )
         pOff->cy = m_IntRect.top - m_Rect.top;

      if ( (pActRect->right <= IntRect.right) &&
           (Point.x != IntRect.right) )
         pOff->cx = m_IntRect.right - m_Rect.right;

      if ( (pActRect->bottom <= IntRect.bottom) &&
           (Point.y != IntRect.bottom) )
         pOff->cy = m_IntRect.bottom - m_Rect.bottom;

   }

}

BOOL CTrk::EnforceRect(int Handle,POINT Point,CWnd* pWnd,double Zoom,
                       BOOL Changed,BOOL Aborted,BOOL RubberBand)
{

   BOOL Ret = Changed;

   if (Changed)
      Ret = EnforceMinSize(Handle);
   else
   {
      if ( RubberBand & !Aborted )
      {
         CPoint LogPoint(Point);
         DevToLog(&LogPoint,pWnd,Zoom);
         Ret = EnforceDefSize(LogPoint);
      }
   }

   return(Ret);

}

void CTrk::OffsetTrackRect(LPCRECT pSrcRect,int Handle,SIZE Off,
                           LPRECT pDstRect) const
{

   pDstRect->left   = pSrcRect->left;
   pDstRect->top    = pSrcRect->top;
   pDstRect->right  = pSrcRect->right;
   pDstRect->bottom = pSrcRect->bottom;

   if ( IsSizeNull(Off) )
      goto End;

   switch (Handle)
   {
      case HC_Middle:
      {
         pDstRect->left   = pDstRect->left   + Off.cx;
         pDstRect->top    = pDstRect->top    + Off.cy;
         pDstRect->right  = pDstRect->right  + Off.cx;
         pDstRect->bottom = pDstRect->bottom + Off.cy;
         break;
      }
      case HC_Left:
      {
         pDstRect->left = pDstRect->left + Off.cx;
         break;
      }
      case HC_Top:
      {
         pDstRect->top = pDstRect->top + Off.cy;
         break;
      }
      case HC_Right:
      {
         pDstRect->right = pDstRect->right + Off.cx;
         break;
      }
      case HC_Bottom:
      {
         pDstRect->bottom = pDstRect->bottom + Off.cy;
         break;
      }
      case HC_LeftTop:
      {
         pDstRect->left = pDstRect->left + Off.cx;
         pDstRect->top  = pDstRect->top  + Off.cy;
         break;
      }
      case HC_LeftBottom:
      {
         pDstRect->left   = pDstRect->left   + Off.cx;
         pDstRect->bottom = pDstRect->bottom + Off.cy;
         break;
      }
      case HC_RightTop:
      {
         pDstRect->right = pDstRect->right + Off.cx;
         pDstRect->top   = pDstRect->top   + Off.cy;
         break;
      }
      case HC_RightBottom:
      {
         pDstRect->right  = pDstRect->right  + Off.cx;
         pDstRect->bottom = pDstRect->bottom + Off.cy;
         break;
      }
      default:
      {
         break;
      }
   }

   End:;

}

void CTrk::ClipCursor(POINT Point,int Handle,CWnd* pWnd,double Zoom) const
{

   CPoint CursorPos(Point);
   pWnd->ClientToScreen(&CursorPos);
   ::SetCursorPos(CursorPos.x,CursorPos.y);

   CRect ClipRect(0,0,INT_MAX,INT_MAX);
   pWnd->ScreenToClient(ClipRect);

   SetClipCursorRect(Point,Handle,pWnd,Zoom,ClipRect);

   // ::ClipCursor permite uno menos en esos casos
   ClipRect.right++;
   ClipRect.bottom++;

   pWnd->ClientToScreen(ClipRect);
   ::ClipCursor(ClipRect);

}

void CTrk::SetClipCursorRect(POINT Point,int Handle,CWnd* pWnd,double Zoom,
                             LPRECT pClipRect) const
{
   DefClip(Point,Handle,pClipRect);
   ClipToWnd(pWnd,Zoom,Point,Handle,pClipRect);
   ClipToInvert(pWnd,Zoom,Point,Handle,pClipRect);
   ClipToExtRect(pWnd,Zoom,Point,Handle,pClipRect);
   ClipToIntRect(pWnd,Zoom,Point,Handle,pClipRect);
}

void CTrk::DefClip(POINT Point,int Handle,LPRECT pClipRect) const
{

   CRect AuxRect(pClipRect);

   if ( ((Handle == HC_Middle) && !CanMove()) ||
        ((Handle != HC_Middle) && !CanResize()) )
   {
      AuxRect.left   = Point.x;
      AuxRect.top    = Point.y;
      AuxRect.right  = Point.x;
      AuxRect.bottom = Point.y;
      goto End;
   }

   switch (Handle)
   {
      case HC_Left:
      case HC_Right:
      {
         AuxRect.top    = Point.y;
         AuxRect.bottom = Point.y;
         break;
      }
      case HC_Top:
      case HC_Bottom:
      {
         AuxRect.left  = Point.x;
         AuxRect.right = Point.x;
         break;
      }
      default:
      {
         break;
      }
   }

   End:

   ::InterxRect(AuxRect,pClipRect,pClipRect);

}

void CTrk::ClipToWnd(CWnd* pWnd,double Zoom,POINT Point,int Handle,
                     LPRECT pClipRect) const
{

   if (pWnd != NULL)
   {

      CRect TrkRect;
      if (Handle == HC_Middle)
         TrkRect = CRect(Point.x,Point.y,Point.x,Point.y);
      else
      {
         TrkRect.CopyRect(m_Rect);
         LogToDev(TrkRect,pWnd,Zoom);
      }

      CRect ExtRect;
      pWnd->GetClientRect(ExtRect);

      ExtClip(TrkRect,ExtRect,Point,Handle,pClipRect);

   }

}

void CTrk::ClipToInvert(CWnd* pWnd,double Zoom,POINT Point,int Handle,
                        LPRECT pClipRect) const
{

   if ( !CanInvert() )
   {

      CRect TrkRect(m_Rect);
      LogToDev(TrkRect,pWnd,Zoom);

      InvClip(TrkRect,Point,Handle,pClipRect);

   }

}

void CTrk::ClipToExtRect(CWnd* pWnd,double Zoom,POINT Point,int Handle,
                         LPRECT pClipRect) const
{

   if ( HasExtRect() )
   {

      CRect TrkRect(m_Rect);
      LogToDev(TrkRect,pWnd,Zoom);

      CRect ExtRect(m_ExtRect);
      LogToDev(ExtRect,pWnd,Zoom);

      ExtClip(TrkRect,ExtRect,Point,Handle,pClipRect);

   }

}

void CTrk::ClipToIntRect(CWnd* pWnd,double Zoom,POINT Point,int Handle,
                         LPRECT pClipRect) const
{

   if ( HasIntRect() )
   {

      CRect TrkRect(m_Rect);
      LogToDev(TrkRect,pWnd,Zoom);

      CRect IntRect(m_IntRect);
      LogToDev(IntRect,pWnd,Zoom);

      IntClip(TrkRect,IntRect,Point,Handle,pClipRect);

   }

}

void CTrk::ExtClip(LPCRECT pTrkRect,LPCRECT pExtRect,POINT Point,int Handle,
                   LPRECT pClipRect) const
{

   CRect AuxRect(pClipRect);

   switch (Handle)
   {
      case HC_Middle:
      {
         AuxRect.left   = Point.x - (pTrkRect->left   - pExtRect->left);
         AuxRect.top    = Point.y - (pTrkRect->top    - pExtRect->top);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->right);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->bottom);
         break;
      }
      case HC_Left:
      {
         AuxRect.left   = Point.x - (pTrkRect->left   - pExtRect->left);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->left);
         break;
      }
      case HC_Top:
      {
         AuxRect.top    = Point.y - (pTrkRect->top    - pExtRect->top);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->top);
         break;
      }
      case HC_Right:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pExtRect->left);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->right);
         break;
      }
      case HC_Bottom:
      {
         AuxRect.top    = Point.y - (pTrkRect->bottom - pExtRect->top);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->bottom);
         break;
      }
      case HC_LeftTop:
      {
         AuxRect.left   = Point.x - (pTrkRect->left   - pExtRect->left);
         AuxRect.top    = Point.y - (pTrkRect->top    - pExtRect->top);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->left);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->top);
         break;
      }
      case HC_LeftBottom:
      {
         AuxRect.left   = Point.x - (pTrkRect->left   - pExtRect->left);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pExtRect->top);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->left);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->bottom);
         break;
      }
      case HC_RightTop:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pExtRect->left);
         AuxRect.top    = Point.y - (pTrkRect->top    - pExtRect->top);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->right);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->top);
         break;
      }
      case HC_RightBottom:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pExtRect->left);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pExtRect->top);
         AuxRect.right  = Point.x + (pExtRect->right  - pTrkRect->right);
         AuxRect.bottom = Point.y + (pExtRect->bottom - pTrkRect->bottom);
         break;
      }
      default:
      {
         break;
      }
   }

   ::InterxRect(AuxRect,pClipRect,pClipRect);

}

void CTrk::IntClip(LPCRECT pTrkRect,LPCRECT pIntRect,POINT Point,int Handle,
                   LPRECT pClipRect) const
{

   CRect AuxRect(pClipRect);

   switch (Handle)
   {
      case HC_Middle:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pIntRect->right);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pIntRect->bottom);
         AuxRect.right  = Point.x + (pIntRect->left   - pTrkRect->left);
         AuxRect.bottom = Point.y + (pIntRect->top    - pTrkRect->top);
         break;
      }
      case HC_Left:
      {
         AuxRect.right  = Point.x + (pIntRect->left   - pTrkRect->left);
         break;
      }
      case HC_Top:
      {
         AuxRect.bottom = Point.y + (pIntRect->top    - pTrkRect->top);
         break;
      }
      case HC_Right:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pIntRect->right);
         break;
      }
      case HC_Bottom:
      {
         AuxRect.top    = Point.y - (pTrkRect->bottom - pIntRect->bottom);
         break;
      }
      case HC_LeftTop:
      {
         AuxRect.right  = Point.x + (pIntRect->left   - pTrkRect->left);
         AuxRect.bottom = Point.y + (pIntRect->top    - pTrkRect->top);
         break;
      }
      case HC_LeftBottom:
      {
         AuxRect.right  = Point.x + (pIntRect->left   - pTrkRect->left);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pIntRect->bottom);
         break;
      }
      case HC_RightTop:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pIntRect->right);
         AuxRect.bottom = Point.y + (pIntRect->top    - pTrkRect->top);
         break;
      }
      case HC_RightBottom:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pIntRect->right);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pIntRect->bottom);
         break;
      }
      default:
      {
         break;
      }
   }

   ::InterxRect(AuxRect,pClipRect,pClipRect);

}

void CTrk::InvClip(LPCRECT pTrkRect,POINT Point,int Handle,
                   LPRECT pClipRect) const
{

   CRect AuxRect(pClipRect);

   switch (Handle)
   {
      case HC_Left:
      {
         AuxRect.right  = Point.x + (pTrkRect->right  - pTrkRect->left);
         break;
      }
      case HC_Top:
      {
         AuxRect.bottom = Point.y + (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      case HC_Right:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pTrkRect->left);
         break;
      }
      case HC_Bottom:
      {
         AuxRect.top    = Point.y - (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      case HC_LeftTop:
      {
         AuxRect.right  = Point.x + (pTrkRect->right  - pTrkRect->left);
         AuxRect.bottom = Point.y + (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      case HC_LeftBottom:
      {
         AuxRect.right  = Point.x + (pTrkRect->right  - pTrkRect->left);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      case HC_RightTop:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pTrkRect->left);
         AuxRect.bottom = Point.y + (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      case HC_RightBottom:
      {
         AuxRect.left   = Point.x - (pTrkRect->right  - pTrkRect->left);
         AuxRect.top    = Point.y - (pTrkRect->bottom - pTrkRect->top);
         break;
      }
      default:
      {
         break;
      }
   }

   ::InterxRect(AuxRect,pClipRect,pClipRect);

}

BOOL CTrk::EnforceMinSize(int Handle)
{

   BOOL Changed = TRUE;

   if ( !HasMinSize() )
      goto End;

   switch (Handle)
   {
      case HC_Left:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.left = m_Rect.right - m_MinSize.cx;
            else
               m_Rect.left = m_Rect.right + m_MinSize.cx;
         }
         break;
      }
      case HC_Top:
      {
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.top = m_Rect.bottom - m_MinSize.cy;
            else
               m_Rect.top = m_Rect.bottom + m_MinSize.cy;
         }
         break;
      }
      case HC_Right:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.right = m_Rect.left + m_MinSize.cx;
            else
               m_Rect.right = m_Rect.left - m_MinSize.cx;
         }
         break;
      }
      case HC_Bottom:
      {
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.bottom = m_Rect.top + m_MinSize.cy;
            else
               m_Rect.bottom = m_Rect.top - m_MinSize.cy;
         }
         break;
      }
      case HC_LeftTop:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.left = m_Rect.right - m_MinSize.cx;
            else
               m_Rect.left = m_Rect.right + m_MinSize.cx;
         }
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.top = m_Rect.bottom - m_MinSize.cy;
            else
               m_Rect.top = m_Rect.bottom + m_MinSize.cy;
         }
         break;
      }
      case HC_LeftBottom:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.left = m_Rect.right - m_MinSize.cx;
            else
               m_Rect.left = m_Rect.right + m_MinSize.cx;
         }
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.bottom = m_Rect.top + m_MinSize.cy;
            else
               m_Rect.bottom = m_Rect.top - m_MinSize.cy;
         }
         break;
      }
      case HC_RightTop:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.right = m_Rect.left + m_MinSize.cx;
            else
               m_Rect.right = m_Rect.left - m_MinSize.cx;
         }
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.top = m_Rect.bottom - m_MinSize.cy;
            else
               m_Rect.top = m_Rect.bottom + m_MinSize.cy;
         }
         break;
      }
      case HC_RightBottom:
      {
         if (abs(m_Rect.right - m_Rect.left) < m_MinSize.cx)
         {
            if (m_Rect.left <= m_Rect.right)
               m_Rect.right = m_Rect.left + m_MinSize.cx;
            else
               m_Rect.right = m_Rect.left - m_MinSize.cx;
         }
         if (abs(m_Rect.bottom - m_Rect.top) < m_MinSize.cy)
         {
            if (m_Rect.top <= m_Rect.bottom)
               m_Rect.bottom = m_Rect.top + m_MinSize.cy;
            else
               m_Rect.bottom = m_Rect.top - m_MinSize.cy;
         }
         break;
      }
      default:
      {
         break;
      }
   }

   m_Rect.NormalizeRect();

   if ( HasExtRect() )
   {

      if (m_Rect.left < m_ExtRect.left)
      {
         m_Rect.left  = m_ExtRect.left;
         m_Rect.right = m_ExtRect.left + m_MinSize.cx;
         if (m_Rect.right > m_ExtRect.right)
         {
            Changed = FALSE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.top < m_ExtRect.top)
      {
         m_Rect.top    = m_ExtRect.top;
         m_Rect.bottom = m_ExtRect.top + m_MinSize.cy;
         if (m_Rect.bottom > m_ExtRect.bottom)
         {
            Changed = FALSE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.right > m_ExtRect.right)
      {
         m_Rect.right = m_ExtRect.right;
         m_Rect.left  = m_ExtRect.right - m_MinSize.cx;
         if (m_Rect.left < m_ExtRect.left)
         {
            Changed = FALSE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.bottom > m_ExtRect.bottom)
      {
         m_Rect.bottom = m_ExtRect.bottom;
         m_Rect.top    = m_ExtRect.bottom - m_MinSize.cy;
         if (m_Rect.top < m_ExtRect.top)
         {
            Changed = FALSE;
            ::MessageBeep(0);
            goto End;
         }
      }

   }

   End:

   if (!Changed)
   {
      m_Rect.left   = 0;
      m_Rect.top    = 0;
      m_Rect.right  = 0;
      m_Rect.bottom = 0;
   }

   return(Changed);

}

BOOL CTrk::EnforceDefSize(POINT LogPoint)
{

   BOOL Changed = TRUE;
   BOOL Error   = FALSE;

   if ( !HasDefSize() )
   {
      Changed = FALSE;
      goto End;
   }

   m_Rect.left   = LogPoint.x  - (m_DefSize.cx / 2);
   m_Rect.top    = LogPoint.y  - (m_DefSize.cy / 2);
   m_Rect.right  = m_Rect.left + m_DefSize.cx;
   m_Rect.bottom = m_Rect.top  + m_DefSize.cy;

   if ( HasExtRect() )
   {

      if (m_Rect.left < m_ExtRect.left)
      {
         m_Rect.left  = m_ExtRect.left;
         m_Rect.right = m_ExtRect.left + m_DefSize.cx;
         if (m_Rect.right > m_ExtRect.right)
         {
            Changed = FALSE;
            Error   = TRUE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.top < m_ExtRect.top)
      {
         m_Rect.top    = m_ExtRect.top;
         m_Rect.bottom = m_ExtRect.top + m_DefSize.cy;
         if (m_Rect.bottom > m_ExtRect.bottom)
         {
            Changed = FALSE;
            Error   = TRUE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.right > m_ExtRect.right)
      {
         m_Rect.right = m_ExtRect.right;
         m_Rect.left  = m_ExtRect.right - m_DefSize.cx;
         if (m_Rect.left < m_ExtRect.left)
         {
            Changed = FALSE;
            Error   = TRUE;
            ::MessageBeep(0);
            goto End;
         }
      }

      if (m_Rect.bottom > m_ExtRect.bottom)
      {
         m_Rect.bottom = m_ExtRect.bottom;
         m_Rect.top    = m_ExtRect.bottom - m_DefSize.cy;
         if (m_Rect.top < m_ExtRect.top)
         {
            Changed = FALSE;
            Error   = TRUE;
            ::MessageBeep(0);
            goto End;
         }
      }

   }

   End:

   if (Error)
   {
      m_Rect.left   = 0;
      m_Rect.top    = 0;
      m_Rect.right  = 0;
      m_Rect.bottom = 0;
   }

   return(Changed);

}

void CTrk::TrackHandleDone(BOOL /*Changed*/,int /*Inversion*/,
                           BOOL /*RubberBand*/,int /*Handle*/,POINT /*Point*/,
                           CWnd* /*pWnd*/,double /*Zoom*/)
{
}

