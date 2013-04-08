// NotDlg.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "NotDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CNotDlg dialog


CNotDlg::CNotDlg(CString& Texto,CWnd* pParent /*=NULL*/)
	: CDialog(CNotDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CNotDlg)
	m_text = Texto;
	//}}AFX_DATA_INIT
}


void CNotDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CNotDlg)
	DDX_Text(pDX, IDC_NOT_TXT, m_text);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CNotDlg, CDialog)
	//{{AFX_MSG_MAP(CNotDlg)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CNotDlg message handlers
