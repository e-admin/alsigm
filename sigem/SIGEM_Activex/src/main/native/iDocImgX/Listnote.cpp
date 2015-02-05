          
          
#include "stdafx.h" 
#include "iDocGear.h"
#include "objnote.h"
#include "listnote.h"
#include "ifile.h"
#include "Ann.h"


#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif 


//const int WM_CNFNAV      = WM_USER + 0x0450; 
const char* NoteIconFile = "NOTE.ICO"; 


// Tipos de objetos que se pueden añadir a la lista
/*
const int IDOC_ANN_TYPE_NAVEGATION = 1;
const int IDOC_ANN_TYPE_TEXT       = 2;
const int IDOC_ANN_TYPE_NOTE       = 3;
const int IDOC_ANN_TYPE_HIGHLINE   = 4;
const int IDOC_ANN_TYPE_LINE       = 5;
const int IDOC_ANN_TYPE_ARROW      = 6;  
const int IDOC_ANN_TYPE_PICTURE    = 7;  
const int IDOC_ANN_TYPE_MARK       = 8;  
*/  ///Están en ann.h


IMPLEMENT_SERIAL(CListNote,CTrkObjList,0)

CListNote::CListNote()
: CTrkObjList()
{
   m_ExeMode        = XM_Run; 
   m_ReadFile       = FALSE; 
   m_Edit           = FALSE;
   m_ColorLine      = RGB(255,0,0); 
   m_ColorArrow     = RGB(255,0,0);  
   m_ColorText      = RGB(0,0,0); 
   m_PosLastIcon    = -1; 
   m_IconZoom       = 1;  
   m_TextFontSize   = 0;  
   m_AllReadOnly    = FALSE;
   m_Angle          = 0;
   m_Flip           = 0;
      
   InitNoteIconImg();    
   ::InitFont(&m_TextFont);
    
}

CListNote::~CListNote()
{  
   ICIco*  pImg;
   int     NumImgIcon = m_ImgIconList.GetSize(); 
   int     i;
   
   for(i=0;i<NumImgIcon;i++)
   {
      pImg = (ICIco FAR*)m_ImgIconList[i]; 
      
      pImg->Term();
      
      delete pImg;       
   }
   
   m_ImgIconList.RemoveAll();
   
   m_NameIconList.RemoveAll();  
   
   m_NoteIconImg.Term(); 
  
}

void CListNote::InitNoteIconImg()
{  
   CString PathIcon; 
   BOOL    Exists;
   LONG    Ret;   
   
   PathIcon = AfxGetApp()->GetProfileString("Directories","ICO","c:\\IMGLOOK");       
   PathIcon += "\\";
   PathIcon += NoteIconFile;  
   
   m_ExistsNoteIcon = FALSE;
      
   Exists = IFILEExists(PathIcon);
      
   if (Exists) 
   {               
      Ret = m_NoteIconImg.Init(PathIcon); 
      
      if (Ret == 0) //sin error al abrir el icono
         m_ExistsNoteIcon = TRUE;       
   } 
}



void CListNote::Serialize(CArchive& Arch)
{
   CTrkObjList::Serialize(Arch);
}


CTrk* CListNote::CreateNewObjTrk(LONG ObjClassId) const
{

   CTrk*    pTrk = NULL;
   CTrkObj* pObj = NULL;  
      
   switch (ObjClassId)
   {    
      case IDOC_ANN_TYPE_TEXT:
      {
         pObj = new CText(ObjClassId);
         break;
      }  
      
      case IDOC_ANN_TYPE_NOTE:
      {
         pObj = new CNote(ObjClassId); 
         break;
      }
      
      case IDOC_ANN_TYPE_HILITE:
      {
         pObj = new CHighLine(ObjClassId); 
         break;
      } 
      case IDOC_ANN_TYPE_LINE:
      {
         pObj = new CLine(ObjClassId); 
         break;
      }
      case IDOC_ANN_TYPE_ARROW:
      {
         pObj = new CArrow(ObjClassId); 
         break;
      } 
      case IDOC_ANN_TYPE_CHECKMARK:
      {
         pObj = new CMark(ObjClassId); 
         break;
      }
      default:
      {
         break;
      }
   }    

   if (pObj != NULL)
   {
      pTrk = pObj->SetupTrkNew();
      delete pObj;
   }

   return(pTrk);

}


