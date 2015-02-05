// iDocScanXP.h : main header file for the IDOCSCANXP application
//

#if !defined(AFX_IDOCSCANXP_H__4EB3E7AE_3F7A_4B03_90B8_0E3B51D183CB__INCLUDED_)
#define AFX_IDOCSCANXP_H__4EB3E7AE_3F7A_4B03_90B8_0E3B51D183CB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols
#include "IDocScanXP_i.h"

/////////////////////////////////////////////////////////////////////////////
// CApp:
// See iDocScanXP.cpp for the implementation of this class
//

class CApp : public CWinApp
{
public:
	CApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CApp)
	public:
	virtual BOOL InitInstance();
		virtual int ExitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//{{AFX_MSG(CApp)
	afx_msg void OnAppAbout();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

   void GetRegCommand(BOOL& Reg,BOOL& Unreg);
   HRESULT RegisterApp();
   HRESULT UnregisterApp();


   private:

	BOOL m_ATLInited;
   BOOL m_CORegistered;

   private:

	BOOL InitATL();
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_IDOCSCANXP_H__4EB3E7AE_3F7A_4B03_90B8_0E3B51D183CB__INCLUDED_)
