
#include "stdafx.h"
#include "iDocGear.h"
#include "objnote.h"
#include "resource.h"

#include "notdlg.h"     
#include "ednotdlg.h"  
#include "textdlg.h" 

#include "mapmode.h"   
#include "Ann.h"   



#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif


#define MAXSIZE_BMP  32         //Máximo tamaño de icono
#define MINSIZE_BMP  16         //Mínimo tamaño de icono  

#define MAXZOOM_ICON    1
#define MINZOOM_ICON  0.5 

//Lado del objeto

const int LEFT_OBJ=0;
const int TOP_OBJ=1; 
const int RIGHT_OBJ=2; 
const int BOTTOM_OBJ=3;    

//Tipos de bordes

const int NONE_BORDER=0; 
const int SIMPLE_BORDER=1;
const int DOUBLE_BORDER=2; 
const int THICK_BORDER=2;


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CNote                                                                              //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////


IMPLEMENT_SERIAL(CNote,CTrkObj,0)

CNote::CNote()
: CTrkGear()
{
   SetInitDefaults();
}

CNote::CNote(LONG ObjClassId)
: CTrkGear(ObjClassId)
{	
   SetInitDefaults();
}

CNote::CNote(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkGear(ObjClassId,pRect,pWnd,STL_None,STT_Null,UPD_Invalidate)
{	
   SetInitDefaults();
}


CNote::CNote(LONG ObjClassId,CTrk* pTrk)
: CTrkGear(ObjClassId,pTrk)
{	
   SetInitDefaults();
}

CNote::~CNote()
{ 
}

void CNote::Serialize(CArchive& Arch)
{
   CTrkGear::Serialize(Arch);   
}


void CNote::SetInitDefaults()
{  

   EnableMove(TRUE); 
   SetUpdateStyle(UPD_Invalidate); 

   SetMinSize( CSize(32,32) );
   SetDefSize( CSize(32,32) );  
   
   m_IconZoom = 1; 
   m_pImg     = 0;   
 
};

/*
   SetExtRect: Función reescrita para permitir que las notas se puedan centrar en los
               bordes de la imagen .                
                
*/ 

void CNote::SetExtRect(LPCRECT pRect)
{ 
      CRect ExtRect(pRect); 
      CSize DefSize;        
      
      GetDefSize(&DefSize);
      ExtRect.left   = ExtRect.left - (DefSize.cx / 2);
      ExtRect.top    = ExtRect.top - (DefSize.cy / 2);
      ExtRect.right  = ExtRect.right + (DefSize.cx / 2);
      ExtRect.bottom = ExtRect.bottom + (DefSize.cy / 2);
      
      CTrkGear::SetExtRect(ExtRect);       
}


void CNote::DrawTrueObj(CDC* pDC,LPCRECT pRect,double Zoom) const
{  

   double NewZoom; 
   CRect  RectDC(pRect);
   
   NewZoom = m_IconZoom; 

   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;
   
   if (m_pImg == 0)  //se crea el bitmap por defecto  
   { 

      CDC* pDisplayMemDC = new CDC;
      CBitmap* pBitmap = new CBitmap;
      pBitmap->LoadBitmap(IDB_NOTE);
      pDisplayMemDC->CreateCompatibleDC(pDC);
      CBitmap* pOldBitmap = (CBitmap*)pDisplayMemDC->SelectObject(pBitmap); 
      CRect RectObj;
      GetRect(RectObj); 
       
      CPoint OrgPoint; 
      CSize  SizeBmp;         
      GetDimsToStretchBlt(m_Rect,RectDC,NewZoom,OrgPoint,SizeBmp); 
        
      pDC->StretchBlt(OrgPoint.x,OrgPoint.y,SizeBmp.cx,SizeBmp.cy,
      pDisplayMemDC, 0,0,RectObj.Width(),RectObj.Height(),SRCCOPY);  
     
      delete pDisplayMemDC->SelectObject(pOldBitmap);
      delete pDisplayMemDC; 
   }    
   else   
   {        
      CSize ImgSize; 
      m_pImg->GetDims(ImgSize);   
      
      if (Zoom != NewZoom)
         RectDC = GetNewRectDC(m_Rect,RectDC,NewZoom);          
               
      m_pImg->Draw(pDC,CRect(CPoint(0,0),ImgSize),RectDC);         
   }          
}

int CNote::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);     
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }
   m_Text = ((ICDocNoteAnnInfo*)(pAnn->m_pInfo))->m_Text;
   
   SetExeMode(XM_Run | XM_InUse);   
   
  

   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CNote::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   
   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);
   //no se escribe CrtDate,UpdDate,CrtName,UpdName
   //pAnn->m_CrtnDate = m_CrtDate;
   //Ed id del creador hay que cogerlo del id de la lista,se tendría que inicializar
   //pAnn->m_UpdDate = m_UpdDate;
   ((ICDocNoteAnnInfo*)(pAnn->m_pInfo))->m_Text = m_Text;
   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL ;
      /*nn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_VIEW;
      if (m_IsModify)
         pAnn->m_pXInfo->m_Access = pAnn->m_pXInfo->m_Access | IDOC_ANN_ACCESS_UPDATE;
      if (m_IsDelete)
         pAnn->m_pXInfo->m_Access = pAnn->m_pXInfo->m_Access | IDOC_ANN_ACCESS_DELETE;*/
   }
   pAnn->m_pXInfo->m_Stat = m_Status;
   //pAnn->m_pXInfo->m_CrtrName = m_CrtName;
   //pAnn->m_pXInfo->m_UpdrName = m_UpdName;
   
   return (Err);

}

