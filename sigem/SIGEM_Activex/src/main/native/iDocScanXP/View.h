// View.h : interface of the Vw class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_VIEW_H__B5B0EC27_93D4_4EAD_AAEB_C5F6E915DE48__INCLUDED_)
#define AFX_VIEW_H__B5B0EC27_93D4_4EAD_AAEB_C5F6E915DE48__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class Vw : public CView
{
protected: // create from serialization only
	Vw();
	DECLARE_DYNCREATE(Vw)

// Attributes
public:
	CDoc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(Vw)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~Vw();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(Vw)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in View.cpp
inline CDoc* Vw::GetDocument()
   { return (CDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_VIEW_H__B5B0EC27_93D4_4EAD_AAEB_C5F6E915DE48__INCLUDED_)
