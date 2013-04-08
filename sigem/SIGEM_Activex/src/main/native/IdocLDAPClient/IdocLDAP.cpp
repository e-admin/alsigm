// IdocLDAP.cpp : Implementation of CIdocLDAP
#include "stdafx.h"
#include "IdocLDAPClient.h"
#include "IdocLDAP.h"
#include "Ntdsapi.h"
#include "Security.h"
#include "Winbase.h"


/////////////////////////////////////////////////////////////////////////////
// CIdocLDAP

#define     IDOCLDAP_ERR_GET_USER_DN       9026
#define     IDOCLDAP_ERR_CODEC_ENCODE     23001

STDMETHODIMP CIdocLDAP::InterfaceSupportsErrorInfo(REFIID riid)
{
	static const IID* arr[] = 
	{
		&IID_IIdocLDAP
	};
	for (int i=0; i < sizeof(arr) / sizeof(arr[0]); i++)
	{
      if (::InlineIsEqualGUID(*arr[i],riid))
			return S_OK;
	}
	return S_FALSE;
}

STDMETHODIMP CIdocLDAP::GetUserDn(BSTR* pUserDn)
{
   CString        sUserDn   = "";
   LONG           lnErrorCode  = 0;
   LONG           lnMsgId      = 0;
   CString        strErrDescription = "";

   try
   {
      lnErrorCode = GetUserDnEx(sUserDn);
      if (lnErrorCode != 0)
      {
         lnMsgId = IDS_ERR_GET_USER_DN;
      }
      *pUserDn = sUserDn.AllocSysString();
   }
   catch(...)
   {
      lnErrorCode = IDOCLDAP_ERR_GET_USER_DN;
      lnMsgId = IDS_ERR_GET_USER_DN;
   }
   
   if (lnErrorCode == 0)
   {
      return S_OK;
   }
   else
   {
      CreateError(this->GetObjectDescription(), "LoginLDAP", lnErrorCode, lnMsgId, strErrDescription);
      return MAKE_HRESULT(SEVERITY_ERROR, FACILITY_ITF,(lnErrorCode + 0x200));
   }
}

STDMETHODIMP CIdocLDAP::GetUserDnEncode(BSTR* pUserDn)
{
   CString        sUserDn   = "";
   CString        sUserDnEncode = "";
   LONG           lnErrorCode  = 0;
   LONG           lnMsgId      = 0;
   CString        strErrDescription = "";

   try
   {
      lnErrorCode = GetUserDnEx(sUserDn);
      if (lnErrorCode != 0)
      {
         lnMsgId = IDS_ERR_GET_USER_DN;
      }
      Encode(sUserDn, sUserDnEncode);
      *pUserDn = sUserDnEncode.AllocSysString();
   }
   catch(...)
   {
      lnErrorCode = IDOCLDAP_ERR_GET_USER_DN;
      lnMsgId = IDS_ERR_GET_USER_DN;
   }
   
   if (lnErrorCode == 0)
   {
      return S_OK;
   }
   else
   {
      CreateError(this->GetObjectDescription(), "LoginLDAP", lnErrorCode, lnMsgId, strErrDescription);
      return MAKE_HRESULT(SEVERITY_ERROR, FACILITY_ITF,(lnErrorCode + 0x200));
   }
}

static char sTbCodes[] = "-*0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

LONG CIdocLDAP::Encode(const CString& sValIn, CString& sValOut)
{

	CString s;
   LONG lnErrorCode = 0;
	
	PCSTR sStr = (PCSTR) sValIn;

	int n,oMsk = 0x80;
	bool bSigue = true;
	try
	{
		while (bSigue)
		{
			n = 0;
			for (int nBit=0 ; nBit < 6 ; nBit++)
			{
				n <<= 1;
				n |= (*sStr & oMsk) ? 1 : 0;

				oMsk >>= 1;
				if (!oMsk)
				{	// Siguiente caracter
					if (*++sStr == '\0')
						bSigue = false;
					oMsk = 0x80;
				}
			}
			s += sTbCodes[n];
		}
		//s += '\0';

		sValOut = s;
	}
   catch ( CException* e )
   {
      lnErrorCode = IDOCLDAP_ERR_CODEC_ENCODE;
	  e->Delete();
   }


	return S_OK;
}


LONG CIdocLDAP::GetUserDnEx(CString& Dn)
{

   Dn.Empty();

   //no se soporta en esta versión LDAP para 98/95 pues es necesario
   //tener instalado Active Directory Client Extensión.
   if ( IsSystemVersionForLdap())
   {
      return GetUserDn2000(Dn);
   }
   else
   {
      return GetUserDn9xNT(Dn);
   }
}

LONG CIdocLDAP::GetUserDn2000(CString& Dn)
{
   LONG Err = 0;
   EXTENDED_NAME_FORMAT Format    = NameFullyQualifiedDN; 
   TCHAR                Buf[500];  
   ULONG                BufLen    = 500;

   if (!GetUserNameEx(Format,Buf,&BufLen))
   {
      Err = IDOCLDAP_ERR_GET_USER_DN;
   }
   else
   {
      Dn = CString(Buf);
   }
   
   return(Err);
}

LONG CIdocLDAP::GetUserDn9xNT(CString& Dn)
{
   LONG                 Err = 0;
   HANDLE               hDS = NULL;
   PDS_NAME_RESULT      pResult = NULL;
   CString              UserName;
   TCHAR                Buf[500];
   ULONG                BufLen = 500;
   CString              Name;
   PDS_NAME_RESULT_ITEM pItem;

   if (!::GetUserName(Buf,&BufLen))
   {
      Err = IDOCLDAP_ERR_GET_USER_DN; 
   }
   else
   {
      UserName = Buf;
      
      Err = DsBind(NULL,NULL,&hDS);
      if (Err)
      { 
         Err = IDOCLDAP_ERR_GET_USER_DN;
      }
      else
      {
         Err = DsCrackNames(hDS,DS_NAME_NO_FLAGS,DS_DISPLAY_NAME,DS_FQDN_1779_NAME,
                  1,(LPTSTR*)&UserName,&pResult);
         if (Err) 
         { 
            Err = IDOCLDAP_ERR_GET_USER_DN;
         }
         else
         {
            pItem = pResult->rItems;
            if (pItem->status == DS_NAME_NO_ERROR)
            {
               Dn = CString(pItem->pName); 
            }
            else
            {
               Err = pItem->status;
            }
         }
      }
   }
  
   if (pResult != NULL)
   {
      DsFreeNameResult(pResult);
   }
   
   if (hDS != NULL)
   {
      DsUnBind(&hDS);
   }
  
   return(Err);
}

BOOL CIdocLDAP::IsSystemVersionForLdap()
{
   OSVERSIONINFO vi; 

   ZeroMemory(&vi, sizeof(OSVERSIONINFO));
   vi.dwOSVersionInfoSize = sizeof(OSVERSIONINFO);

   if ( !GetVersionEx(&vi) )
   {
      return FALSE;   
   }
   else
   {
      if (vi.dwMajorVersion < 5)
      {
         return FALSE;
      }
      else
      {
         return TRUE;
      }
   }

}