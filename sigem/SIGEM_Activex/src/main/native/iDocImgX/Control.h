// Control.h : Declaration of the CControl

#ifndef __CONTROL_H_
#define __CONTROL_H_

#include "resource.h"       // main symbols
#include <atlctl.h>

////EVENTOS DEL CONTROL/////////////////////////////////////////////////////////

template <class T>
class CProxy_iControlEvents : public IConnectionPointImpl<T, &DIID__iControlEvents, CComDynamicUnkArray>
{
	//Warning this class may be recreated by the wizard.
public:
	HRESULT Fire_IsModifyNotes()
	{
		CComVariant varResult;
		T* pT = static_cast<T*>(this);
		int nConnectionIndex;
		int nConnections = m_vec.GetSize();
		
		for (nConnectionIndex = 0; nConnectionIndex < nConnections; nConnectionIndex++)
		{
			pT->Lock();
			CComPtr<IUnknown> sp = m_vec.GetAt(nConnectionIndex);
			pT->Unlock();
			IDispatch* pDispatch = reinterpret_cast<IDispatch*>(sp.p);
			if (pDispatch != NULL)
			{
				VariantClear(&varResult);
				DISPPARAMS disp = { NULL, NULL, 0, 0 };
				pDispatch->Invoke(0x1, IID_NULL, LOCALE_USER_DEFAULT, DISPATCH_METHOD, &disp, &varResult, NULL, NULL);
			}
		}
		return varResult.scode;
	
	}
	HRESULT Fire_IsSaveNotes()
	{
		CComVariant varResult;
		T* pT = static_cast<T*>(this);
		int nConnectionIndex;
		int nConnections = m_vec.GetSize();
		
		for (nConnectionIndex = 0; nConnectionIndex < nConnections; nConnectionIndex++)
		{
			pT->Lock();
			CComPtr<IUnknown> sp = m_vec.GetAt(nConnectionIndex);
			pT->Unlock();
			IDispatch* pDispatch = reinterpret_cast<IDispatch*>(sp.p);
			if (pDispatch != NULL)
			{
				VariantClear(&varResult);
				DISPPARAMS disp = { NULL, NULL, 0, 0 };
				pDispatch->Invoke(0x2, IID_NULL, LOCALE_USER_DEFAULT, DISPATCH_METHOD, &disp, &varResult, NULL, NULL);
			}
		}
		return varResult.scode;
	
	}
};

////////////////////////////////////////////////////////////////////////////////////////

