
#ifndef __TRKDRAW_H__
#define __TRKDRAW_H__

#include "trk.h" 

#include "trksel.h"
#include "vw.h"
#include "ifont.h"

//Tipos de status
#define IDOC_ANN_STATUS_DEF 0
#define IDOC_ANN_STATUS_NEW 1
#define IDOC_ANN_STATUS_UPD 2
#define IDOC_ANN_STATUS_DEL 3

//#define IDOC_ID_NULL 0
///////// PRUEBA ///////////
////////////////////////////
class CGearTrk: public CTrk
{
   DECLARE_SERIAL(CGearTrk);
   public:

   CGearTrk();
   CGearTrk(LPCRECT pRect,CVw* pWnd,
   int	 TrackStyle  = TS_Move|TS_Resize|TS_Invert,
   int	 DrawStyle   = DS_Border|DS_ResizeHandles,
   int	 UpdateStyle = US_Invalidate);
   
   CVw* GetParentView();
   void SetParentView(CVw* pWnd);

   virtual ~CGearTrk();   

   virtual void Serialize(CArchive& Arch);
   virtual BOOL NeedDraw(CDC* pDC,double Zoom);
   
   virtual void LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom= 1) const;
   virtual void LogToDev(LPSIZE pSize,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom= 1) const;
   virtual void LogToDev(LPRECT pRect,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom= 1) const;
   virtual void DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom= 1) const;
   virtual void DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom= 1) const;
   //virtual void DevToLog(LPSIZE pSize,CDC* pDC,double Zoom= 1) const;
   //virtual void DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom= 1) const;
   virtual void DevToLog(LPRECT pRect,CDC* pDC,double Zoom= 1) const;
   virtual void DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom= 1) const;

   public:   ////Cambiar a protected

   CVw* m_pWnd;

};

class CTrkGear : public CTrkObj
{

   DECLARE_SERIAL(CTrkGear);

   public:

   CTrkGear(LONG ObjClassId);
   CTrkGear(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,            
            int     Style       = STL_Move|STL_Resize|STL_Invert,
            int     State       = STT_Normal,
            int     UpdateStyle = UPD_Invalidate);
   CTrkGear(LONG ObjClassId,CTrk* pTrk);

   CVw* GetParentView();
   void SetParentView(CVw* pWnd);

   virtual ~CTrkGear();

   virtual void Serialize(CArchive& Arch);
   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const;
   virtual void OnDraw(CDC* pDC,double Zoom = 1) const;
   virtual void OnContextMenu(CWnd* pWnd, CPoint point); 
   virtual BOOL PropertyInfo(CWnd* pWnd,CPoint point);
   virtual void Move(LPCRECT pPostRect,CWnd* pWnd,double Zoom = 1,int UpdateMode = UPDATE_THIS);
 
   void GetCreatorName(CString& CrtName) const;    
   void SetCreatorName(CString& CrtName);
   void GetCreatorDate(CString& CrtDate) const;    
   void SetCreatorDate(CString& CrtDate);
   void GetUpdateName(CString& UpdName) const;    
   void SetUpdateName(CString& UpdName);
   void GetUpdateDate(CString& UpdDate) const;    
   void SetUpdateDate(CString& UpdDate);
   void GetModify(BOOL& modify) const;
   void SetModify(BOOL modify);
   void GetDelete(BOOL& Del) const;
   void SetDelete(BOOL Del);
   void GetStatus(int& status) const; 
   void SetStatus(int status);
   void Init();
   
   
   protected:
   
   CTrkGear();

   public:
   LONG    m_Id;         //Identificador del item(válido para invesdoc)
   protected:
      
   CVw* m_pWnd;
   
   CString m_CrtName;    //Usuario que creó el item    	
   CString m_CrtDate;    //Fecha de creación
   CString m_UpdName;    //Usuario que actualizó el item
   CString m_UpdDate;    //Fecha de actualización
   BOOL    m_IsModify;     //Si se puede modificar o no
   BOOL    m_IsDelete;    //Si se puede borrar o no
   int     m_StatusRead; // IDOC_ANN_STAT_ /DEF /NEW /DEL /UPD (el que se lee del fichero)
   int     m_Status;     // IDOC_ANN_STAT_ /DEF /NEW /DEL /UPD    
   
}; 
 
//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CLineTrk                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////  

class CLineTrk : public CGearTrk //CTrk
{

   DECLARE_SERIAL(CLineTrk);

   public:

