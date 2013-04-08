
#include "StdAfx.h"
#include "Ann.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

BOOL IANNStrToNum(LPSTR* pStr,LONG* Num);
BOOL IANNStrToNum(LPSTR* pStr,int* Num);
BOOL IANNStrToRect(LPSTR* pStr,CRect& Rect);
BOOL IANNStrToStr(LPSTR* pStr,CString& Str);

LONG IANNReadToBuf(LPCSTR FileName,LPSTR* pBuf);
LONG IANNWriteFromBuf(LPCSTR FileName,LPCSTR Buf,LONG BufLen);

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnn                                                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnn::ICDocAnn(BOOL Extended)
: m_Rect(),m_CrtnDate(),m_UpdDate()
{
   m_Id         = IDOC_NULL_ID;
   m_Type       = IDOC_ANN_TYPE_NULL;
   m_CrtrId     = IDOC_NULL_ID;
   m_UpdrId     = IDOC_NULL_ID;
   m_AccessType = IDOC_ACCESS_TYPE_ANN_PUBLIC;
   m_ACSId      = IDOC_NULL_ID;
   m_pInfo      = NULL;
   m_pXInfo     = NULL;
   m_Extended   = Extended;
}

ICDocAnn::~ICDocAnn()
{
   Term();
}

LONG ICDocAnn::Init(LONG Type)
{

   LONG Err = 0;

   m_Type = Type;

   Err = AllocAllInfo();

   return(Err);

}

LONG ICDocAnn::AllocAllInfo()
{

   LONG Err = 0;

   m_pInfo = AllocInfo();
   if (m_pInfo == NULL) {Err = IDOC_ERR_MEM;goto End;}

   if (m_Extended)
   {
      m_pXInfo = AllocXInfo();
      if (m_pXInfo == NULL) {Err = IDOC_ERR_MEM;goto End;}
   }

   End:

   return(Err);

}

ICDocAnnInfo* ICDocAnn::AllocInfo()
{

   ICDocAnnInfo* pInfo = NULL;

   switch(m_Type)
   {
      case IDOC_ANN_TYPE_TEXT:
      {
         pInfo  = new ICDocTextAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_NOTE:
      {
         pInfo  = new ICDocNoteAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_HILITE:
      {
         pInfo  = new ICDocAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_LINE:
      {
         pInfo  = new ICDocLineAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_ARROW:
      {
         pInfo  = new ICDocArrowAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_PICT:
      {
         pInfo  = new ICDocPictAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_CHECKMARK:
      {
         pInfo  = new ICDocAnnInfo();
         break;
      }
      default:
      {
         pInfo  = NULL;
         break;
      }
   }

   return(pInfo);

}

ICDocXAnnInfo* ICDocAnn::AllocXInfo()
{

   ICDocXAnnInfo* pXInfo = NULL;

   switch(m_Type)
   {
      case IDOC_ANN_TYPE_TEXT:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_NOTE:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_HILITE:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_LINE:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_ARROW:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_PICT:
      {
         pXInfo = new ICDocXPictAnnInfo();
         break;
      }
      case IDOC_ANN_TYPE_CHECKMARK:
      {
         pXInfo = new ICDocXAnnInfo();
         break;
      }
      default:
      {
         pXInfo = NULL;
         break;
      }
   }

   return(pXInfo);

}

LONG ICDocAnn::Term()
{

   LONG Err = 0;

   delete m_pInfo;
   delete m_pXInfo;

   return(Err);

}

LONG ICDocAnn::Copy(ICDocAnn* pDst) const
{

   LONG Err = 0;

   pDst->m_Id         = m_Id;
   pDst->m_Type       = m_Type;
   pDst->m_Rect       = m_Rect;
   pDst->m_CrtrId     = m_CrtrId;
   pDst->m_CrtnDate   = m_CrtnDate;
   pDst->m_UpdrId     = m_UpdrId;
   pDst->m_UpdDate    = m_UpdDate;
   pDst->m_AccessType = m_AccessType;
   pDst->m_ACSId      = m_ACSId;

   Err = pDst->AllocAllInfo();
   if (Err) goto End;

   if ( InfoExists() )
   {
      Err = m_pInfo->Copy(pDst->m_pInfo);
      if (Err) goto End;
   }

   if (m_Extended)
   {
      Err = m_pXInfo->Copy(pDst->m_pXInfo);
      if (Err) goto End;
   }

   End:

   return(Err);

}

LONG ICDocAnn::CreateNotExtended(ICDocAnn*& pDst) const
{

   LONG Err = 0;

   pDst = new ICDocAnn(FALSE);

   pDst->m_Id         = m_Id;
   pDst->m_Type       = m_Type;
   pDst->m_Rect       = m_Rect;
   pDst->m_CrtrId     = m_CrtrId;
   pDst->m_CrtnDate   = m_CrtnDate;
   pDst->m_UpdrId     = m_UpdrId;
   pDst->m_UpdDate    = m_UpdDate;
   pDst->m_AccessType = m_AccessType;
   pDst->m_ACSId      = m_ACSId;

   Err = pDst->AllocAllInfo();
   if (Err) goto End;

   if ( InfoExists() )
   {
      Err = m_pInfo->Copy(pDst->m_pInfo);
      if (Err) goto End;
   }

   End:

   return(Err);

}

LONG ICDocAnn::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_Id);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Type);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToRect(pBuf,m_Rect);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_CrtrId);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_CrtnDate);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_UpdrId);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_UpdDate);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_AccessType);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_ACSId);
   if (Err) goto End;

   m_pInfo = AllocInfo();
   if (m_pInfo == NULL) {Err = IDOC_ERR_MEM;goto End;}

   if ( InfoExists() )
   {

      (*pBuf)++; // La coma

      Err = m_pInfo->InitFromDefBuf(pBuf);
      if (Err) goto End;

   }

   if (m_Extended)
   {

      m_pXInfo = AllocXInfo();
      if (m_pXInfo == NULL) {Err = IDOC_ERR_MEM;goto End;}

      (*pBuf)++; // La coma

      Err = m_pXInfo->InitFromDefBuf(pBuf);
      if (Err) goto End;

   }

   End:

   return(Err);

}

