
#include "stdafx.h"
#include "AXSafe.h"

HRESULT CreateComponentCategory(CATID Id, WCHAR* Description)
{
   ICatRegister* pCat = NULL;
   HRESULT       Res  = S_OK;

   Res = CoCreateInstance(CLSID_StdComponentCategoriesMgr, NULL, CLSCTX_INPROC_SERVER, 
                          IID_ICatRegister, (void**)&pCat);
   if (FAILED(Res))
     return(Res);

   CATEGORYINFO CatInfo;
   CatInfo.catid = Id;
   CatInfo.lcid  = GetSystemDefaultLCID();

   // Copiamos solamente los 127 primeros caracteres
   int len = wcslen(Description);
   if (len > 127)
     len = 127;

   wcsncpy(CatInfo.szDescription, Description, len);
   CatInfo.szDescription[len] = '\0';

   Res = pCat->RegisterCategories(1, &CatInfo);
   pCat->Release();

   return(Res);
}


HRESULT RegisterCLSIDInCategory(REFCLSID clsid, CATID Id)
{
   ICatRegister* pCat = NULL;
   HRESULT       Res  = S_OK;

   Res = CoCreateInstance(CLSID_StdComponentCategoriesMgr, NULL, CLSCTX_INPROC_SERVER, 
                          IID_ICatRegister, (void**)&pCat);
   if (SUCCEEDED(Res))
   {
      CATID Cat[1];
      Cat[0] = Id;
      Res = pCat->RegisterClassImplCategories(clsid, 1, Cat);
   }

   if (pCat != NULL)
      pCat->Release();

   return(Res);
}

HRESULT UnRegisterCLSIDInCategory(REFCLSID clsid, CATID Id)
{
   ICatRegister* pCat = NULL;
   HRESULT       Res  = S_OK;

   Res = CoCreateInstance(CLSID_StdComponentCategoriesMgr, NULL, CLSCTX_INPROC_SERVER, 
                          IID_ICatRegister, (void**)&pCat);
   if (SUCCEEDED(Res))
   {
      CATID Cat[1];
      Cat[0] = Id;
      Res = pCat->UnRegisterClassImplCategories(clsid, 1, Cat);
   }

   if (pCat != NULL)
      pCat->Release();

   return(Res);
}

