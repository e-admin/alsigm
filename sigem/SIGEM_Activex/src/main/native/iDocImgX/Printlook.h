
/* information needed by the status bar callback */
typedef struct
{
	HWND FAR		*lphwndPrint;
	LPBOOL		lpAbort;
	UINT			nCount;
	HWND			hWnd;
	HDC			hDC;
}
STATUSINFO, FAR *LPSTATUSINFO;


#define ERR_PRN_NONE             0x00000000   // No error -- everything's A-OK!
#define ERR_PRN_NODIB            0x00000001   // No DIB specified.
#define ERR_PRN_NODC             0x00000002   // Couldn't get printer's DC.
#define ERR_PRN_CANTBAND         0x00000004   // NEXTBAND not supported by printer.
#define ERR_PRN_BANDINFO         0x00000008   // Error on BANDINFO escape.
#define ERR_PRN_SETDIBITSTODEV   0x00000010   // Error in call to SetDIBitsToDevice().
#define ERR_PRN_STRETCHDIBITS    0x00000020   // Error in call to StretchDIBits().
#define ERR_PRN_STARTDOC         0x00000040   // Error in STARTDOC escape.
#define ERR_PRN_SETABORTPROC     0x00000080   // Error in SETABORTPROC escape.
#define ERR_PRN_STARTPAGE        0x00000100   // Error in StartPage().
#define ERR_PRN_NEWFRAME         0x00000200   // Error in NEWFRAME or EndPage().
#define ERR_PRN_ENDDOC           0x00000400   // Error in ENDDOC or EndDoc().
#define ERR_PRN_NEXTBAND         0x00000800   // Error in NEXTBAND.
#define ERR_PRN_NOFNSTARTDOC     0x00001000   // Error finding StartDoc() in GDI.
#define ERR_PRN_NOFNSETABORTPROC 0x00002000   // Error finding SetAbortProc() in GDI.
#define ERR_PRN_NOFNSTARTPAGE    0x00004000   // Error finding StartPage() in GDI.
#define ERR_PRN_NOFNENDPAGE      0x00008000   // Error finding EndPage() in GDI.
#define ERR_PRN_NOFNENDDOC       0x00010000   // Error findind EndDoc() in GDI.
  
              
extern "C" BOOL FAR PASCAL AbortProc( HDC hDC, INT	nCode);
extern "C" BOOL CALLBACK AbortDlgProc( HWND hWnd, UINT msg,	WPARAM wParam, LPARAM lParam);
extern "C" BOOL ACCUAPI PrinterStatusBar( LPVOID lpPrivate, AT_PIXPOS cyPos, AT_DIMENSION dwHeight);         

extern "C" DWORD DoStartPage (HDC hPrnDC);
extern "C" DWORD DoEndPage (HDC hPrnDC);     
extern "C" DWORD DoEndDoc (HDC hPrnDC); 
extern "C" FARPROC FindGDIFunction (LPSTR lpszFnName);  
  
  
EXTERN BOOL	m_Abort;  /* Print abort flag					*/      
EXTERN BOOL gbUseEscapes 
#ifdef INI_EXT
= FALSE;        // Use Escape() or 3.1 printer APIs?
#else
;
#endif
EXTERN HWND	hwndPrint;				  /* Handle of abort dialog box		*/ 

