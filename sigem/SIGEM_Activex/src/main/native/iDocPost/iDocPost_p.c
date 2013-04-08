

/* this ALWAYS GENERATED file contains the proxy stub code */


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

#if !defined(_M_IA64) && !defined(_M_AMD64)


#pragma warning( disable: 4049 )  /* more than 64k source lines */
#if _MSC_VER >= 1200
#pragma warning(push)
#endif
#pragma warning( disable: 4100 ) /* unreferenced arguments in x86 call */
#pragma warning( disable: 4211 )  /* redefine extent to static */
#pragma warning( disable: 4232 )  /* dllimport identity*/
#pragma optimize("", off ) 

#define USE_STUBLESS_PROXY


/* verify that the <rpcproxy.h> version is high enough to compile this file*/
#ifndef __REDQ_RPCPROXY_H_VERSION__
#define __REQUIRED_RPCPROXY_H_VERSION__ 475
#endif


#include "rpcproxy.h"
#ifndef __RPCPROXY_H_VERSION__
#error this stub requires an updated version of <rpcproxy.h>
#endif // __RPCPROXY_H_VERSION__


#include "iDocPost.h"

#define TYPE_FORMAT_STRING_SIZE   1053                              
#define PROC_FORMAT_STRING_SIZE   553                               
#define TRANSMIT_AS_TABLE_SIZE    0            
#define WIRE_MARSHAL_TABLE_SIZE   2            

typedef struct _MIDL_TYPE_FORMAT_STRING
    {
    short          Pad;
    unsigned char  Format[ TYPE_FORMAT_STRING_SIZE ];
    } MIDL_TYPE_FORMAT_STRING;

typedef struct _MIDL_PROC_FORMAT_STRING
    {
    short          Pad;
    unsigned char  Format[ PROC_FORMAT_STRING_SIZE ];
    } MIDL_PROC_FORMAT_STRING;


static RPC_SYNTAX_IDENTIFIER  _RpcTransferSyntax = 
{{0x8A885D04,0x1CEB,0x11C9,{0x9F,0xE8,0x08,0x00,0x2B,0x10,0x48,0x60}},{2,0}};


extern const MIDL_TYPE_FORMAT_STRING __MIDL_TypeFormatString;
extern const MIDL_PROC_FORMAT_STRING __MIDL_ProcFormatString;


extern const MIDL_STUB_DESC Object_StubDesc;


extern const MIDL_SERVER_INFO IUpload_ServerInfo;
extern const MIDL_STUBLESS_PROXY_INFO IUpload_ProxyInfo;


extern const USER_MARSHAL_ROUTINE_QUADRUPLE UserMarshalRoutines[ WIRE_MARSHAL_TABLE_SIZE ];

#if !defined(__RPC_WIN32__)
#error  Invalid build platform for this stub.
#endif

#if !(TARGET_IS_NT50_OR_LATER)
#error You need a Windows 2000 or later to run this stub because it uses these features:
#error   /robust command line switch.
#error However, your C/C++ compilation flags indicate you intend to run this app on earlier systems.
#error This app will die there with the RPC_X_WRONG_STUB_VERSION error.
#endif


