
#include <afxwin.h>
#include <afxext.h>
#include "geom.h"
#include "trkdraw.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CLineTrk                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CLineTrk,CTrk,0)

CLineTrk::CLineTrk()
: CTrk()
{
   m_DownUp = FALSE;
}

CLineTrk::CLineTrk(LPCRECT pRect,BOOL DownUp,int TrackStyle,int DrawStyle,
                   int UpdateStyle)
: CTrk(pRect,TrackStyle,DrawStyle,UpdateStyle)
{
   m_DownUp = DownUp;
}

CLineTrk::~CLineTrk()
{
}

void CLineTrk::Serialize(CArchive& Arch)
{

   CTrk::Serialize(Arch);

   if ( Arch.IsStoring() )
   {
      DWORD Aux = m_DownUp;
      Arch << Aux;
   }
   else
   {
      DWORD Aux;
      Arch >> Aux;
      m_DownUp = (BOOL)Aux;
   }

}

BOOL CLineTrk::IsDownUp() const
{
   return(m_DownUp);
}

int CLineTrk::GetHitCode(POINT Point,CWnd* pWnd,double Zoom) const
{

   int HitCode = CTrk::GetHitCode(Point,pWnd,Zoom);

   switch (HitCode)
   {
      case HC_Left:
      case HC_Top:
      case HC_Right:
      case HC_Bottom:
      {
         HitCode = HC_Outside;
         break;
      }
      case HC_LeftTop:
      case HC_RightBottom:
      {
         if (m_DownUp)
            HitCode = HC_Outside;
         break;
      }
      case HC_RightTop:
      case HC_LeftBottom:
      {
         if ( !m_DownUp )
            HitCode = HC_Outside;
         break;
      }
      default:
      {
         break;
      }
   }

   return(HitCode);

}

void CLineTrk::TrackHandleDone(BOOL /*Changed*/,int Inversion,BOOL RubberBand,
                               int /*Handle*/,POINT /*Point*/,
                               CWnd* /*pWnd*/,double /*Zoom*/)
{

   if (RubberBand)
   {
      if ( (Inversion == IC_Horz) || (Inversion == IC_Vert) )
         m_DownUp = TRUE;
      else
         m_DownUp = FALSE;
   }
   else
   {
      if ( (Inversion == IC_Horz) || (Inversion == IC_Vert) )
         m_DownUp = !m_DownUp;
   }

   if ( abs(m_Rect.left - m_Rect.right ) <= 3 )
      m_Rect.right = m_Rect.left;
   if ( abs(m_Rect.top  - m_Rect.bottom) <= 3 )
      m_Rect.bottom = m_Rect.top;

}

void CLineTrk::DrawTrackRect(BOOL /*RubberBand*/,int Handle,POINT /*Point*/,
                             CWnd* pWnd,double /*Zoom*/,LPCRECT pRect) const
{

   if ( !IsRectNull(pRect) )
   {

      CRect Rect(pRect);
      Rect.right++;
      Rect.bottom++;

      POINT OrgPt,EndPt;

      GetTrackLine(&Rect,Handle,&OrgPt,&EndPt);

      CClientDC DC(pWnd);

      CPen    Pen(PS_DOT,1,RGB(0,0,0));
      CPen*   pOldPen   = (CPen*)DC.SelectObject(&Pen);
      CBrush* pOldBrush = (CBrush*)DC.SelectStockObject(NULL_BRUSH);
      int     OldROP    = DC.SetROP2(R2_XORPEN);

      ::DrawLine(OrgPt,EndPt,&DC);

      DC.SelectObject(pOldPen);
      DC.SelectObject(pOldBrush);
      DC.SetROP2(OldROP);

   }

}

