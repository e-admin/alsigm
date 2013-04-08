// IdocLDAP.h : Declaration of the CIdocLDAP

#ifndef __IDOCLDAP_H_
#define __IDOCLDAP_H_

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CIdocLDAP
class ATL_NO_VTABLE CIdocLDAP : 
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CIdocLDAP, &CLSID_IdocLDAP>,
	public ISupportErrorInfo,
	public IDispatchImpl<IIdocLDAP, &IID_IIdocLDAP, &LIBID_IDOCLDAPCLIENTLib>
{
public:
	CIdocLDAP()
	{
	}

DECLARE_REGISTRY_RESOURCEID(IDR_IDOCLDAP)
DECLARE_NOT_AGGREGATABLE(CIdocLDAP)

DECLARE_PROTECT_FINAL_CONSTRUCT()

BEGIN_COM_MAP(CIdocLDAP)
	COM_INTERFACE_ENTRY(IIdocLDAP)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
END_COM_MAP()

// ISupportsErrorInfo
	STDMETHOD(InterfaceSupportsErrorInfo)(REFIID riid);

// IIdocLDAP
public:
   // Devuelve el UserDn
   STDMETHOD(GetUserDn)(BSTR* pUserDn);

   // Devuelve el UserDn codificado
   STDMETHOD(GetUserDnEncode)(BSTR* pUserDn);

private:
   // Nos dice si el S.O. soporta LDAP
   BOOL CIdocLDAP::IsSystemVersionForLdap();

   // Consigue el UserDn para un windows NT
   LONG CIdocLDAP::GetUserDn9xNT(CString& Dn);

   // Consigue el UserDn para un windows 2000
   LONG CIdocLDAP::GetUserDn2000(CString& Dn);

   // Consigue el UserDn del usuario conectado
   LONG CIdocLDAP::GetUserDnEx(CString& Dn);

   // codifica un string
   LONG CIdocLDAP::Encode(const CString& sValIn, CString& sValOut);
};

#endif //__IDOCLDAP_H_