static const MIDL_PROC_FORMAT_STRING __MIDL_ProcFormatString =
    {
        0,
        {

	/* Procedure UploadFile */

			0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/*  2 */	NdrFcLong( 0x0 ),	/* 0 */
/*  6 */	NdrFcShort( 0x7 ),	/* 7 */
/*  8 */	NdrFcShort( 0x1c ),	/* x86 Stack size/offset = 28 */
/* 10 */	NdrFcShort( 0x8 ),	/* 8 */
/* 12 */	NdrFcShort( 0x8 ),	/* 8 */
/* 14 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x6,		/* 6 */
/* 16 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 18 */	NdrFcShort( 0x20 ),	/* 32 */
/* 20 */	NdrFcShort( 0x3 ),	/* 3 */
/* 22 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter FileName */

/* 24 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 26 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 28 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter Server */

/* 30 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 32 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 34 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter URL */

/* 36 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 38 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 40 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter bRemove */

/* 42 */	NdrFcShort( 0x48 ),	/* Flags:  in, base type, */
/* 44 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 46 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Parameter pErrorCode */

/* 48 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 50 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 52 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 54 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 56 */	NdrFcShort( 0x18 ),	/* x86 Stack size/offset = 24 */
/* 58 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure SelectFile */

/* 60 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 62 */	NdrFcLong( 0x0 ),	/* 0 */
/* 66 */	NdrFcShort( 0x8 ),	/* 8 */
/* 68 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 70 */	NdrFcShort( 0x8 ),	/* 8 */
/* 72 */	NdrFcShort( 0x8 ),	/* 8 */
/* 74 */	0x45,		/* Oi2 Flags:  srv must size, has return, has ext, */
			0x3,		/* 3 */
/* 76 */	0x8,		/* 8 */
			0x3,		/* Ext Flags:  new corr desc, clt corr check, */
/* 78 */	NdrFcShort( 0x20 ),	/* 32 */
/* 80 */	NdrFcShort( 0x0 ),	/* 0 */
/* 82 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter bSaving */

/* 84 */	NdrFcShort( 0x48 ),	/* Flags:  in, base type, */
/* 86 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 88 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Parameter pFileName */

/* 90 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 92 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 94 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 96 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 98 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 100 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure DownloadFile */

/* 102 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 104 */	NdrFcLong( 0x0 ),	/* 0 */
/* 108 */	NdrFcShort( 0x9 ),	/* 9 */
/* 110 */	NdrFcShort( 0x18 ),	/* x86 Stack size/offset = 24 */
/* 112 */	NdrFcShort( 0x0 ),	/* 0 */
/* 114 */	NdrFcShort( 0x8 ),	/* 8 */
/* 116 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x5,		/* 5 */
/* 118 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 120 */	NdrFcShort( 0x40 ),	/* 64 */
/* 122 */	NdrFcShort( 0x2 ),	/* 2 */
/* 124 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter URL */

/* 126 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 128 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 130 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter LocalPath */

/* 132 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 134 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 136 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter pFileLoc */

/* 138 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 140 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 142 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Parameter pErrorCode */

/* 144 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 146 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 148 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 150 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 152 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 154 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure CallURL */

/* 156 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 158 */	NdrFcLong( 0x0 ),	/* 0 */
/* 162 */	NdrFcShort( 0xa ),	/* 10 */
/* 164 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 166 */	NdrFcShort( 0x0 ),	/* 0 */
/* 168 */	NdrFcShort( 0x8 ),	/* 8 */
/* 170 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x4,		/* 4 */
/* 172 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 174 */	NdrFcShort( 0x40 ),	/* 64 */
/* 176 */	NdrFcShort( 0x1 ),	/* 1 */
/* 178 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter URL */

/* 180 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 182 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 184 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter pResponse */

/* 186 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 188 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 190 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Parameter pErrorCode */

/* 192 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 194 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 196 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 198 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 200 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 202 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure SelectDir */

/* 204 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 206 */	NdrFcLong( 0x0 ),	/* 0 */
/* 210 */	NdrFcShort( 0xb ),	/* 11 */
/* 212 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 214 */	NdrFcShort( 0x0 ),	/* 0 */
/* 216 */	NdrFcShort( 0x8 ),	/* 8 */
/* 218 */	0x45,		/* Oi2 Flags:  srv must size, has return, has ext, */
			0x2,		/* 2 */
/* 220 */	0x8,		/* 8 */
			0x3,		/* Ext Flags:  new corr desc, clt corr check, */
/* 222 */	NdrFcShort( 0x20 ),	/* 32 */
/* 224 */	NdrFcShort( 0x0 ),	/* 0 */
/* 226 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter pDir */

/* 228 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 230 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 232 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 234 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 236 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 238 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure DeleteFile */

/* 240 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 242 */	NdrFcLong( 0x0 ),	/* 0 */
/* 246 */	NdrFcShort( 0xc ),	/* 12 */
/* 248 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 250 */	NdrFcShort( 0x0 ),	/* 0 */
/* 252 */	NdrFcShort( 0x8 ),	/* 8 */
/* 254 */	0x46,		/* Oi2 Flags:  clt must size, has return, has ext, */
			0x2,		/* 2 */
/* 256 */	0x8,		/* 8 */
			0x5,		/* Ext Flags:  new corr desc, srv corr check, */
/* 258 */	NdrFcShort( 0x0 ),	/* 0 */
/* 260 */	NdrFcShort( 0x1 ),	/* 1 */
/* 262 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter FileName */

/* 264 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 266 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 268 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Return value */

/* 270 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 272 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 274 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure GetTempPath */

/* 276 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 278 */	NdrFcLong( 0x0 ),	/* 0 */
/* 282 */	NdrFcShort( 0xd ),	/* 13 */
/* 284 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 286 */	NdrFcShort( 0x0 ),	/* 0 */
/* 288 */	NdrFcShort( 0x8 ),	/* 8 */
/* 290 */	0x45,		/* Oi2 Flags:  srv must size, has return, has ext, */
			0x2,		/* 2 */
/* 292 */	0x8,		/* 8 */
			0x3,		/* Ext Flags:  new corr desc, clt corr check, */
/* 294 */	NdrFcShort( 0x20 ),	/* 32 */
/* 296 */	NdrFcShort( 0x0 ),	/* 0 */
/* 298 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter pTempPath */

/* 300 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 302 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 304 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 306 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 308 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 310 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure PUTFile */

/* 312 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 314 */	NdrFcLong( 0x0 ),	/* 0 */
/* 318 */	NdrFcShort( 0xe ),	/* 14 */
/* 320 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 322 */	NdrFcShort( 0x0 ),	/* 0 */
/* 324 */	NdrFcShort( 0x8 ),	/* 8 */
/* 326 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x4,		/* 4 */
/* 328 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 330 */	NdrFcShort( 0x20 ),	/* 32 */
/* 332 */	NdrFcShort( 0x2 ),	/* 2 */
/* 334 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter File */

/* 336 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 338 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 340 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter URL */

/* 342 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 344 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 346 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter pErrorCode */

/* 348 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 350 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 352 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 354 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 356 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 358 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure GETFile */

/* 360 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 362 */	NdrFcLong( 0x0 ),	/* 0 */
/* 366 */	NdrFcShort( 0xf ),	/* 15 */
/* 368 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 370 */	NdrFcShort( 0x0 ),	/* 0 */
/* 372 */	NdrFcShort( 0x8 ),	/* 8 */
/* 374 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x4,		/* 4 */
/* 376 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 378 */	NdrFcShort( 0x20 ),	/* 32 */
/* 380 */	NdrFcShort( 0x2 ),	/* 2 */
/* 382 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter URL */

/* 384 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 386 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 388 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter LocalFile */

/* 390 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 392 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 394 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter pErrorCode */

/* 396 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 398 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 400 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 402 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 404 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 406 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure UploadFileX */

/* 408 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 410 */	NdrFcLong( 0x0 ),	/* 0 */
/* 414 */	NdrFcShort( 0x10 ),	/* 16 */
/* 416 */	NdrFcShort( 0x24 ),	/* x86 Stack size/offset = 36 */
/* 418 */	NdrFcShort( 0x8 ),	/* 8 */
/* 420 */	NdrFcShort( 0x8 ),	/* 8 */
/* 422 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x8,		/* 8 */
/* 424 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 426 */	NdrFcShort( 0x20 ),	/* 32 */
/* 428 */	NdrFcShort( 0x5 ),	/* 5 */
/* 430 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter FileName */

/* 432 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 434 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 436 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter Server */

/* 438 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 440 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 442 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter Page */

/* 444 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 446 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 448 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter VirtualDir */

/* 450 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 452 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 454 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter DstFileName */

/* 456 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 458 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 460 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter bRemove */

/* 462 */	NdrFcShort( 0x48 ),	/* Flags:  in, base type, */
/* 464 */	NdrFcShort( 0x18 ),	/* x86 Stack size/offset = 24 */
/* 466 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Parameter pErrorCode */

/* 468 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 470 */	NdrFcShort( 0x1c ),	/* x86 Stack size/offset = 28 */
/* 472 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 474 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 476 */	NdrFcShort( 0x20 ),	/* x86 Stack size/offset = 32 */
/* 478 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Procedure UploadFileM */

/* 480 */	0x33,		/* FC_AUTO_HANDLE */
			0x6c,		/* Old Flags:  object, Oi2 */
/* 482 */	NdrFcLong( 0x0 ),	/* 0 */
/* 486 */	NdrFcShort( 0x11 ),	/* 17 */
/* 488 */	NdrFcShort( 0x24 ),	/* x86 Stack size/offset = 36 */
/* 490 */	NdrFcShort( 0x8 ),	/* 8 */
/* 492 */	NdrFcShort( 0x8 ),	/* 8 */
/* 494 */	0x47,		/* Oi2 Flags:  srv must size, clt must size, has return, has ext, */
			0x8,		/* 8 */
/* 496 */	0x8,		/* 8 */
			0x7,		/* Ext Flags:  new corr desc, clt corr check, srv corr check, */
/* 498 */	NdrFcShort( 0x20 ),	/* 32 */
/* 500 */	NdrFcShort( 0x5 ),	/* 5 */
/* 502 */	NdrFcShort( 0x0 ),	/* 0 */

	/* Parameter FileName */

/* 504 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 506 */	NdrFcShort( 0x4 ),	/* x86 Stack size/offset = 4 */
/* 508 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter Server */

/* 510 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 512 */	NdrFcShort( 0x8 ),	/* x86 Stack size/offset = 8 */
/* 514 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter Page */

/* 516 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 518 */	NdrFcShort( 0xc ),	/* x86 Stack size/offset = 12 */
/* 520 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter VirtualDir */

/* 522 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 524 */	NdrFcShort( 0x10 ),	/* x86 Stack size/offset = 16 */
/* 526 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter DstFileName */

/* 528 */	NdrFcShort( 0x8b ),	/* Flags:  must size, must free, in, by val, */
/* 530 */	NdrFcShort( 0x14 ),	/* x86 Stack size/offset = 20 */
/* 532 */	NdrFcShort( 0x1c ),	/* Type Offset=28 */

	/* Parameter bRemove */

/* 534 */	NdrFcShort( 0x48 ),	/* Flags:  in, base type, */
/* 536 */	NdrFcShort( 0x18 ),	/* x86 Stack size/offset = 24 */
/* 538 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

	/* Parameter pErrorCode */

/* 540 */	NdrFcShort( 0x4113 ),	/* Flags:  must size, must free, out, simple ref, srv alloc size=16 */
/* 542 */	NdrFcShort( 0x1c ),	/* x86 Stack size/offset = 28 */
/* 544 */	NdrFcShort( 0x412 ),	/* Type Offset=1042 */

	/* Return value */

/* 546 */	NdrFcShort( 0x70 ),	/* Flags:  out, return, base type, */
/* 548 */	NdrFcShort( 0x20 ),	/* x86 Stack size/offset = 32 */
/* 550 */	0x8,		/* FC_LONG */
			0x0,		/* 0 */

			0x0
        }
    };

