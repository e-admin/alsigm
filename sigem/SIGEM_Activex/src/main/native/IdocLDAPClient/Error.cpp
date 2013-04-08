// Error.cpp: implementation of the Error class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "resource.h"
#include "Error.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

void SetErrorInfo(const char* Class, const char* Method, 
                  LONG ErrorCode, const char* Description, CComModuleErr& Module)
{
   HRESULT           hRes = S_OK;
   ICreateErrorInfo* pCEI = NULL;
   CString           Src, Descr;
   IErrorInfo*       pEI  = NULL;
   BSTR              S, D;

   Module.m_LastError = ErrorCode;

   Src.Format("%s.%s", Module.m_ServerName, Class);
   Descr.Format("%s: %ld.%s", Method, Module.m_LastError, Description);

   S = Src.AllocSysString();
   D = Descr.AllocSysString();

   hRes = CreateErrorInfo(&pCEI);
   if (FAILED(hRes)) goto End;

   hRes = pCEI->QueryInterface(IID_IErrorInfo, (void**)&pEI);
   if (FAILED(hRes)) goto End;

   hRes = pCEI->SetSource(S);
   if (FAILED(hRes)) goto End;

   hRes = pCEI->SetDescription(D);
   if (FAILED(hRes)) goto End;

   hRes = SetErrorInfo(0, pEI);

   End:

   ::SysFreeString(S);
   ::SysFreeString(D);

   if (pEI != NULL) pEI->Release();
   if (pCEI != NULL) pCEI->Release();

}

// crea un errorinfo con errorcode, la descripcion que se corresponde con el id de recurso lnIdDescription
// si lnIdDescription es 0 --> la descripcion es strDescription
void CreateError(const char* Class, const char* Method, long lnErrorCode, long lnIdDescription, const char* strDescription)
{
   CString strCadena = "";

   if (lnIdDescription == 0)
   {
      strCadena = strDescription;
   }
   else
   {
      char MsgTmp[501];
      ::LoadString(_Module.GetResourceInstance(), lnIdDescription, MsgTmp, 500);
      strCadena.Format("%s",MsgTmp);
   }
   SetErrorInfo(Class, Method, lnErrorCode, strCadena, _Module);
}