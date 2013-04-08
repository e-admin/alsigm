

/* this ALWAYS GENERATED file contains the IIDs and CLSIDs */

/* link this file in with the server and any clients */


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


#ifdef __cplusplus
extern "C"{
#endif 


#include <rpc.h>
#include <rpcndr.h>

#ifdef _MIDL_USE_GUIDDEF_

#ifndef INITGUID
#define INITGUID
#include <guiddef.h>
#undef INITGUID
#else
#include <guiddef.h>
#endif

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        DEFINE_GUID(name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8)

#else // !_MIDL_USE_GUIDDEF_

#ifndef __IID_DEFINED__
#define __IID_DEFINED__

typedef struct _IID
{
    unsigned long x;
    unsigned short s1;
    unsigned short s2;
    unsigned char  c[8];
} IID;

#endif // __IID_DEFINED__

#ifndef CLSID_DEFINED
#define CLSID_DEFINED
typedef IID CLSID;
#endif // CLSID_DEFINED

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        const type name = {l,w1,w2,{b1,b2,b3,b4,b5,b6,b7,b8}}

#endif !_MIDL_USE_GUIDDEF_

MIDL_DEFINE_GUID(IID, LIBID_IDOCIMGXLib,0x24C6D590,0x6D0D,0x11D4,0x81,0x28,0x00,0xC0,0xF0,0x49,0x16,0x7F);


MIDL_DEFINE_GUID(IID, DIID__iControlEvents,0x4065D205,0xAD66,0x4EC5,0x88,0x98,0x80,0x3B,0x6E,0x1B,0xD7,0xEA);


MIDL_DEFINE_GUID(IID, IID_IControl,0x24C6D59D,0x6D0D,0x11D4,0x81,0x28,0x00,0xC0,0xF0,0x49,0x16,0x7F);


MIDL_DEFINE_GUID(CLSID, CLSID_Control,0x24C6D59E,0x6D0D,0x11D4,0x81,0x28,0x00,0xC0,0xF0,0x49,0x16,0x7F);


MIDL_DEFINE_GUID(IID, IID_iPrintJob,0x496F91CC,0x7E33,0x4CF3,0x8B,0x94,0xBE,0x06,0x29,0x07,0x77,0xD2);


MIDL_DEFINE_GUID(CLSID, CLSID_PrintJob,0x96B9165F,0xD4FC,0x4AB5,0x94,0x86,0x89,0x78,0x2D,0x73,0x88,0x20);

#undef MIDL_DEFINE_GUID

#ifdef __cplusplus
}
#endif



