/************************************************************************/
/*                                                                      */
/* Upload.cpp : Implementation of CUpload                               */
/*                                                                      */
/*  Mensajes:                                                           */
/*                                                                      */
/************************************************************************/
#include "stdafx.h"
#include <urlmon.h>
#include <wpapi.h>
#pragma comment(lib, "webpost.lib")

#include "..\Common\LexNetErrs.h"
#include "..\Common\Base64.h"
#include "..\Common\FileMapping.h"
#include "..\LNUtil\LNUtil.h"

#include "browsedirectory.h"
#include "ProxyDlg.h"
#include "iDocPost.h"
#include "Upload.h"

// Descomentar para utilizar la implementacion de UploadFile basada en IXMLHTTPRequest
#import "msxml3.dll"
#include <shlwapi.h>


#define  MAX_BUF   100000

//#undef TRACE_TIME
#define TRACE_TIME

//------------------------------------------------------------------------
// Funciones globales
//------------------------------------------------------------------------
static void InitFileDialog(CFileDialog& FileDlg, LPCTSTR pszDlgTitle, LPCTSTR pszIniDir)
{
  if ((pszDlgTitle != NULL) && (lstrlen(pszDlgTitle) > 0))
    FileDlg.m_ofn.lpstrTitle = pszDlgTitle;

  if ((pszIniDir != NULL) && (lstrlen(pszIniDir) > 0))
    FileDlg.m_ofn.lpstrInitialDir = pszIniDir;
}
//------------------------------------------------------------------------
static CString PromptForFileName(BOOL bForOpen, LPCTSTR pszDefExtension, LPCTSTR pszFilter, 
                                 LPCTSTR pszDlgTitle, LPCTSTR pszIniDir, DWORD Flags)
{
  DWORD    dlgFlags = 0;
  CString  defName(_T("*."));
  CString  filePath;

  if (Flags == 0)
  {
    if (bForOpen)
    {
      dlgFlags |= OFN_FILEMUSTEXIST;
    }
    else
    {
      dlgFlags |= OFN_OVERWRITEPROMPT;
    }
  }

  defName += pszDefExtension;

  CFileDialog fileDlg(bForOpen, pszDefExtension, defName, dlgFlags, pszFilter);
  InitFileDialog(fileDlg, pszDlgTitle, pszIniDir);

  if (fileDlg.DoModal() == IDOK)
  {
    filePath = fileDlg.GetPathName();
  }

  return  filePath;
} 
//------------------------------------------------------------------------
static long GetHTTPError(HINTERNET hInet)
{
  DWORD dwStatus = 0;
  DWORD dwStatusSize = sizeof(dwStatus);
  DWORD dwIndex = 0;
  DWORD Err = 0;

  if (!HttpQueryInfo(hInet, HTTP_QUERY_STATUS_CODE | HTTP_QUERY_FLAG_NUMBER, &dwStatus, &dwStatusSize, &dwIndex))
  {
    Err = GetLastHR();
  }
  else if (dwStatus >= 400)
  {
    Err = HTTP_STATUS_TO_ERR(dwStatus);
  }

  return Err;
}
//------------------------------------------------------------------------
static void CreateDownloadFileName(const CString &strURL, const CString &strLocalPath, CString &strLocalFileName)
{
  CString strURLPath, strURLFileName;
  CString strLocalDir, strTempPath;
  int     iParamsPos, iSlashPos;
  DWORD   dwFileAttr;

  // Obtenemos el nombre de fichero de la URL
  iParamsPos = strURL.FindOneOf(_T("#?"));
  if (iParamsPos != -1)
    strURLPath = strURL.Left(iParamsPos);
  else
    strURLPath = strURL;

  LNGetFileName(strURLPath, strURLFileName);

  if (strLocalPath.IsEmpty())
  {
    // Directorio temporal, nombre de la página 
    ::GetTempPath(_MAX_PATH, strTempPath.GetBufferSetLength(_MAX_PATH));
    strTempPath.ReleaseBuffer();
    strLocalFileName.Format(_T("%s%s"), strTempPath, strURLFileName);
  }
  else if (strLocalPath.FindOneOf(_T("\\/")) != -1)
  {
    // Tiene pinta de directorio o especificacion completa
    TCHAR lastChar = strLocalPath[strLocalPath.GetLength() - 1];
    if (lastChar == _T('\\') || lastChar == _T('/'))
    {
      // Acaba en barra... directorio
      strLocalFileName.Format(_T("%s%s"), strLocalPath, strURLFileName);
    }
    else
    {
      // Aun puede ser un directorio, aunque no acabe en barra
      dwFileAttr = ::GetFileAttributes(strLocalPath);
      if ((dwFileAttr != INVALID_FILE_ATTRIBUTES) && (dwFileAttr & FILE_ATTRIBUTE_DIRECTORY) == FILE_ATTRIBUTE_DIRECTORY)
        strLocalFileName.Format(_T("%s\\%s"), strLocalPath, strURLFileName );    // Nos dan el directorio, ponemos el nombre de la página
      else
        strLocalFileName = strLocalPath;      // Asumimos que lo que nos dan es una especificacion completa de fichero
    }
  }
  else
  {
    // Tiene pinta de nombre de fichero, ponemos el directorio temporal
    ::GetTempPath(_MAX_PATH, strTempPath.GetBufferSetLength(_MAX_PATH));
    strTempPath.ReleaseBuffer();
    strLocalFileName.Format(_T("%s%s"), strTempPath, strLocalPath);
  }

  // Nos aseguramos de que el directorio esté creado
  iSlashPos = max(strLocalFileName.ReverseFind(_T('\\')), strLocalFileName.ReverseFind(_T('/')));
  strLocalDir = iSlashPos != -1 ? strLocalFileName.Left(iSlashPos) : _T("");
  CreateDir(strLocalDir);
}
//------------------------------------------------------------------------
static long CreateProxyAuth(LPCTSTR pszUserName, LPCTSTR pszPassword, CString &strProxyAuth)
{
  long Err = 0;
  CString strBasic, strBasicEnc;
  strBasic.Format(_T("%s:%s"), pszUserName, pszPassword);

  Err = Base64::Encode(PBYTE(LPCTSTR(strBasic)), strBasic.GetLength(), strBasicEnc, 0, NULL);
  if (Err) goto End;

  strProxyAuth.Format(_T("Proxy-Authorization: Basic %s\r\n"), strBasicEnc);

End:
  return Err;
}

