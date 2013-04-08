// ifile.h : header file
//

//Clase que controla el manejo de ficheros
//

#ifndef __IFILE_H__
#define __IFILE_H__


class ICFile
{

   public:

   ICFile();
   ~ICFile();

   LONG Open(LPCSTR Name,UINT Mode);
   LONG Close();

   LONG ReadLine(CString& Line,BOOL FAR* EndOfFile);
   LONG WriteLine(LPCSTR Line);

   LONG ReadLines(const CString& LastLine,CString& Lines,BOOL FAR* EndOfFile);

   LONG Skip(char SkipChar,BOOL FAR* EndOfFile);

   protected:

   HFILE m_hFile;

};


BOOL IFILEExists(LPCSTR Name);


#endif // __IFILE_H__

