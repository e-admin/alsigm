// Vw.cpp : implementation file
//

#include "stdafx.h"
#include "afxstat_.h"
#include "resource.h"
#include "iDocImgx.h"
#include "iDocGear.h"
#include "trk.h"
#include "trksel.h"
#include "Vw.h"
#include "mf.h"
#include "pagirdlg.h"
#include "Confgdlg.h"
#include "TratsIG.h"
#include "AuxFnc.h"
#include "ifont.h"
#include "ico.h"
#include "listNote.h"
#include "Ann.h"
#include "EndosoDlg.h"
#include "errs.h"

#undef EXTERN
#define EXTERN 
#define INI_EXT

#include "printlook.h"



#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define TMT_NONE   0
#define TMT_ACL    1
#define TMT_AGC    2
#define TMT_ATD    3
#define TMT_Bold   4
#define TMT_EFD    5
#define TMT_EMA    6
#define TMT_EMT    7
#define TMT_ETT    8
#define TMT_Light  9
#define TMT_Shadow 10


/// tipo de anotación//
#define NULL_PALTOOL     0
#define NAV_PALTOOL      1
#define TEXT_PALTOOL     2
#define NOTE_PALTOOL     3
#define HIGHLINE_PALTOOL 4
#define LINE_PALTOOL     5
#define ARROW_PALTOOL    6
#define IMAGE_PALTOOL    7
#define MARK_PALTOOL     8


CString dialogo("PRINTDLGLOOK"); 


LPCSTR	pcszSFormatList = \
	"BMP Uncompressed (*.bmp)|*.bmp|\
BMP RLE (*.bmp)|*.bmp|\
BRK G3 (*.301)|*.301|\
BRK G3 2D (*.301)|*.301|\
CALS (*.cal)|*.cal|\
CLP (*.clp)|*.clp|\
DCX (*.dcx)|*.dcx|\
EPS (*.eps)|*.eps|\
IOCA G3 (*.ica)|*.ica|\
IOCA G4 (*.ica)|*.ica|\
ICO (*.ico)|*.ico|\
IFF (*.iff)|*.iff|\
IMNET G4 (*.imt)|*.imt|\
JPEG (*.jpg)|*.jpg|\
MODCA G3 (*.mod)|*.mod|\
MODCA G4 (*.mod)|*.mod|\
PCT (*.pct)|*.pct|\
PCX (*.pcx)|*.pcx|\
PNG (*.png)|*.png|\
PSD (*.psd)|*.psd|\
NCR G4 (*.ncr)|*.ncr|\
RAS (*.ras)|*.ras|\
SGI (*.sgi)|*.sgi|\
Targa (*.tga)|*.tga|\
Tiff Uncompressed (*.tif)|*.tif|\
Tiff PackBits (*.tif)|*.tif|\
Tiff Huffman (*.tif)|*.tif|\
Tiff CCITT G3 (*.tif)|*.tif|\
Tiff CCITT G3 2D (*.tif)|*.tif|\
Tiff CCITT G4 (*.tif)|*.tif|\
Tiff JPEG (*.tif)|*.tif|\
XBM (*.xbm)|*.xbm|\
XPM (*.xpm)|*.xpm|\
XWD (*.xwd)|*.xwd|\
All Files (*.*)|*.*|\0";
	/*"WMF (*.wmf)"					"\0"	"*.wmf"	"\0"	\  */
/*	"WPG (*.wpg)"					"\0"	"*.wpg"	"\0"	\	*/
	

const AT_LMODE	nSFormatList[] =
{
	IG_SAVE_BMP_UNCOMP,
	IG_SAVE_BMP_RLE,
	IG_SAVE_BRK_G3,
	IG_SAVE_BRK_G3_2D,		
	IG_SAVE_CAL,	
	IG_SAVE_CLP,
	IG_SAVE_DCX,	
	IG_SAVE_EPS,	
	IG_SAVE_ICA_G3,
	IG_SAVE_ICA_G4,
	IG_SAVE_ICO,
	IG_SAVE_IFF,					
	IG_SAVE_IMT,					
	IG_SAVE_JPG,
	IG_SAVE_MOD_G3,
	IG_SAVE_MOD_G4,
	IG_SAVE_PCT,	
	IG_SAVE_PCX,
	IG_SAVE_PNG,
	IG_SAVE_PSD,
	IG_SAVE_NCR_G4,
	IG_SAVE_RAS,
	IG_SAVE_SGI,				
	IG_SAVE_TGA,
	IG_SAVE_TIF_UNCOMP,
	IG_SAVE_TIF_PACKED,
	IG_SAVE_TIF_HUFFMAN,
	IG_SAVE_TIF_G3,
	IG_SAVE_TIF_G3_2D,
	IG_SAVE_TIF_G4,				
	IG_SAVE_TIF_JPG,
//	IG_SAVE_WMF,
//	IG_SAVE_WPG,
	IG_SAVE_XBM,
	IG_SAVE_XPM,
	IG_SAVE_XWD,
	IG_SAVE_UNKNOWN,
};

UINT	nSFormatListCount = sizeof(nSFormatList)/sizeof(*nSFormatList);

/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
VOID ACCUAPI SelectRect(LPVOID lpPrivate, const LPAT_RECT lprcSelect)
{
   LPMYSELECTDATA lpSelect;

   lpSelect = (LPMYSELECTDATA)lpPrivate;
   CRect rect;
   rect.left = lprcSelect->left;
   rect.right = lprcSelect->right;
   rect.top = lprcSelect->top;
   rect.bottom = lprcSelect->bottom;

   if (rect.IsRectEmpty())
   {
      LocalFree((HLOCAL)(UINT)(DWORD)lpSelect);
      return;
   }
   if (lpSelect && lpSelect->hModGear != NULL)
	{   
      LPFNZoomRectSet lpfn;
      
      lpfn = (LPFNZoomRectSet)GetProcAddress(lpSelect->hModGear,"IG_display_zoom_rect_set");

		// zoom del rectángulo especificado por lprcSelect
		lpfn(lpSelect->hIGear, lpSelect->hwnd, lprcSelect,
			  lpSelect->lpZoomLevel, lpSelect->lprcZoom);

      LocalFree((HLOCAL)(UINT)(DWORD)lpSelect);
   }
}


/////////////////////////////////////////////////////////////////////////////
// CVw

VOID ACCUAPI SelectRect(LPVOID  lpPrivate,const LPAT_RECT lprcSelect);


IMPLEMENT_DYNCREATE(CVw, CView)

CVw::CVw()
:m_ptoContext(0,0)
{
   m_hIGear     = 0;
   m_IsGray     = FALSE;
   m_pIrPag     = NULL;
   m_pConfig    = NULL;
   m_TMT        = TMT_NONE;
   m_Undo       = FALSE;
   m_DoUndo     = FALSE;
   m_paint      = TRUE; 
   m_SetZoomRectNow = FALSE;
   m_EditAnn    = FALSE;
   m_PalTool    = NULL_PALTOOL;
   m_IdAnn      = 0;
   m_TermAnn    = FALSE;
   m_ChangeAnn  = FALSE;
   m_AnyChangeAnn = FALSE;
   m_RotIcon    = FALSE;
   m_pAnnDoc    = NULL;
   m_pEndoso    = NULL;
   m_CanEditAnn = TRUE;
}

CVw::~CVw()
{
   m_TermAnn = TRUE;
   Term();   
}


BEGIN_MESSAGE_MAP(CVw, CView)
	//{{AFX_MSG_MAP(CVw)
	ON_WM_ERASEBKGND()
	ON_WM_SIZE()
	ON_WM_HSCROLL()
	ON_WM_VSCROLL()
   ON_WM_LBUTTONDOWN()
   ON_WM_LBUTTONUP()
   ON_WM_LBUTTONDBLCLK()
   ON_WM_MOUSEMOVE()
   ON_WM_SETCURSOR()
   ON_WM_CONTEXTMENU()
   ON_COMMAND(IDM_SHOWPAL, OnShowpal)
   ON_COMMAND(IDM_HAND, OnHand)
   ON_COMMAND(IDM_FITHORZ, OnFithorz)
	ON_COMMAND(IDM_FITVERT, OnFitvert)
	ON_COMMAND(IDM_FITWINDOW, OnFitwindow)
	ON_COMMAND(IDM_NORMALSIZE, OnNormalsize)
   ON_COMMAND(IDM_ZOOMDECR, OnZoomdecr)
	ON_COMMAND(IDM_ZOOMINCR, OnZoomincr)
   ON_COMMAND(IDM_ZOOMRECT, OnZoomRect)
   ON_COMMAND(IDM_BLACKDRAW, OnBlackdraw)
	ON_COMMAND(IDM_GRAYDRAW, OnGraydraw)
	ON_COMMAND(IDM_WHITEDRAW, OnWhitedraw)
   ON_COMMAND(IDM_FIRSTP, OnFirstp)
	ON_COMMAND(IDM_LASTP, OnLastp)
	ON_COMMAND(IDM_NEXTP, OnNextp)
	ON_COMMAND(IDM_PREVP, OnPrevp)
   ON_COMMAND(IDM_GOTO, OnGoto)
   ON_COMMAND(IDM_ROTD90, OnRotd90)
	ON_COMMAND(IDM_ROTI90, OnRoti90)
   ON_COMMAND(IDM_PRINT, OnPrintImg)
   ON_COMMAND(IDM_CONFIG, OnConfig)
   ON_COMMAND(IDM_TINCRCLOCAL,OnIncrCLocal)
   ON_COMMAND(IDM_TINCRGC,OnIncrGC)
   ON_COMMAND(IDM_TRTD,OnRTD)
   ON_COMMAND(IDM_TTWIDTH,OnTWidth)
   ON_COMMAND(IDM_TSUPFD,OnSupFD)
   ON_COMMAND(IDM_TSUPMTC,OnSupMTC)
   ON_COMMAND(IDM_TSUPMTO,OnSupMTO)
   ON_COMMAND(IDM_TSUPTT,OnSupTT)
   ON_COMMAND(IDM_TTF,OnTF)
   ON_COMMAND(IDM_TREL,OnRel)
   ON_COMMAND(IDM_TUNDO,OnUndo)
   ON_COMMAND(IDM_SELECT,OnSelect)
   ON_COMMAND(IDM_EDITANN, OnEditAnn)
   ON_COMMAND(IDM_SAVEANN, OnSaveAnn)
   ON_COMMAND(IDM_SHOWPALANN, OnShowPalAnn)
   ON_COMMAND(ID_SELECTOR, OnAnnSelector)
   ON_COMMAND(ID_TEXT, OnAnnText)
   ON_COMMAND(ID_NOTE, OnAnnNote)
   ON_COMMAND(ID_HIGHLINE, OnAnnHighline)
   ON_COMMAND(ID_LINE, OnAnnLine)
   ON_COMMAND(ID_ARROW, OnAnnArrow)
   ON_COMMAND(ID_MARK, OnAnnMark)
   ON_COMMAND(IDM_M_PROPERTY, OnMProperty)
   ON_COMMAND(IDM_DELETEANN, OnDeleteAnn)
   ON_COMMAND(IDM_SAVEAS, OnSaveAs)
   ON_COMMAND(IDM_ENDOSO, OnEndoso)
   ON_COMMAND(IDM_INVERT, OnInvert)
	//}}AFX_MSG_MAP
   //ON_COMMAND(ID_FILE_PRINT, OnPrint) 
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CVw drawing

void CVw::OnDraw(CDC* pDC)
{
	LPFNValid   lpfn;
   LPFNDesktop lpfndesktop;
   LPFNDisplay lpfndisplay;
   

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn != NULL)
   {
      lpfndesktop = (LPFNDesktop)GetProcAddress(_Module.m_hModGear,"IG_display_desktop_pattern_set");
      if (lpfndesktop != NULL)
      {
         lpfndisplay = (LPFNDisplay)GetProcAddress(_Module.m_hModGear,"IG_display_image");
         if (lpfndisplay != NULL)
         {
            if (lpfn(m_hIGear) && m_paint)
            {      
               /*CILook4XCtrl* pWnd;
      
	            pWnd =(CILook4XCtrl*)(this->GetParent())->GetParent();
               if (pWnd!= NULL)
               {                  
                  COLORREF color = pWnd->TranslateColor(pWnd->GetBackColor());
                  lpfndesktop(m_hIGear,NULL,NULL,(LPAT_RGB)&color,TRUE);
               }*/
                   
               lpfndisplay(m_hIGear,pDC->m_hDC);
               DrawIcons(pDC,m_hIGear, m_OpenPag);         
            }
           
         }
      }
   }
}

/////////////////////////////////////////////////////////////////////////////
// CVw diagnostics

#ifdef _DEBUG
void CVw::AssertValid() const
{
	CView::AssertValid();
}

void CVw::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CVw message handlers

void CVw::Init()
{
   m_hIGear         = 0;
   m_Zoom           = 100;
   m_showScroll     = TRUE;
   m_FitMode        = FITNONE;  
   m_DrawMode       = PRESERVE_WHITE; 
   m_NumPages       = 1;
   m_OpenPag        = 1; 
   m_ZoomImg        = 1;   
   m_open           = FALSE;
   m_Tool           = P_NONE;
   m_TermAnn        = FALSE;
}

