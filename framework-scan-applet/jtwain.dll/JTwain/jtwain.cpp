// jtwain.cpp

// Copyright (c) 2006-2009, Jeff Friesen (jeff@javajeff.mb.ca).

//#include <windows.h>
#include "stdafx.h"

#include "twain.h" // Standard TWAIN header.

#include "jtwain.h" // JTWAIN header with JNI function
                                          // prototypes.

#include <stdlib.h> 

#define MAJOR_VERSION 1
#define MINOR_VERSION 3
#define TITLE_VERSION "JTwain 1.3"

// g_pDSM_Entry holds the address of function DSM_Entry() in TWAIN_32.DLL. If
// this address is 0, either TWAIN_32.DLL could not be loaded or there is no
// DSM_Entry() function in TWAIN_32.DLL.

static DSMENTRYPROC g_pDSM_Entry;

// g_hinstDLL holds this DLL's instance handle. It's initialized in response
// to the DLL_PROCESS_ATTACH message. This handle is passed to CreateWindow()
// when a window is created, just before opening the data source manager.

static HINSTANCE g_hinstDLL;

// g_hLib holds the handle of the TWAIN_32.DLL library. It's initialized in
// response to the loadTWAIN() function, which loads TWAIN_32.DLL. It's set to
// 0 after freeing this library in unloadTWAIN().

static HINSTANCE g_hLib;

// g_hwnd holds the handle to the window that must be passed to the open data
// source manager call.

static HWND g_hwnd;

// g_jobjectsCount holds a count of the number of jobjects in the vector.

static int g_jobjectsCount = 0;

// g_jobjectsLimit holds the maximum storage index of the vector's g_jobjects
// array before the vector grows that array.

static int g_jobjectsLimit = 0;

// g_rc holds the return code from the most recent operation.

static int g_rc;

// g_jobjects holds the address of the vector's resizable jobjects array.

static jobject *g_jobjects = 0;

// g_AppID is a TWAIN identity structure that uniquely identifies the
// application process responsible for making calls to function DSM_Entry().

static TW_IDENTITY g_AppID;

// g_SrcID is a TWAIN identity structure that uniquely identifies the currently
// open data source.

static TW_IDENTITY g_SrcID;

// Helper function prototypes.

static bool    loadTWAIN      (void);
static void    throwJTE       (JNIEnv *, const char *);
static void    throwUC        (JNIEnv *, const char *);
static void    unloadTWAIN    (void);
static bool    vectorAppend   (jobject);
static bool    vectorCreate   (void);
static void    vectorDestroy  (void);
static jobject vectorGet      (int);
static int     vectorSize     (void);
static jobject xferDIBtoImage (LPBITMAPINFOHEADER, JNIEnv *);

static jboolean saveAsBmp (HANDLE,JNIEnv *,jstring,jboolean,jstring,int);
static jobject newFile (JNIEnv *, const char *);


// ===========================================================================
// DllMain
//
// DLL entry point. Four messages are sent from Windows to this function:
//
// 1) DLL_PROCESS_ATTACH
//
// This message is sent when the DLL is first mapped into a process's address
// space.
//
// 2) DLL_THREAD_ATTACH
//
// This message is sent when a thread is created in the DLL's owner process.
//
// 3) DLL_THREAD_DETACH
//
// This message is sent when a thread created in the DLL's owner process is
// terminated in any fashion other than by calling TerminateThread().
//
// 4) DLL_PROCESS_DETACH
//
// This message is sent when the DLL is unmapped from a process's address
// space -- unless some thread calls TerminateProcess().
//
// Arguments:
//
// hinstDLL    - DLL instance handle
// fdwReason   - a message describing the reason for calling DllMain()
// lpvReserved - additional data that may be present during DLL_PROCESS_ATTACH
//               and DLL_PROCESS_DETACH messages
//
// Return:
//
// TRUE if DLL's initialization (during DLL_PROCESS_ATTACH) was successful, or
// FALSE if unsuccessful. This return value is only recognized in response to
// the DLL_PROCESS_ATTACH message.
// ===========================================================================

//#pragma argsused
bool WINAPI DllMain (HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved)
{
   if (fdwReason == DLL_PROCESS_ATTACH)
   {
       // Save instance handle for later access in other functions.

       g_hinstDLL = hinstDLL;

       // Initialize g_AppID. This structure is passed to DSM_Entry() in each
       // function call.

       g_AppID.Id = 0;
       g_AppID.Version.MajorNum = MAJOR_VERSION;
       g_AppID.Version.MinorNum = MINOR_VERSION;
       g_AppID.Version.Language = TWLG_ENGLISH_USA;
       g_AppID.Version.Country = TWCY_USA;

       lstrcpy (g_AppID.Version.Info, "IECISA JTwain 1.5");

       g_AppID.ProtocolMajor = TWON_PROTOCOLMAJOR;
       g_AppID.ProtocolMinor = TWON_PROTOCOLMINOR;
       g_AppID.SupportedGroups = DG_CONTROL | DG_IMAGE;

       lstrcpy (g_AppID.Manufacturer, "IECISA");
       lstrcpy (g_AppID.ProductFamily, "Interfaz JNI para Twain");
       lstrcpy (g_AppID.ProductName, "JTwain");
   }

   return TRUE;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquire
// ===========================================================================

#define FN "acquire"
//#pragma argsused
JNIEXPORT jobjectArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquire (JNIEnv *env, jclass clazz,jboolean value)
{
   if (g_hLib == 0){
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   clazz = env->FindClass ("java/awt/Image");
   if (clazz == 0){
       throwJTE (env, "Cannot find java.awt.Image class (" FN ")");
       return 0;
   }

   if (!vectorCreate ()) {
       throwJTE (env, "Memoria insuficiente #1 (acquire)");
       return 0;
   }

   TW_USERINTERFACE ui;
   ui.ShowUI = value;
   ui.ModalUI = true;
   ui.hParent = 0;
   
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_USERINTERFACE,
                           MSG_ENABLEDS,
                           &ui);
   if (g_rc != TWRC_SUCCESS)
   {
       throwJTE (env, "No se puede habilitar el Data Source actualmente abierto (" FN ")");
       vectorDestroy ();
       return 0;
   }

   MSG msg;
   TW_EVENT event;
   TW_PENDINGXFERS pxfers;
   bool nonTWAINProblem = FALSE;
   while (GetMessage ((LPMSG) &msg, 0, 0, 0))
   {
      event.pEvent = (TW_MEMREF) &msg;
      event.TWMessage = MSG_NULL;
      g_rc = (*g_pDSM_Entry) (&g_AppID,
                              &g_SrcID,
                              DG_CONTROL,
                              DAT_EVENT,
                              MSG_PROCESSEVENT,
                              (TW_MEMREF) &event);
							  
	if (event.TWMessage == MSG_CLOSEDSREQ)
          break;
							  
      if (g_rc == TWRC_FAILURE)
      {
          // After switching from one data source to another data source, the
          // data source manager indicates failure due to an unknown cause and
          // the data source indicates success the first time that an image is
          // acquired. Because this intermittent problem does not prevent the
          // image from being acquired, it's ignored -- this problem does not
          // appear to be serious and apparently causes no other problems.
          // Once I am able to determine the cause of the problem, I'll change
          // this code accordingly.

          if (Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 0) == 1 &&
              Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 1) == 0)
              g_rc = TWRC_SUCCESS;
          else
          {
              throwJTE (env, "No se puede recuperar el siguiente evento (" FN ")");
              break;
          }
      }

      if (g_rc == TWRC_NOTDSEVENT)
      {             
          TranslateMessage ((LPMSG) &msg);
          DispatchMessage ((LPMSG) &msg);
          continue;
      }

      if (event.TWMessage == MSG_XFERREADY) {
          nextImage:

          TW_IMAGEINFO ii;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGEINFO,
                                  MSG_GET,
                                  (TW_MEMREF) &ii);
                                  
          if (g_rc == TWRC_FAILURE) {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              throwJTE (env, "No se puede recuperar la informacion de la imagen (" FN ")");
              break;
          }

          if (ii.Compression != TWCP_NONE || ii.BitsPerPixel != 1 &&
              ii.BitsPerPixel != 8 && ii.BitsPerPixel != 24)  {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              nonTWAINProblem = TRUE;
              throwJTE (env, "Image compressed or not 1-bit/8-bit/24-bit (" FN ")");
              //break;
          }

          TW_UINT32 handle;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGENATIVEXFER,
                                  MSG_GET,
                                  (TW_MEMREF) &handle);
          if (g_rc == TWRC_XFERDONE)
          {
              LPBITMAPINFOHEADER lpbmih;
              lpbmih = (LPBITMAPINFOHEADER) GlobalLock ((HANDLE) handle);
              jobject image = xferDIBtoImage (lpbmih, env);
              GlobalUnlock ((HANDLE) handle);
              GlobalFree ((HANDLE) handle);

              g_rc = (*g_pDSM_Entry) (&g_AppID,
                                      &g_SrcID,
                                      DG_CONTROL,
                                      DAT_PENDINGXFERS,
                                      MSG_ENDXFER,
                                      (TW_MEMREF) &pxfers);

              if (image != 0)
              {
                  if (!vectorAppend (image))
                  {
                      nonTWAINProblem = TRUE;
                      throwJTE (env, "Memoria insuficiente #2 (" FN ")");
                  }
                  else
                  {
                      if (pxfers.Count > 0)
                          goto nextImage;
                      else
                          break;
                  }
              }
              else
                  nonTWAINProblem = TRUE;
          }
          else
              throwJTE (env, "No se puede transferir la imagen (" FN ")");

          (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_PENDINGXFERS,
                           MSG_RESET,
                           (TW_MEMREF) &pxfers);
          break;
      }
   }

   (*g_pDSM_Entry) (&g_AppID,
                    &g_SrcID,
                    DG_CONTROL,
                    DAT_USERINTERFACE,
                    MSG_DISABLEDS,
                    &ui);

   if (g_rc == TWRC_SUCCESS && !nonTWAINProblem)
   {
       jobjectArray images = env->NewObjectArray (vectorSize (), clazz, 0);
       if (images == 0)
           throwJTE (env, "Memoria insuficiente #3 (" FN ")");
       else
           for (int i = 0; i < vectorSize (); i++)
                env->SetObjectArrayElement (images, i, vectorGet (i));

       vectorDestroy ();
       return images;
   }

   vectorDestroy ();
   return 0;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireNativeToFile
