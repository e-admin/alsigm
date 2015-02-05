#ifndef __ERRORAUX_H_
#define __ERRORAUX_H_

#include "resource.h"       // main symbols
#include <atlctl.h>
#include <atlcom.h>


HRESULT RaiseException(int nID, REFGUID rguid);

#endif //__ERRORAUX_H_