static const MIDL_TYPE_FORMAT_STRING __MIDL_TypeFormatString =
    {
        0,
        {
			NdrFcShort( 0x0 ),	/* 0 */
/*  2 */	
			0x12, 0x0,	/* FC_UP */
/*  4 */	NdrFcShort( 0xe ),	/* Offset= 14 (18) */
/*  6 */	
			0x1b,		/* FC_CARRAY */
			0x1,		/* 1 */
/*  8 */	NdrFcShort( 0x2 ),	/* 2 */
/* 10 */	0x9,		/* Corr desc: FC_ULONG */
			0x0,		/*  */
/* 12 */	NdrFcShort( 0xfffc ),	/* -4 */
/* 14 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 16 */	0x6,		/* FC_SHORT */
			0x5b,		/* FC_END */
/* 18 */	
			0x17,		/* FC_CSTRUCT */
			0x3,		/* 3 */
/* 20 */	NdrFcShort( 0x8 ),	/* 8 */
/* 22 */	NdrFcShort( 0xfff0 ),	/* Offset= -16 (6) */
/* 24 */	0x8,		/* FC_LONG */
			0x8,		/* FC_LONG */
/* 26 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 28 */	0xb4,		/* FC_USER_MARSHAL */
			0x83,		/* 131 */
/* 30 */	NdrFcShort( 0x0 ),	/* 0 */
/* 32 */	NdrFcShort( 0x4 ),	/* 4 */
/* 34 */	NdrFcShort( 0x0 ),	/* 0 */
/* 36 */	NdrFcShort( 0xffde ),	/* Offset= -34 (2) */
/* 38 */	
			0x11, 0x4,	/* FC_RP [alloced_on_stack] */
/* 40 */	NdrFcShort( 0x3ea ),	/* Offset= 1002 (1042) */
/* 42 */	
			0x13, 0x0,	/* FC_OP */
/* 44 */	NdrFcShort( 0x3d2 ),	/* Offset= 978 (1022) */
/* 46 */	
			0x2b,		/* FC_NON_ENCAPSULATED_UNION */
			0x9,		/* FC_ULONG */
/* 48 */	0x7,		/* Corr desc: FC_USHORT */
			0x0,		/*  */
/* 50 */	NdrFcShort( 0xfff8 ),	/* -8 */
/* 52 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 54 */	NdrFcShort( 0x2 ),	/* Offset= 2 (56) */
/* 56 */	NdrFcShort( 0x10 ),	/* 16 */
/* 58 */	NdrFcShort( 0x2f ),	/* 47 */
/* 60 */	NdrFcLong( 0x14 ),	/* 20 */
/* 64 */	NdrFcShort( 0x800b ),	/* Simple arm type: FC_HYPER */
/* 66 */	NdrFcLong( 0x3 ),	/* 3 */
/* 70 */	NdrFcShort( 0x8008 ),	/* Simple arm type: FC_LONG */
/* 72 */	NdrFcLong( 0x11 ),	/* 17 */
/* 76 */	NdrFcShort( 0x8001 ),	/* Simple arm type: FC_BYTE */
/* 78 */	NdrFcLong( 0x2 ),	/* 2 */
/* 82 */	NdrFcShort( 0x8006 ),	/* Simple arm type: FC_SHORT */
/* 84 */	NdrFcLong( 0x4 ),	/* 4 */
/* 88 */	NdrFcShort( 0x800a ),	/* Simple arm type: FC_FLOAT */
/* 90 */	NdrFcLong( 0x5 ),	/* 5 */
/* 94 */	NdrFcShort( 0x800c ),	/* Simple arm type: FC_DOUBLE */
/* 96 */	NdrFcLong( 0xb ),	/* 11 */
/* 100 */	NdrFcShort( 0x8006 ),	/* Simple arm type: FC_SHORT */
/* 102 */	NdrFcLong( 0xa ),	/* 10 */
/* 106 */	NdrFcShort( 0x8008 ),	/* Simple arm type: FC_LONG */
/* 108 */	NdrFcLong( 0x6 ),	/* 6 */
/* 112 */	NdrFcShort( 0xe8 ),	/* Offset= 232 (344) */
/* 114 */	NdrFcLong( 0x7 ),	/* 7 */
/* 118 */	NdrFcShort( 0x800c ),	/* Simple arm type: FC_DOUBLE */
/* 120 */	NdrFcLong( 0x8 ),	/* 8 */
/* 124 */	NdrFcShort( 0xe2 ),	/* Offset= 226 (350) */
/* 126 */	NdrFcLong( 0xd ),	/* 13 */
/* 130 */	NdrFcShort( 0xe0 ),	/* Offset= 224 (354) */
/* 132 */	NdrFcLong( 0x9 ),	/* 9 */
/* 136 */	NdrFcShort( 0xec ),	/* Offset= 236 (372) */
/* 138 */	NdrFcLong( 0x2000 ),	/* 8192 */
/* 142 */	NdrFcShort( 0xf8 ),	/* Offset= 248 (390) */
/* 144 */	NdrFcLong( 0x24 ),	/* 36 */
/* 148 */	NdrFcShort( 0x320 ),	/* Offset= 800 (948) */
/* 150 */	NdrFcLong( 0x4024 ),	/* 16420 */
/* 154 */	NdrFcShort( 0x31a ),	/* Offset= 794 (948) */
/* 156 */	NdrFcLong( 0x4011 ),	/* 16401 */
/* 160 */	NdrFcShort( 0x318 ),	/* Offset= 792 (952) */
/* 162 */	NdrFcLong( 0x4002 ),	/* 16386 */
/* 166 */	NdrFcShort( 0x316 ),	/* Offset= 790 (956) */
/* 168 */	NdrFcLong( 0x4003 ),	/* 16387 */
/* 172 */	NdrFcShort( 0x314 ),	/* Offset= 788 (960) */
/* 174 */	NdrFcLong( 0x4014 ),	/* 16404 */
/* 178 */	NdrFcShort( 0x312 ),	/* Offset= 786 (964) */
/* 180 */	NdrFcLong( 0x4004 ),	/* 16388 */
/* 184 */	NdrFcShort( 0x310 ),	/* Offset= 784 (968) */
/* 186 */	NdrFcLong( 0x4005 ),	/* 16389 */
/* 190 */	NdrFcShort( 0x30e ),	/* Offset= 782 (972) */
/* 192 */	NdrFcLong( 0x400b ),	/* 16395 */
/* 196 */	NdrFcShort( 0x2f8 ),	/* Offset= 760 (956) */
/* 198 */	NdrFcLong( 0x400a ),	/* 16394 */
/* 202 */	NdrFcShort( 0x2f6 ),	/* Offset= 758 (960) */
/* 204 */	NdrFcLong( 0x4006 ),	/* 16390 */
/* 208 */	NdrFcShort( 0x300 ),	/* Offset= 768 (976) */
/* 210 */	NdrFcLong( 0x4007 ),	/* 16391 */
/* 214 */	NdrFcShort( 0x2f6 ),	/* Offset= 758 (972) */
/* 216 */	NdrFcLong( 0x4008 ),	/* 16392 */
/* 220 */	NdrFcShort( 0x2f8 ),	/* Offset= 760 (980) */
/* 222 */	NdrFcLong( 0x400d ),	/* 16397 */
/* 226 */	NdrFcShort( 0x2f6 ),	/* Offset= 758 (984) */
/* 228 */	NdrFcLong( 0x4009 ),	/* 16393 */
/* 232 */	NdrFcShort( 0x2f4 ),	/* Offset= 756 (988) */
/* 234 */	NdrFcLong( 0x6000 ),	/* 24576 */
/* 238 */	NdrFcShort( 0x2f2 ),	/* Offset= 754 (992) */
/* 240 */	NdrFcLong( 0x400c ),	/* 16396 */
/* 244 */	NdrFcShort( 0x2f0 ),	/* Offset= 752 (996) */
/* 246 */	NdrFcLong( 0x10 ),	/* 16 */
/* 250 */	NdrFcShort( 0x8002 ),	/* Simple arm type: FC_CHAR */
/* 252 */	NdrFcLong( 0x12 ),	/* 18 */
/* 256 */	NdrFcShort( 0x8006 ),	/* Simple arm type: FC_SHORT */
/* 258 */	NdrFcLong( 0x13 ),	/* 19 */
/* 262 */	NdrFcShort( 0x8008 ),	/* Simple arm type: FC_LONG */
/* 264 */	NdrFcLong( 0x15 ),	/* 21 */
/* 268 */	NdrFcShort( 0x800b ),	/* Simple arm type: FC_HYPER */
/* 270 */	NdrFcLong( 0x16 ),	/* 22 */
/* 274 */	NdrFcShort( 0x8008 ),	/* Simple arm type: FC_LONG */
/* 276 */	NdrFcLong( 0x17 ),	/* 23 */
/* 280 */	NdrFcShort( 0x8008 ),	/* Simple arm type: FC_LONG */
/* 282 */	NdrFcLong( 0xe ),	/* 14 */
/* 286 */	NdrFcShort( 0x2ce ),	/* Offset= 718 (1004) */
/* 288 */	NdrFcLong( 0x400e ),	/* 16398 */
/* 292 */	NdrFcShort( 0x2d2 ),	/* Offset= 722 (1014) */
/* 294 */	NdrFcLong( 0x4010 ),	/* 16400 */
/* 298 */	NdrFcShort( 0x2d0 ),	/* Offset= 720 (1018) */
/* 300 */	NdrFcLong( 0x4012 ),	/* 16402 */
/* 304 */	NdrFcShort( 0x28c ),	/* Offset= 652 (956) */
/* 306 */	NdrFcLong( 0x4013 ),	/* 16403 */
/* 310 */	NdrFcShort( 0x28a ),	/* Offset= 650 (960) */
/* 312 */	NdrFcLong( 0x4015 ),	/* 16405 */
/* 316 */	NdrFcShort( 0x288 ),	/* Offset= 648 (964) */
/* 318 */	NdrFcLong( 0x4016 ),	/* 16406 */
/* 322 */	NdrFcShort( 0x27e ),	/* Offset= 638 (960) */
/* 324 */	NdrFcLong( 0x4017 ),	/* 16407 */
/* 328 */	NdrFcShort( 0x278 ),	/* Offset= 632 (960) */
/* 330 */	NdrFcLong( 0x0 ),	/* 0 */
/* 334 */	NdrFcShort( 0x0 ),	/* Offset= 0 (334) */
/* 336 */	NdrFcLong( 0x1 ),	/* 1 */
/* 340 */	NdrFcShort( 0x0 ),	/* Offset= 0 (340) */
/* 342 */	NdrFcShort( 0xffff ),	/* Offset= -1 (341) */
/* 344 */	
			0x15,		/* FC_STRUCT */
			0x7,		/* 7 */
/* 346 */	NdrFcShort( 0x8 ),	/* 8 */
/* 348 */	0xb,		/* FC_HYPER */
			0x5b,		/* FC_END */
/* 350 */	
			0x13, 0x0,	/* FC_OP */
/* 352 */	NdrFcShort( 0xfeb2 ),	/* Offset= -334 (18) */
/* 354 */	
			0x2f,		/* FC_IP */
			0x5a,		/* FC_CONSTANT_IID */
/* 356 */	NdrFcLong( 0x0 ),	/* 0 */
/* 360 */	NdrFcShort( 0x0 ),	/* 0 */
/* 362 */	NdrFcShort( 0x0 ),	/* 0 */
/* 364 */	0xc0,		/* 192 */
			0x0,		/* 0 */
/* 366 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 368 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 370 */	0x0,		/* 0 */
			0x46,		/* 70 */
/* 372 */	
			0x2f,		/* FC_IP */
			0x5a,		/* FC_CONSTANT_IID */
/* 374 */	NdrFcLong( 0x20400 ),	/* 132096 */
/* 378 */	NdrFcShort( 0x0 ),	/* 0 */
/* 380 */	NdrFcShort( 0x0 ),	/* 0 */
/* 382 */	0xc0,		/* 192 */
			0x0,		/* 0 */
/* 384 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 386 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 388 */	0x0,		/* 0 */
			0x46,		/* 70 */
/* 390 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 392 */	NdrFcShort( 0x2 ),	/* Offset= 2 (394) */
/* 394 */	
			0x13, 0x0,	/* FC_OP */
/* 396 */	NdrFcShort( 0x216 ),	/* Offset= 534 (930) */
/* 398 */	
			0x2a,		/* FC_ENCAPSULATED_UNION */
			0x49,		/* 73 */
/* 400 */	NdrFcShort( 0x18 ),	/* 24 */
/* 402 */	NdrFcShort( 0xa ),	/* 10 */
/* 404 */	NdrFcLong( 0x8 ),	/* 8 */
/* 408 */	NdrFcShort( 0x5a ),	/* Offset= 90 (498) */
/* 410 */	NdrFcLong( 0xd ),	/* 13 */
/* 414 */	NdrFcShort( 0x7e ),	/* Offset= 126 (540) */
/* 416 */	NdrFcLong( 0x9 ),	/* 9 */
/* 420 */	NdrFcShort( 0x9e ),	/* Offset= 158 (578) */
/* 422 */	NdrFcLong( 0xc ),	/* 12 */
/* 426 */	NdrFcShort( 0xc8 ),	/* Offset= 200 (626) */
/* 428 */	NdrFcLong( 0x24 ),	/* 36 */
/* 432 */	NdrFcShort( 0x124 ),	/* Offset= 292 (724) */
/* 434 */	NdrFcLong( 0x800d ),	/* 32781 */
/* 438 */	NdrFcShort( 0x140 ),	/* Offset= 320 (758) */
/* 440 */	NdrFcLong( 0x10 ),	/* 16 */
/* 444 */	NdrFcShort( 0x15a ),	/* Offset= 346 (790) */
/* 446 */	NdrFcLong( 0x2 ),	/* 2 */
/* 450 */	NdrFcShort( 0x174 ),	/* Offset= 372 (822) */
/* 452 */	NdrFcLong( 0x3 ),	/* 3 */
/* 456 */	NdrFcShort( 0x18e ),	/* Offset= 398 (854) */
/* 458 */	NdrFcLong( 0x14 ),	/* 20 */
/* 462 */	NdrFcShort( 0x1a8 ),	/* Offset= 424 (886) */
/* 464 */	NdrFcShort( 0xffff ),	/* Offset= -1 (463) */
/* 466 */	
			0x1b,		/* FC_CARRAY */
			0x3,		/* 3 */
/* 468 */	NdrFcShort( 0x4 ),	/* 4 */
/* 470 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 472 */	NdrFcShort( 0x0 ),	/* 0 */
/* 474 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 476 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 478 */	
			0x48,		/* FC_VARIABLE_REPEAT */
			0x49,		/* FC_FIXED_OFFSET */
/* 480 */	NdrFcShort( 0x4 ),	/* 4 */
/* 482 */	NdrFcShort( 0x0 ),	/* 0 */
/* 484 */	NdrFcShort( 0x1 ),	/* 1 */
/* 486 */	NdrFcShort( 0x0 ),	/* 0 */
/* 488 */	NdrFcShort( 0x0 ),	/* 0 */
/* 490 */	0x13, 0x0,	/* FC_OP */
/* 492 */	NdrFcShort( 0xfe26 ),	/* Offset= -474 (18) */
/* 494 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 496 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 498 */	
			0x16,		/* FC_PSTRUCT */
			0x3,		/* 3 */
/* 500 */	NdrFcShort( 0x8 ),	/* 8 */
/* 502 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 504 */	
			0x46,		/* FC_NO_REPEAT */
			0x5c,		/* FC_PAD */
/* 506 */	NdrFcShort( 0x4 ),	/* 4 */
/* 508 */	NdrFcShort( 0x4 ),	/* 4 */
/* 510 */	0x11, 0x0,	/* FC_RP */
/* 512 */	NdrFcShort( 0xffd2 ),	/* Offset= -46 (466) */
/* 514 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 516 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 518 */	
			0x21,		/* FC_BOGUS_ARRAY */
			0x3,		/* 3 */
/* 520 */	NdrFcShort( 0x0 ),	/* 0 */
/* 522 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 524 */	NdrFcShort( 0x0 ),	/* 0 */
/* 526 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 528 */	NdrFcLong( 0xffffffff ),	/* -1 */
/* 532 */	NdrFcShort( 0x0 ),	/* Corr flags:  */
/* 534 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 536 */	NdrFcShort( 0xff4a ),	/* Offset= -182 (354) */
/* 538 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 540 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 542 */	NdrFcShort( 0x8 ),	/* 8 */
/* 544 */	NdrFcShort( 0x0 ),	/* 0 */
/* 546 */	NdrFcShort( 0x6 ),	/* Offset= 6 (552) */
/* 548 */	0x8,		/* FC_LONG */
			0x36,		/* FC_POINTER */
/* 550 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 552 */	
			0x11, 0x0,	/* FC_RP */
/* 554 */	NdrFcShort( 0xffdc ),	/* Offset= -36 (518) */
/* 556 */	
			0x21,		/* FC_BOGUS_ARRAY */
			0x3,		/* 3 */
/* 558 */	NdrFcShort( 0x0 ),	/* 0 */
/* 560 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 562 */	NdrFcShort( 0x0 ),	/* 0 */
/* 564 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 566 */	NdrFcLong( 0xffffffff ),	/* -1 */
/* 570 */	NdrFcShort( 0x0 ),	/* Corr flags:  */
/* 572 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 574 */	NdrFcShort( 0xff36 ),	/* Offset= -202 (372) */
/* 576 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 578 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 580 */	NdrFcShort( 0x8 ),	/* 8 */
/* 582 */	NdrFcShort( 0x0 ),	/* 0 */
/* 584 */	NdrFcShort( 0x6 ),	/* Offset= 6 (590) */
/* 586 */	0x8,		/* FC_LONG */
			0x36,		/* FC_POINTER */
/* 588 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 590 */	
			0x11, 0x0,	/* FC_RP */
/* 592 */	NdrFcShort( 0xffdc ),	/* Offset= -36 (556) */
/* 594 */	
			0x1b,		/* FC_CARRAY */
			0x3,		/* 3 */
/* 596 */	NdrFcShort( 0x4 ),	/* 4 */
/* 598 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 600 */	NdrFcShort( 0x0 ),	/* 0 */
/* 602 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 604 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 606 */	
			0x48,		/* FC_VARIABLE_REPEAT */
			0x49,		/* FC_FIXED_OFFSET */
/* 608 */	NdrFcShort( 0x4 ),	/* 4 */
/* 610 */	NdrFcShort( 0x0 ),	/* 0 */
/* 612 */	NdrFcShort( 0x1 ),	/* 1 */
/* 614 */	NdrFcShort( 0x0 ),	/* 0 */
/* 616 */	NdrFcShort( 0x0 ),	/* 0 */
/* 618 */	0x13, 0x0,	/* FC_OP */
/* 620 */	NdrFcShort( 0x192 ),	/* Offset= 402 (1022) */
/* 622 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 624 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 626 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 628 */	NdrFcShort( 0x8 ),	/* 8 */
/* 630 */	NdrFcShort( 0x0 ),	/* 0 */
/* 632 */	NdrFcShort( 0x6 ),	/* Offset= 6 (638) */
/* 634 */	0x8,		/* FC_LONG */
			0x36,		/* FC_POINTER */
/* 636 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 638 */	
			0x11, 0x0,	/* FC_RP */
/* 640 */	NdrFcShort( 0xffd2 ),	/* Offset= -46 (594) */
/* 642 */	
			0x2f,		/* FC_IP */
			0x5a,		/* FC_CONSTANT_IID */
/* 644 */	NdrFcLong( 0x2f ),	/* 47 */
/* 648 */	NdrFcShort( 0x0 ),	/* 0 */
/* 650 */	NdrFcShort( 0x0 ),	/* 0 */
/* 652 */	0xc0,		/* 192 */
			0x0,		/* 0 */
/* 654 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 656 */	0x0,		/* 0 */
			0x0,		/* 0 */
/* 658 */	0x0,		/* 0 */
			0x46,		/* 70 */
/* 660 */	
			0x1b,		/* FC_CARRAY */
			0x0,		/* 0 */
/* 662 */	NdrFcShort( 0x1 ),	/* 1 */
/* 664 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 666 */	NdrFcShort( 0x4 ),	/* 4 */
/* 668 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 670 */	0x1,		/* FC_BYTE */
			0x5b,		/* FC_END */
/* 672 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 674 */	NdrFcShort( 0x10 ),	/* 16 */
/* 676 */	NdrFcShort( 0x0 ),	/* 0 */
/* 678 */	NdrFcShort( 0xa ),	/* Offset= 10 (688) */
/* 680 */	0x8,		/* FC_LONG */
			0x8,		/* FC_LONG */
/* 682 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 684 */	NdrFcShort( 0xffd6 ),	/* Offset= -42 (642) */
/* 686 */	0x36,		/* FC_POINTER */
			0x5b,		/* FC_END */
/* 688 */	
			0x13, 0x0,	/* FC_OP */
/* 690 */	NdrFcShort( 0xffe2 ),	/* Offset= -30 (660) */
/* 692 */	
			0x1b,		/* FC_CARRAY */
			0x3,		/* 3 */
/* 694 */	NdrFcShort( 0x4 ),	/* 4 */
/* 696 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 698 */	NdrFcShort( 0x0 ),	/* 0 */
/* 700 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 702 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 704 */	
			0x48,		/* FC_VARIABLE_REPEAT */
			0x49,		/* FC_FIXED_OFFSET */
/* 706 */	NdrFcShort( 0x4 ),	/* 4 */
/* 708 */	NdrFcShort( 0x0 ),	/* 0 */
/* 710 */	NdrFcShort( 0x1 ),	/* 1 */
/* 712 */	NdrFcShort( 0x0 ),	/* 0 */
/* 714 */	NdrFcShort( 0x0 ),	/* 0 */
/* 716 */	0x13, 0x0,	/* FC_OP */
/* 718 */	NdrFcShort( 0xffd2 ),	/* Offset= -46 (672) */
/* 720 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 722 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 724 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 726 */	NdrFcShort( 0x8 ),	/* 8 */
/* 728 */	NdrFcShort( 0x0 ),	/* 0 */
/* 730 */	NdrFcShort( 0x6 ),	/* Offset= 6 (736) */
/* 732 */	0x8,		/* FC_LONG */
			0x36,		/* FC_POINTER */
/* 734 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 736 */	
			0x11, 0x0,	/* FC_RP */
/* 738 */	NdrFcShort( 0xffd2 ),	/* Offset= -46 (692) */
/* 740 */	
			0x1d,		/* FC_SMFARRAY */
			0x0,		/* 0 */
/* 742 */	NdrFcShort( 0x8 ),	/* 8 */
/* 744 */	0x1,		/* FC_BYTE */
			0x5b,		/* FC_END */
/* 746 */	
			0x15,		/* FC_STRUCT */
			0x3,		/* 3 */
/* 748 */	NdrFcShort( 0x10 ),	/* 16 */
/* 750 */	0x8,		/* FC_LONG */
			0x6,		/* FC_SHORT */
/* 752 */	0x6,		/* FC_SHORT */
			0x4c,		/* FC_EMBEDDED_COMPLEX */
/* 754 */	0x0,		/* 0 */
			NdrFcShort( 0xfff1 ),	/* Offset= -15 (740) */
			0x5b,		/* FC_END */
/* 758 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 760 */	NdrFcShort( 0x18 ),	/* 24 */
/* 762 */	NdrFcShort( 0x0 ),	/* 0 */
/* 764 */	NdrFcShort( 0xa ),	/* Offset= 10 (774) */
/* 766 */	0x8,		/* FC_LONG */
			0x36,		/* FC_POINTER */
/* 768 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 770 */	NdrFcShort( 0xffe8 ),	/* Offset= -24 (746) */
/* 772 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 774 */	
			0x11, 0x0,	/* FC_RP */
/* 776 */	NdrFcShort( 0xfefe ),	/* Offset= -258 (518) */
/* 778 */	
			0x1b,		/* FC_CARRAY */
			0x0,		/* 0 */
/* 780 */	NdrFcShort( 0x1 ),	/* 1 */
/* 782 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 784 */	NdrFcShort( 0x0 ),	/* 0 */
/* 786 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 788 */	0x1,		/* FC_BYTE */
			0x5b,		/* FC_END */
/* 790 */	
			0x16,		/* FC_PSTRUCT */
			0x3,		/* 3 */
/* 792 */	NdrFcShort( 0x8 ),	/* 8 */
/* 794 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 796 */	
			0x46,		/* FC_NO_REPEAT */
			0x5c,		/* FC_PAD */
/* 798 */	NdrFcShort( 0x4 ),	/* 4 */
/* 800 */	NdrFcShort( 0x4 ),	/* 4 */
/* 802 */	0x13, 0x0,	/* FC_OP */
/* 804 */	NdrFcShort( 0xffe6 ),	/* Offset= -26 (778) */
/* 806 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 808 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 810 */	
			0x1b,		/* FC_CARRAY */
			0x1,		/* 1 */
/* 812 */	NdrFcShort( 0x2 ),	/* 2 */
/* 814 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 816 */	NdrFcShort( 0x0 ),	/* 0 */
/* 818 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 820 */	0x6,		/* FC_SHORT */
			0x5b,		/* FC_END */
/* 822 */	
			0x16,		/* FC_PSTRUCT */
			0x3,		/* 3 */
/* 824 */	NdrFcShort( 0x8 ),	/* 8 */
/* 826 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 828 */	
			0x46,		/* FC_NO_REPEAT */
			0x5c,		/* FC_PAD */
/* 830 */	NdrFcShort( 0x4 ),	/* 4 */
/* 832 */	NdrFcShort( 0x4 ),	/* 4 */
/* 834 */	0x13, 0x0,	/* FC_OP */
/* 836 */	NdrFcShort( 0xffe6 ),	/* Offset= -26 (810) */
/* 838 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 840 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 842 */	
			0x1b,		/* FC_CARRAY */
			0x3,		/* 3 */
/* 844 */	NdrFcShort( 0x4 ),	/* 4 */
/* 846 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 848 */	NdrFcShort( 0x0 ),	/* 0 */
/* 850 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 852 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 854 */	
			0x16,		/* FC_PSTRUCT */
			0x3,		/* 3 */
/* 856 */	NdrFcShort( 0x8 ),	/* 8 */
/* 858 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 860 */	
			0x46,		/* FC_NO_REPEAT */
			0x5c,		/* FC_PAD */
/* 862 */	NdrFcShort( 0x4 ),	/* 4 */
/* 864 */	NdrFcShort( 0x4 ),	/* 4 */
/* 866 */	0x13, 0x0,	/* FC_OP */
/* 868 */	NdrFcShort( 0xffe6 ),	/* Offset= -26 (842) */
/* 870 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 872 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 874 */	
			0x1b,		/* FC_CARRAY */
			0x7,		/* 7 */
/* 876 */	NdrFcShort( 0x8 ),	/* 8 */
/* 878 */	0x19,		/* Corr desc:  field pointer, FC_ULONG */
			0x0,		/*  */
/* 880 */	NdrFcShort( 0x0 ),	/* 0 */
/* 882 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 884 */	0xb,		/* FC_HYPER */
			0x5b,		/* FC_END */
/* 886 */	
			0x16,		/* FC_PSTRUCT */
			0x3,		/* 3 */
/* 888 */	NdrFcShort( 0x8 ),	/* 8 */
/* 890 */	
			0x4b,		/* FC_PP */
			0x5c,		/* FC_PAD */
/* 892 */	
			0x46,		/* FC_NO_REPEAT */
			0x5c,		/* FC_PAD */
/* 894 */	NdrFcShort( 0x4 ),	/* 4 */
/* 896 */	NdrFcShort( 0x4 ),	/* 4 */
/* 898 */	0x13, 0x0,	/* FC_OP */
/* 900 */	NdrFcShort( 0xffe6 ),	/* Offset= -26 (874) */
/* 902 */	
			0x5b,		/* FC_END */

			0x8,		/* FC_LONG */
/* 904 */	0x8,		/* FC_LONG */
			0x5b,		/* FC_END */
/* 906 */	
			0x15,		/* FC_STRUCT */
			0x3,		/* 3 */
/* 908 */	NdrFcShort( 0x8 ),	/* 8 */
/* 910 */	0x8,		/* FC_LONG */
			0x8,		/* FC_LONG */
/* 912 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 914 */	
			0x1b,		/* FC_CARRAY */
			0x3,		/* 3 */
/* 916 */	NdrFcShort( 0x8 ),	/* 8 */
/* 918 */	0x7,		/* Corr desc: FC_USHORT */
			0x0,		/*  */
/* 920 */	NdrFcShort( 0xffd8 ),	/* -40 */
/* 922 */	NdrFcShort( 0x1 ),	/* Corr flags:  early, */
/* 924 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 926 */	NdrFcShort( 0xffec ),	/* Offset= -20 (906) */
/* 928 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 930 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x3,		/* 3 */
/* 932 */	NdrFcShort( 0x28 ),	/* 40 */
/* 934 */	NdrFcShort( 0xffec ),	/* Offset= -20 (914) */
/* 936 */	NdrFcShort( 0x0 ),	/* Offset= 0 (936) */
/* 938 */	0x6,		/* FC_SHORT */
			0x6,		/* FC_SHORT */
/* 940 */	0x8,		/* FC_LONG */
			0x8,		/* FC_LONG */
/* 942 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 944 */	NdrFcShort( 0xfdde ),	/* Offset= -546 (398) */
/* 946 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 948 */	
			0x13, 0x0,	/* FC_OP */
/* 950 */	NdrFcShort( 0xfeea ),	/* Offset= -278 (672) */
/* 952 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 954 */	0x1,		/* FC_BYTE */
			0x5c,		/* FC_PAD */
/* 956 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 958 */	0x6,		/* FC_SHORT */
			0x5c,		/* FC_PAD */
/* 960 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 962 */	0x8,		/* FC_LONG */
			0x5c,		/* FC_PAD */
/* 964 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 966 */	0xb,		/* FC_HYPER */
			0x5c,		/* FC_PAD */
/* 968 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 970 */	0xa,		/* FC_FLOAT */
			0x5c,		/* FC_PAD */
/* 972 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 974 */	0xc,		/* FC_DOUBLE */
			0x5c,		/* FC_PAD */
/* 976 */	
			0x13, 0x0,	/* FC_OP */
/* 978 */	NdrFcShort( 0xfd86 ),	/* Offset= -634 (344) */
/* 980 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 982 */	NdrFcShort( 0xfd88 ),	/* Offset= -632 (350) */
/* 984 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 986 */	NdrFcShort( 0xfd88 ),	/* Offset= -632 (354) */
/* 988 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 990 */	NdrFcShort( 0xfd96 ),	/* Offset= -618 (372) */
/* 992 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 994 */	NdrFcShort( 0xfda4 ),	/* Offset= -604 (390) */
/* 996 */	
			0x13, 0x10,	/* FC_OP [pointer_deref] */
/* 998 */	NdrFcShort( 0x2 ),	/* Offset= 2 (1000) */
/* 1000 */	
			0x13, 0x0,	/* FC_OP */
/* 1002 */	NdrFcShort( 0x14 ),	/* Offset= 20 (1022) */
/* 1004 */	
			0x15,		/* FC_STRUCT */
			0x7,		/* 7 */
/* 1006 */	NdrFcShort( 0x10 ),	/* 16 */
/* 1008 */	0x6,		/* FC_SHORT */
			0x1,		/* FC_BYTE */
/* 1010 */	0x1,		/* FC_BYTE */
			0x8,		/* FC_LONG */
/* 1012 */	0xb,		/* FC_HYPER */
			0x5b,		/* FC_END */
/* 1014 */	
			0x13, 0x0,	/* FC_OP */
/* 1016 */	NdrFcShort( 0xfff4 ),	/* Offset= -12 (1004) */
/* 1018 */	
			0x13, 0x8,	/* FC_OP [simple_pointer] */
/* 1020 */	0x2,		/* FC_CHAR */
			0x5c,		/* FC_PAD */
/* 1022 */	
			0x1a,		/* FC_BOGUS_STRUCT */
			0x7,		/* 7 */
/* 1024 */	NdrFcShort( 0x20 ),	/* 32 */
/* 1026 */	NdrFcShort( 0x0 ),	/* 0 */
/* 1028 */	NdrFcShort( 0x0 ),	/* Offset= 0 (1028) */
/* 1030 */	0x8,		/* FC_LONG */
			0x8,		/* FC_LONG */
/* 1032 */	0x6,		/* FC_SHORT */
			0x6,		/* FC_SHORT */
/* 1034 */	0x6,		/* FC_SHORT */
			0x6,		/* FC_SHORT */
/* 1036 */	0x4c,		/* FC_EMBEDDED_COMPLEX */
			0x0,		/* 0 */
/* 1038 */	NdrFcShort( 0xfc20 ),	/* Offset= -992 (46) */
/* 1040 */	0x5c,		/* FC_PAD */
			0x5b,		/* FC_END */
/* 1042 */	0xb4,		/* FC_USER_MARSHAL */
			0x83,		/* 131 */
/* 1044 */	NdrFcShort( 0x1 ),	/* 1 */
/* 1046 */	NdrFcShort( 0x10 ),	/* 16 */
/* 1048 */	NdrFcShort( 0x0 ),	/* 0 */
/* 1050 */	NdrFcShort( 0xfc10 ),	/* Offset= -1008 (42) */

			0x0
        }
    };