LONG ICDocAnn::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnn::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString CDStr;
   CString UDStr;
   CString AuxStr;

   CDStr = '"';
   CDStr = CDStr + m_CrtnDate;
   CDStr = CDStr + '"';

   UDStr = '"';
   UDStr = UDStr + m_UpdDate;
   UDStr = UDStr + '"';

   Str.Format("%lu,%lu,%d,%d,%d,%d,%lu,%s,%lu,%s,%lu,%lu",
              m_Id,m_Type,m_Rect.left,m_Rect.top,m_Rect.right,m_Rect.bottom,
              m_CrtrId,(LPCSTR)CDStr,m_UpdrId,(LPCSTR)UDStr,m_AccessType,m_ACSId);

   if ( InfoExists() )
   {

      Str = Str + ",";

      Err = m_pInfo->GetDumpStr(AuxStr);
      if (Err) goto End;

      Str = Str + AuxStr;

   }

   if (m_Extended)
   {

      Str = Str + ",";

      Err = m_pXInfo->GetDumpStr(AuxStr);
      if (Err) goto End;

      Str = Str + AuxStr;

   }

   End:

   return(Err);

}

LONG ICDocAnn::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

BOOL ICDocAnn::InfoExists() const
{

   BOOL Exists = TRUE;

   if ( (m_Type == IDOC_ANN_TYPE_HILITE) ||
        (m_Type == IDOC_ANN_TYPE_CHECKMARK) )
      Exists = FALSE;

   return(Exists);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnArr                                                     //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnnArr::ICDocAnnArr(BOOL Extended)
: CPtrArray()
{
   m_Extended = Extended;
}

ICDocAnnArr::~ICDocAnnArr()
{
   Term();
}

short ICDocAnnArr::GetAnnIdx(LONG AnnId)
{

   ICDocAnn* pCurrAnn = NULL;
   int       i,Count;
   BOOL      Found = FALSE;
   short     Idx   = -1;

   Count = GetSize();
   i     = 0;

   while ( (i < Count) && (!Found) )
   {
      pCurrAnn = (ICDocAnn*)GetAt(i);

      if ( pCurrAnn->m_Id == AnnId)
      {
         Found = TRUE;
         Idx   = (short)i;
      }

      i++;
   }

   return (Idx);

}

ICDocAnn* ICDocAnnArr::AllocAnn()
{
   ICDocAnn* pAnn = new ICDocAnn(m_Extended);
   return(pAnn);
}

LONG ICDocAnnArr::Term()
{

   LONG      Err = 0;
   int       NumAnns,i;
   ICDocAnn* pAnn;

   NumAnns = GetSize();

   for (i=0;i<NumAnns;i++)
   {
      pAnn = (ICDocAnn*)GetAt(i);
      delete pAnn;
   }

   CPtrArray::RemoveAll();

   return(Err);

}

LONG ICDocAnnArr::Copy(ICDocAnnArr* pDst) const
{

   LONG      Err = 0;
   int       Count,i;
   ICDocAnn* pDstAnn;
   ICDocAnn* pAnn;

   Count = GetSize();
   if (Count <= 0) goto End;

   for (i=0;i<Count;i++)
   {

      pDstAnn = pDst->AllocAnn();
      if (pDstAnn == NULL) {Err = IDOC_ERR_MEM;goto End;}

      pAnn = (ICDocAnn*)GetAt(i);

      Err = pAnn->Copy(pDstAnn);
      if (Err) goto End;

      pDst->Add(pDstAnn);

   }

   End:

   return(Err);

}

LONG ICDocAnnArr::InitFromDefBuf(LPSTR* pBuf)
{

   LONG      Err = 0;
   int       NumAnns,i;
   ICDocAnn* pAnn;

   Err = ::IANNStrToNum(pBuf,&NumAnns);
   if (Err) goto End;

   if (NumAnns > 0)
      (*pBuf)++; // El separador "|"
   else
      goto End;

   SetSize(NumAnns);

   for (i=0;i<NumAnns;i++)
   {

      pAnn = AllocAnn();

      if (pAnn != NULL)
      {
         pAnn->InitFromDefBuf(pBuf);
         if (i < (short)(NumAnns - 1))
            (*pBuf)++; //  El separador "|"
      }
      else
         Err = IDOC_ERR_MEM;

      SetAt(i,pAnn);

   }

   End:

   if (Err) Term();

   return(Err);

}

LONG ICDocAnnArr::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnnArr::GetDumpStr(CString& Str) const
{

   LONG      Err = 0;
   int       NumAnns,i;
   ICDocAnn* pAnn;
   CString   AuxStr;

   NumAnns = GetSize();

   Str.Format("%d",NumAnns);

   if (NumAnns > 0)
      Str = Str + "|";

   for (i=0;i<NumAnns;i++)
   {
      pAnn = (ICDocAnn*)GetAt(i);
      pAnn->GetDumpStr(AuxStr);
      Str = Str + AuxStr;
      if (i < (short)(NumAnns - 1))
         Str = Str + "|";
   }

   return(Err);

}

LONG ICDocAnnArr::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnPage                                                    //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnnPage::ICDocAnnPage(BOOL Extended)
{
   m_pAnns    = NULL;
   m_Extended = Extended;
}

ICDocAnnPage::~ICDocAnnPage()
{
   Term();
}

LONG ICDocAnnPage::Init()
{

   LONG Err = 0;

   m_pAnns = AllocAnns();
   if (m_pAnns == NULL)
   {
      Err = IDOC_ERR_MEM;
      Term();
   }

   return(Err);

}

ICDocAnnArr* ICDocAnnPage::AllocAnns()
{
   return( new ICDocAnnArr(m_Extended) );
}

LONG ICDocAnnPage::Term()
{

   LONG Err = 0;

   if (m_pAnns != NULL)
      Err = m_pAnns->Term();

   delete m_pAnns;
   m_pAnns = NULL;

   return(Err);

}

LONG ICDocAnnPage::Copy(ICDocAnnPage* pDst) const
{

   LONG Err = 0;

   Err = m_pAnns->Copy(pDst->m_pAnns);

   return(Err);

}

LONG ICDocAnnPage::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   m_pAnns = AllocAnns();
   if (m_pAnns == NULL) {Err = IDOC_ERR_MEM;goto End;}

   Err = m_pAnns->InitFromDefBuf(pBuf);
   if (Err) goto End;

   End:

   if (Err) Term();

   return(Err);

}