LONG CVw::OpenFile(CString File)
{
   char        name[256];
   AT_ERRCOUNT ErrCount =0;
   LONG        Err = 0;
   CRect       rect;
   HANDLE      fd = 0;   
   LPFNValid   lpfn; 
   LPFNPage    lpfnpage;    
   LPFNCenter  lpfncenter;
   LPFNDesktop lpfndesktop;
   LPFNLoad    lpfnload;
   LPFNIsGray  lpfngray;
   LPFNInfo    lpfnInfo;   
   AT_DIB      dibInfo;
  
      
   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}      
   lpfnpage = (LPFNPage)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnpage == NULL) {Err = -1; goto End;}
   lpfnload = (LPFNLoad)GetProcAddress(_Module.m_hModGear,"IG_load_FD");
   if (lpfnload == NULL) {Err = -1; goto End;}   
   lpfncenter = (LPFNCenter)GetProcAddress(_Module.m_hModGear,"IG_display_centered_set");
   if (lpfncenter == NULL) {Err = -1; goto End;}
   lpfndesktop = (LPFNDesktop)GetProcAddress(_Module.m_hModGear,"IG_display_desktop_pattern_set");
   if (lpfndesktop == NULL) {Err = -1; goto End;}
   lpfngray = (LPFNIsGray)GetProcAddress(_Module.m_hModGear,"IG_image_is_gray");
   if (lpfngray == NULL) {Err = -1; goto End;}
   lpfnInfo = (LPFNInfo)GetProcAddress(_Module.m_hModGear,"IG_info_get_FD");
   if (lpfnInfo == NULL) {Err = -1; goto End;}
   

   lstrcpy(name,File);
   
   Term();
   
   ShowScrollBar(SB_BOTH,FALSE);
   GetClientRect(&rect);

   ErrCount = lpfnpage(name,&m_NumPages);

   fd = CreateFile(name,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,
              FILE_ATTRIBUTE_NORMAL,NULL);

   if (m_OpenPag > m_NumPages)
      m_OpenPag = 1;
   
   if (fd != INVALID_HANDLE_VALUE)
   {
      ErrCount = lpfnload((int)fd,0,m_OpenPag,1,&m_hIGear);
         
      if (ErrCount == IGE_SUCCESS)
      {
         lpfnInfo((int)fd,0,m_OpenPag,(LPAT_MODE)&m_FileType,(LPAT_MODE)&m_FileCompress,&dibInfo);
         m_FileName = File;
         m_FitMode  = FITNONE; //se resetea el modo de fit
         AT_RGB Back; //background Blanco
         Back.b = 255;
         Back.g = 255;
         Back.r = 255;
         ErrCount = lpfncenter(m_hIGear,this->m_hWnd,FALSE);
         ErrCount = lpfndesktop(m_hIGear,NULL,NULL,&Back,TRUE);
         ErrCount = lpfngray(m_hIGear,&m_IsGray);
      
         ShowScroll(m_showScroll);
         //Invalidate(FALSE);


         ChangeSizeTiffG3Fax();
           
      }
      else
         Err = ErrCount;

      CloseHandle(fd);
   }
   else
      Err = (LONG)fd;

   //habría que cargar las anotaciones de esta página si existe.

End:
   
   return Err;

}

void CVw::ReturnSizeTiffG3Fax(int Page)
{

   LONG           Err = 0;
   LPFNIPResize   lpfnIpResize;   
   LPFNDimensions lpfnDimensions;
   HIGEAR         hIGear;
   UINT           bpp;
   CSize          ImgSize;

   if (m_FileType == IG_FORMAT_TIF && m_FileCompress == IG_COMPRESSION_CCITT_G3)
   { 

      lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
      if (lpfnDimensions == NULL) {Err = -1; goto End;}
      lpfnIpResize = (LPFNIPResize)GetProcAddress(_Module.m_hModGear,"IG_IP_resize");
      if (lpfnIpResize == NULL) {Err = -1; goto End;}

      Err = InitPageImg(m_FileName,Page,&hIGear);
      if (!Err)
      {
         lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);

         lpfnIpResize(m_hIGear,ImgSize.cx,ImgSize.cy,IG_INTERPOLATION_NONE);

         lpfnDimensions(m_hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);
      }

   }

   End:

   return;
   

}


void CVw::ChangeSizeTiffG3Fax()
{
   LONG           Err = 0; 
   LPFNIPResize   lpfnIpResize;
   LPFNResolution lpfnResolution;
   LPFNDimensions lpfnDimensions;
   UINT           bpp;
   CSize          ImgSize;
   int            mx,my;                               
   int            pilxW,pilxH;  
   LONG           XResN,XResD,YResN,YResD,Units;
   int            XRes, YRes;
  

   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}
   lpfnResolution = (LPFNResolution)GetProcAddress(_Module.m_hModGear,"IG_image_resolution_get");
   if (lpfnResolution == NULL) {Err = -1; goto End;}
   lpfnIpResize = (LPFNIPResize)GetProcAddress(_Module.m_hModGear,"IG_IP_resize");
   if (lpfnIpResize == NULL) {Err = -1; goto End;}

   if (m_FileType == IG_FORMAT_TIF && m_FileCompress == IG_COMPRESSION_CCITT_G3)
   {    
      lpfnDimensions(m_hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);

      lpfnResolution(m_hIGear,&XResN,&XResD,&YResN,&YResD,(LPAT_MODE)&Units);

      if (Units == IG_RESOLUTION_METERS)
      {
         XResN = (long)(XResN * 0.0254);
         YResN = (long)(YResN * 0.0254);   
      } 
      if (Units == IG_RESOLUTION_CENTIMETERS)
      {
         XResN = (long)((XResN / 100) * 0.0254);
         YResN = (long)((YResN / 100) * 0.0254);
      }  

	   XRes = (int)(XResN / XResD);
	   YRes = (int)(YResN / YResD);


      mx =(int) (((double)ImgSize.cx * 25.4) / XRes);
      my = (int)(((double)ImgSize.cy * 25.4) / YRes);

      if (ImgSize.cx > ImgSize.cy && mx < my)
      {
         pilxW =(int) (((double)mx * YRes) / 25.4);
         pilxH = (int)(((double)my * YRes) / 25.4);

         lpfnIpResize(m_hIGear,pilxW,pilxH,IG_INTERPOLATION_NONE);
      }     
   } 
   
   End:

   return;
}

void CVw::OnInitialUpdate() 
{
	CView::OnInitialUpdate();
		
   Init();
	
}


void CVw::Term()
{   
   LPFNValid lpfn;
   LPFNDelete lpfndelete;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfndelete = (LPFNDelete)GetProcAddress(_Module.m_hModGear,"IG_image_delete");
   if (lpfndelete == NULL) return;

   if (lpfn(m_hIGear))
   {
      lpfndelete(m_hIGear);      
      m_FileName.Empty();           
   }

   if (m_pIrPag != NULL)
   {
      delete(m_pIrPag);
      m_pIrPag = NULL;
   }

   if (m_pConfig != NULL)
   {
      delete(m_pConfig);
      m_pConfig = NULL;
   }

   if (m_pEndoso != NULL)
   {
      delete(m_pEndoso);
      m_pEndoso = NULL;
   }

   if (m_TermAnn)
   {
      DeleteArrAnnot();
      m_FileNote.Empty();
      m_TermAnn = FALSE;
      m_ChangeAnn = FALSE;
      m_AnyChangeAnn = FALSE;
    //terminar todo lo referente a anotaciones
   }


}

void CVw::ShowScroll(BOOL enable)
{  
   LPFNValid lpfn;
   LPFNScrollSet lpfnscrollset;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfnscrollset = (LPFNScrollSet)GetProcAddress(_Module.m_hModGear,"IG_display_scrollbar_set");
   if (lpfnscrollset == NULL) return;
   
   m_showScroll = enable;
   if (lpfn(m_hIGear))
   {      
      lpfnscrollset(m_hIGear,m_showScroll);
      ShowScrollBar(SB_BOTH,m_showScroll); 
      SetScroll(5,0,TRUE);
      SetScroll(5,0,FALSE);
   }

}

LONG CVw::Display()
{
   LONG     Err  = 0;
   CMF*     pMF  = (CMF*)GetParent();
   BOOL     open = FALSE;
   ICRegIni reg;
   BOOL     DiferentPag = FALSE;
     

   reg.LoadSettings();   
   
   if (pMF->m_FileName.CompareNoCase(m_FileName) || pMF->m_Page != (long)m_OpenPag)
   {
      open = TRUE;     
      if (pMF->m_Page != (long)m_OpenPag)           
         DiferentPag = TRUE;
      //indica si debemos borrar el fichero de anotaciones 
      if (pMF->m_FileName.CompareNoCase(m_FileName)) 
         m_TermAnn = TRUE;
      else
         m_TermAnn = FALSE;
      
      m_OpenPag = pMF->m_Page;
 
      Err = OpenFile(pMF->m_FileName);
      if (Err)
      {
         CString Msg;
      Msg.LoadString(IDS_ERR_OPEN_IMG);
      AfxMessageBox(Msg);
         goto End; 
      }

      
      m_open = TRUE;
      m_Print = pMF->m_EnablePrint;
      m_SaveAs = pMF->m_EnableSaveAs;
      SetStateToolBar();      
      GetEndoso(pMF->m_FileName,m_Endoso);

      //Fichero de anotaciones
      if (pMF->m_FileNote.CompareNoCase(m_FileNote))
      {
         m_FileNote = pMF->m_FileNote;

         ReadFileAnn();
         
      }
      
   }
   else
   {
      //Fichero de anotaciones
      if (pMF->m_FileNote.CompareNoCase(m_FileNote))
      {
         m_FileNote = pMF->m_FileNote;
         ReadFileAnn();
      }
   }

   if (open)
   {
      if (!DiferentPag && !m_DoUndo)
      {
         if (pMF->m_InitRot)
            m_Rot = pMF->m_Rotation;
         else
            m_Rot = reg.GetRot(); //devuelve 0,1,2,3
      }
      Rot(m_Rot,TRUE);
      m_RotIcon = TRUE;
      
   }

   
   if (open)
   {         
      if (!DiferentPag && !m_DoUndo)
      {
         if (pMF->m_InitFit)
            m_FitMode = (enumFitMode)pMF->m_FitMode;
         else
            m_FitMode = (enumFitMode)reg.GetFit();
         
      }
      if (m_FitMode != FITNONE)
         Fit();         
      else
      {
         m_ZoomImg = pMF->m_ZoomImg;
         SetZoom();
      } 
      
   }
   else
   {
      if (m_FitMode != FITNONE)
         Fit();         
      else         
         SetZoom();
      
   }


   
   if (open)
   {
      if (!DiferentPag && !m_DoUndo)
      {
         if (pMF->m_InitDrawMode )
         {
            m_DrawMode = (enumDrawMode)pMF->m_DrawMode;            
         }
         else
            m_DrawMode = (enumDrawMode)reg.GetDrawMode();
      }
      SetDrawMode();
      
   }
   else
      SetDrawMode();

   if (open && !DiferentPag && !m_DoUndo)
   {
      if (m_Tool != P_SEL)
      {
         DrawSelRect();
         m_SelRect.SetRectEmpty();
      }
      if (m_paint != FALSE)
      {
         if (pMF->m_InitTB)
            ShowPal(pMF->m_ShowToolbar);
         else
            ShowPal(reg.GetPalette());   
      }
   }

      
   pMF->m_ZoomImg = m_ZoomImg;
   pMF->m_FitMode = m_FitMode;
   pMF->m_DrawMode = m_DrawMode;   

   End:

   CheckPagesTool();

   Invalidate(FALSE);
   
   return(Err);
}

long CVw::GetNumPages()
{
   long pages = 0;

   if (m_hIGear != 0)
      pages = m_NumPages;

   return(pages);
}

void CVw::Clear()
{
   CMF*    pMF = (CMF*)GetParent();
   CString File;
   
   m_TermAnn = TRUE;
   Term();
   m_open = FALSE;
   ShowScrollBar(SB_BOTH,FALSE);   
   Invalidate();
   pMF->SetFileName(File);
}

BOOL CVw::OnEraseBkgnd(CDC* pDC) 
{
	LPFNValid lpfn;
  
   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn != NULL)
	   if (lpfn(m_hIGear))
         return TRUE;
      
	return CView::OnEraseBkgnd(pDC);
}

void CVw::OnSize(UINT nType, int cx, int cy) 
{
	CRect rectImg;
   LPFNValid lpfn;
   LPFNFit   lpfnfit;
   LPFNResize lpfnresize;   
  
	CView::OnSize(nType, cx, cy);

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;      
   lpfnfit = (LPFNFit)GetProcAddress(_Module.m_hModGear,"IG_display_fit_method");
   if (lpfnfit == NULL) return;
   lpfnresize = (LPFNResize)GetProcAddress(_Module.m_hModGear,"IG_display_handle_resize");
   if (lpfnresize == NULL) return;
      
   rectImg.left = 0;
   rectImg.right = cx;
   rectImg.top = 0;
   rectImg.bottom = cy;

   
   	
   if (lpfn(m_hIGear))
   {      
      ///función que mantiene el fit que se tenga
      if (m_FitMode != FITNONE)
      {
         lpfnfit(m_hIGear,this->m_hWnd,(LPAT_RECT)&rectImg,&m_Zoom,(AT_MODE)m_FitMode);        
         GetZoomImg();
      }
      else
         lpfnresize(m_hIGear,this->m_hWnd,(LPAT_RECT)&rectImg,&m_Zoom);
      
   } 
   
   if (!m_SetZoomRectNow && m_FitMode != FITNONE)
   {      
      SetScroll(5,0,TRUE);
      SetScroll(5,0,FALSE);     
      Invalidate(FALSE);
   }
	
}

void CVw::GetZoomImg()
{
   CSize        ImgSize;
   CRect        TotalRect;
   AT_DIMENSION W,H;
   UINT         bpp;   
   CRect        DCRect;
   LPFNValid    lpfn;
   LPFNDimensions lpfnDimensions;
   LPFNDeviceRectGet lpfnDeviceRectGet;
   
   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) return;
   lpfnDeviceRectGet = (LPFNDeviceRectGet)GetProcAddress(_Module.m_hModGear,"IG_device_rect_get");
   if (lpfnDeviceRectGet == NULL) return;

   GetClientRect(DCRect);

   if (lpfn(m_hIGear))
   {
      lpfnDimensions(m_hIGear,&W,&H,&bpp);
      lpfnDeviceRectGet(m_hIGear,(LPAT_RECT)&TotalRect);
      ImgSize.cx = W;
      ImgSize.cy = H;
      
      
      m_ZoomImg = ((double) TotalRect.Width()) / ImgSize.cx;      

   }
}

