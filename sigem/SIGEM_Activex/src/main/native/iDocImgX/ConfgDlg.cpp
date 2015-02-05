// ConfgDlg.cpp : implementation file
//

#include "stdafx.h"
#include "resource.h"
#include "iDocGear.h"
#include "ConfgDlg.h"
#include "trk.h"
#include "trksel.h"
#include "Vw.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CConfgDlg dialog


CConfgDlg::CConfgDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CConfgDlg::IDD, pParent)
{   
   Create(CConfgDlg::IDD,pParent);
   m_pVw = (CVw*)pParent;
}


void CConfgDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CConfgDlg)
	DDX_Check(pDX, IDC_PAL, m_Pal);
	DDX_Radio(pDX, IDC_WHITE, m_DrawMode);
	DDX_Radio(pDX, IDC_WINDOW, m_Fit);
   DDX_Radio(pDX, IDC_NOROT, m_Rot);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CConfgDlg, CDialog)
	//{{AFX_MSG_MAP(CConfgDlg)
	ON_WM_CLOSE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CConfgDlg message handlers

void CConfgDlg::OnOK() 
{
   UpdateData(TRUE);

   m_DrawMode = m_DrawMode;
   m_reg.SetDrawMode(m_DrawMode);
   
   m_reg.SetFit(m_Fit); 
   m_reg.SetPalette(m_Pal);
   m_reg.SetRot(m_Rot);
	CDialog::OnOK();
   if (m_pVw->m_pConfig != NULL)
   {
      m_pVw->m_pConfig = NULL;
	   delete this;
   }
}

BOOL CConfgDlg::OnInitDialog() 
{ 
   
   m_reg.LoadSettings();
   m_Pal      = m_reg.GetPalette();
	m_DrawMode = (int)m_reg.GetDrawMode();
   m_DrawMode = m_DrawMode;   
	m_Fit      = (int)m_reg.GetFit();
   m_Rot      = (int)m_reg.GetRot();
   
	CDialog::OnInitDialog();	
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CConfgDlg::OnClose() 
{	
	CDialog::OnClose();
   if (m_pVw->m_pConfig != NULL)
   {
      m_pVw->m_pConfig = NULL;
	   delete this;
   }
}

void CConfgDlg::OnCancel() 
{
	CDialog::OnCancel();
   if (m_pVw->m_pConfig != NULL)
   {
      m_pVw->m_pConfig = NULL;
	   delete this;
   }	
}
