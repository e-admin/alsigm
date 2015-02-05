// EdNotDlg.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "EdNotDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CEdNotDlg property page

IMPLEMENT_DYNCREATE(CEdNotDlg, CPropertyPage)

CEdNotDlg::CEdNotDlg() : CPropertyPage(CEdNotDlg::IDD)
{
	//{{AFX_DATA_INIT(CEdNotDlg)
	m_TextDlg = "";
	//}}AFX_DATA_INIT
   
}


CEdNotDlg::CEdNotDlg(CString& Title,CString& Text) : CPropertyPage(CEdNotDlg::IDD)
{
	//{{AFX_DATA_INIT(CEdNotDlg)
	m_TextDlg = Text;
	//}}AFX_DATA_INIT
   m_TitleDlg = Title;
}

CEdNotDlg::~CEdNotDlg()
{
}

void CEdNotDlg::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CEdNotDlg)
	DDX_Text(pDX, IDC_NOTE, m_TextDlg);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CEdNotDlg, CPropertyPage)
	//{{AFX_MSG_MAP(CEdNotDlg)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CEdNotDlg message handlers

BOOL CEdNotDlg::OnInitDialog() 
{
	CPropertyPage::OnInitDialog();
		

	if (!m_TitleDlg.IsEmpty())
	   m_psp.pszTitle = m_TitleDlg;
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CEdNotDlg::OnOK() 
{
	UpdateData(TRUE);
	if (m_TextDlg.IsEmpty())
      return;
	
	CPropertyPage::OnOK();
}
