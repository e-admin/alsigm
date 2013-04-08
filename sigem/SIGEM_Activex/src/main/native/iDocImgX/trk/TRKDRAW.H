
#ifndef __TRKDRAW_H__
#define __TRKDRAW_H__

#include "trk.h"

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CLineTrk                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class CLineTrk : public CTrk
{

   DECLARE_SERIAL(CLineTrk);

   public:

   CLineTrk();
   CLineTrk(LPCRECT pRect,
            BOOL    DownUp      = TRUE,
            int     TrackStyle  = TS_Move|TS_Resize|TS_Invert,
            int     DrawStyle   = DS_Border|DS_ResizeHandles,
            int     UpdateStyle = US_Invalidate);

   virtual ~CLineTrk();

   virtual void Serialize(CArchive& Arch);

   BOOL IsDownUp() const;

   virtual int GetHitCode(POINT Point,CWnd* pWnd,double Zoom = 1) const;

   virtual void TrackHandleDone(BOOL Changed,int Inversion,BOOL RubberBand,
                                int Handle,POINT Point,
                                CWnd* pWnd,double Zoom = 1);

   virtual void DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
                              CWnd* pWnd,double Zoom,LPCRECT pRect) const;

   virtual void GetTrackLine(LPCRECT pTrackRect,int Handle,
                             LPPOINT pOrgPt,LPPOINT pEndPt) const;

   virtual void DrawBorder(CDC* pDC,LPCRECT pDevRect) const;
   virtual void DrawTrueResizeHandle(int Handle,CDC* pDC,
                                     LPCRECT pDevRect) const;

   protected:

   BOOL m_DownUp;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkLine                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class CTrkLine : public CTrkObj
{

   DECLARE_SERIAL(CTrkLine);

   public:

   CTrkLine();
   CTrkLine(LPCRECT pRect,
            BOOL    DownUp      = TRUE,
            int     Style       = STL_Move|STL_Resize|STL_Invert,
            int     State       = STT_Normal,
            int     UpdateStyle = UPD_Invalidate);
   CTrkLine(CTrk* pTrk);

   virtual ~CTrkLine();

   virtual void Serialize(CArchive& Arch);

   void SetColor(COLORREF Color);

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;

   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const;

   virtual void  SetObjDefaults(CTrk* pTrk);

   protected:

   BOOL     m_DownUp;
   COLORREF m_Color;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CArrowTrk                                                       //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class CArrowTrk : public CLineTrk
{

   DECLARE_SERIAL(CArrowTrk);

   public:

   CArrowTrk();
   CArrowTrk(LPCRECT pRect,
             BOOL    DownUp      = TRUE,
             BOOL    Up          = TRUE,
             int     TrackStyle  = TS_Move|TS_Resize|TS_Invert,
             int     DrawStyle   = DS_Border|DS_ResizeHandles,
             int     UpdateStyle = US_Invalidate);

   virtual ~CArrowTrk();

   virtual void Serialize(CArchive& Arch);

   BOOL IsUp() const;

   virtual void TrackHandleDone(BOOL Changed,int Inversion,BOOL RubberBand,
                                int Handle,POINT Point,
                                CWnd* pWnd,double Zoom = 1);

   virtual void GetVisibleFrmRect(LPRECT pRect,CDC* pDC,
                                  double Zoom = 1) const;

   protected:

   BOOL m_Up;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkArrow                                                       //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class CTrkArrow : public CTrkLine
{

   DECLARE_SERIAL(CTrkArrow);

   public:

   CTrkArrow();
   CTrkArrow(LPCRECT pRect,
             BOOL    DownUp      = TRUE,
             BOOL    Up          = TRUE,
             int     Style       = STL_Move|STL_Resize|STL_Invert,
             int     State       = STT_Normal,
             int     UpdateStyle = UPD_Invalidate);
   CTrkArrow(CTrk* pTrk);

   virtual ~CTrkArrow();

   virtual void Serialize(CArchive& Arch);

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;

   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const;

   virtual void  SetObjDefaults(CTrk* pTrk);

   protected:

   BOOL m_Up;

};

#endif // __TRKDRAW_H__

