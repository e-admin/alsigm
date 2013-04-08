// TextDlg.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "TextDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

//Tipos de bordes

#define NONE_BORDER   0 
#define SIMPLE_BORDER 1
#define DOUBLE_BORDER 2

//Lado del objeto

#define LEFT_OBJ     0
#define TOP_OBJ      1 
#define RIGHT_OBJ    2 
#define BOTTOM_OBJ   3  

/////////////////////////////////////////////////////////////////////////////
// CTextDlg property page

IMPLEMENT_DYNCREATE(CTextDlg, CPropertyPage)

CTextDlg::CTextDlg() : CPropertyPage(CTextDlg::IDD)
{
	//{{AFX_DATA_INIT(CTextDlg)
	m_Justify = -1;
	//}}AFX_DATA_INIT
}

CTextDlg::CTextDlg(LOGFONT* pFont,int FontSize,int* BorderArr,int Justify,
            COLORREF FontColor,CWnd* pWnd) : CPropertyPage(CTextDlg::IDD)
{
   m_pFont     = pFont; 
   m_pParent   = pWnd;
   m_FontSize  = FontSize;   
   m_FontColor = FontColor;    
   
   int i;
   
   for(i=0;i<4;i++)
   {
      m_BorderArr[i] = BorderArr[i];
   }
	//{{AFX_DATA_INIT(CTextDlg)
	m_Justify = Justify;
	//}}AFX_DATA_INIT
}


CTextDlg::~CTextDlg()
{
}

void CTextDlg::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTextDlg)
	DDX_Control(pDX, IDC_BORDER_TOP, m_CB_Top);
	DDX_Control(pDX, IDC_BORDER_RIGHT, m_CB_Right);
	DDX_Control(pDX, IDC_BORDER_LEFT, m_CB_Left);
	DDX_Control(pDX, IDC_BORDER_DOWN, m_CB_Down);
	DDX_Control(pDX, IDC_BORDER_ALL, m_CB_All);
	DDX_Radio(pDX, IDC_TXT_LEFT, m_Justify);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CTextDlg, CPropertyPage)
	//{{AFX_MSG_MAP(CTextDlg)
	ON_BN_CLICKED(IDC_FONT, OnFont)
	ON_CBN_SELCHANGE(IDC_BORDER_ALL, OnSelchangeBorderAll)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CTextDlg message handlers

BOOL CTextDlg::OnInitDialog() 
{
   CenterWindow(m_pParent);
	CPropertyPage::OnInitDialog();

   SetDlgItemText(IDC_ST_FONT,m_pFont->lfFaceName);
   SetDlgItemInt(IDC_ST_SIZE,m_FontSize);  
   InitBorderList();
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CTextDlg::OnOK() 
{
	m_BorderArr[TOP_OBJ]    = m_CB_Top.GetCurSel(); 
   m_BorderArr[BOTTOM_OBJ] = m_CB_Down.GetCurSel();
   m_BorderArr[LEFT_OBJ]   = m_CB_Left.GetCurSel();
   m_BorderArr[RIGHT_OBJ]  = m_CB_Right.GetCurSel();
	
	CPropertyPage::OnOK();
}

void CTextDlg::OnFont() 
{
	CFontDialog FontDlg(m_pFont,CF_EFFECTS|CF_SCREENFONTS|CF_FORCEFONTEXIST|
                        CF_INITTOLOGFONTSTRUCT,NULL,this);
                         
   FontDlg.m_cf.hwndOwner = this->m_hWnd; 
   FontDlg.m_cf.rgbColors = m_FontColor; 
    
     
   if (FontDlg.DoModal() == IDOK)
   {   
      SetDlgItemText(IDC_ST_FONT,FontDlg.GetFaceName());
      m_FontSize = FontDlg.GetSize()/10;       
      SetDlgItemInt(IDC_ST_SIZE,m_FontSize);
      m_pFont = FontDlg.m_cf.lpLogFont;
      m_FontColor = FontDlg.GetColor();
   }
	
}

void CTextDlg::OnSelchangeBorderAll() 
{
	int TypeBorder;
   
   TypeBorder = m_CB_All.GetCurSel();    
   
   m_CB_Top.SetCurSel(TypeBorder);   
   m_CB_Down.SetCurSel(TypeBorder);   
   m_CB_Left.SetCurSel(TypeBorder);    
   m_CB_Right.SetCurSel(TypeBorder); 
	
}

void CTextDlg::InitBorderList()
{  
   int  i; 
   int  TypeBorder = NONE_BORDER;
   BOOL DoubleBorder = TRUE;
   
   FillList(&m_CB_Top);
   m_CB_Top.SetCurSel(m_BorderArr[TOP_OBJ]);
   
   FillList(&m_CB_Down);
   m_CB_Down.SetCurSel(m_BorderArr[BOTTOM_OBJ]);    
   
   FillList(&m_CB_Left);
   m_CB_Left.SetCurSel(m_BorderArr[LEFT_OBJ]);  
   
   FillList(&m_CB_Right);
   m_CB_Right.SetCurSel(m_BorderArr[RIGHT_OBJ]);  
   
   FillList(&m_CB_All);
   for(i=0;i<4;i++)
   {
      if (m_BorderArr[i] == NONE_BORDER)
      {
         TypeBorder = NONE_BORDER;
         DoubleBorder = FALSE;
         break;
      }
      if (m_BorderArr[i] == SIMPLE_BORDER)
      {
         DoubleBorder = FALSE;
         TypeBorder = SIMPLE_BORDER;
      }                         
      if (m_BorderArr[i] == DOUBLE_BORDER && DoubleBorder)
         TypeBorder = DOUBLE_BORDER;
   }
   m_CB_All.SetCurSel(TypeBorder);   
} 

/* FillList: Rellena los combobox para que no haya problema con la 
             traducción */

void CTextDlg::FillList(CComboBox* pList)
{  
   CString Aux;   
      
   Aux.LoadString(IDS_NONE_BORDER);   
   pList->AddString(Aux);
   
   Aux.LoadString(IDS_SIMPLE_BORDER);   
   pList->AddString(Aux);                       
   
   Aux.LoadString(IDS_DOUBLE_BORDER);   
   pList->AddString(Aux); 
    
}