void CNote::RotateNote(double Angle,CSize ImgSize)
{ 
   CRect RectTrk;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);
   
   SetRect(RectTrk);   
}

double CNote::GetIconZoom() const
{
   return(m_IconZoom);
}


void CNote::GetText(CString& Text) const
{
   Text = m_Text;
}


void CNote::SetText(CString& Text) 
{
   m_Text = Text;
} 

void CNote::SetIconZoom(double IconZoom) 
{  
   m_IconZoom = IconZoom;    
}

BOOL CNote::EditTextNote()
{   
   BOOL Ret = FALSE;
   CString Title,Page;   
   Title.LoadString(IDS_ANN_NOTE);
   CPropertySheet SheetNote(Title,m_pWnd);   
   SheetNote.m_psh.dwFlags = SheetNote.m_psh.dwFlags | PSH_NOAPPLYNOW;
   
   CEdNotDlg EdNotDlg(Page,m_Text);
   SheetNote.AddPage(&EdNotDlg);

      
   if (SheetNote.DoModal() == IDOK)
   {
      if (!EdNotDlg.m_TextDlg.IsEmpty())
      {
         Ret = TRUE;
         m_Text = EdNotDlg.m_TextDlg;      
      }
   }

   return(Ret);
}

void CNote::SetImgNoteIcon(ICIco* NoteIconImg)
{
   m_pImg = NoteIconImg;
}

void CNote::OnLButtonDblClk(POINT /*Point*/,UINT /*Flags*/,
                            CWnd* pWnd,double /*Zoom*/)
{  
   CNotDlg NotDlg(m_Text,pWnd); 
   int     Mode = GetExeMode(); 
   
   
   if (Mode & XM_Run)
       NotDlg.DoModal();      
 
}


CTrk* CNote::CreateTrk() const
{
   CIconTrk* pTrk = new CIconTrk(m_Rect,m_pWnd,m_IconZoom,CTrk::TS_None,CTrk::DS_None,
          CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CNote::CreateTrkNew() const
{
   CIconTrk* pTrk = new CIconTrk(m_Rect,m_pWnd,m_IconZoom,CTrk::TS_None,CTrk::DS_None,
                                  CTrk::US_Invalidate);
   return(pTrk);
}


BOOL CNote::OnSetCursor(CWnd* pWnd,UINT HitTest,UINT Msg,
           double Zoom) const
{  
   BOOL    Set = FALSE;
   HCURSOR Cursor; 
   int     Mode = GetExeMode();
   CPoint  Point;
   int     HC; 
   
            
   if ( (Mode & XM_Run) && (Mode & XM_InUse) )
   {       
      if (HitTest != HTCLIENT)
      goto End;
      
      if ( m_Rect.IsRectNull() )
         goto End; 
    
      ::GetCursorPos(&Point);
      pWnd->ScreenToClient(&Point); 
      
      CGearTrk* pTrk = (CGearTrk*)SetupTrk(); //llama al CreateTrk

      HC = pTrk->GetHitCode(Point,pWnd,Zoom); 
      
      delete pTrk;  //se hace delete porque el CreateTrk hace un new

      if (HC < 0) //HC_Outside
         goto End;  
          
      Set = TRUE;
      
      Cursor = AfxGetApp()->LoadCursor(IDC_ICONCUR); 
      
      if (Cursor != NULL)
         ::SetCursor(Cursor);       
      
   }
   else 
      Set = CTrkObj::OnSetCursor(pWnd,HitTest,Msg,Zoom);
      
   
   End:
          
   return(Set); 
      return FALSE;
   
}   
void CNote::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkObj::SetExeMode(Mode);

}

void CNote::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}
  
void CNote::OnContextMenu(CWnd* pWnd, CPoint point)
{
   CMenu menu;

   if (m_IsModify)
   {
      menu.LoadMenu(IDR_PROPERTY_MENU);
      menu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN |TPM_RIGHTBUTTON, point.x,point.y,pWnd);
   }
}

BOOL CNote::PropertyInfo(CWnd* pWnd,CPoint /*point*/)
{
   BOOL Ret = FALSE;
   int  Mode = GetExeMode(); 
   int status;
   
   GetStatus(status);
   
   if (Mode & XM_Edit)
   {  
      // En modo edición se muestra cuadro de diálogo que permite escribir un texto
      if (EditTextNote())
      {
         if (status != IDOC_ANN_STATUS_NEW)
         {
            SetStatus(IDOC_ANN_STATUS_UPD);
            GetSysUserName(m_UpdName);         
            GetTime(m_UpdDate);
         }
         pWnd->Invalidate(FALSE);
         Ret = TRUE;
      }
   }

   return(Ret);
}

 
/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CLine                                                                              //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CLine,CTrkLine,0) 

