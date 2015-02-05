#include "StdAfx.h"
#include "Misc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocImgX Misc                                                   //
//                                                                  //
//////////////////////////////////////////////////////////////////////

void IDOCIMGXSetErrorInfo(const CString& Class, const CString& Method,LONG Error)
{

   HRESULT           hRes = S_OK;
   CString           Src,Descr;
   ICreateErrorInfo* pCEI = NULL;
   IErrorInfo*       pEI  = NULL;
   BSTR              S,D;
   CString           ServerName;
      
   Src = "iDocImgX.Control";
   Descr.Format("%s: %ld",(LPCSTR)Method,Error);  

   S = Src.AllocSysString();
   D = Descr.AllocSysString();

   hRes = CreateErrorInfo(&pCEI);
   if ( FAILED(hRes) ) goto End;

   hRes = pCEI->QueryInterface(IID_IErrorInfo,(void**)&pEI);
   if ( FAILED(hRes) ) goto End;

   hRes = pCEI->SetSource(S);
   if ( FAILED(hRes) ) goto End;

   hRes = pCEI->SetDescription(D);
   if ( FAILED(hRes) ) goto End;

   hRes = SetErrorInfo(0,pEI);
   if ( FAILED(hRes) ) goto End;

   End:

   if (pEI != NULL) pEI->Release();
   if (pCEI != NULL) pCEI->Release();

}