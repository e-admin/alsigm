#include "stdafx.h"
#include "iDocGear.h"
#include "geom.h"

#include "trkdraw.h"  
#include "mapmode.h"   
#include "AuxFnc.h"
//#include "ann.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif 

#define MINZOOM_ARROW  0.75

//////definiciones del fichero ann.h///////////
#define IDOC_NULL_ID  LONG_MAX-1



///////// PRUEBA ////////
////////////////////////
IMPLEMENT_SERIAL(CGearTrk,CTrk,0)

CGearTrk::CGearTrk()
: CTrk()
{
   m_pWnd = 0;
}

CGearTrk::CGearTrk(LPCRECT pRect,CVw* pWnd,int TrackStyle,int DrawStyle,
                   int UpdateStyle)
: CTrk(pRect,TrackStyle,DrawStyle,UpdateStyle)
{
   m_pWnd = pWnd;
}

CGearTrk::~CGearTrk()
{
   m_pWnd = 0;
}

CVw* CGearTrk::GetParentView()
{
   return(m_pWnd);
}
void CGearTrk::SetParentView(CVw* pWnd)
{
   m_pWnd = pWnd;
}

void CGearTrk::Serialize(CArchive& Arch)
{
   CTrk::Serialize(Arch);
}

BOOL CGearTrk::NeedDraw(CDC* /*pDC*/,double /*Zoom*/)
{
   BOOL NeedDraw = FALSE;

   if ( !m_Rect.IsRectNull() )
   {
     /* if (m_pWnd != 0)
      {
         ICImg* pImgParent = m_pWnd->GetImage();
         CRect RectDC,ClientRect;
         m_pWnd->GetClientRect(ClientRect);
         pImgParent->GetDeviceRect(RectDC);
      }

      CRect DCRect;
      pDC->GetClipBox(DCRect);

      CRect FrmRect;
      GetTotalFrmRect(FrmRect,pDC,Zoom);

      if ( DCRect.IntersectRect(DCRect,FrmRect) )*/
         NeedDraw = TRUE;

   }

   return(NeedDraw);
}


void CGearTrk::LogToDev(LPRECT pRect,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg,ptFin;
      HIGEAR hIGear = m_pWnd->GetImage();
      
      ptOrg.x = pRect->left;
      ptOrg.y = pRect->top;
      ptFin.x = pRect->right;
      ptFin.y = pRect->bottom;
      ptOrg = ImgToDC(hIGear,ptOrg);
      ptFin = ImgToDC(hIGear,ptFin);
      pRect->left = ptOrg.x;
      pRect->top = ptOrg.y;
      pRect->right = ptFin.x;
      pRect->bottom = ptFin.y;
      return;

   }
   else
      CTrk::LogToDev(pRect,pDC,Zoom);

}

void CGearTrk::LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pRect,&DC,Zoom);
}

void CGearTrk::DevToLog(LPRECT pRect,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg,ptFin;
      HIGEAR hIGear = m_pWnd->GetImage();
      
      ptOrg.x = pRect->left;
      ptOrg.y = pRect->top;
      ptFin.x = pRect->right;
      ptFin.y = pRect->bottom;
      ptOrg = DCToImg(hIGear, ptOrg);
      ptFin = DCToImg(hIGear, ptFin);
      pRect->left = ptOrg.x;
      pRect->top = ptOrg.y;
      pRect->right = ptFin.x;
      pRect->bottom = ptFin.y;
      return;

   }
   else
      CTrk::LogToDev(pRect,pDC,Zoom);

}

void CGearTrk::DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pRect,&DC,Zoom);
}

void CGearTrk::LogToDev(LPSIZE pSize,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg;
      HIGEAR hIGear = m_pWnd->GetImage();
      
      ptOrg.x = pSize->cx;
      ptOrg.y = pSize->cy;      
      ptOrg = ImgToDC(hIGear, ptOrg);
      
      pSize->cx = ptOrg.x;
      pSize->cy = ptOrg.y;      
      return;

   }
   else
      CTrk::LogToDev(pSize,pDC,Zoom);
}

