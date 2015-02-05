
#ifndef __IDOCA_ANN_H__
#define __IDOCA_ANN_H__

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  IDocA Ann Defines                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

// Para que siga el esquema IDocA pero pueda ser utilizado fuera.

#define IDOC_NULL_ID  LONG_MAX-1

#define IDOC_FIRST_ERROR       20000L
#define IDOC_ERR_MEM           IDOC_FIRST_ERROR + 1L
#define IDOC_ERR_FILE          IDOC_FIRST_ERROR + 2L
#define IDOC_ERR_ID_NOT_FOUND  IDOC_FIRST_ERROR + 4L

#define IDOC_ACCESS_TYPE_ANN_PROTECTED  0L
#define IDOC_ACCESS_TYPE_ANN_PUBLIC     LONG_MAX-1

// Propio de Ann

#define IDOC_ANN_TYPE_NULL       0L
#define IDOC_ANN_TYPE_TEXT       2L
#define IDOC_ANN_TYPE_NOTE       3L
#define IDOC_ANN_TYPE_HILITE     4L
#define IDOC_ANN_TYPE_LINE       5L
#define IDOC_ANN_TYPE_ARROW      6L
#define IDOC_ANN_TYPE_PICT       7L
#define IDOC_ANN_TYPE_CHECKMARK  8L

#define IDOC_ANN_TEXT_JUST_LEFT    0L
#define IDOC_ANN_TEXT_JUST_CENTER  1L
#define IDOC_ANN_TEXT_JUST_RIGHT   2L

#define IDOC_ANN_FONT_ENH_NONE       0L
#define IDOC_ANN_FONT_ENH_BOLD       1L
#define IDOC_ANN_FONT_ENH_ITALIC     2L
#define IDOC_ANN_FONT_ENH_UNDERLINE  4L

#define IDOC_ANN_BORDER_STYLE_NONE    0L
#define IDOC_ANN_BORDER_STYLE_SIMPLE  1L
#define IDOC_ANN_BORDER_STYLE_DOUBLE  2L

#define IDOC_ANN_DIRN_UPDOWN  0L
#define IDOC_ANN_DIRN_DOWNUP  1L

#define IDOC_ANN_ENDPOINT_DOWN  0L
#define IDOC_ANN_ENDPOINT_UP    1L

#define IDOC_ANN_ACCESS_NONE    0L
#define IDOC_ANN_ACCESS_VIEW    1L
#define IDOC_ANN_ACCESS_UPDATE  2L
#define IDOC_ANN_ACCESS_DELETE  4L
#define IDOC_ANN_ACCESS_ALL     7L

#define IDOC_ANN_STAT_DEF 0L
#define IDOC_ANN_STAT_NEW 1L
#define IDOC_ANN_STAT_UPD 2L
#define IDOC_ANN_STAT_DEL 3L

#define IDOC_ANN_XDI_FLAG_NONE        0L
#define IDOC_ANN_XDI_FLAG_ADD_ANNS    1L
#define IDOC_ANN_XDI_FLAG_PRINT_ANNS  2L