static const USER_MARSHAL_ROUTINE_QUADRUPLE UserMarshalRoutines[ WIRE_MARSHAL_TABLE_SIZE ] = 
        {
            
            {
            BSTR_UserSize
            ,BSTR_UserMarshal
            ,BSTR_UserUnmarshal
            ,BSTR_UserFree
            },
            {
            VARIANT_UserSize
            ,VARIANT_UserMarshal
            ,VARIANT_UserUnmarshal
            ,VARIANT_UserFree
            }

        };



/* Object interface: IUnknown, ver. 0.0,
   GUID={0x00000000,0x0000,0x0000,{0xC0,0x00,0x00,0x00,0x00,0x00,0x00,0x46}} */


/* Object interface: IDispatch, ver. 0.0,
   GUID={0x00020400,0x0000,0x0000,{0xC0,0x00,0x00,0x00,0x00,0x00,0x00,0x46}} */


/* Object interface: IUpload, ver. 0.0,
   GUID={0x82C12E1E,0x9452,0x4186,{0xB9,0xBB,0x66,0xC0,0x32,0xE0,0x95,0xB5}} */

#pragma code_seg(".orpc")
static const unsigned short IUpload_FormatStringOffsetTable[] =
    {
    (unsigned short) -1,
    (unsigned short) -1,
    (unsigned short) -1,
    (unsigned short) -1,
    0,
    60,
    102,
    156,
    204,
    240,
    276,
    312,
    360,
    408,
    480
    };