void CLineTrk::GetTrackLine(LPCRECT pTrackRect,int Handle,
                            LPPOINT pOrgPt,LPPOINT pEndPt) const
{

   int Inversion = IC_None;
   if (pTrackRect->right  < pTrackRect->left)
      Inversion = Inversion | IC_Horz;
   if (pTrackRect->bottom < pTrackRect->top)
      Inversion = Inversion | IC_Vert;

   BOOL DownUp = m_DownUp;
   if ( (Inversion == IC_Horz) || (Inversion == IC_Vert) )
      DownUp = !DownUp;

   switch (Handle)
   {
      case HC_Middle:
      {
         if (DownUp)
         {
            pOrgPt->x = pTrackRect->left;
            pOrgPt->y = pTrackRect->bottom;
            pEndPt->x = pTrackRect->right;
            pEndPt->y = pTrackRect->top;
         }
         else
         {
            pOrgPt->x = pTrackRect->right;
            pOrgPt->y = pTrackRect->bottom;
            pEndPt->x = pTrackRect->left;
            pEndPt->y = pTrackRect->top;
         }
         break;
      }
      case HC_Left:
      case HC_Top:
      case HC_LeftTop:
      {
         pOrgPt->x = pTrackRect->right;
         pOrgPt->y = pTrackRect->bottom;
         pEndPt->x = pTrackRect->left;
         pEndPt->y = pTrackRect->top;
         break;
      }
      case HC_RightTop:
      {
         pOrgPt->x = pTrackRect->left;
         pOrgPt->y = pTrackRect->bottom;
         pEndPt->x = pTrackRect->right;
         pEndPt->y = pTrackRect->top;
         break;
      }
      case HC_Right:
      case HC_Bottom:
      case HC_RightBottom:
      {
         pOrgPt->x = pTrackRect->left;
         pOrgPt->y = pTrackRect->top;
         pEndPt->x = pTrackRect->right;
         pEndPt->y = pTrackRect->bottom;
         break;
      }
      case HC_LeftBottom:
      {
         pOrgPt->x = pTrackRect->right;
         pOrgPt->y = pTrackRect->top;
         pEndPt->x = pTrackRect->left;
         pEndPt->y = pTrackRect->bottom;
         break;
      }
      default:
      {
         pOrgPt->x = 0;
         pOrgPt->y = 0;
         pEndPt->x = 0;
         pEndPt->y = 0;
      }
   }

}

void CLineTrk::DrawBorder(CDC* /*pDC*/,LPCRECT /*pDevRect*/) const
{
}

void CLineTrk::DrawTrueResizeHandle(int Handle,CDC* pDC,LPCRECT pDevRect) const
{

   BOOL Draw = TRUE;

   switch (Handle)
   {
      case HC_Left:
      case HC_Top:
      case HC_Right:
      case HC_Bottom:
      {
         Draw = FALSE;
         break;
      }
      case HC_LeftTop:
      case HC_RightBottom:
      {
         if (m_DownUp)
            Draw = FALSE;
         break;
      }
      case HC_RightTop:
      case HC_LeftBottom:
      {
         if ( !m_DownUp )
            Draw = FALSE;
         break;
      }
      default:
      {
         break;
      }
   }

   if (Draw)
      CTrk::DrawTrueResizeHandle(Handle,pDC,pDevRect);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkLine                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CTrkLine,CTrkObj,0)

CTrkLine::CTrkLine()
: CTrkObj()
{
   m_DownUp = FALSE;
   m_Color  = RGB(0,0,0);
}

CTrkLine::CTrkLine(LPCRECT pRect,BOOL DownUp,int Style,int State,
                   int UpdateStyle)
: CTrkObj(pRect,Style,State,UpdateStyle)
{
   m_DownUp = DownUp;
   m_Color  = RGB(0,0,0);
}

CTrkLine::CTrkLine(CTrk* pTrk)
: CTrkObj(pTrk)
{
   m_DownUp = ((CLineTrk*)pTrk)->IsDownUp();
   m_Color  = RGB(0,0,0);
}

CTrkLine::~CTrkLine()
{
}

void CTrkLine::Serialize(CArchive& Arch)
{

   CTrkObj::Serialize(Arch);

   if ( Arch.IsStoring() )
   {
      DWORD Aux = m_DownUp;
      Arch << Aux;
   }
   else
   {
      DWORD Aux;
      Arch >> Aux;
      m_DownUp = (BOOL)Aux;
   }

}

void CTrkLine::SetColor(COLORREF Color)
{
   m_Color = Color;
}

void CTrkLine::DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double /*Zoom*/) const
{

   POINT OrgPt,EndPt;

   if (m_DownUp)
   {
      OrgPt.x = pDevRect->left;
      OrgPt.y = pDevRect->bottom;
      EndPt.x = pDevRect->right;
      EndPt.y = pDevRect->top;
   }
   else
   {
      OrgPt.x = pDevRect->left;
      OrgPt.y = pDevRect->top;
      EndPt.x = pDevRect->right;
      EndPt.y = pDevRect->bottom;
   }

   ::DrawLine(OrgPt,EndPt,pDC);

}