// ===========================================================================
#undef  FN
#define FN "acquireNativeToFile"
//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireNativeToFile 
		(JNIEnv *env, jclass clazz,jstring jfilespec, jboolean useCur, jstring pathFile,jboolean userUI)
{
   if (g_hLib == 0){
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   clazz = env->FindClass ("java/awt/Image");
   if (clazz == 0){
       throwJTE (env, "Cannot find java.awt.Image class (" FN ")");
       return 0;
   }

   TW_USERINTERFACE ui;
   ui.ShowUI = userUI;
   ui.ModalUI = true;
   ui.hParent = 0;
   
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_USERINTERFACE,
                           MSG_ENABLEDS,
                           &ui);
   if (g_rc != TWRC_SUCCESS)
   {
       throwJTE (env, "No se puede habilitar el Data Source actualmente abierto (" FN ")");
       return 0;
   }

   MSG msg;
   TW_EVENT event;
   TW_PENDINGXFERS pxfers;
   bool nonTWAINProblem = FALSE;
   int iCounter = 1;
   while (GetMessage ((LPMSG) &msg, 0, 0, 0))
   {
      event.pEvent = (TW_MEMREF) &msg;
      event.TWMessage = MSG_NULL;
      g_rc = (*g_pDSM_Entry) (&g_AppID,
                              &g_SrcID,
                              DG_CONTROL,
                              DAT_EVENT,
                              MSG_PROCESSEVENT,
                              (TW_MEMREF) &event);
							  
	  if (event.TWMessage == MSG_CLOSEDSREQ){	//258
		  //devolvemos -1 para poder detectar y manejar correctamente este caso.
          g_rc=0;  iCounter=-1;
		  break;
	  }

      if (g_rc == TWRC_FAILURE)
      {
          // After switching from one data source to another data source, the
          // data source manager indicates failure due to an unknown cause and
          // the data source indicates success the first time that an image is
          // acquired. Because this intermittent problem does not prevent the
          // image from being acquired, it's ignored -- this problem does not
          // appear to be serious and apparently causes no other problems.
          // Once I am able to determine the cause of the problem, I'll change
          // this code accordingly.

          if (Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 0) == 1 &&
              Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 1) == 0)
              g_rc = TWRC_SUCCESS;
          else
          {
              throwJTE (env, "No se puede recuperar el siguiente evento (" FN ")");
              break;
          }
      }

      if (g_rc == TWRC_NOTDSEVENT) {             
          TranslateMessage ((LPMSG) &msg);
          DispatchMessage ((LPMSG) &msg);
          continue;
      }

      if (event.TWMessage == MSG_XFERREADY) {   //257
          nextImage:

          TW_IMAGEINFO ii;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGEINFO,
                                  MSG_GET,
                                  (TW_MEMREF) &ii);
                                  
          if (g_rc == TWRC_FAILURE) {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              throwJTE (env, "No se puede recuperar la informacion de la imagen (" FN ")");
              break;
          }

          if (ii.Compression != TWCP_NONE || ii.BitsPerPixel != 1 &&
              ii.BitsPerPixel != 8 && ii.BitsPerPixel != 24)  {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              nonTWAINProblem = TRUE;
              throwJTE (env, "Image compressed or not 1-bit/8-bit/24-bit (" FN ")");
              //break;
          }

          TW_UINT32 handle;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGENATIVEXFER,
                                  MSG_GET,
                                  (TW_MEMREF) &handle);
          if (g_rc == TWRC_XFERDONE)
          {
              jboolean result=saveAsBmp((HANDLE)handle, env,jfilespec,useCur,pathFile,iCounter++);
              GlobalFree ((HANDLE) handle);

              g_rc = (*g_pDSM_Entry) (&g_AppID,
                                      &g_SrcID,
                                      DG_CONTROL,
                                      DAT_PENDINGXFERS,
                                      MSG_ENDXFER,
                                      (TW_MEMREF) &pxfers);

              if (result==JNI_TRUE){
                  if (pxfers.Count > 0)
                      goto nextImage;
                  else
                      break;
			  }
              else
                  nonTWAINProblem = TRUE;
          }
          else
              throwJTE (env, "No se puede transferir la imagen (" FN ")");

          (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_PENDINGXFERS,
                           MSG_RESET,
                           (TW_MEMREF) &pxfers);
          break;
      }
   }

   (*g_pDSM_Entry) (&g_AppID,
                    &g_SrcID,
                    DG_CONTROL,
                    DAT_USERINTERFACE,
                    MSG_DISABLEDS,
                    &ui);

   if (g_rc == TWRC_SUCCESS && !nonTWAINProblem){
       return iCounter;
   }
   return 0;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToBMP
// ===========================================================================
#undef  FN
#define FN "acquireToBMP"

