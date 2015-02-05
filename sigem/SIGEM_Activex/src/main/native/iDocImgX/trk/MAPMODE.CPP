
#include <afxwin.h>
#include <afxext.h>
#include "mapmode.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

void LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom)
{

   pPoint->x = (int)(pPoint->x * Zoom);
   pPoint->y = (int)(pPoint->y * Zoom);

   pDC->LPtoDP(pPoint);

}

void LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pPoint,&DC,Zoom);

}

void LogToDev(LPSIZE pSize,CDC* pDC,double Zoom)
{

   pSize->cx = (int)(pSize->cx * Zoom);
   pSize->cy = (int)(pSize->cy * Zoom);

   pDC->LPtoDP(pSize);

}

void LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pSize,&DC,Zoom);

}

void LogToDev(LPRECT pRect,CDC* pDC,double Zoom)
{

   pRect->left   = (int)(pRect->left   * Zoom);
   pRect->top    = (int)(pRect->top    * Zoom);
   pRect->right  = (int)(pRect->right  * Zoom);
   pRect->bottom = (int)(pRect->bottom * Zoom);

   pDC->LPtoDP(pRect);

}

void LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   LogToDev(pRect,&DC,Zoom);

}

void DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom)
{

   pDC->DPtoLP(pPoint);

   pPoint->x = (int)(pPoint->x / Zoom);
   pPoint->y = (int)(pPoint->y / Zoom);

}

void DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pPoint,&DC,Zoom);

}

void DevToLog(LPSIZE pSize,CDC* pDC,double Zoom)
{

   pDC->DPtoLP(pSize);

   pSize->cx = (int)(pSize->cx / Zoom);
   pSize->cy = (int)(pSize->cy / Zoom);

}

void DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pSize,&DC,Zoom);

}

void DevToLog(LPRECT pRect,CDC* pDC,double Zoom)
{

   pDC->DPtoLP(pRect);

   pRect->left   = (int)(pRect->left   / Zoom);
   pRect->top    = (int)(pRect->top    / Zoom);
   pRect->right  = (int)(pRect->right  / Zoom);
   pRect->bottom = (int)(pRect->bottom / Zoom);

}

void DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom)
{

   CClientDC DC(pWnd);

   if ( pWnd->IsKindOf(RUNTIME_CLASS(CView)) )
      ((CView*)pWnd)->OnPrepareDC(&DC);

   DevToLog(pRect,&DC,Zoom);

}

