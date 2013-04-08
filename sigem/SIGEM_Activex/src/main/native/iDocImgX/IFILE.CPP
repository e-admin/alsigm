
#include "stdafx.h" 
#include "ifile.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif


#define IERR_NO_ERROR  0L
#define IERR_FILE      2L


ICFile::ICFile()
{
   m_hFile = HFILE_ERROR;
}

ICFile::~ICFile()
{
   Close();
}

LONG ICFile::Open(LPCSTR Name,UINT Mode)
{

   LONG     Err = IERR_NO_ERROR;
   OFSTRUCT OFS;

   m_hFile = OpenFile(Name,&OFS,Mode);
   if (m_hFile == HFILE_ERROR) Err = IERR_FILE;

   return(Err);

}

LONG ICFile::Close()
{

   LONG Err = IERR_NO_ERROR;

   if (m_hFile != HFILE_ERROR)
   {
      if (_lclose(m_hFile) == HFILE_ERROR)
    Err = IERR_FILE;
      else
    m_hFile = HFILE_ERROR;
   }

   return(Err);

}

LONG ICFile::ReadLine(CString& Line,BOOL FAR* EndOfFile)
{

   LONG Err = IERR_NO_ERROR;
   UINT Ret;
   char Char;

   *EndOfFile = FALSE;

   Line.Empty();

   while ( (!Err) && (!*EndOfFile) )
   {

      Ret = _lread(m_hFile,&Char,1);

      if (Ret == HFILE_ERROR)
    Err = IERR_FILE;
      else
      {
    if (Ret < 1)
       *EndOfFile = TRUE;
    else
    {
       if ( (Char != '\r') && (Char != '\n') && (Char != 0x1a) )
          Line = Line + Char;
       else
       {
          if (Char == '\n')
        break;
       }
    }
      }

   }

   return(Err);

}

LONG ICFile::WriteLine(LPCSTR Line)
{

   LONG Err = IERR_NO_ERROR;
   UINT Ret;

   Ret = _lwrite(m_hFile,Line,lstrlen(Line));
   if (Ret == HFILE_ERROR)
   {
      Err = IERR_FILE;
      goto End;
   }

   Ret = _lwrite(m_hFile,"\r\n",2);
   if (Ret == HFILE_ERROR) Err = IERR_FILE;

   End:

   return(Err);

}

LONG ICFile::ReadLines(const CString& LastLine,CString& Lines,BOOL FAR* EndOfFile)
{

   LONG    Err = IERR_NO_ERROR;
   CString Line;

   *EndOfFile = FALSE;

   Lines.Empty();

   Err = ReadLine(Line,EndOfFile);
   if ( !Err )
   {
      if (Line != LastLine)
    Lines = Lines + Line;
      else
    goto End;
   }

   while ( (!Err) && (!*EndOfFile) )
   {

      Err = ReadLine(Line,EndOfFile);

      if ( !Err )
      {
    if (Line != LastLine)
       Lines = Lines + "\r\n" + Line;
    else
       break;
      }

   }

   End:

   return(Err);

}

LONG ICFile::Skip(char SkipChar,BOOL FAR* EndOfFile)
{

   LONG Err = IERR_NO_ERROR;
   UINT Ret;
   char Char;

   *EndOfFile = FALSE;

   while ( (!Err) && (!*EndOfFile) )
   {

      Ret = _lread(m_hFile,&Char,1);

      if (Ret == HFILE_ERROR)
    Err = IERR_FILE;
      else
      {
    if (Ret < 1)
       *EndOfFile = TRUE;
    else
    {
       if (Char == SkipChar)
          break;
    }
      }

   }

   return(Err);

}

BOOL IFILEExists(LPCSTR Name)
{

   BOOL     Exists = TRUE;
   OFSTRUCT OFS;

   if ( OpenFile(Name,&OFS,OF_EXIST) == HFILE_ERROR )
      Exists = FALSE;

   return(Exists);

} 