LONG ICDocAnnPage::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnnPage::GetDumpStr(CString& Str) const
{

   LONG Err = 0;

   Err = m_pAnns->GetDumpStr(Str);

   return(Err);

}

LONG ICDocAnnPage::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnPageArr                                                 //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnnPageArr::ICDocAnnPageArr(BOOL Extended)
: CPtrArray()
{
   m_Extended = Extended;
}

ICDocAnnPageArr::~ICDocAnnPageArr()
{
   Term();
}

short ICDocAnnPageArr::GetAnnPageIdx(LONG AnnId) const
{

   ICDocAnnPage* pPage;
   BOOL          Found = FALSE;
   int           Count,i;
   short         CurrIdx;
   short         PagIdx   = -1;

   Count = GetSize();
   i     = 0;

   while ( (i < Count) && (!Found) )
   {
      pPage = (ICDocAnnPage*)GetAt(i);

      CurrIdx = pPage->m_pAnns->GetAnnIdx(AnnId);
      if (CurrIdx >= 0)
      {
         Found  = TRUE;
         PagIdx = (short)i;
      }

      i++;
   }

   return (PagIdx);

}

ICDocAnnPage* ICDocAnnPageArr::AllocPage()
{
   ICDocAnnPage* pPage = new ICDocAnnPage(m_Extended);
   return(pPage);
}

LONG ICDocAnnPageArr::Term()
{

   LONG          Err = 0,Error;
   int           NumPages,i;
   ICDocAnnPage* pPage;

   NumPages = GetSize();

   for (i=0;i<NumPages;i++)
   {
      pPage = (ICDocAnnPage*)GetAt(i);
      if (pPage != NULL)
      {
         Error = pPage->Term();
         if (Error) Err = Error;
      }
      delete pPage;
   }

   CPtrArray::RemoveAll();

   return(Err);

}

LONG ICDocAnnPageArr::Copy(ICDocAnnPageArr* pDst) const
{

   LONG          Err = 0;
   int           Count,i;
   ICDocAnnPage* pDstPage;
   ICDocAnnPage* pPage;

   Count = GetSize();
   if (Count <= 0) goto End;

   for (i=0;i<Count;i++)
   {

      pDstPage = pDst->AllocPage();
      if (pDstPage == NULL) {Err = IDOC_ERR_MEM;goto End;}

      Err = pDstPage->Init();
      if (Err) goto End;

      pPage = (ICDocAnnPage*)GetAt(i);

      Err = pPage->Copy(pDstPage);
      if (Err) goto End;

      pDst->Add(pDstPage);

   }

   End:

   return(Err);

}

