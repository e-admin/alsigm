// objnote.h
// Clases de objetos que se pueden añadir a la lista de anotaciones

#ifndef __OBJNOT_H__
#define __OBJNOT_H__  
 
#include "trkdraw.h"
#include "ico.h"

class ICDocAnn;



class CNote : public CTrkGear //CTrkObj
{

   DECLARE_SERIAL(CNote);

   public:

   CNote(LONG ObjClassId);
   CNote(LONG ObjClassId,LPCRECT pRect,CVw* pWnd); 
   CNote(LONG ObjClassId,CTrk* pTrk);  
   
   virtual ~CNote();

   virtual void Serialize(CArchive& Arch); 
   
   virtual void OnLButtonDblClk(POINT Point,UINT Flags,CWnd* pWnd,double Zoom);
   
   virtual void SetExtRect(LPCRECT pRect);  
   
   virtual CTrk* CreateTrk() const;
   virtual CTrk* CreateTrkNew() const; 
   
   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
                            double Zoom) const; 
   
                                      
   //virtual int ReadObjFile(ICFile& File); 
   virtual int ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File);  
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual void SetExeMode(int Mode);   
   
   void RotateNote(double Angle,CSize ImgSize);                                                                
   
   void GetText(CString& Text) const;  
   double GetIconZoom() const;   
   void SetIconZoom(double IconZoom); 
   void SetText(CString& Text);
   BOOL EditTextNote();
   void SetInitDefaults(); 
   void SetImgNoteIcon(ICIco* NoteIconImg);

   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;   
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);
   virtual void OnContextMenu(CWnd* pWnd, CPoint point);
   virtual BOOL PropertyInfo(CWnd* pWnd,CPoint point);
  
   protected:
   
   CNote();   
   
   private:  

   CString   m_Text;      //Texto asociado a la nota  
   double    m_IconZoom;
   ICIco*    m_pImg; 

   //public:
   //CVw* m_pWnd;
	
};


class CHighLine : public CTrkGear   //CTrkObj
{

   DECLARE_SERIAL(CHighLine);

   public:

   CHighLine(LONG ObjClassId);
   CHighLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd); 
   CHighLine(LONG ObjClassId,CTrk* pTrk);
   
   virtual ~CHighLine();

   virtual void Serialize(CArchive& Arch);

                                   
   //virtual int ReadObjFile(ICFile& File);
   virtual int  ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File); 
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual void SetExeMode(int Mode);
   
   void RotateHighLine(double Angle,CSize ImgSize);                                      
   

   void SetInitDefaults();
   
   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const; 
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);   
 
   protected:
   
   CHighLine();    
   
};
        

class CLine : public CTrkLine
{

   DECLARE_SERIAL(CLine);

   public:

   CLine(LONG ObjClassId);
   CLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd);
   CLine(LONG ObjClassId,CTrk* pTrk); 
   
   virtual ~CLine();

   virtual void Serialize(CArchive& Arch);

   //virtual int ReadObjFile(ICFile& File); 
   virtual int  ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File); 
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual void SetExeMode(int Mode);
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);
   virtual void OnContextMenu(CWnd* pWnd, CPoint point);
   virtual BOOL PropertyInfo(CWnd* pWnd,CPoint point);
   
   void RotateLine(double Angle,CSize ImgSize);                                   
   
   
   private:
   
   void SetInitDefaults(); 
   
   protected:
   
   CLine();

}; 

class CArrow : public CTrkArrow
{

   DECLARE_SERIAL(CArrow);

   public:

   CArrow(LONG ObjClassId);
   CArrow(LONG ObjClassId,LPCRECT pRect,CVw* pWnd);
   CArrow(LONG ObjClassId,CTrk* pTrk); 
   
   virtual ~CArrow();

   virtual void Serialize(CArchive& Arch);

   //virtual int ReadObjFile(ICFile& File); 
   virtual int  ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File); 
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual void SetExeMode(int Mode);
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);
   virtual void OnContextMenu(CWnd* pWnd, CPoint point);
   virtual BOOL PropertyInfo(CWnd* pWnd,CPoint point);
 
   void RotateArrow(double Angle,CSize ImgSize);                                      
   
   private:
   
   void SetInitDefaults(); 
   
   protected:
   
   CArrow();

}; 


class CText: public CTrkText
{

   DECLARE_SERIAL(CText);

   public:

   CText(LONG ObjClassId);
   CText(LONG ObjClassId,LPCRECT pRect,CVw* pWnd); 
   CText(LONG ObjClassId,CTrk* pTrk); 
   CText(LONG ObjClassId,CTrk* pTrk,const ICFont& Font);  

   virtual ~CText();

   virtual void Serialize(CArchive& Arch);

   virtual void OnLButtonDblClk(POINT Point,UINT Flags,
                                CWnd* pWnd,double Zoom = 1); 
   virtual void DrawTrueObj(CDC* pDC,LPCRECT pRect,double Zoom) const; 
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);
   
   void RotateText(double Angle,CSize ImgSize);     
   BOOL EditText();
                                
      
   //virtual int ReadObjFile(ICFile& File); 
   virtual int  ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File);
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual void SetExeMode(int Mode);
   
   virtual void SetInitDefaults();  
   virtual void OnContextMenu(CWnd* pWnd, CPoint point);
   virtual BOOL PropertyInfo(CWnd* pWnd,CPoint point);
      
      
   protected:
   
   CText();
   
   private:      
   
   int     m_BorderArr[4]; //Información sobre los bordes del campo   
      
};


class CMark : public CTrkGear
{
   DECLARE_SERIAL(CMark);
   public:

   CMark(LONG ObjClassId);
   CMark(LONG ObjClassId,LPCRECT pRect,CVw* pWnd); 
   CMark(LONG ObjClassId,CTrk* pTrk);  
   
   virtual ~CMark();

   virtual void Serialize(CArchive& Arch); 
   //virtual int ReadObjFile(ICFile& File); 
   virtual int  ReadObjFile(ICDocAnn* pAnn);
   //virtual BOOL WriteObjFile(ICFile& File); 
   virtual BOOL WriteObjFile(ICDocAnn* pAnn);
   virtual CTrk* CreateTrk() const; 
   virtual CTrk* CreateTrkNew() const;
   virtual void SetExeMode(int Mode); 
   virtual void Select(CWnd* pWnd,double Zoom,int UpdateMode);
 
   
   void SetInitDefaults();    
   
  
   virtual void DrawTrueObj(CDC* pDC,LPCRECT pDevRect,double Zoom = 1) const;   
   void RotateMark(double Angle,CSize ImgSize);
         
   protected:
   
   CMark();   
   
   private:  

   CPoint m_point;
         
};

void DisplayMsgErrorTrk(UINT IdMsg);
void GetDimsToStretchBlt(const CRect& RectLog,CRect& RectDC,double NewZoom,
                        CPoint& OrgPoint,CSize& SizeBmp);
CRect RotateRect(const CRect& Rect,double Angle,CSize SizeImg); 
              
void DrawBorder(CDC* pDC,int PosBorder,int TypeBorder,LPCRECT pRect);

#endif // __OBJNOTE_H__

