// ProxyDlg.cpp : implementation file
//

#include "stdafx.h"
#include "idocpost.h"
#include "ProxyDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CProxyDlg dialog


CProxyDlg::CProxyDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CProxyDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CProxyDlg)
	m_strPassword = _T("");
	m_strServer = _T("");
	m_strUserName = _T("");
	//}}AFX_DATA_INIT
}


void CProxyDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CProxyDlg)
	DDX_Text(pDX, IDC_EDIT_PASSWORD, m_strPassword);
	DDX_Text(pDX, IDC_EDIT_SERVER, m_strServer);
	DDX_Text(pDX, IDC_EDIT_USER, m_strUserName);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CProxyDlg, CDialog)
	//{{AFX_MSG_MAP(CProxyDlg)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CProxyDlg message handlers
