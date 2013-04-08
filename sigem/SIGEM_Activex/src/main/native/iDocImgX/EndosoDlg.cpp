#include "stdafx.h"
#include "iDocGear.h"
#include "trk.h"
#include "trksel.h"
#include "resource.h"
#include "EndosoDlg.h"
#include "Vw.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CEndosoDlg dialog

CEndosoDlg::CEndosoDlg(CString Endoso, CWnd* pParent /*=NULL*/)
	: CDialog(CEndosoDlg::IDD, pParent)
{  
   m_Endoso = Endoso;   	
   m_pVw = (CVw*)pParent;
   Create(CEndosoDlg::IDD,pParent);
}


BEGIN_MESSAGE_MAP(CEndosoDlg, CDialog)
	//{{AFX_MSG_MAP(CEndosoDlg)
   ON_WM_CLOSE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

BOOL CEndosoDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
   CenterWindow();
  	
	SetDlgItemText(IDC_NUMBER,m_Endoso);
   
	return TRUE;  
}

void CEndosoDlg::OnClose() 
{	
	CDialog::OnClose();
   if (m_pVw->m_pEndoso != NULL)
   {
      m_pVw->m_pEndoso = NULL;
	   delete this;
   }
	
}

