///////////////////////////////////////////////////////////////////
//
// Header File BrowseDirectory.h
//
// 
///////////////////////////////////////////////////////////////////

#ifndef __BrowseDirectory_H
#define __BrowseDirectory_H

#include <shlobj.h>

class CBrowseDirectory
{
public:
	CBrowseDirectory() {}
	virtual ~CBrowseDirectory();

	CString Do(LPCTSTR Title, CWnd* pFromWnd = NULL);
	virtual void BrowseDir(CString& str, CWnd* pFromWnd, LPCTSTR Title);

protected:
	virtual void InitBrowseInfo(BROWSEINFO& bi, CWnd* pFromWnd, LPCTSTR Title);
};

/*
///////////////////////////////////////////////////////////////////
// class CBrowseDirForFiles Opens "Select Directory" dialog box.
// Class enables "OK" button only if "FileWldcrt" files found in
// selected directory. 
// Warning! Uses Shell Interface found only in Win95 and NT4.0!
//
///////////////////////////////////////////////////////////////////
class CBrowseDirForFiles : public CBrowseDirectory
{
public:
	// Constructor:
	// FileWldcrt - wildcard for files to found (f.e. a*.cp*)
	// MsgFilesExist - status line (shown in the Dialog Box) 
	// when files were found in the selected directory
	// MsgFilesExist - status line when files weren't found
	CBrowseDirForFiles  (LPCTSTR FileWldcrt, 
							LPCTSTR MsgFilesExist = NULL, 
							LPCTSTR MsgFilesNotExist = NULL);
	// System - level callback (see source)
	friend int CALLBACK CBrowseDirForFiles_CallBack(HWND hwnd, UINT uMsg, LPARAM lParam, LPARAM lpData);
protected:
	// Initialisation of bi structure.
	// Besides default initiialisation form CBrowseDirectory,
	// adds a callback and a status line.
	virtual void	InitBrowseInfo(BROWSEINFO& bi, CWnd* pFromWnd, LPCTSTR Title);
	// class-level callback
	virtual int		CallBack(HWND hwnd, UINT uMsg, LPARAM lParam);
	// Overwritable. Called from callback to do test existence of the file.
	// returns 1 for exists and 0 for doesn't.
	virtual int		TestFile(TCHAR* pStr);
	// Overwritable. Called from callback to do some actions when selection changes.
	// In this class simply changes status line and enables/disables OK button.
	virtual void	FileExists(HWND hwnd, WPARAM w);
	
private:
	LPCTSTR		m_strFileWldcrt;
	LPCTSTR		m_MsgFilesExist, m_MsgFilesNotExist;
};
///////////////////////////////////////////////////////////////////
*/

#endif // __BrowseDirectory_H


