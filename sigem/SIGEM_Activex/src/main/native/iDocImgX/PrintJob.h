// PrintJob.h : Declaration of the CPrintJob

#ifndef __PRINTJOB_H_
#define __PRINTJOB_H_

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CPrintJob
class ATL_NO_VTABLE CPrintJob : 
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CPrintJob, &CLSID_PrintJob>,
	public ISupportErrorInfo,
	public IDispatchImpl<iPrintJob, &IID_iPrintJob, &LIBID_IDOCIMGXLib>
{
public:
	CPrintJob()
	{
      m_hDC = NULL;
      m_pDC = NULL;
	}

DECLARE_REGISTRY_RESOURCEID(IDR_PRINTJOB)

DECLARE_PROTECT_FINAL_CONSTRUCT()

BEGIN_COM_MAP(CPrintJob)
	COM_INTERFACE_ENTRY(iPrintJob)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
END_COM_MAP()

// ISupportsErrorInfo
	STDMETHOD(InterfaceSupportsErrorInfo)(REFIID riid)
	{
		static const IID* arr[] = 
		{
			&IID_iPrintJob,
		};
		for (int i=0; i<sizeof(arr)/sizeof(arr[0]); i++)
		{
         if (::InlineIsEqualGUID(*arr[i], riid))
				return S_OK;
		}
		return S_FALSE;
	}


// iPrintJob
   public:

   STDMETHOD(PrintFile)(/*[in]*/ BSTR FileName,/*[in]*/VARIANT_BOOL Auto);
   STDMETHOD(PrintFileWStamp)(/*[in]*/ BSTR FileName,/*[in]*/ BSTR Stamp,/*[in]*/VARIANT_BOOL Auto);
   STDMETHOD(InitializePrint)(/*[in]*/BSTR JobName);
   STDMETHOD(PagePrint)(/*[in]*/ BSTR FileName,/*[in]*/ BSTR Stamp,/*[in]*/VARIANT_BOOL Auto);
   STDMETHOD(PageNormalSizePrint)(/*[in]*/ BSTR FileName,/*[in]*/ BSTR Stamp);
   STDMETHOD(TerminatePrint)();

   STDMETHOD(PrintFileWTxt)(/*[in]*/ BSTR FileName,/*[in]*/ BSTR FileTxt,
             /*[in]*/VARIANT_BOOL Auto,/*[in]*/VARIANT_BOOL Up);

   protected:

   LONG URLDownLoad(CString& FileName);
   LONG GetFile(CString& szUrl,CString& Dest);
   void DelFiles();
   LONG PrintFile(CString File, BOOL Auto, CString Txt,CString NameFont,LONG Enh,LONG Tam);
   LONG InitPrint(CString JobName);
   LONG PagePrint(CString File,BOOL Auto,CString Txt,
                  CString NameFont,LONG Enh,LONG Tam,BOOL NormalSize = FALSE);
   LONG TermPrint();

   LONG PrintFileTxt(CString File, BOOL Auto, CStringArray& TxtArr,CString NameFont,LONG Enh,
                     LONG Tam, BOOL Up);

   BOOL CanNormalSize(HDC hDC, HIGEAR hIGear, CRect& ImgRect);

   protected:

   HDC          m_hDC;
   CDC*         m_pDC;
   CStringArray m_DelFilesArr;

};

#endif //__PRINTJOB_H_
