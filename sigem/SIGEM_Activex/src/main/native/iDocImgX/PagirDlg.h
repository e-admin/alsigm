#if !defined(AFX_PAGIRDLG_H__EC3D8121_55CF_11D1_8126_00C0263508EA__INCLUDED_)
#define AFX_PAGIRDLG_H__EC3D8121_55CF_11D1_8126_00C0263508EA__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000
// PagirDlg.h : header file
//

class CVw;

class CPagirDlg : public CDialog
{

   public:
   
   CPagirDlg(LONG Numpages,LONG PagOpen,CWnd* pParent = NULL); 

	//{{AFX_DATA(CPagirDlg)
	enum { IDD = IDD_PAGIR };
	CComboBox	m_ListPage;
	//}}AFX_DATA


	//{{AFX_VIRTUAL(CPagirDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);
	//}}AFX_VIRTUAL

   protected:

	//{{AFX_MSG(CPagirDlg)
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg void OnEditchangeListpage();
	afx_msg void OnClose();
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()

   protected:

   CVw* m_pVw;
   LONG m_NumPages;
   LONG m_PagOpen;

   
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PAGIRDLG_H__EC3D8121_55CF_11D1_8126_00C0263508EA__INCLUDED_)