CLine::CLine()
: CTrkLine()
{ 
   SetInitDefaults();   
}


CLine::CLine(LONG ObjClassId)
: CTrkLine(ObjClassId)
{ 
   SetInitDefaults();   
}

CLine::CLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkLine(ObjClassId,pRect,pWnd)
{ 
   SetInitDefaults();     
}

CLine::CLine(LONG ObjClassId,CTrk* pTrk)
: CTrkLine(ObjClassId,pTrk)
{
   SetInitDefaults(); 
}

CLine::~CLine()
{ 
} 


void CLine::SetInitDefaults()
{  
   EnableMove(TRUE);
   EnableResize(TRUE);
   EnableInvert(TRUE);
   SetUpdateStyle(UPD_Invalidate);  
   SetColor(RGB(255,0,0));    
}

void CLine::Serialize(CArchive& Arch)
{
   CTrkLine::Serialize(Arch);   
}

int CLine::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;
   LONG    Color;
   LONG    DownUp;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);   
   EnableResize(m_IsModify);
   EnableInvert(m_IsModify);
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }

   Color = ((ICDocLineAnnInfo*)(pAnn->m_pInfo))->m_Color;
   SetColor((COLORREF)Color);
   DownUp = ((ICDocLineAnnInfo*)(pAnn->m_pInfo))->m_Dirn;
    
   if (DownUp == 0)
      SetDownUp(FALSE);
   else
      SetDownUp(TRUE);  
   
   SetExeMode(XM_Run | XM_InUse);   
   
  
   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CLine::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   
   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);
   
   ((ICDocLineAnnInfo*)(pAnn->m_pInfo))->m_Color = GetColor();
   if (IsDownUp())   
      ((ICDocLineAnnInfo*)(pAnn->m_pInfo))->m_Dirn = 1;
   else
      ((ICDocLineAnnInfo*)(pAnn->m_pInfo))->m_Dirn = 0;

   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL;
   }
   pAnn->m_pXInfo->m_Stat = m_Status;
   
   
   return (Err);

}


void CLine::RotateLine(double Angle,CSize ImgSize)
{ 
   CRect RectTrk;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);
   
   SetRect(RectTrk);   
   
   BOOL DownUp  = IsDownUp();    
   
   switch ((int)Angle)
   {
      case 90:   
      {
         SetDownUp(!DownUp);         
         break;
      }      
      case 270:   
      { 
         SetDownUp(!DownUp);         
         break;
      }                 
      case -1: //FLIPX  
      { 
         SetDownUp(!DownUp);         
         break;
      }
      case -2: //FLIPY  
      { 
         SetDownUp(!DownUp);         
         break;     
                           
         break;                  
      }
      default:
         break; 
   }                                             
                                       
         
}

void CLine::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkLine::SetExeMode(Mode);

}

void CLine::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}

void CLine::OnContextMenu(CWnd* pWnd, CPoint point)
{
   CMenu menu;

   if (m_IsModify)
   {
      menu.LoadMenu(IDR_PROPERTY_MENU);
      menu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN |TPM_RIGHTBUTTON, point.x,point.y,pWnd);
   }
}

BOOL CLine::PropertyInfo(CWnd* pWnd,CPoint /*point*/)
{  
   BOOL Ret = FALSE; 
   int status;
   CColorDialog ColorDlg(m_Color,0,pWnd); 

   GetStatus(status);
   
   if (ColorDlg.DoModal() == IDOK)
   {
      m_Color = ColorDlg.GetColor();        
      Ret = TRUE;
      if (status != IDOC_ANN_STATUS_NEW)
      {
         SetStatus(IDOC_ANN_STATUS_UPD);
         GetSysUserName(m_UpdName);         
         GetTime(m_UpdDate);
      }
      pWnd->Invalidate(FALSE);
   }

   return(Ret);
}


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CArrow                                                                             //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CArrow,CTrkArrow,0) 


CArrow::CArrow()
: CTrkArrow()
{ 
   SetInitDefaults();   
}


CArrow::CArrow(LONG ObjClassId)
: CTrkArrow(ObjClassId)
{ 
   SetInitDefaults();   
}

