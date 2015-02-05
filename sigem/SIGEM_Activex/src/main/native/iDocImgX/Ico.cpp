
#include "stdafx.h"
#include "IDocImgX.h"
#include <afxext.h>
#include <windowsx.h>

#include "iDocGear.h"
#include "ico.h"
#include "AuxFnc.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

#define ERR_FILE      2L

ICIco::ICIco()
{
   m_hAccAND = 0;
   m_hIGear = 0;
}

ICIco::~ICIco()
{
   Term();
}

LONG ICIco::Init(LPCSTR IcoFileName)
{

   LONG             Err   = 0;
   HANDLE           fd;
   UINT             Ret;
   WORD             AuxWord;
   BYTE             W,H,NumClrs,AuxByte;
   DWORD            XORSize,ANDSize,AuxDWord;
   BITMAPINFOHEADER IH;
   LPSTR            pXOR = NULL;
   LPSTR            pAND = NULL;
   RGBQUAD          Q;
   AT_ERRCOUNT      Errcount;
   DWORD            BytesRead;
   LPFNCreateDIB    lpfnCreateDIB = NULL;
   HINSTANCE        hModGear = _Module.m_hModGear;

   lstrcpy(m_FileName,IcoFileName);

   fd = CreateFile(m_FileName,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,
                  FILE_ATTRIBUTE_NORMAL,NULL);
   if (fd == INVALID_HANDLE_VALUE)
   {
      DWORD ret = GetLastError();
      Err = ret;
      goto End;
   }

   m_FileType = IG_FORMAT_ICO;
 

   Ret = ReadFile(fd,&AuxWord,sizeof(WORD),&BytesRead,NULL); // Reserved
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxWord,sizeof(WORD),&BytesRead,NULL); // Type
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxWord,sizeof(WORD),&BytesRead,NULL); // Count
   if (!Ret) {Err = ERR_FILE;goto End;}

   Ret = ReadFile(fd,&W,sizeof(BYTE),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&H,sizeof(BYTE),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&NumClrs,sizeof(BYTE),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}

   Ret = ReadFile(fd,&AuxByte,sizeof(BYTE),&BytesRead,NULL); // Reserved
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxWord,sizeof(WORD),&BytesRead,NULL); // Planes
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxWord,sizeof(WORD),&BytesRead,NULL); // BitCount
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxDWord,sizeof(DWORD),&BytesRead,NULL); // Size
   if (!Ret) {Err = ERR_FILE;goto End;}
   Ret = ReadFile(fd,&AuxDWord,sizeof(DWORD),&BytesRead,NULL); // Offset
   if (!Ret) {Err = ERR_FILE;goto End;}

   Ret = ReadFile(fd,&IH,sizeof(BITMAPINFOHEADER),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}

   XORSize = 40 + (4*NumClrs) + ((LONG)W/8)*IH.biBitCount*H;
   ANDSize = 40 + (4*2      ) + ((LONG)W/8)*1            *H;

   pXOR = (LPSTR)GlobalAllocPtr(GHND,XORSize);
   pAND = (LPSTR)GlobalAllocPtr(GHND,ANDSize);

   IH.biWidth  = W;
   IH.biHeight = H;
   memcpy(pXOR,&IH,40);

   Ret = ReadFile(fd,&(pXOR[40]),(UINT)(XORSize-40),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}

   IH.biBitCount  = 1;
   IH.biSizeImage = 0;
   memcpy(pAND,&IH,40);

   Q.rgbRed      = 0;
   Q.rgbGreen    = 0;
   Q.rgbBlue     = 0;
   Q.rgbReserved = 0;
   memcpy(&(pAND[40]),&Q,4);

   Q.rgbRed      = 255;
   Q.rgbGreen    = 255;
   Q.rgbBlue     = 255;
   Q.rgbReserved =   0;
   memcpy(&(pAND[44]),&Q,4);

   Ret = ReadFile(fd,&(pAND[48]),(UINT)(ANDSize-48),&BytesRead,NULL);
   if (!Ret) {Err = ERR_FILE;goto End;}  

   

   lpfnCreateDIB = (LPFNCreateDIB)GetProcAddress(hModGear,"IG_image_create_DIB");
   if (lpfnCreateDIB == NULL) {Err = -1;goto End;}

   Errcount = lpfnCreateDIB(0,0,0,(LPAT_DIB)pXOR,&m_hIGear);
   if (Errcount)
   {
      Err = GetError();
      goto End;
   }   

   Errcount = lpfnCreateDIB(0,0,0,(LPAT_DIB)pAND,&m_hAccAND);
   if (Errcount)
   {
      Err = GetError();;
      goto End;
   }
   

   End:

   if (fd != INVALID_HANDLE_VALUE)
   {
      if (!CloseHandle(fd))
      {
         if (!Err) Err = ERR_FILE;
      }
   }

   if (pXOR != NULL) GlobalFreePtr(pXOR);
   if (pAND != NULL) GlobalFreePtr(pAND);

   if (Err) Term();

   return(Err);

}