CTrk* CTrkLine::CreateTrk() const
{
   CLineTrk* pTrk = new CLineTrk(m_Rect,m_DownUp,CTrk::TS_None,
                                 CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CTrkLine::CreateTrkNew() const
{
   CLineTrk* pTrk = new CLineTrk(m_Rect,m_DownUp,
                                 CTrk::TS_Move|CTrk::TS_Resize|CTrk::TS_Invert,
                                 CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

void CTrkLine::SetObjDefaults(CTrk* pTrk)
{
   m_DownUp = ((CLineTrk*)pTrk)->IsDownUp();
}

#define ARROW_H  10
#define ARROW_L   3

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CArrowTrk                                                       //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CArrowTrk,CLineTrk,0)

CArrowTrk::CArrowTrk()
: CLineTrk()
{
   m_Up = FALSE;
}

CArrowTrk::CArrowTrk(LPCRECT pRect,BOOL DownUp,BOOL Up,
                     int TrackStyle,int DrawStyle,int UpdateStyle)
: CLineTrk(pRect,DownUp,TrackStyle,DrawStyle,UpdateStyle)
{
   m_Up = Up;
}

CArrowTrk::~CArrowTrk()
{
}

void CArrowTrk::Serialize(CArchive& Arch)
{

   CLineTrk::Serialize(Arch);

   if ( Arch.IsStoring() )
   {
      DWORD Aux = m_Up;
      Arch << Aux;
   }
   else
   {
      DWORD Aux;
      Arch >> Aux;
      m_Up = (BOOL)Aux;
   }

}

BOOL CArrowTrk::IsUp() const
{
   return(m_Up);
}

void CArrowTrk::TrackHandleDone(BOOL Changed,int Inversion,BOOL RubberBand,
                                int Handle,POINT Point,
                                CWnd* pWnd,double Zoom)
{

   CLineTrk::TrackHandleDone(Changed,Inversion,RubberBand,Handle,Point,
                             pWnd,Zoom);

   if (RubberBand)
   {
      if ( (Inversion == IC_Horz) || (Inversion == IC_None) )
         m_Up = FALSE;
      else
         m_Up = TRUE;
   }
   else
   {
      if ( (Inversion != IC_Horz) && (Inversion != IC_None) )
         m_Up = !m_Up;
   }

}

void CArrowTrk::GetVisibleFrmRect(LPRECT pRect,CDC* pDC,double Zoom) const
{

   *pRect = m_Rect;
   LogToDev(pRect,pDC,Zoom);

   int L   = (int)(ARROW_L * Zoom);
   int Off = GetFrameSize();
   if (Off < L) Off = L;

   ::InflRect(pRect,Off,pRect);

   (pRect->right)++;
   (pRect->bottom)++;

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkArrow                                                       //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CTrkArrow,CTrkLine,0)

CTrkArrow::CTrkArrow()
: CTrkLine()
{
   m_Up = FALSE;
}

CTrkArrow::CTrkArrow(LPCRECT pRect,BOOL DownUp,BOOL Up,
                     int Style,int State,int UpdateStyle)
: CTrkLine(pRect,DownUp,Style,State,UpdateStyle)
{
   m_Up = Up;
}

CTrkArrow::CTrkArrow(CTrk* pTrk)
: CTrkLine(pTrk)
{
   m_Up = ((CArrowTrk*)pTrk)->IsUp();
}

CTrkArrow::~CTrkArrow()
{
}

void CTrkArrow::Serialize(CArchive& Arch)
{

   CTrkLine::Serialize(Arch);

   if ( Arch.IsStoring() )
   {
      DWORD Aux = m_Up;
      Arch << Aux;
   }
   else
   {
      DWORD Aux;
      Arch >> Aux;
      m_Up = (BOOL)Aux;
   }

}

void CTrkArrow::DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom) const
{

   POINT OrgPt,EndPt;

   if (m_DownUp)
   {
      if (m_Up)
      {
         OrgPt.x = pDevRect->left;
         OrgPt.y = pDevRect->bottom;
         EndPt.x = pDevRect->right;
         EndPt.y = pDevRect->top;
      }
      else
      {
         OrgPt.x = pDevRect->right;
         OrgPt.y = pDevRect->top;
         EndPt.x = pDevRect->left;
         EndPt.y = pDevRect->bottom;
      }
   }
   else
   {
      if (m_Up)
      {
         OrgPt.x = pDevRect->right;
         OrgPt.y = pDevRect->bottom;
         EndPt.x = pDevRect->left;
         EndPt.y = pDevRect->top;
      }
      else
      {
         OrgPt.x = pDevRect->left;
         OrgPt.y = pDevRect->top;
         EndPt.x = pDevRect->right;
         EndPt.y = pDevRect->bottom;
      }
   }

   ::DrawArrow(OrgPt,EndPt,pDC,m_Color,Zoom);

}

CTrk* CTrkArrow::CreateTrk() const
{
   CArrowTrk* pTrk = new CArrowTrk(m_Rect,m_DownUp,m_Up,CTrk::TS_None,
                                   CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CTrkArrow::CreateTrkNew() const
{
   CArrowTrk* pTrk = new CArrowTrk(m_Rect,m_DownUp,m_Up,
                                   CTrk::TS_Move|CTrk::TS_Resize|CTrk::TS_Invert,
                                   CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

void CTrkArrow::SetObjDefaults(CTrk* pTrk)
{
   CTrkLine::SetObjDefaults(pTrk);
   m_Up = ((CArrowTrk*)pTrk)->IsUp();
}

