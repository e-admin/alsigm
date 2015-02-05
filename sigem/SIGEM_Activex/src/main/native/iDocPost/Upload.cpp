/************************************************************************/
/*                                                                      */
/* Upload.cpp : Implementation of CUpload                               */
/*                                                                      */
/*  Mensajes:                                                           */
/*                                                                      */
/************************************************************************/
#include "stdafx.h"
#include <urlmon.h>

#include "..\Common\LexNetErrs.h"
#include "..\Common\Base64.h"
#include "..\Common\FileMapping.h"
#include "..\LNUtil\LNUtil.h"

#include "browsedirectory.h"
#include "ProxyDlg.h"
#include "iDocPost.h"
#include "Upload.h"

// Descomentar para utilizar la implementacion de UploadFile basada en IXMLHTTPRequest
//#import "msxml3.dll"
//#include <shlwapi.h>

#define  MAX_BUF   100000

#undef TRACE_TIME
//#define TRACE_TIME

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
  CString Msg;
  
  if (!HttpQueryInfo(hInet, HTTP_QUERY_STATUS_CODE | HTTP_QUERY_FLAG_NUMBER, &dwStatus, &dwStatusSize, &dwIndex))
  {    
    Err = GetLastHR(); 
    Msg.Format("Err en GetLatHR= %d",Err);
    //::MessageBox(NULL,Msg,"HttpQueryInfo",MB_OK);
    
  }
  else if (dwStatus >= 400)
  {
     CString Msg;
     Msg.Format("Status = %d, Error = %d",dwStatus,GetLastError());
    //::MessageBox(NULL,Msg,"Error en HttpQueryInfo",MB_OK);
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
// CUpload
//------------------------------------------------------------------------
CUpload::CUpload()
{
#ifdef _DEBUG
  GetErrorManager().SetTraceToFile(_T("LN.log"), NULL);
#endif
}
//------------------------------------------------------------------------
CUpload::~CUpload()
{
}
//------------------------------------------------------------------------

STDMETHODIMP CUpload::UploadFile(BSTR File, BSTR Server, BSTR URL, long bRemove, VARIANT* pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet

   CString Msg;
   USES_ERROR_LOG("CUpload::UploadFile");
   long Err = S_OK;

   
   try
   {
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
      BYTE    pBuffer[102400];            // Read from file in 100K chunks
      
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

    dwINetFlags = INTERNET_FLAG_EXISTING_CONNECT;
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
 
    if (!HttpSendRequestEx(hInetReq, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC |HSR_CHUNKED, 0))
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

    CloseHandle(hFile);
    hFile = INVALID_HANDLE_VALUE;
    if (bRemove)
      ::DeleteFile(strFile);
    
End:
    if (hInetReq)       InternetCloseHandle(hInetReq);
    if (hInetConnect)   InternetCloseHandle(hInetConnect);
    if (hInetOpen)      InternetCloseHandle(hInetOpen);
    if (hFile != INVALID_HANDLE_VALUE) CloseHandle(hFile);
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

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;
  
	return S_OK;
}

  
STDMETHODIMP CUpload::UploadFileX(BSTR File, BSTR Server, BSTR Page, BSTR VirtualDir, 
                                  BSTR DstFileName, long bRemove,VARIANT* pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet

   CString Msg;
   USES_ERROR_LOG("CUpload::UploadFileX");
   long Err = S_OK;

   
   try
   {
      CString    strFile(File), strVDir(VirtualDir), strProtocol, strServer(Server), strPage(Page), strFileName;
      CString    FileDst(DstFileName), strResource;
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
      BYTE    pBuffer[102400];            // Read from file in 100K chunks
      
  #ifdef TRACE_TIME
    CTraceTime __tt(_T("CUpload::UploadFileX(%I64d) to %s"), GetFileSizeFN(strFile), strURL);
  #endif

      
      int iDotPos = strServer.Find(_T(":"));         
      if (iDotPos != -1)
      {
         ulPort = _ttoi(LPCTSTR(strServer) + iDotPos + 1);
         strServer = strServer.Left(iDotPos);
      }
      
            
      // Abrimos fichero
      hFile = CreateFile(strFile, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
      if (hFile == INVALID_HANDLE_VALUE)
        CHECK_LAST_ERROR(Err)

  Request:
    if (SetFilePointer(hFile, 0, NULL, FILE_BEGIN) == INVALID_SET_FILE_POINTER)
      CHECK_LAST_ERROR(Err)

    // Acceso a internet
    hInetOpen = InternetOpen( "HttpSendRequestEx", INTERNET_OPEN_TYPE_PRECONFIG,
		          NULL, NULL, 0);	
    if (!hInetOpen)
      CHECK_LAST_ERROR(Err)


    dwINetFlags = INTERNET_FLAG_EXISTING_CONNECT;
    if (ulPort == 443) // https
      dwINetFlags |= INTERNET_FLAG_SECURE;
     
 	 hInetConnect = InternetConnect(hInetOpen, strServer, USHORT(ulPort), NULL, NULL, INTERNET_SERVICE_HTTP, dwINetFlags, 0);    
    if (!hInetConnect)
      CHECK_LAST_ERROR(Err)

    dwHTTPFlags = INTERNET_FLAG_RELOAD | INTERNET_FLAG_NO_CACHE_WRITE | INTERNET_FLAG_KEEP_CONNECTION;
    if (ulPort == 443) // https
      dwHTTPFlags |= INTERNET_FLAG_SECURE;
 
    if (!FileDst.IsEmpty() && !strVDir.IsEmpty())
      strResource.Format("%s?FileName=%s&Dir=%s",strPage,FileDst,strVDir);   
    else
      strResource = strPage;


    hInetReq = HttpOpenRequest(hInetConnect, _T("POST"), strResource, NULL, NULL, NULL, dwHTTPFlags, 0);   
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

    if (!HttpSendRequestEx(hInetReq, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC |HSR_CHUNKED, 0))
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
      if ( Err == ERROR_INTERNET_FORCE_RETRY && 
           (GetHTTPError(hInetReq) == HTTP_STATUS_TO_ERR(HTTP_STATUS_PROXY_AUTH_REQ)||
           GetHTTPError(hInetReq) == HTTP_STATUS_TO_ERR(HTTP_STATUS_DENIED)) )
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

    CloseHandle(hFile);
    hFile = INVALID_HANDLE_VALUE;
    if (bRemove)
      ::DeleteFile(strFile);
    
End:
    if (hInetReq)       InternetCloseHandle(hInetReq);
    if (hInetConnect)   InternetCloseHandle(hInetConnect);
    if (hInetOpen)      InternetCloseHandle(hInetOpen);
    if (hFile != INVALID_HANDLE_VALUE) CloseHandle(hFile);
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

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;  

  return S_OK;
}

CString GenerateMultipartBoundary()
{
   CString MBoundary;
   CString boundaryPrefix("---------------------------");

   //Necesitamos 12 dígitos en hexadecimal
   int r0 = rand() & 0xffff;
   int r1 = rand() & 0xffff;
   int r2 = rand() & 0xffff;

   char temp[13];
   sprintf_s(temp,"%04X%04X%04X",r0,r1,r2);

   MBoundary.Format("%s%s",boundaryPrefix,temp);

   return(MBoundary);
}

BYTE* GenerateRequestBody(CString& FileName, CString& boundary,DWORD& length)
{

   BYTE*   pBuff = NULL;
   CString RequestBody;
   CString field_name("upload_file");
   CString contentDisposition;
   CString FileContent;
   CString contentType("Content-Type: application/octet-stream");
   DWORD   dwFileSize;
   BYTE*   p = NULL;
   CString Rest;
   DWORD   dwBytesRead;
   DWORD   RLength;
   
   Rest.Format("\r\n--%s--\r\n",boundary);
   HANDLE hFile = CreateFile(FileName,GENERIC_READ,0,NULL,OPEN_EXISTING,0,NULL);
   if (hFile != INVALID_HANDLE_VALUE)
   {
      dwFileSize = GetFileSize(hFile,NULL); 
      p = (BYTE*) malloc(dwFileSize);
      if (p == NULL) goto End;

      ReadFile(hFile,p, dwFileSize,&dwBytesRead,NULL);
   }
   else
      goto End;
 
   //Por cada fichero..   
   contentDisposition.Format("Content-Disposition: form-data; name= \"%s\"; filename=\"%s\"",
                             field_name,FileName); 
   RequestBody.Format("--%s\r\n%s\r\n%s\r\n\r\n", boundary,contentDisposition,contentType);


   pBuff = (BYTE*) malloc(RequestBody.GetLength() + dwFileSize + Rest.GetLength());
   if (pBuff != NULL)
   {
      memcpy(pBuff,RequestBody,RequestBody.GetLength());
      RLength = RequestBody.GetLength();

      memcpy(pBuff+RLength ,p,dwFileSize);
      RLength += dwFileSize;

      memcpy(pBuff+RLength, Rest, Rest.GetLength());
      RLength += Rest.GetLength();

      length = RLength;
   }

End:

   if (hFile != INVALID_HANDLE_VALUE)
   {
      CloseHandle(hFile);
      hFile = INVALID_HANDLE_VALUE;
   }

   if (p != NULL)
      free(p);

   return (pBuff);

}

STDMETHODIMP CUpload::UploadFileM(BSTR File, BSTR Server, BSTR Page, BSTR VirtualDir, 
                                  BSTR DstFileName, long bRemove,VARIANT* pErrorCode)
{

   USES_ERROR_LOG("CUpload::UploadFileM");
   long    Err = 0;
   BYTE*   pBuffer = NULL;
   BYTE    Buff[102400];
   INTERNET_BUFFERS BufferIn = {0};
   CString boundary;
   CString ServerName(Server);
	CString ObjectName;
   CString strPage(Page);
   CString VDir(VirtualDir);
   CString DstFile(DstFileName);
	CString LocalFile(File);
   CString contentTypeHeader; 
   DWORD   length, Rlength;
   DWORD   BytesRead;
   DWORD   BytesWritten;
   INTERNET_PORT nServerPort = INTERNET_DEFAULT_HTTP_PORT;
   HINTERNET     hInternet = NULL;
   HINTERNET     hConnect = NULL;
   HINTERNET     hRequest = NULL;
   LPCTSTR       lpszUserName = NULL;
	LPCTSTR       lpszPassword = NULL;
	DWORD         dwConnectFlags = 0;
	DWORD         dwConnectContext = 0;
   DWORD dwOpenRequestFlags = INTERNET_FLAG_IGNORE_REDIRECT_TO_HTTP |
	  	  						      INTERNET_FLAG_IGNORE_REDIRECT_TO_HTTPS | 
                              INTERNET_FLAG_NO_CACHE_WRITE |                              
								      INTERNET_FLAG_KEEP_CONNECTION |
								      INTERNET_FLAG_NO_AUTH |
								      INTERNET_FLAG_NO_AUTO_REDIRECT |
								      INTERNET_FLAG_NO_COOKIES |
								      INTERNET_FLAG_NO_UI |
								      INTERNET_FLAG_RELOAD;	


   srand((int)time(NULL));

   try
   {      
      int iDotPos = ServerName.Find(_T(":"));
      if (iDotPos != -1)
      {
         nServerPort =(INTERNET_PORT)_ttoi(LPCTSTR(ServerName) + iDotPos + 1);
         ServerName = ServerName.Left(iDotPos);
      }
	
      if (!DstFile.IsEmpty() && !VDir.IsEmpty())
         ObjectName.Format("%s?FileName=%s&Dir=%s",strPage,DstFile,VDir);
      else
         ObjectName = strPage;

	   hInternet = InternetOpen("HttpSendRequestEx", INTERNET_OPEN_TYPE_PRECONFIG, NULL, NULL, 0);
	   if (!hInternet)
	      CHECK_LAST_ERROR(Err);

      dwConnectFlags = INTERNET_FLAG_EXISTING_CONNECT;
      if (nServerPort == 443) //https
         dwConnectFlags |= INTERNET_FLAG_SECURE;

	   hConnect = InternetConnect(hInternet, ServerName, nServerPort,
									      lpszUserName, lpszPassword,
									      INTERNET_SERVICE_HTTP,
									      dwConnectFlags, dwConnectContext);
	   if (!hConnect)
         CHECK_LAST_ERROR(Err);
	
      if (nServerPort == 443) //https
         dwOpenRequestFlags |= INTERNET_FLAG_SECURE;

	   hRequest = HttpOpenRequest(hConnect, _T("POST"), ObjectName, NULL,
											 NULL, NULL,dwOpenRequestFlags, 0);
	   if (!hRequest)
         CHECK_LAST_ERROR(Err);
	
	   // Uso de  POST para enviar el fichero:
	   // Añadir la cabecera Content-Type:
	   boundary = GenerateMultipartBoundary();
	   contentTypeHeader.Format("Content-Type: multipart/form-data; boundary=%s", boundary);
	   HttpAddRequestHeaders(hRequest, contentTypeHeader, -1, HTTP_ADDREQ_FLAG_ADD);

   
      pBuffer = GenerateRequestBody(LocalFile,boundary,length);
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
      BufferIn.dwBufferTotal = length;
      BufferIn.dwOffsetLow = 0;
      BufferIn.dwOffsetHigh = 0;
   

     if (!HttpSendRequestEx(hRequest, &BufferIn, NULL, HSR_INITIATE | HSR_SYNC |HSR_CHUNKED, 0))
      CHECK_LAST_ERROR(Err)
      
      BytesRead = sizeof(Buff);
      Rlength = 0;
      while(Rlength + BytesRead < length)
      {
         memcpy(Buff,pBuffer + Rlength, BytesRead);
         Rlength = Rlength + BytesRead;

         if (!InternetWriteFile(hRequest, Buff, BytesRead, &BytesWritten))
         {
           Err = GetLastHR();
           break;
         } 

      }
      if (length > Rlength)
      {
         BytesRead = length - Rlength;
         memcpy(Buff,pBuffer + Rlength ,BytesRead);

         if (!InternetWriteFile(hRequest, Buff, BytesRead, &BytesWritten))
            Err = GetLastHR();
        
      }

      HttpEndRequest(hRequest, NULL, HSR_INITIATE | HSR_SYNC, 0);    
	       
      Err = GetHTTPError(hRequest);
      CHECK_ERR(Err);

      if (bRemove)
        ::DeleteFile(LocalFile);
      

End:
	
      if (pBuffer != NULL)
         free(pBuffer);
      
      if (hRequest)  InternetCloseHandle(hRequest);
      if (hConnect)  InternetCloseHandle(hConnect);
      if (hInternet) InternetCloseHandle(hInternet);
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

   V_VT(pErrorCode) = VT_I4;
   V_I4(pErrorCode) = Err;

   return S_OK;

	
}

/*
STDMETHODIMP CUpload::UploadFileM(BSTR File, BSTR Server, BSTR Page, BSTR VirtualDir, 
                                  BSTR DstFileName, long bRemove,VARIANT* pErrorCode)
{

   USES_ERROR_LOG("CUpload::UploadFileM");
   long    Err = S_OK;
   BYTE*   pBuffer = NULL;
   BOOL    bResult;
   CString boundary;
   CString ServerName(Server);
	CString ObjectName;
   CString strPage(Page);
   CString VDir(VirtualDir);
   CString DstFile(DstFileName);
	CString LocalFile(File);
   CString contentTypeHeader; 
   DWORD         length;
   INTERNET_PORT nServerPort = INTERNET_DEFAULT_HTTP_PORT;
   HINTERNET     hInternet = NULL;
   HINTERNET     hConnect = NULL;
   HINTERNET     hRequest = NULL;
   LPCTSTR       lpszUserName = NULL;
	LPCTSTR       lpszPassword = NULL;
	DWORD         dwConnectFlags = 0;
	DWORD         dwConnectContext = 0;
   DWORD dwOpenRequestFlags = INTERNET_FLAG_IGNORE_REDIRECT_TO_HTTP |
	  	  						      INTERNET_FLAG_IGNORE_REDIRECT_TO_HTTPS | 
                              INTERNET_FLAG_NO_CACHE_WRITE |                              
								      INTERNET_FLAG_KEEP_CONNECTION |
								      INTERNET_FLAG_NO_AUTH |
								      INTERNET_FLAG_NO_AUTO_REDIRECT |
								      INTERNET_FLAG_NO_COOKIES |
								      INTERNET_FLAG_NO_UI |
								      INTERNET_FLAG_RELOAD;	

   srand((int)time(NULL));

   try
   {      
      int iDotPos = ServerName.Find(_T(":"));
      if (iDotPos != -1)
      {
         nServerPort =_ttoi(LPCTSTR(ServerName) + iDotPos + 1);
         ServerName = ServerName.Left(iDotPos);
      }
	
      if (!DstFile.IsEmpty() && !VDir.IsEmpty())
         ObjectName.Format("%s?FileName=%s&Dir=%s",strPage,DstFile,VDir);
      else
         ObjectName = strPage;

	   hInternet = InternetOpen("HttpSendRequestEx", INTERNET_OPEN_TYPE_PRECONFIG, NULL, NULL, 0);
	   if (!hInternet)
	      CHECK_LAST_ERROR(Err);

      dwConnectFlags = INTERNET_FLAG_EXISTING_CONNECT;
      if (nServerPort == 443) //https
         dwConnectFlags |= INTERNET_FLAG_SECURE;

	   hConnect = InternetConnect(hInternet, ServerName, nServerPort,
									      lpszUserName, lpszPassword,
									      INTERNET_SERVICE_HTTP,
									      dwConnectFlags, dwConnectContext);
	   if (!hConnect)
         CHECK_LAST_ERROR(Err);
	
      if (nServerPort == 443) //https
         dwOpenRequestFlags |= INTERNET_FLAG_SECURE;

	   hRequest = HttpOpenRequest(hConnect, _T("POST"), ObjectName, NULL,
											 NULL, NULL,dwOpenRequestFlags, 0);
	   if (!hRequest)
         CHECK_LAST_ERROR(Err);
	
	   // Uso de  POST para enviar el fichero:
	   // Añadir la cabecera Content-Type:
	   boundary = GenerateMultipartBoundary();
	   contentTypeHeader.Format("Content-Type: multipart/form-data; boundary=%s", boundary);
	   HttpAddRequestHeaders(hRequest, contentTypeHeader, -1, HTTP_ADDREQ_FLAG_ADD);

   
      pBuffer = GenerateRequestBody(LocalFile,boundary,length);
	   bResult = HttpSendRequest(hRequest, NULL, 0,pBuffer ,length); 
	   if (!bResult)
         CHECK_LAST_ERROR(Err);

      
      Err = GetHTTPError(hRequest);
      CHECK_ERR(Err);
      if (bRemove)
        ::DeleteFile(LocalFile);
      

End:
	
      if (pBuffer != NULL)
         free(pBuffer);
      
      if (hRequest)  InternetCloseHandle(hRequest);
      if (hConnect)  InternetCloseHandle(hConnect);
      if (hInternet) InternetCloseHandle(hInternet);
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

   V_VT(pErrorCode) = VT_I4;
   V_I4(pErrorCode) = Err;
   
   return S_OK;

	
}
*/


//------------------------------------------------------------------------
STDMETHODIMP CUpload::DownloadFile(BSTR URL, BSTR LocalPath, VARIANT* pFileLoc, VARIANT* pErrorCode)
{
  // Implementacion basada en internet connections
  USES_ERROR_LOG("CUpload::DownloadFile");
  long Err = S_OK;
  try
  {
#ifdef TRACE_TIME
    CTraceTime __tt;
#endif

    HINTERNET  hInetOpen     = NULL;
    HINTERNET  hInetConnect  = NULL;
    CHAR       Head[]        = _T("Accept: *" "/" "*\r\n\r\n");   // Lo pongo separado para que admita comentar el bloque
    FILE*      hDst;

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

    _tfopen_s(&hDst,strLocalFileName, _T("wb"));
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

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
//------------------------------------------------------------------------
STDMETHODIMP CUpload::CallURL(BSTR URL, VARIANT *pResponse, VARIANT *pErrorCode)
{
  // Implementacion basada en URL Monikers
  long Err = S_OK;
  try
  {
    CString strURL(URL);
    CString strResponse;

    V_VT(pErrorCode) = VT_I4;
    V_I4(pErrorCode) = 0;
    V_VT(pResponse)  = VT_BSTR;

    LPSTREAM pStream = NULL;
    ULONG cbRead = 0;
    BYTE  buffer[MAX_BUF];

    Err = URLOpenBlockingStream((LPUNKNOWN)(void*)this, strURL, &pStream, 0, NULL);
    if (Err == S_OK)
    {
      while (1)
      {
        Err = pStream->Read(buffer, sizeof(buffer)-1, &cbRead);
        if (cbRead > 0)
        {
          buffer[cbRead] = 0;
          strResponse += (char*)buffer;
        }

        if (Err != S_OK || cbRead == 0)
        {
          Err = S_OK;
          break;
        }
      }

      pStream->Release();
    }

    V_BSTR(pResponse) = strResponse.AllocSysString();
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

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
/*
//----------------------------------------------------------------------------------------------------
STDMETHODIMP CUpload::CallURL(BSTR URL, VARIANT *pResponse, VARIANT *pErrorCode)
{
  // Implementacion basada en IXMLHTTPRequest
  long Err = S_OK;
  try
  {
    MSXML2::IXMLHTTPRequestPtr pIXMLHTTPRequest = NULL;

    Err = pIXMLHTTPRequest.CreateInstance(_T("Msxml2.XMLHTTP"));
    CHECK_ERR(Err)

    Err = pIXMLHTTPRequest->open(_bstr_t(_T("GET")), URL, false);
    CHECK_ERR(Err)

    Err = pIXMLHTTPRequest->send();
    CHECK_ERR(Err)

    V_VT(pResponse)   = VT_BSTR;
    V_BSTR(pResponse) = pIXMLHTTPRequest->responseText;
End:;
  }

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
*/
//----------------------------------------------------------------------------------------------------
STDMETHODIMP CUpload::DeleteFile(BSTR FileName)
{
  long Err = S_OK;
  try
  {
    CString strFileName(FileName);
    ::DeleteFile(strFileName);
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
STDMETHODIMP CUpload::SelectFile(long bSaving, VARIANT* pFileName)
{
  long Err = S_OK;
  try
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
STDMETHODIMP CUpload::SelectDir(VARIANT* pDir)
{
  long Err = S_OK;
  try
  {
    CString Dir;

    AfxSetResourceHandle(_Module.GetResourceInstance());

    CString  Title;
    Title.LoadString(IDS_SELECT_DIR);

    CBrowseDirectory Browse;
    Dir = Browse.Do(Title, NULL);

    V_VT(pDir)   = VT_BSTR;
    V_BSTR(pDir) = Dir.AllocSysString();
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
STDMETHODIMP CUpload::PUTFile(BSTR File, BSTR URL, VARIANT *pErrorCode)
{
  // Implementacion tradicional basada en conexiones internet
  USES_ERROR_LOG("CUpload::PUTFile");
  long Err = S_OK;
  try
  {
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
  long Err = S_OK;
  try
  {
    CString strLocalFile(File), strLocalFileName;
    CString strURL(URL);

#ifdef TRACE_TIME
    CTraceTime __tt(_T("CUpload::PUTFileXML(%I64d) to %s"), GetFileSizeFN(strLocalFile), strURL);
#endif

    LPSTREAM pStream = NULL;
    MSXML2::IXMLHTTPRequestPtr pIXMLHTTPRequest = NULL;
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
  
  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
*/
//------------------------------------------------------------------------
STDMETHODIMP CUpload::GETFile(BSTR URL, BSTR LocalFile, VARIANT *pErrorCode)
{
  USES_ERROR_LOG("CUpload::GETFile");
  long Err = S_OK;
  try
  {
    // Implementacion basada en URL Monikers
#ifdef TRACE_TIME
    CTraceTime __tt;
#endif

    CString strURL(URL), strLocalPath(LocalFile), strLocalFileName;
    CreateDownloadFileName(strURL, strLocalPath, strLocalFileName);

    Err = URLDownloadToFile((LPUNKNOWN)(void*)this, strURL, strLocalFileName, 0, NULL);

#ifdef TRACE_TIME
    __tt.Trace(CTraceTime::STOP | CTraceTime::MSG, _T("CUpload::GETFile(%I64d)"), GetFileSizeFN(strLocalPath));
#endif
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

  V_VT(pErrorCode) = VT_I4;
  V_I4(pErrorCode) = Err;

  return  S_OK;
}
//----------------------------------------------------------------------------------------------------
