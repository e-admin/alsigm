

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 6.00.0366 */
/* at Fri Jun 29 12:02:33 2007
 */
/* Compiler settings for .\iDocImgX.idl:
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


#ifndef __iDocImgX_h__
#define __iDocImgX_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef ___iControlEvents_FWD_DEFINED__
#define ___iControlEvents_FWD_DEFINED__
typedef interface _iControlEvents _iControlEvents;
#endif 	/* ___iControlEvents_FWD_DEFINED__ */


#ifndef __IControl_FWD_DEFINED__
#define __IControl_FWD_DEFINED__
typedef interface IControl IControl;
#endif 	/* __IControl_FWD_DEFINED__ */


#ifndef __Control_FWD_DEFINED__
#define __Control_FWD_DEFINED__

#ifdef __cplusplus
typedef class Control Control;
#else
typedef struct Control Control;
#endif /* __cplusplus */

#endif 	/* __Control_FWD_DEFINED__ */


#ifndef __iPrintJob_FWD_DEFINED__
#define __iPrintJob_FWD_DEFINED__
typedef interface iPrintJob iPrintJob;
#endif 	/* __iPrintJob_FWD_DEFINED__ */


#ifndef __PrintJob_FWD_DEFINED__
#define __PrintJob_FWD_DEFINED__

#ifdef __cplusplus
typedef class PrintJob PrintJob;
#else
typedef struct PrintJob PrintJob;
#endif /* __cplusplus */

#endif 	/* __PrintJob_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"

