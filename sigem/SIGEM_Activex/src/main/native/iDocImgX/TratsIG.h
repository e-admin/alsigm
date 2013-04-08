// Copyright (c) Informatica El Corte Ingles, S.A.
// TratsIG.h: interface for the CTratsIG class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(_TRATSIG_H_INCLUDED_)
#define _TRATSIG_H_INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef TRATSIG_EXPORT
  #define TRATSIG_EXPORT __declspec(dllimport)

  #ifdef _DEBUG
    #pragma comment(lib, "TratsIGD.lib")
  #else
    #pragma comment(lib, "TratsIG.lib")
  #endif
#endif

class TRATSIG_EXPORT CTratsIG  
{
public:
  // Aumento de contraste local
  static ULONG TratACL(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Aumento global de contraste
  static ULONG TratAGC(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Realzar tintas desvaidas
  static ULONG TratATD(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Hacer el trazo mas grueso
  static ULONG TratBold(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Eliminar el fondo del documento
  static ULONG TratEFD(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Eliminar manchas sobre texto claro
  static ULONG TratEMA(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Eliminar manchas sobre texto oscuro
  static ULONG TratEMT(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Eliminar tintas transparentadas
  static ULONG TratETT(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Hacer el trazo mas fino
  static ULONG TratLight(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
  // Relieve 
  static ULONG TratShadow(BITMAPINFOHEADER *pDIB, LPCRECT pRect = NULL); 
};

#endif