void CVw::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	SetScroll(nSBCode,nPos, TRUE);
}

void CVw::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	SetScroll(nSBCode, nPos, FALSE);		
}

LONG CVw::Fit()
{
   AT_ERRCOUNT ErrCount = 0;
   LONG        Err = 0;
   CRect       rect;
   LPFNValid   lpfn;
   LPFNFit     lpfnfit;
   LPFNCenter  lpfncenter;
   int         FitMode;
     
   
   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   lpfnfit = (LPFNFit)GetProcAddress(_Module.m_hModGear,"IG_display_fit_method");
   if (lpfnfit == NULL) {Err = -1; goto End;}
   lpfncenter = (LPFNCenter)GetProcAddress(_Module.m_hModGear,"IG_display_centered_set");
   if (lpfncenter == NULL) {Err = -1; goto End;}

   switch(m_FitMode)
   {
      case FITWINDOW: {FitMode = IG_DISPLAY_FIT_TO_WINDOW; break;}
      case FITWIDTH:  {FitMode = IG_DISPLAY_FIT_TO_WIDTH; break;}
      case FITHEIGHT: {FitMode = IG_DISPLAY_FIT_TO_HEIGHT; break;}      
   }

   if (lpfn(m_hIGear))
   {  
      m_FitMode = FITNONE;

      lpfncenter(m_hIGear,this->m_hWnd,FALSE);  
      ErrCount = lpfnfit(m_hIGear,this->m_hWnd,(LPAT_RECT)&rect,&m_Zoom,FitMode);
    
      if (!ErrCount)
      {
         switch(FitMode)
         {
            case IG_DISPLAY_FIT_TO_WINDOW: {m_FitMode = FITWINDOW; break;}
            case IG_DISPLAY_FIT_TO_WIDTH:  {m_FitMode = FITWIDTH; break;}
            case IG_DISPLAY_FIT_TO_HEIGHT: {m_FitMode = FITHEIGHT; break;}      
         }
      }            
      GetZoomImg();
   }

 //  SetScroll(5,0,TRUE);
 //  SetScroll(5,0,FALSE);

   if (ErrCount)
      Err = ErrCount;

End:
   return(Err);
}

///función que a partir del zoom de imagen deseado
///lo traduce a zoom de device tal como lo trata
///accusoft.

LONG CVw::SetZoom()
{
   CSize          ImgSize;
   AT_DIMENSION   W,H;
   UINT           bpp;
   CRect          rect;
   UINT           HZoom,VZoom;
   LPFNDimensions lpfnDimensions;
   LPFNZoomSet    lpfnZoomSet;
    
   GetClientRect(rect);
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) goto End;
   lpfnZoomSet = (LPFNZoomSet)GetProcAddress(_Module.m_hModGear,"IG_display_zoom_set");
   if (lpfnZoomSet == NULL) goto End;
      

   //if (!IsValidZoom(m_ZoomImg))
   //   return(-1);
   
   //m_ZoomImg = ZoomImg;
   
   lpfnDimensions(m_hIGear,&W,&H,&bpp);
   ImgSize.cx = W;
   ImgSize.cy = H;


   HZoom = (UINT)(((double)ImgSize.cx * m_ZoomImg / rect.Width()) *100);
   VZoom = (UINT)(((double)ImgSize.cy * m_ZoomImg / rect.Height()) *100);
   if (HZoom >= VZoom)
      m_Zoom = HZoom;
   else 
      m_Zoom = VZoom;

      

   m_FitMode = FITNONE;
   lpfnZoomSet(m_hIGear,this->m_hWnd,(UINT)m_Zoom,(LPAT_RECT)&rect);
  
 
End:

   return 0;

}

LONG CVw::SetDrawMode()
{
   AT_MODE      mode;
   AT_ERRCOUNT  Err = 0;
   UINT         quality;
   BOOL         subsample;
   LPFNValid    lpfn;
   LPFNAliasGet lpfnAliasGet;
   LPFNAliasSet lpfnAliasSet;
   AT_MODE      DrawMode; 
   
   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   lpfnAliasGet = (LPFNAliasGet)GetProcAddress(_Module.m_hModGear,"IG_display_alias_get");
   if (lpfnAliasGet == NULL) {Err = -1; goto End;}
   lpfnAliasSet = (LPFNAliasSet)GetProcAddress(_Module.m_hModGear,"IG_display_alias_set");
   if (lpfnAliasSet == NULL) {Err = -1; goto End;}

   switch(m_DrawMode)
   {
      case PRESERVE_WHITE: {DrawMode = IG_DISPLAY_ALIAS_NONE; break;}
      case PRESERVE_BLACK: {DrawMode = IG_DISPLAY_ALIAS_PRESERVE_BLACK; break;}
      case SCALE_TO_GRAY:  {DrawMode = IG_DISPLAY_ALIAS_SCALE_TO_GRAY; break;}
   }
   if (lpfn(m_hIGear))
   {
      Err = lpfnAliasGet(m_hIGear,&mode,&quality,&subsample);
      if (mode != (AT_MODE)DrawMode)
      {
         Err = lpfnAliasSet(m_hIGear,DrawMode,100,FALSE);         
      }
      
   }
 

End:
   return((LONG)Err);

}


void CVw::OnLButtonDown(UINT nFlags, CPoint point) 
{
   LONG   Err = 0;
   CMF*   pMF  = (CMF*)GetParent();
  
   if (!m_open)
      return;

   if (m_EditAnn)
   {
      BOOL   Changed;
      POSITION pos;

      GetZoomImg();
         
      pos = m_ListPages.FindIndex(m_OpenPag - 1);
      if (pos != NULL)
      {    
         CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);
         Changed = pNote->OnLButtonDown(m_PalTool,point,nFlags,this,m_ZoomImg,UPDATE_THIS); 
         if (Changed)
         {
            m_ChangeAnn = TRUE;
            pMF->EventModifyNotes();
            m_AnyChangeAnn = TRUE;
         }

         OnAnnSelector();
        
      }   

   }
   

   switch(m_Tool)
   {
      case P_ZINC:
      {         
         GetZoomImg();
         pMF->m_FitMode = FITNONE;
         m_ZoomImg = m_ZoomImg * (1 + 0.1);
         SetZoom();   
         pMF->m_ZoomImg = m_ZoomImg;
        
         break;
      }
      case P_ZDEC:
      {
         GetZoomImg();
         pMF->m_FitMode = FITNONE;
         m_ZoomImg = m_ZoomImg / (1 + 0.1);
         SetZoom();	
         pMF->m_ZoomImg = m_ZoomImg;

         break;
      }
      case P_ZRECT:
      {
         GetZoomImg();
         pMF->m_FitMode = FITNONE;
         SetZoomRect(point);         
      }
      case P_DRAG:
      {
         SavePointToDrag(point);
         break;
      }
      case P_SEL:
      {
         GetSelRect(point,m_SelRect);
         break;
      }
      default:
         break;
   }

   if (m_pIrPag != NULL)
   {
      m_pIrPag->DestroyWindow();
      delete(m_pIrPag);
      m_pIrPag = NULL;
   }
   if (m_pConfig != NULL)
   {
      m_pConfig->DestroyWindow();
      delete(m_pConfig);
      m_pConfig = NULL;
   }

   if (m_pEndoso != NULL)
   {
      m_pEndoso->DestroyWindow();
      delete(m_pEndoso);
      m_pEndoso = NULL;
   }

	CView::OnLButtonDown(nFlags, point);
}

void CVw::OnLButtonDblClk(UINT nFlags, CPoint point) 
{
   
   if (!m_EditAnn)
   {
      POSITION pos;
      pos = m_ListPages.FindIndex(m_OpenPag - 1);
      if (pos != NULL)
      {
         CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);      
         pNote->OnLButtonDblClk(point,nFlags,this,m_Zoom);
      }      
            
   }
	
	CView::OnLButtonDblClk(nFlags, point);

}

void CVw::SavePointToDrag(CPoint point)
{
	m_PointAntDrag.x = point.x;
	m_PointAntDrag.y = point.y;
}

void CVw::EmptyPointDrag()
{
	m_PointAntDrag.x = 0;
	m_PointAntDrag.y = 0;
}

void CVw::DragImg(CPoint point)
{
   CPoint Pos,PosB;   
   CRect  TotalRect;
   int    LimitH,LimitV;      
   
   PosB.x = (long)GetScrollPos(SB_HORZ);
   PosB.y = (long)GetScrollPos(SB_VERT);
  
   LimitH = GetScrollLimit(SB_HORZ);
   LimitV = GetScrollLimit(SB_VERT);
   
  
   if (m_PointAntDrag.x > point.x)
      Pos.x = PosB.x + (m_PointAntDrag.x - point.x);
   else
      Pos.x = PosB.x -(point.x - m_PointAntDrag.x);
   m_PointAntDrag.x = point.x;

   if (m_PointAntDrag.y > point.y)
      Pos.y = PosB.y + (m_PointAntDrag.y - point.y);
   else
      Pos.y = PosB.y - (point.y - m_PointAntDrag.y);
   m_PointAntDrag.y = point.y;

   ///hay que controlar que se ha llegado al límite
   
   if (Pos.x <= LimitH && Pos.x > 0 && Pos.x != PosB.x)
   { 
      SetScroll(5,Pos.x,TRUE);
   }
   if (Pos.y <= LimitV && Pos.y > 0 && Pos.y != PosB.y)
   { 
      SetScroll(5,Pos.y, FALSE);
   }

   UpdateWindow();
 
}

void CVw::SetZoomRect(CPoint point)
{
   
   CRect       ZoomRect;
   AT_ERRCOUNT ErrCount;
   LPFNTrackMouse lpfn;

   m_FitMode = FITNONE;
   
   lpfn = (LPFNTrackMouse)GetProcAddress(_Module.m_hModGear,"IG_GUI_select_track_mouse");
   if (lpfn == NULL) return;

   m_lpSelectData = (LPMYSELECTDATA)LocalAlloc(LPTR, sizeof(MYSELECTDATA));

	if (m_lpSelectData)
	{
		m_lpSelectData->hIGear		  = m_hIGear;
		m_lpSelectData->hwnd		  = this->m_hWnd;
		m_lpSelectData->lpZoomLevel = &m_Zoom;
		m_lpSelectData->lprcZoom	  = (LPAT_RECT)&ZoomRect;  
      m_lpSelectData->hModGear   = _Module.m_hModGear;
	}
	m_SetZoomRectNow = TRUE;
	ErrCount = lpfn(m_hIGear, this->m_hWnd, point.x,  point.y,  SelectRect,m_lpSelectData);
   m_SetZoomRectNow = FALSE;

}


void CVw::OnLButtonUp(UINT nFlags, CPoint point) 
{
   CMF* pMF = (CMF*)GetParent();
 
   if (!m_open)
      goto End;

	EmptyPointDrag();
   
   if (m_Tool == P_ZRECT)
      pMF->m_ZoomImg = m_ZoomImg;
    

End:

	CView::OnLButtonUp(nFlags, point);
}


void CVw::OnMouseMove(UINT nFlags, CPoint point) 
{

	if (nFlags == MK_LBUTTON && m_Tool == P_DRAG && m_open)
	{		
		DragImg(point);
	}
}