CArrow::CArrow(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkArrow(ObjClassId,pRect,pWnd)
{ 
   SetInitDefaults();     
} 

CArrow::CArrow(LONG ObjClassId,CTrk* pTrk)
: CTrkArrow(ObjClassId,pTrk)
{
   SetInitDefaults();          
}

CArrow::~CArrow()
{ 
}

void CArrow::SetInitDefaults()
{  
   EnableMove(TRUE);
   EnableResize(TRUE);
   EnableInvert(TRUE);
   SetUpdateStyle(UPD_Invalidate); 
   SetColor(RGB(255,0,0)); 
}

void CArrow::Serialize(CArchive& Arch)
{
   CTrkArrow::Serialize(Arch);   
}


int CArrow::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;
   LONG    Color;
   LONG    DownUp;
   LONG    Up;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);   
   EnableResize(m_IsModify);
   EnableInvert(m_IsModify);
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }

   Color = ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_Color;
   SetColor((COLORREF)Color);
   DownUp = ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_Dirn;
    
   if (DownUp == 0)
      SetDownUp(FALSE);
   else
      SetDownUp(TRUE);  
   
   Up = ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_EndPoint;

   if (Up == 0)
      SetUp(FALSE);
   else
      SetUp(TRUE);                  
   

   SetExeMode(XM_Run | XM_InUse);   
   
   
   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CArrow::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   
   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);
   
   ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_Color = GetColor();
   if (IsDownUp())   
      ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_Dirn = 1;
   else
      ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_Dirn = 0;
   if (IsUp())
      ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_EndPoint = 1;
   else
      ((ICDocArrowAnnInfo*)(pAnn->m_pInfo))->m_EndPoint = 0;
   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL;
   }
   pAnn->m_pXInfo->m_Stat = m_Status;
   
   
   return (Err);

}

void CArrow::RotateArrow(double Angle,CSize ImgSize)
{ 
   CRect RectTrk;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);
   
   SetRect(RectTrk);
   
   BOOL DownUp  = IsDownUp();
   BOOL Up = IsUp(); 
    
   
   switch ((int)Angle)
   {
      case 90:   
      {  
         if (DownUp)
         {
            if (Up)
            { 
               SetDownUp(FALSE);
               SetUp(FALSE);
            }
            else 
            {  
               SetDownUp(FALSE);
               SetUp(TRUE);
            }
            
         }
         else
         {
            if (Up)
            { 
               SetDownUp(TRUE);
            }
            else
            { 
               SetDownUp(TRUE);
            }
         }
         
         break;
      }              
      case 180:   
      {  
         if (DownUp)
         {
            if (Up)
            {                 
               SetUp(FALSE);
            }
            else
               SetUp(TRUE);
            
         }
         else
         {
            if (Up)
            { 
               SetUp(FALSE);                
            }
            else
            { 
               SetUp(TRUE);
            }
         }
                                   
         break;
      }
      case 270:   
      { 
         if (DownUp)
         {
            if (Up)
            {  
               SetDownUp(FALSE);
            }
            else
            {
               SetDownUp(FALSE);
               SetUp(FALSE);
            }
            
         }
         else
         {
            if (Up)
            {  
               SetDownUp(TRUE);
               SetUp(FALSE);
            }
            else
            {  
               SetDownUp(TRUE);
               SetUp(TRUE);
            }
         } 
                
         break;
      }                 
      case -1: //FLIPX  
      { 
         if (DownUp)
         {
            if (Up)
            { 
               SetDownUp(FALSE);
            }
            else
            {
               SetDownUp(FALSE);
            }
            
         }
         else
         {
            if (Up)
            { 
               SetDownUp(TRUE);
            }
            else
            {  
               SetDownUp(TRUE);
            }
         }  
                          
         break;   
      }
      case -2: //FLIPY  
      { 
         if (DownUp)
         {
            if (Up)
            {  
               SetDownUp(FALSE);
               SetUp(FALSE);
            }
            else
            {
               SetDownUp(FALSE);
               SetUp(TRUE); 
            }
            
         }
         else
         {
            if (Up)
            {  
               SetDownUp(TRUE);
               SetUp(FALSE);
            }
            else
            {  
               SetDownUp(TRUE);
               SetUp(TRUE);
            }
         } 
                 
                           
         break;                  
      }
      default:
         break;                                              
                                       
   }  
    
}


void CArrow::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkArrow::SetExeMode(Mode);

}

void CArrow::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}

void CArrow::OnContextMenu(CWnd* pWnd, CPoint point)
{
   CMenu menu;

   if (m_IsModify)
   {
      menu.LoadMenu(IDR_PROPERTY_MENU);
      menu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN |TPM_RIGHTBUTTON, point.x,point.y,pWnd);
   }
}

BOOL CArrow::PropertyInfo(CWnd* pWnd,CPoint /*point*/)
{  
   BOOL Ret = FALSE;
   int status;
   CColorDialog ColorDlg(m_Color,0,pWnd); 

   GetStatus(status);
   
   if (ColorDlg.DoModal() == IDOK)
   {
      m_Color = ColorDlg.GetColor();   
      Ret = TRUE;
      if (status != IDOC_ANN_STATUS_NEW)
      {
         SetStatus(IDOC_ANN_STATUS_UPD);
         GetSysUserName(m_UpdName);         
         GetTime(m_UpdDate);         
      }
      pWnd->Invalidate(FALSE);
   }

   return(Ret);
}


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CHighLine                                                                          //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////


IMPLEMENT_SERIAL(CHighLine,CTrkGear,0) //CTrkObj

CHighLine::CHighLine()
: CTrkGear()
{
   SetInitDefaults();
}


