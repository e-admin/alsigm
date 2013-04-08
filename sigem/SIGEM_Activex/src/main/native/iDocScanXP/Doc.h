// Doc.h : interface of the CDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_DOC_H__9EAB4B9A_5495_45B0_919E_03EE8A667023__INCLUDED_)
#define AFX_DOC_H__9EAB4B9A_5495_45B0_919E_03EE8A667023__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CDoc : public CDocument
{
protected: // create from serialization only
	CDoc();
	DECLARE_DYNCREATE(CDoc)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CDoc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CDoc)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_DOC_H__9EAB4B9A_5495_45B0_919E_03EE8A667023__INCLUDED_)