void CVw::OnContextMenu(CWnd* pWnd, CPoint point) 
{
   POSITION pos;
   BOOL     Ret = FALSE;
   CMF*     pFrame = (CMF*)GetParent();
	CMenu    menu;
   CMenu*   pSubMenu = NULL;
   CMenu*   pMenuImagen = NULL;
   CMenu*   pMenuModov = NULL;
   CMenu*   pMenuPages = NULL;
   CMenu*   pMenuTrat = NULL;
   CMenu*   pMenuAnn = NULL;
   CMenu*   pMenuTypeAnn = NULL;   
   UINT     bpp;
    
   if (!m_open)
      return;

   m_ptoContext = point;
   pos = m_ListPages.FindIndex(m_OpenPag -1);
   if (pos != NULL && m_EditAnn)
   {
      CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);
      Ret = pNote->OnContextMenu(pWnd,point,m_Zoom);    
   }

   if (Ret) return;

   GetBitPerPixel(m_hIGear,bpp);

   if (pFrame->m_hMod != NULL && m_IsGray)
      menu.LoadMenu(IDR_TRMAINFRAME);
   else
      menu.LoadMenu(IDR_MAINFRAME);

   pSubMenu = menu.GetSubMenu(0);
  if (pFrame->m_wndToolBar.IsWindowVisible())
      pSubMenu->CheckMenuItem(IDM_SHOWPAL,MF_CHECKED|MF_BYCOMMAND);

   if (m_Print == FALSE)
      pSubMenu->EnableMenuItem(IDM_PRINT,MF_GRAYED|MF_BYCOMMAND);

   if (m_SaveAs == FALSE)
      pSubMenu->EnableMenuItem(IDM_SAVEAS, MF_GRAYED|MF_BYCOMMAND);

   pMenuPages = pSubMenu->GetSubMenu(2);
   if (m_NumPages == 1)
   {
      pMenuPages->EnableMenuItem(IDM_NEXTP,MF_GRAYED|MF_BYCOMMAND);
      pMenuPages->EnableMenuItem(IDM_PREVP,MF_GRAYED|MF_BYCOMMAND);
      pMenuPages->EnableMenuItem(IDM_LASTP,MF_GRAYED|MF_BYCOMMAND);
      pMenuPages->EnableMenuItem(IDM_FIRSTP,MF_GRAYED|MF_BYCOMMAND);
   }
   else
   {
      if (m_OpenPag == m_NumPages)
      {
         pMenuPages->EnableMenuItem(IDM_NEXTP,MF_GRAYED|MF_BYCOMMAND);
         pMenuPages->EnableMenuItem(IDM_LASTP,MF_GRAYED|MF_BYCOMMAND);
         pMenuPages->EnableMenuItem(IDM_FIRSTP,MF_ENABLED|MF_BYCOMMAND);
         pMenuPages->EnableMenuItem(IDM_PREVP,MF_ENABLED|MF_BYCOMMAND);

      }
      else
      {
         if (m_OpenPag == 1)
         {
            pMenuPages->EnableMenuItem(IDM_NEXTP,MF_ENABLED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_LASTP,MF_ENABLED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_FIRSTP,MF_GRAYED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_PREVP,MF_GRAYED|MF_BYCOMMAND);
         }
         else
         {
            pMenuPages->EnableMenuItem(IDM_NEXTP,MF_ENABLED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_LASTP,MF_ENABLED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_FIRSTP,MF_ENABLED|MF_BYCOMMAND);
            pMenuPages->EnableMenuItem(IDM_PREVP,MF_ENABLED|MF_BYCOMMAND);
         }
      }
   }
   pMenuImagen = pSubMenu->GetSubMenu(1);
   switch(m_Tool)
   {
      case P_DRAG:
         pMenuImagen->CheckMenuItem(IDM_HAND,MF_CHECKED|MF_BYCOMMAND);
         break;
      case P_ZINC:
         pMenuImagen->CheckMenuItem(IDM_ZOOMINCR,MF_CHECKED|MF_BYCOMMAND);
         break;
      case P_ZDEC:
         pMenuImagen->CheckMenuItem(IDM_ZOOMDECR,MF_CHECKED|MF_BYCOMMAND);
         break;
      case P_ZRECT:
         pMenuImagen->CheckMenuItem(IDM_ZOOMRECT,MF_CHECKED|MF_BYCOMMAND);
         break;   
      case P_NSIZ:
         pMenuImagen->CheckMenuItem(IDM_NORMALSIZE,MF_CHECKED|MF_BYCOMMAND);
         break;
      case P_BFIT:
         pMenuImagen->CheckMenuItem(IDM_FITWINDOW,MF_CHECKED|MF_BYCOMMAND);
         break;
      case P_HFIT:
         pMenuImagen->CheckMenuItem(IDM_FITHORZ,MF_CHECKED|MF_BYCOMMAND);
         break;            

   }
   if (m_Endoso.IsEmpty())
      pSubMenu->EnableMenuItem(IDM_ENDOSO,MF_GRAYED|MF_BYCOMMAND);
   else
      pSubMenu->EnableMenuItem(IDM_ENDOSO,MF_ENABLED|MF_BYCOMMAND);

   pMenuModov = pMenuImagen->GetSubMenu(14);
   switch(m_DrawMode)
   {
      case PRESERVE_WHITE:
         pMenuModov->CheckMenuItem(IDM_WHITEDRAW,MF_CHECKED|MF_BYCOMMAND);
         break;
      case PRESERVE_BLACK:
         pMenuModov->CheckMenuItem(IDM_BLACKDRAW,MF_CHECKED|MF_BYCOMMAND);
         break;
      case SCALE_TO_GRAY:
         pMenuModov->CheckMenuItem(IDM_GRAYDRAW,MF_CHECKED|MF_BYCOMMAND);
         break;
   }
   m_CanEditAnn = pFrame->m_EnableEditAnn;
   if (m_FileNote.IsEmpty() || !m_CanEditAnn)
   {
      pMenuAnn = pSubMenu->GetSubMenu(3);
      pMenuAnn->EnableMenuItem(IDM_EDITANN, MF_GRAYED|MF_BYCOMMAND);
      pMenuAnn->EnableMenuItem(IDM_SHOWPALANN, MF_GRAYED|MF_BYCOMMAND);
      pMenuAnn->EnableMenuItem(IDM_SAVEANN, MF_GRAYED|MF_BYCOMMAND);
      pMenuAnn->EnableMenuItem(IDM_DELETEANN, MF_GRAYED|MF_BYCOMMAND);
   }
   else
   {
      pMenuAnn = pSubMenu->GetSubMenu(3);
      pMenuAnn->EnableMenuItem(IDM_EDITANN, MF_ENABLED|MF_BYCOMMAND);
      pMenuAnn->EnableMenuItem(IDM_SHOWPALANN, MF_ENABLED|MF_BYCOMMAND);
      pMenuAnn->EnableMenuItem(IDM_DELETEANN, MF_ENABLED|MF_BYCOMMAND);
      if (m_ChangeAnn)
         pMenuAnn->EnableMenuItem(IDM_SAVEANN, MF_ENABLED|MF_BYCOMMAND);
      else
         pMenuAnn->EnableMenuItem(IDM_SAVEANN, MF_GRAYED|MF_BYCOMMAND);
   }
   if (m_EditAnn)
   {
      pMenuAnn = pSubMenu->GetSubMenu(3);

      if (!IsSelectedAnns())
         pMenuAnn->EnableMenuItem(IDM_DELETEANN, MF_GRAYED|MF_BYCOMMAND);


      pMenuAnn->CheckMenuItem(IDM_EDITANN,MF_CHECKED|MF_BYCOMMAND);

      if (pFrame->m_wndAnnBar.IsWindowVisible())
      pMenuAnn->CheckMenuItem(IDM_SHOWPALANN,MF_CHECKED|MF_BYCOMMAND);

      pMenuTypeAnn = pMenuAnn->GetSubMenu(4);

      if (m_PalTool == NULL_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_SELECTOR, MF_CHECKED|MF_BYCOMMAND);
    
      if (m_PalTool == NOTE_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_NOTE, MF_CHECKED|MF_BYCOMMAND);

      if (m_PalTool == ARROW_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_ARROW, MF_CHECKED|MF_BYCOMMAND);
      
      if (m_PalTool == HIGHLINE_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_HIGHLINE, MF_CHECKED|MF_BYCOMMAND);
      
      if (m_PalTool == LINE_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_LINE, MF_CHECKED|MF_BYCOMMAND);
      
      if (m_PalTool == TEXT_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_TEXT, MF_CHECKED|MF_BYCOMMAND);
      
      if (m_PalTool == MARK_PALTOOL)
         pMenuTypeAnn->CheckMenuItem(ID_MARK, MF_CHECKED|MF_BYCOMMAND);
      
   }
   else
   {
      pMenuAnn = pSubMenu->GetSubMenu(3);
      
      pMenuAnn->EnableMenuItem(IDM_DELETEANN, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn = pMenuAnn->GetSubMenu(4);

      pMenuTypeAnn->EnableMenuItem(ID_SELECTOR, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_NOTE, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_ARROW, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_HIGHLINE, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_LINE, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_TEXT, MF_GRAYED|MF_BYCOMMAND);
      pMenuTypeAnn->EnableMenuItem(ID_MARK, MF_GRAYED|MF_BYCOMMAND);
   }

   if (pFrame->m_hMod != NULL && m_IsGray)
   {
      pMenuTrat = pSubMenu->GetSubMenu(4);
      if (m_Undo)
      {        
         pMenuTrat->EnableMenuItem(IDM_TINCRCLOCAL,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TINCRGC,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TRTD,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TTWIDTH,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPFD,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPMTC,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPMTO,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPTT,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TTF,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TREL,MF_GRAYED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TUNDO,MF_ENABLED|MF_BYCOMMAND);
      }
      else
      {       
         pMenuTrat->EnableMenuItem(IDM_TINCRCLOCAL,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TINCRGC,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TRTD,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TTWIDTH,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPFD,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPMTC,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPMTO,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TSUPTT,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TTF,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TREL,MF_ENABLED|MF_BYCOMMAND);
         pMenuTrat->EnableMenuItem(IDM_TUNDO,MF_GRAYED|MF_BYCOMMAND);
      }
   }
   if (bpp == 1)
      pSubMenu->EnableMenuItem(IDM_INVERT,MF_ENABLED|MF_BYCOMMAND);
   else
      pSubMenu->EnableMenuItem(IDM_INVERT,MF_GRAYED|MF_BYCOMMAND);



   pSubMenu->TrackPopupMenu(TPM_LEFTALIGN |TPM_RIGHTBUTTON, point.x,point.y,this);


}

void CVw::OnMProperty() 
{	
   POSITION pos;
   CPoint point = m_ptoContext;
   ScreenToClient(&point);

   pos = m_ListPages.FindIndex(m_OpenPag - 1);
   if (pos != NULL && m_EditAnn)
   {
      CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);      
      if (pNote->PropertyInfo(this,point,m_Zoom))
      {         
         m_ChangeAnn = TRUE;
         m_AnyChangeAnn = TRUE;
      }
   }

   m_ptoContext = CPoint(0,0);
	
}


void CVw::OnHand() 
{	
   if (m_Tool == P_DRAG)
      return;
      
   m_Tool = P_DRAG;
   SetStateToolBar();
   if (m_EditAnn)
      OnEditAnn();
}


void CVw::OnShowpal() 
{
	CMF* pFrame = (CMF*)GetParent();
   BOOL Visible = pFrame->m_wndToolBar.IsWindowVisible();

   pFrame->ShowControlBar(&(pFrame->m_wndToolBar),!Visible,FALSE);

   SetStateToolBar();

   CheckPagesTool();
	
}

void CVw::ShowPal(BOOL Show)
{
   CMF* pFrame = (CMF*)GetParent();
   BOOL Visible = pFrame->m_wndToolBar.IsWindowVisible();

    
   if (Show)
   {
      if (!Visible)
         pFrame->ShowControlBar(&(pFrame->m_wndToolBar),Show,FALSE);
   }
   else
   {
      if(Visible)
         pFrame->ShowControlBar(&(pFrame->m_wndToolBar),Show,FALSE);
   }

   
}



void CVw::OnFithorz() 
{
   CMF* pMF  = (CMF*)GetParent();

   m_Tool = P_NONE;
   SetStateToolBar();

   m_FitMode = FITWIDTH;
	Display();	
}

void CVw::OnFitvert() 
{   
	CMF* pMF  = (CMF*)GetParent();

   m_Tool = P_NONE;
   SetStateToolBar();

   m_FitMode = FITHEIGHT;
	Display();		
}

void CVw::OnFitwindow() 
{
	CMF* pMF  = (CMF*)GetParent();

   m_Tool = P_NONE;
   SetStateToolBar();

   m_FitMode = FITWINDOW;
	Display();		
}

void CVw::OnNormalsize() 
{ 
	CMF* pMF  = (CMF*)GetParent();

   m_Tool = P_NONE;
   
   m_ZoomImg = 1;
   m_FitMode = FITNONE;
	Display();	
}

void CVw::OnZoomdecr() 
{
	if (m_Tool == P_ZDEC)
      return;
      
   m_Tool = P_ZDEC;
   SetStateToolBar();
   if (m_EditAnn)
      OnEditAnn();
	
}

void CVw::OnZoomincr() 
{
	if (m_Tool == P_ZINC)
      return;
      
   m_Tool = P_ZINC;
   SetStateToolBar();
   if (m_EditAnn)
      OnEditAnn();
}

void CVw::OnZoomRect()
{
   if (m_Tool == P_ZRECT)
      return;

   m_Tool = P_ZRECT;
   SetStateToolBar();
   if (m_EditAnn)
      OnEditAnn();

}

void CVw::OnBlackdraw() 
{
   CMF* pMF  = (CMF*)GetParent();   

   m_DrawMode = PRESERVE_BLACK;   
	Display();
}

void CVw::OnGraydraw() 
{
   CMF* pMF  = (CMF*)GetParent();

   m_DrawMode = SCALE_TO_GRAY;
	Display();
}

void CVw::OnWhitedraw() 
{
   CMF* pMF  = (CMF*)GetParent();

   m_DrawMode = PRESERVE_WHITE;
   Display();
}

void CVw::OnFirstp() 
{
   CMF* pMF  = (CMF*)GetParent();
   pMF->m_Page = 1;
	Display();	
   CheckPagesTool();
}

void CVw::OnLastp() 
{
   CMF* pMF = (CMF*)GetParent();
   pMF->m_Page = m_NumPages;
	Display();
   CheckPagesTool();
	
}

void CVw::OnNextp() 
{   
   CMF* pMF = (CMF*)GetParent();
   if (pMF->m_Page <(LONG)m_NumPages)
   {
      pMF->m_Page = pMF->m_Page + 1;
	   Display();
      CheckPagesTool();
   }
	
}

void CVw::OnPrevp() 
{
   CMF* pMF = (CMF*)GetParent();
   if (pMF->m_Page > 1)
   {
      pMF->m_Page = pMF->m_Page - 1;
	   Display();
      CheckPagesTool();
   }
	
}

void CVw::OnGoto()
{
   if (m_pIrPag == NULL)
      m_pIrPag = new CPagirDlg(m_NumPages,m_OpenPag,this);
}

void CVw::SelPage(LONG page)
{
   CMF* pMF = (CMF*)GetParent();
   if (page != (LONG)m_OpenPag)
   {
      pMF->m_Page = page;
	   Display();
      CheckPagesTool();
   }
   
}

void CVw::OnRotd90() 
{
	if (!Rot(1))
   {
      Display();
      RotateIcons(1);      
   }
	
}

void CVw::OnRoti90() 
{
	if (!Rot(3))   
   {
      Display();
      RotateIcons(3);      
   }
	
}

void CVw::NormalizeRot(LONG Rot)
{
   switch (Rot)
   {
      case IG_ROTATE_0: //solo al abrir la imagen
      {         
         m_Rot = 0; 
         break; 
      }
      case IG_ROTATE_90:
      {
         switch (m_Rot)
         {
            case 0: m_Rot = 1; break;
            case 1: m_Rot = 2; break;
            case 2: m_Rot = 3; break;
            case 3: m_Rot = 0; break;
         }
         break;
      }
      case IG_ROTATE_180: //solo al abrir la imagen
      {
         m_Rot = 2; 
         break;
      }
      case IG_ROTATE_270:
      {
         switch(m_Rot)
         {
         case 0: m_Rot = 3; break;
         case 1: m_Rot = 0; break;
         case 2: m_Rot = 1; break;
         case 3: m_Rot = 2; break;
         }
         
         break; 
      }
   }
}