//#pragma argsused
JNIEXPORT void JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToBMP
  (JNIEnv *env, jclass clazz, jstring jfilespec, jboolean useCur)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   jboolean isCopy;
   const char *cfilespec = env->GetStringUTFChars (jfilespec, &isCopy);
   if (cfilespec == 0)
   {
       throwJTE (env, "Memoria insuficiente #1 (" FN ")");
       return;
   }

   TW_USERINTERFACE ui;
   ui.ShowUI = TRUE;
   ui.ModalUI = TRUE;
   ui.hParent = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_USERINTERFACE,
                           MSG_ENABLEDS,
                           &ui);
   if (g_rc != TWRC_SUCCESS)
   {
       if (isCopy == JNI_TRUE)
           env->ReleaseStringUTFChars (jfilespec, cfilespec);

       throwJTE (env, "No se puede habilitar el Data Source actualmente abierto (" FN ")");
       return;
   }

   MSG msg;
   TW_EVENT event;
   TW_PENDINGXFERS pxfers;
   int iCounter = 1;
   while (GetMessage ((LPMSG) &msg, 0, 0, 0))
   {
      event.pEvent = (TW_MEMREF) &msg;
      event.TWMessage = MSG_NULL;
      g_rc = (*g_pDSM_Entry) (&g_AppID,
                              &g_SrcID,
                              DG_CONTROL,
                              DAT_EVENT,
                              MSG_PROCESSEVENT,
                              (TW_MEMREF) &event);
      if (g_rc == TWRC_FAILURE)
      {
          if (Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 0) == 1 &&
              Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 1) == 0)
              g_rc = TWRC_SUCCESS;
          else
          {
              throwJTE (env, "No se puede recuperar el siguiente evento (" FN ")");
              break;
          }
      }

      if (g_rc == TWRC_NOTDSEVENT)
      {             
          TranslateMessage ((LPMSG) &msg);
          DispatchMessage ((LPMSG) &msg);
          continue;
      }

      if (event.TWMessage == MSG_XFERREADY)
      {
          nextImage:

          TW_SETUPFILEXFER sfxfer;

          sfxfer.Format = TWFF_JFIF;
          if (useCur == JNI_TRUE)
          {
              char buffer [MAX_PATH];
              GetCurrentDirectory (MAX_PATH, buffer);
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s\\%s%d.jpeg", "C:\\", cfilespec,
                        iCounter++);						
          }
          else
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s%d.jpeg", cfilespec, iCounter++);			  		
          sfxfer.VRefNum = (TW_INT16)TWON_DONTCARE16;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_CONTROL,
                                  DAT_SETUPFILEXFER,
                                  MSG_SET,
                                  (TW_MEMREF) &sfxfer);
          if (g_rc == TWRC_FAILURE)
          {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              throwJTE (env, "No se puede configurar la transferencia del fichero (" FN ")");
              break;
          }

          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGEFILEXFER,
                                  MSG_GET,
                                  0);
          if (g_rc == TWRC_XFERDONE)
          {
              g_rc = (*g_pDSM_Entry) (&g_AppID,
                                      &g_SrcID,
                                      DG_CONTROL,
                                      DAT_PENDINGXFERS,
                                      MSG_ENDXFER,
                                      (TW_MEMREF) &pxfers);
              if (pxfers.Count > 0)
                  goto nextImage;
              else
                  break;
          }
          else
              throwJTE (env, "No se puede transferir la imagen (" FN ")");

          (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_PENDINGXFERS,
                           MSG_RESET,
                           (TW_MEMREF) &pxfers);
          break;
      }
   }

   (*g_pDSM_Entry) (&g_AppID,
                    &g_SrcID,
                    DG_CONTROL,
                    DAT_USERINTERFACE,
                    MSG_DISABLEDS,
                    &ui);

   if (isCopy == JNI_TRUE)
       env->ReleaseStringUTFChars (jfilespec, cfilespec);
}


// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToJPEG
// ===========================================================================
#undef  FN
#define FN "acquireToJPEG"

//#pragma argsused
JNIEXPORT jint JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToJPEG
  (JNIEnv *env, jclass clazz, jstring jfilespec, jboolean useCur, jstring pathFile,jboolean userUI)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   jboolean isCopy;
   const char *cfilespec = env->GetStringUTFChars (jfilespec, &isCopy);
   const char *pathFileC = env->GetStringUTFChars (pathFile, &isCopy);
   if (cfilespec == 0)
   {
       throwJTE (env, "Memoria insuficiente #1 (" FN ")");
       return 0;
   }

   TW_USERINTERFACE ui;
   ui.ShowUI = userUI;
   ui.ModalUI = TRUE;
   ui.hParent = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_USERINTERFACE,
                           MSG_ENABLEDS,
                           &ui);
   if (g_rc != TWRC_SUCCESS)
   {
       if (isCopy == JNI_TRUE){
           env->ReleaseStringUTFChars (jfilespec, cfilespec);
		     env->ReleaseStringUTFChars (pathFile, pathFileC);
		}

       throwJTE (env, "No se puede habilitar el Data Source actualmente abierto (" FN ")");
       return 0;
   }

   MSG msg;
   TW_EVENT event;
   TW_PENDINGXFERS pxfers;
   int iCounter = 1;
   while (GetMessage ((LPMSG) &msg, 0, 0, 0))
   {
      event.pEvent = (TW_MEMREF) &msg;
      event.TWMessage = MSG_NULL;
      g_rc = (*g_pDSM_Entry) (&g_AppID,
                              &g_SrcID,
                              DG_CONTROL,
                              DAT_EVENT,
                              MSG_PROCESSEVENT,
                              (TW_MEMREF) &event);
	  if (event.TWMessage == MSG_CLOSEDSREQ)
          break;
      if (g_rc == TWRC_FAILURE)
      {
          if (Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 0) == 1 &&
              Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 1) == 0)
              g_rc = TWRC_SUCCESS;
          else
          {
              throwJTE (env, "No se puede recuperar el siguiente evento (" FN ")");
              break;
          }
      }

      if (g_rc == TWRC_NOTDSEVENT)
      {             
          TranslateMessage ((LPMSG) &msg);
          DispatchMessage ((LPMSG) &msg);
          continue;
      }

      if (event.TWMessage == MSG_XFERREADY)
      {
          nextImage:

          TW_SETUPFILEXFER sfxfer;

          sfxfer.Format = TWFF_JFIF;
          if (useCur == JNI_TRUE)
          {
              char buffer [MAX_PATH];
              GetCurrentDirectory (MAX_PATH, buffer);
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s\\%s%d.jpeg", pathFileC, cfilespec,
                        iCounter++);						
          }
          else
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s%d.jpeg", cfilespec, iCounter++);			  		
          sfxfer.VRefNum = (TW_INT16)TWON_DONTCARE16;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_CONTROL,
                                  DAT_SETUPFILEXFER,
                                  MSG_SET,
                                  (TW_MEMREF) &sfxfer);
          if (g_rc == TWRC_FAILURE)
          {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              throwJTE (env, "No se puede configurar la transferencia del fichero (" FN ")");
              break;
          }

          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGEFILEXFER,
                                  MSG_GET,
                                  0);
          if (g_rc == TWRC_XFERDONE)
          {
              g_rc = (*g_pDSM_Entry) (&g_AppID,
                                      &g_SrcID,
                                      DG_CONTROL,
                                      DAT_PENDINGXFERS,
                                      MSG_ENDXFER,
                                      (TW_MEMREF) &pxfers);
              if (pxfers.Count > 0)
                  goto nextImage;
              else
                  break;
          }
          else
              throwJTE (env, "No se puede transferir la imagen (" FN ")");

          (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_PENDINGXFERS,
                           MSG_RESET,
                           (TW_MEMREF) &pxfers);
          break;
      }
   }

   (*g_pDSM_Entry) (&g_AppID,
                    &g_SrcID,
                    DG_CONTROL,
                    DAT_USERINTERFACE,
                    MSG_DISABLEDS,
                    &ui);

   if (isCopy == JNI_TRUE)
       env->ReleaseStringUTFChars (jfilespec, cfilespec);
	   
	return iCounter;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToFile
// ===========================================================================
#undef  FN
#define FN "acquireToFile"