CTrkObj* CListNote::CreateObj(LONG ObjClassId,CTrk* pTrk,CWnd* pWnd)
{
   

   CTrkObj* pObj = NULL; 
         
   switch (ObjClassId)
   {
      case IDOC_ANN_TYPE_TEXT:
      {  
         CFont Font;
         Font.CreateFontIndirect(&m_TextFont);
         
         pObj = new CText(ObjClassId,pTrk,ICFont(Font));         
         ((CText*)pObj)->SetParentView((CVw*)pWnd);
         ((CText*)pObj)->SetColor(m_ColorText);
         if (!m_ReadFile)
            if (! ((CText*)pObj)->EditText() )
            {
               delete pObj;
               pObj = NULL;
            }
            else
               ((CText*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();

         break;
      }  
      
      case IDOC_ANN_TYPE_NOTE:
      {
         pObj = new CNote(ObjClassId,pTrk);                   
         ((CNote*)pObj)->SetParentView((CVw*)pWnd);
         ((CNote*)pObj)->SetIconZoom(m_IconZoom); 
         if (m_ExistsNoteIcon)
            ((CNote*)pObj)->SetImgNoteIcon(&m_NoteIconImg);  
         if (!m_ReadFile)
             if ( !((CNote*)pObj)->EditTextNote() )
             {
                delete pObj;
                pObj = NULL;
             }
             else
             {
               ((CNote*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();
             }
                
         break;
      }
      
      case IDOC_ANN_TYPE_HILITE:
      {
         pObj = new CHighLine(ObjClassId,pTrk);          
         ((CHighLine*)pObj)->SetParentView((CVw*)pWnd);
         if (!m_ReadFile)
            ((CHighLine*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();
         break;
      } 
      case IDOC_ANN_TYPE_LINE:
      {
         pObj = new CLine(ObjClassId,pTrk);  
         ((CLine*)pObj)->SetParentView((CVw*)pWnd);
         ((CLine*)pObj)->SetColor(m_ColorLine);      
         if (!m_ReadFile)
            ((CLine*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();      
         break;
      }
      case IDOC_ANN_TYPE_ARROW:
      {
         pObj = new CArrow(ObjClassId,pTrk);  
         ((CArrow*)pObj)->SetParentView((CVw*)pWnd);
         ((CArrow*)pObj)->SetColor(m_ColorArrow);
         if (!m_ReadFile)
            ((CArrow*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();
         break;
      }
      case IDOC_ANN_TYPE_CHECKMARK:
      {
         pObj = new CMark(ObjClassId,pTrk); 
         ((CMark*)pObj)->SetParentView((CVw*)pWnd);
         if (!m_ReadFile)
            ((CMark*)pObj)->m_Id = ((CVw*)(pWnd))->GetId();
         break;
      }
      default:
      {
         break;
      }
   }
   
   return(pObj);

} 

void CListNote::SetNewObjTrkDefaults(LONG ObjClassId,CTrk* pTrk) const
{  
   CTrkObjList::SetNewObjTrkDefaults(ObjClassId,pTrk);
   
   if (ObjClassId == IDOC_ANN_TYPE_NOTE)
   {
      CRect ExtRect; 
      CSize DefSize;
      
      GetDefExtRect(ExtRect);
      pTrk->GetDefSize(&DefSize);
      ExtRect.left   = ExtRect.left - (DefSize.cx / 2);
      ExtRect.top    = ExtRect.top - (DefSize.cy / 2);
      ExtRect.right  = ExtRect.right + (DefSize.cx / 2);
      ExtRect.bottom = ExtRect.bottom + (DefSize.cy / 2);
      
      pTrk->SetExtRect(ExtRect);
   }
}


BOOL CListNote::ReadNotesFile(ICDocAnnDoc* pAnnDoc,LONG page,CWnd* pWnd)
{
   BOOL          ret = TRUE;
   CWaitCursor   wait;
   ICDocAnnPage* pAnnPage = NULL;
   LONG          NumAnn;
   CTrk          Trk;
   LONG          Err = FALSE;
   int           i;
   int           pagesAnn;

   m_ReadFile = TRUE;
   m_AllReadOnly = TRUE;
   m_Version = pAnnDoc->m_Ver;
   if (pAnnDoc->m_pXInfo->m_Flags & IDOC_ANN_XDI_FLAG_ADD_ANNS)
      m_Edit = TRUE; //añadir anotaciones
   m_User    = pAnnDoc->m_pXInfo->m_UserName; //User
   m_UserId   = pAnnDoc->m_pXInfo->m_UserId;

   pagesAnn = pAnnDoc->m_pPages->GetSize();

   if (pagesAnn < page)
   {
      NumAnn = 0;
   }
   else
   {
      pAnnPage = (ICDocAnnPage*)pAnnDoc->m_pPages->GetAt(page - 1); 
      if (pAnnPage == NULL) {ret = FALSE; goto End;}
   

      NumAnn = pAnnPage->m_pAnns->GetSize();
   }
   for (i=0; i<NumAnn; i++)
   {
      ICDocAnn* pAnn = (ICDocAnn*)pAnnPage->m_pAnns->GetAt(i);
      if (pAnn == NULL) {ret = FALSE; goto End;}
      if (pAnn->m_pXInfo->m_Access & IDOC_ANN_ACCESS_VIEW) //Si es visible
      {
         Trk.SetRect(pAnn->m_Rect);
         Err = FillListNotes(&Trk,pAnn,pWnd);
         if (Err) goto End;
      }
   }

End:
   if (!Err) ret = FALSE;

   m_ReadFile = FALSE;

   return(ret);
}

/*
   FillListNotes: Función que crea y añade en la lista los objetos definidos en el
                  fichero de anotaciones.

*/
BOOL CListNote::FillListNotes(CTrk* pTrk,ICDocAnn* pAnn,CWnd* pWnd)
{
   CTrkObj* pTrkObj;
   BOOL     Err = FALSE; 
   BOOL     modify= FALSE;
   BOOL     del = FALSE;
   int      ObjClassId = (int)pAnn->m_Type;

   pTrkObj = CreateObj(ObjClassId,pTrk,pWnd);
   if (pTrkObj == 0)          
   {  
      Err = TRUE;
      goto End;
   }   

   switch (ObjClassId)
   {
      case IDOC_ANN_TYPE_TEXT:
      {
         Err = ((CText*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err)
         {
            ((CText*)pTrkObj)->GetModify(modify);
            ((CText*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            Add((CText*)pTrkObj);
            ((CVw*)(pWnd))->SetId(((CText*)pTrkObj)->m_Id);
         }
         break;
      }  
      
      case IDOC_ANN_TYPE_NOTE:
      {                
         Err = ((CNote*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err)
         {
            ((CNote*)pTrkObj)->GetModify(modify);
            ((CNote*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            ((CVw*)(pWnd))->SetId(((CNote*)pTrkObj)->m_Id);
            Add((CNote*)pTrkObj);         
         }
         break;
      }
      
      case IDOC_ANN_TYPE_HILITE:
      {    
         Err = ((CHighLine*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err) 
         {
            ((CHighLine*)pTrkObj)->GetModify(modify);
            ((CHighLine*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            ((CVw*)(pWnd))->SetId(((CHighLine*)pTrkObj)->m_Id);
            Add((CHighLine*)pTrkObj); 
         }
         break;
      } 
      case IDOC_ANN_TYPE_LINE:
      {                   
         Err = ((CLine*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err) 
         {
            ((CLine*)pTrkObj)->GetModify(modify);
            ((CLine*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            ((CVw*)(pWnd))->SetId(((CLine*)pTrkObj)->m_Id);
            Add((CLine*)pTrkObj);
         }
         break;
      }
      case IDOC_ANN_TYPE_ARROW:
      {        
         Err = ((CArrow*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err)
         {
            ((CArrow*)pTrkObj)->GetModify(modify);
            ((CArrow*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            ((CVw*)(pWnd))->SetId(((CArrow*)pTrkObj)->m_Id);
            Add((CArrow*)pTrkObj); 
         }
         break;
      }
      case IDOC_ANN_TYPE_CHECKMARK:
      {        
         Err = ((CMark*)pTrkObj)->ReadObjFile(pAnn);
         if (!Err)
         {
            ((CMark*)pTrkObj)->GetModify(modify);
            ((CMark*)pTrkObj)->GetDelete(del);
            if (modify || del) m_AllReadOnly = FALSE;
            ((CVw*)(pWnd))->SetId(((CMark*)pTrkObj)->m_Id);
            Add((CMark*)pTrkObj); 
         }
         break;
      }
       default:
      {
         Err = TRUE;
         break;
      }
   } 
   

   if (Err)
   {
      delete pTrkObj;
      pTrkObj = 0;
      if (Err == 2) //El item tenía status = IDOC_ANN_STATUS_DEL
         Err = 0;
   }

   
   End:
   
   return(Err);
}

ICDocAnn* CListNote::FindAnn(ICDocAnnPage* pAnnPage,LONG Type,CTrkGear* pTrkObj)
{  
   ICDocAnn* pAnnRet = NULL;
   ICDocAnn* pAnn = NULL;
   BOOL      find = FALSE;

   LONG NumAnn = pAnnPage->m_pAnns->GetSize();
   for (int h=0; h<NumAnn; h++)
   {
      pAnn = (ICDocAnn*)pAnnPage->m_pAnns->GetAt(h);      
      if((pAnn->m_Id == pTrkObj->m_Id) && (pAnn->m_Type == Type) )//&& (pAnn->m_pXInfo->m_Stat != IDOC_ANN_STAT_NEW))
      {
         find = TRUE;
         break;
      }
   }
   if (find)
   {
      pAnnRet = pAnn;      
   }


   return(pAnnRet);
}

/*
   WriteNotesFile: Función que escribe el fichero de anotaciones a partir de los valores
                   de los objetos de la lista.
               
*/  
BOOL CListNote::WriteNotesFile(ICDocAnnDoc* pAnnDoc,LONG page)
{
   BOOL          ret = FALSE;
   ICDocAnnPage* pAnnPage;
   int           NumItems = GetCount();
   POSITION      Pos = GetHeadPos(); 
   CTrkGear*     pTrkObj = 0;
   int           i;
   int           PagesAnn;
   BOOL          newPage = FALSE;

   PagesAnn = pAnnDoc->m_pPages->GetSize(); 

   if (PagesAnn < page)
   {
      pAnnPage = new ICDocAnnPage(TRUE);
      pAnnPage->Init();
      newPage = TRUE;
   }
   else
   {
      pAnnPage = (ICDocAnnPage*)pAnnDoc->m_pPages->GetAt(page - 1); 
      if (pAnnPage == NULL) {ret = FALSE; goto End;}  
   } 
   
   for(i=0; i<NumItems; i++)
   {
      pTrkObj = (CTrkGear*)GetNext(Pos);

      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CText)) )
      { 
         //Busco la anotación en estructura INVESDOC
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_TEXT,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CText*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_TEXT);
            ret = ((CText*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; }
            pAnnPage->m_pAnns->Add(pAnn);            
         }
      } 
   
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CNote)) )
      {  
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_NOTE,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CNote*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_NOTE);
            ret = ((CNote*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; } 
            pAnnPage->m_pAnns->Add(pAnn);
         }  
      } 
   
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CHighLine)) )
      {   
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_HILITE,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CHighLine*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_HILITE);
            ret = ((CHighLine*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; } 
            pAnnPage->m_pAnns->Add(pAnn);
         }  
      }
   
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CLine)) )
      {  
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_LINE,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CLine*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_LINE);
            ret = ((CLine*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; } 
            pAnnPage->m_pAnns->Add(pAnn);
         }  
      } 
   
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CArrow)) )
      {                            
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_ARROW,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CArrow*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_ARROW);
            ret = ((CArrow*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; } 
            pAnnPage->m_pAnns->Add(pAnn);
         }    
      } 

      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CMark)) )
      {  
         ICDocAnn* pAnn = NULL;
         if (!newPage)
            pAnn = FindAnn(pAnnPage,IDOC_ANN_TYPE_CHECKMARK,pTrkObj);
         if (pAnn != NULL)
         {
            ret = ((CMark*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) goto End;   
         }
         else
         {
            pAnn = new ICDocAnn(TRUE);
            pAnn->Init(IDOC_ANN_TYPE_CHECKMARK);
            ret = ((CMark*)pTrkObj)->WriteObjFile(pAnn); 
            if (ret) { delete pAnn; goto End; } 
            pAnnPage->m_pAnns->Add(pAnn);
         }    
      }
   }
   if (newPage)
      pAnnDoc->m_pPages->Add(pAnnPage);

End:

   return(ret);
}

void CListNote::SetExeModeAllObjList(int Mode)
{  
   CTrkObj* pTrkObj;
   int      i;
   int      NumObj = GetCount(); 
   POSITION Pos = GetHeadPos();
   
   SetExeMode(Mode);
      
   for(i=0;i<NumObj;i++)
   {
      pTrkObj = GetNext(Pos); 
      pTrkObj->SetExeMode(Mode);      
   }
   
}



void CListNote::RotateImg(double Angle,CSize ImgSize)
{  
   CTrkObj* pTrkObj;
   int      i;
   int      NumItems = GetCount(); 
   POSITION Pos = GetHeadPos();   
   
   for (i=0;i<NumItems;i++)
   {  
      pTrkObj = GetNext(Pos);
         
              
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CText)) )
      {   
         ((CText*)pTrkObj)->RotateText(Angle,ImgSize); 
            
      } 
         
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CNote)) )
      { 
         ((CNote*)pTrkObj)->RotateNote(Angle,ImgSize);             
      } 
         
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CHighLine)) )
      { 
         ((CHighLine*)pTrkObj)->RotateHighLine(Angle,ImgSize);            
      }
         
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CLine)) )
      { 
         ((CLine*)pTrkObj)->RotateLine(Angle,ImgSize);             
      } 
         
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CArrow)) )
      { 
         ((CArrow*)pTrkObj)->RotateArrow(Angle,ImgSize);             
      }
      
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CMark)) )
      { 
         ((CMark*)pTrkObj)->RotateMark(Angle,ImgSize);             
      }
   }       
   
}