CHighLine::CHighLine(LONG ObjClassId)
: CTrkGear(ObjClassId)
{
   SetInitDefaults();
}

CHighLine::CHighLine(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkGear(ObjClassId,pRect,pWnd,STL_None,STT_Null,UPD_Invalidate)
{
   SetInitDefaults();
}


CHighLine::CHighLine(LONG ObjClassId,CTrk* pTrk)
: CTrkGear(ObjClassId,pTrk)
{
   SetInitDefaults();
}

CHighLine::~CHighLine()
{ 
}

void CHighLine::Serialize(CArchive& Arch)
{
   CTrkGear::Serialize(Arch);   
}


void CHighLine::SetInitDefaults()
{  
   EnableMove(TRUE); 
   SetUpdateStyle(UPD_Invalidate); 
   EnableResize(TRUE);

   SetMinSize( CSize(32,32) );
   SetDefSize( CSize(32,32) );
}


void CHighLine::DrawTrueObj(CDC* pDC,LPCRECT pRect,double /*Zoom*/) const
{
   CPen*   pOldPen   = (CPen*)pDC->SelectStockObject(NULL_PEN);
   CBrush  Brush(RGB(255,255,128));
   CBrush* pOldBrush = (CBrush*)pDC->SelectObject(&Brush);  
   int     OldROP    = pDC->SetROP2(R2_MASKPEN);
   
   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;

   CRect Rect(pRect);
   Rect.right++;
   Rect.bottom++;
   
   pDC->Rectangle(Rect);

   pDC->SelectObject(pOldPen);                                
   pDC->SelectObject(pOldBrush); 
   pDC->SetROP2(OldROP);                       
}

int CHighLine::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);   
   EnableResize(m_IsModify);
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }
   

   SetExeMode(XM_Run | XM_InUse);   
   
   
   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CHighLine::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   
   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);   

   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL;
   }
   pAnn->m_pXInfo->m_Stat = m_Status;   
   
   return (Err);

}


void CHighLine::RotateHighLine(double Angle,CSize ImgSize)
{ 
   CRect RectTrk;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);
   
   SetRect(RectTrk);   
}

void CHighLine::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkObj::SetExeMode(Mode);

}

void CHighLine::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CTextObj                                                                   //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CText,CTrkText,0)

CText::CText()
: CTrkText()
{
   SetInitDefaults();
}

CText::CText(LONG ObjClassId)
: CTrkText(ObjClassId)
{
   SetInitDefaults();
}

CText::CText(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkText(ObjClassId,pRect,pWnd)
{
   SetInitDefaults();
}

CText::CText(LONG ObjClassId,CTrk* pTrk)
: CTrkText(ObjClassId,pTrk)
{
   SetInitDefaults();    
}

CText::CText(LONG ObjClassId,CTrk* pTrk,const ICFont& Font)
: CTrkText(ObjClassId,pTrk,Font)
{
   SetInitDefaults();       
}

CText::~CText()
{ 
}


void CText::Serialize(CArchive& Arch)
{
   CTrkText::Serialize(Arch);   
} 


void CText::SetInitDefaults()
{  

   CTrkText::SetInitDefaults();
   
   int i;
   
   for(i=0;i<4;i++)
   {
      m_BorderArr[i] = NONE_BORDER;
   }   

} 

////////////////////////////////////////////////////////////


void CText::DrawTrueObj(CDC* pDC,LPCRECT pRect,double Zoom) const
{ 
   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;

   CTrkText::DrawTrueObj(pDC,pRect,Zoom);  
   
   int i;
      
   for(i=0;i<4;i++)
   {
      if (m_BorderArr[i] != NONE_BORDER)
         DrawBorder(pDC,i,m_BorderArr[i],pRect);
   }         
}

int CText::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;
   CString FontName;  
   DWORD   Color;
   LONG    FontSize;
   LONG    Enh;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);   
   EnableResize(m_IsModify);
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }

   Color = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontColor;
   SetColor((COLORREF)Color);

   FontName = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontName;
   FontSize = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontSize;
   Enh = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontEnh;
   SetFont(ICFont((LPCSTR)FontName,FontSize,Enh)); 

   m_BorderArr[LEFT_OBJ]   = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_LBS;
   m_BorderArr[TOP_OBJ]    = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_TBS;
   m_BorderArr[RIGHT_OBJ]  = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_RBS;
   m_BorderArr[BOTTOM_OBJ] = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_BBS;

   m_Justify = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_Just;
   m_Text = ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_Text;

   SetExeMode(XM_Run | XM_InUse);   
   
  
   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CText::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   CString FontName;

   
   m_Font.GetName(FontName);
   
   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);
   
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_Text = m_Text;
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_LBS = m_BorderArr[LEFT_OBJ];
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_TBS = m_BorderArr[TOP_OBJ];
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_RBS = m_BorderArr[RIGHT_OBJ];
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_BBS = m_BorderArr[BOTTOM_OBJ];
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontSize = m_Font.GetSize();
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontEnh = m_Font.GetEnh();
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontColor = GetColor();
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_FontName = FontName;
   ((ICDocTextAnnInfo*)(pAnn->m_pInfo))->m_Just = m_Justify;
   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL;
   }
   pAnn->m_pXInfo->m_Stat = m_Status;   
   
   return (Err);

}


