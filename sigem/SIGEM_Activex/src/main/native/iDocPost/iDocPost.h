

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 6.00.0366 */
/* at Tue May 27 18:19:06 2008
 */
/* Compiler settings for .\iDocPost.idl:
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

#ifndef COM_NO_WINDOWS_H
#include "windows.h"
#include "ole2.h"
#endif /*COM_NO_WINDOWS_H*/

#ifndef __iDocPost_h__
#define __iDocPost_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef __IUpload_FWD_DEFINED__
#define __IUpload_FWD_DEFINED__
typedef interface IUpload IUpload;
#endif 	/* __IUpload_FWD_DEFINED__ */


#ifndef __Upload_FWD_DEFINED__
#define __Upload_FWD_DEFINED__

#ifdef __cplusplus
typedef class Upload Upload;
#else
typedef struct Upload Upload;
#endif /* __cplusplus */

#endif 	/* __Upload_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"

#ifdef __cplusplus
extern "C"{
#endif 

void * __RPC_USER MIDL_user_allocate(size_t);
void __RPC_USER MIDL_user_free( void * ); 

#ifndef __IUpload_INTERFACE_DEFINED__
#define __IUpload_INTERFACE_DEFINED__

/* interface IUpload */
/* [unique][helpstring][dual][uuid][object] */ 


EXTERN_C const IID IID_IUpload;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("82C12E1E-9452-4186-B9BB-66C032E095B5")
    IUpload : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UploadFile( 
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR URL,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SelectFile( 
            /* [in] */ long bSaving,
            /* [retval][out] */ VARIANT *pFileName) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE DownloadFile( 
            /* [in] */ BSTR URL,
            /* [in] */ BSTR LocalPath,
            /* [out] */ VARIANT *pFileLoc,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE CallURL( 
            /* [in] */ BSTR URL,
            /* [out] */ VARIANT *pResponse,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SelectDir( 
            /* [retval][out] */ VARIANT *pDir) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE DeleteFile( 
            /* [in] */ BSTR FileName) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetTempPath( 
            /* [retval][out] */ VARIANT *pTempPath) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PUTFile( 
            /* [in] */ BSTR File,
            /* [in] */ BSTR URL,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GETFile( 
            /* [in] */ BSTR URL,
            /* [in] */ BSTR LocalFile,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UploadFileX( 
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR Page,
            /* [in] */ BSTR VirtualDir,
            /* [in] */ BSTR DstFileName,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE UploadFileM( 
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR Page,
            /* [in] */ BSTR VirtualDir,
            /* [in] */ BSTR DstFileName,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IUploadVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IUpload * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IUpload * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IUpload * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IUpload * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IUpload * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IUpload * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IUpload * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UploadFile )( 
            IUpload * This,
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR URL,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SelectFile )( 
            IUpload * This,
            /* [in] */ long bSaving,
            /* [retval][out] */ VARIANT *pFileName);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *DownloadFile )( 
            IUpload * This,
            /* [in] */ BSTR URL,
            /* [in] */ BSTR LocalPath,
            /* [out] */ VARIANT *pFileLoc,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *CallURL )( 
            IUpload * This,
            /* [in] */ BSTR URL,
            /* [out] */ VARIANT *pResponse,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SelectDir )( 
            IUpload * This,
            /* [retval][out] */ VARIANT *pDir);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *DeleteFile )( 
            IUpload * This,
            /* [in] */ BSTR FileName);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *GetTempPath )( 
            IUpload * This,
            /* [retval][out] */ VARIANT *pTempPath);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PUTFile )( 
            IUpload * This,
            /* [in] */ BSTR File,
            /* [in] */ BSTR URL,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *GETFile )( 
            IUpload * This,
            /* [in] */ BSTR URL,
            /* [in] */ BSTR LocalFile,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UploadFileX )( 
            IUpload * This,
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR Page,
            /* [in] */ BSTR VirtualDir,
            /* [in] */ BSTR DstFileName,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *UploadFileM )( 
            IUpload * This,
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Server,
            /* [in] */ BSTR Page,
            /* [in] */ BSTR VirtualDir,
            /* [in] */ BSTR DstFileName,
            /* [in] */ long bRemove,
            /* [retval][out] */ VARIANT *pErrorCode);
        
        END_INTERFACE
    } IUploadVtbl;

    interface IUpload
    {
        CONST_VTBL struct IUploadVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IUpload_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define IUpload_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define IUpload_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define IUpload_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define IUpload_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define IUpload_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define IUpload_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define IUpload_UploadFile(This,FileName,Server,URL,bRemove,pErrorCode)	\
    (This)->lpVtbl -> UploadFile(This,FileName,Server,URL,bRemove,pErrorCode)

#define IUpload_SelectFile(This,bSaving,pFileName)	\
    (This)->lpVtbl -> SelectFile(This,bSaving,pFileName)

#define IUpload_DownloadFile(This,URL,LocalPath,pFileLoc,pErrorCode)	\
    (This)->lpVtbl -> DownloadFile(This,URL,LocalPath,pFileLoc,pErrorCode)

#define IUpload_CallURL(This,URL,pResponse,pErrorCode)	\
    (This)->lpVtbl -> CallURL(This,URL,pResponse,pErrorCode)

#define IUpload_SelectDir(This,pDir)	\
    (This)->lpVtbl -> SelectDir(This,pDir)

#define IUpload_DeleteFile(This,FileName)	\
    (This)->lpVtbl -> DeleteFile(This,FileName)

#define IUpload_GetTempPath(This,pTempPath)	\
    (This)->lpVtbl -> GetTempPath(This,pTempPath)

#define IUpload_PUTFile(This,File,URL,pErrorCode)	\
    (This)->lpVtbl -> PUTFile(This,File,URL,pErrorCode)

#define IUpload_GETFile(This,URL,LocalFile,pErrorCode)	\
    (This)->lpVtbl -> GETFile(This,URL,LocalFile,pErrorCode)

#define IUpload_UploadFileX(This,FileName,Server,Page,VirtualDir,DstFileName,bRemove,pErrorCode)	\
    (This)->lpVtbl -> UploadFileX(This,FileName,Server,Page,VirtualDir,DstFileName,bRemove,pErrorCode)

#define IUpload_UploadFileM(This,FileName,Server,Page,VirtualDir,DstFileName,bRemove,pErrorCode)	\
    (This)->lpVtbl -> UploadFileM(This,FileName,Server,Page,VirtualDir,DstFileName,bRemove,pErrorCode)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_UploadFile_Proxy( 
    IUpload * This,
    /* [in] */ BSTR FileName,
    /* [in] */ BSTR Server,
    /* [in] */ BSTR URL,
    /* [in] */ long bRemove,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_UploadFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_SelectFile_Proxy( 
    IUpload * This,
    /* [in] */ long bSaving,
    /* [retval][out] */ VARIANT *pFileName);


void __RPC_STUB IUpload_SelectFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_DownloadFile_Proxy( 
    IUpload * This,
    /* [in] */ BSTR URL,
    /* [in] */ BSTR LocalPath,
    /* [out] */ VARIANT *pFileLoc,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_DownloadFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_CallURL_Proxy( 
    IUpload * This,
    /* [in] */ BSTR URL,
    /* [out] */ VARIANT *pResponse,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_CallURL_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_SelectDir_Proxy( 
    IUpload * This,
    /* [retval][out] */ VARIANT *pDir);


void __RPC_STUB IUpload_SelectDir_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_DeleteFile_Proxy( 
    IUpload * This,
    /* [in] */ BSTR FileName);


void __RPC_STUB IUpload_DeleteFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_GetTempPath_Proxy( 
    IUpload * This,
    /* [retval][out] */ VARIANT *pTempPath);


void __RPC_STUB IUpload_GetTempPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_PUTFile_Proxy( 
    IUpload * This,
    /* [in] */ BSTR File,
    /* [in] */ BSTR URL,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_PUTFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_GETFile_Proxy( 
    IUpload * This,
    /* [in] */ BSTR URL,
    /* [in] */ BSTR LocalFile,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_GETFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_UploadFileX_Proxy( 
    IUpload * This,
    /* [in] */ BSTR FileName,
    /* [in] */ BSTR Server,
    /* [in] */ BSTR Page,
    /* [in] */ BSTR VirtualDir,
    /* [in] */ BSTR DstFileName,
    /* [in] */ long bRemove,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_UploadFileX_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IUpload_UploadFileM_Proxy( 
    IUpload * This,
    /* [in] */ BSTR FileName,
    /* [in] */ BSTR Server,
    /* [in] */ BSTR Page,
    /* [in] */ BSTR VirtualDir,
    /* [in] */ BSTR DstFileName,
    /* [in] */ long bRemove,
    /* [retval][out] */ VARIANT *pErrorCode);


void __RPC_STUB IUpload_UploadFileM_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __IUpload_INTERFACE_DEFINED__ */



#ifndef __IDOCPOSTLib_LIBRARY_DEFINED__
#define __IDOCPOSTLib_LIBRARY_DEFINED__

/* library IDOCPOSTLib */
/* [helpstring][version][uuid] */ 


EXTERN_C const IID LIBID_IDOCPOSTLib;

EXTERN_C const CLSID CLSID_Upload;

#ifdef __cplusplus

class DECLSPEC_UUID("CDB30A8F-7EB9-407D-A20D-E10010CEF693")
Upload;
#endif
#endif /* __IDOCPOSTLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

unsigned long             __RPC_USER  BSTR_UserSize(     unsigned long *, unsigned long            , BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserMarshal(  unsigned long *, unsigned char *, BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserUnmarshal(unsigned long *, unsigned char *, BSTR * ); 
void                      __RPC_USER  BSTR_UserFree(     unsigned long *, BSTR * ); 

unsigned long             __RPC_USER  VARIANT_UserSize(     unsigned long *, unsigned long            , VARIANT * ); 
unsigned char * __RPC_USER  VARIANT_UserMarshal(  unsigned long *, unsigned char *, VARIANT * ); 
unsigned char * __RPC_USER  VARIANT_UserUnmarshal(unsigned long *, unsigned char *, VARIANT * ); 
void                      __RPC_USER  VARIANT_UserFree(     unsigned long *, VARIANT * ); 

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


