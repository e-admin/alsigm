// PagirDlg.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "iDocGear.h"
#include "trk.h"
#include "trksel.h"
#include "PagirDlg.h"
#include "Vw.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


CPagirDlg::CPagirDlg(LONG Numpages,LONG PagOpen,CWnd* pParent /*=NULL*/)
	: CDialog(CPagirDlg::IDD, pParent)
{	
   m_pVw = (CVw*)pParent;
   m_NumPages = Numpages;
   m_PagOpen = PagOpen;
   Create(CPagirDlg::IDD,pParent);   
}


void CPagirDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPagirDlg)
	DDX_Control(pDX, IDC_LISTPAGE, m_ListPage);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CPagirDlg, CDialog)
	//{{AFX_MSG_MAP(CPagirDlg)
	ON_CBN_EDITCHANGE(IDC_LISTPAGE, OnEditchangeListpage)
	ON_WM_CLOSE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPagirDlg message handlers

void CPagirDlg::OnOK() 
{
   CString page;
   LONG pageSel;
   CWnd* pWnd = GetDlgItem(IDC_LISTPAGE);
   pWnd->GetWindowText(page);
	pageSel = atol(page);
   	
	CDialog::OnOK();
   if (m_pVw->m_pIrPag != NULL)
   {
      m_pVw->m_pIrPag = NULL;
      m_pVw->SelPage(pageSel);
      delete this;
   }

   
}


BOOL CPagirDlg::OnInitDialog() 
{
   LONG i;
   int cur;
   char buff[8];

	CDialog::OnInitDialog();
	
   for (i=1; i<= m_NumPages ; i++)
   {
      if (i == m_PagOpen)
         cur = (int)(i-1);
      ltoa(i,buff,10);
      m_ListPage.AddString(buff);
   }
   m_ListPage.SetCurSel(cur);
	
	return TRUE;  
}

void CPagirDlg::OnEditchangeListpage() 
{
   CString page;
	CWnd* pWnd = GetDlgItem(IDC_LISTPAGE);
   pWnd->GetWindowText(page);
   
   pWnd= GetDlgItem(IDOK);

   if (m_ListPage.FindString(-1,page) == CB_ERR )
      pWnd->EnableWindow(FALSE);
   else
      pWnd->EnableWindow(TRUE);
	
}

void CPagirDlg::OnClose() 
{	
	CDialog::OnClose();
   if (m_pVw->m_pIrPag != NULL)
   {
      m_pVw->m_pIrPag = NULL;
	   delete this;
   }
	
}