void CText::OnLButtonDblClk(POINT /*Point*/,UINT /*Flags*/,
                            CWnd* /*pWnd*/,double /*Zoom*/)
{   /*
   LOGFONT LogFont;
   CFont   Font;
   
   m_Font.GetObject(sizeof(LOGFONT),&LogFont);
   
   CTextDlg Dlg(m_Text,&LogFont,(int)m_Font.GetSize(),m_BorderArr,
                m_Justify,m_Color,pWnd); 
   int      Mode = GetExeMode(); 
   
   
   if (Mode & XM_Edit)
   {  
      // En modo edición se muestra cuadro de diálogo que permite escribir un texto  
      
      if (Dlg.DoModal() == IDOK)
      {  
         m_Text     = Dlg.m_Text; 
         m_Justify  = Dlg.m_Justify;
         m_Color    = Dlg.m_FontColor; 
         
         Font.CreateFontIndirect(Dlg.m_pFont);         
         m_Font = ICFont(Font);       
         
         int i;
         
         for(i=0;i<4;i++)
         {
            m_BorderArr[i] = Dlg.m_BorderArr[i];
         } 
         
         Invalidate(pWnd,Zoom);                
      }          
   }
  */
}

void CText::RotateText(double Angle,CSize ImgSize)
{ 
   CRect RectTrk,Aux;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);    
   
   SetRect(RectTrk);   
}

void CText::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkText::SetExeMode(Mode);

}

void CText::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}

BOOL CText::EditText()
{   
   BOOL Ret = FALSE;
   CString Title;
   LOGFONT LogFont;
   CFont   Font;  
   
   
   m_Font.GetObject(sizeof(LOGFONT),&LogFont);
   Title.LoadString(IDS_ANN_TEXT);
   CPropertySheet SheetText(Title,m_pWnd);
   SheetText.m_psh.dwFlags = SheetText.m_psh.dwFlags | PSH_NOAPPLYNOW;

   Title.LoadString(IDS_GEN);
   CEdNotDlg EdNotDlg(Title,m_Text);

   CTextDlg textDlg(&LogFont,(int)m_Font.GetSize(),m_BorderArr,
                    m_Justify,m_Color,m_pWnd);
   SheetText.AddPage(&EdNotDlg);
   SheetText.AddPage(&textDlg);
   if (SheetText.DoModal() == IDOK)
   {     
      if (!EdNotDlg.m_TextDlg.IsEmpty())
      {
         m_Text = EdNotDlg.m_TextDlg;

         m_Justify  = textDlg.m_Justify;
         m_Color    = textDlg.m_FontColor; 
      
         Font.CreateFontIndirect(textDlg.m_pFont);         
         m_Font = ICFont(Font);       
      
         int i;
      
         for(i=0;i<4;i++)
         {
            m_BorderArr[i] = textDlg.m_BorderArr[i];
         } 
         Ret = TRUE;
      }
   }
      
   return Ret;
}

void CText::OnContextMenu(CWnd* pWnd, CPoint point)
{
   CMenu menu;

   if (m_IsModify)
   {
      menu.LoadMenu(IDR_PROPERTY_MENU);
      menu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN |TPM_RIGHTBUTTON, point.x,point.y,pWnd);
   }
}


BOOL CText::PropertyInfo(CWnd* pWnd,CPoint /*point*/)
{   
   BOOL Ret = FALSE;
   int status;
   int  Mode = GetExeMode(); 

   GetStatus(status);
   
   if (Mode & XM_Edit)
   {  
      if (EditText())
      {
         if (status != IDOC_ANN_STATUS_NEW)
         {
            SetStatus(IDOC_ANN_STATUS_UPD);
            GetSysUserName(m_UpdName);         
            GetTime(m_UpdDate);
         }
         Ret = TRUE;
         pWnd->Invalidate(FALSE);    
      }
   }

   return Ret;
}


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  CMark                                                                           //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CMark,CTrkGear,0)

CMark::CMark()
: CTrkGear()
{
   SetInitDefaults();
}

CMark::CMark(LONG ObjClassId)
: CTrkGear(ObjClassId)
{	
   SetInitDefaults();
}

CMark::CMark(LONG ObjClassId,LPCRECT pRect,CVw* pWnd)
: CTrkGear(ObjClassId,pRect,pWnd,STL_None,STT_Null,UPD_Invalidate)
{	
   SetInitDefaults();
}


CMark::CMark(LONG ObjClassId,CTrk* pTrk)
: CTrkGear(ObjClassId,pTrk)
{	
   SetInitDefaults();
}

CMark::~CMark()
{ 
}

void CMark::Serialize(CArchive& Arch)
{
   CTrkGear::Serialize(Arch);   
}