LONG ICDocAnnPageArr::InitFromDefBuf(LPSTR* pBuf)
{

   LONG          Err = 0;
   int           NumPages,i;
   ICDocAnnPage* pPage;

   Err = ::IANNStrToNum(pBuf,&NumPages);

   if (NumPages > 0)
      (*pBuf)++; // El separador "|"

   SetSize(NumPages);
   
   for (i=0;i<NumPages;i++)
   {

      pPage = AllocPage();
      
      if (pPage != NULL)
      {
         pPage->InitFromDefBuf(pBuf);
         if (i < (short)(NumPages - 1))
            (*pBuf)++; // El separador "|"
      }
      else
         Err = IDOC_ERR_MEM;

      SetAt(i,pPage);

   }

   return(Err);

}

LONG ICDocAnnPageArr::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnnPageArr::GetDumpStr(CString& Str) const
{

   LONG          Err = 0;
   int           NumPages,i;
   ICDocAnnPage* pPage;
   CString       AuxStr;

   NumPages = GetSize();

   Str.Format("%d",NumPages);

   if (NumPages > 0)
      Str = Str + "|";

   for (i=0;i<NumPages;i++)
   {
      pPage = (ICDocAnnPage*)GetAt(i);
      pPage->GetDumpStr(AuxStr);
      Str = Str + AuxStr;
      if (i < (NumPages - 1))
         Str = Str + "|";
   }

   return(Err);

}

LONG ICDocAnnPageArr::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);

   *Len = Str.GetLength();

   return(Err);

}

#define IDOC_ANN_CURR_VER "01.00"

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnDoc                                                     //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnnDoc::ICDocAnnDoc(BOOL Extended)
: m_Ver(IDOC_ANN_CURR_VER)
{
   m_pPages   = NULL;
   m_pXInfo   = NULL;
   m_Extended = Extended;
}

ICDocAnnDoc::~ICDocAnnDoc()
{
   Term();
}

LONG ICDocAnnDoc::Init()
{

   LONG Err = 0;

   m_pPages = AllocPages();
   if (m_pPages == NULL)
   {
      Err = IDOC_ERR_MEM;
      goto End;
   }

   if (m_Extended)
   {
      m_pXInfo = AllocXInfo();
      if (m_pXInfo == NULL)
      {
         Err = IDOC_ERR_MEM;
         goto End;
      }
   }

   End:

   if (Err) Term();

   return(Err);

}

LONG ICDocAnnDoc::InitFromStr(LPCSTR Str)
{

   LONG  Err = 0;
   LPSTR Addr;

   Addr = (LPSTR)Str;

   Err = InitFromDefBuf(&Addr);

   if (Err) Term();

   return(Err);

}

LONG ICDocAnnDoc::InitFromFile(LPCSTR FileName)
{

   LONG  Err  = 0;
   LPSTR pBuf = NULL;
   LPSTR Addr;

   Err = ::IANNReadToBuf(FileName,&pBuf);
   if (Err) goto End;

   Addr = pBuf;

   Err = InitFromDefBuf(&Addr);

   End:

   delete [] pBuf;

   if (Err) Term();

   return(Err);

}

LONG ICDocAnnDoc::WriteToStr(CString& Str) const
{
   return( GetDumpStr(Str) );
}

LONG ICDocAnnDoc::WriteToFile(LPCSTR FileName) const
{

   LONG   Err  = 0;
   BYTE*  pBuf = NULL;
   LPSTR  Addr;
   LONG   Len;

   Err = GetDumpStrLen(&Len);
   if (Err) goto End;

   pBuf = new BYTE[Len+1];
   if (pBuf == NULL) {Err = IDOC_ERR_MEM;goto End;}

   Addr = (LPSTR)pBuf;

   Err = WriteDefToBuf(&Addr);
   if (Err) goto End;

   Err = ::IANNWriteFromBuf(FileName,(LPSTR)pBuf,Len);
   if (Err) goto End;

   End:

   delete [] pBuf;

   return(Err);

}

LONG ICDocAnnDoc::CreateExtended(ICDocAnnDoc*& pXDoc) const
{

   LONG Err = 0;

   pXDoc = new ICDocAnnDoc(TRUE);
   if (pXDoc == NULL) {Err = IDOC_ERR_MEM;goto End;}

   Err = pXDoc->Init();
   if (Err) goto End;

   pXDoc->m_Ver = m_Ver;

   Err = m_pPages->Copy(pXDoc->m_pPages);

   End:

   if (Err)
   {
      delete pXDoc;
      pXDoc = NULL;
   }

   return(Err);

}

LONG ICDocAnnDoc::AddAnn(short PagIdx,ICDocAnn* pNewAnn)
{

   ICDocAnnPage* pPage = NULL;
   int           NumPages,i;
   LONG          Err = 0;

   NumPages = m_pPages->GetSize();

   if (PagIdx >= NumPages)
   {
      // Crear página(s)
      for (i = NumPages; i <= PagIdx; i++)
      {
         pPage = m_pPages->AllocPage();
         if (pPage == NULL) {Err = IDOC_ERR_MEM;goto End;}

         Err = pPage->Init();
         if (Err) {delete pPage; goto End;}

         m_pPages->Add(pPage);
      }
   }
   else
      pPage = (ICDocAnnPage*)m_pPages->GetAt(PagIdx);

   pPage->m_pAnns->Add(pNewAnn);

   End:

   return (Err);

}

