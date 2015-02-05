#if !defined(AFX_NOTDLG_H__35B251A5_6FDD_11D2_8126_00C0263508EA__INCLUDED_)
#define AFX_NOTDLG_H__35B251A5_6FDD_11D2_8126_00C0263508EA__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000
// NotDlg.h : header file
//


class CNotDlg : public CDialog
{

   public:
	CNotDlg(CString& Texto,CWnd* pParent = NULL);  

	//{{AFX_DATA(CNotDlg)
	enum { IDD = IDD_TITLE_NOT };
	CString	m_text;
	//}}AFX_DATA


	//{{AFX_VIRTUAL(CNotDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);  
	//}}AFX_VIRTUAL

   protected:
	
	//{{AFX_MSG(CNotDlg)
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()

};

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_NOTDLG_H__35B251A5_6FDD_11D2_8126_00C0263508EA__INCLUDED_)
