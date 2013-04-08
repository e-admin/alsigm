// Scanner.h : Declaration of the CScanner

#ifndef __SCANNER_H_
#define __SCANNER_H_

#include "resource.h"       // main symbols

class CiDocScanMgr;

/////////////////////////////////////////////////////////////////////////////
// CScanner
class ATL_NO_VTABLE CScanner : 
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CScanner, &CLSID_Scanner>,
	public ISupportErrorInfo,
	public IDispatchImpl<IScanner, &IID_IScanner, &LIBID_IDocScanXPLib>
{
   public:
	CScanner();
	virtual ~CScanner();
   void FinalRelease();
  

	STDMETHOD(InterfaceSupportsErrorInfo)(REFIID riid);


DECLARE_REGISTRY_RESOURCEID(IDR_SCANNER)
DECLARE_PROTECT_FINAL_CONSTRUCT()


BEGIN_COM_MAP(CScanner)
	COM_INTERFACE_ENTRY(IScanner)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
END_COM_MAP()

// IScanner
   public:
	
   STDMETHOD(NewFiles)(LONG PagesToScan, VARIANT* Files);
   STDMETHOD(UIUpdateFile)(BSTR File, VARIANT_BOOL* Updated);
   STDMETHOD(UINewFiles)(VARIANT* Files);
   STDMETHOD(UINewFilesToPath)(BSTR Path, VARIANT* Files);
   STDMETHOD(UIConfig)(VARIANT_BOOL StdDlg, VARIANT_BOOL InitCapture);
   STDMETHOD(Configure)();
   STDMETHOD(Initialize)();
   STDMETHOD(ConfigureScan)();
   STDMETHOD(InitializeX)(LONG hWnd);
   STDMETHOD(SelectDevice)(BSTR Name);
   STDMETHOD(SelectConfiguration)(BSTR Name);
   STDMETHOD(Terminate)();
   STDMETHOD(GetNumPages)(BSTR File, LONG* NumPages);
   STDMETHOD(UINewFilesX)(LONG NumPages, VARIANT* Files);
   STDMETHOD(NewFilesToPath)(BSTR Path,LONG PagesToScan, VARIANT* Files);

   private:

   CiDocScanMgr* m_pScanMgr;
   CFrameWnd*    m_pFrameWnd;
   BOOL          m_Init;
};

LONG CopyFileToPath(CStringArray& OrgFiles, CStringArray& DstFiles, CString Path);

#endif //__SCANNER_H_
