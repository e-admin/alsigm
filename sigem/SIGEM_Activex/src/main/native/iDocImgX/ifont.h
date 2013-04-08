
#ifndef __IFONT_H__
#define __IFONT_H__

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICFont                                                          //
//                                                                  //
//////////////////////////////////////////////////////////////////////

#define IFE_NONE      0L
#define IFE_BOLD      1L
#define IFE_ITALIC    2L
#define IFE_UNDERLINE 4L
#define IFE_STRIKEOUT 8L

class ICFont : public CFont
{

   public:

   ICFont(LPCSTR Name,LONG Size,LONG Enh,CWnd* pWnd=NULL);
   ICFont(LPCSTR Name,LONG Size,LONG Enh,CDC* pDC);
   ICFont();
   virtual ~ICFont();

   ICFont(const CFont& Other,CWnd* pWnd=NULL);
   ICFont(const ICFont& Other);
   ICFont& operator=(const ICFont& Other);

   void GetName(CString& Name) const;
   LONG GetSize() const;
   LONG GetEnh() const;

   BOOL IsBold() const;
   BOOL IsItalic() const;
   BOOL IsUnderline() const;
   BOOL IsStrikeout() const;

   protected:

   DECLARE_DYNCREATE(ICFont)

   void Term();
   void MakeFont(CWnd* pWnd);
   void MakeFont(CDC* pDC);
   void ConvertFontToICFont(const CFont& Other,CWnd* pWnd);
   void ConvertFontToICFont(const CFont& Other,CDC* pDC);

   protected:

   CString m_Name;
   LONG    m_Size;
   LONG    m_Enh;

};

#define IFONT_CDF_CENTER    0
#define IFONT_CDF_TOPLEFT   1
#define IFONT_CDF_NONE      2

int WINAPI IPointSizeToCharHeight(int PointSize,CDC* pDC);
int WINAPI ICharHeightToPointSize(int Height,CDC* pDC);
void WINAPI DlgUnitsToPixels(CRect& Rect,CFont* pFont);
void WINAPI DlgUnitsToPixels(short& Num,BOOL Horizontal,CFont FAR* pFont);
void WINAPI PixelsToDlgUnits(CRect& Rect,CFont* pFont);
void WINAPI PixelsToDlgUnits(short& Num,BOOL Horizontal,CFont FAR* pFont);

void WINAPI IFONTChangeWndFont(CWnd* pWnd,CFont* pFont,int Flag=IFONT_CDF_CENTER);
void WINAPI IFONTChangeWndFont(CWnd* pWnd,ICFont* pFont,int Flag=IFONT_CDF_CENTER);

#endif // __IFONT_H__