static const MIDL_STUBLESS_PROXY_INFO IUpload_ProxyInfo =
    {
    &Object_StubDesc,
    __MIDL_ProcFormatString.Format,
    &IUpload_FormatStringOffsetTable[-3],
    0,
    0,
    0
    };


static const MIDL_SERVER_INFO IUpload_ServerInfo = 
    {
    &Object_StubDesc,
    0,
    __MIDL_ProcFormatString.Format,
    &IUpload_FormatStringOffsetTable[-3],
    0,
    0,
    0,
    0};
CINTERFACE_PROXY_VTABLE(18) _IUploadProxyVtbl = 
{
    &IUpload_ProxyInfo,
    &IID_IUpload,
    IUnknown_QueryInterface_Proxy,
    IUnknown_AddRef_Proxy,
    IUnknown_Release_Proxy ,
    0 /* (void *) (INT_PTR) -1 /* IDispatch::GetTypeInfoCount */ ,
    0 /* (void *) (INT_PTR) -1 /* IDispatch::GetTypeInfo */ ,
    0 /* (void *) (INT_PTR) -1 /* IDispatch::GetIDsOfNames */ ,
    0 /* IDispatch_Invoke_Proxy */ ,
    (void *) (INT_PTR) -1 /* IUpload::UploadFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::SelectFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::DownloadFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::CallURL */ ,
    (void *) (INT_PTR) -1 /* IUpload::SelectDir */ ,
    (void *) (INT_PTR) -1 /* IUpload::DeleteFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::GetTempPath */ ,
    (void *) (INT_PTR) -1 /* IUpload::PUTFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::GETFile */ ,
    (void *) (INT_PTR) -1 /* IUpload::UploadFileX */ ,
    (void *) (INT_PTR) -1 /* IUpload::UploadFileM */
};


