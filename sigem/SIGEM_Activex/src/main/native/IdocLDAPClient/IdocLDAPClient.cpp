// IdocLDAPClient.cpp : Implementation of DLL Exports.


// Note: Proxy/Stub Information
//      To build a separate proxy/stub DLL, 
//      run nmake -f IdocLDAPClientps.mk in the project directory.

#include "stdafx.h"
#include "resource.h"
#include <initguid.h>
#include "IdocLDAPClient.h"

#include "IdocLDAPClient_i.c"
#include "IdocLDAP.h"
#include "AXSafe.h"


CComModuleErr _Module;

BEGIN_OBJECT_MAP(ObjectMap)
OBJECT_ENTRY(CLSID_IdocLDAP, CIdocLDAP)
END_OBJECT_MAP()

class CIdocLDAPClientApp : public CWinApp
{
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CIdocLDAPClientApp)
	public:
    virtual BOOL InitInstance();
    virtual int ExitInstance();
	//}}AFX_VIRTUAL

	//{{AFX_MSG(CIdocLDAPClientApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

BEGIN_MESSAGE_MAP(CIdocLDAPClientApp, CWinApp)
	//{{AFX_MSG_MAP(CIdocLDAPClientApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

CIdocLDAPClientApp theApp;

BOOL CIdocLDAPClientApp::InitInstance()
{
    _Module.Init(ObjectMap, m_hInstance, &LIBID_IDOCLDAPCLIENTLib);
    return CWinApp::InitInstance();
}

int CIdocLDAPClientApp::ExitInstance()
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

   HRESULT Res;

    // registers object, typelib and all interfaces in typelib
   Res = _Module.RegisterServer(TRUE);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForInitializing, L"Controles con inicialización segura");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_IdocLDAP, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForScripting, L"Controles con scripting seguro");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_IdocLDAP, CATID_SafeForScripting);
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

   Res = UnRegisterCLSIDInCategory(CLSID_IdocLDAP, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_IdocLDAP, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   return(Res);

}


