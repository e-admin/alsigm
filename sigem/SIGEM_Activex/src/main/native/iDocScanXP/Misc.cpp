
#include "StdAfx.h"
#include "Errs.h"
#include "iGral.h"
#include "Misc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocScanXP Misc                                                  //
//                                                                  //
//////////////////////////////////////////////////////////////////////

void IDOCSCANXSetErrorInfo(const CString& Class,const CString& Method,LONG Error)
{

   HRESULT           hRes = S_OK;
   CString           Src,Descr;
   ICreateErrorInfo* pCEI = NULL;
   IErrorInfo*       pEI  = NULL;
   BSTR              S,D;

   Src.Format("%s.%s","iDocScanX",Class);

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

LONG IDOCSCANXStringArrayToVariantArray(const CStringArray& StrArr, SAFEARRAY*& VarArr)
{

   LONG          Err = 0;
   CString       Aux;
   COleSafeArray SafeArray;
   COleVariant   String;
   ULONG         i, Count;
   LONG          Index[1] = { 0 };

   Count = StrArr.GetSize();
   if (Count == 0) goto End;

   SafeArray.Create(VT_BSTR, 1, &Count);
         
   for (i=0;i<Count;i++)
   {
      Index[0] = i;
      String = StrArr[i];
      SafeArray.PutElement(Index, String.bstrVal);
   }

   SafeArray.Copy(&VarArr);
   
   End:

   return(Err);

}

LONG IDOCSCANXCopyVariantArray(const CStringArray& Src,VARIANT* pDst)
{
   LONG             Err = 0;
   VARIANT          ValTmp;
   SAFEARRAYBOUND   Rgsa;
   LPSAFEARRAY      pSa = NULL;
   LONG             i, Elms = Src.GetSize();

   VariantInit(pDst);

   Rgsa.lLbound   = 0;
   Rgsa.cElements = Elms;
   pSa = SafeArrayCreate(VT_VARIANT, 1, &Rgsa);
   if (pSa == NULL)
   {      
      Err = IDOCSCANX_ERR_MEM;
      goto End;
   }

   for (i = 0; i < Elms; i++)
   {
      V_VT(&ValTmp)   = VT_BSTR;
      V_BSTR(&ValTmp) = Src.GetAt(i).AllocSysString();
      if (SafeArrayPutElement(pSa, &i, &ValTmp) != S_OK)
      {   
         Err = IDOCSCANX_ERR_MEM;
         goto End;
      }
      VariantClear(&ValTmp);
   }
   V_VT(pDst)    = VT_ARRAY | VT_VARIANT;
   V_ARRAY(pDst) = pSa;

   End:

   return(Err);
}