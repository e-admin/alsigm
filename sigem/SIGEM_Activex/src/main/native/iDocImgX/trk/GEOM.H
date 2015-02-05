
#ifndef __GEOM_H__
#define __GEOM_H__

BOOL IsSizeNull(SIZE Size);
BOOL IsRectNull(LPCRECT pRect);

void InflRect(LPCRECT pSrcRect,int Off,LPRECT pDstRect);
void OffRect(LPCRECT pSrcRect,SIZE Off,LPRECT pDstRect);

BOOL IsRectIntToRect(LPCRECT pRect1,LPCRECT pRect2);
BOOL IsRectExtToRect(LPCRECT pRect1,LPCRECT pRect2);

BOOL PointInRect(LPCRECT pRect,POINT Point);

BOOL InterxRect(LPCRECT pRect1,LPCRECT pRect2,LPRECT pIntersectRect);

void DrawFocusRect(LPCRECT pRect,CDC* pDC,double Zoom = 1);

void DrawLine(POINT OrgPt,POINT EndPt,CDC* pDC);
void DrawLine(POINT OrgPt,POINT EndPt,CDC* pDC,COLORREF ColorRef,
	      double Zoom = 1);

void DrawArrow(POINT OrgPt,POINT EndPt,CDC* pDC,COLORREF ColorRef,
	       double Zoom = 1);

int GetMiddle(int X1,int X2);

#endif // __GEOM_H__

