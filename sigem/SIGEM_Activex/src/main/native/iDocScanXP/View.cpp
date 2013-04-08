// View.cpp : implementation of the Vw class
//

#include "stdafx.h"
#include "iDocScanXP.h"

#include "Doc.h"
#include "View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// Vw

IMPLEMENT_DYNCREATE(Vw, CView)

BEGIN_MESSAGE_MAP(Vw, CView)
	//{{AFX_MSG_MAP(Vw)
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// Vw construction/destruction

Vw::Vw()
{
}

Vw::~Vw()
{
}

BOOL Vw::PreCreateWindow(CREATESTRUCT& cs)
{
	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// Vw drawing

void Vw::OnDraw(CDC* /*pDC*/)
{
	CDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
}

/////////////////////////////////////////////////////////////////////////////
// Vw printing

BOOL Vw::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void Vw::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
}

void Vw::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
}

/////////////////////////////////////////////////////////////////////////////
// Vw diagnostics

#ifdef _DEBUG
void Vw::AssertValid() const
{
	CView::AssertValid();
}

void Vw::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CDoc* Vw::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CDoc)));
	return (CDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// Vw message handlers
