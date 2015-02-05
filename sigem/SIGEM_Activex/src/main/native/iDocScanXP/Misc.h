
#ifndef __IDOCSCANXP_MISC_H__
#define __IDOCSCANXP_MISC_H__

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocScanXP Misc                                                  //
//                                                                  //
//////////////////////////////////////////////////////////////////////

void IDOCSCANXSetErrorInfo(const CString& Class,const CString& Method,LONG Error);
LONG IDOCSCANXCopyVariantArray(const CStringArray& Src,VARIANT* pDst);
LONG IDOCSCANXStringArrayToVariantArray(const CStringArray& StrArr, SAFEARRAY*& VarArr);

#endif // __IDOCSCANXP_MISC_H__