LONG ICDocAnnDoc::ReplAnn(LONG AnnId,ICDocAnn* pDocAnn)
{

   short PagIdx;
   LONG  Err = 0;

   PagIdx = m_pPages->GetAnnPageIdx(AnnId);

   if (PagIdx >= 0)
      Err = ReplAnn(AnnId,PagIdx,pDocAnn);
   else
      Err = IDOC_ERR_ID_NOT_FOUND;

   return (Err);

}

LONG ICDocAnnDoc::ReplAnn(LONG AnnId,short PagIdx,ICDocAnn* pDocAnn)
{

   ICDocAnnPage* pPage;
   ICDocAnn*     pOldDocAnn;
   int           NumPages;
   short         AnnIdx;
   LONG          Err = 0;

   NumPages = m_pPages->GetSize();

   ASSERT(PagIdx < NumPages);

   pPage  = (ICDocAnnPage*)m_pPages->GetAt(PagIdx);
   AnnIdx = pPage->m_pAnns->GetAnnIdx(AnnId);

   pOldDocAnn = (ICDocAnn*)pPage->m_pAnns->GetAt(AnnIdx);
   delete pOldDocAnn;

   pPage->m_pAnns->SetAt(AnnIdx,pDocAnn);

   return (Err);

}

LONG ICDocAnnDoc::DelAnn(LONG AnnId)
{

   short PagIdx;
   LONG  Err = 0;

   PagIdx = m_pPages->GetAnnPageIdx(AnnId);

   if (PagIdx >= 0)
      Err = DelAnn(AnnId);
   else
      Err = IDOC_ERR_ID_NOT_FOUND;

   return (Err);

}

LONG ICDocAnnDoc::DelAnn(LONG AnnId,short PagIdx)
{

   ICDocAnnPage* pPage;
   ICDocAnn*     pOldDocAnn;
   int           NumPages;
   short         AnnIdx;
   LONG          Err = 0;

   NumPages = m_pPages->GetSize();

   ASSERT(PagIdx < NumPages);

   pPage  = (ICDocAnnPage*)m_pPages->GetAt(PagIdx);
   AnnIdx = pPage->m_pAnns->GetAnnIdx(AnnId);

   pOldDocAnn = (ICDocAnn*)pPage->m_pAnns->GetAt(AnnIdx);
   delete pOldDocAnn;

   pPage->m_pAnns->RemoveAt(AnnIdx);

   return (Err);

}

ICDocAnnPageArr* ICDocAnnDoc::AllocPages()
{
   return( new ICDocAnnPageArr(m_Extended) );
}

ICDocXAnnDocInfo* ICDocAnnDoc::AllocXInfo()
{
   return( new ICDocXAnnDocInfo() );
}

LONG ICDocAnnDoc::Term()
{

   LONG Err = 0;

   if (m_pPages != NULL)
      Err = m_pPages->Term();

   delete m_pPages;
   m_pPages = NULL;

   delete m_pXInfo;
   m_pXInfo = NULL;

   return(Err);

}

LONG ICDocAnnDoc::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToStr(pBuf,m_Ver);
   if (Err) goto End;
   (*pBuf)++; // La coma
   
   m_pPages = AllocPages();
   if (m_pPages == NULL) {Err = IDOC_ERR_MEM;goto End;}
   
   Err = m_pPages->InitFromDefBuf(pBuf);
   if (Err) goto End;

   
   if (m_Extended)
   {

      (*pBuf)++; // El separador "|"

      m_pXInfo = AllocXInfo();
      if (m_pXInfo == NULL)
      {
         Err = IDOC_ERR_MEM;
         goto End;
      }

      Err = m_pXInfo->InitFromDefBuf(pBuf);
      if (Err) goto End;

   }

   End:

   return(Err);

}

LONG ICDocAnnDoc::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnnDoc::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString AuxStr;

   AuxStr = '"';
   AuxStr = AuxStr + m_Ver;
   AuxStr = AuxStr + '"';

   Str.Format("%s,",(LPCSTR)AuxStr);

   m_pPages->GetDumpStr(AuxStr);
   Str = Str + AuxStr;

   if (m_Extended)
   {
      Str = Str + "|";
      m_pXInfo->GetDumpStr(AuxStr);
      Str = Str + AuxStr;
   }

   return(Err);

}

LONG ICDocAnnDoc::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);

   *Len = Str.GetLength();

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  Funciones auxiliares                                            //
//                                                                  //
//////////////////////////////////////////////////////////////////////

BOOL IANNStrToNum(LPSTR* pStr,LONG* Num)
{

   BOOL  Err = FALSE;
   LPSTR Aux;

   *Num = strtol(*pStr,&Aux,10);

   if ( (Aux == *pStr) || (*Num == LONG_MAX) || (*Num == LONG_MIN) )
      Err = TRUE;
   else
      *pStr = Aux;

   return(Err);

}