void CMark::SetInitDefaults()
{  
   EnableMove(TRUE); 
   SetUpdateStyle(UPD_Invalidate); 

   SetMinSize( CSize(40,40) );
   SetDefSize( CSize(40,40) );  

   m_point = CPoint(0,0);
   
   m_ExeMode = XM_Run | XM_InUse;   
   
}



void CMark::DrawTrueObj(CDC* pDC,LPCRECT pRect,double /*Zoom*/) const
{  
   
   CPoint pointDst;   
   CRect  RectDC(pRect);
   CPen  Pen(PS_SOLID,2,RGB(255,0,0));

   if (m_Status == IDOC_ANN_STATUS_DEL)
      return;

	CPen* pOldPen = (CPen*)pDC->SelectObject(&Pen);   


   pDC->MoveTo(RectDC.left,RectDC.bottom);

   pointDst.x = RectDC.right;   
   pointDst.y = RectDC.top;
   pDC->LineTo(pointDst);

   pDC->MoveTo(RectDC.left,RectDC.bottom);
   pointDst.x = RectDC.left + (long)((double)RectDC.Width()* 10/100);
   pointDst.y = RectDC.top + (RectDC.Height() / 2);
   pDC->LineTo(pointDst);

   pDC->SelectObject(pOldPen);  
   
   
}


void CMark::RotateMark(double Angle,CSize ImgSize)
{ 
   CRect RectTrk;
   
   GetRect(&RectTrk);
   
   RectTrk = RotateRect(RectTrk,Angle,ImgSize);
   
   SetRect(RectTrk);   
}

int CMark::ReadObjFile(ICDocAnn* pAnn)
{   
   CString Linea;   
   int     Err = 0;   
   BOOL    IsDel = FALSE;

   m_Id      = pAnn->m_Id;
   m_CrtDate = pAnn->m_CrtnDate;
   m_CrtName = pAnn->m_pXInfo->m_CrtrName;
   m_UpdDate = pAnn->m_UpdDate;
   m_UpdName = pAnn->m_pXInfo->m_UpdrName;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_UPDATE)
      m_IsModify = TRUE;
   else
      m_IsModify = FALSE;
   if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_DELETE)
      m_IsDelete = TRUE;
   else
      m_IsDelete = FALSE;

   EnableMove(m_IsModify);   
   EnableResize(m_IsModify);
   

   if (pAnn->m_pXInfo->m_Stat == IDOC_ANN_STATUS_DEL)
   {
      IsDel = TRUE; //Item a no tener en cuenta      
   }
   else
   {
//Pongo el estado de todas las anotaciones a DEF
      pAnn->m_pXInfo->m_Stat = m_Status = IDOC_ANN_STAT_DEF;
   }
   

   SetExeMode(XM_Run | XM_InUse);   
   
   
   if (IsDel)
      Err = 2;
   
   return(Err);    

}

BOOL CMark::WriteObjFile(ICDocAnn* pAnn)
{
   BOOL    Err = FALSE;
   CRect   rect;
   

   pAnn->m_Id = m_Id;
   GetRect(pAnn->m_Rect);
      
   if (pAnn->m_pXInfo->m_Access == IDOC_ANN_ACCESS_NONE)
   {
      pAnn->m_pXInfo->m_Access = IDOC_ANN_ACCESS_ALL;
   }
   pAnn->m_pXInfo->m_Stat = m_Status;   
   
   return (Err);

}


CTrk* CMark::CreateTrk() const
{
   CGearTrk* pTrk = new CGearTrk(m_Rect,m_pWnd,CTrk::TS_None,CTrk::DS_None,
          CTrk::US_Invalidate);
   return(pTrk);
}

CTrk* CMark::CreateTrkNew() const
{
   CGearTrk* pTrk = new CGearTrk(m_Rect,m_pWnd,CTrk::TS_None,CTrk::DS_None,
                                  CTrk::US_Invalidate);
   return(pTrk);
}



void CMark::SetExeMode(int Mode)
{
   if (!m_IsModify && (Mode & XM_Edit))
      return;
   else
      CTrkObj::SetExeMode(Mode);

}

void CMark::Select(CWnd* pWnd,double Zoom,int UpdateMode)
{

   if ( !IsSelected() && m_IsModify == TRUE && m_Status != IDOC_ANN_STATUS_DEL)
   {
      Update(UpdateMode,pWnd,Zoom);
      SetSelected();
      Update(UpdateMode,pWnd,Zoom);
   }

}


/////////////////////////////////////////////////////////////////////////////////////////
//                                                                                     //
//  Funciones generales                                                                //
//                                                                                     //
/////////////////////////////////////////////////////////////////////////////////////////

void DisplayMsgErrorTrk(UINT /*IdMsg*/)
{/*
   CString Msg;  
   CMsg    Imsg;
   
   if (!Imsg.TranslateStr(IdMsg,Msg))
      Msg.LoadString(IdMsg);
   
   AfxGetMainWnd()->MessageBox(Msg,NULL,MB_ICONEXCLAMATION);*/
}

