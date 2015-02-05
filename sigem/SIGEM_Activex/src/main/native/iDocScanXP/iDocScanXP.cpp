// iDocScanXP.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include <atlctl.h>
#include <initguid.h>
#include "iDocScanXP.h"

#include "MF.h"
#include "Doc.h"
#include "View.h"
//#include "IDocScanXP_i.c"
#include "Scanner.h"
#include "AxSafe.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocScanXP Module                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

CIDocScanXPModule _Module;

BEGIN_OBJECT_MAP(ObjectMap)
OBJECT_ENTRY(CLSID_Scanner, CScanner)
END_OBJECT_MAP()

LONG CIDocScanXPModule::Unlock()
{
	AfxOleUnlockApp();
	return 0;
}

LONG CIDocScanXPModule::Lock()
{
	AfxOleLockApp();
	return 1;
}


/////////////////////////////////////////////////////////////////////////////
// CApp

BEGIN_MESSAGE_MAP(CApp, CWinApp)
	//{{AFX_MSG_MAP(CApp)
	ON_COMMAND(ID_APP_ABOUT, OnAppAbout)
	//}}AFX_MSG_MAP
	// Standard file based document commands
	ON_COMMAND(ID_FILE_NEW, CWinApp::OnFileNew)
	ON_COMMAND(ID_FILE_OPEN, CWinApp::OnFileOpen)
	// Standard print setup command
	ON_COMMAND(ID_FILE_PRINT_SETUP, CWinApp::OnFilePrintSetup)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CApp construction

CApp::CApp()
{
   m_ATLInited    = FALSE;
   m_CORegistered = FALSE;
}

/////////////////////////////////////////////////////////////////////////////
// The one and only CApp object

CApp theApp;

/////////////////////////////////////////////////////////////////////////////
// CApp initialization

BOOL CApp::InitInstance()
{

	if ( !AfxOleInit() ) return(FALSE);
   if ( !InitATL() ) return(FALSE);

   BOOL Reg,Unreg;
   GetRegCommand(Reg,Unreg);

   if (Reg)
   {
      RegisterApp();
      return(TRUE);
   }
   else if (Unreg)
   {
      UnregisterApp();
      return(TRUE);
   }

	AfxEnableControlContainer();

	// Standard initialization

#ifdef _AFXDLL
	Enable3dControls();			// Call this when using MFC in a shared DLL
#else
	Enable3dControlsStatic();	// Call this when linking to MFC statically
#endif

	// Change the registry key under which our settings are stored.
	SetRegistryKey(_T("Local AppWizard-Generated Applications"));

	LoadStdProfileSettings();  // Load standard INI file options (including MRU)

	// Register document templates

	CSingleDocTemplate* pDocTemplate;
	pDocTemplate = new CSingleDocTemplate(
		IDR_MAINFRAME,
		RUNTIME_CLASS(CDoc),
		RUNTIME_CLASS(CMF),       // main SDI frame window
		RUNTIME_CLASS(Vw));
	AddDocTemplate(pDocTemplate);

	_Module.RegisterClassObjects(CLSCTX_LOCAL_SERVER,REGCLS_MULTIPLEUSE);
   m_CORegistered = TRUE;

	// Parse command line for standard shell commands, DDE, file open
	CCommandLineInfo cmdInfo;
	ParseCommandLine(cmdInfo);

	if (cmdInfo.m_bRunEmbedded || cmdInfo.m_bRunAutomated)
	{
		return TRUE;
	}
   else
      return FALSE;
   

	// Dispatch commands specified on the command line
	if (!ProcessShellCommand(cmdInfo))
		return FALSE;
	m_pMainWnd->ShowWindow(SW_SHOW);
	m_pMainWnd->UpdateWindow();

	return TRUE;

}

HRESULT CApp::RegisterApp()
{
   HRESULT Res;

   _Module.UpdateRegistryFromResource(IDR_IDOCSCANXP,TRUE);
   _Module.RegisterServer(TRUE);
   COleObjectFactory::UpdateRegistryAll();

   Res = CreateComponentCategory(CATID_SafeForInitializing, L"Controles con inicialización segura");
   if (FAILED(Res))
      goto End;

   Res = RegisterCLSIDInCategory(CLSID_Scanner, CATID_SafeForInitializing);
   if (FAILED(Res))
      goto End;

   Res = CreateComponentCategory(CATID_SafeForScripting, L"Controles con scripting seguro");
   if (FAILED(Res))
      goto End;

   Res = RegisterCLSIDInCategory(CLSID_Scanner, CATID_SafeForScripting);
   if (FAILED(Res))
      goto End;

   End:

   return(Res);
}

HRESULT CApp::UnregisterApp()
{
   HRESULT Res;

   _Module.UpdateRegistryFromResource(IDR_IDOCSCANXP,FALSE);
   _Module.UnregisterServer(TRUE);
   Unregister();


   Res = UnRegisterCLSIDInCategory(CLSID_Scanner, CATID_SafeForInitializing);
   if (FAILED(Res))
      goto End;

   Res = UnRegisterCLSIDInCategory(CLSID_Scanner, CATID_SafeForScripting);
   if (FAILED(Res))
      goto End;

   End:

   return(Res);

}


/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
		// No message handlers
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

// App command to run the dialog
void CApp::OnAppAbout()
{
	CAboutDlg aboutDlg;
	aboutDlg.DoModal();
}

/////////////////////////////////////////////////////////////////////////////
// CApp message handlers


LPCTSTR FindOneOf(LPCTSTR Src,LPCTSTR Tokens)
{

   while (*Src != NULL)
   {

      LPCTSTR p = Tokens;

      while (*p != NULL)
      {
         if (*Src == *p++)
            return(Src+1);
      }

      Src++;

   }

   return(NULL);

}

void CApp::GetRegCommand(BOOL& Reg,BOOL& Unreg)
{

   LPTSTR  pCmdLine;
   LPCTSTR pToken;
   TCHAR   Tokens[] = _T("-/");

   Reg   = FALSE;
   Unreg = FALSE;

   pCmdLine = GetCommandLine();

   pToken = FindOneOf(pCmdLine,Tokens);

   while (pToken != NULL)
   {

      if (lstrcmpi(pToken,_T("UnregServer")) == 0)
      {
         Unreg = TRUE;
         break;
      }

      if (lstrcmpi(pToken,_T("Unregister")) == 0)
      {
         Unreg = TRUE;
         break;
      }

      if (lstrcmpi(pToken,_T("RegServer")) == 0)
      {
         Reg = TRUE;
         break;
      }

      if (lstrcmpi(pToken,_T("Register")) == 0)
      {
         Reg = TRUE;
         break;
      }

      pToken = FindOneOf(pToken,Tokens);

   }

}


int CApp::ExitInstance()
{
	if (m_CORegistered)
      _Module.RevokeClassObjects();

   if (m_ATLInited)
      _Module.Term();

	return CWinApp::ExitInstance();

}

BOOL CApp::InitATL()
{

   m_ATLInited = TRUE;

   _Module.Init(ObjectMap,AfxGetInstanceHandle());
   _Module.m_ThreadID = GetCurrentThreadId();

   return(TRUE);

}

