
#include "stdafx.h"
#include "ifont.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

IMPLEMENT_DYNCREATE(ICFont,CFont)

ICFont::ICFont()
:CFont(),m_Name()
{
   m_Size = 0L;
   m_Enh  = 0L;
}

ICFont::ICFont(LPCSTR Name,LONG Size,LONG Enh,CWnd* pWnd)
:CFont(),m_Name(Name)
{
   m_Size = Size;
   m_Enh  = Enh;
   MakeFont(pWnd);
}

ICFont::ICFont(LPCSTR Name,LONG Size,LONG Enh,CDC* pDC)
:CFont(),m_Name(Name)
{
   m_Size = Size;
   m_Enh  = Enh;
   MakeFont(pDC);
}

void ICFont::MakeFont(CWnd* pWnd)
{
   HDC  hDC = ::GetDC(pWnd->GetSafeHwnd());
   CDC* pDC = CDC::FromHandle(hDC);

   MakeFont(pDC);

   ::ReleaseDC(pWnd->GetSafeHwnd(),hDC);
}

void ICFont::MakeFont(CDC* pDC)
{
   BOOL Italic = FALSE,Underline = FALSE,Strikeout = FALSE;
   int  Height = IPointSizeToCharHeight((int)m_Size,pDC);
   int  Weight = FW_NORMAL;

   if (IsBold()     ) {Weight = FW_BOLD;}
   if (IsItalic()   ) {Italic = TRUE;}
   if (IsUnderline()) {Underline = TRUE;}
   if (IsStrikeout()) {Strikeout = TRUE;}


   CreateFont( Height,
               0,
               0,
               0,
               Weight,
         (BYTE)Italic,
         (BYTE)Underline,
         (BYTE)Strikeout,
               DEFAULT_CHARSET,
               OUT_DEFAULT_PRECIS,
               CLIP_DEFAULT_PRECIS,
               DEFAULT_QUALITY,
               DEFAULT_PITCH,
        (LPCSTR)m_Name);

}


ICFont::~ICFont()
{
   Term();
}

void ICFont::Term()
{
   m_Name.Empty();
   m_Size = 0L;
   m_Enh  = 0L;
}

ICFont::ICFont(const CFont& Other,CWnd* pWnd)
{
   ConvertFontToICFont(Other,pWnd);
}

ICFont::ICFont(const ICFont& Other)
{
   LOGFONT LogFont;

   Other.GetObject(sizeof(LOGFONT),&LogFont);
   CreateFontIndirect(&LogFont);

   m_Name = Other.m_Name;
   m_Size = Other.m_Size;
   m_Enh  = Other.m_Enh;
}

ICFont& ICFont::operator=(const ICFont& Other)
{
   LOGFONT LogFont;
   if (&Other != this)
   {

      DeleteObject();

      Other.GetObject(sizeof(LOGFONT),&LogFont);
      CreateFontIndirect(&LogFont);

      m_Name = Other.m_Name;
      m_Size = Other.m_Size;
      m_Enh  = Other.m_Enh;
   }

   return(*this);
}

LONG ICFont::GetSize() const
{
   return(m_Size);
}

LONG ICFont::GetEnh() const
{
   return(m_Enh);
}


void ICFont::GetName(CString& Name) const
{
   Name = m_Name;
}



BOOL ICFont::IsBold() const
{
   if (m_Enh & IFE_BOLD)
      return(TRUE);
   else
      return(FALSE);
}

BOOL ICFont::IsItalic() const
{
   if (m_Enh & IFE_ITALIC)
      return(TRUE);
   else
      return(FALSE);
}

BOOL ICFont::IsUnderline() const
{
   if (m_Enh & IFE_UNDERLINE)
      return(TRUE);
   else
      return(FALSE);
}

BOOL ICFont::IsStrikeout() const
{
   if (m_Enh & IFE_STRIKEOUT)
      return(TRUE);
   else
      return(FALSE);
}