void CGearTrk::LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pSize,&DC,Zoom);
}
/*
void CGearTrk::DevToLog(LPSIZE pSize,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg;
      ICImg* pImgParent = m_pWnd->GetImage();
      
      ptOrg.x = pSize->cx;
      ptOrg.y = pSize->cy;
      
      ptOrg = pImgParent->DCToImg(ptOrg);
      
      pSize->cx = ptOrg.x;
      pSize->cy = ptOrg.y;
      
      return;

   }
   else
      CTrk::DevToLog(pSize,pDC,Zoom);
}

void CGearTrk::DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pSize,&DC,Zoom);
}
*/
void CGearTrk::LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {      
      CPoint point;
      point.x = pPoint->x;
      point.y = pPoint->y;
      HIGEAR hIGear = m_pWnd->GetImage();
      
      point = ImgToDC(hIGear, point);
      pPoint->x = point.x;
      pPoint->y = point.y;
      
      return;

   }
   else
      CTrk::LogToDev(pPoint,pDC,Zoom);
}

void CGearTrk::LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pPoint,&DC,Zoom);
}

void CGearTrk::DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg;
      HIGEAR hIGear = m_pWnd->GetImage();
      
      ptOrg.x = pPoint->x;
      ptOrg.y = pPoint->y;
      
      ptOrg = DCToImg(hIGear, ptOrg);
      
      pPoint->x = ptOrg.x;
      pPoint->y = ptOrg.y;
      
      return;

   }
   else
      CTrk::DevToLog(pPoint,pDC,Zoom);

}

void CGearTrk::DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pPoint,&DC,Zoom);
}


///////////////////////////////////////////////////////////////
IMPLEMENT_SERIAL(CTrkGear,CTrkObj,0)

CTrkGear::CTrkGear()
: CTrkObj()
{   
   m_pWnd = 0;
   Init();   
} 


CTrkGear::CTrkGear(LONG ObjClassId)
: CTrkObj(ObjClassId)
{
   m_pWnd = 0;
   Init();
}


CTrkGear::CTrkGear(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,int Style,int State,
                   int UpdateStyle)
: CTrkObj(ObjClassId,pRect,Style,State,UpdateStyle)
{   
   m_pWnd = pWnd;
   Init();
}

CTrkGear::CTrkGear(LONG ObjClassId,CTrk* pTrk)
: CTrkObj(ObjClassId,pTrk)
{
   m_pWnd = ((CGearTrk*)pTrk)->GetParentView();   
   Init();
}

CTrkGear::~CTrkGear()
{
}

void CTrkGear::Init()
{
   m_Id       = IDOC_NULL_ID;
   m_IsModify = TRUE;
   m_IsDelete = TRUE;
   m_Status   = IDOC_ANN_STATUS_NEW;
   GetSysUserName(m_CrtName);
   m_UpdName   = m_CrtName;
   GetTime(m_CrtDate);
   m_UpdDate = m_CrtDate;
}
void CTrkGear::Serialize(CArchive& Arch)
{
   CTrkObj::Serialize(Arch);
}