class CMF;
/////////////////////////////////////////////////////////////////////////////
// CControl
class ATL_NO_VTABLE CControl : 
	public CComObjectRootEx<CComSingleThreadModel>,   
	public IDispatchImpl<IControl, &IID_IControl, &LIBID_IDOCIMGXLib>,
	public CComControl<CControl>,
	public IPersistStreamInitImpl<CControl>,
	public IOleControlImpl<CControl>,
	public IOleObjectImpl<CControl>,
	public IOleInPlaceActiveObjectImpl<CControl>,
	public IViewObjectExImpl<CControl>,
	public IOleInPlaceObjectWindowlessImpl<CControl>,
	public ISupportErrorInfo,
   public IConnectionPointContainerImpl<CControl>,
	public IPersistStorageImpl<CControl>,
	public ISpecifyPropertyPagesImpl<CControl>,
   public IPersistPropertyBagImpl<CControl>,
	public IQuickActivateImpl<CControl>,
	public IDataObjectImpl<CControl>,
	public IProvideClassInfo2Impl<&CLSID_Control, NULL, &LIBID_IDOCIMGXLib>,
   public IPropertyNotifySinkCP<CControl>,
	public CComCoClass<CControl, &CLSID_Control>,
	public CProxy_iControlEvents< CControl >
{
public:
	CControl()
	{
		m_bWindowOnly = TRUE;
//      m_bBorderVisible = FALSE;
      m_pMF         = NULL;
      m_FitMode = 3;
      m_Enhancement = 0;   
      m_EnablePrint = TRUE;
      m_EnableSaveAs = FALSE;
      m_ShowToolBar = FALSE;
      m_InitFit = FALSE;
      m_InitEnh = FALSE;
      m_InitTB = FALSE;
      m_InitRot = FALSE;
      m_Rotation = 0;
      m_EnableEditAnn = TRUE;
      m_Drag = FALSE;
	}
   
   virtual ~CControl();

DECLARE_REGISTRY_RESOURCEID(IDR_CONTROL)

DECLARE_PROTECT_FINAL_CONSTRUCT()

BEGIN_COM_MAP(CControl)
	COM_INTERFACE_ENTRY(IControl)
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
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
   COM_INTERFACE_ENTRY(IConnectionPointContainer)
	COM_INTERFACE_ENTRY(ISpecifyPropertyPages)
	COM_INTERFACE_ENTRY(IQuickActivate)
	COM_INTERFACE_ENTRY(IPersistStorage)
   COM_INTERFACE_ENTRY(IPersistPropertyBag)
	COM_INTERFACE_ENTRY(IDataObject)
	COM_INTERFACE_ENTRY(IProvideClassInfo)
	COM_INTERFACE_ENTRY(IProvideClassInfo2)
	COM_INTERFACE_ENTRY_IMPL(IConnectionPointContainer)
END_COM_MAP()

BEGIN_PROP_MAP(CControl)
	PROP_DATA_ENTRY("_cx", m_sizeExtent.cx, VT_UI4)
	PROP_DATA_ENTRY("_cy", m_sizeExtent.cy, VT_UI4)
   PROP_ENTRY("FileName", 1, CLSID_NULL)
   PROP_ENTRY("FitMode", 6, CLSID_NULL)
   PROP_ENTRY("Enhancement", 8, CLSID_NULL)      
   PROP_ENTRY("ShowToolbar", 10, CLSID_NULL)   
   PROP_ENTRY("EnablePrint", 9, CLSID_NULL)   
   PROP_ENTRY("Rotation", 11, CLSID_NULL)   
   PROP_ENTRY("FileNote",12,CLSID_NULL)
   PROP_ENTRY("EnableSaveAs",17,CLSID_NULL)
   PROP_ENTRY("EnableEditAnn",18,CLSID_NULL)
   PROP_ENTRY("Drag",28,CLSID_NULL)
	// Example entries
	// PROP_ENTRY("Property Description", dispid, clsid)
	// PROP_PAGE(CLSID_StockColorPage)
END_PROP_MAP()


BEGIN_CONNECTION_POINT_MAP(CControl)
	CONNECTION_POINT_ENTRY(IID_IPropertyNotifySink)
	CONNECTION_POINT_ENTRY(DIID__iControlEvents)
END_CONNECTION_POINT_MAP()


BEGIN_MSG_MAP(CControl)
	CHAIN_MSG_MAP(CComControl<CControl>)
	DEFAULT_REFLECTION_HANDLER()
	MESSAGE_HANDLER(WM_CREATE, OnCreate)
	MESSAGE_HANDLER(WM_DESTROY, OnDestroy)
	MESSAGE_HANDLER(WM_SIZE, OnSize)
   MESSAGE_HANDLER(WM_MOUSEACTIVATE, OnMouseActivate)
   MESSAGE_HANDLER(WM_SETFOCUS, OnSetFocus)  
END_MSG_MAP()
// Handler prototypes:
//  LRESULT MessageHandler(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
//  LRESULT CommandHandler(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
//  LRESULT NotifyHandler(int idCtrl, LPNMHDR pnmh, BOOL& bHandled);



// ISupportsErrorInfo
	STDMETHOD(InterfaceSupportsErrorInfo)(REFIID riid)
	{
		static const IID* arr[] = 
		{
			&IID_IControl,
		};
		for (int i=0; i<sizeof(arr)/sizeof(arr[0]); i++)
		{
         if (::InlineIsEqualGUID(*arr[i], riid))
				return S_OK;
		}
		return S_FALSE;
	}

// IViewObjectEx
	DECLARE_VIEW_STATUS(VIEWSTATUS_SOLIDBKGND | VIEWSTATUS_OPAQUE)

// IControl
public:	
  
   STDMETHOD(TranslateAccelerator)(MSG *pMsg);
   
	STDMETHOD(put_ShowToolbar)(/*[in]*/ VARIANT_BOOL newVal);
   STDMETHOD(get_ShowToolbar)(/*[out, retval]*/ VARIANT_BOOL *pVal);
	STDMETHOD(put_EnablePrint)(/*[in]*/ VARIANT_BOOL newVal);
   STDMETHOD(get_EnablePrint)(/*[out, retval]*/ VARIANT_BOOL *pVal);
	STDMETHOD(get_Enhancement)(/*[out, retval]*/ short *pVal);
	STDMETHOD(put_Enhancement)(/*[in]*/ short newVal);
	STDMETHOD(get_Zoom)(/*[out, retval]*/ double *pVal);
	STDMETHOD(put_Zoom)(/*[in]*/ double newVal);
	STDMETHOD(get_FitMode)(/*[out, retval]*/ short *pVal);
	STDMETHOD(put_FitMode)(/*[in]*/ short newVal);
	STDMETHOD(Clear)();
	STDMETHOD(get_PageCount)(/*[out, retval]*/ long *pVal);
	STDMETHOD(get_Page)(/*[out, retval]*/ long *pVal);
	STDMETHOD(put_Page)(/*[in]*/ long newVal);
	STDMETHOD(Display)();
	STDMETHOD(get_FileName)(/*[out, retval]*/ BSTR *pVal);
	STDMETHOD(put_FileName)(/*[in]*/ BSTR newVal);
   STDMETHOD(get_Rotation)(/*[out, retval]*/ short *pVal);
	STDMETHOD(put_Rotation)(/*[in]*/ short newVal);
   STDMETHOD(get_FileNote)(/*[out, retval]*/ BSTR *pVal);
   STDMETHOD(put_FileNote)(/*[in]*/ BSTR newVal);
   STDMETHOD(put_EnableSaveAs)(/*[in]*/ VARIANT_BOOL newVal);
   STDMETHOD(get_EnableSaveAs)(/*[out, retval]*/ VARIANT_BOOL *pVal);
   STDMETHOD(put_EnableEditAnn)(/*[in]*/ VARIANT_BOOL newVal);
   STDMETHOD(get_EnableEditAnn)(/*[out, retval]*/ VARIANT_BOOL *pVal);
   STDMETHOD(put_Drag)(/*[in]*/ VARIANT_BOOL newVal);
   STDMETHOD(get_Drag)(/*[out, retval]*/ VARIANT_BOOL *pVal);   
   STDMETHOD(SaveNotes)();
   STDMETHOD(IsNotesModified)(/*[out, retval]*/ VARIANT_BOOL* Modified);
   STDMETHOD(HasAnyNotesModified)(/*[out, retval]*/ VARIANT_BOOL* Modified);
   STDMETHOD(SaveNotesToFile)(/*[in]*/ BSTR FileName);
   STDMETHOD(ExistsSelection)(/*[out, retval]*/ VARIANT_BOOL* Exists);
   STDMETHOD(SaveSelectionToFile)(/*[in]*/ BSTR FileName);
   STDMETHOD(get_ImgWidth)(/*[in]*/ long * pVal);
   STDMETHOD(get_ImgHeight)(/*[in]*/ long * pVal);
   STDMETHOD(SaveFileWithRotation)(/*[in]*/ BSTR FileName); 
   STDMETHOD(SaveFile)(/*[out, retval]*/ BSTR *pVal); 
   STDMETHOD(ExtractPagesToFile)(/*[in]*/ BSTR FileName, /*[in]*/ BSTR Pages,/*[out, retval]*/ BSTR *pVal);
   STDMETHOD(JoinFiles)(/*[in]*/ BSTR FileNameFirst, /*[in]*/ BSTR FileNameSecond,/*[out, retval]*/ BSTR *pVal);
   STDMETHOD(DeletePages)(/*[in]*/ BSTR FileName, /*[in]*/ BSTR Pages);
	HRESULT OnDraw(ATL_DRAWINFO& di);

   void EventModifyNotes();
   void EventSaveNotes();   

   protected:
	
	LRESULT OnCreate(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
	LRESULT OnDestroy(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
   LRESULT OnSize(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);

   LRESULT OnMouseActivate(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);	
	LRESULT OnSetFocus(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);

  
   LONG ExtractPages(CString FileOrg, CString Pages, CString& FileDst);
   LONG Join(CString FileFirst, CString FileSecond, CString& FileDst);
   LONG DeletePagesFromFile(CString File, CString Pages);
   LONG AddPagesFromFile(CString File, CString FileDst);
   LONG GetPages(CString Pages, CDWordArray& PGS);
   BOOL HasDelPage(int page, CDWordArray& Pages);
   BOOL URLDownLoad(CString& FileName);
   BOOL GetFile(CString& szUrl,CString& Dest);
   void InitParams();

   protected:

   CMF*    m_pMF;

   public:

   CString m_FileName;
   CString m_FileNote;
   short   m_FitMode;   
   short   m_Enhancement;
   BOOL    m_EnablePrint;
   BOOL    m_EnableSaveAs;
   BOOL    m_ShowToolBar;	
   BOOL    m_InitTB;
   BOOL    m_InitRot;
   BOOL    m_InitFit;
   BOOL    m_InitEnh;
   short   m_Rotation;
   CStringArray m_DelFileArr;
   BOOL    m_EnableEditAnn;
   BOOL    m_Drag;

   protected:

   BOOL IsUserMode();
		
};




#endif //__CONTROL_H_