BOOL IANNStrToNum(LPSTR* pStr,int* Num)
{

   BOOL Err = FALSE;
   LONG Aux;

   Err = IANNStrToNum(pStr,&Aux);

   if ( (Err) || (Aux < INT_MIN) || (Aux > INT_MAX) )
      Err = TRUE;
   else
      *Num = (int)Aux;

   return(Err);

}

// L,T,R,B

BOOL IANNStrToRect(LPSTR* pStr,CRect& Rect)
{

   BOOL Err = FALSE;

   Err = IANNStrToNum(pStr,&Rect.left);
   if ( Err ) goto End;
   (*pStr)++; // La coma
   Err = IANNStrToNum(pStr,&Rect.top);
   if ( Err ) goto End;
   (*pStr)++; // La coma
   Err = IANNStrToNum(pStr,&Rect.right);
   if ( Err ) goto End;
   (*pStr)++; // La coma
   Err = IANNStrToNum(pStr,&Rect.bottom);

   End:

   return(Err);

}

// "Texto"

BOOL IANNStrToStr(LPSTR* pStr,CString& Str)
{

   BOOL  Err = FALSE;
   LPSTR Aux;

   *pStr = strchr(*pStr,'"');
   if (*pStr == NULL) {Err = TRUE;goto End;}
   (*pStr)++; // Saltar comillas
   Aux = strchr(*pStr,'"');
   if (Aux == NULL) {Err = TRUE;goto End;}

   Aux[0] = '\0';
   Str    = *pStr;
   Aux[0] = '"';

   *pStr = Aux;
   (*pStr)++; // Saltar comillas

   End:

   return(Err);

}

// Hace el alloc del buffer

LONG IANNReadToBuf(LPCSTR FileName,LPSTR* pBuf)
{

   LONG     Err   = 0;
   HFILE    hFile = HFILE_ERROR;  
   LONG     Len,Pos;

   *pBuf = NULL;

   hFile =(int) CreateFile(FileName,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,
                      FILE_ATTRIBUTE_NORMAL,NULL);
   
   if (hFile == HFILE_ERROR) {Err = IDOC_ERR_FILE;goto End;}

   Len = _llseek(hFile,0,2); // Ir al final
   if (Len == HFILE_ERROR) {Err = IDOC_ERR_FILE;goto End;}

   Pos = _llseek(hFile,0,0); // Ir al principio
   if (Pos == HFILE_ERROR) {Err = IDOC_ERR_FILE;goto End;}

   *pBuf = new char[Len];
   if (*pBuf == NULL) {Err = IDOC_ERR_MEM;goto End;}

   Pos = _hread(hFile,*pBuf,Len);
   if (Pos != Len) {Err = IDOC_ERR_FILE;goto End;}

   End:

   if (hFile != HFILE_ERROR) CloseHandle((HANDLE)hFile);

   if (Err) {delete [] (*pBuf);*pBuf = NULL;}

   return(Err);

}

LONG IANNWriteFromBuf(LPCSTR FileName,LPCSTR Buf,LONG BufLen)
{

   LONG     Err   = 0;
   HFILE    hFile = HFILE_ERROR;
   OFSTRUCT OFS;
   LONG     Ret;

   hFile = OpenFile(FileName,&OFS,OF_WRITE|OF_CREATE);
   if (hFile == HFILE_ERROR) {Err = IDOC_ERR_FILE;goto End;}

   Ret = _hwrite(hFile,Buf,BufLen);
   if (Ret == -1) {Err = IDOC_ERR_FILE;goto End;}

   End:

   if (hFile != HFILE_ERROR) _lclose(hFile);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocAnnInfo                                                    //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocAnnInfo::ICDocAnnInfo()
{
}

ICDocAnnInfo::~ICDocAnnInfo()
{
}

LONG ICDocAnnInfo::Copy(ICDocAnnInfo* /*pDst*/) const
{

   LONG Err = 0;

   return(Err);

}

LONG ICDocAnnInfo::InitFromDefBuf(LPSTR* /*pBuf*/)
{

   LONG Err = 0;

   return(Err);

}

LONG ICDocAnnInfo::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocAnnInfo::GetDumpStr(CString& /*Str*/) const
{

   LONG Err = 0;

   return(Err);

}

LONG ICDocAnnInfo::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocTextAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocTextAnnInfo::ICDocTextAnnInfo()
: ICDocAnnInfo(),m_Text(),m_FontName("MS Sans Serif")
{
   m_Just      = IDOC_ANN_TEXT_JUST_LEFT;
   m_FontSize  = 8;
   m_FontEnh   = IDOC_ANN_FONT_ENH_NONE;
   m_FontColor = RGB(0,0,0);
   m_LBS       = IDOC_ANN_BORDER_STYLE_NONE;
   m_TBS       = IDOC_ANN_BORDER_STYLE_NONE;
   m_RBS       = IDOC_ANN_BORDER_STYLE_NONE;
   m_BBS       = IDOC_ANN_BORDER_STYLE_NONE;
}

ICDocTextAnnInfo::~ICDocTextAnnInfo()
{
}

LONG ICDocTextAnnInfo::Copy(ICDocAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocTextAnnInfo*)pDst)->m_Text      = m_Text;
   ((ICDocTextAnnInfo*)pDst)->m_Just      = m_Just;
   ((ICDocTextAnnInfo*)pDst)->m_FontName  = m_FontName;
   ((ICDocTextAnnInfo*)pDst)->m_FontSize  = m_FontSize;
   ((ICDocTextAnnInfo*)pDst)->m_FontEnh   = m_FontEnh;
   ((ICDocTextAnnInfo*)pDst)->m_FontColor = m_FontColor;
   ((ICDocTextAnnInfo*)pDst)->m_LBS       = m_LBS;
   ((ICDocTextAnnInfo*)pDst)->m_TBS       = m_TBS;
   ((ICDocTextAnnInfo*)pDst)->m_RBS       = m_RBS;
   ((ICDocTextAnnInfo*)pDst)->m_BBS       = m_BBS;

   End:

   return(Err);

}