void ICFont::ConvertFontToICFont(const CFont& Other,CWnd* pWnd)
{
   HDC  hDC = ::GetDC(pWnd->GetSafeHwnd());
   CDC* pDC = CDC::FromHandle(hDC);

   ConvertFontToICFont(Other,pDC);
}


void ICFont::ConvertFontToICFont(const CFont& Other,CDC* pDC)
{

   LOGFONT LogFont;

   Other.GetObject(sizeof(LOGFONT),&LogFont);
   CreateFontIndirect(&LogFont);

   m_Enh = IFE_NONE;

   if (LogFont.lfWeight == FW_BOLD)
      m_Enh = IFE_BOLD;

   if (LogFont.lfItalic)
   {
      if (m_Enh == IFE_NONE)
         m_Enh = IFE_ITALIC;
      else
         m_Enh = m_Enh|IFE_ITALIC;
   }

   if (LogFont.lfUnderline)
   {
      if (m_Enh == IFE_NONE)
         m_Enh = IFE_UNDERLINE;
      else
         m_Enh = m_Enh|IFE_UNDERLINE;
   }

   if (LogFont.lfStrikeOut)
   {
      if (m_Enh == IFE_NONE)
         m_Enh = IFE_STRIKEOUT;
      else
         m_Enh = m_Enh|IFE_STRIKEOUT;
   }

   m_Name = LogFont.lfFaceName;
   m_Size = ICharHeightToPointSize(LogFont.lfHeight,pDC);
}

int WINAPI IPointSizeToCharHeight(int PointSize,CDC* pDC)
{
   int Height;

   Height = - (int)( PointSize * (pDC->GetDeviceCaps(LOGPIXELSY)/(double)72) );

   return(Height);
}

int WINAPI ICharHeightToPointSize(int Height,CDC* pDC)
{
   double Size;
   int    Aux;

   Size = (Height * (double)72) / (double)pDC->GetDeviceCaps(LOGPIXELSY);

   if (Size < 0)
      Size = -Size;

   Aux = (int)Size;

   if ( Size-Aux >= 0.5 )
      Size = Aux + 1;
   else
      Size = Aux;

   return((int)Size);
}

void WINAPI DlgUnitsToPixels(CRect& Rect,CFont* pFont)
{

   HDC    hDC;
   CDC    DC;
   CFont* pOldFont;
   CSize  Sz;
   int    X,Y;

   hDC = GetDC(NULL);
   DC.Attach(hDC);
   pOldFont = DC.SelectObject(pFont);

   Sz = DC.GetTextExtent("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",52);

   X = (Sz.cx/26 + 1) / 2;
   Y = Sz.cy;

   Rect.left   = (Rect.left   * X) / 4;
   Rect.top    = (Rect.top    * Y) / 8;
   Rect.right  = (Rect.right  * X) / 4;
   Rect.bottom = (Rect.bottom * Y) / 8;

   DC.SelectObject(pOldFont);
   DC.Detach();
   ReleaseDC(NULL,hDC);

}

void WINAPI DlgUnitsToPixels(short& Num,BOOL Horizontal,CFont FAR* pFont)
{
   CRect RectAux(0,0,(int)Num,(int)Num);

   DlgUnitsToPixels(RectAux,pFont);

   if (Horizontal)
      Num = (short)RectAux.right;
   else
      Num = (short)RectAux.bottom;
}

void WINAPI PixelsToDlgUnits(CRect& Rect,CFont* pFont)
{

   HDC    hDC;
   CDC    DC;
   CFont* pOldFont;
   CSize  Sz;
   int    X,Y;

   hDC = GetDC(NULL);
   DC.Attach(hDC);
   pOldFont = DC.SelectObject(pFont);

   Sz = DC.GetTextExtent("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",52);

   X = (Sz.cx/26 + 1) / 2;
   Y = Sz.cy;

   Rect.left   = (Rect.left   * 4) / X;
   Rect.top    = (Rect.top    * 8) / Y;
   Rect.right  = (Rect.right  * 4) / X;
   Rect.bottom = (Rect.bottom * 8) / Y;

   DC.SelectObject(pOldFont);
   DC.Detach();
   ReleaseDC(NULL,hDC);

}

