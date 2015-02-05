#if !defined(AFX_PROXYDLG_H__7A88A42D_9C7C_4EBC_B160_A394016B1C98__INCLUDED_)
#define AFX_PROXYDLG_H__7A88A42D_9C7C_4EBC_B160_A394016B1C98__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ProxyDlg.h : header file
//

#include "resource.h"

/////////////////////////////////////////////////////////////////////////////
// CProxyDlg dialog

class CProxyDlg : public CDialog
{
// Construction
public:
	CProxyDlg(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CProxyDlg)
	enum { IDD = IDD_PROXY_AUTH };
	CString	m_strPassword;
	CString	m_strServer;
	CString	m_strUserName;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CProxyDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CProxyDlg)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PROXYDLG_H__7A88A42D_9C7C_4EBC_B160_A394016B1C98__INCLUDED_)