#ifdef __cplusplus
extern "C"{
#endif 

void * __RPC_USER MIDL_user_allocate(size_t);
void __RPC_USER MIDL_user_free( void * ); 


#ifndef __IDOCIMGXLib_LIBRARY_DEFINED__
#define __IDOCIMGXLib_LIBRARY_DEFINED__

/* library IDOCIMGXLib */
/* [helpstring][version][uuid] */ 

typedef 
enum tagFitMode
    {	IMG_FIT_TO_WIDTH	= 0,
	IMG_FIT_TO_WINDOW	= 1,
	IMG_FIT_TO_HEIGHT	= 2,
	IMG_FIT_NONE	= 3
    } 	FitMode;

typedef 
enum tagEnhancement
    {	IMG_PRESERVE_WHITE	= 0,
	IMG_PRESERVE_BLACK	= 1,
	IMG_SCALE_TO_GRAY	= 2
    } 	Enhancement;


EXTERN_C const IID LIBID_IDOCIMGXLib;

#ifndef ___iControlEvents_DISPINTERFACE_DEFINED__
#define ___iControlEvents_DISPINTERFACE_DEFINED__

/* dispinterface _iControlEvents */
/* [helpstring][uuid] */ 


EXTERN_C const IID DIID__iControlEvents;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("4065D205-AD66-4EC5-8898-803B6E1BD7EA")
    _iControlEvents : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _iControlEventsVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _iControlEvents * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _iControlEvents * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _iControlEvents * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _iControlEvents * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _iControlEvents * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _iControlEvents * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _iControlEvents * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        END_INTERFACE
    } _iControlEventsVtbl;

    interface _iControlEvents
    {
        CONST_VTBL struct _iControlEventsVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _iControlEvents_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define _iControlEvents_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define _iControlEvents_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define _iControlEvents_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define _iControlEvents_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define _iControlEvents_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define _iControlEvents_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___iControlEvents_DISPINTERFACE_DEFINED__ */


#ifndef __IControl_INTERFACE_DEFINED__
#define __IControl_INTERFACE_DEFINED__

/* interface IControl */
/* [unique][helpstring][dual][uuid][object] */ 


EXTERN_C const IID IID_IControl;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("24C6D59D-6D0D-11D4-8128-00C0F049167F")
    IControl : public IDispatch
    {
    public:
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_FileName( 
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_FileName( 
            /* [in] */ BSTR newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Display( void) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Page( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Page( 
            /* [in] */ long newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_PageCount( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Clear( void) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_FitMode( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_FitMode( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Zoom( 
            /* [retval][out] */ double *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Zoom( 
            /* [in] */ double newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Enhancement( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Enhancement( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_EnablePrint( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_EnablePrint( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_ShowToolbar( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ShowToolbar( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Rotation( 
            /* [in] */ short newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Rotation( 
            /* [retval][out] */ short *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_FileNote( 
            /* [in] */ BSTR newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_FileNote( 
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveNotes( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IsNotesModified( 
            /* [retval][out] */ VARIANT_BOOL *Modified) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE HasAnyNotesModified( 
            /* [retval][out] */ VARIANT_BOOL *Modified) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveNotesToFile( 
            /* [in] */ BSTR FileName) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_EnableSaveAs( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_EnableSaveAs( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_EnableEditAnn( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_EnableEditAnn( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ExistsSelection( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveSelectionToFile( 
            /* [in] */ BSTR FileName) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ImgWidth( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_ImgHeight( 
            /* [retval][out] */ long *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveFileWithRotation( 
            /* [in] */ BSTR FileName) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SaveFile( 
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ExtractPagesToFile( 
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Pages,
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE JoinFiles( 
            /* [in] */ BSTR FileNameFirst,
            /* [in] */ BSTR FileNameSecond,
            /* [retval][out] */ BSTR *pVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE DeletePages( 
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Pages) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_Drag( 
            /* [in] */ VARIANT_BOOL newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_Drag( 
            /* [retval][out] */ VARIANT_BOOL *pVal) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IControlVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IControl * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IControl * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IControl * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IControl * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IControl * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IControl * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IControl * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_FileName )( 
            IControl * This,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_FileName )( 
            IControl * This,
            /* [in] */ BSTR newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Display )( 
            IControl * This);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Page )( 
            IControl * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Page )( 
            IControl * This,
            /* [in] */ long newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_PageCount )( 
            IControl * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *Clear )( 
            IControl * This);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_FitMode )( 
            IControl * This,
            /* [retval][out] */ short *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_FitMode )( 
            IControl * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Zoom )( 
            IControl * This,
            /* [retval][out] */ double *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Zoom )( 
            IControl * This,
            /* [in] */ double newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Enhancement )( 
            IControl * This,
            /* [retval][out] */ short *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Enhancement )( 
            IControl * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_EnablePrint )( 
            IControl * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_EnablePrint )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_ShowToolbar )( 
            IControl * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ShowToolbar )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Rotation )( 
            IControl * This,
            /* [in] */ short newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Rotation )( 
            IControl * This,
            /* [retval][out] */ short *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_FileNote )( 
            IControl * This,
            /* [in] */ BSTR newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_FileNote )( 
            IControl * This,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveNotes )( 
            IControl * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *IsNotesModified )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *Modified);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *HasAnyNotesModified )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *Modified);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveNotesToFile )( 
            IControl * This,
            /* [in] */ BSTR FileName);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_EnableSaveAs )( 
            IControl * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_EnableSaveAs )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_EnableEditAnn )( 
            IControl * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_EnableEditAnn )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *ExistsSelection )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveSelectionToFile )( 
            IControl * This,
            /* [in] */ BSTR FileName);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ImgWidth )( 
            IControl * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_ImgHeight )( 
            IControl * This,
            /* [retval][out] */ long *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveFileWithRotation )( 
            IControl * This,
            /* [in] */ BSTR FileName);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *SaveFile )( 
            IControl * This,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *ExtractPagesToFile )( 
            IControl * This,
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Pages,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *JoinFiles )( 
            IControl * This,
            /* [in] */ BSTR FileNameFirst,
            /* [in] */ BSTR FileNameSecond,
            /* [retval][out] */ BSTR *pVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *DeletePages )( 
            IControl * This,
            /* [in] */ BSTR FileName,
            /* [in] */ BSTR Pages);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE *put_Drag )( 
            IControl * This,
            /* [in] */ VARIANT_BOOL newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE *get_Drag )( 
            IControl * This,
            /* [retval][out] */ VARIANT_BOOL *pVal);
        
        END_INTERFACE
    } IControlVtbl;

    interface IControl
    {
        CONST_VTBL struct IControlVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IControl_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define IControl_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define IControl_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define IControl_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define IControl_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define IControl_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define IControl_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define IControl_get_FileName(This,pVal)	\
    (This)->lpVtbl -> get_FileName(This,pVal)

#define IControl_put_FileName(This,newVal)	\
    (This)->lpVtbl -> put_FileName(This,newVal)

#define IControl_Display(This)	\
    (This)->lpVtbl -> Display(This)

#define IControl_get_Page(This,pVal)	\
    (This)->lpVtbl -> get_Page(This,pVal)

#define IControl_put_Page(This,newVal)	\
    (This)->lpVtbl -> put_Page(This,newVal)

#define IControl_get_PageCount(This,pVal)	\
    (This)->lpVtbl -> get_PageCount(This,pVal)

#define IControl_Clear(This)	\
    (This)->lpVtbl -> Clear(This)

#define IControl_get_FitMode(This,pVal)	\
    (This)->lpVtbl -> get_FitMode(This,pVal)

#define IControl_put_FitMode(This,newVal)	\
    (This)->lpVtbl -> put_FitMode(This,newVal)

#define IControl_get_Zoom(This,pVal)	\
    (This)->lpVtbl -> get_Zoom(This,pVal)

#define IControl_put_Zoom(This,newVal)	\
    (This)->lpVtbl -> put_Zoom(This,newVal)

#define IControl_get_Enhancement(This,pVal)	\
    (This)->lpVtbl -> get_Enhancement(This,pVal)

#define IControl_put_Enhancement(This,newVal)	\
    (This)->lpVtbl -> put_Enhancement(This,newVal)

#define IControl_put_EnablePrint(This,newVal)	\
    (This)->lpVtbl -> put_EnablePrint(This,newVal)

#define IControl_get_EnablePrint(This,pVal)	\
    (This)->lpVtbl -> get_EnablePrint(This,pVal)

#define IControl_put_ShowToolbar(This,newVal)	\
    (This)->lpVtbl -> put_ShowToolbar(This,newVal)

#define IControl_get_ShowToolbar(This,pVal)	\
    (This)->lpVtbl -> get_ShowToolbar(This,pVal)

#define IControl_put_Rotation(This,newVal)	\
    (This)->lpVtbl -> put_Rotation(This,newVal)

#define IControl_get_Rotation(This,pVal)	\
    (This)->lpVtbl -> get_Rotation(This,pVal)

#define IControl_put_FileNote(This,newVal)	\
    (This)->lpVtbl -> put_FileNote(This,newVal)

#define IControl_get_FileNote(This,pVal)	\
    (This)->lpVtbl -> get_FileNote(This,pVal)

#define IControl_SaveNotes(This)	\
    (This)->lpVtbl -> SaveNotes(This)

#define IControl_IsNotesModified(This,Modified)	\
    (This)->lpVtbl -> IsNotesModified(This,Modified)

#define IControl_HasAnyNotesModified(This,Modified)	\
    (This)->lpVtbl -> HasAnyNotesModified(This,Modified)

#define IControl_SaveNotesToFile(This,FileName)	\
    (This)->lpVtbl -> SaveNotesToFile(This,FileName)

#define IControl_put_EnableSaveAs(This,newVal)	\
    (This)->lpVtbl -> put_EnableSaveAs(This,newVal)

#define IControl_get_EnableSaveAs(This,pVal)	\
    (This)->lpVtbl -> get_EnableSaveAs(This,pVal)

#define IControl_put_EnableEditAnn(This,newVal)	\
    (This)->lpVtbl -> put_EnableEditAnn(This,newVal)

#define IControl_get_EnableEditAnn(This,pVal)	\
    (This)->lpVtbl -> get_EnableEditAnn(This,pVal)

#define IControl_ExistsSelection(This,pVal)	\
    (This)->lpVtbl -> ExistsSelection(This,pVal)

#define IControl_SaveSelectionToFile(This,FileName)	\
    (This)->lpVtbl -> SaveSelectionToFile(This,FileName)

#define IControl_get_ImgWidth(This,pVal)	\
    (This)->lpVtbl -> get_ImgWidth(This,pVal)

#define IControl_get_ImgHeight(This,pVal)	\
    (This)->lpVtbl -> get_ImgHeight(This,pVal)

#define IControl_SaveFileWithRotation(This,FileName)	\
    (This)->lpVtbl -> SaveFileWithRotation(This,FileName)

#define IControl_SaveFile(This,pVal)	\
    (This)->lpVtbl -> SaveFile(This,pVal)

#define IControl_ExtractPagesToFile(This,FileName,Pages,pVal)	\
    (This)->lpVtbl -> ExtractPagesToFile(This,FileName,Pages,pVal)

#define IControl_JoinFiles(This,FileNameFirst,FileNameSecond,pVal)	\
    (This)->lpVtbl -> JoinFiles(This,FileNameFirst,FileNameSecond,pVal)

#define IControl_DeletePages(This,FileName,Pages)	\
    (This)->lpVtbl -> DeletePages(This,FileName,Pages)

#define IControl_put_Drag(This,newVal)	\
    (This)->lpVtbl -> put_Drag(This,newVal)

#define IControl_get_Drag(This,pVal)	\
    (This)->lpVtbl -> get_Drag(This,pVal)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_FileName_Proxy( 
    IControl * This,
    /* [retval][out] */ BSTR *pVal);


void __RPC_STUB IControl_get_FileName_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_FileName_Proxy( 
    IControl * This,
    /* [in] */ BSTR newVal);


void __RPC_STUB IControl_put_FileName_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_Display_Proxy( 
    IControl * This);


void __RPC_STUB IControl_Display_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_Page_Proxy( 
    IControl * This,
    /* [retval][out] */ long *pVal);


void __RPC_STUB IControl_get_Page_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_Page_Proxy( 
    IControl * This,
    /* [in] */ long newVal);


void __RPC_STUB IControl_put_Page_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_PageCount_Proxy( 
    IControl * This,
    /* [retval][out] */ long *pVal);


void __RPC_STUB IControl_get_PageCount_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_Clear_Proxy( 
    IControl * This);


void __RPC_STUB IControl_Clear_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_FitMode_Proxy( 
    IControl * This,
    /* [retval][out] */ short *pVal);


void __RPC_STUB IControl_get_FitMode_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_FitMode_Proxy( 
    IControl * This,
    /* [in] */ short newVal);


void __RPC_STUB IControl_put_FitMode_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_Zoom_Proxy( 
    IControl * This,
    /* [retval][out] */ double *pVal);


void __RPC_STUB IControl_get_Zoom_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_Zoom_Proxy( 
    IControl * This,
    /* [in] */ double newVal);


void __RPC_STUB IControl_put_Zoom_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_Enhancement_Proxy( 
    IControl * This,
    /* [retval][out] */ short *pVal);


void __RPC_STUB IControl_get_Enhancement_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_Enhancement_Proxy( 
    IControl * This,
    /* [in] */ short newVal);


void __RPC_STUB IControl_put_Enhancement_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_EnablePrint_Proxy( 
    IControl * This,
    /* [in] */ VARIANT_BOOL newVal);


void __RPC_STUB IControl_put_EnablePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_EnablePrint_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_get_EnablePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_ShowToolbar_Proxy( 
    IControl * This,
    /* [in] */ VARIANT_BOOL newVal);


void __RPC_STUB IControl_put_ShowToolbar_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_ShowToolbar_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_get_ShowToolbar_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_Rotation_Proxy( 
    IControl * This,
    /* [in] */ short newVal);


void __RPC_STUB IControl_put_Rotation_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_Rotation_Proxy( 
    IControl * This,
    /* [retval][out] */ short *pVal);


void __RPC_STUB IControl_get_Rotation_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_FileNote_Proxy( 
    IControl * This,
    /* [in] */ BSTR newVal);


void __RPC_STUB IControl_put_FileNote_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_FileNote_Proxy( 
    IControl * This,
    /* [retval][out] */ BSTR *pVal);


void __RPC_STUB IControl_get_FileNote_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_SaveNotes_Proxy( 
    IControl * This);


void __RPC_STUB IControl_SaveNotes_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_IsNotesModified_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *Modified);


void __RPC_STUB IControl_IsNotesModified_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_HasAnyNotesModified_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *Modified);


void __RPC_STUB IControl_HasAnyNotesModified_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_SaveNotesToFile_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileName);


void __RPC_STUB IControl_SaveNotesToFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_EnableSaveAs_Proxy( 
    IControl * This,
    /* [in] */ VARIANT_BOOL newVal);


void __RPC_STUB IControl_put_EnableSaveAs_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_EnableSaveAs_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_get_EnableSaveAs_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_EnableEditAnn_Proxy( 
    IControl * This,
    /* [in] */ VARIANT_BOOL newVal);


void __RPC_STUB IControl_put_EnableEditAnn_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_EnableEditAnn_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_get_EnableEditAnn_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_ExistsSelection_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_ExistsSelection_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_SaveSelectionToFile_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileName);


void __RPC_STUB IControl_SaveSelectionToFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_ImgWidth_Proxy( 
    IControl * This,
    /* [retval][out] */ long *pVal);


void __RPC_STUB IControl_get_ImgWidth_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_ImgHeight_Proxy( 
    IControl * This,
    /* [retval][out] */ long *pVal);


void __RPC_STUB IControl_get_ImgHeight_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_SaveFileWithRotation_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileName);


void __RPC_STUB IControl_SaveFileWithRotation_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_SaveFile_Proxy( 
    IControl * This,
    /* [retval][out] */ BSTR *pVal);


void __RPC_STUB IControl_SaveFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_ExtractPagesToFile_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileName,
    /* [in] */ BSTR Pages,
    /* [retval][out] */ BSTR *pVal);


void __RPC_STUB IControl_ExtractPagesToFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_JoinFiles_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileNameFirst,
    /* [in] */ BSTR FileNameSecond,
    /* [retval][out] */ BSTR *pVal);


void __RPC_STUB IControl_JoinFiles_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IControl_DeletePages_Proxy( 
    IControl * This,
    /* [in] */ BSTR FileName,
    /* [in] */ BSTR Pages);


void __RPC_STUB IControl_DeletePages_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IControl_put_Drag_Proxy( 
    IControl * This,
    /* [in] */ VARIANT_BOOL newVal);


void __RPC_STUB IControl_put_Drag_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IControl_get_Drag_Proxy( 
    IControl * This,
    /* [retval][out] */ VARIANT_BOOL *pVal);


void __RPC_STUB IControl_get_Drag_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __IControl_INTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_Control;

#ifdef __cplusplus

class DECLSPEC_UUID("24C6D59E-6D0D-11D4-8128-00C0F049167F")
Control;
#endif

#ifndef __iPrintJob_INTERFACE_DEFINED__
#define __iPrintJob_INTERFACE_DEFINED__

/* interface iPrintJob */
/* [unique][helpstring][dual][uuid][object] */ 


EXTERN_C const IID IID_iPrintJob;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("496F91CC-7E33-4CF3-8B94-BE06290777D2")
    iPrintJob : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PrintFile( 
            /* [in] */ BSTR newVal,
            /* [in] */ VARIANT_BOOL newVa) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PrintFileWStamp( 
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp,
            /* [in] */ VARIANT_BOOL newVa) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE InitializePrint( 
            /* [in] */ BSTR newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PagePrint( 
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp,
            /* [in] */ VARIANT_BOOL newVa) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE TerminatePrint( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PrintFileWTxt( 
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newTxt,
            /* [in] */ VARIANT_BOOL newVa,
            /* [in] */ VARIANT_BOOL up) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE PageNormalSizePrint( 
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct iPrintJobVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            iPrintJob * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            iPrintJob * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            iPrintJob * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            iPrintJob * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            iPrintJob * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            iPrintJob * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            iPrintJob * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PrintFile )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal,
            /* [in] */ VARIANT_BOOL newVa);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PrintFileWStamp )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp,
            /* [in] */ VARIANT_BOOL newVa);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *InitializePrint )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PagePrint )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp,
            /* [in] */ VARIANT_BOOL newVa);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *TerminatePrint )( 
            iPrintJob * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PrintFileWTxt )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newTxt,
            /* [in] */ VARIANT_BOOL newVa,
            /* [in] */ VARIANT_BOOL up);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *PageNormalSizePrint )( 
            iPrintJob * This,
            /* [in] */ BSTR newVal,
            /* [in] */ BSTR newStamp);
        
        END_INTERFACE
    } iPrintJobVtbl;

    interface iPrintJob
    {
        CONST_VTBL struct iPrintJobVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define iPrintJob_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define iPrintJob_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define iPrintJob_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define iPrintJob_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define iPrintJob_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define iPrintJob_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define iPrintJob_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define iPrintJob_PrintFile(This,newVal,newVa)	\
    (This)->lpVtbl -> PrintFile(This,newVal,newVa)

#define iPrintJob_PrintFileWStamp(This,newVal,newStamp,newVa)	\
    (This)->lpVtbl -> PrintFileWStamp(This,newVal,newStamp,newVa)

#define iPrintJob_InitializePrint(This,newVal)	\
    (This)->lpVtbl -> InitializePrint(This,newVal)

#define iPrintJob_PagePrint(This,newVal,newStamp,newVa)	\
    (This)->lpVtbl -> PagePrint(This,newVal,newStamp,newVa)

#define iPrintJob_TerminatePrint(This)	\
    (This)->lpVtbl -> TerminatePrint(This)

#define iPrintJob_PrintFileWTxt(This,newVal,newTxt,newVa,up)	\
    (This)->lpVtbl -> PrintFileWTxt(This,newVal,newTxt,newVa,up)

#define iPrintJob_PageNormalSizePrint(This,newVal,newStamp)	\
    (This)->lpVtbl -> PageNormalSizePrint(This,newVal,newStamp)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_PrintFile_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal,
    /* [in] */ VARIANT_BOOL newVa);


void __RPC_STUB iPrintJob_PrintFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_PrintFileWStamp_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal,
    /* [in] */ BSTR newStamp,
    /* [in] */ VARIANT_BOOL newVa);


void __RPC_STUB iPrintJob_PrintFileWStamp_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_InitializePrint_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal);


void __RPC_STUB iPrintJob_InitializePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_PagePrint_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal,
    /* [in] */ BSTR newStamp,
    /* [in] */ VARIANT_BOOL newVa);


void __RPC_STUB iPrintJob_PagePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_TerminatePrint_Proxy( 
    iPrintJob * This);


void __RPC_STUB iPrintJob_TerminatePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_PrintFileWTxt_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal,
    /* [in] */ BSTR newTxt,
    /* [in] */ VARIANT_BOOL newVa,
    /* [in] */ VARIANT_BOOL up);


void __RPC_STUB iPrintJob_PrintFileWTxt_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE iPrintJob_PageNormalSizePrint_Proxy( 
    iPrintJob * This,
    /* [in] */ BSTR newVal,
    /* [in] */ BSTR newStamp);


void __RPC_STUB iPrintJob_PageNormalSizePrint_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __iPrintJob_INTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_PrintJob;

#ifdef __cplusplus

class DECLSPEC_UUID("96B9165F-D4FC-4AB5-9486-89782D738820")
PrintJob;
#endif
#endif /* __IDOCIMGXLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


