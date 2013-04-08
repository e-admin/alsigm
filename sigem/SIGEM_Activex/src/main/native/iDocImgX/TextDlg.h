#if !defined(AFX_TEXTDLG_H__D3BC3C23_7312_11D2_8126_00C0263508EA__INCLUDED_)
#define AFX_TEXTDLG_H__D3BC3C23_7312_11D2_8126_00C0263508EA__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000
// TextDlg.h : header file
//


class CTextDlg : public CPropertyPage
{
	DECLARE_DYNCREATE(CTextDlg)

   public:

	CTextDlg();
   CTextDlg(LOGFONT* pFont,int FontSize,int* BorderArr,int Justify,
            COLORREF FontColor,CWnd* pWnd);
	~CTextDlg();

	//{{AFX_DATA(CTextDlg)
	enum { IDD = IDD_TITLE_TEXT };
	CComboBox	m_CB_Top;
	CComboBox	m_CB_Right;
	CComboBox	m_CB_Left;
	CComboBox	m_CB_Down;
	CComboBox	m_CB_All;
	int		m_Justify;
	//}}AFX_DATA


	//{{AFX_VIRTUAL(CTextDlg)
	public:
	virtual void OnOK();
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL


   protected:
	
	//{{AFX_MSG(CTextDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnFont();
	afx_msg void OnSelchangeBorderAll();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

   void InitBorderList();
   void FillList(CComboBox* pList);

   public:
   
   LPLOGFONT m_pFont; 
   int       m_BorderArr[4]; 
   int       m_FontSize; 
   COLORREF  m_FontColor;
   
   private:
      
   CWnd*     m_pParent;

};

//{{AFX_INSERT_LOCATION}}
// Microsoft Developer Studio will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTDLG_H__D3BC3C23_7312_11D2_8126_00C0263508EA__INCLUDED_)
