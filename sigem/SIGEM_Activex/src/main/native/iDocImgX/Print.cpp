#include "stdafx.h"
#include "resource.h"
#include "iDocGear.h"

                                   

#ifndef WIN32
#include <print.h>		/* Definition of DEVMODE					*/
#endif                                                              
/*  
#undef EXTERN
#define EXTERN 
#define INI_EXT
*/

                
#include "printlook.h"  

#ifdef WIN32

/* this function has been deleted in the WIN32 API, simulate it here */
#define QueryAbort(_hdc, _ncode)		AbortProc(_hdc, _ncode)

#endif
  

// LPDOCINFO is now defined in 3.1's WINDOWS.H.  We're compiling under
//  both 3.0 and 3.1.  For now, we'll define our own LPDOCINFO here.
//  This is a LESS than adequate solution!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

typedef struct
   {
   short cbSize;
   LPSTR lpszDocName;
   LPSTR lpszOutput;
   }
OURDOCINFO, far * LPOURDOCINFO;  

char szGDIModule[]    = "GDI";         // Module name for GDI in Win31.
char szStartDoc[]     = "StartDoc";    // StartDoc() function in GDI.
char szSetAbortProc[] = "SetAbortProc";// SetAbortProc() function in GDI.
char szStartPage[]    = "StartPage";   // StartPage() function in GDI.
char szEndPage[]      = "EndPage";     // EndPage function in GDI.
char szEndDoc[]       = "EndDoc";      // EndDoc function in GDI.
  
// The following typedef's and string variables are used to link to
//  printing functions on-the-fly in Windows 3.1.  These API are not
//  present in Windows 3.0!  As such, we must use GetModuleHandle() and
//  GetProcAddress() to find and call them.

typedef int (FAR PASCAL *LPSTARTDOC)     (HDC, LPOURDOCINFO);
typedef int (FAR PASCAL *LPSETABORTPROC) (HDC, FARPROC);
typedef int (FAR PASCAL *LPSTARTPAGE)    (HDC);
typedef int (FAR PASCAL *LPENDPAGE)      (HDC);
typedef int (FAR PASCAL *LPENDDOC)       (HDC); 
              



/***************************************************************************/
/* Called so user can cancel the print function										*/
/***************************************************************************/

extern "C" BOOL CALLBACK AbortDlgProc(

	HWND			hWnd,
	UINT			msg,
	WPARAM		wParam,
	LPARAM		lParam
	)
{
	switch (msg)
	{
	case WM_INITDIALOG:
		SetDlgItemText(hWnd, IDC_DOCNAME, (LPSTR)lParam);
      //EnableMenuItem(GetSystemMenu(hWnd, FALSE), SC_CLOSE, MF_GRAYED);
      SetFocus(GetDlgItem(hWnd, IDCANCEL));
		return FALSE;

	case WM_COMMAND:
		switch (LOWORD(wParam))
		{
		case IDCANCEL:
			m_Abort = TRUE;
			hwndPrint = NULL;

         EnableWindow(GetParent(hWnd), TRUE);
         DestroyWindow(hWnd);
			return TRUE;
		}
	}

	return FALSE;
}




extern "C" DWORD DoStartPage (HDC hPrnDC)
{
  
   #ifndef WIN32

   if (!gbUseEscapes) {
     Win31StartPage = (LPSTARTPAGE) FindGDIFunction (szStartPage);
     if (Win31StartPage) {
       if (!Win31StartPage (hPrnDC))
         return ERR_PRN_STARTPAGE;
     }
     else
       return ERR_PRN_NOFNSTARTPAGE;
     }
   #else
     if (StartPage( hPrnDC) <= 0)
	   return ERR_PRN_STARTPAGE;
   #endif

   return ERR_PRN_NONE;
}

  

extern "C" DWORD DoEndPage (HDC hPrnDC)
{
 
   #ifndef WIN32

   if (gbUseEscapes)
      {
      if (Escape (hPrnDC, NEWFRAME, NULL, NULL, NULL) < 0)
         return ERR_PRN_NEWFRAME;
      }
   else
      {
      Win31EndPage = (LPENDPAGE) FindGDIFunction (szEndPage);
      if (Win31EndPage)
         {
         if ((err=Win31EndPage (hPrnDC)) < 0)
            return ERR_PRN_NEWFRAME;
         }
      else
         return ERR_PRN_NOFNENDPAGE;
      }

   #else
     if (EndPage( hPrnDC) <= 0)
	   return ERR_PRN_NEWFRAME;
   #endif

   return ERR_PRN_NONE;
}

 

