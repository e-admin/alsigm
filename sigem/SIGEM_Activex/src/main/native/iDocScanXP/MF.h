// MF.h : interface of the CMF class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_MF_H__C4C6CFD9_C1B7_42B4_BA5D_B7893AC0B442__INCLUDED_)
#define AFX_MF_H__C4C6CFD9_C1B7_42B4_BA5D_B7893AC0B442__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CMF : public CFrameWnd
{
	
protected: // create from serialization only
	CMF();
	DECLARE_DYNCREATE(CMF)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMF)
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CMF();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:  // control bar embedded members
	CStatusBar  m_wndStatusBar;
	CToolBar    m_wndToolBar;

// Generated message map functions
protected:
	//{{AFX_MSG(CMF)
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MF_H__C4C6CFD9_C1B7_42B4_BA5D_B7893AC0B442__INCLUDED_)