COLORREF CListNote::GetColorLine() const
{
   return(m_ColorLine);
}


void CListNote::SetColorLine(COLORREF ColorLine)
{
   m_ColorLine = ColorLine;
} 

COLORREF CListNote::GetColorArrow() const
{
   return(m_ColorArrow);
}


void CListNote::SetColorArrow(COLORREF ColorArrow)
{
   m_ColorArrow = ColorArrow;
}


COLORREF CListNote::GetColorText() const
{
   return(m_ColorText);
}


void CListNote::SetColorText(COLORREF ColorText)
{
   m_ColorText = ColorText;
} 



/*
   ChangeLineColor: Función que cambia el color a todas las líneas seleccionadas.                
                
*/ 
 
void CListNote::ChangeLineColor(CWnd* pWnd, double Zoom)
{ 

   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      pTrkObj = GetNextSel(Pos);
      
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CLine)) )           
      {  
         ((CLine*)pTrkObj)->SetColor(m_ColorLine);
         ((CLine*)pTrkObj)->Invalidate(pWnd,Zoom);                      
      }      
   }
}

/*
   ChangeArrowColor: Función que cambia el color a todas las flechas seleccionadas.                
                
*/      
        
void CListNote::ChangeArrowColor(CWnd* pWnd,double Zoom)
{ 

   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      pTrkObj = GetNextSel(Pos);
      
      if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CArrow)) )
      { 
         ((CArrow*)pTrkObj)->SetColor(m_ColorArrow); 
         ((CArrow*)pTrkObj)->Invalidate(pWnd,Zoom);            
      }
   } 
     
}