LONG ICIco::Term()
{

   LONG        Err = 0;
   AT_ERRCOUNT Errcount;
   HINSTANCE   hModGear = _Module.m_hModGear;
   LPFNDelete  lpfnDelete = NULL;

   lpfnDelete = (LPFNDelete)GetProcAddress(hModGear,"IG_image_delete");
   if (lpfnDelete == NULL) {Err = -1; goto End;}

   if (m_hAccAND > 0)
   {        
      Errcount = lpfnDelete(m_hAccAND); 
      Err = Errcount;
      
      if ( !Errcount )
         m_hAccAND = 0;
   }
	if (m_hIGear > 0)
   {        
      Errcount = lpfnDelete(m_hIGear); 
      Err = Errcount;
      
      if ( !Errcount )
         m_hIGear = 0;
   }

   End:
   
   return(Err);

}

LONG ICIco::Draw(CDC* pDC,LPCRECT /*pImgRect*/,LPCRECT pDCRect) const
{

   LONG        Err = 0;
	AT_ERRCOUNT Errcount;
   HINSTANCE   hModGear = _Module.m_hModGear;
   LPFNROPSet  lpfnROPSet = NULL;
   LPFNDesktop lpfnDesktop = NULL;
   LPFNDeviceRectSet lpfnDevRectSet = NULL;
   LPFNDisplay lpfnDisplay = NULL;
//	AT_RGB      rgb = {192,192,192};
//	AT_RGB      rgbForeground = {0,0,0};

   lpfnROPSet = (LPFNROPSet)GetProcAddress(hModGear,"IG_display_ROP_set");
   if (lpfnROPSet == NULL) {Err = -1; goto End;}

	Errcount = lpfnROPSet(m_hAccAND,SRCAND);
	if (Errcount)
	{
		Err = GetError();
		goto End;
	}

   lpfnDesktop = (LPFNDesktop)GetProcAddress(hModGear,"IG_display_desktop_pattern_set");
   if (lpfnDesktop == NULL) {Err = -1; goto End;}
	
	Errcount = lpfnDesktop(m_hAccAND,NULL,NULL,NULL,FALSE);
	
   lpfnDevRectSet = (LPFNDeviceRectSet)GetProcAddress(hModGear,"IG_device_rect_set");
   if (lpfnDevRectSet == NULL) {Err = -1; goto End;}

	lpfnDevRectSet(m_hAccAND,(LPAT_RECT)pDCRect);

   lpfnDisplay = (LPFNDisplay)GetProcAddress(hModGear,"IG_display_image");
   if (lpfnDisplay == NULL) {Err = -1; goto End;}

   Errcount = lpfnDisplay(m_hAccAND,pDC->m_hDC);
	if (Errcount)
	{
		Err = GetError();
		goto End;
	}	
   

	Errcount = lpfnROPSet(m_hIGear,SRCINVERT);
	if (Errcount)
	{
		Err = GetError();
		goto End;
	}
	Errcount = lpfnDesktop(m_hIGear,NULL,NULL,NULL,FALSE);
	
	lpfnDevRectSet(m_hIGear,(LPAT_RECT)pDCRect);

   
	Errcount = lpfnDisplay(m_hIGear,pDC->m_hDC);
	if (Errcount)
	{
		Err = GetError();
		goto End;
	}

	End:
   return(Err);

}



LONG ICIco::SetCropRect(LPCRECT pImgZRect,BOOL XOR) const
{

   LONG Err    = 0;
   AT_ERRCOUNT Errcount;
   HINSTANCE   hModGear = _Module.m_hModGear;
   LPFNImgRectSet lpfnRectSet = NULL;

   lpfnRectSet = (LPFNImgRectSet)GetProcAddress(hModGear,"IG_image_rect_set");
   if (lpfnRectSet == NULL) {Err = -1; goto End;}

   if (XOR)
	{
		Errcount = lpfnRectSet(m_hIGear,(LPAT_RECT)&pImgZRect);
		if (Errcount) Err = GetError();	
	}
   else
	{
		Errcount = lpfnRectSet(m_hAccAND,(LPAT_RECT)&pImgZRect);
		if (Errcount) Err = GetError();	
	}
   
   End:
   
   return(Err);

}

LONG ICIco::GetDims(CSize& ImgSize) const
{
   LONG Err = 0;
   int W,H;
   Err =  GetDims(W,H);
   if (!Err)
   {
      ImgSize.cx = W;
      ImgSize.cy = H;
   }
   return (Err);
}

LONG ICIco::GetDims(int& W,int& H) const
{
   LONG        Err = 0;
   AT_ERRCOUNT Errcount = IGE_SUCCESS;
   UINT        bpp;
   HINSTANCE   hModGear = _Module.m_hModGear;
   LPFNValid      lpfn = NULL;  
   LPFNDimensions lpfnDimensions = NULL;


   lpfn = (LPFNValid)GetProcAddress(hModGear,"IG_image_is_valid");
   if (lpfn == NULL) {Err = -1; goto End;}
   
   lpfnDimensions = (LPFNDimensions)GetProcAddress(hModGear,"IG_image_dimensions_get");
   if (lpfnDimensions == NULL) {Err = -1; goto End;}

   if (lpfn(m_hIGear))
   {
      Errcount = lpfnDimensions(m_hIGear,(LPAT_DIMENSION)&W,(LPAT_DIMENSION)&H,&bpp);
      if (Errcount) Err = GetError();
   }

   End:

   return (Err);
}

