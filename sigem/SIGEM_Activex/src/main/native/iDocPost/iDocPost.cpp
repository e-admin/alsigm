// iDocPost.cpp : Implementation of DLL Exports.


// Note: Proxy/Stub Information
//      To build a separate proxy/stub DLL, 
//      run nmake -f iDocPostps.mk in the project directory.

#include "stdafx.h"
#include "resource.h"
#include <initguid.h>
#include "iDocPost.h"

#include "iDocPost_i.c"

#include  "..\LNUtil\LNUtil.h"
#include "Upload.h"

CComModule _Module;

BEGIN_OBJECT_MAP(ObjectMap)
OBJECT_ENTRY(CLSID_Upload, CUpload)
END_OBJECT_MAP()

class CIDocPostApp : public CWinApp
{
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CIDocPostApp)
	public:
    virtual BOOL InitInstance();
    virtual int ExitInstance();
	//}}AFX_VIRTUAL

	//{{AFX_MSG(CIDocPostApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

BEGIN_MESSAGE_MAP(CIDocPostApp, CWinApp)
	//{{AFX_MSG_MAP(CIDocPostApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

CIDocPostApp theApp;

BOOL CIDocPostApp::InitInstance()
{
    _Module.Init(ObjectMap, m_hInstance, &LIBID_IDOCPOSTLib);
    return CWinApp::InitInstance();
}

int CIDocPostApp::ExitInstance()
{
    _Module.Term();
    return CWinApp::ExitInstance();
}

/////////////////////////////////////////////////////////////////////////////
// Used to determine whether the DLL can be unloaded by OLE

STDAPI DllCanUnloadNow(void)
{
    AFX_MANAGE_STATE(AfxGetStaticModuleState());
    return (AfxDllCanUnloadNow()==S_OK && _Module.GetLockCount()==0) ? S_OK : S_FALSE;
}

/////////////////////////////////////////////////////////////////////////////
// Returns a class factory to create an object of the requested type

STDAPI DllGetClassObject(REFCLSID rclsid, REFIID riid, LPVOID* ppv)
{
    return _Module.GetClassObject(rclsid, riid, ppv);
}

/////////////////////////////////////////////////////////////////////////////
// DllRegisterServer - Adds entries to the system registry

STDAPI DllRegisterServer(void)
{
    // registers object, typelib and all interfaces in typelib
   HRESULT Res;

   Res = _Module.RegisterServer(TRUE);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForInitializing, L"Controles con inicialización segura");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_Upload, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForScripting, L"Controles con scripting seguro");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_Upload, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   return(Res);
}

/////////////////////////////////////////////////////////////////////////////
// DllUnregisterServer - Removes entries from the system registry

STDAPI DllUnregisterServer(void)
{
   HRESULT Res;

   Res = _Module.UnregisterServer(TRUE);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_Upload, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_Upload, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   return(Res);
}


