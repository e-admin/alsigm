#if !defined(AFX_CONFGDLG_H__EEC29D20_0205_11D3_8126_00C0263508EA__INCLUDED_)
#define AFX_CONFGDLG_H__EEC29D20_0205_11D3_8126_00C0263508EA__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ConfgDlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CConfgDlg dialog
#include "iregini.h"

class CVw;

class CConfgDlg : public CDialog
{
// Construction
public:
	CConfgDlg(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CConfgDlg)
	enum { IDD = IDD_CONFIGURATION };
	BOOL	m_Pal;
	int		m_DrawMode;
	int		m_Fit;
   int      m_Rot;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CConfgDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CConfgDlg)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnClose();
	virtual void OnCancel();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

private:
   ICRegIni m_reg;  
   CVw*    m_pVw;   
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CONFGDLG_H__EEC29D20_0205_11D3_8126_00C0263508EA__INCLUDED_)