//#pragma argsused
JNIEXPORT jint JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_acquireToFile
  (JNIEnv *env, jclass clazz, jstring jfilespec, jboolean useCur, jstring pathFile,jboolean userUI)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   jboolean isCopy;
   const char *cfilespec = env->GetStringUTFChars (jfilespec, &isCopy);
   const char *pathFileC = env->GetStringUTFChars (pathFile, &isCopy);
   if (cfilespec == 0)
   {
       throwJTE (env, "Memoria insuficiente #1 (" FN ")");
       return 0;
   }

   TW_USERINTERFACE ui;
   ui.ShowUI = userUI;
   ui.ModalUI = TRUE;
   ui.hParent = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_USERINTERFACE,
                           MSG_ENABLEDS,
                           &ui);
   if (g_rc != TWRC_SUCCESS)
   {
       if (isCopy == JNI_TRUE){
           env->ReleaseStringUTFChars (jfilespec, cfilespec);
		   env->ReleaseStringUTFChars (pathFile, pathFileC);
		}

       throwJTE (env, "No se puede habilitar el Data Source actualmente abierto (" FN ")");
       return 0;
   }

   MSG msg;
   TW_EVENT event;
   TW_PENDINGXFERS pxfers;
   int iCounter = 1;
   while (GetMessage ((LPMSG) &msg, 0, 0, 0))
   {
      event.pEvent = (TW_MEMREF) &msg;
      event.TWMessage = MSG_NULL;
      g_rc = (*g_pDSM_Entry) (&g_AppID,
                              &g_SrcID,
                              DG_CONTROL,
                              DAT_EVENT,
                              MSG_PROCESSEVENT,
                              (TW_MEMREF) &event);
	  if (event.TWMessage == MSG_CLOSEDSREQ)
          break;
      if (g_rc == TWRC_FAILURE)
      {
          if (Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 0) == 1 &&
              Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (env, clazz, 1) == 0)
              g_rc = TWRC_SUCCESS;
          else
          {
              throwJTE (env, "No se puede recuperar el siguiente evento (" FN ")");
              break;
          }
      }

      if (g_rc == TWRC_NOTDSEVENT)
      {             
          TranslateMessage ((LPMSG) &msg);
          DispatchMessage ((LPMSG) &msg);
          continue;
      }

      if (event.TWMessage == MSG_XFERREADY)
      {
          nextImage:

          TW_SETUPFILEXFER sfxfer;

          sfxfer.Format = TWFF_BMP;
          if (useCur == JNI_TRUE)
          {
              char buffer [MAX_PATH];
              GetCurrentDirectory (MAX_PATH, buffer);
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s\\%s%d.bmp", pathFileC, cfilespec,
                        iCounter++);						
          }
          else
              // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
              wsprintf (sfxfer.FileName, "%s%d.bmp", cfilespec, iCounter++);			  		
          sfxfer.VRefNum = (TW_INT16)TWON_DONTCARE16;
          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_CONTROL,
                                  DAT_SETUPFILEXFER,
                                  MSG_SET,
                                  (TW_MEMREF) &sfxfer);
          if (g_rc == TWRC_FAILURE)
          {
              (*g_pDSM_Entry) (&g_AppID,
                               &g_SrcID,
                               DG_CONTROL,
                               DAT_PENDINGXFERS,
                               MSG_RESET,
                               (TW_MEMREF) &pxfers);

              throwJTE (env, "No se puede configurar la transferencia del fichero (" FN ")");
              break;
          }

          g_rc = (*g_pDSM_Entry) (&g_AppID,
                                  &g_SrcID,
                                  DG_IMAGE,
                                  DAT_IMAGEFILEXFER,
                                  MSG_GET,
                                  0);
          if (g_rc == TWRC_XFERDONE)
          {
              g_rc = (*g_pDSM_Entry) (&g_AppID,
                                      &g_SrcID,
                                      DG_CONTROL,
                                      DAT_PENDINGXFERS,
                                      MSG_ENDXFER,
                                      (TW_MEMREF) &pxfers);
              if (pxfers.Count > 0)
                  goto nextImage;
              else
                  break;
          }
          else
              throwJTE (env, "No se puede transferir la imagen (" FN ")");

          (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_PENDINGXFERS,
                           MSG_RESET,
                           (TW_MEMREF) &pxfers);
          break;
      }
   }

   (*g_pDSM_Entry) (&g_AppID,
                    &g_SrcID,
                    DG_CONTROL,
                    DAT_USERINTERFACE,
                    MSG_DISABLEDS,
                    &ui);

   if (isCopy == JNI_TRUE)
       env->ReleaseStringUTFChars (jfilespec, cfilespec);
	   
	return iCounter;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_closeDS
// ===========================================================================
#undef  FN
#define FN "closeDS"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_closeDS (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_IDENTITY,
                           MSG_CLOSEDS,
                           (TW_MEMREF) &g_SrcID);
   if (g_rc == TWRC_FAILURE)
       throwJTE (env, "No se puede cerrar el Data Source (" FN ")");
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_closeDSM
// ===========================================================================
#undef  FN
#define FN "closeDSM"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_closeDSM (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;                                                              
   }

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_PARENT,
                           MSG_CLOSEDSM,
                           (TW_MEMREF) &g_hwnd);
   if (g_rc == TWRC_FAILURE)
       throwJTE (env, "No se puede cerrar el Data Source manager (" FN ")");
   else
   {
       DestroyWindow (g_hwnd);
       unloadTWAIN ();
   }
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC
// ===========================================================================
#undef  FN
#define FN "getCC"

//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getCC (JNIEnv *env, jclass clazz,
                                           jint dest)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_STATUS status;

   // Get the condition code associated with either the data source manager
   // (dest contains 0) or the data source (dest contains any nonzero value).

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           (dest == 0) ? 0 : &g_SrcID,
                           DG_CONTROL,
                           DAT_STATUS,
                           MSG_GET,
                           (TW_MEMREF) &status);
   if (g_rc == TWRC_FAILURE)
   {
       throwJTE (env, "No se puede leer el codigo de condicion (" FN ")");
       return 0;
   }

   return status.ConditionCode;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDefaultDS
// ===========================================================================
#undef FN
#define FN "getDefaultDS"

//#pragma argsused
JNIEXPORT jstring JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDefaultDS (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_IDENTITY,
                           MSG_GETDEFAULT,
                           &g_SrcID);
   if (g_rc == TWRC_FAILURE)
   {
       throwJTE (env, "No se puede recuperar el nombre del Data Source por defecto (" FN ")");
       return 0;
   }

   return env->NewStringUTF (g_SrcID.ProductName);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getFirstDS
// ===========================================================================
#undef FN
#define FN "getFirstDS"

//#pragma argsused
JNIEXPORT jstring JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getFirstDS (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_IDENTITY,
                           MSG_GETFIRST,
                           (TW_MEMREF) &g_SrcID);
   if (g_rc == TWRC_FAILURE)
   {
       throwJTE (env, "No se puede recuperar el nombre del primer Data Source (" FN ")");
       return 0;
   }

   return env->NewStringUTF (g_SrcID.ProductName);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getNextDS
// ===========================================================================

#undef FN
#define FN "getNextDS"

//#pragma argsused
JNIEXPORT jstring JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getNextDS (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_IDENTITY,
                           MSG_GETNEXT,
                           (TW_MEMREF) &g_SrcID);
   if (g_rc == TWRC_FAILURE)
   {
       throwJTE (env, "No se puede recuperar el nombre del siguiente Data Source (" FN ")");
       return 0;
   }

   return env->NewStringUTF (g_rc == TWRC_SUCCESS
                             ? g_SrcID.ProductName : "");
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPhysicalHeight
// ===========================================================================

#undef FN
#define FN "getPhysicalHeight"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPhysicalHeight (JNIEnv *env,
                                                       jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_PHYSICALHEIGHT;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_PHYSICALHEIGHT");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float)fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPhysicalWidth
// ===========================================================================

#undef FN
#define FN "getPhysicalWidth"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPhysicalWidth (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_PHYSICALWIDTH;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_PHYSICALWIDTH");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPixelType
// ===========================================================================

#undef FN
#define FN "getPixelType"

//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPixelType (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_PIXELTYPE;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_PIXELTYPE");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   jint item = (jint) pTWOneValue->Item;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPixelTypeList
// ===========================================================================

#undef FN
#define FN "getPixelTypeList"