/*
   ChangeTextColor: Función que cambia el color a todas las textos seleccionadas.                
                
*/      
        
void CListNote::ChangeTextColor(CWnd* pWnd,double Zoom)
{ 

   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPosSel();
   while (Pos != NULL)
   {
      pTrkObj = GetNextSel(Pos);
                                                              
      if ( pTrkObj->GetObjClassId() == IDOC_ANN_TYPE_TEXT )
      { 
         ((CText*)pTrkObj)->SetColor(m_ColorText); 
         ((CText*)pTrkObj)->Invalidate(pWnd,Zoom);            
      }
   } 
     
}


void CListNote::Invalidate(LONG ObjClassId,CWnd* pWnd,double Zoom)
{  
   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPos(); 
   
   while (Pos != NULL)
   {
      pTrkObj = GetNext(Pos);
      
      if ( pTrkObj->GetObjClassId() == ObjClassId)        
         pTrkObj->Invalidate(pWnd,Zoom);       
   } 

}


void CListNote::SetIconZoom(double IconZoom,CWnd* pWnd,double Zoom)
{ 

   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPos(); 
   
   while (Pos != NULL)
   {
      pTrkObj = GetNext(Pos);
      
      if ( pTrkObj->GetObjClassId() == IDOC_ANN_TYPE_NOTE )
      {  
         if (pTrkObj->IsInUse())
            ((CNote*)pTrkObj)->Invalidate(pWnd,Zoom); 
            
         ((CNote*)pTrkObj)->SetIconZoom(IconZoom);
         
         if (pTrkObj->IsInUse())
            ((CNote*)pTrkObj)->Invalidate(pWnd,Zoom);          
      }
   } 
     
} 

