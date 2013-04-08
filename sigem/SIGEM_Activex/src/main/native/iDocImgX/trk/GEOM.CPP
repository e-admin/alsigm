
#include <afxwin.h>
#include <afxext.h>
#include <math.h>
#include "mapmode.h"
#include "geom.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

BOOL IsSizeNull(SIZE Size)
{

   BOOL IsNull = TRUE;

   if ( (Size.cx != 0) || (Size.cy != 0) )
      IsNull = FALSE;

   return(IsNull);

}

BOOL IsRectNull(LPCRECT pRect)
{

   BOOL IsNull = TRUE;

   if ( (pRect->left  != 0) || (pRect->top    != 0) ||
        (pRect->right != 0) || (pRect->bottom != 0) )
      IsNull = FALSE;

   return(IsNull);

}

void InflRect(LPCRECT pSrcRect,int Off,LPRECT pDstRect)
{

   pDstRect->left   = pSrcRect->left   - Off;
   pDstRect->top    = pSrcRect->top    - Off;
   pDstRect->right  = pSrcRect->right  + Off;
   pDstRect->bottom = pSrcRect->bottom + Off;

   if (Off < 0)
   {

      int XMiddle = GetMiddle(pSrcRect->right,pSrcRect->left);
      int YMiddle = GetMiddle(pSrcRect->bottom,pSrcRect->top);

      if (pDstRect->left   > XMiddle) pDstRect->left   = XMiddle;
      if (pDstRect->top    > YMiddle) pDstRect->top    = YMiddle;
      if (pDstRect->right  < XMiddle) pDstRect->right  = XMiddle;
      if (pDstRect->bottom < YMiddle) pDstRect->bottom = YMiddle;

   }

}

void OffRect(LPCRECT pSrcRect,SIZE Off,LPRECT pDstRect)
{
   pDstRect->left   = pSrcRect->left   + Off.cx;
   pDstRect->top    = pSrcRect->top    + Off.cy;
   pDstRect->right  = pSrcRect->right  + Off.cx;
   pDstRect->bottom = pSrcRect->bottom + Off.cy;
}

BOOL IsRectIntToRect(LPCRECT pRect1,LPCRECT pRect2)
{

   BOOL IsInt = TRUE;

   if ( (pRect1->left  < pRect2->left)  || (pRect1->top    < pRect2->top) ||
        (pRect1->right > pRect2->right) || (pRect1->bottom > pRect2->bottom) )
      IsInt = FALSE;

   return(IsInt);

}

BOOL IsRectExtToRect(LPCRECT pRect1,LPCRECT pRect2)
{

   BOOL IsExt = TRUE;

   if ( (pRect1->left  > pRect2->left)  || (pRect1->top    > pRect2->top) ||
        (pRect1->right < pRect2->right) || (pRect1->bottom < pRect2->bottom) )
      IsExt = FALSE;

   return(IsExt);

}

BOOL PointInRect(LPCRECT pRect,POINT Point)
{

   BOOL Ret = TRUE;

   if (Point.x < pRect->left)
   {
      Ret = FALSE;
      goto End;
   }

   if (Point.y < pRect->top)
   {
      Ret = FALSE;
      goto End;
   }

   if (Point.x > pRect->right)
   {
      Ret = FALSE;
      goto End;
   }

   if (Point.y > pRect->bottom)
   {
      Ret = FALSE;
      goto End;
   }

   End:

   return(Ret);

}

BOOL InterxRect(LPCRECT pRect1,LPCRECT pRect2,LPRECT pIntersectRect)
{

   BOOL Intersect = TRUE;

   if (pRect1->left > pRect2->left)
      pIntersectRect->left = pRect1->left;
   else
      pIntersectRect->left = pRect2->left;

   if (pRect1->top > pRect2->top)
      pIntersectRect->top = pRect1->top;
   else
      pIntersectRect->top = pRect2->top;

   if (pRect1->right < pRect2->right)
      pIntersectRect->right = pRect1->right;
   else
      pIntersectRect->right = pRect2->right;

   if (pRect1->bottom < pRect2->bottom)
      pIntersectRect->bottom = pRect1->bottom;
   else
      pIntersectRect->bottom = pRect2->bottom;

   if ( (pIntersectRect->left > pIntersectRect->right ) ||
        (pIntersectRect->top  > pIntersectRect->bottom) )
      Intersect = FALSE;

   return(Intersect);

}

void DrawFocusRect(LPCRECT pRect,CDC* pDC,double Zoom)
{

   CRect Rect(pRect);
   LogToDev(Rect,pDC,Zoom);

   int DCStat = pDC->SaveDC();

   pDC->SetMapMode(MM_TEXT);
   pDC->SetViewportOrg(0,0);
   pDC->SetWindowOrg(0,0);

   Rect.right++;
   Rect.bottom++;

   pDC->DrawFocusRect(&Rect);

   pDC->RestoreDC(DCStat);

}

void DrawLine(POINT OrgPt,POINT EndPt,CDC* pDC)
{

   pDC->MoveTo(OrgPt);

   //(EndPt.x)++;
   //(EndPt.y)++;

   pDC->LineTo(EndPt);

}

void DrawLine(POINT OrgPt,POINT EndPt,CDC* pDC,COLORREF ColorRef,
              double Zoom)
{

   int W = (int)Zoom;
   if (W < 1) W = 1;

   CPen  Pen(PS_SOLID,W,ColorRef);
   CPen* pOldPen = (CPen*)pDC->SelectObject(&Pen);

   DrawLine(OrgPt,EndPt,pDC);

   pDC->SelectObject(pOldPen);

}