LONG ICDocTextAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToStr(pBuf,m_Text);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Just);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_FontName);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_FontSize);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_FontEnh);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_FontColor);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_LBS);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_TBS);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_RBS);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_BBS);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocTextAnnInfo::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString TextStr;
   CString FontNameStr;

   TextStr = '"';
   TextStr = TextStr + m_Text;
   TextStr = TextStr + '"';

   FontNameStr = '"';
   FontNameStr = FontNameStr + m_FontName;
   FontNameStr = FontNameStr + '"';

   Str.Format("%s,%lu,%s,%lu,%lu,%lu,%lu,%lu,%lu,%lu",
              (LPCSTR)TextStr,m_Just,(LPCSTR)FontNameStr,
              m_FontSize,m_FontEnh,m_FontColor,m_LBS,m_TBS,m_RBS,m_BBS);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocNoteAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocNoteAnnInfo::ICDocNoteAnnInfo()
: ICDocAnnInfo(),m_Text()
{
}

ICDocNoteAnnInfo::~ICDocNoteAnnInfo()
{
}

LONG ICDocNoteAnnInfo::Copy(ICDocAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocNoteAnnInfo*)pDst)->m_Text = m_Text;

   End:

   return(Err);

}

LONG ICDocNoteAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToStr(pBuf,m_Text);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocNoteAnnInfo::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString TextStr;

   TextStr = '"';
   TextStr = TextStr + m_Text;
   TextStr = TextStr + '"';

   Str.Format("%s",(LPCSTR)TextStr);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocLineAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocLineAnnInfo::ICDocLineAnnInfo()
: ICDocAnnInfo()
{
   m_Dirn  = IDOC_ANN_DIRN_DOWNUP;
   m_Color = RGB(0,0,0);
}

ICDocLineAnnInfo::~ICDocLineAnnInfo()
{
}

LONG ICDocLineAnnInfo::Copy(ICDocAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocLineAnnInfo*)pDst)->m_Dirn  = m_Dirn;
   ((ICDocLineAnnInfo*)pDst)->m_Color = m_Color;

   End:

   return(Err);

}

LONG ICDocLineAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_Dirn);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Color);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocLineAnnInfo::GetDumpStr(CString& Str) const
{

   LONG Err = 0;

   Str.Format("%lu,%lu",m_Dirn,m_Color);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocArrowAnnInfo                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocArrowAnnInfo::ICDocArrowAnnInfo()
: ICDocAnnInfo()
{
   m_Dirn     = IDOC_ANN_DIRN_DOWNUP;
   m_EndPoint = IDOC_ANN_ENDPOINT_UP;
   m_Color    = RGB(0,0,0);
}

ICDocArrowAnnInfo::~ICDocArrowAnnInfo()
{
}

LONG ICDocArrowAnnInfo::Copy(ICDocAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocArrowAnnInfo*)pDst)->m_Dirn     = m_Dirn;
   ((ICDocArrowAnnInfo*)pDst)->m_EndPoint = m_EndPoint;
   ((ICDocArrowAnnInfo*)pDst)->m_Color    = m_Color;

   End:

   return(Err);

}

LONG ICDocArrowAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_Dirn);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_EndPoint);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Color);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocArrowAnnInfo::GetDumpStr(CString& Str) const
{

   LONG Err = 0;

   Str.Format("%lu,%lu,%lu",m_Dirn,m_EndPoint,m_Color);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocPictAnnInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocPictAnnInfo::ICDocPictAnnInfo()
: ICDocAnnInfo()
{
   m_PictId = IDOC_NULL_ID;
   m_BS     = IDOC_ANN_BORDER_STYLE_NONE;
}

ICDocPictAnnInfo::~ICDocPictAnnInfo()
{
}

LONG ICDocPictAnnInfo::Copy(ICDocAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocPictAnnInfo*)pDst)->m_PictId = m_PictId;
   ((ICDocPictAnnInfo*)pDst)->m_BS     = m_BS;

   End:

   return(Err);

}