void WINAPI PixelsToDlgUnits(short& Num,BOOL Horizontal,CFont FAR* pFont)
{
   CRect RectAux(0,0,(int)Num,(int)Num);

   PixelsToDlgUnits(RectAux,pFont);

   if (Horizontal)
      Num = (short)RectAux.right;
   else
      Num = (short)RectAux.bottom;
}

void WINAPI IFONTChangeWndFont(CWnd* pWnd,ICFont* pFont,int Flag)
{
   IFONTChangeWndFont(pWnd,(CFont*)pFont,Flag);
}


// helper function which sets the font for a window and all its children
// and also resizes everything according to the new font

void WINAPI IFONTChangeWndFont(CWnd* pWnd,CFont* pFont,int Flag)
{
        CRect windowRect;

        // grab old and new text metrics
        TEXTMETRIC tmOld, tmNew;
        CDC * pDC = pWnd->GetDC();
        CFont * pSavedFont = pDC->SelectObject(pWnd->GetFont());
        pDC->GetTextMetrics(&tmOld);
        pDC->SelectObject(pFont);
        pDC->GetTextMetrics(&tmNew);
        pDC->SelectObject(pSavedFont);
        pWnd->ReleaseDC(pDC);

        long oldHeight = tmOld.tmHeight+tmOld.tmExternalLeading;
        long newHeight = tmNew.tmHeight+tmNew.tmExternalLeading;

        if (Flag != IFONT_CDF_NONE)
        {
                // calculate new dialog window rectangle
                CRect clientRect, newClientRect, newWindowRect;

                pWnd->GetWindowRect(windowRect);
                pWnd->GetClientRect(clientRect);
                long xDiff = windowRect.Width() - clientRect.Width();
                long yDiff = windowRect.Height() - clientRect.Height();

                newClientRect.left = newClientRect.top = 0;
                newClientRect.right = clientRect.right * tmNew.tmAveCharWidth / tmOld.tmAveCharWidth;
                newClientRect.bottom = clientRect.bottom * newHeight / oldHeight;

                if (Flag == IFONT_CDF_TOPLEFT) // resize with origin at top/left of window
                {
                        newWindowRect.left = windowRect.left;
                        newWindowRect.top = windowRect.top;
                        newWindowRect.right = windowRect.left + newClientRect.right + xDiff;
                        newWindowRect.bottom = windowRect.top + newClientRect.bottom + yDiff;
                }
                else if (Flag == IFONT_CDF_CENTER) // resize with origin at center of window
                {
                        newWindowRect.left = windowRect.left -
                                                        (newClientRect.right - clientRect.right)/2;
                        newWindowRect.top = windowRect.top -
                                                        (newClientRect.bottom - clientRect.bottom)/2;
                        newWindowRect.right = newWindowRect.left + newClientRect.right + xDiff;
                        newWindowRect.bottom = newWindowRect.top + newClientRect.bottom + yDiff;
                }
                pWnd->MoveWindow(newWindowRect);
        }

        pWnd->SetFont(pFont);

        // iterate through and move all child windows and change their font.
        CWnd* pChildWnd = pWnd->GetWindow(GW_CHILD);

        while (pChildWnd)
        {
                pChildWnd->SetFont(pFont);
                pChildWnd->GetWindowRect(windowRect);
                pWnd->ScreenToClient(windowRect);
                windowRect.left = windowRect.left * tmNew.tmAveCharWidth / tmOld.tmAveCharWidth;
                windowRect.right = windowRect.right * tmNew.tmAveCharWidth / tmOld.tmAveCharWidth;
                windowRect.top = windowRect.top * newHeight / oldHeight;
                windowRect.bottom = windowRect.bottom * newHeight / oldHeight;
                pChildWnd->MoveWindow(windowRect);

                pChildWnd = pChildWnd->GetWindow(GW_HWNDNEXT);
        }
}

