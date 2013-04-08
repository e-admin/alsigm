// iDocImgX.cpp : Implementation of DLL Exports.


// Note: Proxy/Stub Information
//      To build a separate proxy/stub DLL, 
//      run nmake -f iDocImgXps.mk in the project directory.

#include "stdafx.h"
#include "resource.h"
#include <initguid.h>
#include "iDocImgX.h"
#include "AXSafe.h"

#include "iDocImgX_i.c"
#include "Control.h"
#include "iDocGear.h"
#include "PrintJob.h"


CiDocImgXModule _Module;

BEGIN_OBJECT_MAP(ObjectMap)
OBJECT_ENTRY(CLSID_Control, CControl)
OBJECT_ENTRY(CLSID_PrintJob, CPrintJob)
END_OBJECT_MAP()

class CIDocImgXApp : public CWinApp
{
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CIDocImgXApp)
	public:
    virtual BOOL InitInstance();
    virtual int ExitInstance();
    virtual void OnFinalRelease();
	//}}AFX_VIRTUAL

	//{{AFX_MSG(CIDocImgXApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
   protected:

   void LoadGearLibrary();
};

BEGIN_MESSAGE_MAP(CIDocImgXApp, CWinApp)
	//{{AFX_MSG_MAP(CIDocImgXApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

CIDocImgXApp theApp;

BOOL CIDocImgXApp::InitInstance()
{
    _Module.Init(ObjectMap, m_hInstance, &LIBID_IDOCIMGXLib);
    _Module.m_hModGear = NULL;
    LoadGearLibrary();
    return CWinApp::InitInstance();
}

void CIDocImgXApp::OnFinalRelease()
{
   if (_Module.m_hModGear != NULL)
      FreeLibrary(_Module.m_hModGear); 
}

int CIDocImgXApp::ExitInstance()
{       
   _Module.Term();    
   return CWinApp::ExitInstance();   
}

void CIDocImgXApp::LoadGearLibrary()
{
   TCHAR   PathName[_MAX_PATH];
   HMODULE hInst;
   CString Name;
   int     index;
   CString CurrLibName;
   CString FindLibName;

   #ifdef _DEBUG
      CurrLibName = "iDocImgXd.dll";
   #else
      CurrLibName = "iDocImgX.dll";
   #endif

   
   FindLibName = "Gear32pd.dll";
   
   hInst = ::GetModuleHandle(CurrLibName);
   ::GetModuleFileName(hInst,PathName,_MAX_PATH);
   Name = PathName;   
   
   index = Name.ReverseFind('\\');
   if (index != -1)
   {
      Name = Name.Left(index+1);
      Name += FindLibName;
      _Module.m_hModGear = LoadLibrary(Name);
   }

   if (_Module.m_hModGear == NULL)
      _Module.m_hModGear = LoadLibrary("Gear32pd.dll");

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
   // return _Module.RegisterServer(TRUE);

   HRESULT Res;

   Res = _Module.RegisterServer(TRUE);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForInitializing, L"Controles con inicialización segura");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_Control, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_PrintJob, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = CreateComponentCategory(CATID_SafeForScripting, L"Controles con scripting seguro");
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_Control, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   Res = RegisterCLSIDInCategory(CLSID_PrintJob, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   return(Res);




}

/////////////////////////////////////////////////////////////////////////////
// DllUnregisterServer - Removes entries from the system registry

STDAPI DllUnregisterServer(void)
{
   // return _Module.UnregisterServer(TRUE);

   HRESULT Res;

   Res = _Module.UnregisterServer(TRUE);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_Control, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_Control, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_PrintJob, CATID_SafeForInitializing);
   if (FAILED(Res))
      return(Res);

   Res = UnRegisterCLSIDInCategory(CLSID_PrintJob, CATID_SafeForScripting);
   if (FAILED(Res))
      return(Res);

   return(Res);
}

/*
 * RaiseException
 *
 * Parameters:
 *  nID                 Error number
 *  rguid               GUID of interface that is raising the exception.
 *
 * Return Value:
 *  HRESULT correspnding to the nID error number.
 *
 * Purpose:
 *  Fills the EXCEPINFO structure. 
 *  Sets ErrorInfo object for vtable-binding controllers.
 *  For id-binding and late binding controllers DispInvoke
 *  will return DISP_E_EXCEPTION and fill the EXCEPINFO parameter
 *  with the error information set by SetErrorInfo.
 *
 */  
HRESULT RaiseException(int nID, REFGUID rguid)
{  
   CString           ProgId,Descr;    
   ICreateErrorInfo* pCErrInfo = NULL;;  
   IErrorInfo*       pErrInfo;
   HRESULT           hr = S_OK;      
   BSTR              Dsc,PgId;
    
   Descr.LoadString(nID);
   Dsc = Descr.AllocSysString();     
    
   ProgId = "iDocImgX-Control";
   PgId = ProgId.AllocSysString();

   hr = CreateErrorInfo(&pCErrInfo); 
   if (SUCCEEDED(hr))
   {
      pCErrInfo->SetGUID(rguid);
      pCErrInfo->SetSource(PgId);
    
      pCErrInfo->SetDescription(Dsc);  
      hr = pCErrInfo->QueryInterface(IID_IErrorInfo, (LPVOID FAR*) &pErrInfo);
      if (SUCCEEDED(hr))
      {
         SetErrorInfo(0, pErrInfo);
         pErrInfo->Release();
      }  
      pCErrInfo->Release();
   }  
 
   return (nID);   
}