LONG CVw::SetRot(short Rotation)
{
  LONG Err = 0;
  UINT Angle = 0;

  switch(Rotation)
  {
     case 0:   { Angle = 0; break; }
     case 90:  { Angle = 1; break; }
     case 180: { Angle = 2; break; }
     case 270: { Angle = 3; break; }
  }

  Err = Rot(Angle,FALSE);
  RotateIcons(Angle);

  return(Err);
}

short CVw::GetRot()
{
   short      ret = 0;
//   AT_MODE    rot;
   LPFNValid  lpfn;
   //LPFNAngle  lpfnAngle;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) goto End;
//   lpfnAngle = (LPFNAngle)GetProcAddress(_Module.m_hModGear,"IG_display_angle_get");
   //if (lpfnAngle == NULL) goto End;

   if (lpfn(m_hIGear))
   {     
      //lpfnAngle(m_hIGear,&rot);
      switch(m_Rot)
      {
      case IG_ROTATE_0:
         ret = 0;         
         break;
      case IG_ROTATE_90:
         ret = 90;
         break;
      case IG_ROTATE_180:
         ret = 180;
         break;
      case IG_ROTATE_270:
         ret = 270;
         break;
      default:
         break;

      }

   }
  
  
   End:

   return(ret);
}

LONG CVw::Rot(UINT Angle,BOOL open)
{
   AT_ERRCOUNT Err = 0;
   AT_MODE     rot;
   CRect       rect;
   LPFNValid   lpfn;
   LPFNAngle   lpfnAngle;
   LPFNRotate  lpfnRotate;
   LPFNFit     lpfnFit;
   

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) goto End;
   lpfnAngle = (LPFNAngle)GetProcAddress(_Module.m_hModGear,"IG_display_angle_get");
   if (lpfnAngle == NULL) goto End;
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) goto End;
   lpfnFit = (LPFNFit)GetProcAddress(_Module.m_hModGear,"IG_display_fit_method");
   if (lpfnFit == NULL) goto End;
   

   GetClientRect(rect);

   if (lpfn(m_hIGear))
   {     
      lpfnAngle(m_hIGear,&rot);
      switch(Angle)
      {
      case 0:
         rot = IG_ROTATE_0;         
         break;
      case 1:
         rot = IG_ROTATE_90;
         break;
      case 2:
         rot = IG_ROTATE_180;
         break;
      case 3:
         rot = IG_ROTATE_270;
         break;
      default:
         break;

      }
      
      Err = lpfnRotate(m_hIGear,rot);      

      if (!Err && !open)
      {
         NormalizeRot(rot);         
      }
      

      Fit();
      
   }
  // SetScroll(5,0,TRUE);
  // SetScroll(5,0,FALSE);

   End:

   if (m_Tool == P_SEL)
   {
      DrawSelRect();
      m_SelRect.SetRectEmpty();
   }
 
   return((LONG)Err);
}


void CVw::SetStateToolBar()
{
   CMF* pFrame = (CMF*)GetParent();
   CToolBarCtrl& BarCtrl = pFrame->m_wndToolBar.GetToolBarCtrl();

   if (m_Tool != P_SEL)
   {
      DrawSelRect();
      m_SelRect.SetRectEmpty();
   }
   if (pFrame->m_EnablePrint)
      BarCtrl.SetState(IDM_PRINT,TBSTATE_ENABLED);
   else
      BarCtrl.SetState(IDM_PRINT,TBSTATE_INDETERMINATE);
   for (int i= P_ZINC; i <= P_SEL; i++)
   {
      switch(i)
      {
         case P_DRAG:
            if (m_Tool == P_DRAG)
               BarCtrl.SetState(IDM_HAND,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_HAND,TBSTATE_ENABLED);
            break;         
         case P_ZINC:
            if (m_Tool == P_ZINC)
               BarCtrl.SetState(IDM_ZOOMINCR,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_ZOOMINCR,TBSTATE_ENABLED);
            break;         
         case P_ZDEC:
            if (m_Tool == P_ZDEC)
               BarCtrl.SetState(IDM_ZOOMDECR,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_ZOOMDECR,TBSTATE_ENABLED);
            break;         
         case P_ZRECT:
            if (m_Tool == P_ZRECT)
               BarCtrl.SetState(IDM_ZOOMRECT,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_ZOOMRECT,TBSTATE_ENABLED);
            break;         
         case P_NSIZ:
            if (m_Tool == P_NSIZ)
               BarCtrl.SetState(IDM_NORMALSIZE,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_NORMALSIZE,TBSTATE_ENABLED);
            break;         
         case P_BFIT:
            if (m_Tool == P_BFIT)
               BarCtrl.SetState(IDM_FITWINDOW,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_FITWINDOW,TBSTATE_ENABLED);
            break;         
         case P_HFIT:
            if (m_Tool == P_HFIT)
               BarCtrl.SetState(IDM_FITHORZ,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_FITHORZ,TBSTATE_ENABLED);
            break;         
         case P_VFIT:
            if (m_Tool == P_VFIT)
               BarCtrl.SetState(IDM_FITVERT,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_FITVERT,TBSTATE_ENABLED);
            break; 
         case P_SEL:
            if (m_Tool == P_SEL)
               BarCtrl.SetState(IDM_SELECT,TBSTATE_CHECKED);
            else
               BarCtrl.SetState(IDM_SELECT,TBSTATE_ENABLED);
            break;                 
   
      }
   }
}

void CVw::SetScroll(UINT SBCode,UINT Pos, BOOL Horz)
{
   LPFNValid       lpfn;  
   LPFNScrollImage lpfnscroll; 
   LONG            Dir;

   if (Horz)
      Dir = SB_HORZ;
   else
      Dir = SB_VERT;

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfnscroll = (LPFNScrollImage)GetProcAddress(_Module.m_hModGear,"IG_display_scroll_image");
   if (lpfnscroll == NULL) return;
   
	if (lpfn(m_hIGear))
   {
      lpfnscroll(m_hIGear,this->m_hWnd,Dir,Pos,SBCode);      
   }	
}

void CVw::CheckPagesTool()
{
  
   CString  TextStatic;
   CMF*     pFrame = (CMF*)GetParent();
   CToolBarCtrl& BarCtrl = pFrame->m_wndToolBar.GetToolBarCtrl();
  
  
   if (m_NumPages == 1)
   {
      BarCtrl.SetState(IDM_PREVP,TBSTATE_INDETERMINATE);
      BarCtrl.SetState(IDM_NEXTP,TBSTATE_INDETERMINATE);
   }
   else
   {
      if (m_OpenPag == m_NumPages)
         BarCtrl.SetState(IDM_NEXTP,TBSTATE_INDETERMINATE);
      else
         BarCtrl.SetState(IDM_NEXTP,TBSTATE_ENABLED);

      if (m_OpenPag == 1)
         BarCtrl.SetState(IDM_PREVP,TBSTATE_INDETERMINATE);
      else
         BarCtrl.SetState(IDM_PREVP,TBSTATE_ENABLED);
   }

   TextStatic.Format("(%i de %i)", m_OpenPag,m_NumPages);
 //  if (pFrame->m_hMod == NULL)
      pFrame->m_wndToolBar.m_StaticBox.SetWindowText(TextStatic);

}

void CVw::OnConfig()
{
   if (m_pConfig == NULL)
      m_pConfig = new CConfgDlg(this);
}

///////////////////////Gestión de Impresión////////////////////////////


void CVw::PrintFile()
{
   LONG		 Err;		
   PRINTDLG	 pd;			    // print dommon dialog structure   
   BOOL	 	 fAbort;		    // Print abort flag					
   INT		 nError;			// return value from print funcs.  
   LPSTR		 lpszDocName;    // name of document to be printed       
   DOCINFO	 di;				// needed for StartDoc()			          
   HINSTANCE hInstance;		// instance of this task			     
   CRect 	 DCRect; 			
   BOOL		 bAborted;				
   CHAR	 	 szFile[_MAX_PATH];  
   HIGEAR    hIGear = 0;
   UINT      bpp;
   LPFNValid lpfn;
   LPFNDimensions lpfnDimensions;


   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) return;
   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) return;


   lstrcpy( (LPSTR)szFile, "Impresión"); 
    
   lpszDocName = (LPSTR)szFile;         
                  
   
   memset(&pd, 0, sizeof(PRINTDLG));
   
  
   pd.lStructSize = sizeof(PRINTDLG);
   pd.hwndOwner = GetSafeHwnd();
   pd.Flags = PD_RETURNDC|PD_NOSELECTION;
       
   pd.hInstance = AfxGetInstanceHandle();
   pd.lpPrintTemplateName = dialogo;     
   pd.nMinPage = 1;
   pd.nMaxPage = (short)m_NumPages;
   pd.nFromPage	= 1;
   pd.nToPage = (short)m_NumPages;
   
    if (::PrintDlg(&pd))  
   {  
      ::UpdateWindow(GetSafeHwnd());
      ::EnableWindow(GetSafeHwnd(), FALSE);

      
      fAbort = FALSE;      
    
     
      nError = SetAbortProc(pd.hDC, (ABORTPROC)AbortProc);
      if (nError <= 0)
         ::MessageBox(GetSafeHwnd(), "No se puede llamar a la función abortar.", NULL, MB_OK);

     
      memset(&di, 0, sizeof(DOCINFO));
      di.cbSize = sizeof(DOCINFO);
      di.lpszDocName =  lpszDocName;
      di.lpszOutput = NULL;
      
      nError = StartDoc(pd.hDC, &di);

      if (nError <= 0)
         ::MessageBox(GetSafeHwnd(), "No se puede iniciar el trabajo de impresión", NULL, MB_OK);
      else
      {
         SetCursor(LoadCursor(NULL,IDC_WAIT));
         hInstance = (HINSTANCE)GetWindowLong(GetSafeHwnd(), GWL_HINSTANCE);
         if (hInstance != NULL)
            hwndPrint = CreateDialogParam(hInstance, MAKEINTRESOURCE(IDD_PRINT),
									         GetSafeHwnd(),(DLGPROC)AbortDlgProc,
									         (LPARAM)(LPVOID)szFile);	         
        
	                           
         CDC dc;
         dc.Attach( pd.hDC);            
	                   
         for (int k=pd.nFromPage; k<=pd.nToPage; k++)
         {    
            if (hwndPrint != NULL)
               ::UpdateWindow(hwndPrint);
            
            TermImg(hIGear);
            if (k == (int)m_OpenPag)
               hIGear = m_hIGear;
            else
            {
               Err = InitPageImg(m_FileName,k,&hIGear);
               if (Err)
                  ::MessageBox(GetSafeHwnd(), "Error Inicializando página", NULL, MB_OK);
            }
            
            nError = DoStartPage(pd.hDC);	

            if (nError != ERR_PRN_NONE)
               ::MessageBox(GetSafeHwnd(), "Error en StartPage.", NULL, MB_OK);
            else
            {	               
               CRect FitRectDC;
               CSize ImgSize;
 
               if (lpfn(hIGear))
                  lpfnDimensions(hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);
               CRect ImgRect = CRect(CPoint(0,0),ImgSize);
               DCRect.left = 0;
               DCRect.top = 0;
               DCRect.right = GetDeviceCaps(pd.hDC, HORZRES);
               DCRect.bottom = GetDeviceCaps(pd.hDC, VERTRES);

               GetFitRect(ImgSize,DCRect,&FitRectDC);
              
               Err = PagePrint(hIGear,&dc,FitRectDC,bpp);
                          
               if (Err != 0)
               {
                  AbortDoc(pd.hDC);
                  bAborted = TRUE;
               }
               else
                  bAborted = FALSE;


            }
            nError = DoEndPage(pd.hDC);     			
            if (k == (int)m_OpenPag) hIGear = 0;
         }		  
         dc.Detach();     
      } 
      TermImg(hIGear);
      
      if (nError != ERR_PRN_NONE)
      {		
	        ::MessageBox(m_hWnd, "Error en EndPage.", NULL, MB_OK);
	        AbortDoc(pd.hDC);
      }
      else
      {			
	         // The document has ended. 
	        nError = EndDoc(pd.hDC);
      }

      if (nError <= 0) 
	        ::MessageBox(m_hWnd, "Error en EndDoc.", NULL, MB_OK);                           
	   
      SetCursor(LoadCursor(NULL,IDC_ARROW));
      // done with the DC, delete it 
      DeleteDC(pd.hDC);

      // free up these handles 
      if (pd.hDevMode)
      GlobalFree(pd.hDevMode);
      if (pd.hDevNames)
      GlobalFree(pd.hDevNames);

      ::EnableWindow(m_hWnd, TRUE);

       //destroy the abort dialog box 
      if (hwndPrint && IsWindow(hwndPrint))
         ::DestroyWindow(hwndPrint);
	}

  
}


void CVw::OnPrintImg() 
{
   if (!m_open)
      return;

   PrintFile();  	
}


/////////////////////////////TRATAMIENTOS//////////////////////////////////

LONG CVw::GetError()
{
	AT_ERRCOUNT  Errcount = 0;
	AT_ERRCODE   iCode    = 0;
	LONG         Err;
   LPFNGetError lpfnGetError;
   LPFNClearErr lpfnclear;

   lpfnGetError = (LPFNGetError)GetProcAddress(_Module.m_hModGear,"IG_error_get");
   if (lpfnGetError == NULL) {Err = -1; goto End;}
   lpfnclear = (LPFNClearErr)GetProcAddress(_Module.m_hModGear,"IG_error_clear");
   if (lpfnGetError == NULL) {Err = -1; goto End;}

	
	lpfnGetError(0, NULL, 0, NULL, (LPAT_ERRCODE)&iCode, NULL, NULL);
	lpfnclear();

   Err = (LONG)iCode;
		
End:

   return(Err);
}