CTrk* CTrkGear::CreateTrk() const
{
   CGearTrk* pTrk = new CGearTrk(m_Rect,m_pWnd,CTrk::TS_None,
                                 CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CTrkGear::CreateTrkNew() const
{
   CGearTrk* pTrk = new CGearTrk(m_Rect,m_pWnd,
                                 CTrk::TS_Move|CTrk::TS_Resize|CTrk::TS_Invert,
                                 CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CVw* CTrkGear::GetParentView()
{
   return(m_pWnd);
}
void CTrkGear::SetParentView(CVw* pWnd)
{
   m_pWnd = pWnd;
}

void CTrkGear::OnDraw(CDC* pDC,double Zoom) const
{

   if (IsInUse())
   {
      CGearTrk* pTrk = (CGearTrk*)SetupTrk();

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

void CTrkGear::GetCreatorName(CString& CrtName) const
{
   CrtName = m_CrtName;
}


void CTrkGear::SetCreatorName(CString& CrtName) 
{
   m_CrtName = CrtName;
} 

void CTrkGear::GetCreatorDate(CString& CrtDate) const
{
   CrtDate = m_CrtDate;
}


void CTrkGear::SetCreatorDate(CString& CrtDate) 
{
   m_CrtDate = CrtDate;
} 


void CTrkGear::GetUpdateName(CString& UpdName) const
{
   UpdName = m_UpdName;
}


void CTrkGear::SetUpdateName(CString& UpdName) 
{
   m_UpdName = UpdName;
} 

void CTrkGear::GetUpdateDate(CString& UpdDate) const
{
   UpdDate = m_UpdDate;
}

void CTrkGear::SetUpdateDate(CString& UpdDate) 
{
   m_UpdDate = UpdDate;
} 

void CTrkGear::GetModify(BOOL& modify) const
{
   modify = m_IsModify;
}

void CTrkGear::SetModify(BOOL modify) 
{
   m_IsModify = modify;
} 

void CTrkGear::GetDelete(BOOL& Del) const
{
   Del = m_IsDelete;
}

void CTrkGear::SetDelete(BOOL Del) 
{
   m_IsDelete = Del;
}
void CTrkGear::GetStatus(int& status) const
{
   status = m_Status;
}

void CTrkGear::SetStatus(int status) 
{
   m_Status = status;
} 

void CTrkGear::OnContextMenu(CWnd* /*pWnd*/, CPoint /*point*/)
{
}


BOOL CTrkGear::PropertyInfo(CWnd* /*pWnd*/,CPoint /*point*/)
{
   return(FALSE);
}
void CTrkGear::Move(LPCRECT pPostRect,CWnd* pWnd,double Zoom,int UpdateMode)
{  
   CTrkObj::Move(pPostRect,pWnd,Zoom,UpdateMode);
   if (m_Status != IDOC_ANN_STATUS_NEW)
   {
      m_Status = IDOC_ANN_STATUS_UPD;
      GetSysUserName(m_UpdName);
      GetTime(m_UpdDate);   
   }

}


//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CLineTrk                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CLineTrk,CGearTrk,0)  //CTrk

CLineTrk::CLineTrk()
: CGearTrk()
{
   m_DownUp = FALSE;
}

CLineTrk::CLineTrk(LPCRECT pRect,CVw* pWnd,BOOL DownUp,int TrackStyle,int DrawStyle,
                   int UpdateStyle)
: CGearTrk(pRect,pWnd,TrackStyle,DrawStyle,UpdateStyle)
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

/*   
     DownUp = TRUE       DownUp = FALSE
     __________          __________  
    |        /|         |\        |     
    |      /  |         |  \      |
    |    /    |         |    \    |
    |  /      |         |      \  |
    |/________|         |________\|                             

   
*/

BOOL CLineTrk::IsDownUp() const
{
   return(m_DownUp);
}

int CLineTrk::GetHitCode(POINT Point,CWnd* pWnd,double Zoom) const
{

   int HitCode = CGearTrk::GetHitCode(Point,pWnd,Zoom);

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
      CGearTrk::DrawTrueResizeHandle(Handle,pDC,pDevRect);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkLine                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CTrkLine,CTrkGear,0)  //CTrkObj

CTrkLine::CTrkLine()
: CTrkGear()
{
   m_DownUp = FALSE;
   m_Color  = RGB(0,0,0);
} 


CTrkLine::CTrkLine(LONG ObjClassId)
: CTrkGear(ObjClassId)
{
   m_DownUp = FALSE;
   m_Color  = RGB(0,0,0);
}


CTrkLine::CTrkLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,BOOL DownUp,int Style,int State,
                   int UpdateStyle)
: CTrkGear(ObjClassId,pRect,pWnd,Style,State,UpdateStyle)
{
   m_DownUp = DownUp;
   m_Color  = RGB(0,0,0);
}

CTrkLine::CTrkLine(LONG ObjClassId,CTrk* pTrk)
: CTrkGear(ObjClassId,pTrk)
{
   m_DownUp = ((CLineTrk*)pTrk)->IsDownUp();
   m_Color  = RGB(0,0,0);
}

CTrkLine::~CTrkLine()
{
}

void CTrkLine::Serialize(CArchive& Arch)
{

   CTrkGear::Serialize(Arch);

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

COLORREF CTrkLine::GetColor() const
{
   return(m_Color);
}

void CTrkLine::SetColor(COLORREF Color)
{
   m_Color = Color;
}

BOOL CTrkLine::IsDownUp() const
{
   return(m_DownUp);
}

void CTrkLine::SetDownUp(BOOL DownUp) 
{
   m_DownUp = DownUp;
}

void CTrkLine::DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom) const
{

   POINT OrgPt,EndPt;

   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;

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

   ::DrawLine(OrgPt,EndPt,pDC,m_Color,Zoom);
}

CTrk* CTrkLine::CreateTrk() const
{
   CLineTrk* pTrk = new CLineTrk(m_Rect,m_pWnd,m_DownUp,CTrk::TS_None,
                                 CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CTrkLine::CreateTrkNew() const
{
   CLineTrk* pTrk = new CLineTrk(m_Rect,m_pWnd,m_DownUp,
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

CArrowTrk::CArrowTrk(LPCRECT pRect,CVw* pWnd,BOOL DownUp,BOOL Up,
                     int TrackStyle,int DrawStyle,int UpdateStyle)
: CLineTrk(pRect,pWnd,DownUp,TrackStyle,DrawStyle,UpdateStyle)
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

/*   
     DownUp = FALSE   DownUp = TRUE
     Up = TRUE        Up = TRUE 
     __________       ___________
    | _       |       |        _ |
    ||\       |       |        /||     
    |  \      |       |      /   |
    |    \    |       |    /     |
    |      \  |       |  /       |
    |________\|       |/_________|  
    
     DownUp = TRUE    DownUp = FALSE
     Up = FALSE       Up = FALSE 
     ___________       ___________
    |         /|      |\          |
    |       /  |      |  \        |     
    |     /    |      |    \      |
    |   /      |      |      \    |
    ||/_       |      |       _\| |
    |__________|      |___________|         

   
*/



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

CTrkArrow::CTrkArrow(LONG ObjClassId)
: CTrkLine(ObjClassId)
{
   m_Up = FALSE;
}

CTrkArrow::CTrkArrow(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,BOOL DownUp,BOOL Up,
                     int Style,int State,int UpdateStyle)
: CTrkLine(ObjClassId,pRect,pWnd,DownUp,Style,State,UpdateStyle)
{
   m_Up = Up;
}

CTrkArrow::CTrkArrow(LONG ObjClassId,CTrk* pTrk)
: CTrkLine(ObjClassId,pTrk)
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

BOOL CTrkArrow::IsUp() const
{
   return(m_Up);
} 
 

void CTrkArrow::SetUp(BOOL Up) 
{
   m_Up = Up;
}

void CTrkArrow::DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom) const
{

   POINT OrgPt,EndPt;

   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;

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
   
   if (Zoom < MINZOOM_ARROW)
      Zoom = MINZOOM_ARROW;

   ::DrawArrow(OrgPt,EndPt,pDC,m_Color,Zoom);

}

CTrk* CTrkArrow::CreateTrk() const
{
   CArrowTrk* pTrk = new CArrowTrk(m_Rect,m_pWnd,m_DownUp,m_Up,CTrk::TS_None,
                                   CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CTrkArrow::CreateTrkNew() const
{
   CArrowTrk* pTrk = new CArrowTrk(m_Rect,m_pWnd,m_DownUp,m_Up,
                                   CTrk::TS_Move|CTrk::TS_Resize|CTrk::TS_Invert,
                                   CTrk::DS_None,CTrk::US_Invalidate);
   return(pTrk);
}

void CTrkArrow::SetObjDefaults(CTrk* pTrk)
{
   CTrkLine::SetObjDefaults(pTrk);
   m_Up = ((CArrowTrk*)pTrk)->IsUp();
}



/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CTrkText                                                                  //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////


IMPLEMENT_SERIAL(CTrkText,CTrkGear,0) //CTrkObj

CTrkText::CTrkText()
: CTrkGear(),m_Font()
{
   SetInitDefaults(); 
}

CTrkText::CTrkText(LONG ObjClassId)
: CTrkGear(ObjClassId),m_Font()
{
   SetInitDefaults();
}

CTrkText::CTrkText(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkGear(ObjClassId,pRect,pWnd,STL_None,STT_Null,UPD_Invalidate),m_Font()
{
   SetInitDefaults();
}

CTrkText::CTrkText(LONG ObjClassId,CTrk* pTrk)
: CTrkGear(ObjClassId,pTrk)
{
   SetInitDefaults();    
}

CTrkText::CTrkText(LONG ObjClassId,CTrk* pTrk,const ICFont& Font)
: CTrkGear(ObjClassId,pTrk),m_Font(Font)
{
   SetInitDefaults();   
} 

CTrkText::CTrkText(LONG ObjClassId,CTrk* pTrk,LOGFONT LogFont,int /*FontSize*/)
: CTrkGear(ObjClassId,pTrk)
{
   SetInitDefaults();  
   CFont Font;
   Font.CreateFontIndirect(&LogFont);
   m_Font = ICFont(Font);         
}

CTrkText::~CTrkText()
{ 
}  

void CTrkText::Serialize(CArchive& Arch)
{
   CTrkGear::Serialize(Arch);   
} 


void CTrkText::SetInitDefaults()
{      
   //m_Text     = "Texto";
   m_Justify  = LEFT_JUSTIFY; 
   m_Color    = RGB(0,0,0);   
      
   EnableMove(TRUE); 
   SetUpdateStyle(UPD_Invalidate); 
   EnableResize(TRUE);      
} 

////////////////////////////////////////////////////////////

void CTrkText::GetFont(ICFont& Font) const
{ 
   Font = m_Font;
}

void CTrkText::GetFont(LOGFONT& LogFont) const
{ 
   m_Font.GetObject(sizeof(LOGFONT),&LogFont);
}       

int CTrkText::GetJustify() const
{ 
   return(m_Justify);
}

COLORREF CTrkText::GetColor() const
{
   return(m_Color);
} 

int CTrkText::GetFontSize() const
{ 
   return((int)m_Font.GetSize());
}

void CTrkText::SetColor(COLORREF Color)
{
   m_Color = Color;
}

void CTrkText::SetFont(const ICFont& Font)
{
   m_Font = Font;
}

void CTrkText::SetFont(const LOGFONT& LogFont)
{  
   CFont Font;
   
   Font.CreateFontIndirect(&LogFont);
   
   m_Font = ICFont(Font);
}

void CTrkText::SetJustify(int Justify)
{
   m_Justify = Justify;
}


void CTrkText::DrawTrueObj(CDC* pDC,LPCRECT pRect,double /*Zoom*/) const
{ 
  COLORREF OldColor = pDC->GetTextColor();
  pDC->SetTextColor(m_Color);   
 
  CRect SrcRect;
  GetRect(SrcRect);   
  
  ZoomText(pDC,m_Text,SrcRect,*pRect,&m_Font,m_Justify,SRCAND);
  
  pDC->SetTextColor(OldColor);              
} 

////////////////////////////////////////////////////////////////////////
//                                                                    //
//  CIconTrk:Clase creada para que el tracker de los iconos se pinte  //
//           en pantalla con un cierto tamaño dependiendo del zoom    //
//           (a partir de ciertos zooms, no se pinta más grande ni    //
//           más pequeño)                                             //
//                                                                    //
////////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CIconTrk,CTrk,0)

CIconTrk::CIconTrk()
: CGearTrk()
{ 
   m_IconZoom = 1;
}

CIconTrk::CIconTrk(LPCRECT pRect,CVw* pWnd,double IconZoom,int TrackStyle,int DrawStyle,
                   int UpdateStyle)
: CGearTrk(pRect,pWnd,TrackStyle,DrawStyle,UpdateStyle)
{
    m_IconZoom = IconZoom;
}

CIconTrk::~CIconTrk()
{     
}

void CIconTrk::Serialize(CArchive& Arch)
{

   CTrk::Serialize(Arch);    

}

void CIconTrk::Draw(CDC* pDC,LPCRECT pDevRect,double Zoom) const
{  
   double NewZoom; 
   CRect  RectDC(pDevRect);
   
   NewZoom = m_IconZoom;
         
   if (Zoom != NewZoom)
         RectDC = GetNewRectDC(m_Rect,RectDC,NewZoom);  
   
   CTrk::Draw(pDC,RectDC,Zoom);
}


int CIconTrk::GetHitCode(POINT Point,CWnd* pWnd,double Zoom) const
{  
   double     NewZoom;
   CRect      Rect(m_Rect); 
   int        HitCode; 
      
   LogToDev(Rect,pWnd,Zoom); 
      
   NewZoom = m_IconZoom;   
      
   if (Zoom != NewZoom)
         Rect = GetNewRectDC(m_Rect,Rect,NewZoom); 
            
   if(PointInRect(Rect,Point))
      HitCode = CTrk::HC_Middle;
   else 
      HitCode = CTrk::HC_Outside; 
   
   return(HitCode); 
}

void CIconTrk::GetVisibleFrmRect(LPRECT pRect,CDC* pDC,
                                 double Zoom) const
{  

   CTrk::GetVisibleFrmRect(pRect,pDC,Zoom);
   
   double NewZoom; 
   CRect  RectDC(pRect);
   
   NewZoom = m_IconZoom;    
      
   if (Zoom != NewZoom)
      RectDC = GetNewRectDC(m_Rect,RectDC,NewZoom);
         
   *pRect = RectDC;   
   
   int Off = GetFrameSize();
   ::InflRect(pRect,Off,pRect);
   
   (pRect->right)++;
   (pRect->bottom)++;  
   
   (pRect->left)--;
   (pRect->top)--; 
      
} 


void CIconTrk::DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
          CWnd* pWnd,double Zoom,LPCRECT pRect) const
{ 
   double     NewZoom;
   CRect      Rect(pRect);
           
   NewZoom = m_IconZoom;   
      
   if (Zoom != NewZoom)
         Rect = GetNewRectDC(m_Rect,Rect,NewZoom); 
         
   CTrk::DrawTrackRect(RubberBand,Handle,Point,pWnd,Zoom,Rect);
}



/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  Funciones generales                                                                //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////


/*

   GetNewRectDC:Dada la posición de un rectángulo con un zoom, calcula
                su posición con un zoom distinto, de forma que el punto
                del centro coincida en ambos
*/

CRect GetNewRectDC(const CRect& RectLog,const CRect& RectDC,double NewZoom) 
{ 
   CPoint CenterPoint; 
   CRect  NewRect;
       
   CenterPoint.x = RectDC.left + (RectDC.Width()/2); 
   CenterPoint.y = RectDC.top + (RectDC.Height()/2); 
   
      
   NewRect.left   = CenterPoint.x - (int)((RectLog.Width()*NewZoom)/2);
   NewRect.top    = CenterPoint.y - (int)((RectLog.Height()*NewZoom)/2); 
   NewRect.bottom = NewRect.top + (int)(RectLog.Height()*NewZoom); 
   NewRect.right  = NewRect.left + (int)(RectLog.Width()*NewZoom); 
   
   return(NewRect);
} 


void ZoomText(CDC* pDC,const CString& Text,const CRect& SrcRect,
              const CRect& DstRect,const ICFont FAR* pFontText,int Just,
              DWORD StretchRop)
{        
   CDC MemDC;
   MemDC.CreateCompatibleDC(NULL);

   CBitmap Bmp;
   Bmp.CreateCompatibleBitmap(&MemDC,SrcRect.Width(),SrcRect.Height());

   CBitmap* pOldBmp  = MemDC.SelectObject(&Bmp);
   CFont*   pOldFont = MemDC.SelectObject((CFont*)pFontText); 
  
   CRect ClipRect(0,0,SrcRect.Width(),SrcRect.Height());

   UINT Frm = DT_WORDBREAK;

   if (Just == LEFT_JUSTIFY)
      Frm = Frm | DT_LEFT;
   else
   {
      if (Just == RIGHT_JUSTIFY)
         Frm = Frm | DT_RIGHT;
      else
         Frm = Frm | DT_CENTER;
   }

   MemDC.PatBlt(0,0,SrcRect.Width(),SrcRect.Height(),PATCOPY);

   MemDC.SetBkMode(OPAQUE);
   MemDC.DrawText(Text,-1,ClipRect,Frm);

   pDC->StretchBlt(DstRect.left,DstRect.top,
                   DstRect.Width(),DstRect.Height(),
                   &MemDC,
                   0,0,
                   SrcRect.Width(),SrcRect.Height(),
                   StretchRop);

   MemDC.SelectObject(pOldBmp);
   MemDC.SelectObject(pOldFont);   
}

LONG GetSysUserName(CString& Name)
{
   LONG  Err = 0;
   DWORD Ret;
   TCHAR Buf[256];
   DWORD BufLen = 256;

   Name.Empty();

   Ret = ::WNetGetUser(NULL,Buf,&BufLen);
   if (Ret != NO_ERROR) {Err = -1;goto End;}

   Name = Buf;

End:
   return(Err);
}

void GetTime(CString& Time)
{
   int Y,M,D,H,m,S;
   CTime t = CTime::GetCurrentTime();
   Y = t.GetYear();
   M = t.GetMonth();
   D = t.GetDay();
   H = t.GetHour();
   m = t.GetMinute();
   S = t.GetSecond();
   Time.Format("%d/%d/%d %d:%d:%d",Y,M,D,H,m,S);
}


CPoint DCToImg(HIGEAR hIGear, CPoint point)
{
   CPoint            pointImg(0,0);
   AT_POINT          aux;
   LPFNValid         lpfn = NULL;
   LPFNDeviceToImage lpfnDCToImg = NULL;
   HINSTANCE         hModGear = _Module.m_hModGear;

   lpfn = (LPFNValid)GetProcAddress(hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {goto End;}
   lpfnDCToImg = (LPFNDeviceToImage)GetProcAddress(hModGear,"IG_display_device_to_image");
   if (lpfnDCToImg == NULL) {goto End;}
 
   aux.x = point.x;
   aux.y = point.y;

   if (lpfn(hIGear))
   {
      lpfnDCToImg(hIGear,&aux,1);
      pointImg.x = aux.x;
      pointImg.y = aux.y;
   }

   End:

   return(pointImg);

}


CPoint ImgToDC(HIGEAR hIGear, CPoint point)
{
   CPoint            pointDC(0,0);
   AT_POINT          aux;
   LPFNValid         lpfn = NULL;
   LPFNImageToDevice lpfnImgToDC = NULL;   
   HINSTANCE         hModGear = _Module.m_hModGear;
   

   lpfn = (LPFNValid)GetProcAddress(hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {goto End;}
   lpfnImgToDC = (LPFNImageToDevice)GetProcAddress(hModGear,"IG_display_image_to_device");
   if (lpfnImgToDC == NULL) {goto End;}
   
   aux.x = point.x;
   aux.y = point.y;

   if (lpfn(hIGear))
   {
      lpfnImgToDC(hIGear,&aux,1);

      pointDC.x = aux.x;
      pointDC.y = aux.y;    
   }

   End:

   return(pointDC);
}