
#include <afxwin.h>
#include <afxext.h>
#include <limits.h>
#include "geom.h"
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

CMoveTrk::CMoveTrk()
: CTrk(CRect(0,0,0,0),CTrk::TS_Move,CTrk::DS_None,CTrk::US_Repaint),m_RectList()
{
   m_Rect = CRect(INT_MAX,INT_MAX,INT_MIN,INT_MIN);
}

CMoveTrk::~CMoveTrk()
{
   POSITION Pos = m_RectList.GetHeadPosition();
   while (Pos != NULL)
   {
      CRect* pRect = (CRect*)m_RectList.GetNext(Pos);
      delete pRect;
   }
   m_RectList.RemoveAll();
}

void CMoveTrk::Add(LPCRECT pRect)
{

   CRect* pElem = new CRect(pRect);

   m_RectList.AddTail(pElem);

   if (m_Rect.left   > pElem->left)
      m_Rect.left   = pElem->left;
   if (m_Rect.top    > pElem->top)
      m_Rect.top    = pElem->top;
   if (m_Rect.right  < pElem->right)
      m_Rect.right  = pElem->right;
   if (m_Rect.bottom < pElem->bottom)
      m_Rect.bottom = pElem->bottom;

}

BOOL CMoveTrk::TrackMove(POINT Point,UINT Flags,CWnd* pWnd,double Zoom)
{

   BOOL Changed = FALSE;

   CRect OldRect(m_Rect);

   m_DevRect = m_Rect;
   LogToDev(m_DevRect,pWnd,Zoom);

   m_Zoom = Zoom;

   Changed = Track(Point,Flags,pWnd,Zoom,UPDATE_NULL);

   if (Changed)
   {

      CSize Off;
      Off.cx = m_Rect.left - OldRect.left;
      Off.cy = m_Rect.top  - OldRect.top;

      POSITION Pos = m_RectList.GetHeadPosition();
      while (Pos != NULL)
      {
         CRect* pRect = (CRect*)m_RectList.GetNext(Pos);
         pRect->left   = pRect->left   + Off.cx;
         pRect->top    = pRect->top    + Off.cy;
         pRect->right  = pRect->right  + Off.cx;
         pRect->bottom = pRect->bottom + Off.cy;
      }

   }

   return(Changed);

}

void CMoveTrk::DrawTrackRect(BOOL /*RubberBand*/,int /*Handle*/,POINT /*Point*/,
                             CWnd* pWnd,double /*Zoom*/,LPCRECT pRect) const
{

   if ( !IsRectNull(pRect) )
   {

      CSize     Off;
      CClientDC DC(pWnd);
      if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
         ((CView*)pWnd)->OnPrepareDC(&DC);

      Off.cx = pRect->left - m_DevRect.left;
      Off.cy = pRect->top  - m_DevRect.top;

      DrawTrackRectList(&DC,Off);

   }

}

void CMoveTrk::DrawTrackRectList(CDC* pDC,SIZE Off) const
{
   POSITION Pos = m_RectList.GetHeadPosition();
   while (Pos != NULL)
   {
      CRect* pRect = (CRect*)m_RectList.GetNext(Pos);
      CRect Rect(pRect);
      LogToDev(Rect,pDC,m_Zoom);
      Rect.left   = Rect.left   + Off.cx;
      Rect.top    = Rect.top    + Off.cy;
      Rect.right  = Rect.right  + Off.cx + 1;
      Rect.bottom = Rect.bottom + Off.cy + 1;

      int DCStat = pDC->SaveDC();
      pDC->SetMapMode(MM_TEXT);
      pDC->SetViewportOrg(0,0);
      pDC->SetWindowOrg(0,0);
      pDC->DrawFocusRect(Rect);
      pDC->RestoreDC(DCStat);

   }
}