LONG CVw::GetDIB(LPBITMAPINFOHEADER FAR* pDIB)
{
   LONG          Err = 0;
   AT_ERRCOUNT   Errcount = IGE_SUCCESS;  
   LPFNExportDIB lpfnEDib;

   lpfnEDib = (LPFNExportDIB)GetProcAddress(_Module.m_hModGear,"IG_image_export_DIB");
   if (lpfnEDib == NULL) {Err = -1; goto End;}


   Errcount = lpfnEDib(m_hIGear,(LPAT_DIB FAR*)pDIB);
   if (Errcount)
   {
      Err = GetError();
      goto End;
   }   

   End:

   return(Err);
   
}

LONG CVw::SetDIB(LPBITMAPINFOHEADER pDIB)
{
   LONG          Err = 0;
   AT_ERRCOUNT   Errcount = IGE_SUCCESS; 
   LPFNCreateDIB lpfnCDib;
   LPFNCenter    lpfncenter;
   LPFNDesktop   lpfndesktop;
   AT_RGB        Back;

   lpfnCDib = (LPFNCreateDIB)GetProcAddress(_Module.m_hModGear,"IG_image_create_DIB");
   if (lpfnCDib == NULL) {Err = -1; goto End;}
   lpfncenter = (LPFNCenter)GetProcAddress(_Module.m_hModGear,"IG_display_centered_set");
   if (lpfncenter == NULL) {Err = -1; goto End;}
   lpfndesktop = (LPFNDesktop)GetProcAddress(_Module.m_hModGear,"IG_display_desktop_pattern_set");
   if (lpfndesktop == NULL) {Err = -1; goto End;}

   Back.b = 255; Back.g = 255; Back.r = 255;
   Errcount = lpfnCDib(0,0,0,(LPAT_DIB)pDIB,&m_hIGear);
   if (Errcount)
   {
      Err = GetError();
      goto End;
   }   
   lpfncenter(m_hIGear,this->m_hWnd,FALSE);
   lpfndesktop(m_hIGear,NULL,NULL,&Back,TRUE);

   End:

   return(Err); 
}