/*
   DeleteAllObjSel: Función que elimina de la lista todos los objetos seleccionados.                
                
*/ 
 

void CListNote::DeleteAllObjSel(CWnd* pWnd,double Zoom)
{ 
   CTrkGear* pTrkObj;    
   int status;
   BOOL del;
   POSITION Pos = GetHeadPosSel();
   
   while (Pos != NULL)
   {
      pTrkObj = (CTrkGear*)GetNextSel(Pos); 
      pTrkObj->GetDelete(del);
      if (del)
      {
         pTrkObj->GetStatus(status);
         pTrkObj->SetStatus(IDOC_ANN_STATUS_DEL); 
         Unselect(pTrkObj,pWnd,Zoom); //quito la selección para que no se pinte el rectángulo
         RemoveSel(pTrkObj); //se borra de la lista de seleccionados
         pTrkObj->Invalidate(pWnd,Zoom);
         //RemoveSel(pTrkObj); //se borra de la lista de seleccionados
      
         if (status == IDOC_ANN_STATUS_NEW)
            Remove(pTrkObj);//solo si el estado era NEW se borra de la lista general
      }      
   }
}

void CListNote::DeleteAllObj()
{ 
   CTrkGear* pTrkObj;    
   int status;
   BOOL del;
   POSITION Pos = GetHeadPos();
   
   while (Pos != NULL)
   {
      pTrkObj = (CTrkGear*)GetNext(Pos); 
      pTrkObj->GetDelete(del);
      if (del)
      {
         pTrkObj->GetStatus(status);
         pTrkObj->SetStatus(IDOC_ANN_STATUS_DEL);                     
      
         if (status == IDOC_ANN_STATUS_NEW)
            Remove(pTrkObj);//solo si el estado era NEW se borra de la lista general
      }      
   }
}


