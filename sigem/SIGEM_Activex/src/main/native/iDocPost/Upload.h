// Upload.h : Declaration of the CUpload

#ifndef __UPLOAD_H_
#define __UPLOAD_H_

#include "resource.h"       // main symbols
#include <atlctl.h>


/////////////////////////////////////////////////////////////////////////////
// CUpload
class ATL_NO_VTABLE CUpload : 
	public CComObjectRootEx<CComSingleThreadModel>,
	public CStockPropImpl<CUpload, IUpload, &IID_IUpload, &LIBID_IDOCPOSTLib>,
	public CComControl<CUpload>,
	public IPersistStreamInitImpl<CUpload>,
	public IOleControlImpl<CUpload>,
	public IOleObjectImpl<CUpload>,
	public IOleInPlaceActiveObjectImpl<CUpload>,
	public IViewObjectExImpl<CUpload>,
	public IOleInPlaceObjectWindowlessImpl<CUpload>,
	public CComCoClass<CUpload, &CLSID_Upload>
{
public:
	CUpload();
	~CUpload();
  
  CString m_strBasicProxyAuth;
  long SendFile(HINTERNET hInetReq, LPCTSTR FileName);

// CRM
DECLARE_AGGREGATABLE(CUpload)
DECLARE_GET_CONTROLLING_UNKNOWN()


DECLARE_REGISTRY_RESOURCEID(IDR_UPLOAD)

DECLARE_PROTECT_FINAL_CONSTRUCT()

BEGIN_COM_MAP(CUpload)
	COM_INTERFACE_ENTRY(IUpload)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(IViewObjectEx)
	COM_INTERFACE_ENTRY(IViewObject2)
	COM_INTERFACE_ENTRY(IViewObject)
	COM_INTERFACE_ENTRY(IOleInPlaceObjectWindowless)
	COM_INTERFACE_ENTRY(IOleInPlaceObject)
	COM_INTERFACE_ENTRY2(IOleWindow, IOleInPlaceObjectWindowless)
	COM_INTERFACE_ENTRY(IOleInPlaceActiveObject)
	COM_INTERFACE_ENTRY(IOleControl)
	COM_INTERFACE_ENTRY(IOleObject)
	COM_INTERFACE_ENTRY(IPersistStreamInit)
	COM_INTERFACE_ENTRY2(IPersist, IPersistStreamInit)
END_COM_MAP()

BEGIN_PROP_MAP(CUpload)
	PROP_DATA_ENTRY("_cx", m_sizeExtent.cx, VT_UI4)
	PROP_DATA_ENTRY("_cy", m_sizeExtent.cy, VT_UI4)
	// Example entries
	// PROP_ENTRY("Property Description", dispid, clsid)
	// PROP_PAGE(CLSID_StockColorPage)
END_PROP_MAP()

BEGIN_MSG_MAP(CUpload)
	CHAIN_MSG_MAP(CComControl<CUpload>)
	DEFAULT_REFLECTION_HANDLER()
END_MSG_MAP()
// Handler prototypes:
//  LRESULT MessageHandler(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
//  LRESULT CommandHandler(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
//  LRESULT NotifyHandler(int idCtrl, LPNMHDR pnmh, BOOL& bHandled);



// IViewObjectEx
	DECLARE_VIEW_STATUS(VIEWSTATUS_SOLIDBKGND | VIEWSTATUS_OPAQUE)

// IUpload
public:
	STDMETHOD(GETFile)(/*[in]*/ BSTR URL, /*[in]*/ BSTR LocalFile, /*[out, retval]*/ VARIANT *pErrorCode);
	STDMETHOD(PUTFile)(/*[in]*/ BSTR File, /*[in]*/ BSTR URL, /*[out, retval]*/ VARIANT *pErrorCode);
	STDMETHOD(GetTempPath)(/*[out]*/ VARIANT *pTempPath);
	STDMETHOD(DeleteFile)(/*[in]*/ BSTR FileName);
	STDMETHOD(CallURL)(/*[in]*/ BSTR URL, /*[out]*/ VARIANT *pResponse, /*[out, retval]*/ VARIANT *pErrorCode);
	STDMETHOD(DownloadFile)(BSTR URL, BSTR LocalPath, VARIANT* pFileLoc, VARIANT* pErrorCode);
	STDMETHOD(SelectFile)(long bSaving, VARIANT* pFileName);
	STDMETHOD(SelectDir)(VARIANT* pDir);
	STDMETHOD(UploadFile)(BSTR FileName, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode);
   STDMETHOD(UploadFileX)(BSTR FileName, BSTR Server, BSTR Page, BSTR VirtualDir, 
                          BSTR DstFileName, long bRemove, VARIANT* pErrorCode);
   STDMETHOD(UploadFileM)(BSTR FileName, BSTR Server, BSTR Page, BSTR VirtualDir, 
                          BSTR DstFileName, long bRemove, VARIANT* pErrorCode);
   
	HRESULT OnDraw(ATL_DRAWINFO& /*di*/)
	{
		return(S_OK);
	}
};

#endif //__UPLOAD_H_