#define IDOC_ANN_FILE_EXT  "ann"

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnInfo                                                    //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnnInfo
{

   public:

   ICDocAnnInfo();
   virtual ~ICDocAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   friend class ICDocAnn;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocTextAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocTextAnnInfo : public ICDocAnnInfo
{

   public:

   ICDocTextAnnInfo();
   virtual ~ICDocTextAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   CString m_Text;
   LONG    m_Just;
   CString m_FontName;
   LONG    m_FontSize;
   LONG    m_FontEnh;
   LONG    m_FontColor;
   LONG    m_LBS;
   LONG    m_TBS;
   LONG    m_RBS;
   LONG    m_BBS;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocNoteAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocNoteAnnInfo : public ICDocAnnInfo
{

   public:

   ICDocNoteAnnInfo();
   virtual ~ICDocNoteAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   CString m_Text;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocLineAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocLineAnnInfo : public ICDocAnnInfo
{

   public:

   ICDocLineAnnInfo();
   virtual ~ICDocLineAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   LONG m_Dirn;
   LONG m_Color;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocArrowAnnInfo                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocArrowAnnInfo : public ICDocAnnInfo
{

   public:

   ICDocArrowAnnInfo();
   virtual ~ICDocArrowAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   LONG m_Dirn;
   LONG m_EndPoint;
   LONG m_Color;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocPictAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocPictAnnInfo : public ICDocAnnInfo
{

   public:

   ICDocPictAnnInfo();
   virtual ~ICDocPictAnnInfo();

   protected:

   virtual LONG Copy(ICDocAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   LONG m_PictId;
   LONG m_BS;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXAnnInfo                                                   //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocXAnnInfo
{

   public:

   ICDocXAnnInfo();
   virtual ~ICDocXAnnInfo();

   protected:

   virtual LONG Copy(ICDocXAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   friend class ICDocAnn;

   public:

   LONG    m_Access;
   CString m_CrtrName;
   CString m_UpdrName;
   LONG    m_Stat;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXPictAnnInfo                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocXPictAnnInfo : public ICDocXAnnInfo
{

   public:

   ICDocXPictAnnInfo();
   virtual ~ICDocXPictAnnInfo();

   protected:

   virtual LONG Copy(ICDocXAnnInfo* pDst) const;

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   public:

   CString m_FileName;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXAnnDocInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocXAnnDocInfo
{

   public:

   ICDocXAnnDocInfo();
   virtual ~ICDocXAnnDocInfo();

   protected:

   virtual LONG InitFromDefBuf(LPSTR* pBuf);
   virtual LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   friend class ICDocAnnDoc;

   public:

   LONG    m_UserId;
   CString m_UserName;
   CString m_IconPath;
   CString m_DefNavIcon;
   LONG    m_Flags;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnn                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnn
{

   public:

   ICDocAnn(BOOL Extended);
   virtual ~ICDocAnn();

   LONG Init(LONG Type);

   LONG CreateNotExtended(ICDocAnn*& pDst) const; // pDst es NO extendida

   protected:

   LONG AllocAllInfo();

   ICDocAnnInfo*  AllocInfo();
   ICDocXAnnInfo* AllocXInfo();

   LONG Term();

   LONG Copy(ICDocAnn* pDst) const;

   LONG InitFromDefBuf(LPSTR* pBuf);
   LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   BOOL InfoExists() const;

   public:

   LONG           m_Id;
   LONG           m_Type;
   CRect          m_Rect;
   LONG           m_CrtrId;
   CString        m_CrtnDate;
   LONG           m_UpdrId;
   CString        m_UpdDate;
   LONG           m_AccessType;
   LONG           m_ACSId;      // Id en sistema de control de acceso
   ICDocAnnInfo*  m_pInfo;
   ICDocXAnnInfo* m_pXInfo;
   BOOL           m_Extended;

   friend class ICDocAnnArr;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnArr                                                     //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnnArr : public CPtrArray
{

   public:

   ICDocAnnArr(BOOL Extended);
   virtual ~ICDocAnnArr();

   short GetAnnIdx(LONG AnnId);

   protected:

   ICDocAnn* AllocAnn();

   LONG Term();

   LONG Copy(ICDocAnnArr* pDst) const;

   LONG InitFromDefBuf(LPSTR* pBuf);
   LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   friend class ICDocAnnPage;

   public:

   BOOL m_Extended;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnPage                                                    //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnnPage
{

   public:

   ICDocAnnPage(BOOL Extended);
   virtual ~ICDocAnnPage();

   LONG Init();

   protected:

   ICDocAnnArr* AllocAnns();

   LONG Term();

   LONG Copy(ICDocAnnPage* pDst) const;

   LONG InitFromDefBuf(LPSTR* pBuf);
   LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   public:

   ICDocAnnArr* m_pAnns;
   BOOL         m_Extended;

   friend class ICDocAnnPageArr;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnPageArr                                                 //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnnPageArr : public CPtrArray
{

   public:

   ICDocAnnPageArr(BOOL Extended);
   virtual ~ICDocAnnPageArr();

   short GetAnnPageIdx(LONG AnnId) const;

   protected:

   ICDocAnnPage* AllocPage();

   LONG Term();

   LONG Copy(ICDocAnnPageArr* pDst) const;

   LONG InitFromDefBuf(LPSTR* pBuf);
   LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   friend class ICDocAnnDoc;

   public:

   BOOL m_Extended;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnDoc                                                     //
//                                                                  //
//////////////////////////////////////////////////////////////////////

class ICDocAnnDoc
{

   public:

   ICDocAnnDoc(BOOL Extended);
   virtual ~ICDocAnnDoc();

   LONG Init();
   LONG InitFromStr(LPCSTR Str);
   LONG InitFromFile(LPCSTR FileName);

   LONG WriteToStr(CString& Str) const;
   LONG WriteToFile(LPCSTR FileName) const;

   LONG CreateExtended(ICDocAnnDoc*& pXDoc) const;

   // Si no existe la pág., se crea (y todas las anteriores necesarias)
   LONG AddAnn(short PagIdx,ICDocAnn* pNewAnn);

   LONG ReplAnn(LONG AnnId,ICDocAnn* pDocAnn);
   LONG ReplAnn(LONG AnnId,short AnnPageIdx,ICDocAnn* pDocAnn);

   LONG DelAnn(LONG AnnId);
   LONG DelAnn(LONG AnnId,short AnnPageIdx);

   protected:

   ICDocAnnPageArr*  AllocPages();
   ICDocXAnnDocInfo* AllocXInfo();

   LONG Term();

   LONG InitFromDefBuf(LPSTR* pBuf);
   LONG GetDumpStr(CString& Str) const;

   LONG WriteDefToBuf(LPSTR* pBuf) const;
   LONG GetDumpStrLen(LONG* Len) const;

   public:

   CString           m_Ver;
   ICDocAnnPageArr*  m_pPages;
   ICDocXAnnDocInfo* m_pXInfo;
   BOOL              m_Extended;

};

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  IDocA Ann Documentaci¢n                                         //
//                                                                  //
//////////////////////////////////////////////////////////////////////

/*

   General
   -------

   Para poder manejar anotaciones fuera de invesDoc, se necesita
   informaci¢n adicional: extended (X).

   Formato de string con informaci¢n de anotaciones
   ------------------------------------------------

   Doc: "Ver",NumPages|Page1|Page2|...|PageN|XDocInfo

   Page: NumAnns|Ann1|Ann2|...|AnnM

   Ann: Id,Type,L,T,R,B,CrtrId,"CrtnDate",UpdrId,"UpdDate",
        AccessType,AnnInfo,XAnnInfo

   AnnInfo:

      TextAnnInfo: "Text",Just,"FontName",FontSize,FontEnh,FontColor,
                   LBS,TBS,RBS,BBS

      NoteAnnInfo: "Text"

      LineAnnInfo: Dirn,Color

      ArrowAnnInfo: Dirn,EndPoint,Color

      PictAnnInfo: PictId,BS

   XAnnInfo: Access,"CrtrName","UpdrName",Stat

      XPictAnnInfo: "FileName"

   XDocInfo: UserId,"UserName","IconPath","DefNavIcon",Flags

   Varios
   ------

   Versi¢n como "01.00".

   Fechas como "YYYY/MM/DD hh:mm:ss". Si fecha nula, string empty.

*/

#endif // __IDOCA_ANN_H__