LONG CVw::SetTMT()
{
   LPBITMAPINFOHEADER pdib = NULL;
   HGLOBAL            hGlobal = NULL;
   LONG               Err = 0;   
   CMF*               pFrame = (CMF*)GetParent();
   CPoint             Pos;
   LPCRECT            pSelRect = NULL;
   LPFNDelete         lpfndelete;
   CMF*               pMF = (CMF*)GetParent();

   lpfndelete = (LPFNDelete)GetProcAddress(_Module.m_hModGear,"IG_image_delete");
   if (lpfndelete == NULL) {Err = -1; goto End;};


   SetCursor(LoadCursor(NULL,IDC_WAIT));

   GetZoomImg();

   if(pFrame->m_hMod != NULL)
   {       
      ULONG rc;   
      m_paint = FALSE;

      if (!m_SelRect.IsRectNull())
         pSelRect = &m_SelRect;
      
      Pos.x = (long)GetScrollPos(SB_HORZ);
      Pos.y = (long)GetScrollPos(SB_VERT);
      
      Err = GetDIB(&pdib);
      if (Err) goto End;
      
      switch(m_TMT)
      {
         case TMT_ACL:
         {
            rc = CTratsIG::TratACL((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_AGC:
         {
            rc = CTratsIG::TratAGC((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_ATD:
         {
            rc = CTratsIG::TratATD((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_Bold:
         {
            rc = CTratsIG::TratBold((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_EFD:
         {
            rc = CTratsIG::TratEFD((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_EMA:
         {
            rc = CTratsIG::TratEMA((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_EMT:
         {
            rc = CTratsIG::TratEMT((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_ETT:
         {
            rc = CTratsIG::TratETT((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_Light:
         {
            rc = CTratsIG::TratLight((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         case TMT_Shadow:
         {
            rc = CTratsIG::TratShadow((BITMAPINFOHEADER*)pdib,pSelRect);
            break;
         }
         default:
         {            
            break;
         }
      }      
      
      if (rc) 
      {
         Err = rc;
    
         lpfndelete(m_hIGear);
         m_FileName.Empty();
         Display();
    
         SetScroll(5,Pos.x,TRUE);
         SetScroll(5,Pos.y,FALSE);

         m_paint = TRUE;
         Invalidate(FALSE);
         UpdateWindow();
         goto End;
      }

      Err = SetDIB(pdib);
      if (Err) goto End;
   /*   if (m_FitMode != FITNONE)
         m_FitMode = FITNONE;
      else
      {
         pMF->m_ZoomImg = m_ZoomImg;
         m_ZoomImg = 1;
      }

      if (m_DrawMode != PRESERVE_WHITE)
         m_DrawMode = PRESERVE_WHITE;*/

      Display();
      SetScroll(5,Pos.x,TRUE);
      SetScroll(5,Pos.y,FALSE);

      m_paint = TRUE;
      Invalidate(FALSE);
      UpdateWindow();
      
   }

   End:

   m_SelRect.SetRectEmpty();
   DrawSelRect();
   

   if (pdib != NULL)
   {  
		hGlobal = GlobalHandle(pdib);

		GlobalUnlock(hGlobal);
		GlobalFree(hGlobal);
   }
   SetCursor(LoadCursor(NULL,IDC_ARROW));

   return (Err);

}

void CVw::OnIncrCLocal()
{
   LONG Err = 0;

   m_TMT = TMT_ACL;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;

}

void CVw::OnIncrGC()
{
   LONG Err = 0;

   m_TMT = TMT_AGC;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnRTD()
{
   LONG Err = 0;

   m_TMT = TMT_ATD;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnTWidth()
{
   LONG Err = 0;

   m_TMT = TMT_Bold;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnSupFD()
{
   LONG Err = 0;

   m_TMT = TMT_EFD;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnSupMTC()
{
   LONG Err = 0;

   m_TMT = TMT_EMA;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnSupMTO()
{
  LONG Err = 0;

   m_TMT = TMT_EMT;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnSupTT()
{
   LONG Err = 0;

   m_TMT = TMT_ETT;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnTF()
{
   LONG Err = 0;

   m_TMT = TMT_Light;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnRel()
{
   LONG Err = 0;

   m_TMT = TMT_Shadow;
   Err = SetTMT();
   if (!Err)
      m_Undo =TRUE;
}

void CVw::OnUndo()
{
   double Zoom = m_Zoom;
   CPoint Pos;
   LPFNDelete lpfndelete;

   lpfndelete = (LPFNDelete)GetProcAddress(_Module.m_hModGear,"IG_image_delete");
   if (lpfndelete == NULL) { goto End;};

   Pos.x = (long)GetScrollPos(SB_HORZ);
   Pos.y = (long)GetScrollPos(SB_VERT);

   GetZoomImg();

   m_paint = FALSE;

   lpfndelete(m_hIGear);
   //m_FileName.Empty(); no funciona con url
   m_FileName = ""; 
   m_DoUndo = TRUE;
   Display();
        
   SetScroll(5,Pos.x,TRUE);
   SetScroll(5,Pos.y,FALSE);

   m_paint = TRUE;

   m_SelRect.SetRectEmpty();
   DrawSelRect();

   Invalidate();
   UpdateWindow();
   m_DoUndo = FALSE;
   m_Undo = FALSE;

End:
   ;
}

void CVw::OnSelect() 
{	
   Select();      	
}

void CVw::Select()
{
   if (m_Tool == P_SEL)
      return;
      
   m_Tool = P_SEL;
   SetStateToolBar();
   if (m_EditAnn)
      OnEditAnn();
}
//////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////

HIGEAR CVw::GetImage()
{
   return(m_hIGear);
}



void CVw::DrawSelRect()
{
   m_DCTrkRect.SetRect(m_SelRect);
   m_DCTrkRect.Update(UPDATE_THIS,this,m_Zoom);
}

BOOL CVw::GetSelRect(CPoint point, CRect& SelRect)
{
  
   BOOL  Ret = FALSE;
   CRect AuxRect;
   CSize ImgSize;   
   UINT         bpp;   
   LPFNDimensions lpfnDimensions;

   lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Ret = FALSE; goto End;}


   lpfnDimensions(m_hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);  
   m_DCTrkRect.m_pWnd = this;
   DrawSelRect();
  
   AuxRect = CRect(CPoint(0,0),ImgSize);

   if ( (AuxRect).PtInRect(point) )
   {
      
      GetZoomImg(); 
      m_DCTrkRect.SetExtRect(AuxRect);
      
      m_DCTrkRect.TrackRubberBand(point,0,this,m_ZoomImg);

      

      m_DCTrkRect.GetRect(AuxRect);
      if(AuxRect.Width() < 5 && AuxRect.Height() < 5)
      {
         SelRect = CRect(0,0,0,0);
         Ret = FALSE;
         goto End;
      }
      
      m_DCTrkRect.OnDraw(GetDC());
      SelRect = AuxRect;

   }

End:
   
   return(Ret);

}




LONG CVw::PagePrint(HIGEAR hIGear,CDC* pDC,CRect FitRectDC,UINT bpp)
{
   LONG              Err = 0;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;
   LPFNValid         lpfn;
   LPFNDeviceRectSet lpfnDevRS;
   LPFNrunToDIB      lpfnRToDIB;
   LPFNPrint         lpfnPrint;

   ASSERT( FitRectDC != NULL );

   lpfn = (LPFNValid)GetProcAddress(_Module.m_hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}     
   lpfnDevRS = (LPFNDeviceRectSet)GetProcAddress(_Module.m_hModGear,"IG_device_rect_set");
   if (lpfnDevRS == NULL) {Err = -1;goto End;}
   lpfnRToDIB = (LPFNrunToDIB)GetProcAddress(_Module.m_hModGear,"IG_IP_convert_runs_to_DIB");
   if (lpfnRToDIB == NULL) {Err = -1;goto End;}
   lpfnPrint = (LPFNPrint)GetProcAddress(_Module.m_hModGear,"IG_print_image");
   if (lpfnPrint == NULL) {Err = -1;goto End;}

	if (!FitRectDC.IsRectEmpty())
	{   
      Errcount = lpfnDevRS(hIGear,(LPAT_RECT)&FitRectDC);
      if (Errcount)
      {
         Err = GetError();
         goto End;
      }
      
      if (bpp == 1)      
      {
         lpfnRToDIB(hIGear);
      } 
												 
      Errcount = lpfnPrint(hIGear,pDC->GetSafeHdc(),TRUE);
      if (Errcount) Err = GetError();
	   
   }
		
End:

	return (Err);

}



/////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////

BOOL CVw::OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message) 
{
	BOOL Halt = FALSE;

   if (!m_open)
      goto End;

   switch(m_Tool)
   {
      case P_ZINC:
      case P_ZDEC:
      {
         if ( (nHitTest != HTHSCROLL) && (nHitTest != HTVSCROLL) )
         {
            HCURSOR ZoomCursor = GetZoomCursor();
            if (ZoomCursor != NULL)
            {
               ::SetCursor(ZoomCursor);
               Halt = TRUE;
            }

         }
         break;
      }
      case P_ZRECT:
      case P_SEL:
      {
         if ( (nHitTest != HTHSCROLL) && (nHitTest != HTVSCROLL) )
         {
            HCURSOR ZoomRCursor = GetZoomRectCursor();
            if (ZoomRCursor != NULL)
            {
               ::SetCursor(ZoomRCursor);
               Halt = TRUE;
            }

         }
         break;
      }
      case P_DRAG:
      {
         if ( (nHitTest != HTHSCROLL) && (nHitTest != HTVSCROLL) )
         {
            HCURSOR DragCursor = GetDragCursor();
            if (DragCursor != NULL)
            {
               ::SetCursor(DragCursor);
               Halt = TRUE;
            }

         }
         break;
      }

   }

End:   
	if (!Halt)
	   Halt = CView::OnSetCursor(pWnd, nHitTest, message);

   return(Halt);

}

HCURSOR CVw::GetZoomCursor()
{
   return(AfxGetApp()->LoadCursor(IDC_ZOOM));
}

HCURSOR CVw::GetZoomRectCursor()
{
   return(AfxGetApp()->LoadCursor(IDC_SEL));
}

HCURSOR CVw::GetDragCursor()
{
   return(AfxGetApp()->LoadCursor(IDC_HHAND));
}



void CVw::PaletteChanged(CWnd* pFocusWnd)
{
   LPFNHandlePalette lpfnHP;

   if (pFocusWnd == this)
      return;

   lpfnHP = (LPFNHandlePalette)GetProcAddress(_Module.m_hModGear,"IG_display_handle_palette");
   if (lpfnHP == NULL) {return;}     

  lpfnHP(m_hIGear,this->m_hWnd,IG_PALETTE_PRIORITY_LOW,NULL);
  
   
}

BOOL CVw::QueryNewPalette()
{
   LPFNHandlePalette lpfnHP;
   BOOL              realize = FALSE;  	

   lpfnHP = (LPFNHandlePalette)GetProcAddress(_Module.m_hModGear,"IG_display_handle_palette");
   if (lpfnHP == NULL) {return(realize);}     

   lpfnHP(m_hIGear,this->m_hWnd,IG_PALETTE_PRIORITY_HIGH,&realize);
   	 
   return(realize);
   
}


void CVw::SetEnablePrint(BOOL Enable)
{
   m_Print = Enable;
}

BOOL CVw::IsEnablePrint()
{
   return(m_Print);
}

void CVw::SetEnableSaveAs(BOOL Enable)
{
   m_SaveAs = Enable;
}

BOOL CVw::IsEnableSaveAs()
{
   return(m_SaveAs);
}

void CVw::SetEnableEditAnn(BOOL Enable)
{
   m_CanEditAnn = Enable;
}

BOOL CVw::IsEnableEditAnn()
{
   return(m_CanEditAnn);
}

void CVw::SetFitMode(short FitMode)
{
   m_FitMode = (enumFitMode)FitMode;
}

short CVw::GetFitMode()
{
   return(m_FitMode);
}

void CVw::SetDrawMode(short DrawMode)
{
   m_DrawMode = (enumDrawMode)DrawMode;
}

short CVw::GetDrawMode()
{
   return(m_DrawMode);
}

void CVw::SetZoomImg(double Zoom)
{
   m_ZoomImg = Zoom;
   m_FitMode = FITNONE;
}

double CVw::GetZoom()
{
   GetZoomImg();
   return(m_ZoomImg);
}

void CVw::OnSaveAs()
{
   CString Filter = "All Files (*.*)|*.*|\0";
   
   CFileDialog SaveFileDlg(FALSE,"tif",NULL,OFN_HIDEREADONLY|OFN_OVERWRITEPROMPT,
                             Filter,this);
   
   if (SaveFileDlg.DoModal() == IDOK)
   {  
      CString File = SaveFileDlg.GetPathName();      	
      //CopyFile(m_FileName, File,FALSE);
      if (!File.IsEmpty())
      {
         SaveFileAs(File);
      }
	} 
}

BOOL CVw::ExistsSelection()
{
   BOOL Exists = FALSE;

   if (!m_SelRect.IsRectNull())
      Exists = TRUE;

   return (Exists);
}

LONG CVw::SaveSelectionToFile(CString FileName)
{

   LONG              Err = 0;
   ULONG             TypeToSave;
   LPFNSave          lpfnSave;   
   LPFNLoadFile      lpfnLoadFile;
   LPFNDeviceRectGet lpfnDeviceRectGet;
   LPFNDeviceRectSet lpfnDeviceRectSet;  
   LPFNDeviceToImage lpfnDeviceToImg;
   LPFNRotate        lpfnRotate; 
   HANDLE            fd  = 0;    
   CRect             ImgRect;   
   HIGEAR            hIGear;
   AT_RECT           SaveRect;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;   
   AT_ERRCOUNT       Errsave = IGE_SUCCESS;
  

   if (!m_SelRect.IsRectNull())
   {

      lpfnLoadFile = (LPFNLoadFile)GetProcAddress(_Module.m_hModGear,"IG_load_file");
      if (lpfnLoadFile == NULL) {Err = -1; goto End;}
      lpfnSave = (LPFNSave)GetProcAddress(_Module.m_hModGear,"IG_save_FD");
      if (lpfnSave == NULL) {Err = -1; goto End;}  
      //se llama a IG_image_rect_get
      lpfnDeviceRectGet = (LPFNDeviceRectGet)GetProcAddress(_Module.m_hModGear,"IG_image_rect_get");
      if (lpfnDeviceRectGet == NULL) {Err = -1; goto End;}
      //se llama a IG_image_rect_set
      lpfnDeviceRectSet = (LPFNDeviceRectSet)GetProcAddress(_Module.m_hModGear,"IG_image_rect_set");
      if (lpfnDeviceRectSet == NULL) {Err = -1; goto End;}
      lpfnDeviceToImg = (LPFNDeviceToImage)GetProcAddress(_Module.m_hModGear,"IG_display_device_to_image");
      if (lpfnDeviceRectGet == NULL) {Err = -1; goto End;}
      lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
      if (lpfnRotate == NULL) goto End;

      Err = InitPageImg(m_FileName,m_OpenPag,&hIGear);
      if (Err) goto End;
      switch(m_Rot)
      {
         case 1:
            lpfnRotate(hIGear,IG_ROTATE_90);
            break;
         case 2:
            lpfnRotate(hIGear,IG_ROTATE_180);
            break;
         case 3:
            lpfnRotate(hIGear,IG_ROTATE_270);
            break;
      }
      
      
      fd = CreateFile(FileName,GENERIC_READ|GENERIC_WRITE,FILE_SHARE_READ,NULL,OPEN_ALWAYS,
                  FILE_ATTRIBUTE_NORMAL,NULL);
      if (fd == INVALID_HANDLE_VALUE)
      {
         Err = IDOCIMGX_ERR_FILE;
         goto End;
      }
   
      GetTypeSave(m_FileType,m_FileCompress,&TypeToSave);
      

      Errcount = lpfnDeviceRectGet(hIGear,(LPAT_RECT)&ImgRect);
      if (Errcount)
      { Err = GetError(); goto End; }

      SaveRect.left = m_SelRect.left;
      SaveRect.top = m_SelRect.top;
      SaveRect.right = m_SelRect.right;
      SaveRect.bottom = m_SelRect.bottom;


      Errcount = lpfnDeviceRectSet(hIGear, &SaveRect);
      if (Errcount)
      { Err = GetError(); goto End; }
      
      Errsave = lpfnSave(hIGear,(int)fd,1,0,TypeToSave);
      if (Errsave)
        Err = GetError();         
      

      Errcount = lpfnDeviceRectGet(hIGear,(LPAT_RECT)&ImgRect);
      if (Errcount && !Errsave)
         Err = GetError(); 

   }
   else
      Err = IDOCIMGX_ERR_NO_SELECTION;


   End: 

   if (fd != INVALID_HANDLE_VALUE)
   {
      if (!CloseHandle(fd))
      {
         if (!Err) Err = IDOCIMGX_ERR_FILE;
      }
   }

   TermImg(hIGear);

   return (Err);

}

LONG CVw::SaveFileWithRotation(CString FileName)
{
   LONG              Err = 0;
   char              name[256];
   ULONG             TypeToSave;
   HANDLE            fd  = 0;    
   CRect             ImgRect;   
   HIGEAR            hIGear;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;   
   AT_ERRCOUNT       Errsave = IGE_SUCCESS;
   LPFNPage          lpfnpage;
   LPFNSaveFile      lpfnSaveFile;
   LPFNRotate        lpfnRotate;
   UINT              NumPages,i;


   lpfnSaveFile = (LPFNSaveFile)GetProcAddress(_Module.m_hModGear,"IG_save_file");
   if (lpfnSaveFile == NULL) {Err = -1; goto End;}     
   lpfnpage = (LPFNPage)GetProcAddress(_Module.m_hModGear,"IG_page_count_get");
   if (lpfnpage == NULL) {Err = -1; goto End;}
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) goto End;



   lstrcpy(name,m_FileName);

   Errcount = lpfnpage(name,&NumPages);

   GetTypeSave(m_FileType,m_FileCompress,&TypeToSave);

   fd = CreateFile(FileName,GENERIC_READ|GENERIC_WRITE,FILE_SHARE_READ,NULL,OPEN_ALWAYS,
                  FILE_ATTRIBUTE_NORMAL,NULL);
   if (fd == INVALID_HANDLE_VALUE)
   {
      Err = IDOCIMGX_ERR_FILE;
      goto End;
   }
   else
      CloseHandle(fd);

   for (i=1; i <= NumPages; i++)
   {
      Err = InitPageImg(m_FileName,i,&hIGear);
      if (Err) goto End;
      switch(m_Rot)
      {
         case 1:
            lpfnRotate(hIGear,IG_ROTATE_90);
            break;
         case 2:
            lpfnRotate(hIGear,IG_ROTATE_180);
            break;
         case 3:
            lpfnRotate(hIGear,IG_ROTATE_270);
            break;
      }

      char*  file = FileName.GetBuffer(FileName.GetLength());
      Errcount = lpfnSaveFile(hIGear,file,TypeToSave);
      FileName.ReleaseBuffer();      
      if (Errcount)
      {
         Err = GetError();
         goto End;
      } 
      
      TermImg(hIGear);
      
   }

   End:
   
   TermImg(hIGear);

   return (Err);   
         
}


LONG CVw::SaveFile(CString& FileName)
{

   LONG              Err = 0;
   ULONG             TypeToSave;
   HANDLE            fd  = 0;    
   CRect             ImgRect;   
   HIGEAR            hIGear;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;   
   AT_ERRCOUNT       Errsave = IGE_SUCCESS;
   LPFNSaveFile      lpfnSaveFile;  
   LPFNRotate        lpfnRotate;
   UINT              i;

   CMF* pFrame = (CMF*)GetParent();

   char TmpDir[MAX_PATH];
   char szFile[MAX_PATH];
   int  Ret;

   Ret = GetTempPath(MAX_PATH, TmpDir);
   if(Ret==0) { Err = IDOCIMGX_ERR_CREATE_TEMP_DIR; goto End; }

   Ret = GetTempFileName(TmpDir, "IMG", 0, szFile);
   if (Ret==0)
   {
      Err = GetLastError();
      goto End;
   }


   lpfnSaveFile = (LPFNSaveFile)GetProcAddress(_Module.m_hModGear,"IG_save_file");
   if (lpfnSaveFile == NULL) {Err = -1; goto End;}     
   lpfnRotate = (LPFNRotate)GetProcAddress(_Module.m_hModGear,"IG_IP_rotate_multiple_90");
   if (lpfnRotate == NULL) goto End;

      
   GetTypeSave(m_FileType,m_FileCompress,&TypeToSave);

   fd = CreateFile(szFile,GENERIC_READ|GENERIC_WRITE,FILE_SHARE_READ,NULL,OPEN_ALWAYS,
                  FILE_ATTRIBUTE_NORMAL,NULL);
   if (fd == INVALID_HANDLE_VALUE)
   {
      Err = IDOCIMGX_ERR_FILE;
      goto End;
   }
   else
      CloseHandle(fd);


   for (i=1; i <= m_NumPages; i++)
   {

      if (i != m_OpenPag)
      {
         Err = InitPageImg(m_FileName,i,&hIGear);
         if (Err) goto End;    
         switch(m_Rot)
         {
            case 1:
               lpfnRotate(hIGear,IG_ROTATE_90);
               break;
            case 2:
               lpfnRotate(hIGear,IG_ROTATE_180);
               break;
            case 3:
               lpfnRotate(hIGear,IG_ROTATE_270);
               break;
         }
    
         Errcount = lpfnSaveFile(hIGear,szFile,TypeToSave);
         TermImg(hIGear);
      }
      else
      {
        
         ReturnSizeTiffG3Fax(m_OpenPag);
         Errcount = lpfnSaveFile(m_hIGear, szFile, TypeToSave);         
         ChangeSizeTiffG3Fax();
      }

      if (Errcount)
      {
         Err = GetError();
         goto End;
      } 
      
   }

   FileName = szFile;
   CopyFile(szFile, m_FileName,FALSE);
   pFrame->m_TempFiles.Add(FileName);
  

   End:
   


   return (Err);   
         
}

LONG CVw::SaveFileAs(CString FileName)
{

   LONG              Err = 0;
   ULONG             TypeToSave;
   HANDLE            fd  = 0;    
   CRect             ImgRect;   
   HIGEAR            hIGear;
   AT_ERRCOUNT       Errcount = IGE_SUCCESS;   
   AT_ERRCOUNT       Errsave = IGE_SUCCESS;
   LPFNSaveFile      lpfnSaveFile;   
   UINT              i;
   char*             szFile;

   CMF* pFrame = (CMF*)GetParent();
/*
   char TmpDir[MAX_PATH];
   char szFile[MAX_PATH];
   int  Ret;

   Ret = GetTempPath(MAX_PATH, TmpDir);
   if(Ret==0) { Err = IDOCIMGX_ERR_CREATE_TEMP_DIR; goto End; }

   Ret = GetTempFileName(TmpDir, "IMG", 0, szFile);
   if (Ret==0)
   {
      Err = GetLastError();
      goto End;
   }
*/
   DeleteFile(FileName);

   lpfnSaveFile = (LPFNSaveFile)GetProcAddress(_Module.m_hModGear,"IG_save_file");
   if (lpfnSaveFile == NULL) {Err = -1; goto End;}        
      
   GetTypeSave(m_FileType,m_FileCompress,&TypeToSave);

   fd = CreateFile(FileName,GENERIC_READ|GENERIC_WRITE,FILE_SHARE_READ,NULL,OPEN_ALWAYS,
                  FILE_ATTRIBUTE_NORMAL,NULL);
   if (fd == INVALID_HANDLE_VALUE)
   {
      Err = IDOCIMGX_ERR_FILE;
      goto End;
   }
   else
      CloseHandle(fd);


   for (i=1; i <= m_NumPages; i++)
   {

      if (i != m_OpenPag)
      {
         Err = InitPageImg(m_FileName,i,&hIGear);
         if (Err) goto End;    
    
         szFile = FileName.GetBuffer(FileName.GetLength());
         Errcount = lpfnSaveFile(hIGear,szFile,TypeToSave);
         FileName.ReleaseBuffer();
         TermImg(hIGear);
      }
      else
      {
         ReturnSizeTiffG3Fax(m_OpenPag);

         szFile = FileName.GetBuffer(FileName.GetLength());
         Errcount = lpfnSaveFile(m_hIGear, szFile, TypeToSave);         
         FileName.ReleaseBuffer();

         ChangeSizeTiffG3Fax();
      }

      if (Errcount)
      {
         Err = GetError();
         goto End;
      } 
      
   }

   //FileName = szFile;
   //CopyFile(szFile, m_FileName,FALSE);
   //pFrame->m_TempFiles.Add(FileName);
  

   End:
   


   return (Err);   
         
}


///////////////// ANOTACIONES//////////////////////////
///////////////////////////////////////////////////////

void CVw::DrawIcons(CDC* pDC,HIGEAR& hIGear,LONG Page)
{
   double Zoom = 1;   
  //////////Pintar Iconos//////

   if (m_RotIcon && Page == (LONG)m_OpenPag)
   {
      RotateIcons((short)m_Rot);
      m_RotIcon = FALSE;
   }

   POSITION pos;
   pos = m_ListPages.FindIndex(Page -1);
   if (pos != NULL)
   {
      CSize          ImgSize;
      UINT           bpp;   
      LPFNDimensions lpfnDimensions;
      CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);
            
      lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
      if (lpfnDimensions == NULL) {goto End;}

      lpfnDimensions(m_hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);        
      
      pNote->SetDefExtRect(CRect(CPoint(0,0),ImgSize));
      
      pNote->OnDraw(pDC,Zoom); //El zoom no hace falta
   } 

   End:

   return;

}   

void CVw::DeleteArrAnnot()
{
   POSITION pos;
 
   pos = m_ListPages.GetHeadPosition();
   while(pos != NULL)
   {
      CListNote* pObj = (CListNote*)m_ListPages.GetNext(pos);
      pObj->RemoveAll();
      delete pObj;
   }
   m_ListPages.RemoveAll();
   
   if (m_pAnnDoc != NULL)
   {
      delete m_pAnnDoc;
      m_pAnnDoc = NULL;
   }
}

void CVw::OnEditAnn()
{

   POSITION pos;
   CListNote* pNote;
   pos = m_ListPages.FindIndex(m_OpenPag - 1);
   if (pos != NULL)
   {     
      pNote = (CListNote*)m_ListPages.GetAt(pos);      
   }   
   else
      return;

   m_EditAnn = !m_EditAnn;

   if (!m_EditAnn)
   {  
      pNote->DelSelectAll(this,m_Zoom);
      pNote->SetExeModeAllObjList(CTrkObj::XM_Run | CTrkObj::XM_InUse);		          
   }  
   else
   {
      CMF* pFrame = (CMF*)GetParent();
      m_Tool = P_NONE;
      pFrame->ShowControlBar(&(pFrame->m_wndAnnBar),TRUE,FALSE);
      SetStateToolBar();
      pNote->SetExeModeAllObjList(CTrkObj::XM_Edit | CTrkObj::XM_InUse);  
      
   }
   
   SetStateAnnBar();
   m_PalTool = NULL_PALTOOL;

}

void CVw::OnShowPalAnn()
{
   CMF* pFrame = (CMF*)GetParent();
   BOOL Visible = pFrame->m_wndAnnBar.IsWindowVisible();

   pFrame->ShowControlBar(&(pFrame->m_wndAnnBar),!Visible,FALSE);

   SetStateAnnBar();

}

void CVw::SetStateAnnBar()
{
   CMF* pFrame = (CMF*)GetParent();
   CToolBarCtrl& BarCtrl = pFrame->m_wndAnnBar.GetToolBarCtrl();
   if (m_EditAnn)
   {
      for (int i= NULL_PALTOOL; i <= MARK_PALTOOL; i++)
      {
         switch(i)
         {
            case NULL_PALTOOL:
               if (m_PalTool == NULL_PALTOOL)
                  BarCtrl.SetState(ID_SELECTOR,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_SELECTOR,TBSTATE_ENABLED);
               break;         
            case TEXT_PALTOOL:
               if (m_PalTool == TEXT_PALTOOL)
                  BarCtrl.SetState(ID_TEXT,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_TEXT,TBSTATE_ENABLED);
               break;         
            case NOTE_PALTOOL:
               if (m_PalTool == NOTE_PALTOOL)
                  BarCtrl.SetState(ID_NOTE,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_NOTE,TBSTATE_ENABLED);
               break;         
            case HIGHLINE_PALTOOL:
               if (m_PalTool == HIGHLINE_PALTOOL)
                  BarCtrl.SetState(ID_HIGHLINE,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_HIGHLINE,TBSTATE_ENABLED);
               break;         
            case LINE_PALTOOL:
               if (m_PalTool == LINE_PALTOOL)
                  BarCtrl.SetState(ID_LINE,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_LINE,TBSTATE_ENABLED);
               break;         
            case ARROW_PALTOOL:
               if (m_PalTool == ARROW_PALTOOL)
                  BarCtrl.SetState(ID_ARROW,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_ARROW,TBSTATE_ENABLED);
               break;         
            case MARK_PALTOOL:
               if (m_PalTool == MARK_PALTOOL)
                  BarCtrl.SetState(ID_MARK,TBSTATE_CHECKED);
               else
                  BarCtrl.SetState(ID_MARK,TBSTATE_ENABLED);
               break;                  
         }
      }
   }
   else
   {
      BarCtrl.SetState(ID_SELECTOR, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_TEXT, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_NOTE, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_HIGHLINE, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_LINE, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_ARROW, TBSTATE_INDETERMINATE);
      BarCtrl.SetState(ID_MARK, TBSTATE_INDETERMINATE);
   }
}

void CVw::OnAnnSelector()
{
   m_PalTool = NULL_PALTOOL;
   SetStateAnnBar();
}
 
void CVw::OnAnnText()
{
   m_PalTool = TEXT_PALTOOL;
   SetStateAnnBar();
}

void CVw::OnAnnNote()
{
   m_PalTool = NOTE_PALTOOL;
   SetStateAnnBar();
}

void CVw::OnAnnHighline()
{
   m_PalTool = HIGHLINE_PALTOOL;
   SetStateAnnBar();
}

void CVw::OnAnnLine()
{
   m_PalTool = LINE_PALTOOL;
   SetStateAnnBar();
}

void CVw::OnAnnArrow()
{
   m_PalTool = ARROW_PALTOOL;
   SetStateAnnBar();
}

void CVw::OnAnnMark()
{
   m_PalTool = MARK_PALTOOL;
   SetStateAnnBar();
}

LONG CVw::GetId()
{   
   m_IdAnn = m_IdAnn + 1;

   return (m_IdAnn);
}


void CVw::SetId(LONG Id)
{
   if (m_IdAnn < Id)
      m_IdAnn = Id;
}

BOOL CVw::ReadFileAnn()
{
   BOOL Ret = TRUE;
   LONG Err = 0;
   int  i;
   int PagesAnn;
   

   m_pAnnDoc = new ICDocAnnDoc(TRUE);
   
   Err = m_pAnnDoc->InitFromFile(m_FileNote);
   if (Err)
   {
      delete m_pAnnDoc;
      m_pAnnDoc = NULL;
      goto End;
   }
   
   for (i=1; i <=(int)m_NumPages; i++)
   {
      CListNote* pNotesList = new CListNote();

      Err = pNotesList->ReadNotesFile(m_pAnnDoc,i,this);

      if (Err) goto End;
      m_ListPages.AddTail(pNotesList);
   }

   PagesAnn = m_pAnnDoc->m_pPages->GetSize();

   for (i=PagesAnn; i<(int)m_NumPages; i++)
   {
      ICDocAnnPage* pAnnPage = new ICDocAnnPage(TRUE);
      pAnnPage->Init();
      m_pAnnDoc->m_pPages->Add(pAnnPage);
   }

   m_RotIcon = TRUE;

   End:

   if (Err) 
   {
      CString Msg;
      Msg.LoadString(IDS_ERR_FILENOTE);
      AfxMessageBox(Msg);
      Ret = FALSE;
   }
   
   return(Ret);
}


void CVw::RotateIcons(short Angle)
{
   CSize    ImgSize;   
   POSITION pos;
   short    SetAngle;

   if (Angle == 0) return;
   
   pos = m_ListPages.FindIndex(m_OpenPag -1);
   if (pos != NULL)
   {  
      LPFNDimensions lpfnDimensions = NULL;
      UINT           bpp;
      CListNote*     pNote = (CListNote*)m_ListPages.GetAt(pos);

      lpfnDimensions = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
      if (lpfnDimensions == NULL) {goto End;}

      lpfnDimensions(m_hIGear,&ImgSize.cx,&ImgSize.cy,&bpp);       
      
      pNote->SetDefExtRect(CRect(CPoint(0,0),ImgSize));  
      //pNote->m_Angle = GetRot();     
      
      switch(Angle)
      {
         case 0:
            SetAngle = 0;         
            break;
         case 1:
            SetAngle = 90;
            break;
         case 2:
            SetAngle = 180;
            break;
         case 3:
            SetAngle = 270;
            break;
      }
      pNote->m_Angle = SetAngle;
   
      pNote->RotateImg(SetAngle,ImgSize); 
      Invalidate(FALSE);
   }

   End:

   return;
   
}
void CVw::OnSaveAnn()
{
   if (m_ChangeAnn)
   {
      SaveAnn(m_FileNote);
      CMF* pFrame = (CMF*)GetParent();
      pFrame->EventSaveNotes();
      m_ChangeAnn = FALSE;
   }
}

BOOL CVw::SaveAnn(CString FileName)
{
   POSITION pos;
   int      i = 1;
   BOOL     ret = TRUE;
   CRect    rect,NewRect;
   int      OldAngle;

   pos = m_ListPages.GetHeadPosition();
   while(pos != NULL)
   {
      CListNote* pNote = (CListNote*)m_ListPages.GetNext(pos);
      OldAngle = (int)pNote->m_Angle;
      pNote->GetDefExtRect(&rect);
      switch(OldAngle)
      {
         case 90:
         {
            NewRect = CRect(CPoint(0,0),CSize(rect.Height(),rect.Width()));
            pNote->SetDefExtRect(NewRect);
            pNote->RotateImg (270,CSize(NewRect.Width(),NewRect.Height()));
            break;
         }
         case 180:
         {
            pNote->RotateImg(180,CSize(rect.Width(),rect.Height()));
            break;
         }
         case 270:
         {
            NewRect = CRect(CPoint(0,0), CSize(rect.Height(),rect.Width()));
            pNote->SetDefExtRect(NewRect);
            pNote->RotateImg(90,CSize(NewRect.Width(),NewRect.Height()));
            break;

         }
      }

      pNote->WriteNotesFile(m_pAnnDoc,i);
      i++;
   }

   if (m_pAnnDoc != NULL)
      m_pAnnDoc->WriteToFile(FileName);

   //Se vuelve al estado anterior

   pos = m_ListPages.GetHeadPosition();
   while(pos != NULL)
   {
      CListNote* pNote = (CListNote*)m_ListPages.GetNext(pos);
      OldAngle = (int)pNote->m_Angle;
      pNote->GetDefExtRect(&rect);

      switch(OldAngle)
      {
      case 90:
         {
            NewRect = CRect(CPoint(0,0),CSize(rect.Height(),rect.Width()));
            pNote->SetDefExtRect(NewRect);
            pNote->RotateImg(90,CSize(NewRect.Width(),NewRect.Height()));
            break;
         }
      case 180:
         {
            pNote->RotateImg(180,CSize(rect.Width(),rect.Height()));
            break;
         }
      case 270:
         {
            NewRect = CRect(CPoint(0,0), CSize(rect.Height(),rect.Width()));
            pNote->SetDefExtRect(NewRect);
            pNote->RotateImg(270,CSize(NewRect.Width(),NewRect.Height()));
            break;
         }
      }
   }
   
   
   return(ret);
}

void CVw::OnDeleteAnn()
{   
   DeleteAnn();
}

void CVw::DeleteAnn()
{
   POSITION pos;
   CMF*     pMF  = (CMF*)GetParent();
   
   pos = m_ListPages.FindIndex(m_OpenPag - 1);
   if (pos != NULL)
   {     
      CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);
      
      if (pNote->GetCountSel() != 0)
      {   
         pNote->DeleteAllObjSel(this,m_Zoom);           
         m_ChangeAnn = TRUE;
         pMF->EventModifyNotes();
         m_AnyChangeAnn = TRUE;
      }
   }
}

BOOL CVw::IsSelectedAnns()
{
   BOOL     IsSel = FALSE;
   POSITION pos;
   pos = m_ListPages.FindIndex(m_OpenPag - 1);
   if (pos != NULL)
   {     
      CListNote* pNote = (CListNote*)m_ListPages.GetAt(pos);
      
      if (pNote->GetCountSel() != 0)
         IsSel = TRUE;
   }

   return(IsSel);
}

BOOL CVw::SaveNotes()
{
   BOOL ret = TRUE;
   CMF* pFrame = (CMF*)GetParent();

   if (m_ChangeAnn)
   {
      ret = SaveAnn(m_FileNote);
      pFrame->EventSaveNotes();
      m_ChangeAnn = FALSE;
   }

   return(ret);
}

BOOL CVw::GetChangeAnn()
{
   return (m_ChangeAnn);
}

BOOL CVw::GetAnyChangeAnn()
{
   return (m_AnyChangeAnn);
}

LONG CVw::SaveNotesToFile(CString File)
{
   LONG Err = 0;
   BOOL Ret = TRUE;

   Ret = SaveAnn(File);
   if (!Ret) Err = IDOCIMGX_ERR_SAVEFILE;
 

   return (Err);
}

LONG CVw::GetImgWidth()
{
   LPFNDimensions lpfn;
   LONG           Width = 0;
   AT_DIMENSION   W,H;
   UINT           bpp;

   if (m_open)
   {
      lpfn = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
      if (lpfn == NULL) goto End;

      lpfn(m_hIGear,&W,&H,&bpp);

      Width = W;

   }

End:

   return(Width);
}

LONG CVw::GetImgHeight()
{
   LPFNDimensions lpfn;
   LONG           Height;
   AT_DIMENSION   W,H;
   UINT           bpp;

   if (m_open)
   {
      lpfn = (LPFNDimensions)GetProcAddress(_Module.m_hModGear,"IG_image_dimensions_get");
      if (lpfn == NULL) goto End;

      lpfn(m_hIGear,&W,&H,&bpp);

      Height = H;

   }

End:

   return(Height);
}

void CVw::OnEndoso()
{
   if (m_pEndoso == NULL)
      m_pEndoso = new CEndosoDlg(m_Endoso,this);
}

void CVw::OnInvert()
{
   InvertPalette(m_hIGear);
   Display();
}


void CVw::SetDrag(BOOL Drag)
{
   if (Drag)
      OnHand();
   else
   {
      m_Tool = P_NONE;
      SetStateToolBar();
   }
}

BOOL CVw::IsDrag()
{

   BOOL Drag = FALSE;

   if (m_Tool == P_DRAG)
      Drag = TRUE;
   
   return(Drag);

}



