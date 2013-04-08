// Error.h: interface for the Error class.
//
//////////////////////////////////////////////////////////////////////
#ifndef __ERROR_H_
#define __ERROR_H_

#include <atlbase.h>

class CComModuleErr : public CComModule
{
public:
   CString  m_ServerName;
   LONG     m_LastError;
};


void SetErrorInfo(const char* Class, const char* Method, 
                   LONG ErrorCode, const char* Description, CComModuleErr& Module);

// crea un errorinfo con errorcode, la descripcion que se corresponde con el id de recurso lnIdDescription
// si lnIdDescription es 0 --> la descripcion es strDescription
void CreateError(const char* Class, const char* Method, long lnErrorCode, long lnIdDescription, const char* strDescription);

#endif // !defined(AFX_ERROR_H__83301883_F592_47F0_AD0B_45265C3D6B91__INCLUDED_)