extern "C" DWORD DoEndDoc (HDC hPrnDC)
{
  
   #ifndef WIN32
   if (gbUseEscapes)
      {
      if (Escape(hPrnDC, ENDDOC, NULL, NULL, NULL) < 0)
         return ERR_PRN_ENDDOC;
      }
   else
      {
      Win31EndDoc = (LPENDDOC) FindGDIFunction (szEndDoc);
      if (Win31EndDoc)
         {
         if (Win31EndDoc (hPrnDC) < 0)
            return ERR_PRN_ENDDOC;
         }
      else
         return ERR_PRN_NOFNENDDOC;
      }
   #else
     if (EndDoc( hPrnDC) <= 0)
	   return ERR_PRN_ENDDOC;
   #endif

   return ERR_PRN_NONE;
}



extern "C" FARPROC FindGDIFunction (LPSTR lpszFnName)
{
   HANDLE hGDI;

   hGDI = GetModuleHandle (szGDIModule);

   if (!hGDI)
      return NULL;

   return GetProcAddress ((HINSTANCE)hGDI, lpszFnName);
}




/***************************************************************************/
/* Handles the status bar																	*/
/***************************************************************************/

extern "C" BOOL ACCUAPI PrinterStatusBar(
   LPVOID            lpPrivate,		/* Private data passed in		*/
   AT_PIXPOS            cyPos,			/* Y position in the image		*/
   AT_DIMENSION			dwHeight			/* Height of the image			*/
   )
{
	UINT					nProgress;
	HDC					hDC;
	RECT					rc;
	HBRUSH				hBrush;
	HWND					hwndStatus = NULL;
	LPSTATUSINFO		lpSI;

	cyPos; /* Not used */

	lpSI = (LPSTATUSINFO)lpPrivate;

	/* cyPos contains the current line being printed.  */
	/* however, the lines are not printed in order, so */
	/* we have bump nCount every time this function is */
	/* called to help with the progress calculation.	*/
	lpSI->nCount++;

	if (!(lpSI->nCount % 5) || lpSI->nCount == (UINT)dwHeight)
	{
		/* This gets called once for every 5 raster lines */

		/* Calculate the progress percentage */
		nProgress = (UINT)((100L * (LONG)lpSI->nCount)/dwHeight);

		if (IsWindow(*lpSI->lphwndPrint))
		{
			/* Get the rectangle of the drawing area */
	//		hwndStatus = GetDlgItem(*lpSI->lphwndPrint, IDC_STATUS_BAR);
			GetWindowRect(hwndStatus, &rc);
			ScreenToClient(*lpSI->lphwndPrint, (LPPOINT)&rc.left);
			ScreenToClient(*lpSI->lphwndPrint, (LPPOINT)&rc.right);
			InflateRect(&rc, -1, -1);

			rc.right	= rc.left + (INT)(((LONG)(rc.right - rc.left) *
												 (LONG)nProgress)/100);
	
			/* Draw the status bar */
			hDC = GetDC(*lpSI->lphwndPrint);
			SetBkColor(hDC, RGB(0x00, 0x00, 0xEE));
			hBrush = CreateHatchBrush(HS_DIAGCROSS, RGB(0xAA, 0xAA, 0xAA));
			FillRect(hDC, &rc, hBrush);
			DeleteObject(hBrush);
			ReleaseDC(*lpSI->lphwndPrint, hDC);
		}

		QueryAbort(lpSI->hDC, 0);

	}

	return !(*lpSI->lpAbort);
}
             
             
/***************************************************************************/
/* Processes messages intended for Abort dialog box								*/
/***************************************************************************/

extern "C" BOOL FAR PASCAL AbortProc(

	HDC			hDC,
	INT			nCode
	)
{
	MSG			msg;

	nCode, hDC; /* Not Used */

	if (!m_Abort && PeekMessage(&msg, NULL, 0, 0, PM_REMOVE))
	{
		if (!hwndPrint || !IsDialogMessage(hwndPrint, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return !m_Abort;
}