BOOL CListNote::OnSetCursor(CWnd* pWnd,UINT HitTest,
                              UINT Msg,double Zoom) const
{  
   BOOL     Set = FALSE;
   int      Mode = GetExeMode();   
   POSITION Pos = GetHeadPos(); 
   
   
   if ( (Mode & XM_Run) )
   {
      while (Pos != NULL)
      {
         CTrkObj* pTrkObj = GetNext(Pos);  
                 
         
         if ( pTrkObj->IsKindOf(RUNTIME_CLASS(CNote)) )
         { 
            Set = ((CNote*)pTrkObj)->OnSetCursor(pWnd,HitTest,Msg,Zoom);            
         } 
         
         if (Set) break;
      }
      
   }
   else 
      Set = CTrkObjList::OnSetCursor(pWnd,HitTest,Msg,Zoom); 
      
   return(Set);  
}

void CListNote::DelSelectAll(CWnd* pWnd,double Zoom)
{ 
   CTrkObj* pTrkObj;
      
   POSITION Pos = GetHeadPosSel(); 
   
   while (Pos != NULL)
   {
      pTrkObj = GetNextSel(Pos); 
      Unselect(pTrkObj,pWnd,Zoom);      
      pTrkObj->Invalidate(pWnd,Zoom);            
   }
   
} 

