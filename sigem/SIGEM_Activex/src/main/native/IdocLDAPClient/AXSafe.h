
#ifndef __AXSAFE_H__
#define __AXSAFE_H__

#include <comcat.h>

HRESULT CreateComponentCategory(CATID Id, WCHAR* Description);
HRESULT RegisterCLSIDInCategory(REFCLSID clsid, CATID Id);
HRESULT UnRegisterCLSIDInCategory(REFCLSID clsid, CATID Id);

#endif   // __AXSAFE_H__
