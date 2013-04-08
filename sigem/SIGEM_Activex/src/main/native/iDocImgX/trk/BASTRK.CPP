
#include <afxwin.h>
#include <afxext.h>
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CFixTrk                                                         //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CFixTrk,CTrk,0)

CFixTrk::CFixTrk()
: CTrk()
{
}

CFixTrk::CFixTrk(LPCRECT pRect,int TrackStyle,int DrawStyle,int UpdateStyle)
: CTrk(pRect,TrackStyle,DrawStyle,UpdateStyle)
{
}

CFixTrk::~CFixTrk()
{
}

void CFixTrk::Serialize(CArchive& Arch)
{
   CTrk::Serialize(Arch);
}

BOOL CFixTrk::OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
                            double Zoom,int UpdateMode)
{
    return( Track(Point,Flags,pWnd,Zoom,UpdateMode) );
}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CSelTrk                                                         //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CSelTrk,CTrk,0)

CSelTrk::CSelTrk()
: CTrk()
{
   SetTrackStyle(TS_Resize|TS_Invert);
}

CSelTrk::~CSelTrk()
{
}

void CSelTrk::Serialize(CArchive& Arch)
{
   CTrk::Serialize(Arch);
}

BOOL CSelTrk::OnLButtonDown(POINT Point,UINT Flags,
                            CWnd* pWnd,double Zoom)
{

   BOOL Changed = TrackRubberBand(Point,Flags,pWnd,Zoom);

   if ( !Changed )
      SetRect(CRect(0,0,0,0));

   return(Changed);

}

BOOL CSelTrk::OnSetCursor(CWnd* /*pWnd*/,UINT /*HitTest*/,UINT /*Msg*/,
                          double /*Zoom*/) const
{
   return(FALSE);
}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CCutTrk                                                         //
//                                                                  //
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CCutTrk,CTrk,0)

CCutTrk::CCutTrk()
: CTrk()
{
}

CCutTrk::CCutTrk(LPCRECT pRect,int TrackStyle,int DrawStyle,int UpdateStyle)
: CTrk(pRect,TrackStyle,DrawStyle,UpdateStyle)
{
}

CCutTrk::~CCutTrk()
{
}

void CCutTrk::Serialize(CArchive& Arch)
{
   CTrk::Serialize(Arch);
}

BOOL CCutTrk::OnLButtonDown(POINT Point,UINT Flags,CWnd* pWnd,
                            double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;

   int HC = GetHitCode(Point,pWnd,Zoom);

   if (HC == HC_Outside)
   {
      Update(UpdateMode,pWnd,Zoom);
      Changed = TrackRubberBand(Point,Flags,pWnd,Zoom);
      if (Changed)
         Update(UpdateMode,pWnd,Zoom);
      else
         SetRect(CRect(0,0,0,0));
      Changed = TRUE;
   }
   else
      Changed = Track(Point,Flags,pWnd,Zoom,UpdateMode);

   return(Changed);

}