static const PRPC_STUB_FUNCTION IUpload_table[] =
{
    STUB_FORWARDING_FUNCTION,
    STUB_FORWARDING_FUNCTION,
    STUB_FORWARDING_FUNCTION,
    STUB_FORWARDING_FUNCTION,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2,
    NdrStubCall2
};

CInterfaceStubVtbl _IUploadStubVtbl =
{
    &IID_IUpload,
    &IUpload_ServerInfo,
    18,
    &IUpload_table[-3],
    CStdStubBuffer_DELEGATING_METHODS
};

static const MIDL_STUB_DESC Object_StubDesc = 
    {
    0,
    NdrOleAllocate,
    NdrOleFree,
    0,
    0,
    0,
    0,
    0,
    __MIDL_TypeFormatString.Format,
    1, /* -error bounds_check flag */
    0x50002, /* Ndr library version */
    0,
    0x600016e, /* MIDL Version 6.0.366 */
    0,
    UserMarshalRoutines,
    0,  /* notify & notify_flag routine table */
    0x1, /* MIDL flag */
    0, /* cs routines */
    0,   /* proxy/server info */
    0   /* Reserved5 */
    };

const CInterfaceProxyVtbl * _iDocPost_ProxyVtblList[] = 
{
    ( CInterfaceProxyVtbl *) &_IUploadProxyVtbl,
    0
};