/*
   GetDimsToStretchBlt: Función que dado un rectángulo en coordenadas de dispositivo 
                        y dependiendo del zoom devuelve el pto origen y tamaño, en
                        coordenadas lógicas necesario para dibujar un bitmap en pantalla.                  .                
                
*/ 

void GetDimsToStretchBlt(const CRect& RectLog,CRect& RectDC,double NewZoom,
                         CPoint& OrgPoint,CSize& SizeBmp) 
{ 
   CPoint Center; 
   CRect  NewRect;    
   
   Center.x = RectDC.left + (RectDC.Width()/2); 
   Center.y = RectDC.top + (RectDC.Height()/2); 
                                                                         
      
   OrgPoint.x  = Center.x - (int)((RectLog.Width()*NewZoom)/2);
   OrgPoint.y  = Center.y - (int)((RectLog.Height()*NewZoom)/2); 
      
   SizeBmp = CSize((int)(RectLog.Width()*NewZoom),(int)(RectLog.Height()*NewZoom));    
}

/*
   RotateRect: Función que dado un rectángulo dibujado sobre una imagen calcula su nueva
               posición al girar la imagen un ángulo de 90,180,270 o al hacer FLIP                .                
                
*/ 

CRect RotateRect(const CRect& RectTrk,double Angle,CSize SizeImg)
{  
   CRect RotRect; 
   CRect Rect(RectTrk); 
   CPoint Center,NewCenter;
   
   Center.x = Rect.left + (Rect.Width()/2); 
   Center.y = Rect.top + (Rect.Height()/2); 
      
   switch ((int)Angle)
   {
      case 90:   
      {  
         NewCenter.x = SizeImg.cx - Center.y;
         NewCenter.y  = Center.x;
         break;
      }              
      case 180:   
      {  
         NewCenter.x  = SizeImg.cx - Center.x;
         NewCenter.y  = SizeImg.cy - Center.y;                            
         break;
      }
      case 270:   
      {  
         NewCenter.x  = Center.y;
         NewCenter.y  = SizeImg.cy - Center.x;         
         break;
      }                 
      case -1: //FLIPX  
      {  
         NewCenter.x  = SizeImg.cx - Center.x;
         NewCenter.y  = Center.y;                        
         break;   
      }
      case -2: //FLIPY  
      {  
         NewCenter.x  = Center.x;
         NewCenter.y  = SizeImg.cy - Center.y;                          
         break;                  
      }
      default:
         break;                                              
                                       
      }
            
      if (Angle == -1 || Angle == -2 || Angle == 180)
      { 
         RotRect.left   = NewCenter.x - (Rect.Width()/2);
         RotRect.top    = NewCenter.y - (Rect.Height()/2); 
         RotRect.right  = RotRect.left + Rect.Width(); 
         RotRect.bottom = RotRect.top + Rect.Height(); 
      } 
      else
      {
         RotRect.left   = NewCenter.x - (Rect.Height()/2);
         RotRect.top    = NewCenter.y - (Rect.Width()/2); 
         RotRect.right  = RotRect.left + Rect.Height(); 
         RotRect.bottom = RotRect.top + Rect.Width(); 
      }
      
      
      return(RotRect);   
}

void DrawBorder(CDC* pDC,int PosBorder,int TypeBorder,LPCRECT pRect)
{   
   switch (PosBorder)
   {
      case TOP_OBJ:
      {  
         
         pDC->MoveTo(pRect->left,pRect->top); 
         pDC->LineTo(pRect->right, pRect->top);          
         
         if (TypeBorder == DOUBLE_BORDER)
         {
            pDC->MoveTo(pRect->left+2,pRect->top+2); 
            pDC->LineTo(pRect->right-2, pRect->top+2);
         }
         break;
      }
      case BOTTOM_OBJ:
      {  
         pDC->MoveTo(pRect->left,pRect->bottom); 
         pDC->LineTo(pRect->right, pRect->bottom);
            
         if (TypeBorder == DOUBLE_BORDER)
         {
            pDC->MoveTo(pRect->left+2,pRect->bottom-2); 
            pDC->LineTo(pRect->right-2, pRect->bottom-2);
         }
         break;
      } 
      case LEFT_OBJ:
      {  
         pDC->MoveTo(pRect->left,pRect->top); 
         pDC->LineTo(pRect->left, pRect->bottom);
            
         if (TypeBorder == DOUBLE_BORDER)
         {
            pDC->MoveTo(pRect->left+2,pRect->top+2); 
            pDC->LineTo(pRect->left+2, pRect->bottom-2);
         }
         break;
      } 
      case RIGHT_OBJ:
      {  
         pDC->MoveTo(pRect->right,pRect->top); 
         pDC->LineTo(pRect->right, pRect->bottom); 
         
         if (TypeBorder == DOUBLE_BORDER)
         {
            pDC->MoveTo(pRect->right-2,pRect->top+2); 
            pDC->LineTo(pRect->right-2,pRect->bottom-2);
         }
         break;
      }  
      
   }   
} 