CPoint GetPtoInLine(CPoint Vector,CPoint PtoLine,int DistToPtoLine,BOOL OrtUp)
{
   double X1,X2,X,Y1,Y2,Y,K;
   double A,B,C;
   CPoint PointInLine;


   if (Vector.x == 0)
   {
      X = PtoLine.x;
      A = 1;
      B = -2*((double)PtoLine.y);
      C = ((double)PtoLine.y)*PtoLine.y - ((double)DistToPtoLine)*DistToPtoLine;
      Y1 = ((-B + sqrt(B*B - 4*A*C))/(2*A));
      Y2 = ((-B - sqrt(B*B - 4*A*C))/(2*A));
      if (OrtUp)
      {
         if (Y1 > ((double)PtoLine.y))
            Y = Y1;
         else
            Y = Y2;
      }
      else
      {
         if (Y1 < ((double)PtoLine.y))
            Y = Y1;
         else
            Y = Y2;
      }

      PointInLine = CPoint((int)X,(int)Y);
      return(PointInLine);

   }

   if (Vector.y == 0)
   {
      Y = PtoLine.y;
      A = 1;
      B = -2*((double)PtoLine.x);
      C = ((double)PtoLine.x)*PtoLine.x - ((double)DistToPtoLine)*DistToPtoLine;
      X1 = ((-B + sqrt(B*B - 4*A*C))/(2*A));
      X2 = ((-B - sqrt(B*B - 4*A*C))/(2*A));
      if (OrtUp)
      {
         if (X1 > ((double)PtoLine.x))
            X = X1;
         else
            X = X2;
      }
      else
      {
         if (X1 < ((double)PtoLine.x))
            X = X1;
         else
            X = X2;
      }

      PointInLine = CPoint((int)X,(int)Y);
      return(PointInLine);
   }

   K = (Vector.y*((double)PtoLine.x) - Vector.x*((double)PtoLine.y)) / Vector.y;
   A = (Vector.x*((double)Vector.x))/(Vector.y*((double)Vector.y)) + 1;
   B = (2*K*Vector.x - (2*((double)PtoLine.x)*Vector.x) - (2*((double)PtoLine.y)*Vector.y))/Vector.y;
   C = ((double)PtoLine.x)*PtoLine.x - 2*PtoLine.x*K + K*K + ((double)PtoLine.y)*PtoLine.y
        - (((double)DistToPtoLine)*DistToPtoLine);

   Y1 = ((-B + sqrt(B*B - 4*A*C))/(2*A));
   Y2 = ((-B - sqrt(B*B - 4*A*C))/(2*A));
   X1 = (((double)Vector.x)/Vector.y)*Y1 + K;
   X2 = (((double)Vector.x)/Vector.y)*Y2 + K;

   if (OrtUp)
   {
      if (X1 > ((double)PtoLine.x))
      {
         X = X1;
         Y = Y1;
      }
      else
      {
         X = X2;
         Y = Y2;
      }
   }
   else
   {
      if (X1 < ((double)PtoLine.x))
      {
         X = X1;
         Y = Y1;
      }
      else
      {
         X = X2;
         Y = Y2;
      }
   }

   PointInLine = CPoint((int)X,(int)Y);

   return(PointInLine);

}

void DrawArrow(CPoint OrgPt,CPoint EndPt,int H,int L,CDC* pDC)
{
   CPoint  HPoint,LLeftPoint,LRightPoint;
   BOOL    ArrowUp;
   int     DistToEndPt;
   double  Distance;
   CPoint  VectorArrow(EndPt.x - OrgPt.x, EndPt.y - OrgPt.y);
   CPoint  VectorNormalArrow(-VectorArrow.y,VectorArrow.x);
   POINT   ArrayArrow[3];

   // Up = TRUE significa que HPoint.x >= EndPt.x

   if( ((EndPt.x <= OrgPt.x) && (EndPt.y < OrgPt.y))
        || ((EndPt.x < OrgPt.x) && (EndPt.y >= OrgPt.y)))
      ArrowUp = TRUE;
   else
      ArrowUp = FALSE;

   Distance = sqrt( ((double)VectorArrow.x)*VectorArrow.x +
                    ((double)VectorArrow.y)*VectorArrow.y );
   if ( H > (int)Distance)
      DistToEndPt = (int)Distance;
   else
      DistToEndPt = H;

   HPoint     = GetPtoInLine(VectorArrow,EndPt,DistToEndPt,ArrowUp);
   LLeftPoint = GetPtoInLine(VectorNormalArrow,HPoint,L,FALSE);
   LRightPoint.x = 2*HPoint.x - LLeftPoint.x;
   LRightPoint.y = 2*HPoint.y - LLeftPoint.y;

   pDC->MoveTo(OrgPt);
   pDC->LineTo(EndPt);
   ArrayArrow[0] = EndPt;
   ArrayArrow[1] = LLeftPoint;
   ArrayArrow[2] = LRightPoint;
   pDC->Polygon(ArrayArrow,3);
}

void DrawArrow(POINT OrgPt,POINT EndPt,CDC* pDC,COLORREF ColorRef,double Zoom)
{

   //(EndPt.x)++;
   //(EndPt.y)++;

   int W = (int)Zoom;
   if (W < 1) W = 1;

   int H = (int)(10 * Zoom);
   int L = (int)( 3 * Zoom);

   CPen    Pen(PS_SOLID,W,ColorRef);
   CBrush  Brush(ColorRef);
   CPen*   pOldPen   = (CPen*)pDC->SelectObject(&Pen);
   CBrush* pOldBrush = (CBrush*)pDC->SelectObject(&Brush);

   DrawArrow(OrgPt,EndPt,H,L,pDC);

   pDC->SelectObject(pOldPen);
   pDC->SelectObject(pOldBrush);

}

int GetMiddle(int X1,int X2)
{
   return( (X1 + X2) / 2 );
}