//#pragma argsused
JNIEXPORT jintArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getPixelTypeList (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_PIXELTYPE;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_PIXELTYPE");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
   jintArray intArray = env->NewIntArray (pTWEnum->NumItems);
   if (intArray != 0)
   {
       for (int i = 0; i < (int) pTWEnum->NumItems; i++)
       {
            jint x = (jint) ((TW_UINT16 *) pTWEnum->ItemList) [i];
            env->SetIntArrayRegion (intArray, i, 1, &x);
       }
   }
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   if (intArray == 0)
       throwJTE (env, "Memoria Insuficiente (" FN ")");

   return intArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getRC
// ===========================================================================

//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getRC (JNIEnv *env, jclass clazz)
{
   return g_rc;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnits
// ===========================================================================

#undef FN
#define FN "getUnits"

//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnits (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_UNITS;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_UNITS");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   jint item = (jint) pTWOneValue->Item;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnitsDefault
// ===========================================================================

#undef FN
#define FN "getUnitsDefault"

//#pragma argsused
JNIEXPORT jint JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnitsDefault (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_UNITS;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETDEFAULT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_UNITS");
       }else{
           throwJTE (env, "No se puede recuperar el valor por defecto (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   jint item = (jint) pTWOneValue->Item;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnitsList
// ===========================================================================

#undef FN
#define FN "getUnitsList"

//#pragma argsused
JNIEXPORT jintArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getUnitsList (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_UNITS;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_UNITS");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
   jintArray intArray = env->NewIntArray (pTWEnum->NumItems);
   if (intArray != 0)
   {
       for (int i = 0; i < (int) pTWEnum->NumItems; i++)
       {
            jint x = (jint) ((TW_UINT16 *) pTWEnum->ItemList) [i];
            env->SetIntArrayRegion (intArray, i, 1, &x);
       }
   }
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   if (intArray == 0)
       throwJTE (env, "Memoria Insufficiente (" FN ")");

   return intArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXNativeResolution
// ===========================================================================

#undef FN
#define FN "getXNativeResolution"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXNativeResolution (JNIEnv *env,
                                                          jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XNATIVERESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XNATIVERESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXNativeResolutionList
// ===========================================================================

#undef FN
#define FN "getXNativeResolutionList"

//#pragma argsused
JNIEXPORT jfloatArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXNativeResolutionList (JNIEnv *env,
                                                              jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XNATIVERESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XNATIVERESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   jfloatArray floatArray;
   if (cap.ConType == TWON_ENUMERATION)
   {
       TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (pTWEnum->NumItems);
       if (floatArray != 0)
       {
           for (int i = 0; i < (int) pTWEnum->NumItems; i++)
           {
                TW_FIX32 fix32 = ((TW_FIX32 *) pTWEnum->ItemList) [i];
                jfloat item = (jfloat) fix32.Whole+(float) fix32.Frac/(float)65536.0;
                env->SetFloatArrayRegion (floatArray, i, 1, &item);
           }
       }
       else
           throwJTE (env, "Memoria insuficiente #1 (" FN ")");
   }
   else
   {
       TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (1);
       if (floatArray != 0)
       {
           TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
           jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
           env->SetFloatArrayRegion (floatArray, 0, 1, &item);
       }
       else
           throwJTE (env, "Memoria insuficiente #2 (" FN ")");
   }

   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return floatArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXResolution
// ===========================================================================

#undef FN
#define FN "getXResolution"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXResolution (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XRESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XRESOLUTION");
	   }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXResolutionList
// ===========================================================================

#undef FN
#define FN "getXResolutionList"

//#pragma argsused
JNIEXPORT jfloatArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXResolutionList (JNIEnv *env,
                                                        jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XRESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XRESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   jfloatArray floatArray;
   if (cap.ConType == TWON_ENUMERATION)
   {
       TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (pTWEnum->NumItems);
       if (floatArray != 0)
       {
           for (int i = 0; i < (int) pTWEnum->NumItems; i++)
           {
                TW_FIX32 fix32 = ((TW_FIX32 *) pTWEnum->ItemList) [i];
                jfloat item = (jfloat) fix32.Whole+(float) fix32.Frac/(float)65536.0;
                env->SetFloatArrayRegion (floatArray, i, 1, &item);
           }
       }
       else
           throwJTE (env, "Memoria insuficiente #1 (" FN ")");
   }
   else
   if (cap.ConType == TWON_ONEVALUE)
   {
       TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (1);
       if (floatArray != 0)
       {
           TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
           jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
           env->SetFloatArrayRegion (floatArray, 0, 1, &item);
       }
       else
           throwJTE (env, "Memoria insuficiente #2 (" FN ")");
   }
   else
       throwJTE (env, "TWON_ENUMERATION o TWON_ONEVALUE esperado (" FN ")");

   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return floatArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXScaling
// ===========================================================================

#undef FN
#define FN "getXScaling"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXScaling (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XSCALING;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XSCALING");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXScalingDefault
// ===========================================================================

#undef FN
#define FN "getXScalingDefault"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getXScalingDefault (JNIEnv *env,
                                                        jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XSCALING;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETDEFAULT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_XSCALING");
       }else{
           throwJTE (env, "No se puede recuperar el valor por defecto (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYNativeResolution
// ===========================================================================

#undef FN
#define FN "getYNativeResolution"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYNativeResolution (JNIEnv *env,
                                                          jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YNATIVERESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YNATIVERESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYNativeResolutionList
// ===========================================================================

#undef FN
#define FN "getYNativeResolutionList"

//#pragma argsused
JNIEXPORT jfloatArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYNativeResolutionList (JNIEnv *env,
                                                              jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YNATIVERESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YNATIVERESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   jfloatArray floatArray;
   if (cap.ConType == TWON_ENUMERATION)
   {
       TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (pTWEnum->NumItems);
       if (floatArray != 0)
       {
           for (int i = 0; i < (int) pTWEnum->NumItems; i++)
           {
                TW_FIX32 fix32 = ((TW_FIX32 *) pTWEnum->ItemList) [i];
                jfloat item = (jfloat) fix32.Whole+(float) fix32.Frac/(float)65536.0;
                env->SetFloatArrayRegion (floatArray, i, 1, &item);
           }
       }
       else
           throwJTE (env, "Memoria insuficiente #1 (" FN ")");
   }
   else
   {
       TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (1);
       if (floatArray != 0)
       {
           TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
           jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
           env->SetFloatArrayRegion (floatArray, 0, 1, &item);
       }
       else
           throwJTE (env, "Memoria insuficiente #2 (" FN ")");
   }

   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return floatArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYResolution
// ===========================================================================

#undef FN
#define FN "getYResolution"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYResolution (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YRESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YRESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYResolutionList
// ===========================================================================

#undef FN
#define FN "getYResolutionList"

//#pragma argsused
JNIEXPORT jfloatArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYResolutionList (JNIEnv *env,
                                                        jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YRESOLUTION;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YRESOLUTION");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   jfloatArray floatArray;
   if (cap.ConType == TWON_ENUMERATION)
   {
       TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (pTWEnum->NumItems);
       if (floatArray != 0)
       {
           for (int i = 0; i < (int) pTWEnum->NumItems; i++)
           {
                TW_FIX32 fix32 = ((TW_FIX32 *) pTWEnum->ItemList) [i];
                jfloat item = (jfloat) fix32.Whole+(float) fix32.Frac/(float)65536.0;
                env->SetFloatArrayRegion (floatArray, i, 1, &item);
           }
       }
       else
           throwJTE (env, "Memoria insuficiente #1 (" FN ")");
   }
   else
   if (cap.ConType == TWON_ONEVALUE)
   {
       TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
       floatArray = env->NewFloatArray (1);
       if (floatArray != 0)
       {
           TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
           jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
           env->SetFloatArrayRegion (floatArray, 0, 1, &item);
       }
       else
           throwJTE (env, "Memoria insuficiente #2 (" FN ")");
   }
   else
       throwJTE (env, "TWON_ENUMERATION o TWON_ONEVALUE esperado (" FN ")");

   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return floatArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYScaling
// ===========================================================================

#undef FN
#define FN "getYScaling"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYScaling (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YSCALING;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YSCALING");
       }else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYScalingDefault
// ===========================================================================

#undef FN
#define FN "getYScalingDefault"

//#pragma argsused
JNIEXPORT jfloat JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getYScalingDefault (JNIEnv *env,
                                                        jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YSCALING;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETDEFAULT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_YSCALING");
       }else{
           throwJTE (env, "No se puede recuperar el valor por defecto (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOV = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 *fix32 = (TW_FIX32 *) &pTWOV->Item;
   jfloat item = (jfloat) fix32->Whole+(float) fix32->Frac/(float)65536.0;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_openDS
// ===========================================================================

#undef FN
#define FN "openDS"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_openDS (JNIEnv *env, jclass clazz,
                                            jstring jsrcName)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   jboolean isCopy;
   const char *csrcName = env->GetStringUTFChars (jsrcName, &isCopy);
   if (csrcName == 0)
   {
       throwJTE (env, "Memoria Insuficiente (" FN ")");
       return;
   }
   ZeroMemory (&g_SrcID, sizeof (g_SrcID));
   lstrcpy (g_SrcID.ProductName, csrcName);
   if (isCopy == JNI_TRUE)
       env->ReleaseStringUTFChars (jsrcName, csrcName);

   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_IDENTITY,
                           MSG_OPENDS,
                           (TW_MEMREF) &g_SrcID);
   if (g_rc == TWRC_FAILURE){
       throwJTE (env, "No se puede abrir el datasource (" FN ")");
	}
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_openDSM
// ===========================================================================

#undef FN
#define FN "openDSM"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_openDSM (JNIEnv *env, jclass clazz)
{
   if (g_hLib != 0)
   {
       throwJTE (env, "Data source manager ya abierto (" FN ")");
       return;
   }

   if (!loadTWAIN ())
   {
       throwJTE (env, "No se puede cargar TWAIN (" FN ")");
       return;
   }

   g_hwnd = CreateWindow ("STATIC",
                          "",
                          WS_POPUPWINDOW,
                          CW_USEDEFAULT,
                          CW_USEDEFAULT,
                          CW_USEDEFAULT,
                          CW_USEDEFAULT,
                          HWND_DESKTOP,
                          0,
                          g_hinstDLL,
                          0);
   if (g_hwnd == 0)
   {
       throwJTE (env, "No se puede crear una ventana privada (" FN ")");
       return;
   }
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           0,
                           DG_CONTROL,
                           DAT_PARENT,
                           MSG_OPENDSM,
                           (TW_MEMREF) &g_hwnd);
   if (g_rc == TWRC_FAILURE)
   {
       DestroyWindow (g_hwnd);
       throwJTE (env, "No se puede abrir el Data Source manager (" FN ")");
   }
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setPixelType
// ===========================================================================

#undef FN
#define FN "setPixelType"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setPixelType (JNIEnv *env, jclass clazz,
                                                  jint pixType)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_PIXELTYPE;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT16;
   pTWOneValue->Item = pixType;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_PIXELTYPE");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setUnits
// ===========================================================================

#undef FN
#define FN "setUnits"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setUnits (JNIEnv *env, jclass clazz,
                                              jint units)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_UNITS;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT16;
   pTWOneValue->Item = units;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_UNITS");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setXResolution
// ===========================================================================

#undef FN
#define FN "setXResolution"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setXResolution (JNIEnv *env, jclass clazz,
                                                    jfloat res)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XRESOLUTION;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 fix32;
   TW_INT32 value = (TW_INT32) (res*65536.0+0.5);
   fix32.Whole = value >> 16;
   fix32.Frac = value & 0x0000ffffL;
   pTWOneValue->ItemType = TWTY_FIX32;
   pTWOneValue->Item = *(TW_UINT32 *) &fix32;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_XRESOLUTION");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setXScaling
// ===========================================================================

#undef FN
#define FN "setXScaling"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setXScaling (JNIEnv *env, jclass clazz,
                                                 jfloat scale)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_XSCALING;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 fix32;
   TW_INT32 value = (TW_INT32) (scale*65536.0+0.5);
   fix32.Whole = value >> 16;
   fix32.Frac = value & 0x0000ffffL;
   pTWOneValue->ItemType = TWTY_FIX32;
   pTWOneValue->Item = *(TW_UINT32 *) &fix32;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_XSCALING");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setYResolution
// ===========================================================================

#undef FN
#define FN "setYResolution"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setYResolution (JNIEnv *env, jclass clazz,
                                                    jfloat res)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YRESOLUTION;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 fix32;
   TW_INT32 value = (TW_INT32) (res*65536.0+0.5);
   fix32.Whole = value >> 16;
   fix32.Frac = value & 0x0000ffffL;
   pTWOneValue->ItemType = TWTY_FIX32;
   pTWOneValue->Item = *(TW_UINT32 *) &fix32;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_YRESOLUTION");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setYScaling
// ===========================================================================

#undef FN
#define FN "setYScaling"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setYScaling (JNIEnv *env, jclass clazz,
                                                 jfloat scale)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_YSCALING;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   TW_FIX32 fix32;
   TW_INT32 value = (TW_INT32) (scale*65536.0+0.5);
   fix32.Whole = value >> 16;
   fix32.Frac = value & 0x0000ffffL;
   pTWOneValue->ItemType = TWTY_FIX32;
   pTWOneValue->Item = *(TW_UINT32 *) &fix32;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_YSCALING");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ================
// HELPER FUNCTIONS
// ================

// ===========================================================================
// loadTWAIN
// ===========================================================================

static bool loadTWAIN (void)
{
   //bool is32bit=(sizeof(void*)==4);
#if _WIN64 || _WIN32
#if _WIN64
#pragma message ("¡¡¡ GENERANDO PARA 64 bits !!!")
   g_hLib = LoadLibrary ("TWAINDSM.DLL");
#else
   g_hLib = LoadLibrary ("TWAIN_32.DLL");
#endif
#endif

   if (g_hLib == 0)
       return FALSE;

   g_pDSM_Entry = (DSMENTRYPROC) GetProcAddress (g_hLib, "DSM_Entry");
   if (g_pDSM_Entry == 0)
   {
       unloadTWAIN ();
       return FALSE;
   }

   return TRUE;
}

// ===========================================================================
// throwJTE
// ===========================================================================

static void throwJTE (JNIEnv *env, const char *msg)
{
   jclass clazz = env->FindClass ("es/ieci/tecdoc/fwktd/applets/scan/jtwain/JTwainException");
   if (clazz == 0)
   {
       //MessageBox (g_hwnd, msg, "JTWAIN", MB_OK);
       env->FatalError ("Could not find es.ieci.tecdoc.fwktd.applets.scan.jtwain.JTwainException");
   }
   else 
       env->ThrowNew (clazz, msg);
}

// ===========================================================================
// throwUC
// ===========================================================================

static void throwUC (JNIEnv *env, const char *msg)
{
   jclass clazz =
      env->FindClass ("es/ieci/tecdoc/fwktd/applets/scan/jtwain/UnsupportedCapabilityException");
   if (clazz == 0)
   {
       //MessageBox (0, "Unsupported Capability", "JTWAIN", MB_OK);
       env->FatalError ("Could not find "
                        "es.ieci.tecdoc.fwktd.applets.scan.jtwain.UnsupportedCapabilityException");
   }
   else 
       env->ThrowNew (clazz, msg);
}

// ===========================================================================
// unloadTWAIN
// ===========================================================================

static void unloadTWAIN (void)
{
   FreeLibrary (g_hLib);
   g_hLib = 0;
}

// ===========================================================================
// vectorAppend
// ===========================================================================

static bool vectorAppend (jobject o)
{
   if (g_jobjectsCount <= g_jobjectsLimit)
       g_jobjects [g_jobjectsCount++] = o;
   else
   {
       g_jobjectsLimit += g_jobjectsLimit; // Double the limit.
       jobject *jobjects;
       jobjects = (jobject *) malloc (g_jobjectsLimit*sizeof (jobject *));
       if (jobjects == 0)
           return FALSE;
       for (int i = 0; i < g_jobjectsCount; i++)
            jobjects [i] = g_jobjects [i];
       free (g_jobjects);
       g_jobjects = jobjects;
   }
   return TRUE;
}

// ===========================================================================
// vectorCreate
// ===========================================================================

static bool vectorCreate (void)
{
   g_jobjects = (jobject *) malloc (100*sizeof (jobject *));
   g_jobjectsCount = 0;
   g_jobjectsLimit = 99;
   return (g_jobjects != 0) ? TRUE : FALSE;
}

// ===========================================================================
// vectorDestroy
// ===========================================================================

static void vectorDestroy (void)
{
   free (g_jobjects);
}

// ===========================================================================
// vectorGet
// ===========================================================================

static jobject vectorGet (int index)
{
   return g_jobjects [index];
}

// ===========================================================================
// vectorSize
// ===========================================================================

static int vectorSize (void)
{
   return g_jobjectsCount;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setDuplexEnabled
// ===========================================================================

#undef FN
#define FN "setDuplexEnabled"

//#pragma argsused
JNIEXPORT void JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setDuplexEnabled
  (JNIEnv *env, jclass clazz, jboolean value)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = CAP_DUPLEXENABLED;
 
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_BOOL;
   pTWOneValue->Item = value;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "CAP_DUPLEXENABLED");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setADFEnabled
// ===========================================================================

#undef FN
#define FN "setADFEnabled"

//#pragma argsused
JNIEXPORT void JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setADFEnabled
  (JNIEnv *env, jclass clazz, jboolean value)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   //CAP_AUTOFEED
   cap.Cap = CAP_FEEDERENABLED;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_BOOL;
   pTWOneValue->Item = value;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "CAP_FEEDERENABLED");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDuplexList
// ===========================================================================

#undef FN
#define FN "getDuplexList"

//#pragma argsused
JNIEXPORT jintArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDuplexList (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = CAP_DUPLEX;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "CAP_DUPLEX");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
   jintArray intArray = env->NewIntArray (pTWEnum->NumItems);
   if (intArray != 0)
   {
       for (int i = 0; i < (int) pTWEnum->NumItems; i++)
       {
            jint x = (jint) ((TW_UINT16 *) pTWEnum->ItemList) [i];
            env->SetIntArrayRegion (intArray, i, 1, &x);
       }
   }
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   if (intArray == 0)
       throwJTE (env, "Memoria insuficiente (" FN ")");

   return intArray;
}


// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setAutoDiscardBlankPages
// ===========================================================================

#undef FN		
#define FN "setAutoDiscardBlankPages"
		
//#pragma argsused
JNIEXPORT void JNICALL Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setAutoDiscardBlankPages
  (JNIEnv *env, jclass clazz, jint value)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_AUTODISCARDBLANKPAGES;
 
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT32;
   pTWOneValue->Item = value;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED))
           throwUC (env, "ICAP_AUTODISCARDBLANKPAGES");
       else
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getSupportedSizes
// ===========================================================================

#undef FN
#define FN "getSupportedSizes"

//#pragma argsused
JNIEXPORT jintArray JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getSupportedSizes (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_SUPPORTEDSIZES;
   cap.ConType = TWON_DONTCARE16;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GET,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES");
       }else{
           throwJTE (env, "No se puede recuperar los valores soportados (" FN ")");
		}
       return 0;
   }

   TW_ENUMERATION *pTWEnum = (TW_ENUMERATION *) GlobalLock (cap.hContainer);
   jintArray intArray = env->NewIntArray (pTWEnum->NumItems);
   if (intArray != 0)
   {
       for (int i = 0; i < (int) pTWEnum->NumItems; i++)
       {
            jint x = (jint) ((TW_UINT16 *) pTWEnum->ItemList) [i];
            env->SetIntArrayRegion (intArray, i, 1, &x);
       }
   }
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   if (intArray == 0)
       throwJTE (env, "Memoria insuficiente (" FN ")");

   return intArray;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setSupportedSizes