//------------------------------------------------------------------------
static void Query(HINTERNET hInet)
{
  long Err = 0;
  TCHAR chProxy[10000];
  DWORD dwSize = sizeof(chProxy);
  DWORD dwIndex = 0;
  INTERNET_PROXY_INFO *ipf = (INTERNET_PROXY_INFO *)chProxy;
 
  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!HttpQueryInfo(hInet, HTTP_QUERY_FLAG_REQUEST_HEADERS | HTTP_QUERY_PROXY_AUTHORIZATION, chProxy, &dwSize, &dwIndex))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!HttpQueryInfo(hInet, HTTP_QUERY_FLAG_REQUEST_HEADERS | HTTP_QUERY_PROXY_AUTHORIZATION, chProxy, &dwSize, &dwIndex))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!HttpQueryInfo(hInet, HTTP_QUERY_VIA, chProxy, &dwSize, &dwIndex))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!HttpQueryInfo(hInet, HTTP_QUERY_RAW_HEADERS_CRLF, chProxy, &dwSize, &dwIndex))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!InternetQueryOption(hInet, INTERNET_OPTION_PROXY, PVOID(ipf), &dwSize))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!InternetQueryOption(hInet, INTERNET_OPTION_PROXY_USERNAME, chProxy, &dwSize))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!InternetQueryOption(hInet, INTERNET_OPTION_PROXY_PASSWORD, chProxy, &dwSize))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!InternetQueryOption(hInet, INTERNET_OPTION_USERNAME, chProxy, &dwSize))
    Err = GetLastHR();

  dwSize = sizeof(chProxy); chProxy[0] = 0;
  if (!InternetQueryOption(hInet, INTERNET_OPTION_PASSWORD, chProxy, &dwSize))
    Err = GetLastHR();

  if (hInet != NULL)
  {
    HINTERNET hParent = NULL;
    dwSize = sizeof(hParent);
    if (!InternetQueryOption(hInet, INTERNET_OPTION_PARENT_HANDLE, PVOID(&hParent), &dwSize))
      Err = GetLastHR();
    else
      Query(hParent);
  }
}   
//------------------------------------------------------------------------
long CUpload::SendFile(HINTERNET hInetReq, LPCTSTR FileName)
{
  USES_ERROR_LOG("CUpload::SendFile");
  LONG  Err = 0;

  INTERNET_BUFFERS BufferIn = {0};

  DWORD   BytesRead;
  DWORD   BytesWritten;
  BYTE    pBuffer[100000];            // Read from file in 1K chunks
  HANDLE  hFile = INVALID_HANDLE_VALUE;

  hFile = CreateFile(FileName, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

#ifdef TRACE_TIME
  CTraceTime __tt(_T("SendFile(%d) From %s"), GetFileSize(hFile, NULL), FileName);
#endif

  if (hFile == INVALID_HANDLE_VALUE)
    CHECK_LAST_ERROR(Err)

Request:
  if (SetFilePointer(hFile, 0, NULL, FILE_BEGIN) == INVALID_SET_FILE_POINTER)
    CHECK_LAST_ERROR(Err)

  BufferIn.dwStructSize = sizeof(INTERNET_BUFFERS);
  BufferIn.Next = NULL; 
  if (m_strBasicProxyAuth.IsEmpty())
  {
    BufferIn.lpcszHeader = NULL;
    BufferIn.dwHeadersLength = 0;
  }
  else
  {
    BufferIn.lpcszHeader = m_strBasicProxyAuth; 
    BufferIn.dwHeadersLength = m_strBasicProxyAuth.GetLength();
  }

  BufferIn.dwHeadersTotal = 0;
  BufferIn.lpvBuffer = NULL;                
  BufferIn.dwBufferLength = 0;
  BufferIn.dwBufferTotal = GetFileSize(hFile, NULL);
  BufferIn.dwOffsetLow = 0;
  BufferIn.dwOffsetHigh = 0;

#ifdef TRACE_TIME
  __tt.Start();
#endif

  if (!HttpSendRequestEx(hInetReq, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC, 0))
    CHECK_LAST_ERROR(Err)

  do
  {
    if (!ReadFile(hFile, pBuffer, sizeof(pBuffer), &BytesRead, NULL))
    {
      Err = GetLastHR();
      LOG_ERR(Err)
      break;
    }

    if (!InternetWriteFile(hInetReq, pBuffer, BytesRead, &BytesWritten))
    {
      Err = GetLastHR();
      LOG_ERR(Err)
      break;
    }

  }
  while (BytesRead == sizeof(pBuffer));

  if (!HttpEndRequest(hInetReq, NULL, HSR_INITIATE | HSR_SYNC, 0))
  {
    Err = GetLastError();
    if (Err == ERROR_INTERNET_FORCE_RETRY && GetHTTPError(hInetReq) == HTTP_STATUS_TO_ERR(HTTP_STATUS_PROXY_AUTH_REQ))
    {
      AFX_MANAGE_STATE(AfxGetStaticModuleState());

      CProxyDlg dlg;
      Err = dlg.DoModal();
      if (Err == IDOK)
      {
        Err = CreateProxyAuth(dlg.m_strUserName, dlg.m_strPassword, m_strBasicProxyAuth);
        CHECK_ERR(Err)
        
        Err = ERROR_INTERNET_FORCE_RETRY;
        goto End;
      }
        

//      Err = InternetErrorDlg(GetDesktopWindow(), hInetReq, Err, 
//                             FLAGS_ERROR_UI_FILTER_FOR_ERRORS | 
//                             FLAGS_ERROR_UI_FLAGS_GENERATE_DATA | 
//                             FLAGS_ERROR_UI_FLAGS_CHANGE_OPTIONS, NULL);
//
   
//      if (Err == ERROR_INTERNET_FORCE_RETRY)
//        goto Request; 
    }
  }

  Err = GetHTTPError(hInetReq);
  CHECK_ERR(Err)

End:
  if (hFile != INVALID_HANDLE_VALUE)
    CloseHandle(hFile);

  return  Err;
}
/*
static LONG SendFile(HINTERNET hInetReq, const char* FileName)
{
  USES_ERROR_LOG("CUpload::SendFile");
  LONG  Err = 0;

  INTERNET_BUFFERS BufferIn = {0};

  DWORD   BytesRead;
  DWORD   BytesWritten;
  BYTE    pBuffer[100000];            // Read from file in 1K chunks
  HANDLE  hFile = INVALID_HANDLE_VALUE;

  hFile = CreateFile(FileName, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

  if (hFile == INVALID_HANDLE_VALUE)
    CHECK_LAST_ERROR(Err)

request:
  if (SetFilePointer(hFile, 0, NULL, FILE_BEGIN) == INVALID_SET_FILE_POINTER)
    CHECK_LAST_ERROR(Err)

  if (!ReadFile(hFile, pBuffer, sizeof(pBuffer), &BytesRead, NULL))
    CHECK_LAST_ERROR(Err)

  if (!HttpSendRequest(hInetReq, NULL, 0, pBuffer, BytesRead))
    CHECK_LAST_ERROR(Err)

  Err = GetHTTPError(hInetReq);
  CHECK_ERR(Err)

End:
  if (hFile != INVALID_HANDLE_VALUE)
    CloseHandle(hFile);

  return  Err;
}
*/
/*
static LONG SendFile(HINTERNET hInetReq, const char* FileName)
{
  USES_ERROR_LOG("CUpload::SendFile");
  LONG  Err = 0;

  INTERNET_BUFFERS BufferIn = {0};

  DWORD   BytesRead;
  DWORD   BytesWritten;
  BYTE    pBuffer[100000];            // Read from file in 1K chunks
  HANDLE  hFile = INVALID_HANDLE_VALUE;
  TCHAR chUser[200], chPassword[200];
  DWORD dwUser = 200, dwPassword = 200;

  CString strProxyAuth;
  CString strUsrPwd;
  TCHAR *pUsrPwd = _T("u005370:71547db4");

  hFile = CreateFile(FileName, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

  if (hFile == INVALID_HANDLE_VALUE)
    CHECK_LAST_ERROR(Err)

request:
  if (SetFilePointer(hFile, 0, NULL, FILE_BEGIN) == INVALID_SET_FILE_POINTER)
    CHECK_LAST_ERROR(Err)

  Err = Base64::Encode(PBYTE(pUsrPwd), strlen(pUsrPwd), strUsrPwd, 0, NULL);
  CHECK_ERR(Err);

  strProxyAuth.Format(_T("Proxy-Authorization: Basic %s\r\n"), strUsrPwd);

  BufferIn.dwStructSize = sizeof(INTERNET_BUFFERS);
  BufferIn.Next = NULL; 
  BufferIn.lpcszHeader = strProxyAuth; // NULL;
  BufferIn.dwHeadersLength = strProxyAuth.GetLength(); //0;
  BufferIn.dwHeadersTotal = 0;
  BufferIn.lpvBuffer = NULL;                
  BufferIn.dwBufferLength = 0;
  BufferIn.dwBufferTotal = GetFileSize(hFile, NULL);
  BufferIn.dwOffsetLow = 0;
  BufferIn.dwOffsetHigh = 0;

  //if (!HttpSendRequest(hInetReq, NULL, 0, "Hola", 4))
//    CHECK_LAST_ERROR(Err)

  if (!HttpSendRequestEx(hInetReq, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC, 0))
    CHECK_LAST_ERROR(Err)

  do
  {
    if (!ReadFile(hFile, pBuffer, sizeof(pBuffer), &BytesRead, NULL))
    {
      Err = GetLastHR();
      LOG_ERR(Err)
      break;
    }

    if (!InternetWriteFile(hInetReq, pBuffer, BytesRead, &BytesWritten))
    {
      Err = GetLastHR();
      LOG_ERR(Err)
      break;
    }
  }
  while (BytesRead == sizeof(pBuffer));

  if (!HttpEndRequest(hInetReq, NULL, HSR_INITIATE | HSR_SYNC, 0))
  {
    Err = GetLastError();

    pBuffer[0] = 0;
    do
      InternetReadFile(hInetReq, pBuffer, sizeof(pBuffer), &BytesRead);
    while (BytesRead != 0);

//    if (!HttpAddRequestHeaders(hInetReq, strProxyAuth, strProxyAuth.GetLength(), HTTP_ADDREQ_FLAG_ADD))
//      CHECK_LAST_ERROR(Err);

    if (Err == ERROR_INTERNET_FORCE_RETRY && GetHTTPError(hInetReq) == HTTP_STATUS_TO_ERR(HTTP_STATUS_PROXY_AUTH_REQ))
    {
      Err = InternetErrorDlg(GetDesktopWindow(), hInetReq, Err, 
                             FLAGS_ERROR_UI_FILTER_FOR_ERRORS | 
                             FLAGS_ERROR_UI_FLAGS_GENERATE_DATA | 
                             FLAGS_ERROR_UI_FLAGS_CHANGE_OPTIONS, NULL);

      if (Err == ERROR_INTERNET_FORCE_RETRY)
      {
        if (!InternetQueryOption(hInetReq, INTERNET_OPTION_PROXY_USERNAME, chUser, &dwUser))
          CHECK_LAST_ERROR(Err)

        if (!InternetQueryOption(hInetReq, INTERNET_OPTION_PROXY_PASSWORD, chPassword, &dwPassword))
          CHECK_LAST_ERROR(Err)
      }
    }
  }

  Err = GetHTTPError(hInetReq);
  CHECK_ERR(Err)

End:
  if (hFile != INVALID_HANDLE_VALUE)
    CloseHandle(hFile);

  return  Err;
}
*/
//------------------------------------------------------------------------
// CUpload
//------------------------------------------------------------------------
CUpload::CUpload()
{
  GetErrorManager().SetTraceToFile(_T("LN.log"), NULL);
}
//------------------------------------------------------------------------
CUpload::~CUpload()
{
}
/*
STDMETHODIMP CUpload::UploadFile(BSTR FileName, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet
  USES_ERROR_LOG("CUpload::UploadFile");
  long     Err         = 0;
  CString  Archive     = FileName;
  CString  URLStr(URL);

#ifdef TRACE_TIME
  CTraceTime __tt(_T("CUpload::UploadFile(%I64d) to %s"), GetFileSizeFN(Archive), URLStr);
#endif

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;

  HINTERNET  hInetOpen      = NULL;
  HINTERNET  hInetConnect   = NULL;
  HINTERNET  hInetReq       = NULL;
  DWORD      dwINetFlags    = 0;
  DWORD      dwHTTPFlags    = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  TCHAR      szAccept[]     = _T("*" "/" "*");
  LPSTR      AcceptTypes[2] = {0}; 
  AcceptTypes[0]            = szAccept;  

  CString  ServerStr   = Server;
  CString  ArchiveName;
  LPCTSTR pszProxyUsername = _T("u005370");
  LPCTSTR pszProxyPassword = _T("71547db4");
  TCHAR chUser[200], chPassword[200];
  DWORD dwUser = 200, dwPassword = 200;
  CString strProxyAuth;
  CString strUsrPwd;
  CString strUsrPwdEnc;
  TCHAR buff[10000];
  DWORD cbRead;

  // Si todo el destino va en la URL...
  if (ServerStr.IsEmpty())
  {
    int iTwoSlash = URLStr.Find(_T("//"));
    if (iTwoSlash == -1)
      return HRESULT_FROM_WIN32(ERROR_INVALID_PARAMETER);

    int iNextSlash = URLStr.Find(_T('/'), iTwoSlash + 2);
    if (iNextSlash == -1)
      return HRESULT_FROM_WIN32(ERROR_INVALID_PARAMETER);

    ServerStr = URLStr.Mid(iTwoSlash + 2 , iNextSlash - iTwoSlash - 2);
  }
  
  // Separar servidor y puerto
  USHORT   usPort      = INTERNET_DEFAULT_HTTP_PORT; //INTERNET_INVALID_PORT_NUMBER;
  int      iDotPos     = ServerStr.Find(_T(":"));         
  if (iDotPos != -1)       // CRM
  {
    CString PortStr = ServerStr.Mid( iDotPos + 1 );
    usPort     = (USHORT) atoi( PortStr );
    ServerStr = ServerStr.Left( iDotPos );
  }

  // Utilizar nombre origen en el servidor
  if (URLStr[URLStr.GetLength()-1] == _T('/'))
  {
    LNGetFileName(Archive, ArchiveName);
    URLStr += ArchiveName;
  }

  // Acceso a internet
  //if (hInetOpen == NULL)
  {
  hInetOpen = InternetOpen(_T("POSTer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  dwINetFlags = 0;//INTERNET_FLAG_EXISTING_CONNECT;
  if (usPort == 443) // https
    dwINetFlags |= INTERNET_FLAG_SECURE;

 	hInetConnect = InternetConnect(hInetOpen, ServerStr, usPort, _T(""),  _T(""), INTERNET_SERVICE_HTTP, dwINetFlags, 0);
 	//hInetConnect = InternetConnect(hInetOpen, ServerStr, usPort, pszProxyUsername,  pszProxyPassword, INTERNET_SERVICE_HTTP, dwINetFlags, 0);
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  dwHTTPFlags = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  if (usPort == 443) // https
    dwHTTPFlags |= INTERNET_FLAG_SECURE;

  hInetReq = HttpOpenRequest(hInetConnect, _T("PUT"), URLStr, HTTP_VERSION, _T(""), (LPCTSTR*)AcceptTypes, dwHTTPFlags, 0);
  if (!hInetReq)
    CHECK_LAST_ERROR(Err)
#if 0
  strUsrPwd.Format(_T("%s:%s"), chUser, chPassword);

  Err = Base64::Encode(PBYTE(LPCTSTR(strUsrPwd)), strUsrPwd.GetLength(), strUsrPwdEnc, 0, NULL);
  CHECK_ERR(Err);

  strProxyAuth.Format(_T("Proxy-Authorization: Basic %s\r\n"), strUsrPwdEnc);
  if (!HttpAddRequestHeaders(hInetReq, strProxyAuth, strProxyAuth.GetLength(), HTTP_ADDREQ_FLAG_ADD))
    CHECK_LAST_ERROR(Err);
#endif
  }

  Err = SendFile(hInetReq, Archive);

  if ((Err == 0) && bRemove)
    ::DeleteFile(Archive);

End:
  if (hInetReq)       InternetCloseHandle(hInetReq);
  if (hInetConnect)   InternetCloseHandle(hInetConnect);
  if (hInetOpen)      InternetCloseHandle(hInetOpen);

  V_I4(pErrorCode) = Err;

  if (V_I4(pErrorCode))
  {
    ErrorLog(_T("UploadFile.Archive = %s"),   Archive);
    ErrorLog(_T("UploadFile.URLStr = %s"),    URLStr);
    ErrorLog(_T("UploadFile.ServerStr = %s"), ServerStr);
  }

  return  S_OK;
}
*/
/*
//------------------------------------------------------------------------
STDMETHODIMP CUpload::UploadFile(BSTR FileName, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet
  USES_ERROR_LOG("CUpload::UploadFile");
  long     Err         = 0;
  CString  Archive     = FileName;

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;

  HINTERNET  hInetOpen      = NULL;
  HINTERNET  hInetConnect   = NULL;
  HINTERNET  hInetReq       = NULL;
  DWORD      dwINetFlags    = 0;
  DWORD      dwHTTPFlags    = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  TCHAR      szAccept[]     = _T("*" "/" "*");
  LPSTR      AcceptTypes[2] = {0}; 
  AcceptTypes[0]            = szAccept;  

  CString  URLStr      = URL;
  CString  ServerStr   = Server;
  CString  ArchiveName;
  USHORT   usPort      = INTERNET_INVALID_PORT_NUMBER;
  int      iDotPos     = ServerStr.Find(_T(":"));         

Request:
  hInetOpen = InternetOpen(_T("POSTer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  if (iDotPos != -1)       // CRM
  {
    CString PortStr = ServerStr.Mid( iDotPos + 1 );
    usPort     = (USHORT) atoi( PortStr );
    ServerStr = ServerStr.Left( iDotPos );
  }

  // Utilizar nombre origen en el servidor
  if (URLStr[URLStr.GetLength()-1] == _T('/'))
  {
    LNGetFileName(Archive, ArchiveName);
    URLStr += ArchiveName;
  }

 	hInetConnect = InternetConnect(hInetOpen, ServerStr, usPort, _T(""),  _T(""), INTERNET_SERVICE_HTTP, dwINetFlags, 0);
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  if (usPort == 443) // https
    dwHTTPFlags |= INTERNET_FLAG_SECURE;

  hInetReq = HttpOpenRequest(hInetConnect, _T("PUT"), URLStr, HTTP_VERSION, _T(""), (LPCTSTR*)AcceptTypes, dwHTTPFlags, 0);
  if (!hInetReq)
    CHECK_LAST_ERROR(Err)

  Err = SendFile(hInetReq, Archive);
  if (Err == ERROR_INTERNET_FORCE_RETRY)
  {
    InternetCloseHandle(hInetReq);
    InternetCloseHandle(hInetConnect);
    InternetCloseHandle(hInetOpen);
    goto Request;
  }

  if ((Err == 0) && bRemove)
    ::DeleteFile(Archive);

End:
  if (hInetReq)       InternetCloseHandle(hInetReq);
  if (hInetConnect)   InternetCloseHandle(hInetConnect);
  if (hInetOpen)      InternetCloseHandle(hInetOpen);

  V_I4(pErrorCode) = Err;

  if (V_I4(pErrorCode))
  {
    ErrorLog(_T("UploadFile.Archive = %s"),   Archive);
    ErrorLog(_T("UploadFile.URLStr = %s"),    URLStr);
    ErrorLog(_T("UploadFile.ServerStr = %s"), ServerStr);
  }

  return  S_OK;
}
*/
/*
//------------------------------------------------------------------------
STDMETHODIMP CUpload::UploadFile(BSTR FileName, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode)
{
  // Implementacion basada en IXMLHTTPRequest. Consume mas memoria, porque solo se puede hacer un send,
  // pero atraviesa proxies con las credenciales utilizadas en el explorador

  USES_ERROR_LOG("CUpload::UploadFile");

  CString strLocalFile(FileName), strLocalFileName;
  CString strServer(Server);
  CString strURL(URL);

  LPSTREAM pStream = NULL;
  MSXML2::IXMLHTTPRequestPtr pIXMLHTTPRequest = NULL;
  long Err = 0;
  long lStatus = 0;
  
  // Si la URL acaba en barra, añadimos el nombre origen
  if (strURL[strURL.GetLength()-1] == _T('/'))
  {
    LNGetFileName(strLocalFile, strLocalFileName);
    strURL += strLocalFileName;
  }

  Err = SHCreateStreamOnFile(strLocalFile, STGM_READ | STGM_SHARE_DENY_WRITE, &pStream);
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest.CreateInstance(_T("Msxml2.XMLHTTP"));
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest->open(_bstr_t(_T("PUT")), _bstr_t(LPCTSTR(strURL)), false);
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest->send(pStream);
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest->get_status(&lStatus);
  CHECK_ERR(Err);

  if (lStatus >= 400) Err = HTTP_STATUS_TO_ERR(lStatus);
  CHECK_ERR(Err);

  if ((Err == 0) && bRemove)
    ::DeleteFile(Archive);

End:
  if (pStream)
    pStream->Release();
  
  // No se puede borrar hasta que se hace pStream->Release
  if ((Err == 0) && bRemove)
    ::DeleteFile(strLocalFile);

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
*/
//------------------------------------------------------------------------
STDMETHODIMP CUpload::CallURL(BSTR URL, VARIANT *pResponse, VARIANT *pErrorCode)
{
  // Implementacion basada en URL Monikers
  CString strURL(URL);
  CString strResponse;

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;
  V_VT(pResponse)  = VT_BSTR;

  LPSTREAM pStream = NULL;
  ULONG cbRead = 0;
  BYTE  buffer[MAX_BUF];

  HRESULT hRes = URLOpenBlockingStream((LPUNKNOWN)(void*)this, strURL, &pStream, 0, NULL);
  if (hRes == S_OK)
  {
    while (1)
    {
      hRes = pStream->Read(buffer, sizeof(buffer)-1, &cbRead);
      if (cbRead > 0)
      {
        buffer[cbRead] = 0;
        strResponse += buffer;
      }

      if (hRes != S_OK || cbRead == 0)
      {
        hRes = S_OK;
        break;
      }
    }

    pStream->Release();
  }

  V_I4(pErrorCode) = hRes;
  V_BSTR(pResponse) = strResponse.AllocSysString();

  return  S_OK;
}
/*
//----------------------------------------------------------------------------------------------------
STDMETHODIMP CUpload::CallURL(BSTR URL, VARIANT *pResponse, VARIANT *pErrorCode)
{
  // Implementacion basada en IXMLHTTPRequest

  MSXML2::IXMLHTTPRequestPtr pIXMLHTTPRequest = NULL;
  HRESULT hr;

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;

  try 
  {
    hr = pIXMLHTTPRequest.CreateInstance(_T("Msxml2.XMLHTTP"));
    SUCCEEDED(hr) ? 0 : throw hr;

    hr = pIXMLHTTPRequest->open(_bstr_t(_T("GET")), URL, false);
    SUCCEEDED(hr) ? 0 : throw hr;

    hr = pIXMLHTTPRequest->send();
    SUCCEEDED(hr) ? 0 : throw hr;
  
    V_VT(pResponse)   = VT_BSTR;
    V_BSTR(pResponse) = pIXMLHTTPRequest->responseText;
  } 
  catch (...) 
  {
    V_I4(pErrorCode) = hr;
  }

  return  S_OK;
}
*/
//----------------------------------------------------------------------------------------------------
STDMETHODIMP CUpload::DeleteFile(BSTR FileName)
{
  CString strFileName(FileName);
  ::DeleteFile(strFileName);

	return S_OK;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::SelectFile(long bSaving, VARIANT* pFileName)
{
  AfxSetResourceHandle(_Module.GetResourceInstance());

  CString FileName = _T("");
  CString Title, Filter;

  Title.LoadString(IDS_SELECT_FILE);
  Filter.LoadString(IDS_FILTER);

  BOOL  bForOpen = !bSaving;
  FileName = PromptForFileName(bForOpen, _T("*"), Filter, Title, _T(""), 0);

  V_VT(pFileName)   = VT_BSTR;
  V_BSTR(pFileName) = FileName.AllocSysString();

  return S_OK;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::SelectDir(VARIANT* pDir)
{
  CString Dir;

  AfxSetResourceHandle(_Module.GetResourceInstance());

  CString  Title;
  Title.LoadString(IDS_SELECT_DIR);

  CBrowseDirectory Browse;
  Dir = Browse.Do(Title, NULL);

  V_VT(pDir)   = VT_BSTR;
  V_BSTR(pDir) = Dir.AllocSysString();

  return S_OK;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::GetTempPath(VARIANT *pTempPath)
{
  long Err = S_OK;
  try
  {
    CString strTempPath;
    Err = LNGetTempPath(strTempPath);
    if (Err == 0)
    {
      V_VT(pTempPath) = VT_BSTR;
      V_BSTR(pTempPath) = strTempPath.AllocSysString();
    }
  }
  catch (CException *e)
  {
    e->Delete();
    Err = LEXNET_ERR_EXCEPTION;
  }
  catch (...)
  {
    Err = LEXNET_ERR_EXCEPTION;
  }

	return Err;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::DownloadFile(BSTR URL, BSTR LocalPath, VARIANT* pFileLoc, VARIANT* pErrorCode)
{
  // Implementacion basada en internet connections
  USES_ERROR_LOG("CUpload::DownloadFile");
#ifdef TRACE_TIME
  CTraceTime __tt;
#endif

  HINTERNET  hInetOpen     = NULL;
  HINTERNET  hInetConnect  = NULL;
  CHAR       Head[]        = _T("Accept: *" "/" "*\r\n\r\n");   // Lo pongo separado para que admita comentar el bloque
  FILE*      hDst;
  long       Err           = 0;

  CString strURL(URL), strLocalPath(LocalPath), strLocalFileName;
  CreateDownloadFileName(strURL, strLocalPath, strLocalFileName);

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;
  V_VT(pFileLoc)   = VT_BSTR;

  hInetOpen = InternetOpen(_T("DOWNer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  hInetConnect = InternetOpenUrl(hInetOpen, strURL, Head, lstrlen(Head), INTERNET_FLAG_DONT_CACHE, 0);
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  Err = GetHTTPError(hInetConnect);
  CHECK_ERR(Err)

  hDst = _tfopen(strLocalFileName, _T("wb"));
  if (hDst == NULL)
    CHECK_LAST_ERROR(Err)
  else
  {
    char   Buffer[MAX_BUF + 1];
    DWORD  BytesRead = 1;

    while (BytesRead)
    {
      if (!InternetReadFile(hInetConnect, Buffer, MAX_BUF, &BytesRead))
      {
        Err = GetLastHR();
        LOG_ERR(Err)
        break;
      }

      if (fwrite(Buffer, 1, BytesRead, hDst) != BytesRead)
      {
        Err = GetLastHR();
        LOG_ERR(Err)
        break;
      }
    }

    fflush(hDst);
    fclose(hDst);
  }

#ifdef TRACE_TIME
  __tt.Trace(CTraceTime::STOP | CTraceTime::MSG, _T("CUpload::DownloadFile(%I64d)"), GetFileSizeFN(strLocalFileName));
#endif

  V_BSTR(pFileLoc) = strLocalFileName.AllocSysString();

End:
  if (hInetConnect)  InternetCloseHandle(hInetConnect);
  if (hInetOpen)     InternetCloseHandle(hInetOpen);

  V_I4(pErrorCode) = Err;

  return  S_OK;
}
/*
//----------------------------------------------------------------------------------------------------
STDMETHODIMP CUpload::CallURL(BSTR URL, VARIANT *pResponse, VARIANT *pErrorCode)
{
  USES_ERROR_LOG("CUpload::CallURL");
#ifdef TRACE_TIME
  CTraceTime __tt(_T("CUpload::CallURL()"));
#endif

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = 0;
  V_VT(pResponse)  = VT_BSTR;

  HINTERNET  hInetOpen     = NULL;
  HINTERNET  hInetConnect  = NULL;
  CHAR       Head[]        = _T("Accept: *" "/" "*\r\n\r\n");    // Lo pongo separado para que admita comentar el bloque

  long       Err           = 0;
  CString    URLStr        = URL;
  CString    strResponse;

  hInetOpen = InternetOpen(_T("DOWNer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  hInetConnect = InternetOpenUrl(hInetOpen, URLStr, Head, lstrlen(Head), INTERNET_FLAG_DONT_CACHE, 0 );
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  Err = GetHTTPError(hInetConnect);
  CHECK_ERR(Err)
  // Este bloque es necesario para evitar errores de inicializacion por el goto
  {
    char   Buffer[MAX_BUF + 1];
    DWORD  BytesRead = 1;

    while (BytesRead)
    {
      if (!InternetReadFile(hInetConnect, Buffer, MAX_BUF, &BytesRead))
      {
        Err = GetLastHR();
        CHECK_ERR(Err)

        Err = GetHTTPError(hInetConnect);
        CHECK_ERR(Err)
        break;
      }

      if (BytesRead > 0)
      {
        Buffer[BytesRead] = _T('\0');
        strResponse += Buffer;
      }
    }
  }

  V_BSTR(pResponse) = strResponse.AllocSysString();

End:
  if (hInetConnect)  InternetCloseHandle( hInetConnect );
  if (hInetOpen)     InternetCloseHandle( hInetOpen );

  V_I4(pErrorCode) = Err;

  if (V_I4(pErrorCode))
  {
    ErrorLog(_T("DownloadFile.URLStr = %s"),   URLStr );
    ErrorLog(_T("DownloadFile.Response = %s"), strResponse);
  }

  return  S_OK;
}
//------------------------------------------------------------------------
*/

//------------------------------------------------------------------------
static long CrackURL(const CString &strURL, CString &strProt, CString &strSrv, ULONG &lPort, CString &strRes)
{
  // Separar protocolo, server, puerto, path... 
  int iTwoSlash = strURL.Find(_T("//"));
  if (iTwoSlash == -1)
    return HRESULT_FROM_WIN32(ERROR_INVALID_PARAMETER);

  strProt = strURL.Left(iTwoSlash);

  if (strProt.CompareNoCase(_T("http:")) == 0)
    lPort = INTERNET_DEFAULT_HTTP_PORT;
  else if (strProt.CompareNoCase(_T("https:")) == 0)
    lPort = INTERNET_DEFAULT_HTTPS_PORT;
  else if (strProt.CompareNoCase(_T("ftp:")) == 0)
    lPort = INTERNET_DEFAULT_FTP_PORT;

  int iNextSlash = strURL.Find(_T('/'), iTwoSlash + 2);
  if (iNextSlash == -1)
    return HRESULT_FROM_WIN32(ERROR_INVALID_PARAMETER);

  strSrv = strURL.Mid(iTwoSlash + 2 , iNextSlash - iTwoSlash - 2);

  int iDotPos = strSrv.Find(_T(':'));
  if (iDotPos != -1)
  {
    lPort = _ttoi(LPCTSTR(strSrv) + iDotPos + 1);
    strSrv = strSrv.Left(iDotPos);
  }

  strRes = strURL.Mid(iNextSlash);

  return 0;  
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::PUTFile(BSTR File, BSTR URL, VARIANT *pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet
  USES_ERROR_LOG("CUpload::PUTFile");
  long Err = 0;
  CString    strFile(File), strURL(URL), strProtocol, strServer, strResource, strFileName;
  ULONG ulPort;

  HINTERNET  hInetOpen      = NULL;
  HINTERNET  hInetConnect   = NULL;
  HINTERNET  hInetReq       = NULL;
  DWORD      dwINetFlags    = 0;
  DWORD      dwHTTPFlags    = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  LPTSTR     AcceptTypes[]  = {_T("*" "/" "*"), NULL}; 
  CFileMapping fmap;

#ifdef TRACE_TIME
  CTraceTime __tt(_T("CUpload::PUTFile(%I64d) to %s"), GetFileSizeFN(strFile), strURL);
#endif

  Err = CrackURL(strURL, strProtocol, strServer, ulPort, strResource);
  CHECK_ERR(Err);

  // Utilizar nombre origen en el servidor
  if (strResource[strResource.GetLength()-1] == _T('/'))
  {
    LNGetFileName(strFile, strFileName);
    strResource += strFileName;
  }

  // Acceso a internet
  hInetOpen = InternetOpen(_T("POSTer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  dwINetFlags = 0;//INTERNET_FLAG_EXISTING_CONNECT;
  if (ulPort == 443) // https
    dwINetFlags |= INTERNET_FLAG_SECURE;

 	hInetConnect = InternetConnect(hInetOpen, strServer, USHORT(ulPort), NULL, NULL, INTERNET_SERVICE_HTTP, dwINetFlags, 0);
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  dwHTTPFlags = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  if (ulPort == 443) // https
    dwHTTPFlags |= INTERNET_FLAG_SECURE;

  hInetReq = HttpOpenRequest(hInetConnect, _T("PUT"), strResource, HTTP_VERSION, NULL, (LPCTSTR*)AcceptTypes, dwHTTPFlags, 0);
  if (!hInetReq)
    CHECK_LAST_ERROR(Err)

  if (!fmap.Open(strFile, CFileMapping::accessRead))
    CHECK_LAST_ERROR(Err)

#ifdef TRACE_TIME
  __tt.Start();
#endif

  if (!HttpSendRequest(hInetReq, NULL, 0, fmap.GetPointer(), ULONG(fmap.GetSize())))
    CHECK_LAST_ERROR(Err)

  Err = GetHTTPError(hInetReq);
  CHECK_ERR(Err)

End:
  if (hInetReq)       InternetCloseHandle(hInetReq);
  if (hInetConnect)   InternetCloseHandle(hInetConnect);
  if (hInetOpen)      InternetCloseHandle(hInetOpen);

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

	return S_OK;
}
/*
//------------------------------------------------------------------------
STDMETHODIMP CUpload::PUTFile(BSTR File, BSTR URL, VARIANT *pErrorCode)
{
  // Implementacion basada en IXMLHTTPRequest. Consume mas memoria, porque solo se puede hacer un send,
  // pero atraviesa proxies con las credenciales utilizadas en el explorador

  USES_ERROR_LOG("CUpload::PUTFile (XML)");
  CString strLocalFile(File), strLocalFileName;
  CString strURL(URL);

#ifdef TRACE_TIME
  CTraceTime __tt(_T("CUpload::PUTFileXML(%I64d) to %s"), GetFileSizeFN(strLocalFile), strURL);
#endif

  LPSTREAM pStream = NULL;
  MSXML2::IXMLHTTPRequestPtr pIXMLHTTPRequest = NULL;
  long Err = 0;
  long lStatus = 0;
  
  // Si la URL acaba en barra, añadimos el nombre origen
  if (strURL[strURL.GetLength()-1] == _T('/'))
  {
    LNGetFileName(strLocalFile, strLocalFileName);
    strURL += strLocalFileName;
  }

  Err = SHCreateStreamOnFile(strLocalFile, STGM_READ | STGM_SHARE_DENY_WRITE, &pStream);
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest.CreateInstance(_T("Msxml2.XMLHTTP"));
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest->open(_bstr_t(_T("PUT")), _bstr_t(LPCTSTR(strURL)), false);
  CHECK_ERR(Err);

#ifdef TRACE_TIME
  __tt.Start();
#endif

  Err = pIXMLHTTPRequest->send(pStream);
  CHECK_ERR(Err);

  Err = pIXMLHTTPRequest->get_status(&lStatus);
  CHECK_ERR(Err);

  if (lStatus >= 400) Err = HTTP_STATUS_TO_ERR(lStatus);
  CHECK_ERR(Err);

End:
  if (pStream)
    pStream->Release();
  
  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
*/
//------------------------------------------------------------------------
static long GetVia(HINTERNET hInet, CString &strVia)
{
  TCHAR chVia[500];
  DWORD dwSize = sizeof(chVia);
  DWORD dwIndex = 0;

  if (!HttpQueryInfo(hInet, HTTP_QUERY_VIA, chVia, &dwSize, &dwIndex))
    return GetLastHR();

  strVia = chVia;
  return 0;
}
//------------------------------------------------------------------------
static void DiscardResult(HINTERNET hInet, PVOID pBuffer, ULONG cbSize)
{
  DWORD cbRead = 0;

  do
    InternetReadFile(hInet, pBuffer, cbSize, &cbRead);
  while (cbRead != 0);
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::UploadFile(BSTR File, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet
  USES_ERROR_LOG("CUpload::UploadFile");
  long Err = 0;
  CString    strFile(File), strURL(URL), strProtocol, strServer(Server), strResource(URL), strFileName;
  ULONG ulPort = INTERNET_DEFAULT_HTTP_PORT;

  HINTERNET  hInetOpen      = NULL;
  HINTERNET  hInetConnect   = NULL;
  HINTERNET  hInetReq       = NULL;
  DWORD      dwINetFlags    = 0;
  DWORD      dwHTTPFlags    = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  LPTSTR     AcceptTypes[]  = {_T("*" "/" "*"), NULL}; 
  HANDLE     hFile = INVALID_HANDLE_VALUE;
  INTERNET_BUFFERS BufferIn = {0};
  DWORD   BytesRead;
  DWORD   BytesWritten;
  BYTE    pBuffer[100000];            // Read from file in 1K chunks

#ifdef TRACE_TIME
  CTraceTime __tt(_T("CUpload::UploadFile(%I64d) to %s"), GetFileSizeFN(strFile), strURL);
#endif

  if (strServer.IsEmpty())
  {
    Err = CrackURL(strURL, strProtocol, strServer, ulPort, strResource);
    CHECK_ERR(Err);
  }
  else
  {
    int iDotPos = strServer.Find(_T(":"));         
    if (iDotPos != -1)
    {
      ulPort = _ttoi(LPCTSTR(strServer) + iDotPos + 1);
      strServer = strServer.Left(iDotPos);
    }
  }

  // Utilizar nombre origen en el servidor
  if (strResource[strResource.GetLength()-1] == _T('/'))
  {
    LNGetFileName(strFile, strFileName);
    strResource += strFileName;
  }

  // Abrimos fichero
  hFile = CreateFile(strFile, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
  if (hFile == INVALID_HANDLE_VALUE)
    CHECK_LAST_ERROR(Err)

Request:
  if (SetFilePointer(hFile, 0, NULL, FILE_BEGIN) == INVALID_SET_FILE_POINTER)
    CHECK_LAST_ERROR(Err)

  // Acceso a internet
  hInetOpen = InternetOpen(_T("POSTer"), INTERNET_OPEN_TYPE_PRECONFIG, NULL, 0, 0);
  if (!hInetOpen)
    CHECK_LAST_ERROR(Err)

  dwINetFlags = 0; //INTERNET_FLAG_EXISTING_CONNECT;
  if (ulPort == 443) // https
    dwINetFlags |= INTERNET_FLAG_SECURE;

 	hInetConnect = InternetConnect(hInetOpen, strServer, USHORT(ulPort), NULL, NULL, INTERNET_SERVICE_HTTP, dwINetFlags, 0);
  if (!hInetConnect)
    CHECK_LAST_ERROR(Err)

  dwHTTPFlags = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
  if (ulPort == 443) // https
    dwHTTPFlags |= INTERNET_FLAG_SECURE;

  hInetReq = HttpOpenRequest(hInetConnect, _T("PUT"), strResource, HTTP_VERSION, NULL, (LPCTSTR*)AcceptTypes, dwHTTPFlags, 0);
  if (!hInetReq)
    CHECK_LAST_ERROR(Err)

  // Preparamos HttpSendRequestEx
  BufferIn.dwStructSize = sizeof(INTERNET_BUFFERS);
  BufferIn.Next = NULL; 
  if (m_strBasicProxyAuth.IsEmpty())
  {
    BufferIn.lpcszHeader = NULL;
    BufferIn.dwHeadersLength = 0;
  }
  else
  {
    BufferIn.lpcszHeader = m_strBasicProxyAuth; 
    BufferIn.dwHeadersLength = m_strBasicProxyAuth.GetLength();
  }

  BufferIn.dwHeadersTotal = 0;
  BufferIn.lpvBuffer = NULL;                
  BufferIn.dwBufferLength = 0;
  BufferIn.dwBufferTotal = GetFileSize(hFile, NULL);
  BufferIn.dwOffsetLow = 0;
  BufferIn.dwOffsetHigh = 0;

  if (!HttpSendRequestEx(hInetReq, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC, 0))
    CHECK_LAST_ERROR(Err)

  // Enviamos los datos
  do
  {
    if (!ReadFile(hFile, pBuffer, sizeof(pBuffer), &BytesRead, NULL))
    {
      Err = GetLastHR();
      break;
    }

    if (!InternetWriteFile(hInetReq, pBuffer, BytesRead, &BytesWritten))
    {
      Err = GetLastHR();
      break;
    }

  }
  while (BytesRead == sizeof(pBuffer));

  // Finalizamos la peticion
  if (!HttpEndRequest(hInetReq, NULL, HSR_INITIATE | HSR_SYNC, 0))
  {
    Err = GetLastError();
    //DiscardResult(hInetReq, pBuffer, sizeof(pBuffer));
    if (Err == ERROR_INTERNET_FORCE_RETRY && GetHTTPError(hInetReq) == HTTP_STATUS_TO_ERR(HTTP_STATUS_PROXY_AUTH_REQ))
    {
      AFX_MANAGE_STATE(AfxGetStaticModuleState());

      CProxyDlg dlg;
      GetVia(hInetReq, dlg.m_strServer);
      Err = dlg.DoModal();
      if (Err == IDOK)
      {
        Err = CreateProxyAuth(dlg.m_strUserName, dlg.m_strPassword, m_strBasicProxyAuth);
        CHECK_ERR(Err)
        
        // Cerramos handles y repetimos la peticion        
        InternetCloseHandle(hInetReq);
        InternetCloseHandle(hInetConnect);
        InternetCloseHandle(hInetOpen);
        goto Request;
      }
      else
      {
        Err = HRESULT_FROM_WIN32(ERROR_CANCELLED);
        goto End;
      }
    }
  }

  Err = GetHTTPError(hInetReq);
  //DiscardResult(hInetReq, pBuffer, sizeof(pBuffer));
  CHECK_ERR(Err)

End:
  if (hInetReq)       InternetCloseHandle(hInetReq);
  if (hInetConnect)   InternetCloseHandle(hInetConnect);
  if (hInetOpen)      InternetCloseHandle(hInetOpen);
  if (hFile != INVALID_HANDLE_VALUE) CloseHandle(hFile);

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

	return S_OK;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::GETFile(BSTR URL, BSTR LocalFile, VARIANT *pErrorCode)
{
  // Implementacion basada en URL Monikers
  USES_ERROR_LOG("CUpload::GETFile");
#ifdef TRACE_TIME
  CTraceTime __tt;
#endif

  CString strURL(URL), strLocalPath(LocalFile), strLocalFileName;
  CreateDownloadFileName(strURL, strLocalPath, strLocalFileName);

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = URLDownloadToFile((LPUNKNOWN)(void*)this, strURL, strLocalFileName, 0, NULL);

#ifdef TRACE_TIME
  __tt.Trace(CTraceTime::STOP | CTraceTime::MSG, _T("CUpload::GETFile(%I64d)"), GetFileSizeFN(strLocalPath));
#endif

  return  S_OK;
}
//----------------------------------------------------------------------------------------------------
