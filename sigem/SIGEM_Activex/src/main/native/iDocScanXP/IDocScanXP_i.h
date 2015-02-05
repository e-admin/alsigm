

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 6.00.0366 */
/* at Fri Jun 29 12:02:26 2007
 */
/* Compiler settings for .\IDocScanXP.idl:
    Oicf, W1, Zp8, env=Win32 (32b run)
    protocol : dce , ms_ext, c_ext, robust
    error checks: allocation ref bounds_check enum stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
//@@MIDL_FILE_HEADING(  )

#pragma warning( disable: 4049 )  /* more than 64k source lines */


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__


#ifndef __IDocScanXP_i_h__
#define __IDocScanXP_i_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef __IScanner_FWD_DEFINED__
#define __IScanner_FWD_DEFINED__
typedef interface IScanner IScanner;
#endif 	/* __IScanner_FWD_DEFINED__ */


#ifndef __Scanner_FWD_DEFINED__
#define __Scanner_FWD_DEFINED__

#ifdef __cplusplus
typedef class Scanner Scanner;
#else
typedef struct Scanner Scanner;
#endif /* __cplusplus */

#endif 	/* __Scanner_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"

#ifdef __cplusplus
extern "C"{
#endif 

void * __RPC_USER MIDL_user_allocate(size_t);
void __RPC_USER MIDL_user_free( void * ); 


#ifndef __IDocScanXPLib_LIBRARY_DEFINED__
#define __IDocScanXPLib_LIBRARY_DEFINED__

/* library IDocScanXPLib */
/* [helpstring][version][uuid] */ 


DEFINE_GUID(LIBID_IDocScanXPLib,0xC78C8DB9,0xF105,0x470C,0xBC,0x81,0x96,0xA3,0xE8,0x45,0x3A,0xC9);

#ifndef __IScanner_INTERFACE_DEFINED__
#define __IScanner_INTERFACE_DEFINED__

/* interface IScanner */
/* [unique][helpstring][dual][uuid][object] */ 


DEFINE_GUID(IID_IScanner,0x6E8DBAA7,0xA128,0x4034,0xB5,0x20,0x09,0x82,0x76,0xAF,0xF0,0xB7);

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("6E8DBAA7-A128-4034-B520-098276AFF0B7")
    IScanner : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Initialize( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Configure( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UINewFiles( 
            /* [retval][out] */ VARIANT *Files) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UIUpdateFile( 
            /* [in] */ BSTR File,
            /* [retval][out] */ VARIANT_BOOL *Updated) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE NewFiles( 
            /* [in] */ LONG PagesToScan,
            /* [retval][out] */ VARIANT *Files) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ConfigureScan( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UINewFilesToPath( 
            /* [in] */ BSTR Path,
            /* [retval][out] */ VARIANT *Files) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE InitializeX( 
            /* [in] */ LONG hWnd) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SelectDevice( 
            /* [in] */ BSTR Name) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Terminate( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SelectConfiguration( 
            /* [in] */ BSTR Name) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UIConfig( 
            /* [in] */ VARIANT_BOOL StdDlg,
            /* [in] */ VARIANT_BOOL InitCapture) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetNumPages( 
            /* [in] */ BSTR File,
            /* [retval][out] */ LONG *NumPages) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UINewFilesX( 
            /* [in] */ LONG NumPages,
            /* [retval][out] */ VARIANT *Files) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE NewFilesToPath( 
            /* [in] */ BSTR Path,
            /* [in] */ LONG PagesToScan,
            /* [retval][out] */ VARIANT *Files) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IScannerVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IScanner * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IScanner * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IScanner * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IScanner * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IScanner * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IScanner * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IScanner * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Initialize )( 
            IScanner * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Configure )( 
            IScanner * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UINewFiles )( 
            IScanner * This,
            /* [retval][out] */ VARIANT *Files);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UIUpdateFile )( 
            IScanner * This,
            /* [in] */ BSTR File,
            /* [retval][out] */ VARIANT_BOOL *Updated);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *NewFiles )( 
            IScanner * This,
            /* [in] */ LONG PagesToScan,
            /* [retval][out] */ VARIANT *Files);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *ConfigureScan )( 
            IScanner * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UINewFilesToPath )( 
            IScanner * This,
            /* [in] */ BSTR Path,
            /* [retval][out] */ VARIANT *Files);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *InitializeX )( 
            IScanner * This,
            /* [in] */ LONG hWnd);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SelectDevice )( 
            IScanner * This,
            /* [in] */ BSTR Name);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Terminate )( 
            IScanner * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SelectConfiguration )( 
            IScanner * This,
            /* [in] */ BSTR Name);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UIConfig )( 
            IScanner * This,
            /* [in] */ VARIANT_BOOL StdDlg,
            /* [in] */ VARIANT_BOOL InitCapture);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *GetNumPages )( 
            IScanner * This,
            /* [in] */ BSTR File,
            /* [retval][out] */ LONG *NumPages);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UINewFilesX )( 
            IScanner * This,
            /* [in] */ LONG NumPages,
            /* [retval][out] */ VARIANT *Files);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *NewFilesToPath )( 
            IScanner * This,
            /* [in] */ BSTR Path,
            /* [in] */ LONG PagesToScan,
            /* [retval][out] */ VARIANT *Files);
        
        END_INTERFACE
    } IScannerVtbl;

    interface IScanner
    {
        CONST_VTBL struct IScannerVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IScanner_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define IScanner_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define IScanner_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define IScanner_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define IScanner_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define IScanner_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define IScanner_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define IScanner_Initialize(This)	\
    (This)->lpVtbl -> Initialize(This)

#define IScanner_Configure(This)	\
    (This)->lpVtbl -> Configure(This)

#define IScanner_UINewFiles(This,Files)	\
    (This)->lpVtbl -> UINewFiles(This,Files)

#define IScanner_UIUpdateFile(This,File,Updated)	\
    (This)->lpVtbl -> UIUpdateFile(This,File,Updated)

#define IScanner_NewFiles(This,PagesToScan,Files)	\
    (This)->lpVtbl -> NewFiles(This,PagesToScan,Files)

#define IScanner_ConfigureScan(This)	\
    (This)->lpVtbl -> ConfigureScan(This)

#define IScanner_UINewFilesToPath(This,Path,Files)	\
    (This)->lpVtbl -> UINewFilesToPath(This,Path,Files)

#define IScanner_InitializeX(This,hWnd)	\
    (This)->lpVtbl -> InitializeX(This,hWnd)

#define IScanner_SelectDevice(This,Name)	\
    (This)->lpVtbl -> SelectDevice(This,Name)

#define IScanner_Terminate(This)	\
    (This)->lpVtbl -> Terminate(This)

#define IScanner_SelectConfiguration(This,Name)	\
    (This)->lpVtbl -> SelectConfiguration(This,Name)

#define IScanner_UIConfig(This,StdDlg,InitCapture)	\
    (This)->lpVtbl -> UIConfig(This,StdDlg,InitCapture)

#define IScanner_GetNumPages(This,File,NumPages)	\
    (This)->lpVtbl -> GetNumPages(This,File,NumPages)

#define IScanner_UINewFilesX(This,NumPages,Files)	\
    (This)->lpVtbl -> UINewFilesX(This,NumPages,Files)

#define IScanner_NewFilesToPath(This,Path,PagesToScan,Files)	\
    (This)->lpVtbl -> NewFilesToPath(This,Path,PagesToScan,Files)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_Initialize_Proxy( 
    IScanner * This);


void __RPC_STUB IScanner_Initialize_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_Configure_Proxy( 
    IScanner * This);


void __RPC_STUB IScanner_Configure_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_UINewFiles_Proxy( 
    IScanner * This,
    /* [retval][out] */ VARIANT *Files);


void __RPC_STUB IScanner_UINewFiles_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_UIUpdateFile_Proxy( 
    IScanner * This,
    /* [in] */ BSTR File,
    /* [retval][out] */ VARIANT_BOOL *Updated);


void __RPC_STUB IScanner_UIUpdateFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_NewFiles_Proxy( 
    IScanner * This,
    /* [in] */ LONG PagesToScan,
    /* [retval][out] */ VARIANT *Files);


void __RPC_STUB IScanner_NewFiles_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_ConfigureScan_Proxy( 
    IScanner * This);


void __RPC_STUB IScanner_ConfigureScan_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_UINewFilesToPath_Proxy( 
    IScanner * This,
    /* [in] */ BSTR Path,
    /* [retval][out] */ VARIANT *Files);


void __RPC_STUB IScanner_UINewFilesToPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_InitializeX_Proxy( 
    IScanner * This,
    /* [in] */ LONG hWnd);


void __RPC_STUB IScanner_InitializeX_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_SelectDevice_Proxy( 
    IScanner * This,
    /* [in] */ BSTR Name);


void __RPC_STUB IScanner_SelectDevice_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_Terminate_Proxy( 
    IScanner * This);


void __RPC_STUB IScanner_Terminate_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_SelectConfiguration_Proxy( 
    IScanner * This,
    /* [in] */ BSTR Name);


void __RPC_STUB IScanner_SelectConfiguration_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_UIConfig_Proxy( 
    IScanner * This,
    /* [in] */ VARIANT_BOOL StdDlg,
    /* [in] */ VARIANT_BOOL InitCapture);


void __RPC_STUB IScanner_UIConfig_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_GetNumPages_Proxy( 
    IScanner * This,
    /* [in] */ BSTR File,
    /* [retval][out] */ LONG *NumPages);


void __RPC_STUB IScanner_GetNumPages_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_UINewFilesX_Proxy( 
    IScanner * This,
    /* [in] */ LONG NumPages,
    /* [retval][out] */ VARIANT *Files);


void __RPC_STUB IScanner_UINewFilesX_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IScanner_NewFilesToPath_Proxy( 
    IScanner * This,
    /* [in] */ BSTR Path,
    /* [in] */ LONG PagesToScan,
    /* [retval][out] */ VARIANT *Files);


void __RPC_STUB IScanner_NewFilesToPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __IScanner_INTERFACE_DEFINED__ */


DEFINE_GUID(CLSID_Scanner,0x936846A9,0xD889,0x48FF,0xAD,0x95,0x08,0x46,0x62,0x39,0x41,0x43);

#ifdef __cplusplus

class DECLSPEC_UUID("936846A9-D889-48FF-AD95-084662394143")
Scanner;
#endif
#endif /* __IDocScanXPLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