// ===========================================================================

#undef FN
#define FN "setSupportedSizes"

//#pragma argsused
JNIEXPORT void JNICALL  
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setSupportedSizes (JNIEnv *env, jclass clazz,
                                              jint value)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_SUPPORTEDSIZES;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT16;
   pTWOneValue->Item = value;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}


// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDuplexSupport
// ===========================================================================

#undef FN
#define FN "getDuplexSupport"

//#pragma argsused
JNIEXPORT jboolean JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getDuplexSupport (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = CAP_DUPLEX;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "CAP_DUPLEX");
		}
       else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   jboolean item = (jboolean) pTWOneValue->Item;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getADFSupport
// ===========================================================================

#undef FN
#define FN "getADFSupport"

//#pragma argsused
JNIEXPORT jboolean JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_getADFSupport (JNIEnv *env, jclass clazz)
{
   if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto(" FN ")");
       return 0;
   }

   TW_CAPABILITY cap;

   cap.Cap = CAP_FEEDERENABLED;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = 0;
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_GETCURRENT,
                           (TW_MEMREF) &cap);
   if (g_rc == TWRC_FAILURE)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "CAP_FEEDERENABLED");
		}
       else{
           throwJTE (env, "No se puede recuperar el valor actual (" FN ")");
		}
       return 0;
   }

   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   jboolean item = (jboolean) pTWOneValue->Item;
   GlobalUnlock (cap.hContainer);
   GlobalFree (cap.hContainer);
   return item;
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setContrast
// ===========================================================================

#undef FN
#define FN "setContrast"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setContrast (JNIEnv *env, jclass clazz,
                                                    jfloat res)
{
 if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_CONTRAST;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT32;
   pTWOneValue->Item = (TW_UINT32)res;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}

// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setBright
// ===========================================================================

#undef FN
#define FN "setBright"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setBright (JNIEnv *env, jclass clazz,
                                                    jfloat res)
{
 if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_BRIGHTNESS;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT32;
   pTWOneValue->Item = (TW_UINT32)res;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}


// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setJPGQuality
// ===========================================================================

#undef FN
#define FN "setJPGQuality"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setJPGQuality (JNIEnv *env, jclass clazz,
                                                    jint res)
{
 if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_JPEGQUALITY;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT16;
   pTWOneValue->Item = res;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}
// ===========================================================================
// Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setCompressJPEG
// ===========================================================================

#undef FN
#define FN "setCompressJPEG"

//#pragma argsused
JNIEXPORT void JNICALL
  Java_es_ieci_tecdoc_fwktd_applets_scan_jtwain_JTwain_setCompressJPEG (JNIEnv *env, jclass clazz)
{
 if (g_hLib == 0)
   {
       throwJTE (env, "Data source manager no abierto (" FN ")");
       return;
   }

   TW_CAPABILITY cap;

   cap.Cap = ICAP_COMPRESSION;
   cap.ConType = TWON_ONEVALUE;
   cap.hContainer = GlobalAlloc (GHND, sizeof (TW_ONEVALUE));
   if (cap.hContainer == 0)
   {
       throwJTE (env, "Memoria insuficiente (" FN ")");
       return;
   }
   TW_ONEVALUE *pTWOneValue = (TW_ONEVALUE *) GlobalLock (cap.hContainer);
   pTWOneValue->ItemType = TWTY_UINT16;
   pTWOneValue->Item = TWCP_JPEG;
   GlobalUnlock (cap.hContainer);
   g_rc = (*g_pDSM_Entry) (&g_AppID,
                           &g_SrcID,
                           DG_CONTROL,
                           DAT_CAPABILITY,
                           MSG_SET,
                           (TW_MEMREF) &cap);
   if (g_rc != TWRC_SUCCESS)
   {
       TW_STATUS status;

       int rc = (*g_pDSM_Entry) (&g_AppID,
                                 &g_SrcID,
                                 DG_CONTROL,
                                 DAT_STATUS,
                                 MSG_GET,
                                 (TW_MEMREF) &status);
       if (rc == TWRC_SUCCESS && (status.ConditionCode == TWCC_BADCAP ||
           status.ConditionCode == TWCC_CAPUNSUPPORTED)){
           throwUC (env, "ICAP_SUPPORTEDSIZES_JPEG");
       }else{
           throwJTE (env, "No se puede asignar la caracteristica (" FN ")");
		}
   }
   GlobalFree (cap.hContainer);
}


// ===========================================================================
// getErrorCode
// ===========================================================================

static int getErrorCode ()
{
   TW_STATUS status;
   int rc = (*g_pDSM_Entry) (&g_AppID,
                             &g_SrcID,
                             DG_CONTROL,
                             DAT_STATUS,
                             MSG_GET,
                             (TW_MEMREF) &status);
   if (rc == TWRC_SUCCESS){
		return status.ConditionCode;
   }else{
		return 0;
   }
}

// ===========================================================================
// newFile
// ===========================================================================
#undef FN
#define FN "newFile"
static jobject newFile (JNIEnv *env, const char * path)
{
   jstring filepath=env->NewStringUTF(path);
   jclass clazz = env->FindClass ("java/io/File");
   if (clazz == 0){
       throwJTE (env, "Cannot find java.io.File class (" FN ")");
       return 0;
   }

   jmethodID mid = env->GetMethodID (clazz, "<init>", "(Ljava/lang/String;)V");
   if (mid == 0){
       throwJTE (env, "Cannot find java.io.File (" FN ")");
       return 0;
   }

   jobject obj = env->NewObject (clazz, mid, (jstring) filepath);
   if (obj == 0){
       throwJTE (env, "Cannot create java.io.File object (" FN ")");
       return 0;
   }
   return obj;
}

// ===========================================================================
// xferDIBtoImage
// ===========================================================================

#undef FN
#define FN "xferDIBtoImage"