LONG ICDocPictAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_PictId);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_BS);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocPictAnnInfo::GetDumpStr(CString& Str) const
{

   LONG Err = 0;

   Str.Format("%lu,%lu",m_PictId,m_BS);

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXAnnInfo                                                   //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocXAnnInfo::ICDocXAnnInfo()
: m_CrtrName(),m_UpdrName()
{
   m_Access = IDOC_ANN_ACCESS_NONE;
   m_Stat   = IDOC_ANN_STAT_DEF;
}

ICDocXAnnInfo::~ICDocXAnnInfo()
{
}

LONG ICDocXAnnInfo::Copy(ICDocXAnnInfo* pDst) const
{

   LONG Err = 0;

   pDst->m_Access   = m_Access;
   pDst->m_CrtrName = m_CrtrName;
   pDst->m_UpdrName = m_UpdrName;
   pDst->m_Stat     = m_Stat;

   return(Err);

}

LONG ICDocXAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_Access);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_CrtrName);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_UpdrName);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Stat);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocXAnnInfo::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocXAnnInfo::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString CrtrStr;
   CString UpdrStr;

   CrtrStr = '"';
   CrtrStr = CrtrStr + m_CrtrName;
   CrtrStr = CrtrStr + '"';

   UpdrStr = '"';
   UpdrStr = UpdrStr + m_UpdrName;
   UpdrStr = UpdrStr + '"';

   Str.Format("%lu,%s,%s,%lu",
              m_Access,(LPCSTR)CrtrStr,(LPCSTR)UpdrStr,m_Stat);

   return(Err);

}

LONG ICDocXAnnInfo::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXPictAnnInfo                                               //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocXPictAnnInfo::ICDocXPictAnnInfo()
: ICDocXAnnInfo(),m_FileName()
{
}

ICDocXPictAnnInfo::~ICDocXPictAnnInfo()
{
}

LONG ICDocXPictAnnInfo::Copy(ICDocXAnnInfo* pDst) const
{

   LONG Err = 0;

   Err = ICDocXAnnInfo::Copy(pDst);
   if (Err) goto End;

   ((ICDocXPictAnnInfo*)pDst)->m_FileName = m_FileName;

   End:

   return(Err);

}

LONG ICDocXPictAnnInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ICDocXAnnInfo::InitFromDefBuf(pBuf);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_FileName);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocXPictAnnInfo::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString FileStr;
   CString AuxStr;

   FileStr = '"';
   FileStr = FileStr + m_FileName;
   FileStr = FileStr + '"';

   AuxStr.Format(",%s",(LPCSTR)FileStr);

   Err = ICDocXAnnInfo::GetDumpStr(Str);
   if (Err) goto End;

   Str = Str + AuxStr;

   End:

   return(Err);

}

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  ICDocXAnnDocInfo                                                //
//                                                                  //
//////////////////////////////////////////////////////////////////////

ICDocXAnnDocInfo::ICDocXAnnDocInfo()
: m_UserName(),m_IconPath(),m_DefNavIcon()
{
   m_UserId = IDOC_NULL_ID;
   m_Flags  = IDOC_ANN_XDI_FLAG_NONE;
}

ICDocXAnnDocInfo::~ICDocXAnnDocInfo()
{
}

LONG ICDocXAnnDocInfo::InitFromDefBuf(LPSTR* pBuf)
{

   LONG Err = 0;

   Err = ::IANNStrToNum(pBuf,&m_UserId);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_UserName);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_IconPath);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToStr(pBuf,m_DefNavIcon);
   if (Err) goto End;
   (*pBuf)++; // La coma

   Err = ::IANNStrToNum(pBuf,&m_Flags);
   if (Err) goto End;

   End:

   return(Err);

}

LONG ICDocXAnnDocInfo::WriteDefToBuf(LPSTR* pBuf) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   lstrcpy(*pBuf,Str);
   (*pBuf) = (*pBuf) + Str.GetLength();

   End:

   return(Err);

}

LONG ICDocXAnnDocInfo::GetDumpStr(CString& Str) const
{

   LONG    Err = 0;
   CString UNStr;
   CString IPStr;
   CString DNIStr;

   UNStr = '"';
   UNStr = UNStr + m_UserName;
   UNStr = UNStr + '"';

   IPStr = '"';
   IPStr = IPStr + m_IconPath;
   IPStr = IPStr + '"';

   DNIStr = '"';
   DNIStr = DNIStr + m_DefNavIcon;
   DNIStr = DNIStr + '"';

   Str.Format("%lu,%s,%s,%s,%lu",
              m_UserId,(LPCSTR)UNStr,(LPCSTR)IPStr,(LPCSTR)DNIStr,m_Flags);

   return(Err);

}

LONG ICDocXAnnDocInfo::GetDumpStrLen(LONG* Len) const
{

   LONG    Err = 0;
   CString Str;

   Err = GetDumpStr(Str);
   if (Err) goto End;

   *Len = Str.GetLength();

   End:

   return(Err);

}

