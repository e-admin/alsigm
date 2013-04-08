#include "stdafx.h"
#include "iDocGear.h"
#include "trk.h"
//#include "img.h"
#include "trksel.h"  
#include "vw.h"
#include "AuxFnc.h"

IMPLEMENT_SERIAL(CSelGearTrk,CTrk,0)

CSelGearTrk::CSelGearTrk()
: CTrk(CRect(0,0,0,0),TS_Resize|TS_Invert,DS_Border,US_Repaint)
{//TS_Move|TS_Resize|,|DS_ResizeHandles
   m_pWnd = 0;
}

CSelGearTrk::~CSelGearTrk()
{
   m_pWnd = 0;
}

CView* CSelGearTrk::GetParentView()
{
   return(m_pWnd);
}
void CSelGearTrk::SetParentView(CView* pWnd)
{
   m_pWnd = pWnd;
}

void CSelGearTrk::Serialize(CArchive& Arch)
{
   CTrk::Serialize(Arch);
}

CPoint CSelGearTrk::DCToImg(CPoint point) const
{
   CPoint            pointImg(0,0);
   AT_POINT          aux;
   LPFNValid         lpfn;
   LPFNDeviceToImage lpfnDCToImg;
   HIGEAR            hIGear = ((CVw*)m_pWnd)->GetImage();
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

CPoint CSelGearTrk::ImgToDC(CPoint point) const
{
   CPoint            pointDC(0,0);
   AT_POINT          aux;
   LPFNValid         lpfn;
   LPFNImageToDevice lpfnImgToDC;
   HIGEAR            hIGear = ((CVw*)m_pWnd)->GetImage();
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


void CSelGearTrk::LogToDev(LPRECT pRect,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint     ptOrg,ptFin;      
      
      ptOrg.x = pRect->left;
      ptOrg.y = pRect->top;
      ptFin.x = pRect->right;
      ptFin.y = pRect->bottom;
      ptOrg = ImgToDC(ptOrg);
      ptFin = ImgToDC(ptFin);
      pRect->left = ptOrg.x;
      pRect->top = ptOrg.y;
      pRect->right = ptFin.x;
      pRect->bottom = ptFin.y;
      return;

   }
   else
      CTrk::LogToDev(pRect,pDC,Zoom);

}

void CSelGearTrk::LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pRect,&DC,Zoom);
}

void CSelGearTrk::DevToLog(LPRECT pRect,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg,ptFin;
           
      ptOrg.x = pRect->left;
      ptOrg.y = pRect->top;
      ptFin.x = pRect->right;
      ptFin.y = pRect->bottom;
      ptOrg = DCToImg(ptOrg);
      ptFin = DCToImg(ptFin);
      pRect->left = ptOrg.x;
      pRect->top = ptOrg.y;
      pRect->right = ptFin.x;
      pRect->bottom = ptFin.y;
      return;

   }
   else
      CTrk::LogToDev(pRect,pDC,Zoom);

}

void CSelGearTrk::DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pRect,&DC,Zoom);
}

void CSelGearTrk::LogToDev(LPSIZE pSize,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg;     
      
      ptOrg.x = pSize->cx;
      ptOrg.y = pSize->cy;      
      ptOrg = ImgToDC(ptOrg);
      
      pSize->cx = ptOrg.x;
      pSize->cy = ptOrg.y;      
      return;

   }
   else
      CTrk::LogToDev(pSize,pDC,Zoom);
}

void CSelGearTrk::LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pSize,&DC,Zoom);
}
/*
void CSelGearTrk::DevToLog(LPSIZE pSize,CDC* pDC,double Zoom) const
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

void CSelGearTrk::DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pSize,&DC,Zoom);
}
*/
void CSelGearTrk::LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {      
      CPoint point;
      point.x = pPoint->x;
      point.y = pPoint->y;
       
      point = ImgToDC(point);
      pPoint->x = point.x;
      pPoint->y = point.y;
      
      return;

   }
   else
      CTrk::LogToDev(pPoint,pDC,Zoom);
}

void CSelGearTrk::LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pPoint,&DC,Zoom);
}

void CSelGearTrk::DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom) const
{
   if (m_pWnd != 0)
   {
      CPoint ptOrg;    
      
      ptOrg.x = pPoint->x;
      ptOrg.y = pPoint->y;
      
      ptOrg = DCToImg(ptOrg);
      
      pPoint->x = ptOrg.x;
      pPoint->y = ptOrg.y;
      
      return;

   }
   else
      CTrk::DevToLog(pPoint,pDC,Zoom);
}
void CSelGearTrk::DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom) const
{
   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pPoint,&DC,Zoom);
}