/*
   ChangeTextFont:Función que cambia el fuente de los objetos texto 
                  seleccionados de la lista por el valor de la variable 
                  fuente de la lista en ese momento.
               
*/  

void CListNote::ChangeTextFont(CWnd* pWnd,double Zoom)
{
   CTrkObj* pTrkObj;       
   POSITION Pos = GetHeadPosSel();
   CFont    Font;
   
   Font.CreateFontIndirect(&m_TextFont); 
   
   while (Pos != NULL)
   {
      pTrkObj = GetNextSel(Pos);
      LONG ObjClassId = pTrkObj->GetObjClassId();
      
      if (ObjClassId == IDOC_ANN_TYPE_TEXT)
      { 
         ((CText*)pTrkObj)->SetFont(ICFont(Font)); 
         ((CText*)pTrkObj)->Invalidate(pWnd,Zoom);      
      }         
   }   
} 

void CListNote::GetTextFont(LOGFONT& LogFont) const
{     
   LogFont = m_TextFont;
} 

int CListNote::GetTextFontSize() const
{     
   return(m_TextFontSize);
}

void CListNote::SetTextFont(const LOGFONT& LogFont)
{
   m_TextFont = LogFont;
} 

void CListNote::SetTextFontSize(int FontSize)
{
   m_TextFontSize = FontSize;
}



void InitFont(LOGFONT* pFont)
{  
   CFont Font;
   
   Font.CreateFont(-27,0,0,0,FW_NORMAL,FALSE,FALSE,FALSE,ANSI_CHARSET, 
                   OUT_DEFAULT_PRECIS,CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,
                   DEFAULT_PITCH,"Arial");   
  
   Font.GetObject(sizeof(LOGFONT),pFont);
   
   Font.DeleteObject();    
}



BOOL CListNote::OnNewObj(LONG ObjClassId,POINT Point,UINT Flags,CWnd* pWnd,
                           double Zoom,int UpdateMode)
{

   BOOL Created = FALSE;

   UnselectAll(pWnd,Zoom,UpdateMode);

   CGearTrk* pTrk = (CGearTrk*)SetupNewObjTrk(ObjClassId);   

   if (pTrk != NULL)
   {
      pTrk->SetParentView((CVw*)pWnd);
      Created = pTrk->TrackRubberBand(Point,Flags,pWnd,Zoom);
   }

   if (Created)
   {

      CTrkObj* pObj = CreateObj(ObjClassId,pTrk,pWnd);

      if (pObj != NULL)
      {
         Add(pObj);          
         Select(pObj,pWnd,Zoom,UpdateMode);
      }
      else
         Created = FALSE;

   }

   delete pTrk;

   return(Created);

}

BOOL CListNote::OnContextMenu(CWnd* pWnd, CPoint point,double Zoom) 
{
   BOOL Ret = FALSE;
   int HitCode;
   CPoint pClient = point;
   pWnd->ScreenToClient(&pClient);

   CTrkGear* pObj = (CTrkGear*)GetHitObj(pClient,pWnd,HitCode,Zoom);

   if (pObj != NULL)
   {
      pObj->OnContextMenu(pWnd,point);
      Ret = TRUE;
   }

   return(Ret);
}


BOOL CListNote::PropertyInfo(CWnd* pWnd,CPoint point,double Zoom)
{
   BOOL Ret = FALSE;
   int HitCode;
   
   CTrkGear* pObj = (CTrkGear*)GetHitObj(point,pWnd,HitCode,Zoom);

   if (pObj != NULL)
      Ret = pObj->PropertyInfo(pWnd,point);

   return(Ret);
}

void CListNote::SetInUse(BOOL Show,CWnd* pWnd,double Zoom)
{
   //CTrkObjList::SetInUse(IDOC_ANN_TYPE_NAVEGATION,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_TEXT,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_NOTE,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_HILITE,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_LINE,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_ARROW,Show,pWnd,Zoom);
   //CTrkObjList::SetInUse(IDOC_ANN_TYPE_PICT,Show,pWnd,Zoom);
   CTrkObjList::SetInUse(IDOC_ANN_TYPE_CHECKMARK,Show,pWnd,Zoom);
}


