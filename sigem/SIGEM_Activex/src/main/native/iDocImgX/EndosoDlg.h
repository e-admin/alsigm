#ifndef __ENDOSODLG_H__
#define __ENDOSODLG_H__

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

class CVw;
class CEndosoDlg : public CDialog
{

   public:

	CEndosoDlg(CString Endoso, CWnd* pParent = NULL);


	//{{AFX_DATA(CEndosoDlg)
	enum { IDD = IDD_ENDOSO };
	//}}AFX_DATA

	//{{AFX_VIRTUAL(CEndosoDlg)	
	//}}AFX_VIRTUAL


   protected:

	//{{AFX_MSG(CEndosoDlg)
   afx_msg void OnClose();
	virtual BOOL OnInitDialog();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

   protected:

   CString m_Endoso;
   CVw*    m_pVw;
   
};

#endif // __ENDOSODLG_H__
