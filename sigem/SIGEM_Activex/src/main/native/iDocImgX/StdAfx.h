// stdafx.h : include file for standard system include files,
//      or project specific include files that are used frequently,
//      but are changed infrequently

#if !defined(AFX_STDAFX_H__24C6D594_6D0D_11D4_8128_00C0F049167F__INCLUDED_)
#define AFX_STDAFX_H__24C6D594_6D0D_11D4_8128_00C0F049167F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#define STRICT
#ifndef _WIN32_WINNT
#define _WIN32_WINNT 0x0400
#endif
#define _ATL_APARTMENT_THREADED

#include <afxwin.h>
#include <afxdisp.h>

#include <atlbase.h>

class CiDocImgXModule : public CComModule
{

   public:

   HINSTANCE m_hModGear;

};

extern CiDocImgXModule _Module;

#include <atlcom.h>
#include <atlctl.h>
#include <afxext.h>
#include <afxcmn.h>


#ifndef EXTERN 
#define EXTERN extern
#endif   


//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_STDAFX_H__24C6D594_6D0D_11D4_8128_00C0F049167F__INCLUDED)
