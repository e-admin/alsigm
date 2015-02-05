//listnote.h
// Clase que define la lista de anotaciones asociada a una imagen invesdoc 

#ifndef __LISTNOTE_H__
#define __LISTNOTE_H__

#include "trk.h" 


class ICDocAnnDoc;
class ICDocAnn;
class ICDocAnnPage;
class CTrkGear;

class CListNote : public CTrkObjList
{

   DECLARE_SERIAL(CListNote);

   public:

   CListNote();

   virtual ~CListNote();

   virtual void Serialize(CArchive& Arch);                
   
   virtual CTrk* CreateNewObjTrk(LONG ObjClassId) const;
   virtual CTrkObj* CreateObj(LONG ObjClassId,CTrk* pTrk,CWnd* pWnd);
   virtual void SetNewObjTrkDefaults(LONG ObjClassId,CTrk* pTrk) const;
   virtual BOOL OnSetCursor(CWnd* pWnd,UINT HitTest,
                            UINT Msg,double Zoom) const; 
      
   //virtual BOOL ReadNotesFile(const CString& FileName,LONG page,CWnd* pWnd);  
   virtual BOOL ReadNotesFile(ICDocAnnDoc* pAnnDoc,LONG page,CWnd* pWnd);
   //virtual BOOL WriteNotesFile(LONG page);
   virtual BOOL WriteNotesFile(ICDocAnnDoc* pAnnDoc,LONG page);

   virtual BOOL OnNewObj(LONG ObjClassId,POINT Point,UINT Flags,CWnd* pWnd,
                           double Zoom,int UpdateMode);
  
   
   COLORREF GetColorLine() const;
   void SetColorLine(COLORREF ColorLine);
   
   COLORREF GetColorArrow() const;
   void SetColorArrow(COLORREF ColorLine);   
   
   COLORREF GetColorText() const;
   void SetColorText(COLORREF ColorLine); 
   
   void GetTextFont(LOGFONT& LogFont) const;  
   void SetTextFont(const LOGFONT& LogFont); 
   
   int  GetTextFontSize() const;  
   void SetTextFontSize(int FontSize); 
      
   void ChangeLineColor(CWnd* pWnd,double Zoom);
   void ChangeArrowColor(CWnd* pWnd,double Zoom);  
   void ChangeTextColor(CWnd* pWnd,double Zoom);
   void ChangeTextFont(CWnd* pWnd,double Zoom); 
                         
   void SetExeModeAllObjList(int Mode); 
   void RotateImg(double Angle,CSize ImgSize); 
   void DeleteAllObjSel(CWnd* pWnd,double Zoom);
   void DeleteAllObj();
   
   void SetIconZoom(double IconZoom,CWnd* pWnd, double Zoom); 
   
   void Invalidate(LONG ObjClassId,CWnd* pWnd,double Zoom); 
   
   void DelSelectAll(CWnd* pWnd,double Zoom = 1);

   BOOL OnContextMenu(CWnd* pWnd,CPoint point,double Zoom);   
   BOOL PropertyInfo(CWnd* pWnd,CPoint point,double Zoom);

   void SetInUse(BOOL Show,CWnd* pWnd,double Zoom);
      
   private:
   
   //BOOL FillListNotes(int ObjClassId,CTrk* pTrk,ICFile& File,
   //                   CWnd* pWnd);
   BOOL FillListNotes(CTrk* pTrk,ICDocAnn* pAnn,CWnd* pWnd);
   ICDocAnn* FindAnn(ICDocAnnPage* pAnnPage,LONG Type,CTrkGear* pTrkObj);
   void InitNoteIconImg();
      
   public:

   double       m_Angle;   //Angulo aplicado a las anotaciones 
   double       m_Flip;    //Flip aplicado a las anotaciones
   CString      m_User;    // Nombre de Usuario 
   LONG         m_UserId;
   
   CString      m_Version;
   BOOL         m_Edit;    // Permiso de edición en navegación
   BOOL         m_AllReadOnly;
   LONG         m_NumImgs;
   CString      m_FileName; //Fichero de anotaciones con el path
   BOOL         m_ReadFile; //indica si el obj se está creando a partir de la información del
                            //fichero de anotaciones ó es uno nuevo
   COLORREF     m_ColorLine;
   COLORREF     m_ColorArrow; 
   COLORREF     m_ColorText; 
   LOGFONT      m_TextFont; // Fuente con la que se crean los obj texto por defecto
   int          m_TextFontSize;// Tamaño de la fuente del texto   
   double       m_IconZoom;      
   int          m_PosLastIcon;   
   CPtrArray    m_ImgIconList; 
   CStringArray m_NameIconList;    
   ICIco        m_NoteIconImg; 
   BOOL         m_ExistsNoteIcon;
};

void InitFont(LOGFONT* pFont);

#endif // __LISTNOTE_H__

