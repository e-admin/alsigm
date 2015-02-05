#if !defined(AFX_EDNOTDLG_H__D3BC3C20_7312_11D2_8126_00C0263508EA__INCLUDED_)
#define AFX_EDNOTDLG_H__D3BC3C20_7312_11D2_8126_00C0263508EA__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000
// EdNotDlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CEdNotDlg dialog

class CEdNotDlg : public CPropertyPage
{
	DECLARE_DYNCREATE(CEdNotDlg)

   public:

   CEdNotDlg();
	CEdNotDlg(CString& Title,CString& Text);
	~CEdNotDlg();


	//{{AFX_DATA(CEdNotDlg)
	enum { IDD = IDD_TITLE_EDNOT };
	CString	m_TextDlg;
	//}}AFX_DATA


	//{{AFX_VIRTUAL(CEdNotDlg)
	public:
	virtual void OnOK();
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);   
	//}}AFX_VIRTUAL


   protected:
	
	//{{AFX_MSG(CEdNotDlg)
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

   protected:
   CString m_TitleDlg;

};

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_EDNOTDLG_H__D3BC3C20_7312_11D2_8126_00C0263508EA__INCLUDED_)