const CInterfaceStubVtbl * _iDocPost_StubVtblList[] = 
{
    ( CInterfaceStubVtbl *) &_IUploadStubVtbl,
    0
};

PCInterfaceName const _iDocPost_InterfaceNamesList[] = 
{
    "IUpload",
    0
};

const IID *  _iDocPost_BaseIIDList[] = 
{
    &IID_IDispatch,
    0
};


#define _iDocPost_CHECK_IID(n)	IID_GENERIC_CHECK_IID( _iDocPost, pIID, n)

int __stdcall _iDocPost_IID_Lookup( const IID * pIID, int * pIndex )
{
    
    if(!_iDocPost_CHECK_IID(0))
        {
        *pIndex = 0;
        return 1;
        }

    return 0;
}

const ExtendedProxyFileInfo iDocPost_ProxyFileInfo = 
{
    (PCInterfaceProxyVtblList *) & _iDocPost_ProxyVtblList,
    (PCInterfaceStubVtblList *) & _iDocPost_StubVtblList,
    (const PCInterfaceName * ) & _iDocPost_InterfaceNamesList,
    (const IID ** ) & _iDocPost_BaseIIDList,
    & _iDocPost_IID_Lookup, 
    1,
    2,
    0, /* table of [async_uuid] interfaces */
    0, /* Filler1 */
    0, /* Filler2 */
    0  /* Filler3 */
};
#pragma optimize("", on )
#if _MSC_VER >= 1200
#pragma warning(pop)
#endif


#endif /* !defined(_M_IA64) && !defined(_M_AMD64)*/