   CLineTrk();
   CLineTrk(LPCRECT pRect,
            CVw*    pWnd        = 0,
            BOOL    DownUp      = TRUE,
            int     TrackVStyle  = TS_Move|TS_Resize|TS_Invert,
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

class CTrkLine : public CTrkGear  //CTrkObj
{

   DECLARE_SERIAL(CTrkLine);

   public:

   CTrkLine(LONG ObjClassId);
   CTrkLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,
            BOOL    DownUp      = TRUE,
            int     Style       = STL_Move|STL_Resize|STL_Invert,
            int     State       = STT_Normal,
            int     UpdateStyle = UPD_Invalidate);
   CTrkLine(LONG ObjClassId,CTrk* pTrk);

   virtual ~CTrkLine();

   virtual void Serialize(CArchive& Arch);
   
   COLORREF GetColor() const;
   void SetColor(COLORREF Color);
   void SetDownUp(BOOL DownUp); 
   BOOL IsDownUp() const;  

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;

   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const;

   virtual void  SetObjDefaults(CTrk* pTrk); 
   
   protected:
   
   CTrkLine();

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
             CVw*    pWnd        = 0,   
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

   CTrkArrow(LONG ObjClassId);
   CTrkArrow(LONG ObjClassId,LPCRECT pRect,CVw* pWnd,
             BOOL    DownUp      = TRUE,
             BOOL    Up          = TRUE,
             int     Style       = STL_Move|STL_Resize|STL_Invert,
             int     State       = STT_Normal,
             int     UpdateStyle = UPD_Invalidate);
   CTrkArrow(LONG ObjClassId,CTrk* pTrk);

   virtual ~CTrkArrow();

   virtual void Serialize(CArchive& Arch);

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;

   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const;

   virtual void  SetObjDefaults(CTrk* pTrk); 
   
   void SetUp(BOOL DownUp);
   BOOL IsUp() const;
   
   protected:
   
   CTrkArrow(); 

   protected:

   BOOL m_Up;

};


//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CTrkText                                                       //
//                                                                  //
//////////////////////////////////////////////////////////////////////

#define LEFT_JUSTIFY    0
#define CENTER_JUSTIFY  1
#define RIGHT_JUSTIFY   2  

class CTrkText: public CTrkGear  //CTrkObj
{

   DECLARE_SERIAL(CTrkText);

   public:

   CTrkText(LONG ObjClassId);
   CTrkText(LONG ObjClassId,LPCRECT pRect,CVw* pWnd); 
   CTrkText(LONG ObjClassId,CTrk* pTrk); 
   CTrkText(LONG ObjClassId,CTrk* pTrk,const ICFont& Font);
   CTrkText(LONG ObjClassId,CTrk* pTrk,LOGFONT LogFont,int FontSize);    

   virtual ~CTrkText();

   virtual void Serialize(CArchive& Arch);    
                                
   virtual void DrawTrueObj(CDC* pDC,LPCRECT pRect,double Zoom) const;   
   
   void GetFont(ICFont& Font) const;  
   void GetFont(LOGFONT& LogFont) const; 
   int  GetFontSize() const; 
   int  GetJustify() const; 
   COLORREF GetColor() const;
   
   void SetColor(COLORREF Color);       
   void SetFont(const ICFont& Font); 
   void SetFont(const LOGFONT& LogFont);
   void SetJustify(int Justify);  
   
   virtual void SetInitDefaults();       
  
   protected:
   
   CTrkText();
   
   protected:  
   
   COLORREF m_Color;  
   CString  m_Text ;    //Texto asociado  
   ICFont   m_Font;     //Fuente asociada al texto    
   int      m_Justify;  //Posición del texto dentro del rectángulo que lo
                         //contiene                                    
};


//////////////////////////////////////////////////////////////////////
//                                                                  //
//  CIconTrk                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class CIconTrk : public CGearTrk //CTrk
{

   DECLARE_SERIAL(CIconTrk);

   public:

   CIconTrk();
   CIconTrk(LPCRECT pRect,CVw* pWnd, 
            double  IconZoom,
            int     TrackStyle  = TS_Move,
            int     DrawStyle   = DS_Border,
            int     UpdateStyle = US_Invalidate);

   virtual ~CIconTrk();

   virtual void Serialize(CArchive& Arch);  
   
   virtual void Draw(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;
   
   virtual int GetHitCode(POINT Point,CWnd* pWnd,double Zoom = 1) const; 
   
   virtual void DrawTrackRect(BOOL RubberBand,int Handle,POINT Point,
                 CWnd* pWnd,double Zoom,LPCRECT pRect) const; 
                 
   virtual void GetVisibleFrmRect(LPRECT pRect,CDC* pDC,double Zoom = 1) const; 
   
   protected:
   
   double m_IconZoom;               
   
}; 

CRect GetNewRectDC(const CRect& RectLog,const CRect& RectDC,double NewZoom); 
void ZoomText(CDC* pDC,const CString& Text,const CRect& SrcRect,
              const CRect& DstRect,const ICFont FAR* pFontText,int Just,
              DWORD StretchRop);              
LONG GetSysUserName(CString& Name);
void GetTime(CString& Time);
CPoint ImgToDC(HIGEAR hIGear,CPoint point);
CPoint DCToImg(HIGEAR hIGear,CPoint point);

#endif // __TRKDRAW_H__