static jobject xferDIBtoImage (LPBITMAPINFOHEADER lpbmih, JNIEnv *env)
{
   int width = lpbmih->biWidth;
   int height = lpbmih->biHeight; // height < 0 if bitmap is top-down
   if (height < 0)
       height = -height;

   int *localPixelsArray = (int *) malloc (width*sizeof (int));
   if (localPixelsArray == 0)
   {
       throwJTE (env, "Memoria insuficiente para array Java de pixeles (" FN ")");
       return 0;
   }

   jintArray JavaPixelsArray = env->NewIntArray (width);
   if (JavaPixelsArray == 0)
   {
       free (localPixelsArray);
       throwJTE (env, "Memoria insuficiente para array Java de pixeles (" FN ")");
       return (jobject) 0;
   }

   // Build the equivalent of the following Java code fragment:

   // BufferedImage bi;
   // bi = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);

   jclass clazz = env->FindClass ("java/awt/image/BufferedImage");
   if (clazz == 0)
   {
       free (localPixelsArray);
       throwJTE (env, "Cannot find java.awt.image.BufferedImage class (" FN
                 ")");
       return 0;
   }

   jmethodID mid = env->GetMethodID (clazz, "<init>", "(III)V");
   if (mid == 0)
   {
       free (localPixelsArray);
       throwJTE (env, "Cannot find java.awt.image.BufferedImage (" FN ")");
       return 0;
   }

   jobject obj = env->NewObject (clazz, mid, (jint) width, (jint) height, 1);
   if (obj == 0)
   {
       free (localPixelsArray);
       throwJTE (env, "Cannot create java.awt.image.BufferedImage object (" FN
                 ")");

       return 0;
   }

   mid = env->GetMethodID (clazz, "setRGB", "(IIII[III)V");
   if (mid == 0)
   {
       free (localPixelsArray);
       throwJTE (env, "Cannot find java.awt.image.BufferedImage "
                 "setRGB(int,int,int,int,int[],int,int) (" FN ")");
       return 0;
   }

   int numColors;
   if (lpbmih->biClrUsed > 0)
       numColors = lpbmih->biClrUsed;
   else
       numColors = 1<<lpbmih->biBitCount;

   unsigned char *bitmap = (unsigned char *) lpbmih+
                           sizeof (BITMAPINFOHEADER)+
                           ((lpbmih->biBitCount != 24) ?
                           numColors*sizeof (RGBQUAD) : 0);

   int rowBytes = (((width*lpbmih->biBitCount)+31)/32)*4;

   for (int row = 0; row < height; row++)
   {
        for (int col = 0; col < width; col++)
        {
             int pixel;
             if (lpbmih->biBitCount == 1)
             {
                 int byte = bitmap [rowBytes*row+col/8];
                 int masks [] = { 128, 64, 32, 16, 8, 4, 2, 1 };
                 pixel = 0xff000000;
                 if ((byte&masks [col%8]) != 0)
                     pixel |= 0xffffff;
             }
             else
             if (lpbmih->biBitCount == 8)
             {
                 int *palette = (int *) lpbmih+sizeof (BITMAPINFOHEADER);
                 pixel = 0xff000000|palette [bitmap [rowBytes*row+col]];
             }
             else
             {
                 int index = rowBytes*row+col*3;
                 pixel = 0xff000000|(bitmap [index+2]<<16)|
                         (bitmap [index+1]<<8)|bitmap [index];
             }

             localPixelsArray [col] = pixel;
        }
        env->SetIntArrayRegion (JavaPixelsArray, 0, width,
                                (jint *) localPixelsArray);
        env->CallObjectMethod (obj, mid, 0, (jint) (height-row-1),
                               (jint) width, 1, JavaPixelsArray, 0,
                               (jint) width);
   }

   free (localPixelsArray);
   return obj;
}

// ===========================================================================
// saveAsBmpJNI   Puede desbordar la memoria
// ===========================================================================
static jboolean saveAsBmpJNI (LPBITMAPINFOHEADER lpbmih, JNIEnv *env,
						   jstring jfilespec,jboolean useCur,jstring pathFile,int iCounter){
	
	jobject image=xferDIBtoImage(lpbmih,env);
	jboolean result=JNI_FALSE;
    char fileName[256]; 

	jboolean isCopy;
    const char *cfilespec = env->GetStringUTFChars (jfilespec, &isCopy);
    const char *pathFileC = env->GetStringUTFChars (pathFile, &isCopy);
	if (useCur == JNI_TRUE) {
      char buffer [MAX_PATH];
      GetCurrentDirectory (MAX_PATH, buffer);
      // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
      sprintf_s (fileName, 256, "%s\\%s%d.bmp", pathFileC, cfilespec,iCounter);						
    }else
      // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
      sprintf_s (fileName, 256, "%s%d.bmp", cfilespec, iCounter++);

	//escribir a disco
	// ImageIO.write(img,"BMP",new File(path))
	jobject f=newFile(env,(const char *)fileName);
	if (isCopy == JNI_TRUE){
       env->ReleaseStringUTFChars (jfilespec, cfilespec);
	   env->ReleaseStringUTFChars (pathFile, pathFileC);
	}
	jclass clazz = env->FindClass ("javax/imageio/ImageIO");
	jmethodID mid = env->GetStaticMethodID(clazz, "write", "(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/File;)Z");
    if(mid !=0){
		result = env->CallStaticBooleanMethod(clazz, mid, image, env->NewStringUTF("BMP"),f);
		if(result != JNI_TRUE){
			throwJTE (env, "Cannot write image file with ImageIO (" FN ")");
		}
    }
	return result;
}

// ===========================================================================
// saveAsBmp
// ===========================================================================
static jboolean saveAsBmp (HANDLE hDIB,JNIEnv *env,jstring jfilespec,jboolean useCur,jstring pathFile,int iCounter){
	BITMAPFILEHEADER	hdr;
	LPBITMAPINFOHEADER lpbi;
    lpbi = (LPBITMAPINFOHEADER) GlobalLock (hDIB);
	HANDLE file;
    DWORD write = 0;

	bool result=false;
    char fileName[256]; 

		jboolean isCopy;
		const char *cfilespec = env->GetStringUTFChars (jfilespec, &isCopy);
		const char *pathFileC = env->GetStringUTFChars (pathFile, &isCopy);
		if (useCur == JNI_TRUE) {
		  char buffer [MAX_PATH];
		  GetCurrentDirectory (MAX_PATH, buffer);
		  // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
		  sprintf_s (fileName, 256, "%s\\%s%d.bmp", pathFileC, cfilespec,iCounter);						
		}else
		  // WARNING: sfxfer.FileName holds a maximum of 256 bytes.
		  sprintf_s (fileName, 256, "%s%d.bmp", cfilespec, iCounter++);

		file = CreateFile(fileName,GENERIC_WRITE,0,NULL,CREATE_ALWAYS,FILE_ATTRIBUTE_NORMAL,NULL);  
		if (isCopy == JNI_TRUE){
		   env->ReleaseStringUTFChars (jfilespec, cfilespec);
		   env->ReleaseStringUTFChars (pathFile, pathFileC);
		}
	if(file==INVALID_HANDLE_VALUE){
		throwJTE (env, "Cannot write BMP in Disk(saveAsBmp)");
		return FALSE;
	}else{
		int nColors = 1 << lpbi->biBitCount;
	 
		// Fill in the fields of the file header 
		hdr.bfType		= ((WORD) ('M' << 8) | 'B');	// is always "BM"
		hdr.bfSize		= (DWORD)(GlobalSize (hDIB) + sizeof( hdr ));
		hdr.bfReserved1 = 0;
		hdr.bfReserved2 = 0;

		hdr.bfOffBits	= (DWORD) (sizeof( hdr ) + lpbi->biSize);
		if(lpbi->biBitCount<24){		
			hdr.bfOffBits+= (DWORD) (nColors * sizeof(RGBQUAD));
		}
	 
		// Write the file header 
		//file.Write( &hdr, sizeof(hdr) );
	 
		// Write the DIB header and the bits 
		//file.Write( lpbi, (UINT)GlobalSize(hDIB) )
	 
		 //Sets up the new bmp to be written to
	     
	 
		 WriteFile(file,&hdr,sizeof(hdr),&write,NULL);
		 WriteFile(file,lpbi,(UINT)GlobalSize(hDIB),&write,NULL);
	 
		 //for (int i = 0; i < 512*512*24; i++){
		 //    image[i].rgbtBlue = 255;
		 //    image[i].rgbtGreen = 0;
		 //    image[i].rgbtRed = 0;
		 //}
	 
		 //WriteFile(file, (UINT)GlobalSize(hDIB), lpbi->biSizeImage, &write, NULL);
	 
		 CloseHandle(file);
	}
	GlobalUnlock (hDIB);
	return TRUE;
}

